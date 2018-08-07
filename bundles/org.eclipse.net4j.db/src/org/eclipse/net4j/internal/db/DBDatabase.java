/*
 * Copyright (c) 2013, 2015, 2016 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.net4j.internal.db;

import org.eclipse.net4j.db.DBException;
import org.eclipse.net4j.db.DBUtil;
import org.eclipse.net4j.db.DBUtil.RunnableWithConnection;
import org.eclipse.net4j.db.IDBConnection;
import org.eclipse.net4j.db.IDBConnectionProvider;
import org.eclipse.net4j.db.IDBDatabase;
import org.eclipse.net4j.db.ddl.IDBSchema;
import org.eclipse.net4j.db.ddl.delta.IDBSchemaDelta;
import org.eclipse.net4j.internal.db.ddl.delta.DBSchemaDelta;
import org.eclipse.net4j.spi.db.DBAdapter;
import org.eclipse.net4j.spi.db.ddl.InternalDBSchema;
import org.eclipse.net4j.util.WrappedException;
import org.eclipse.net4j.util.container.SetContainer;
import org.eclipse.net4j.util.event.Event;
import org.eclipse.net4j.util.security.IUserAware;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * @author Eike Stepper
 */
public final class DBDatabase extends SetContainer<IDBConnection> implements IDBDatabase
{
  private DBAdapter adapter;

  private IDBConnectionProvider connectionProvider;

  private int statementCacheCapacity = DEFAULT_STATEMENT_CACHE_CAPACITY;

  private IDBSchema schema;

  private DBSchemaTransaction schemaTransaction;

  private final LinkedList<SchemaAccess> schemaAccessQueue = new LinkedList<SchemaAccess>();

  public DBDatabase(final DBAdapter adapter, IDBConnectionProvider connectionProvider, final String schemaName, final boolean fixNullableIndexColumns)
  {
    super(IDBConnection.class);
    this.adapter = adapter;
    this.connectionProvider = connectionProvider;

    schema = DBUtil.execute(DBDatabase.this, new RunnableWithConnection<IDBSchema>()
    {
      public IDBSchema run(Connection connection) throws SQLException
      {
        return DBUtil.readSchema(adapter, connection, schemaName, fixNullableIndexColumns);
      }
    });

    ((InternalDBSchema)schema).lock();
    activate();
  }

  public String getUserID()
  {
    if (connectionProvider instanceof IUserAware)
    {
      return ((IUserAware)connectionProvider).getUserID();
    }

    return null;
  }

  public DBAdapter getAdapter()
  {
    return adapter;
  }

  public IDBSchema getSchema()
  {
    return schema;
  }

  public DBSchemaTransaction openSchemaTransaction()
  {
    DBSchemaTransaction schemaTransaction = new DBSchemaTransaction(this);
    this.schemaTransaction = schemaTransaction;
    return schemaTransaction;
  }

  public void closeSchemaTransaction(DBSchemaDelta delta)
  {
    try
    {
      beginSchemaAccess(true);

      for (IDBConnection transaction : getConnections())
      {
        ((DBConnection)transaction).invalidateStatementCache();
      }

      fireEvent(new SchemaChangedEventImpl(delta));
    }
    finally
    {
      schemaTransaction = null;
      endSchemaAccess();
    }
  }

  public DBSchemaTransaction getSchemaTransaction()
  {
    return schemaTransaction;
  }

  public void updateSchema(RunnableWithSchema runnable)
  {
    DBSchemaTransaction schemaTransaction = openSchemaTransaction();

    try
    {
      IDBSchema workingCopy = schemaTransaction.getWorkingCopy();
      runnable.run(workingCopy);
      schemaTransaction.commit();
    }
    finally
    {
      schemaTransaction.close();
    }
  }

  public DBConnection getConnection()
  {
    Connection delegate = connectionProvider.getConnection();
    if (delegate == null)
    {
      throw new DBException("No connection from connection provider: " + connectionProvider);
    }

    delegate = adapter.modifyConnection(delegate);

    DBConnection connection = new DBConnection(this, delegate);
    addElement(connection);
    return connection;
  }

  public void closeConnection(DBConnection connection)
  {
    removeElement(connection);
  }

  public IDBConnection[] getConnections()
  {
    return getElements();
  }

  public int getStatementCacheCapacity()
  {
    return statementCacheCapacity;
  }

  public void setStatementCacheCapacity(int statementCacheCapacity)
  {
    this.statementCacheCapacity = statementCacheCapacity;
  }

  public boolean isClosed()
  {
    return !isActive();
  }

  public void close()
  {
    deactivate();
  }

  @Override
  protected void doDeactivate() throws Exception
  {
    for (IDBConnection connection : getConnections())
    {
      connection.close();
    }

    super.doDeactivate();
  }

  public void beginSchemaAccess(boolean write)
  {
    SchemaAccess schemaAccess = null;
    synchronized (schemaAccessQueue)
    {
      if (write)
      {
        schemaAccess = new WriteSchemaAccess();
        schemaAccessQueue.addLast(schemaAccess);
      }
      else
      {
        if (!schemaAccessQueue.isEmpty())
        {
          schemaAccess = schemaAccessQueue.getFirst();
          if (schemaAccess instanceof ReadSchemaAccess)
          {
            ReadSchemaAccess readSchemaAccess = (ReadSchemaAccess)schemaAccess;
            readSchemaAccess.incrementReaders();
          }
          else
          {
            schemaAccess = null;
          }
        }

        if (schemaAccess == null)
        {
          schemaAccess = new ReadSchemaAccess();
          schemaAccessQueue.addLast(schemaAccess);
        }
      }
    }

    for (;;)
    {
      synchronized (schemaAccessQueue)
      {
        if (schemaAccessQueue.getFirst() == schemaAccess)
        {
          return;
        }

        try
        {
          schemaAccessQueue.wait();
        }
        catch (InterruptedException ex)
        {
          throw WrappedException.wrap(ex);
        }
      }
    }
  }

  public void endSchemaAccess()
  {
    synchronized (schemaAccessQueue)
    {
      SchemaAccess schemaAccess = schemaAccessQueue.getFirst();
      if (schemaAccess instanceof ReadSchemaAccess)
      {
        ReadSchemaAccess readSchemaAccess = (ReadSchemaAccess)schemaAccess;
        if (readSchemaAccess.decrementReaders())
        {
          return;
        }
      }

      schemaAccessQueue.removeFirst();
      schemaAccessQueue.notifyAll();
    }
  }

  public String convertString(DBPreparedStatement preparedStatement, int parameterIndex, String value)
  {
    return adapter.convertString(preparedStatement, parameterIndex, value);
  }

  public String convertString(DBResultSet resultSet, int columnIndex, String value)
  {
    return adapter.convertString(resultSet, columnIndex, value);
  }

  public String convertString(DBResultSet resultSet, String columnLabel, String value)
  {
    return adapter.convertString(resultSet, columnLabel, value);
  }

  /**
   * @author Eike Stepper
   */
  private interface SchemaAccess
  {
  }

  /**
   * @author Eike Stepper
   */
  private final class ReadSchemaAccess implements SchemaAccess
  {
    private int readers = 1;

    public void incrementReaders()
    {
      ++readers;
    }

    public boolean decrementReaders()
    {
      return --readers > 0;
    }

    @Override
    public String toString()
    {
      return "READERS[" + readers + "]";
    }
  }

  /**
   * @author Eike Stepper
   */
  private final class WriteSchemaAccess implements SchemaAccess
  {
    @Override
    public String toString()
    {
      return "WRITER";
    }
  }

  /**
   * @author Eike Stepper
   */
  private final class SchemaChangedEventImpl extends Event implements SchemaChangedEvent
  {
    private static final long serialVersionUID = 1L;

    private final IDBSchemaDelta schemaDelta;

    public SchemaChangedEventImpl(IDBSchemaDelta schemaDelta)
    {
      super(DBDatabase.this);
      this.schemaDelta = schemaDelta;
    }

    @Override
    public IDBDatabase getSource()
    {
      return (IDBDatabase)super.getSource();
    }

    public IDBSchemaDelta getSchemaDelta()
    {
      return schemaDelta;
    }
  }
}

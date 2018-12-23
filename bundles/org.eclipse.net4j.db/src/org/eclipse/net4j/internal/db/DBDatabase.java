/*
 * Copyright (c) 2013, 2015, 2016, 2018 Eike Stepper (Loehne, Germany) and others.
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
import org.eclipse.net4j.util.concurrent.TimeoutRuntimeException;
import org.eclipse.net4j.util.container.SetContainer;
import org.eclipse.net4j.util.event.Event;
import org.eclipse.net4j.util.io.IOUtil;
import org.eclipse.net4j.util.om.OMPlatform;
import org.eclipse.net4j.util.security.IUserAware;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * @author Eike Stepper
 */
public final class DBDatabase extends SetContainer<IDBConnection> implements IDBDatabase
{
  private static final long TIMEOUT_SCHEMA_ACCESS = OMPlatform.INSTANCE.getProperty("org.eclipse.net4j.internal.db.DBDatabase.TIMEOUT_SCHEMA_ACCESS", 15000L);

  private static final boolean DEBUG_SCHEMA_ACCESS = OMPlatform.INSTANCE.isProperty("org.eclipse.net4j.internal.db.DBDatabase.DEBUG_SCHEMA_ACCESS");

  private DBAdapter adapter;

  private IDBConnectionProvider connectionProvider;

  private int statementCacheCapacity = DEFAULT_STATEMENT_CACHE_CAPACITY;

  private IDBSchema schema;

  private final LinkedList<SchemaAccess> schemaAccessQueue = new LinkedList<SchemaAccess>();

  private int schemaWriters;

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
    return openSchemaTransaction(null);
  }

  public DBSchemaTransaction openSchemaTransaction(IDBConnection connection)
  {
    DBSchemaTransaction schemaTransaction = new DBSchemaTransaction(this);
    schemaTransaction.setConnection((DBConnection)connection);
    return schemaTransaction;
  }

  public void closeSchemaTransaction(DBSchemaDelta delta)
  {
    if (delta == null || delta.isEmpty())
    {
      return;
    }

    try
    {
      beginSchemaAccess(true);

      for (IDBConnection transaction : getConnections())
      {
        ((DBConnection)transaction).invalidateStatementCache();
      }

      fireEvent(new SchemaChangedEventImpl(this, delta));
    }
    finally
    {
      endSchemaAccess();
    }
  }

  @Deprecated
  public DBSchemaTransaction getSchemaTransaction()
  {
    throw new UnsupportedOperationException();
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
    if (DEBUG_SCHEMA_ACCESS)
    {
      try
      {
        throw new Exception("Begin " + (write ? "write" : "read") + " schema access: " + schema.getName());
      }
      catch (Exception ex)
      {
        ex.printStackTrace(IOUtil.OUT());
      }
    }

    SchemaAccess schemaAccess = null;
    synchronized (schemaAccessQueue)
    {
      if (write)
      {
        schemaAccess = new WriteSchemaAccess();
        schemaAccessQueue.addLast(schemaAccess);
        ++schemaWriters;
      }
      else
      {
        if (schemaWriters == 0 && !schemaAccessQueue.isEmpty())
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

    long end = System.currentTimeMillis() + TIMEOUT_SCHEMA_ACCESS;

    do
    {
      synchronized (schemaAccessQueue)
      {
        if (schemaAccessQueue.getFirst() == schemaAccess)
        {
          if (write)
          {
            --schemaWriters;
          }

          return;
        }

        try
        {
          schemaAccessQueue.wait(1000L);
        }
        catch (InterruptedException ex)
        {
          Thread.currentThread().interrupt();
          throw WrappedException.wrap(ex);
        }
      }
    } while (System.currentTimeMillis() < end);

    throw new TimeoutRuntimeException(
        "Schema " + schema.getName() + " could not be locked for " + (write ? "write" : "read") + " access within " + TIMEOUT_SCHEMA_ACCESS + " milliseconds");
  }

  public void endSchemaAccess()
  {
    if (DEBUG_SCHEMA_ACCESS)
    {
      try
      {
        throw new Exception("End schema access: " + schema.getName());
      }
      catch (Exception ex)
      {
        ex.printStackTrace(IOUtil.OUT());
      }
    }

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
  private static final class ReadSchemaAccess implements SchemaAccess
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
  private static final class WriteSchemaAccess implements SchemaAccess
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
  private static final class SchemaChangedEventImpl extends Event implements SchemaChangedEvent
  {
    private static final long serialVersionUID = 1L;

    private final IDBSchemaDelta schemaDelta;

    public SchemaChangedEventImpl(DBDatabase database, IDBSchemaDelta schemaDelta)
    {
      super(database);
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

/*
 * Copyright (c) 2013, 2018 Eike Stepper (Loehne, Germany) and others.
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
import org.eclipse.net4j.db.IDBSchemaTransaction;
import org.eclipse.net4j.db.ddl.IDBSchema;
import org.eclipse.net4j.db.ddl.delta.IDBDeltaVisitor;
import org.eclipse.net4j.db.ddl.delta.IDBSchemaDelta;
import org.eclipse.net4j.internal.db.ddl.DelegatingDBSchema;
import org.eclipse.net4j.internal.db.ddl.DelegatingDBSchemaElement;
import org.eclipse.net4j.internal.db.ddl.delta.DBSchemaDelta;
import org.eclipse.net4j.spi.db.DBAdapter;
import org.eclipse.net4j.spi.db.ddl.InternalDBSchema;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Eike Stepper
 */
public final class DBSchemaTransaction implements IDBSchemaTransaction, RunnableWithConnection<DBSchemaDelta>
{
  private DBDatabase database;

  private DBConnection connection;

  private IDBSchema oldSchema;

  private IDBSchema oldSchemaCopy;

  private IDBSchema workingCopy;

  public DBSchemaTransaction(DBDatabase database)
  {
    this.database = database;

    oldSchema = database.getSchema();
    oldSchemaCopy = DBUtil.copySchema(oldSchema);

    IDBSchema copy = DBUtil.copySchema(oldSchema);
    workingCopy = DelegatingDBSchemaElement.wrap(copy);
  }

  public DBDatabase getDatabase()
  {
    return database;
  }

  public DBConnection getConnection()
  {
    return connection;
  }

  public void setConnection(DBConnection connection)
  {
    this.connection = connection;
  }

  public IDBSchema getWorkingCopy()
  {
    return workingCopy;
  }

  public DBSchemaDelta ensureSchema(IDBSchema schema, IDBDeltaVisitor.Filter.Policy policy)
  {
    IDBSchema workingCopy = getWorkingCopy();

    IDBDeltaVisitor.Copier copier = new IDBDeltaVisitor.Copier(policy);
    IDBSchemaDelta delta = schema.compare(workingCopy);
    delta.accept(copier);

    DBSchemaDelta result = (DBSchemaDelta)copier.getResult();
    result.setName(workingCopy.getName());
    result.applyTo(workingCopy);
    return result;
  }

  public DBSchemaDelta ensureSchema(IDBSchema schema)
  {
    return ensureSchema(schema, DEFAULT_ENSURE_SCHEMA_POLICY);
  }

  public DBSchemaDelta getSchemaDelta()
  {
    return (DBSchemaDelta)workingCopy.compare(oldSchemaCopy);
  }

  public DBSchemaDelta commit()
  {
    if (connection == null)
    {
      return DBUtil.execute(database, this);
    }

    // Remember connection locally because the instance field will be reset in run/doClose.
    DBConnection connection = this.connection;

    try
    {
      DBSchemaDelta result = run(connection);
      connection.commit();
      return result;
    }
    catch (SQLException ex)
    {
      DBUtil.rollbackSilently(connection);
      throw new DBException(ex);
    }
  }

  public DBSchemaDelta run(Connection connection) throws SQLException
  {
    DBSchemaDelta delta = getSchemaDelta();

    synchronized (oldSchema)
    {
      try
      {
        ((InternalDBSchema)oldSchema).unlock();

        DBAdapter adapter = database.getAdapter();
        adapter.updateSchema(connection, oldSchema, delta);

        ((DelegatingDBSchema)workingCopy).setDelegate(oldSchema);
      }
      finally
      {
        ((InternalDBSchema)oldSchema).lock();
        doClose(delta);
      }
    }

    return delta;
  }

  public void close()
  {
    doClose(null);
  }

  private void doClose(DBSchemaDelta delta)
  {
    if (!isClosed())
    {
      database.closeSchemaTransaction(delta);
      connection = null;
      oldSchema = null;
      oldSchemaCopy = null;
      workingCopy = null;
    }
  }

  public boolean isClosed()
  {
    return workingCopy == null;
  }
}

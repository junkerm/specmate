/*
 * Copyright (c) 2013 Eike Stepper (Loehne, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.net4j.internal.db.ddl;

import org.eclipse.net4j.db.DBException;
import org.eclipse.net4j.db.IDBAdapter;
import org.eclipse.net4j.db.IDBConnectionProvider;
import org.eclipse.net4j.db.ddl.IDBField;
import org.eclipse.net4j.db.ddl.IDBIndex.Type;
import org.eclipse.net4j.db.ddl.IDBSchema;
import org.eclipse.net4j.db.ddl.IDBSchemaElement;
import org.eclipse.net4j.db.ddl.IDBTable;
import org.eclipse.net4j.db.ddl.SchemaElementNotFoundException;
import org.eclipse.net4j.db.ddl.delta.IDBSchemaDelta;
import org.eclipse.net4j.spi.db.ddl.InternalDBSchema;

import javax.sql.DataSource;

import java.io.PrintStream;
import java.sql.Connection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Eike Stepper
 */
public final class DelegatingDBSchema extends DelegatingDBSchemaElement implements InternalDBSchema
{
  DelegatingDBSchema(InternalDBSchema delegate)
  {
    super(delegate);
  }

  @Override
  public InternalDBSchema getDelegate()
  {
    return (InternalDBSchema)super.getDelegate();
  }

  @Override
  public void setDelegate(IDBSchemaElement delegate)
  {
    IDBTable[] wrapperTables = getTables();

    IDBSchema delegateSchema = (IDBSchema)delegate;
    super.setDelegate(delegateSchema);

    for (IDBTable wrapperTable : wrapperTables)
    {
      ((DelegatingDBSchemaElement)wrapperTable).setDelegate(delegateSchema.getTable(wrapperTable.getName()));
    }
  }

  public IDBSchema getWrapper()
  {
    return this;
  }

  @Override
  public IDBSchemaElement getParent()
  {
    return wrap(getDelegate().getParent());
  }

  public IDBTable addTable(String name)
  {
    return wrap(getDelegate().addTable(name));
  }

  public IDBTable removeTable(String name)
  {
    return wrap(getDelegate().removeTable(name));
  }

  public String createIndexName(IDBTable table, Type type, IDBField[] fields, int position)
  {
    return getDelegate().createIndexName(unwrap(table), type, fields, position);
  }

  public boolean isLocked()
  {
    return getDelegate().isLocked();
  }

  public boolean lock()
  {
    return getDelegate().lock();
  }

  @SuppressWarnings("unchecked")
  public <T extends IDBSchemaElement> T findElement(IDBSchemaElement prototype)
  {
    T unwrapped = (T)unwrap(prototype);
    /** BEGIN SPECMATE PATCH */
    IDBSchemaElement element = getDelegate().findElement(unwrapped);
    return (T)wrap(element);
    /** END SPECMATE PATCH */
  }

  public boolean unlock()
  {
    return getDelegate().unlock();
  }

  public void assertUnlocked() throws DBException
  {
    getDelegate().assertUnlocked();
  }

  public IDBTable getTableSafe(String name) throws SchemaElementNotFoundException
  {
    return wrap(getDelegate().getTableSafe(name));
  }

  public IDBTable getTable(String name)
  {
    return wrap(getDelegate().getTable(name));
  }

  public IDBTable[] getTables()
  {
    IDBTable[] tables = getDelegate().getTables();
    IDBTable[] wrappers = new IDBTable[tables.length];
    for (int i = 0; i < tables.length; i++)
    {
      wrappers[i] = wrap(tables[i]);
    }

    return wrappers;
  }

  public Set<IDBTable> create(IDBAdapter dbAdapter, Connection connection) throws DBException
  {
    return wrap(getDelegate().create(dbAdapter, connection));
  }

  public Set<IDBTable> create(IDBAdapter dbAdapter, DataSource dataSource) throws DBException
  {
    return wrap(getDelegate().create(dbAdapter, dataSource));
  }

  public Set<IDBTable> create(IDBAdapter dbAdapter, IDBConnectionProvider connectionProvider) throws DBException
  {
    return wrap(getDelegate().create(dbAdapter, connectionProvider));
  }

  public void drop(IDBAdapter dbAdapter, Connection connection) throws DBException
  {
    getDelegate().drop(dbAdapter, connection);
  }

  public void drop(IDBAdapter dbAdapter, DataSource dataSource) throws DBException
  {
    getDelegate().drop(dbAdapter, dataSource);
  }

  public void drop(IDBAdapter dbAdapter, IDBConnectionProvider connectionProvider) throws DBException
  {
    getDelegate().drop(dbAdapter, connectionProvider);
  }

  public void export(Connection connection, PrintStream out) throws DBException
  {
    getDelegate().export(connection, out);
  }

  public void export(DataSource dataSource, PrintStream out) throws DBException
  {
    getDelegate().export(dataSource, out);
  }

  public void export(IDBConnectionProvider connectionProvider, PrintStream out) throws DBException
  {
    getDelegate().export(connectionProvider, out);
  }

  public IDBSchemaDelta compare(IDBSchema oldSchema)
  {
    return getDelegate().compare(unwrap(oldSchema));
  }

  private static Set<IDBTable> wrap(Set<IDBTable> tables)
  {
    return wrap(tables, new HashSet<IDBTable>());
  }
}

/*
 * Copyright (c) 2008-2013 Eike Stepper (Loehne, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.net4j.spi.db;

import org.eclipse.net4j.db.DBException;
import org.eclipse.net4j.db.DBUtil;
import org.eclipse.net4j.db.IDBAdapter;
import org.eclipse.net4j.db.IDBConnectionProvider;
import org.eclipse.net4j.db.ddl.IDBSchema;
import org.eclipse.net4j.db.ddl.IDBTable;
import org.eclipse.net4j.util.event.IEvent;
import org.eclipse.net4j.util.event.IListener;

import javax.sql.DataSource;

import java.io.PrintStream;
import java.sql.Connection;
import java.util.Set;
import java.util.concurrent.ExecutorService;

/**
 * @author Eike Stepper
 * @deprecated As of 4.2 call {@link DBUtil#createSchema(String)}, {@link DBUtil#readSchema(IDBAdapter, Connection, IDBSchema)},
 *    {@link DBUtil#readSchema(IDBAdapter, Connection, String)} or {@link DBUtil#copySchema(IDBSchema)}.
 */
@Deprecated
public class DBSchema extends org.eclipse.net4j.internal.db.ddl.DBSchema
{
  private static final long serialVersionUID = 1L;

  public DBSchema(String name)
  {
    super(name);
  }

  /**
   * @since 4.2
   */
  public DBSchema(IDBSchema source)
  {
    super(source);
  }

  /**
   * Constructor for deserialization.
   *
   * @since 4.2
   */
  protected DBSchema()
  {
  }

  @Override
  public IDBSchema getSchema()
  {
    return super.getSchema();
  }

  @Override
  public String getFullName()
  {
    return super.getFullName();
  }

  @Override
  public IDBTable addTable(String name) throws DBException
  {
    return super.addTable(name);
  }

  @Override
  public IDBTable removeTable(String name)
  {
    return super.removeTable(name);
  }

  @Override
  public IDBTable getTable(String name)
  {
    return super.getTable(name);
  }

  @Override
  public IDBTable[] getTables()
  {
    return super.getTables();
  }

  @Override
  public boolean isLocked()
  {
    return super.isLocked();
  }

  @Override
  public boolean lock()
  {
    return super.lock();
  }

  @Override
  public void assertUnlocked() throws DBException
  {
    super.assertUnlocked();
  }

  @Override
  public Set<IDBTable> create(IDBAdapter dbAdapter, Connection connection) throws DBException
  {
    return super.create(dbAdapter, connection);
  }

  @Override
  public Set<IDBTable> create(IDBAdapter dbAdapter, DataSource dataSource) throws DBException
  {
    return super.create(dbAdapter, dataSource);
  }

  @Override
  public Set<IDBTable> create(IDBAdapter dbAdapter, IDBConnectionProvider connectionProvider) throws DBException
  {
    return super.create(dbAdapter, connectionProvider);
  }

  @Override
  public void drop(IDBAdapter dbAdapter, Connection connection) throws DBException
  {
    super.drop(dbAdapter, connection);
  }

  @Override
  public void drop(IDBAdapter dbAdapter, DataSource dataSource) throws DBException
  {
    super.drop(dbAdapter, dataSource);
  }

  @Override
  public void drop(IDBAdapter dbAdapter, IDBConnectionProvider connectionProvider) throws DBException
  {
    super.drop(dbAdapter, connectionProvider);
  }

  @Override
  public void export(Connection connection, PrintStream out) throws DBException
  {
    super.export(connection, out);
  }

  @Override
  public void export(DataSource dataSource, PrintStream out) throws DBException
  {
    super.export(dataSource, out);
  }

  @Override
  public void export(IDBConnectionProvider connectionProvider, PrintStream out) throws DBException
  {
    super.export(connectionProvider, out);
  }

  @Override
  public String getName()
  {
    return super.getName();
  }

  @Override
  public String toString()
  {
    return super.toString();
  }

  @Override
  public void addListener(IListener listener)
  {
    super.addListener(listener);
  }

  @Override
  public void removeListener(IListener listener)
  {
    super.removeListener(listener);
  }

  @Override
  public boolean hasListeners()
  {
    return super.hasListeners();
  }

  @Override
  public IListener[] getListeners()
  {
    return super.getListeners();
  }

  @Override
  public void fireEvent()
  {
    super.fireEvent();
  }

  @Override
  public void fireEvent(IEvent event)
  {
    super.fireEvent(event);
  }

  @Override
  public void fireEvent(IEvent event, IListener[] listeners)
  {
    super.fireEvent(event, listeners);
  }

  @Override
  protected void fireThrowable(Throwable throwable)
  {
    super.fireThrowable(throwable);
  }

  @Override
  protected ExecutorService getNotificationService()
  {
    return super.getNotificationService();
  }

  @Override
  protected void firstListenerAdded()
  {
    super.firstListenerAdded();
  }

  @Override
  protected void lastListenerRemoved()
  {
    super.lastListenerRemoved();
  }

  @Override
  protected void finalize() throws Throwable
  {
    super.finalize();
  }
}

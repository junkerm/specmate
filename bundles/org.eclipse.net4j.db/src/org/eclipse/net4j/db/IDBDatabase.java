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
package org.eclipse.net4j.db;

import org.eclipse.net4j.db.ddl.IDBSchema;
import org.eclipse.net4j.db.ddl.delta.IDBSchemaDelta;
import org.eclipse.net4j.util.collection.Closeable;
import org.eclipse.net4j.util.container.IContainer;
import org.eclipse.net4j.util.event.IEvent;

/**
 * @author Eike Stepper
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 * @since 4.2
 */
public interface IDBDatabase extends IContainer<IDBConnection>, IDBConnectionProvider2, Closeable
{
  public static final int DEFAULT_STATEMENT_CACHE_CAPACITY = 200;

  public IDBAdapter getAdapter();

  public IDBSchema getSchema();

  public IDBSchemaTransaction openSchemaTransaction();

  /**
   * @since 4.7
   */
  public IDBSchemaTransaction openSchemaTransaction(IDBConnection connection);

  /**
   * @deprecated As of 4.7 no longer supported in favor of support for multiple schema transactions.
   */
  @Deprecated
  public IDBSchemaTransaction getSchemaTransaction();

  public void updateSchema(RunnableWithSchema runnable);

  public IDBConnection getConnection();

  public IDBConnection[] getConnections();

  public int getStatementCacheCapacity();

  public void setStatementCacheCapacity(int statementCacheCapacity);

  /**
   * @author Eike Stepper
   */
  public interface SchemaChangedEvent extends IEvent
  {
    public IDBDatabase getSource();

    public IDBSchemaDelta getSchemaDelta();
  }

  /**
   * @author Eike Stepper
   */
  public interface RunnableWithSchema
  {
    public void run(IDBSchema schema);
  }
}

/*
 * Copyright (c) 2013, 2015 Eike Stepper (Berlin, Germany) and others.
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
import org.eclipse.net4j.db.ddl.delta.IDBDelta.ChangeKind;
import org.eclipse.net4j.db.ddl.delta.IDBDelta.DeltaType;
import org.eclipse.net4j.db.ddl.delta.IDBDeltaVisitor;
import org.eclipse.net4j.db.ddl.delta.IDBDeltaVisitor.Filter.Policy;
import org.eclipse.net4j.db.ddl.delta.IDBSchemaDelta;
import org.eclipse.net4j.util.collection.Closeable;

/**
 * @since 4.2
 * @author Eike Stepper
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface IDBSchemaTransaction extends Closeable
{
  public static final Policy DEFAULT_ENSURE_SCHEMA_POLICY = //
      new IDBDeltaVisitor.Filter.Policy().allow(DeltaType.SCHEMA, ChangeKind.CHANGE).allow(ChangeKind.ADD).freeze();

  public IDBDatabase getDatabase();

  public IDBConnection getConnection();

  public IDBSchema getWorkingCopy();

  public IDBSchemaDelta ensureSchema(IDBSchema schema, IDBDeltaVisitor.Filter.Policy policy);

  public IDBSchemaDelta ensureSchema(IDBSchema schema);

  public IDBSchemaDelta getSchemaDelta();

  public IDBSchemaDelta commit();
}

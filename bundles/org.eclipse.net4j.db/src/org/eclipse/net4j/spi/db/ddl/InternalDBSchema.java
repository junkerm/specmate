/*
 * Copyright (c) 2013 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.net4j.spi.db.ddl;

import org.eclipse.net4j.db.DBException;
import org.eclipse.net4j.db.ddl.IDBField;
import org.eclipse.net4j.db.ddl.IDBIndex;
import org.eclipse.net4j.db.ddl.IDBSchema;
import org.eclipse.net4j.db.ddl.IDBTable;

/**
 * @since 4.2
 * @author Eike Stepper
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface InternalDBSchema extends IDBSchema, InternalDBSchemaElement
{
  public static final IDBTable[] NO_TABLES = {};

  public IDBSchema getWrapper();

  public IDBTable addTable(String name);

  public IDBTable removeTable(String name);

  public String createIndexName(IDBTable table, IDBIndex.Type type, IDBField[] fields, int position);

  public boolean lock();

  public boolean unlock();

  public void assertUnlocked() throws DBException;
}

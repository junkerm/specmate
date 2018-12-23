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
package org.eclipse.net4j.db.ddl.delta;

import org.eclipse.net4j.db.ddl.IDBSchema;
import org.eclipse.net4j.db.ddl.IDBTable;

import java.util.Map;

/**
 * @since 4.2
 * @author Eike Stepper
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface IDBTableDelta extends IDBDelta
{
  public IDBSchemaDelta getParent();

  public int getFieldDeltaCount();

  public int getIndexDeltaCount();

  public IDBFieldDelta getFieldDelta(int position);

  public IDBFieldDelta getFieldDelta(String name);

  public IDBIndexDelta getIndexDelta(String name);

  public Map<String, IDBFieldDelta> getFieldDeltas();

  public Map<String, IDBIndexDelta> getIndexDeltas();

  public IDBFieldDelta[] getFieldDeltasSortedByPosition();

  public IDBIndexDelta[] getIndexDeltasSortedByName();

  public IDBTable getSchemaElement(IDBSchema schema);
}

/*
 * Copyright (c) 2013, 2015 Eike Stepper (Loehne, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.net4j.db.ddl.delta;

import org.eclipse.net4j.db.ddl.IDBIndex;
import org.eclipse.net4j.db.ddl.IDBSchema;

import java.util.Map;

/**
 * @since 4.2
 * @author Eike Stepper
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface IDBIndexDelta extends IDBDeltaWithProperties
{
  public static final String TYPE_PROPERTY = "type";

  /**
   * @since 4.5
   */
  public static final String OPTIONAL_PROPERTY = "optional";

  public IDBTableDelta getParent();

  public int getIndexFieldDeltaCount();

  public IDBIndexFieldDelta getIndexFieldDelta(int position);

  public IDBIndexFieldDelta getIndexFieldDelta(String name);

  public Map<String, IDBIndexFieldDelta> getIndexFieldDeltas();

  public IDBIndexFieldDelta[] getIndexFieldDeltasSortedByPosition();

  public IDBIndex getSchemaElement(IDBSchema schema);
}

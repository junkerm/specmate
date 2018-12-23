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

import org.eclipse.net4j.db.ddl.IDBField;
import org.eclipse.net4j.db.ddl.IDBSchema;

/**
 * @since 4.2
 * @author Eike Stepper
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface IDBFieldDelta extends IDBDeltaWithPosition
{
  public static final String TYPE_PROPERTY = "type";

  public static final String PRECISION_PROPERTY = "precision";

  public static final String SCALE_PROPERTY = "scale";

  public static final String NOT_NULL_PROPERTY = "notNull";

  public IDBTableDelta getParent();

  public IDBField getSchemaElement(IDBSchema schema);
}

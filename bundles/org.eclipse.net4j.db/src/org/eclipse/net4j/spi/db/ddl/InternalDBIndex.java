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
package org.eclipse.net4j.spi.db.ddl;

import org.eclipse.net4j.db.ddl.IDBIndex;
import org.eclipse.net4j.db.ddl.IDBIndexField;

/**
 * @since 4.2
 * @author Eike Stepper
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface InternalDBIndex extends IDBIndex, InternalDBSchemaElement
{
  public static final IDBIndexField[] NO_INDEX_FIELDS = {};

  public IDBIndex getWrapper();

  public void removeIndexField(IDBIndexField indexFieldToRemove);

  /**
   * @since 4.5
   */
  public boolean isOptional();

  /**
   * @since 4.5
   */
  public void setOptional(boolean optional);
}

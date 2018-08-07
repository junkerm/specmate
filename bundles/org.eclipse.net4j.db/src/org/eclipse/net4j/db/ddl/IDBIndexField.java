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
package org.eclipse.net4j.db.ddl;

import org.eclipse.net4j.util.collection.PositionProvider;

/**
 * An index field specification in a {@link IDBIndex DB index}.
 *
 * @since 4.2
 * @author Eike Stepper
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IDBIndexField extends IDBSchemaElement, PositionProvider
{
  /**
   * @since 4.2
   */
  public IDBIndex getParent();

  public IDBIndex getIndex();

  public IDBField getField();
}

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
package org.eclipse.net4j.internal.db.ddl.delta;

import org.eclipse.net4j.db.ddl.delta.IDBDeltaWithPosition;

/**
 * @author Eike Stepper
 */
public abstract class DBDeltaWithPosition extends DBDeltaWithProperties implements IDBDeltaWithPosition
{
  private static final long serialVersionUID = 1L;

  public DBDeltaWithPosition(DBDelta parent, String name, ChangeKind changeKind)
  {
    super(parent, name, changeKind);
  }

  /**
   * Constructor for deserialization.
   */
  protected DBDeltaWithPosition()
  {
  }

  public int getPosition()
  {
    Integer value = getPropertyValue(POSITION_PROPERTY);
    if (value == null)
    {
      return 0;
    }

    return value;
  }
}

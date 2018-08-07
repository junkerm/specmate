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
package org.eclipse.net4j.db.ddl.delta;

/**
 * @since 4.2
 * @author Eike Stepper
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface IDBPropertyDelta<T> extends IDBDelta
{
  public IDBPropertyDelta.Type getType();

  public T getValue();

  public T getOldValue();

  /**
   * @author Eike Stepper
   */
  public enum Type
  {
    BOOLEAN, INTEGER, STRING, FIELD_TYPE, INDEX_TYPE
  }
}

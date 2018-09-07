/*
 * Copyright (c) 2008, 2011-2013 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.net4j.db.ddl;

/**
 * An index specification in a {@link IDBTable DB table}.
 *
 * @author Eike Stepper
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IDBIndex extends IDBSchemaElement
{
  /**
   * @since 4.2
   */
  public IDBTable getParent();

  public IDBTable getTable();

  public Type getType();

  /**
   * @since 4.2
   */
  public void setType(Type type);

  @Deprecated
  public int getPosition();

  /**
   * @since 4.2
   */
  public IDBIndexField addIndexField(IDBField field);

  /**
   * @since 4.2
   */
  public IDBIndexField addIndexField(String name) throws SchemaElementNotFoundException;

  /**
   * @since 4.2
   */
  public IDBIndexField getIndexFieldSafe(String name) throws SchemaElementNotFoundException;

  /**
   * @since 4.2
   */
  public IDBIndexField getIndexField(String name);

  /**
   * @since 4.2
   */
  public IDBIndexField getIndexField(int position);

  /**
   * @since 4.2
   */
  public IDBField getFieldSafe(String name) throws SchemaElementNotFoundException;

  /**
   * @since 4.2
   */
  public IDBField getField(String name);

  public IDBField getField(int position);

  public int getFieldCount();

  /**
   * @since 4.2
   */
  public IDBIndexField[] getIndexFields();

  public IDBField[] getFields();

  /**
   * The type of an {@link IDBIndex index} specification in a {@link IDBTable DB table}.
   *
   * @author Eike Stepper
   * @noextend This interface is not intended to be extended by clients.
   */
  public enum Type
  {
    PRIMARY_KEY, UNIQUE, NON_UNIQUE
  }
}

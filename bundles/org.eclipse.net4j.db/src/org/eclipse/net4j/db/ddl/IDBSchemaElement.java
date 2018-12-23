/*
 * Copyright (c) 2008, 2011-2013 Eike Stepper (Loehne, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.net4j.db.ddl;

import org.eclipse.net4j.util.container.IContainer;

/**
 * Specifies a hierachical namespace for elements in a {@link IDBSchema DB schema}.
 *
 * @author Eike Stepper
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IDBSchemaElement extends IDBNamedElement, IContainer<IDBSchemaElement>, Comparable<IDBSchemaElement>
{
  /**
   * @since 4.2
   */
  public SchemaElementType getSchemaElementType();

  public IDBSchema getSchema();

  /**
   * @since 4.2
   */
  public IDBSchemaElement getParent();

  /**
   * @since 4.2
   */
  @Deprecated
  public void setName(String name);

  public String getFullName();

  /**
   * @since 4.2
   */
  public <T extends IDBSchemaElement> T getElement(Class<T> type, String name);

  /**
   * @since 4.2
   */
  public void accept(IDBSchemaVisitor visitor);

  /**
   * @since 4.2
   */
  public void remove();

  /**
   * @since 4.2
   * @author Eike Stepper
   */
  public enum SchemaElementType
  {
    SCHEMA(0), TABLE(1), FIELD(2), INDEX(2), INDEX_FIELD(3);

    private final int level;

    private SchemaElementType(int level)
    {
      this.level = level;
    }

    public int getLevel()
    {
      return level;
    }
  }
}

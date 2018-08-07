/*
 * Copyright (c) 2013, 2016 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.net4j.db.ddl;

import org.eclipse.net4j.db.DBException;
import org.eclipse.net4j.internal.db.ddl.DBSchemaElement;

/**
 * @since 4.2
 * @author Eike Stepper
 */
public class SchemaElementNotFoundException extends DBException
{
  private static final long serialVersionUID = 1L;

  private final IDBSchemaElement parent;

  private final IDBSchemaElement.SchemaElementType type;

  private final String name;

  public SchemaElementNotFoundException(IDBSchemaElement parent, IDBSchemaElement.SchemaElementType type, String name)
  {
    super(type.toString() + " " + DBSchemaElement.name(name) + " not found in " + parent.getSchemaElementType() + " " + parent);
    this.parent = parent;
    this.type = type;
    this.name = DBSchemaElement.name(name);
  }

  public IDBSchemaElement getParent()
  {
    return parent;
  }

  public IDBSchemaElement.SchemaElementType getType()
  {
    return type;
  }

  public String getName()
  {
    return name;
  }
}

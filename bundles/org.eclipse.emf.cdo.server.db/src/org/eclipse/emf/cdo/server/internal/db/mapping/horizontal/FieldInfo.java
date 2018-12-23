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
package org.eclipse.emf.cdo.server.internal.db.mapping.horizontal;

import org.eclipse.net4j.db.DBType;
import org.eclipse.net4j.db.ddl.IDBField;

/**
 * Used by subclasses to indicate which fields should be in the table. I.e. just a triple of name, DBType and precision.
 *
 * @author Stefan Winkler
 */
public final class FieldInfo
{
  private final String name;

  private final DBType type;

  private final int precision;

  public FieldInfo(String name, DBType type, int precision)
  {
    this.name = name;
    this.type = type;
    this.precision = precision;
  }

  public FieldInfo(String name, DBType type)
  {
    this(name, type, IDBField.DEFAULT);
  }

  public String getName()
  {
    return name;
  }

  public DBType getType()
  {
    return type;
  }

  public int getPrecision()
  {
    return precision;
  }

  @Override
  public String toString()
  {
    return "FieldInfo[name=" + name + ", type=" + type + ", precision=" + precision + "]";
  }
}

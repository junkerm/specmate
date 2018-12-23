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
package org.eclipse.net4j.internal.db.ddl;

import org.eclipse.net4j.db.DBType;
import org.eclipse.net4j.db.ddl.IDBField;
import org.eclipse.net4j.db.ddl.IDBTable;
import org.eclipse.net4j.spi.db.ddl.InternalDBField;

/**
 * @author Eike Stepper
 */
public final class DelegatingDBField extends DelegatingDBSchemaElement implements InternalDBField
{
  DelegatingDBField(InternalDBField delegate)
  {
    super(delegate);
  }

  @Override
  public InternalDBField getDelegate()
  {
    return (InternalDBField)super.getDelegate();
  }

  public IDBField getWrapper()
  {
    return this;
  }

  public int getPosition()
  {
    return getDelegate().getPosition();
  }

  public void setPosition(int position)
  {
    getDelegate().setPosition(position);
  }

  public Exception getConstructionStackTrace()
  {
    return getDelegate().getConstructionStackTrace();
  }

  @Override
  public IDBTable getParent()
  {
    return wrap(getDelegate().getParent());
  }

  public IDBTable getTable()
  {
    return wrap(getDelegate().getTable());
  }

  public DBType getType()
  {
    return getDelegate().getType();
  }

  public void setType(DBType type)
  {
    getDelegate().setType(type);
  }

  public int getPrecision()
  {
    return getDelegate().getPrecision();
  }

  public void setPrecision(int precision)
  {
    getDelegate().setPrecision(precision);
  }

  public int getScale()
  {
    return getDelegate().getScale();
  }

  public void setScale(int scale)
  {
    getDelegate().setScale(scale);
  }

  public boolean isNotNull()
  {
    return getDelegate().isNotNull();
  }

  public void setNotNull(boolean notNull)
  {
    getDelegate().setNotNull(notNull);
  }

  public String formatPrecision()
  {
    return getDelegate().formatPrecision();
  }

  public String formatPrecisionAndScale()
  {
    return getDelegate().formatPrecisionAndScale();
  }
}

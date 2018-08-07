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
package org.eclipse.net4j.internal.db.ddl;

import org.eclipse.net4j.db.ddl.IDBField;
import org.eclipse.net4j.db.ddl.IDBIndex;
import org.eclipse.net4j.db.ddl.IDBIndexField;
import org.eclipse.net4j.spi.db.ddl.InternalDBIndexField;

/**
 * @author Eike Stepper
 */
public final class DelegatingDBIndexField extends DelegatingDBSchemaElement implements InternalDBIndexField
{
  DelegatingDBIndexField(InternalDBIndexField delegate)
  {
    super(delegate);
  }

  @Override
  public InternalDBIndexField getDelegate()
  {
    return (InternalDBIndexField)super.getDelegate();
  }

  public IDBIndexField getWrapper()
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

  @Override
  public IDBIndex getParent()
  {
    return wrap(getDelegate().getParent());
  }

  public IDBIndex getIndex()
  {
    return wrap(getDelegate().getIndex());
  }

  public IDBField getField()
  {
    return wrap(getDelegate().getField());
  }
}

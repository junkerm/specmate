/*
 * Copyright (c) 2013, 2015 Eike Stepper (Berlin, Germany) and others.
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
import org.eclipse.net4j.db.ddl.IDBSchemaElement;
import org.eclipse.net4j.db.ddl.IDBTable;
import org.eclipse.net4j.db.ddl.SchemaElementNotFoundException;
import org.eclipse.net4j.spi.db.ddl.InternalDBIndex;

/**
 * @author Eike Stepper
 */
public final class DelegatingDBIndex extends DelegatingDBSchemaElement implements InternalDBIndex
{
  DelegatingDBIndex(InternalDBIndex delegate)
  {
    super(delegate);
  }

  @Override
  public InternalDBIndex getDelegate()
  {
    return (InternalDBIndex)super.getDelegate();
  }

  @Override
  public void setDelegate(IDBSchemaElement delegate)
  {
    IDBIndexField[] wrapperIndexFields = getIndexFields();

    IDBIndex delegateIndex = (IDBIndex)delegate;
    super.setDelegate(delegateIndex);

    for (IDBIndexField wrapperIndexField : wrapperIndexFields)
    {
      IDBIndexField delegateIndexField = delegateIndex.getIndexField(wrapperIndexField.getName());
      ((DelegatingDBSchemaElement)wrapperIndexField).setDelegate(delegateIndexField);
    }
  }

  public IDBIndex getWrapper()
  {
    return this;
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

  public Type getType()
  {
    return getDelegate().getType();
  }

  public void setType(Type type)
  {
    getDelegate().setType(type);
  }

  public void removeIndexField(IDBIndexField indexFieldToRemove)
  {
    getDelegate().removeIndexField(unwrap(indexFieldToRemove));
  }

  public boolean isOptional()
  {
    return getDelegate().isOptional();
  }

  public void setOptional(boolean optional)
  {
    getDelegate().setOptional(optional);
  }

  @Deprecated
  public int getPosition()
  {
    return getDelegate().getPosition();
  }

  public IDBIndexField addIndexField(IDBField field)
  {
    return wrap(getDelegate().addIndexField(unwrap(field)));
  }

  public IDBIndexField addIndexField(String name) throws SchemaElementNotFoundException
  {
    return wrap(getDelegate().addIndexField(name));
  }

  public IDBIndexField getIndexFieldSafe(String name) throws SchemaElementNotFoundException
  {
    return wrap(getDelegate().getIndexFieldSafe(name));
  }

  public IDBIndexField getIndexField(String name)
  {
    return wrap(getDelegate().getIndexField(name));
  }

  public IDBIndexField getIndexField(int position)
  {
    return wrap(getDelegate().getIndexField(position));
  }

  public IDBField getFieldSafe(String name) throws SchemaElementNotFoundException
  {
    return wrap(getDelegate().getFieldSafe(name));
  }

  public IDBField getField(String name)
  {
    return wrap(getDelegate().getField(name));
  }

  public IDBField getField(int position)
  {
    return wrap(getDelegate().getField(position));
  }

  public int getFieldCount()
  {
    return getDelegate().getFieldCount();
  }

  public IDBIndexField[] getIndexFields()
  {
    return wrap(getDelegate().getIndexFields(), IDBIndexField.class);
  }

  public IDBField[] getFields()
  {
    return wrap(getDelegate().getFields(), IDBField.class);
  }
}

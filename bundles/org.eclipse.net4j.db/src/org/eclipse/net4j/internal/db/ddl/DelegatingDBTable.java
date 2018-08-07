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

import org.eclipse.net4j.db.DBType;
import org.eclipse.net4j.db.ddl.IDBField;
import org.eclipse.net4j.db.ddl.IDBIndex;
import org.eclipse.net4j.db.ddl.IDBIndex.Type;
import org.eclipse.net4j.db.ddl.IDBSchema;
import org.eclipse.net4j.db.ddl.IDBSchemaElement;
import org.eclipse.net4j.db.ddl.IDBTable;
import org.eclipse.net4j.db.ddl.SchemaElementNotFoundException;
import org.eclipse.net4j.spi.db.ddl.InternalDBTable;

/**
 * @author Eike Stepper
 */
public final class DelegatingDBTable extends DelegatingDBSchemaElement implements InternalDBTable
{
  DelegatingDBTable(InternalDBTable delegate)
  {
    super(delegate);
  }

  @Override
  public InternalDBTable getDelegate()
  {
    return (InternalDBTable)super.getDelegate();
  }

  @Override
  public void setDelegate(IDBSchemaElement delegate)
  {
    IDBField[] wrapperFields = getFields();
    IDBIndex[] wrapperIndices = getIndices();

    IDBTable delegateTable = (IDBTable)delegate;
    super.setDelegate(delegateTable);

    for (IDBField wrapperField : wrapperFields)
    {
      IDBField delegateField = delegateTable.getField(wrapperField.getName());
      ((DelegatingDBSchemaElement)wrapperField).setDelegate(delegateField);
    }

    for (IDBIndex wrapperIndex : wrapperIndices)
    {
      IDBIndex delegateIndex = delegateTable.getIndex(wrapperIndex.getName());
      ((DelegatingDBSchemaElement)wrapperIndex).setDelegate(delegateIndex);
    }
  }

  public IDBTable getWrapper()
  {
    return this;
  }

  @Override
  public IDBSchema getParent()
  {
    return wrap(getDelegate().getParent());
  }

  public IDBField addField(String name, DBType type)
  {
    return wrap(getDelegate().addField(name, type));
  }

  public IDBField addField(String name, DBType type, boolean notNull)
  {
    return wrap(getDelegate().addField(name, type, notNull));
  }

  public void removeField(IDBField fieldToRemove)
  {
    getDelegate().removeField(unwrap(fieldToRemove));
  }

  public IDBField addField(String name, DBType type, int precision)
  {
    return wrap(getDelegate().addField(name, type, precision));
  }

  public void removeIndex(IDBIndex indexToRemove)
  {
    getDelegate().removeIndex(unwrap(indexToRemove));
  }

  public IDBField addField(String name, DBType type, int precision, boolean notNull)
  {
    return wrap(getDelegate().addField(name, type, precision, notNull));
  }

  public IDBField addField(String name, DBType type, int precision, int scale)
  {
    return wrap(getDelegate().addField(name, type, precision, scale));
  }

  public IDBField addField(String name, DBType type, int precision, int scale, boolean notNull)
  {
    return wrap(getDelegate().addField(name, type, precision, scale, notNull));
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

  public IDBField[] getFields()
  {
    return wrap(getDelegate().getFields(), IDBField.class);
  }

  public IDBField[] getFields(String... fieldNames) throws SchemaElementNotFoundException
  {
    return wrap(getDelegate().getFields(fieldNames), IDBField.class);
  }

  public boolean hasIndexFor(IDBField... fields)
  {
    return getDelegate().hasIndexFor(unwrap(fields, IDBField.class));
  }

  public IDBIndex addIndex(String name, Type type, IDBField... fields)
  {
    return wrap(getDelegate().addIndex(name, type, unwrap(fields, IDBField.class)));
  }

  public IDBIndex addIndex(String name, Type type, String... fieldNames) throws SchemaElementNotFoundException
  {
    return wrap(getDelegate().addIndex(name, type, fieldNames));
  }

  public IDBIndex addIndexEmpty(String name, Type type)
  {
    return wrap(getDelegate().addIndexEmpty(name, type));
  }

  public IDBIndex addIndex(Type type, IDBField... fields)
  {
    return wrap(getDelegate().addIndex(type, unwrap(fields, IDBField.class)));
  }

  public IDBIndex addIndex(Type type, String... fieldNames) throws SchemaElementNotFoundException
  {
    return wrap(getDelegate().addIndex(type, fieldNames));
  }

  public IDBIndex addIndexEmpty(Type type)
  {
    return wrap(getDelegate().addIndexEmpty(type));
  }

  public IDBIndex getIndexSafe(String name) throws SchemaElementNotFoundException
  {
    return wrap(getDelegate().getIndexSafe(name));
  }

  public IDBIndex getIndex(String name)
  {
    return wrap(getDelegate().getIndex(name));
  }

  public IDBIndex getIndex(int position)
  {
    return wrap(getDelegate().getIndex(position));
  }

  public int getIndexCount()
  {
    return getDelegate().getIndexCount();
  }

  public IDBIndex[] getIndices()
  {
    return wrap(getDelegate().getIndices(), IDBIndex.class);
  }

  public IDBIndex getPrimaryKeyIndex()
  {
    return wrap(getDelegate().getPrimaryKeyIndex());
  }

  public String sqlInsert()
  {
    return getDelegate().sqlInsert();
  }
}

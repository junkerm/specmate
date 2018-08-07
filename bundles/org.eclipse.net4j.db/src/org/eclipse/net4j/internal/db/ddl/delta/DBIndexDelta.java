/*
 * Copyright (c) 2013, 2015, 2016 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.net4j.internal.db.ddl.delta;

import org.eclipse.net4j.db.ddl.IDBIndex;
import org.eclipse.net4j.db.ddl.IDBIndexField;
import org.eclipse.net4j.db.ddl.IDBSchema;
import org.eclipse.net4j.db.ddl.IDBTable;
import org.eclipse.net4j.db.ddl.delta.IDBDelta;
import org.eclipse.net4j.db.ddl.delta.IDBDeltaVisitor;
import org.eclipse.net4j.db.ddl.delta.IDBIndexDelta;
import org.eclipse.net4j.db.ddl.delta.IDBIndexFieldDelta;
import org.eclipse.net4j.db.ddl.delta.IDBPropertyDelta;
import org.eclipse.net4j.spi.db.ddl.InternalDBIndex;
import org.eclipse.net4j.util.ObjectUtil;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Eike Stepper
 */
public final class DBIndexDelta extends DBDeltaWithProperties implements IDBIndexDelta
{
  private static final long serialVersionUID = 1L;

  private Map<String, IDBIndexFieldDelta> indexFieldDeltas = new HashMap<String, IDBIndexFieldDelta>();

  public DBIndexDelta(DBDelta parent, String name, ChangeKind changeKind)
  {
    super(parent, name, changeKind);
  }

  public DBIndexDelta(DBDelta parent, IDBIndex index, IDBIndex oldIndex)
  {
    this(parent, getName(index, oldIndex), getChangeKind(index, oldIndex));

    IDBIndex.Type type = index == null ? null : index.getType();
    IDBIndex.Type oldType = oldIndex == null ? null : oldIndex.getType();
    if (!ObjectUtil.equals(type, oldType))
    {
      addPropertyDelta(new DBPropertyDelta<IDBIndex.Type>(this, TYPE_PROPERTY, IDBPropertyDelta.Type.STRING, type, oldType));
    }

    Boolean optional = index == null ? null : ((InternalDBIndex)index).isOptional();
    Boolean oldOptional = oldIndex == null ? null : ((InternalDBIndex)oldIndex).isOptional();
    if (!ObjectUtil.equals(optional, oldOptional))
    {
      addPropertyDelta(new DBPropertyDelta<Boolean>(this, OPTIONAL_PROPERTY, IDBPropertyDelta.Type.BOOLEAN, optional, oldOptional));
    }

    IDBIndexField[] indexFields = index == null ? InternalDBIndex.NO_INDEX_FIELDS : index.getIndexFields();
    IDBIndexField[] oldIndexFields = oldIndex == null ? InternalDBIndex.NO_INDEX_FIELDS : oldIndex.getIndexFields();
    compare(indexFields, oldIndexFields, new SchemaElementComparator<IDBIndexField>()
    {
      public void compare(IDBIndexField indexField, IDBIndexField oldIndexField)
      {
        DBIndexFieldDelta indexFieldDelta = new DBIndexFieldDelta(DBIndexDelta.this, indexField, oldIndexField);
        if (!indexFieldDelta.isEmpty())
        {
          addIndexFieldDelta(indexFieldDelta);
        }
      }
    });
  }

  /**
   * Constructor for deserialization.
   */
  protected DBIndexDelta()
  {
  }

  public DeltaType getDeltaType()
  {
    return DeltaType.INDEX;
  }

  @Override
  public DBTableDelta getParent()
  {
    return (DBTableDelta)super.getParent();
  }

  public int getIndexFieldDeltaCount()
  {
    return indexFieldDeltas.size();
  }

  public DBIndexFieldDelta getIndexFieldDelta(int position)
  {
    for (IDBIndexFieldDelta indexFieldDelta : indexFieldDeltas.values())
    {
      if (indexFieldDelta.getPosition() == position)
      {
        return (DBIndexFieldDelta)indexFieldDelta;
      }
    }

    return null;
  }

  public DBIndexFieldDelta getIndexFieldDelta(String name)
  {
    return (DBIndexFieldDelta)indexFieldDeltas.get(name);
  }

  public Map<String, IDBIndexFieldDelta> getIndexFieldDeltas()
  {
    return Collections.unmodifiableMap(indexFieldDeltas);
  }

  public DBIndexFieldDelta[] getIndexFieldDeltasSortedByPosition()
  {
    DBIndexFieldDelta[] result = indexFieldDeltas.values().toArray(new DBIndexFieldDelta[indexFieldDeltas.size()]);
    Arrays.sort(result);
    return result;
  }

  public IDBIndex getSchemaElement(IDBSchema schema)
  {
    IDBTable table = getParent().getSchemaElement(schema);
    if (table == null)
    {
      return null;
    }

    return table.getIndex(getName());
  }

  @Override
  public String toString()
  {
    return MessageFormat.format("DBIndexDelta[name={0}, kind={1}, propertyDeltas={2}, indexFieldDeltas={3}]", getName(), getChangeKind(),
        getPropertyDeltas().values(), indexFieldDeltas.values());
  }

  public void addIndexFieldDelta(DBIndexFieldDelta indexFieldDelta)
  {
    indexFieldDeltas.put(indexFieldDelta.getName(), indexFieldDelta);
    resetElements();
  }

  @Override
  protected void doAccept(IDBDeltaVisitor visitor)
  {
    visitor.visit(this);
  }

  @Override
  protected void collectElements(List<IDBDelta> elements)
  {
    elements.addAll(indexFieldDeltas.values());
    super.collectElements(elements);
  }
}

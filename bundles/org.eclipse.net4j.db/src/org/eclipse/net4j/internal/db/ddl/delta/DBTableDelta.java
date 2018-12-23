/*
 * Copyright (c) 2013, 2016 Eike Stepper (Loehne, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.net4j.internal.db.ddl.delta;

import org.eclipse.net4j.db.ddl.IDBField;
import org.eclipse.net4j.db.ddl.IDBIndex;
import org.eclipse.net4j.db.ddl.IDBSchema;
import org.eclipse.net4j.db.ddl.IDBTable;
import org.eclipse.net4j.db.ddl.delta.IDBDelta;
import org.eclipse.net4j.db.ddl.delta.IDBDeltaVisitor;
import org.eclipse.net4j.db.ddl.delta.IDBFieldDelta;
import org.eclipse.net4j.db.ddl.delta.IDBIndexDelta;
import org.eclipse.net4j.db.ddl.delta.IDBTableDelta;
import org.eclipse.net4j.spi.db.ddl.InternalDBTable;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Eike Stepper
 */
public final class DBTableDelta extends DBDelta implements IDBTableDelta
{
  private static final long serialVersionUID = 1L;

  private Map<String, IDBFieldDelta> fieldDeltas = new HashMap<String, IDBFieldDelta>();

  private Map<String, IDBIndexDelta> indexDeltas = new HashMap<String, IDBIndexDelta>();

  public DBTableDelta(DBDelta parent, String name, ChangeKind changeKind)
  {
    super(parent, name, changeKind);
  }

  public DBTableDelta(DBSchemaDelta parent, IDBTable table, IDBTable oldTable)
  {
    this(parent, getName(table, oldTable), getChangeKind(table, oldTable));

    IDBField[] fields = table == null ? InternalDBTable.NO_FIELDS : table.getFields();
    IDBField[] oldFields = oldTable == null ? InternalDBTable.NO_FIELDS : oldTable.getFields();
    compare(fields, oldFields, new SchemaElementComparator<IDBField>()
    {
      public void compare(IDBField field, IDBField oldField)
      {
        DBFieldDelta fieldDelta = new DBFieldDelta(DBTableDelta.this, field, oldField);
        if (!fieldDelta.isEmpty())
        {
          addFieldDelta(fieldDelta);
        }
      }
    });

    IDBIndex[] indices = table == null ? InternalDBTable.NO_INDICES : table.getIndices();
    IDBIndex[] oldIndices = oldTable == null ? InternalDBTable.NO_INDICES : oldTable.getIndices();
    compare(indices, oldIndices, new SchemaElementComparator<IDBIndex>()
    {
      public void compare(IDBIndex index, IDBIndex oldIndex)
      {
        DBIndexDelta indexDelta = new DBIndexDelta(DBTableDelta.this, index, oldIndex);
        if (!indexDelta.isEmpty())
        {
          addIndexDelta(indexDelta);
        }
      }
    });
  }

  /**
   * Constructor for deserialization.
   */
  protected DBTableDelta()
  {
  }

  public DeltaType getDeltaType()
  {
    return DeltaType.TABLE;
  }

  @Override
  public DBSchemaDelta getParent()
  {
    return (DBSchemaDelta)super.getParent();
  }

  public int getFieldDeltaCount()
  {
    return fieldDeltas.size();
  }

  public int getIndexDeltaCount()
  {
    return indexDeltas.size();
  }

  public DBFieldDelta getFieldDelta(int position)
  {
    for (IDBFieldDelta fieldDelta : fieldDeltas.values())
    {
      if (fieldDelta.getPosition() == position)
      {
        return (DBFieldDelta)fieldDelta;
      }
    }

    return null;
  }

  public DBFieldDelta getFieldDelta(String name)
  {
    return (DBFieldDelta)fieldDeltas.get(name);
  }

  public DBIndexDelta getIndexDelta(String name)
  {
    return (DBIndexDelta)indexDeltas.get(name);
  }

  public Map<String, IDBFieldDelta> getFieldDeltas()
  {
    return Collections.unmodifiableMap(fieldDeltas);
  }

  public Map<String, IDBIndexDelta> getIndexDeltas()
  {
    return Collections.unmodifiableMap(indexDeltas);
  }

  public DBFieldDelta[] getFieldDeltasSortedByPosition()
  {
    DBFieldDelta[] result = fieldDeltas.values().toArray(new DBFieldDelta[fieldDeltas.size()]);
    Arrays.sort(result);
    return result;
  }

  public DBIndexDelta[] getIndexDeltasSortedByName()
  {
    DBIndexDelta[] result = indexDeltas.values().toArray(new DBIndexDelta[indexDeltas.size()]);
    Arrays.sort(result);
    return result;
  }

  public IDBTable getSchemaElement(IDBSchema schema)
  {
    return schema.getTable(getName());
  }

  @Override
  public String toString()
  {
    return MessageFormat.format("DBTableDelta[name={0}, kind={1}, fieldDeltas={2}, indexDeltas={3}]", getName(), getChangeKind(), fieldDeltas.values(),
        indexDeltas.values());
  }

  public void addFieldDelta(IDBFieldDelta fieldDelta)
  {
    fieldDeltas.put(fieldDelta.getName(), fieldDelta);
    resetElements();
  }

  public void addIndexDelta(IDBIndexDelta indexDelta)
  {
    indexDeltas.put(indexDelta.getName(), indexDelta);
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
    elements.addAll(fieldDeltas.values());
    elements.addAll(indexDeltas.values());
  }
}

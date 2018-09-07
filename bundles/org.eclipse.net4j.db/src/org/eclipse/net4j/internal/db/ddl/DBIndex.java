/*
 * Copyright (c) 2008, 2009, 2011-2013, 2015, 2016 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.net4j.internal.db.ddl;

import org.eclipse.net4j.db.DBException;
import org.eclipse.net4j.db.ddl.IDBField;
import org.eclipse.net4j.db.ddl.IDBIndex;
import org.eclipse.net4j.db.ddl.IDBIndexField;
import org.eclipse.net4j.db.ddl.IDBSchema;
import org.eclipse.net4j.db.ddl.IDBSchemaElement;
import org.eclipse.net4j.db.ddl.IDBSchemaVisitor;
import org.eclipse.net4j.db.ddl.IDBTable;
import org.eclipse.net4j.db.ddl.SchemaElementNotFoundException;
import org.eclipse.net4j.spi.db.ddl.InternalDBField;
import org.eclipse.net4j.spi.db.ddl.InternalDBIndex;
import org.eclipse.net4j.spi.db.ddl.InternalDBSchema;
import org.eclipse.net4j.spi.db.ddl.InternalDBTable;
import org.eclipse.net4j.util.om.OMPlatform;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author Eike Stepper
 */
public class DBIndex extends DBSchemaElement implements InternalDBIndex
{
  public static final ThreadLocal<Boolean> FIX_NULLABLE_INDEX_COLUMNS = new InheritableThreadLocal<Boolean>();

  public static final ThreadLocal<Set<IDBField>> NULLABLE_INDEX_FIELDS = new InheritableThreadLocal<Set<IDBField>>();

  private static final boolean DISABLE_NULLABLE_CHECK = OMPlatform.INSTANCE.isProperty("org.eclipse.net4j.db.DisableNullableCheck", true);

  private static final long serialVersionUID = 1L;

  private IDBTable table;

  private Type type;

  private List<IDBIndexField> indexFields = new ArrayList<IDBIndexField>();

  private boolean optional;

  public DBIndex(IDBTable table, String name, Type type, IDBField[] fields)
  {
    super(name);
    this.table = table;
    this.type = type;

    for (int i = 0; i < fields.length; i++)
    {
      IDBField field = fields[i];
      addIndexField(field);
    }
  }

  /**
   * Constructor for deserialization.
   */
  protected DBIndex()
  {
  }

  @Override
  public IDBIndex getWrapper()
  {
    return (IDBIndex)super.getWrapper();
  }

  public SchemaElementType getSchemaElementType()
  {
    return SchemaElementType.INDEX;
  }

  public IDBSchema getSchema()
  {
    return table.getSchema();
  }

  public IDBTable getTable()
  {
    return table;
  }

  public IDBTable getParent()
  {
    return getTable();
  }

  public Type getType()
  {
    return type;
  }

  public void setType(Type type)
  {
    assertUnlocked();
    this.type = type;
  }

  public boolean isOptional()
  {
    return optional;
  }

  public void setOptional(boolean optional)
  {
    this.optional = optional;
  }

  @Deprecated
  public int getPosition()
  {
    IDBIndex[] indices = table.getIndices();
    return Arrays.asList(indices).indexOf(this);
  }

  public IDBIndexField addIndexField(IDBField field)
  {
    assertUnlocked();

    if (type != Type.NON_UNIQUE && !field.isNotNull())
    {
      if (!DISABLE_NULLABLE_CHECK && FIX_NULLABLE_INDEX_COLUMNS.get() != Boolean.TRUE)
      {
        Exception constructionStackTrace = ((InternalDBField)field).getConstructionStackTrace();
        throw new DBException("Index field is nullable: " + field //$NON-NLS-1$
            + " (to disable this check run with '-Dorg.eclipse.net4j.db.DisableNullableCheck=true')", constructionStackTrace);
      }

      Set<IDBField> nullableIndexFields = NULLABLE_INDEX_FIELDS.get();
      if (nullableIndexFields == null)
      {
        nullableIndexFields = new HashSet<IDBField>();
        NULLABLE_INDEX_FIELDS.set(nullableIndexFields);
      }

      nullableIndexFields.add(field);
    }

    if (field.getTable() != table)
    {
      throw new DBException("Index field is from different table: " + field); //$NON-NLS-1$
    }

    String name = field.getName();
    if (getIndexField(name) != null)
    {
      throw new DBException("Index field exists: " + name); //$NON-NLS-1$
    }

    int position = indexFields.size();
    IDBIndexField indexField = new DBIndexField(this, field, position);
    indexFields.add(indexField);
    resetElements();
    return indexField;
  }

  public IDBIndexField addIndexField(String name) throws SchemaElementNotFoundException
  {
    IDBField field = table.getFieldSafe(name);
    return addIndexField(field);
  }

  public void removeIndexField(IDBIndexField indexFieldToRemove)
  {
    assertUnlocked();

    boolean found = false;
    for (Iterator<IDBIndexField> it = indexFields.iterator(); it.hasNext();)
    {
      IDBIndexField indexField = it.next();
      if (found)
      {
        ((InternalDBField)indexField).setPosition(indexField.getPosition() - 1);
      }
      else if (indexField == indexFieldToRemove)
      {
        it.remove();
        found = true;
      }
    }

    resetElements();
  }

  public IDBIndexField getIndexFieldSafe(String name) throws SchemaElementNotFoundException
  {
    IDBIndexField indexField = getIndexField(name);
    if (indexField == null)
    {
      throw new SchemaElementNotFoundException(this, SchemaElementType.INDEX_FIELD, name);
    }

    return indexField;
  }

  public IDBIndexField getIndexField(String name)
  {
    return findElement(getIndexFields(), name);
  }

  public IDBIndexField getIndexField(int position)
  {
    return indexFields.get(position);
  }

  public IDBField getFieldSafe(String name) throws SchemaElementNotFoundException
  {
    IDBIndexField indexField = getIndexFieldSafe(name);
    return indexField.getField();
  }

  public IDBField getField(String name)
  {
    name = name(name);
    for (IDBIndexField indexField : indexFields)
    {
      if (indexField.getName() == name)
      {
        return indexField.getField();
      }
    }

    return null;
  }

  public IDBField getField(int position)
  {
    return indexFields.get(position).getField();
  }

  public int getFieldCount()
  {
    return indexFields.size();
  }

  public IDBIndexField[] getIndexFields()
  {
    return indexFields.toArray(new IDBIndexField[indexFields.size()]);
  }

  public IDBField[] getFields()
  {
    IDBField[] fields = new IDBField[indexFields.size()];
    for (int i = 0; i < fields.length; i++)
    {
      fields[i] = getField(i);
    }

    return fields;
  }

  public String getFullName()
  {
    return getName();
  }

  public void remove()
  {
    ((InternalDBTable)table).removeIndex(this);
  }

  @Override
  protected void collectElements(List<IDBSchemaElement> elements)
  {
    elements.addAll(indexFields);
  }

  @Override
  protected void doAccept(IDBSchemaVisitor visitor)
  {
    visitor.visit(this);
  }

  @Override
  protected void dumpAdditionalProperties(Writer writer) throws IOException
  {
    writer.append(", type=");
    writer.append(String.valueOf(getType()));
  }

  private void assertUnlocked()
  {
    ((InternalDBSchema)table.getSchema()).assertUnlocked();
  }
}

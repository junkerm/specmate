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

import org.eclipse.net4j.db.DBType;
import org.eclipse.net4j.db.DBUtil;
import org.eclipse.net4j.db.ddl.IDBField;
import org.eclipse.net4j.db.ddl.IDBIndex;
import org.eclipse.net4j.db.ddl.IDBIndexField;
import org.eclipse.net4j.db.ddl.IDBSchema;
import org.eclipse.net4j.db.ddl.IDBTable;
import org.eclipse.net4j.db.ddl.delta.IDBDelta;
import org.eclipse.net4j.db.ddl.delta.IDBDeltaVisitor;
import org.eclipse.net4j.db.ddl.delta.IDBFieldDelta;
import org.eclipse.net4j.db.ddl.delta.IDBIndexDelta;
import org.eclipse.net4j.db.ddl.delta.IDBIndexFieldDelta;
import org.eclipse.net4j.db.ddl.delta.IDBSchemaDelta;
import org.eclipse.net4j.db.ddl.delta.IDBTableDelta;
import org.eclipse.net4j.spi.db.ddl.InternalDBSchema;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Eike Stepper
 */
public final class DBSchemaDelta extends DBDelta implements IDBSchemaDelta
{
  private static final long serialVersionUID = 1L;

  private Map<String, IDBTableDelta> tableDeltas = new HashMap<String, IDBTableDelta>();

  public DBSchemaDelta(String name, ChangeKind changeKind)
  {
    super(null, name, changeKind);
  }

  public DBSchemaDelta(IDBSchema schema, IDBSchema oldSchema)
  {
    this(schema.getName(), oldSchema == null ? ChangeKind.ADD : ChangeKind.CHANGE);

    IDBTable[] tables = schema.getTables();
    IDBTable[] oldTables = oldSchema == null ? InternalDBSchema.NO_TABLES : oldSchema.getTables();
    compare(tables, oldTables, new SchemaElementComparator<IDBTable>()
    {
      public void compare(IDBTable table, IDBTable oldTable)
      {
        DBTableDelta tableDelta = new DBTableDelta(DBSchemaDelta.this, table, oldTable);
        if (!tableDelta.isEmpty())
        {
          addTableDelta(tableDelta);
        }
      }
    });
  }

  /**
   * Constructor for deserialization.
   */
  protected DBSchemaDelta()
  {
  }

  public DeltaType getDeltaType()
  {
    return DeltaType.SCHEMA;
  }

  public int getTableDeltaCount()
  {
    return tableDeltas.size();
  }

  public DBTableDelta getTableDelta(String name)
  {
    return (DBTableDelta)tableDeltas.get(name);
  }

  public Map<String, IDBTableDelta> getTableDeltas()
  {
    return Collections.unmodifiableMap(tableDeltas);
  }

  public IDBTableDelta[] getTableDeltasSortedByName()
  {
    IDBTableDelta[] result = tableDeltas.values().toArray(new IDBTableDelta[tableDeltas.size()]);
    Arrays.sort(result);
    return result;
  }

  public void addTableDelta(IDBTableDelta tableDelta)
  {
    tableDeltas.put(tableDelta.getName(), tableDelta);
    resetElements();
  }

  public IDBSchema getSchemaElement(IDBSchema schema)
  {
    return schema;
  }

  public void applyTo(IDBSchema schema)
  {
    IDBDeltaVisitor visitor = new DBSchemaDelta.Applier(schema);
    accept(visitor);
  }

  @Override
  public String toString()
  {
    return MessageFormat.format("DBSchemaDelta[name={0}, kind={1}, tableDeltas={2}]", getName(), getChangeKind(), tableDeltas.values());
  }

  @Override
  protected void doAccept(IDBDeltaVisitor visitor)
  {
    visitor.visit(this);
  }

  @Override
  protected void collectElements(List<IDBDelta> elements)
  {
    elements.addAll(tableDeltas.values());
  }

  /**
   * @author Eike Stepper
   */
  public static class Applier extends IDBDeltaVisitor.Default
  {
    private final IDBSchema schema;

    public Applier(IDBSchema schema)
    {
      this.schema = schema;
    }

    public final IDBSchema getSchema()
    {
      return schema;
    }

    @Override
    public void added(IDBTableDelta delta)
    {
      String name = delta.getName();
      schema.addTable(name);
    }

    @Override
    public void removed(IDBTableDelta delta)
    {
      IDBTable table = delta.getSchemaElement(schema);
      table.remove();
    }

    @Override
    public void changed(IDBTableDelta delta)
    {
    }

    @Override
    public void added(IDBFieldDelta delta)
    {
      String name = delta.getName();
      DBType type = delta.getPropertyValue(IDBFieldDelta.TYPE_PROPERTY);
      int precision = delta.getPropertyValue(IDBFieldDelta.PRECISION_PROPERTY);
      int scale = delta.getPropertyValue(IDBFieldDelta.SCALE_PROPERTY);
      boolean notNull = delta.getPropertyValue(IDBFieldDelta.NOT_NULL_PROPERTY);

      IDBTable table = delta.getParent().getSchemaElement(schema);
      table.addField(name, type, precision, scale, notNull);
    }

    @Override
    public void removed(IDBFieldDelta delta)
    {
      IDBField field = delta.getSchemaElement(schema);
      field.remove();
    }

    @Override
    public void changed(IDBFieldDelta delta)
    {
    }

    @Override
    public void added(IDBIndexDelta delta)
    {
      String name = delta.getName();
      IDBIndex.Type type = delta.getPropertyValue(IDBIndexDelta.TYPE_PROPERTY);
      Boolean optional = delta.getPropertyValue(IDBIndexDelta.OPTIONAL_PROPERTY);

      IDBTable table = delta.getParent().getSchemaElement(schema);
      IDBIndex index = table.addIndexEmpty(name, type);
      DBUtil.setOptional(index, optional == Boolean.TRUE);
    }

    @Override
    public void removed(IDBIndexDelta delta)
    {
      IDBIndex index = delta.getSchemaElement(schema);
      index.remove();
    }

    @Override
    public void changed(IDBIndexDelta delta)
    {
    }

    @Override
    public void added(IDBIndexFieldDelta delta)
    {
      IDBIndexDelta parent = delta.getParent();
      IDBTable table = parent.getParent().getSchemaElement(schema);

      String name = delta.getName();
      IDBField field = table.getField(name);

      IDBIndex index = parent.getSchemaElement(schema);
      index.addIndexField(field);
    }

    @Override
    public void removed(IDBIndexFieldDelta delta)
    {
      IDBIndexField indexField = delta.getSchemaElement(schema);
      indexField.remove();
    }

    @Override
    public void changed(IDBIndexFieldDelta delta)
    {
    }
  }
}

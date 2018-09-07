/*
 * Copyright (c) 2008-2015 Eike Stepper (Berlin, Germany) and others.
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
import org.eclipse.net4j.db.ddl.IDBSchema;
import org.eclipse.net4j.db.ddl.IDBSchemaElement;
import org.eclipse.net4j.db.ddl.IDBSchemaVisitor;
import org.eclipse.net4j.db.ddl.IDBTable;
import org.eclipse.net4j.spi.db.ddl.InternalDBField;
import org.eclipse.net4j.spi.db.ddl.InternalDBSchema;
import org.eclipse.net4j.spi.db.ddl.InternalDBTable;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

/**
 * @author Eike Stepper
 */
public class DBField extends DBSchemaElement implements InternalDBField
{
  public static final int DEFAULT_BOOLEAN_PRECISION = 1;

  public static final int DEFAULT_INTEGER_PRECISION = 10;

  public static final int DEFAULT_DECIMAL_PRECISION = 5;

  public static final int DEFAULT_PRECISION = 0;

  public static final int DEFAULT_SCALE = 0;

  public static final int DEFAULT_CHAR_LENGTH = 1;

  public static final int DEFAULT_VARCHAR_LENGTH = 255;

  private static final ThreadLocal<Boolean> TRACK_CONSTRUCTION = new InheritableThreadLocal<Boolean>()
  {
    @Override
    protected Boolean initialValue()
    {
      return false;
    }
  };

  private static final long serialVersionUID = 1L;

  private IDBTable table;

  private DBType type;

  private int precision;

  private int scale;

  private boolean notNull;

  private int position;

  /**
   * Tracks the construction stack trace to provide better debug infos in IDBTable.addIndex().
   */
  private transient Exception constructionStackTrace;

  public DBField(IDBTable table, String name, DBType type, int precision, int scale, boolean notNull, int position)
  {
    super(name);
    this.table = table;
    this.type = type;
    this.precision = precision;
    this.scale = scale;
    this.notNull = notNull;
    this.position = position;

    if (TRACK_CONSTRUCTION.get() == Boolean.TRUE)
    {
      try
      {
        throw new Exception("The field " + this + " has been constructed here:");
      }
      catch (Exception ex)
      {
        constructionStackTrace = ex;
      }
    }
  }

  /**
   * Constructor for deserialization.
   */
  protected DBField()
  {
  }

  @Override
  public IDBField getWrapper()
  {
    return (IDBField)super.getWrapper();
  }

  public SchemaElementType getSchemaElementType()
  {
    return SchemaElementType.FIELD;
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

  public DBType getType()
  {
    return type;
  }

  public void setType(DBType type)
  {
    assertUnlocked();
    this.type = type;
  }

  public int getPrecision()
  {
    if (precision == DEFAULT)
    {
      switch (type)
      {
      case BOOLEAN:
        return DEFAULT_BOOLEAN_PRECISION;

      case INTEGER:
        return DEFAULT_INTEGER_PRECISION;

      case CHAR:
        return DEFAULT_CHAR_LENGTH;

      case VARCHAR:
      case VARBINARY:
        return DEFAULT_VARCHAR_LENGTH;

      case DECIMAL:
      case NUMERIC:
        return DEFAULT_DECIMAL_PRECISION;

      default:
        return DEFAULT_PRECISION;
      }
    }

    return precision;
  }

  public void setPrecision(int precision)
  {
    assertUnlocked();
    this.precision = precision;
  }

  public int getScale()
  {
    if (scale == DEFAULT)
    {
      return DEFAULT_SCALE;
    }

    return scale;
  }

  public void setScale(int scale)
  {
    assertUnlocked();
    this.scale = scale;
  }

  public boolean isNotNull()
  {
    return notNull;
  }

  public void setNotNull(boolean notNull)
  {
    if (DBIndex.FIX_NULLABLE_INDEX_COLUMNS.get() != Boolean.TRUE)
    {
      assertUnlocked();
    }

    this.notNull = notNull;
  }

  public int getPosition()
  {
    return position;
  }

  public void setPosition(int position)
  {
    assertUnlocked();
    this.position = position;
  }

  public String getFullName()
  {
    return table.getName() + "." + getName(); //$NON-NLS-1$
  }

  public void remove()
  {
    ((InternalDBTable)table).removeField(this);
  }

  public String formatPrecision()
  {
    int precision = getPrecision();
    if (precision > 0)
    {
      return "(" + precision + ")"; //$NON-NLS-1$ //$NON-NLS-2$
    }

    return ""; //$NON-NLS-1$
  }

  public String formatPrecisionAndScale()
  {
    if (scale == DEFAULT)
    {
      return "(" + getPrecision() + ")"; //$NON-NLS-1$ //$NON-NLS-2$
    }

    return "(" + getPrecision() + ", " + getScale() + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
  }

  public Exception getConstructionStackTrace()
  {
    return constructionStackTrace;
  }

  @Override
  protected void collectElements(List<IDBSchemaElement> elements)
  {
    // Do nothing
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
    writer.append(getType().toString());
    writer.append(", precision=");
    writer.append(String.valueOf(getPrecision()));
    writer.append(", scale=");
    writer.append(String.valueOf(getScale()));
    writer.append(", notNull=");
    writer.append(String.valueOf(isNotNull()));
  }

  private void assertUnlocked()
  {
    ((InternalDBSchema)table.getSchema()).assertUnlocked();
  }

  public static void trackConstruction(boolean on)
  {
    if (on)
    {
      TRACK_CONSTRUCTION.set(true);
    }
    else
    {
      TRACK_CONSTRUCTION.remove();
    }
  }

  public static boolean isTrackConstruction()
  {
    return TRACK_CONSTRUCTION.get();
  }
}

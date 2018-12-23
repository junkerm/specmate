/*
 * Copyright (c) 2013, 2015, 2016 Eike Stepper (Loehne, Germany) and others.
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
import org.eclipse.net4j.db.ddl.delta.IDBDeltaVisitor;
import org.eclipse.net4j.db.ddl.delta.IDBIndexFieldDelta;
import org.eclipse.net4j.db.ddl.delta.IDBPropertyDelta;
import org.eclipse.net4j.util.ObjectUtil;

import java.text.MessageFormat;

/**
 * @author Eike Stepper
 */
public final class DBIndexFieldDelta extends DBDeltaWithPosition implements IDBIndexFieldDelta
{
  private static final long serialVersionUID = 1L;

  public DBIndexFieldDelta(DBDelta parent, String name, ChangeKind changeKind)
  {
    super(parent, name, changeKind);
  }

  public DBIndexFieldDelta(DBIndexDelta parent, IDBIndexField indexField, IDBIndexField oldIndexField)
  {
    this(parent, getName(indexField, oldIndexField), getChangeKind(indexField, oldIndexField));

    Integer position = indexField == null ? null : indexField.getPosition();
    Integer oldPosition = oldIndexField == null ? null : oldIndexField.getPosition();
    if (!ObjectUtil.equals(position, oldPosition))
    {
      addPropertyDelta(new DBPropertyDelta<Integer>(this, POSITION_PROPERTY, IDBPropertyDelta.Type.INTEGER, position, oldPosition));
    }
  }

  /**
   * Constructor for deserialization.
   */
  protected DBIndexFieldDelta()
  {
  }

  public DeltaType getDeltaType()
  {
    return DeltaType.INDEX_FIELD;
  }

  @Override
  public DBIndexDelta getParent()
  {
    return (DBIndexDelta)super.getParent();
  }

  public IDBIndexField getSchemaElement(IDBSchema schema)
  {
    IDBIndex index = getParent().getSchemaElement(schema);
    if (index == null)
    {
      return null;
    }

    return index.getIndexField(getName());
  }

  @Override
  public String toString()
  {
    return MessageFormat.format("DBIndexFieldDelta[name={0}, kind={1}, propertyDeltas={2}]", getName(), getChangeKind(), getPropertyDeltas().values());
  }

  @Override
  protected void doAccept(IDBDeltaVisitor visitor)
  {
    visitor.visit(this);
  }
}

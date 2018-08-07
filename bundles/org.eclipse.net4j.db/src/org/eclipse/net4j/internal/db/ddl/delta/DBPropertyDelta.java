/*
 * Copyright (c) 2013, 2016 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.net4j.internal.db.ddl.delta;

import org.eclipse.net4j.db.ddl.IDBSchema;
import org.eclipse.net4j.db.ddl.IDBSchemaElement;
import org.eclipse.net4j.db.ddl.delta.IDBDelta;
import org.eclipse.net4j.db.ddl.delta.IDBDeltaVisitor;
import org.eclipse.net4j.db.ddl.delta.IDBPropertyDelta;

import java.io.IOException;
import java.io.Writer;
import java.text.MessageFormat;
import java.util.List;

/**
 * @author Eike Stepper
 */
public final class DBPropertyDelta<T> extends DBDelta implements IDBPropertyDelta<T>
{
  private static final long serialVersionUID = 1L;

  private Type type;

  private T value;

  private T oldValue;

  public DBPropertyDelta(DBDelta parent, String name, Type type, T value, T oldValue)
  {
    super(parent, name, DBDelta.getChangeKind(value, oldValue));
    this.type = type;
    this.value = value;
    this.oldValue = oldValue;
  }

  /**
   * Constructor for deserialization.
   */
  protected DBPropertyDelta()
  {
  }

  public DeltaType getDeltaType()
  {
    return DeltaType.PROPERTY;
  }

  public IDBSchemaElement getSchemaElement(IDBSchema schema)
  {
    return null;
  }

  public Type getType()
  {
    return type;
  }

  public T getValue()
  {
    return value;
  }

  public T getOldValue()
  {
    return oldValue;
  }

  @Override
  public String toString()
  {
    return MessageFormat.format("DBPropertyDelta[name={0}, kind={1}, type={2}, value={3}, oldValue={4}]", getName(), getChangeKind(), getType(), getValue(),
        getOldValue());
  }

  @Override
  protected void doAccept(IDBDeltaVisitor visitor)
  {
    visitor.visit(this);
  }

  @Override
  protected void collectElements(List<IDBDelta> elements)
  {
    // Do nothing
  }

  @Override
  protected void dumpAdditionalProperties(Writer writer) throws IOException
  {
    writer.append(", type=");
    writer.append(getType().toString());
    writer.append(", value=");
    writer.append(toString(getValue()));
    writer.append(", oldValue=");
    writer.append(toString(getOldValue()));
  }

  private static CharSequence toString(Object object)
  {
    if (object == null)
    {
      return "null";
    }

    return object.toString();
  }
}

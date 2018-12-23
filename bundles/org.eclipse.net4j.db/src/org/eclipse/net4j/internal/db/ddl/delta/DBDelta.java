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

import org.eclipse.net4j.db.ddl.IDBSchemaElement;
import org.eclipse.net4j.db.ddl.delta.IDBDelta;
import org.eclipse.net4j.db.ddl.delta.IDBDeltaVisitor;
import org.eclipse.net4j.db.ddl.delta.IDBDeltaVisitor.StopRecursion;
import org.eclipse.net4j.internal.db.ddl.DBNamedElement;
import org.eclipse.net4j.internal.db.ddl.DBSchemaElement;
import org.eclipse.net4j.util.StringUtil;
import org.eclipse.net4j.util.collection.PositionProvider;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Eike Stepper
 */
public abstract class DBDelta extends DBNamedElement implements IDBDelta
{
  private static final IDBDelta[] NO_ELEMENTS = {};

  private static final long serialVersionUID = 1L;

  private DBDelta parent;

  private ChangeKind changeKind;

  private transient IDBDelta[] elements;

  public DBDelta(DBDelta parent, String name, ChangeKind changeKind)
  {
    super(name);
    this.parent = parent;
    this.changeKind = changeKind;
  }

  /**
   * Constructor for deserialization.
   */
  protected DBDelta()
  {
  }

  public DBDelta getParent()
  {
    return parent;
  }

  public final ChangeKind getChangeKind()
  {
    return changeKind;
  }

  public final int compareTo(IDBDelta delta2)
  {
    int result = getDeltaType().compareTo(delta2.getDeltaType());
    if (result == 0)
    {
      if (this instanceof PositionProvider && delta2 instanceof PositionProvider)
      {
        PositionProvider withPosition1 = (PositionProvider)this;
        PositionProvider withPosition2 = (PositionProvider)delta2;
        return withPosition1.getPosition() - withPosition2.getPosition();
      }

      result = getName().compareTo(delta2.getName());
    }

    return result;
  }

  public final void accept(IDBDeltaVisitor visitor)
  {
    try
    {
      doAccept(visitor);
    }
    catch (StopRecursion ex)
    {
      return;
    }

    for (IDBDelta delta : getElements())
    {
      delta.accept(visitor);
    }
  }

  protected abstract void doAccept(IDBDeltaVisitor visitor);

  public final boolean isEmpty()
  {
    return getElements().length == 0;
  }

  public final IDBDelta[] getElements()
  {
    if (elements == null)
    {
      List<IDBDelta> result = new ArrayList<IDBDelta>();
      collectElements(result);

      if (result.isEmpty())
      {
        elements = NO_ELEMENTS;
      }
      else
      {
        elements = result.toArray(new IDBDelta[result.size()]);
        Arrays.sort(elements);
      }
    }

    return elements;
  }

  protected final void resetElements()
  {
    elements = null;
  }

  protected abstract void collectElements(List<IDBDelta> elements);

  public void dump(Writer writer) throws IOException
  {
    int level = getLevel();
    for (int i = 0; i < level; i++)
    {
      writer.append("  ");
    }

    writer.append(getChangeKind().toString());
    writer.append(" ");
    writer.append(getDeltaType().toString());
    writer.append(" ");
    writer.append(getName());
    dumpAdditionalProperties(writer);
    writer.append(StringUtil.NL);

    for (IDBDelta delta : getElements())
    {
      ((DBDelta)delta).dump(writer);
    }
  }

  protected void dumpAdditionalProperties(Writer writer) throws IOException
  {
  }

  private int getLevel()
  {
    if (parent == null)
    {
      return 0;
    }

    return parent.getLevel() + 1;
  }

  public static String getName(IDBSchemaElement element, IDBSchemaElement oldElement)
  {
    return oldElement == null ? element.getName() : oldElement.getName();
  }

  public static ChangeKind getChangeKind(Object object, Object oldObject)
  {
    return object == null ? ChangeKind.REMOVE : oldObject == null ? ChangeKind.ADD : ChangeKind.CHANGE;
  }

  protected static <T extends IDBSchemaElement> void compare(T[] elements, T[] oldElements, SchemaElementComparator<T> comparator)
  {
    for (int i = 0; i < elements.length; i++)
    {
      T element = elements[i];
      String name = element.getName();

      T oldElement = DBSchemaElement.findElement(oldElements, name);
      comparator.compare(element, oldElement);
    }

    for (int i = 0; i < oldElements.length; i++)
    {
      T oldElement = oldElements[i];
      String name = oldElement.getName();

      if (DBSchemaElement.findElement(elements, name) == null)
      {
        comparator.compare(null, oldElement);
      }
    }
  }

  /**
   * @author Eike Stepper
   */
  public interface SchemaElementComparator<T extends IDBSchemaElement>
  {
    public void compare(T element, T oldElement);
  }
}

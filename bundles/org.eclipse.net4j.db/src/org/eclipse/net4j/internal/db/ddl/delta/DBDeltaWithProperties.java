/*
 * Copyright (c) 2013 Eike Stepper (Loehne, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.net4j.internal.db.ddl.delta;

import org.eclipse.net4j.db.ddl.delta.IDBDelta;
import org.eclipse.net4j.db.ddl.delta.IDBDeltaWithPosition;
import org.eclipse.net4j.db.ddl.delta.IDBDeltaWithProperties;
import org.eclipse.net4j.db.ddl.delta.IDBPropertyDelta;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Eike Stepper
 */
public abstract class DBDeltaWithProperties extends DBDelta implements IDBDeltaWithProperties
{
  private static final long serialVersionUID = 1L;

  private Map<String, IDBPropertyDelta<?>> propertyDeltas = new HashMap<String, IDBPropertyDelta<?>>();

  public DBDeltaWithProperties(DBDelta parent, String name, ChangeKind changeKind)
  {
    super(parent, name, changeKind);
  }

  /**
   * Constructor for deserialization.
   */
  protected DBDeltaWithProperties()
  {
  }

  public <T> DBPropertyDelta<T> getPropertyDelta(String name)
  {
    name = name(name);

    @SuppressWarnings("unchecked")
    DBPropertyDelta<T> propertyDelta = (DBPropertyDelta<T>)propertyDeltas.get(name);
    return propertyDelta;
  }

  public <T> T getPropertyValue(String name)
  {
    return getPropertyValue(name, false);
  }

  public <T> T getPropertyValue(String name, boolean old)
  {
    IDBPropertyDelta<T> propertyDelta = getPropertyDelta(name);
    if (propertyDelta == null)
    {
      return null;
    }

    if (old)
    {
      return propertyDelta.getOldValue();
    }

    return propertyDelta.getValue();
  }

  public final Map<String, IDBPropertyDelta<?>> getPropertyDeltas()
  {
    return Collections.unmodifiableMap(propertyDeltas);
  }

  public IDBPropertyDelta<?>[] getPropertyDeltasSortedByName()
  {
    DBPropertyDelta<?>[] result = propertyDeltas.values().toArray(new DBPropertyDelta[propertyDeltas.size()]);
    Arrays.sort(result);
    return result;
  }

  public final void addPropertyDelta(IDBPropertyDelta<?> propertyDelta)
  {
    String name = propertyDelta.getName();
    propertyDeltas.put(name, propertyDelta);
    resetElements();

    if (IDBDeltaWithPosition.POSITION_PROPERTY.equals(name))
    {
      DBDelta parent = getParent();
      if (parent != null)
      {
        parent.resetElements();
      }
    }
  }

  @Override
  protected void collectElements(List<IDBDelta> elements)
  {
    elements.addAll(propertyDeltas.values());
  }
}

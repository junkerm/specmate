/*
 * Copyright (c) 2007, 2008, 2011, 2012, 2015 Eike Stepper (Loehne, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.net4j.internal.db;

import org.eclipse.net4j.db.IDBAdapter;
import org.eclipse.net4j.util.registry.HashMapRegistry;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Eike Stepper
 */
public class DBAdapterRegistry extends HashMapRegistry<String, IDBAdapter>
{
  public static final DBAdapterRegistry INSTANCE = new DBAdapterRegistry();

  private Map<String, DBAdapterDescriptor> descriptors = new HashMap<String, DBAdapterDescriptor>();

  public DBAdapterRegistry()
  {
  }

  public DBAdapterRegistry(int initialCapacity)
  {
    super(initialCapacity);
  }

  public DBAdapterRegistry(int initialCapacity, float loadFactor)
  {
    super(initialCapacity, loadFactor);
  }

  public DBAdapterRegistry(Map<? extends String, ? extends IDBAdapter> m)
  {
    super(m);
  }

  @Override
  public IDBAdapter get(Object key)
  {
    IDBAdapter adapter = super.get(key);
    if (adapter == null)
    {
      if (key instanceof String)
      {
        DBAdapterDescriptor descriptor = descriptors.get(key);
        if (descriptor != null)
        {
          adapter = descriptor.createDBAdapter();
          if (adapter != null)
          {
            put((String)key, adapter);
          }
        }
      }
    }

    return adapter;
  }

  public DBAdapterDescriptor addDescriptor(DBAdapterDescriptor descriptor)
  {
    return descriptors.put(descriptor.getName(), descriptor);
  }

  public DBAdapterDescriptor removeDescriptor(String name)
  {
    return descriptors.remove(name);
  }
}

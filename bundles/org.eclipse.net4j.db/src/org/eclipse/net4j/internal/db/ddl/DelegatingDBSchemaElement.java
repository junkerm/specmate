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
package org.eclipse.net4j.internal.db.ddl;

import org.eclipse.net4j.db.ddl.IDBSchema;
import org.eclipse.net4j.db.ddl.IDBSchemaElement;
import org.eclipse.net4j.db.ddl.IDBSchemaVisitor;
import org.eclipse.net4j.spi.db.ddl.InternalDBField;
import org.eclipse.net4j.spi.db.ddl.InternalDBIndex;
import org.eclipse.net4j.spi.db.ddl.InternalDBIndexField;
import org.eclipse.net4j.spi.db.ddl.InternalDBSchema;
import org.eclipse.net4j.spi.db.ddl.InternalDBSchemaElement;
import org.eclipse.net4j.spi.db.ddl.InternalDBTable;
import org.eclipse.net4j.util.event.IListener;

import java.io.IOException;
import java.io.Writer;
import java.util.Properties;
import java.util.Set;

/**
 * @author Eike Stepper
 */
public abstract class DelegatingDBSchemaElement implements InternalDBSchemaElement
{
  private InternalDBSchemaElement delegate;

  DelegatingDBSchemaElement(InternalDBSchemaElement delegate)
  {
    this.delegate = delegate;
  }

  public InternalDBSchemaElement getDelegate()
  {
    return delegate;
  }

  public void setDelegate(IDBSchemaElement delegate)
  {
    if (this.delegate != null)
    {
      this.delegate.setWrapper(null);
    }

    this.delegate = (InternalDBSchemaElement)delegate;

    if (this.delegate != null)
    {
      this.delegate.setWrapper(this);
    }
  }

  public final void setWrapper(IDBSchemaElement wrapper)
  {
    throw new UnsupportedOperationException();
  }

  public IDBSchemaElement getParent()
  {
    return wrap(getDelegate().getParent());
  }

  public final String getName()
  {
    return getDelegate().getName();
  }

  @Deprecated
  public final void setName(String name)
  {
    getDelegate().setName(name);
  }

  public final <T extends IDBSchemaElement> T getElement(Class<T> type, String name)
  {
    return getDelegate().getElement(type, name);
  }

  public final String getFullName()
  {
    return getDelegate().getFullName();
  }

  public final IDBSchema getSchema()
  {
    return wrap(getDelegate().getSchema());
  }

  public final Properties getProperties()
  {
    return getDelegate().getProperties();
  }

  public final String dumpToString()
  {
    return getDelegate().dumpToString();
  }

  public final void dump()
  {
    getDelegate().dump();
  }

  public final void dump(Writer writer) throws IOException
  {
    getDelegate().dump(writer);
  }

  public final SchemaElementType getSchemaElementType()
  {
    return getDelegate().getSchemaElementType();
  }

  public final boolean isEmpty()
  {
    return getDelegate().isEmpty();
  }

  public final IDBSchemaElement[] getElements()
  {
    IDBSchemaElement[] elements = getDelegate().getElements();
    IDBSchemaElement[] wrappers = new IDBSchemaElement[elements.length];
    for (int i = 0; i < elements.length; i++)
    {
      wrappers[i] = wrap(elements[i]);
    }

    return wrappers;
  }

  public final void accept(IDBSchemaVisitor visitor)
  {
    getDelegate().accept(visitor);
  }

  public final void remove()
  {
    getDelegate().remove();
  }

  public final void addListener(IListener listener)
  {
    getDelegate().addListener(listener);
  }

  public final IListener[] getListeners()
  {
    return getDelegate().getListeners();
  }

  public final boolean hasListeners()
  {
    return getDelegate().hasListeners();
  }

  public final void removeListener(IListener listener)
  {
    getDelegate().removeListener(listener);
  }

  @Override
  public int hashCode()
  {
    return getDelegate().hashCode();
  }

  @Override
  public boolean equals(Object obj)
  {
    return getDelegate().equals(obj);
  }

  public final int compareTo(IDBSchemaElement o)
  {
    return getDelegate().compareTo(unwrap(o));
  }

  @Override
  public String toString()
  {
    return getDelegate().toString();
  }

  public static <T extends IDBSchemaElement> T wrap(T delegate)
  {
    if (delegate == null || delegate instanceof DelegatingDBSchemaElement)
    {
      return delegate;
    }

    InternalDBSchemaElement internalDelegate = (InternalDBSchemaElement)delegate;
    IDBSchemaElement wrapper = internalDelegate.getWrapper();
    if (wrapper == null)
    {
      SchemaElementType schemaElementType = internalDelegate.getSchemaElementType();
      switch (schemaElementType)
      {
      case SCHEMA:
        wrapper = new DelegatingDBSchema((InternalDBSchema)internalDelegate);
        break;

      case TABLE:
        wrapper = new DelegatingDBTable((InternalDBTable)internalDelegate);
        break;

      case FIELD:
        wrapper = new DelegatingDBField((InternalDBField)internalDelegate);
        break;

      case INDEX:
        wrapper = new DelegatingDBIndex((InternalDBIndex)internalDelegate);
        break;

      case INDEX_FIELD:
        wrapper = new DelegatingDBIndexField((InternalDBIndexField)internalDelegate);
        break;

      default:
        throw new IllegalStateException("Illegal schema element type: " + schemaElementType);
      }

      internalDelegate.setWrapper(wrapper);
    }

    @SuppressWarnings("unchecked")
    T result = (T)wrapper;
    return result;
  }

  public static <T extends IDBSchemaElement> T[] wrap(T[] delegates, Class<T> type)
  {
    @SuppressWarnings("unchecked")
    T[] wrappers = (T[])java.lang.reflect.Array.newInstance(type, delegates.length);
    for (int i = 0; i < delegates.length; i++)
    {
      T wrapper = wrap(delegates[i]);
      wrappers[i] = wrapper;
    }

    return wrappers;
  }

  public static <T extends IDBSchemaElement> Set<T> wrap(Set<T> delegates, Set<T> wrappers)
  {
    for (T delegate : delegates)
    {
      wrappers.add(wrap(delegate));
    }

    return wrappers;
  }

  public static <T extends IDBSchemaElement> T unwrap(T wrapper)
  {
    if (wrapper instanceof DelegatingDBSchemaElement)
    {
      @SuppressWarnings("unchecked")
      T delegate = (T)((DelegatingDBSchemaElement)wrapper).getDelegate();
      return delegate;
    }

    return wrapper;
  }

  public static <T extends IDBSchemaElement> T[] unwrap(T[] wrappers, Class<T> type)
  {
    @SuppressWarnings("unchecked")
    T[] delegates = (T[])java.lang.reflect.Array.newInstance(type, wrappers.length);
    for (int i = 0; i < wrappers.length; i++)
    {
      delegates[i] = unwrap(wrappers[i]);
    }

    return delegates;
  }
}

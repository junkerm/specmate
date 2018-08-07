/*
 * Copyright (c) 2008, 2011-2013, 2015 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.net4j.db.ddl;

import org.eclipse.net4j.db.DBType;

/**
 * A table specification in a {@link IDBSchema DB schema}.
 *
 * @author Eike Stepper
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IDBTable extends IDBSchemaElement
{
  /**
   * @since 4.2
   */
  public IDBSchema getParent();

  public IDBField addField(String name, DBType type);

  public IDBField addField(String name, DBType type, boolean notNull);

  public IDBField addField(String name, DBType type, int precision);

  public IDBField addField(String name, DBType type, int precision, boolean notNull);

  public IDBField addField(String name, DBType type, int precision, int scale);

  public IDBField addField(String name, DBType type, int precision, int scale, boolean notNull);

  /**
   * @since 4.2
   */
  public IDBField getFieldSafe(String name) throws SchemaElementNotFoundException;

  public IDBField getField(String name);

  public IDBField getField(int position);

  public int getFieldCount();

  public IDBField[] getFields();

  /**
   * @since 4.2
   */
  public IDBField[] getFields(String... fieldNames) throws SchemaElementNotFoundException;

  /**
   * @since 4.5
   */
  public boolean hasIndexFor(IDBField... fields);

  /**
   * @since 4.2
   */
  public IDBIndex addIndex(String name, IDBIndex.Type type, IDBField... fields);

  /**
   * @since 4.2
   */
  public IDBIndex addIndex(String name, IDBIndex.Type type, String... fieldNames) throws SchemaElementNotFoundException;

  /**
   * @since 4.2
   */
  public IDBIndex addIndexEmpty(String name, IDBIndex.Type type);

  public IDBIndex addIndex(IDBIndex.Type type, IDBField... fields);

  /**
   * @since 4.2
   */
  public IDBIndex addIndex(IDBIndex.Type type, String... fieldNames) throws SchemaElementNotFoundException;

  /**
   * @since 4.2
   */
  public IDBIndex addIndexEmpty(IDBIndex.Type type);

  /**
   * @since 4.2
   */
  public IDBIndex getIndexSafe(String name) throws SchemaElementNotFoundException;

  /**
   * @since 4.2
   */
  public IDBIndex getIndex(String name);

  /**
   * @since 4.2
   */
  public IDBIndex getIndex(int position);

  public int getIndexCount();

  public IDBIndex[] getIndices();

  public IDBIndex getPrimaryKeyIndex();

  public String sqlInsert();
}

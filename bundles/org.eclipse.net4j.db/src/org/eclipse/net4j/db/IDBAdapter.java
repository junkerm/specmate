/*
 * Copyright (c) 2007-2013, 2016 Eike Stepper (Loehne, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 *    Stefan Winkler - bug 289445
 */
package org.eclipse.net4j.db;

import org.eclipse.net4j.db.ddl.IDBField;
import org.eclipse.net4j.db.ddl.IDBSchema;
import org.eclipse.net4j.db.ddl.IDBTable;
import org.eclipse.net4j.db.ddl.delta.IDBSchemaDelta;
import org.eclipse.net4j.internal.db.DBAdapterRegistry;
import org.eclipse.net4j.spi.db.DBAdapter;
import org.eclipse.net4j.util.registry.IRegistry;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Set;

/**
 * Abstracts all aspects of a database that are vendor-specific.
 *
 * @author Eike Stepper
 * @noimplement This interface is not intended to be implemented by clients. Subclass {@link DBAdapter} instead.
 */
public interface IDBAdapter
{
  public static final IRegistry<String, IDBAdapter> REGISTRY = DBAdapterRegistry.INSTANCE;

  public String getName();

  public String getVersion();

  /**
   * @deprecated As of 4.2 no longer supported because of IP issues for external build dependencies (the vendor driver libs).
   */
  @Deprecated
  public Driver getJDBCDriver();

  /**
   * @deprecated As of 4.2 no longer supported because of IP issues for external build dependencies (the vendor driver libs).
   */
  @Deprecated
  public DataSource createJDBCDataSource();

  /**
   * @since 4.3
   */
  public IDBConnectionProvider createConnectionProvider(DataSource dataSource);

  /**
   * @since 4.5
   */
  public Connection modifyConnection(Connection connection);

  /**
   * @since 4.2
   */
  public IDBSchema readSchema(Connection connection, String name);

  /**
   * @since 4.2
   */
  public void readSchema(Connection connection, IDBSchema schema);

  /**
   * @since 4.2
   */
  public void updateSchema(Connection connection, IDBSchema schema, IDBSchemaDelta delta) throws DBException;

  public Set<IDBTable> createTables(Iterable<? extends IDBTable> tables, Connection connection) throws DBException;

  public boolean createTable(IDBTable table, Statement statement) throws DBException;

  public Collection<IDBTable> dropTables(Iterable<? extends IDBTable> tables, Connection connection) throws DBException;

  public boolean dropTable(IDBTable table, Statement statement);

  public String[] getReservedWords();

  public boolean isReservedWord(String word);

  /**
   * @since 2.0
   */
  public int getMaxTableNameLength();

  /**
   * @since 2.0
   */
  public int getMaxFieldNameLength();

  /**
   * Returns the column length for the given database type.
   *
   * @param type the {@link DBType} to check.
   * @return the supported column length for the type.
   * @since 4.2
   */
  public int getFieldLength(DBType type);

  public boolean isTypeIndexable(DBType type);

  /**
   * Provide a way for the DBAdapter to override unsupported DB types with replacements. The default implementation just
   * returns the given type. Subclasses may override single types with replacements.
   *
   * @since 3.0
   */
  public DBType adaptType(DBType type);

  /**
   * Check if a character is valid as first character. (e.g., underscores are forbidden as first character in Derby
   * elements.
   *
   * @since 4.0
   */
  public boolean isValidFirstChar(char ch);

  /**
   * Check if an exception indicates a constraint violation (duplicate key)
   *
   * @since 4.0
   */
  public boolean isDuplicateKeyException(SQLException ex);

  /**
   * @since 4.2
   */
  public boolean isTableNotFoundException(SQLException ex);

  /**
   * @since 4.2
   */
  public boolean isColumnNotFoundException(SQLException ex);

  /**
   * @since 4.2
   */
  public String sqlRenameField(IDBField field, String oldName);

  /**
   * @since 4.2
   */
  public String sqlModifyField(IDBField field);
}

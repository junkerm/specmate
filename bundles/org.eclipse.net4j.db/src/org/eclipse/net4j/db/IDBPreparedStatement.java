/*
 * Copyright (c) 2013, 2015 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.net4j.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @since 4.2
 * @author Eike Stepper
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface IDBPreparedStatement extends Comparable<IDBPreparedStatement>, PreparedStatement
{
  /**
   * @since 4.3
   */
  public IDBConnection getConnection() throws SQLException;

  /**
   * @deprecated As of 4.3 use {@link #getConnection()}.
   */
  @Deprecated
  public IDBConnection getTransaction();

  public String getSQL();

  public ReuseProbability getReuseProbability();

  public IDBResultSet getGeneratedKeys() throws SQLException;

  public IDBResultSet getResultSet() throws SQLException;

  public IDBResultSet executeQuery() throws SQLException;

  /**
   * @deprecated Not supported.
   */
  @Deprecated
  public ResultSet executeQuery(String sql) throws SQLException;

  /**
   * An enum for the degree of probability to which a prepared statement is reused later on. This is used for managing
   * the cache of prepared statements so that statements which are more likely reused are kept in the cache longer. Rule
   * of thumb:
   * <ul>
   * <li>For global statements which are used regularly use {@link ReuseProbability#MAX MAX}.
   * <li>For constant object-specific statements which are used regularly use {@link ReuseProbability#HIGH HIGH}.
   * <li>For object-specific statements which are assembled from constants which are used regularly use {@link ReuseProbability#MEDIUM MEDIUM}.
   * <li>For all other dynamic statements, like queries, use {@link ReuseProbability#LOW LOW}
   * </ul>
   *
   * @author Stefan Winkler
   */
  public static enum ReuseProbability
  {
    MAX, HIGH, MEDIUM, LOW;
  }
}

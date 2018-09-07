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
package org.eclipse.net4j.internal.db;

import org.eclipse.net4j.db.DBException;
import org.eclipse.net4j.db.IDBPreparedStatement;
import org.eclipse.net4j.db.IDBResultSet;
import org.eclipse.net4j.db.jdbc.DelegatingPreparedStatement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Eike Stepper
 */
public class DBPreparedStatement extends DelegatingPreparedStatement implements IDBPreparedStatement
{
  private final String sql;

  private final ReuseProbability reuseProbability;

  private int touch;

  public DBPreparedStatement(DBConnection transaction, String sql, ReuseProbability reuseProbability, PreparedStatement delegate)
  {
    super(delegate, transaction);
    this.sql = sql;
    this.reuseProbability = reuseProbability;
  }

  @Override
  public DBConnection getConnection() throws SQLException
  {
    return (DBConnection)super.getConnection();
  }

  @Deprecated
  public DBConnection getTransaction()
  {
    try
    {
      return getConnection();
    }
    catch (SQLException ex)
    {
      throw new DBException(ex);
    }
  }

  public String getSQL()
  {
    return sql;
  }

  public ReuseProbability getReuseProbability()
  {
    return reuseProbability;
  }

  public void setTouch(int touch)
  {
    this.touch = touch;
  }

  public int compareTo(IDBPreparedStatement o)
  {
    int result = reuseProbability.compareTo(o.getReuseProbability());
    if (result == 0)
    {
      result = ((DBPreparedStatement)o).touch - touch;
    }

    return result;
  }

  @Override
  public String toString()
  {
    return "PreparedStatement[sql=" + sql + ", probability=" + reuseProbability + ", touch=" + touch + "]";
  }

  @Override
  public void close() throws SQLException
  {
    getConnection().releasePreparedStatement(this);
  }

  @Override
  public IDBResultSet getGeneratedKeys() throws SQLException
  {
    return new DBResultSet(getDelegate().getGeneratedKeys(), this);
  }

  @Override
  public IDBResultSet getResultSet() throws SQLException
  {
    return new DBResultSet(getDelegate().getResultSet(), this);
  }

  @Override
  public IDBResultSet executeQuery() throws SQLException
  {
    return new DBResultSet(getDelegate().executeQuery(), this);
  }

  @Override
  @Deprecated
  public ResultSet executeQuery(String sql) throws SQLException
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setString(int parameterIndex, String value) throws SQLException
  {
    value = getConnection().convertString(this, parameterIndex, value);
    super.setString(parameterIndex, value);
  }

  public String convertString(DBResultSet resultSet, int columnIndex, String value) throws SQLException
  {
    return getConnection().convertString(resultSet, columnIndex, value);
  }

  public String convertString(DBResultSet resultSet, String columnLabel, String value) throws SQLException
  {
    return getConnection().convertString(resultSet, columnLabel, value);
  }
}

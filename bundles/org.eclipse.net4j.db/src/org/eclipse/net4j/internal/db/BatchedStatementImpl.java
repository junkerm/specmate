/*
 * Copyright (c) 2016 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.net4j.internal.db;

import org.eclipse.net4j.db.BatchedStatement;
import org.eclipse.net4j.db.DBException;
import org.eclipse.net4j.db.jdbc.DelegatingPreparedStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Eike Stepper
 * @since 4.5
 */
public final class BatchedStatementImpl extends DelegatingPreparedStatement implements BatchedStatement
{
  private final int batchSize;

  private int batchCount;

  private int totalResult;

  public BatchedStatementImpl(PreparedStatement delegate, int batchSize) throws DBException
  {
    super(delegate, getConnection(delegate));
    this.batchSize = batchSize;
  }

  public int getBatchSize()
  {
    return batchSize;
  }

  public int getBatchCount()
  {
    return batchCount;
  }

  public int getTotalResult()
  {
    return totalResult;
  }

  @Override
  public int executeUpdate() throws SQLException
  {
    PreparedStatement delegate = getDelegate();
    delegate.addBatch();

    if (++batchCount >= batchSize)
    {
      return doExecuteBatch();
    }

    return 0;
  }

  @Override
  public void close() throws SQLException
  {
    if (batchCount != 0)
    {
      doExecuteBatch();
    }

    super.close();
  }

  @Override
  public ResultSet getResultSet() throws SQLException
  {
    throw new UnsupportedOperationException("Only updates are supported");
  }

  @Override
  public ResultSet executeQuery() throws SQLException
  {
    throw new UnsupportedOperationException("Only updates are supported");
  }

  @Deprecated
  @Override
  public ResultSet executeQuery(String sql) throws SQLException
  {
    throw new UnsupportedOperationException("Only updates are supported");
  }

  private int doExecuteBatch() throws SQLException
  {
    int sum = 0;

    int[] results = getDelegate().executeBatch();
    for (int i = 0; i < results.length; i++)
    {
      int result = results[i];
      if (result != Statement.SUCCESS_NO_INFO)
      {
        if (result < 0)
        {
          throw new DBException("Result " + i + " is not successful: " + result);
        }

        sum += result;
      }
    }

    totalResult += sum;
    return sum;
  }

  private static Connection getConnection(PreparedStatement delegate) throws DBException
  {
    try
    {
      return delegate.getConnection();
    }
    catch (SQLException ex)
    {
      throw new DBException(ex);
    }
  }
}

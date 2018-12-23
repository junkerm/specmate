/*
 * Copyright (c) 2007, 2008, 2011-2013, 2015 Eike Stepper (Loehne, Germany) and others.
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
import org.eclipse.net4j.db.IDBConnectionProvider2;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Eike Stepper
 */
public class DataSourceConnectionProvider implements IDBConnectionProvider2
{
  private final DataSource dataSource;

  private final String user;

  public DataSourceConnectionProvider(DataSource dataSource, String user)
  {
    this.dataSource = dataSource;
    this.user = user;
  }

  public DataSource getDataSource()
  {
    return dataSource;
  }

  public String getUserID()
  {
    return user;
  }

  public Connection getConnection()
  {
    try
    {
      return dataSource.getConnection();
    }
    catch (SQLException ex)
    {
      throw new DBException(ex);
    }
  }

  @Override
  public String toString()
  {
    return dataSource.toString();
  }
}

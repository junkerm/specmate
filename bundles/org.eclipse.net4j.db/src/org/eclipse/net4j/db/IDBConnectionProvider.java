/*
 * Copyright (c) 2008, 2011, 2012, 2015 Eike Stepper (Loehne, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.net4j.db;

import javax.sql.DataSource;

import java.sql.Connection;

/**
 * Provides a database {@link Connection connection}, roughly comparable with a {@link DataSource data source}.
 *
 * @author Eike Stepper
 */
public interface IDBConnectionProvider
{
  /**
   * Returns a connection.
   */
  public Connection getConnection() throws DBException;
}

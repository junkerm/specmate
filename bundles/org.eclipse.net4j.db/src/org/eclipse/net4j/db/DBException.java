/*
 * Copyright (c) 2007, 2008, 2010-2012, 2015 Eike Stepper (Loehne, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.net4j.db;

import org.eclipse.net4j.util.StringUtil;

import java.sql.SQLException;

/**
 * A {@link RuntimeException runtime exception} thrown to indicate problems with a database, frequently used to wrap
 * checked {@link SQLException SQL exceptions}.
 *
 * @author Eike Stepper
 */
public class DBException extends RuntimeException
{
  private static final long serialVersionUID = 1L;

  public DBException()
  {
  }

  public DBException(String message)
  {
    super(message);
  }

  public DBException(Throwable cause)
  {
    super(cause);
  }

  public DBException(String message, Throwable cause)
  {
    super(message, cause);
  }

  /**
   * @since 4.0
   */
  public DBException(Throwable cause, String sql)
  {
    super(format(null, sql), cause);
  }

  /**
   * @since 4.0
   */
  public DBException(String message, Throwable cause, String sql)
  {
    super(format(message, sql), cause);
  }

  private static String format(String message, String sql)
  {
    if (StringUtil.isEmpty(message))
    {
      if (StringUtil.isEmpty(sql))
      {
        return "";
      }

      return sql;
    }

    if (StringUtil.isEmpty(sql))
    {
      return message;
    }

    return message + ": " + sql;
  }
}

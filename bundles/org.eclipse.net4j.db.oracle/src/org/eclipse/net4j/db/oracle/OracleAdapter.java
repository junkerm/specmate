/*
 * Copyright (c) 2012, 2013, 2015, 2016 Eike Stepper (Loehne, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 *    Erdal Karaca - copied from mysql impl to adapt to oracle db
 */
package org.eclipse.net4j.db.oracle;

import org.eclipse.net4j.db.DBType;
import org.eclipse.net4j.db.DBUtil;
import org.eclipse.net4j.db.IDBConnectionProvider;
import org.eclipse.net4j.db.ddl.IDBField;
import org.eclipse.net4j.spi.db.DBAdapter;
import org.eclipse.net4j.util.security.IUserAware;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import oracle.jdbc.pool.OracleDataSource;

public class OracleAdapter extends DBAdapter
{
  public static final String NAME = "oracle"; //$NON-NLS-1$

  public static final String VERSION = "11.1.0.7"; //$NON-NLS-1$

  public OracleAdapter()
  {
    super(NAME, VERSION);
  }

  @Override
  public IDBConnectionProvider createConnectionProvider(DataSource dataSource)
  {
    if (dataSource instanceof OracleDataSource)
    {
      return DBUtil.createConnectionProvider(dataSource, ((OracleDataSource)dataSource).getUser());
    }

    return super.createConnectionProvider(dataSource);
  }

  public String[] getReservedWords()
  {
    List<String> list = new ArrayList<String>(Arrays.asList(getSQL92ReservedWords()));
    list.add("INDEX");
    list.add("COMMENT");
    list.add("ACCESS");
    return list.toArray(new String[list.size()]);
  }

  @Override
  public boolean isTypeIndexable(DBType type)
  {
    switch (type)
    {
    case VARCHAR:
      return false;

    default:
      return super.isTypeIndexable(type);
    }
  }

  @Override
  protected String getTypeName(IDBField field)
  {
    DBType type = field.getType();
    switch (type)
    {
    case NUMERIC:
    case DECIMAL:
    case FLOAT:
    case REAL:
    case DOUBLE:
    case BIGINT:
      return "NUMBER";
    case TINYINT:
      return "NUMBER(5)";
    case SMALLINT:
    case BOOLEAN:
    case BIT:
      return "NUMBER(7)";
    case INTEGER:
      return "NUMBER(12)";
    case DATE:
    case TIME:
      return "DATE";
    case CHAR:
    case VARCHAR:
      return "VARCHAR2(" + field.getPrecision() + " CHAR)";
    case LONGVARCHAR:
      return "LONG";
    case BINARY:
    case VARBINARY:
    case LONGVARBINARY:
      return "LONG RAW";
    default:
      return super.getTypeName(field);
    }
  }

  @Override
  public int getFieldLength(DBType type)
  {
    if (type == DBType.VARCHAR)
    {
      return 4000; // Oracle only supports 4000 for VARCHAR
    }

    return super.getFieldLength(type);
  }

  @Override
  public DBType adaptType(DBType type)
  {
    if (type == DBType.BOOLEAN)
    {
      return DBType.SMALLINT;
    }

    return super.adaptType(type);
  }

  @Override
  public int getMaxTableNameLength()
  {
    return 30;
  }

  @Override
  public int getMaxFieldNameLength()
  {
    return 30;
  }

  @Override
  public boolean isTableNotFoundException(SQLException ex)
  {
    String message = ex.getMessage();
    return message != null && message.toLowerCase().contains("ora-00942") && "42000".equals(ex.getSQLState());
  }

  @Override
  public boolean isColumnNotFoundException(SQLException ex)
  {
    String message = ex.getMessage();
    return message != null && message.toLowerCase().contains("ora-00904") && "42000".equals(ex.getSQLState());
  }

  @Override
  public boolean isDuplicateKeyException(SQLException ex)
  {
    String message = ex.getMessage();
    return message != null && message.toLowerCase().contains("ora-00001") && "23000".equals(ex.getSQLState());
  }

  @Override
  protected String sqlModifyField(String tableName, String fieldName, String definition)
  {
    return "ALTER TABLE " + tableName + " MODIFY " + fieldName + " " + definition;
  }

  @Override
  protected ResultSet readTables(Connection connection, DatabaseMetaData metaData, String schemaName) throws SQLException
  {
    if (schemaName == null && connection instanceof IUserAware)
    {
      schemaName = ((IUserAware)connection).getUserID();
    }

    return metaData.getTables(null, schemaName, null, new String[] { "TABLE" });
  }

  @Override
  public String convertString(PreparedStatement preparedStatement, int parameterIndex, String value)
  {
    if (value != null && value.length() == 0)
    {
      String replacement = getEmptyStringReplacement();
      if (replacement != null)
      {
        value = replacement;
      }
    }

    return super.convertString(preparedStatement, parameterIndex, value);
  }

  @Override
  public String convertString(ResultSet resultSet, int columnIndex, String value)
  {
    value = super.convertString(resultSet, columnIndex, value);

    if (value != null)
    {
      String replacement = getEmptyStringReplacement();
      if (replacement != null && replacement.equals(value))
      {
        value = "";
      }
    }

    return value;
  }

  @Override
  public String convertString(ResultSet resultSet, String columnLabel, String value)
  {
    value = super.convertString(resultSet, columnLabel, value);

    if (value != null)
    {
      String replacement = getEmptyStringReplacement();
      if (replacement != null && replacement.equals(value))
      {
        value = null;
      }
    }

    return value;
  }

  /**
   * @since 1.1
   */
  protected String getEmptyStringReplacement()
  {
    return "<empty>";
  }
}

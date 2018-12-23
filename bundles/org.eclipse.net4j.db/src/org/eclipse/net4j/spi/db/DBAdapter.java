/*
 * Copyright (c) 2008-2016 Eike Stepper (Loehne, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 *    Stefan Winkler - Bug 289445
 */
package org.eclipse.net4j.spi.db;

import org.eclipse.net4j.db.DBException;
import org.eclipse.net4j.db.DBType;
import org.eclipse.net4j.db.DBUtil;
import org.eclipse.net4j.db.IDBAdapter;
import org.eclipse.net4j.db.IDBConnectionProvider;
import org.eclipse.net4j.db.ddl.IDBField;
import org.eclipse.net4j.db.ddl.IDBIndex;
import org.eclipse.net4j.db.ddl.IDBSchema;
import org.eclipse.net4j.db.ddl.IDBTable;
import org.eclipse.net4j.db.ddl.delta.IDBDelta.ChangeKind;
import org.eclipse.net4j.db.ddl.delta.IDBDeltaVisitor;
import org.eclipse.net4j.db.ddl.delta.IDBFieldDelta;
import org.eclipse.net4j.db.ddl.delta.IDBIndexDelta;
import org.eclipse.net4j.db.ddl.delta.IDBSchemaDelta;
import org.eclipse.net4j.db.ddl.delta.IDBTableDelta;
import org.eclipse.net4j.internal.db.bundle.OM;
import org.eclipse.net4j.internal.db.ddl.DBField;
import org.eclipse.net4j.spi.db.ddl.InternalDBIndex;
import org.eclipse.net4j.util.CheckUtil;
import org.eclipse.net4j.util.om.trace.ContextTracer;

import javax.sql.DataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A useful base class for implementing custom {@link IDBAdapter DB adapters}.
 *
 * @author Eike Stepper
 */
public abstract class DBAdapter implements IDBAdapter
{
  private static final ContextTracer TRACER = new ContextTracer(OM.DEBUG_SQL, DBAdapter.class);

  private static final String[] SQL92_RESERVED_WORDS = { "ABSOLUTE", "ACTION", "ADD", "AFTER", "ALL", "ALLOCATE", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
      "ALTER", "AND", "ANY", "ARE", "ARRAY", "AS", "ASC", "ASENSITIVE", "ASSERTION", "ASYMMETRIC", "AT", "ATOMIC", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$ //$NON-NLS-12$
      "AUTHORIZATION", "AVG", "BEFORE", "BEGIN", "BETWEEN", "BIGINT", "BINARY", "BIT", "BIT_LENGTH", "BLOB", "BOOLEAN", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$
      "BOTH", "BREADTH", "BY", "CALL", "CALLED", "CASCADE", "CASCADED", "CASE", "CAST", "CATALOG", "CHAR", "CHARACTER", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$ //$NON-NLS-12$
      "CHARACTER_LENGTH", "CHAR_LENGTH", "CHECK", "CLOB", "CLOSE", "COALESCE", "COLLATE", "COLLATION", "COLUMN", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$
      "COMMIT", "CONDITION", "CONNECT", "CONNECTION", "CONSTRAINT", "CONSTRAINTS", "CONSTRUCTOR", "CONTAINS", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$
      "CONTINUE", "CONVERT", "CORRESPONDING", "COUNT", "CREATE", "CROSS", "CUBE", "CURRENT", "CURRENT_DATE", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$
      "CURRENT_DEFAULT_TRANSFORM_GROUP", "CURRENT_PATH", "CURRENT_ROLE", "CURRENT_TIME", "CURRENT_TIMESTAMP", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
      "CURRENT_TRANSFORM_GROUP_FOR_TYPE", "CURRENT_USER", "CURSOR", "CYCLE", "DATA", "DATE", "DAY", "DEALLOCATE", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$
      "DEC", "DECIMAL", "DECLARE", "DEFAULT", "DEFERRABLE", "DEFERRED", "DELETE", "DEPTH", "DEREF", "DESC", "DESCRIBE", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$
      "DESCRIPTOR", "DETERMINISTIC", "DIAGNOSTICS", "DISCONNECT", "DISTINCT", "DO", "DOMAIN", "DOUBLE", "DROP", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$
      "DYNAMIC", "EACH", "ELEMENT", "ELSE", "ELSEIF", "END", "EQUALS", "ESCAPE", "EXCEPT", "EXCEPTION", "EXEC", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$
      "EXECUTE", "EXISTS", "EXIT", "EXTERNAL", "EXTRACT", "FALSE", "FETCH", "FILTER", "FIRST", "FLOAT", "FOR", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$
      "FOREIGN", "FOUND", "FREE", "FROM", "FULL", "FUNCTION", "GENERAL", "GET", "GLOBAL", "GO", "GOTO", "GRANT", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$ //$NON-NLS-12$
      "GROUP", "GROUPING", "HANDLER", "HAVING", "HOLD", "HOUR", "IDENTITY", "IF", "IMMEDIATE", "IN", "INDICATOR", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$
      "INITIALLY", "INNER", "INOUT", "INPUT", "INSENSITIVE", "INSERT", "INT", "INTEGER", "INTERSECT", "INTERVAL", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$
      "INTO", "IS", "ISOLATION", "ITERATE", "JOIN", "KEY", "LANGUAGE", "LARGE", "LAST", "LATERAL", "LEADING", "LEAVE", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$ //$NON-NLS-12$
      "LEFT", "LEVEL", "LIKE", "LOCAL", "LOCALTIME", "LOCALTIMESTAMP", "LOCATOR", "LOOP", "LOWER", "MAP", "MATCH", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$
      "MAX", "MEMBER", "MERGE", "METHOD", "MIN", "MINUTE", "MODIFIES", "MODULE", "MONTH", "MULTISET", "NAMES", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$
      "NATIONAL", "NATURAL", "NCHAR", "NCLOB", "NEW", "NEXT", "NO", "NONE", "NOT", "NULL", "NULLIF", "NUMERIC", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$ //$NON-NLS-12$
      "OBJECT", "OCTET_LENGTH", "OF", "OLD", "ON", "ONLY", "OPEN", "OPTION", "OR", "ORDER", "ORDINALITY", "OUT", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$ //$NON-NLS-12$
      "OUTER", "OUTPUT", "OVER", "OVERLAPS", "PAD", "PARAMETER", "PARTIAL", "PARTITION", "PATH", "POSITION", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$
      "PRECISION", "PREPARE", "PRESERVE", "PRIMARY", "PRIOR", "PRIVILEGES", "PROCEDURE", "PUBLIC", "RANGE", "READ", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$
      "READS", "REAL", "RECURSIVE", "REF", "REFERENCES", "REFERENCING", "RELATIVE", "RELEASE", "REPEAT", "RESIGNAL", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$
      "RESTRICT", "RESULT", "RETURN", "RETURNS", "REVOKE", "RIGHT", "ROLE", "ROLLBACK", "ROLLUP", "ROUTINE", "ROW", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$
      "ROWS", "SAVEPOINT", "SCHEMA", "SCOPE", "SCROLL", "SEARCH", "SECOND", "SECTION", "SELECT", "SENSITIVE", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$
      "SESSION", "SESSION_USER", "SET", "SETS", "SIGNAL", "SIMILAR", "SIZE", "SMALLINT", "SOME", "SPACE", "SPECIFIC", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$
      "SPECIFICTYPE", "SQL", "SQLCODE", "SQLERROR", "SQLEXCEPTION", "SQLSTATE", "SQLWARNING", "START", "STATE", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$
      "STATIC", "SUBMULTISET", "SUBSTRING", "SUM", "SYMMETRIC", "SYSTEM", "SYSTEM_USER", "TABLE", "TABLESAMPLE", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$
      "TEMPORARY", "THEN", "TIME", "TIMESTAMP", "TIMEZONE_HOUR", "TIMEZONE_MINUTE", "TO", "TRAILING", "TRANSACTION", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$
      "TRANSLATE", "TRANSLATION", "TREAT", "TRIGGER", "TRIM", "TRUE", "UNDER", "UNDO", "UNION", "UNIQUE", "UNKNOWN", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$
      "UNNEST", "UNTIL", "UPDATE", "UPPER", "USAGE", "USER", "USING", "VALUE", "VALUES", "VARCHAR", "VARYING", "VIEW", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$ //$NON-NLS-12$
      "WHEN", "WHENEVER", "WHERE", "WHILE", "WINDOW", "WITH", "WITHIN", "WITHOUT", "WORK", "WRITE", "YEAR", "ZONE" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$ //$NON-NLS-12$

  private String name;

  private String version;

  private Set<String> reservedWords;

  public DBAdapter(String name, String version)
  {
    this.name = name;
    this.version = version;
  }

  public String getName()
  {
    return name;
  }

  public String getVersion()
  {
    return version;
  }

  /**
   * @since 4.2
   * @deprecated As of 4.2 no longer supported because of IP issues for external build dependencies (the vendor driver libs).
   */
  @Deprecated
  public Driver getJDBCDriver()
  {
    throw new UnsupportedOperationException();
  }

  /**
   * @since 4.2
   * @deprecated As of 4.2 no longer supported because of IP issues for external build dependencies (the vendor driver libs).
   */
  @Deprecated
  public DataSource createJDBCDataSource()
  {
    throw new UnsupportedOperationException();
  }

  /**
   * @since 4.3
   */
  public IDBConnectionProvider createConnectionProvider(DataSource dataSource)
  {
    return DBUtil.createConnectionProvider(dataSource);
  }

  /**
   * @since 4.5
   */
  public Connection modifyConnection(Connection connection)
  {
    return connection;
  }

  /**
   * @since 4.2
   */
  public IDBSchema readSchema(Connection connection, String name)
  {
    return DBUtil.readSchema(this, connection, name);
  }

  /**
   * @since 4.2
   */
  public void readSchema(Connection connection, IDBSchema schema)
  {
    boolean wasTrackConstruction = DBField.isTrackConstruction();
    DBField.trackConstruction(false);

    try
    {
      String schemaName = schema.getName();

      DatabaseMetaData metaData = connection.getMetaData();
      Set<String> schemaNames = DBUtil.getAllSchemaNames(metaData);
      if (!schemaNames.contains(schemaName))
      {
        schemaName = null;
      }

      ResultSet tables = readTables(connection, metaData, schemaName);
      while (tables.next())
      {
        String tableName = tables.getString(3);

        IDBTable table = schema.addTable(tableName);
        readFields(connection, table);
        readIndices(connection, metaData, table, schemaName);
      }
    }
    catch (SQLException ex)
    {
      throw new DBException(ex);
    }
    finally
    {
      DBField.trackConstruction(wasTrackConstruction);
    }
  }

  /**
   * @since 4.3
   */
  protected ResultSet readTables(Connection connection, DatabaseMetaData metaData, String schemaName) throws SQLException
  {
    return metaData.getTables(null, schemaName, null, new String[] { "TABLE" });
  }

  /**
   * @since 4.2
   */
  protected void readFields(Connection connection, IDBTable table) throws SQLException
  {
    Statement statement = null;
    ResultSet resultSet = null;

    try
    {
      statement = connection.createStatement();
      statement.setMaxRows(1);
      resultSet = statement.executeQuery("SELECT * FROM " + table);
      ResultSetMetaData metaData = resultSet.getMetaData();

      for (int i = 0; i < metaData.getColumnCount(); i++)
      {
        int column = i + 1;
        String name = metaData.getColumnName(column);
        if (name == null)
        {
          // Bug 405924: Just to be sure in case this happens with Oracle.
          continue;
        }

        DBType type = DBType.getTypeByCode(metaData.getColumnType(column));
        int precision = metaData.getPrecision(column);
        int scale = metaData.getScale(column);
        boolean notNull = metaData.isNullable(column) == ResultSetMetaData.columnNoNulls;

        table.addField(name, type, precision, scale, notNull);
      }
    }
    finally
    {
      DBUtil.close(resultSet);
      DBUtil.close(statement);
    }
  }

  /**
   * @since 4.2
   */
  protected void readIndices(Connection connection, DatabaseMetaData metaData, IDBTable table, String schemaName) throws SQLException
  {
    String tableName = table.getName();

    ResultSet primaryKeys = metaData.getPrimaryKeys(null, schemaName, tableName);
    readIndices(connection, primaryKeys, table, 6, 0, 4, 5);

    ResultSet indexInfo = metaData.getIndexInfo(null, schemaName, tableName, false, false);
    readIndices(connection, indexInfo, table, 6, 4, 9, 8);
  }

  /**
   * @since 4.2
   */
  protected void readIndices(Connection connection, ResultSet resultSet, IDBTable table, int indexNameColumn, int indexTypeColumn, int fieldNameColumn,
      int fieldPositionColumn) throws SQLException
  {
    try
    {
      String indexName = null;
      IDBIndex.Type indexType = null;
      List<FieldInfo> fieldInfos = new ArrayList<FieldInfo>();

      while (resultSet.next())
      {
        String name = resultSet.getString(indexNameColumn);
        if (name == null)
        {
          // Bug 405924: It seems that this can happen with Oracle.
          continue;
        }

        if (indexName != null && !indexName.equals(name))
        {
          addIndex(connection, table, indexName, indexType, fieldInfos);
          fieldInfos.clear();
        }

        indexName = name;

        if (indexTypeColumn == 0)
        {
          indexType = IDBIndex.Type.PRIMARY_KEY;
        }
        else
        {
          boolean nonUnique = resultSet.getBoolean(indexTypeColumn);
          indexType = nonUnique ? IDBIndex.Type.NON_UNIQUE : IDBIndex.Type.UNIQUE;
        }

        FieldInfo fieldInfo = new FieldInfo();
        fieldInfo.name = resultSet.getString(fieldNameColumn);
        fieldInfo.position = resultSet.getShort(fieldPositionColumn);
        fieldInfos.add(fieldInfo);
      }

      if (indexName != null)
      {
        addIndex(connection, table, indexName, indexType, fieldInfos);
      }
    }
    finally
    {
      DBUtil.close(resultSet);
    }
  }

  /**
   * @since 4.2
   */
  protected void addIndex(Connection connection, IDBTable table, String name, IDBIndex.Type type, List<FieldInfo> fieldInfos)
  {
    IDBField[] fields = new IDBField[fieldInfos.size()];

    Collections.sort(fieldInfos);
    for (int i = 0; i < fieldInfos.size(); i++)
    {
      FieldInfo fieldInfo = fieldInfos.get(i);
      IDBField field = table.getField(fieldInfo.name);
      if (field == null)
      {
        throw new IllegalStateException("Field not found: " + fieldInfo.name);
      }

      fields[i] = field;
    }

    if (!isPrimaryKeyShadow(connection, table, name, type, fields))
    {
      table.addIndex(name, type, fields);
    }
  }

  /**
   * @since 4.2
   */
  protected boolean isPrimaryKeyShadow(Connection connection, IDBTable table, String name, IDBIndex.Type type, IDBField[] fields)
  {
    if (type != IDBIndex.Type.UNIQUE)
    {
      return false;
    }

    IDBIndex primaryKey = table.getPrimaryKeyIndex();
    if (primaryKey == null)
    {
      return false;
    }

    IDBField[] primaryKeyFields = primaryKey.getFields();
    return Arrays.equals(primaryKeyFields, fields);
  }

  /**
   * @since 4.2
   */
  public void updateSchema(final Connection connection, final IDBSchema schema, IDBSchemaDelta delta) throws DBException
  {
    // Apply delta to in-memory representation of the schema
    delta.applyTo(schema);

    // Call DDL methods to update the database schema
    IDBDeltaVisitor schemaUpdater = new IDBDeltaVisitor.Default()
    {
      @Override
      public void visit(IDBTableDelta delta)
      {
        IDBTable table = delta.getSchemaElement(schema);
        ChangeKind changeKind = delta.getChangeKind();

        switch (changeKind)
        {
        case ADD:
          createTable(connection, table, delta);
          break;

        case CHANGE:
          alterTable(connection, table, delta);
          break;

        case REMOVE:
          dropTable(connection, table, delta);
          break;

        default:
          throw illegalChangeKind(changeKind);
        }
      }

      @Override
      public void visit(IDBIndexDelta delta)
      {
        InternalDBIndex index = (InternalDBIndex)delta.getSchemaElement(schema);
        ChangeKind changeKind = delta.getChangeKind();

        switch (changeKind)
        {
        case ADD:
          try
          {
            createIndex(connection, index, delta);
          }
          catch (RuntimeException ex)
          {
            if (!index.isOptional())
            {
              throw ex;
            }
          }
          break;

        case CHANGE:
          dropIndex(connection, index, delta);
          try
          {
            createIndex(connection, index, delta);
          }
          catch (RuntimeException ex)
          {
            if (!index.isOptional())
            {
              throw ex;
            }
          }
          break;

        case REMOVE:
          dropIndex(connection, index, delta);
          break;

        default:
          throw illegalChangeKind(changeKind);
        }

        stopRecursion();
      }

      @Override
      public void visit(IDBFieldDelta delta)
      {
        stopRecursion();
      }
    };

    delta.accept(schemaUpdater);
  }

  /**
   * @since 4.2
   */
  protected void createTable(Connection connection, IDBTable table, IDBTableDelta delta)
  {
    CheckUtil.checkArg(delta.getChangeKind() == ChangeKind.ADD, "Not added: " + delta.getName());

    StringBuilder builder = new StringBuilder();
    builder.append("CREATE TABLE "); //$NON-NLS-1$
    builder.append(delta.getName());
    builder.append(" ("); //$NON-NLS-1$
    appendFieldDefs(builder, table, createFieldDefinitions(table));
    builder.append(")"); //$NON-NLS-1$

    DBUtil.execute(connection, builder);
  }

  /**
   * @since 4.2
   */
  protected void dropTable(Connection connection, IDBTable table, IDBTableDelta delta)
  {
    String sql = getDropTableSQL(table);
    DBUtil.execute(connection, sql);
  }

  /**
   * @since 4.2
   */
  protected void alterTable(Connection connection, IDBTable table, IDBTableDelta delta)
  {
    for (IDBFieldDelta fieldDelta : delta.getFieldDeltas().values())
    {
      ChangeKind changeKind = fieldDelta.getChangeKind();
      String fieldName = fieldDelta.getName();
      String tableName = table.getName();

      switch (changeKind)
      {
      case ADD:
        createField(connection, tableName, table.getField(fieldName));
        break;

      case CHANGE:
        dropField(connection, tableName, fieldName);
        createField(connection, tableName, table.getField(fieldName));
        break;

      case REMOVE:
        dropField(connection, tableName, fieldName);
        break;

      default:
        throw IDBDeltaVisitor.Default.illegalChangeKind(changeKind);
      }
    }
  }

  /**
   * @since 4.6
   */
  protected void createField(Connection connection, String tableName, IDBField field)
  {
    DBUtil.execute(connection, "ALTER TABLE " + tableName + " ADD COLUMN " + field.getName() + " " + createFieldDefinition(field));
  }

  /**
   * @since 4.6
   */
  protected void dropField(Connection connection, String tableName, String fieldName)
  {
    DBUtil.execute(connection, "ALTER TABLE " + tableName + " DROP COLUMN " + fieldName);
  }

  /**
   * @since 4.2
   */
  protected void createIndex(Connection connection, IDBIndex index, IDBIndexDelta delta)
  {
    StringBuilder builder = new StringBuilder();
    if (index.getType() == IDBIndex.Type.PRIMARY_KEY)
    {
      createPrimaryKey(index, builder);
    }
    else
    {
      createIndex(index, builder);
    }

    createIndexFields(index, builder);
    DBUtil.execute(connection, builder);
  }

  /**
   * @since 4.2
   */
  protected void createPrimaryKey(IDBIndex index, StringBuilder builder)
  {
    builder.append("ALTER TABLE "); //$NON-NLS-1$
    builder.append(index.getTable());
    builder.append(" ADD CONSTRAINT "); //$NON-NLS-1$
    builder.append(index);
    builder.append(" PRIMARY KEY"); //$NON-NLS-1$
  }

  /**
   * @since 4.2
   */
  protected void createIndex(IDBIndex index, StringBuilder builder)
  {
    builder.append("CREATE "); //$NON-NLS-1$
    if (index.getType() == IDBIndex.Type.UNIQUE)
    {
      builder.append("UNIQUE "); //$NON-NLS-1$
    }

    builder.append("INDEX "); //$NON-NLS-1$
    builder.append(index);
    builder.append(" ON "); //$NON-NLS-1$
    builder.append(index.getTable());
  }

  /**
   * @since 4.2
   */
  protected void createIndexFields(IDBIndex index, StringBuilder builder)
  {
    builder.append(" ("); //$NON-NLS-1$

    IDBField[] fields = index.getFields();
    for (int i = 0; i < fields.length; i++)
    {
      if (i != 0)
      {
        builder.append(", "); //$NON-NLS-1$
      }

      addIndexField(builder, fields[i]);
    }

    builder.append(")"); //$NON-NLS-1$
  }

  /**
   * @since 4.2
   */
  protected void dropIndex(Connection connection, IDBIndex index, IDBIndexDelta delta)
  {
    StringBuilder builder = new StringBuilder();
    if (index.getType() == IDBIndex.Type.PRIMARY_KEY)
    {
      dropPrimaryKey(index, builder);
    }
    else
    {
      dropIndex(index, builder);
    }

    DBUtil.execute(connection, builder);
  }

  /**
   * @since 4.2
   */
  protected void dropPrimaryKey(IDBIndex index, StringBuilder builder)
  {
    builder.append("ALTER TABLE "); //$NON-NLS-1$
    builder.append(index.getTable());
    builder.append(" DROP CONSTRAINT "); //$NON-NLS-1$
    builder.append(index);
  }

  /**
   * @since 4.2
   */
  protected void dropIndex(IDBIndex index, StringBuilder builder)
  {
  }

  public Set<IDBTable> createTables(Iterable<? extends IDBTable> tables, Connection connection) throws DBException
  {
    Set<IDBTable> createdTables = new HashSet<IDBTable>();

    for (IDBTable table : tables)
    {
      Statement statement = null;

      try
      {
        statement = connection.createStatement();
        if (createTable(table, statement))
        {
          createdTables.add(table);
        }
      }
      catch (SQLException ex)
      {
        throw new DBException(ex);
      }
      finally
      {
        DBUtil.close(statement);
      }
    }

    return createdTables;
  }

  public boolean createTable(IDBTable table, Statement statement) throws DBException
  {
    boolean created = true;

    try
    {
      doCreateTable(table, statement);
    }
    catch (SQLException ex)
    {
      created = false;
      if (TRACER.isEnabled())
      {
        TRACER.trace("-- " + ex.getMessage()); //$NON-NLS-1$
      }
    }

    validateTable(table, statement);
    return created;
  }

  public Collection<IDBTable> dropTables(Iterable<? extends IDBTable> tables, Connection connection) throws DBException
  {
    List<IDBTable> droppedTables = new ArrayList<IDBTable>();

    for (IDBTable table : tables)
    {
      Statement statement = null;

      try
      {
        statement = connection.createStatement();
        if (dropTable(table, statement))
        {
          droppedTables.add(table);
        }
      }
      catch (SQLException ex)
      {
        OM.LOG.error(ex);
      }
      finally
      {
        DBUtil.close(statement);
      }
    }

    return droppedTables;
  }

  public boolean dropTable(IDBTable table, Statement statement)
  {
    try
    {
      String sql = getDropTableSQL(table);
      if (TRACER.isEnabled())
      {
        TRACER.trace(sql);
      }

      statement.execute(sql);
      return true;
    }
    catch (SQLException ex)
    {
      if (TRACER.isEnabled())
      {
        TRACER.trace(ex.getMessage());
      }

      return false;
    }
  }

  protected String getDropTableSQL(IDBTable table)
  {
    return "DROP TABLE " + table; //$NON-NLS-1$
  }

  /**
   * @since 2.0
   */
  public int getMaxTableNameLength()
  {
    // Ansi SQL 92 default value
    return 128;
  }

  /**
   * @since 2.0
   */
  public int getMaxFieldNameLength()
  {
    // Ansi SQL 92 default value
    return 128;
  }

  /**
   * @since 4.2
   */
  public int getFieldLength(DBType type)
  {
    return getDefaultDBLength(type);
  }

  public boolean isTypeIndexable(DBType type)
  {
    switch (type)
    {
    case CLOB:
    case BLOB:
    case LONGVARCHAR:
    case LONGVARBINARY:
    case VARBINARY:
    case BINARY:
      return false;

    default:
      return true;
    }
  }

  @Override
  public String toString()
  {
    return getName() + "-" + getVersion(); //$NON-NLS-1$
  }

  /**
   * @since 4.3
   */
  public String convertString(PreparedStatement preparedStatement, int parameterIndex, String value)
  {
    return value;
  }

  /**
   * @since 4.3
   */
  public String convertString(ResultSet resultSet, int columnIndex, String value)
  {
    return value;
  }

  /**
   * @since 4.3
   */
  public String convertString(ResultSet resultSet, String columnLabel, String value)
  {
    return value;
  }

  /**
   * @since 2.0
   */
  protected void doCreateTable(IDBTable table, Statement statement) throws SQLException
  {
    StringBuilder builder = new StringBuilder();
    builder.append("CREATE TABLE "); //$NON-NLS-1$
    builder.append(table);
    builder.append(" ("); //$NON-NLS-1$
    appendFieldDefs(builder, table, createFieldDefinitions(table));
    String constraints = createConstraints(table);
    if (constraints != null)
    {
      builder.append(", "); //$NON-NLS-1$
      builder.append(constraints);
    }

    builder.append(")"); //$NON-NLS-1$
    String sql = builder.toString();
    if (TRACER.isEnabled())
    {
      TRACER.trace(sql);
    }

    statement.execute(sql);

    IDBIndex[] indices = table.getIndices();
    for (int i = 0; i < indices.length; i++)
    {
      InternalDBIndex index = (InternalDBIndex)indices[i];

      try
      {
        createIndex(index, statement, i);
      }
      catch (SQLException ex)
      {
        if (!index.isOptional())
        {
          throw ex;
        }
      }
      catch (RuntimeException ex)
      {
        if (!index.isOptional())
        {
          throw ex;
        }
      }
    }
  }

  /**
   * @since 2.0
   */
  protected void createIndex(IDBIndex index, Statement statement, int num) throws SQLException
  {
    IDBTable table = index.getTable();
    StringBuilder builder = new StringBuilder();
    builder.append("CREATE "); //$NON-NLS-1$
    if (index.getType() == IDBIndex.Type.UNIQUE || index.getType() == IDBIndex.Type.PRIMARY_KEY)
    {
      builder.append("UNIQUE "); //$NON-NLS-1$
    }

    builder.append("INDEX "); //$NON-NLS-1$
    builder.append(index);
    builder.append(" ON "); //$NON-NLS-1$
    builder.append(table);
    createIndexFields(index, builder);
    String sql = builder.toString();
    if (TRACER.isEnabled())
    {
      TRACER.trace(sql);
    }

    statement.execute(sql);
  }

  protected void addIndexField(StringBuilder builder, IDBField field)
  {
    builder.append(field);
  }

  /**
   * @since 2.0
   */
  protected String createConstraints(IDBTable table)
  {
    return null;
  }

  /**
   * @since 2.0
   */
  protected String createFieldDefinition(IDBField field)
  {
    return getTypeName(field) + (field.isNotNull() ? " NOT NULL" : ""); //$NON-NLS-1$ //$NON-NLS-2$
  }

  // protected String getTypeName(DBType type)
  // {
  // new DBField(null,null)
  // return getTypeName(field);
  // }

  protected String getTypeName(IDBField field)
  {
    DBType type = field.getType();
    switch (type)
    {
    case BOOLEAN:
    case BIT:
    case TINYINT:
    case SMALLINT:
    case INTEGER:
    case BIGINT:
    case FLOAT:
    case REAL:
    case DOUBLE:
    case DATE:
    case TIME:
    case TIMESTAMP:
    case LONGVARCHAR:
    case LONGVARBINARY:
    case BLOB:
    case CLOB:
      return type.toString();

    case CHAR:
    case VARCHAR:
    case BINARY:
    case VARBINARY:
      return type.toString() + field.formatPrecision();

    case NUMERIC:
    case DECIMAL:
      return type.toString() + field.formatPrecisionAndScale();
    }

    throw new IllegalArgumentException("Unknown type: " + type); //$NON-NLS-1$
  }

  public String[] getSQL92ReservedWords()
  {
    return SQL92_RESERVED_WORDS;
  }

  public boolean isReservedWord(String word)
  {
    if (reservedWords == null)
    {
      reservedWords = new HashSet<String>();
      for (String reservedWord : getReservedWords())
      {
        reservedWords.add(reservedWord.toUpperCase());
      }
    }

    word = word.toUpperCase();
    return reservedWords.contains(word);
  }

  /**
   * @since 2.0
   */
  protected void validateTable(IDBTable table, Statement statement) throws DBException
  {
    int maxRows = 1;

    try
    {
      maxRows = statement.getMaxRows();
      statement.setMaxRows(1);

      String sql = null;

      try
      {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT "); //$NON-NLS-1$
        appendFieldNames(builder, table);
        builder.append(" FROM "); //$NON-NLS-1$
        builder.append(table);
        sql = builder.toString();

        if (TRACER.isEnabled())
        {
          TRACER.format("{0}", sql); //$NON-NLS-1$
        }

        ResultSet resultSet = statement.executeQuery(sql);

        try
        {
          ResultSetMetaData metaData = resultSet.getMetaData();
          int columnCount = metaData.getColumnCount();
          if (columnCount != table.getFieldCount())
          {
            throw new DBException("DBTable " + table + " has " + columnCount + " columns instead of " //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                + table.getFieldCount());
          }
        }
        finally
        {
          DBUtil.close(resultSet);
        }
      }
      catch (SQLException ex)
      {
        throw new DBException("Problem with table " + table, ex, sql);
      }
      finally
      {
        if (maxRows != 1)
        {
          statement.setMaxRows(maxRows);
        }
      }
    }
    catch (SQLException ex)
    {
      throw new DBException(ex);
    }
  }

  /**
   * @since 4.2
   */
  protected String[] createFieldDefinitions(IDBTable table)
  {
    IDBField[] fields = table.getFields();
    int fieldCount = fields.length;

    String[] result = new String[fieldCount];
    for (int i = 0; i < fieldCount; i++)
    {
      IDBField field = fields[i];
      result[i] = createFieldDefinition(field);
    }

    return result;
  }

  public void appendFieldNames(Appendable appendable, IDBTable table)
  {
    try
    {
      IDBField[] fields = table.getFields();
      for (int i = 0; i < fields.length; i++)
      {
        IDBField field = fields[i];
        if (i != 0)
        {
          appendable.append(", "); //$NON-NLS-1$
        }

        String fieldName = field.getName();
        appendable.append(fieldName);
      }
    }
    catch (IOException canNotHappen)
    {
    }
  }

  /**
   * @since 4.2
   */
  protected void appendFieldDefs(Appendable appendable, IDBTable table, String[] defs)
  {
    try
    {
      IDBField[] fields = table.getFields();
      for (int i = 0; i < fields.length; i++)
      {
        IDBField field = fields[i];
        if (i != 0)
        {
          appendable.append(", "); //$NON-NLS-1$
        }

        String fieldName = field.getName();
        appendable.append(fieldName);
        appendable.append(" "); //$NON-NLS-1$
        appendable.append(defs[i]);
      }
    }
    catch (IOException canNotHappen)
    {
    }
  }

  /**
   * @since 3.0
   */
  public DBType adaptType(DBType type)
  {
    return type;
  }

  /**
   * @since 4.0
   */
  public boolean isValidFirstChar(char ch)
  {
    return true;
  }

  /**
   * @since 4.0
   */
  public boolean isDuplicateKeyException(SQLException ex)
  {
    String sqlState = ex.getSQLState();
    return "23001".equals(sqlState);
  }

  /**
   * @since 4.2
   */
  public boolean isTableNotFoundException(SQLException ex)
  {
    String sqlState = ex.getSQLState();
    return "42S02".equals(sqlState);
  }

  /**
   * @since 4.2
   */
  public boolean isColumnNotFoundException(SQLException ex)
  {
    String sqlState = ex.getSQLState();
    return "42S22".equals(sqlState);
  }

  /**
   * @since 4.2
   */
  public String sqlRenameField(IDBField field, String oldName)
  {
    return "ALTER TABLE " + field.getTable() + " RENAME COLUMN " + oldName + " TO " + field;
  }

  /**
   * @since 4.2
   */
  public String sqlModifyField(IDBField field)
  {
    String tableName = field.getTable().getName();
    String fieldName = field.getName();

    String definition = createFieldDefinition(field);
    return sqlModifyField(tableName, fieldName, definition);
  }

  /**
   * @since 4.2
   */
  protected String sqlModifyField(String tableName, String fieldName, String definition)
  {
    return "ALTER TABLE " + tableName + " ALTER COLUMN " + fieldName + " " + definition;
  }

  /**
   * @since 4.2
   */
  public String format(PreparedStatement stmt)
  {
    return stmt.toString();
  }

  /**
   * @since 4.2
   */
  public String format(ResultSet resultSet)
  {
    try
    {
      StringBuilder builder = new StringBuilder();
      ResultSetMetaData metaData = resultSet.getMetaData();
      int columnCount = metaData.getColumnCount();
      for (int i = 0; i < columnCount; i++)
      {
        if (i != 0)
        {
          builder.append(", ");
        }

        builder.append(metaData.getColumnName(i + 1).toLowerCase());
        builder.append("=");
        builder.append(resultSet.getObject(i + 1));
      }

      return builder.toString();
    }
    catch (SQLException ex)
    {
      throw new DBException(ex);
    }
  }

  /**
   * @since 4.2
   */
  public static int getDefaultDBLength(DBType type)
  {
    return type == DBType.VARCHAR ? 32672 : IDBField.DEFAULT;
  }

  /**
   * @since 4.2
   * @author Eike Stepper
   */
  protected static final class FieldInfo implements Comparable<FieldInfo>
  {
    public String name;

    public int position;

    public int compareTo(FieldInfo o)
    {
      return position - o.position;
    }
  }
}

/*
 * Copyright (c) 2009-2013, 2016 Eike Stepper (Loehne, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Stefan Winkler - initial API and implementation
 *    Stefan Winkler - bug 249610: [DB] Support external references (Implementation)
 *    Eike Stepper - maintenance
 */
package org.eclipse.emf.cdo.server.internal.db;

import org.eclipse.emf.cdo.common.id.CDOIDExternal;
import org.eclipse.emf.cdo.common.id.CDOIDUtil;
import org.eclipse.emf.cdo.common.protocol.CDODataInput;
import org.eclipse.emf.cdo.common.protocol.CDODataOutput;
import org.eclipse.emf.cdo.server.IStoreAccessor;
import org.eclipse.emf.cdo.server.StoreThreadLocal;
import org.eclipse.emf.cdo.server.db.IDBStore;
import org.eclipse.emf.cdo.server.db.IDBStoreAccessor;
import org.eclipse.emf.cdo.server.db.IIDHandler;
import org.eclipse.emf.cdo.server.internal.db.bundle.OM;

import org.eclipse.net4j.db.DBException;
import org.eclipse.net4j.db.DBType;
import org.eclipse.net4j.db.DBUtil;
import org.eclipse.net4j.db.IDBDatabase;
import org.eclipse.net4j.db.IDBDatabase.RunnableWithSchema;
import org.eclipse.net4j.db.IDBPreparedStatement;
import org.eclipse.net4j.db.IDBPreparedStatement.ReuseProbability;
import org.eclipse.net4j.db.ddl.IDBIndex;
import org.eclipse.net4j.db.ddl.IDBSchema;
import org.eclipse.net4j.db.ddl.IDBTable;
import org.eclipse.net4j.util.ReflectUtil.ExcludeFromDump;
import org.eclipse.net4j.util.lifecycle.Lifecycle;
import org.eclipse.net4j.util.om.monitor.OMMonitor;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Stefan Winkler
 */
public class ExternalReferenceManager extends Lifecycle
{
  private static final String EXTERNAL_REFS = "cdo_external_refs";

  private static final String EXTERNAL_REFS_ID = "ID";

  private static final String EXTERNAL_REFS_URI = "URI";

  private static final String EXTERNAL_REFS_COMMITTIME = "COMMITTIME";

  private static final int NULL = 0;

  private IDBTable table;

  private final IIDHandler idHandler;

  private AtomicLong lastMappedID = new AtomicLong(NULL);

  @ExcludeFromDump
  private transient String sqlSelectByLongID;

  @ExcludeFromDump
  private transient String sqlSelectByURI;

  @ExcludeFromDump
  private transient String sqlInsert;

  public ExternalReferenceManager(IIDHandler idHandler)
  {
    this.idHandler = idHandler;
  }

  public IIDHandler getIDHandler()
  {
    return idHandler;
  }

  public long mapExternalReference(CDOIDExternal id, long commitTime)
  {
    IDBStoreAccessor accessor = getAccessor();
    return mapURI(accessor, id.getURI(), commitTime);
  }

  public CDOIDExternal unmapExternalReference(long mappedId)
  {
    IDBStoreAccessor accessor = getAccessor();
    return CDOIDUtil.createExternal(unmapURI(accessor, mappedId));
  }

  public long mapURI(IDBStoreAccessor accessor, String uri, long commitTime)
  {
    long result = lookupByURI(accessor, uri);
    if (result < NULL)
    {
      // mapping found
      return result;
    }

    return insertNew(accessor, uri, commitTime);
  }

  public String unmapURI(IDBStoreAccessor accessor, long mappedId)
  {
    IDBPreparedStatement stmt = accessor.getDBConnection().prepareStatement(sqlSelectByLongID, ReuseProbability.HIGH);
    ResultSet resultSet = null;

    try
    {
      stmt.setLong(1, mappedId);

      resultSet = stmt.executeQuery();
      if (!resultSet.next())
      {
        OM.LOG.error("External ID " + mappedId + " not found. Database inconsistent!");
        throw new IllegalStateException("External ID " + mappedId + " not found. Database inconsistent!");
      }

      return resultSet.getString(1);
    }
    catch (SQLException e)
    {
      throw new DBException(e);
    }
    finally
    {
      DBUtil.close(resultSet);
      DBUtil.close(stmt);
    }
  }

  public long lookupByURI(IDBStoreAccessor accessor, String uri)
  {
    IDBPreparedStatement stmt = accessor.getDBConnection().prepareStatement(sqlSelectByURI, ReuseProbability.HIGH);
    ResultSet resultSet = null;

    try
    {
      stmt.setString(1, uri);

      resultSet = stmt.executeQuery();
      if (resultSet.next())
      {
        return resultSet.getLong(1);
      }

      // Not found ...
      return NULL;
    }
    catch (SQLException e)
    {
      throw new DBException(e);
    }
    finally
    {
      DBUtil.close(resultSet);
      DBUtil.close(stmt);
    }
  }

  public void rawExport(Connection connection, CDODataOutput out, long fromCommitTime, long toCommitTime) throws IOException
  {
    String where = " WHERE " + EXTERNAL_REFS_COMMITTIME + " BETWEEN " + fromCommitTime + " AND " + toCommitTime;
    DBUtil.serializeTable(out, connection, table, null, where);
  }

  public void rawImport(Connection connection, CDODataInput in, long fromCommitTime, long toCommitTime, OMMonitor monitor) throws IOException
  {
    DBUtil.deserializeTable(in, connection, table, monitor);
  }

  @Override
  protected void doActivate() throws Exception
  {
    super.doActivate();

    final IDBStore store = idHandler.getStore();
    IDBDatabase database = store.getDatabase();

    table = database.getSchema().getTable(EXTERNAL_REFS);
    if (table == null)
    {
      database.updateSchema(new RunnableWithSchema()
      {
        public void run(IDBSchema schema)
        {
          table = schema.addTable(EXTERNAL_REFS);
          table.addField(EXTERNAL_REFS_ID, idHandler.getDBType(), store.getIDColumnLength(), true);
          table.addField(EXTERNAL_REFS_URI, DBType.VARCHAR, 1024);
          table.addField(EXTERNAL_REFS_COMMITTIME, DBType.BIGINT);
          table.addIndex(IDBIndex.Type.PRIMARY_KEY, EXTERNAL_REFS_ID);
          table.addIndex(IDBIndex.Type.NON_UNIQUE, EXTERNAL_REFS_URI);
        }
      });
    }
    else
    {
      String sql = "SELECT MIN(" + EXTERNAL_REFS_ID + ") FROM " + table;

      IDBStoreAccessor writer = store.getWriter(null);
      IDBPreparedStatement stmt = writer.getDBConnection().prepareStatement(sql, ReuseProbability.LOW);
      ResultSet resultSet = null;

      try
      {
        resultSet = stmt.executeQuery();
        if (resultSet.next())
        {
          lastMappedID.set(resultSet.getLong(1));
        }

        // else: resultSet is empty => table is empty
        // and lastMappedId stays 0 - as initialized.
      }
      catch (SQLException ex)
      {
        writer.getDBConnection().rollback();
        throw new DBException(ex);
      }
      finally
      {
        DBUtil.close(resultSet);
        DBUtil.close(stmt);
        writer.release();
      }
    }

    sqlInsert = "INSERT INTO " + table + "(" + EXTERNAL_REFS_ID + "," + EXTERNAL_REFS_URI + "," + EXTERNAL_REFS_COMMITTIME + ") VALUES (?, ?, ?)";
    sqlSelectByURI = "SELECT " + EXTERNAL_REFS_ID + " FROM " + table + " WHERE " + EXTERNAL_REFS_URI + "=?";
    sqlSelectByLongID = "SELECT " + EXTERNAL_REFS_URI + " FROM " + table + " WHERE " + EXTERNAL_REFS_ID + "=?";
  }

  private long insertNew(IDBStoreAccessor accessor, String uri, long commitTime)
  {
    long newMappedID = lastMappedID.decrementAndGet();
    IDBPreparedStatement stmt = accessor.getDBConnection().prepareStatement(sqlInsert, ReuseProbability.MEDIUM);

    try
    {
      stmt.setLong(1, newMappedID);
      stmt.setString(2, uri);
      stmt.setLong(3, commitTime);

      DBUtil.update(stmt, true);
      return newMappedID;
    }
    catch (SQLException e)
    {
      throw new DBException(e);
    }
    finally
    {
      DBUtil.close(stmt);
    }
  }

  private static IDBStoreAccessor getAccessor()
  {
    IStoreAccessor accessor = StoreThreadLocal.getAccessor();
    if (accessor == null)
    {
      throw new IllegalStateException("Can only be called from within a valid IDBStoreAccessor context");
    }

    return (IDBStoreAccessor)accessor;
  }
}

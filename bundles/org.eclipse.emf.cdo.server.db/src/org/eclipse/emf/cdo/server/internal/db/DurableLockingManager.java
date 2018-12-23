/*
 * Copyright (c) 2011-2013, 2015, 2016 Eike Stepper (Loehne, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.emf.cdo.server.internal.db;

import org.eclipse.emf.cdo.common.branch.CDOBranchPoint;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.id.CDOIDUtil;
import org.eclipse.emf.cdo.common.lock.CDOLockUtil;
import org.eclipse.emf.cdo.common.lock.IDurableLockingManager.LockArea;
import org.eclipse.emf.cdo.common.lock.IDurableLockingManager.LockArea.Handler;
import org.eclipse.emf.cdo.common.lock.IDurableLockingManager.LockAreaAlreadyExistsException;
import org.eclipse.emf.cdo.common.lock.IDurableLockingManager.LockAreaNotFoundException;
import org.eclipse.emf.cdo.common.lock.IDurableLockingManager.LockGrade;
import org.eclipse.emf.cdo.common.protocol.CDODataInput;
import org.eclipse.emf.cdo.common.protocol.CDODataOutput;
import org.eclipse.emf.cdo.server.db.IIDHandler;
import org.eclipse.emf.cdo.spi.common.branch.InternalCDOBranchManager;
import org.eclipse.emf.cdo.spi.server.InternalLockManager;

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
import org.eclipse.net4j.util.concurrent.IRWLockManager.LockType;
import org.eclipse.net4j.util.lifecycle.Lifecycle;
import org.eclipse.net4j.util.om.monitor.OMMonitor;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Eike Stepper
 */
public class DurableLockingManager extends Lifecycle
{
  private static final String LOCK_AREAS = "CDO_LOCK_AREAS";

  private static final String LOCK_AREAS_ID = "ID";

  private static final String LOCK_AREAS_USER_ID = "USER_ID";

  private static final String LOCK_AREAS_VIEW_BRANCH = "VIEW_BRANCH";

  private static final String LOCK_AREAS_VIEW_TIME = "VIEW_TIME";

  private static final String LOCK_AREAS_READ_ONLY = "READ_ONLY";

  private static final String LOCKS = "CDO_LOCKS";

  private static final String LOCKS_AREA_ID = "AREA_ID";

  private static final String LOCKS_OBJECT_ID = "OBJECT_ID";

  private static final String LOCKS_LOCK_GRADE = "LOCK_GRADE";

  private DBStore store;

  private InternalCDOBranchManager branchManager;

  private IIDHandler idHandler;

  private IDBTable lockAreasTable;

  private IDBTable locksTable;

  private String sqlInsertLockArea;

  private String sqlSelectLockArea;

  private String sqlSelectAllLockAreas;

  private String sqlSelectLockAreas;

  private String sqlDeleteLockArea;

  private String sqlDeleteLockAreas;

  private String sqlSelectLocks;

  private String sqlSelectLock;

  private String sqlInsertLock;

  private String sqlUpdateLock;

  private String sqlDeleteLock;

  private String sqlDeleteLocks;

  public DurableLockingManager(DBStore store)
  {
    this.store = store;
  }

  public synchronized LockArea createLockArea(DBStoreAccessor accessor, String durableLockingID, String userID, CDOBranchPoint branchPoint, boolean readOnly,
      Map<CDOID, LockGrade> locks)
  {
    if (durableLockingID == null)
    {
      durableLockingID = getNextDurableLockingID(accessor);
    }
    else
    {
      // If the caller is specifying the ID, make sure there is no area with this ID yet
      //
      try
      {
        getLockArea(accessor, durableLockingID);
        throw new LockAreaAlreadyExistsException(durableLockingID);
      }
      catch (LockAreaNotFoundException good)
      {
      }
    }

    IDBPreparedStatement stmt = accessor.getDBConnection().prepareStatement(sqlInsertLockArea, ReuseProbability.LOW);

    try
    {
      stmt.setString(1, durableLockingID);
      stmt.setString(2, userID);
      stmt.setInt(3, branchPoint.getBranch().getID());
      stmt.setLong(4, branchPoint.getTimeStamp());
      stmt.setBoolean(5, readOnly);

      DBUtil.update(stmt, true);
    }
    catch (SQLException e)
    {
      throw new DBException(e);
    }
    finally
    {
      DBUtil.close(stmt);
    }

    if (!locks.isEmpty())
    {
      insertLocks(accessor, durableLockingID, locks);
    }

    commit(accessor);

    return CDOLockUtil.createLockArea(durableLockingID, userID, branchPoint, readOnly, locks);
  }

  private void insertLocks(DBStoreAccessor accessor, String durableLockingID, Map<CDOID, LockGrade> locks)
  {
    IDBPreparedStatement stmt = accessor.getDBConnection().prepareStatement(sqlInsertLock, ReuseProbability.MEDIUM);

    try
    {
      stmt.setString(1, durableLockingID);

      for (Entry<CDOID, LockGrade> entry : locks.entrySet())
      {
        CDOID id = entry.getKey();
        int grade = entry.getValue().getValue();

        idHandler.setCDOID(stmt, 2, id);
        stmt.setInt(3, grade);

        DBUtil.update(stmt, true);
      }
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

  public LockArea getLockArea(DBStoreAccessor accessor, String durableLockingID) throws LockAreaNotFoundException
  {
    IDBPreparedStatement stmt = accessor.getDBConnection().prepareStatement(sqlSelectLockArea, ReuseProbability.MEDIUM);
    ResultSet resultSet = null;

    try
    {
      stmt.setString(1, durableLockingID);

      resultSet = stmt.executeQuery();
      if (!resultSet.next())
      {
        throw new LockAreaNotFoundException(durableLockingID);
      }

      String userID = resultSet.getString(1);
      int branchID = resultSet.getInt(2);
      long timeStamp = resultSet.getLong(3);
      boolean readOnly = resultSet.getBoolean(4);

      return makeLockArea(accessor, durableLockingID, userID, branchID, timeStamp, readOnly);
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

  public void getLockAreas(DBStoreAccessor accessor, String userIDPrefix, Handler handler)
  {
    IDBPreparedStatement stmt = null;
    ResultSet resultSet = null;

    try
    {
      if (userIDPrefix.length() == 0)
      {
        stmt = accessor.getDBConnection().prepareStatement(sqlSelectAllLockAreas, ReuseProbability.MEDIUM);
      }
      else
      {
        stmt = accessor.getDBConnection().prepareStatement(sqlSelectLockAreas, ReuseProbability.MEDIUM);
        stmt.setString(1, userIDPrefix + "%");
      }

      resultSet = stmt.executeQuery();
      while (resultSet.next())
      {
        String durableLockingID = resultSet.getString(1);
        String userID = resultSet.getString(2);
        int branchID = resultSet.getInt(3);
        long timeStamp = resultSet.getLong(4);
        boolean readOnly = resultSet.getBoolean(5);

        LockArea area = makeLockArea(accessor, durableLockingID, userID, branchID, timeStamp, readOnly);
        if (!handler.handleLockArea(area))
        {
          break;
        }
      }
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

  public void deleteLockArea(DBStoreAccessor accessor, String durableLockingID)
  {
    unlockWithoutCommit(accessor, durableLockingID);

    IDBPreparedStatement stmt = accessor.getDBConnection().prepareStatement(sqlDeleteLockArea, ReuseProbability.LOW);

    try
    {
      stmt.setString(1, durableLockingID);
      DBUtil.update(stmt, true);
    }
    catch (SQLException e)
    {
      throw new DBException(e);
    }
    finally
    {
      DBUtil.close(stmt);
    }

    commit(accessor);
  }

  public void updateLockArea(DBStoreAccessor accessor, LockArea area)
  {
    String areaID = area.getDurableLockingID();
    unlockWithoutCommit(accessor, areaID);
    insertLocks(accessor, areaID, area.getLocks());
    commit(accessor);
  }

  public void lock(DBStoreAccessor accessor, String durableLockingID, LockType type, Collection<? extends Object> objectsToLock)
  {
    changeLocks(accessor, durableLockingID, type, objectsToLock, true);
  }

  public void unlock(DBStoreAccessor accessor, String durableLockingID, LockType type, Collection<? extends Object> objectsToUnlock)
  {
    changeLocks(accessor, durableLockingID, type, objectsToUnlock, false);
  }

  public void unlock(DBStoreAccessor accessor, String durableLockingID)
  {
    unlockWithoutCommit(accessor, durableLockingID);
    commit(accessor);
  }

  private void unlockWithoutCommit(DBStoreAccessor accessor, String durableLockingID)
  {
    IDBPreparedStatement stmt = accessor.getDBConnection().prepareStatement(sqlDeleteLocks, ReuseProbability.MEDIUM);

    try
    {
      stmt.setString(1, durableLockingID);
      DBUtil.update(stmt, false);
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

  @Override
  protected void doActivate() throws Exception
  {
    super.doActivate();

    branchManager = store.getRepository().getBranchManager();
    idHandler = store.getIDHandler();
    IDBDatabase database = store.getDatabase();

    // Lock areas
    lockAreasTable = database.getSchema().getTable(LOCK_AREAS);
    if (lockAreasTable == null)
    {
      database.updateSchema(new RunnableWithSchema()
      {
        public void run(IDBSchema schema)
        {
          lockAreasTable = schema.addTable(LOCK_AREAS);
          lockAreasTable.addField(LOCK_AREAS_ID, DBType.VARCHAR, true);
          lockAreasTable.addField(LOCK_AREAS_USER_ID, DBType.VARCHAR);
          lockAreasTable.addField(LOCK_AREAS_VIEW_BRANCH, DBType.INTEGER);
          lockAreasTable.addField(LOCK_AREAS_VIEW_TIME, DBType.BIGINT);
          lockAreasTable.addField(LOCK_AREAS_READ_ONLY, DBType.BOOLEAN);
          lockAreasTable.addIndex(IDBIndex.Type.PRIMARY_KEY, LOCK_AREAS_ID);
          lockAreasTable.addIndex(IDBIndex.Type.NON_UNIQUE, LOCK_AREAS_USER_ID);
        }
      });
    }

    sqlInsertLockArea = "INSERT INTO " + LOCK_AREAS + "(" + LOCK_AREAS_ID + "," + LOCK_AREAS_USER_ID + "," + LOCK_AREAS_VIEW_BRANCH + "," + LOCK_AREAS_VIEW_TIME
        + "," + LOCK_AREAS_READ_ONLY + ") VALUES (?, ?, ?, ?, ?)";
    sqlSelectLockArea = "SELECT " + LOCK_AREAS_USER_ID + "," + LOCK_AREAS_VIEW_BRANCH + "," + LOCK_AREAS_VIEW_TIME + "," + LOCK_AREAS_READ_ONLY + " FROM "
        + LOCK_AREAS + " WHERE " + LOCK_AREAS_ID + "=?";
    sqlSelectAllLockAreas = "SELECT " + LOCK_AREAS_ID + "," + LOCK_AREAS_USER_ID + "," + LOCK_AREAS_VIEW_BRANCH + "," + LOCK_AREAS_VIEW_TIME + ","
        + LOCK_AREAS_READ_ONLY + " FROM " + LOCK_AREAS;
    sqlSelectLockAreas = sqlSelectAllLockAreas + " WHERE " + LOCK_AREAS_USER_ID + " LIKE ?";
    sqlDeleteLockArea = "DELETE FROM " + LOCK_AREAS + " WHERE " + LOCK_AREAS_ID + "=?";

    /** BEGIN SPEMATE PATCH */
    sqlDeleteLockAreas = "DELETE FROM " + LOCK_AREAS;// + " WHERE EXISTS (SELECT * FROM " + LOCKS + " WHERE " + LOCKS + "." + LOCKS_AREA_ID + "=" + LOCK_AREAS
    //+ "." + LOCK_AREAS_ID + ")";
    /** END SPECMATE PATCH */
    
    // Locks
    locksTable = database.getSchema().getTable(LOCKS);
    if (locksTable == null)
    {
      database.updateSchema(new RunnableWithSchema()
      {
        public void run(IDBSchema schema)
        {
          locksTable = schema.addTable(LOCKS);
          locksTable.addField(LOCKS_AREA_ID, DBType.VARCHAR, true);
          locksTable.addField(LOCKS_OBJECT_ID, idHandler.getDBType(), store.getIDColumnLength(), true);
          locksTable.addField(LOCKS_LOCK_GRADE, DBType.INTEGER);
          locksTable.addIndex(IDBIndex.Type.PRIMARY_KEY, LOCKS_AREA_ID, LOCKS_OBJECT_ID);
          locksTable.addIndex(IDBIndex.Type.NON_UNIQUE, LOCKS_AREA_ID);
        }
      });
    }

    sqlSelectLocks = "SELECT " + LOCKS_OBJECT_ID + "," + LOCKS_LOCK_GRADE + " FROM " + LOCKS + " WHERE " + LOCKS_AREA_ID + "=?";
    sqlSelectLock = "SELECT " + LOCKS_LOCK_GRADE + " FROM " + LOCKS + " WHERE " + LOCKS_AREA_ID + "=? AND " + LOCKS_OBJECT_ID + "=?";
    sqlInsertLock = "INSERT INTO " + LOCKS + "(" + LOCKS_AREA_ID + "," + LOCKS_OBJECT_ID + "," + LOCKS_LOCK_GRADE + ") VALUES (?, ?, ?)";
    sqlUpdateLock = "UPDATE " + LOCKS + " SET " + LOCKS_LOCK_GRADE + "=? " + " WHERE " + LOCKS_AREA_ID + "=? AND " + LOCKS_OBJECT_ID + "=?";
    sqlDeleteLock = "DELETE FROM " + LOCKS + " WHERE " + LOCKS_AREA_ID + "=? AND " + LOCKS_OBJECT_ID + "=?";
    sqlDeleteLocks = "DELETE FROM " + LOCKS + " WHERE " + LOCKS_AREA_ID + "=?";
  }

  private String getNextDurableLockingID(DBStoreAccessor accessor)
  {
    for (;;)
    {
      String durableLockingID = CDOLockUtil.createDurableLockingID();

      try
      {
        getLockArea(accessor, durableLockingID); // Check uniqueness
        // Not unique; try once more...
      }
      catch (LockAreaNotFoundException ex)
      {
        return durableLockingID;
      }
    }
  }

  private LockArea makeLockArea(DBStoreAccessor accessor, String durableLockingID, String userID, int branchID, long timeStamp, boolean readOnly)
  {
    CDOBranchPoint branchPoint = branchManager.getBranch(branchID).getPoint(timeStamp);
    Map<CDOID, LockGrade> lockMap = getLockMap(accessor, durableLockingID);
    return CDOLockUtil.createLockArea(durableLockingID, userID, branchPoint, readOnly, lockMap);
  }

  private Map<CDOID, LockGrade> getLockMap(DBStoreAccessor accessor, String durableLockingID)
  {
    IDBPreparedStatement stmt = accessor.getDBConnection().prepareStatement(sqlSelectLocks, ReuseProbability.MEDIUM);
    ResultSet resultSet = null;

    try
    {
      stmt.setString(1, durableLockingID);
      resultSet = stmt.executeQuery();

      Map<CDOID, LockGrade> lockMap = CDOIDUtil.createMap();
      while (resultSet.next())
      {
        CDOID id = idHandler.getCDOID(resultSet, 1);
        int grade = resultSet.getInt(2);

        lockMap.put(id, LockGrade.get(grade));
      }

      return lockMap;
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

  private void changeLocks(DBStoreAccessor accessor, String durableLockingID, LockType type, Collection<? extends Object> keys, boolean on)
  {
    if (keys.isEmpty())
    {
      return;
    }

    String sql = on ? sqlInsertLock : sqlDeleteLock;

    IDBPreparedStatement stmtSelect = accessor.getDBConnection().prepareStatement(sqlSelectLock, ReuseProbability.MEDIUM);
    IDBPreparedStatement stmtInsertOrDelete = accessor.getDBConnection().prepareStatement(sql, ReuseProbability.MEDIUM);
    IDBPreparedStatement stmtUpdate = accessor.getDBConnection().prepareStatement(sqlUpdateLock, ReuseProbability.MEDIUM);
    ResultSet resultSet = null;

    try
    {
      stmtSelect.setString(1, durableLockingID);
      stmtInsertOrDelete.setString(1, durableLockingID);
      stmtUpdate.setString(2, durableLockingID);

      InternalLockManager lockManager = accessor.getStore().getRepository().getLockingManager();
      for (Object key : keys)
      {
        CDOID id = lockManager.getLockKeyID(key);
        idHandler.setCDOID(stmtSelect, 2, id);
        resultSet = stmtSelect.executeQuery();

        LockGrade oldGrade = LockGrade.NONE;
        if (resultSet.next())
        {
          oldGrade = LockGrade.get(resultSet.getInt(1));
        }

        LockGrade newGrade = oldGrade.getUpdated(type, on);
        if (on && oldGrade == LockGrade.NONE)
        {
          idHandler.setCDOID(stmtInsertOrDelete, 2, id);
          stmtInsertOrDelete.setInt(3, newGrade.getValue());
          DBUtil.update(stmtInsertOrDelete, true);
        }
        else if (!on && newGrade == LockGrade.NONE)
        {
          idHandler.setCDOID(stmtInsertOrDelete, 2, id);
          DBUtil.update(stmtInsertOrDelete, true);
        }
        else
        {
          stmtUpdate.setInt(1, newGrade.getValue());
          idHandler.setCDOID(stmtUpdate, 3, id);
          DBUtil.update(stmtUpdate, true);
        }
      }

      accessor.getDBConnection().commit();
    }
    catch (SQLException e)
    {
      throw new DBException(e);
    }
    finally
    {
      DBUtil.close(resultSet);
      DBUtil.close(stmtUpdate);
      DBUtil.close(stmtInsertOrDelete);
      DBUtil.close(stmtSelect);
    }
  }

  public void rawExport(Connection connection, CDODataOutput out, long fromCommitTime, long toCommitTime) throws IOException
  {
    DBUtil.serializeTable(out, connection, lockAreasTable, null, null);
    DBUtil.serializeTable(out, connection, locksTable, null, null);
  }

  public void rawImport(Connection connection, CDODataInput in, long fromCommitTime, long toCommitTime, OMMonitor monitor) throws IOException
  {
    monitor.begin(4);

    try
    {
      // Delete all non-empty lock areas
      DBUtil.update(connection, sqlDeleteLockAreas);
      monitor.worked();

      DBUtil.deserializeTable(in, connection, lockAreasTable, monitor.fork());

      DBUtil.clearTable(connection, locksTable);
      monitor.worked();

      DBUtil.deserializeTable(in, connection, locksTable, monitor.fork());
    }
    finally
    {
      monitor.done();
    }
  }

  private static void commit(DBStoreAccessor accessor)
  {
    try
    {
      accessor.getDBConnection().commit();
    }
    catch (SQLException ex)
    {
      throw new DBException(ex);
    }
  }
}

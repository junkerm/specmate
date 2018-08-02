/*
 * Copyright (c) 2010-2013, 2016 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 *    Stefan Winkler - major refactoring
 */
package org.eclipse.emf.cdo.server.internal.db.mapping.horizontal;

import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.server.db.IIDHandler;
import org.eclipse.emf.cdo.server.db.mapping.IClassMapping;
import org.eclipse.emf.cdo.server.db.mapping.IListMapping;

import org.eclipse.net4j.db.DBException;
import org.eclipse.net4j.db.DBUtil;
import org.eclipse.net4j.db.IDBConnection;
import org.eclipse.net4j.db.IDBPreparedStatement;
import org.eclipse.net4j.db.IDBPreparedStatement.ReuseProbability;
import org.eclipse.net4j.db.ddl.IDBTable;
import org.eclipse.net4j.util.om.monitor.OMMonitor;
import org.eclipse.net4j.util.om.monitor.OMMonitor.Async;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Eike Stepper
 * @since 2.0
 */
public class HorizontalBranchingMappingStrategy extends AbstractHorizontalMappingStrategy
{
  public HorizontalBranchingMappingStrategy()
  {
  }

  public boolean hasAuditSupport()
  {
    return true;
  }

  public boolean hasBranchingSupport()
  {
    return true;
  }

  public boolean hasDeltaSupport()
  {
    return false;
  }

  @Override
  protected IClassMapping doCreateClassMapping(EClass eClass)
  {
    return new HorizontalBranchingClassMapping(this, eClass);
  }

  @Override
  public IListMapping doCreateListMapping(EClass containingClass, EStructuralFeature feature)
  {
    return new BranchingListTableMapping(this, containingClass, feature);
  }

  @Override
  public IListMapping doCreateFeatureMapMapping(EClass containingClass, EStructuralFeature feature)
  {
    return new BranchingFeatureMapTableMapping(this, containingClass, feature);
  }

  @Override
  public String getListJoin(String attrTable, String listTable)
  {
    String join = getListJoinBasic(attrTable, listTable);
    return modifyListJoin(attrTable, listTable, join, false);
  }

  @Override
  protected String getListJoinForRawExport(String attrTable, String listTable)
  {
    String join = getListJoinBasic(attrTable, listTable);
    return modifyListJoin(attrTable, listTable, join, true);
  }

  protected String getListJoinBasic(String attrTable, String listTable)
  {
    return super.getListJoin(attrTable, listTable);
  }

  protected String modifyListJoin(String attrTable, String listTable, String join, boolean forRawExport)
  {
    join += " AND " + attrTable + "." + ATTRIBUTES_VERSION;
    join += "=" + listTable + "." + LIST_REVISION_VERSION;
    join += " AND " + attrTable + "." + ATTRIBUTES_BRANCH;
    join += "=" + listTable + "." + LIST_REVISION_BRANCH;
    return join;
  }

  @Override
  protected void rawImportReviseOldRevisions(IDBConnection connection, IDBTable table, OMMonitor monitor)
  {
    String sqlUpdate = "UPDATE " + table + " SET " + ATTRIBUTES_REVISED + "=? WHERE " + ATTRIBUTES_ID + "=? AND " + ATTRIBUTES_BRANCH + "=? AND "
        + ATTRIBUTES_VERSION + "=?";

    String sqlQuery = "SELECT cdo1." + ATTRIBUTES_ID + ", cdo1." + ATTRIBUTES_BRANCH + ", cdo1." + ATTRIBUTES_VERSION + ", cdo2." + ATTRIBUTES_CREATED
        + " FROM " + table + " cdo1, " + table + " cdo2 WHERE cdo1." + ATTRIBUTES_ID + "=cdo2." + ATTRIBUTES_ID + " AND cdo1." + ATTRIBUTES_BRANCH + "=cdo2."
        + ATTRIBUTES_BRANCH + " AND (cdo1." + ATTRIBUTES_VERSION + "=cdo2." + ATTRIBUTES_VERSION + "-1 OR (cdo1." + ATTRIBUTES_VERSION + "+cdo2."
        + ATTRIBUTES_VERSION + "=-1 AND cdo1." + ATTRIBUTES_VERSION + ">cdo2." + ATTRIBUTES_VERSION + ")) AND cdo1." + ATTRIBUTES_REVISED + "=0";

    IIDHandler idHandler = getStore().getIDHandler();
    IDBPreparedStatement stmtUpdate = connection.prepareStatement(sqlUpdate, ReuseProbability.MEDIUM);
    IDBPreparedStatement stmtQuery = connection.prepareStatement(sqlQuery, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY,
        ReuseProbability.MEDIUM);
    ResultSet resultSet = null;

    try
    {
      resultSet = stmtQuery.executeQuery();
      int size = DBUtil.getRowCount(resultSet);
      if (size == 0)
      {
        return;
      }

      monitor.begin(2 * size);
      while (resultSet.next())
      {
        CDOID id = idHandler.getCDOID(resultSet, 1);
        int branch = resultSet.getInt(2);
        int version = resultSet.getInt(3);
        long revised = resultSet.getLong(4) - 1L;

        stmtUpdate.setLong(1, revised);
        idHandler.setCDOID(stmtUpdate, 2, id);
        stmtUpdate.setInt(3, branch);
        stmtUpdate.setInt(4, version);
        stmtUpdate.addBatch();
        monitor.worked();
      }

      Async async = monitor.forkAsync(size);

      try
      {
        stmtUpdate.executeBatch();
      }
      finally
      {
        async.stop();
      }
    }
    catch (SQLException ex)
    {
      throw new DBException(ex);
    }
    finally
    {
      DBUtil.close(resultSet);
      DBUtil.close(stmtQuery);
      DBUtil.close(stmtUpdate);
      monitor.done();
    }
  }

  @Override
  protected void rawImportUnreviseNewRevisions(IDBConnection connection, IDBTable table, long fromCommitTime, long toCommitTime, OMMonitor monitor)
  {
    String sql = "UPDATE " + table + " SET " + ATTRIBUTES_REVISED + "=0 WHERE " + ATTRIBUTES_BRANCH + ">=0 AND " + ATTRIBUTES_CREATED + "<=" + toCommitTime
        + " AND " + ATTRIBUTES_REVISED + ">" + toCommitTime + " AND " + ATTRIBUTES_VERSION + ">0";

    IDBPreparedStatement stmt = connection.prepareStatement(sql, ReuseProbability.MEDIUM);

    try
    {
      monitor.begin();
      Async async = monitor.forkAsync();

      try
      {
        stmt.executeUpdate();
      }
      finally
      {
        async.stop();
      }
    }
    catch (SQLException ex)
    {
      throw new DBException(ex);
    }
    finally
    {
      DBUtil.close(stmt);
      monitor.done();
    }
  }
}

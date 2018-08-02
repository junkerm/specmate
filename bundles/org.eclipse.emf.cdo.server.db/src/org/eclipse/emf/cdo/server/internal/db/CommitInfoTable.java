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
package org.eclipse.emf.cdo.server.internal.db;

import org.eclipse.emf.cdo.common.CDOCommonRepository.CommitInfoStorage;
import org.eclipse.emf.cdo.common.branch.CDOBranch;
import org.eclipse.emf.cdo.common.branch.CDOBranchPoint;
import org.eclipse.emf.cdo.common.commit.CDOCommitInfo;
import org.eclipse.emf.cdo.common.commit.CDOCommitInfoHandler;
import org.eclipse.emf.cdo.common.protocol.CDODataInput;
import org.eclipse.emf.cdo.common.protocol.CDODataOutput;
import org.eclipse.emf.cdo.server.db.IDBStoreAccessor;
import org.eclipse.emf.cdo.spi.common.branch.InternalCDOBranch;
import org.eclipse.emf.cdo.spi.common.branch.InternalCDOBranchManager;
import org.eclipse.emf.cdo.spi.common.commit.CDOCommitInfoUtil;
import org.eclipse.emf.cdo.spi.common.commit.InternalCDOCommitInfoManager;
import org.eclipse.emf.cdo.spi.server.InternalRepository;

import org.eclipse.net4j.db.DBException;
import org.eclipse.net4j.db.DBType;
import org.eclipse.net4j.db.DBUtil;
import org.eclipse.net4j.db.IDBDatabase;
import org.eclipse.net4j.db.IDBDatabase.RunnableWithSchema;
import org.eclipse.net4j.db.IDBPreparedStatement;
import org.eclipse.net4j.db.IDBPreparedStatement.ReuseProbability;
import org.eclipse.net4j.db.ddl.IDBField;
import org.eclipse.net4j.db.ddl.IDBIndex;
import org.eclipse.net4j.db.ddl.IDBSchema;
import org.eclipse.net4j.db.ddl.IDBTable;
import org.eclipse.net4j.util.lifecycle.Lifecycle;
import org.eclipse.net4j.util.om.monitor.OMMonitor;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Eike Stepper
 * @since 4.6
 */
public class CommitInfoTable extends Lifecycle
{
  private static final String COMMIT_INFOS = "cdo_commit_infos"; //$NON-NLS-1$

  private static final String TIMESTAMP = "commit_time"; //$NON-NLS-1$

  private static final String PREVIOUS_TIMESTAMP = "previous_time";

  private static final String BRANCH = "branch_id"; //$NON-NLS-1$

  private static final String USER = "user_id"; //$NON-NLS-1$

  private static final String COMMENT = "commit_comment"; //$NON-NLS-1$

  private static final String MERGED_BRANCH = "merged_branch"; //$NON-NLS-1$

  private static final String MERGED_TIMESTAMP = "merged_time"; //$NON-NLS-1$

  private DBStore store;

  private boolean withMergeSource;

  private IDBTable table;

  private String sqlInsert;

  public CommitInfoTable(DBStore store)
  {
    this.store = store;
  }

  public void writeCommitInfo(IDBStoreAccessor accessor, CDOBranch branch, long timeStamp, long previousTimeStamp, String userID, String comment,
      CDOBranchPoint mergeSource, OMMonitor monitor)
  {
    IDBPreparedStatement stmt = accessor.getDBConnection().prepareStatement(sqlInsert, ReuseProbability.HIGH);

    try
    {
      stmt.setLong(1, timeStamp);
      stmt.setLong(2, previousTimeStamp);
      stmt.setInt(3, branch.getID());
      stmt.setString(4, userID);
      stmt.setString(5, comment);

      if (withMergeSource)
      {
        if (mergeSource != null)
        {
          stmt.setInt(6, mergeSource.getBranch().getID());
          stmt.setLong(7, mergeSource.getTimeStamp());
        }
        else
        {
          stmt.setNull(6, DBType.INTEGER.getCode());
          stmt.setNull(7, DBType.BIGINT.getCode());
        }
      }

      DBUtil.update(stmt, true);
    }
    catch (SQLException ex)
    {
      throw new DBException(ex);
    }
    finally
    {
      DBUtil.close(stmt);
    }
  }

  public void loadCommitInfos(IDBStoreAccessor accessor, CDOBranch branch, long startTime, long endTime, CDOCommitInfoHandler handler)
  {
    int count = CDOCommitInfoUtil.decodeCount(endTime);

    StringBuilder builder = new StringBuilder();
    builder.append("SELECT "); //$NON-NLS-1$
    builder.append(TIMESTAMP);
    builder.append(", "); //$NON-NLS-1$
    builder.append(PREVIOUS_TIMESTAMP);
    builder.append(", "); //$NON-NLS-1$
    builder.append(USER);
    builder.append(", "); //$NON-NLS-1$
    builder.append(COMMENT);

    if (branch == null)
    {
      builder.append(", "); //$NON-NLS-1$
      builder.append(BRANCH);
    }

    if (withMergeSource)
    {
      builder.append(", "); //$NON-NLS-1$
      builder.append(MERGED_BRANCH);
      builder.append(", "); //$NON-NLS-1$
      builder.append(MERGED_TIMESTAMP);
    }

    builder.append(" FROM "); //$NON-NLS-1$
    builder.append(COMMIT_INFOS);
    boolean where = false;

    if (branch != null)
    {
      builder.append(where ? " AND " : " WHERE "); //$NON-NLS-1$ //$NON-NLS-2$
      builder.append(BRANCH);
      builder.append("="); //$NON-NLS-1$
      builder.append(branch.getID());
      where = true;
    }

    if (startTime != CDOBranchPoint.UNSPECIFIED_DATE)
    {
      builder.append(where ? " AND " : " WHERE "); //$NON-NLS-1$ //$NON-NLS-2$
      builder.append(TIMESTAMP);
      builder.append(count < 0 ? "<=" : ">="); //$NON-NLS-1$
      builder.append(startTime);
      where = true;
    }

    if (endTime > CDOBranchPoint.UNSPECIFIED_DATE)
    {
      builder.append(where ? " AND " : " WHERE "); //$NON-NLS-1$ //$NON-NLS-2$
      builder.append(TIMESTAMP);
      builder.append("<="); //$NON-NLS-1$
      builder.append(endTime);
      where = true;
    }

    builder.append(" ORDER BY "); //$NON-NLS-1$
    builder.append(TIMESTAMP);
    builder.append(count < 0 || CDOBranchPoint.UNSPECIFIED_DATE <= endTime && endTime <= startTime ? " DESC" : " ASC"); //$NON-NLS-1$
    String sql = builder.toString();

    IDBPreparedStatement stmt = accessor.getDBConnection().prepareStatement(sql, ReuseProbability.LOW);
    ResultSet resultSet = null;

    InternalRepository repository = store.getRepository();
    InternalCDOBranchManager branchManager = repository.getBranchManager();
    InternalCDOCommitInfoManager commitInfoManager = repository.getCommitInfoManager();
    count = Math.abs(count);

    try
    {
      resultSet = stmt.executeQuery();
      while (count-- > 0 && resultSet.next())
      {
        int column = 0;

        long timeStamp = resultSet.getLong(++column);
        long previousTimeStamp = resultSet.getLong(++column);
        String userID = resultSet.getString(++column);
        String comment = resultSet.getString(++column);

        CDOBranch infoBranch = branch;
        if (infoBranch == null)
        {
          int id = resultSet.getInt(++column);
          infoBranch = branchManager.getBranch(id);
        }

        CDOBranchPoint mergeSource = null;
        if (withMergeSource)
        {
          int id = resultSet.getInt(++column);
          if (!resultSet.wasNull())
          {
            InternalCDOBranch mergedBranch = branchManager.getBranch(id);

            long mergedTimeStamp = resultSet.getLong(++column);
            mergeSource = mergedBranch.getPoint(mergedTimeStamp);
          }
        }

        CDOCommitInfo commitInfo = commitInfoManager.createCommitInfo(infoBranch, timeStamp, previousTimeStamp, userID, comment, mergeSource, null);
        handler.handleCommitInfo(commitInfo);
      }
    }
    catch (SQLException ex)
    {
      throw new DBException(ex);
    }
    finally
    {
      DBUtil.close(resultSet);
      DBUtil.close(stmt);
    }
  }

  public void rawExport(Connection connection, CDODataOutput out, long fromCommitTime, long toCommitTime) throws IOException
  {
    out.writeBoolean(withMergeSource);

    String where = " WHERE " + TIMESTAMP + " BETWEEN " + fromCommitTime + " AND " + toCommitTime;
    DBUtil.serializeTable(out, connection, table, null, where);
  }

  public void rawImport(Connection connection, CDODataInput in, long fromCommitTime, long toCommitTime, OMMonitor monitor) throws IOException
  {
    boolean actualWithMergeSource = in.readBoolean();
    if (actualWithMergeSource != withMergeSource)
    {
      throw new IllegalStateException("Commit info data mismatch. Expected: " + (withMergeSource ? "with" : "without") + " merge source. Actual: "
          + (actualWithMergeSource ? "with" : "without") + " merge source.");
    }

    DBUtil.deserializeTable(in, connection, table, monitor.fork());
  }

  public void repairAfterCrash(Connection connection)
  {
    IDBField timeStampField = table.getField(TIMESTAMP);

    long lastCommitTime = DBUtil.selectMaximumLong(connection, timeStampField);
    long lastNonLocalCommitTime = DBUtil.selectMaximumLong(connection, timeStampField, CDOBranch.MAIN_BRANCH_ID + "<=" + BRANCH);

    if (lastNonLocalCommitTime == CDOBranchPoint.UNSPECIFIED_DATE)
    {
      lastNonLocalCommitTime = lastCommitTime;
    }

    store.setLastCommitTime(lastCommitTime);
    store.setLastNonLocalCommitTime(lastNonLocalCommitTime);
  }

  @Override
  protected void doActivate() throws Exception
  {
    super.doActivate();

    InternalRepository repository = store.getRepository();
    withMergeSource = repository.getCommitInfoStorage() == CommitInfoStorage.WITH_MERGE_SOURCE;

    IDBDatabase database = store.getDatabase();
    table = database.getSchema().getTable(COMMIT_INFOS);
    if (table == null)
    {
      database.updateSchema(new RunnableWithSchema()
      {
        public void run(IDBSchema schema)
        {
          table = schema.addTable(COMMIT_INFOS);
          table.addField(TIMESTAMP, DBType.BIGINT, true);
          table.addField(PREVIOUS_TIMESTAMP, DBType.BIGINT);
          table.addField(BRANCH, DBType.INTEGER);
          table.addField(USER, DBType.VARCHAR);
          table.addField(COMMENT, DBType.VARCHAR);
          table.addIndex(IDBIndex.Type.PRIMARY_KEY, TIMESTAMP);
          table.addIndex(IDBIndex.Type.NON_UNIQUE, BRANCH);

          if (withMergeSource)
          {
            table.addField(MERGED_BRANCH, DBType.INTEGER);
            table.addField(MERGED_TIMESTAMP, DBType.BIGINT);
            table.addIndex(IDBIndex.Type.NON_UNIQUE, MERGED_BRANCH, MERGED_TIMESTAMP);
          }
        }
      });
    }
    else
    {
      if (withMergeSource && table.getField(MERGED_BRANCH) == null)
      {
        database.updateSchema(new RunnableWithSchema()
        {
          public void run(IDBSchema schema)
          {
            IDBTable table = schema.getTable(COMMIT_INFOS);
            table.addField(MERGED_BRANCH, DBType.INTEGER);
            table.addField(MERGED_TIMESTAMP, DBType.BIGINT);
            table.addIndex(IDBIndex.Type.NON_UNIQUE, MERGED_BRANCH, MERGED_TIMESTAMP);
          }
        });
      }
    }

    StringBuilder builder = new StringBuilder();
    builder.append("INSERT INTO "); //$NON-NLS-1$
    builder.append(COMMIT_INFOS);
    builder.append("("); //$NON-NLS-1$
    builder.append(TIMESTAMP);
    builder.append(", "); //$NON-NLS-1$
    builder.append(PREVIOUS_TIMESTAMP);
    builder.append(", "); //$NON-NLS-1$
    builder.append(BRANCH);
    builder.append(", "); //$NON-NLS-1$
    builder.append(USER);
    builder.append(", "); //$NON-NLS-1$
    builder.append(COMMENT);

    if (withMergeSource)
    {
      builder.append(", "); //$NON-NLS-1$
      builder.append(MERGED_BRANCH);
      builder.append(", "); //$NON-NLS-1$
      builder.append(MERGED_TIMESTAMP);
    }

    builder.append(") VALUES (?, ?, ?, ?, ?"); //$NON-NLS-1$
    if (withMergeSource)
    {
      builder.append(", ?, ?"); //$NON-NLS-1$
    }

    builder.append(")"); //$NON-NLS-1$
    sqlInsert = builder.toString();
  }

  @Override
  protected void doDeactivate() throws Exception
  {
    sqlInsert = null;
    table = null;
    super.doDeactivate();
  }
}

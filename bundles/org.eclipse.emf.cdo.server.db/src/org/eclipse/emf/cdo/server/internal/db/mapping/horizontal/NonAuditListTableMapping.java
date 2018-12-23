/*
 * Copyright (c) 2009-2016, 2018 Eike Stepper (Loehne, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 *    Stefan Winkler - 271444: [DB] Multiple refactorings bug 271444
 *    Stefan Winkler - Bug 329025: [DB] Support branching for range-based mapping strategy
 */
package org.eclipse.emf.cdo.server.internal.db.mapping.horizontal;

import org.eclipse.emf.cdo.common.branch.CDOBranch;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.revision.CDORevision;
import org.eclipse.emf.cdo.common.revision.delta.CDOFeatureDelta;
import org.eclipse.emf.cdo.common.revision.delta.CDOListFeatureDelta;
import org.eclipse.emf.cdo.server.db.IDBStoreAccessor;
import org.eclipse.emf.cdo.server.db.IIDHandler;
import org.eclipse.emf.cdo.server.db.mapping.IListMappingDeltaSupport;
import org.eclipse.emf.cdo.server.db.mapping.IMappingStrategy;
import org.eclipse.emf.cdo.server.db.mapping.ITypeMapping;
import org.eclipse.emf.cdo.server.internal.db.bundle.OM;

import org.eclipse.net4j.db.DBException;
import org.eclipse.net4j.db.DBUtil;
import org.eclipse.net4j.db.IDBPreparedStatement;
import org.eclipse.net4j.db.IDBPreparedStatement.ReuseProbability;
import org.eclipse.net4j.db.ddl.IDBTable;
import org.eclipse.net4j.util.om.trace.ContextTracer;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ListIterator;

/**
 * This is a list-to-table mapping optimized for non-audit-mode. It doesn't care about version and has delta support.
 *
 * @author Eike Stepper
 * @since 2.0
 */
public class NonAuditListTableMapping extends AbstractListTableMapping implements IListMappingDeltaSupport
{
  private static final ContextTracer TRACER = new ContextTracer(OM.DEBUG, NonAuditListTableMapping.class);

  private String sqlClear;

  private String sqlUpdateValue;

  private String sqlUpdateIndex;

  private String sqlInsertValue;

  private String sqlDeleteItem;

  private String sqlShiftDownIndex;

  private String sqlReadCurrentIndexOffset;

  private String sqlShiftUpIndex;

  public NonAuditListTableMapping(IMappingStrategy mappingStrategy, EClass eClass, EStructuralFeature feature)
  {
    super(mappingStrategy, eClass, feature);
  }

  @Override
  protected void initSQLStrings()
  {
    super.initSQLStrings();

    IDBTable table = getTable();

    // ----------- clear list -------------------------
    StringBuilder builder = new StringBuilder();
    builder.append("DELETE FROM "); //$NON-NLS-1$
    builder.append(table);
    builder.append(" WHERE "); //$NON-NLS-1$
    builder.append(LIST_REVISION_ID);
    builder.append("=?"); //$NON-NLS-1$
    sqlClear = builder.toString();

    builder.append(" AND "); //$NON-NLS-1$
    builder.append(LIST_IDX);
    builder.append("=?"); //$NON-NLS-1$
    sqlDeleteItem = builder.toString();

    // ----------- update one item --------------------
    builder = new StringBuilder();
    builder.append("UPDATE "); //$NON-NLS-1$
    builder.append(getTable());
    builder.append(" SET "); //$NON-NLS-1$
    builder.append(LIST_VALUE);
    builder.append("=? "); //$NON-NLS-1$
    builder.append(" WHERE "); //$NON-NLS-1$
    builder.append(LIST_REVISION_ID);
    builder.append("=? AND "); //$NON-NLS-1$
    builder.append(LIST_IDX);
    builder.append("=?"); //$NON-NLS-1$
    sqlUpdateValue = builder.toString();

    // ----------- insert one item --------------------
    builder = new StringBuilder();
    builder.append("INSERT INTO "); //$NON-NLS-1$
    builder.append(getTable());
    builder.append(" ("); //$NON-NLS-1$
    builder.append(LIST_REVISION_ID);
    builder.append(", "); //$NON-NLS-1$
    builder.append(LIST_IDX);
    builder.append(", "); //$NON-NLS-1$
    builder.append(LIST_VALUE);
    builder.append(") VALUES(?, ?, ?)"); //$NON-NLS-1$
    sqlInsertValue = builder.toString();

    // ----------- update one item index --------------
    builder = new StringBuilder();
    builder.append("UPDATE "); //$NON-NLS-1$
    builder.append(getTable());
    builder.append(" SET "); //$NON-NLS-1$
    builder.append(LIST_IDX);
    builder.append("=? "); //$NON-NLS-1$
    builder.append(" WHERE "); //$NON-NLS-1$
    builder.append(LIST_REVISION_ID);
    builder.append("=? AND "); //$NON-NLS-1$
    builder.append(LIST_IDX);
    builder.append("=?"); //$NON-NLS-1$
    sqlUpdateIndex = builder.toString();

    // ----------- mass update item indexes --------------
    builder = new StringBuilder();
    builder.append("UPDATE "); //$NON-NLS-1$
    builder.append(getTable());
    builder.append(" SET "); //$NON-NLS-1$
    builder.append(LIST_IDX);
    builder.append("="); //$NON-NLS-1$
    builder.append(LIST_IDX);
    builder.append("+? WHERE "); //$NON-NLS-1$
    builder.append(LIST_REVISION_ID);
    builder.append("=? AND "); //$NON-NLS-1$
    builder.append(LIST_IDX);
    builder.append(" BETWEEN ? AND ?"); //$NON-NLS-1$

    // needed because of MySQL:
    builder.append(" /*! ORDER BY "); //$NON-NLS-1$ /
    builder.append(LIST_IDX);
    sqlShiftDownIndex = builder.toString() + " */"; //$NON-NLS-1$

    builder.append(" DESC"); //$NON-NLS-1$
    sqlShiftUpIndex = builder.toString() + " */"; //$NON-NLS-1$

    // ----------- read current index offset --------------
    builder = new StringBuilder();
    builder.append("SELECT MIN("); //$NON-NLS-1$
    builder.append(LIST_IDX);
    builder.append(") FROM "); //$NON-NLS-1$
    builder.append(getTable());
    builder.append(" WHERE "); //$NON-NLS-1$
    builder.append(LIST_REVISION_ID);
    builder.append("=?"); //$NON-NLS-1$
    sqlReadCurrentIndexOffset = builder.toString();
  }

  @Override
  public void addSimpleChunkWhere(IDBStoreAccessor accessor, CDOID cdoid, StringBuilder builder, int index)
  {
    int offset = getCurrentIndexOffset(accessor, cdoid);
    super.addSimpleChunkWhere(accessor, cdoid, builder, index + offset);
  }

  @Override
  public void addRangedChunkWhere(IDBStoreAccessor accessor, CDOID cdoid, StringBuilder builder, int fromIndex, int toIndex)
  {
    int offset = getCurrentIndexOffset(accessor, cdoid);
    super.addRangedChunkWhere(accessor, cdoid, builder, fromIndex + offset, toIndex + offset);
  }

  @Override
  protected void addKeyFields(List<FieldInfo> list)
  {
    // Do nothing.
  }

  @Override
  protected void setKeyFields(PreparedStatement stmt, CDORevision revision) throws SQLException
  {
    IIDHandler idHandler = getMappingStrategy().getStore().getIDHandler();
    idHandler.setCDOID(stmt, 1, revision.getID());
  }

  public void objectDetached(IDBStoreAccessor accessor, CDOID id, long revised)
  {
    clearList(accessor, id);
  }

  @Override
  public void rawDeleted(IDBStoreAccessor accessor, CDOID id, CDOBranch branch, int version)
  {
    clearList(accessor, id);
  }

  public void processDelta(IDBStoreAccessor accessor, CDOID id, int branchId, int oldVersion, int newVersion, long created, CDOListFeatureDelta delta)
  {
    if (getTable() == null)
    {
      initTable(accessor);
    }

    List<CDOFeatureDelta> listChanges = delta.getListChanges();
    int oldListSize = delta.getOriginSize();

    if (TRACER.isEnabled())
    {
      TRACER.format("ListTableMapping.processDelta for object {0} - original list size: {1}", id, //$NON-NLS-1$
          oldListSize);
    }

    ListDeltaWriter writer = new ListDeltaWriter(accessor, id, listChanges, oldListSize);
    writer.writeListDeltas();
  }

  /**
   * Clear a list of a given revision.
   *
   * @param accessor
   *          the accessor to use
   * @param id
   *          the id of the revision from which to remove all items
   */
  private void clearList(IDBStoreAccessor accessor, CDOID id)
  {
    if (getTable() == null)
    {
      return;
    }

    IIDHandler idHandler = getMappingStrategy().getStore().getIDHandler();
    IDBPreparedStatement stmt = accessor.getDBConnection().prepareStatement(sqlClear, ReuseProbability.HIGH);

    try
    {
      idHandler.setCDOID(stmt, 1, id);
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

  private int getCurrentIndexOffset(IDBStoreAccessor accessor, CDOID id)
  {
    if (getTable() == null)
    {
      // List is empty. Return the default offset of 0.
      return 0;
    }

    IIDHandler idHandler = getMappingStrategy().getStore().getIDHandler();
    IDBPreparedStatement stmt = accessor.getDBConnection().prepareStatement(sqlReadCurrentIndexOffset, ReuseProbability.HIGH);
    ResultSet rset = null;

    try
    {
      idHandler.setCDOID(stmt, 1, id);
      rset = stmt.executeQuery();
      if (!rset.next())
      {
        // List is empty. Return the default offset of 0.
        return 0;
      }

      // Return the minimum index which is equal to the current offset.
      return rset.getInt(1);
    }
    catch (SQLException e)
    {
      throw new DBException(e);
    }
    finally
    {
      DBUtil.close(rset);
      DBUtil.close(stmt);
    }
  }

  /**
   * @author Eike Stepper
   */
  private final class ListDeltaWriter extends AbstractListDeltaWriter
  {
    private IDBPreparedStatement stmtDelete;

    private IDBPreparedStatement stmtMove;

    private IDBPreparedStatement stmtSet;

    private IDBPreparedStatement stmtInsert;

    private IDBPreparedStatement stmtShiftDown;

    private IDBPreparedStatement stmtShiftUp;

    private int countDelete;

    private int countMove;

    private int countSet;

    private int countInsert;

    private int countShiftDown;

    private int countShiftUp;

    public ListDeltaWriter(IDBStoreAccessor accessor, CDOID id, List<CDOFeatureDelta> listChanges, int oldListSize)
    {
      super(accessor, id, listChanges, oldListSize);
    }

    @Override
    protected boolean isZeroBasedIndex()
    {
      return ((HorizontalNonAuditMappingStrategy)getMappingStrategy()).shallForceZeroBasedIndex();
    }

    @Override
    protected ITypeMapping getTypeMapping()
    {
      return NonAuditListTableMapping.this.getTypeMapping();
    }

    @Override
    protected int getCurrentIndexOffset()
    {
      return NonAuditListTableMapping.this.getCurrentIndexOffset(accessor, id);
    }

    @Override
    protected void clearList()
    {
      NonAuditListTableMapping.this.clearList(accessor, id);
    }

    @Override
    protected void writeResultToDatabase() throws SQLException
    {
      try
      {
        super.writeResultToDatabase();

        if (countMove > 0)
        {
          if (TRACER.isEnabled())
          {
            TRACER.format("Performing {0} move operations", countMove); //$NON-NLS-1$
          }

          DBUtil.executeBatch(stmtMove, countMove);
        }

        if (countInsert > 0)
        {
          if (TRACER.isEnabled())
          {
            TRACER.format("Performing {0} insert operations", countInsert); //$NON-NLS-1$
          }

          DBUtil.executeBatch(stmtInsert, countInsert);
        }

        if (countSet > 0)
        {
          if (TRACER.isEnabled())
          {
            TRACER.format("Performing {0} set operations", countSet); //$NON-NLS-1$
          }

          DBUtil.executeBatch(stmtSet, countSet);
        }
      }
      finally
      {
        close(stmtDelete, stmtMove, stmtInsert, stmtSet);
      }
    }

    @Override
    protected void writeShifts(IIDHandler idHandler) throws SQLException
    {
      if (countDelete > 0)
      {
        if (TRACER.isEnabled())
        {
          TRACER.format("Performing {0} delete operations", countDelete); //$NON-NLS-1$
        }

        DBUtil.executeBatch(stmtDelete, countDelete);
      }

      if (countMove > 0)
      {
        if (TRACER.isEnabled())
        {
          TRACER.format("Performing {0} move operations", countMove); //$NON-NLS-1$
        }

        DBUtil.executeBatch(stmtMove, countMove);
        countMove = 0;
      }

      super.writeShifts(idHandler);
    }

    @Override
    protected void writeShiftsDown(IIDHandler idHandler, ListIterator<Shift> operationIt) throws SQLException
    {
      try
      {
        super.writeShiftsDown(idHandler, operationIt);

        if (countShiftDown > 0)
        {
          DBUtil.executeBatch(stmtShiftDown, countShiftDown, false);
        }
      }
      finally
      {
        close(stmtShiftDown);
      }
    }

    @Override
    protected void writeShiftsUp(IIDHandler idHandler, ListIterator<Shift> operationIt) throws SQLException
    {
      try
      {
        super.writeShiftsUp(idHandler, operationIt);

        if (countShiftUp > 0)
        {
          DBUtil.executeBatch(stmtShiftUp, countShiftUp, false);
        }
      }
      finally
      {
        close(stmtShiftUp);
      }
    }

    @Override
    protected void dbDelete(IIDHandler idHandler, int index) throws SQLException
    {
      if (stmtDelete == null)
      {
        stmtDelete = accessor.getDBConnection().prepareStatement(sqlDeleteItem, ReuseProbability.HIGH);
        idHandler.setCDOID(stmtDelete, 1, id);
      }

      stmtDelete.setInt(2, index);
      stmtDelete.addBatch();
      ++countDelete;
    }

    @Override
    protected void dbMove(IIDHandler idHandler, int fromIndex, int toIndex, int srcIndex) throws SQLException
    {
      if (stmtMove == null)
      {
        stmtMove = accessor.getDBConnection().prepareStatement(sqlUpdateIndex, ReuseProbability.HIGH);
        idHandler.setCDOID(stmtMove, 2, id);
      }

      stmtMove.setInt(3, fromIndex);
      stmtMove.setInt(1, toIndex);
      stmtMove.addBatch();
      ++countMove;
    }

    @Override
    protected void dbSet(IIDHandler idHandler, ITypeMapping typeMapping, int index, Object value, int srcIndex) throws SQLException
    {
      if (stmtSet == null)
      {
        stmtSet = accessor.getDBConnection().prepareStatement(sqlUpdateValue, ReuseProbability.HIGH);
        idHandler.setCDOID(stmtSet, 2, id);
      }

      stmtSet.setInt(3, index);
      typeMapping.setValue(stmtSet, 1, value);
      stmtSet.addBatch();
      ++countSet;
    }

    @Override
    protected void dbInsert(IIDHandler idHandler, ITypeMapping typeMapping, int index, Object value) throws SQLException
    {
      if (stmtInsert == null)
      {
        stmtInsert = accessor.getDBConnection().prepareStatement(sqlInsertValue, ReuseProbability.HIGH);
        idHandler.setCDOID(stmtInsert, 1, id);
      }

      stmtInsert.setInt(2, index);
      typeMapping.setValue(stmtInsert, 3, value);
      stmtInsert.addBatch();
      ++countInsert;
    }

    @Override
    protected void dbShiftDown(IIDHandler idHandler, int offset, int startIndex, int endIndex) throws SQLException
    {
      if (stmtShiftDown == null)
      {
        stmtShiftDown = accessor.getDBConnection().prepareStatement(sqlShiftDownIndex, ReuseProbability.HIGH);
        idHandler.setCDOID(stmtShiftDown, 2, id);
      }

      stmtShiftDown.setInt(1, offset);
      stmtShiftDown.setInt(3, startIndex);
      stmtShiftDown.setInt(4, endIndex);
      stmtShiftDown.addBatch();
      ++countShiftDown;
    }

    @Override
    protected void dbShiftUp(IIDHandler idHandler, int offset, int startIndex, int endIndex) throws SQLException
    {
      if (stmtShiftUp == null)
      {
        stmtShiftUp = accessor.getDBConnection().prepareStatement(sqlShiftUpIndex, ReuseProbability.HIGH);
        idHandler.setCDOID(stmtShiftUp, 2, id);
      }

      stmtShiftUp.setInt(1, offset);
      stmtShiftUp.setInt(3, startIndex);
      stmtShiftUp.setInt(4, endIndex);
      stmtShiftUp.addBatch();
      ++countShiftUp;
    }
  }
}

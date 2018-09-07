/*
 * Copyright (c) 2010-2013, 2015, 2016 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * This class has been derived from AbstractListTableMapping
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 *    Lothar Werzinger - Bug 296440: [DB] Change RDB schema to improve scalability of to-many references in audit mode
 *    Stefan Winkler - cleanup, merge and maintenance
 *    Stefan Winkler - Bug 329025: [DB] Support branching for range-based mapping strategy
 */
package org.eclipse.emf.cdo.server.internal.db.mapping.horizontal;

import org.eclipse.emf.cdo.common.branch.CDOBranch;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.revision.CDOList;
import org.eclipse.emf.cdo.common.revision.CDORevision;
import org.eclipse.emf.cdo.common.revision.delta.CDOAddFeatureDelta;
import org.eclipse.emf.cdo.common.revision.delta.CDOClearFeatureDelta;
import org.eclipse.emf.cdo.common.revision.delta.CDOContainerFeatureDelta;
import org.eclipse.emf.cdo.common.revision.delta.CDOFeatureDelta;
import org.eclipse.emf.cdo.common.revision.delta.CDOFeatureDeltaVisitor;
import org.eclipse.emf.cdo.common.revision.delta.CDOListFeatureDelta;
import org.eclipse.emf.cdo.common.revision.delta.CDOMoveFeatureDelta;
import org.eclipse.emf.cdo.common.revision.delta.CDORemoveFeatureDelta;
import org.eclipse.emf.cdo.common.revision.delta.CDOSetFeatureDelta;
import org.eclipse.emf.cdo.common.revision.delta.CDOUnsetFeatureDelta;
import org.eclipse.emf.cdo.server.IRepository;
import org.eclipse.emf.cdo.server.IStoreAccessor.QueryXRefsContext;
import org.eclipse.emf.cdo.server.IStoreChunkReader.Chunk;
import org.eclipse.emf.cdo.server.db.IDBStore;
import org.eclipse.emf.cdo.server.db.IDBStoreAccessor;
import org.eclipse.emf.cdo.server.db.IDBStoreChunkReader;
import org.eclipse.emf.cdo.server.db.IIDHandler;
import org.eclipse.emf.cdo.server.db.mapping.IClassMapping;
import org.eclipse.emf.cdo.server.db.mapping.IListMappingDeltaSupport;
import org.eclipse.emf.cdo.server.db.mapping.IListMappingUnitSupport;
import org.eclipse.emf.cdo.server.db.mapping.IMappingStrategy;
import org.eclipse.emf.cdo.server.db.mapping.ITypeMapping;
import org.eclipse.emf.cdo.server.internal.db.bundle.OM;
import org.eclipse.emf.cdo.spi.common.revision.InternalCDORevision;
import org.eclipse.emf.cdo.spi.server.InternalRepository;

import org.eclipse.net4j.db.DBException;
import org.eclipse.net4j.db.DBType;
import org.eclipse.net4j.db.DBUtil;
import org.eclipse.net4j.db.IDBDatabase;
import org.eclipse.net4j.db.IDBPreparedStatement;
import org.eclipse.net4j.db.IDBPreparedStatement.ReuseProbability;
import org.eclipse.net4j.db.ddl.IDBIndex.Type;
import org.eclipse.net4j.db.ddl.IDBTable;
import org.eclipse.net4j.util.ImplementationError;
import org.eclipse.net4j.util.collection.MoveableList;
import org.eclipse.net4j.util.om.trace.ContextTracer;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * This is a list-table mapping for audit mode. It is optimized for frequent insert operations at the list's end, which
 * causes just 1 DB row to be changed. This is achieved by introducing a version range (columns cdo_version_added and
 * cdo_version_removed) which records for which revisions a particular entry existed. Also, this mapping is mainly
 * optimized for potentially very large lists: the need for having the complete list stored in memopy to do
 * in-the-middle-moved and inserts is traded in for a few more DB access operations.
 *
 * @author Eike Stepper
 * @author Stefan Winkler
 * @author Lothar Werzinger
 */
public class AuditListTableMappingWithRanges extends AbstractBasicListTableMapping implements IListMappingDeltaSupport, IListMappingUnitSupport
{
  private static final ContextTracer TRACER = new ContextTracer(OM.DEBUG, AuditListTableMappingWithRanges.class);

  /**
   * Used to clean up lists for detached objects.
   */
  private static final int FINAL_VERSION = Integer.MAX_VALUE;

  private static final String SQL_ORDER_BY_INDEX = " ORDER BY " + LIST_IDX;

  private static final boolean CHECK_UNIT_ENTRIES = Boolean.getBoolean("org.eclipse.emf.cdo.server.db.checkUnitEntries");

  /**
   * The table of this mapping.
   */
  private IDBTable table;

  /**
   * The type mapping for the value field.
   */
  private ITypeMapping typeMapping;

  // --------- SQL strings - see initSQLStrings() -----------------
  private String sqlSelectChunksPrefix;

  private String sqlSelectUnitEntries;

  private String sqlInsertEntry;

  private String sqlDeleteEntry;

  private String sqlRemoveEntry;

  private String sqlUpdateIndex;

  private String sqlGetValue;

  private String sqlClearList;

  private String sqlDeleteList;

  public AuditListTableMappingWithRanges(IMappingStrategy mappingStrategy, EClass eClass, EStructuralFeature feature)
  {
    super(mappingStrategy, eClass, feature);
    initTable();
    initSQLStrings();
  }

  private void initTable()
  {
    String tableName = getMappingStrategy().getTableName(getContainingClass(), getFeature());
    typeMapping = getMappingStrategy().createValueMapping(getFeature());

    IDBStore store = getMappingStrategy().getStore();
    DBType idType = store.getIDHandler().getDBType();
    int idLength = store.getIDColumnLength();

    IDBDatabase database = getMappingStrategy().getStore().getDatabase();
    table = database.getSchema().getTable(tableName);
    if (table == null)
    {
      table = database.getSchemaTransaction().getWorkingCopy().addTable(tableName);
      table.addField(LIST_REVISION_ID, idType, idLength, true);
      table.addField(LIST_REVISION_VERSION_ADDED, DBType.INTEGER);
      table.addField(LIST_REVISION_VERSION_REMOVED, DBType.INTEGER);
      table.addField(LIST_IDX, DBType.INTEGER, true);

      // TODO think about indexes
      table.addIndex(Type.NON_UNIQUE, LIST_REVISION_ID, LIST_REVISION_VERSION_ADDED, LIST_REVISION_VERSION_REMOVED, LIST_IDX);

      typeMapping.createDBField(table, LIST_VALUE);
    }
    else
    {
      typeMapping.setDBField(table, LIST_VALUE);
    }
  }

  private void initSQLStrings()
  {
    String tableName = getTable().getName();

    // ---------------- read chunks ----------------------------
    StringBuilder builder = new StringBuilder();
    builder.append("SELECT "); //$NON-NLS-1$
    builder.append(LIST_VALUE);
    builder.append(" FROM "); //$NON-NLS-1$
    builder.append(tableName);
    builder.append(" WHERE "); //$NON-NLS-1$
    builder.append(LIST_REVISION_ID);
    builder.append("=? AND "); //$NON-NLS-1$
    builder.append(LIST_REVISION_VERSION_ADDED);
    builder.append("<=? AND ("); //$NON-NLS-1$
    builder.append(LIST_REVISION_VERSION_REMOVED);
    builder.append(" IS NULL OR "); //$NON-NLS-1$
    builder.append(LIST_REVISION_VERSION_REMOVED);
    builder.append(">?)"); //$NON-NLS-1$
    sqlSelectChunksPrefix = builder.toString();

    // ----------------- insert entry -----------------
    builder = new StringBuilder("INSERT INTO "); //$NON-NLS-1$
    builder.append(tableName);
    builder.append("("); //$NON-NLS-1$
    builder.append(LIST_REVISION_ID);
    builder.append(","); //$NON-NLS-1$
    builder.append(LIST_REVISION_VERSION_ADDED);
    builder.append(","); //$NON-NLS-1$
    builder.append(LIST_REVISION_VERSION_REMOVED);
    builder.append(","); //$NON-NLS-1$
    builder.append(LIST_IDX);
    builder.append(","); //$NON-NLS-1$
    builder.append(LIST_VALUE);
    builder.append(") VALUES (?, ?, NULL, ?, ?)"); //$NON-NLS-1$
    sqlInsertEntry = builder.toString();

    // ----------------- remove current entry -----------------
    builder = new StringBuilder("UPDATE "); //$NON-NLS-1$
    builder.append(getTable());
    builder.append(" SET "); //$NON-NLS-1$
    builder.append(LIST_REVISION_VERSION_REMOVED);
    builder.append("=? "); //$NON-NLS-1$
    builder.append(" WHERE "); //$NON-NLS-1$
    builder.append(LIST_REVISION_ID);
    builder.append("=? AND "); //$NON-NLS-1$
    builder.append(LIST_IDX);
    builder.append("=? AND "); //$NON-NLS-1$
    builder.append(LIST_REVISION_VERSION_REMOVED);
    builder.append(" IS NULL"); //$NON-NLS-1$
    sqlRemoveEntry = builder.toString();

    // ----------------- delete temporary entry -----------------
    builder = new StringBuilder("DELETE FROM "); //$NON-NLS-1$
    builder.append(getTable());
    builder.append(" WHERE "); //$NON-NLS-1$
    builder.append(LIST_REVISION_ID);
    builder.append("=? AND "); //$NON-NLS-1$
    builder.append(LIST_IDX);
    builder.append("=? AND "); //$NON-NLS-1$
    builder.append(LIST_REVISION_VERSION_ADDED);
    builder.append("=?"); //$NON-NLS-1$
    sqlDeleteEntry = builder.toString();

    // ----------------- update index -----------------
    builder = new StringBuilder("UPDATE "); //$NON-NLS-1$
    builder.append(getTable());
    builder.append(" SET "); //$NON-NLS-1$
    builder.append(LIST_IDX);
    builder.append("=? WHERE "); //$NON-NLS-1$
    builder.append(LIST_REVISION_ID);
    builder.append("=? AND "); //$NON-NLS-1$
    builder.append(LIST_REVISION_VERSION_ADDED);
    builder.append("=? AND "); //$NON-NLS-1$
    builder.append(LIST_IDX);
    builder.append("=?"); //$NON-NLS-1$
    sqlUpdateIndex = builder.toString();

    // ----------------- get current value -----------------
    builder = new StringBuilder("SELECT "); //$NON-NLS-1$
    builder.append(LIST_VALUE);
    builder.append(" FROM "); //$NON-NLS-1$
    builder.append(getTable());
    builder.append(" WHERE "); //$NON-NLS-1$
    builder.append(LIST_REVISION_ID);
    builder.append("=? AND "); //$NON-NLS-1$
    builder.append(LIST_IDX);
    builder.append("=? AND "); //$NON-NLS-1$
    builder.append(LIST_REVISION_VERSION_REMOVED);
    builder.append(" IS NULL"); //$NON-NLS-1$
    sqlGetValue = builder.toString();

    // ----------- clear list items -------------------------
    builder = new StringBuilder("UPDATE "); //$NON-NLS-1$
    builder.append(getTable());
    builder.append(" SET "); //$NON-NLS-1$
    builder.append(LIST_REVISION_VERSION_REMOVED);
    builder.append("=? "); //$NON-NLS-1$
    builder.append(" WHERE "); //$NON-NLS-1$
    builder.append(LIST_REVISION_ID);
    builder.append("=? AND "); //$NON-NLS-1$
    builder.append(LIST_REVISION_VERSION_REMOVED);
    builder.append(" IS NULL"); //$NON-NLS-1$
    sqlClearList = builder.toString();

    // ----------- delete temporary list items -------------------------
    builder = new StringBuilder("DELETE FROM "); //$NON-NLS-1$
    builder.append(getTable());
    builder.append(" WHERE "); //$NON-NLS-1$
    builder.append(LIST_REVISION_ID);
    builder.append("=? AND "); //$NON-NLS-1$
    builder.append(LIST_REVISION_VERSION_ADDED);
    builder.append("=? AND "); //$NON-NLS-1$
    builder.append(LIST_REVISION_VERSION_REMOVED);
    builder.append(" IS NULL"); //$NON-NLS-1$
    sqlDeleteList = builder.toString();
  }

  @Override
  public void setClassMapping(IClassMapping classMapping)
  {
    InternalRepository repository = (InternalRepository)getMappingStrategy().getStore().getRepository();
    if (repository.isSupportingUnits())
    {
      String listTableName = getTable().getName();
      String attributesTableName = classMapping.getDBTables().get(0).getName();

      sqlSelectUnitEntries = "SELECT " + (CHECK_UNIT_ENTRIES ? ATTRIBUTES_ID + ", " : "") + "cdo_list." + LIST_VALUE + //
          " FROM " + listTableName + " cdo_list, " + attributesTableName + ", " + UnitMappingTable.UNITS + //
          " WHERE " + UnitMappingTable.UNITS_ELEM + "=" + ATTRIBUTES_ID + //
          " AND " + ATTRIBUTES_ID + "=cdo_list." + LIST_REVISION_ID + //
          " AND " + UnitMappingTable.UNITS_UNIT + "=?" + //
          " AND " + ATTRIBUTES_CREATED + "<=?" + //
          " AND (" + ATTRIBUTES_REVISED + "=0 OR " + ATTRIBUTES_REVISED + ">=?)" + //
          " AND cdo_list." + LIST_REVISION_VERSION_ADDED + "<=" + ATTRIBUTES_VERSION + //
          " AND (cdo_list." + LIST_REVISION_VERSION_REMOVED + " IS NULL OR cdo_list." + LIST_REVISION_VERSION_REMOVED + ">" + ATTRIBUTES_VERSION
          + ") ORDER BY cdo_list." + LIST_REVISION_ID + ", cdo_list." + LIST_IDX;
    }
  }

  public Collection<IDBTable> getDBTables()
  {
    return Collections.singleton(table);
  }

  protected final IDBTable getTable()
  {
    return table;
  }

  protected final ITypeMapping getTypeMapping()
  {
    return typeMapping;
  }

  public void readValues(IDBStoreAccessor accessor, InternalCDORevision revision, int listChunk)
  {
    MoveableList<Object> list = revision.getList(getFeature());
    if (listChunk == 0 || list.size() == 0)
    {
      // nothing to read take shortcut
      return;
    }

    if (TRACER.isEnabled())
    {
      TRACER.format("Reading list values for feature {0}.{1} of {2}v{3}", getContainingClass().getName(), //$NON-NLS-1$
          getFeature().getName(), revision.getID(), revision.getVersion());
    }

    String sql = sqlSelectChunksPrefix + SQL_ORDER_BY_INDEX;

    IIDHandler idHandler = getMappingStrategy().getStore().getIDHandler();
    IDBPreparedStatement stmt = accessor.getDBConnection().prepareStatement(sql, ReuseProbability.HIGH);
    ResultSet resultSet = null;

    try
    {
      idHandler.setCDOID(stmt, 1, revision.getID());
      stmt.setInt(2, revision.getVersion());
      stmt.setInt(3, revision.getVersion());

      if (listChunk != CDORevision.UNCHUNKED)
      {
        stmt.setMaxRows(listChunk); // optimization - don't read unneeded rows.
      }

      resultSet = stmt.executeQuery();

      int currentIndex = 0;
      while ((listChunk == CDORevision.UNCHUNKED || --listChunk >= 0) && resultSet.next())
      {
        Object value = typeMapping.readValue(resultSet);
        if (TRACER.isEnabled())
        {
          TRACER.format("Read value for index {0} from result set: {1}", currentIndex, value); //$NON-NLS-1$
        }

        list.set(currentIndex++, value);
      }

      if (TRACER.isEnabled())
      {
        TRACER.format("Reading {4} list values done for feature {0}.{1} of {2}v{3}", //$NON-NLS-1$
            getContainingClass().getName(), getFeature().getName(), revision.getID(), revision.getVersion(), list.size());
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

  public final void readChunks(IDBStoreChunkReader chunkReader, List<Chunk> chunks, String where)
  {
    if (TRACER.isEnabled())
    {
      TRACER.format("Reading list chunk values for feature() {0}.{1} of {2}v{3}", getContainingClass().getName(), //$NON-NLS-1$
          getFeature().getName(), chunkReader.getRevision().getID(), chunkReader.getRevision().getVersion());
    }

    StringBuilder builder = new StringBuilder(sqlSelectChunksPrefix);
    if (where != null)
    {
      builder.append(" AND "); //$NON-NLS-1$
      builder.append(where);
    }

    builder.append(SQL_ORDER_BY_INDEX);
    String sql = builder.toString();

    IIDHandler idHandler = getMappingStrategy().getStore().getIDHandler();
    IDBPreparedStatement stmt = chunkReader.getAccessor().getDBConnection().prepareStatement(sql, ReuseProbability.LOW);
    ResultSet resultSet = null;

    try
    {
      idHandler.setCDOID(stmt, 1, chunkReader.getRevision().getID());
      stmt.setInt(2, chunkReader.getRevision().getVersion());
      stmt.setInt(3, chunkReader.getRevision().getVersion());

      resultSet = stmt.executeQuery();

      Chunk chunk = null;
      int chunkSize = 0;
      int chunkIndex = 0;
      int indexInChunk = 0;

      while (resultSet.next())
      {
        Object value = typeMapping.readValue(resultSet);

        if (chunk == null)
        {
          chunk = chunks.get(chunkIndex++);
          chunkSize = chunk.size();

          if (TRACER.isEnabled())
          {
            TRACER.format("Current chunk no. {0} is [start = {1}, size = {2}]", chunkIndex - 1, chunk.getStartIndex(), //$NON-NLS-1$
                chunkSize);
          }
        }

        if (TRACER.isEnabled())
        {
          TRACER.format("Read value for chunk index {0} from result set: {1}", indexInChunk, value); //$NON-NLS-1$
        }

        chunk.add(indexInChunk++, value);
        if (indexInChunk == chunkSize)
        {
          if (TRACER.isEnabled())
          {
            TRACER.format("Chunk finished"); //$NON-NLS-1$
          }

          chunk = null;
          indexInChunk = 0;
        }
      }

      if (TRACER.isEnabled())
      {
        TRACER.format("Reading list chunk values done for feature() {0}.{1} of {2}v{3}", //$NON-NLS-1$
            getContainingClass().getName(), getFeature().getName(), chunkReader.getRevision().getID(), chunkReader.getRevision().getVersion());
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

  public void writeValues(IDBStoreAccessor accessor, InternalCDORevision revision)
  {
    CDOList values = revision.getList(getFeature());

    int idx = 0;
    for (Object element : values)
    {
      writeValue(accessor, revision, idx++, element);
    }

    if (TRACER.isEnabled())
    {
      TRACER.format("Writing done"); //$NON-NLS-1$
    }
  }

  protected final void writeValue(IDBStoreAccessor accessor, CDORevision revision, int index, Object value)
  {
    if (TRACER.isEnabled())
    {
      TRACER.format("Writing value for feature {0}.{1} index {2} of {3}v{4} : {5}", //$NON-NLS-1$
          getContainingClass().getName(), getFeature().getName(), index, revision.getID(), revision.getVersion(), value);
    }

    addEntry(accessor, revision.getID(), revision.getVersion(), index, value);
  }

  /**
   * Clear a list of a given revision.
   *
   * @param accessor
   *          the accessor to use
   * @param id
   *          the id of the revision from which to remove all items
   */
  public void clearList(IDBStoreAccessor accessor, CDOID id, int oldVersion, int newVersion)
  {
    IIDHandler idHandler = getMappingStrategy().getStore().getIDHandler();
    IDBPreparedStatement stmtDeleteTemp = accessor.getDBConnection().prepareStatement(sqlDeleteList, ReuseProbability.HIGH);
    IDBPreparedStatement stmtClear = accessor.getDBConnection().prepareStatement(sqlClearList, ReuseProbability.HIGH);

    try
    {
      // delete temporary entries
      idHandler.setCDOID(stmtDeleteTemp, 1, id);
      stmtDeleteTemp.setInt(2, newVersion);

      int result = DBUtil.update(stmtDeleteTemp, false);
      if (TRACER.isEnabled())
      {
        TRACER.format("DeleteList result: {0}", result); //$NON-NLS-1$
      }

      // clear rest of the list
      stmtClear.setInt(1, newVersion);
      idHandler.setCDOID(stmtClear, 2, id);

      result = DBUtil.update(stmtClear, false);
      if (TRACER.isEnabled())
      {
        TRACER.format("ClearList result: {0}", result); //$NON-NLS-1$
      }
    }
    catch (SQLException e)
    {
      throw new DBException(e);
    }
    finally
    {
      DBUtil.close(stmtClear);
      DBUtil.close(stmtDeleteTemp);
    }
  }

  public void objectDetached(IDBStoreAccessor accessor, CDOID id, long revised)
  {
    if (TRACER.isEnabled())
    {
      TRACER.format("objectRevised {0}: {1}", id, revised); //$NON-NLS-1$
    }

    CDOBranch main = getMappingStrategy().getStore().getRepository().getBranchManager().getMainBranch();

    // get revision from cache to find out version number
    CDORevision revision = getMappingStrategy().getStore().getRepository().getRevisionManager().getRevision(id, main.getHead(),
        /* chunksize = */0, CDORevision.DEPTH_NONE, true);

    // set cdo_revision_removed for all list items (so we have no NULL values)
    clearList(accessor, id, revision.getVersion(), FINAL_VERSION);
  }

  @Override
  public void rawDeleted(IDBStoreAccessor accessor, CDOID id, CDOBranch branch, int version)
  {
    throw new UnsupportedOperationException("Raw deletion does not work in range-based mappings");
  }

  public ResultSet queryUnitEntries(IDBStoreAccessor accessor, IIDHandler idHandler, long timeStamp, CDOID rootID) throws SQLException
  {
    IDBPreparedStatement stmt = accessor.getDBConnection().prepareStatement(sqlSelectUnitEntries, ReuseProbability.MEDIUM);
    idHandler.setCDOID(stmt, 1, rootID);
    stmt.setLong(2, timeStamp);
    stmt.setLong(3, timeStamp);
    return stmt.executeQuery();
  }

  public void readUnitEntries(ResultSet resultSet, IIDHandler idHandler, CDOID id, MoveableList<Object> list) throws SQLException
  {
    int size = list.size();
    for (int i = 0; i < size; i++)
    {
      resultSet.next();

      if (CHECK_UNIT_ENTRIES)
      {
        CDOID checkID = idHandler.getCDOID(resultSet, 1);
        if (checkID != id)
        {
          throw new IllegalStateException("Result set does not deliver expected result");
        }
      }

      Object value = typeMapping.readValue(resultSet);
      list.set(i, value);
    }
  }

  private void addEntry(IDBStoreAccessor accessor, CDOID id, int version, int index, Object value)
  {
    if (TRACER.isEnabled())
    {
      TRACER.format("Adding value for feature() {0}.{1} index {2} of {3}v{4} : {5}", //$NON-NLS-1$
          getContainingClass().getName(), getFeature().getName(), index, id, version, value);
    }

    IIDHandler idHandler = getMappingStrategy().getStore().getIDHandler();
    IDBPreparedStatement stmt = accessor.getDBConnection().prepareStatement(sqlInsertEntry, ReuseProbability.HIGH);

    try
    {
      int column = 1;
      idHandler.setCDOID(stmt, column++, id);
      stmt.setInt(column++, version);
      stmt.setInt(column++, index);
      typeMapping.setValue(stmt, column++, value);

      DBUtil.update(stmt, true);
    }
    catch (SQLException e)
    {
      throw new DBException(e);
    }
    catch (IllegalStateException e)
    {
      throw new DBException(e);
    }
    finally
    {
      DBUtil.close(stmt);
    }
  }

  private void removeEntry(IDBStoreAccessor accessor, CDOID id, int oldVersion, int newVersion, int index)
  {
    if (TRACER.isEnabled())
    {
      TRACER.format("Removing value for feature() {0}.{1} index {2} of {3}v{4}", //$NON-NLS-1$
          getContainingClass().getName(), getFeature().getName(), index, id, newVersion);
    }

    IIDHandler idHandler = getMappingStrategy().getStore().getIDHandler();
    IDBPreparedStatement stmt = accessor.getDBConnection().prepareStatement(sqlDeleteEntry, ReuseProbability.HIGH);

    try
    {
      // try to delete a temporary entry first
      int column = 1;
      idHandler.setCDOID(stmt, column++, id);
      stmt.setInt(column++, index);
      stmt.setInt(column++, newVersion);

      int result = DBUtil.update(stmt, false);
      if (result == 1)
      {
        if (TRACER.isEnabled())
        {
          TRACER.format("removeEntry deleted: {0}", index); //$NON-NLS-1$
        }
      }
      else if (result > 1)
      {
        if (TRACER.isEnabled())
        {
          TRACER.format("removeEntry Too many results: {0}: {1}", index, result); //$NON-NLS-1$
        }

        throw new DBException("Too many results"); //$NON-NLS-1$
      }
      else
      {
        // no temporary entry found, so mark the entry as removed
        DBUtil.close(stmt);
        stmt = accessor.getDBConnection().prepareStatement(sqlRemoveEntry, ReuseProbability.HIGH);

        column = 1;
        stmt.setInt(column++, newVersion);
        idHandler.setCDOID(stmt, column++, id);
        stmt.setInt(column++, index);

        DBUtil.update(stmt, true);
      }
    }
    catch (SQLException e)
    {
      if (TRACER.isEnabled())
      {
        TRACER.format("Removing value for feature() {0}.{1} index {2} of {3}v{4} FAILED {5}", //$NON-NLS-1$
            getContainingClass().getName(), getFeature().getName(), index, id, newVersion, e.getMessage());
      }

      throw new DBException(e);
    }
    catch (IllegalStateException e)
    {
      if (TRACER.isEnabled())
      {
        TRACER.format("Removing value for feature() {0}.{1} index {2} of {3}v{4} FAILED {5}", //$NON-NLS-1$
            getContainingClass().getName(), getFeature().getName(), index, id, newVersion, e.getMessage());
      }

      throw new DBException(e);
    }
    finally
    {
      DBUtil.close(stmt);
    }
  }

  private Object getValue(IDBStoreAccessor accessor, CDOID id, int index)
  {
    IIDHandler idHandler = getMappingStrategy().getStore().getIDHandler();
    IDBPreparedStatement stmt = accessor.getDBConnection().prepareStatement(sqlGetValue, ReuseProbability.HIGH);
    Object result = null;

    try
    {
      int column = 1;
      idHandler.setCDOID(stmt, column++, id);
      stmt.setInt(column++, index);

      ResultSet resultSet = stmt.executeQuery();
      if (!resultSet.next())
      {
        throw new DBException("getValue() expects exactly one result");
      }

      result = typeMapping.readValue(resultSet);
      if (TRACER.isEnabled())
      {
        TRACER.format("Read value (index {0}) from result set: {1}", index, result); //$NON-NLS-1$
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

    return result;
  }

  public final boolean queryXRefs(IDBStoreAccessor accessor, String mainTableName, String mainTableWhere, QueryXRefsContext context, String idString)
  {

    String tableName = getTable().getName();
    String listJoin = getMappingStrategy().getListJoin("a_t", "l_t");

    StringBuilder builder = new StringBuilder();
    builder.append("SELECT l_t."); //$NON-NLS-1$
    builder.append(LIST_REVISION_ID);
    builder.append(", l_t."); //$NON-NLS-1$
    builder.append(LIST_VALUE);
    builder.append(", l_t."); //$NON-NLS-1$
    builder.append(LIST_IDX);
    builder.append(" FROM "); //$NON-NLS-1$
    builder.append(tableName);
    builder.append(" l_t, ");//$NON-NLS-1$
    builder.append(mainTableName);
    builder.append(" a_t WHERE ");//$NON-NLS-1$
    builder.append("a_t.");//$NON-NLS-1$
    builder.append(mainTableWhere);
    builder.append(listJoin);
    builder.append(" AND "); //$NON-NLS-1$
    builder.append(LIST_VALUE);
    builder.append(" IN "); //$NON-NLS-1$
    builder.append(idString);
    String sql = builder.toString();

    if (TRACER.isEnabled())
    {
      TRACER.format("Query XRefs (list): {0}", sql);
    }

    IIDHandler idHandler = getMappingStrategy().getStore().getIDHandler();
    IDBPreparedStatement stmt = accessor.getDBConnection().prepareStatement(sql, ReuseProbability.MEDIUM);
    ResultSet resultSet = null;

    try
    {
      resultSet = stmt.executeQuery();
      while (resultSet.next())
      {
        CDOID sourceID = idHandler.getCDOID(resultSet, 1);
        CDOID targetID = idHandler.getCDOID(resultSet, 2);
        int idx = resultSet.getInt(3);

        boolean more = context.addXRef(targetID, sourceID, (EReference)getFeature(), idx);
        if (TRACER.isEnabled())
        {
          TRACER.format("  add XRef to context: src={0}, tgt={1}, idx={2}", sourceID, targetID, idx);
        }

        if (!more)
        {
          if (TRACER.isEnabled())
          {
            TRACER.format("  result limit reached. Ignoring further results.");
          }

          return false;
        }
      }

      return true;
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

  public void processDelta(final IDBStoreAccessor accessor, final CDOID id, final int branchId, int oldVersion, final int newVersion, long created,
      CDOListFeatureDelta delta)
  {
    IRepository repo = accessor.getStore().getRepository();
    InternalCDORevision originalRevision = (InternalCDORevision)repo.getRevisionManager().getRevision(id, repo.getBranchManager().getMainBranch().getHead(),
        /* chunksize = */0, CDORevision.DEPTH_NONE, true);

    int oldListSize = originalRevision.getList(getFeature()).size();

    if (TRACER.isEnabled())
    {
      TRACER.format("ListTableMapping.processDelta for revision {0} - previous list size: {1}", originalRevision, //$NON-NLS-1$
          oldListSize);
    }

    // let the visitor collect the changes
    ListDeltaVisitor visitor = new ListDeltaVisitor(accessor, originalRevision, oldVersion, newVersion);

    if (TRACER.isEnabled())
    {
      TRACER.format("Processing deltas..."); //$NON-NLS-1$
    }

    for (CDOFeatureDelta listDelta : delta.getListChanges())
    {
      listDelta.accept(visitor);
    }

    visitor.finishPendingRemove();
  }

  /**
   * @author Stefan Winkler
   */
  private class ListDeltaVisitor implements CDOFeatureDeltaVisitor
  {
    private IDBStoreAccessor accessor;

    private CDOID id;

    private int oldVersion;

    private int newVersion;

    private int lastIndex;

    private int lastRemovedIndex;

    public ListDeltaVisitor(IDBStoreAccessor accessor, InternalCDORevision originalRevision, int oldVersion, int newVersion)
    {
      this.accessor = accessor;
      id = originalRevision.getID();
      this.oldVersion = oldVersion;
      this.newVersion = newVersion;
      lastIndex = originalRevision.getList(getFeature()).size() - 1;
      lastRemovedIndex = -1;
    }

    public void visit(CDOMoveFeatureDelta delta)
    {
      int fromIdx = delta.getOldPosition();
      int toIdx = delta.getNewPosition();

      // optimization: a move from the end of the list to an index that was just removed requires no shifting
      boolean optimizeMove = lastRemovedIndex != -1 && fromIdx == lastIndex - 1 && toIdx == lastRemovedIndex;

      if (TRACER.isEnabled())
      {
        TRACER.format("Delta Moving: {0} to {1}", fromIdx, toIdx); //$NON-NLS-1$
      }

      // items after a pending remove have an index offset by one
      if (optimizeMove)
      {
        fromIdx++;
      }
      else
      {
        finishPendingRemove();
      }

      Object value = getValue(accessor, id, fromIdx);

      // remove the item
      removeEntry(accessor, id, oldVersion, newVersion, fromIdx);

      // adjust indexes and shift either up or down
      if (!optimizeMove)
      {
        if (fromIdx < toIdx)
        {
          moveOneUp(accessor, id, oldVersion, newVersion, fromIdx + 1, toIdx);
        }
        else
        { // fromIdx > toIdx here
          moveOneDown(accessor, id, oldVersion, newVersion, toIdx, fromIdx - 1);
        }
      }
      else
      {
        // finish the optimized move by resetting lastRemovedIndex
        lastRemovedIndex = -1;
        --lastIndex;
      }

      // create the item
      addEntry(accessor, id, newVersion, toIdx, value);
    }

    public void visit(CDOAddFeatureDelta delta)
    {
      finishPendingRemove();
      int startIndex = delta.getIndex();
      int endIndex = lastIndex;

      if (TRACER.isEnabled())
      {
        TRACER.format("Delta Adding at: {0}", startIndex); //$NON-NLS-1$
      }

      if (startIndex <= endIndex)
      {
        // make room for the new item
        moveOneDown(accessor, id, oldVersion, newVersion, startIndex, endIndex);
      }

      // create the item
      addEntry(accessor, id, newVersion, startIndex, delta.getValue());

      ++lastIndex;
    }

    public void visit(CDORemoveFeatureDelta delta)
    {
      finishPendingRemove();
      lastRemovedIndex = delta.getIndex();

      if (TRACER.isEnabled())
      {
        TRACER.format("Delta Removing at: {0}", lastRemovedIndex); //$NON-NLS-1$
      }

      // remove the item
      removeEntry(accessor, id, oldVersion, newVersion, lastRemovedIndex);
    }

    public void visit(CDOSetFeatureDelta delta)
    {
      finishPendingRemove();
      int index = delta.getIndex();

      if (TRACER.isEnabled())
      {
        TRACER.format("Delta Setting at: {0}", index); //$NON-NLS-1$
      }

      // remove the item
      removeEntry(accessor, id, oldVersion, newVersion, index);

      // create the item
      addEntry(accessor, id, newVersion, index, delta.getValue());
    }

    public void visit(CDOUnsetFeatureDelta delta)
    {
      if (delta.getFeature().isUnsettable())
      {
        throw new ImplementationError("Should not be called"); //$NON-NLS-1$
      }

      if (TRACER.isEnabled())
      {
        TRACER.format("Delta Unsetting"); //$NON-NLS-1$
      }

      clearList(accessor, id, oldVersion, newVersion);
      lastIndex = -1;
      lastRemovedIndex = -1;
    }

    public void visit(CDOListFeatureDelta delta)
    {
      throw new ImplementationError("Should not be called"); //$NON-NLS-1$
    }

    public void visit(CDOClearFeatureDelta delta)
    {
      if (TRACER.isEnabled())
      {
        TRACER.format("Delta Clearing"); //$NON-NLS-1$
      }

      clearList(accessor, id, oldVersion, newVersion);
      lastIndex = -1;
      lastRemovedIndex = -1;
    }

    public void visit(CDOContainerFeatureDelta delta)
    {
      throw new ImplementationError("Should not be called"); //$NON-NLS-1$
    }

    public void finishPendingRemove()
    {
      if (lastRemovedIndex != -1)
      {
        int startIndex = lastRemovedIndex;
        int endIndex = lastIndex;

        // make room for the new item
        moveOneUp(accessor, id, oldVersion, newVersion, startIndex + 1, endIndex);

        --lastIndex;
        lastRemovedIndex = -1;
      }
    }

    private void moveOneUp(IDBStoreAccessor accessor, CDOID id, int oldVersion, int newVersion, int startIndex, int endIndex)
    {
      IIDHandler idHandler = getMappingStrategy().getStore().getIDHandler();
      IDBPreparedStatement stmt = accessor.getDBConnection().prepareStatement(sqlUpdateIndex, ReuseProbability.HIGH);

      try
      {
        for (int index = startIndex; index <= endIndex; ++index)
        {
          if (TRACER.isEnabled())
          {
            TRACER.format("moveOneUp moving: {0} -> {1}", index, index - 1); //$NON-NLS-1$
          }

          int column = 1;
          stmt.setInt(column++, index - 1);
          idHandler.setCDOID(stmt, column++, id);
          stmt.setInt(column++, newVersion);
          stmt.setInt(column++, index);

          int result = DBUtil.update(stmt, false);
          switch (result)
          {
          case 0:
            Object value = getValue(accessor, id, index);
            if (TRACER.isEnabled())
            {
              TRACER.format("moveOneUp remove: {0}", index); //$NON-NLS-1$
            }

            removeEntry(accessor, id, oldVersion, newVersion, index);
            if (TRACER.isEnabled())
            {
              TRACER.format("moveOneUp add: {0}", index - 1); //$NON-NLS-1$
            }

            addEntry(accessor, id, newVersion, index - 1, value);
            break;

          case 1:
            if (TRACER.isEnabled())
            {
              TRACER.format("moveOneUp updated: {0} -> {1}", index, index - 1); //$NON-NLS-1$
            }

            break;

          default:
            if (TRACER.isEnabled())
            {
              TRACER.format("moveOneUp Too many results: {0} -> {1}: {2}", index, index + 1, result); //$NON-NLS-1$
            }

            throw new DBException("Too many results"); //$NON-NLS-1$
          }
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

    private void moveOneDown(IDBStoreAccessor accessor, CDOID id, int oldVersion, int newVersion, int startIndex, int endIndex)
    {
      IIDHandler idHandler = getMappingStrategy().getStore().getIDHandler();
      IDBPreparedStatement stmt = accessor.getDBConnection().prepareStatement(sqlUpdateIndex, ReuseProbability.HIGH);

      try
      {
        for (int index = endIndex; index >= startIndex; --index)
        {
          if (TRACER.isEnabled())
          {
            TRACER.format("moveOneDown moving: {0} -> {1}", index, index + 1); //$NON-NLS-1$
          }

          int column = 1;
          stmt.setInt(column++, index + 1);
          idHandler.setCDOID(stmt, column++, id);
          stmt.setInt(column++, newVersion);
          stmt.setInt(column++, index);

          int result = DBUtil.update(stmt, false);
          switch (result)
          {
          case 0:
            Object value = getValue(accessor, id, index);
            if (TRACER.isEnabled())
            {
              TRACER.format("moveOneDown remove: {0}", index); //$NON-NLS-1$
            }

            removeEntry(accessor, id, oldVersion, newVersion, index);
            if (TRACER.isEnabled())
            {
              TRACER.format("moveOneDown add: {0}", index + 1); //$NON-NLS-1$
            }

            addEntry(accessor, id, newVersion, index + 1, value);
            break;

          case 1:
            if (TRACER.isEnabled())
            {
              TRACER.format("moveOneDown updated: {0} -> {1}", index, index + 1); //$NON-NLS-1$
            }

            break;

          default:
            if (TRACER.isEnabled())
            {
              TRACER.format("moveOneDown Too many results: {0} -> {1}: {2}", index, index + 1, result); //$NON-NLS-1$
            }

            throw new DBException("Too many results"); //$NON-NLS-1$
          }
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
  }
}

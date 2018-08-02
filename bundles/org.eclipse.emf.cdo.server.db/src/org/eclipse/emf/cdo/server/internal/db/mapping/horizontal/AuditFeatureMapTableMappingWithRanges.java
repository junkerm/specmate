/*
 * Copyright (c) 2010-2013, 2015, 2016 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 *    Stefan Winkler - Bug 271444: [DB] Multiple refactorings bug 271444
 *    Christopher Albert - Bug 254455: [DB] Support FeatureMaps bug 254455
 *    Victor Roldan Betancort - Bug 283998: [DB] Chunk reading for multiple chunks fails
 *    Lothar Werzinger - Bug 296440: [DB] Change RDB schema to improve scalability of to-many references in audit mode
 *    Stefan Winkler - cleanup, merge and maintenance
 *    Stefan Winkler - Bug 285426: [DB] Implement user-defined typeMapping support
 *    Stefan Winkler - Bug 329025: [DB] Support branching for range-based mapping strategy
 */
package org.eclipse.emf.cdo.server.internal.db.mapping.horizontal;

import org.eclipse.emf.cdo.common.branch.CDOBranch;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.id.CDOIDUtil;
import org.eclipse.emf.cdo.common.revision.CDOList;
import org.eclipse.emf.cdo.common.revision.CDORevision;
import org.eclipse.emf.cdo.common.revision.CDORevisionUtil;
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
import org.eclipse.emf.cdo.server.db.mapping.IListMappingDeltaSupport;
import org.eclipse.emf.cdo.server.db.mapping.IMappingStrategy;
import org.eclipse.emf.cdo.server.db.mapping.ITypeMapping;
import org.eclipse.emf.cdo.server.internal.db.bundle.OM;
import org.eclipse.emf.cdo.spi.common.revision.InternalCDORevision;

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
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.FeatureMap;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * This is a featuremap-table mapping for audit mode. It is optimized for frequent insert operations at the list's end,
 * which causes just 1 DB row to be changed. This is achieved by introducing a version range (columns
 * {@link IMappingConstants#LIST_REVISION_VERSION_ADDED cdo_version_added} and
 * {@link IMappingConstants#LIST_REVISION_VERSION_REMOVED cdo_version_removed}) which records for which revisions a particular
 * entry existed. Also, this mapping is mainly optimized for potentially very large lists: the need for having the
 * complete list stored in memory to do in-the-middle-moved and inserts is traded in for a few more DB access
 * operations.
 *
 * @author Eike Stepper
 * @author Stefan Winkler
 * @author Lothar Werzinger
 * @since 3.0
 */
public class AuditFeatureMapTableMappingWithRanges extends AbstractBasicListTableMapping implements IListMappingDeltaSupport
{
  private static final ContextTracer TRACER = new ContextTracer(OM.DEBUG, AuditFeatureMapTableMappingWithRanges.class);

  /**
   * Used to clean up lists for detached objects.
   */
  private static final int FINAL_VERSION = Integer.MAX_VALUE;

  /**
   * The table of this mapping.
   */
  private IDBTable table;

  /**
   * Column names.
   */
  private List<String> columnNames = new ArrayList<String>();

  /**
   * The type mappings for the value fields.
   */
  private Map<CDOID, ITypeMapping> typeMappings = CDOIDUtil.createMap();

  /**
   * The tags mapped to column names
   */
  private Map<CDOID, String> tagMap = CDOIDUtil.createMap();

  private List<DBType> dbTypes;

  // --------- SQL strings - see initSQLStrings() -----------------
  private String sqlSelectChunksPrefix;

  private String sqlOrderByIndex;

  private String sqlInsert;

  private String sqlRemoveEntry;

  private String sqlDeleteEntry;

  private String sqlUpdateIndex;

  private String sqlGetValue;

  private String sqlClearList;

  private String sqlDeleteList;

  public AuditFeatureMapTableMappingWithRanges(IMappingStrategy mappingStrategy, EClass eClass, EStructuralFeature feature)
  {
    super(mappingStrategy, eClass, feature);
    initDBTypes();
    initTable();
    initSQLStrings();
  }

  private void initDBTypes()
  {
    // TODO add annotation processing here ...
    ITypeMapping.Registry registry = ITypeMapping.Registry.INSTANCE;
    dbTypes = new ArrayList<DBType>(registry.getDefaultFeatureMapDBTypes());
  }

  private void initTable()
  {
    String tableName = getMappingStrategy().getTableName(getContainingClass(), getFeature());
    IDBStore store = getMappingStrategy().getStore();
    DBType idType = store.getIDHandler().getDBType();
    int idLength = store.getIDColumnLength();

    IDBDatabase database = getMappingStrategy().getStore().getDatabase();
    table = database.getSchema().getTable(tableName);
    if (table == null)
    {
      table = database.getSchemaTransaction().getWorkingCopy().addTable(tableName);
      table.addField(FEATUREMAP_REVISION_ID, idType, idLength);
      table.addField(FEATUREMAP_VERSION_ADDED, DBType.INTEGER);
      table.addField(FEATUREMAP_VERSION_REMOVED, DBType.INTEGER);
      table.addField(FEATUREMAP_IDX, DBType.INTEGER);
      table.addField(FEATUREMAP_TAG, idType, idLength);

      initTypeColumns(true);

      // TODO think about indices
      table.addIndex(Type.NON_UNIQUE, FEATUREMAP_REVISION_ID);
      table.addIndex(Type.NON_UNIQUE, FEATUREMAP_VERSION_ADDED);
      table.addIndex(Type.NON_UNIQUE, FEATUREMAP_VERSION_REMOVED);
      table.addIndex(Type.NON_UNIQUE, FEATUREMAP_IDX);
      table.addIndex(Type.NON_UNIQUE, FEATUREMAP_TAG);
    }
    else
    {
      initTypeColumns(false);
    }
  }

  private void initTypeColumns(boolean create)
  {
    for (DBType type : getDBTypes())
    {
      String column = FEATUREMAP_VALUE + "_" + type.name();
      if (create)
      {
        table.addField(column, type);
      }

      columnNames.add(column);
    }
  }

  private void initSQLStrings()
  {
    String tableName = getTable().getName();

    // ---------------- SELECT to read chunks ----------------------------
    StringBuilder builder = new StringBuilder();
    builder.append("SELECT "); //$NON-NLS-1$

    builder.append(FEATUREMAP_TAG);
    builder.append(", "); //$NON-NLS-1$

    Iterator<String> iter = columnNames.iterator();
    while (iter.hasNext())
    {
      builder.append(iter.next());
      if (iter.hasNext())
      {
        builder.append(", "); //$NON-NLS-1$
      }
    }

    builder.append(" FROM "); //$NON-NLS-1$
    builder.append(tableName);
    builder.append(" WHERE "); //$NON-NLS-1$
    builder.append(FEATUREMAP_REVISION_ID);
    builder.append("=? AND "); //$NON-NLS-1$
    builder.append(FEATUREMAP_VERSION_ADDED);
    builder.append("<=? AND ("); //$NON-NLS-1$
    builder.append(FEATUREMAP_VERSION_REMOVED);
    builder.append(" IS NULL OR "); //$NON-NLS-1$
    builder.append(FEATUREMAP_VERSION_REMOVED);
    builder.append(">?)"); //$NON-NLS-1$
    sqlSelectChunksPrefix = builder.toString();

    sqlOrderByIndex = " ORDER BY " + FEATUREMAP_IDX; //$NON-NLS-1$

    // ----------------- INSERT - prefix -----------------
    builder = new StringBuilder("INSERT INTO "); //$NON-NLS-1$
    builder.append(tableName);
    builder.append("("); //$NON-NLS-1$
    builder.append(FEATUREMAP_REVISION_ID);
    builder.append(", "); //$NON-NLS-1$
    builder.append(FEATUREMAP_VERSION_ADDED);
    builder.append(", "); //$NON-NLS-1$
    builder.append(FEATUREMAP_VERSION_REMOVED);
    builder.append(", "); //$NON-NLS-1$
    builder.append(FEATUREMAP_IDX);
    builder.append(", "); //$NON-NLS-1$
    builder.append(FEATUREMAP_TAG);

    for (int i = 0; i < columnNames.size(); i++)
    {
      builder.append(", "); //$NON-NLS-1$
      builder.append(columnNames.get(i));
    }

    builder.append(") VALUES (?, ?, ?, ?, ?"); //$NON-NLS-1$
    for (int i = 0; i < columnNames.size(); i++)
    {
      builder.append(", ?"); //$NON-NLS-1$
    }

    builder.append(")"); //$NON-NLS-1$
    sqlInsert = builder.toString();

    // ----------------- remove current entry -----------------
    builder = new StringBuilder("UPDATE "); //$NON-NLS-1$
    builder.append(tableName);
    builder.append(" SET "); //$NON-NLS-1$
    builder.append(FEATUREMAP_VERSION_REMOVED);
    builder.append("=? "); //$NON-NLS-1$
    builder.append(" WHERE "); //$NON-NLS-1$
    builder.append(FEATUREMAP_REVISION_ID);
    builder.append("=? AND "); //$NON-NLS-1$
    builder.append(FEATUREMAP_IDX);
    builder.append("=? AND "); //$NON-NLS-1$
    builder.append(FEATUREMAP_VERSION_REMOVED);
    builder.append(" IS NULL"); //$NON-NLS-1$
    sqlRemoveEntry = builder.toString();

    // ----------------- delete temporary entry -----------------
    builder = new StringBuilder("DELETE FROM "); //$NON-NLS-1$
    builder.append(tableName);
    builder.append(" WHERE "); //$NON-NLS-1$
    builder.append(FEATUREMAP_REVISION_ID);
    builder.append("=? AND "); //$NON-NLS-1$
    builder.append(FEATUREMAP_IDX);
    builder.append("=? AND "); //$NON-NLS-1$
    builder.append(FEATUREMAP_VERSION_ADDED);
    builder.append("=?"); //$NON-NLS-1$
    sqlDeleteEntry = builder.toString();

    // ----------------- update index -----------------
    builder = new StringBuilder("UPDATE "); //$NON-NLS-1$
    builder.append(tableName);
    builder.append(" SET "); //$NON-NLS-1$
    builder.append(FEATUREMAP_IDX);
    builder.append("=? WHERE "); //$NON-NLS-1$
    builder.append(FEATUREMAP_REVISION_ID);
    builder.append("=? AND "); //$NON-NLS-1$
    builder.append(FEATUREMAP_VERSION_ADDED);
    builder.append("=? AND "); //$NON-NLS-1$
    builder.append(FEATUREMAP_IDX);
    builder.append("=?"); //$NON-NLS-1$
    sqlUpdateIndex = builder.toString();

    // ----------------- get current value -----------------
    builder = new StringBuilder("SELECT "); //$NON-NLS-1$
    builder.append(FEATUREMAP_TAG);
    builder.append(", "); //$NON-NLS-1$

    iter = columnNames.iterator();
    while (iter.hasNext())
    {
      builder.append(iter.next());
      if (iter.hasNext())
      {
        builder.append(", "); //$NON-NLS-1$
      }
    }

    builder.append(" FROM "); //$NON-NLS-1$
    builder.append(tableName);
    builder.append(" WHERE "); //$NON-NLS-1$
    builder.append(FEATUREMAP_REVISION_ID);
    builder.append("=? AND "); //$NON-NLS-1$
    builder.append(FEATUREMAP_IDX);
    builder.append("=? AND "); //$NON-NLS-1$
    builder.append(FEATUREMAP_VERSION_REMOVED);
    builder.append(" IS NULL"); //$NON-NLS-1$
    sqlGetValue = builder.toString();

    // ----------- clear list items -------------------------
    builder = new StringBuilder("UPDATE "); //$NON-NLS-1$
    builder.append(tableName);
    builder.append(" SET "); //$NON-NLS-1$
    builder.append(FEATUREMAP_VERSION_REMOVED);
    builder.append("=? "); //$NON-NLS-1$
    builder.append(" WHERE "); //$NON-NLS-1$
    builder.append(FEATUREMAP_REVISION_ID);
    builder.append("=? AND "); //$NON-NLS-1$
    builder.append(FEATUREMAP_VERSION_REMOVED);
    builder.append(" IS NULL"); //$NON-NLS-1$
    sqlClearList = builder.toString();

    // ----------- delete temporary list items -------------------------
    builder = new StringBuilder("DELETE FROM "); //$NON-NLS-1$
    builder.append(tableName);
    builder.append(" WHERE "); //$NON-NLS-1$
    builder.append(FEATUREMAP_REVISION_ID);
    builder.append("=? AND "); //$NON-NLS-1$
    builder.append(FEATUREMAP_VERSION_ADDED);
    builder.append("=? AND "); //$NON-NLS-1$
    builder.append(FEATUREMAP_VERSION_REMOVED);
    builder.append(" IS NULL"); //$NON-NLS-1$
    sqlDeleteList = builder.toString();
  }

  protected List<DBType> getDBTypes()
  {
    return dbTypes;
  }

  public Collection<IDBTable> getDBTables()
  {
    return Collections.singleton(table);
  }

  protected final IDBTable getTable()
  {
    return table;
  }

  protected final List<String> getColumnNames()
  {
    return columnNames;
  }

  protected final Map<CDOID, ITypeMapping> getTypeMappings()
  {
    return typeMappings;
  }

  protected final Map<CDOID, String> getTagMap()
  {
    return tagMap;
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
      TRACER.format("Reading list values for feature {0}.{1} of {2}v{3}", getContainingClass().getName(), getFeature() //$NON-NLS-1$
          .getName(), revision.getID(), revision.getVersion());
    }

    String sql = sqlSelectChunksPrefix + sqlOrderByIndex;

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
        CDOID tag = idHandler.getCDOID(resultSet, 1);
        Object value = getTypeMapping(tag).readValue(resultSet);

        if (TRACER.isEnabled())
        {
          TRACER.format("Read value for index {0} from result set: {1}", currentIndex, value); //$NON-NLS-1$
        }

        list.set(currentIndex++, CDORevisionUtil.createFeatureMapEntry(getFeatureByTag(tag), value));
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

    if (TRACER.isEnabled())
    {
      TRACER.format("Reading list values done for feature {0}.{1} of {2}v{3}", getContainingClass().getName(), //$NON-NLS-1$
          getFeature().getName(), revision.getID(), revision.getVersion());
    }
  }

  private void addFeature(CDOID tag)
  {
    EStructuralFeature modelFeature = getFeatureByTag(tag);

    ITypeMapping typeMapping = getMappingStrategy().createValueMapping(modelFeature);
    String column = FEATUREMAP_VALUE + "_" + typeMapping.getDBType(); //$NON-NLS-1$

    tagMap.put(tag, column);
    typeMapping.setDBField(table, column);
    typeMappings.put(tag, typeMapping);
  }

  public final void readChunks(IDBStoreChunkReader chunkReader, List<Chunk> chunks, String where)
  {
    if (TRACER.isEnabled())
    {
      TRACER.format("Reading list chunk values for feature {0}.{1} of {2}v{3}", getContainingClass().getName(), //$NON-NLS-1$
          getFeature().getName(), chunkReader.getRevision().getID(), chunkReader.getRevision().getVersion());
    }

    StringBuilder builder = new StringBuilder(sqlSelectChunksPrefix);
    if (where != null)
    {
      builder.append(" AND "); //$NON-NLS-1$
      builder.append(where);
    }

    builder.append(sqlOrderByIndex);
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
        CDOID tag = idHandler.getCDOID(resultSet, 1);
        Object value = getTypeMapping(tag).readValue(resultSet);

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

        chunk.add(indexInChunk++, CDORevisionUtil.createFeatureMapEntry(getFeatureByTag(tag), value));
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
        TRACER.format("Reading list chunk values done for feature {0}.{1} of {2}", getContainingClass().getName(), //$NON-NLS-1$
            getFeature(), chunkReader.getRevision());
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

  protected final void writeValue(IDBStoreAccessor accessor, CDORevision revision, int idx, Object value)
  {
    if (TRACER.isEnabled())
    {
      TRACER.format("Writing value for feature {0}.{1} index {2} of {3} : {4}", getContainingClass().getName(), //$NON-NLS-1$
          getFeature(), idx, revision, value);
    }

    addEntry(accessor, revision.getID(), revision.getVersion(), idx, value, revision.getTimeStamp());
  }

  /**
   * Get column name (lazy).
   *
   * @param tag
   *          The feature's MetaID in CDO
   * @return the column name where the values are stored
   */
  protected String getColumnName(CDOID tag)
  {
    String column = tagMap.get(tag);
    if (column == null)
    {
      addFeature(tag);
      column = tagMap.get(tag);
    }

    return column;
  }

  /**
   * Get type mapping (lazy).
   *
   * @param tag
   *          The feature's MetaID in CDO
   * @return the corresponding type mapping
   */
  protected ITypeMapping getTypeMapping(CDOID tag)
  {
    ITypeMapping typeMapping = typeMappings.get(tag);
    if (typeMapping == null)
    {
      addFeature(tag);
      typeMapping = typeMappings.get(tag);
    }

    return typeMapping;
  }

  /**
   * @param metaID
   * @return the column name where the values are stored
   */
  private EStructuralFeature getFeatureByTag(CDOID tag)
  {
    return (EStructuralFeature)getMappingStrategy().getStore().getMetaDataManager().getMetaInstance(tag);
  }

  /**
   * @param feature
   *          The EStructuralFeature
   * @return The feature's MetaID in CDO
   */
  protected CDOID getTagByFeature(EStructuralFeature feature, long timestamp)
  {
    return getMappingStrategy().getStore().getMetaDataManager().getMetaID(feature, timestamp);
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
    ListDeltaVisitor visitor = new ListDeltaVisitor(accessor, originalRevision, oldVersion, newVersion, created);

    if (TRACER.isEnabled())
    {
      TRACER.format("Processing deltas..."); //$NON-NLS-1$
    }

    for (CDOFeatureDelta listDelta : delta.getListChanges())
    {
      listDelta.accept(visitor);
    }
  }

  private class ListDeltaVisitor implements CDOFeatureDeltaVisitor
  {
    private IDBStoreAccessor accessor;

    private InternalCDORevision originalRevision;

    private CDOID id;

    private int oldVersion;

    private int newVersion;

    private int lastIndex;

    private long timestamp;

    public ListDeltaVisitor(IDBStoreAccessor accessor, InternalCDORevision originalRevision, int oldVersion, int newVersion, long timestamp)
    {
      this.accessor = accessor;
      this.originalRevision = originalRevision;
      id = this.originalRevision.getID();
      this.oldVersion = oldVersion;
      this.newVersion = newVersion;
      lastIndex = originalRevision.getList(getFeature()).size() - 1;
      this.timestamp = timestamp;
    }

    public void visit(CDOMoveFeatureDelta delta)
    {
      int fromIdx = delta.getOldPosition();
      int toIdx = delta.getNewPosition();

      if (TRACER.isEnabled())
      {
        TRACER.format("Delta Moving: {0} to {1}", fromIdx, toIdx); //$NON-NLS-1$
      }

      Object value = getValue(accessor, id, fromIdx);

      // remove the item
      removeEntry(accessor, id, oldVersion, newVersion, fromIdx);

      // adjust indexes and shift either up or down
      if (fromIdx < toIdx)
      {
        moveOneUp(accessor, id, oldVersion, newVersion, fromIdx + 1, toIdx);
      }
      else
      { // fromIdx > toIdx here
        moveOneDown(accessor, id, oldVersion, newVersion, toIdx, fromIdx - 1);
      }

      // create the item
      addEntry(accessor, id, newVersion, toIdx, value, timestamp);
    }

    public void visit(CDOAddFeatureDelta delta)
    {
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
      addEntry(accessor, id, newVersion, startIndex, delta.getValue(), timestamp);

      ++lastIndex;
    }

    public void visit(CDORemoveFeatureDelta delta)
    {
      int startIndex = delta.getIndex();
      int endIndex = lastIndex;

      if (TRACER.isEnabled())
      {
        TRACER.format("Delta Removing at: {0}", startIndex); //$NON-NLS-1$
      }

      // remove the item
      removeEntry(accessor, id, oldVersion, newVersion, startIndex);

      // make room for the new item
      moveOneUp(accessor, id, oldVersion, newVersion, startIndex + 1, endIndex);

      --lastIndex;
    }

    public void visit(CDOSetFeatureDelta delta)
    {
      int index = delta.getIndex();

      if (TRACER.isEnabled())
      {
        TRACER.format("Delta Setting at: {0}", index); //$NON-NLS-1$
      }

      // remove the item
      removeEntry(accessor, id, oldVersion, newVersion, index);

      // create the item
      addEntry(accessor, id, newVersion, index, delta.getValue(), timestamp);
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
    }

    public void visit(CDOContainerFeatureDelta delta)
    {
      throw new ImplementationError("Should not be called"); //$NON-NLS-1$
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

            addEntry(accessor, id, newVersion, index - 1, value, timestamp);
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

            addEntry(accessor, id, newVersion, index + 1, value, timestamp);
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

  private void addEntry(IDBStoreAccessor accessor, CDOID id, int version, int index, Object value, long timestamp)
  {
    if (TRACER.isEnabled())
    {
      TRACER.format("Adding value for feature() {0}.{1} index {2} of {3}v{4} : {5}", //$NON-NLS-1$
          getContainingClass().getName(), getFeature().getName(), index, id, version, value);
    }

    FeatureMap.Entry entry = (FeatureMap.Entry)value;
    EStructuralFeature entryFeature = entry.getEStructuralFeature();
    CDOID tag = getTagByFeature(entryFeature, timestamp);
    ITypeMapping typeMapping = getTypeMapping(tag);
    String columnName = getColumnName(tag);

    IIDHandler idHandler = getMappingStrategy().getStore().getIDHandler();
    IDBPreparedStatement stmt = accessor.getDBConnection().prepareStatement(sqlInsert, ReuseProbability.HIGH);

    try
    {
      int column = 1;
      idHandler.setCDOID(stmt, column++, id);
      stmt.setInt(column++, version);
      stmt.setNull(column++, DBType.INTEGER.getCode()); // versionRemoved
      stmt.setInt(column++, index);
      idHandler.setCDOID(stmt, column++, tag);

      for (int i = 0; i < columnNames.size(); i++)
      {
        if (columnNames.get(i).equals(columnName))
        {
          typeMapping.setValue(stmt, column++, entry.getValue());
        }
        else
        {
          stmt.setNull(column++, getDBTypes().get(i).getCode());
        }
      }

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

  private FeatureMap.Entry getValue(IDBStoreAccessor accessor, CDOID id, int index)
  {
    IIDHandler idHandler = getMappingStrategy().getStore().getIDHandler();
    IDBPreparedStatement stmt = accessor.getDBConnection().prepareStatement(sqlGetValue, ReuseProbability.HIGH);

    try
    {
      int column = 1;
      idHandler.setCDOID(stmt, column++, id);
      stmt.setInt(column++, index);

      ResultSet resultSet = stmt.executeQuery();
      if (!resultSet.next())
      {
        throw new DBException("getValue expects exactly one result");
      }

      CDOID tag = idHandler.getCDOID(resultSet, 1);
      Object value = getTypeMapping(tag).readValue(resultSet);
      FeatureMap.Entry result = CDORevisionUtil.createFeatureMapEntry(getFeatureByTag(tag), value);

      if (TRACER.isEnabled())
      {
        TRACER.format("Read value (index {0}) from result set: {1}", index, result); //$NON-NLS-1$
      }

      return result;
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

  public final boolean queryXRefs(IDBStoreAccessor accessor, String mainTableName, String mainTableWhere, QueryXRefsContext context, String idString)
  {
    // must never be called (a feature map is not associated with an EReference feature, so XRefs are nor supported
    // here)
    throw new ImplementationError("Should never be called!");
  }
}

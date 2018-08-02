/*
 * Copyright (c) 2009-2013, 2015, 2016 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 *    Stefan Winkler - 271444: [DB] Multiple refactorings bug 271444
 *    Christopher Albert - 254455: [DB] Support FeatureMaps bug 254455
 *    Victor Roldan Betancort - Bug 283998: [DB] Chunk reading for multiple chunks fails
 *    Stefan Winkler - Bug 285426: [DB] Implement user-defined typeMapping support
 *    Stefan Winkler - Bug 329025: [DB] Support branching for range-based mapping strategy
 */
package org.eclipse.emf.cdo.server.internal.db.mapping.horizontal;

import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.id.CDOIDUtil;
import org.eclipse.emf.cdo.common.revision.CDOList;
import org.eclipse.emf.cdo.common.revision.CDORevision;
import org.eclipse.emf.cdo.common.revision.CDORevisionUtil;
import org.eclipse.emf.cdo.server.IStoreAccessor.QueryXRefsContext;
import org.eclipse.emf.cdo.server.IStoreChunkReader.Chunk;
import org.eclipse.emf.cdo.server.db.IDBStore;
import org.eclipse.emf.cdo.server.db.IDBStoreAccessor;
import org.eclipse.emf.cdo.server.db.IDBStoreChunkReader;
import org.eclipse.emf.cdo.server.db.IIDHandler;
import org.eclipse.emf.cdo.server.db.IMetaDataManager;
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
import org.eclipse.net4j.db.ddl.IDBField;
import org.eclipse.net4j.db.ddl.IDBIndex;
import org.eclipse.net4j.db.ddl.IDBIndex.Type;
import org.eclipse.net4j.db.ddl.IDBTable;
import org.eclipse.net4j.util.ImplementationError;
import org.eclipse.net4j.util.collection.MoveableList;
import org.eclipse.net4j.util.om.trace.ContextTracer;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.FeatureMap;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * This abstract base class provides basic behavior needed for mapping many-valued attributes to tables.
 *
 * @author Eike Stepper
 * @since 3.0
 */
public abstract class AbstractFeatureMapTableMapping extends AbstractBasicListTableMapping
{
  private static final ContextTracer TRACER = new ContextTracer(OM.DEBUG, AbstractFeatureMapTableMapping.class);

  /**
   * The table of this mapping.
   */
  private IDBTable table;

  private FieldInfo[] keyFields;

  /**
   * The tags mapped to column names.
   */
  private Map<CDOID, String> tagMap = CDOIDUtil.createMap();

  /**
   * Column names.
   */
  private List<String> columnNames = new ArrayList<String>();

  /**
   * The type mappings for the value fields.
   */
  private Map<CDOID, ITypeMapping> typeMappings = CDOIDUtil.createMap();

  // --------- SQL strings - see initSQLStrings() -----------------
  private String sqlSelectChunksPrefix;

  private String sqlOrderByIndex;

  protected String sqlInsert;

  private List<DBType> dbTypes;

  public AbstractFeatureMapTableMapping(IMappingStrategy mappingStrategy, EClass eClass, EStructuralFeature feature)
  {
    super(mappingStrategy, eClass, feature);
    initDBTypes();
    initTable();
    initSQLStrings();
  }

  private void initDBTypes()
  {
    // TODO add annotation processing here ...
    ITypeMapping.Registry registry = getTypeMappingRegistry();
    dbTypes = new ArrayList<DBType>(registry.getDefaultFeatureMapDBTypes());
  }

  protected ITypeMapping.Registry getTypeMappingRegistry()
  {
    return ITypeMapping.Registry.INSTANCE;
  }

  private void initTable()
  {
    String tableName = getMappingStrategy().getTableName(getContainingClass(), getFeature());
    DBType idType = getMappingStrategy().getStore().getIDHandler().getDBType();
    int idLength = getMappingStrategy().getStore().getIDColumnLength();

    IDBDatabase database = getMappingStrategy().getStore().getDatabase();
    table = database.getSchema().getTable(tableName);
    if (table == null)
    {
      table = database.getSchemaTransaction().getWorkingCopy().addTable(tableName);

      IDBIndex index = table.addIndexEmpty(Type.NON_UNIQUE);
      for (FieldInfo fieldInfo : getKeyFields())
      {
        IDBField field = table.addField(fieldInfo.getName(), fieldInfo.getType(), fieldInfo.getPrecision());
        index.addIndexField(field);
      }

      // Add field for list index
      table.addField(FEATUREMAP_IDX, DBType.INTEGER);

      // Add field for FeatureMap tag (MetaID for Feature in CDO registry)
      table.addField(FEATUREMAP_TAG, idType, idLength);

      // Create columns for all DBTypes
      initTypeColumns(true);

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
    FieldInfo[] fields = getKeyFields();

    // ---------------- SELECT to read chunks ----------------------------
    StringBuilder builder = new StringBuilder();
    builder.append("SELECT ");

    builder.append(FEATUREMAP_TAG);
    builder.append(", ");

    Iterator<String> iter = columnNames.iterator();
    while (iter.hasNext())
    {
      builder.append(iter.next());
      if (iter.hasNext())
      {
        builder.append(", ");
      }
    }

    builder.append(" FROM ");
    builder.append(tableName);
    builder.append(" WHERE ");

    for (int i = 0; i < fields.length; i++)
    {
      builder.append(fields[i].getName());
      if (i + 1 < fields.length)
      {
        // more to come
        builder.append("=? AND ");
      }
      else
      {
        // last one
        builder.append("=? ");
      }
    }

    sqlSelectChunksPrefix = builder.toString();

    sqlOrderByIndex = " ORDER BY " + FEATUREMAP_IDX; //$NON-NLS-1$

    // INSERT with dynamic field name
    // TODO: Better: universal INSERT-Statement, because of stmt caching!

    // ----------------- INSERT - prefix -----------------
    builder = new StringBuilder("INSERT INTO ");
    builder.append(tableName);
    builder.append(" ("); //$NON-NLS-1$
    for (int i = 0; i < fields.length; i++)
    {
      builder.append(fields[i].getName());
      builder.append(", "); //$NON-NLS-1$
    }

    for (int i = 0; i < columnNames.size(); i++)
    {
      builder.append(columnNames.get(i));
      builder.append(", "); //$NON-NLS-1$
    }

    builder.append(FEATUREMAP_IDX);
    builder.append(", "); //$NON-NLS-1$
    builder.append(FEATUREMAP_TAG);
    builder.append(") VALUES ("); //$NON-NLS-1$
    for (int i = 0; i < fields.length + columnNames.size(); i++)
    {
      builder.append("?, ");
    }

    builder.append("?, ?)");
    sqlInsert = builder.toString();
  }

  protected final FieldInfo[] getKeyFields()
  {
    if (keyFields == null)
    {
      List<FieldInfo> list = new ArrayList<FieldInfo>(3);

      IDBStore store = getMappingStrategy().getStore();
      DBType type = store.getIDHandler().getDBType();
      int precision = store.getIDColumnLength();
      list.add(new FieldInfo(FEATUREMAP_REVISION_ID, type, precision));

      addKeyFields(list);

      keyFields = list.toArray(new FieldInfo[list.size()]);
    }

    return keyFields;
  }

  protected abstract void addKeyFields(List<FieldInfo> list);

  protected abstract void setKeyFields(PreparedStatement stmt, CDORevision revision) throws SQLException;

  public Collection<IDBTable> getDBTables()
  {
    return Collections.singleton(table);
  }

  protected List<DBType> getDBTypes()
  {
    return dbTypes;
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
      TRACER.format("Reading list values for feature {0}.{1} of {2}v{3}", getContainingClass().getName(), getFeature().getName(), revision.getID(),
          revision.getVersion());
    }

    String sql = sqlSelectChunksPrefix + sqlOrderByIndex;

    IIDHandler idHandler = getMappingStrategy().getStore().getIDHandler();
    IDBPreparedStatement stmt = accessor.getDBConnection().prepareStatement(sql, ReuseProbability.HIGH);
    ResultSet resultSet = null;

    try
    {
      setKeyFields(stmt, revision);

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
          TRACER.format("Read value for index {0} from result set: {1}", currentIndex, value);
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
      TRACER.format("Reading list values done for feature {0}.{1} of {2}v{3}", getContainingClass().getName(), getFeature().getName(), revision.getID(),
          revision.getVersion());
    }
  }

  private void addFeature(CDOID tag)
  {
    EStructuralFeature modelFeature = getFeatureByTag(tag);

    ITypeMapping typeMapping = getMappingStrategy().createValueMapping(modelFeature);
    String column = FEATUREMAP_VALUE + "_" + typeMapping.getDBType();

    tagMap.put(tag, column);
    typeMapping.setDBField(table, column);
    typeMappings.put(tag, typeMapping);
  }

  public final void readChunks(IDBStoreChunkReader chunkReader, List<Chunk> chunks, String where)
  {
    if (TRACER.isEnabled())
    {
      TRACER.format("Reading list chunk values for feature {0}.{1} of {2}v{3}", getContainingClass().getName(), getFeature().getName(),
          chunkReader.getRevision().getID(), chunkReader.getRevision().getVersion());
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
      setKeyFields(stmt, chunkReader.getRevision());

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
            TRACER.format("Current chunk no. {0} is [start = {1}, size = {2}]", chunkIndex - 1, chunk.getStartIndex(), chunkSize);
          }
        }

        if (TRACER.isEnabled())
        {
          TRACER.format("Read value for chunk index {0} from result set: {1}", indexInChunk, value);
        }

        chunk.add(indexInChunk++, CDORevisionUtil.createFeatureMapEntry(getFeatureByTag(tag), value));
        if (indexInChunk == chunkSize)
        {
          if (TRACER.isEnabled())
          {
            TRACER.format("Chunk finished");
          }

          chunk = null;
          indexInChunk = 0;
        }
      }

      if (TRACER.isEnabled())
      {
        TRACER.format("Reading list chunk values done for feature {0}.{1} of {2}", getContainingClass().getName(), getFeature(), chunkReader.getRevision());
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
  }

  protected final void writeValue(IDBStoreAccessor accessor, CDORevision revision, int idx, Object value)
  {
    if (TRACER.isEnabled())
    {
      TRACER.format("Writing value for feature {0}.{1} index {2} of {3} : {4}", getContainingClass().getName(), //$NON-NLS-1$
          getFeature(), idx, revision, value);
    }

    FeatureMap.Entry entry = (FeatureMap.Entry)value;
    EStructuralFeature entryFeature = entry.getEStructuralFeature();
    CDOID tag = getTagByFeature(entryFeature, revision.getTimeStamp());
    ITypeMapping typeMapping = getTypeMapping(tag);
    String columnName = getColumnName(tag);

    IIDHandler idHandler = getMappingStrategy().getStore().getIDHandler();
    IDBPreparedStatement stmt = accessor.getDBConnection().prepareStatement(sqlInsert, ReuseProbability.HIGH);

    try
    {
      setKeyFields(stmt, revision);
      int column = getKeyFields().length + 1;

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

      stmt.setInt(column++, idx);
      idHandler.setCDOID(stmt, column++, tag);
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
  }

  /**
   * Get column name (lazy)
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
   * Get type mapping (lazy)
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
    IMetaDataManager metaDataManager = getMappingStrategy().getStore().getMetaDataManager();
    return (EStructuralFeature)metaDataManager.getMetaInstance(tag);
  }

  /**
   * @param feature
   *          The EStructuralFeature
   * @return The feature's MetaID in CDO
   */
  protected CDOID getTagByFeature(EStructuralFeature feature, long timeStamp)
  {
    IMetaDataManager metaDataManager = getMappingStrategy().getStore().getMetaDataManager();
    return metaDataManager.getMetaID(feature, timeStamp);
  }

  public final boolean queryXRefs(IDBStoreAccessor accessor, String mainTableName, String mainTableWhere, QueryXRefsContext context, String idString)
  {
    /*
     * must never be called (a feature map is not associated with an EReference feature, so XRefs are nor supported
     * here)
     */
    throw new ImplementationError("Should never be called!");
  }

}

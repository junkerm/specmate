/*
 * Copyright (c) 2009-2013, 2015, 2016 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 *    Stefan Winkler - Bug 271444: [DB] Multiple refactorings
 *    Stefan Winkler - Bug 283998: [DB] Chunk reading for multiple chunks fails
 *    Stefan Winkler - Bug 329025: [DB] Support branching for range-based mapping strategy
 */
package org.eclipse.emf.cdo.server.internal.db.mapping.horizontal;

import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.model.CDOFeatureType;
import org.eclipse.emf.cdo.common.revision.CDOList;
import org.eclipse.emf.cdo.common.revision.CDORevision;
import org.eclipse.emf.cdo.server.IStoreAccessor.QueryXRefsContext;
import org.eclipse.emf.cdo.server.IStoreChunkReader.Chunk;
import org.eclipse.emf.cdo.server.db.IDBStore;
import org.eclipse.emf.cdo.server.db.IDBStoreAccessor;
import org.eclipse.emf.cdo.server.db.IDBStoreChunkReader;
import org.eclipse.emf.cdo.server.db.IIDHandler;
import org.eclipse.emf.cdo.server.db.mapping.IMappingStrategy;
import org.eclipse.emf.cdo.server.db.mapping.ITypeMapping;
import org.eclipse.emf.cdo.server.internal.db.bundle.OM;
import org.eclipse.emf.cdo.server.internal.db.mapping.AbstractMappingStrategy;
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
import org.eclipse.net4j.util.collection.MoveableList;
import org.eclipse.net4j.util.om.trace.ContextTracer;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * This abstract base class provides basic behavior needed for mapping many-valued attributes to tables.
 *
 * @author Eike Stepper
 * @since 2.0
 */
public abstract class AbstractListTableMapping extends AbstractBasicListTableMapping
{
  private static final ContextTracer TRACER = new ContextTracer(OM.DEBUG, AbstractListTableMapping.class);

  /**
   * The table of this mapping.
   */
  private IDBTable table;

  private FieldInfo[] keyFields;

  /**
   * The type mapping for the value field.
   */
  private ITypeMapping typeMapping;

  // --------- SQL strings - see initSQLStrings() -----------------
  private String sqlSelectChunksPrefix;

  private String sqlOrderByIndex;

  private String sqlInsertEntry;

  public AbstractListTableMapping(IMappingStrategy mappingStrategy, EClass eClass, EStructuralFeature feature)
  {
    super(mappingStrategy, eClass, feature);
    initTable();
    initSQLStrings();
  }

  private void initTable()
  {
    IMappingStrategy mappingStrategy = getMappingStrategy();
    EStructuralFeature feature = getFeature();

    String tableName = mappingStrategy.getTableName(getContainingClass(), feature);
    typeMapping = mappingStrategy.createValueMapping(feature);

    IDBDatabase database = mappingStrategy.getStore().getDatabase();
    table = database.getSchema().getTable(tableName);
    if (table == null)
    {
      table = database.getSchemaTransaction().getWorkingCopy().addTable(tableName);

      IDBIndex primaryKey = table.addIndexEmpty(Type.PRIMARY_KEY);
      for (FieldInfo info : getKeyFields())
      {
        IDBField field = table.addField(info.getName(), info.getType(), info.getPrecision(), true);
        primaryKey.addIndexField(field);
      }

      // Add field for list index
      IDBField field = table.addField(LIST_IDX, DBType.INTEGER, true);
      primaryKey.addIndexField(field);

      // Add field for value
      typeMapping.createDBField(table, LIST_VALUE);
    }
    else
    {
      typeMapping.setDBField(table, LIST_VALUE);
    }

    Set<CDOFeatureType> forceIndexes = AbstractMappingStrategy.getForceIndexes(mappingStrategy);
    if (CDOFeatureType.matchesCombination(feature, forceIndexes))
    {
      IDBField field = table.getField(LIST_VALUE);

      if (!table.hasIndexFor(field))
      {
        IDBIndex index = table.addIndex(IDBIndex.Type.NON_UNIQUE, field);
        DBUtil.setOptional(index, true); // Creation might fail for unsupported column type!
      }
    }
  }

  private void initSQLStrings()
  {
    String tableName = table.getName();
    FieldInfo[] fields = getKeyFields();

    // ---------------- SELECT to read chunks ----------------------------
    StringBuilder builder = new StringBuilder();
    builder.append("SELECT "); //$NON-NLS-1$
    builder.append(LIST_VALUE);
    builder.append(" FROM "); //$NON-NLS-1$
    builder.append(tableName);
    builder.append(" WHERE "); //$NON-NLS-1$

    for (int i = 0; i < fields.length; i++)
    {
      builder.append(fields[i].getName());
      if (i + 1 < fields.length)
      {
        // more to come
        builder.append("=? AND "); //$NON-NLS-1$
      }
      else
      {
        // last one
        builder.append("=? "); //$NON-NLS-1$
      }
    }

    sqlSelectChunksPrefix = builder.toString();

    sqlOrderByIndex = " ORDER BY " + LIST_IDX; //$NON-NLS-1$

    // ----------------- INSERT - reference entry -----------------
    builder = new StringBuilder("INSERT INTO "); //$NON-NLS-1$
    builder.append(tableName);
    builder.append("("); //$NON-NLS-1$

    for (int i = 0; i < fields.length; i++)
    {
      builder.append(fields[i].getName());
      builder.append(", "); //$NON-NLS-1$
    }

    builder.append(LIST_IDX);
    builder.append(", "); //$NON-NLS-1$
    builder.append(LIST_VALUE);
    builder.append(") VALUES ("); //$NON-NLS-1$
    for (int i = 0; i < fields.length; i++)
    {
      builder.append("?, "); //$NON-NLS-1$
    }

    builder.append(" ?, ?)"); //$NON-NLS-1$
    sqlInsertEntry = builder.toString();
  }

  protected final FieldInfo[] getKeyFields()
  {
    if (keyFields == null)
    {
      List<FieldInfo> list = new ArrayList<FieldInfo>(3);

      IDBStore store = getMappingStrategy().getStore();
      DBType type = store.getIDHandler().getDBType();
      int precision = store.getIDColumnLength();
      list.add(new FieldInfo(LIST_REVISION_ID, type, precision));

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
      // Nothing to read. Take shortcut.
      return;
    }

    if (TRACER.isEnabled())
    {
      TRACER.format("Reading list values for feature {0}.{1} of {2}v{3}", getContainingClass().getName(), //$NON-NLS-1$
          getFeature().getName(), revision.getID(), revision.getVersion());
    }

    String sql = sqlSelectChunksPrefix + sqlOrderByIndex;
    IDBPreparedStatement stmt = accessor.getDBConnection().prepareStatement(sql, ReuseProbability.HIGH);
    ResultSet resultSet = null;

    try
    {
      setKeyFields(stmt, revision);

      if (TRACER.isEnabled())
      {
        TRACER.trace(stmt.toString());
      }

      if (listChunk != CDORevision.UNCHUNKED)
      {
        if (stmt.getMaxRows() != listChunk)
        {
          stmt.setMaxRows(listChunk); // optimization - don't read unneeded rows.
        }
      }
      else
      {
        if (stmt.getMaxRows() != 0)
        {
          stmt.setMaxRows(0); // No limit.
        }
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
        TRACER.format("Reading list chunk values done for feature {0}.{1} of {2}v{3}", getContainingClass().getName(), //$NON-NLS-1$
            getFeature().getName(), chunkReader.getRevision().getID(), chunkReader.getRevision().getVersion());
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
      TRACER.format("Writing value for feature {0}.{1} index {2} of {3}v{4} : {5}", getContainingClass().getName(), getFeature().getName(), idx,
          revision.getID(), revision.getVersion(), value);
    }

    IDBPreparedStatement stmt = accessor.getDBConnection().prepareStatement(sqlInsertEntry, ReuseProbability.HIGH);

    try
    {
      setKeyFields(stmt, revision);
      int column = getKeyFields().length + 1;
      stmt.setInt(column++, idx);
      typeMapping.setValue(stmt, column++, value);
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

  public boolean queryXRefs(IDBStoreAccessor accessor, String mainTableName, String mainTableWhere, QueryXRefsContext context, String idString)
  {
    String tableName = table.getName();
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
    builder.append("a_t." + mainTableWhere);//$NON-NLS-1$
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
        CDOID srcId = idHandler.getCDOID(resultSet, 1);
        CDOID targetId = idHandler.getCDOID(resultSet, 2);
        int idx = resultSet.getInt(3);

        if (TRACER.isEnabled())
        {
          TRACER.format("  add XRef to context: src={0}, tgt={1}, idx={2}", srcId, targetId, idx);
        }

        boolean more = context.addXRef(targetId, srcId, (EReference)getFeature(), idx);
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
}

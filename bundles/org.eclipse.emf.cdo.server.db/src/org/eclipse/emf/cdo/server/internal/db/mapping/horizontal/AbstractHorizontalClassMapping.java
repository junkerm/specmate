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
 *    Stefan Winkler - 249610: [DB] Support external references (Implementation)
 *    Stefan Winkler - Bug 329025: [DB] Support branching for range-based mapping strategy
 */
package org.eclipse.emf.cdo.server.internal.db.mapping.horizontal;

import org.eclipse.emf.cdo.common.branch.CDOBranch;
import org.eclipse.emf.cdo.common.branch.CDOBranchManager;
import org.eclipse.emf.cdo.common.branch.CDOBranchPoint;
import org.eclipse.emf.cdo.common.branch.CDOBranchVersion;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.model.CDOFeatureType;
import org.eclipse.emf.cdo.common.model.CDOModelUtil;
import org.eclipse.emf.cdo.common.revision.CDOList;
import org.eclipse.emf.cdo.common.revision.CDORevision;
import org.eclipse.emf.cdo.common.revision.CDORevisionHandler;
import org.eclipse.emf.cdo.common.revision.CDORevisionManager;
import org.eclipse.emf.cdo.eresource.EresourcePackage;
import org.eclipse.emf.cdo.server.IRepository;
import org.eclipse.emf.cdo.server.IStoreAccessor.QueryXRefsContext;
import org.eclipse.emf.cdo.server.db.IDBStoreAccessor;
import org.eclipse.emf.cdo.server.db.IIDHandler;
import org.eclipse.emf.cdo.server.db.mapping.IClassMapping;
import org.eclipse.emf.cdo.server.db.mapping.IListMapping;
import org.eclipse.emf.cdo.server.db.mapping.IListMapping3;
import org.eclipse.emf.cdo.server.db.mapping.IMappingStrategy;
import org.eclipse.emf.cdo.server.db.mapping.ITypeMapping;
import org.eclipse.emf.cdo.server.internal.db.DBIndexAnnotation;
import org.eclipse.emf.cdo.server.internal.db.bundle.OM;
import org.eclipse.emf.cdo.server.internal.db.mapping.AbstractMappingStrategy;
import org.eclipse.emf.cdo.spi.common.commit.CDOChangeSetSegment;
import org.eclipse.emf.cdo.spi.common.revision.DetachedCDORevision;
import org.eclipse.emf.cdo.spi.common.revision.InternalCDOList;
import org.eclipse.emf.cdo.spi.common.revision.InternalCDORevision;

import org.eclipse.net4j.db.DBException;
import org.eclipse.net4j.db.DBType;
import org.eclipse.net4j.db.DBUtil;
import org.eclipse.net4j.db.IDBDatabase;
import org.eclipse.net4j.db.IDBPreparedStatement;
import org.eclipse.net4j.db.IDBPreparedStatement.ReuseProbability;
import org.eclipse.net4j.db.IDBSchemaTransaction;
import org.eclipse.net4j.db.ddl.IDBField;
import org.eclipse.net4j.db.ddl.IDBIndex;
import org.eclipse.net4j.db.ddl.IDBSchema;
import org.eclipse.net4j.db.ddl.IDBTable;
import org.eclipse.net4j.util.lifecycle.IDeactivateable;
import org.eclipse.net4j.util.om.monitor.OMMonitor;
import org.eclipse.net4j.util.om.monitor.OMMonitor.Async;
import org.eclipse.net4j.util.om.trace.ContextTracer;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.FeatureMapUtil;

import org.eclipse.core.runtime.Assert;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Eike Stepper
 * @since 2.0
 */
public abstract class AbstractHorizontalClassMapping implements IClassMapping, IMappingConstants, IDeactivateable
{
  private static final ContextTracer TRACER = new ContextTracer(OM.DEBUG, AbstractHorizontalClassMapping.class);

  private EClass eClass;

  private IDBTable table;

  private AbstractHorizontalMappingStrategy mappingStrategy;

  private List<ITypeMapping> valueMappings;

  private List<IListMapping> listMappings;

  private Map<EStructuralFeature, IDBField> listSizeFields;

  private Map<EStructuralFeature, IDBField> unsettableFields;

  private String sqlSelectForHandle;

  private String sqlSelectForChangeSet;

  public AbstractHorizontalClassMapping(AbstractHorizontalMappingStrategy mappingStrategy, EClass eClass)
  {
    this.mappingStrategy = mappingStrategy;
    this.eClass = eClass;

    initTable();
    initFields();
    initSQLStrings();
  }

  private void initTable()
  {
    final String tableName = mappingStrategy.getTableName(eClass);
    final DBType idType = mappingStrategy.getStore().getIDHandler().getDBType();
    final int idLength = mappingStrategy.getStore().getIDColumnLength();

    IDBDatabase database = mappingStrategy.getStore().getDatabase();
    table = database.getSchema().getTable(tableName);
    if (table == null)
    {
      IDBSchemaTransaction schemaTransaction = database.getSchemaTransaction();
      IDBSchema workingCopy = schemaTransaction.getWorkingCopy();

      table = workingCopy.addTable(tableName);
      table.addField(ATTRIBUTES_ID, idType, idLength, true);
      table.addField(ATTRIBUTES_VERSION, DBType.INTEGER, true);

      IDBField branchField = addBranchField(table);

      table.addField(ATTRIBUTES_CREATED, DBType.BIGINT, true);
      table.addField(ATTRIBUTES_REVISED, DBType.BIGINT, true);
      table.addField(ATTRIBUTES_RESOURCE, idType, idLength, true);
      addContainerField(table, idType, idLength);
      table.addField(ATTRIBUTES_FEATURE, DBType.INTEGER, true);

      IDBIndex primaryKey = table.addIndex(IDBIndex.Type.PRIMARY_KEY, ATTRIBUTES_ID, ATTRIBUTES_VERSION);
      if (branchField != null)
      {
        primaryKey.addIndexField(branchField);
      }

      table.addIndex(IDBIndex.Type.NON_UNIQUE, ATTRIBUTES_REVISED);
    }
  }

  private void initFields()
  {
    final EStructuralFeature[] allPersistentFeatures = CDOModelUtil.getClassInfo(eClass).getAllPersistentFeatures();
    if (allPersistentFeatures == null)
    {
      valueMappings = Collections.emptyList();
      listMappings = Collections.emptyList();
    }
    else
    {
      valueMappings = new ArrayList<ITypeMapping>();
      listMappings = new ArrayList<IListMapping>();

      boolean hasUnsettableFeatures = false;
      for (EStructuralFeature feature : allPersistentFeatures)
      {
        String fieldName = mappingStrategy.getFieldName(feature);
        if (feature.isMany())
        {
          IListMapping mapping = null;
          if (FeatureMapUtil.isFeatureMap(feature))
          {
            mapping = mappingStrategy.createFeatureMapMapping(eClass, feature);
          }
          else
          {
            mapping = mappingStrategy.createListMapping(eClass, feature);
          }

          if (mapping == null)
          {
            continue;
          }

          if (mapping instanceof IListMapping3)
          {
            ((IListMapping3)mapping).setClassMapping(this);
          }

          listMappings.add(mapping);

          // Add field for list sizes
          if (listSizeFields == null)
          {
            listSizeFields = new LinkedHashMap<EStructuralFeature, IDBField>();
          }

          IDBField field = table.getField(fieldName);
          if (field == null)
          {
            field = table.addField(fieldName, DBType.INTEGER);
          }

          listSizeFields.put(feature, field);
        }
        else
        {
          ITypeMapping mapping = mappingStrategy.createValueMapping(feature);
          IDBField field = table.getField(fieldName);
          if (field == null)
          {
            mapping.createDBField(table, fieldName);
          }
          else
          {
            mapping.setDBField(table, fieldName);
          }

          valueMappings.add(mapping);

          Set<CDOFeatureType> forceIndexes = AbstractMappingStrategy.getForceIndexes(mappingStrategy);
          if (CDOFeatureType.matchesCombination(feature, forceIndexes))
          {
            if (field == null)
            {
              field = table.getField(fieldName);
            }

            if (!table.hasIndexFor(field))
            {
              IDBIndex index = table.addIndex(IDBIndex.Type.NON_UNIQUE, field);
              DBUtil.setOptional(index, true); // Creation might fail for unsupported column type!
            }
          }

          if (feature.isUnsettable())
          {
            hasUnsettableFeatures = true;
          }
        }
      }

      // Add unsettable fields to end of table
      if (hasUnsettableFeatures)
      {
        unsettableFields = new LinkedHashMap<EStructuralFeature, IDBField>();
        for (EStructuralFeature feature : allPersistentFeatures)
        {
          if (!feature.isMany() && feature.isUnsettable())
          {
            String fieldName = mappingStrategy.getUnsettableFieldName(feature);
            IDBField field = table.getField(fieldName);
            if (field == null)
            {
              field = table.addField(fieldName, DBType.BOOLEAN);
            }

            unsettableFields.put(feature, field);
          }
        }
      }

      initIndices(allPersistentFeatures);
    }
  }

  private void initIndices(EStructuralFeature[] allPersistentFeatures)
  {
    for (List<EStructuralFeature> features : DBIndexAnnotation.getIndices(eClass, allPersistentFeatures))
    {
      int size = features.size();
      if (size > 1)
      {
        IDBField[] fields = new IDBField[size];

        for (int i = 0; i < size; i++)
        {
          EStructuralFeature feature = features.get(i);

          ITypeMapping typeMapping = getValueMapping(feature);
          IDBField field = typeMapping.getField();
          fields[i] = field;
        }

        if (!table.hasIndexFor(fields))
        {
          IDBIndex index = table.addIndex(IDBIndex.Type.NON_UNIQUE, fields);
          DBUtil.setOptional(index, true); // Creation might fail for unsupported column type!
        }
      }
      else
      {
        EStructuralFeature feature = features.get(0);
        if (feature.isMany())
        {
          IListMapping listMapping = getListMapping(feature);
          if (listMapping instanceof AbstractListTableMapping)
          {
            AbstractListTableMapping mapping = (AbstractListTableMapping)listMapping;

            IDBTable table = mapping.getDBTables().iterator().next();
            ITypeMapping typeMapping = mapping.getTypeMapping();
            IDBField field = typeMapping.getField();

            if (!table.hasIndexFor(field))
            {
              IDBIndex index = table.addIndex(IDBIndex.Type.NON_UNIQUE, field);
              DBUtil.setOptional(index, true); // Creation might fail for unsupported column type!
            }
          }
        }
        else
        {
          ITypeMapping typeMapping = getValueMapping(feature);
          IDBField field = typeMapping.getField();

          if (!table.hasIndexFor(field))
          {
            IDBIndex index = table.addIndex(IDBIndex.Type.NON_UNIQUE, field);
            DBUtil.setOptional(index, true); // Creation might fail for unsupported column type!
          }
        }
      }
    }
  }

  private void initSQLStrings()
  {
    // ----------- Select all revisions (for handleRevisions) ---
    StringBuilder builder = new StringBuilder("SELECT "); //$NON-NLS-1$
    builder.append(ATTRIBUTES_ID);
    builder.append(", "); //$NON-NLS-1$
    builder.append(ATTRIBUTES_VERSION);
    builder.append(" FROM "); //$NON-NLS-1$
    builder.append(table);
    sqlSelectForHandle = builder.toString();

    // ----------- Select all revisions (for readChangeSet) ---
    builder = new StringBuilder("SELECT DISTINCT "); //$NON-NLS-1$
    builder.append(ATTRIBUTES_ID);
    builder.append(" FROM "); //$NON-NLS-1$
    builder.append(table);
    builder.append(" WHERE "); //$NON-NLS-1$
    sqlSelectForChangeSet = builder.toString();
  }

  protected IDBField addContainerField(IDBTable table, DBType idType, int idLength)
  {
    return table.addField(ATTRIBUTES_CONTAINER, idType, idLength, true);
  }

  protected IDBField addBranchField(IDBTable table)
  {
    return null;
  }

  /**
   * Read the revision's values from the DB.
   *
   * @return <code>true</code> if the revision has been read successfully.<br>
   *         <code>false</code> if the revision does not exist in the DB.
   */
  protected final boolean readValuesFromStatement(PreparedStatement stmt, InternalCDORevision revision, IDBStoreAccessor accessor)
  {
    ResultSet resultSet = null;

    try
    {
      if (TRACER.isEnabled())
      {
        TRACER.format("Executing Query: {0}", stmt.toString()); //$NON-NLS-1$
      }

      stmt.setMaxRows(1); // Optimization: only 1 row
      resultSet = stmt.executeQuery();

      IIDHandler idHandler = getMappingStrategy().getStore().getIDHandler();
      if (!readValuesFromResultSet(resultSet, idHandler, revision, false))
      {
        if (TRACER.isEnabled())
        {
          TRACER.format("Resultset was empty"); //$NON-NLS-1$
        }

        return false;
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
    }
  }

  /**
   * Read the revision's values from the DB.
   *
   * @return <code>true</code> if the revision has been read successfully.<br>
   *         <code>false</code> if the revision does not exist in the DB.
   */
  protected final boolean readValuesFromResultSet(ResultSet resultSet, IIDHandler idHandler, InternalCDORevision revision, boolean forUnit)
  {
    try
    {
      if (resultSet.next())
      {
        long timeStamp = resultSet.getLong(ATTRIBUTES_CREATED);
        CDOBranchPoint branchPoint = revision.getBranch().getPoint(timeStamp);

        if (forUnit)
        {
          revision.setID(idHandler.getCDOID(resultSet, ATTRIBUTES_ID));
        }

        revision.setBranchPoint(branchPoint);
        revision.setVersion(resultSet.getInt(ATTRIBUTES_VERSION));
        revision.setRevised(resultSet.getLong(ATTRIBUTES_REVISED));
        revision.setResourceID(idHandler.getCDOID(resultSet, ATTRIBUTES_RESOURCE));
        revision.setContainerID(idHandler.getCDOID(resultSet, ATTRIBUTES_CONTAINER));
        revision.setContainingFeatureID(resultSet.getInt(ATTRIBUTES_FEATURE));

        for (ITypeMapping mapping : valueMappings)
        {
          EStructuralFeature feature = mapping.getFeature();
          if (feature.isUnsettable())
          {
            IDBField field = unsettableFields.get(feature);
            if (!resultSet.getBoolean(field.getName()))
            {
              // isSet==false -- setValue: null
              revision.setValue(feature, null);
              continue;
            }
          }

          mapping.readValueToRevision(resultSet, revision);
        }

        if (listSizeFields != null)
        {
          for (Map.Entry<EStructuralFeature, IDBField> listSizeEntry : listSizeFields.entrySet())
          {
            EStructuralFeature feature = listSizeEntry.getKey();
            IDBField field = listSizeEntry.getValue();
            int size = resultSet.getInt(field.getName());

            // ensure the listSize (TODO: remove assertion)
            CDOList list = revision.getList(feature, size);

            for (int i = 0; i < size; i++)
            {
              list.add(InternalCDOList.UNINITIALIZED);
            }

            if (list.size() != size)
            {
              Assert.isTrue(false);
            }
          }
        }

        return true;
      }

      return false;
    }
    catch (SQLException ex)
    {
      throw new DBException(ex);
    }
  }

  protected final void readLists(IDBStoreAccessor accessor, InternalCDORevision revision, int listChunk)
  {
    for (IListMapping listMapping : listMappings)
    {
      listMapping.readValues(accessor, revision, listChunk);
    }
  }

  protected final IMappingStrategy getMappingStrategy()
  {
    return mappingStrategy;
  }

  public final EClass getEClass()
  {
    return eClass;
  }

  protected final Map<EStructuralFeature, IDBField> getUnsettableFields()
  {
    return unsettableFields;
  }

  protected final Map<EStructuralFeature, IDBField> getListSizeFields()
  {
    return listSizeFields;
  }

  public final List<ITypeMapping> getValueMappings()
  {
    return valueMappings;
  }

  public final ITypeMapping getValueMapping(EStructuralFeature feature)
  {
    for (ITypeMapping mapping : valueMappings)
    {
      if (mapping.getFeature() == feature)
      {
        return mapping;
      }
    }

    return null;
  }

  public final List<IListMapping> getListMappings()
  {
    return listMappings;
  }

  public final IListMapping getListMapping(EStructuralFeature feature)
  {
    for (IListMapping mapping : listMappings)
    {
      if (mapping.getFeature() == feature)
      {
        return mapping;
      }
    }

    throw new IllegalArgumentException("List mapping for feature " + feature + " does not exist"); //$NON-NLS-1$ //$NON-NLS-2$
  }

  protected final IDBTable getTable()
  {
    return table;
  }

  public List<IDBTable> getDBTables()
  {
    List<IDBTable> tables = new ArrayList<IDBTable>();
    tables.add(table);

    for (IListMapping listMapping : listMappings)
    {
      tables.addAll(listMapping.getDBTables());
    }

    return tables;
  }

  @Override
  public String toString()
  {
    return MessageFormat.format("{0}[{1} -> {2}]", getClass().getSimpleName(), eClass, table);
  }

  protected void checkDuplicateResources(IDBStoreAccessor accessor, CDORevision revision) throws IllegalStateException
  {
    CDOID folderID = (CDOID)revision.data().getContainerID();
    String name = (String)revision.data().get(EresourcePackage.eINSTANCE.getCDOResourceNode_Name(), 0);

    CDOID existingID = accessor.readResourceID(folderID, name, revision.getBranch().getHead());
    if (existingID != null && !existingID.equals(revision.getID()))
    {
      throw new IllegalStateException("Duplicate resource node in folder " + folderID + ": " + name); //$NON-NLS-1$ //$NON-NLS-2$
    }
  }

  protected void writeLists(IDBStoreAccessor accessor, InternalCDORevision revision)
  {
    for (IListMapping listMapping : listMappings)
    {
      listMapping.writeValues(accessor, revision);
    }
  }

  public void writeRevision(IDBStoreAccessor accessor, InternalCDORevision revision, boolean mapType, boolean revise, OMMonitor monitor)
  {
    // If the repository's root resource ID is not yet set, then this must be the initial initRootResource()
    // commit. The duplicate check is certainly not needed in this case, and it appears that Mysql has problems
    // with it (Table definition has changed, please retry transaction), see bug 482886.
    boolean duplicateResourcesCheckNeeded = revision.isResourceNode() && mappingStrategy.getStore().getRepository().getRootResourceID() != null;

    monitor.begin(duplicateResourcesCheckNeeded ? 10 : 9);
    Async async = null;

    try
    {
      try
      {
        async = monitor.forkAsync();
        CDOID id = revision.getID();
        if (mapType)
        {
          long timeStamp = revision.getTimeStamp();
          mappingStrategy.putObjectType(accessor, timeStamp, id, eClass);
        }
        else if (revise)
        {
          long revised = revision.getTimeStamp() - 1;
          reviseOldRevision(accessor, id, revision.getBranch(), revised);
          for (IListMapping mapping : getListMappings())
          {
            mapping.objectDetached(accessor, id, revised);
          }
        }
      }
      finally
      {
        if (async != null)
        {
          async.stop();
        }
      }

      if (duplicateResourcesCheckNeeded)
      {
        try
        {
          async = monitor.forkAsync();
          checkDuplicateResources(accessor, revision);
        }
        finally
        {
          if (async != null)
          {
            async.stop();
          }
        }
      }

      try
      {
        // Write attribute table always (even without modeled attributes!)
        async = monitor.forkAsync();
        writeValues(accessor, revision);
      }
      finally
      {
        if (async != null)
        {
          async.stop();
        }
      }

      try
      {
        // Write list tables only if they exist
        if (listMappings != null)
        {
          async = monitor.forkAsync(7);
          writeLists(accessor, revision);
        }
        else
        {
          monitor.worked(7);
        }
      }
      finally
      {
        if (async != null)
        {
          async.stop();
        }
      }
    }
    finally
    {
      monitor.done();
    }
  }

  public void handleRevisions(IDBStoreAccessor accessor, CDOBranch branch, long timeStamp, boolean exactTime, CDORevisionHandler handler)
  {
    // branch parameter is ignored, because either it is null or main branch.
    // this does not make any difference for non-branching store.
    // see #handleRevisions() implementation in HorizontalBranchingClassMapping
    // for branch handling.

    IRepository repository = accessor.getStore().getRepository();
    CDORevisionManager revisionManager = repository.getRevisionManager();
    CDOBranchManager branchManager = repository.getBranchManager();

    // TODO: test for timeStamp == INVALID_TIME and encode revision.isValid() as WHERE instead of fetching all revisions
    // in order to increase performance

    StringBuilder builder = new StringBuilder(sqlSelectForHandle);

    int timeParameters = 0;
    if (timeStamp != CDOBranchPoint.INVALID_DATE)
    {
      if (exactTime)
      {
        if (timeStamp != CDOBranchPoint.UNSPECIFIED_DATE)
        {
          builder.append(" WHERE "); //$NON-NLS-1$
          builder.append(ATTRIBUTES_CREATED);
          builder.append("=?"); //$NON-NLS-1$
          timeParameters = 1;
        }
      }
      else
      {
        builder.append(" WHERE "); //$NON-NLS-1$
        if (timeStamp != CDOBranchPoint.UNSPECIFIED_DATE)
        {
          builder.append(ATTRIBUTES_CREATED);
          builder.append("<=?"); //$NON-NLS-1$
          builder.append(" AND ("); //$NON-NLS-1$
          builder.append(ATTRIBUTES_REVISED);
          builder.append(">=? OR "); //$NON-NLS-1$
          builder.append(ATTRIBUTES_REVISED);
          builder.append("=0)"); //$NON-NLS-1$
          timeParameters = 2;
        }
        else
        {
          builder.append(ATTRIBUTES_REVISED);
          builder.append("=0"); //$NON-NLS-1$
        }
      }
    }

    IIDHandler idHandler = getMappingStrategy().getStore().getIDHandler();
    IDBPreparedStatement stmt = accessor.getDBConnection().prepareStatement(builder.toString(), ReuseProbability.LOW);
    ResultSet resultSet = null;

    try
    {
      for (int i = 0; i < timeParameters; i++)
      {
        stmt.setLong(i + 1, timeStamp);
      }

      resultSet = stmt.executeQuery();
      while (resultSet.next())
      {
        CDOID id = idHandler.getCDOID(resultSet, 1);
        int version = resultSet.getInt(2);

        if (version >= CDOBranchVersion.FIRST_VERSION)
        {
          InternalCDORevision revision = (InternalCDORevision)revisionManager.getRevisionByVersion(id, branchManager.getMainBranch().getVersion(version),
              CDORevision.UNCHUNKED, true);

          if (!handler.handleRevision(revision))
          {
            break;
          }
        }
        else
        {
          // Tell handler about detached IDs
          InternalCDORevision revision = new DetachedCDORevision(null, id, null, version, 0);
          if (!handler.handleRevision(revision))
          {
            break;
          }
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

  public Set<CDOID> readChangeSet(IDBStoreAccessor accessor, CDOChangeSetSegment[] segments)
  {
    StringBuilder builder = new StringBuilder(sqlSelectForChangeSet);
    boolean isFirst = true;

    for (int i = 0; i < segments.length; i++)
    {
      if (isFirst)
      {
        isFirst = false;
      }
      else
      {
        builder.append(" OR "); //$NON-NLS-1$
      }

      builder.append(ATTRIBUTES_CREATED);
      builder.append(">=?"); //$NON-NLS-1$
      builder.append(" AND ("); //$NON-NLS-1$
      builder.append(ATTRIBUTES_REVISED);
      builder.append("<=? OR "); //$NON-NLS-1$
      builder.append(ATTRIBUTES_REVISED);
      builder.append("=0)"); //$NON-NLS-1$
    }

    IIDHandler idHandler = getMappingStrategy().getStore().getIDHandler();
    IDBPreparedStatement stmt = accessor.getDBConnection().prepareStatement(builder.toString(), ReuseProbability.LOW);
    ResultSet resultSet = null;

    Set<CDOID> result = new HashSet<CDOID>();

    try
    {
      int column = 1;
      for (CDOChangeSetSegment segment : segments)
      {
        stmt.setLong(column++, segment.getTimeStamp());
        stmt.setLong(column++, segment.getEndTime());
      }

      resultSet = stmt.executeQuery();
      while (resultSet.next())
      {
        CDOID id = idHandler.getCDOID(resultSet, 1);
        result.add(id);
      }

      return result;
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

  public void detachObject(IDBStoreAccessor accessor, CDOID id, int version, CDOBranch branch, long timeStamp, OMMonitor monitor)
  {
    Async async = null;
    monitor.begin(1 + listMappings.size());

    try
    {
      if (version >= CDOBranchVersion.FIRST_VERSION)
      {
        reviseOldRevision(accessor, id, branch, timeStamp - 1);
      }

      detachAttributes(accessor, id, version, branch, timeStamp, monitor.fork());

      // notify list mappings so they can clean up
      for (IListMapping mapping : getListMappings())
      {
        try
        {
          async = monitor.forkAsync();
          mapping.objectDetached(accessor, id, timeStamp);
        }
        finally
        {
          if (async != null)
          {
            async.stop();
          }
        }
      }
    }
    finally
    {
      monitor.done();
    }
  }

  public void rawDelete(IDBStoreAccessor accessor, CDOID id, int version, CDOBranch branch, OMMonitor monitor)
  {
    Async async = null;
    monitor.begin(1 + listMappings.size());

    try
    {
      rawDeleteAttributes(accessor, id, branch, version, monitor.fork());

      // notify list mappings so they can clean up
      for (IListMapping mapping : getListMappings())
      {
        if (mapping instanceof AbstractBasicListTableMapping)
        {
          try
          {
            async = monitor.forkAsync();

            AbstractBasicListTableMapping m = (AbstractBasicListTableMapping)mapping;
            m.rawDeleted(accessor, id, branch, version);
          }
          finally
          {
            if (async != null)
            {
              async.stop();
            }
          }
        }
        else
        {
          throw new UnsupportedOperationException("rawDeleted() is not supported by " + mapping.getClass().getName());
        }
      }
    }
    finally
    {
      monitor.done();
    }
  }

  protected abstract void rawDeleteAttributes(IDBStoreAccessor accessor, CDOID id, CDOBranch branch, int version, OMMonitor fork);

  public final boolean queryXRefs(IDBStoreAccessor accessor, QueryXRefsContext context, String idString)
  {
    String tableName = table.getName();
    List<EReference> refs = context.getSourceCandidates().get(eClass);
    List<EReference> scalarRefs = new ArrayList<EReference>();

    for (EReference ref : refs)
    {
      if (ref.isMany())
      {
        IListMapping listMapping = getListMapping(ref);
        String where = getListXRefsWhere(context);

        boolean more = listMapping.queryXRefs(accessor, tableName, where, context, idString);
        if (!more)
        {
          return false;
        }
      }
      else
      {
        scalarRefs.add(ref);
      }
    }

    if (!scalarRefs.isEmpty())
    {
      boolean more = queryScalarXRefs(accessor, scalarRefs, context, idString);
      if (!more)
      {
        return false;
      }
    }

    return true;
  }

  protected final boolean queryScalarXRefs(IDBStoreAccessor accessor, List<EReference> scalarRefs, QueryXRefsContext context, String idString)
  {
    String tableName = table.getName();
    String where = getListXRefsWhere(context);

    for (EReference ref : scalarRefs)
    {
      ITypeMapping valueMapping = getValueMapping(ref);
      String valueField = valueMapping.getField().getName();

      StringBuilder builder = new StringBuilder();
      builder.append("SELECT ");
      builder.append(ATTRIBUTES_ID);
      builder.append(", ");
      builder.append(valueField);
      builder.append(" FROM ");
      builder.append(tableName);
      builder.append(" WHERE ");
      builder.append(ATTRIBUTES_VERSION);
      builder.append(">0 AND ");
      builder.append(where);
      builder.append(" AND ");
      builder.append(valueField);
      builder.append(" IN ");
      builder.append(idString);
      String sql = builder.toString();
      if (TRACER.isEnabled())
      {
        TRACER.format("Query XRefs (attributes): {0}", sql);
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

          boolean more = context.addXRef(targetID, sourceID, ref, 0);
          if (TRACER.isEnabled())
          {
            TRACER.format("  add XRef to context: src={0}, tgt={1}, idx=0", sourceID, targetID);
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

    return true;
  }

  protected abstract String getListXRefsWhere(QueryXRefsContext context);

  protected abstract void detachAttributes(IDBStoreAccessor accessor, CDOID id, int version, CDOBranch branch, long timeStamp, OMMonitor fork);

  protected abstract void reviseOldRevision(IDBStoreAccessor accessor, CDOID id, CDOBranch branch, long timeStamp);

  protected abstract void writeValues(IDBStoreAccessor accessor, InternalCDORevision revision);

  public Exception deactivate()
  {
    return null;
  }

  protected static void appendTypeMappingNames(StringBuilder builder, Collection<ITypeMapping> typeMappings)
  {
    if (typeMappings != null)
    {
      for (ITypeMapping typeMapping : typeMappings)
      {
        builder.append(", "); //$NON-NLS-1$
        builder.append(typeMapping.getField());
      }
    }
  }

  protected static void appendFieldNames(StringBuilder builder, Map<EStructuralFeature, IDBField> fields)
  {
    if (fields != null)
    {
      for (IDBField field : fields.values())
      {
        builder.append(", "); //$NON-NLS-1$
        builder.append(field);
      }
    }
  }

  protected static void appendTypeMappingParameters(StringBuilder builder, Collection<ITypeMapping> typeMappings)
  {
    if (typeMappings != null)
    {
      for (int i = 0; i < typeMappings.size(); i++)
      {
        builder.append(", ?"); //$NON-NLS-1$
      }
    }
  }

  protected static void appendFieldParameters(StringBuilder builder, Map<EStructuralFeature, IDBField> fields)
  {
    if (fields != null)
    {
      for (int i = 0; i < fields.size(); i++)
      {
        builder.append(", ?"); //$NON-NLS-1$
      }
    }
  }
}

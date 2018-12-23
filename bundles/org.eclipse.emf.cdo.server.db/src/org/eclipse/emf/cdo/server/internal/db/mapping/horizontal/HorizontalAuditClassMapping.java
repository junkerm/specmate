/*
 * Copyright (c) 2009-2016, 2018 Eike Stepper (Loehne, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 *    Stefan Winkler - major refactoring
 *    Stefan Winkler - Bug 249610: [DB] Support external references (Implementation)
 *    Lothar Werzinger - Bug 296440: [DB] Change RDB schema to improve scalability of to-many references in audit mode
 *    Stefan Winkler - Bug 329025: [DB] Support branching for range-based mapping strategy
 */
package org.eclipse.emf.cdo.server.internal.db.mapping.horizontal;

import org.eclipse.emf.cdo.common.branch.CDOBranch;
import org.eclipse.emf.cdo.common.branch.CDOBranchPoint;
import org.eclipse.emf.cdo.common.branch.CDOBranchVersion;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.revision.CDOList;
import org.eclipse.emf.cdo.common.revision.CDORevision;
import org.eclipse.emf.cdo.common.revision.CDORevisionHandler;
import org.eclipse.emf.cdo.common.revision.delta.CDOContainerFeatureDelta;
import org.eclipse.emf.cdo.common.revision.delta.CDOListFeatureDelta;
import org.eclipse.emf.cdo.common.revision.delta.CDOSetFeatureDelta;
import org.eclipse.emf.cdo.common.revision.delta.CDOUnsetFeatureDelta;
import org.eclipse.emf.cdo.eresource.EresourcePackage;
import org.eclipse.emf.cdo.server.IStoreAccessor.QueryXRefsContext;
import org.eclipse.emf.cdo.server.StoreThreadLocal;
import org.eclipse.emf.cdo.server.db.IDBStoreAccessor;
import org.eclipse.emf.cdo.server.db.IIDHandler;
import org.eclipse.emf.cdo.server.db.mapping.IClassMappingAuditSupport;
import org.eclipse.emf.cdo.server.db.mapping.IClassMappingDeltaSupport;
import org.eclipse.emf.cdo.server.db.mapping.IClassMappingUnitSupport;
import org.eclipse.emf.cdo.server.db.mapping.IListMapping;
import org.eclipse.emf.cdo.server.db.mapping.IListMappingDeltaSupport;
import org.eclipse.emf.cdo.server.db.mapping.IListMappingUnitSupport;
import org.eclipse.emf.cdo.server.db.mapping.ITypeMapping;
import org.eclipse.emf.cdo.server.internal.db.DBStore;
import org.eclipse.emf.cdo.server.internal.db.bundle.OM;
import org.eclipse.emf.cdo.spi.common.revision.InternalCDORevision;
import org.eclipse.emf.cdo.spi.common.revision.InternalCDORevisionDelta;
import org.eclipse.emf.cdo.spi.common.revision.StubCDORevision;
import org.eclipse.emf.cdo.spi.server.InternalRepository;

import org.eclipse.net4j.db.DBException;
import org.eclipse.net4j.db.DBUtil;
import org.eclipse.net4j.db.IDBPreparedStatement;
import org.eclipse.net4j.db.IDBPreparedStatement.ReuseProbability;
import org.eclipse.net4j.db.IDBResultSet;
import org.eclipse.net4j.db.ddl.IDBField;
import org.eclipse.net4j.util.ImplementationError;
import org.eclipse.net4j.util.WrappedException;
import org.eclipse.net4j.util.collection.MoveableList;
import org.eclipse.net4j.util.concurrent.ConcurrencyUtil;
import org.eclipse.net4j.util.concurrent.TimeoutRuntimeException;
import org.eclipse.net4j.util.om.monitor.OMMonitor;
import org.eclipse.net4j.util.om.monitor.OMMonitor.Async;
import org.eclipse.net4j.util.om.trace.ContextTracer;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author Eike Stepper
 * @since 2.0
 */
public class HorizontalAuditClassMapping extends AbstractHorizontalClassMapping
    implements IClassMappingAuditSupport, IClassMappingDeltaSupport, IClassMappingUnitSupport
{
  private static final ContextTracer TRACER = new ContextTracer(OM.DEBUG, HorizontalAuditClassMapping.class);

  private static final ContextTracer TRACER_UNITS = new ContextTracer(OM.DEBUG_UNITS, HorizontalAuditClassMapping.class);

  private String sqlInsertAttributes;

  private String sqlSelectAttributesCurrent;

  private String sqlSelectAttributesByTime;

  private String sqlSelectAttributesByVersion;

  private String sqlSelectUnitByTime;

  private String sqlSelectAllObjectIDs;

  private String sqlReviseAttributes;

  private String sqlRawDeleteAttributes;

  public HorizontalAuditClassMapping(AbstractHorizontalMappingStrategy mappingStrategy, EClass eClass)
  {
    super(mappingStrategy, eClass);
  }

  @Override
  protected void initSQLStrings()
  {
    super.initSQLStrings();

    // ----------- Select Revision ---------------------------
    String[] strings = buildSQLSelects(false);
    String sqlSelectAttributesPrefix = strings[0];
    sqlSelectAttributesCurrent = strings[1];
    sqlSelectAttributesByTime = strings[2];

    StringBuilder builder = new StringBuilder(sqlSelectAttributesPrefix);
    builder.append("ABS(");
    builder.append(ATTRIBUTES_VERSION);
    builder.append(")=?"); //$NON-NLS-1$
    sqlSelectAttributesByVersion = builder.toString();

    InternalRepository repository = (InternalRepository)getMappingStrategy().getStore().getRepository();
    if (repository.isSupportingUnits())
    {
      strings = buildSQLSelects(true);
      sqlSelectUnitByTime = strings[2];
    }

    // ----------- Insert Attributes -------------------------
    builder = new StringBuilder();
    builder.append("INSERT INTO "); //$NON-NLS-1$
    builder.append(getTable());
    builder.append("("); //$NON-NLS-1$
    builder.append(ATTRIBUTES_ID);
    builder.append(", "); //$NON-NLS-1$
    builder.append(ATTRIBUTES_VERSION);
    builder.append(", "); //$NON-NLS-1$
    builder.append(ATTRIBUTES_CREATED);
    builder.append(", "); //$NON-NLS-1$
    builder.append(ATTRIBUTES_REVISED);
    builder.append(", "); //$NON-NLS-1$
    builder.append(ATTRIBUTES_RESOURCE);
    builder.append(", "); //$NON-NLS-1$
    builder.append(ATTRIBUTES_CONTAINER);
    builder.append(", "); //$NON-NLS-1$
    builder.append(ATTRIBUTES_FEATURE);
    appendTypeMappingNames(builder, getValueMappings());
    appendFieldNames(builder, getUnsettableFields());
    appendFieldNames(builder, getListSizeFields());
    builder.append(") VALUES (?, ?, ?, ?, ?, ?, ?"); //$NON-NLS-1$
    appendTypeMappingParameters(builder, getValueMappings());
    appendFieldParameters(builder, getUnsettableFields());
    appendFieldParameters(builder, getListSizeFields());
    builder.append(")"); //$NON-NLS-1$
    sqlInsertAttributes = builder.toString();

    // ----------- Update to set revised ----------------
    builder = new StringBuilder("UPDATE "); //$NON-NLS-1$
    builder.append(getTable());
    builder.append(" SET "); //$NON-NLS-1$
    builder.append(ATTRIBUTES_REVISED);
    builder.append("=? WHERE "); //$NON-NLS-1$
    builder.append(ATTRIBUTES_ID);
    builder.append("=? AND "); //$NON-NLS-1$
    builder.append(ATTRIBUTES_REVISED);
    builder.append("=0"); //$NON-NLS-1$
    sqlReviseAttributes = builder.toString();

    // ----------- Select all unrevised Object IDs ------
    builder = new StringBuilder("SELECT "); //$NON-NLS-1$
    builder.append(ATTRIBUTES_ID);
    builder.append(" FROM "); //$NON-NLS-1$
    builder.append(getTable());
    builder.append(" WHERE "); //$NON-NLS-1$
    builder.append(ATTRIBUTES_REVISED);
    builder.append("=0"); //$NON-NLS-1$
    sqlSelectAllObjectIDs = builder.toString();

    // ----------- Raw delete one specific revision ------
    builder = new StringBuilder("DELETE FROM "); //$NON-NLS-1$
    builder.append(getTable());
    builder.append(" WHERE "); //$NON-NLS-1$
    builder.append(ATTRIBUTES_ID);
    builder.append("=? AND "); //$NON-NLS-1$
    builder.append(ATTRIBUTES_VERSION);
    builder.append("=?"); //$NON-NLS-1$
    sqlRawDeleteAttributes = builder.toString();
  }

  @Override
  protected void appendSelectForHandleFields(StringBuilder builder)
  {
    builder.append(", "); //$NON-NLS-1$
    builder.append(ATTRIBUTES_CREATED);
    builder.append(", "); //$NON-NLS-1$
    builder.append(ATTRIBUTES_REVISED);
  }

  private String[] buildSQLSelects(boolean forUnits)
  {
    String[] strings = new String[3];

    StringBuilder builder = new StringBuilder();
    builder.append("SELECT "); //$NON-NLS-1$
    if (forUnits)
    {
      builder.append(ATTRIBUTES_ID);
      builder.append(", "); //$NON-NLS-1$
    }

    builder.append(ATTRIBUTES_VERSION);
    builder.append(", "); //$NON-NLS-1$
    builder.append(ATTRIBUTES_CREATED);
    builder.append(", "); //$NON-NLS-1$
    builder.append(ATTRIBUTES_REVISED);
    builder.append(", "); //$NON-NLS-1$
    builder.append(ATTRIBUTES_RESOURCE);
    builder.append(", "); //$NON-NLS-1$
    builder.append(ATTRIBUTES_CONTAINER);
    builder.append(", "); //$NON-NLS-1$
    builder.append(ATTRIBUTES_FEATURE);
    appendTypeMappingNames(builder, getValueMappings());
    appendFieldNames(builder, getUnsettableFields());
    appendFieldNames(builder, getListSizeFields());
    builder.append(" FROM "); //$NON-NLS-1$
    builder.append(getTable());

    if (forUnits)
    {
      builder.append(", "); //$NON-NLS-1$
      builder.append(UnitMappingTable.UNITS);
      builder.append(" WHERE "); //$NON-NLS-1$
      builder.append(ATTRIBUTES_ID);
      builder.append("="); //$NON-NLS-1$
      builder.append(UnitMappingTable.UNITS);
      builder.append("."); //$NON-NLS-1$
      builder.append(UnitMappingTable.UNITS_ELEM);
      builder.append(" AND "); //$NON-NLS-1$
      builder.append(UnitMappingTable.UNITS);
      builder.append("."); //$NON-NLS-1$
      builder.append(UnitMappingTable.UNITS_UNIT);
      builder.append("=?"); //$NON-NLS-1$
      builder.append(" AND "); //$NON-NLS-1$
    }
    else
    {
      builder.append(" WHERE "); //$NON-NLS-1$
      builder.append(ATTRIBUTES_ID);
      builder.append("=? AND "); //$NON-NLS-1$
    }

    strings[0] = builder.toString();

    builder.append(ATTRIBUTES_REVISED);
    builder.append("=0"); //$NON-NLS-1$

    if (forUnits)
    {
      builder.append(" ORDER BY "); //$NON-NLS-1$
      builder.append(ATTRIBUTES_ID);
    }

    strings[1] = builder.toString();

    builder = new StringBuilder(strings[0]);
    builder.append("("); //$NON-NLS-1$
    builder.append(ATTRIBUTES_CREATED);
    builder.append("<=? AND ("); //$NON-NLS-1$
    builder.append(ATTRIBUTES_REVISED);
    builder.append("=0 OR "); //$NON-NLS-1$
    builder.append(ATTRIBUTES_REVISED);
    builder.append(">=?))"); //$NON-NLS-1$

    if (forUnits)
    {
      builder.append(" ORDER BY "); //$NON-NLS-1$
      builder.append(ATTRIBUTES_ID);
    }

    strings[2] = builder.toString();

    return strings;
  }

  public boolean readRevision(IDBStoreAccessor accessor, InternalCDORevision revision, int listChunk)
  {
    IIDHandler idHandler = getMappingStrategy().getStore().getIDHandler();
    IDBPreparedStatement stmt = null;

    try
    {
      long timeStamp = revision.getTimeStamp();
      if (timeStamp != CDOBranchPoint.UNSPECIFIED_DATE)
      {
        stmt = accessor.getDBConnection().prepareStatement(sqlSelectAttributesByTime, ReuseProbability.MEDIUM);
        idHandler.setCDOID(stmt, 1, revision.getID());
        stmt.setLong(2, timeStamp);
        stmt.setLong(3, timeStamp);
      }
      else
      {
        stmt = accessor.getDBConnection().prepareStatement(sqlSelectAttributesCurrent, ReuseProbability.HIGH);
        idHandler.setCDOID(stmt, 1, revision.getID());
      }

      // Read singleval-attribute table always (even without modeled attributes!)
      boolean success = readValuesFromStatement(stmt, revision, accessor);

      // Read multival tables only if revision exists
      if (success && revision.getVersion() >= CDOBranchVersion.FIRST_VERSION)
      {
        readLists(accessor, revision, listChunk);
      }

      return success;
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

  public boolean readRevisionByVersion(IDBStoreAccessor accessor, InternalCDORevision revision, int listChunk)
  {
    IIDHandler idHandler = getMappingStrategy().getStore().getIDHandler();
    IDBPreparedStatement stmt = accessor.getDBConnection().prepareStatement(sqlSelectAttributesByVersion, ReuseProbability.HIGH);

    try
    {
      idHandler.setCDOID(stmt, 1, revision.getID());
      stmt.setInt(2, revision.getVersion());

      // Read singleval-attribute table always (even without modeled attributes!)
      boolean success = readValuesFromStatement(stmt, revision, accessor);

      // Read multival tables only if revision exists
      if (success)
      {
        readLists(accessor, revision, listChunk);
      }

      return success;
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

  public IDBPreparedStatement createResourceQueryStatement(IDBStoreAccessor accessor, CDOID folderId, String name, boolean exactMatch,
      CDOBranchPoint branchPoint)
  {
    if (getTable() == null)
    {
      return null;
    }

    EStructuralFeature nameFeature = EresourcePackage.eINSTANCE.getCDOResourceNode_Name();
    long timeStamp = branchPoint.getTimeStamp();

    ITypeMapping nameValueMapping = getValueMapping(nameFeature);
    if (nameValueMapping == null)
    {
      throw new ImplementationError(nameFeature + " not found in ClassMapping " + this); //$NON-NLS-1$
    }

    StringBuilder builder = new StringBuilder();
    builder.append("SELECT "); //$NON-NLS-1$
    builder.append(ATTRIBUTES_ID);
    builder.append(" FROM "); //$NON-NLS-1$
    builder.append(getTable());
    builder.append(" WHERE "); //$NON-NLS-1$
    builder.append(ATTRIBUTES_VERSION);
    builder.append(">0 AND "); //$NON-NLS-1$
    builder.append(ATTRIBUTES_CONTAINER);
    builder.append("=? AND "); //$NON-NLS-1$
    builder.append(nameValueMapping.getField());
    if (name == null)
    {
      builder.append(" IS NULL"); //$NON-NLS-1$
    }
    else
    {
      builder.append(exactMatch ? "=? " : " LIKE ? "); //$NON-NLS-1$ //$NON-NLS-2$
    }

    builder.append(" AND ("); //$NON-NLS-1$

    if (timeStamp == CDORevision.UNSPECIFIED_DATE)
    {
      builder.append(ATTRIBUTES_REVISED);
      builder.append("=0)"); //$NON-NLS-1$
    }
    else
    {
      builder.append(ATTRIBUTES_CREATED);
      builder.append("<=? AND ("); //$NON-NLS-1$
      builder.append(ATTRIBUTES_REVISED);
      builder.append("=0 OR "); //$NON-NLS-1$
      builder.append(ATTRIBUTES_REVISED);
      builder.append(">=?))"); //$NON-NLS-1$
    }

    IIDHandler idHandler = getMappingStrategy().getStore().getIDHandler();
    IDBPreparedStatement stmt = accessor.getDBConnection().prepareStatement(builder.toString(), ReuseProbability.MEDIUM);

    try
    {
      int column = 1;
      idHandler.setCDOID(stmt, column++, folderId);

      if (name != null)
      {
        String queryName = exactMatch ? name : name + "%"; //$NON-NLS-1$
        nameValueMapping.setValue(stmt, column++, queryName);
      }

      if (timeStamp != CDORevision.UNSPECIFIED_DATE)
      {
        stmt.setLong(column++, timeStamp);
        stmt.setLong(column++, timeStamp);
      }

      if (TRACER.isEnabled())
      {
        TRACER.format("Created Resource Query: {0}", stmt); //$NON-NLS-1$
      }

      return stmt;
    }
    catch (Throwable ex)
    {
      DBUtil.close(stmt); // only release on error
      throw new DBException(ex);
    }
  }

  public IDBPreparedStatement createObjectIDStatement(IDBStoreAccessor accessor)
  {
    if (TRACER.isEnabled())
    {
      TRACER.format("Created ObjectID Statement : {0}", sqlSelectAllObjectIDs); //$NON-NLS-1$
    }

    return accessor.getDBConnection().prepareStatement(sqlSelectAllObjectIDs, ReuseProbability.HIGH);
  }

  @Override
  protected final void writeValues(IDBStoreAccessor accessor, InternalCDORevision revision)
  {
    IIDHandler idHandler = getMappingStrategy().getStore().getIDHandler();
    IDBPreparedStatement stmt = accessor.getDBConnection().prepareStatement(sqlInsertAttributes, ReuseProbability.HIGH);

    try
    {
      int column = 1;
      idHandler.setCDOID(stmt, column++, revision.getID());
      stmt.setInt(column++, revision.getVersion());
      stmt.setLong(column++, revision.getTimeStamp());
      stmt.setLong(column++, revision.getRevised());
      idHandler.setCDOID(stmt, column++, revision.getResourceID());
      idHandler.setCDOID(stmt, column++, (CDOID)revision.getContainerID());
      stmt.setInt(column++, revision.getContainingFeatureID());

      int isSetCol = column + getValueMappings().size();

      for (ITypeMapping mapping : getValueMappings())
      {
        EStructuralFeature feature = mapping.getFeature();
        if (feature.isUnsettable())
        {
          if (revision.getValue(feature) == null)
          {
            stmt.setBoolean(isSetCol++, false);

            // also set value column to default value
            mapping.setDefaultValue(stmt, column++);

            continue;
          }

          stmt.setBoolean(isSetCol++, true);
        }

        mapping.setValueFromRevision(stmt, column++, revision);
      }

      Map<EStructuralFeature, IDBField> listSizeFields = getListSizeFields();
      if (listSizeFields != null)
      {
        // isSetCol now points to the first listTableSize-column
        column = isSetCol;

        for (EStructuralFeature feature : listSizeFields.keySet())
        {
          CDOList list = revision.getListOrNull(feature);
          int size = list == null ? UNSET_LIST : list.size();
          stmt.setInt(column++, size);
        }
      }

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

  @Override
  protected void detachAttributes(IDBStoreAccessor accessor, CDOID id, int version, CDOBranch branch, long timeStamp, OMMonitor mon)
  {
    IIDHandler idHandler = getMappingStrategy().getStore().getIDHandler();
    IDBPreparedStatement stmt = accessor.getDBConnection().prepareStatement(sqlInsertAttributes, ReuseProbability.HIGH);

    try
    {
      int column = 1;
      idHandler.setCDOID(stmt, column++, id);
      stmt.setInt(column++, -version); // cdo_version
      stmt.setLong(column++, timeStamp); // cdo_created
      stmt.setLong(column++, CDOBranchPoint.UNSPECIFIED_DATE); // cdo_revised
      idHandler.setCDOID(stmt, column++, CDOID.NULL); // resource
      idHandler.setCDOID(stmt, column++, CDOID.NULL); // container
      stmt.setInt(column++, 0); // containing feature ID

      int isSetCol = column + getValueMappings().size();

      for (ITypeMapping mapping : getValueMappings())
      {
        EStructuralFeature feature = mapping.getFeature();
        if (feature.isUnsettable())
        {
          stmt.setBoolean(isSetCol++, false);
        }

        mapping.setDefaultValue(stmt, column++);
      }

      Map<EStructuralFeature, IDBField> listSizeFields = getListSizeFields();
      if (listSizeFields != null)
      {
        // list size columns begin after isSet-columns
        column = isSetCol;

        for (int i = 0; i < listSizeFields.size(); i++)
        {
          stmt.setInt(column++, 0);
        }
      }

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

  @Override
  protected void rawDeleteAttributes(IDBStoreAccessor accessor, CDOID id, CDOBranch branch, int version, OMMonitor fork)
  {
    IDBPreparedStatement stmt = accessor.getDBConnection().prepareStatement(sqlRawDeleteAttributes, ReuseProbability.HIGH);

    try
    {
      getMappingStrategy().getStore().getIDHandler().setCDOID(stmt, 1, id);
      stmt.setInt(2, version);
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
  protected void reviseOldRevision(IDBStoreAccessor accessor, CDOID id, CDOBranch branch, long revised)
  {
    IIDHandler idHandler = getMappingStrategy().getStore().getIDHandler();
    IDBPreparedStatement stmt = accessor.getDBConnection().prepareStatement(sqlReviseAttributes, ReuseProbability.HIGH);

    try
    {
      stmt.setLong(1, revised);
      idHandler.setCDOID(stmt, 2, id);

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

  public void writeRevisionDelta(IDBStoreAccessor accessor, InternalCDORevisionDelta delta, long created, OMMonitor monitor)
  {
    Async async = null;
    monitor.begin();

    try
    {
      try
      {
        async = monitor.forkAsync();

        FeatureDeltaWriter writer = new FeatureDeltaWriter();
        writer.process(accessor, delta, created);
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

  @Override
  protected String getListXRefsWhere(QueryXRefsContext context)
  {
    if (CDOBranch.MAIN_BRANCH_ID != context.getBranch().getID())
    {
      throw new IllegalArgumentException("Non-audit mode does not support branch specification");
    }

    StringBuilder builder = new StringBuilder();
    long timeStamp = context.getTimeStamp();
    if (timeStamp == CDORevision.UNSPECIFIED_DATE)
    {
      builder.append(ATTRIBUTES_REVISED);
      builder.append("=0"); //$NON-NLS-1$
    }
    else
    {
      builder.append(ATTRIBUTES_CREATED);
      builder.append("<=");
      builder.append(timeStamp);
      builder.append(" AND ("); //$NON-NLS-1$
      builder.append(ATTRIBUTES_REVISED);
      builder.append("=0 OR "); //$NON-NLS-1$
      builder.append(ATTRIBUTES_REVISED);
      builder.append(">=");
      builder.append(timeStamp);
      builder.append(")"); //$NON-NLS-1$
    }

    return builder.toString();
  }

  public void readUnitRevisions(IDBStoreAccessor accessor, CDOBranchPoint branchPoint, CDOID rootID, CDORevisionHandler revisionHandler) throws SQLException
  {
    DBStore store = (DBStore)getMappingStrategy().getStore();
    InternalRepository repository = store.getRepository();

    CDOBranchPoint head = repository.getBranchManager().getMainBranch().getHead();
    EClass eClass = getEClass();
    long timeStamp = branchPoint.getTimeStamp();

    IIDHandler idHandler = store.getIDHandler();
    IDBPreparedStatement stmt = null;

    int jdbcFetchSize = store.getJDBCFetchSize();
    int oldFetchSize = -1;

    final long start1 = TRACER_UNITS.isEnabled() ? System.currentTimeMillis() : CDOBranchPoint.UNSPECIFIED_DATE;

    try
    {
      stmt = accessor.getDBConnection().prepareStatement(sqlSelectUnitByTime, ReuseProbability.MEDIUM);
      idHandler.setCDOID(stmt, 1, rootID);
      stmt.setLong(2, timeStamp);
      stmt.setLong(3, timeStamp);

      AsnychronousListFiller listFiller = new AsnychronousListFiller(accessor, timeStamp, rootID, revisionHandler);
      ConcurrencyUtil.execute(repository, listFiller);

      oldFetchSize = stmt.getFetchSize();
      stmt.setFetchSize(jdbcFetchSize);
      IDBResultSet resultSet = stmt.executeQuery();

      for (;;)
      {
        InternalCDORevision revision = store.createRevision(eClass, null);
        revision.setBranchPoint(head);

        if (!readValuesFromResultSet(resultSet, idHandler, revision, true))
        {
          break;
        }

        listFiller.schedule(revision);
      }

      final long start2 = start1 != CDOBranchPoint.UNSPECIFIED_DATE ? System.currentTimeMillis() : start1;

      listFiller.await();

      if (start1 != CDOBranchPoint.UNSPECIFIED_DATE)
      {
        TRACER_UNITS.format("Read {0} revisions of unit {1}: {2} millis + {3} millis", eClass.getName(), rootID, start2 - start1,
            System.currentTimeMillis() - start2);
      }
    }
    finally
    {
      if (oldFetchSize != -1)
      {
        stmt.setFetchSize(oldFetchSize);
      }

      DBUtil.close(stmt);
    }
  }

  private class AsnychronousListFiller implements Runnable
  {
    private final BlockingQueue<InternalCDORevision> queue = new LinkedBlockingQueue<InternalCDORevision>();

    private final CountDownLatch latch = new CountDownLatch(1);

    private final IDBStoreAccessor accessor;

    private final long timeStamp;

    private final CDOID rootID;

    private final DBStore store;

    private final IIDHandler idHandler;

    private final IListMappingUnitSupport[] listMappings;

    private final ResultSet[] resultSets;

    private final CDORevisionHandler revisionHandler;

    private Throwable exception;

    public AsnychronousListFiller(IDBStoreAccessor accessor, long timeStamp, CDOID rootID, CDORevisionHandler revisionHandler)
    {
      this.accessor = accessor;
      this.timeStamp = timeStamp;
      this.rootID = rootID;
      this.revisionHandler = revisionHandler;

      store = (DBStore)accessor.getStore();
      idHandler = store.getIDHandler();

      List<IListMapping> tmp = getListMappings();
      int size = tmp.size();

      listMappings = new IListMappingUnitSupport[size];
      resultSets = new ResultSet[size];

      int i = 0;
      for (IListMapping listMapping : tmp)
      {
        listMappings[i++] = (IListMappingUnitSupport)listMapping;
      }
    }

    public void schedule(InternalCDORevision revision)
    {
      queue.offer(revision);
    }

    public void await() throws SQLException
    {
      // Schedule an end marker revision.
      schedule(new StubCDORevision(getEClass()));

      try
      {
        latch.await();
      }
      catch (InterruptedException ex)
      {
        throw new TimeoutRuntimeException();
      }
      finally
      {
        for (ResultSet resultSet : resultSets)
        {
          if (resultSet != null)
          {
            Statement statement = resultSet.getStatement();
            DBUtil.close(statement);
          }
        }
      }

      if (exception instanceof RuntimeException)
      {
        throw (RuntimeException)exception;
      }

      if (exception instanceof Error)
      {
        throw (Error)exception;
      }

      if (exception instanceof SQLException)
      {
        throw (SQLException)exception;
      }

      if (exception instanceof Exception)
      {
        throw WrappedException.wrap((Exception)exception);
      }
    }

    public void run()
    {
      StoreThreadLocal.setAccessor(accessor);

      try
      {
        while (store.isActive())
        {
          InternalCDORevision revision = queue.poll(1, TimeUnit.SECONDS);
          if (revision == null)
          {
            continue;
          }

          if (revision instanceof StubCDORevision)
          {
            return;
          }

          readUnitEntries(revision);
        }
      }
      catch (Throwable ex)
      {
        exception = ex;
      }
      finally
      {
        latch.countDown();
        StoreThreadLocal.remove();
      }
    }

    private void readUnitEntries(InternalCDORevision revision) throws SQLException
    {
      CDOID id = revision.getID();

      for (int i = 0; i < listMappings.length; i++)
      {
        IListMappingUnitSupport listMapping = listMappings[i];
        EStructuralFeature feature = listMapping.getFeature();

        MoveableList<Object> list = revision.getListOrNull(feature);
        if (list != null)
        {
          int size = list.size();
          if (size != 0)
          {
            if (resultSets[i] == null)
            {
              resultSets[i] = listMapping.queryUnitEntries(accessor, idHandler, timeStamp, rootID);
            }

            listMapping.readUnitEntries(resultSets[i], idHandler, id, list);
          }
        }
      }

      synchronized (revisionHandler)
      {
        revisionHandler.handleRevision(revision);
      }
    }
  }

  /**
   * @author Stefan Winkler
   */
  private final class FeatureDeltaWriter extends AbstractFeatureDeltaWriter
  {
    private int oldVersion;

    private InternalCDORevision newRevision;

    private int branchId;

    @Override
    protected void doProcess(InternalCDORevisionDelta delta)
    {
      branchId = delta.getBranch().getID();
      oldVersion = delta.getVersion();

      if (TRACER.isEnabled())
      {
        TRACER.format("FeatureDeltaWriter: old version: {0}, new version: {1}", oldVersion, oldVersion + 1); //$NON-NLS-1$
      }

      InternalCDORevision originalRevision = (InternalCDORevision)accessor.getStore().getRepository().getRevisionManager().getRevisionByVersion(id, delta, 0,
          true);

      newRevision = originalRevision.copy();

      newRevision.setVersion(oldVersion + 1);
      newRevision.setBranchPoint(delta.getBranch().getPoint(created));

      // process revision delta tree
      delta.accept(this);

      long revised = newRevision.getTimeStamp() - 1;
      reviseOldRevision(accessor, id, delta.getBranch(), revised);

      writeValues(accessor, newRevision);
    }

    public void visit(CDOSetFeatureDelta delta)
    {
      delta.applyTo(newRevision);
    }

    public void visit(CDOUnsetFeatureDelta delta)
    {
      delta.applyTo(newRevision);
    }

    public void visit(CDOListFeatureDelta delta)
    {
      delta.applyTo(newRevision);

      IListMappingDeltaSupport listMapping = (IListMappingDeltaSupport)getListMapping(delta.getFeature());
      listMapping.processDelta(accessor, id, branchId, oldVersion, oldVersion + 1, created, delta);
    }

    public void visit(CDOContainerFeatureDelta delta)
    {
      delta.applyTo(newRevision);
    }
  }
}

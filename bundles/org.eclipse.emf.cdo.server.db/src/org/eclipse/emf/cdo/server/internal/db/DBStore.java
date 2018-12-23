/*
 * Copyright (c) 2007-2013, 2015, 2016 Eike Stepper (Loehne, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 *    Stefan Winkler - Bug 259402
 *    Stefan Winkler - Bug 271444: [DB] Multiple refactorings bug 271444
 *    Stefan Winkler - Bug 249610: [DB] Support external references (Implementation)
 *    Stefan Winkler - Bug 289056: [DB] Exception "ERROR: relation "cdo_external_refs" does not exist" while executing test-suite for PostgreSQL
 */
package org.eclipse.emf.cdo.server.internal.db;

import org.eclipse.emf.cdo.common.CDOCommonRepository.CommitInfoStorage;
import org.eclipse.emf.cdo.common.CDOCommonRepository.IDGenerationLocation;
import org.eclipse.emf.cdo.common.branch.CDOBranch;
import org.eclipse.emf.cdo.common.branch.CDOBranchPoint;
import org.eclipse.emf.cdo.common.branch.CDOBranchVersion;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.revision.CDOAllRevisionsProvider;
import org.eclipse.emf.cdo.common.revision.CDORevision;
import org.eclipse.emf.cdo.common.revision.CDORevisionHandler;
import org.eclipse.emf.cdo.server.ISession;
import org.eclipse.emf.cdo.server.ITransaction;
import org.eclipse.emf.cdo.server.IView;
import org.eclipse.emf.cdo.server.StoreThreadLocal;
import org.eclipse.emf.cdo.server.db.IDBStore;
import org.eclipse.emf.cdo.server.db.IIDHandler;
import org.eclipse.emf.cdo.server.db.IMetaDataManager;
import org.eclipse.emf.cdo.server.db.mapping.IMappingStrategy;
import org.eclipse.emf.cdo.server.internal.db.bundle.OM;
import org.eclipse.emf.cdo.server.internal.db.mapping.horizontal.IMappingConstants;
import org.eclipse.emf.cdo.server.internal.db.mapping.horizontal.UnitMappingTable;
import org.eclipse.emf.cdo.server.internal.db.messages.Messages;
import org.eclipse.emf.cdo.spi.server.InternalRepository;
import org.eclipse.emf.cdo.spi.server.InternalSession;
import org.eclipse.emf.cdo.spi.server.LongIDStoreAccessor;
import org.eclipse.emf.cdo.spi.server.Store;
import org.eclipse.emf.cdo.spi.server.StoreAccessorPool;

import org.eclipse.net4j.db.DBException;
import org.eclipse.net4j.db.DBUtil;
import org.eclipse.net4j.db.IDBAdapter;
import org.eclipse.net4j.db.IDBConnection;
import org.eclipse.net4j.db.IDBConnectionProvider;
import org.eclipse.net4j.db.IDBDatabase;
import org.eclipse.net4j.db.IDBPreparedStatement;
import org.eclipse.net4j.db.IDBPreparedStatement.ReuseProbability;
import org.eclipse.net4j.db.IDBSchemaTransaction;
import org.eclipse.net4j.db.ddl.IDBField;
import org.eclipse.net4j.db.ddl.IDBSchema;
import org.eclipse.net4j.db.ddl.IDBTable;
import org.eclipse.net4j.util.ReflectUtil.ExcludeFromDump;
import org.eclipse.net4j.util.lifecycle.LifecycleUtil;
import org.eclipse.net4j.util.om.monitor.ProgressDistributor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Timer;

/**
 * @author Eike Stepper
 */
public class DBStore extends Store implements IDBStore, IMappingConstants, CDOAllRevisionsProvider
{
  public static final String TYPE = "db"; //$NON-NLS-1$

  public static final int SCHEMA_VERSION = 4;

  // public static final int SCHEMA_VERSION = 3; // Bug 404047: Indexed columns must be NOT NULL.
  // public static final int SCHEMA_VERSION = 2; // Bug 344232: Rename cdo_lobs.size to cdo_lobs.lsize.
  // public static final int SCHEMA_VERSION = 1; // Bug 351068: Delete detached objects from non-auditing stores.

  private static final int FIRST_START = -1;

  private static final String PROP_SCHEMA_VERSION = "org.eclipse.emf.cdo.server.db.schemaVersion"; //$NON-NLS-1$

  private static final String PROP_REPOSITORY_CREATED = "org.eclipse.emf.cdo.server.db.repositoryCreated"; //$NON-NLS-1$

  private static final String PROP_REPOSITORY_STOPPED = "org.eclipse.emf.cdo.server.db.repositoryStopped"; //$NON-NLS-1$

  private static final String PROP_NEXT_LOCAL_CDOID = "org.eclipse.emf.cdo.server.db.nextLocalCDOID"; //$NON-NLS-1$

  private static final String PROP_LAST_CDOID = "org.eclipse.emf.cdo.server.db.lastCDOID"; //$NON-NLS-1$

  private static final String PROP_LAST_BRANCHID = "org.eclipse.emf.cdo.server.db.lastBranchID"; //$NON-NLS-1$

  private static final String PROP_LAST_LOCAL_BRANCHID = "org.eclipse.emf.cdo.server.db.lastLocalBranchID"; //$NON-NLS-1$

  private static final String PROP_LAST_COMMITTIME = "org.eclipse.emf.cdo.server.db.lastCommitTime"; //$NON-NLS-1$

  private static final String PROP_LAST_NONLOCAL_COMMITTIME = "org.eclipse.emf.cdo.server.db.lastNonLocalCommitTime"; //$NON-NLS-1$

  private static final String PROP_GRACEFULLY_SHUT_DOWN = "org.eclipse.emf.cdo.server.db.gracefullyShutDown"; //$NON-NLS-1$

  private long creationTime;

  private boolean firstTime;

  private Map<String, String> properties;

  private int idColumnLength = IDBField.DEFAULT;

  private int jdbcFetchSize = 100000;

  private IIDHandler idHandler;

  private IMetaDataManager metaDataManager = new MetaDataManager(this);

  private DurableLockingManager durableLockingManager = new DurableLockingManager(this);

  private CommitInfoTable commitInfoTable;

  private UnitMappingTable unitMappingTable;

  private IMappingStrategy mappingStrategy;

  private IDBDatabase database;

  private IDBAdapter dbAdapter;

  private IDBConnectionProvider dbConnectionProvider;

  @ExcludeFromDump
  private transient ProgressDistributor accessorWriteDistributor = new ProgressDistributor.Geometric()
  {
    @Override
    public String toString()
    {
      String result = "accessorWriteDistributor"; //$NON-NLS-1$
      if (getRepository() != null)
      {
        result += ": " + getRepository().getName(); //$NON-NLS-1$
      }

      return result;
    }
  };

  @ExcludeFromDump
  private transient StoreAccessorPool readerPool = new StoreAccessorPool(this, null);

  @ExcludeFromDump
  private transient StoreAccessorPool writerPool = new StoreAccessorPool(this, null);

  @ExcludeFromDump
  private transient Timer connectionKeepAliveTimer;

  public DBStore()
  {
    super(TYPE, null, set(ChangeFormat.REVISION, ChangeFormat.DELTA), //
        set(RevisionTemporality.AUDITING, RevisionTemporality.NONE), //
        set(RevisionParallelism.NONE, RevisionParallelism.BRANCHING));
  }

  public IMappingStrategy getMappingStrategy()
  {
    return mappingStrategy;
  }

  public void setMappingStrategy(IMappingStrategy mappingStrategy)
  {
    this.mappingStrategy = mappingStrategy;
    mappingStrategy.setStore(this);
  }

  public IDBAdapter getDBAdapter()
  {
    return dbAdapter;
  }

  public void setDBAdapter(IDBAdapter dbAdapter)
  {
    this.dbAdapter = dbAdapter;
  }

  public void setProperties(Map<String, String> properties)
  {
    checkInactive();
    this.properties = properties;
  }

  public Map<String, String> getProperties()
  {
    return properties;
  }

  public int getJDBCFetchSize()
  {
    return jdbcFetchSize;
  }

  public int getIDColumnLength()
  {
    return idColumnLength;
  }

  public IIDHandler getIDHandler()
  {
    return idHandler;
  }

  public IDBDatabase getDatabase()
  {
    return database;
  }

  public Connection getConnection()
  {
    Connection connection = dbConnectionProvider.getConnection();
    if (connection == null)
    {
      throw new DBException("No connection from connection provider: " + dbConnectionProvider); //$NON-NLS-1$
    }

    try
    {
      connection.setAutoCommit(false);
    }
    catch (SQLException ex)
    {
      throw new DBException(ex, "SET AUTO COMMIT = false");
    }

    return connection;
  }

  public void setDBConnectionProvider(IDBConnectionProvider dbConnectionProvider)
  {
    this.dbConnectionProvider = dbConnectionProvider;
  }

  public IMetaDataManager getMetaDataManager()
  {
    return metaDataManager;
  }

  public DurableLockingManager getDurableLockingManager()
  {
    return durableLockingManager;
  }

  public CommitInfoTable getCommitInfoTable()
  {
    return commitInfoTable;
  }

  public UnitMappingTable getUnitMappingTable()
  {
    return unitMappingTable;
  }

  public Timer getConnectionKeepAliveTimer()
  {
    return connectionKeepAliveTimer;
  }

  @Override
  public Set<ChangeFormat> getSupportedChangeFormats()
  {
    if (mappingStrategy.hasDeltaSupport())
    {
      return set(ChangeFormat.DELTA);
    }

    return set(ChangeFormat.REVISION);
  }

  public ProgressDistributor getAccessorWriteDistributor()
  {
    return accessorWriteDistributor;
  }

  public IDBSchema getDBSchema()
  {
    return database.getSchema();
  }

  public void visitAllTables(Connection connection, IDBStore.TableVisitor visitor)
  {
    for (String name : DBUtil.getAllTableNames(connection, getRepository().getName()))
    {
      try
      {
        visitor.visitTable(connection, name);
        connection.commit();
      }
      catch (SQLException ex)
      {
        try
        {
          connection.rollback();
        }
        catch (SQLException ex1)
        {
          throw new DBException(ex1);
        }

        if (!dbAdapter.isColumnNotFoundException(ex))
        {
          throw new DBException(ex);
        }
      }
    }
  }

  public Map<String, String> getPersistentProperties(Set<String> names)
  {
    IDBConnection connection = database.getConnection();
    IDBPreparedStatement stmt = null;
    String sql = null;

    try
    {
      Map<String, String> result = new HashMap<String, String>();
      boolean allProperties = names == null || names.isEmpty();
      if (allProperties)
      {
        sql = CDODBSchema.SQL_SELECT_ALL_PROPERTIES;
        stmt = connection.prepareStatement(sql, ReuseProbability.MEDIUM);
        ResultSet resultSet = null;

        try
        {
          resultSet = stmt.executeQuery();
          while (resultSet.next())
          {
            String key = resultSet.getString(1);
            String value = resultSet.getString(2);
            result.put(key, value);
          }
        }
        finally
        {
          DBUtil.close(resultSet);
        }
      }
      else
      {
        sql = CDODBSchema.SQL_SELECT_PROPERTIES;
        stmt = connection.prepareStatement(sql, ReuseProbability.MEDIUM);
        for (String name : names)
        {
          stmt.setString(1, name);
          ResultSet resultSet = null;

          try
          {
            resultSet = stmt.executeQuery();
            if (resultSet.next())
            {
              String value = resultSet.getString(1);
              result.put(name, value);
            }
          }
          finally
          {
            DBUtil.close(resultSet);
          }
        }
      }

      return result;
    }
    catch (SQLException ex)
    {
      throw new DBException(ex, sql);
    }
    finally
    {
      DBUtil.close(stmt);
      DBUtil.close(connection);
    }
  }

  public void setPersistentProperties(Map<String, String> properties)
  {
    IDBConnection connection = database.getConnection();
    IDBPreparedStatement deleteStmt = connection.prepareStatement(CDODBSchema.SQL_DELETE_PROPERTIES, ReuseProbability.MEDIUM);
    IDBPreparedStatement insertStmt = connection.prepareStatement(CDODBSchema.SQL_INSERT_PROPERTIES, ReuseProbability.MEDIUM);
    String sql = null;

    try
    {
      for (Entry<String, String> entry : properties.entrySet())
      {
        String name = entry.getKey();
        String value = entry.getValue();

        sql = CDODBSchema.SQL_DELETE_PROPERTIES;
        deleteStmt.setString(1, name);
        deleteStmt.executeUpdate();

        sql = CDODBSchema.SQL_INSERT_PROPERTIES;
        insertStmt.setString(1, name);
        insertStmt.setString(2, value);
        insertStmt.executeUpdate();
      }

      sql = null;
      connection.commit();
    }
    catch (SQLException ex)
    {
      throw new DBException(ex, sql);
    }
    finally
    {
      DBUtil.close(insertStmt);
      DBUtil.close(deleteStmt);
      DBUtil.close(connection);
    }
  }

  public void putPersistentProperty(String key, String value)
  {
    Map<String, String> map = new HashMap<String, String>();
    map.put(key, value);

    setPersistentProperties(map);
  }

  public void removePersistentProperties(Set<String> names)
  {
    IDBConnection connection = database.getConnection();
    IDBPreparedStatement stmt = connection.prepareStatement(CDODBSchema.SQL_DELETE_PROPERTIES, ReuseProbability.MEDIUM);

    try
    {
      for (String name : names)
      {
        stmt.setString(1, name);
        stmt.executeUpdate();
      }

      connection.commit();
    }
    catch (SQLException ex)
    {
      throw new DBException(ex, CDODBSchema.SQL_DELETE_PROPERTIES);
    }
    finally
    {
      DBUtil.close(stmt);
      DBUtil.close(connection);
    }
  }

  @Override
  public DBStoreAccessor getReader(ISession session)
  {
    return (DBStoreAccessor)super.getReader(session);
  }

  @Override
  public DBStoreAccessor getWriter(ITransaction transaction)
  {
    return (DBStoreAccessor)super.getWriter(transaction);
  }

  @Override
  protected StoreAccessorPool getReaderPool(ISession session, boolean forReleasing)
  {
    return readerPool;
  }

  @Override
  protected StoreAccessorPool getWriterPool(IView view, boolean forReleasing)
  {
    return writerPool;
  }

  @Override
  protected DBStoreAccessor createReader(ISession session) throws DBException
  {
    return new DBStoreAccessor(this, session);
  }

  @Override
  protected DBStoreAccessor createWriter(ITransaction transaction) throws DBException
  {
    return new DBStoreAccessor(this, transaction);
  }

  public Map<CDOBranch, List<CDORevision>> getAllRevisions()
  {
    final Map<CDOBranch, List<CDORevision>> result = new HashMap<CDOBranch, List<CDORevision>>();

    InternalSession session = null;
    if (!StoreThreadLocal.hasSession())
    {
      session = getRepository().getSessionManager().openSession(null);
      StoreThreadLocal.setSession(session);
    }

    try
    {
      StoreThreadLocal.getAccessor().handleRevisions(null, null, CDOBranchPoint.UNSPECIFIED_DATE, true,
          new CDORevisionHandler.Filtered.Undetached(new CDORevisionHandler()
          {
            public boolean handleRevision(CDORevision revision)
            {
              CDOBranch branch = revision.getBranch();
              List<CDORevision> list = result.get(branch);
              if (list == null)
              {
                list = new ArrayList<CDORevision>();
                result.put(branch, list);
              }

              list.add(revision);
              return true;
            }
          }));
    }
    finally
    {
      if (session != null)
      {
        StoreThreadLocal.release();
        session.close();
      }
    }

    return result;
  }

  public CDOID createObjectID(String val)
  {
    return idHandler.createCDOID(val);
  }

  @Deprecated
  public boolean isLocal(CDOID id)
  {
    throw new UnsupportedOperationException();
  }

  public CDOID getNextCDOID(LongIDStoreAccessor accessor, CDORevision revision)
  {
    return idHandler.getNextCDOID(revision);
  }

  public long getCreationTime()
  {
    return creationTime;
  }

  public void setCreationTime(long creationTime)
  {
    this.creationTime = creationTime;

    Map<String, String> map = new HashMap<String, String>();
    map.put(PROP_REPOSITORY_CREATED, Long.toString(creationTime));
    setPersistentProperties(map);
  }

  public boolean isFirstStart()
  {
    return firstTime;
  }

  @Override
  protected void doBeforeActivate() throws Exception
  {
    super.doBeforeActivate();
    checkNull(mappingStrategy, Messages.getString("DBStore.2")); //$NON-NLS-1$
    checkNull(dbAdapter, Messages.getString("DBStore.1")); //$NON-NLS-1$
    checkNull(dbConnectionProvider, Messages.getString("DBStore.0")); //$NON-NLS-1$
  }

  @Override
  protected void doActivate() throws Exception
  {
    super.doActivate();

    InternalRepository repository = getRepository();
    IDGenerationLocation idGenerationLocation = repository.getIDGenerationLocation();
    if (idGenerationLocation == IDGenerationLocation.CLIENT)
    {
      idHandler = new UUIDHandler(this);
    }
    else
    {
      idHandler = new LongIDHandler(this);
    }

    setObjectIDTypes(idHandler.getObjectIDTypes());
    connectionKeepAliveTimer = new Timer("Connection-Keep-Alive-" + this); //$NON-NLS-1$

    if (properties != null)
    {
      if (idGenerationLocation == IDGenerationLocation.CLIENT)
      {
        String prop = properties.get(IDBStore.Props.ID_COLUMN_LENGTH);
        if (prop != null)
        {
          idColumnLength = Integer.parseInt(prop);
        }
      }

      configureAccessorPool(readerPool, IDBStore.Props.READER_POOL_CAPACITY);
      configureAccessorPool(writerPool, IDBStore.Props.WRITER_POOL_CAPACITY);

      String prop = properties.get(IDBStore.Props.DROP_ALL_DATA_ON_ACTIVATE);
      if (prop != null)
      {
        setDropAllDataOnActivate(Boolean.parseBoolean(prop));
      }

      prop = properties.get(IDBStore.Props.JDBC_FETCH_SIZE);
      if (prop != null)
      {
        jdbcFetchSize = Integer.parseInt(prop);
      }
    }

    Connection connection = getConnection();
    int schemaVersion;

    try
    {
      if (isDropAllDataOnActivate())
      {
        OM.LOG.info("Dropping all tables from repository " + repository.getName() + "...");
        DBUtil.dropAllTables(connection, null);
        connection.commit();
      }

      schemaVersion = selectSchemaVersion(connection);
      if (0 <= schemaVersion && schemaVersion < SCHEMA_VERSION)
      {
        migrateSchema(schemaVersion);
      }

      // CDODBSchema.INSTANCE.create(dbAdapter, connection);
      connection.commit();
    }
    finally
    {
      DBUtil.close(connection);
    }

    String schemaName = repository.getName();
    boolean fixNullableIndexColumns = schemaVersion != FIRST_START && schemaVersion < FIRST_VERSION_WITH_NULLABLE_CHECKS;

    database = DBUtil.openDatabase(dbAdapter, dbConnectionProvider, schemaName, fixNullableIndexColumns);
    IDBSchemaTransaction schemaTransaction = database.openSchemaTransaction();

    try
    {
      schemaTransaction.ensureSchema(CDODBSchema.INSTANCE);
      schemaTransaction.commit();
    }
    finally
    {
      schemaTransaction.close();
    }

    LifecycleUtil.activate(idHandler);
    LifecycleUtil.activate(metaDataManager);
    LifecycleUtil.activate(durableLockingManager);
    LifecycleUtil.activate(mappingStrategy);

    if (repository.getCommitInfoStorage() != CommitInfoStorage.NO)
    {
      commitInfoTable = new CommitInfoTable(this);
      commitInfoTable.activate();
    }

    if (repository.isSupportingUnits())
    {
      unitMappingTable = new UnitMappingTable(mappingStrategy);
      unitMappingTable.activate();
    }

    setRevisionTemporality(mappingStrategy.hasAuditSupport() ? RevisionTemporality.AUDITING : RevisionTemporality.NONE);
    setRevisionParallelism(mappingStrategy.hasBranchingSupport() ? RevisionParallelism.BRANCHING : RevisionParallelism.NONE);

    if (schemaVersion == FIRST_START)
    {
      firstStart();
    }
    else
    {
      reStart();
    }

    putPersistentProperty(PROP_SCHEMA_VERSION, Integer.toString(SCHEMA_VERSION));
  }

  @Override
  protected void doDeactivate() throws Exception
  {
    LifecycleUtil.deactivate(unitMappingTable);
    LifecycleUtil.deactivate(commitInfoTable);
    LifecycleUtil.deactivate(mappingStrategy);
    LifecycleUtil.deactivate(durableLockingManager);
    LifecycleUtil.deactivate(metaDataManager);
    LifecycleUtil.deactivate(idHandler);

    Map<String, String> map = new HashMap<String, String>();
    map.put(PROP_GRACEFULLY_SHUT_DOWN, Boolean.TRUE.toString());
    map.put(PROP_REPOSITORY_STOPPED, Long.toString(getRepository().getTimeStamp()));

    if (getRepository().getIDGenerationLocation() == IDGenerationLocation.STORE)
    {
      map.put(PROP_NEXT_LOCAL_CDOID, Store.idToString(idHandler.getNextLocalObjectID()));
      map.put(PROP_LAST_CDOID, Store.idToString(idHandler.getLastObjectID()));
    }

    map.put(PROP_LAST_BRANCHID, Integer.toString(getLastBranchID()));
    map.put(PROP_LAST_LOCAL_BRANCHID, Integer.toString(getLastLocalBranchID()));
    map.put(PROP_LAST_COMMITTIME, Long.toString(getLastCommitTime()));
    map.put(PROP_LAST_NONLOCAL_COMMITTIME, Long.toString(getLastNonLocalCommitTime()));
    setPersistentProperties(map);

    if (readerPool != null)
    {
      readerPool.dispose();
    }

    if (writerPool != null)
    {
      writerPool.dispose();
    }

    connectionKeepAliveTimer.cancel();
    connectionKeepAliveTimer = null;

    super.doDeactivate();
  }

  protected boolean isFirstStart(Set<IDBTable> createdTables)
  {
    if (createdTables.contains(CDODBSchema.PROPERTIES))
    {
      return true;
    }

    Set<String> names = new HashSet<String>();
    names.add(PROP_REPOSITORY_CREATED);

    Map<String, String> map = getPersistentProperties(names);
    return map.get(PROP_REPOSITORY_CREATED) == null;
  }

  protected void firstStart()
  {
    InternalRepository repository = getRepository();
    setCreationTime(repository.getTimeStamp());
    firstTime = true;
  }

  protected void reStart() throws Exception
  {
    Set<String> names = new HashSet<String>();
    names.add(PROP_REPOSITORY_CREATED);
    names.add(PROP_GRACEFULLY_SHUT_DOWN);

    Map<String, String> map = getPersistentProperties(names);
    creationTime = Long.valueOf(map.get(PROP_REPOSITORY_CREATED));

    if (map.containsKey(PROP_GRACEFULLY_SHUT_DOWN))
    {
      names.clear();

      InternalRepository repository = getRepository();
      boolean generatingIDs = repository.getIDGenerationLocation() == IDGenerationLocation.STORE;
      if (generatingIDs)
      {
        names.add(PROP_NEXT_LOCAL_CDOID);
        names.add(PROP_LAST_CDOID);
      }

      names.add(PROP_LAST_BRANCHID);
      names.add(PROP_LAST_LOCAL_BRANCHID);
      names.add(PROP_LAST_COMMITTIME);
      names.add(PROP_LAST_NONLOCAL_COMMITTIME);
      map = getPersistentProperties(names);

      if (generatingIDs)
      {
        idHandler.setNextLocalObjectID(Store.stringToID(map.get(PROP_NEXT_LOCAL_CDOID)));
        idHandler.setLastObjectID(Store.stringToID(map.get(PROP_LAST_CDOID)));
      }

      setLastBranchID(Integer.valueOf(map.get(PROP_LAST_BRANCHID)));
      setLastLocalBranchID(Integer.valueOf(map.get(PROP_LAST_LOCAL_BRANCHID)));
      setLastCommitTime(Long.valueOf(map.get(PROP_LAST_COMMITTIME)));
      setLastNonLocalCommitTime(Long.valueOf(map.get(PROP_LAST_NONLOCAL_COMMITTIME)));
    }
    else
    {
      repairAfterCrash();
    }

    removePersistentProperties(Collections.singleton(PROP_GRACEFULLY_SHUT_DOWN));
  }

  protected void repairAfterCrash()
  {
    InternalRepository repository = getRepository();
    String name = repository.getName();
    OM.LOG.warn(MessageFormat.format(Messages.getString("DBStore.9"), name)); //$NON-NLS-1$

    Connection connection = getConnection();

    try
    {
      connection.setAutoCommit(false);
      connection.setReadOnly(true);

      mappingStrategy.repairAfterCrash(dbAdapter, connection); // Must update the idHandler

      boolean storeIDs = repository.getIDGenerationLocation() == IDGenerationLocation.STORE;
      CDOID lastObjectID = storeIDs ? idHandler.getLastObjectID() : CDOID.NULL;
      CDOID nextLocalObjectID = storeIDs ? idHandler.getNextLocalObjectID() : CDOID.NULL;

      int branchID = DBUtil.selectMaximumInt(connection, CDODBSchema.BRANCHES_ID);
      setLastBranchID(branchID > 0 ? branchID : 0);

      int localBranchID = DBUtil.selectMinimumInt(connection, CDODBSchema.BRANCHES_ID);
      setLastLocalBranchID(localBranchID < 0 ? localBranchID : 0);

      if (commitInfoTable != null)
      {
        commitInfoTable.repairAfterCrash(connection);
      }
      else
      {
        boolean branching = repository.isSupportingBranches();

        long lastCommitTime = CDOBranchPoint.UNSPECIFIED_DATE;
        long lastNonLocalCommitTime = CDOBranchPoint.UNSPECIFIED_DATE;

        // Unfortunately the package registry is still inactive, so the class mappings can not be used at this point.
        // Use all tables with a "CDO_CREATED" field instead.
        for (String tableName : DBUtil.getAllTableNames(connection, repository.getName()))
        {
          try
          {
            if (CDODBSchema.CDO_OBJECTS.equals(tableName))
            {
              continue;
            }

            IDBTable table = database.getSchema().getTable(tableName);
            IDBField createdField = table.getField(IMappingConstants.ATTRIBUTES_CREATED);
            if (createdField == null)
            {
              continue;
            }

            if (branching)
            {
              IDBField branchField = table.getField(IMappingConstants.ATTRIBUTES_BRANCH);
              if (branchField == null)
              {
                continue;
              }

              lastNonLocalCommitTime = Math.max(lastNonLocalCommitTime,
                  DBUtil.selectMaximumLong(connection, branchField, CDOBranch.MAIN_BRANCH_ID + "<=" + IMappingConstants.ATTRIBUTES_BRANCH));
            }

            lastCommitTime = Math.max(lastCommitTime, DBUtil.selectMaximumLong(connection, createdField));
          }
          catch (Exception ex)
          {
            OM.LOG.warn(ex.getMessage());
          }
        }

        if (lastNonLocalCommitTime == CDOBranchPoint.UNSPECIFIED_DATE)
        {
          lastNonLocalCommitTime = lastCommitTime;
        }

        setLastCommitTime(lastCommitTime);
        setLastNonLocalCommitTime(lastNonLocalCommitTime);
      }

      if (storeIDs)
      {
        OM.LOG.info(MessageFormat.format(Messages.getString("DBStore.10"), name, lastObjectID, nextLocalObjectID, //$NON-NLS-1$
            getLastBranchID(), getLastCommitTime(), getLastNonLocalCommitTime()));
      }
      else
      {
        OM.LOG.info(MessageFormat.format(Messages.getString("DBStore.10b"), name, getLastBranchID(), //$NON-NLS-1$
            getLastCommitTime(), getLastNonLocalCommitTime()));
      }
    }
    catch (SQLException e)
    {
      OM.LOG.error(MessageFormat.format(Messages.getString("DBStore.11"), name), e); //$NON-NLS-1$
      throw new DBException(e);
    }
    finally
    {
      DBUtil.close(connection);
    }
  }

  protected void configureAccessorPool(StoreAccessorPool pool, String property)
  {
    if (pool != null)
    {
      String value = properties.get(property);
      if (value != null)
      {
        int capacity = Integer.parseInt(value);
        pool.setCapacity(capacity);
      }
    }
  }

  protected int selectSchemaVersion(Connection connection) throws SQLException
  {
    Statement statement = null;
    ResultSet resultSet = null;

    try
    {
      statement = connection.createStatement();
      resultSet = statement.executeQuery("SELECT " + CDODBSchema.PROPERTIES_VALUE + " FROM " + CDODBSchema.PROPERTIES + " WHERE " + CDODBSchema.PROPERTIES_NAME
          + "='" + PROP_SCHEMA_VERSION + "'");

      if (resultSet.next())
      {
        String value = resultSet.getString(1);
        return Integer.parseInt(value);
      }

      return 0;
    }
    catch (SQLException ex)
    {
      connection.rollback();
      if (dbAdapter.isTableNotFoundException(ex))
      {
        return FIRST_START;
      }

      throw ex;
    }
    finally
    {
      DBUtil.close(resultSet);
      DBUtil.close(statement);
    }
  }

  protected void migrateSchema(int fromVersion) throws Exception
  {
    Connection connection = null;

    try
    {
      connection = getConnection();

      for (int version = fromVersion; version < SCHEMA_VERSION; version++)
      {
        if (SCHEMA_MIGRATORS[version] != null)
        {
          int nextVersion = version + 1;
          OM.LOG.info("Migrating schema from version " + version + " to version " + nextVersion + "...");
          SCHEMA_MIGRATORS[version].migrateSchema(this, connection);
        }
      }

      connection.commit();
    }
    finally
    {
      DBUtil.close(connection);
    }
  }

  /**
   * @author Eike Stepper
   */
  private static abstract class SchemaMigrator
  {
    public abstract void migrateSchema(DBStore store, Connection connection) throws Exception;
  }

  private static final int FIRST_VERSION_WITH_NULLABLE_CHECKS = 4;

  private static final SchemaMigrator NO_MIGRATION_NEEDED = null;

  private static final SchemaMigrator NON_AUDIT_MIGRATION = new SchemaMigrator()
  {
    @Override
    public void migrateSchema(DBStore store, Connection connection) throws Exception
    {
      InternalRepository repository = store.getRepository();
      if (!repository.isSupportingAudits())
      {
        store.visitAllTables(connection, new IDBStore.TableVisitor()
        {
          public void visitTable(Connection connection, String name) throws SQLException
          {
            Statement statement = null;

            try
            {
              statement = connection.createStatement();

              String from = " FROM " + name + " WHERE " + ATTRIBUTES_VERSION + "<" + CDOBranchVersion.FIRST_VERSION;

              statement.executeUpdate("DELETE FROM " + CDODBSchema.CDO_OBJECTS + " WHERE " + ATTRIBUTES_ID + " IN (SELECT " + ATTRIBUTES_ID + from + ")");

              statement.executeUpdate("DELETE" + from);
            }
            finally
            {
              DBUtil.close(statement);
            }
          }
        });
      }
    }
  };

  private static final SchemaMigrator LOB_SIZE_MIGRATION = new SchemaMigrator()
  {
    @Override
    public void migrateSchema(DBStore store, final Connection connection) throws Exception
    {
      Statement statement = null;

      try
      {
        statement = connection.createStatement();

        IDBAdapter dbAdapter = store.getDBAdapter();
        String sql = dbAdapter.sqlRenameField(CDODBSchema.LOBS_SIZE, "size");
        statement.execute(sql);
      }
      finally
      {
        DBUtil.close(statement);
      }
    }
  };

  private static final SchemaMigrator NULLABLE_COLUMNS_MIGRATION = null;

  private static final SchemaMigrator[] SCHEMA_MIGRATORS = { NO_MIGRATION_NEEDED, NON_AUDIT_MIGRATION, LOB_SIZE_MIGRATION, NULLABLE_COLUMNS_MIGRATION };

  static
  {
    if (SCHEMA_MIGRATORS.length != SCHEMA_VERSION)
    {
      throw new Error("There must be exactly " + SCHEMA_VERSION + " schema migrators provided");
    }
  }
}

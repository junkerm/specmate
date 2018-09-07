/*
 * Copyright (c) 2016 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 *    Stefan Winkler - bug 259402
 *    Stefan Winkler - redesign (prepared statements)
 *    Stefan Winkler - bug 276926
 */
package org.eclipse.emf.cdo.server.internal.db.mapping.horizontal;

import org.eclipse.emf.cdo.common.branch.CDOBranchPoint;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.revision.CDORevision;
import org.eclipse.emf.cdo.common.revision.CDORevisionHandler;
import org.eclipse.emf.cdo.common.revision.CDORevisionUtil;
import org.eclipse.emf.cdo.server.IView;
import org.eclipse.emf.cdo.server.db.IDBStore;
import org.eclipse.emf.cdo.server.db.IDBStoreAccessor;
import org.eclipse.emf.cdo.server.db.IIDHandler;
import org.eclipse.emf.cdo.server.db.IMetaDataManager;
import org.eclipse.emf.cdo.server.db.mapping.IClassMappingUnitSupport;
import org.eclipse.emf.cdo.server.db.mapping.IMappingStrategy;
import org.eclipse.emf.cdo.server.internal.db.CDODBSchema;

import org.eclipse.net4j.db.BatchedStatement;
import org.eclipse.net4j.db.DBException;
import org.eclipse.net4j.db.DBType;
import org.eclipse.net4j.db.DBUtil;
import org.eclipse.net4j.db.IDBConnection;
import org.eclipse.net4j.db.IDBDatabase;
import org.eclipse.net4j.db.IDBDatabase.RunnableWithSchema;
import org.eclipse.net4j.db.IDBPreparedStatement;
import org.eclipse.net4j.db.IDBPreparedStatement.ReuseProbability;
import org.eclipse.net4j.db.ddl.IDBIndex;
import org.eclipse.net4j.db.ddl.IDBSchema;
import org.eclipse.net4j.db.ddl.IDBTable;
import org.eclipse.net4j.util.lifecycle.Lifecycle;
import org.eclipse.net4j.util.om.monitor.OMMonitor;

import org.eclipse.emf.ecore.EClass;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @author Eike Stepper
 * @since 4.0
 */
public class UnitMappingTable extends Lifecycle implements IMappingConstants
{
  public static final String UNITS = "CDO_UNITS"; //$NON-NLS-1$

  public static final String UNITS_ELEM = "CDO_ELEM"; //$NON-NLS-1$

  public static final String UNITS_UNIT = "CDO_UNIT"; //$NON-NLS-1$

  // public static final String UNITS_CREATED = "CDO_CREATED"; //$NON-NLS-1$

  private static final String SQL_SELECT_ROOTS = "SELECT DISTINCT " + UNITS_UNIT + " FROM " + UNITS;

  private static final String SQL_INSERT_MAPPINGS = "INSERT INTO " + UNITS + " (" + UNITS_ELEM + ", " + UNITS_UNIT + ") VALUES (?, ?)";

  // private static final String SQL_SELECT_SIZE = "SELECT COUNT(" + UNITS_ELEM + ") FROM " + UNITS + " WHERE "
  // + UNITS_UNIT + "=?";

  private static final String SQL_SELECT_CLASSES = "SELECT " + ATTRIBUTES_CLASS + ", COUNT(" + UNITS_ELEM + ") FROM " + UNITS + ", " + CDODBSchema.CDO_OBJECTS
      + " WHERE " + UNITS_ELEM + "=" + ATTRIBUTES_ID + " AND " + UNITS_UNIT + "=? GROUP BY " + ATTRIBUTES_CLASS;

  private static final int WRITE_UNIT_MAPPING_BATCH_SIZE = 100000;

  private final IMappingStrategy mappingStrategy;

  private IDBTable table;

  public UnitMappingTable(IMappingStrategy mappingStrategy)
  {
    this.mappingStrategy = mappingStrategy;
  }

  public List<CDOID> readUnitRoots(IDBStoreAccessor accessor)
  {
    List<CDOID> rootIDs = new ArrayList<CDOID>();
    IIDHandler idHandler = mappingStrategy.getStore().getIDHandler();
    Statement stmt = null;

    try
    {
      stmt = accessor.getDBConnection().createStatement();

      if (DBUtil.isTracerEnabled())
      {
        DBUtil.trace(stmt.toString());
      }

      ResultSet resultSet = stmt.executeQuery(SQL_SELECT_ROOTS);
      while (resultSet.next())
      {
        CDOID rootID = idHandler.getCDOID(resultSet, 1);
        rootIDs.add(rootID);
      }
    }
    catch (SQLException ex)
    {
      throw new DBException(ex);
    }
    finally
    {
      DBUtil.close(stmt);
    }

    return rootIDs;
  }

  public void readUnitRevisions(IDBStoreAccessor accessor, IView view, CDOID rootID, CDORevisionHandler revisionHandler, OMMonitor monitor)
  {
    IDBStore store = mappingStrategy.getStore();
    IIDHandler idHandler = store.getIDHandler();
    IMetaDataManager metaDataManager = store.getMetaDataManager();

    long timeStamp = view.isHistorical() ? view.getTimeStamp() : store.getRepository().getTimeStamp();
    CDOBranchPoint branchPoint = view.getBranch().getPoint(timeStamp);

    IDBConnection connection = accessor.getDBConnection();
    IDBPreparedStatement stmt = connection.prepareStatement(SQL_SELECT_CLASSES, ReuseProbability.HIGH);

    int jdbcFetchSize = store.getJDBCFetchSize();
    int oldFetchSize = -1;

    try
    {
      idHandler.setCDOID(stmt, 1, rootID);

      oldFetchSize = stmt.getFetchSize();
      stmt.setFetchSize(jdbcFetchSize);
      ResultSet resultSet = stmt.executeQuery();

      while (resultSet.next())
      {
        CDOID classID = idHandler.getCDOID(resultSet, 1);
        EClass eClass = (EClass)metaDataManager.getMetaInstance(classID);

        IClassMappingUnitSupport classMapping = (IClassMappingUnitSupport)mappingStrategy.getClassMapping(eClass);
        classMapping.readUnitRevisions(accessor, branchPoint, rootID, revisionHandler);
      }
    }
    catch (SQLException ex)
    {
      throw new DBException(ex);
    }
    finally
    {
      if (oldFetchSize != -1)
      {
        try
        {
          stmt.setFetchSize(oldFetchSize);
        }
        catch (SQLException ex)
        {
          throw new DBException(ex);
        }
      }

      DBUtil.close(stmt);
    }
  }

  public BatchedStatement initUnit(IDBStoreAccessor accessor, long timeStamp, IView view, CDOID rootID, CDORevisionHandler revisionHandler,
      Set<CDOID> initializedIDs, OMMonitor monitor)
  {
    IIDHandler idHandler = mappingStrategy.getStore().getIDHandler();
    IDBConnection connection = accessor.getDBConnection();
    BatchedStatement stmt = DBUtil.batched(connection.prepareStatement(SQL_INSERT_MAPPINGS, ReuseProbability.HIGH), WRITE_UNIT_MAPPING_BATCH_SIZE);

    try
    {
      CDORevision revision = view.getRevision(rootID);

      initUnit(stmt, view, rootID, revisionHandler, initializedIDs, timeStamp, idHandler, revision, monitor);
      return stmt;
    }
    catch (SQLException ex)
    {
      throw new DBException(ex);
    }
    finally
    {
      // Don't close the statement; that's done later in finishUnit().
    }
  }

  private void initUnit(BatchedStatement stmt, IView view, CDOID rootID, CDORevisionHandler revisionHandler, Set<CDOID> initializedIDs, long timeStamp,
      IIDHandler idHandler, CDORevision revision, OMMonitor monitor) throws SQLException
  {
    revisionHandler.handleRevision(revision);

    CDOID id = revision.getID();
    initializedIDs.add(id);

    writeUnitMapping(stmt, rootID, timeStamp, idHandler, id);

    List<CDORevision> children = CDORevisionUtil.getChildRevisions(revision, view, true);
    for (CDORevision child : children)
    {
      initUnit(stmt, view, rootID, revisionHandler, initializedIDs, timeStamp, idHandler, child, monitor);
    }
  }

  public void finishUnit(BatchedStatement stmt, CDOID rootID, List<CDOID> ids, long timeStamp)
  {
    IDBStore store = mappingStrategy.getStore();
    IIDHandler idHandler = store.getIDHandler();
    Connection connection = null;

    try
    {
      connection = stmt.getConnection();

      for (CDOID id : ids)
      {
        writeUnitMapping(stmt, rootID, timeStamp, idHandler, id);
      }
    }
    catch (SQLException ex)
    {
      DBUtil.rollbackSilently(connection);
      throw new DBException(ex);
    }
    finally
    {
      DBUtil.close(stmt);
    }

    try
    {
      connection.commit();
    }
    catch (SQLException ex)
    {
      throw new DBException(ex);
    }
  }

  public void writeUnitMappings(IDBStoreAccessor accessor, Map<CDOID, CDOID> unitMappings, long timeStamp)
  {
    IIDHandler idHandler = mappingStrategy.getStore().getIDHandler();
    IDBConnection connection = accessor.getDBConnection();
    BatchedStatement stmt = DBUtil.batched(connection.prepareStatement(SQL_INSERT_MAPPINGS, ReuseProbability.HIGH), WRITE_UNIT_MAPPING_BATCH_SIZE);

    try
    {
      for (Entry<CDOID, CDOID> entry : unitMappings.entrySet())
      {
        CDOID id = entry.getKey();
        CDOID rootID = entry.getValue();
        writeUnitMapping(stmt, rootID, timeStamp, idHandler, id);
      }
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

  private void writeUnitMapping(BatchedStatement stmt, CDOID rootID, long timeStamp, IIDHandler idHandler, CDOID id) throws SQLException
  {
    idHandler.setCDOID(stmt, 1, id);
    idHandler.setCDOID(stmt, 2, rootID);
    // stmt.setLong(3, timeStamp);
    stmt.executeUpdate();
  }

  @Override
  protected void doActivate() throws Exception
  {
    super.doActivate();

    IDBStore store = mappingStrategy.getStore();
    final DBType idType = store.getIDHandler().getDBType();
    final int idLength = store.getIDColumnLength();

    IDBDatabase database = store.getDatabase();
    table = database.getSchema().getTable(UNITS);
    if (table == null)
    {
      database.updateSchema(new RunnableWithSchema()
      {
        public void run(IDBSchema schema)
        {
          table = schema.addTable(UNITS);
          table.addField(UNITS_ELEM, idType, idLength, true);
          table.addField(UNITS_UNIT, idType, idLength);
          // table.addField(UNITS_CREATED, DBType.BIGINT);
          table.addIndex(IDBIndex.Type.PRIMARY_KEY, UNITS_ELEM);
          table.addIndex(IDBIndex.Type.NON_UNIQUE, UNITS_UNIT);
        }
      });
    }
  }

  @Override
  protected void doDeactivate() throws Exception
  {
    table = null;
    super.doDeactivate();
  }
}

/*
 * Copyright (c) 2010-2014, 2016 Eike Stepper (Loehne, Germany) and others.
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

import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.model.CDOClassifierRef;
import org.eclipse.emf.cdo.common.protocol.CDODataInput;
import org.eclipse.emf.cdo.common.protocol.CDODataOutput;
import org.eclipse.emf.cdo.server.db.IDBStore;
import org.eclipse.emf.cdo.server.db.IDBStoreAccessor;
import org.eclipse.emf.cdo.server.db.IIDHandler;
import org.eclipse.emf.cdo.server.internal.db.CDODBSchema;
import org.eclipse.emf.cdo.spi.server.InternalRepository;

import org.eclipse.net4j.db.DBException;
import org.eclipse.net4j.db.DBType;
import org.eclipse.net4j.db.DBUtil;
import org.eclipse.net4j.db.IDBDatabase;
import org.eclipse.net4j.db.IDBDatabase.RunnableWithSchema;
import org.eclipse.net4j.db.IDBPreparedStatement;
import org.eclipse.net4j.db.IDBPreparedStatement.ReuseProbability;
import org.eclipse.net4j.db.ddl.IDBIndex;
import org.eclipse.net4j.db.ddl.IDBSchema;
import org.eclipse.net4j.db.ddl.IDBTable;
import org.eclipse.net4j.util.om.monitor.OMMonitor;

import org.eclipse.emf.ecore.EClass;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Eike Stepper
 * @since 4.0
 */
public class ObjectTypeTable extends AbstractObjectTypeMapper implements IMappingConstants
{
  private IDBTable table;

  private String sqlDelete;

  private String sqlInsert;

  private String sqlSelect;

  public ObjectTypeTable()
  {
  }

  public final CDOClassifierRef getObjectType(IDBStoreAccessor accessor, CDOID id)
  {
    IIDHandler idHandler = getMappingStrategy().getStore().getIDHandler();
    IDBPreparedStatement stmt = accessor.getDBConnection().prepareStatement(sqlSelect, ReuseProbability.MAX);

    try
    {
      idHandler.setCDOID(stmt, 1, id);

      if (DBUtil.isTracerEnabled())
      {
        DBUtil.trace(stmt.toString());
      }

      ResultSet resultSet = stmt.executeQuery();

      if (!resultSet.next())
      {
        if (DBUtil.isTracerEnabled())
        {
          DBUtil.trace("ClassID for CDOID " + id + " not found"); //$NON-NLS-1$ //$NON-NLS-2$
        }

        return null;
      }

      CDOID classID = idHandler.getCDOID(resultSet, 1);
      EClass eClass = (EClass)getMetaDataManager().getMetaInstance(classID);
      return new CDOClassifierRef(eClass);
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

  public final boolean putObjectType(IDBStoreAccessor accessor, long timeStamp, CDOID id, EClass type)
  {
    IDBStore store = getMappingStrategy().getStore();
    IIDHandler idHandler = store.getIDHandler();
    IDBPreparedStatement stmt = accessor.getDBConnection().prepareStatement(sqlInsert, ReuseProbability.MAX);

    try
    {
      idHandler.setCDOID(stmt, 1, id);
      idHandler.setCDOID(stmt, 2, getMetaDataManager().getMetaID(type, timeStamp));
      stmt.setLong(3, timeStamp);

      if (DBUtil.isTracerEnabled())
      {
        DBUtil.trace(stmt.toString());
      }

      int result = stmt.executeUpdate();
      if (result != 1)
      {
        throw new DBException("Object type could not be inserted: " + id); //$NON-NLS-1$
      }

      return true;
    }
    catch (SQLException ex)
    {
      if (store.getDBAdapter().isDuplicateKeyException(ex))
      {
        // Unique key violation can occur in rare cases (merging new objects from other branches)
        return false;
      }

      throw new DBException(ex);
    }
    finally
    {
      DBUtil.close(stmt);
    }
  }

  public final boolean removeObjectType(IDBStoreAccessor accessor, CDOID id)
  {
    IIDHandler idHandler = getMappingStrategy().getStore().getIDHandler();
    IDBPreparedStatement stmt = accessor.getDBConnection().prepareStatement(sqlDelete, ReuseProbability.MAX);

    try
    {
      idHandler.setCDOID(stmt, 1, id);

      if (DBUtil.isTracerEnabled())
      {
        DBUtil.trace(stmt.toString());
      }

      int result = stmt.executeUpdate();
      return result == 1;
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

  public CDOID getMaxID(Connection connection, IIDHandler idHandler)
  {
    Statement stmt = null;
    ResultSet resultSet = null;

    try
    {
      stmt = connection.createStatement();
      resultSet = stmt.executeQuery("SELECT MAX(" + ATTRIBUTES_ID + ") FROM " + table);

      if (resultSet.next())
      {
        return idHandler.getCDOID(resultSet, 1);
      }

      return null;
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
    String where = " WHERE " + ATTRIBUTES_CREATED + " BETWEEN " + fromCommitTime + " AND " + toCommitTime;
    DBUtil.serializeTable(out, connection, table, null, where);
  }

  public void rawImport(Connection connection, CDODataInput in, OMMonitor monitor) throws IOException
  {
    DBUtil.deserializeTable(in, connection, table, monitor);
  }

  @Override
  protected void doActivate() throws Exception
  {
    super.doActivate();

    final IDBStore store = getMappingStrategy().getStore();
    final DBType idType = store.getIDHandler().getDBType();
    final int idLength = store.getIDColumnLength();

    IDBDatabase database = store.getDatabase();
    table = database.getSchema().getTable(CDODBSchema.CDO_OBJECTS);
    if (table == null)
    {
      database.updateSchema(new RunnableWithSchema()
      {
        public void run(IDBSchema schema)
        {
          table = schema.addTable(CDODBSchema.CDO_OBJECTS);
          table.addField(ATTRIBUTES_ID, idType, idLength, true);
          table.addField(ATTRIBUTES_CLASS, idType, idLength);
          table.addField(ATTRIBUTES_CREATED, DBType.BIGINT);
          table.addIndex(IDBIndex.Type.PRIMARY_KEY, ATTRIBUTES_ID);

          InternalRepository repository = (InternalRepository)store.getRepository();
          if (repository.isSupportingUnits())
          {
            table.addIndex(IDBIndex.Type.NON_UNIQUE, ATTRIBUTES_CLASS);
          }
        }
      });
    }

    sqlSelect = "SELECT " + ATTRIBUTES_CLASS + " FROM " + table + " WHERE " + ATTRIBUTES_ID + "=?";
    sqlInsert = "INSERT INTO " + table + "(" + ATTRIBUTES_ID + "," + ATTRIBUTES_CLASS + "," + ATTRIBUTES_CREATED + ") VALUES (?, ?, ?)";
    sqlDelete = "DELETE FROM " + table + " WHERE " + ATTRIBUTES_ID + "=?";
  }

  @Override
  protected void doDeactivate() throws Exception
  {
    sqlDelete = null;
    sqlInsert = null;
    sqlSelect = null;
    table = null;
    super.doDeactivate();
  }
}

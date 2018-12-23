/*
 * Copyright (c) 2007-2013, 2015, 2016 Eike Stepper (Loehne, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 *    Stefan Winkler - 271444: [DB] Multiple refactorings bug 271444
 *    Stefan Winkler - 249610: [DB] Support external references (Implementation)
 *    Andre Dietisheim - bug 256649
 *
 */
package org.eclipse.emf.cdo.server.internal.db;

import org.eclipse.net4j.db.DBType;
import org.eclipse.net4j.db.DBUtil;
import org.eclipse.net4j.db.ddl.IDBField;
import org.eclipse.net4j.db.ddl.IDBIndex;
import org.eclipse.net4j.db.ddl.IDBSchema;
import org.eclipse.net4j.db.ddl.IDBTable;
import org.eclipse.net4j.internal.db.ddl.DBNamedElement;
import org.eclipse.net4j.spi.db.ddl.InternalDBSchema;

/**
 * @author Eike Stepper
 */
public class CDODBSchema
{
  public static final IDBSchema INSTANCE = DBUtil.createSchema("CDO");

  /**
   * DBTable cdo_properties
   */
  public static final IDBTable PROPERTIES = INSTANCE.addTable("cdo_properties"); //$NON-NLS-1$

  public static final IDBField PROPERTIES_NAME = //
      PROPERTIES.addField("name", DBType.VARCHAR, 255, true); //$NON-NLS-1$

  public static final IDBField PROPERTIES_VALUE = //
      PROPERTIES.addField("value", DBType.LONGVARCHAR); //$NON-NLS-1$

  public static final IDBIndex INDEX_PROPERTIES_PK = //
      PROPERTIES.addIndex(IDBIndex.Type.PRIMARY_KEY, PROPERTIES_NAME);

  public static final String SQL_DELETE_PROPERTIES = "DELETE FROM " + PROPERTIES + " WHERE " + PROPERTIES_NAME + "=?"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

  public static final String SQL_INSERT_PROPERTIES = "INSERT INTO " + PROPERTIES + " (" + PROPERTIES_NAME + ", " //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
      + PROPERTIES_VALUE + ") VALUES (?, ?)"; //$NON-NLS-1$

  public static final String SQL_SELECT_PROPERTIES = "SELECT " + PROPERTIES_VALUE + " FROM " + PROPERTIES + " WHERE " //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
      + PROPERTIES_NAME + "=?"; //$NON-NLS-1$

  public static final String SQL_SELECT_ALL_PROPERTIES = "SELECT " + PROPERTIES_NAME + ", " + PROPERTIES_VALUE //$NON-NLS-1$ //$NON-NLS-2$
      + " FROM " + PROPERTIES; //$NON-NLS-1$

  /**
   * DBTable cdo_package_units
   */
  public static final IDBTable PACKAGE_UNITS = INSTANCE.addTable("cdo_package_units"); //$NON-NLS-1$

  public static final IDBField PACKAGE_UNITS_ID = //
      PACKAGE_UNITS.addField("id", DBType.VARCHAR, 255, true); //$NON-NLS-1$

  public static final IDBField PACKAGE_UNITS_ORIGINAL_TYPE = //
      PACKAGE_UNITS.addField("original_type", DBType.INTEGER); //$NON-NLS-1$

  public static final IDBField PACKAGE_UNITS_TIME_STAMP = //
      PACKAGE_UNITS.addField("time_stamp", DBType.BIGINT); //$NON-NLS-1$

  public static final IDBField PACKAGE_UNITS_PACKAGE_DATA = //
      PACKAGE_UNITS.addField("package_data", DBType.BLOB); //$NON-NLS-1$

  public static final IDBIndex INDEX_PACKAGE_UNITS_PK = //
      PACKAGE_UNITS.addIndex(IDBIndex.Type.PRIMARY_KEY, PACKAGE_UNITS_ID);

  /**
   * DBTable cdo_packages
   */
  public static final IDBTable PACKAGE_INFOS = INSTANCE.addTable("cdo_package_infos"); //$NON-NLS-1$

  public static final IDBField PACKAGE_INFOS_URI = //
      PACKAGE_INFOS.addField("uri", DBType.VARCHAR, 255, true); //$NON-NLS-1$

  public static final IDBField PACKAGE_INFOS_PARENT = //
      PACKAGE_INFOS.addField("parent", DBType.VARCHAR, 255); //$NON-NLS-1$

  public static final IDBField PACKAGE_INFOS_UNIT = //
      PACKAGE_INFOS.addField("unit", DBType.VARCHAR, 255); //$NON-NLS-1$

  public static final IDBIndex INDEX_PACKAGE_INFOS_PK = //
      PACKAGE_INFOS.addIndex(IDBIndex.Type.PRIMARY_KEY, PACKAGE_INFOS_URI);

  public static final IDBIndex INDEX_PACKAGE_INFOS_PARENT = //
      PACKAGE_INFOS.addIndex(IDBIndex.Type.NON_UNIQUE, PACKAGE_INFOS_PARENT);

  public static final IDBIndex INDEX_PACKAGE_INFOS_UNIT = //
      PACKAGE_INFOS.addIndex(IDBIndex.Type.NON_UNIQUE, PACKAGE_INFOS_UNIT);

  /**
   * DBTable cdo_branches
   */
  public static final IDBTable BRANCHES = INSTANCE.addTable("cdo_branches"); //$NON-NLS-1$

  public static final IDBField BRANCHES_ID = //
      BRANCHES.addField("id", DBType.INTEGER, true); //$NON-NLS-1$

  public static final IDBField BRANCHES_NAME = //
      BRANCHES.addField("name", DBType.VARCHAR); //$NON-NLS-1$

  public static final IDBField BRANCHES_BASE_BRANCH_ID = //
      BRANCHES.addField("base_id", DBType.INTEGER); //$NON-NLS-1$

  public static final IDBField BRANCHES_BASE_TIMESTAMP = //
      BRANCHES.addField("base_time", DBType.BIGINT); //$NON-NLS-1$

  public static final IDBIndex INDEX_BRANCHES_ID = //
      BRANCHES.addIndex(IDBIndex.Type.PRIMARY_KEY, BRANCHES_ID);

  public static final String SQL_CREATE_BRANCH = "INSERT INTO " + BRANCHES + " (" + BRANCHES_ID + ", " + BRANCHES_NAME //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
      + ", " + BRANCHES_BASE_BRANCH_ID + ", " + BRANCHES_BASE_TIMESTAMP + ") VALUES (?, ?, ?, ?)"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

  public static final String SQL_LOAD_BRANCH = "SELECT " + BRANCHES_NAME + ", " + BRANCHES_BASE_BRANCH_ID + ", " //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
      + BRANCHES_BASE_TIMESTAMP + " FROM " + BRANCHES + " WHERE " + BRANCHES_ID + "=?"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

  public static final String SQL_RENAME_BRANCH = "UPDATE " + BRANCHES + " SET " + BRANCHES_NAME + "=?" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
      + " WHERE " + BRANCHES_ID + "=?"; //$NON-NLS-1$ //$NON-NLS-2$

  public static final String SQL_LOAD_SUB_BRANCHES = "SELECT " + BRANCHES_ID + ", " + BRANCHES_NAME + ", " //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
      + BRANCHES_BASE_TIMESTAMP + " FROM " + BRANCHES + " WHERE " + BRANCHES_BASE_BRANCH_ID + "=?"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

  public static final String SQL_LOAD_BRANCHES = "SELECT " + BRANCHES_ID + ", " + BRANCHES_NAME + ", " + //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
      BRANCHES_BASE_BRANCH_ID + ", " + BRANCHES_BASE_TIMESTAMP //$NON-NLS-1$
      + " FROM " + BRANCHES + " WHERE " + BRANCHES_ID + " BETWEEN ? AND ? ORDER BY " + BRANCHES_ID; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

  /**
   * DBTable cdo_lobs
   */
  public static final IDBTable LOBS = INSTANCE.addTable("cdo_lobs"); //$NON-NLS-1$

  public static final IDBField LOBS_ID = //
      LOBS.addField("id", DBType.VARCHAR, 64, true); //$NON-NLS-1$

  public static final IDBField LOBS_SIZE = //
      LOBS.addField("lsize", DBType.BIGINT); //$NON-NLS-1$

  public static final IDBField LOBS_BDATA = //
      LOBS.addField("bdata", DBType.BLOB); //$NON-NLS-1$

  public static final IDBField LOBS_CDATA = //
      LOBS.addField("cdata", DBType.CLOB); //$NON-NLS-1$

  public static final IDBIndex INDEX_LOBS_ID = //
      LOBS.addIndex(IDBIndex.Type.PRIMARY_KEY, LOBS_ID);

  public static final String SQL_QUERY_LOBS = "SELECT 1 FROM " + LOBS + " WHERE " + LOBS_ID //$NON-NLS-1$ //$NON-NLS-2$
      + "=?"; //$NON-NLS-1$

  public static final String SQL_HANDLE_LOBS = "SELECT " + LOBS_ID + ", " + LOBS_SIZE + ", " //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
      + LOBS_BDATA + ", " + LOBS_CDATA + " FROM " + LOBS; //$NON-NLS-1$ //$NON-NLS-2$

  public static final String SQL_LOAD_LOB = "SELECT " + LOBS_SIZE + ", " + LOBS_BDATA + ", " //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
      + LOBS_CDATA + " FROM " + LOBS + " WHERE " + LOBS_ID + "=?"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

  public static final String SQL_WRITE_BLOB = "INSERT INTO " + LOBS + "(" + LOBS_ID + ", " //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
      + LOBS_SIZE + ", " + LOBS_BDATA + ") VALUES(?, ?, ?)"; //$NON-NLS-1$ //$NON-NLS-2$

  public static final String SQL_WRITE_CLOB = "INSERT INTO " + LOBS + "(" + LOBS_ID + ", " //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
      + LOBS_SIZE + ", " + LOBS_CDATA + ") VALUES(?, ?, ?)"; //$NON-NLS-1$ //$NON-NLS-2$

  /**
   * Name of object table
   */
  public static final String CDO_OBJECTS = DBNamedElement.name("cdo_objects"); //$NON-NLS-1$

  static
  {
    ((InternalDBSchema)INSTANCE).lock();
  }
}

/*
 * Copyright (c) 2007-2013, 2016, 2018 Eike Stepper (Loehne, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.emf.cdo.server.db;

import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.server.IStoreAccessor;
import org.eclipse.emf.cdo.server.IStoreAccessor.UnitSupport;

import org.eclipse.net4j.db.IDBConnection;
import org.eclipse.net4j.db.ddl.IDBTable;

import org.eclipse.emf.ecore.EClass;

import java.sql.Connection;

/**
 * A {@link IStoreAccessor store accessor} for CDO's proprietary object/relational mapper.
 *
 * @author Eike Stepper
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface IDBStoreAccessor extends IStoreAccessor.Raw2, UnitSupport
{
  public IDBStore getStore();

  /**
   * @since 4.2
   */
  public IDBConnection getDBConnection();

  public Connection getConnection();

  /**
   * @since 4.5
   */
  public EClass getObjectType(CDOID id);

  /**
   * @since 2.0
   * @deprecated As of 4.2 use {@link IDBConnection#prepareStatement(String, org.eclipse.net4j.db.IDBPreparedStatement.ReuseProbability)}.
   */
  @Deprecated
  public IPreparedStatementCache getStatementCache();

  /**
   * @since 4.6
   */
  public void tableCreated(IDBTable table);
}

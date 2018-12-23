/*
 * Copyright (c) 2016 Eike Stepper (Loehne, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.emf.cdo.server.db.mapping;

import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.server.db.IDBStoreAccessor;
import org.eclipse.emf.cdo.server.db.IIDHandler;

import org.eclipse.net4j.util.collection.MoveableList;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Interface to complement {@link IListMapping} in order to provide unit support.
 *
 * @author Eike Stepper
 * @since 4.4
 */
public interface IListMappingUnitSupport extends IListMapping
{
  public ResultSet queryUnitEntries(IDBStoreAccessor accessor, IIDHandler idHandler, long timeStamp, CDOID rootID) throws SQLException;

  public void readUnitEntries(ResultSet resultSet, IIDHandler idHandler, CDOID id, MoveableList<Object> list) throws SQLException;
}

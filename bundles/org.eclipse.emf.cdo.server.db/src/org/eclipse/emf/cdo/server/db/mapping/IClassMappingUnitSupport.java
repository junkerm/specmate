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

import org.eclipse.emf.cdo.common.branch.CDOBranchPoint;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.revision.CDORevisionHandler;
import org.eclipse.emf.cdo.server.db.IDBStoreAccessor;

import java.sql.SQLException;

/**
 * An extension interface for {@link IClassMapping class mappings} that support <i>units</i>.
 *
 * @author Eike Stepper
 * @since 4.4
 */
public interface IClassMappingUnitSupport extends IClassMapping
{
  public void readUnitRevisions(IDBStoreAccessor accessor, CDOBranchPoint branchPoint, CDOID rootID, CDORevisionHandler revisionHandler) throws SQLException;
}

/*
 * Copyright (c) 2012, 2013 Eike Stepper (Loehne, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.emf.cdo.server;

import org.eclipse.emf.cdo.common.branch.CDOBranchPoint;
import org.eclipse.emf.cdo.common.revision.CDORevision;
import org.eclipse.emf.cdo.common.security.CDOPermission;

import java.util.Set;

/**
 * Provides the protection level of {@link CDORevision revisions} in the context of a specific user.
 *
 * @author Eike Stepper
 * @since 4.1
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface IPermissionManager
{
  /**
   * @deprecated As of 4.2 call {@link #getPermission(CDORevision, CDOBranchPoint, ISession)}.
   */
  @Deprecated
  public CDOPermission getPermission(CDORevision revision, CDOBranchPoint securityContext, String userID);

  /**
   * @since 4.2
   */
  public CDOPermission getPermission(CDORevision revision, CDOBranchPoint securityContext, ISession session);

  /**
   * @since 4.3
   */
  public boolean hasAnyRule(ISession session, Set<? extends Object> rules);
}

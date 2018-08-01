/*
 * Copyright (c) 2009-2013, 2015 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 *    Andre Dietisheim - bug 256649
 *    Christian W. Damus (CEA LIST) - bug 399306
 */
package org.eclipse.emf.cdo.internal.server.embedded;

import org.eclipse.emf.cdo.common.lob.CDOLobStore;
import org.eclipse.emf.cdo.common.revision.CDORevisionCache;
import org.eclipse.emf.cdo.common.revision.CDORevisionUtil;
import org.eclipse.emf.cdo.internal.server.embedded.EmbeddedClientSessionConfiguration.RepositoryInfo;
import org.eclipse.emf.cdo.server.embedded.CDOSession;
import org.eclipse.emf.cdo.spi.common.branch.InternalCDOBranchManager;
import org.eclipse.emf.cdo.spi.common.commit.InternalCDOCommitInfoManager;
import org.eclipse.emf.cdo.spi.common.model.InternalCDOPackageRegistry;
import org.eclipse.emf.cdo.spi.common.revision.InternalCDORevisionManager;
import org.eclipse.emf.cdo.spi.server.InternalRepository;

import org.eclipse.emf.internal.cdo.session.CDOSessionImpl;

/**
 * @author Eike Stepper
 * @deprecated Not yet supported.
 */
@Deprecated
public class EmbeddedClientSession extends CDOSessionImpl implements CDOSession
{
  private InternalRepository repository;

  public EmbeddedClientSession()
  {
  }

  public InternalRepository getRepository()
  {
    return repository;
  }

  @Override
  public InternalCDOPackageRegistry getPackageRegistry()
  {
    return getRepository().getPackageRegistry();
  }

  @Override
  public InternalCDOBranchManager getBranchManager()
  {
    return getRepository().getBranchManager();
  }

  @Override
  public InternalCDOCommitInfoManager getCommitInfoManager()
  {
    return getRepository().getCommitInfoManager();
  }

  @Override
  public CDOLobStore getLobStore()
  {
    throw new UnsupportedOperationException();
  }

  /**
   * Changing the user's password in an embedded client session is not supported:
   * it must be done interactively with the user in a client session.
   *
   * @since 4.3
   */
  public void changeCredentials()
  {
    throw new UnsupportedOperationException();
  }

  /**
   * Resetting a user's password in an embedded client session is not supported:
   * it must be done interactively in a client session.
   *
   * @since 4.3
   */
  public void resetCredentials(String userID)
  {
    throw new UnsupportedOperationException();
  }

  @Override
  protected void doActivate() throws Exception
  {
    super.doActivate();

    EmbeddedClientSessionProtocol protocol = new EmbeddedClientSessionProtocol(this);
    setSessionProtocol(protocol);
    protocol.activate();
    protocol.openSession(options().isPassiveUpdateEnabled());

    setLastUpdateTime(repository.getLastCommitTimeStamp());
    setRepositoryInfo(new RepositoryInfo(this));

    InternalCDORevisionManager revisionManager = (InternalCDORevisionManager)CDORevisionUtil.createRevisionManager();
    setRevisionManager(revisionManager);
    revisionManager.setSupportingAudits(getRepositoryInfo().isSupportingAudits());
    revisionManager.setSupportingBranches(getRepositoryInfo().isSupportingBranches());
    revisionManager.setCache(CDORevisionCache.NOOP);
    revisionManager.setRevisionLoader(getSessionProtocol());
    revisionManager.setRevisionLocker(this);
    revisionManager.activate();
  }

  @Override
  protected void doDeactivate() throws Exception
  {
    super.doDeactivate();

    getRevisionManager().deactivate();
    setRevisionManager(null);
  }
}

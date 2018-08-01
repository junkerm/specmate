/*
 * Copyright (c) 2009-2013, 2016 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 *    Christian W. Damus (CEA LIST) - bug 399306
 */
package org.eclipse.emf.cdo.internal.server.embedded;

import org.eclipse.emf.cdo.common.CDOCommonRepository;
import org.eclipse.emf.cdo.common.branch.CDOBranchChangedEvent.ChangeKind;
import org.eclipse.emf.cdo.common.commit.CDOCommitInfo;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.lock.CDOLockChangeInfo;
import org.eclipse.emf.cdo.session.remote.CDORemoteSessionMessage;
import org.eclipse.emf.cdo.spi.common.branch.InternalCDOBranch;
import org.eclipse.emf.cdo.spi.server.ISessionProtocol;
import org.eclipse.emf.cdo.spi.server.InternalRepository;
import org.eclipse.emf.cdo.spi.server.InternalSession;

import org.eclipse.net4j.util.lifecycle.Lifecycle;
import org.eclipse.net4j.util.security.CredentialsUpdateOperation;
import org.eclipse.net4j.util.security.DiffieHellman.Client.Response;
import org.eclipse.net4j.util.security.DiffieHellman.Server.Challenge;

/**
 * @author Eike Stepper
 * @deprecated Not yet supported.
 */
@Deprecated
public class EmbeddedServerSessionProtocol extends Lifecycle implements ISessionProtocol
{
  // A separate session protocol instance is required because the getSession() methods are ambiguous!
  private EmbeddedClientSessionProtocol clientSessionProtocol;

  private InternalSession session;

  public EmbeddedServerSessionProtocol(EmbeddedClientSessionProtocol clientSessionProtocol)
  {
    this.clientSessionProtocol = clientSessionProtocol;
  }

  public EmbeddedClientSessionProtocol getClientSessionProtocol()
  {
    return clientSessionProtocol;
  }

  public InternalSession openSession(InternalRepository repository, boolean passiveUpdateEnabled)
  {
    session = repository.getSessionManager().openSession(this);
    session.setPassiveUpdateEnabled(passiveUpdateEnabled);
    return session;
  }

  public InternalSession getSession()
  {
    return session;
  }

  @Deprecated
  public org.eclipse.emf.cdo.spi.common.CDOAuthenticationResult sendAuthenticationChallenge(byte[] randomToken) throws Exception
  {
    return clientSessionProtocol.handleAuthenticationChallenge(randomToken);
  }

  public Response sendAuthenticationChallenge(Challenge challenge) throws Exception
  {
    throw new UnsupportedOperationException();
  }

  public Response sendCredentialsChallenge(Challenge challenge, String userID, CredentialsUpdateOperation operation) throws Exception
  {
    throw new UnsupportedOperationException();
  }

  public void sendRepositoryTypeNotification(CDOCommonRepository.Type oldType, CDOCommonRepository.Type newType)
  {
    EmbeddedClientSession clientSession = clientSessionProtocol.getSession();
    clientSession.handleRepositoryTypeChanged(oldType, newType);
  }

  @Deprecated
  public void sendRepositoryStateNotification(CDOCommonRepository.State oldState, CDOCommonRepository.State newState)
  {
    sendRepositoryStateNotification(oldState, newState, null);
  }

  public void sendRepositoryStateNotification(CDOCommonRepository.State oldState, CDOCommonRepository.State newState, CDOID rootResourceID)
  {
    EmbeddedClientSession clientSession = clientSessionProtocol.getSession();
    clientSession.handleRepositoryStateChanged(oldState, newState);
  }

  public void sendBranchNotification(InternalCDOBranch branch)
  {
    throw new UnsupportedOperationException();
  }

  public void sendBranchNotification(InternalCDOBranch branch, ChangeKind changeKind) throws Exception
  {
    throw new UnsupportedOperationException();
  }

  @Deprecated
  public void sendCommitNotification(CDOCommitInfo commitInfo)
  {
    EmbeddedClientSession clientSession = clientSessionProtocol.getSession();
    clientSession.handleCommitNotification(commitInfo);
  }

  @Deprecated
  public void sendCommitNotification(CDOCommitInfo commitInfo, boolean clearResourcePathCache) throws Exception
  {
    throw new UnsupportedOperationException();
  }

  public void sendCommitNotification(CommitNotificationInfo info) throws Exception
  {
    throw new UnsupportedOperationException();
  }

  public void sendLockNotification(CDOLockChangeInfo lockChangeInfo)
  {
    EmbeddedClientSession clientSession = clientSessionProtocol.getSession();
    clientSession.handleLockNotification(lockChangeInfo, null);
  }

  public void sendRemoteSessionNotification(InternalSession sender, byte opcode)
  {
    throw new UnsupportedOperationException();
  }

  public void sendRemoteMessageNotification(InternalSession sender, CDORemoteSessionMessage message)
  {
    throw new UnsupportedOperationException();
  }
}

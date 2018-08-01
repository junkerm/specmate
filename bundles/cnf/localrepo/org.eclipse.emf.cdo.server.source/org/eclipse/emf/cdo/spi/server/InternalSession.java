/*
 * Copyright (c) 2009-2013, 2015, 2016 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.emf.cdo.spi.server;

import org.eclipse.emf.cdo.common.CDOCommonRepository;
import org.eclipse.emf.cdo.common.CDOCommonSession;
import org.eclipse.emf.cdo.common.branch.CDOBranchChangedEvent.ChangeKind;
import org.eclipse.emf.cdo.common.branch.CDOBranchPoint;
import org.eclipse.emf.cdo.common.commit.CDOCommitInfo;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.id.CDOIDProvider;
import org.eclipse.emf.cdo.common.lock.CDOLockChangeInfo;
import org.eclipse.emf.cdo.common.protocol.CDOProtocol.CommitNotificationInfo;
import org.eclipse.emf.cdo.common.revision.CDORevision;
import org.eclipse.emf.cdo.common.security.CDOPermissionProvider;
import org.eclipse.emf.cdo.server.ISession;
import org.eclipse.emf.cdo.session.remote.CDORemoteSessionMessage;
import org.eclipse.emf.cdo.spi.common.branch.InternalCDOBranch;
import org.eclipse.emf.cdo.spi.common.revision.InternalCDORevision;

import java.util.List;
import java.util.Set;

/**
 * If the meaning of this type isn't clear, there really should be more of a description here...
 *
 * @author Eike Stepper
 * @since 4.3
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface InternalSession extends ISession, CDOIDProvider, CDOPermissionProvider, CDOCommonSession.Options
{
  public static final int TEMP_VIEW_ID = 0;

  public InternalSessionManager getManager();

  /**
   * @since 4.2
   */
  public void setUserID(String userID);

  /**
   * @since 4.5
   */
  public long getFirstUpdateTime();

  /**
   * @since 4.5
   */
  public void setFirstUpdateTime(long firstUpdateTime);

  /**
   * @since 4.5
   */
  public boolean isOpenOnClientSide();

  /**
   * @since 4.5
   */
  public void setOpenOnClientSide();

  public InternalView[] getViews();

  public InternalView getView(int viewID);

  public InternalView openView(int viewID, CDOBranchPoint branchPoint);

  public InternalTransaction openTransaction(int viewID, CDOBranchPoint branchPoint);

  public void viewClosed(InternalView view);

  public void setSubscribed(boolean subscribed);

  public void collectContainedRevisions(InternalCDORevision revision, CDOBranchPoint branchPoint, int referenceChunk, Set<CDOID> revisions,
      List<CDORevision> additionalRevisions);

  public void sendRepositoryTypeNotification(CDOCommonRepository.Type oldType, CDOCommonRepository.Type newType) throws Exception;

  /**
   * @deprecated use
   *             {@link #sendRepositoryStateNotification(org.eclipse.emf.cdo.common.CDOCommonRepository.State, org.eclipse.emf.cdo.common.CDOCommonRepository.State, CDOID)}
   *             instead
   */
  @Deprecated
  public void sendRepositoryStateNotification(CDOCommonRepository.State oldState, CDOCommonRepository.State newState) throws Exception;

  /**
   * @since 4.1
   */
  public void sendRepositoryStateNotification(CDOCommonRepository.State oldState, CDOCommonRepository.State newState, CDOID rootResourceID) throws Exception;

  /**
   * @deprecated As of 4.3 use {@link #sendBranchNotification(InternalCDOBranch, ChangeKind)}.
   */
  @Deprecated
  public void sendBranchNotification(InternalCDOBranch branch) throws Exception;

  /**
   * @since 4.3
   */
  public void sendBranchNotification(InternalCDOBranch branch, ChangeKind changeKind) throws Exception;

  /**
   * @deprecated As of 4.2 use {@link #sendCommitNotification(CDOCommitInfo, boolean)}.
   */
  @Deprecated
  public void sendCommitNotification(CDOCommitInfo commitInfo) throws Exception;

  /**
   * @since 4.2
   * @deprecated As of 4.3 use {@link #sendCommitNotification(CommitNotificationInfo)}.
   */
  @Deprecated
  public void sendCommitNotification(CDOCommitInfo commitInfo, boolean clearResourcePathCache) throws Exception;

  /**
   * @since 4.3
   */
  public void sendCommitNotification(CommitNotificationInfo info) throws Exception;

  public void sendRemoteSessionNotification(InternalSession sender, byte opcode) throws Exception;

  public void sendRemoteMessageNotification(InternalSession sender, CDORemoteSessionMessage message) throws Exception;

  /**
   * @since 4.1
   */
  public void sendLockNotification(CDOLockChangeInfo lockChangeInfo) throws Exception;
}

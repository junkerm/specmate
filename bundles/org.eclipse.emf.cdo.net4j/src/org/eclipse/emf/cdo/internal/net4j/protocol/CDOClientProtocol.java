/*
 * Copyright (c) 2009-2016 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 *    Christian W. Damus (CEA LIST) - bug 399306
 */
package org.eclipse.emf.cdo.internal.net4j.protocol;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.common.CDOCommonSession.Options.LockNotificationMode;
import org.eclipse.emf.cdo.common.CDOCommonSession.Options.PassiveUpdateMode;
import org.eclipse.emf.cdo.common.branch.CDOBranch;
import org.eclipse.emf.cdo.common.branch.CDOBranchHandler;
import org.eclipse.emf.cdo.common.branch.CDOBranchPoint;
import org.eclipse.emf.cdo.common.branch.CDOBranchPointRange;
import org.eclipse.emf.cdo.common.branch.CDOBranchVersion;
import org.eclipse.emf.cdo.common.commit.CDOChangeSetData;
import org.eclipse.emf.cdo.common.commit.CDOCommitData;
import org.eclipse.emf.cdo.common.commit.CDOCommitInfo;
import org.eclipse.emf.cdo.common.commit.CDOCommitInfoHandler;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.id.CDOIDProvider;
import org.eclipse.emf.cdo.common.lob.CDOLob;
import org.eclipse.emf.cdo.common.lob.CDOLobInfo;
import org.eclipse.emf.cdo.common.lock.CDOLockState;
import org.eclipse.emf.cdo.common.model.CDOPackageUnit;
import org.eclipse.emf.cdo.common.revision.CDOIDAndVersion;
import org.eclipse.emf.cdo.common.revision.CDORevision;
import org.eclipse.emf.cdo.common.revision.CDORevisionHandler;
import org.eclipse.emf.cdo.common.revision.CDORevisionKey;
import org.eclipse.emf.cdo.common.security.CDOPermission;
import org.eclipse.emf.cdo.common.util.TransportException;
import org.eclipse.emf.cdo.internal.net4j.bundle.OM;
import org.eclipse.emf.cdo.session.CDOSession;
import org.eclipse.emf.cdo.session.remote.CDORemoteSession;
import org.eclipse.emf.cdo.session.remote.CDORemoteSessionMessage;
import org.eclipse.emf.cdo.spi.common.CDORawReplicationContext;
import org.eclipse.emf.cdo.spi.common.CDOReplicationContext;
import org.eclipse.emf.cdo.spi.common.commit.CDORevisionAvailabilityInfo;
import org.eclipse.emf.cdo.spi.common.model.InternalCDOPackageUnit;
import org.eclipse.emf.cdo.spi.common.revision.InternalCDORevision;
import org.eclipse.emf.cdo.spi.common.revision.RevisionInfo;
import org.eclipse.emf.cdo.view.CDOView;

import org.eclipse.emf.internal.cdo.session.CDOSessionImpl;

import org.eclipse.net4j.signal.RemoteException;
import org.eclipse.net4j.signal.Request;
import org.eclipse.net4j.signal.RequestWithConfirmation;
import org.eclipse.net4j.signal.RequestWithMonitoring;
import org.eclipse.net4j.signal.SignalReactor;
import org.eclipse.net4j.signal.security.AuthenticatingSignalProtocol;
import org.eclipse.net4j.signal.security.AuthenticationIndication;
import org.eclipse.net4j.util.WrappedException;
import org.eclipse.net4j.util.collection.Pair;
import org.eclipse.net4j.util.concurrent.IRWLockManager.LockType;
import org.eclipse.net4j.util.io.StringCompressor;
import org.eclipse.net4j.util.io.StringIO;
import org.eclipse.net4j.util.om.monitor.Monitor;
import org.eclipse.net4j.util.om.monitor.OMMonitor;
import org.eclipse.net4j.util.om.trace.PerfTracer;
import org.eclipse.net4j.util.security.CredentialsUpdateOperation;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.spi.cdo.AbstractQueryIterator;
import org.eclipse.emf.spi.cdo.CDOSessionProtocol;
import org.eclipse.emf.spi.cdo.InternalCDOObject;
import org.eclipse.emf.spi.cdo.InternalCDORemoteSessionManager;
import org.eclipse.emf.spi.cdo.InternalCDOTransaction.InternalCDOCommitContext;
import org.eclipse.emf.spi.cdo.InternalCDOXATransaction.InternalCDOXACommitContext;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Eike Stepper
 */
public class CDOClientProtocol extends AuthenticatingSignalProtocol<CDOSessionImpl> implements CDOSessionProtocol
{
  private static final PerfTracer REVISION_LOADING = new PerfTracer(OM.PERF_REVISION_LOADING, CDOClientProtocol.class);

  private StringIO packageURICompressor = StringCompressor.BYPASS ? StringIO.DIRECT : new StringCompressor(true);

  public CDOClientProtocol()
  {
    super(PROTOCOL_NAME);
  }

  @Override
  public int getVersion()
  {
    return PROTOCOL_VERSION;
  }

  public CDOSession getSession()
  {
    return getInfraStructure();
  }

  public StringIO getPackageURICompressor()
  {
    return packageURICompressor;
  }

  public OpenSessionResult openSession(String repositoryName, String userID, boolean passiveUpdateEnabled, PassiveUpdateMode passiveUpdateMode,
      LockNotificationMode lockNotificationMode)
  {
    return send(new OpenSessionRequest(this, repositoryName, userID, passiveUpdateEnabled, passiveUpdateMode, lockNotificationMode), new Monitor());
  }

  public void disablePassiveUpdate()
  {
    send(new DisablePassiveUpdateRequest(this));
  }

  public void setPassiveUpdateMode(PassiveUpdateMode mode)
  {
    send(new SetPassiveUpdateModeRequest(this, mode));
  }

  public RepositoryTimeResult getRepositoryTime()
  {
    return send(new RepositoryTimeRequest(this));
  }

  public void openedSession()
  {
    send(new OpenedSessionRequest(this));
  }

  public EPackage[] loadPackages(CDOPackageUnit packageUnit)
  {
    return send(new LoadPackagesRequest(this, (InternalCDOPackageUnit)packageUnit));
  }

  public Pair<Integer, Long> createBranch(int branchID, BranchInfo branchInfo)
  {
    return send(new CreateBranchRequest(this, branchID, branchInfo));
  }

  public BranchInfo loadBranch(int branchID)
  {
    return send(new LoadBranchRequest(this, branchID));
  }

  public SubBranchInfo[] loadSubBranches(int branchID)
  {
    return send(new LoadSubBranchesRequest(this, branchID));
  }

  public int loadBranches(int startID, int endID, CDOBranchHandler handler)
  {
    return send(new LoadBranchesRequest(this, startID, endID, handler));
  }

  @Deprecated
  public void deleteBranch(int branchID)
  {
    throw new UnsupportedOperationException();
  }

  @Deprecated
  public void renameBranch(int branchID, String newName)
  {
    throw new UnsupportedOperationException();
  }

  public void renameBranch(int branchID, String oldName, String newName)
  {
    send(new RenameBranchRequest(this, branchID, oldName, newName));
  }

  public void loadCommitInfos(CDOBranch branch, long startTime, long endTime, CDOCommitInfoHandler handler)
  {
    send(new LoadCommitInfosRequest(this, branch, startTime, endTime, handler));
  }

  public CDOCommitData loadCommitData(long timeStamp)
  {
    return send(new LoadCommitDataRequest(this, timeStamp));
  }

  public Object loadChunk(InternalCDORevision revision, EStructuralFeature feature, int accessIndex, int fetchIndex, int fromIndex, int toIndex)
  {
    return send(new LoadChunkRequest(this, revision, feature, accessIndex, fetchIndex, fromIndex, toIndex));
  }

  public List<RevisionInfo> loadRevisions(List<RevisionInfo> infos, CDOBranchPoint branchPoint, int referenceChunk, int prefetchDepth)
  {
    return send(new LoadRevisionsRequest(this, infos, branchPoint, referenceChunk, prefetchDepth));
  }

  public InternalCDORevision loadRevisionByVersion(CDOID id, CDOBranchVersion branchVersion, int referenceChunk)
  {
    return send(new LoadRevisionByVersionRequest(this, id, branchVersion, referenceChunk));
  }

  public CDOBranchPointRange loadObjectLifetime(CDOID id, CDOBranchPoint branchPoint)
  {
    return send(new LoadObjectLifetimeRequest(this, id, branchPoint));
  }

  public RefreshSessionResult refresh(long lastUpdateTime, Map<CDOBranch, Map<CDOID, InternalCDORevision>> viewedRevisions, int initialChunkSize,
      boolean enablePassiveUpdates)
  {
    return send(new RefreshSessionRequest(this, lastUpdateTime, viewedRevisions, initialChunkSize, enablePassiveUpdates));
  }

  public void openView(int viewID, boolean readOnly, CDOBranchPoint branchPoint)
  {
    send(new OpenViewRequest(this, viewID, readOnly, branchPoint));
  }

  public CDOBranchPoint openView(int viewID, boolean readOnly, String durableLockingID)
  {
    return send(new OpenViewRequest(this, viewID, readOnly, durableLockingID));
  }

  public void switchTarget(int viewID, CDOBranchPoint branchPoint, List<InternalCDOObject> invalidObjects, List<CDORevisionKey> allChangedObjects,
      List<CDOIDAndVersion> allDetachedObjects, OMMonitor monitor)
  {
    send(new SwitchTargetRequest(this, viewID, branchPoint, invalidObjects, allChangedObjects, allDetachedObjects), monitor);
  }

  public void closeView(int viewID)
  {
    send(new CloseViewRequest(this, viewID));
  }

  public void changeSubscription(int viewID, List<CDOID> ids, boolean subscribeMode, boolean clear)
  {
    send(new ChangeSubscriptionRequest(this, viewID, ids, subscribeMode, clear));
  }

  public void query(CDOView view, AbstractQueryIterator<?> queryResult)
  {
    send(new QueryRequest(this, view, queryResult));
  }

  public boolean cancelQuery(int queryId)
  {
    try
    {
      return new QueryCancelRequest(this, queryId).send();
    }
    catch (Exception ignore)
    {
      return false;
    }
  }

  @Deprecated
  public LockObjectsResult lockObjects(List<InternalCDORevision> revisions, int viewID, CDOBranch viewedBranch, LockType lockType, long timeout)
      throws InterruptedException
  {
    // List<CDORevisionKey> revisionKeys = new LinkedList<CDORevisionKey>();
    // for (InternalCDORevision rev : revisions)
    // {
    // revisionKeys.add(rev);
    // }
    //
    // return lockObjects2(revisionKeys, viewID, viewedBranch, lockType, false, timeout);

    throw new UnsupportedOperationException();
  }

  public LockObjectsResult lockObjects2(List<CDORevisionKey> revisionKeys, int viewID, CDOBranch viewedBranch, LockType lockType, boolean recursive,
      long timeout) throws InterruptedException
  {
    InterruptedException interruptedException = null;
    RuntimeException runtimeException = null;

    try
    {
      return new LockObjectsRequest(this, revisionKeys, viewID, lockType, recursive, timeout).send();
    }
    catch (RemoteException ex)
    {
      if (ex.getCause() instanceof RuntimeException)
      {
        runtimeException = (RuntimeException)ex.getCause();
      }
      else if (ex.getCause() instanceof InterruptedException)
      {
        interruptedException = (InterruptedException)ex.getCause();
      }
      else
      {
        runtimeException = WrappedException.wrap(ex);
      }
    }
    catch (Exception ex)
    {
      throw WrappedException.wrap(ex);
    }

    if (interruptedException != null)
    {
      throw interruptedException;
    }

    throw runtimeException;
  }

  public LockObjectsResult delegateLockObjects(String lockAreaID, List<CDORevisionKey> revisionKeys, CDOBranch viewedBranch, LockType lockType,
      boolean recursive, long timeout) throws InterruptedException
  {
    InterruptedException interruptedException = null;
    RuntimeException runtimeException = null;

    try
    {
      return new LockDelegationRequest(this, lockAreaID, revisionKeys, viewedBranch, lockType, recursive, timeout).send();
    }
    catch (RemoteException ex)
    {
      if (ex.getCause() instanceof RuntimeException)
      {
        runtimeException = (RuntimeException)ex.getCause();
      }
      else if (ex.getCause() instanceof InterruptedException)
      {
        interruptedException = (InterruptedException)ex.getCause();
      }
      else
      {
        runtimeException = WrappedException.wrap(ex);
      }
    }
    catch (Exception ex)
    {
      throw WrappedException.wrap(ex);
    }

    if (interruptedException != null)
    {
      throw interruptedException;
    }

    throw runtimeException;
  }

  @Deprecated
  public void unlockObjects(CDOView view, Collection<CDOID> objectIDs, LockType lockType)
  {
    // send(new UnlockObjectsRequest(this, view.getViewID(), objectIDs, lockType, false));

    throw new UnsupportedOperationException();
  }

  public UnlockObjectsResult unlockObjects2(CDOView view, Collection<CDOID> objectIDs, LockType lockType, boolean recursive)
  {
    return send(new UnlockObjectsRequest(this, view.getViewID(), objectIDs, lockType, recursive));
  }

  public UnlockObjectsResult delegateUnlockObjects(String lockAreaID, Collection<CDOID> objectIDs, LockType lockType, boolean recursive)
  {
    return send(new UnlockDelegationRequest(this, lockAreaID, objectIDs, lockType, recursive));
  }

  public boolean isObjectLocked(CDOView view, CDOObject object, LockType lockType, boolean byOthers)
  {
    return send(new ObjectLockedRequest(this, view, object, lockType, byOthers));
  }

  public String changeLockArea(CDOView view, boolean create)
  {
    return send(new LockAreaRequest(this, view, create));
  }

  public List<byte[]> queryLobs(Set<byte[]> ids)
  {
    return send(new QueryLobsRequest(this, ids));
  }

  public void loadLob(CDOLobInfo info, Object outputStreamOrWriter) throws IOException
  {
    try
    {
      new LoadLobRequest(this, info, outputStreamOrWriter).send();
    }
    catch (RuntimeException ex)
    {
      throw ex;
    }
    catch (IOException ex)
    {
      throw ex;
    }
    catch (Exception ex)
    {
      throw new TransportException(ex);
    }
  }

  public void handleRevisions(EClass eClass, CDOBranch branch, boolean exactBranch, long timeStamp, boolean exactTime, CDORevisionHandler handler)
  {
    send(new HandleRevisionsRequest(this, eClass, branch, exactBranch, timeStamp, exactTime, handler));
  }

  @Deprecated
  public CommitTransactionResult commitTransaction(int transactionID, String comment, boolean releaseLocks, CDOIDProvider idProvider, CDOCommitData commitData,
      Collection<CDOLob<?>> lobs, OMMonitor monitor)
  {
    throw new UnsupportedOperationException();
  }

  public CommitTransactionResult commitTransaction(InternalCDOCommitContext context, OMMonitor monitor)
  {
    return send(new CommitTransactionRequest(this, context), monitor);
  }

  @Deprecated
  public CommitTransactionResult commitDelegation(CDOBranch branch, String userID, String comment, CDOCommitData commitData,
      Map<CDOID, EClass> detachedObjectTypes, Collection<CDOLob<?>> lobs, OMMonitor monitor)
  {
    throw new UnsupportedOperationException();
  }

  public CommitTransactionResult commitDelegation(InternalCDOCommitContext context, OMMonitor monitor)
  {
    return send(new CommitDelegationRequest(this, context), monitor);
  }

  public CommitTransactionResult commitXATransactionPhase1(InternalCDOXACommitContext xaContext, OMMonitor monitor)
  {
    return send(new CommitXATransactionPhase1Request(this, xaContext), monitor);
  }

  public CommitTransactionResult commitXATransactionPhase2(InternalCDOXACommitContext xaContext, OMMonitor monitor)
  {
    return send(new CommitXATransactionPhase2Request(this, xaContext), monitor);
  }

  public CommitTransactionResult commitXATransactionPhase3(InternalCDOXACommitContext xaContext, OMMonitor monitor)
  {
    return send(new CommitXATransactionPhase3Request(this, xaContext), monitor);
  }

  public CommitTransactionResult commitXATransactionCancel(InternalCDOXACommitContext xaContext, OMMonitor monitor)
  {
    return send(new CommitXATransactionCancelRequest(this, xaContext), monitor);
  }

  public CDOCommitInfo resetTransaction(int transactionID, int commitNumber)
  {
    return send(new ResetTransactionRequest(this, transactionID, commitNumber));
  }

  public List<CDORemoteSession> getRemoteSessions(InternalCDORemoteSessionManager manager, boolean subscribe)
  {
    return send(new GetRemoteSessionsRequest(this, subscribe));
  }

  public Set<Integer> sendRemoteMessage(CDORemoteSessionMessage message, List<CDORemoteSession> recipients)
  {
    return send(new RemoteMessageRequest(this, message, recipients));
  }

  public boolean unsubscribeRemoteSessions()
  {
    return send(new UnsubscribeRemoteSessionsRequest(this));
  }

  public void replicateRepository(CDOReplicationContext context, OMMonitor monitor)
  {
    send(new ReplicateRepositoryRequest(this, context, monitor));
  }

  public void replicateRepositoryRaw(CDORawReplicationContext context, OMMonitor monitor)
  {
    send(new ReplicateRepositoryRawRequest(this, context), monitor);
  }

  public CDOChangeSetData[] loadChangeSets(CDOBranchPointRange... ranges)
  {
    return send(new LoadChangeSetsRequest(this, ranges));
  }

  @Deprecated
  public Set<CDOID> loadMergeData(CDORevisionAvailabilityInfo targetInfo, CDORevisionAvailabilityInfo sourceInfo, CDORevisionAvailabilityInfo targetBaseInfo,
      CDORevisionAvailabilityInfo sourceBaseInfo)
  {
    throw new UnsupportedOperationException();
  }

  public MergeDataResult loadMergeData2(CDORevisionAvailabilityInfo targetInfo, CDORevisionAvailabilityInfo sourceInfo,
      CDORevisionAvailabilityInfo targetBaseInfo, CDORevisionAvailabilityInfo sourceBaseInfo)
  {
    return send(new LoadMergeDataRequest(this, targetInfo, sourceInfo, targetBaseInfo, sourceBaseInfo));
  }

  @Deprecated
  public CDOLockState[] getLockStates(int viewID, Collection<CDOID> ids)
  {
    return getLockStates(viewID, ids, CDOLockState.DEPTH_NONE);
  }

  public CDOLockState[] getLockStates(int viewID, Collection<CDOID> ids, int depth)
  {
    return send(new LockStateRequest(this, viewID, ids, depth));
  }

  public void enableLockNotifications(int viewID, boolean on)
  {
    send(new EnableLockNotificationRequest(this, viewID, on));
  }

  public void setLockNotificationMode(LockNotificationMode mode)
  {
    send(new SetLockNotificationModeRequest(this, mode));
  }

  public Map<CDORevision, CDOPermission> loadPermissions(InternalCDORevision[] revisions)
  {
    return send(new LoadPermissionsRequest(this, revisions));
  }

  public void requestChangeCredentials()
  {
    send(new ChangeCredentialsRequest(this, CredentialsUpdateOperation.CHANGE_PASSWORD, null), new Monitor());
  }

  public void requestResetCredentials(String userID)
  {
    send(new ChangeCredentialsRequest(this, CredentialsUpdateOperation.RESET_PASSWORD, userID), new Monitor());
  }

  public boolean requestUnit(int viewID, CDOID rootID, UnitOpcode opcode, CDORevisionHandler revisionHandler, OMMonitor monitor)
  {
    return send(new UnitRequest(this, viewID, rootID, opcode, revisionHandler), monitor);
  }

  @Override
  protected SignalReactor createSignalReactor(short signalID)
  {
    switch (signalID)
    {
    case SIGNAL_AUTHENTICATION:
      return new AuthenticationIndication(this, SIGNAL_AUTHENTICATION);

    case SIGNAL_BRANCH_NOTIFICATION:
      return new BranchNotificationIndication(this);

    case SIGNAL_REPOSITORY_TYPE_NOTIFICATION:
      return new RepositoryTypeNotificationIndication(this);

    case SIGNAL_REPOSITORY_STATE_NOTIFICATION:
      return new RepositoryStateNotificationIndication(this);

    case SIGNAL_COMMIT_NOTIFICATION:
      return new CommitNotificationIndication(this);

    case SIGNAL_REMOTE_SESSION_NOTIFICATION:
      return new RemoteSessionNotificationIndication(this);

    case SIGNAL_REMOTE_MESSAGE_NOTIFICATION:
      return new RemoteMessageNotificationIndication(this);

    case SIGNAL_LOCK_NOTIFICATION:
      return new LockNotificationIndication(this);

    case SIGNAL_CREDENTIALS_CHALLENGE:
      return new CredentialsChallengeIndication(this);

    default:
      return super.createSignalReactor(signalID);
    }
  }

  private void send(Request request)
  {
    try
    {
      request.sendAsync();
    }
    catch (RuntimeException ex)
    {
      throw ex;
    }
    catch (Exception ex)
    {
      throw new TransportException(ex);
    }
  }

  private <RESULT> RESULT send(RequestWithConfirmation<RESULT> request)
  {
    try
    {
      return request.send();
    }
    catch (RuntimeException ex)
    {
      throw ex;
    }
    catch (Exception ex)
    {
      throw new TransportException(ex);
    }
  }

  private <RESULT> RESULT send(RequestWithMonitoring<RESULT> request, OMMonitor monitor)
  {
    try
    {
      return request.send(monitor);
    }
    catch (RuntimeException ex)
    {
      throw ex;
    }
    catch (Exception ex)
    {
      throw new TransportException(ex);
    }
  }

  private List<RevisionInfo> send(LoadRevisionsRequest request)
  {
    try
    {
      REVISION_LOADING.start(request);
      return send((RequestWithConfirmation<List<RevisionInfo>>)request);
    }
    finally
    {
      REVISION_LOADING.stop(request);
    }
  }
}

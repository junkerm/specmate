/*
 * Copyright (c) 2009-2016 Eike Stepper (Loehne, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 *    Simon McDuff - bug 226778
 *    Simon McDuff - bug 230832
 *    Simon McDuff - bug 233490
 *    Simon McDuff - bug 213402
 *    Victor Roldan Betancort - maintenance
 *    Andre Dietisheim - bug 256649
 *    Christian W. Damus (CEA LIST) - bug 399306
 */
package org.eclipse.emf.cdo.internal.net4j;

import org.eclipse.emf.cdo.common.CDOCommonSession.Options.LockNotificationMode;
import org.eclipse.emf.cdo.common.CDOCommonSession.Options.PassiveUpdateMode;
import org.eclipse.emf.cdo.common.revision.CDORevisionUtil;
import org.eclipse.emf.cdo.common.util.NotAuthenticatedException;
import org.eclipse.emf.cdo.internal.common.model.CDOPackageRegistryImpl;
import org.eclipse.emf.cdo.internal.net4j.CDONet4jSessionConfigurationImpl.RepositoryInfo;
import org.eclipse.emf.cdo.internal.net4j.protocol.CDOClientProtocol;
import org.eclipse.emf.cdo.internal.net4j.protocol.CommitTransactionRequest;
import org.eclipse.emf.cdo.net4j.CDONet4jSession;
import org.eclipse.emf.cdo.session.CDORepositoryInfo;
import org.eclipse.emf.cdo.spi.common.branch.CDOBranchUtil;
import org.eclipse.emf.cdo.spi.common.branch.InternalCDOBranchManager;
import org.eclipse.emf.cdo.spi.common.commit.CDOCommitInfoUtil;
import org.eclipse.emf.cdo.spi.common.commit.InternalCDOCommitInfoManager;
import org.eclipse.emf.cdo.spi.common.model.InternalCDOPackageRegistry;
import org.eclipse.emf.cdo.spi.common.model.InternalCDOPackageUnit;
import org.eclipse.emf.cdo.spi.common.revision.InternalCDORevisionManager;

import org.eclipse.emf.internal.cdo.session.CDOSessionImpl;
import org.eclipse.emf.internal.cdo.session.DelegatingSessionProtocol;

import org.eclipse.net4j.connector.IConnector;
import org.eclipse.net4j.signal.ISignalProtocol;
import org.eclipse.net4j.signal.RemoteException;
import org.eclipse.net4j.signal.SignalProtocol;
import org.eclipse.net4j.util.io.IStreamWrapper;

import org.eclipse.emf.spi.cdo.CDOSessionProtocol;
import org.eclipse.emf.spi.cdo.CDOSessionProtocol.OpenSessionResult;

/**
 * @author Eike Stepper
 */
@SuppressWarnings("deprecation")
public class CDONet4jSessionImpl extends CDOSessionImpl implements org.eclipse.emf.cdo.net4j.CDOSession
{
  private IStreamWrapper streamWrapper;

  private IConnector connector;

  private String repositoryName;

  private long signalTimeout = SignalProtocol.DEFAULT_TIMEOUT;

  public CDONet4jSessionImpl()
  {
  }

  public IStreamWrapper getStreamWrapper()
  {
    return streamWrapper;
  }

  public void setStreamWrapper(IStreamWrapper streamWrapper)
  {
    this.streamWrapper = streamWrapper;
  }

  public IConnector getConnector()
  {
    return connector;
  }

  public void setConnector(IConnector connector)
  {
    this.connector = connector;
  }

  public String getRepositoryName()
  {
    return repositoryName;
  }

  public void setRepositoryName(String repositoryName)
  {
    this.repositoryName = repositoryName;
  }

  public long getSignalTimeout()
  {
    return signalTimeout;
  }

  public void setSignalTimeout(long signalTimeout)
  {
    this.signalTimeout = signalTimeout;

    // Deal with the possibility that the sessionProtocol has already been created.
    CDOClientProtocol clientProtocol = getClientProtocol();
    if (clientProtocol != null)
    {
      clientProtocol.setTimeout(this.signalTimeout);
    }
  }

  public void changeCredentials()
  {
    // Send a request to the server to initiate (from the server) the password change protocol
    CDOSessionProtocol sessionProtocol = getSessionProtocol();
    sessionProtocol.requestChangeCredentials();
  }

  public void resetCredentials(String userID)
  {
    // Send a request to the server to initiate (from the server) the password reset protocol
    CDOSessionProtocol sessionProtocol = getSessionProtocol();
    sessionProtocol.requestResetCredentials(userID);
  }

  @Override
  public OptionsImpl options()
  {
    return (OptionsImpl)super.options();
  }

  @Override
  protected OptionsImpl createOptions()
  {
    return new OptionsImpl();
  }

  protected InternalCDOBranchManager createBranchManager()
  {
    return CDOBranchUtil.createBranchManager();
  }

  protected OpenSessionResult openSession()
  {
    CDOClientProtocol protocol = createProtocol();
    setSessionProtocol(protocol);
    hookSessionProtocol();

    try
    {
      String userID = getUserID();
      boolean passiveUpdateEnabled = options().isPassiveUpdateEnabled();
      PassiveUpdateMode passiveUpdateMode = options().getPassiveUpdateMode();
      LockNotificationMode lockNotificationMode = options().getLockNotificationMode();

      // TODO (CD) The next call is on the CDOClientProtocol; shouldn't it be on the DelegatingSessionProtocol instead?
      OpenSessionResult result = protocol.openSession(repositoryName, userID, passiveUpdateEnabled, passiveUpdateMode, lockNotificationMode);

      if (result == null)
      {
        // Skip to response because the user has canceled the authentication
        return null;
      }

      setSessionID(result.getSessionID());
      setUserID(result.getUserID());
      setLastUpdateTime(result.getLastUpdateTime());
      setRepositoryInfo(new RepositoryInfo(this, result));
      return result;
    }
    catch (RemoteException ex)
    {
      if (ex.getCause() instanceof SecurityException)
      {
        throw (SecurityException)ex.getCause();
      }

      throw ex;
    }
  }

  @Override
  protected void doActivate() throws Exception
  {
    // Package registry must be available when CDOPackageUnits are received in the open session response!
    InternalCDOPackageRegistry packageRegistry = getPackageRegistry();
    if (packageRegistry == null)
    {
      packageRegistry = new CDOPackageRegistryImpl();
      setPackageRegistry(packageRegistry);
    }

    packageRegistry.setPackageProcessor(this);
    packageRegistry.setPackageLoader(this);
    packageRegistry.activate();

    OpenSessionResult result = openSession();
    if (result == null)
    {
      throw new NotAuthenticatedException();
    }

    super.doActivate();
    CDORepositoryInfo repository = getRepositoryInfo();
    CDOSessionProtocol sessionProtocol = getSessionProtocol();

    InternalCDORevisionManager revisionManager = getRevisionManager();
    if (revisionManager == null)
    {
      revisionManager = (InternalCDORevisionManager)CDORevisionUtil.createRevisionManager();
      setRevisionManager(revisionManager);
    }

    if (!revisionManager.isActive())
    {
      revisionManager.setSupportingAudits(repository.isSupportingAudits());
      revisionManager.setSupportingBranches(repository.isSupportingBranches());
      revisionManager.setRevisionLoader(sessionProtocol);
      revisionManager.setRevisionLocker(this);
      revisionManager.activate();
    }

    InternalCDOBranchManager branchManager = getBranchManager();
    if (branchManager == null)
    {
      branchManager = createBranchManager();
      setBranchManager(branchManager);
    }

    if (!branchManager.isActive())
    {
      branchManager.setRepository(repository);
      branchManager.setBranchLoader(sessionProtocol);
      branchManager.initMainBranch(isMainBranchLocal(), repository.getCreationTime());
      branchManager.activate();
    }

    InternalCDOCommitInfoManager commitInfoManager = getCommitInfoManager();
    if (commitInfoManager == null)
    {
      commitInfoManager = CDOCommitInfoUtil.createCommitInfoManager(true);
      setCommitInfoManager(commitInfoManager);
    }

    if (!commitInfoManager.isActive())
    {
      commitInfoManager.setRepository(repository);
      commitInfoManager.setCommitInfoLoader(sessionProtocol);
      commitInfoManager.setLastCommitOfBranch(null, getLastUpdateTime());
      commitInfoManager.activate();
    }

    for (InternalCDOPackageUnit packageUnit : result.getPackageUnits())
    {
      getPackageRegistry().putPackageUnit(packageUnit);
    }

    repository.getTimeStamp(true);
    sessionProtocol.openedSession();
  }

  @Override
  protected void doDeactivate() throws Exception
  {
    CDOSessionProtocol sessionProtocol = getSessionProtocol();
    super.doDeactivate();

    InternalCDOCommitInfoManager commitInfoManager = getCommitInfoManager();
    if (commitInfoManager.getCommitInfoLoader() == sessionProtocol)
    {
      commitInfoManager.deactivate();
    }

    InternalCDORevisionManager revisionManager = getRevisionManager();
    if (revisionManager.getRevisionLoader() == sessionProtocol)
    {
      revisionManager.deactivate();
    }

    InternalCDOBranchManager branchManager = getBranchManager();
    if (branchManager.getBranchLoader() == sessionProtocol)
    {
      branchManager.deactivate();
    }

    getPackageRegistry().deactivate();
  }

  private CDOClientProtocol createProtocol()
  {
    CDOClientProtocol protocol = new CDOClientProtocol();
    protocol.setInfraStructure(this);
    if (streamWrapper != null)
    {
      protocol.setStreamWrapper(streamWrapper);
    }

    protocol.open(connector);
    protocol.setTimeout(signalTimeout);
    return protocol;
  }

  /**
   * Gets the CDOClientProtocol instance, which may be wrapped inside a DelegatingSessionProtocol
   */
  private CDOClientProtocol getClientProtocol()
  {
    CDOSessionProtocol sessionProtocol = getSessionProtocol();
    CDOClientProtocol clientProtocol;
    if (sessionProtocol instanceof DelegatingSessionProtocol)
    {
      clientProtocol = (CDOClientProtocol)((DelegatingSessionProtocol)sessionProtocol).getDelegate();
    }
    else
    {
      clientProtocol = (CDOClientProtocol)sessionProtocol;
    }

    return clientProtocol;
  }

  /**
   * @author Eike Stepper
   */
  protected class OptionsImpl extends org.eclipse.emf.internal.cdo.session.CDOSessionImpl.OptionsImpl implements org.eclipse.emf.cdo.net4j.CDOSession.Options
  {
    private int commitTimeout = CommitTransactionRequest.DEFAULT_MONITOR_TIMEOUT_SECONDS;

    private int progressInterval = CommitTransactionRequest.DEFAULT_MONITOR_PROGRESS_SECONDS;

    public OptionsImpl()
    {
    }

    @Override
    public CDONet4jSession getContainer()
    {
      return (CDONet4jSession)super.getContainer();
    }

    public ISignalProtocol<org.eclipse.emf.cdo.net4j.CDONet4jSession> getNet4jProtocol()
    {
      CDOSessionProtocol protocol = getSessionProtocol();
      if (protocol instanceof DelegatingSessionProtocol)
      {
        protocol = ((DelegatingSessionProtocol)protocol).getDelegate();
      }

      @SuppressWarnings("unchecked")
      ISignalProtocol<CDONet4jSession> signalProtocol = (ISignalProtocol<CDONet4jSession>)protocol;
      return signalProtocol;
    }

    public ISignalProtocol<org.eclipse.emf.cdo.net4j.CDOSession> getProtocol()
    {
      @SuppressWarnings("unchecked")
      ISignalProtocol<org.eclipse.emf.cdo.net4j.CDOSession> net4jProtocol = (ISignalProtocol<org.eclipse.emf.cdo.net4j.CDOSession>)(ISignalProtocol<?>)getNet4jProtocol();
      return net4jProtocol;
    }

    public int getCommitTimeout()
    {
      return commitTimeout;
    }

    public synchronized void setCommitTimeout(int commitTimeout)
    {
      this.commitTimeout = commitTimeout;
    }

    public int getProgressInterval()
    {
      return progressInterval;
    }

    public synchronized void setProgressInterval(int progressInterval)
    {
      this.progressInterval = progressInterval;
    }
  }
}

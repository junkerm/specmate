/*
 * Copyright (c) 2009-2013, 2015, 2016 Eike Stepper (Loehne, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 *    Andre Dietisheim - bug 256649
 */
package org.eclipse.emf.cdo.internal.net4j;

import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.util.CDOCommonUtil;
import org.eclipse.emf.cdo.internal.net4j.testrecorder.TestRecorderSession;
import org.eclipse.emf.cdo.net4j.CDONet4jSession;
import org.eclipse.emf.cdo.net4j.CDOSession;
import org.eclipse.emf.cdo.session.CDORepositoryInfo;

import org.eclipse.emf.internal.cdo.session.CDOSessionConfigurationImpl;

import org.eclipse.net4j.connector.IConnector;
import org.eclipse.net4j.signal.SignalProtocol;
import org.eclipse.net4j.util.CheckUtil;
import org.eclipse.net4j.util.io.IStreamWrapper;
import org.eclipse.net4j.util.om.OMPlatform;

import org.eclipse.emf.spi.cdo.CDOSessionProtocol.OpenSessionResult;
import org.eclipse.emf.spi.cdo.CDOSessionProtocol.RepositoryTimeResult;
import org.eclipse.emf.spi.cdo.InternalCDOSession;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.PlatformObject;

import java.util.Set;

/**
 * @author Eike Stepper
 */
@SuppressWarnings("deprecation")
public class CDONet4jSessionConfigurationImpl extends CDOSessionConfigurationImpl implements org.eclipse.emf.cdo.net4j.CDOSessionConfiguration
{
  private static final boolean TEST_RECORDER = OMPlatform.INSTANCE.isProperty("org.eclipse.emf.cdo.test.recorder.enabled");

  private String repositoryName;

  private IConnector connector;

  private IStreamWrapper streamWrapper;

  private long signalTimeout = SignalProtocol.DEFAULT_TIMEOUT;

  public CDONet4jSessionConfigurationImpl()
  {
  }

  public String getRepositoryName()
  {
    return repositoryName;
  }

  public void setRepositoryName(String repositoryName)
  {
    this.repositoryName = repositoryName;
  }

  public IConnector getConnector()
  {
    return connector;
  }

  public void setConnector(IConnector connector)
  {
    checkNotOpen();
    uncheckedSetConnector(connector);
  }

  protected void uncheckedSetConnector(IConnector connector)
  {
    this.connector = connector;
  }

  public IStreamWrapper getStreamWrapper()
  {
    return streamWrapper;
  }

  public void setStreamWrapper(IStreamWrapper streamWrapper)
  {
    checkNotOpen();
    this.streamWrapper = streamWrapper;
  }

  public long getSignalTimeout()
  {
    return signalTimeout;
  }

  public void setSignalTimeout(long signalTimeout)
  {
    this.signalTimeout = signalTimeout;
  }

  public CDONet4jSession openNet4jSession()
  {
    return (CDONet4jSession)super.openSession();
  }

  @Override
  public CDOSession openSession()
  {
    return (CDOSession)openNet4jSession();
  }

  public InternalCDOSession createSession()
  {
    if (isActivateOnOpen())
    {
      CheckUtil.checkState(connector, "connector"); //$NON-NLS-1$
    }

    if (TEST_RECORDER)
    {
      return new TestRecorderSession();
    }

    return new CDONet4jSessionImpl();
  }

  @Override
  protected void configureSession(InternalCDOSession session)
  {
    super.configureSession(session);

    CDONet4jSessionImpl sessionImpl = (CDONet4jSessionImpl)session;
    sessionImpl.setStreamWrapper(streamWrapper);
    sessionImpl.setConnector(connector);
    sessionImpl.setRepositoryName(repositoryName);
    sessionImpl.setUserID(getUserID());
    sessionImpl.setSignalTimeout(signalTimeout);
  }

  /**
   * @author Eike Stepper
   */
  public static class RepositoryInfo extends PlatformObject implements CDORepositoryInfo
  {
    private String uuid;

    private String name;

    private Type type;

    private State state;

    private String storeType;

    private Set<CDOID.ObjectType> objectIDTypes;

    private long creationTime;

    private RepositoryTimeResult timeResult;

    private CDOID rootResourceID;

    private boolean authenticating;

    private boolean supportingAudits;

    private boolean supportingBranches;

    private boolean supportingUnits;

    private boolean serializingCommits;

    private boolean ensuringReferentialIntegrity;

    private IDGenerationLocation idGenerationLocation;

    private CommitInfoStorage commitInfoStorage;

    private InternalCDOSession session;

    public RepositoryInfo(InternalCDOSession session, OpenSessionResult result)
    {
      this.session = session;
      uuid = result.getUUID();
      name = result.getName();
      type = result.getType();
      state = result.getState();
      storeType = result.getStoreType();
      objectIDTypes = result.getObjectIDTypes();
      creationTime = result.getCreationTime();
      timeResult = result.getRepositoryTimeResult();
      rootResourceID = result.getRootResourceID();
      authenticating = result.isAuthenticating();
      supportingAudits = result.isSupportingAudits();
      supportingBranches = result.isSupportingBranches();
      supportingUnits = result.isSupportingUnits();
      serializingCommits = result.isEnsuringReferentialIntegrity();
      ensuringReferentialIntegrity = result.isEnsuringReferentialIntegrity();
      idGenerationLocation = result.getIDGenerationLocation();
      commitInfoStorage = result.getCommitInfoStorage();
    }

    public InternalCDOSession getSession()
    {
      return session;
    }

    public String getName()
    {
      return name;
    }

    /**
     * Must be callable before session activation has finished!
     */
    public String getUUID()
    {
      return uuid;
    }

    public Type getType()
    {
      return type;
    }

    public void setType(Type type)
    {
      this.type = type;
    }

    public State getState()
    {
      return state;
    }

    public void setState(State state)
    {
      this.state = state;
    }

    public String getStoreType()
    {
      return storeType;
    }

    public Set<CDOID.ObjectType> getObjectIDTypes()
    {
      return objectIDTypes;
    }

    public long getCreationTime()
    {
      return creationTime;
    }

    public long getTimeStamp()
    {
      return getTimeStamp(false);
    }

    public long getTimeStamp(boolean forceRefresh)
    {
      if (timeResult == null || forceRefresh)
      {
        timeResult = refreshTime();
      }

      return timeResult.getAproximateRepositoryTime();
    }

    public CDOID getRootResourceID()
    {
      return rootResourceID;
    }

    public void setRootResourceID(CDOID rootResourceID)
    {
      // The rootResourceID may only be set if it is currently null
      if (this.rootResourceID == null || this.rootResourceID.isNull())
      {
        this.rootResourceID = rootResourceID;
      }
      else if (this.rootResourceID != null && this.rootResourceID.equals(rootResourceID))
      {
        // Do nothing; it is the same.
      }
      else
      {
        throw new IllegalStateException("rootResourceID must not be changed unless it is null");
      }
    }

    public boolean isAuthenticating()
    {
      return authenticating;
    }

    public boolean isSupportingAudits()
    {
      return supportingAudits;
    }

    public boolean isSupportingBranches()
    {
      return supportingBranches;
    }

    public boolean isSupportingUnits()
    {
      return supportingUnits;
    }

    @Deprecated
    public boolean isSupportingEcore()
    {
      return true;
    }

    public boolean isSerializingCommits()
    {
      return serializingCommits;
    }

    public boolean isEnsuringReferentialIntegrity()
    {
      return ensuringReferentialIntegrity;
    }

    public IDGenerationLocation getIDGenerationLocation()
    {
      return idGenerationLocation;
    }

    public CommitInfoStorage getCommitInfoStorage()
    {
      return commitInfoStorage;
    }

    public boolean waitWhileInitial(IProgressMonitor monitor)
    {
      return CDOCommonUtil.waitWhileInitial(this, session, monitor);
    }

    private RepositoryTimeResult refreshTime()
    {
      return session.getSessionProtocol().getRepositoryTime();
    }
  }
}

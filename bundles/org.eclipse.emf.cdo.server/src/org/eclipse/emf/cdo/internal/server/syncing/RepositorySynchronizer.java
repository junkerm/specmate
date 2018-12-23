/*
 * Copyright (c) 2010-2015 Eike Stepper (Loehne, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.emf.cdo.internal.server.syncing;

import org.eclipse.emf.cdo.common.CDOCommonRepository;
import org.eclipse.emf.cdo.common.CDOCommonRepository.State;
import org.eclipse.emf.cdo.common.CDOCommonSession.Options.LockNotificationMode;
import org.eclipse.emf.cdo.common.CDOCommonSession.Options.PassiveUpdateMode;
import org.eclipse.emf.cdo.common.branch.CDOBranch;
import org.eclipse.emf.cdo.common.branch.CDOBranchChangedEvent;
import org.eclipse.emf.cdo.common.branch.CDOBranchChangedEvent.ChangeKind;
import org.eclipse.emf.cdo.common.commit.CDOCommitInfo;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.lock.CDOLockChangeInfo;
import org.eclipse.emf.cdo.internal.common.revision.NOOPRevisionCache;
import org.eclipse.emf.cdo.internal.server.bundle.OM;
import org.eclipse.emf.cdo.server.StoreThreadLocal;
import org.eclipse.emf.cdo.session.CDOSession;
import org.eclipse.emf.cdo.session.CDOSessionConfiguration;
import org.eclipse.emf.cdo.session.CDOSessionConfigurationFactory;
import org.eclipse.emf.cdo.session.CDOSessionInvalidationEvent;
import org.eclipse.emf.cdo.session.CDOSessionLocksChangedEvent;
import org.eclipse.emf.cdo.spi.common.branch.CDOBranchAdjustable;
import org.eclipse.emf.cdo.spi.common.revision.InternalCDORevisionCache;
import org.eclipse.emf.cdo.spi.server.InternalRepositorySynchronizer;
import org.eclipse.emf.cdo.spi.server.InternalSynchronizableRepository;

import org.eclipse.net4j.util.concurrent.ConcurrencyUtil;
import org.eclipse.net4j.util.concurrent.PriorityQueueRunnable;
import org.eclipse.net4j.util.concurrent.PriorityQueueRunner;
import org.eclipse.net4j.util.container.IContainerDelta;
import org.eclipse.net4j.util.container.SingleDeltaContainerEvent;
import org.eclipse.net4j.util.event.IEvent;
import org.eclipse.net4j.util.event.IListener;
import org.eclipse.net4j.util.lifecycle.ILifecycleEvent;
import org.eclipse.net4j.util.om.monitor.NotifyingMonitor;
import org.eclipse.net4j.util.om.monitor.OMMonitor;
import org.eclipse.net4j.util.om.trace.ContextTracer;

import org.eclipse.emf.spi.cdo.CDOSessionProtocol;
import org.eclipse.emf.spi.cdo.InternalCDOSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Eike Stepper
 * @since 3.0
 */
public class RepositorySynchronizer extends PriorityQueueRunner implements InternalRepositorySynchronizer
{
  private static final ContextTracer TRACER = new ContextTracer(OM.DEBUG_REPOSITORY, RepositorySynchronizer.class);

  private static final Integer CONNECT_PRIORITY = 0;

  private static final Integer REPLICATE_PRIORITY = 1;

  private static final Integer BRANCH_PRIORITY = 2;

  private static final Integer COMMIT_PRIORITY = 3;

  private static final Integer LOCKS_PRIORITY = COMMIT_PRIORITY;

  private int retryInterval = DEFAULT_RETRY_INTERVAL;

  private Object connectLock = new Object();

  private InternalSynchronizableRepository localRepository;

  /**
   * The session that connects to the master; used passively to receive notifications, and actively to request
   * replications.
   */
  private InternalCDOSession remoteSession;

  private RemoteSessionListener remoteSessionListener = new RemoteSessionListener();

  private CDOSessionConfigurationFactory remoteSessionConfigurationFactory;

  private boolean rawReplication = true;

  private int maxRecommits = DEFAULT_MAX_RECOMMITS;

  private int recommitInterval = DEFAULT_RECOMMIT_INTERVAL;

  private Timer recommitTimer;

  public RepositorySynchronizer()
  {
    setDaemon(true);
  }

  public int getRetryInterval()
  {
    return retryInterval;
  }

  public void setRetryInterval(int retryInterval)
  {
    this.retryInterval = retryInterval;
  }

  public InternalSynchronizableRepository getLocalRepository()
  {
    return localRepository;
  }

  public void setLocalRepository(InternalSynchronizableRepository localRepository)
  {
    checkInactive();
    this.localRepository = localRepository;
  }

  public CDOSessionConfigurationFactory getRemoteSessionConfigurationFactory()
  {
    return remoteSessionConfigurationFactory;
  }

  public void setRemoteSessionConfigurationFactory(CDOSessionConfigurationFactory masterSessionConfigurationFactory)
  {
    checkArg(masterSessionConfigurationFactory, "remoteSessionConfigurationFactory"); //$NON-NLS-1$
    remoteSessionConfigurationFactory = masterSessionConfigurationFactory;
  }

  public InternalCDOSession getRemoteSession()
  {
    return remoteSession;
  }

  public boolean isRawReplication()
  {
    return rawReplication;
  }

  public void setRawReplication(boolean rawReplication)
  {
    checkInactive();
    this.rawReplication = rawReplication;
  }

  public int getMaxRecommits()
  {
    return maxRecommits;
  }

  public void setMaxRecommits(int maxRecommits)
  {
    this.maxRecommits = maxRecommits;
  }

  public int getRecommitInterval()
  {
    return recommitInterval;
  }

  public void setRecommitInterval(int recommitInterval)
  {
    this.recommitInterval = recommitInterval;
  }

  public boolean isEmpty()
  {
    return remoteSession == null;
  }

  public CDOSession[] getElements()
  {
    if (remoteSession == null)
    {
      return new CDOSession[0];
    }

    return new CDOSession[] { remoteSession };
  }

  @Override
  protected String getThreadName()
  {
    return "CDORepositorySynchronizer"; //$NON-NLS-1$
  }

  @Override
  protected void noWork(WorkContext context)
  {
    if (!localRepository.isActive())
    {
      context.terminate();
    }
  }

  @Override
  protected void doBeforeActivate() throws Exception
  {
    super.doBeforeActivate();
    checkState(remoteSessionConfigurationFactory, "remoteSessionConfigurationFactory"); //$NON-NLS-1$
    checkState(localRepository, "localRepository"); //$NON-NLS-1$
  }

  @Override
  protected void doAfterActivate() throws Exception
  {
    super.doAfterActivate();
    scheduleConnect();
  }

  @Override
  protected void doDeactivate() throws Exception
  {
    if (recommitTimer != null)
    {
      recommitTimer.cancel();
      recommitTimer = null;
    }

    if (remoteSession != null)
    {
      closeRemoteSession();
    }

    super.doDeactivate();
  }

  protected void handleConnect()
  {
    scheduleReplicate();

    remoteSession.addListener(remoteSessionListener);
    remoteSession.getBranchManager().addListener(remoteSessionListener);

    fireEvent(new SingleDeltaContainerEvent<CDOSession>(this, remoteSession, IContainerDelta.Kind.ADDED));
  }

  protected void handleDisconnect()
  {
    if (TRACER.isEnabled())
    {
      TRACER.trace("Disconnected from master."); //$NON-NLS-1$
    }

    if (localRepository.hasBeenReplicated())
    {
      localRepository.setState(CDOCommonRepository.State.OFFLINE);
    }
    else
    {
      localRepository.setState(CDOCommonRepository.State.INITIAL);
    }

    if (remoteSession != null)
    {
      CDOSession element = remoteSession;
      closeRemoteSession();
      fireEvent(new SingleDeltaContainerEvent<CDOSession>(this, element, IContainerDelta.Kind.REMOVED));
    }

    reconnect();
  }

  private void closeRemoteSession()
  {
    remoteSession.removeListener(remoteSessionListener);
    remoteSession.getBranchManager().removeListener(remoteSessionListener);
    remoteSession.close();
    remoteSession = null;
  }

  private void reconnect()
  {
    clearQueue();
    if (isActive())
    {
      scheduleConnect();
    }
  }

  private void scheduleConnect()
  {
    synchronized (connectLock)
    {
      State state = localRepository.getState();
      if (state.isConnected())
      {
        return;
      }

      if (isActive())
      {
        addWork(new ConnectRunnable());
      }
    }
  }

  private void scheduleReplicate()
  {
    if (isActive())
    {
      addWork(new ReplicateRunnable());
    }
  }

  private void sleepRetryInterval()
  {
    long end = System.currentTimeMillis() + 1000L * retryInterval;

    for (;;)
    {
      long now = System.currentTimeMillis();
      if (now >= end || !isActive())
      {
        break;
      }

      ConcurrencyUtil.sleep(Math.min(100L, end - now));
    }
  }

  /**
   * @author Eike Stepper
   */
  private final class RemoteSessionListener implements IListener
  {
    public void notifyEvent(IEvent event)
    {
      if (!isActive())
      {
        return;
      }

      if (event instanceof CDOBranchChangedEvent)
      {
        CDOBranchChangedEvent e = (CDOBranchChangedEvent)event;
        if (e.getChangeKind() == ChangeKind.CREATED)
        {
          addWork(new BranchRunnable(e.getBranch()));
        }
        else
        {
          throw new UnsupportedOperationException("Branch renaming not supported: " + RepositorySynchronizer.this);
        }
      }
      else if (event instanceof CDOSessionInvalidationEvent)
      {
        CDOSessionInvalidationEvent e = (CDOSessionInvalidationEvent)event;
        if (e.isRemote())
        {
          addWork(new CommitRunnable(e));
        }
      }
      else if (event instanceof CDOSessionLocksChangedEvent)
      {
        CDOSessionLocksChangedEvent e = (CDOSessionLocksChangedEvent)event;
        addWork(new LocksRunnable(e));
      }
      else if (event instanceof ILifecycleEvent)
      {
        ILifecycleEvent e = (ILifecycleEvent)event;
        if (e.getKind() == ILifecycleEvent.Kind.DEACTIVATED && e.getSource() == remoteSession)
        {
          handleDisconnect();
        }
      }
    }
  }

  /**
   * @author Eike Stepper
   */
  private final class ConnectRunnable extends PriorityQueueRunnable
  {
    public ConnectRunnable()
    {
    }

    public void run()
    {
      synchronized (connectLock)
      {
        checkActive();
        if (TRACER.isEnabled())
        {
          TRACER.trace("Connecting to master..."); //$NON-NLS-1$
        }

        try
        {
          CDOSessionConfiguration masterConfiguration = remoteSessionConfigurationFactory.createSessionConfiguration();
          masterConfiguration.setPassiveUpdateMode(PassiveUpdateMode.ADDITIONS);
          masterConfiguration.setLockNotificationMode(LockNotificationMode.ALWAYS);

          remoteSession = (InternalCDOSession)masterConfiguration.openSession();

          ensureNOOPRevisionCache();
        }
        catch (Exception ex)
        {
          remoteSession = null;

          if (isActive())
          {
            if (TRACER.isEnabled())
            {
              TRACER.format("Connection attempt failed. Retrying in {0} seconds...", retryInterval); //$NON-NLS-1$
            }

            fireThrowable(ex);
            sleepRetryInterval();
            reconnect();
          }

          return;
        }

        if (TRACER.isEnabled())
        {
          TRACER.trace("Connected to master."); //$NON-NLS-1$
        }

        handleConnect();
      }
    }

    @Override
    protected Integer getPriority()
    {
      return CONNECT_PRIORITY;
    }

    private void ensureNOOPRevisionCache()
    {
      // Ensure that incoming revisions are not cached!
      InternalCDORevisionCache cache = remoteSession.getRevisionManager().getCache();
      if (!(cache instanceof NOOPRevisionCache))
      {
        String message = "Master session does not use a NOOPRevisionCache: " + cache.getClass().getName();
        OM.LOG.error(message);
        throw new Error(message);
      }
    }
  }

  /**
   * @author Eike Stepper
   */
  private final class ReplicateRunnable extends PriorityQueueRunnable
  {
    public ReplicateRunnable()
    {
    }

    public void run()
    {
      try
      {
        checkActive();
        if (TRACER.isEnabled())
        {
          TRACER.trace("Synchronizing with master..."); //$NON-NLS-1$
        }

        boolean firstSyncing = !localRepository.hasBeenReplicated();
        if (!firstSyncing)
        {
          localRepository.setState(CDOCommonRepository.State.SYNCING);
        }

        CDOSessionProtocol sessionProtocol = remoteSession.getSessionProtocol();
        OMMonitor monitor = new NotifyingMonitor("Synchronizing", getListeners());

        if (isRawReplication())
        {
          sessionProtocol.replicateRepositoryRaw(localRepository, monitor);
        }
        else
        {
          sessionProtocol.replicateRepository(localRepository, monitor);
        }

        if (firstSyncing)
        {
          CDOID id = remoteSession.getRepositoryInfo().getRootResourceID();
          localRepository.setRootResourceID(id);
        }

        localRepository.setState(CDOCommonRepository.State.ONLINE);
        if (TRACER.isEnabled())
        {
          TRACER.trace("Synchronized with master."); //$NON-NLS-1$
        }
      }
      catch (RuntimeException ex)
      {
        if (isActive())
        {
          if (TRACER.isEnabled())
          {
            TRACER.format("Replication attempt failed. Retrying in {0} seconds...", ex, retryInterval); //$NON-NLS-1$
          }

          fireThrowable(ex);
          sleepRetryInterval();
          handleDisconnect();
        }
      }
    }

    @Override
    protected Integer getPriority()
    {
      return REPLICATE_PRIORITY;
    }
  }

  /**
   * @author Eike Stepper
   */
  private final class BranchRunnable extends PriorityQueueRunnable
  {
    private CDOBranch branch;

    public BranchRunnable(CDOBranch branch)
    {
      this.branch = branch;
    }

    public void run()
    {
      try
      {
        localRepository.handleBranch(branch);
      }
      catch (Exception ex)
      {
        fireThrowable(ex);
      }
    }

    @Override
    public int compareTo(PriorityQueueRunnable o)
    {
      int result = super.compareTo(o);
      if (result == 0)
      {
        result = branch.compareTo(((BranchRunnable)o).branch);
      }

      return result;
    }

    @Override
    protected Integer getPriority()
    {
      return BRANCH_PRIORITY;
    }
  }

  /**
   * @author Eike Stepper
   */
  private abstract class RetryingRunnable extends PriorityQueueRunnable
  {
    private List<Exception> failedRuns;

    public RetryingRunnable()
    {
    }

    public void run()
    {
      try
      {
        doRun();
      }
      catch (Exception ex)
      {
        fireThrowable(ex);
        if (failedRuns == null)
        {
          failedRuns = new ArrayList<Exception>();
        }

        failedRuns.add(ex);
        if (failedRuns.size() <= maxRecommits)
        {
          if (TRACER.isEnabled())
          {
            String simpleName = RetryingRunnable.this.getClass().getSimpleName();
            TRACER.format(simpleName + " failed. Trying again in {0} seconds...", recommitInterval); //$NON-NLS-1$
          }

          if (recommitTimer == null)
          {
            recommitTimer = new Timer("RetryTimer-" + RepositorySynchronizer.this);
          }

          recommitTimer.schedule(new TimerTask()
          {
            @Override
            public void run()
            {
              try
              {
                addWork(RetryingRunnable.this);
              }
              catch (Exception ex)
              {
                if (TRACER.isEnabled())
                {
                  TRACER.format("{0} failed. Exiting.", RetryingRunnable.this.getClass().getSimpleName()); //$NON-NLS-1$
                }

                fireThrowable(ex);
              }
            }
          }, recommitInterval * 1000L);
        }
        else
        {
          if (TRACER.isEnabled())
          {
            TRACER.trace(ex);
          }

          fireThrowable(ex);
        }
      }
    }

    protected abstract void doRun();

    protected abstract String getErrorMessage();
  }

  /**
   * @author Eike Stepper
   */
  private final class CommitRunnable extends RetryingRunnable
  {
    private CDOCommitInfo commitInfo;

    public CommitRunnable(CDOCommitInfo commitInfo)
    {
      this.commitInfo = commitInfo;
    }

    @Override
    protected void doRun()
    {
      localRepository.handleCommitInfo(commitInfo);
    }

    @Override
    public int compareTo(PriorityQueueRunnable o)
    {
      int result = super.compareTo(o);
      if (result == 0)
      {
        Long timeStamp = commitInfo.getTimeStamp();
        
        /** BEGIN SPECMATE PATCH */
        Long timeStamp2;
        if(o instanceof CommitRunnable) {
        		 timeStamp2 = ((CommitRunnable)o).commitInfo.getTimeStamp();
      	} else  {
      		return super.compareTo(o);
      	}
        /** END SPECMATE PATCH */
        result = timeStamp < timeStamp2 ? -1 : timeStamp == timeStamp2 ? 0 : 1;
      }

      return result;
    }

    @Override
    protected Integer getPriority()
    {
      return COMMIT_PRIORITY;
    }

    @Override
    protected String getErrorMessage()
    {
      return "Replication of master commit failed:" + commitInfo;
    }
  }

  /**
   * @author Caspar De Groot
   */
  private final class LocksRunnable extends RetryingRunnable
  {
    private CDOLockChangeInfo lockChangeInfo;

    public LocksRunnable(CDOLockChangeInfo lockChangeInfo)
    {
      this.lockChangeInfo = lockChangeInfo;
    }

    @Override
    protected Integer getPriority()
    {
      return LOCKS_PRIORITY;
    }

    @Override
    protected void doRun()
    {
      try
      {
        StoreThreadLocal.setSession(localRepository.getReplicatorSession());

        if (lockChangeInfo instanceof CDOBranchAdjustable)
        {
          ((CDOBranchAdjustable)lockChangeInfo).adjustBranches(localRepository.getBranchManager());
        }

        localRepository.handleLockChangeInfo(lockChangeInfo);
      }
      finally
      {
        StoreThreadLocal.release();
      }
    }

    @Override
    protected String getErrorMessage()
    {
      return "Replication of master lock changes failed:" + lockChangeInfo;
    }
  }
}

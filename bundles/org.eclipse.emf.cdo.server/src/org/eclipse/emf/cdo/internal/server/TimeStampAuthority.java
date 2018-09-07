/*
 * Copyright (c) 2011-2013, 2016 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Caspar De Groot - initial API and implementation
 */
package org.eclipse.emf.cdo.internal.server;

import org.eclipse.emf.cdo.common.branch.CDOBranchPoint;
import org.eclipse.emf.cdo.spi.server.InternalRepository;

import org.eclipse.net4j.util.ReflectUtil.ExcludeFromDump;
import org.eclipse.net4j.util.concurrent.ConcurrencyUtil;
import org.eclipse.net4j.util.om.monitor.OMMonitor;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Bug 297940, 290032
 *
 * @author Caspar De Groot
 */
class TimeStampAuthority
{
  private InternalRepository repository;

  /**
   * Holds the <i>begin</i> timestamp that was issued in response to the last call to {@link #startCommit(long, OMMonitor)}.
   * <p>
   * Synchronized on <code>TimeStampAuthority.this</code>.
   */
  @ExcludeFromDump
  private transient long lastIssuedTimeStamp = CDOBranchPoint.UNSPECIFIED_DATE;

  /**
   * Holds the <i>begin</i> timestamp that was last reported finished by a call to {@link #endCommit(long)}.
   * <p>
   * Synchronized on <code>lastFinishedTimeStampLock</code>.
   */
  private long lastFinishedTimeStamp;

  private LastCommitTimeStampLock lastFinishedTimeStampLock = new LastCommitTimeStampLock();

  private boolean strictOrdering; // TODO (CD) Should be a repo property

  /**
   * A lock to block on if strict commit ordering is enabled
   */
  private StrictOrderingLock strictOrderingLock = new StrictOrderingLock();

  /**
   * An ordered list of timestamps that have been issued but have not (yet) been reported finished. (It is ordered
   * because the timestamps are added sequentially.)
   */
  private List<Long> runningTransactions = new LinkedList<Long>();

  /**
   * A set of timestamps that have been reported finished but have not yet been
   */
  private SortedSet<Long> finishedTransactions = new TreeSet<Long>();

  TimeStampAuthority(InternalRepository repository)
  {
    this.repository = repository;
  }

  /**
   * The purpose of this method is to make sure that no commit can occur at the same time as
   * the base of a new branch. Otherwise that commit could change revisions of that branch base.
   * See bug 506768 and bug 383602.
   */
  synchronized long getMaxBaseTimeForNewBranch()
  {
    long now = repository.getTimeStamp();
    while (repository.getTimeStamp() == now)
    {
      ConcurrencyUtil.sleep(1);
    }

    return now;
  }

  /**
   * @deprecated Not used anymore.
   */
  @Deprecated
  synchronized long[] startCommit(OMMonitor monitor)
  {
    return startCommit(CDOBranchPoint.UNSPECIFIED_DATE, monitor);
  }

  synchronized long[] startCommit(long timeStampOverride, OMMonitor monitor)
  {
    monitor.begin();
    lockIfNeeded();

    try
    {
      long now = repository.getTimeStamp();
      if (lastIssuedTimeStamp != CDOBranchPoint.UNSPECIFIED_DATE)
      {
        while (lastIssuedTimeStamp == now)
        {
          ConcurrencyUtil.sleep(1);
          now = repository.getTimeStamp();
          monitor.checkCanceled();
        }
      }

      if (timeStampOverride != CDOBranchPoint.UNSPECIFIED_DATE)
      {
        now = timeStampOverride;
      }

      long previousTimeStamp = lastIssuedTimeStamp;
      lastIssuedTimeStamp = now;

      runningTransactions.add(now);
      return new long[] { now, previousTimeStamp };
    }
    finally
    {
      monitor.done();
    }
  }

  synchronized void endCommit(long timeStamp)
  {
    if (!runningTransactions.remove(timeStamp))
    {
      throw new IllegalArgumentException("Cannot end transaction with unknown timestamp " + timeStamp);
    }

    finishedTransactions.add(timeStamp);

    // We can remove a timestamp from finishedTransactions if it is smaller (i.e. older) than any
    // of the runningTransactions. Since both sets are sorted, we only need to compare the heads.
    long oldestRunning = runningTransactions.isEmpty() ? Long.MAX_VALUE : runningTransactions.get(0);
    long oldestFinished;
    synchronized (lastFinishedTimeStampLock)
    {
      long oldValue = lastFinishedTimeStamp;
      while (!finishedTransactions.isEmpty() && (oldestFinished = finishedTransactions.first()) < oldestRunning)
      {
        finishedTransactions.remove(oldestFinished);
        setLastFinishedTimeStampUnsynced(oldestFinished);
      }

      // If we actually changed the lastFinishedTimeStamp, we need to notify waiting threads
      if (lastFinishedTimeStamp != oldValue)
      {
        lastFinishedTimeStampLock.notifyAll();
      }
    }

    unlockIfNeeded();
  }

  synchronized void failCommit(long timeStamp)
  {
    // Exclude problems before TransactionCommitContext.setTimeStamp()
    if (timeStamp == CDOBranchPoint.UNSPECIFIED_DATE)
    {
      unlockIfNeeded();
    }
    else
    {
      endCommit(timeStamp);
    }
  }

  synchronized long getLastFinishedTimeStamp()
  {
    if (lastFinishedTimeStamp != 0)
    {
      return lastFinishedTimeStamp;
    }

    // If we get here, no commit has finished since the server was started
    if (lastIssuedTimeStamp == 0) // No commit has started either
    {
      // We can safely return the current system time minus one milli.
      return repository.getTimeStamp() - 1;
    }

    // If we get here, one or more commits are running
    // We can safely return the start time of the longest-running, minus one milli.
    return runningTransactions.get(0) - 1;
  }

  long waitForCommit(long timeout)
  {
    synchronized (lastFinishedTimeStampLock)
    {
      try
      {
        lastFinishedTimeStampLock.wait(timeout);
      }
      catch (Exception ignore)
      {
      }

      return lastFinishedTimeStamp;
    }
  }

  void setLastFinishedTimeStamp(long lastCommitTimeStamp)
  {
    synchronized (lastFinishedTimeStampLock)
    {
      if (lastCommitTimeStamp > lastFinishedTimeStamp)
      {
        lastIssuedTimeStamp = lastCommitTimeStamp;
        setLastFinishedTimeStampUnsynced(lastCommitTimeStamp);
        lastFinishedTimeStampLock.notifyAll();
      }
    }
  }

  private void setLastFinishedTimeStampUnsynced(long lastCommitTimeStamp)
  {
    lastFinishedTimeStamp = lastCommitTimeStamp;
    repository.getStore().setLastCommitTime(lastFinishedTimeStamp);
  }

  private void lockIfNeeded()
  {
    if (strictOrdering)
    {
      strictOrderingLock.lock();
    }
  }

  private void unlockIfNeeded()
  {
    if (strictOrdering)
    {
      strictOrderingLock.unlock();
    }
  }

  /**
   * A separate class for better monitor debugging.
   *
   * @author Eike Stepper
   */
  private static final class LastCommitTimeStampLock
  {
  }

  /**
   * A separate class for better monitor debugging.
   *
   * @author Eike Stepper
   */
  private static final class StrictOrderingLock extends ReentrantLock
  {
    private static final long serialVersionUID = 1L;
  }
}

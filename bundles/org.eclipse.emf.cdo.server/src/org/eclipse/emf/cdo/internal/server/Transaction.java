/*
 * Copyright (c) 2008-2013, 2015, 2016 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 *    Simon McDuff - bug 233490
 *    Simon McDuff - bug 213402
 */
package org.eclipse.emf.cdo.internal.server;

import org.eclipse.emf.cdo.common.branch.CDOBranchPoint;
import org.eclipse.emf.cdo.spi.server.InternalCommitContext;
import org.eclipse.emf.cdo.spi.server.InternalRepository;
import org.eclipse.emf.cdo.spi.server.InternalSession;
import org.eclipse.emf.cdo.spi.server.InternalTransaction;

import org.eclipse.net4j.util.om.monitor.OMMonitor;

/**
 * @author Eike Stepper
 * @since 2.0
 */
public class Transaction extends View implements InternalTransaction
{
  private CommitAttempt lastCommitAttempt;

  public Transaction(InternalSession session, int viewID, CDOBranchPoint branchPoint)
  {
    super(session, viewID, branchPoint);
  }

  @Override
  public boolean isReadOnly()
  {
    return false;
  }

  @Override
  protected String getClassName()
  {
    return "Transaction"; //$NON-NLS-1$
  }

  /**
   * @since 2.0
   */
  public InternalCommitContext createCommitContext()
  {
    checkOpen();

    InternalRepository repository = getRepository();
    return repository.createCommitContext(this);
  }

  /**
   * For tests only.
   *
   * @since 2.0
   */
  public InternalCommitContext testCreateCommitContext(final long timeStamp)
  {
    checkOpen();

    return new TransactionCommitContext(this)
    {
      @Override
      protected long[] createTimeStamp(OMMonitor monitor)
      {
        return new long[] { timeStamp, UNSPECIFIED_DATE };
      }
    };
  }

  public CommitAttempt getLastCommitAttempt()
  {
    return lastCommitAttempt;
  }

  public void setLastCommitAttempt(CommitAttempt lastCommitAttempt)
  {
    this.lastCommitAttempt = lastCommitAttempt;
  }

  @Override
  protected void validateTimeStamp(long timeStamp) throws IllegalArgumentException
  {
    if (timeStamp != UNSPECIFIED_DATE)
    {
      throw new IllegalArgumentException("Changing the target time is not supported by transactions");
    }
  }
}

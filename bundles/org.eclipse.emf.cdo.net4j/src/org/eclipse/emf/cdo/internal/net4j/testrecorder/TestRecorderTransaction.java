/*
 * Copyright (c) 2016 Eike Stepper (Loehne, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.emf.cdo.internal.net4j.testrecorder;

import org.eclipse.emf.cdo.common.branch.CDOBranch;
import org.eclipse.emf.cdo.common.branch.CDOBranchPoint;
import org.eclipse.emf.cdo.common.commit.CDOChangeSetData;
import org.eclipse.emf.cdo.common.commit.CDOCommitInfo;
import org.eclipse.emf.cdo.session.CDOSession;
import org.eclipse.emf.cdo.transaction.CDOMerger;
import org.eclipse.emf.cdo.util.CommitException;

import org.eclipse.emf.internal.cdo.transaction.CDOTransactionImpl;

import org.eclipse.core.runtime.IProgressMonitor;

/**
 * @author Eike Stepper
 */
public class TestRecorderTransaction extends CDOTransactionImpl
{
  public TestRecorderTransaction(CDOSession session, CDOBranch branch)
  {
    super(session, branch);
    TestRecorder.INSTANCE.openTransaction(this, session, branch);
  }

  @Override
  public TestRecorderSession getSession()
  {
    return (TestRecorderSession)super.getSession();
  }

  @Override
  public CDOChangeSetData merge(CDOBranch source, CDOMerger merger)
  {
    TestRecorder.INSTANCE.mergeTransaction(this, source, merger);
    return super.merge(source, merger);
  }

  @Override
  public CDOChangeSetData merge(CDOBranchPoint source, CDOMerger merger)
  {
    TestRecorder.INSTANCE.mergeTransaction(this, source, merger);
    return super.merge(source, merger);
  }

  @Override
  public CDOCommitInfo commit(IProgressMonitor progressMonitor) throws CommitException
  {
    CDOCommitInfo commitInfo = super.commit(progressMonitor);
    TestRecorder.INSTANCE.commitTransaction(this, commitInfo);
    return commitInfo;
  }

  @Override
  public void rollback()
  {
    super.rollback();
    TestRecorder.INSTANCE.rollbackTransaction(this);
  }
}

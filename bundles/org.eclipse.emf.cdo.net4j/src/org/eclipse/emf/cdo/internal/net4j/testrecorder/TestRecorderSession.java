/*
 * Copyright (c) 2004-2016 Eike Stepper (Berlin, Germany) and others.
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
import org.eclipse.emf.cdo.internal.net4j.CDONet4jSessionImpl;
import org.eclipse.emf.cdo.spi.common.branch.InternalCDOBranchManager;

import org.eclipse.emf.spi.cdo.InternalCDOTransaction;
import org.eclipse.emf.spi.cdo.InternalCDOView;

/**
 * @author Eike Stepper
 */
public class TestRecorderSession extends CDONet4jSessionImpl
{
  public TestRecorderSession()
  {
    TestRecorder.INSTANCE.openSession(this);
  }

  @Override
  protected InternalCDOBranchManager createBranchManager()
  {
    return new TestRecorderBranchManager();
  }

  @Override
  protected InternalCDOView createView(CDOBranch branch, long timeStamp)
  {
    return new TestRecorderView(this, branch, timeStamp);
  }

  @Override
  protected InternalCDOTransaction createTransaction(CDOBranch branch)
  {
    return new TestRecorderTransaction(this, branch);
  }
}

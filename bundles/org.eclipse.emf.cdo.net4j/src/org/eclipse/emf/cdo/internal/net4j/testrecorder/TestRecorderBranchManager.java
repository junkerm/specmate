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

import org.eclipse.emf.cdo.common.branch.CDOBranchPoint;
import org.eclipse.emf.cdo.internal.common.branch.CDOBranchManagerImpl;
import org.eclipse.emf.cdo.spi.common.branch.InternalCDOBranch;

/**
 * @author Eike Stepper
 */
@SuppressWarnings("restriction")
public class TestRecorderBranchManager extends CDOBranchManagerImpl
{
  public TestRecorderBranchManager()
  {
  }

  @Override
  protected InternalCDOBranch createBranch(int branchID, String name, CDOBranchPoint base, long originalBaseTimeStamp)
  {
    TestRecorderBranch branch = new TestRecorderBranch(this, branchID, name, base);
    TestRecorder.INSTANCE.createBranch(branch, originalBaseTimeStamp);
    return branch;
  }
}

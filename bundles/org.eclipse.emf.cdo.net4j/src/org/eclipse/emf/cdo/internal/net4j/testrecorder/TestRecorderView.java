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
import org.eclipse.emf.cdo.session.CDOSession;

import org.eclipse.emf.internal.cdo.view.CDOViewImpl;

/**
 * @author Eike Stepper
 */
public class TestRecorderView extends CDOViewImpl
{
  public TestRecorderView(CDOSession session, CDOBranch branch, long timeStamp)
  {
    super(session, branch, timeStamp);
    TestRecorder.INSTANCE.openView(this, session, branch, timeStamp);
  }

  @Override
  public TestRecorderSession getSession()
  {
    return (TestRecorderSession)super.getSession();
  }
}

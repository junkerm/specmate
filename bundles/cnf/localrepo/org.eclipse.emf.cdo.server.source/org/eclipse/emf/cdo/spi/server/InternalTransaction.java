/*
 * Copyright (c) 2009, 2011, 2012, 2016 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.emf.cdo.spi.server;

import org.eclipse.emf.cdo.server.ITransaction;

/**
 * If the meaning of this type isn't clear, there really should be more of a description here...
 *
 * @author Eike Stepper
 * @since 3.0
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface InternalTransaction extends ITransaction, InternalView
{
  public InternalCommitContext createCommitContext();

  /**
   * @since 4.5
   */
  public CommitAttempt getLastCommitAttempt();

  /**
   * @since 4.5
   */
  public void setLastCommitAttempt(CommitAttempt lastCommitAttempt);

  /**
   * @author Eike Stepper
   * @since 4.5
   */
  public static final class CommitAttempt
  {
    private final int commitNumber;

    private final long timeStamp;

    private final long previousTimeStamp;

    public CommitAttempt(int commitNumber, long timeStamp, long previousTimeStamp)
    {
      this.commitNumber = commitNumber;
      this.timeStamp = timeStamp;
      this.previousTimeStamp = previousTimeStamp;
    }

    public int getCommitNumber()
    {
      return commitNumber;
    }

    public long getTimeStamp()
    {
      return timeStamp;
    }

    public long getPreviousTimeStamp()
    {
      return previousTimeStamp;
    }
  }
}

/*
 * Copyright (c) 2009, 2011, 2012, 2015 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.emf.cdo.spi.server;

import org.eclipse.emf.cdo.common.protocol.CDODataInput;

import org.eclipse.net4j.util.om.monitor.OMMonitor;

import java.util.concurrent.ExecutionException;

/**
 * If the meaning of this type isn't clear, there really should be more of a description here...
 *
 * @author Eike Stepper
 * @since 3.0
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface InternalCommitManager
{
  public InternalRepository getRepository();

  public void setRepository(InternalRepository repository);

  /**
   * Create a future to execute commitContext in a different thread.
   *
   * @deprecated As of 4.5 use {@link #preCommit(InternalCommitContext, CDODataInput, OMMonitor)}.
   */
  @Deprecated
  public void preCommit(InternalCommitContext commitContext, OMMonitor monitor);

  /**
   * Create a future to execute commitContext in a different thread.
   * @since 4.5
   */
  public void preCommit(InternalCommitContext commitContext, CDODataInput in, OMMonitor monitor);

  /**
   * Called after a commitContext is done successfully or not.
   */
  public void remove(InternalCommitContext commitContext);

  public void rollback(InternalCommitContext commitContext);

  /**
   * Waiting for a commit to be done.
   */
  public void waitForTermination(InternalTransaction transaction) throws InterruptedException, ExecutionException;

  public InternalCommitContext get(InternalTransaction transaction);
}

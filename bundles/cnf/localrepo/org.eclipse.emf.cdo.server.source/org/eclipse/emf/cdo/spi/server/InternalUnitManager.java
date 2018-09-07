/*
 * Copyright (c) 2016 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.emf.cdo.spi.server;

import org.eclipse.emf.cdo.common.revision.CDORevisionProvider;
import org.eclipse.emf.cdo.server.IUnitManager;
import org.eclipse.emf.cdo.spi.common.revision.InternalCDORevisionDelta;

import java.util.List;

/**
 * @author Eike Stepper
 * @since 4.5
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
*/
public interface InternalUnitManager extends IUnitManager
{
  public InternalRepository getRepository();

  public List<InternalCDORevisionDelta> getUnitMoves(InternalCDORevisionDelta[] deltas, CDORevisionProvider before, CDORevisionProvider after);

  public InternalObjectAttacher attachObjects(InternalCommitContext commitContext);

  /**
   * @author Eike Stepper
   */
  public interface InternalObjectAttacher
  {
    public void finishedCommit(boolean success);
  }
}

/*
 * Copyright (c) 2009-2012, 2016 Eike Stepper (Loehne, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.emf.cdo.spi.server;

import org.eclipse.emf.cdo.common.branch.CDOBranchPoint;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.protocol.CDOProtocolConstants.UnitOpcode;
import org.eclipse.emf.cdo.common.revision.CDORevisionHandler;
import org.eclipse.emf.cdo.common.revision.delta.CDORevisionDelta;
import org.eclipse.emf.cdo.server.IView;

import org.eclipse.net4j.util.lifecycle.ILifecycle;
import org.eclipse.net4j.util.om.monitor.OMMonitor;

import java.util.List;

/**
 * If the meaning of this type isn't clear, there really should be more of a description here...
 *
 * @author Eike Stepper
 * @since 3.0
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface InternalView extends IView, ILifecycle
{
  public InternalSession getSession();

  public InternalRepository getRepository();

  public void setBranchPoint(CDOBranchPoint branchPoint);

  /**
   * @since 4.0
   */
  public void setDurableLockingID(String durableLockingID);

  /**
   * @since 4.0
   */
  public void changeTarget(CDOBranchPoint branchPoint, List<CDOID> invalidObjects, List<CDORevisionDelta> allChangedObjects, List<CDOID> allDetachedObjects);

  public void subscribe(CDOID id);

  public void unsubscribe(CDOID id);

  public boolean hasSubscription(CDOID id);

  public void clearChangeSubscription();

  public void doClose();

  /**
   * @since 4.5
   */
  public boolean openUnit(CDOID rootID, UnitOpcode opcode, CDORevisionHandler revisionHandler, OMMonitor monitor);

  /**
   * @since 4.5
   */
  public void closeUnit(CDOID rootID);

  /**
   * @since 4.5
   */
  public boolean isInOpenUnit(CDOID id);
}

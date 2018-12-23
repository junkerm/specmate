/*
 * Copyright (c) 2009-2012, 2016, 2017 Eike Stepper (Loehne, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 *    Caspar De Groot - maintenance
 */
package org.eclipse.emf.cdo.internal.net4j.protocol;

import org.eclipse.emf.cdo.common.lock.CDOLockState;
import org.eclipse.emf.cdo.common.protocol.CDODataInput;
import org.eclipse.emf.cdo.common.protocol.CDODataOutput;
import org.eclipse.emf.cdo.common.protocol.CDOProtocolConstants;
import org.eclipse.emf.cdo.common.revision.CDORevisionKey;

import org.eclipse.net4j.util.concurrent.IRWLockManager;
import org.eclipse.net4j.util.concurrent.IRWLockManager.LockType;

import org.eclipse.emf.spi.cdo.CDOSessionProtocol.LockObjectsResult;

import java.io.IOException;
import java.util.List;

/**
 * @author Eike Stepper, Caspar De Groot
 */
public class LockObjectsRequest extends CDOClientRequest<LockObjectsResult>
{
  private int viewID;

  private IRWLockManager.LockType lockType;

  private long timeout;

  private List<CDORevisionKey> revisionKeys;

  private boolean recursive;

  public LockObjectsRequest(CDOClientProtocol protocol, List<CDORevisionKey> revisionKeys, int viewID, LockType lockType, boolean recursive, long timeout)
  {
    this(protocol, CDOProtocolConstants.SIGNAL_LOCK_OBJECTS, revisionKeys, viewID, lockType, recursive, timeout);
  }

  protected LockObjectsRequest(CDOClientProtocol protocol, short signalID, List<CDORevisionKey> revisionKeys, int viewID, LockType lockType, boolean recursive,
      long timeout)
  {
    super(protocol, signalID);

    this.viewID = viewID;
    this.lockType = lockType;
    this.timeout = timeout;
    this.revisionKeys = revisionKeys;
    this.recursive = recursive;
  }

  @Override
  protected void requesting(CDODataOutput out) throws IOException
  {
    out.writeXInt(viewID);
    out.writeCDOLockType(lockType);
    out.writeBoolean(recursive);
    out.writeXLong(timeout);

    out.writeXInt(revisionKeys.size());
    for (CDORevisionKey revKey : revisionKeys)
    {
      out.writeCDORevisionKey(revKey);
    }
  }

  @Override
  protected LockObjectsResult confirming(CDODataInput in) throws IOException
  {
    boolean succesful = in.readBoolean();
    boolean timeout = in.readBoolean();
    boolean waitForUpdate = in.readBoolean();
    long requiredTimestamp = in.readXLong();

    int nStaleRevisions = in.readXInt();
    CDORevisionKey[] staleRevisions = new CDORevisionKey[nStaleRevisions];
    for (int i = 0; i < nStaleRevisions; i++)
    {
      staleRevisions[i] = in.readCDORevisionKey();
    }

    long timestamp = in.readXLong();

    int n = in.readXInt();
    CDOLockState[] newLockStates = new CDOLockState[n];
    for (int i = 0; i < n; i++)
    {
      newLockStates[i] = in.readCDOLockState();
    }

    return new LockObjectsResult(succesful, timeout, waitForUpdate, requiredTimestamp, staleRevisions, newLockStates, timestamp);
  }
}

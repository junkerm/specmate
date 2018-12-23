/*
 * Copyright (c) 2010-2013, 2016 Eike Stepper (Loehne, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.emf.cdo.internal.server;

import org.eclipse.emf.cdo.common.branch.CDOBranchPoint;
import org.eclipse.emf.cdo.common.branch.CDOBranchVersion;
import org.eclipse.emf.cdo.common.commit.CDOCommitInfo;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.id.CDOIDReference;
import org.eclipse.emf.cdo.common.lock.CDOLockState;
import org.eclipse.emf.cdo.common.revision.CDORevision;
import org.eclipse.emf.cdo.server.IStoreAccessor;
import org.eclipse.emf.cdo.server.IStoreAccessor.CommitContext;
import org.eclipse.emf.cdo.server.ITransaction;
import org.eclipse.emf.cdo.server.IView;
import org.eclipse.emf.cdo.spi.common.model.InternalCDOPackageRegistry;
import org.eclipse.emf.cdo.spi.common.model.InternalCDOPackageUnit;
import org.eclipse.emf.cdo.spi.common.revision.InternalCDORevision;
import org.eclipse.emf.cdo.spi.common.revision.InternalCDORevisionDelta;

import org.eclipse.net4j.util.concurrent.RWOLockManager.LockState;
import org.eclipse.net4j.util.io.ExtendedDataInputStream;

import org.eclipse.emf.ecore.EClass;

import java.util.List;
import java.util.Map;

/**
 * @author Eike Stepper
 */
public abstract class DelegatingCommitContext implements IStoreAccessor.CommitContext
{
  protected abstract CommitContext getDelegate();

  public ITransaction getTransaction()
  {
    return getDelegate().getTransaction();
  }

  public CDOBranchPoint getBranchPoint()
  {
    return getDelegate().getBranchPoint();
  }

  public String getUserID()
  {
    return getDelegate().getUserID();
  }

  public String getCommitComment()
  {
    return getDelegate().getCommitComment();
  }

  public InternalCDOPackageRegistry getPackageRegistry()
  {
    return getDelegate().getPackageRegistry();
  }

  public InternalCDOPackageUnit[] getNewPackageUnits()
  {
    return getDelegate().getNewPackageUnits();
  }

  public InternalCDORevision[] getNewObjects()
  {
    return getDelegate().getNewObjects();
  }

  public InternalCDORevision[] getDirtyObjects()
  {
    return getDelegate().getDirtyObjects();
  }

  public InternalCDORevisionDelta[] getDirtyObjectDeltas()
  {
    return getDelegate().getDirtyObjectDeltas();
  }

  public CDOID[] getDetachedObjects()
  {
    return getDelegate().getDetachedObjects();
  }

  public Map<CDOID, EClass> getDetachedObjectTypes()
  {
    return getDelegate().getDetachedObjectTypes();
  }

  public CDORevision getRevision(CDOID id)
  {
    return getDelegate().getRevision(id);
  }

  public Map<CDOID, CDOID> getIDMappings()
  {
    return getDelegate().getIDMappings();
  }

  public long getPreviousTimeStamp()
  {
    return getDelegate().getPreviousTimeStamp();
  }

  public long getLastUpdateTime()
  {
    return getDelegate().getLastUpdateTime();
  }

  public boolean isClearResourcePathCache()
  {
    return getDelegate().isClearResourcePathCache();
  }

  public boolean isUsingEcore()
  {
    return getDelegate().isUsingEcore();
  }

  public boolean isUsingEtypes()
  {
    return getDelegate().isUsingEtypes();
  }

  @Deprecated
  public boolean isAutoReleaseLocksEnabled()
  {
    return getDelegate().isAutoReleaseLocksEnabled();
  }

  public CDOLockState[] getLocksOnNewObjects()
  {
    return getDelegate().getLocksOnNewObjects();
  }

  public CDOID[] getIDsToUnlock()
  {
    return getDelegate().getIDsToUnlock();
  }

  public CDOBranchVersion[] getDetachedObjectVersions()
  {
    return getDelegate().getDetachedObjectVersions();
  }

  public ExtendedDataInputStream getLobs()
  {
    return getDelegate().getLobs();
  }

  public CDOCommitInfo createCommitInfo()
  {
    return getDelegate().createCommitInfo();
  }

  public List<LockState<Object, IView>> getPostCommmitLockStates()
  {
    return getDelegate().getPostCommmitLockStates();
  }

  public byte getRollbackReason()
  {
    return getDelegate().getRollbackReason();
  }

  public String getRollbackMessage()
  {
    return getDelegate().getRollbackMessage();
  }

  public List<CDOIDReference> getXRefs()
  {
    return getDelegate().getXRefs();
  }
}

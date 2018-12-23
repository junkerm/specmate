/*
 * Copyright (c) 2009-2013, 2015, 2016 Eike Stepper (Loehne, Germany) and others.
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
import org.eclipse.emf.cdo.common.branch.CDOBranchVersion;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.lock.CDOLockState;
import org.eclipse.emf.cdo.common.util.CDOTimeProvider;
import org.eclipse.emf.cdo.server.IStoreAccessor;
import org.eclipse.emf.cdo.spi.common.model.InternalCDOPackageUnit;
import org.eclipse.emf.cdo.spi.common.revision.InternalCDORevision;
import org.eclipse.emf.cdo.spi.common.revision.InternalCDORevisionDelta;

import org.eclipse.net4j.util.io.ExtendedDataInputStream;
import org.eclipse.net4j.util.om.monitor.OMMonitor;
import org.eclipse.net4j.util.om.monitor.ProgressDistributable;
import org.eclipse.net4j.util.om.monitor.ProgressDistributor;

import org.eclipse.emf.ecore.EClass;

import java.util.Map;
import java.util.Set;

/**
 * If the meaning of this type isn't clear, there really should be more of a description here...
 *
 * @author Eike Stepper
 * @since 3.0
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface InternalCommitContext extends IStoreAccessor.CommitContext, CDOTimeProvider
{
  @SuppressWarnings("unchecked")
  public static final ProgressDistributable<InternalCommitContext>[] OPS = ProgressDistributor.array( //
      new ProgressDistributable.Default<InternalCommitContext>()
      {
        public void runLoop(int index, InternalCommitContext commitContext, OMMonitor monitor) throws Exception
        {
          commitContext.write(monitor.fork());
        }
      }, //

      new ProgressDistributable.Default<InternalCommitContext>()
      {
        public void runLoop(int index, InternalCommitContext commitContext, OMMonitor monitor) throws Exception
        {
          if (commitContext.getRollbackMessage() == null)
          {
            commitContext.commit(monitor.fork());
          }
          else
          {
            monitor.worked();
          }
        }
      });

  public InternalTransaction getTransaction();

  /**
   * @since 4.5
   */
  public IStoreAccessor getAccessor();

  /**
   * @since 4.2
   */
  public long getTimeStamp();

  /**
   * @since 4.5
   */
  public boolean isTreeRestructuring();

  /**
   * @since 4.2
   */
  public void setLastTreeRestructuringCommit(long lastTreeRestructuringCommit);

  public void preWrite();

  public void write(OMMonitor monitor);

  public void commit(OMMonitor monitor);

  public void rollback(String message);

  public void postCommit(boolean success);

  /**
   * @since 4.0
   */
  public InternalCDORevision[] getDetachedRevisions();

  /**
   * @since 4.6
   */
  public InternalCDORevision[] getDetachedRevisions(boolean check);

  /**
   * @since 4.2
   */
  public void setClearResourcePathCache(boolean clearResourcePathCache);

  /**
   * @since 4.2
   */
  public void setUsingEcore(boolean usingEcore);

  /**
   * @since 4.2
   */
  public void setUsingEtypes(boolean usingEtypes);

  public void setNewPackageUnits(InternalCDOPackageUnit[] newPackageUnits);

  public void setNewObjects(InternalCDORevision[] newObjects);

  public void setDirtyObjectDeltas(InternalCDORevisionDelta[] dirtyObjectDeltas);

  public void setDetachedObjects(CDOID[] detachedObjects);

  /**
   * @since 4.0
   */
  public void setDetachedObjectTypes(Map<CDOID, EClass> detachedObjectTypes);

  /**
   * @since 4.2
   */
  public void setDetachedObjectVersions(CDOBranchVersion[] detachedObjectVersions);

  /**
   * @since 4.2
   */
  public void setLastUpdateTime(long lastUpdateTime);

  /**
   * @deprecated As of 4.5 no longer supported. See {@link #setIDsToUnlock(CDOID[])}.
   */
  @Deprecated
  public void setAutoReleaseLocksEnabled(boolean on);

  /**
   * @since 4.1
   */
  public void setLocksOnNewObjects(CDOLockState[] locksOnNewObjects);

  /**
   * @since 4.6
   */
  public void setIDsToUnlock(CDOID[] idsToUnlock);

  /**
   * @since 4.5
   */
  public void setCommitNumber(int commitNumber);

  public void setCommitComment(String comment);

  /**
   * @since 4.6
   */
  public void setCommitMergeSource(CDOBranchPoint mergeSource);

  /**
   * @since 4.0
   */
  public void setLobs(ExtendedDataInputStream in);

  public void addIDMapping(CDOID oldID, CDOID newID);

  public void applyIDMappings(OMMonitor monitor);

  /**
   * @since 4.3
   */
  public void setSecurityImpact(byte securityImpact, Set<? extends Object> impactedRules);
}

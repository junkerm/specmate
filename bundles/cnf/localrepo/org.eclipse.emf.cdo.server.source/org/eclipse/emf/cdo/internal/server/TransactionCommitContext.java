/*
 * Copyright (c) 2010-2016 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Simon McDuff - initial API and implementation
 *    Eike Stepper - maintenance
 *    Martin Fluegge - maintenance, bug 318518
 *    Christian W. Damus (CEA LIST) - bug 399487
 */
package org.eclipse.emf.cdo.internal.server;

import org.eclipse.emf.cdo.common.CDOCommonRepository.IDGenerationLocation;
import org.eclipse.emf.cdo.common.branch.CDOBranch;
import org.eclipse.emf.cdo.common.branch.CDOBranchPoint;
import org.eclipse.emf.cdo.common.branch.CDOBranchVersion;
import org.eclipse.emf.cdo.common.commit.CDOCommitData;
import org.eclipse.emf.cdo.common.commit.CDOCommitInfo;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.id.CDOIDObject;
import org.eclipse.emf.cdo.common.id.CDOIDReference;
import org.eclipse.emf.cdo.common.id.CDOIDUtil;
import org.eclipse.emf.cdo.common.lock.CDOLockChangeInfo;
import org.eclipse.emf.cdo.common.lock.CDOLockChangeInfo.Operation;
import org.eclipse.emf.cdo.common.lock.CDOLockOwner;
import org.eclipse.emf.cdo.common.lock.CDOLockState;
import org.eclipse.emf.cdo.common.lock.CDOLockUtil;
import org.eclipse.emf.cdo.common.model.CDOPackageUnit;
import org.eclipse.emf.cdo.common.protocol.CDOProtocol.CommitNotificationInfo;
import org.eclipse.emf.cdo.common.protocol.CDOProtocolConstants;
import org.eclipse.emf.cdo.common.revision.CDOIDAndBranch;
import org.eclipse.emf.cdo.common.revision.CDOIDAndVersion;
import org.eclipse.emf.cdo.common.revision.CDORevision;
import org.eclipse.emf.cdo.common.revision.CDORevisionKey;
import org.eclipse.emf.cdo.common.revision.CDORevisionUtil;
import org.eclipse.emf.cdo.common.revision.delta.CDOAddFeatureDelta;
import org.eclipse.emf.cdo.common.revision.delta.CDOContainerFeatureDelta;
import org.eclipse.emf.cdo.common.revision.delta.CDOFeatureDelta;
import org.eclipse.emf.cdo.common.revision.delta.CDOFeatureDeltaVisitor;
import org.eclipse.emf.cdo.common.revision.delta.CDORevisionDelta;
import org.eclipse.emf.cdo.common.revision.delta.CDOSetFeatureDelta;
import org.eclipse.emf.cdo.common.security.NoPermissionException;
import org.eclipse.emf.cdo.common.util.CDOCommonUtil;
import org.eclipse.emf.cdo.common.util.CDOQueryInfo;
import org.eclipse.emf.cdo.internal.common.commit.FailureCommitInfo;
import org.eclipse.emf.cdo.internal.common.model.CDOPackageRegistryImpl;
import org.eclipse.emf.cdo.internal.server.bundle.OM;
import org.eclipse.emf.cdo.server.IRepository;
import org.eclipse.emf.cdo.server.IStoreAccessor;
import org.eclipse.emf.cdo.server.IStoreAccessor.QueryXRefsContext;
import org.eclipse.emf.cdo.server.IView;
import org.eclipse.emf.cdo.server.StoreThreadLocal;
import org.eclipse.emf.cdo.spi.common.commit.CDOCommitInfoUtil;
import org.eclipse.emf.cdo.spi.common.commit.InternalCDOCommitInfoManager;
import org.eclipse.emf.cdo.spi.common.model.InternalCDOPackageInfo;
import org.eclipse.emf.cdo.spi.common.model.InternalCDOPackageRegistry;
import org.eclipse.emf.cdo.spi.common.model.InternalCDOPackageUnit;
import org.eclipse.emf.cdo.spi.common.revision.CDOFeatureDeltaVisitorImpl;
import org.eclipse.emf.cdo.spi.common.revision.CDOIDMapper;
import org.eclipse.emf.cdo.spi.common.revision.CDOReferenceAdjuster;
import org.eclipse.emf.cdo.spi.common.revision.DetachedCDORevision;
import org.eclipse.emf.cdo.spi.common.revision.InternalCDORevision;
import org.eclipse.emf.cdo.spi.common.revision.InternalCDORevisionCache;
import org.eclipse.emf.cdo.spi.common.revision.InternalCDORevisionDelta;
import org.eclipse.emf.cdo.spi.common.revision.InternalCDORevisionManager;
import org.eclipse.emf.cdo.spi.common.revision.StubCDORevision;
import org.eclipse.emf.cdo.spi.server.InternalCommitContext;
import org.eclipse.emf.cdo.spi.server.InternalLockManager;
import org.eclipse.emf.cdo.spi.server.InternalRepository;
import org.eclipse.emf.cdo.spi.server.InternalTransaction;
import org.eclipse.emf.cdo.spi.server.InternalTransaction.CommitAttempt;
import org.eclipse.emf.cdo.spi.server.InternalUnitManager;

import org.eclipse.net4j.util.CheckUtil;
import org.eclipse.net4j.util.StringUtil;
import org.eclipse.net4j.util.collection.IndexedList;
import org.eclipse.net4j.util.concurrent.IRWLockManager.LockType;
import org.eclipse.net4j.util.concurrent.RWOLockManager;
import org.eclipse.net4j.util.concurrent.RWOLockManager.LockState;
import org.eclipse.net4j.util.io.ExtendedDataInputStream;
import org.eclipse.net4j.util.lifecycle.LifecycleUtil;
import org.eclipse.net4j.util.om.monitor.Monitor;
import org.eclipse.net4j.util.om.monitor.OMMonitor;
import org.eclipse.net4j.util.om.trace.ContextTracer;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Simon McDuff
 * @since 2.0
 */
public class TransactionCommitContext implements InternalCommitContext
{
  private static final ContextTracer TRACER = new ContextTracer(OM.DEBUG_TRANSACTION, TransactionCommitContext.class);

  private static final InternalCDORevision DETACHED = new StubCDORevision(null);

  private final InternalTransaction transaction;

  private final CDOBranch branch;

  private InternalRepository repository;

  private InternalLockManager lockManager;

  private InternalCDOPackageRegistry repositoryPackageRegistry;

  private boolean packageRegistryLocked;

  private TransactionPackageRegistry packageRegistry;

  private IStoreAccessor accessor;

  private long lastUpdateTime;

  private long lastTreeRestructuringCommit;

  private Boolean treeRestructuring;

  private long timeStamp = CDORevision.UNSPECIFIED_DATE;

  private long previousTimeStamp = CDORevision.UNSPECIFIED_DATE;

  private int commitNumber;

  private String commitComment;

  private CDOBranchPoint commitMergeSource;

  private boolean usingEcore;

  private boolean usingEtypes;

  private InternalCDOPackageUnit[] newPackageUnits = new InternalCDOPackageUnit[0];

  private InternalCDORevision[] newObjects = new InternalCDORevision[0];

  private InternalCDORevisionDelta[] dirtyObjectDeltas = new InternalCDORevisionDelta[0];

  private CDOID[] detachedObjects = new CDOID[0];

  private Map<CDOID, EClass> detachedObjectTypes;

  private CDOBranchVersion[] detachedObjectVersions;

  private InternalCDORevision[] dirtyObjects = new InternalCDORevision[0];

  private InternalCDORevision[] cachedDetachedRevisions = new InternalCDORevision[0];

  private Map<CDOID, InternalCDORevision> cachedRevisions;

  private Map<CDOID, InternalCDORevision> oldRevisions = CDOIDUtil.createMap();

  private Map<CDOID, InternalCDORevision> newRevisions;

  private Set<Object> lockedObjects = new HashSet<Object>();

  private List<CDOID> lockedTargets;

  private Map<CDOID, CDOID> idMappings = CDOIDUtil.createMap();

  private CDOReferenceAdjuster idMapper = new CDOIDMapper(idMappings);

  private byte rollbackReason = CDOProtocolConstants.ROLLBACK_REASON_UNKNOWN;

  private String rollbackMessage;

  private List<CDOIDReference> xRefs;

  private boolean hasChanges;

  private boolean serializingCommits;

  private boolean ensuringReferentialIntegrity;

  private ExtendedDataInputStream lobs;

  private CDOLockState[] locksOnNewObjects = new CDOLockState[0];

  private CDOID[] idsToUnlock = new CDOID[0];

  private Map<Object, Object> data;

  private CommitNotificationInfo commitNotificationInfo = new CommitNotificationInfo();

  private CDOLockChangeInfo lockChangeInfo;

  private final List<LockState<Object, IView>> postCommitLockStates = new ArrayList<LockState<Object, IView>>();

  public TransactionCommitContext(InternalTransaction transaction)
  {
    this.transaction = transaction;

    branch = transaction.getBranch();
    repository = transaction.getRepository();
    lockManager = repository.getLockingManager();
    serializingCommits = repository.isSerializingCommits();
    ensuringReferentialIntegrity = repository.isEnsuringReferentialIntegrity();

    repositoryPackageRegistry = repository.getPackageRegistry(false);
  }

  public InternalTransaction getTransaction()
  {
    return transaction;
  }

  public CDOBranchPoint getBranchPoint()
  {
    return branch.getPoint(timeStamp);
  }

  public String getUserID()
  {
    return transaction.getSession().getUserID();
  }

  public int getCommitNumber()
  {
    return commitNumber;
  }

  public String getCommitComment()
  {
    return commitComment;
  }

  public CDOBranchPoint getCommitMergeSource()
  {
    return commitMergeSource;
  }

  public long getLastUpdateTime()
  {
    return lastUpdateTime;
  }

  public byte getRollbackReason()
  {
    return rollbackReason;
  }

  public String getRollbackMessage()
  {
    return rollbackMessage;
  }

  public List<CDOIDReference> getXRefs()
  {
    return xRefs;
  }

  public InternalCDOPackageRegistry getPackageRegistry()
  {
    if (packageRegistry == null)
    {
      packageRegistry = new TransactionPackageRegistry(repositoryPackageRegistry);
      packageRegistry.activate();
    }

    return packageRegistry;
  }

  public boolean isClearResourcePathCache()
  {
    return commitNotificationInfo.isClearResourcePathCache();
  }

  public byte getSecurityImpact()
  {
    return commitNotificationInfo.getSecurityImpact();
  }

  public boolean isUsingEcore()
  {
    return usingEcore;
  }

  public boolean isUsingEtypes()
  {
    return usingEtypes;
  }

  public InternalCDOPackageUnit[] getNewPackageUnits()
  {
    return newPackageUnits;
  }

  public InternalCDORevision[] getNewObjects()
  {
    return newObjects;
  }

  public InternalCDORevision[] getDirtyObjects()
  {
    return dirtyObjects;
  }

  public CDOID[] getDetachedObjects()
  {
    return detachedObjects;
  }

  public Map<CDOID, EClass> getDetachedObjectTypes()
  {
    return detachedObjectTypes;
  }

  public CDOBranchVersion[] getDetachedObjectVersions()
  {
    return detachedObjectVersions;
  }

  public InternalCDORevision[] getDetachedRevisions()
  {
    return getDetachedRevisions(true);
  }

  public InternalCDORevision[] getDetachedRevisions(boolean check)
  {
    if (check)
    {
      // This array can contain null values as they only come from the cache!
      for (InternalCDORevision cachedDetachedRevision : cachedDetachedRevisions)
      {
        if (cachedDetachedRevision == null)
        {
          throw new AssertionError("Detached revisions are incomplete");
        }
      }
    }

    return cachedDetachedRevisions;
  }

  public InternalCDORevisionDelta[] getDirtyObjectDeltas()
  {
    return dirtyObjectDeltas;
  }

  public Map<CDOID, InternalCDORevision> getOldRevisions()
  {
    return oldRevisions;
  }

  public Map<CDOID, InternalCDORevision> getNewRevisions()
  {
    if (newRevisions == null)
    {
      newRevisions = CDOIDUtil.createMap();

      for (int i = 0; i < newObjects.length; i++)
      {
        InternalCDORevision revision = newObjects[i];
        newRevisions.put(revision.getID(), revision);
      }
    }

    return newRevisions;
  }

  public InternalCDORevision getRevision(CDOID id)
  {
    if (cachedRevisions == null)
    {
      cachedRevisions = cacheRevisions();
    }

    // Try "after state"
    InternalCDORevision revision = cachedRevisions.get(id);
    if (revision == DETACHED)
    {
      return null;
    }

    if (revision != null)
    {
      return revision;
    }

    // Fall back to "before state"
    return (InternalCDORevision)transaction.getRevision(id);
  }

  protected Map<CDOID, InternalCDORevision> cacheRevisions()
  {
    Map<CDOID, InternalCDORevision> cache = CDOIDUtil.createMap();
    if (newObjects != null)
    {
      for (int i = 0; i < newObjects.length; i++)
      {
        InternalCDORevision revision = newObjects[i];
        cache.put(revision.getID(), revision);
      }
    }

    if (dirtyObjects != null)
    {
      for (int i = 0; i < dirtyObjects.length; i++)
      {
        InternalCDORevision revision = dirtyObjects[i];
        cache.put(revision.getID(), revision);
      }
    }

    if (detachedObjects != null)
    {
      for (int i = 0; i < detachedObjects.length; i++)
      {
        cache.put(detachedObjects[i], DETACHED);
      }
    }

    return cache;
  }

  public Map<CDOID, CDOID> getIDMappings()
  {
    return Collections.unmodifiableMap(idMappings);
  }

  public void addIDMapping(CDOID oldID, CDOID newID)
  {
    if (CDOIDUtil.isNull(newID) || newID.isTemporary())
    {
      throw new IllegalStateException("newID=" + newID); //$NON-NLS-1$
    }

    CDOID previousMapping = idMappings.put(oldID, newID);
    if (previousMapping != null && previousMapping != newID)
    {
      throw new IllegalStateException("previousMapping != null && previousMapping != newID"); //$NON-NLS-1$
    }
  }

  public void applyIDMappings(OMMonitor monitor)
  {
    boolean mapIDs = !idMappings.isEmpty();
    monitor.begin(1 + (mapIDs ? newObjects.length + dirtyObjects.length + dirtyObjectDeltas.length : 0));

    try
    {
      if (mapIDs)
      {
        applyIDMappings(newObjects, monitor.fork(newObjects.length));
        applyIDMappings(dirtyObjects, monitor.fork(dirtyObjects.length));
        for (CDORevisionDelta dirtyObjectDelta : dirtyObjectDeltas)
        {
          ((InternalCDORevisionDelta)dirtyObjectDelta).adjustReferences(idMapper);
          monitor.worked();
        }
      }

      // Do not notify handlers before the IDs are fully mapped!
      notifyBeforeCommitting(monitor);
    }
    finally
    {
      monitor.done();
    }
  }

  protected void applyIDMappings(InternalCDORevision[] revisions, OMMonitor monitor)
  {
    try
    {
      monitor.begin(revisions.length);
      for (InternalCDORevision revision : revisions)
      {
        if (revision != null)
        {
          CDOID newID = idMappings.get(revision.getID());
          if (newID != null)
          {
            revision.setID(newID);
          }

          revision.adjustReferences(idMapper);
          monitor.worked();
        }
      }
    }
    finally
    {
      monitor.done();
    }
  }

  protected void notifyBeforeCommitting(OMMonitor monitor)
  {
    repository.notifyWriteAccessHandlers(transaction, this, true, monitor.fork());
  }

  public void preWrite()
  {
    // Allocate a store writer
    accessor = repository.getStore().getWriter(transaction);

    // Make the store writer available in a ThreadLocal variable
    StoreThreadLocal.setAccessor(accessor);
    StoreThreadLocal.setCommitContext(this);
  }

  public boolean isTreeRestructuring()
  {
    if (treeRestructuring == null)
    {
      treeRestructuring = CDORevisionUtil.isTreeRestructuring(dirtyObjectDeltas);
    }

    return treeRestructuring;
  }

  public void setLastTreeRestructuringCommit(long lastTreeRestructuringCommit)
  {
    this.lastTreeRestructuringCommit = lastTreeRestructuringCommit;
  }

  public void setClearResourcePathCache(boolean clearResourcePathCache)
  {
    commitNotificationInfo.setClearResourcePathCache(clearResourcePathCache);
  }

  public void setSecurityImpact(byte securityImpact, Set<? extends Object> impactedRules)
  {
    commitNotificationInfo.setSecurityImpact(securityImpact);
    commitNotificationInfo.setImpactedRules(impactedRules);
  }

  public void setUsingEcore(boolean usingEcore)
  {
    this.usingEcore = usingEcore;
  }

  public void setUsingEtypes(boolean usingEtypes)
  {
    this.usingEtypes = usingEtypes;
  }

  public void setNewPackageUnits(InternalCDOPackageUnit[] newPackageUnits)
  {
    this.newPackageUnits = newPackageUnits;
  }

  public void setNewObjects(InternalCDORevision[] newObjects)
  {
    this.newObjects = newObjects;
  }

  public void setDirtyObjectDeltas(InternalCDORevisionDelta[] dirtyObjectDeltas)
  {
    this.dirtyObjectDeltas = dirtyObjectDeltas;
  }

  public void setDetachedObjects(CDOID[] detachedObjects)
  {
    this.detachedObjects = detachedObjects;
  }

  public void setDetachedObjectTypes(Map<CDOID, EClass> detachedObjectTypes)
  {
    this.detachedObjectTypes = detachedObjectTypes;
  }

  public void setDetachedObjectVersions(CDOBranchVersion[] detachedObjectVersions)
  {
    this.detachedObjectVersions = detachedObjectVersions;
  }

  public void setLastUpdateTime(long lastUpdateTime)
  {
    this.lastUpdateTime = lastUpdateTime;
  }

  public void setCommitNumber(int commitNumber)
  {
    this.commitNumber = commitNumber;
  }

  public void setCommitComment(String commitComment)
  {
    this.commitComment = commitComment;
  }

  public void setCommitMergeSource(CDOBranchPoint commitMergeSource)
  {
    this.commitMergeSource = commitMergeSource;
  }

  public ExtendedDataInputStream getLobs()
  {
    return lobs;
  }

  public void setLobs(ExtendedDataInputStream in)
  {
    lobs = in;
  }

  @Deprecated
  public boolean isAutoReleaseLocksEnabled()
  {
    return false;
  }

  @Deprecated
  public void setAutoReleaseLocksEnabled(boolean on)
  {
    // Do nothing.
  }

  public CDOLockState[] getLocksOnNewObjects()
  {
    return locksOnNewObjects;
  }

  public void setLocksOnNewObjects(CDOLockState[] locksOnNewObjects)
  {
    this.locksOnNewObjects = locksOnNewObjects;
  }

  public CDOID[] getIDsToUnlock()
  {
    return idsToUnlock;
  }

  public void setIDsToUnlock(CDOID[] idsToUnlock)
  {
    this.idsToUnlock = idsToUnlock;
  }

  public <T> T getData(Object key)
  {
    if (data == null)
    {
      return null;
    }

    @SuppressWarnings("unchecked")
    T result = (T)data.get(key);
    return result;
  }

  public synchronized <T extends Object> T setData(Object key, T value)
  {
    if (data == null)
    {
      data = new HashMap<Object, Object>();
    }

    @SuppressWarnings("unchecked")
    T old = (T)data.put(key, value);
    return old;
  }

  protected InternalCDOPackageUnit[] lockPackageRegistry(InternalCDOPackageUnit[] packageUnits) throws InterruptedException
  {
    if (!packageRegistryLocked)
    {
      repository.getPackageRegistryCommitLock().acquire();
      packageRegistryLocked = true;
    }

    List<InternalCDOPackageUnit> noDuplicates = new ArrayList<InternalCDOPackageUnit>();
    for (InternalCDOPackageUnit packageUnit : packageUnits)
    {
      String id = packageUnit.getID();
      if (!repositoryPackageRegistry.containsKey(id))
      {
        noDuplicates.add(packageUnit);
      }
    }

    int newSize = noDuplicates.size();
    if (packageUnits.length != newSize)
    {
      return noDuplicates.toArray(new InternalCDOPackageUnit[newSize]);
    }

    return packageUnits;
  }

  /**
   * @since 2.0
   */
  public void write(OMMonitor monitor)
  {
    try
    {
      monitor.begin(107);

      hasChanges = newPackageUnits.length != 0 || newObjects.length != 0 || dirtyObjectDeltas.length != 0;
      if (!hasChanges)
      {
        return;
      }

      dirtyObjects = new InternalCDORevision[dirtyObjectDeltas.length];

      if (newPackageUnits.length != 0)
      {
        newPackageUnits = lockPackageRegistry(newPackageUnits);
      }

      lockObjects(); // Can take long and must come before setTimeStamp()
      monitor.worked();

      setTimeStamp(monitor.fork());

      adjustForCommit();
      monitor.worked();

      computeDirtyObjects(monitor.fork());

      checkContainmentCycles();
      checkXRefs();
      checkUnitMoves();
      monitor.worked();

      detachObjects(monitor.fork());
      writeAccessor(monitor.fork(100));
    }
    catch (RollbackException ex)
    {
      rollbackReason = ex.getRollbackReason();
      rollback(ex.getRollbackMessage());
    }
    catch (Throwable t)
    {
      handleException(t);
    }
    finally
    {
      finishMonitor(monitor);
    }
  }

  public void commit(OMMonitor monitor)
  {
    try
    {
      monitor.begin(101);
      if (hasChanges)
      {
        accessor.commit(monitor.fork(100));
      }
      else
      {
        monitor.worked(100);
      }

      updateInfraStructure(monitor.fork());

      if (hasChanges)
      {
        // Bug 297940
        repository.endCommit(timeStamp);
      }
    }
    catch (Throwable ex)
    {
      handleException(ex);
    }
    finally
    {
      finishMonitor(monitor);
    }
  }

  public List<LockState<Object, IView>> getPostCommmitLockStates()
  {
    return postCommitLockStates;
  }

  protected void handleException(Throwable ex)
  {
    try
    {
      if (TRACER.isEnabled())
      {
        TRACER.trace(ex);
      }

      if (ex instanceof IRepository.WriteAccessHandler.TransactionValidationException)
      {
        rollbackReason = CDOProtocolConstants.ROLLBACK_REASON_VALIDATION_ERROR;
        rollback(ex.getLocalizedMessage());
      }
      else
      {
        String storeClass = repository.getStore().getClass().getSimpleName();
        rollback("Rollback in " + storeClass + ": " + StringUtil.formatException(ex)); //$NON-NLS-1$ //$NON-NLS-2$
      }
    }
    catch (Exception ex1)
    {
      if (rollbackMessage == null)
      {
        rollbackMessage = ex1.getMessage();
      }

      try
      {
        if (TRACER.isEnabled())
        {
          TRACER.trace(ex1);
        }
      }
      catch (Exception ignore)
      {
      }
    }
  }

  protected void finishMonitor(OMMonitor monitor)
  {
    try
    {
      monitor.done();
    }
    catch (Exception ex)
    {
      try
      {
        OM.LOG.warn(ex);
      }
      catch (Exception ignore)
      {
      }
    }
  }

  protected void setTimeStamp(OMMonitor monitor)
  {
    long[] times = createTimeStamp(monitor); // Could throw an exception
    timeStamp = times[0];
    previousTimeStamp = times[1];
    CheckUtil.checkState(timeStamp != CDOBranchPoint.UNSPECIFIED_DATE, "Commit timestamp must not be 0");

    transaction.setLastCommitAttempt(new CommitAttempt(commitNumber, timeStamp, previousTimeStamp));
  }

  protected long[] createTimeStamp(OMMonitor monitor)
  {
    return repository.createCommitTimeStamp(monitor);
  }

  public long getTimeStamp()
  {
    return timeStamp;
  }

  protected void setTimeStamp(long timeStamp)
  {
    repository.forceCommitTimeStamp(timeStamp, new Monitor());
    this.timeStamp = timeStamp;
  }

  public long getPreviousTimeStamp()
  {
    return previousTimeStamp;
  }

  public void postCommit(boolean success)
  {
    try
    {
      if (packageRegistryLocked)
      {
        repository.getPackageRegistryCommitLock().release();
      }
    }
    catch (Throwable ex)
    {
      OM.LOG.warn("A problem occured while releasing the package registry commit lock", ex);
    }

    try
    {
      // Send notifications (in particular FailureCommitInfos) only if timeStamp had been allocated
      if (timeStamp != CDOBranchPoint.UNSPECIFIED_DATE)
      {
        sendCommitNotifications(success);
      }
    }
    catch (Throwable ex)
    {
      OM.LOG.warn("A problem occured while notifying other sessions", ex);
    }
    finally
    {
      StoreThreadLocal.release();
      accessor = null;
      lockedTargets = null;

      if (packageRegistry != null)
      {
        packageRegistry.deactivate();
        packageRegistry = null;
      }
    }
  }

  protected void sendCommitNotifications(boolean success)
  {
    commitNotificationInfo.setSender(transaction.getSession());
    commitNotificationInfo.setRevisionProvider(this);
    commitNotificationInfo.setLockChangeInfo(lockChangeInfo);

    if (success)
    {
      commitNotificationInfo.setCommitInfo(createCommitInfo());
    }
    else
    {
      commitNotificationInfo.setCommitInfo(createFailureCommitInfo());
    }

    repository.sendCommitNotification(commitNotificationInfo);
  }

  public CDOCommitInfo createCommitInfo()
  {
    String userID = transaction.getSession().getUserID();
    CDOCommitData commitData = createCommitData();

    InternalCDOCommitInfoManager commitInfoManager = repository.getCommitInfoManager();
    return commitInfoManager.createCommitInfo(branch, timeStamp, previousTimeStamp, userID, commitComment, commitMergeSource, commitData);
  }

  public CDOCommitInfo createFailureCommitInfo()
  {
    InternalCDOCommitInfoManager commitInfoManager = repository.getCommitInfoManager();
    return new FailureCommitInfo(commitInfoManager, timeStamp, previousTimeStamp);
  }

  protected CDOCommitData createCommitData()
  {
    List<CDOPackageUnit> newPackageUnitsCollection = new IndexedList.ArrayBacked<CDOPackageUnit>()
    {
      @Override
      protected CDOPackageUnit[] getArray()
      {
        return newPackageUnits;
      }
    };

    List<CDOIDAndVersion> newObjectsCollection = new IndexedList.ArrayBacked<CDOIDAndVersion>()
    {
      @Override
      protected CDOIDAndVersion[] getArray()
      {
        return newObjects;
      }
    };

    List<CDORevisionKey> changedObjectsCollection = new IndexedList.ArrayBacked<CDORevisionKey>()
    {
      @Override
      protected CDORevisionKey[] getArray()
      {
        return dirtyObjectDeltas;
      }
    };

    List<CDOIDAndVersion> detachedObjectsCollection = new IndexedList<CDOIDAndVersion>()
    {
      @Override
      public CDOIDAndVersion get(int i)
      {
        if (cachedDetachedRevisions[i] != null)
        {
          return cachedDetachedRevisions[i];
        }

        return CDOIDUtil.createIDAndVersion(detachedObjects[i], CDORevision.UNSPECIFIED_VERSION);
      }

      @Override
      public int size()
      {
        return detachedObjects.length;
      }
    };

    return CDOCommitInfoUtil.createCommitData(newPackageUnitsCollection, newObjectsCollection, changedObjectsCollection, detachedObjectsCollection);
  }

  protected void adjustForCommit()
  {
    for (InternalCDOPackageUnit newPackageUnit : newPackageUnits)
    {
      newPackageUnit.setTimeStamp(timeStamp);
    }

    for (InternalCDORevision newObject : newObjects)
    {
      newObject.adjustForCommit(branch, timeStamp);
    }
  }

  protected void lockObjects() throws InterruptedException
  {
    lockedObjects.clear();
    lockedTargets = null;

    try
    {
      CDOFeatureDeltaVisitor deltaTargetLocker = null;
      if (ensuringReferentialIntegrity && !serializingCommits)
      {
        final Set<CDOID> newIDs = new HashSet<CDOID>();
        for (int i = 0; i < newObjects.length; i++)
        {
          InternalCDORevision newRevision = newObjects[i];
          CDOID newID = newRevision.getID();
          if (newID instanceof CDOIDObject)
          {
            // After merges newObjects may contain non-TEMP ids
            newIDs.add(newID);
          }
        }

        final boolean supportingBranches = repository.isSupportingBranches();
        deltaTargetLocker = new CDOFeatureDeltaVisitorImpl()
        {
          @Override
          public void visit(CDOAddFeatureDelta delta)
          {
            lockTarget(delta.getValue(), newIDs, supportingBranches);
          }

          @Override
          public void visit(CDOSetFeatureDelta delta)
          {
            lockTarget(delta.getValue(), newIDs, supportingBranches);
          }
        };

        CDOReferenceAdjuster revisionTargetLocker = new CDOReferenceAdjuster()
        {
          public Object adjustReference(Object value, EStructuralFeature feature, int index)
          {
            lockTarget(value, newIDs, supportingBranches);
            return value;
          }
        };

        for (int i = 0; i < newObjects.length; i++)
        {
          InternalCDORevision newRevision = newObjects[i];
          newRevision.adjustReferences(revisionTargetLocker);
        }
      }

      for (int i = 0; i < dirtyObjectDeltas.length; i++)
      {
        InternalCDORevisionDelta delta = dirtyObjectDeltas[i];
        CDOID id = delta.getID();
        Object key = lockManager.getLockKey(id, branch);
        lockedObjects.add(key);
      }

      if (deltaTargetLocker != null)
      {
        for (int i = 0; i < dirtyObjectDeltas.length; i++)
        {
          InternalCDORevisionDelta delta = dirtyObjectDeltas[i];
          delta.accept(deltaTargetLocker);
        }
      }

      for (int i = 0; i < detachedObjects.length; i++)
      {
        CDOID id = detachedObjects[i];
        Object key = lockManager.getLockKey(id, branch);
        lockedObjects.add(key);
      }

      if (!lockedObjects.isEmpty())
      {
        try
        {
          long timeout = repository.getOptimisticLockingTimeout();

          // First lock all objects (incl. possible ref targets).
          // This is a transient operation, it does not check for existance!
          lockManager.lock2(LockType.WRITE, transaction, lockedObjects, timeout);
        }
        catch (Exception ex)
        {
          throw new RollbackException(CDOProtocolConstants.ROLLBACK_REASON_OPTIMISTIC_LOCKING, ex);
        }

        // If all locks could be acquired, check if locked targets do still exist
        if (lockedTargets != null)
        {
          for (CDOID id : lockedTargets)
          {
            CDORevision revision = transaction.getRevision(id);
            if (revision == null || revision instanceof DetachedCDORevision)
            {
              throw new RollbackException(CDOProtocolConstants.ROLLBACK_REASON_REFERENTIAL_INTEGRITY,
                  "Attempt by " + transaction + " to introduce a stale reference");
            }
          }
        }
      }
    }
    catch (RuntimeException ex)
    {
      lockedObjects.clear();
      lockedTargets = null;
      throw ex;
    }
  }

  protected void lockTarget(Object value, Set<CDOID> newIDs, boolean supportingBranches)
  {
    if (value instanceof CDOIDObject)
    {
      CDOIDObject id = (CDOIDObject)value;
      if (id.isNull())
      {
        return;
      }

      if (newIDs.contains(id))
      {
        // After merges newObjects may contain non-TEMP ids
        return;
      }

      if (detachedObjectTypes != null && detachedObjectTypes.containsKey(id))
      {
        throw new IllegalStateException("This commit deletes object " + id + " and adds a reference at the same time");
      }

      // Let this object be locked
      Object key = lockManager.getLockKey(id, branch);
      lockedObjects.add(key);

      // Let this object be checked for existance after it has been locked
      if (lockedTargets == null)
      {
        lockedTargets = new ArrayList<CDOID>();
      }

      lockedTargets.add(id);
    }
  }

  protected void computeDirtyObjects(OMMonitor monitor)
  {
    try
    {
      monitor.begin(dirtyObjectDeltas.length);
      for (int i = 0; i < dirtyObjectDeltas.length; i++)
      {
        dirtyObjects[i] = computeDirtyObject(dirtyObjectDeltas[i]);
        if (dirtyObjects[i] == null)
        {
          throw new IllegalStateException("Can not retrieve origin revision for " + dirtyObjectDeltas[i]); //$NON-NLS-1$
        }

        if (!dirtyObjects[i].isWritable())
        {
          throw new NoPermissionException(dirtyObjects[i]);
        }

        monitor.worked();
      }
    }
    finally
    {
      monitor.done();
    }
  }

  protected InternalCDORevision computeDirtyObject(InternalCDORevisionDelta delta)
  {
    CDOID id = delta.getID();

    InternalCDORevision oldRevision = null;
    String rollbackMessage = null;
    byte rollbackReason = CDOProtocolConstants.ROLLBACK_REASON_UNKNOWN;

    try
    {
      oldRevision = (InternalCDORevision)transaction.getRevision(id);
      if (oldRevision != null)
      {
        if (oldRevision.getBranch() != delta.getBranch() || oldRevision.getVersion() != delta.getVersion())
        {
          rollbackMessage = "Attempt by " + transaction + " to modify historical revision: " + delta;
          rollbackReason = CDOProtocolConstants.ROLLBACK_REASON_COMMIT_CONFLICT;
        }
      }
      else
      {
        rollbackMessage = "Revision " + id + " not found by " + transaction;
      }
    }
    catch (Exception ex)
    {
      OM.LOG.error(ex);

      rollbackMessage = ex.getMessage();
      if (rollbackMessage == null)
      {
        rollbackMessage = ex.getClass().getName();
      }
    }

    if (rollbackMessage != null)
    {
      // If the object is logically locked (see lockObjects) but has a wrong (newer) version, someone else modified it.
      throw new RollbackException(rollbackReason, rollbackMessage);
    }

    // Make sure all chunks are loaded
    repository.ensureChunks(oldRevision, CDORevision.UNCHUNKED);

    oldRevisions.put(id, oldRevision);

    InternalCDORevision newRevision = oldRevision.copy();
    newRevision.adjustForCommit(branch, timeStamp);

    delta.applyTo(newRevision);
    return newRevision;
  }

  protected void checkContainmentCycles()
  {
    if (lastTreeRestructuringCommit == 0)
    {
      // If this was a tree-restructuring commit then lastTreeRestructuringCommit would be initialized.
      return;
    }

    if (lastUpdateTime == CDOBranchPoint.UNSPECIFIED_DATE)
    {
      // Happens during replication (see CommitDelegationRequest). Commits are checked in the master repo.
      return;
    }

    if (lastTreeRestructuringCommit <= lastUpdateTime)
    {
      // If this client's original state includes the state of the last tree-restructuring commit there's no danger.
      return;
    }

    Set<CDOID> objectsThatReachTheRoot = new HashSet<CDOID>();
    for (int i = 0; i < dirtyObjectDeltas.length; i++)
    {
      InternalCDORevisionDelta revisionDelta = dirtyObjectDeltas[i];
      CDOFeatureDelta containerDelta = revisionDelta.getFeatureDelta(CDOContainerFeatureDelta.CONTAINER_FEATURE);
      if (containerDelta != null)
      {
        InternalCDORevision revision = dirtyObjects[i];
        if (!isTheRootReachable(revision, objectsThatReachTheRoot, new HashSet<CDOID>()))
        {
          throw new RollbackException(CDOProtocolConstants.ROLLBACK_REASON_CONTAINMENT_CYCLE,
              "Attempt by " + transaction + " to introduce a containment cycle");
        }
      }
    }
  }

  protected boolean isTheRootReachable(InternalCDORevision revision, Set<CDOID> objectsThatReachTheRoot, Set<CDOID> visited)
  {
    CDOID id = revision.getID();
    if (!visited.add(id))
    {
      // Cycle detected on the way up to the root.
      return false;
    }

    if (!objectsThatReachTheRoot.add(id))
    {
      // Has already been checked before.
      return true;
    }

    CDOID containerID = (CDOID)revision.getContainerID();
    if (CDOIDUtil.isNull(containerID))
    {
      // The tree root has been reached.
      return true;
    }

    // Use this commit context as CDORevisionProvider for the container revisions.
    // This is safe because Repository.commit() serializes all tree-restructuring commits.
    InternalCDORevision containerRevision = getRevision(containerID);

    // Recurse Up
    return isTheRootReachable(containerRevision, objectsThatReachTheRoot, visited);
  }

  protected void checkXRefs()
  {
    if (ensuringReferentialIntegrity && detachedObjectTypes != null)
    {
      XRefContext context = new XRefContext();
      xRefs = context.getXRefs(accessor);
      if (!xRefs.isEmpty())
      {
        throw new RollbackException(CDOProtocolConstants.ROLLBACK_REASON_REFERENTIAL_INTEGRITY,
            "Attempt by " + transaction + " to introduce a stale reference");
      }
    }
  }

  protected void checkUnitMoves()
  {
    if (repository.isSupportingUnits() && isTreeRestructuring())
    {
      String checkUnitMoves = repository.getProperties().get(IRepository.Props.CHECK_UNIT_MOVES);
      if ("true".equalsIgnoreCase(checkUnitMoves))
      {
        InternalUnitManager unitManager = repository.getUnitManager();

        List<InternalCDORevisionDelta> unitMoves = unitManager.getUnitMoves(dirtyObjectDeltas, transaction, this);
        if (!unitMoves.isEmpty())
        {
          StringBuilder builder = new StringBuilder("Attempt by " + transaction + " to move objects between units: ");
          CDOIDUtil.write(builder, unitMoves);

          throw new RollbackException(CDOProtocolConstants.ROLLBACK_REASON_UNIT_INTEGRITY, builder.toString());
        }
      }
    }
  }

  public synchronized void rollback(String message)
  {
    // Check if we already rolled back
    if (rollbackMessage == null)
    {
      rollbackMessage = message;

      if (accessor != null)
      {
        try
        {
          accessor.rollback();
        }
        catch (RuntimeException ex)
        {
          OM.LOG.warn("Problem while rolling back the transaction", ex); //$NON-NLS-1$
        }
        finally
        {
          repository.failCommit(timeStamp);
        }
      }

      releaseImplicitLocks();
    }
  }

  public IStoreAccessor getAccessor()
  {
    return accessor;
  }

  protected void updateInfraStructure(OMMonitor monitor)
  {
    try
    {
      monitor.begin(9);
      addNewPackageUnits(monitor.fork());
      addRevisions(newObjects, monitor.fork());
      addRevisions(dirtyObjects, monitor.fork());
      reviseDetachedObjects(monitor.fork());

      InternalCDOCommitInfoManager commitInfoManager = repository.getCommitInfoManager();
      commitInfoManager.setLastCommitOfBranch(branch, timeStamp);

      releaseImplicitLocks();
      monitor.worked();

      acquireLocksOnNewObjects();
      monitor.worked();

      autoReleaseExplicitLocks();
      monitor.worked();

      if (!postCommitLockStates.isEmpty())
      {
        lockChangeInfo = createLockChangeInfo(postCommitLockStates);
      }

      repository.notifyWriteAccessHandlers(transaction, this, false, monitor.fork());
    }
    catch (Throwable t)
    {
      handleException(t);
    }
    finally
    {
      monitor.done();
    }
  }

  protected synchronized void releaseImplicitLocks()
  {
    // Unlock objects locked during commit
    if (!lockedObjects.isEmpty())
    {
      lockManager.unlock2(LockType.WRITE, transaction, lockedObjects);
      lockedObjects.clear();
    }
  }

  protected void acquireLocksOnNewObjects() throws InterruptedException
  {
    final CDOLockOwner owner = CDOLockUtil.createLockOwner(transaction);
    final boolean mapIDs = transaction.getRepository().getIDGenerationLocation() == IDGenerationLocation.STORE;

    for (CDOLockState lockState : locksOnNewObjects)
    {
      Object target = lockState.getLockedObject();

      if (mapIDs)
      {
        CDOIDAndBranch idAndBranch = target instanceof CDOIDAndBranch ? (CDOIDAndBranch)target : null;
        CDOID id = idAndBranch != null ? ((CDOIDAndBranch)target).getID() : (CDOID)target;
        CDOID newID = idMappings.get(id);
        CheckUtil.checkNull(newID, "newID");

        target = idAndBranch != null ? CDOIDUtil.createIDAndBranch(newID, idAndBranch.getBranch()) : newID;
      }

      LockState<Object, IView> postCommitLockState = null;
      for (LockType type : LockType.values())
      {
        if (lockState.isLocked(type, owner, false))
        {
          List<LockState<Object, IView>> lockStates = lockManager.lock2(type, transaction, Collections.singleton(target), 0);
          postCommitLockState = lockStates.get(0);
        }
      }

      if (postCommitLockState != null)
      {
        postCommitLockStates.add(postCommitLockState);
      }
    }
  }

  protected void autoReleaseExplicitLocks() throws InterruptedException
  {
    List<Object> targets = new ArrayList<Object>();

    // Release locks that have been sent from the client.
    for (CDOID id : idsToUnlock)
    {
      Object target = lockManager.getLockKey(id, branch);
      targets.add(target);
    }

    // Release durable locks that have been acquired on detached objects.
    for (CDOID id : detachedObjects)
    {
      Object target = lockManager.getLockKey(id, branch);
      if (lockManager.hasLock(LockType.WRITE, transaction, target))
      {
        // We only need to consider detached objects that have been explicitly locked
        targets.add(target);
      }
    }

    try
    {
      RWOLockManager.setUnlockAll(true);

      List<LockState<Object, IView>> lockStates = lockManager.unlock2(true, LockType.WRITE, transaction, targets, false);
      if (lockStates != null)
      {
        postCommitLockStates.addAll(lockStates);
      }
    }
    finally
    {
      RWOLockManager.setUnlockAll(false);
    }
  }

  protected CDOLockChangeInfo createLockChangeInfo(List<LockState<Object, IView>> newLockStates)
  {
    long timeStamp = getTimeStamp();
    CDOLockState[] newStates = Repository.toCDOLockStates(newLockStates);

    return CDOLockUtil.createLockChangeInfo(timeStamp, transaction, branch, Operation.UNLOCK, null, newStates);
  }

  protected void addNewPackageUnits(OMMonitor monitor)
  {
    InternalCDOPackageRegistry repositoryPackageRegistry = repository.getPackageRegistry(false);
    synchronized (repositoryPackageRegistry)
    {
      try
      {
        monitor.begin(newPackageUnits.length);
        for (int i = 0; i < newPackageUnits.length; i++)
        {
          InternalCDOPackageUnit packageUnit = newPackageUnits[i];
          packageUnit.setState(CDOPackageUnit.State.LOADED);
          packageUnit.setPackageRegistry(repositoryPackageRegistry);
          repositoryPackageRegistry.putPackageUnit(packageUnit);
          monitor.worked();
        }
      }
      finally
      {
        monitor.done();
      }
    }
  }

  protected void addRevisions(CDORevision[] revisions, OMMonitor monitor)
  {
    try
    {
      monitor.begin(revisions.length);
      InternalCDORevisionManager revisionManager = repository.getRevisionManager();

      for (CDORevision revision : revisions)
      {
        if (revision != null)
        {
          revisionManager.addRevision(revision);
        }

        monitor.worked();
      }
    }
    finally
    {
      monitor.done();
    }
  }

  protected void reviseDetachedObjects(OMMonitor monitor)
  {
    try
    {
      monitor.begin(cachedDetachedRevisions.length);
      long revised = getBranchPoint().getTimeStamp() - 1;
      for (InternalCDORevision revision : cachedDetachedRevisions)
      {
        if (revision != null)
        {
          revision.setRevised(revised);
        }

        monitor.worked();
      }
    }
    finally
    {
      monitor.done();
    }
  }

  protected void detachObjects(OMMonitor monitor)
  {
    int size = detachedObjects.length;
    cachedDetachedRevisions = new InternalCDORevision[size];

    CDOID[] detachedObjects = getDetachedObjects();

    try
    {
      monitor.begin(size);
      InternalCDORevisionCache cache = repository.getRevisionManager().getCache();

      for (int i = 0; i < size; i++)
      {
        CDOID id = detachedObjects[i];

        // Remember the cached revision that must be revised after successful commit through updateInfraStructure
        cachedDetachedRevisions[i] = (InternalCDORevision)cache.getRevision(id, transaction);
        monitor.worked();
      }
    }
    finally
    {
      monitor.done();
    }
  }

  protected void writeAccessor(OMMonitor monitor)
  {
    accessor.write(this, monitor);
  }

  @Override
  public String toString()
  {
    return MessageFormat.format("TransactionCommitContext[{0}, {1}, {2}]", transaction.getSession(), transaction, //$NON-NLS-1$
        CDOCommonUtil.formatTimeStamp(timeStamp));
  }

  /**
   * @author Eike Stepper
   */
  public static final class TransactionPackageRegistry extends CDOPackageRegistryImpl
  {
    private static final long serialVersionUID = 1L;

    public TransactionPackageRegistry(InternalCDOPackageRegistry repositoryPackageRegistry)
    {
      delegateRegistry = repositoryPackageRegistry;
      setPackageLoader(repositoryPackageRegistry.getPackageLoader());
    }

    @Override
    public synchronized void putPackageUnit(InternalCDOPackageUnit packageUnit)
    {
      LifecycleUtil.checkActive(this);
      packageUnit.setPackageRegistry(this);
      for (InternalCDOPackageInfo packageInfo : packageUnit.getPackageInfos())
      {
        EPackage ePackage = packageInfo.getEPackage();
        basicPut(ePackage.getNsURI(), ePackage);
      }

      resetInternalCaches();
    }

    @Override
    protected void disposePackageUnits()
    {
      // Do nothing
    }

    @Override
    public Collection<Object> values()
    {
      throw new UnsupportedOperationException();
    }

    @Override
    public String toString()
    {
      return "TransactionPackageRegistry";
    }
  }

  /**
   * @author Eike Stepper
   */
  protected static final class RollbackException extends RuntimeException
  {
    private static final long serialVersionUID = 1L;

    private final byte rollbackReason;

    private final String rollbackMessage;

    public RollbackException(byte rollbackReason, String rollbackMessage)
    {
      this.rollbackReason = rollbackReason;
      this.rollbackMessage = rollbackMessage;
    }

    public RollbackException(byte rollbackReason, Throwable cause)
    {
      super(cause);
      this.rollbackReason = rollbackReason;
      rollbackMessage = cause.getMessage();
    }

    public byte getRollbackReason()
    {
      return rollbackReason;
    }

    public String getRollbackMessage()
    {
      return rollbackMessage;
    }
  }

  /**
   * @author Eike Stepper
   */
  private final class XRefContext implements QueryXRefsContext
  {
    private Map<EClass, List<EReference>> sourceCandidates = new HashMap<EClass, List<EReference>>();

    private Set<CDOID> detachedIDs = new HashSet<CDOID>();

    private Set<CDOID> dirtyIDs = new HashSet<CDOID>();

    private List<CDOIDReference> result = new ArrayList<CDOIDReference>();

    public XRefContext()
    {
      XRefsQueryHandler.collectSourceCandidates(transaction, detachedObjectTypes.values(), sourceCandidates);

      for (CDOID id : detachedObjects)
      {
        detachedIDs.add(id);
      }

      for (InternalCDORevision revision : dirtyObjects)
      {
        dirtyIDs.add(revision.getID());
      }
    }

    public List<CDOIDReference> getXRefs(IStoreAccessor accessor)
    {
      accessor.queryXRefs(this);
      checkDirtyObjects();
      return result;
    }

    private void checkDirtyObjects()
    {
      final CDOID[] dirtyID = { null };
      CDOReferenceAdjuster dirtyObjectChecker = new CDOReferenceAdjuster()
      {
        public Object adjustReference(Object targetID, EStructuralFeature feature, int index)
        {
          if (!(feature instanceof EReference && ((EReference)feature).isContainer()))
          {
            if (detachedIDs.contains(targetID))
            {
              result.add(new CDOIDReference((CDOID)targetID, dirtyID[0], feature, index));
            }
          }

          return targetID;
        }
      };

      for (InternalCDORevision dirtyObject : dirtyObjects)
      {
        dirtyID[0] = dirtyObject.getID();
        dirtyObject.adjustReferences(dirtyObjectChecker);
      }
    }

    public long getTimeStamp()
    {
      return CDOBranchPoint.UNSPECIFIED_DATE;
    }

    public CDOBranch getBranch()
    {
      return branch;
    }

    public Map<CDOID, EClass> getTargetObjects()
    {
      return detachedObjectTypes;
    }

    public EReference[] getSourceReferences()
    {
      return new EReference[0];
    }

    public Map<EClass, List<EReference>> getSourceCandidates()
    {
      return sourceCandidates;
    }

    public int getMaxResults()
    {
      return CDOQueryInfo.UNLIMITED_RESULTS;
    }

    public boolean addXRef(CDOID targetID, CDOID sourceID, EReference sourceReference, int sourceIndex)
    {
      if (CDOIDUtil.isNull(targetID))
      {
        // Compensate potential issues with the XRef implementation in the store accessor.
        return true;
      }

      if (detachedIDs.contains(sourceID))
      {
        // Ignore XRefs from objects that are about to be detached themselves by this commit.
        return true;
      }

      if (dirtyIDs.contains(sourceID))
      {
        // Ignore XRefs from objects that are about to be modified by this commit. They're handled later in getXRefs().
        return true;
      }

      result.add(new CDOIDReference(targetID, sourceID, sourceReference, sourceIndex));
      return true;
    }
  }
}

/*
 * Copyright (c) 2009-2013, 2015, 2016 Eike Stepper (Loehne, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 *    Simon McDuff - bug 201266
 *    Simon McDuff - bug 213402
 */
package org.eclipse.emf.cdo.spi.server;

import org.eclipse.emf.cdo.common.CDOCommonRepository.IDGenerationLocation;
import org.eclipse.emf.cdo.common.branch.CDOBranch;
import org.eclipse.emf.cdo.common.branch.CDOBranchPoint;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.server.ISession;
import org.eclipse.emf.cdo.server.IStore;
import org.eclipse.emf.cdo.server.ITransaction;
import org.eclipse.emf.cdo.spi.common.model.InternalCDOPackageUnit;
import org.eclipse.emf.cdo.spi.common.revision.InternalCDORevision;
import org.eclipse.emf.cdo.spi.common.revision.InternalCDORevisionDelta;

import org.eclipse.net4j.util.WrappedException;
import org.eclipse.net4j.util.io.ExtendedDataInputStream;
import org.eclipse.net4j.util.io.LimitedInputStream;
import org.eclipse.net4j.util.om.monitor.OMMonitor;
import org.eclipse.net4j.util.om.monitor.OMMonitor.Async;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * If the meaning of this type isn't clear, there really should be more of a description here...
 *
 * @author Eike Stepper
 * @since 2.0
 */
public abstract class StoreAccessor extends StoreAccessorBase
{
  protected StoreAccessor(Store store, ISession session)
  {
    super(store, session);
  }

  protected StoreAccessor(Store store, ITransaction transaction)
  {
    super(store, transaction);
  }

  /**
   * @since 4.0
   */
  @Override
  protected void doWrite(InternalCommitContext context, OMMonitor monitor)
  {
    CDOBranch branch = context.getBranchPoint().getBranch();
    long timeStamp = context.getBranchPoint().getTimeStamp();
    long previousTimeStamp = context.getPreviousTimeStamp();
    String userID = context.getUserID();
    String commitComment = context.getCommitComment();
    CDOBranchPoint mergeSource = context.getCommitMergeSource();

    Store store = getStore();
    boolean deltas = store.getSupportedChangeFormats().contains(IStore.ChangeFormat.DELTA);

    InternalCDOPackageUnit[] newPackageUnits = context.getNewPackageUnits();
    InternalCDORevision[] newObjects = context.getNewObjects();
    CDOID[] detachedObjects = context.getDetachedObjects();
    InternalCDORevisionDelta[] dirtyObjectDeltas = context.getDirtyObjectDeltas();
    InternalCDORevision[] dirtyObjects = context.getDirtyObjects();

    int dirtyCount = deltas ? dirtyObjectDeltas.length : dirtyObjects.length;
    int postProcessCount = needsRevisionPostProcessing() ? newObjects.length + detachedObjects.length + dirtyCount : 0;

    try
    {
      monitor.begin(1 + newPackageUnits.length + 2 + newObjects.length + detachedObjects.length + dirtyCount + postProcessCount + 1);

      writeCommitInfo(branch, timeStamp, previousTimeStamp, userID, commitComment, mergeSource, monitor.fork());
      writePackageUnits(newPackageUnits, monitor.fork(newPackageUnits.length));

      IDGenerationLocation idGenerationLocation = store.getRepository().getIDGenerationLocation();
      if (idGenerationLocation == IDGenerationLocation.STORE)
      {
        addIDMappings(context, monitor.fork());
      }

      applyIDMappings(context, monitor);

      if (detachedObjects.length != 0)
      {
        detachObjects(detachedObjects, branch, timeStamp, monitor.fork(detachedObjects.length));
      }

      if (newObjects.length != 0)
      {
        writeNewObjectRevisions(context, newObjects, branch, monitor.fork(newObjects.length));
      }

      if (dirtyCount != 0)
      {
        if (deltas)
        {
          writeRevisionDeltas(dirtyObjectDeltas, branch, timeStamp, monitor.fork(dirtyCount));
        }
        else
        {
          writeDirtyObjectRevisions(context, dirtyObjects, branch, monitor.fork(dirtyCount));
        }
      }

      if (needsRevisionPostProcessing())
      {
        postProcessRevisions(context, monitor.fork(postProcessCount));
      }

      ExtendedDataInputStream in = context.getLobs();
      if (in != null)
      {
        Async async = monitor.forkAsync();

        try
        {
          int count = in.readInt();
          for (int i = 0; i < count; i++)
          {
            byte[] id = in.readByteArray();
            long size = in.readLong();
            if (size > 0)
            {
              writeBlob(id, size, new LimitedInputStream(in, size));
            }
            else
            {
              writeClob(id, -size, new InputStreamReader(new LimitedInputStream(in, -size)));
            }
          }
        }
        catch (IOException ex)
        {
          throw WrappedException.wrap(ex);
        }
        finally
        {
          async.stop();
        }
      }
      else
      {
        monitor.worked();
      }
    }
    finally
    {
      monitor.done();
    }
  }

  /**
   * @since 4.6
   */
  protected boolean needsRevisionPostProcessing()
  {
    return false;
  }

  /**
   * @since 4.6
   */
  protected void postProcessRevisions(InternalCommitContext context, OMMonitor monitor)
  {
  }

  /**
   * @since 3.0
   */
  protected void applyIDMappings(InternalCommitContext context, OMMonitor monitor)
  {
    context.applyIDMappings(monitor.fork());
  }

  /**
   * @since 4.0
   * @deprecated As of 4.6 override {@link #writeCommitInfo(CDOBranch, long, long, String, String, CDOBranchPoint, OMMonitor)}.
   */
  @Deprecated
  protected abstract void writeCommitInfo(CDOBranch branch, long timeStamp, long previousTimeStamp, String userID, String comment, OMMonitor monitor);

  /**
   * @since 4.6
   */
  protected void writeCommitInfo(CDOBranch branch, long timeStamp, long previousTimeStamp, String userID, String comment, CDOBranchPoint mergeSource,
      OMMonitor monitor)
  {
    writeCommitInfo(branch, timeStamp, previousTimeStamp, userID, comment, monitor);
  }

  /**
   * @since 4.5
   */
  protected void writeNewObjectRevisions(InternalCommitContext context, InternalCDORevision[] newObjects, CDOBranch branch, OMMonitor monitor)
  {
    writeRevisions(newObjects, branch, monitor);
  }

  /**
   * @since 4.5
   */
  protected void writeDirtyObjectRevisions(InternalCommitContext context, InternalCDORevision[] dirtyObjects, CDOBranch branch, OMMonitor monitor)
  {
    writeRevisions(dirtyObjects, branch, monitor);
  }

  /**
   * @since 3.0
   */
  protected abstract void writeRevisions(InternalCDORevision[] revisions, CDOBranch branch, OMMonitor monitor);

  /**
   * @since 3.0
   */
  protected abstract void writeRevisionDeltas(InternalCDORevisionDelta[] revisionDeltas, CDOBranch branch, long created, OMMonitor monitor);

  /**
   * @since 3.0
   */
  protected abstract void detachObjects(CDOID[] detachedObjects, CDOBranch branch, long timeStamp, OMMonitor monitor);

  /**
   * @since 4.0
   */
  protected abstract void writeBlob(byte[] id, long size, InputStream inputStream) throws IOException;

  /**
   * @since 4.0
   */
  protected abstract void writeClob(byte[] id, long size, Reader reader) throws IOException;
}

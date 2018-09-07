/*
 * Copyright (c) 2009-2013 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 *    Simon McDuff - bug 233490
 */
package org.eclipse.emf.cdo.internal.net4j.protocol;

import org.eclipse.emf.cdo.common.branch.CDOBranchManager;
import org.eclipse.emf.cdo.common.commit.CDOCommitInfoManager;
import org.eclipse.emf.cdo.common.lob.CDOLobStore;
import org.eclipse.emf.cdo.common.model.CDOPackageRegistry;
import org.eclipse.emf.cdo.common.protocol.CDODataInput;
import org.eclipse.emf.cdo.common.protocol.CDOProtocolConstants;
import org.eclipse.emf.cdo.common.revision.CDOListFactory;
import org.eclipse.emf.cdo.common.revision.CDORevisionFactory;
import org.eclipse.emf.cdo.internal.common.revision.CDOListWithElementProxiesImpl;
import org.eclipse.emf.cdo.spi.common.protocol.CDODataInputImpl;

import org.eclipse.net4j.signal.Indication;
import org.eclipse.net4j.util.io.ExtendedDataInputStream;
import org.eclipse.net4j.util.io.StringIO;
import org.eclipse.net4j.util.lifecycle.LifecycleException;
import org.eclipse.net4j.util.lifecycle.LifecycleState;
import org.eclipse.net4j.util.lifecycle.LifecycleUtil;

import org.eclipse.emf.spi.cdo.InternalCDOSession;

import java.io.IOException;

/**
 * @author Eike Stepper
 */
public abstract class CDOClientIndication extends Indication
{
  public CDOClientIndication(CDOClientProtocol protocol, short signalID)
  {
    super(protocol, signalID);
  }

  @Override
  public CDOClientProtocol getProtocol()
  {
    return (CDOClientProtocol)super.getProtocol();
  }

  protected InternalCDOSession getSession()
  {
    return (InternalCDOSession)getProtocol().getSession();
  }

  @Override
  protected final void indicating(ExtendedDataInputStream in) throws Exception
  {
    final InternalCDOSession session = getSession();
    if (session.getLifecycleState() == LifecycleState.ACTIVATING)
    {
      LifecycleUtil.waitForActive(session, 10000L);
    }

    try
    {
      LifecycleUtil.checkActive(session);
    }
    catch (LifecycleException ex)
    {
      throw new LifecycleException(session + " is inactive in " + getClass().getName(), ex);
    }

    indicating(new CDODataInputImpl(in)
    {
      public CDOPackageRegistry getPackageRegistry()
      {
        return session.getPackageRegistry();
      }

      @Override
      protected boolean isXCompression()
      {
        return CDOProtocolConstants.X_COMPRESSION;
      }

      @Override
      protected StringIO getPackageURICompressor()
      {
        return getProtocol().getPackageURICompressor();
      }

      @Override
      protected CDOListFactory getListFactory()
      {
        return CDOListWithElementProxiesImpl.FACTORY;
      }

      @Override
      protected CDOBranchManager getBranchManager()
      {
        return session.getBranchManager();
      }

      @Override
      protected CDOCommitInfoManager getCommitInfoManager()
      {
        return session.getCommitInfoManager();
      }

      @Override
      protected CDORevisionFactory getRevisionFactory()
      {
        return session.getRevisionManager().getFactory();
      }

      @Override
      protected CDOLobStore getLobStore()
      {
        return session.getLobStore();
      }
    });
  }

  protected abstract void indicating(CDODataInput in) throws IOException;
}

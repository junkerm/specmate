/*
 * Copyright (c) 2016, 2017 Eike Stepper (Loehne, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.emf.cdo.internal.net4j.protocol;

import org.eclipse.emf.cdo.common.commit.CDOCommitInfo;
import org.eclipse.emf.cdo.common.protocol.CDODataInput;
import org.eclipse.emf.cdo.common.protocol.CDODataOutput;
import org.eclipse.emf.cdo.common.protocol.CDOProtocolConstants;
import org.eclipse.emf.cdo.internal.net4j.bundle.OM;
import org.eclipse.emf.cdo.spi.common.commit.InternalCDOCommitInfoManager;

import org.eclipse.net4j.util.om.trace.ContextTracer;

import java.io.IOException;

/**
 * @author Eike Stepper
 */
public class ResetTransactionRequest extends CDOClientRequest<CDOCommitInfo>
{
  private static final ContextTracer TRACER = new ContextTracer(OM.DEBUG_PROTOCOL, ResetTransactionRequest.class);

  private int transactionID;

  private int commitNumber;

  public ResetTransactionRequest(CDOClientProtocol protocol, int transactionID, int commitNumber)
  {
    super(protocol, CDOProtocolConstants.SIGNAL_RESET_TRANSACTION);
    this.transactionID = transactionID;
    this.commitNumber = commitNumber;
  }

  @Override
  protected void requesting(CDODataOutput out) throws IOException
  {
    if (TRACER.isEnabled())
    {
      TRACER.format("Writing transactionID: {0}", transactionID); //$NON-NLS-1$
    }

    out.writeXInt(transactionID);

    if (TRACER.isEnabled())
    {
      TRACER.format("Writing commitNumber: {0}", commitNumber); //$NON-NLS-1$
    }

    out.writeXInt(commitNumber);
  }

  @Override
  protected CDOCommitInfo confirming(CDODataInput in) throws IOException
  {
    if (in.readBoolean())
    {
      long timeStamp = in.readXLong();
      long previousTimeStamp = in.readXLong();

      InternalCDOCommitInfoManager commitInfoManager = getSession().getCommitInfoManager();
      return commitInfoManager.createCommitInfo(null, timeStamp, previousTimeStamp, null, null, null, null);
    }

    return null;
  }
}

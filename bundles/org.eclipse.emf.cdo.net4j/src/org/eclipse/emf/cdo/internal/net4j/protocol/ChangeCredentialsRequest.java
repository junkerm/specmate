/*
 * Copyright (c) 2013, 2014 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Christian W. Damus (CEA LIST) - initial API and implementation
 */
package org.eclipse.emf.cdo.internal.net4j.protocol;

import org.eclipse.emf.cdo.common.protocol.CDODataInput;
import org.eclipse.emf.cdo.common.protocol.CDODataOutput;
import org.eclipse.emf.cdo.common.protocol.CDOProtocolConstants;
import org.eclipse.emf.cdo.internal.net4j.bundle.OM;

import org.eclipse.net4j.util.om.monitor.OMMonitor;
import org.eclipse.net4j.util.om.trace.ContextTracer;
import org.eclipse.net4j.util.security.CredentialsUpdateOperation;

import java.io.IOException;

/**
 * Request from the client to the server to initiate (from the server) the change-credentials protocol.
 *
 * @author Christian W. Damus (CEA LIST)
 */
public class ChangeCredentialsRequest extends CDOClientRequestWithMonitoring<Boolean>
{
  private static final ContextTracer TRACER = new ContextTracer(OM.DEBUG_PROTOCOL, ChangeCredentialsRequest.class);

  private final CredentialsUpdateOperation operation;

  private final String userID;

  public ChangeCredentialsRequest(CDOClientProtocol protocol, CredentialsUpdateOperation operation, String userID)
  {
    super(protocol, CDOProtocolConstants.SIGNAL_CHANGE_CREDENTIALS);

    this.operation = operation;
    this.userID = userID;
  }

  @Override
  protected void requesting(CDODataOutput out, OMMonitor monitor) throws IOException
  {
    if (TRACER.isEnabled())
    {
      TRACER.format("Requesting %s of user credentials", operation); //$NON-NLS-1$
    }

    out.writeEnum(operation);
    out.writeString(userID);
  }

  @Override
  protected Boolean confirming(CDODataInput in, OMMonitor monitor) throws IOException
  {
    return in.readBoolean();
  }
}

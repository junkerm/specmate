/*
 * Copyright (c) 2013, 2016 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 *    Christian W. Damus (CEA LIST) - Adapted from AuthenticationIndication for 399306
 */
package org.eclipse.emf.cdo.internal.net4j.protocol;

import org.eclipse.emf.cdo.common.protocol.CDOProtocolConstants;
import org.eclipse.emf.cdo.internal.net4j.bundle.OM;

import org.eclipse.net4j.signal.IndicationWithMonitoring;
import org.eclipse.net4j.signal.SignalProtocol;
import org.eclipse.net4j.util.StringUtil;
import org.eclipse.net4j.util.io.ExtendedDataInputStream;
import org.eclipse.net4j.util.io.ExtendedDataOutputStream;
import org.eclipse.net4j.util.om.monitor.OMMonitor;
import org.eclipse.net4j.util.om.monitor.OMMonitor.Async;
import org.eclipse.net4j.util.security.CredentialsUpdateOperation;
import org.eclipse.net4j.util.security.DiffieHellman;
import org.eclipse.net4j.util.security.DiffieHellman.Client.Response;
import org.eclipse.net4j.util.security.DiffieHellman.Server.Challenge;
import org.eclipse.net4j.util.security.IPasswordCredentialsProvider;
import org.eclipse.net4j.util.security.IPasswordCredentialsUpdate;
import org.eclipse.net4j.util.security.IPasswordCredentialsUpdateProvider;

import org.eclipse.emf.spi.cdo.InternalCDOSession;

import java.io.ByteArrayOutputStream;

/**
 * Implementation of the CDO client handler for the server-initiated change-credentials protocol.
 *
 * @author Christian W. Damus (CEA LIST)
 */
public class CredentialsChallengeIndication extends IndicationWithMonitoring
{
  private Challenge challenge;

  private CredentialsUpdateOperation operation;

  private String userID;

  public CredentialsChallengeIndication(SignalProtocol<?> protocol)
  {
    super(protocol, CDOProtocolConstants.SIGNAL_CREDENTIALS_CHALLENGE);
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
  protected int getIndicatingWorkPercent()
  {
    return 1;
  }

  @Override
  protected void indicating(ExtendedDataInputStream in, OMMonitor monitor) throws Exception
  {
    operation = in.readEnum(CredentialsUpdateOperation.class);
    userID = in.readString(); // May be null if operation is not reset
    challenge = new Challenge(in);
  }

  @Override
  protected void responding(ExtendedDataOutputStream out, OMMonitor monitor) throws Exception
  {
    monitor.begin();
    Async async = monitor.forkAsync();

    try
    {
      IPasswordCredentialsProvider credentialsProvider = getSession().getCredentialsProvider();
      if (!(credentialsProvider instanceof IPasswordCredentialsUpdateProvider))
      {
        throw new IllegalStateException("No credentials update provider configured"); //$NON-NLS-1$
      }

      IPasswordCredentialsUpdate credentials = ((IPasswordCredentialsUpdateProvider)credentialsProvider).getCredentialsUpdate(userID, operation);
      if (credentials == null)
      {
        // User canceled. Fine
        out.writeBoolean(false);
        return;
      }

      String authUserID = credentials.getUserID();
      String authPassword = new String(credentials.getPassword());
      String newPassword = new String(credentials.getNewPassword());
      if (StringUtil.isEmpty(newPassword))
      {
        throw new IllegalStateException("No new password provided"); //$NON-NLS-1$
      }

      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ExtendedDataOutputStream stream = new ExtendedDataOutputStream(baos);

      switch (operation)
      {
      case CHANGE_PASSWORD:
        stream.writeString(authUserID);
        stream.writeString(authPassword);
        stream.writeString(newPassword);
        break;
      case RESET_PASSWORD:
        stream.writeString(authUserID);
        stream.writeString(authPassword);
        stream.writeString(userID);
        stream.writeString(newPassword);
        break;
      }

      stream.close();
      byte[] clearText = baos.toByteArray();

      DiffieHellman.Client client = new DiffieHellman.Client();
      Response response = client.handleChallenge(challenge, clearText);
      out.writeBoolean(true);
      response.write(out);
    }
    catch (Throwable ex)
    {
      out.writeBoolean(false);
      OM.LOG.error(ex);
    }
    finally
    {
      async.stop();
      monitor.done();
    }
  }
}

/*
 * Copyright (c) 2013, 2016 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 *    Christian W. Damus (CEA LIST) - bug 418454: factored out authentication from ISessionProtocol
 */
package org.eclipse.emf.cdo.spi.server;

import org.eclipse.net4j.util.security.CredentialsUpdateOperation;
import org.eclipse.net4j.util.security.DiffieHellman.Client.Response;
import org.eclipse.net4j.util.security.DiffieHellman.Server.Challenge;

/**
 * @author Eike Stepper
 *
 * @since 4.3
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface IAuthenticationProtocol
{
  /**
   * Sends a challenge to the client to authenticate the user attempting to
   * or already connected.
   *
   * @since 4.2
   */
  public Response sendAuthenticationChallenge(Challenge challenge) throws Exception;

  /**
   * Sends a challenge to the client to change the authenticated user's credentials.
   * This is an optional operation; implementators may simply throw
   * {@link UnsupportedOperationException}.
   *
   * @since 4.3
   *
   * @throws UnsupportedOperationException if credentials change is not supported
   */
  public Response sendCredentialsChallenge(Challenge challenge, String userID, CredentialsUpdateOperation operation) throws Exception;
}

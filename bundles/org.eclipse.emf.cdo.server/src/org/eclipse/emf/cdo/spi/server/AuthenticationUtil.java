/*
 * Copyright (c) 2013-2016 Eike Stepper (Loehne, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Christian W. Damus (CEA LIST) - initial API and implementation
 */
package org.eclipse.emf.cdo.spi.server;

import java.util.concurrent.Callable;

/**
 * Static utility methods for binding {@link IAuthenticationProtocol authentication protocols} to the current thread.
 *
 * @author Christian W. Damus (CEA LIST)
 * @since 4.3
 */
public final class AuthenticationUtil
{
  private static final ThreadLocal<IAuthenticationProtocol> AUTHENTICATION_PROTOCOL = new ThreadLocal<IAuthenticationProtocol>();

  // Not instantiable by clients.
  private AuthenticationUtil()
  {
  }

  /**
   * Obtains the authentication protocol, if any, on which the current thread should
   * authenticate administrative operations in handling incoming signals.
   *
   * @return the authentication protocol to use, or {@code null} if authentication is not required
   */
  public static IAuthenticationProtocol getAuthenticationProtocol()
  {
    return AUTHENTICATION_PROTOCOL.get();
  }

  /**
   * Wrap an {@code operation} to make an authentication protocol {@linkplain #getAuthenticationProtocol() available}
   * to the thread that invokes it, for the duration of the {@code operation}'s execution.
   */
  public static <V> Callable<V> authenticatingOperation(IAuthenticationProtocol authenticationProtocol, final Callable<V> operation)
  {
    return new AuthenticatingOperation<V>(authenticationProtocol)
    {
      @Override
      protected V doCall() throws Exception
      {
        return operation.call();
      }
    };
  }

  /**
   * Encapsulation of an administrative operation requiring (potentially) client
   * authentication to authorize the operation.
   *
   * @author Christian W. Damus (CEA LIST)
   *
   * @since 4.3
   */
  public static abstract class AuthenticatingOperation<V> implements Callable<V>
  {
    private final IAuthenticationProtocol authenticationProtocol;

    public AuthenticatingOperation(IAuthenticationProtocol authenticationProtocol)
    {
      this.authenticationProtocol = authenticationProtocol;
    }

    public final V call() throws Exception
    {
      V result;

      try
      {
        AuthenticationUtil.AUTHENTICATION_PROTOCOL.set(authenticationProtocol);
        result = doCall();
      }
      finally
      {
        AuthenticationUtil.AUTHENTICATION_PROTOCOL.remove();
      }

      return result;
    }

    protected abstract V doCall() throws Exception;
  }
}

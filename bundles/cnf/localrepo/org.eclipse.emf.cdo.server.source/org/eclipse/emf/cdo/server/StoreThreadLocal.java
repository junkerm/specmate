/*
 * Copyright (c) 2008-2013, 2016 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.emf.cdo.server;

import org.eclipse.emf.cdo.server.IStoreAccessor.CommitContext;
import org.eclipse.emf.cdo.spi.server.InternalSession;

import org.eclipse.net4j.util.lifecycle.LifecycleUtil;

/**
 * Provides server-side consumers with the {@link IStoreAccessor store accessor} that is valid in the context of a
 * specific {@link ISession session} during read operations or a specific {@link CommitContext commit context} during
 * write operations.
 *
 * @author Eike Stepper
 * @since 2.0
 * @apiviz.exclude
 */
public final class StoreThreadLocal
{
  private static final ThreadLocal<InternalSession> SESSION = new InheritableThreadLocal<InternalSession>();

  private static final ThreadLocal<IStoreAccessor> ACCESSOR = new InheritableThreadLocal<IStoreAccessor>();

  private static final ThreadLocal<IStoreAccessor.CommitContext> COMMIT_CONTEXT = new InheritableThreadLocal<IStoreAccessor.CommitContext>();

  private StoreThreadLocal()
  {
  }

  /**
   * @since 3.0
   */
  public static void setSession(InternalSession session)
  {
    if (session == null)
    {
      SESSION.remove();
    }
    else
    {
      SESSION.set(session);
    }

    ACCESSOR.remove();
    COMMIT_CONTEXT.remove();
  }

  /**
   * Returns the session associated with the current thread.
   *
   * @return Never <code>null</code>.
   * @throws IllegalStateException
   *           if no session is associated with the current thread.
   * @since 3.0
   */
  public static InternalSession getSession() throws NoSessionRegisteredException
  {
    InternalSession session = SESSION.get();
    if (session == null)
    {
      throw new NoSessionRegisteredException();
    }

    return session;
  }

  /**
   * @since 4.2
   */
  public static boolean hasSession()
  {
    return SESSION.get() != null;
  }

  public static void setAccessor(IStoreAccessor accessor)
  {
    if (accessor == null)
    {
      ACCESSOR.remove();
      SESSION.remove();
    }
    else
    {
      ACCESSOR.set(accessor);
      SESSION.set(accessor.getSession());
    }
  }

  public static IStoreAccessor getAccessor() throws NoSessionRegisteredException
  {
    IStoreAccessor accessor = ACCESSOR.get();
    if (accessor == null)
    {
      ISession session = getSession();
      IStore store = session.getManager().getRepository().getStore();
      accessor = store.getReader(session);
      ACCESSOR.set(accessor);
    }

    return accessor;
  }

  public static void setCommitContext(IStoreAccessor.CommitContext commitContext)
  {
    if (commitContext == null)
    {
      COMMIT_CONTEXT.remove();
    }
    else
    {
      COMMIT_CONTEXT.set(commitContext);
    }
  }

  public static IStoreAccessor.CommitContext getCommitContext()
  {
    return COMMIT_CONTEXT.get();
  }

  /**
   * @since 4.2
   */
  public static void release()
  {
    try
    {
      IStoreAccessor accessor = ACCESSOR.get();
      if (accessor != null)
      {
        if (LifecycleUtil.isActive(accessor))
        {
          accessor.release();
        }
      }
    }
    finally
    {
      remove();
    }
  }

  /**
   * @since 4.5
   */
  public static void remove()
  {
    ACCESSOR.remove();
    SESSION.remove();
    COMMIT_CONTEXT.remove();
  }

  /**
   * An {@link IllegalStateException} that can be thrown from {@link StoreThreadLocal#getSession()}.
   *
   * @author Eike Stepper
   * @since 4.2
   */
  public static final class NoSessionRegisteredException extends IllegalStateException
  {
    private static final long serialVersionUID = 1L;

    public NoSessionRegisteredException()
    {
      super("session == null");
    }
  }
}

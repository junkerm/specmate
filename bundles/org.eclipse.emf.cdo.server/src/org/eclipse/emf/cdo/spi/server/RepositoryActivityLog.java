/*
 * Copyright (c) 2004-2018 Eike Stepper (Loehne, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.emf.cdo.spi.server;

import org.eclipse.emf.cdo.internal.server.bundle.OM;
import org.eclipse.emf.cdo.server.IRepository;
import org.eclipse.emf.cdo.server.IRepository.WriteAccessHandler;
import org.eclipse.emf.cdo.server.ISession;
import org.eclipse.emf.cdo.server.IStoreAccessor.CommitContext;
import org.eclipse.emf.cdo.server.ITransaction;
import org.eclipse.emf.cdo.server.IView;

import org.eclipse.net4j.util.StringUtil;
import org.eclipse.net4j.util.container.ContainerEventAdapter;
import org.eclipse.net4j.util.container.IContainer;
import org.eclipse.net4j.util.event.IListener;
import org.eclipse.net4j.util.factory.ProductCreationException;
import org.eclipse.net4j.util.lifecycle.LifecycleHook;
import org.eclipse.net4j.util.om.log.Log;
import org.eclipse.net4j.util.om.log.RollingLog;
import org.eclipse.net4j.util.om.monitor.OMMonitor;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Eike Stepper
 * @since 4.7
 */
public abstract class RepositoryActivityLog extends LifecycleHook<IRepository> implements Log
{
  private final IListener sessionManagerListener = new ContainerEventAdapter<ISession>()
  {
    @Override
    protected void onAdded(IContainer<ISession> container, ISession session)
    {
      sessionOpened(session, concurrentSessions.incrementAndGet(), sessions.incrementAndGet());
      session.addListener(sessionListener);
    }

    @Override
    protected void onRemoved(IContainer<ISession> container, ISession session)
    {
      session.removeListener(sessionListener);
      sessionClosed(session, concurrentSessions.decrementAndGet());
    }
  };

  private final IListener sessionListener = new ContainerEventAdapter<IView>()
  {
    @Override
    protected void onAdded(IContainer<IView> container, IView view)
    {
      if (view instanceof ITransaction)
      {
        transactionOpened((ITransaction)view, concurrentTransactions.incrementAndGet(), transactions.incrementAndGet());
      }
      else
      {
        viewOpened(view, concurrentViews.incrementAndGet(), views.incrementAndGet());
      }
    }

    @Override
    protected void onRemoved(IContainer<IView> container, IView view)
    {
      if (view instanceof ITransaction)
      {
        transactionClosed((ITransaction)view, concurrentTransactions.decrementAndGet());
      }
      else
      {
        viewClosed(view, concurrentViews.decrementAndGet());
      }
    }
  };

  private final WriteAccessHandler writeAccessHandler = new WriteAccessHandler()
  {
    public void handleTransactionBeforeCommitting(ITransaction transaction, CommitContext commitContext, OMMonitor monitor) throws RuntimeException
    {
      commitStarted(commitContext, concurrentCommits.incrementAndGet(), commits.incrementAndGet());
    }

    public void handleTransactionAfterCommitted(ITransaction transaction, CommitContext commitContext, OMMonitor monitor)
    {
      commitFinished(commitContext, concurrentCommits.decrementAndGet());
    }
  };

  private final AtomicInteger sessions = new AtomicInteger();

  private final AtomicInteger views = new AtomicInteger();

  private final AtomicInteger transactions = new AtomicInteger();

  private final AtomicInteger commits = new AtomicInteger();

  private final AtomicInteger concurrentSessions = new AtomicInteger();

  private final AtomicInteger concurrentViews = new AtomicInteger();

  private final AtomicInteger concurrentTransactions = new AtomicInteger();

  private final AtomicInteger concurrentCommits = new AtomicInteger();

  public RepositoryActivityLog()
  {
  }

  public IRepository getRepository()
  {
    return getDelegate();
  }

  public void setRepository(IRepository repository)
  {
    setDelegate(repository);
  }

  protected void sessionOpened(ISession session, int concurrentSessions, int sessions)
  {
    log(formatSession(session) + " opened" + formatUser(session) + " (" + concurrentSessions + "/" + sessions + ")");
  }

  protected void sessionClosed(ISession session, int concurrentSessions)
  {
    log(formatSession(session) + " closed" + formatUser(session) + " (" + concurrentSessions + ")");
  }

  protected void viewOpened(IView view, int concurrentViews, int views)
  {
    log(formatView(view) + " opened" + formatUser(view.getSession()) + " (" + concurrentViews + "/" + views + ")");
  }

  protected void viewClosed(IView view, int concurrentViews)
  {
    log(formatView(view) + " closed" + formatUser(view.getSession()) + " (" + concurrentViews + ")");
  }

  protected void transactionOpened(ITransaction transaction, int concurrentTransactions, int transactions)
  {
    log(formatView(transaction) + " opened" + formatUser(transaction.getSession()) + " (" + concurrentTransactions + "/" + transactions + ")");
  }

  protected void transactionClosed(ITransaction transaction, int concurrentTransactions)
  {
    log(formatView(transaction) + " closed" + formatUser(transaction.getSession()) + " (" + concurrentTransactions + ")");
  }

  protected void commitStarted(CommitContext commitContext, int concurrentCommits, int commits)
  {
    ITransaction transaction = commitContext.getTransaction();
    log(formatView(transaction) + " committing " + commitContext.getBranchPoint().getTimeStamp() + formatUser(transaction.getSession()) + " ("
        + concurrentCommits + "/" + commits + ")");
  }

  protected void commitFinished(CommitContext commitContext, int concurrentCommits)
  {
    ITransaction transaction = commitContext.getTransaction();
    log(formatView(transaction) + (commitContext.getRollbackMessage() != null ? " committed " : " rolled back ") + commitContext.getBranchPoint().getTimeStamp()
        + formatUser(transaction.getSession()) + " (" + concurrentCommits + ")");
  }

  protected String formatSession(ISession session)
  {
    return "Session " + session.getSessionID();
  }

  protected String formatUser(ISession session)
  {
    String userID = session.getUserID();
    return StringUtil.isEmpty(userID) ? "" : " by user " + userID;
  }

  protected String formatView(IView view)
  {
    return (view instanceof ITransaction ? "Transaction " : "View ") + view.getSessionID() + ":" + view.getViewID();
  }

  @Override
  protected void hookDelegate(IRepository repository)
  {
    repository.getSessionManager().addListener(sessionManagerListener);
    repository.addHandler(writeAccessHandler);
  }

  @Override
  protected void unhookDelegate(IRepository repository)
  {
    repository.removeHandler(writeAccessHandler);
    repository.getSessionManager().removeListener(sessionManagerListener);
  }

  /**
   * @author Eike Stepper
   * @since 4.7
   */
  public static abstract class Factory extends org.eclipse.net4j.util.factory.PropertiesFactory
  {
    public static final String PRODUCT_GROUP = "org.eclipse.emf.cdo.server.repositoryActivityLogs"; //$NON-NLS-1$

    public Factory(String type)
    {
      super(PRODUCT_GROUP, type);
    }

    @Override
    protected abstract RepositoryActivityLog create(Map<String, String> properties) throws ProductCreationException;
  }

  /**
   * @author Eike Stepper
   * @since 4.7
   */
  public static class Rolling extends RepositoryActivityLog
  {
    private final RollingLog rollingLog;

    public Rolling(String logFile, long logSize, boolean append)
    {
      rollingLog = new RollingLog(logFile, logSize, append);
    }

    public void log(String message)
    {
      rollingLog.log(message);
    }

    @Override
    protected void delegateChanged(IRepository oldRepository, IRepository newRepository)
    {
      if (newRepository != null)
      {
        OM.LOG.info("Logging activities of repository " + newRepository.getName() + " to " + rollingLog.getLogFile());
      }

    }

    @Override
    protected void doActivate() throws Exception
    {
      rollingLog.activate();
      super.doActivate();
    }

    @Override
    protected void doDeactivate() throws Exception
    {
      super.doDeactivate();
      rollingLog.deactivate();
    }

    /**
     * @author Eike Stepper
     */
    public static final class Factory extends RepositoryActivityLog.Factory
    {
      public static final String TYPE = "rolling"; //$NON-NLS-1$

      public Factory()
      {
        super(TYPE);
      }

      @Override
      protected RepositoryActivityLog create(Map<String, String> properties) throws ProductCreationException
      {
        String file = properties.get("file"); //$NON-NLS-1$
        if (file == null)
        {
          file = "activities";
        }

        String size = properties.get("size"); //$NON-NLS-1$
        if (StringUtil.isEmpty(size))
        {
          size = "100000000";
        }

        String append = properties.get("append"); //$NON-NLS-1$
        if (StringUtil.isEmpty(append))
        {
          append = Boolean.TRUE.toString();
        }

        return new RepositoryActivityLog.Rolling(file, Long.parseLong(size), Boolean.parseBoolean(append));
      }
    }
  }
}

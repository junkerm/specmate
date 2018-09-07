/*
 * Copyright (c) 2007-2013, 2015, 2016 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 *    Simon McDuff - bug 201266
 *    Simon McDuff - bug 202725
 *    Christian W. Damus (CEA LIST) - bug 399306
 *    Christian W. Damus (CEA LIST) - bug 418454
 */
package org.eclipse.emf.cdo.internal.server;

import org.eclipse.emf.cdo.common.CDOCommonRepository;
import org.eclipse.emf.cdo.common.CDOCommonSession;
import org.eclipse.emf.cdo.common.CDOCommonSession.Options.LockNotificationMode;
import org.eclipse.emf.cdo.common.branch.CDOBranchChangedEvent.ChangeKind;
import org.eclipse.emf.cdo.common.commit.CDOCommitInfo;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.lock.CDOLockChangeInfo;
import org.eclipse.emf.cdo.common.protocol.CDOProtocol.CommitNotificationInfo;
import org.eclipse.emf.cdo.common.protocol.CDOProtocolConstants;
import org.eclipse.emf.cdo.internal.server.bundle.OM;
import org.eclipse.emf.cdo.server.IPermissionManager;
import org.eclipse.emf.cdo.server.ISession;
import org.eclipse.emf.cdo.session.remote.CDORemoteSessionMessage;
import org.eclipse.emf.cdo.spi.common.branch.InternalCDOBranch;
import org.eclipse.emf.cdo.spi.server.IAuthenticationProtocol;
import org.eclipse.emf.cdo.spi.server.ISessionProtocol;
import org.eclipse.emf.cdo.spi.server.InternalRepository;
import org.eclipse.emf.cdo.spi.server.InternalSession;
import org.eclipse.emf.cdo.spi.server.InternalSessionManager;

import org.eclipse.net4j.util.ObjectUtil;
import org.eclipse.net4j.util.container.Container;
import org.eclipse.net4j.util.event.IListener;
import org.eclipse.net4j.util.io.ExtendedDataInputStream;
import org.eclipse.net4j.util.lifecycle.ILifecycle;
import org.eclipse.net4j.util.lifecycle.LifecycleEventAdapter;
import org.eclipse.net4j.util.lifecycle.LifecycleUtil;
import org.eclipse.net4j.util.om.trace.ContextTracer;
import org.eclipse.net4j.util.security.CredentialsUpdateOperation;
import org.eclipse.net4j.util.security.DiffieHellman;
import org.eclipse.net4j.util.security.DiffieHellman.Client.Response;
import org.eclipse.net4j.util.security.DiffieHellman.Server.Challenge;
import org.eclipse.net4j.util.security.IAuthenticator;
import org.eclipse.net4j.util.security.IAuthenticator2;
import org.eclipse.net4j.util.security.IUserManager;
import org.eclipse.net4j.util.security.UserManagerAuthenticator;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Eike Stepper
 */
public class SessionManager extends Container<ISession> implements InternalSessionManager
{
  private static final ContextTracer TRACER = new ContextTracer(OM.DEBUG_SESSION, SessionManager.class);

  private InternalRepository repository;

  private DiffieHellman.Server authenticationServer;

  private IAuthenticator authenticator;

  private IPermissionManager permissionManager;

  private final Map<Integer, InternalSession> sessions = new HashMap<Integer, InternalSession>();

  private final AtomicInteger lastSessionID = new AtomicInteger();

  private final Map<InternalSession, List<CommitNotificationInfo>> commitNotificationInfoQueues = new HashMap<InternalSession, List<CommitNotificationInfo>>();

  private final IListener sessionListener = new LifecycleEventAdapter()
  {
    @Override
    protected void onDeactivated(ILifecycle lifecycle)
    {
      synchronized (commitNotificationInfoQueues)
      {
        commitNotificationInfoQueues.remove(lifecycle);
      }
    }
  };

  /**
   * @since 2.0
   */
  public SessionManager()
  {
  }

  /**
   * @since 2.0
   */
  public InternalRepository getRepository()
  {
    return repository;
  }

  /**
   * @since 2.0
   */
  public void setRepository(InternalRepository repository)
  {
    checkInactive();
    this.repository = repository;
  }

  @Deprecated
  public IUserManager getUserManager()
  {
    if (authenticator instanceof UserManagerAuthenticator)
    {
      return ((UserManagerAuthenticator)authenticator).getUserManager();
    }

    return null;
  }

  @Deprecated
  public void setUserManager(IUserManager userManager)
  {
    UserManagerAuthenticator userManagerAuthenticator = new UserManagerAuthenticator();
    userManagerAuthenticator.setUserManager(userManager);

    setAuthenticator(userManagerAuthenticator);
  }

  public DiffieHellman.Server getAuthenticationServer()
  {
    return authenticationServer;
  }

  public void setAuthenticationServer(DiffieHellman.Server authenticationServer)
  {
    this.authenticationServer = authenticationServer;
  }

  public IAuthenticator getAuthenticator()
  {
    return authenticator;
  }

  public void setAuthenticator(IAuthenticator authenticator)
  {
    this.authenticator = authenticator;
    if (isActive() && authenticator != null)
    {
      initAuthentication();
    }
  }

  public IPermissionManager getPermissionManager()
  {
    return permissionManager;
  }

  public void setPermissionManager(IPermissionManager permissionManager)
  {
    this.permissionManager = permissionManager;
  }

  public InternalSession[] getSessions()
  {
    synchronized (sessions)
    {
      return sessions.values().toArray(new InternalSession[sessions.size()]);
    }
  }

  /**
   * @since 2.0
   */
  public InternalSession getSession(int sessionID)
  {
    checkActive();
    synchronized (sessions)
    {
      return sessions.get(sessionID);
    }
  }

  public InternalSession[] getElements()
  {
    return getSessions();
  }

  @Override
  public boolean isEmpty()
  {
    synchronized (sessions)
    {
      return sessions.isEmpty();
    }
  }

  /**
   * @since 2.0
   */
  public InternalSession openSession(ISessionProtocol sessionProtocol)
  {
    final int id = lastSessionID.incrementAndGet();
    if (TRACER.isEnabled())
    {
      TRACER.trace("Opening session " + id); //$NON-NLS-1$
    }

    String userID = authenticateUser(sessionProtocol);
    final InternalSession session = createSession(id, userID, sessionProtocol);
    LifecycleUtil.activate(session);

    synchronized (sessions)
    {
      repository.executeOutsideStartCommit(new Runnable()
      {
        public void run()
        {
          long firstUpdateTime = repository.getLastCommitTimeStamp();
          session.setFirstUpdateTime(firstUpdateTime);

          sessions.put(id, session);
        }
      });
    }

    fireElementAddedEvent(session);
    sendRemoteSessionNotification(session, CDOProtocolConstants.REMOTE_SESSION_OPENED);
    return session;
  }

  protected InternalSession createSession(int id, String userID, ISessionProtocol protocol)
  {
    return new Session(this, protocol, id, userID);
  }

  public void sessionClosed(InternalSession session)
  {
    int sessionID = session.getSessionID();
    InternalSession removeSession = null;
    synchronized (sessions)
    {
      removeSession = sessions.remove(sessionID);
    }

    if (removeSession != null)
    {
      fireElementRemovedEvent(session);
      sendRemoteSessionNotification(session, CDOProtocolConstants.REMOTE_SESSION_CLOSED);
    }
  }

  public void openedOnClientSide(InternalSession session)
  {
    processQueuedCommitNotifications(session);
  }

  public void sendRepositoryTypeNotification(CDOCommonRepository.Type oldType, CDOCommonRepository.Type newType)
  {
    for (InternalSession session : getSessions())
    {
      try
      {
        session.sendRepositoryTypeNotification(oldType, newType);
      }
      catch (Exception ex)
      {
        handleNotificationProblem(session, ex);
      }
    }
  }

  @Deprecated
  public void sendRepositoryStateNotification(CDOCommonRepository.State oldState, CDOCommonRepository.State newState)
  {
    sendRepositoryStateNotification(oldState, newState, null);
  }

  public void sendRepositoryStateNotification(CDOCommonRepository.State oldState, CDOCommonRepository.State newState, CDOID rootResourceID)
  {
    for (InternalSession session : getSessions())
    {
      try
      {
        session.sendRepositoryStateNotification(oldState, newState, rootResourceID);
      }
      catch (Exception ex)
      {
        handleNotificationProblem(session, ex);
      }
    }
  }

  @Deprecated
  public void sendBranchNotification(InternalSession sender, InternalCDOBranch branch)
  {
    sendBranchNotification(sender, branch, ChangeKind.CREATED);
  }

  public void sendBranchNotification(InternalSession sender, InternalCDOBranch branch, ChangeKind changeKind)
  {
    for (InternalSession session : getSessions())
    {
      if (session != sender)
      {
        try
        {
          session.sendBranchNotification(branch, changeKind);
        }
        catch (Exception ex)
        {
          handleNotificationProblem(session, ex);
        }
      }
    }
  }

  @Deprecated
  public void sendCommitNotification(InternalSession sender, CDOCommitInfo commitInfo)
  {
    throw new UnsupportedOperationException();
  }

  @Deprecated
  public void sendCommitNotification(InternalSession sender, CDOCommitInfo commitInfo, boolean clearResourcePathCache)
  {
    throw new UnsupportedOperationException();
  }

  public void sendCommitNotification(CommitNotificationInfo info)
  {
    CDOCommonSession sender = info.getSender();
    for (InternalSession session : getSessions())
    {
      if (session != sender)
      {
        if (session.isOpenOnClientSide())
        {
          processQueuedCommitNotifications(session);
          doSendCommitNotification(session, info);
        }
        else
        {
          queueCommitNotification(session, info);
        }
      }
    }
  }

  private void doSendCommitNotification(InternalSession session, CommitNotificationInfo info)
  {
    try
    {
      session.sendCommitNotification(info);
    }
    catch (Exception ex)
    {
      handleNotificationProblem(session, ex);
    }
  }

  private void queueCommitNotification(InternalSession session, CommitNotificationInfo info)
  {
    synchronized (commitNotificationInfoQueues)
    {
      List<CommitNotificationInfo> queue = commitNotificationInfoQueues.get(session);
      if (queue == null)
      {
        queue = new ArrayList<CommitNotificationInfo>();
        commitNotificationInfoQueues.put(session, queue);

        session.addListener(sessionListener);
      }

      queue.add(info);
    }
  }

  private void processQueuedCommitNotifications(InternalSession session)
  {
    List<CommitNotificationInfo> queue;
    synchronized (commitNotificationInfoQueues)
    {
      queue = commitNotificationInfoQueues.remove(session);
    }

    if (queue != null && !session.isClosed())
    {
      session.removeListener(sessionListener);

      for (CommitNotificationInfo queuedInfo : queue)
      {
        doSendCommitNotification(session, queuedInfo);
      }
    }
  }

  public void sendLockNotification(InternalSession sender, CDOLockChangeInfo lockChangeInfo)
  {
    for (InternalSession session : getSessions())
    {
      if (session == sender || session.options().getLockNotificationMode() == LockNotificationMode.OFF)
      {
        continue;
      }

      try
      {
        session.sendLockNotification(lockChangeInfo);
      }
      catch (Exception ex)
      {
        handleNotificationProblem(session, ex);
      }
    }
  }

  /**
   * @since 2.0
   */
  public void sendRemoteSessionNotification(InternalSession sender, byte opcode)
  {
    try
    {
      for (InternalSession session : getSessions())
      {
        if (session != sender && session.isSubscribed())
        {
          try
          {
            session.sendRemoteSessionNotification(sender, opcode);
          }
          catch (Exception ex)
          {
            handleNotificationProblem(session, ex);
          }
        }
      }
    }
    catch (Exception ex)
    {
      OM.LOG.warn("A problem occured while notifying other sessions", ex);
    }
  }

  public List<Integer> sendRemoteMessageNotification(InternalSession sender, CDORemoteSessionMessage message, int[] recipients)
  {
    List<Integer> result = new ArrayList<Integer>();
    for (int i = 0; i < recipients.length; i++)
    {
      InternalSession recipient = getSession(recipients[i]);

      try
      {
        if (recipient != null && recipient.isSubscribed())
        {
          recipient.sendRemoteMessageNotification(sender, message);
          result.add(recipient.getSessionID());
        }
      }
      catch (Exception ex)
      {
        handleNotificationProblem(recipient, ex);
      }
    }

    return result;
  }

  protected void handleNotificationProblem(InternalSession session, Throwable t)
  {
    if (session.isClosed())
    {
      if (TRACER.isEnabled())
      {
        TRACER.trace("A problem occured while notifying session " + session, t);
      }
    }
    else
    {
      OM.LOG.warn("A problem occured while notifying session " + session, t);
    }
  }

  public String authenticateUser(IAuthenticationProtocol protocol) throws SecurityException
  {
    if (protocol == null)
    {
      return null;
    }

    if (authenticationServer == null || authenticator == null)
    {
      return null;
    }

    try
    {
      Challenge challenge = authenticationServer.getChallenge();
      Response response = protocol.sendAuthenticationChallenge(challenge);
      if (response == null)
      {
        throw notAuthenticated();
      }

      ByteArrayInputStream bais = new ByteArrayInputStream(authenticationServer.handleResponse(response));

      ExtendedDataInputStream stream = new ExtendedDataInputStream(bais);
      String userID = stream.readString();
      char[] password = stream.readString().toCharArray();

      authenticator.authenticate(userID, password);
      return userID;
    }
    catch (SecurityException ex)
    {
      throw ex;
    }
    catch (Exception ex)
    {
      Throwable cause = ex.getCause();
      if (cause instanceof SecurityException)
      {
        throw (SecurityException)cause;
      }

      throw new SecurityException(ex);
    }
  }

  public void changeUserCredentials(IAuthenticationProtocol sessionProtocol, String userID)
  {
    changeUserCredentials(sessionProtocol, userID, CredentialsUpdateOperation.CHANGE_PASSWORD);
  }

  public void resetUserCredentials(IAuthenticationProtocol sessionProtocol, String userID)
  {
    changeUserCredentials(sessionProtocol, userID, CredentialsUpdateOperation.RESET_PASSWORD);
  }

  protected void changeUserCredentials(IAuthenticationProtocol sessionProtocol, String userID, CredentialsUpdateOperation operation)
  {

    if (sessionProtocol == null)
    {
      return;
    }

    if (authenticationServer == null || authenticator == null)
    {
      return;
    }

    if (!(authenticator instanceof IAuthenticator2))
    {
      throw new SecurityException("Current authenticator does not permit password updates"); //$NON-NLS-1$
    }

    try
    {
      Challenge challenge = authenticationServer.getChallenge();
      Response response = sessionProtocol.sendCredentialsChallenge(challenge, userID, operation);
      if (response == null)
      {
        throw notAuthenticated();
      }

      ByteArrayInputStream baos = new ByteArrayInputStream(authenticationServer.handleResponse(response));
      ExtendedDataInputStream stream = new ExtendedDataInputStream(baos);

      if (operation == CredentialsUpdateOperation.RESET_PASSWORD)
      {
        String adminID = stream.readString();
        char[] adminPassword = stream.readString().toCharArray();
        if (!ObjectUtil.equals(userID, stream.readString()))
        {
          throw new SecurityException("Attempt to reset password of a different user than requested"); //$NON-NLS-1$
        }
        char[] newPassword = stream.readString().toCharArray();

        // this will throw if the current credentials are not authenticated as an administrator
        ((IAuthenticator2)authenticator).resetPassword(adminID, adminPassword, userID, newPassword);
      }
      else
      {
        userID = stream.readString(); // user can change any password that she can authenticate on the old password
        char[] password = stream.readString().toCharArray();
        char[] newPassword = stream.readString().toCharArray();

        // this will throw if the "old password" provided by the user is not correct
        ((IAuthenticator2)authenticator).updatePassword(userID, password, newPassword);
      }
    }
    catch (SecurityException ex)
    {
      throw ex;
    }
    catch (Exception ex)
    {
      Throwable cause = ex.getCause();
      if (cause instanceof SecurityException)
      {
        throw (SecurityException)cause;
      }

      throw new SecurityException(ex);
    }
  }

  @Override
  protected void doActivate() throws Exception
  {
    super.doActivate();
    initAuthentication();
  }

  protected void initAuthentication()
  {
    if (authenticator != null)
    {
      if (authenticationServer == null)
      {
        authenticationServer = new DiffieHellman.Server(repository.getUUID());
      }

      LifecycleUtil.activate(authenticationServer);
      LifecycleUtil.activate(authenticator);
    }
  }

  @Override
  protected void doDeactivate() throws Exception
  {
    LifecycleUtil.deactivate(authenticator);
    LifecycleUtil.deactivate(authenticationServer);

    for (InternalSession session : getSessions())
    {
      LifecycleUtil.deactivate(session);
    }

    super.doDeactivate();
  }

  @SuppressWarnings("deprecation")
  private SecurityException notAuthenticated()
  {
    // Existing clients may expect this deprecated exception type
    return new org.eclipse.emf.cdo.common.util.NotAuthenticatedException();
  }
}

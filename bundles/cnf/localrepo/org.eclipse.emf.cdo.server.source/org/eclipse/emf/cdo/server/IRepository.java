/*
 * Copyright (c) 2007-2013, 2015, 2016 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 *    Christian W. Damus (CEA LIST) - bug 399487
 */
package org.eclipse.emf.cdo.server;

import org.eclipse.emf.cdo.common.CDOCommonRepository;
import org.eclipse.emf.cdo.common.branch.CDOBranchManager;
import org.eclipse.emf.cdo.common.commit.CDOCommitInfoHandler;
import org.eclipse.emf.cdo.common.commit.CDOCommitInfoManager;
import org.eclipse.emf.cdo.common.lock.IDurableLockingManager;
import org.eclipse.emf.cdo.common.model.CDOPackageRegistry;
import org.eclipse.emf.cdo.common.revision.CDORevision;
import org.eclipse.emf.cdo.common.revision.CDORevisionManager;
import org.eclipse.emf.cdo.common.util.CDOCommonUtil;

import org.eclipse.net4j.util.container.IContainer;
import org.eclipse.net4j.util.lifecycle.ILifecycle;
import org.eclipse.net4j.util.om.monitor.OMMonitor;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A CDO repository.
 *
 * @author Eike Stepper
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 * @apiviz.landmark
 * @apiviz.has {@link IStore}
 * @apiviz.has {@link java.util.Map} oneway - - properties
 * @apiviz.has {@link org.eclipse.emf.cdo.common.model.CDOPackageRegistry}
 * @apiviz.has {@link org.eclipse.emf.cdo.common.branch.CDOBranchManager}
 * @apiviz.has {@link org.eclipse.emf.cdo.common.revision.CDORevisionManager}
 * @apiviz.has {@link org.eclipse.emf.cdo.common.lock.IDurableLockingManager}
 * @apiviz.has {@link ISessionManager}
 * @apiviz.has {@link IQueryHandlerProvider}
 * @apiviz.composedOf {@link org.eclipse.emf.cdo.common.commit.CDOCommitInfoHandler}
 * @apiviz.composedOf {@link IRepository.Handler} - - accessHandlers
 */
public interface IRepository extends CDOCommonRepository, IQueryHandlerProvider, IContainer<Object>, ILifecycle
{
  /**
   * @since 3.0
   */
  public static final String SYSTEM_USER_ID = CDOCommonUtil.SYSTEM_USER_ID;

  public IStore getStore();

  public Map<String, String> getProperties();

  /**
   * Returns the EMF {@link Registry package registry} that is used by this repository.
   *
   * @since 2.0
   */
  public CDOPackageRegistry getPackageRegistry();

  /**
   * @since 3.0
   */
  public CDOBranchManager getBranchManager();

  /**
   * @since 3.0
   */
  public CDORevisionManager getRevisionManager();

  /**
   * @since 4.2
   */
  public CDOCommitInfoManager getCommitInfoManager();

  public ISessionManager getSessionManager();

  /**
   * @since 4.5
   */
  public IUnitManager getUnitManager();

  /**
   * @since 4.0
   * @deprecated As of 4.1 use {@link #getLockingManager()}.
   */
  @Deprecated
  public IDurableLockingManager getLockManager();

  /**
   * @since 4.1
   */
  public ILockingManager getLockingManager();

  /**
   * @since 2.0
   */
  public IQueryHandlerProvider getQueryHandlerProvider();

  /**
   * Returns the time stamp of the last commit operation.
   *
   * @since 3.0
   */
  public long getLastCommitTimeStamp();

  /**
   * Blocks the calling thread until the next commit operation has succeeded and returns the last (highest) commit time
   * stamp.
   *
   * @since 3.0
   */
  public long waitForCommit(long timeout);

  /**
   * Validates the given timeStamp against the repository time.
   *
   * @throws IllegalArgumentException
   *           if the given timeStamp is less than the repository creation time or greater than the current repository
   *           time.
   * @since 2.0
   */
  public void validateTimeStamp(long timeStamp) throws IllegalArgumentException;

  /**
   * @since 4.1
   * @deprecated As of 4.2 call {@link CDOCommitInfoManager#getCommitInfoHandlers()}
   */
  @Deprecated
  public CDOCommitInfoHandler[] getCommitInfoHandlers();

  /**
   * @since 4.0
   * @deprecated As of 4.2 call {@link CDOCommitInfoManager#addCommitInfoHandler(CDOCommitInfoHandler)}
   */
  @Deprecated
  public void addCommitInfoHandler(CDOCommitInfoHandler handler);

  /**
   * @since 4.0
   * @deprecated As of 4.2 call {@link CDOCommitInfoManager#removeCommitInfoHandler(CDOCommitInfoHandler)}
   */
  @Deprecated
  public void removeCommitInfoHandler(CDOCommitInfoHandler handler);

  /**
   * @since 4.1
   */
  public Set<Handler> getHandlers();

  /**
   * @since 2.0
   */
  public void addHandler(Handler handler);

  /**
   * @since 2.0
   */
  public void removeHandler(Handler handler);

  /**
   * @since 4.0
   */
  public void setInitialPackages(EPackage... initialPackages);

  /**
   * A marker interface to indicate valid arguments to {@link IRepository#addHandler(Handler)} and
   * {@link IRepository#removeHandler(Handler)}.
   *
   * @see ReadAccessHandler
   * @see WriteAccessHandler
   * @author Eike Stepper
   * @since 2.0
   */
  public interface Handler
  {
  }

  /**
   * Provides a way to handle revisions that are to be sent to the client.
   *
   * @author Eike Stepper
   * @since 2.0
   */
  public interface ReadAccessHandler extends Handler
  {
    /**
     * Provides a way to handle revisions that are to be sent to the client.
     *
     * @param session
     *          The session that is going to send the revisions.
     * @param revisions
     *          The revisions that are requested by the client. If the client must not see any of these revisions an
     *          unchecked exception must be thrown.
     * @param additionalRevisions
     *          The additional revisions that are to be sent to the client because internal optimizers believe that they
     *          will be needed soon. If the client must not see any of these revisions they should be removed from the
     *          list.
     * @throws RuntimeException
     *           to indicate that none of the revisions must be sent to the client. This exception will be visible at
     *           the client side!
     */
    public void handleRevisionsBeforeSending(ISession session, CDORevision[] revisions, List<CDORevision> additionalRevisions) throws RuntimeException;
  }

  /**
   * Provides a way to handle commits that are received from a client.
   *
   * @author Eike Stepper
   * @since 2.0
   */
  public interface WriteAccessHandler extends Handler
  {
    /**
     * Provides a way to handle transactions that are to be committed to the backend store.
     *
     * @param transaction
     *          The transaction that is going to be committed.
     * @param commitContext
     *          The context of the commit operation that is to be executed against the backend store. The context can be
     *          used to introspect all aspects of the current commit operation. <b>Note that you must not alter the
     *          internal state of the commit context in any way!</b>
     * @param monitor
     *          A monitor that should be used by the implementor to avoid timeouts.
     * @throws TransactionValidationException
     *           to indicate that the commit operation must not be executed against the backend store because some
     *           semantic validation checks failed. The message should describe the validation failure and will be
     *           passed through to the client
     * @throws RuntimeException
     *           to indicate that the commit operation must not be executed against the backend store. This exception
     *           will be visible at the client side!
     */
    public void handleTransactionBeforeCommitting(ITransaction transaction, IStoreAccessor.CommitContext commitContext, OMMonitor monitor)
        throws RuntimeException;

    /**
     * Provides a way to handle transactions after they have been committed to the backend store.
     *
     * @param transaction
     *          The transaction that has been committed.
     * @param commitContext
     *          The context of the commit operation that was executed against the backend store. The context can be used
     *          to introspect all aspects of the current commit operation. <b>Note that you must not alter the internal
     *          state of the commit context in any way!</b>
     * @param monitor
     *          A monitor that should be used by the implementor to avoid timeouts.
     * @since 3.0
     */
    public void handleTransactionAfterCommitted(ITransaction transaction, IStoreAccessor.CommitContext commitContext, OMMonitor monitor);

    /**
     * An exception that a {@link WriteAccessHandler} may throw to indicate that a
     * {@linkplain WriteAccessHandler#handleTransactionBeforeCommitting(ITransaction, org.eclipse.emf.cdo.server.IStoreAccessor.CommitContext, OMMonitor) transaction commit}
     * was rejected because one or more semantic validation checks reported errors.
     *
     * @author Christian W. Damus (CEA LIST)
     *
     * @since 4.3
     */
    public static final class TransactionValidationException extends RuntimeException
    {
      private static final long serialVersionUID = 1L;

      public TransactionValidationException()
      {
      }

      public TransactionValidationException(String message, Throwable cause)
      {
        super(message, cause);
      }

      public TransactionValidationException(String message)
      {
        super(message);
      }

      public TransactionValidationException(Throwable cause)
      {
        super(cause);
      }

    }
  }

  /**
   * Contains symbolic constants that specifiy valid keys of {@link IRepository#getProperties() repository properties}.
   *
   * @author Eike Stepper
   * @noimplement This interface is not intended to be implemented by clients.
   * @noextend This interface is not intended to be extended by clients.
   * @apiviz.exclude
   */
  public interface Props
  {
    /**
     * Used to override the automatic UUID generation during first startup of a repository. Passing the empty string
     * causes the UUID of the repository to be set to its {@link IRepository#getName() name}.
     *
     * @since 2.0
     */
    public static final String OVERRIDE_UUID = "overrideUUID"; //$NON-NLS-1$

    /**
     * @since 2.0
     */
    public static final String SUPPORTING_AUDITS = "supportingAudits"; //$NON-NLS-1$

    /**
     * @since 3.0
     */
    public static final String SUPPORTING_BRANCHES = "supportingBranches"; //$NON-NLS-1$

    /**
     * @since 4.5
     */
    public static final String SUPPORTING_UNITS = "supportingUnits"; //$NON-NLS-1$

    /**
     * @since 4.5
     */
    public static final String CHECK_UNIT_MOVES = "checkUnitMoves"; //$NON-NLS-1$

    /**
     * @since 4.2
     */
    public static final String SERIALIZE_COMMITS = "serializeCommits"; //$NON-NLS-1$

    /**
     * @since 3.0
     */
    public static final String ENSURE_REFERENTIAL_INTEGRITY = "ensureReferentialIntegrity"; //$NON-NLS-1$

    /**
     * @since 4.0
     */
    public static final String ALLOW_INTERRUPT_RUNNING_QUERIES = "allowInterruptRunningQueries"; //$NON-NLS-1$

    /**
     * @since 4.1
     */
    public static final String ID_GENERATION_LOCATION = "idGenerationLocation"; //$NON-NLS-1$

    /**
     * Possible values: NO | YES | WITH_MERGE_SOURCE.
     *
     * @since 4.6
     */
    public static final String COMMIT_INFO_STORAGE = "commitInfoStorage"; //$NON-NLS-1$

    /**
     * @since 4.2
     */
    public static final String OPTIMISTIC_LOCKING_TIMEOUT = "optimisticLockingTimeout"; //$NON-NLS-1$

    /**
     * @since 4.0
     * @deprecated As of 4.2 instances of Ecore are always supported (on demand).
     */
    @Deprecated
    public static final String SUPPORTING_ECORE = "supportingEcore"; //$NON-NLS-1$
  }
}

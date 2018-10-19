package com.specmate.persistency.cdo.internal;

import java.util.List;
import java.util.Map;

import org.eclipse.emf.cdo.common.commit.CDOChangeSetData;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.id.CDOIDUtil;
import org.eclipse.emf.cdo.common.revision.CDOIDAndVersion;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.cdo.view.CDOQuery;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.osgi.service.log.LogService;

import com.specmate.administration.api.IStatusService;
import com.specmate.common.SpecmateException;
import com.specmate.common.SpecmateValidationException;
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.persistency.IChange;
import com.specmate.persistency.IChangeListener;
import com.specmate.persistency.ITransaction;
import com.specmate.persistency.event.EChangeKind;
import com.specmate.rest.RestResult;

/**
 * Implements ITransaction with CDO in the back
 *
 */
public class TransactionImpl extends ViewImpl implements ITransaction {

	/* The CDO transaction */
	private CDOTransaction transaction;

	/* The log service */
	private LogService logService;

	/* Listeners that are notified on commits */
	private List<IChangeListener> changeListeners;
	private IStatusService statusService;

	public TransactionImpl(CDOPersistencyService persistency, CDOTransaction transaction, String resourceName,
			LogService logService, IStatusService statusService, List<IChangeListener> listeners) {
		super(persistency, transaction, resourceName, logService);
		this.transaction = transaction;
		this.logService = logService;
		this.statusService = statusService;
		this.changeListeners = listeners;

	}

	@Override
	public void close() {
		if (transaction != null) {
			transaction.close();
			persistency.closedTransaction(this);
		}
		logService.log(LogService.LOG_DEBUG, "Transaction closed: " + transaction.getViewID());
	}

	@Override
	public void commit() throws SpecmateException {
		commit(null);
	}

	@Override
	public <T> void commit(T object) throws SpecmateException {
		if (!isActive()) {
			throw new SpecmateException("Attempt to commit but transaction is not active");
		}
		if (!isDirty()) {
			return;
		}
		if (statusService != null && statusService.getCurrentStatus().isReadOnly()) {
			throw new SpecmateException("Attempt to commit when in read-only mode");
		}
		try {
			try {
				notifyListeners();
				List<CDOIDAndVersion> detachedObjects = transaction.getChangeSetData().getDetachedObjects();
				for (CDOIDAndVersion id : detachedObjects) {
					SpecmateEcoreUtil.unsetAllReferences(transaction.getObject(id.getID()));
				}
			} catch (SpecmateException | SpecmateValidationException s) {
				transaction.rollback();
				throw (new SpecmateException("Error while preparing commit, transaction rolled back", s));
			}
			extractAndSetMetadata(object);
			transaction.commit();
		} catch (CommitException e) {
			transaction.rollback();
			logService.log(LogService.LOG_ERROR, "Error during commit, transaction rolled back");
			throw new SpecmateException("Error during commit, transaction rolled back", e);
		}
	}

	@Override
	public <T> T doAndCommit(IChange<T> change) throws SpecmateException {
		int maxAttempts = 10;
		boolean success = false;
		int attempts = 1;
		T result = null;
		SpecmateException failCause = null;

		while (!success && attempts <= maxAttempts) {

			result = change.doChange();

			try {
				commit(result);
			} catch (SpecmateException e) {
				try {
					Thread.sleep(attempts * 50);
					failCause = e;
				} catch (InterruptedException ie) {
					throw new SpecmateException("Interrupted during commit.", ie);
				}
				attempts += 1;
				continue;
			}
			success = true;
		}
		if (!success) {
			throw new SpecmateException("Could not commit after " + maxAttempts + " attempts.", failCause);
		}
		return result;
	}

	@Override
	public boolean isDirty() {
		return transaction.isDirty();
	}

	private <T> void extractAndSetMetadata(T object) {
		if (object instanceof RestResult<?>) {
			String userName = ((RestResult<?>) object).getUserName();
			if (userName != null) {
				transaction.setCommitComment(userName);
			}
		}
	}

	private void notifyListeners() throws SpecmateException, SpecmateValidationException {
		CDOChangeSetData data = transaction.getChangeSetData();
		DeltaProcessor processor = new DeltaProcessor(data) {

			@Override
			protected void newObject(CDOID id, String className, Map<EStructuralFeature, Object> featureMap)
					throws SpecmateValidationException {
				StringBuilder builder = new StringBuilder();
				CDOIDUtil.write(builder, id);
				String idAsString = builder.toString();
				for (IChangeListener listener : changeListeners) {
					listener.newObject(transaction.getObject(id), idAsString, className, featureMap);
				}
			}

			@Override
			protected void detachedObject(CDOID id, int version) throws SpecmateValidationException {
				for (IChangeListener listener : changeListeners) {
					listener.removedObject(transaction.getObject(id));
				}
			}

			@Override
			public void changedObject(CDOID id, EStructuralFeature feature, EChangeKind changeKind, Object oldValue,
					Object newValue, int index) throws SpecmateValidationException {
				for (IChangeListener listener : changeListeners) {
					if (newValue instanceof CDOID) {
						newValue = transaction.getObject((CDOID) newValue);
					}
					listener.changedObject(transaction.getObject(id), feature, changeKind, oldValue, newValue);
				}
			}

		};

		processor.process();

	}

	public CDOTransaction getInternalTransaction() {
		return transaction;
	}

	@Override
	public void rollback() {
		transaction.rollback();

	}

	@Override
	public List<Object> query(String queryString, Object context) {
		CDOQuery cdoQuery = this.transaction.createQuery("ocl", queryString, context);
		return cdoQuery.getResult();
	}

	@Override
	public boolean isActive() {
		return persistency.isActive();
	}

	public void update(CDOTransaction transaction) {
		super.update(transaction);
		this.transaction = transaction;
	}
}

package com.specmate.persistency.cdo.internal;

import java.util.List;

import org.eclipse.emf.cdo.common.commit.CDOChangeSetData;
import org.eclipse.emf.cdo.common.commit.CDOCommitInfo;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.id.CDOIDUtil;
import org.eclipse.emf.cdo.common.revision.CDOIDAndVersion;
import org.eclipse.emf.cdo.transaction.CDOCommitContext;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.transaction.CDOTransactionHandler3;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.cdo.view.CDOQuery;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.osgi.service.log.LogService;

import com.specmate.common.SpecmateException;
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.persistency.IChangeListener;
import com.specmate.persistency.ITransaction;
import com.specmate.persistency.event.EChangeKind;

public class TransactionImpl extends ViewImpl implements ITransaction {

	private CDOTransaction transaction;
	private LogService logService;
	private List<IChangeListener> changeListeners;

	public TransactionImpl(CDOTransaction transaction, String resourceName, LogService logService,
			List<IChangeListener> listeners) {
		super(transaction, resourceName, logService);
		this.transaction = transaction;
		this.logService = logService;
		// TODO: this could be modified from different thread --> not thread
		// safe
		this.changeListeners = listeners;
		this.transaction.addTransactionHandler(new CDOTransactionHandler3() {

			@Override
			public void rolledBackTransaction(CDOTransaction transaction) {
				// TODO Auto-generated method stub

			}

			@Override
			public void committingTransaction(CDOTransaction transaction, CDOCommitContext commitContext) {
				// TODO Auto-generated method stub

			}

			@Override
			public void committedTransaction(CDOTransaction transaction, CDOCommitContext commitContext) {
				// TODO Auto-generated method stub

			}

			@Override
			public void committedTransaction(CDOTransaction transaction, CDOCommitContext commitContext,
					CDOCommitInfo result) {
				try {
					notifyListeners(result);
				} catch (SpecmateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
	}

	@Override
	public void close() {
		if (transaction != null) {
			transaction.close();
		}
		logService.log(LogService.LOG_INFO, "Transaction closed: " + transaction.getViewID());
	}

	@Override
	public void commit() throws SpecmateException {
		try {
			// try {
			List<CDOIDAndVersion> detachedObjects = transaction.getChangeSetData().getDetachedObjects();
			for (CDOIDAndVersion id : detachedObjects) {
				SpecmateEcoreUtil.unsetAllReferences(transaction.getObject(id.getID()));
			}
			// } catch (SpecmateException s) {
			// throw (new SpecmateException("Error during commit, transaction
			// rolled back", s));
			// }
			transaction.commit();
		} catch (CommitException e) {
			transaction.rollback();
			throw new SpecmateException(e);
		}
	}

	@Override
	public boolean isDirty() {
		return transaction.isDirty();
	}

	private void notifyListeners(CDOChangeSetData data) throws SpecmateException {

		DeltaProcessor processor = new DeltaProcessor(data) {

			@Override
			protected void newObject(CDOID id) {
				StringBuilder builder = new StringBuilder();
				CDOIDUtil.write(builder, id);
				String idAsString = builder.toString();
				for (IChangeListener listener : changeListeners) {
					listener.newObject(idAsString, transaction.getObject(id));
				}
			}

			@Override
			protected void detachedObject(CDOID id, int version) {
				for (IChangeListener listener : changeListeners) {
					listener.removedObject(transaction.getObject(id));
				}
			}

			@Override
			public void changedObject(CDOID id, EStructuralFeature feature, EChangeKind changeKind, Object oldValue,
					Object newValue, int index) {
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

	public void addListener(IChangeListener listener) {
		changeListeners.add(listener);
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

}

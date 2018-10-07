package com.specmate.persistency.cdo.internal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.CDOObjectHistory;
import org.eclipse.emf.cdo.common.commit.CDOChangeSetData;
import org.eclipse.emf.cdo.common.commit.CDOCommitInfo;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import com.specmate.common.SpecmateException;
import com.specmate.model.base.BasePackage;
import com.specmate.model.base.INamed;
import com.specmate.model.history.Change;
import com.specmate.model.history.History;
import com.specmate.model.history.HistoryEntry;
import com.specmate.model.history.HistoryFactory;
import com.specmate.model.requirements.RequirementsPackage;
import com.specmate.persistency.IHistoryProvider;
import com.specmate.persistency.IPersistencyService;
import com.specmate.persistency.ITransaction;
import com.specmate.persistency.event.EChangeKind;

@Component(immediate = true)
public class HistoryProviderImpl implements IHistoryProvider {
	private IPersistencyService persistency;
	private LogService logService;

	@Override
	public History getHistory(EObject object) throws SpecmateException {
		CDOObject cdoObject = (CDOObject) object;
		History history = HistoryFactory.eINSTANCE.createHistory();
		return processHistory(cdoObject, history);
	}

	@Override
	public History getContainerHistory(EObject object) throws SpecmateException {
		History history = getHistory(object);

		List<EObject> contents = object.eContents();
		for (EObject content : contents) {
			CDOObject cdoObject = (CDOObject) content;
			processHistory(cdoObject, history);
		}

		sortHistory(history, false);

		return history;
	}

	@Override
	public History getRecursiveHistory(EObject object) throws SpecmateException {
		History history = getHistory(object);

		// Get all contents recursively
		// Note: this retrieves only existing elements, deleted elements are NOT
		// retrieved
		// As a consequence, we don't find any detached objects when traversing the
		// tree.
		// The current solution is to check changed elements if the change kind is
		// "REMOVED".
		// This will however also mark moved elements as deleted.

		TreeIterator<EObject> it = object.eAllContents();
		while (it.hasNext()) {
			CDOObject cdoObject = (CDOObject) it.next();
			processHistory(cdoObject, history);
		}

		sortHistory(history, false);

		return history;
	}

	private void sortHistory(History history, boolean ascending) {
		ECollections.sort(history.getEntries(), new Comparator<HistoryEntry>() {
			@Override
			public int compare(HistoryEntry o1, HistoryEntry o2) {
				if (ascending) {
					return (int) ((int) o1.getTimestamp() - o2.getTimestamp());
				}

				return (int) (o2.getTimestamp() - o1.getTimestamp());
			}
		});
	}

	private History processHistory(CDOObject cdoObject, History history) {
		CDOCommitInfo[] cdoHistoryElements = getCDOHistoryElements(cdoObject);
		for (int i = 0; i < cdoHistoryElements.length; i++) {
			CDOCommitInfo cdoHistoryElement = cdoHistoryElements[i];
			HistoryEntry historyEntry = HistoryFactory.eINSTANCE.createHistoryEntry();

			fillHistoryEntry(cdoObject, cdoHistoryElement, historyEntry);
			if (!historyEntry.getChanges().isEmpty() || !historyEntry.getDeletedObjects().isEmpty()) {
				history.getEntries().add(historyEntry);
			}

		}
		return history;
	}

	private void fillHistoryEntry(CDOObject cdoObject, CDOCommitInfo cdoHistoryElement, HistoryEntry historyEntry) {
		HistoryDeltaProcessor deltaProcessor = new HistoryDeltaProcessor(cdoHistoryElement, cdoObject.cdoID());
		deltaProcessor.process();
		historyEntry.getChanges().addAll(deltaProcessor.getChanges());
		historyEntry.setTimestamp(cdoHistoryElement.getTimeStamp());
		extractCommentInfo(cdoHistoryElement, historyEntry);

	}

	private void extractCommentInfo(CDOCommitInfo cdoHistoryElement, HistoryEntry historyEntry) {
		String comment = cdoHistoryElement.getComment();
		if (comment == null || comment.length() == 0) {
			return;
		}

		String[] info = comment.split(ITransaction.COMMENT_FIELD_SEPARATOR);
		if (info.length == 0) {
			return;
		}

		historyEntry.setUser(info[0]);

		if (info.length == 2) {
			String[] deletedObjects = info[1].split(ITransaction.COMMENT_DATA_SEPARATOR);
			for (int i = 0; i < deletedObjects.length; i++) {
				historyEntry.getDeletedObjects().add(deletedObjects[i]);
			}
		}

		if (info.length == 3) {
			historyEntry.setComment(info[2]);
		}
	}

	private CDOCommitInfo[] getCDOHistoryElements(CDOObject cdoObject) {
		CDOObjectHistory cdoHistory = cdoObject.cdoHistory();
		CDOCommitInfo[] cdoHistoryElements = cdoHistory.getElements();
		// CDO loads the history asynchronously, hence if the history is
		// initialized it might be empty. An empty history is a hint that the
		// history is not loaded. We wait until the history is loaded.
		while (cdoHistoryElements.length == 0) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// ignore
			}
			cdoHistoryElements = cdoHistory.getElements();
		}
		return cdoHistoryElements;
	}

	private class HistoryDeltaProcessor extends DeltaProcessor {

		List<Change> changes = new ArrayList<>();
		private CDOID cdoId;

		public HistoryDeltaProcessor(CDOChangeSetData data, CDOID id) {
			super(data);
			this.cdoId = id;
		}

		public List<Change> getChanges() {
			return this.changes;
		}

		@Override
		protected void changedObject(CDOID id, EStructuralFeature feature, EChangeKind changeKind, Object oldValue,
				Object newValue, int index) {
			if (!id.equals(this.cdoId)) {
				return;
			}

			if (showChange(feature, changeKind)) {
				String objectName = getObjectName(id);
				if (objectName != null) {
					Change change = HistoryFactory.eINSTANCE.createChange();

					if (newValue != null) {
						change.setNewValue(newValue.toString());
					}
					if (oldValue != null) {
						// For some reason, when this is retrieved by CDO, the object has no type
						// information and lands here as a plain object which we cannot read as a
						// string. Hence, for now, the clients should ignore these values.
						change.setOldValue(oldValue.toString());
					}

					change.setFeature(feature.getName());
					change.setObjectName(objectName);
					changes.add(change);
				}
			}
		}

		@Override
		protected void newObject(CDOID id, String className, Map<EStructuralFeature, Object> featureMap) {
			if (!id.equals(this.cdoId)) {
				return;
			}

			featureMap.forEach((k, v) -> {
				// For now, we are only interested in seeing the new objects' name in the
				// history
				if (k.getName().equals(BasePackage.Literals.INAMED__NAME.getName())) {
					Change change = HistoryFactory.eINSTANCE.createChange();
					change.setIsCreate(true);
					change.setFeature(k.getName());
					change.setNewValue((String) v);
					change.setObjectName((String) v);
					changes.add(change);
				}
			});
		}

		@Override
		protected void detachedObject(CDOID id, int version) {
			// Information about deleted object is stored in transaction commits
		}

		private boolean showChange(EStructuralFeature feature, EChangeKind changeKind) {
			String featureName = feature.getName();
			// For now, we are only interested in seeing changes in object names,
			// descriptions, variables and conditions
			return changeKind.equals(EChangeKind.SET)
					&& (featureName.equals(BasePackage.Literals.INAMED__NAME.getName())
							|| featureName.equals(BasePackage.Literals.IDESCRIBED__DESCRIPTION.getName())
							|| featureName.equals(RequirementsPackage.Literals.CEG_NODE__VARIABLE.getName())
							|| featureName.equals(RequirementsPackage.Literals.CEG_NODE__CONDITION.getName()));
		}

		private String getObjectName(CDOID id) {
			ITransaction transaction;
			String objectName = null;

			try {
				transaction = persistency.openTransaction();
				CDOObject obj = ((TransactionImpl) transaction).getInternalTransaction().getObject(id);
				if (obj != null && obj instanceof INamed) {
					objectName = ((INamed) obj).getName();
				}
			} catch (SpecmateException e) {
				logService.log(LogService.LOG_ERROR, "Could not create change object for " + id.toString(), e);
			}

			return objectName;
		}

	}

	@Reference
	public void setPersistencyService(IPersistencyService persistency) {
		this.persistency = persistency;
	}

	@Reference
	public void setLogService(LogService logService) {
		this.logService = logService;
	}
}

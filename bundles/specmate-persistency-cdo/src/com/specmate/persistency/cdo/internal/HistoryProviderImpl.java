package com.specmate.persistency.cdo.internal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
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
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.osgi.service.component.annotations.Component;

import com.specmate.common.SpecmateException;
import com.specmate.model.history.Change;
import com.specmate.model.history.History;
import com.specmate.model.history.HistoryEntry;
import com.specmate.model.history.HistoryFactory;
import com.specmate.persistency.IHistoryProvider;
import com.specmate.persistency.event.EChangeKind;

@Component(immediate = true)
public class HistoryProviderImpl implements IHistoryProvider {

	@Override
	public History getHistory(EObject object) throws SpecmateException {
		CDOObject cdoObject = (CDOObject) object;
		History history = HistoryFactory.eINSTANCE.createHistory();
		return processHistory(cdoObject, history);
	}

	@Override
	public History getHistoryRecursive(EObject object) throws SpecmateException {
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
			history = processHistory(cdoObject, history);
		}

		// sort by date (new to old)
		ECollections.sort(history.getEntries(), new Comparator<HistoryEntry>() {
			@Override
			public int compare(HistoryEntry o1, HistoryEntry o2) {
				return o2.getDate().compareTo(o1.getDate());
			}
		});

		return history;
	}

	private History processHistory(CDOObject cdoObject, History history) {
		CDOCommitInfo[] cdoHistoryElements = getCDOHistoryElements(cdoObject);
		for (int i = 0; i < cdoHistoryElements.length; i++) {
			CDOCommitInfo cdoHistoryElement = cdoHistoryElements[i];
			HistoryEntry historyEntry = HistoryFactory.eINSTANCE.createHistoryEntry();

			fillHistoryEntry(cdoObject, cdoHistoryElement, historyEntry);
			if (!historyEntry.getChanges().isEmpty()) {
				history.getEntries().add(historyEntry);
			}
		}
		return history;
	}

	private void fillHistoryEntry(CDOObject cdoObject, CDOCommitInfo cdoHistoryElement, HistoryEntry historyEntry) {
		HistoryDeltaProcessor deltaProcessor = new HistoryDeltaProcessor(cdoHistoryElement, cdoObject.cdoID());
		deltaProcessor.process();
		historyEntry.getChanges().addAll(deltaProcessor.getChanges());
		historyEntry.setDate(new Date(cdoHistoryElement.getTimeStamp()));
		extractUserInfo(cdoHistoryElement, historyEntry);

	}

	private void extractUserInfo(CDOCommitInfo cdoHistoryElement, HistoryEntry historyEntry) {
		String comment = cdoHistoryElement.getComment();
		if (comment == null || comment.length() == 0) {
			return;
		}

		String[] info = comment.split(";", 2);
		if (info.length == 0) {
			return;
		}

		historyEntry.setUser(info[0]);

		if (info.length == 2) {
			historyEntry.setComment(info[1]);
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

			Change change = HistoryFactory.eINSTANCE.createChange();
			change.setFeature(feature.getName());

			if (feature instanceof EReference) {
				EReference ref = (EReference) feature;
				if (ref.isContainment() && changeKind.equals(EChangeKind.REMOVE)) {
					change.setIsDelete(true);
					change.setNewValue(oldValue.toString());
				} else if (ref.isContainment() && changeKind.equals(EChangeKind.ADD)) {
					change.setIsCreate(true);
					change.setNewValue(newValue.toString());
				} else {
					return;
				}
			}

			if (newValue != null) {
				change.setNewValue(newValue.toString());
			}

			changes.add(change);
		}

		@Override
		protected void newObject(CDOID id, String className, Map<EStructuralFeature, Object> featureMap) {
			if (!id.equals(this.cdoId)) {
				return;
			}

			featureMap.forEach((k, v) -> {
				// we just create a new change if we also have something to display, i.e. value
				if (v != null && v instanceof String) {
					Change change = HistoryFactory.eINSTANCE.createChange();
					change.setIsCreate(true);
					change.setFeature(k.getName());
					change.setNewValue((String) v);
					changes.add(change);
				}
			});
		}

		@Override
		protected void detachedObject(CDOID id, int version) {
			if (!id.equals(this.cdoId)) {
				return;
			}
			Change change = HistoryFactory.eINSTANCE.createChange();
			change.setIsDelete(true);
			changes.add(change);
		}

	}
}

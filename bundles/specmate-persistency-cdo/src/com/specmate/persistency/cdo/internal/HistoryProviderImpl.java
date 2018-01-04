package com.specmate.persistency.cdo.internal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.CDOObjectHistory;
import org.eclipse.emf.cdo.common.commit.CDOChangeSetData;
import org.eclipse.emf.cdo.common.commit.CDOCommitInfo;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.ecore.EObject;
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
		History history = HistoryFactory.eINSTANCE.createHistory();
		CDOObject cdoObject = (CDOObject) object;
		CDOObjectHistory cdoHistory = cdoObject.cdoHistory();
		CDOCommitInfo[] cdoHistoryElements = cdoHistory.getElements();
		for (int i = 0; i < cdoHistoryElements.length; i++) {
			CDOCommitInfo cdoHistoryElement = cdoHistoryElements[i];
			HistoryEntry historyEntry = HistoryFactory.eINSTANCE.createHistoryEntry();
			HistoryDeltaProcessor deltaProcessor = new HistoryDeltaProcessor(cdoHistoryElement, cdoObject.cdoID());
			deltaProcessor.process();
			historyEntry.getChanges().addAll(deltaProcessor.getChanges());
			historyEntry.setDate(new Date(cdoHistoryElement.getTimeStamp()));
			history.getEntries().add(historyEntry);
		}
		return history;
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
			change.setValue(newValue.toString());
			changes.add(change);
		}

		@Override
		protected void newObject(CDOID id) {
			if (!id.equals(this.cdoId)) {
				return;
			}
			Change change = HistoryFactory.eINSTANCE.createChange();
			change.setIsCreate(true);
			changes.add(change);
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

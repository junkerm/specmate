package com.specmate.persistency.cdo.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.cdo.common.commit.CDOChangeSetData;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.revision.CDOIDAndVersion;
import org.eclipse.emf.cdo.common.revision.CDORevisionKey;
import org.eclipse.emf.cdo.common.revision.delta.CDOAddFeatureDelta;
import org.eclipse.emf.cdo.common.revision.delta.CDOClearFeatureDelta;
import org.eclipse.emf.cdo.common.revision.delta.CDOFeatureDelta;
import org.eclipse.emf.cdo.common.revision.delta.CDOFeatureDelta.Type;
import org.eclipse.emf.cdo.common.revision.delta.CDOListFeatureDelta;
import org.eclipse.emf.cdo.common.revision.delta.CDORemoveFeatureDelta;
import org.eclipse.emf.cdo.common.revision.delta.CDORevisionDelta;
import org.eclipse.emf.cdo.common.revision.delta.CDOSetFeatureDelta;
import org.eclipse.emf.cdo.spi.common.revision.InternalCDORevision;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.specmate.common.SpecmateValidationException;
import com.specmate.persistency.event.EChangeKind;

public abstract class DeltaProcessor {

	private CDOChangeSetData data;

	public DeltaProcessor(CDOChangeSetData data) {
		super();
		this.data = data;
	}

	public void process() throws SpecmateValidationException {
		for (CDOIDAndVersion key : data.getNewObjects()) {
			if (key instanceof InternalCDORevision) {
				InternalCDORevision revision = (InternalCDORevision) key;
				Map<EStructuralFeature, Object> featureMap = new HashMap<>();
				for (EStructuralFeature feature : revision.getClassInfo().getAllPersistentFeatures()) {
					featureMap.put(feature, revision.getValue(feature));
				}
				newObject(key.getID(), revision.getEClass().getName(), featureMap);
			}
		}
		for (CDOIDAndVersion id : data.getDetachedObjects()) {
			detachedObject(id.getID(), id.getVersion());
		}
		for (CDORevisionKey key : data.getChangedObjects()) {
			if (key instanceof CDORevisionDelta) {
				CDORevisionDelta delta = (CDORevisionDelta) key;
				for (CDOFeatureDelta fDelta : delta.getFeatureDeltas()) {
					processDelta(delta.getID(), fDelta, delta.getEClass().getName());
				}
			}
		}
	}

	private void processDelta(CDOID id, CDOFeatureDelta delta, String objectClassName)
			throws SpecmateValidationException {
		if (delta.getType().equals(Type.LIST)) {
			CDOListFeatureDelta listDelta = (CDOListFeatureDelta) delta;
			ArrayList<CDOFeatureDelta> deltas = new ArrayList<>(listDelta.getListChanges());
			for (CDOFeatureDelta nestedDelta : deltas) {
				processDelta(id, nestedDelta, objectClassName);
			}
			return;
		}

		processBasicDelta(id, delta, objectClassName);
	}

	private void processBasicDelta(CDOID id, CDOFeatureDelta delta, String objectClassName)
			throws SpecmateValidationException {
		switch (delta.getType()) {
		case SET:
			CDOSetFeatureDelta setDelta = (CDOSetFeatureDelta) delta;
			changedObject(id, setDelta.getFeature(), EChangeKind.SET, setDelta.getOldValue(), setDelta.getValue(),
					setDelta.getIndex(), objectClassName);
			break;
		case ADD:
			CDOAddFeatureDelta addDelta = (CDOAddFeatureDelta) delta;
			changedObject(id, addDelta.getFeature(), EChangeKind.ADD, null, addDelta.getValue(), addDelta.getIndex(),
					objectClassName);
			break;

		case REMOVE:
			CDORemoveFeatureDelta removeDelta = (CDORemoveFeatureDelta) delta;
			changedObject(id, removeDelta.getFeature(), EChangeKind.REMOVE, removeDelta.getValue(), null,
					removeDelta.getIndex(), objectClassName);
			break;

		case CLEAR:
			CDOClearFeatureDelta clearDelta = (CDOClearFeatureDelta) delta;
			changedObject(id, clearDelta.getFeature(), EChangeKind.CLEAR, null, null, 0, objectClassName);
			break;
		}
	}

	protected abstract void changedObject(CDOID id, EStructuralFeature feature, EChangeKind changeKind, Object oldValue,
			Object newValue, int index, String objectClassName) throws SpecmateValidationException;

	protected abstract void newObject(CDOID id, String className, Map<EStructuralFeature, Object> featureMap)
			throws SpecmateValidationException;

	protected abstract void detachedObject(CDOID id, int version) throws SpecmateValidationException;

}

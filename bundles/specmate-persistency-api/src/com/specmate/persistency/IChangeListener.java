package com.specmate.persistency;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.specmate.common.SpecmateValidationException;
import com.specmate.persistency.event.EChangeKind;

public interface IChangeListener {

	void changedObject(EObject object, EStructuralFeature feature, EChangeKind changeKind, Object oldValue,
			Object newValue) throws SpecmateValidationException;

	void removedObject(EObject object) throws SpecmateValidationException;

	void newObject(EObject object, String id, String className, Map<EStructuralFeature, Object> featureMap)
			throws SpecmateValidationException;

}

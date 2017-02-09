package com.specmate.persistency;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.specmate.persistency.event.EChangeKind;

public interface IChangeListener {

	void newObject(EObject object);

	void changedObject(EObject object, EStructuralFeature feature, EChangeKind changeKind, Object oldValue,
			Object newValue);

	void removedObject(EObject object);
	
}

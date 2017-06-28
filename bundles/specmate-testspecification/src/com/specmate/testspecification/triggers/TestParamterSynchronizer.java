package com.specmate.testspecification.triggers;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.specmate.model.testspecification.TestParameter;
import com.specmate.persistency.IChangeListener;
import com.specmate.persistency.event.EChangeKind;

public class TestParamterSynchronizer implements IChangeListener {

	@Override
	public void newObject(EObject object) {
		if (object instanceof TestParameter) {
			TestParameter parameter = (TestParameter) object;
		}
	}

	@Override
	public void changedObject(EObject object, EStructuralFeature feature, EChangeKind changeKind, Object oldValue,
			Object newValue) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removedObject(EObject object) {
		// TODO Auto-generated method stub

	}

}

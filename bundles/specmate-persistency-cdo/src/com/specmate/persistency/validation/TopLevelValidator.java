package com.specmate.persistency.validation;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.specmate.common.SpecmateValidationException;
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.persistency.event.EChangeKind;

public class TopLevelValidator extends BaseValidator {

	@Override
	public void changedObject(EObject object, EStructuralFeature feature, EChangeKind changeKind, Object oldValue,
			Object newValue, String objectClassName) throws SpecmateValidationException {
		validateNotTopLevel(object);
	}

	@Override
	public void removedObject(EObject object) throws SpecmateValidationException {
		validateNotTopLevel(object);
	}

	@Override
	public void newObject(EObject object, String id, String className, Map<EStructuralFeature, Object> featureMap)
			throws SpecmateValidationException {
		validateNotTopLevel(object);
	}

	private void validateNotTopLevel(EObject object) throws SpecmateValidationException {
		EObject parent = object.eContainer();
		if (parent == null || SpecmateEcoreUtil.isProject(parent)) {
			throw new SpecmateValidationException(SpecmateEcoreUtil.getName(object) + " is at top-level.",
					getValidatorName(), getObjectName(object));
		}
	}
}

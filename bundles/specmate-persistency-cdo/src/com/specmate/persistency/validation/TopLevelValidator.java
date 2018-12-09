package com.specmate.persistency.validation;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.specmate.common.SpecmateValidationException;
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.persistency.event.EChangeKind;

public class TopLevelValidator extends ValidatorBase {

	@Override
	public void changedObject(EObject object, EStructuralFeature feature, EChangeKind changeKind, Object oldValue,
			Object newValue, String objectClassName) throws SpecmateValidationException {
		// we allow to add and remove objects to top level folders
		if (changeKind == EChangeKind.SET) {
			validateNotTopLevel(object);
		}
	}

	@Override
	public void removedObject(EObject object) throws SpecmateValidationException {
		/*
		 * Currently, we cannot validate that top level folders are not deleted. When an
		 * object is detached, we loose all information about it's location in the
		 * model, hence we cannot traverse to the parents to see if the deleted object
		 * is at the top level. The only solution I see is to store meta-information in
		 * folder objects (e.g. whether they are project- or library-folders). This is
		 * planned to be implemented next.
		 */
		// validateNotTopLevel(object);
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

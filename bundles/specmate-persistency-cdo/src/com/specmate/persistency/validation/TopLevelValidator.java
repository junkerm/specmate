package com.specmate.persistency.validation;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.specmate.common.exception.SpecmateValidationException;
import com.specmate.model.base.Folder;
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.persistency.event.EChangeKind;

public class TopLevelValidator extends ValidatorBase {

	@Override
	public void changedObject(EObject object, EStructuralFeature feature, EChangeKind changeKind, Object oldValue,
			Object newValue, String objectClassName) throws SpecmateValidationException {
		// we allow to add and remove objects to top level folders, but not to change
		// (SET) it
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
		 * folder objects (e.g. whether they are top-level folders in a project or
		 * library folders). The latter we have implemented, hence we validate that
		 * library folders are not deleted.
		 */

		if (object instanceof Folder) {
			Folder removed = (Folder) object;
			if (removed.isLibrary()) {
				throw new SpecmateValidationException(removed.getName() + " is a library folder.", getValidatorName(),
						removed.getName());
			}
		}
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

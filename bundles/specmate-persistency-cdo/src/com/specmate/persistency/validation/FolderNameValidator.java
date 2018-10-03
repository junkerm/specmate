package com.specmate.persistency.validation;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.specmate.common.SpecmateValidationException;
import com.specmate.model.base.BasePackage;
import com.specmate.model.base.Folder;
import com.specmate.persistency.IChangeListener;
import com.specmate.persistency.IValidator;
import com.specmate.persistency.event.EChangeKind;

public class FolderNameValidator implements IChangeListener, IValidator {

	@Override
	public void changedObject(EObject object, EStructuralFeature feature, EChangeKind changeKind, Object oldValue,
			Object newValue) throws SpecmateValidationException {

		if (object instanceof Folder) {
			validateFolderName(newValue);
		}
	}

	@Override
	public void removedObject(EObject object) throws SpecmateValidationException {
		// No validation required
	}

	@Override
	public void newObject(EObject object, String id, String className, Map<EStructuralFeature, Object> featureMap)
			throws SpecmateValidationException {

		if (object instanceof Folder) {
			validateFolderName(featureMap.get(BasePackage.Literals.INAMED__NAME));
		}
	}

	private void validateFolderName(Object fn) throws SpecmateValidationException {
		if (fn == null) {
			throw new SpecmateValidationException("Folder name is undefined");
		}

		if (!(fn instanceof String)) {
			throw new SpecmateValidationException("Folder name is not a string");
		}

		String folderName = (String) fn;

		if (folderName.trim().length() == 0) {
			throw new SpecmateValidationException("Folder name is empty");
		}
	}
}

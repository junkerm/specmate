package com.specmate.persistency.validation;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.specmate.common.SpecmateValidationException;
import com.specmate.model.base.BasePackage;
import com.specmate.model.base.INamed;
import com.specmate.persistency.IChangeListener;
import com.specmate.persistency.IValidator;
import com.specmate.persistency.event.EChangeKind;

public class NameValidator implements IChangeListener, IValidator {
	/** Pattern that describes invalid object names */
	private static Pattern inValidNameChars = Pattern.compile("[,;|]");

	@Override
	public void changedObject(EObject object, EStructuralFeature feature, EChangeKind changeKind, Object oldValue,
			Object newValue) throws SpecmateValidationException {

		if (object instanceof INamed) {
			validateName(newValue);
		}
	}

	@Override
	public void removedObject(EObject object) throws SpecmateValidationException {
		// No validation required
	}

	@Override
	public void newObject(EObject object, String id, String className, Map<EStructuralFeature, Object> featureMap)
			throws SpecmateValidationException {

		if (object instanceof INamed) {
			validateName(featureMap.get(BasePackage.Literals.INAMED__NAME));
		}
	}

	private void validateName(Object fn) throws SpecmateValidationException {
		if (fn == null) {
			throw new SpecmateValidationException("Name is undefined");
		}

		if (!(fn instanceof String)) {
			throw new SpecmateValidationException("Name is not a string");
		}

		String folderName = (String) fn;

		if (folderName.trim().length() == 0) {
			throw new SpecmateValidationException("Name is empty");
		}

		Matcher m = inValidNameChars.matcher(folderName);
		if (m.find()) {
			throw new SpecmateValidationException(
					"Name contains an invalid character: " + folderName.charAt(m.start()));
		}
	}
}

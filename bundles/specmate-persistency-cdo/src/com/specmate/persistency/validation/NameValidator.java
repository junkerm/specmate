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
import com.specmate.persistency.event.EChangeKind;

public class NameValidator implements IChangeListener {
	/** Pattern that describes invalid object names */
	private static Pattern inValidNameChars = Pattern.compile("[,;|]");

	@Override
	public void changedObject(EObject object, EStructuralFeature feature, EChangeKind changeKind, Object oldValue,
			Object newValue) throws SpecmateValidationException {

		if (object instanceof INamed && feature.equals(BasePackage.Literals.INAMED__NAME)) {
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

	private void validateName(Object obj) throws SpecmateValidationException {
		if (obj == null) {
			throw new SpecmateValidationException("Name is undefined");
		}

		if (!(obj instanceof String)) {
			throw new SpecmateValidationException("Name is not a string");
		}

		String name = (String) obj;

		if (name.trim().length() == 0) {
			throw new SpecmateValidationException("Name is empty");
		}

		Matcher m = inValidNameChars.matcher(name);
		if (m.find()) {
			throw new SpecmateValidationException("Name contains an invalid character: " + name.charAt(m.start()));
		}
	}
}

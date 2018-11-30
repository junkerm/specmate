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

public class NameValidator extends ValidatorBase implements IChangeListener {
	/**
	 * Pattern that describes invalid object names We use these characters in
	 * transaction commits for field and data separators
	 **/
	private static Pattern inValidNameChars = Pattern.compile("[,;|]");

	@Override
	public void changedObject(EObject object, EStructuralFeature feature, EChangeKind changeKind, Object oldValue,
			Object newValue, String objectClassName) throws SpecmateValidationException {

		if (object instanceof INamed && feature.equals(BasePackage.Literals.INAMED__NAME)) {
			validateName(newValue, object);
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
			validateName(featureMap.get(BasePackage.Literals.INAMED__NAME), object);
		}
	}

	private void validateName(Object objName, EObject obj) throws SpecmateValidationException {
		if (objName == null) {
			throw new SpecmateValidationException("Name is undefined.", getValidatorName(), null);
		}

		if (!(objName instanceof String)) {
			throw new SpecmateValidationException("Name is not a string.", getValidatorName(), null);
		}

		String name = (String) objName;

		if (name.trim().length() == 0) {
			throw new SpecmateValidationException("Name is empty.", getValidatorName(), null);
		}

		Matcher m = inValidNameChars.matcher(name);
		if (m.find()) {
			throw new SpecmateValidationException("Name contains an invalid character: " + name.charAt(m.start()),
					getValidatorName(), getObjectName(obj));
		}
	}
}

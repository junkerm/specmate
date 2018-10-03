package com.specmate.persistency.validation;

import java.util.Map;
import java.util.regex.Pattern;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.specmate.common.SpecmateValidationException;
import com.specmate.model.base.BasePackage;
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.persistency.IChangeListener;
import com.specmate.persistency.IValidator;
import com.specmate.persistency.event.EChangeKind;

public class IDValidator implements IChangeListener, IValidator {
	/** Pattern that describes valid object ids */
	private static Pattern idPattern = Pattern.compile("[a-zA-Z_0-9\\-]+");

	@Override
	public void changedObject(EObject object, EStructuralFeature feature, EChangeKind changeKind, Object oldValue,
			Object newValue) throws SpecmateValidationException {

		if (feature.getName().equals(BasePackage.Literals.IID__ID.getName())) {
			String objectID = validateID(newValue);
			validateUniqueID(objectID, object);
		}
	}

	@Override
	public void removedObject(EObject object) throws SpecmateValidationException {
		// No validation required
	}

	@Override
	public void newObject(EObject object, String id, String className, Map<EStructuralFeature, Object> featureMap)
			throws SpecmateValidationException {

		String objectID = validateID(featureMap.get(BasePackage.Literals.IID__ID));
		validateUniqueID(objectID, object);
	}

	private String validateID(Object obj) throws SpecmateValidationException {
		if (obj == null) {
			throw new SpecmateValidationException("Object does not have a valid Id");
		}

		String id = (String) obj;
		if (!idPattern.matcher(id).matches()) {
			throw new SpecmateValidationException("Object id may only contain letters, digits, '_' and '-'");
		}

		return id;
	}

	private void validateUniqueID(String id, EObject object) throws SpecmateValidationException {
		EObject parent = object.eContainer();

		if (parent != null && SpecmateEcoreUtil.getEObjectWithId(id, parent.eContents()) != null) {
			throw new SpecmateValidationException("Duplicate id:" + id);
		}
	}

}

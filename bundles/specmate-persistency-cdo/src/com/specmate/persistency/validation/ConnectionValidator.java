package com.specmate.persistency.validation;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.specmate.common.exception.SpecmateValidationException;
import com.specmate.model.base.IModelConnection;
import com.specmate.persistency.event.EChangeKind;

public class ConnectionValidator extends ValidatorBase {

	@Override
	public void changedObject(EObject object, EStructuralFeature feature, EChangeKind changeKind, Object oldValue,
			Object newValue, String objectClassName) throws SpecmateValidationException {

		checkSourceTarget(object);
	}

	@Override
	public void removedObject(EObject object) throws SpecmateValidationException {
		// Nothing to check
	}

	@Override
	public void newObject(EObject object, String id, String className, Map<EStructuralFeature, Object> featureMap)
			throws SpecmateValidationException {
		checkSourceTarget(object);
	}

	private void checkSourceTarget(EObject object) throws SpecmateValidationException {
		if (object instanceof IModelConnection) {
			IModelConnection connection = (IModelConnection) object;
			if (connection.getSource() == null) {
				throw new SpecmateValidationException(
						"Connection " + connection.getName() + " is not connected to a source.", getValidatorName(),
						getObjectName(object));
			}

			if (connection.getTarget() == null) {
				throw new SpecmateValidationException(
						"Connection " + connection.getName() + " is not connected to a target.", getValidatorName(),
						getObjectName(object));
			}
		}
	}

}

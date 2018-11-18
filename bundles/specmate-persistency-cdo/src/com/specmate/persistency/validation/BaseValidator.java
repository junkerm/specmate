package com.specmate.persistency.validation;

import org.eclipse.emf.ecore.EObject;

import com.specmate.model.base.INamed;
import com.specmate.persistency.IChangeListener;

public abstract class BaseValidator implements IChangeListener {

	public String getObjectName(EObject obj) {
		String name = "";

		if (obj instanceof INamed) {
			name = ((INamed) obj).getName();
		}

		return name;
	}

	public String getValidatorName() {
		return getClass().getName();
	}
}

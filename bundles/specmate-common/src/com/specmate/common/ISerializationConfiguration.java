package com.specmate.common;

import org.eclipse.emf.ecore.EObject;

public interface ISerializationConfiguration {

	boolean serializeContainedElements(EObject object);

}

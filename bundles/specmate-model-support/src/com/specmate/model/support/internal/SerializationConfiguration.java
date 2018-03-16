package com.specmate.model.support.internal;

import org.eclipse.emf.ecore.EObject;
import org.osgi.service.component.annotations.Component;

import com.specmate.common.ISerializationConfiguration;

@Component(immediate = true)
public class SerializationConfiguration implements ISerializationConfiguration {

	@Override
	public boolean serializeContainedElements(EObject object) {
		return object.eClass().getEPackage().getNsURI().endsWith("history");
	}

}

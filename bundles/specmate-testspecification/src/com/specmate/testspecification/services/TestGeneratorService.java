package com.specmate.testspecification.services;

import org.eclipse.emf.ecore.EObject;
import org.osgi.service.component.annotations.Component;

import com.specmate.common.SpecmateValidationException;
import com.specmate.emfrest.api.IRestService;
import com.specmate.emfrest.api.RestServiceBase;
import com.specmate.model.requirements.CEGModel;
import com.specmate.model.testspecification.TestSpecification;

@Component(immediate = true, service = IRestService.class)
public class TestGeneratorService extends RestServiceBase {

	@Override
	public String getServiceName() {
		return "generateTests";
	}

	@Override
	public boolean canPost(Object target, EObject object) {
		return target instanceof TestSpecification;
	}

	@Override
	public Object post(Object target, EObject object) throws SpecmateValidationException {
		TestSpecification specification = (TestSpecification) target;
		EObject container = specification.eContainer();
		if (!(container instanceof CEGModel)) {
			throw new SpecmateValidationException(
					"To generate test cases, the test specification must be associcated to a ceg model");
		}

		// TODO generate

		return null;
	}

}

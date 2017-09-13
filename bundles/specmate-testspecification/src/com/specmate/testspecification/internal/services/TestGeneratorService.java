package com.specmate.testspecification.internal.services;

import org.eclipse.emf.ecore.EObject;
import org.osgi.service.component.annotations.Component;

import com.specmate.common.SpecmateException;
import com.specmate.common.SpecmateValidationException;
import com.specmate.emfrest.api.IRestService;
import com.specmate.emfrest.api.RestServiceBase;
import com.specmate.model.requirements.CEGModel;
import com.specmate.model.testspecification.TestSpecification;

/**
 * Service for generating test cases for a test specification that is linked to
 * a CEG model.
 * 
 * @author junkerm
 */
@Component(immediate = true, service = IRestService.class)
public class TestGeneratorService extends RestServiceBase {

	/** {@inheritDoc} */
	@Override
	public String getServiceName() {
		return "generateTests";

	}

	/** {@inheritDoc} */
	@Override
	public boolean canPost(Object target, EObject object) {
		return target instanceof TestSpecification;
	}

	/** {@inheritDoc} */
	@Override
	public Object post(Object target, EObject object) throws SpecmateValidationException, SpecmateException {
		TestSpecification specification = (TestSpecification) target;
		EObject container = specification.eContainer();
		if (!(container instanceof CEGModel)) {
			throw new SpecmateValidationException(
					"To generate test cases, the test specification must be associcated to a ceg model");
		}

		new TestCaseGenerator(specification).generate();

		return null;
	}

}

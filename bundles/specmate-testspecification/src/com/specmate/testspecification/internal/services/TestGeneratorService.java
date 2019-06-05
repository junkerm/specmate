package com.specmate.testspecification.internal.services;

import javax.ws.rs.core.Response;

import org.eclipse.emf.ecore.EObject;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.specmate.common.exception.SpecmateException;
import com.specmate.common.exception.SpecmateInternalException;
import com.specmate.emfrest.api.IRestService;
import com.specmate.emfrest.api.RestServiceBase;
import com.specmate.metrics.ICounter;
import com.specmate.metrics.IMetricsService;
import com.specmate.model.administration.ErrorCode;
import com.specmate.model.processes.Process;
import com.specmate.model.requirements.CEGModel;
import com.specmate.model.testspecification.TestSpecification;
import com.specmate.rest.RestResult;

/**
 * Service for generating test cases for a test specification that is linked to
 * a CEG model.
 *
 * @author junkerm
 */
@Component(immediate = true, service = IRestService.class)
public class TestGeneratorService extends RestServiceBase {
	
	private IMetricsService metricsService;
	private ICounter testGenCounter;
	
	@Activate
	public void activate() throws SpecmateException {
		this.testGenCounter = metricsService.createCounter("test_generation_counter", "Total number of generated test specifications");
	}
	

	/** {@inheritDoc} */
	@Override
	public String getServiceName() {
		return "generateTests";

	}

	/** {@inheritDoc} */
	@Override
	public boolean canPost(Object target, Object object) {
		return target instanceof TestSpecification;
	}

	/** {@inheritDoc} */
	@Override
	public RestResult<?> post(Object target, Object object, String token) throws SpecmateException {
		TestSpecification specification = (TestSpecification) target;
		EObject container = specification.eContainer();
		if (container instanceof CEGModel) {
			new CEGTestCaseGenerator(specification).generate();
			this.testGenCounter.inc();
		} else if (container instanceof Process) {
			new ProcessTestCaseGenerator(specification).generate();
			this.testGenCounter.inc();
		} else {
			throw new SpecmateInternalException(ErrorCode.REST_SERVICE,
					"You can only generate test cases from ceg models or processes. The supplied element is of class "
							+ container.getClass().getSimpleName() + ".");
		}
		return new RestResult<>(Response.Status.NO_CONTENT);
	}
	
	@Reference
	public void setMetricsService(IMetricsService metricsService) {
		this.metricsService = metricsService;
	}

}

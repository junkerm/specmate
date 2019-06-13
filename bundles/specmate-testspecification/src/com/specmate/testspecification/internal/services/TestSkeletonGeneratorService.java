package com.specmate.testspecification.internal.services;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.specmate.common.exception.SpecmateException;
import com.specmate.common.exception.SpecmateValidationException;
import com.specmate.emfrest.api.IRestService;
import com.specmate.emfrest.api.RestServiceBase;
import com.specmate.metrics.ICounter;
import com.specmate.metrics.IMetricsService;
import com.specmate.model.testspecification.TestSpecification;
import com.specmate.model.testspecification.TestSpecificationSkeleton;
import com.specmate.rest.RestResult;
import com.specmate.testspecification.internal.testskeleton.BaseSkeleton;
import com.specmate.testspecification.internal.testskeleton.CSVTestSpecificationSkeleton;
import com.specmate.testspecification.internal.testskeleton.JavaTestSpecificationSkeleton;
import com.specmate.testspecification.internal.testskeleton.JavascriptTestSpecificationSkeleton;

@Component(immediate = true, service = IRestService.class)
public class TestSkeletonGeneratorService extends RestServiceBase {
	private final String LPARAM = "language";
	private final String JAVA = "java";
	private final String JAVASCRIPT = "javascript";
	private final String CSV = "csv";
	private Map<String, BaseSkeleton> skeletonGenerators;
	private IMetricsService metricsService;
	private ICounter exportCounter;

	@Activate
	public void activate() throws SpecmateException {
		this.skeletonGenerators = new HashMap<>();
		this.skeletonGenerators.put(this.JAVA, new JavaTestSpecificationSkeleton(this.JAVA));
		this.skeletonGenerators.put(this.JAVASCRIPT, new JavascriptTestSpecificationSkeleton(this.JAVASCRIPT));
		this.skeletonGenerators.put(this.CSV, new CSVTestSpecificationSkeleton(this.CSV));
		this.exportCounter = metricsService.createCounter("export_counter", "Total number of exported test specifications");
	}

	@Override
	public String getServiceName() {
		return "generateTestSkeleton";
	}

	@Override
	public boolean canGet(Object object) {
		return object instanceof TestSpecification;
	}

	@Override
	public RestResult<?> get(Object object, MultivaluedMap<String, String> queryParams, String token)
			throws SpecmateException {

		String language = queryParams.getFirst(this.LPARAM);
		if (language == null) {
			throw new SpecmateValidationException("Language for test skeleton not specified.");
		}

		BaseSkeleton generator = this.skeletonGenerators.get(language);
		if (generator == null) {
			throw new SpecmateValidationException("Generator for langauge " + language + " does not exist.");
		}

		if (!(object instanceof TestSpecification)) {
			throw new SpecmateValidationException(
					"Cannot generate test skeleton for object of type " + object.getClass().getName());
		}

		TestSpecification ts = (TestSpecification) object;
		
		this.exportCounter.inc();

		return new RestResult<TestSpecificationSkeleton>(Response.Status.OK, generator.generate(ts));
	}
	
	@Reference
	public void setMetricsService(IMetricsService metricsService) {
		this.metricsService = metricsService;
	}
}

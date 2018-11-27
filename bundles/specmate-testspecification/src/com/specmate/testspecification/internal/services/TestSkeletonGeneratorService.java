package com.specmate.testspecification.internal.services;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

import com.specmate.common.SpecmateException;
import com.specmate.emfrest.api.IRestService;
import com.specmate.emfrest.api.RestServiceBase;
import com.specmate.model.testspecification.TestSpecification;
import com.specmate.model.testspecification.TestSpecificationSkeleton;
import com.specmate.rest.RestResult;
import com.specmate.testspecification.internal.testskeleton.BaseSkeleton;
import com.specmate.testspecification.internal.testskeleton.JavaTestSpecificationSkeleton;
import com.specmate.testspecification.internal.testskeleton.JavascriptTestSpecificationSkeleton;

@Component(immediate = true, service = IRestService.class)
public class TestSkeletonGeneratorService extends RestServiceBase {
	private final String LPARAM = "language";
	private final String JAVA = "java";
	private final String JAVASCRIPT = "javascript";
	private Map<String, BaseSkeleton> skeletonGenerators;

	@Activate
	public void activate() {
		skeletonGenerators = new HashMap<>();
		skeletonGenerators.put(JAVA, new JavaTestSpecificationSkeleton(JAVA));
		skeletonGenerators.put(JAVASCRIPT, new JavascriptTestSpecificationSkeleton(JAVASCRIPT));
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

		String language = queryParams.getFirst(LPARAM);
		if (language == null) {
			return new RestResult<>(Response.Status.BAD_REQUEST);
		}

		BaseSkeleton generator = skeletonGenerators.get(language);
		if (generator == null) {
			return new RestResult<>(Response.Status.BAD_REQUEST);
		}

		if (!(object instanceof TestSpecification)) {
			return new RestResult<>(Response.Status.BAD_REQUEST);
		}

		TestSpecification ts = (TestSpecification) object;

		return new RestResult<TestSpecificationSkeleton>(Response.Status.OK, generator.generate(ts));
	}

}

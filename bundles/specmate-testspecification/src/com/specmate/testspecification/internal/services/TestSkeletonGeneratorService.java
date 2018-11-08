package com.specmate.testspecification.internal.services;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;

import com.specmate.common.SpecmateException;
import com.specmate.emfrest.api.IRestService;
import com.specmate.emfrest.api.RestServiceBase;
import com.specmate.model.testspecification.TestSpecification;
import com.specmate.rest.RestResult;

@Component(immediate = true, service = IRestService.class)
public class TestSkeletonGeneratorService extends RestServiceBase {
	private final String LPARAM = "language";
	private final String JAVA = "java";
	private final String JAVASCRIPT = "javascript";
	private final String TYPESCRIPT = "typescript";

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

		Response response;

		if (language.equals(JAVA)) {
			response = Response.ok().entity("{\"lang\": \"java\"}").build();
		} else if (language.equals(JAVASCRIPT)) {
			response = Response.ok().entity("{\"lang\": \"js\"}").build();
		} else if (language.equals(TYPESCRIPT)) {
			response = Response.ok().entity("{\"lang\": \"ts\"}").build();
		} else {
			return new RestResult<>(Response.Status.BAD_REQUEST);
		}

		return new RestResult<String>(response);
	}

}

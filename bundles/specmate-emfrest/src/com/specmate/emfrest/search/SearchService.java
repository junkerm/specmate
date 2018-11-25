package com.specmate.emfrest.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.eclipse.emf.ecore.EObject;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import com.specmate.common.exception.SpecmateException;
import com.specmate.common.exception.SpecmateValidationException;
import com.specmate.emfrest.api.IRestService;
import com.specmate.emfrest.api.RestServiceBase;
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.rest.RestResult;
import com.specmate.search.api.IModelSearchService;

@Component(immediate = true, service = IRestService.class)
public class SearchService extends RestServiceBase {

	private LogService logService;
	private IModelSearchService searchService;

	@Activate
	public void activate(Map<String, Object> properties) {
		this.logService.log(LogService.LOG_INFO, "Initialized search service " + properties.toString());
	}

	@Override
	public String getServiceName() {
		return "search";
	}

	@Override
	public boolean canGet(Object target) {
		return (target instanceof EObject) && SpecmateEcoreUtil.isProject((EObject) target);
	}

	@Override
	public RestResult<?> get(Object target, MultivaluedMap<String, String> queryParams, String token)
			throws SpecmateException {
		String queryString = queryParams.getFirst("query");
		if (queryString == null) {
			throw new SpecmateValidationException("Missing parameter: query");
		}
		Set<EObject> searchResult;
		try {
			String project = SpecmateEcoreUtil.getProjectId((EObject) target);
			searchResult = this.searchService.search(queryString, project);
		} catch (SpecmateValidationException e) {
			// Act robust against wrong query syntax
			return new RestResult<>(Response.Status.OK, Collections.emptyList());
		}
		return new RestResult<>(Response.Status.OK, new ArrayList<>(searchResult));
	}

	@Reference
	public void setLogService(LogService logService) {
		this.logService = logService;
	}

	@Reference
	public void setSearchService(IModelSearchService searchService) {
		this.searchService = searchService;
	}

}

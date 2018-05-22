package com.specmate.emfrest.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.MultivaluedMap;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import com.specmate.common.SpecmateException;
import com.specmate.common.SpecmateInvalidQueryException;
import com.specmate.common.SpecmateValidationException;
import com.specmate.emfrest.api.IRestService;
import com.specmate.emfrest.api.RestServiceBase;
import com.specmate.search.api.IModelSearchService;

@Component(immediate = true, service = IRestService.class)
public class SearchService extends RestServiceBase {

	private LogService logService;
	private IModelSearchService searchService;

	@Activate
	public void activate(Map<String, Object> properties) throws SpecmateValidationException {
		this.logService.log(LogService.LOG_INFO, "Initialized search service" + properties.toString());
	}

	@Override
	public String getServiceName() {
		return "search";
	}

	@Override
	public boolean canGet(Object target) {
		return (target instanceof Resource);
	}

	@Override
	public Object get(Object target, MultivaluedMap<String, String> queryParams) throws SpecmateException {
		String queryString = queryParams.getFirst("query");
		if (queryString == null) {
			throw new SpecmateException("Missing parameter: query");
		}
		Set<EObject> searchResult;
		try {
			project = SpecmateE
			searchResult = this.searchService.search(queryString, null);
		} catch (SpecmateInvalidQueryException e) {
			// Act robust against wrong query syntax
			return Collections.emptyList();
		}
		return new ArrayList<>(searchResult);
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

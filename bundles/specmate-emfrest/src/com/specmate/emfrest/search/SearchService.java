package com.specmate.emfrest.search;

import java.util.Collections;
import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.emf.ecore.resource.Resource;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import com.specmate.common.SpecmateException;
import com.specmate.common.SpecmateValidationException;
import com.specmate.emfrest.api.IRestService;
import com.specmate.emfrest.api.RestServiceBase;
import com.specmate.persistency.IPersistencyService;
import com.specmate.persistency.IView;

@Component(immediate = true, service = IRestService.class) // , configurationPid
															// =
															// SearchServiceConfig.PID,
															// configurationPolicy
															// =
															// ConfigurationPolicy.OPTIONAL)
public class SearchService extends RestServiceBase {

	IPersistencyService persistencyService;
	private IView view;
	private LogService logService;
	private String[] types;
	private String[] fields;

	public void activate(Map<String, Object> properties) throws SpecmateValidationException {
		// String types = (String)
		// properties.get(SearchServiceConfig.KEY_TYPES);
		// String fields = (String)
		// properties.get(SearchServiceConfig.KEY_FIELDS);
		// this.types = StringUtils.split(types, ",");
		// this.fields = StringUtils.split(fields, ",");
		// this.logService.log(LogService.LOG_INFO, "Initialized search service
		// " + properties.toString());
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
		String query = queryParams.getFirst("query");
		if (StringUtils.isEmpty(query)) {
			return Collections.emptyList();
		}
		// String typeQuery = getTypeQuery();
		return view.query("requirements::Requirement.allInstances()->select(r:requirements::Requirement | "
				+ "((r.name <> null and r.name.toLower().matches('.*" + query.toLowerCase()
				+ ".*')) or (r.extId<>null and r.extId.matches('.*" + query.toLowerCase() + ".*'))))", target);
	}

	// private String getTypeQueryTemplate(String types) {
	// StringBuilder builder = new StringBuilder();
	// Arrays.asList(types.split(",")).stream().map(t -> "oclIsKindOf(" + t +
	// ")")
	// .collect(Collectors.joining(" and "));
	// }

	@Reference
	public void setPersistencyService(IPersistencyService persistencyService) {
		this.persistencyService = persistencyService;
		this.view = persistencyService.openView();
	}

	public void setLogService(LogService logService) {
		this.logService = logService;
	}

}

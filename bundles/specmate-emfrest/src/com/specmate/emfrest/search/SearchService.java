package com.specmate.emfrest.search;

import java.util.Collections;
import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrLookup;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.eclipse.emf.ecore.resource.Resource;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import com.specmate.common.SpecmateException;
import com.specmate.common.SpecmateValidationException;
import com.specmate.emfrest.api.IRestService;
import com.specmate.emfrest.api.RestServiceBase;
import com.specmate.emfrest.internal.config.SearchServiceConfig;
import com.specmate.persistency.IPersistencyService;
import com.specmate.persistency.IView;

@Component(immediate = true, service = IRestService.class, configurationPid = SearchServiceConfig.PID, configurationPolicy = ConfigurationPolicy.OPTIONAL)
public class SearchService extends RestServiceBase {

	IPersistencyService persistencyService;
	private IView view;
	private LogService logService;
	private String queryTemplate;

	@Activate
	public void activate(Map<String, Object> properties) throws SpecmateValidationException {
		this.queryTemplate = (String) properties.get(SearchServiceConfig.KEY_QUERY_TEMPLATE);
		if (StringUtils.isEmpty(queryTemplate)) {
			throw new SpecmateValidationException("Empty query template.");
		}
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
		String query = queryParams.getFirst("query");
		if (StringUtils.isEmpty(query)) {
			return Collections.emptyList();
		}

		String oclQuery = getQueryFromTemplate(query);

		return view.query(oclQuery, target);
	}

	private String getQueryFromTemplate(String query) {
		StrSubstitutor sub = new StrSubstitutor(new StrLookup<String>() {
			@Override
			public String lookup(String arg0) {
				return query;
			}
		});
		return sub.replace(this.queryTemplate);
	}

	@Reference
	public void setPersistencyService(IPersistencyService persistencyService) {
		this.persistencyService = persistencyService;
		this.view = persistencyService.openView();
	}

	public void setLogService(LogService logService) {
		this.logService = logService;
	}

}

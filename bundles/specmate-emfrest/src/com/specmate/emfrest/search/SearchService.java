package com.specmate.emfrest.search;

import java.util.Collections;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.emf.ecore.resource.Resource;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.specmate.common.SpecmateException;
import com.specmate.emfrest.api.IRestService;
import com.specmate.emfrest.api.RestServiceBase;
import com.specmate.persistency.IPersistencyService;
import com.specmate.persistency.IView;

@Component(immediate = true, service = IRestService.class)
public class SearchService extends RestServiceBase {

	IPersistencyService persistencyService;
	private IView view;

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
		return view
				.query("base::IContentElement.allInstances()->select(r:base::IContentElement | r.name <> null and r.name.toLower().matches('.*"
						+ query + ".*'))", target);
	}

	@Reference
	public void setPersistencyService(IPersistencyService persistencyService) {
		this.persistencyService = persistencyService;
		this.view = persistencyService.openView();
	}

}

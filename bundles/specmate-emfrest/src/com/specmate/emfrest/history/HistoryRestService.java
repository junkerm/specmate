package com.specmate.emfrest.history;

import javax.ws.rs.core.MultivaluedMap;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.specmate.common.SpecmateException;
import com.specmate.emfrest.api.IRestService;
import com.specmate.emfrest.api.RestServiceBase;
import com.specmate.persistency.IHistoryProvider;

@Component(immediate = true, service = IRestService.class)
public class HistoryRestService extends RestServiceBase {

	private IHistoryProvider historyProvider;

	@Override
	public String getServiceName() {
		return "history";
	}

	@Override
	public boolean canGet(Object object) {
		return object instanceof EObject && !(object instanceof Resource);
	}

	@Override
	public Object get(Object object, MultivaluedMap<String, String> queryParams, String token)
			throws SpecmateException {
		return historyProvider.getHistory((EObject) object);
	}

	@Reference
	public void setHistoryProvider(IHistoryProvider historyProvider) {
		this.historyProvider = historyProvider;
	}

}

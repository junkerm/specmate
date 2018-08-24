package com.specmate.emfrest.history;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.specmate.common.SpecmateException;
import com.specmate.emfrest.api.IRestService;
import com.specmate.emfrest.api.RestServiceBase;
import com.specmate.persistency.IHistoryProvider;
import com.specmate.rest.RestResult;

@Component(immediate = true, service = IRestService.class)
public class HistoryRestService extends RestServiceBase {
	private final String HPARAM = "type";
	public static final String HSINGLE = "single";
	public static final String HCONTAINER = "container";
	public static final String HRECURSIVE = "recursive";

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
	public RestResult<?> get(Object object, MultivaluedMap<String, String> queryParams, String token)
			throws SpecmateException {
		String type = queryParams.getFirst(HPARAM);
		if (type == null) {
			return new RestResult<>(Response.Status.BAD_REQUEST);
		}

		if (type.equals(HSINGLE)) {
			return new RestResult<>(Response.Status.OK, historyProvider.getHistory((EObject) object));
		}

		if (type.equals(HCONTAINER)) {
			return new RestResult<>(Response.Status.OK, historyProvider.getContainerHistory((EObject) object));
		}

		if (type.equals(HRECURSIVE)) {
			return new RestResult<>(Response.Status.OK, historyProvider.getRecursiveHistory((EObject) object));
		}

		return new RestResult<>(Response.Status.BAD_REQUEST);
	}

	@Reference
	public void setHistoryProvider(IHistoryProvider historyProvider) {
		this.historyProvider = historyProvider;
	}

}

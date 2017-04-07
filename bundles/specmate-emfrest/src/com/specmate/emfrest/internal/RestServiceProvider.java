package com.specmate.emfrest.internal;

import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.log.LogService;

import com.specmate.emfrest.api.IRestService;

@Component(immediate = true, service = RestServiceProvider.class)
public class RestServiceProvider {
	private Map<String, IRestService> restServices = new HashMap<>();
	private LogService logService;

	public void activate() {
		this.logService.log(LogService.LOG_DEBUG, "Activating RestServiceProvider");
	}

	@Reference
	public void setLogService(LogService logService) {
		this.logService = logService;
	}

	@Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
	public void addRestService(IRestService restService) {
		if (restServices.containsKey(restService.getServiceName())) {
			this.logService.log(LogService.LOG_ERROR, "Duplicate REST Service id: " + restService.getServiceName());
			return;
		}
		restServices.put(restService.getServiceName(), restService);
	}

	public void removeRestService(IRestService restService) {
		restServices.remove(restService);
	}

	public IRestService getRestService(String name) {
		return restServices.get(name);
	}
}

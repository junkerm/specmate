package com.specmate.emfrest.internal;

import java.util.SortedSet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.log.LogService;

import com.google.common.collect.TreeMultimap;
import com.specmate.emfrest.api.IRestService;

@Component(immediate = true, service = RestServiceProvider.class)
public class RestServiceProvider {
	TreeMultimap<String, IRestService> restServices = TreeMultimap.create();
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
		restServices.put(restService.getServiceName(), restService);
	}

	public void removeRestService(IRestService restService) {
		restServices.remove(restService.getServiceName(), restService);
	}

	public IRestService getRestService(String name) {
		if (restServices.containsKey(name)) {
			return restServices.get(name).first();
		} else
			return null;
	}

	public SortedSet<IRestService> getAllRestServices(String name) {
		return restServices.get(name);
	}

}

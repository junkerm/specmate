package com.specmate.ui.core;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;

@Component(immediate=true)
public class ResourceRegistration {

	private HttpService httpService;

	@Activate
	public void activate(){
		try {
			httpService.registerResources("/", "/webcontent",null);
		} catch (NamespaceException e) {
			e.printStackTrace();
		}
	}
	
	@Reference
	public void setHttpService(HttpService http){
		this.httpService=http; 
	}
}

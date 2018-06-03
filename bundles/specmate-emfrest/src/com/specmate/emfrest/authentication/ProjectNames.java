package com.specmate.emfrest.authentication;

import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.specmate.connectors.api.IProjectService;
import com.specmate.emfrest.api.IRestService;
import com.specmate.emfrest.api.RestServiceBase;

@Component(service = IRestService.class)
public class ProjectNames extends RestServiceBase {
	public static final String SERVICE_NAME = "projectnames";
	private IProjectService projectService;
	
	@Override
	public String getServiceName() {
		return SERVICE_NAME;
	}
	
	@Override
	public boolean canGet(Object obj) {
		return true;
	}

	@Override
	public Object get(Object object, MultivaluedMap<String, String> queryParams) {
		return projectService.getProjectNames();
	}
	
	@Reference
	public void setProjectService(IProjectService projectService) {
		this.projectService = projectService;
	}
}

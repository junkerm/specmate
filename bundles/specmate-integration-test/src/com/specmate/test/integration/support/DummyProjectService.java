package com.specmate.test.integration.support;

import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

import com.specmate.connectors.api.IProjectService;
import com.specmate.connectors.api.IProject;

/**
 * Dummy implementation that does not require the config service. Pulling in the config service in the integration 
 * tests renders manual configuration impossible.
 */
@Component(immediate = true, property= {"service.ranking:Integer=1"})
public class DummyProjectService implements IProjectService {
	private Map<String, IProject> projects = new HashMap<>();

	@Override
	public IProject getProject(String projectName) {
		return projects.get(projectName);
	}
	
	public void addProject(IProject project) {
		projects.put(project.getName(), project);
	}

}

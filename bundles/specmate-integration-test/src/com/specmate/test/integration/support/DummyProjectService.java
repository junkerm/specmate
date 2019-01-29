package com.specmate.test.integration.support;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;

import com.specmate.connectors.api.IProject;
import com.specmate.connectors.api.IProjectService;

/**
 * Dummy implementation that does not require the config service. Pulling in the
 * config service in the integration tests renders manual configuration
 * impossible.
 */
@Component(immediate = true, property = { "service.ranking:Integer=1" })
public class DummyProjectService implements IProjectService {
	private Map<String, IProject> projects = new HashMap<>();

	@Override
	public IProject getProject(String projectId) {
		return projects.get(projectId);
	}

	@Override
	public Set<String> getProjectNames() {
		return Collections.unmodifiableSet(projects.keySet());
	}

	public void addProject(IProject project) {
		projects.put(project.getID(), project);
	}

}

package com.specmate.test.integration.support;

import org.osgi.service.component.annotations.Component;

import com.specmate.connectors.api.IProjectService;
import com.specmate.connectors.api.Project;

/**
 * Dummy implementation that does not require the config service. Pulling in the config service in the integration 
 * tests renders manual configuration impossible.
 */
@Component(immediate = true)
public class DummyProjectService implements IProjectService {

	@Override
	public Project getProject(String projectName) {
		return null;
	}

}

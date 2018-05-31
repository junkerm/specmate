package com.specmate.test.integration.support;

import org.osgi.service.component.annotations.Component;

import com.specmate.connectors.api.IProjectService;
import com.specmate.connectors.api.IRequirementsSource;
import com.specmate.connectors.api.IExportService;
import com.specmate.connectors.api.IProject;

/**
 * Dummy implementation that does not require the config service. Pulling in the config service in the integration 
 * tests renders manual configuration impossible.
 */
@Component(immediate = true)
public class DummyProjectService implements IProjectService {

	@Override
	public IProject getProject(String projectName) {
		return new IProject() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public IRequirementsSource getConnector() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public IExportService getExporter() {
				// TODO Auto-generated method stub
				return null;
			}
			
		};
	}

}

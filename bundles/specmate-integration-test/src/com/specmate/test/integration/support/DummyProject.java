package com.specmate.test.integration.support;

import java.util.Collection;
import java.util.List;

import com.specmate.common.exception.SpecmateException;
import com.specmate.connectors.api.IExportService;
import com.specmate.connectors.api.IProject;
import com.specmate.connectors.api.IRequirementsSource;
import com.specmate.model.base.IContainer;
import com.specmate.model.requirements.Requirement;
import com.specmate.model.testspecification.TestProcedure;

public class DummyProject implements IProject {
	private String projectName;

	public DummyProject(String projectName) {
		this.projectName = projectName;
	}

	@Override
	public String getName() {
		return this.projectName;
	}

	@Override
	public IRequirementsSource getConnector() {
		return new IRequirementsSource() {

			@Override
			public Collection<Requirement> getRequirements() throws SpecmateException {
				return null;
			}

			@Override
			public String getId() {
				return null;
			}

			@Override
			public IContainer getContainerForRequirement(Requirement requirement) throws SpecmateException {
				return null;
			}

			@Override
			public boolean authenticate(String username, String password) throws SpecmateException {
				return true;
			}
		};
	}

	@Override
	public IExportService getExporter() {
		return new IExportService() {

			@Override
			public boolean isAuthorizedToExport(String username, String password) {
				return false;
			}

			@Override
			public void export(TestProcedure testProcedure) throws SpecmateException {
				// Nothing to do
			}
		};
	}

	@Override
	public List<String> getLibraryFolders() {
		return null;
	}
}

package com.specmate.connectors.internal;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;

import com.specmate.connectors.api.IExportService;
import com.specmate.connectors.api.IProject;
import com.specmate.connectors.api.IProjectConfigService;
import com.specmate.connectors.api.IRequirementsSource;
import com.specmate.connectors.config.ProjectConfigService;

@Component(service = IProject.class, configurationPid = ProjectConfigService.PROJECT_PID, configurationPolicy = ConfigurationPolicy.REQUIRE)
public class ProjectImpl implements IProject {
	private String name;
	private IRequirementsSource connector = null;
	private IExportService exporter;
	private List<String> libraryFolders;

	@Activate
	public void activate(Map<String, Object> properties) {
		this.name = (String) properties.get(ProjectConfigService.KEY_PROJECT_NAME);

		String[] lf = (String[]) properties.get(IProjectConfigService.KEY_PROJECT_LIBRARY_FOLDERS);
		if (lf != null) {
			libraryFolders = Arrays.asList(lf);
		}
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Reference(name = "connector")
	public void setConnector(IRequirementsSource connector) {
		this.connector = connector;
	}

	@Override
	public IRequirementsSource getConnector() {
		return connector;
	}

	@Reference(cardinality = ReferenceCardinality.OPTIONAL, name = "exporter")
	public void setExporter(IExportService exporter) {
		this.exporter = exporter;

	}

	@Override
	public IExportService getExporter() {
		return this.exporter;
	}

	@Override
	public List<String> getLibraryFolders() {
		return libraryFolders;
	}

}

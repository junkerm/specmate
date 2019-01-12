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

@Component(service = IProject.class, configurationPid = ProjectConfigService.PROJECT_CONFIG_FACTORY_PID, configurationPolicy = ConfigurationPolicy.REQUIRE)
public class ProjectImpl implements IProject {
	private String id = null;
	private IRequirementsSource connector = null;
	private IExportService exporter = null;
	private List<String> libraryFolders = null;

	@Activate
	public void activate(Map<String, Object> properties) {
		Object obj = properties.get(ProjectConfigService.KEY_PROJECT_ID);
		if (obj != null && obj instanceof String) {
			this.id = (String) properties.get(ProjectConfigService.KEY_PROJECT_ID);
		}

		obj = properties.get(IProjectConfigService.KEY_PROJECT_LIBRARY_FOLDERS);
		if (obj != null && obj instanceof String[]) {
			libraryFolders = Arrays.asList((String[]) obj);
		}
	}

	@Override
	public String getID() {
		return id;
	}

	public void setID(String id) {
		this.id = id;
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

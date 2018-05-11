package com.specmate.connectors.internal;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import com.specmate.common.OSGiUtil;
import com.specmate.common.SpecmateException;
import com.specmate.config.api.IConfigService;
import com.specmate.connectors.api.Connector;
import com.specmate.connectors.api.IProjectService;
import com.specmate.connectors.api.Project;

@Component(immediate = true)
public class ProjectService implements IProjectService {

	private IConfigService configService;
	private Map<String, Project> projects = new HashMap<>();
	private ConfigurationAdmin configAdmin;
	private LogService logService;

	@Activate
	public void activate() {
		String[] projectsNames = configService.getConfigurationPropertyArray("project.projects");
		for (int i = 0; i < projectsNames.length; i++) {
			Project project = new Project();
			project.setName(projectsNames[i]);
			String projectPrefix = "project." + projectsNames[i];
			String[] connectors = configService.getConfigurationPropertyArray(projectPrefix + ".connectors");
			for (int j = 0; j < connectors.length; j++) {
				String connectorName = connectors[j];
				Set<Entry<Object, Object>> config = configService
						.getConfigurationProperties(projectPrefix + "." + connectorName);
				String connectorPrefix = projectPrefix + "." + connectorName;
				Connector connector = createConnector(connectorName, config, connectorPrefix);
				project.addConnector(connector);
			}
			projects.put(project.getName(), project);
		}

		for (Project project : projects.values()) {
			configureConnectors(project);
		}
	}

	private void configureConnectors(Project project) {
		for (Connector connector : project.getConnectors()) {
			try {
				OSGiUtil.configureFactory(configAdmin, connector.getPid(), connector.getConfig());
			} catch (SpecmateException e) {
				this.logService.log(LogService.LOG_ERROR,
						"Failed attempt to configure connector of type " + connector.getPid() + " with config "
								+ OSGiUtil.configDictionaryToString(connector.getConfig()));
			}
		}
	}

	private Connector createConnector(String connectorName, Set<Entry<Object, Object>> config, String projectPrefix) {
		Connector connector = new Connector();
		Hashtable<String, Object> configTable = new Hashtable<>();
		for (Entry<Object, Object> configEntry : config) {
			String key = (String) configEntry.getKey();
			String connectorConfigKey = key.substring(projectPrefix.length() + 1);
			String pidKey = "pid";
			if (connectorConfigKey.equals(pidKey)) {
				connector.setPid((String) configEntry.getValue());
			} else {
				configTable.put(connectorConfigKey, configEntry.getValue());
			}
		}
		connector.setConfig(configTable);
		return connector;
	}

	@Override
	public Project getProject(String projectName) {
		return this.projects.get(projectName);
	}

	@Reference
	public void setConfigService(IConfigService configService) {
		this.configService = configService;
	}

	@Reference
	public void setConfigurationAdmin(ConfigurationAdmin configAdmin) {
		this.configAdmin = configAdmin;
	}

	@Reference
	public void setLogService(LogService logService) {
		this.logService = logService;
	}
}

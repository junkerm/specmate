package com.specmate.connectors.config;

import java.util.Hashtable;
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
import com.specmate.connectors.api.Configurable;

/**
 * Service that configures connectors and exporters based on configured projects
 */
@Component(immediate = true)
public class ProjectConfigService {

	/** The prefix for project configuation keys */
	private static final String PROJECT_PREFIX = "project.";

	/** The PID of a single project service */
	public static final String PROJECT_PID = "com.specmate.connectors.project";

	/** The configuration key for the id of a connector */
	public static final String KEY_CONNECTOR_ID = "connectorID";

	/** The configuration key for the id of an exporter */
	public static final String KEY_EXPORTER_ID = "exporterID";

	/** the configuration key for the name of a project */
	public static final String KEY_PROJECT_NAME = "projectName";

	/** The configuration key for the list of projects. */
	private static final String KEY_PROJECT_NAMES = PROJECT_PREFIX + "projects";

	/** The config service */
	private IConfigService configService;

	/** The config admin service. */
	private ConfigurationAdmin configAdmin;

	/** The log service. */
	private LogService logService;

	@Activate
	public void activate() throws SpecmateException {
		String[] projectsNames = configService.getConfigurationPropertyArray(KEY_PROJECT_NAMES);
		if (projectsNames == null) {
			return;
		}

		configureProjects(projectsNames);
	}

	/**
	 * Configures the given projects based on the configuration data from the
	 * configuration service.
	 */
	private void configureProjects(String[] projectsNames) throws SpecmateException {
		for (int i = 0; i < projectsNames.length; i++) {
			String projectName = projectsNames[i];
			String projectPrefix = PROJECT_PREFIX + projectsNames[i];

			Configurable connector = createConnector(projectPrefix);
			if (connector != null) {
				configureConfigurable(connector);
			}
			Configurable exporter = createExporter(projectPrefix);
			if (exporter != null) {
				configureConfigurable(exporter);
			}

			configureProject(projectName, connector, exporter);
		}
	}

	/**
	 * Configures a single project with a given connector and exporter description
	 */
	private void configureProject(String projectName, Configurable connector, Configurable exporter) throws SpecmateException {
		String exporterFilter;
		if(exporter!=null) {
			exporterFilter= "(" + KEY_EXPORTER_ID + "=" + exporter.getConfig().get(KEY_EXPORTER_ID) + ")";
		} else {
			exporterFilter= "(" + KEY_EXPORTER_ID + "= NO_ID)";
		}
		String connectorFilter = "(" + KEY_CONNECTOR_ID + "=" + connector.getConfig().get(KEY_CONNECTOR_ID) + ")";

		Hashtable<String, Object> projectConfig = new Hashtable<String, Object>();
		projectConfig.put(KEY_PROJECT_NAME, projectName);

		// Set the target of the 'exporter' reference in the Project.
		// This ensures that the right exporter will be bound to the project.
		projectConfig.put("exporter.target", exporterFilter);

		// Set the target of the 'connector' reference in the Project.
		// This ensures that the right connector will be bound to the project.
		projectConfig.put("connector.target", connectorFilter);

		projectConfig.put(KEY_PROJECT_NAME, projectName);

		OSGiUtil.configureFactory(configAdmin, PROJECT_PID, projectConfig);
	}

	/**
	 * Creates an exporter from the config for the project given by the config
	 * prefix.
	 */
	private Configurable createExporter(String projectPrefix) {
		String exporterPrefix = projectPrefix + "." + "exporter";
		Configurable exporter = new Configurable();
		return fillConfigurable(exporter, exporterPrefix);
	}

	/**
	 * Creates an connector from the config for the project given by the config
	 * prefix.
	 */
	private Configurable createConnector(String projectPrefix) {
		String connectorPrefix = projectPrefix + "." + "connector";
		Configurable connector = new Configurable();
		return fillConfigurable(connector, connectorPrefix);
	}

	/** Configures a configurable with the ConfigAdmin */
	private void configureConfigurable(Configurable configurable) {
		try {
			OSGiUtil.configureFactory(configAdmin, configurable.getPid(), configurable.getConfig());
		} catch (Exception e) {
			this.logService.log(LogService.LOG_ERROR, "Failed attempt to configure "
					+ configurable.getPid() + " with config " + OSGiUtil.configDictionaryToString(configurable.getConfig()));
		}
	}


	/** Fills the config entries into the configurable object. */
	private <T extends Configurable> T fillConfigurable(T configurable,  String prefix) {
		
		Set<Entry<Object, Object>> config = configService.getConfigurationProperties(prefix);
		if (config == null || config.isEmpty()) {
			return null;
		}
		
		Hashtable<String, Object> configTable = new Hashtable<>();
		for (Entry<Object, Object> configEntry : config) {
			String key = (String) configEntry.getKey();
			String connectorConfigKey = key.substring(prefix.length() + 1);
			String pidKey = "pid";
			if (connectorConfigKey.equals(pidKey)) {
				configurable.setPid((String) configEntry.getValue());
			} else {
				configTable.put(connectorConfigKey, configEntry.getValue());
			}
		}
		configurable.setConfig(configTable);
		return configurable;
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

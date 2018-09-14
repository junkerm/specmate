package com.specmate.connectors.config;

import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import com.specmate.common.OSGiUtil;
import com.specmate.common.SpecmateException;
import com.specmate.config.api.IConfigService;
import com.specmate.connectors.api.Configurable;
import com.specmate.connectors.api.IProjectConfigService;
import com.specmate.model.base.BaseFactory;
import com.specmate.model.base.Folder;
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.persistency.IPersistencyService;
import com.specmate.persistency.ITransaction;

/**
 * Service that configures connectors, exporters and top-level library folders
 * based on configured projects
 */
@Component(immediate = true)
public class ProjectConfigService implements IProjectConfigService {
	/** The prefix for project configuration keys */
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
	public static final String KEY_PROJECT_NAMES = PROJECT_PREFIX + "projects";

	/** The configuration key for the list of top-level library folder ids. */
	public static final String KEY_PROJECT_LIBRARY = ".library";

	/** The configuration key for the library name */
	public static final String KEY_PROJECT_LIBRARY_NAME = ".name";

	/** The configuration key for the library description */
	public static final String KEY_PROJECT_LIBRARY_DESCRIPTION = ".description";

	/** The config service */
	private IConfigService configService;

	/** The config admin service. */
	private ConfigurationAdmin configAdmin;

	/** The log service. */
	private LogService logService;

	/** The persistency service to access the model data */
	private IPersistencyService persistencyService;

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
	@Override
	public void configureProjects(String[] projectsNames) throws SpecmateException {
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
			bootstrapProjectLibrary(projectName);
		}
	}

	/**
	 * Configures a single project with a given connector and exporter description
	 */
	private void configureProject(String projectName, Configurable connector, Configurable exporter)
			throws SpecmateException {
		String exporterFilter;
		if (exporter != null) {
			exporterFilter = "(" + KEY_EXPORTER_ID + "=" + exporter.getConfig().get(KEY_EXPORTER_ID) + ")";
		} else {
			exporterFilter = "(" + KEY_EXPORTER_ID + "= NO_ID)";
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
			System.out.println(configurable.getPid());
			OSGiUtil.configureFactory(configAdmin, configurable.getPid(), configurable.getConfig());
		} catch (Exception e) {
			this.logService.log(LogService.LOG_ERROR, "Failed attempt to configure " + configurable.getPid()
					+ " with config " + OSGiUtil.configDictionaryToString(configurable.getConfig()), e);
		}
	}

	/** Fills the config entries into the configurable object. */
	private <T extends Configurable> T fillConfigurable(T configurable, String prefix) {

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

	/** Creates top-level library folders, if necessary */
	private void bootstrapProjectLibrary(String projectName) throws SpecmateException {
		ITransaction trans = null;

		try {
			trans = this.persistencyService.openTransaction();
			EList<EObject> projects = trans.getResource().getContents();
			if (projects == null || projects.size() == 0) {
				return;
			}

			EObject obj = SpecmateEcoreUtil.getEObjectWithName(projectName, projects);
			if (obj == null || !(obj instanceof Folder)) {
				throw new SpecmateException("Expected project " + projectName + " not found in database");
			}

			Folder project = (Folder) obj;
			createOrUpdateLibraryFolders(project);

			trans.commit();
		} finally {
			if (trans != null) {
				trans.close();
			}
		}
	}

	private void createOrUpdateLibraryFolders(Folder parent) {
		String projectName = SpecmateEcoreUtil.getName(parent);
		String projectLibraryKey = PROJECT_PREFIX + projectName + KEY_PROJECT_LIBRARY;
		String[] libraryFolders = configService.getConfigurationPropertyArray(projectLibraryKey);
		if (libraryFolders != null) {
			for (int i = 0; i < libraryFolders.length; i++) {
				String projectLibraryId = libraryFolders[i];
				String libraryName = configService.getConfigurationProperty(
						projectLibraryKey + "." + projectLibraryId + KEY_PROJECT_LIBRARY_NAME);
				String libraryDescription = configService.getConfigurationProperty(
						projectLibraryKey + "." + projectLibraryId + KEY_PROJECT_LIBRARY_DESCRIPTION);

				EObject obj = SpecmateEcoreUtil.getEObjectWithId(projectLibraryId, parent.eContents());
				Folder libraryFolder = null;
				if (obj == null) {
					libraryFolder = BaseFactory.eINSTANCE.createFolder();
					parent.getContents().add(libraryFolder);
				} else {
					assert (obj instanceof Folder);
					libraryFolder = (Folder) obj;
				}

				libraryFolder.setId(projectLibraryId);
				libraryFolder.setName(libraryName);
				libraryFolder.setDescription(libraryDescription);
			}
		}
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

	@Reference
	public void setPersistencyService(IPersistencyService persistencyService) {
		this.persistencyService = persistencyService;
	}
}

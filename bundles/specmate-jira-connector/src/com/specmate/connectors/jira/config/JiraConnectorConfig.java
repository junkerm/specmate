package com.specmate.connectors.jira.config;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import com.specmate.common.OSGiUtil;
import com.specmate.common.SpecmateException;
import com.specmate.config.api.IConfigService;

@Component(immediate = true)
public class JiraConnectorConfig {

	public static final String PID = "com.specmate.connectors.jira.JiraConnector";
	public static final String KEY_JIRA_URL = "jira.url";
	public static final String KEY_JIRA_PROJECT = "jira.project";
	public static final String KEY_JIRA_USERNAME = "jira.username";
	public static final String KEY_JIRA_PASSWORD = "jira.password";
	private ConfigurationAdmin configurationAdmin;
	private LogService logService;
	private IConfigService configService;

	/**
	 * Configures the Jira Connector.
	 * 
	 * @throws SpecmateException
	 */
	@Activate
	private void configureTelloConnector() throws SpecmateException {
		Dictionary<String, Object> properties = new Hashtable<>();
		String url = configService.getConfigurationProperty(KEY_JIRA_URL);
		String project = configService.getConfigurationProperty(KEY_JIRA_PROJECT);
		String username = configService.getConfigurationProperty(KEY_JIRA_USERNAME);
		String password = configService.getConfigurationProperty(KEY_JIRA_PASSWORD);

		if (project != null && username != null && password != null) {
			properties.put(KEY_JIRA_URL, url);
			properties.put(KEY_JIRA_PROJECT, project);
			properties.put(KEY_JIRA_USERNAME, username);
			properties.put(KEY_JIRA_PASSWORD, password);
			logService.log(LogService.LOG_DEBUG,
					"Configuring Jira Connector with:\n" + OSGiUtil.configDictionaryToString(properties));

			OSGiUtil.configureService(configurationAdmin, PID, properties);
		}

	}

	/** Service reference for config admin */
	@Reference
	public void setConfigurationAdmin(ConfigurationAdmin configurationAdmin) {
		this.configurationAdmin = configurationAdmin;
	}

	/** Service reference for config service */
	@Reference
	public void setConfigurationService(IConfigService configService) {
		this.configService = configService;
	}

	@Reference
	public void setLogService(LogService logService) {
		this.logService = logService;
	}

}

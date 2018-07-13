package com.specmate.cdoserver.config;

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
public class SpecmateCDOServerConfig {

	public static final String PID = "com.specmate.cdoserver";

	public static final String KEY_SERVER_PORT = "cdo.serverPort";
	public static final String KEY_REPOSITORY_NAME = "cdo.repositoryName";

	private ConfigurationAdmin configurationAdmin;

	private IConfigService configService;

	private LogService logService;

	private String serverPort;

	private String repositoryName;

	@Activate
	private void activate() throws SpecmateException {
		this.serverPort = configService.getConfigurationProperty(KEY_SERVER_PORT);
		this.repositoryName = configService.getConfigurationProperty(KEY_REPOSITORY_NAME);

		Dictionary<String, Object> properties = new Hashtable<>();
		if (serverPort != null && this.repositoryName != null) {
			properties.put(KEY_SERVER_PORT, serverPort);
			properties.put(KEY_REPOSITORY_NAME, repositoryName);
			logService.log(LogService.LOG_DEBUG,
					"Configuring CDO with:\n" + OSGiUtil.configDictionaryToString(properties));
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

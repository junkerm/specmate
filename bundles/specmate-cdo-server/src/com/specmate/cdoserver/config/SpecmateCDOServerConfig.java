package com.specmate.cdoserver.config;

import java.util.Dictionary;
import java.util.Hashtable;

import org.eclipse.net4j.util.StringUtil;
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
	public static final String KEY_CDO_USER = "cdo.user";
	public static final String KEY_CDO_PASSWORD = "cdo.password";

	private ConfigurationAdmin configurationAdmin;

	private IConfigService configService;

	private LogService logService;

	private String serverPort;

	private String repositoryName;

	private String cdoUser;

	private String cdoPassword;

	@Activate
	private void activate() throws SpecmateException {
		this.serverPort = configService.getConfigurationProperty(KEY_SERVER_PORT);
		this.repositoryName = configService.getConfigurationProperty(KEY_REPOSITORY_NAME);
		this.cdoUser = configService.getConfigurationProperty(KEY_CDO_USER);
		this.cdoPassword = configService.getConfigurationProperty(KEY_CDO_PASSWORD);

		Dictionary<String, Object> properties = new Hashtable<>();
		if (!StringUtil.isEmpty(serverPort) && !StringUtil.isEmpty(repositoryName) && !StringUtil.isEmpty(cdoUser)
				&& !StringUtil.isEmpty(cdoPassword)) {
			properties.put(KEY_SERVER_PORT, serverPort);
			properties.put(KEY_REPOSITORY_NAME, repositoryName);
			properties.put(KEY_CDO_USER, cdoUser);
			properties.put(KEY_CDO_PASSWORD, cdoPassword);
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

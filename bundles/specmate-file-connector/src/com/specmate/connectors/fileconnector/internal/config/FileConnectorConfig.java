package com.specmate.connectors.fileconnector.internal.config;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import com.specmate.common.OSGiUtil;
import com.specmate.common.exception.SpecmateException;
import com.specmate.config.api.IConfigService;

@Component(immediate = true)
public class FileConnectorConfig {
	public static final String PID = "com.specmate.FileConnector";
	public static final String KEY_FOLDER = "fileConnector.folder";
	public static final String KEY_USER = "fileConnector.user";
	public static final String KEY_PASSWORD = "fileConnector.password";
	private IConfigService configService;
	private LogService logService;
	private ConfigurationAdmin configurationAdmin;

	/**
	 * Configures the CDO persistency service.
	 *
	 * @throws SpecmateException
	 */
	@Activate
	private void configureFileConnector() throws SpecmateException {
		Dictionary<String, Object> properties = new Hashtable<>();
		String folder = configService.getConfigurationProperty(KEY_FOLDER);

		if (folder != null) {
			properties.put(KEY_FOLDER, folder);
			logService.log(LogService.LOG_DEBUG,
					"Configuring CDO with:\n" + OSGiUtil.configDictionaryToString(properties) + ".");

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

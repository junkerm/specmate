package com.specmate.logging.internal.config;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.specmate.common.OSGiUtil;
import com.specmate.common.exception.SpecmateException;
import com.specmate.config.api.IConfigService;

@Component(immediate = true)
public class SpecmateLogReaderConfig {
	public static final String PID = "com.specmate.logger";

	public static final String KEY_LOG_LEVEL = "logging.level";

	private ConfigurationAdmin configurationAdmin;

	private IConfigService configService;

	@Activate
	private void configureFileConnector() throws SpecmateException {
		Dictionary<String, Object> properties = new Hashtable<>();
		String logLevel = configService.getConfigurationProperty(KEY_LOG_LEVEL, "info");

		if (logLevel != null) {
			properties.put(KEY_LOG_LEVEL, logLevel);
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

}

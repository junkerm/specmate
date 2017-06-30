package com.specmate.connectors.internal.config;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import com.specmate.common.OSGiUtil;
import com.specmate.config.api.IConfigService;

@Component(immediate = true)
public class ConnectorServiceConfig {

	public static final String PID = "com.specmate.connectors.ConnectorService";
	public static final String KEY_POLL_TIME = "connectorPollTime";

	private ConfigurationAdmin configurationAdmin;
	private IConfigService configService;
	private LogService logService;

	/** Configures the CDO persistency service. */
	@Activate
	private void configureCDO() throws IOException {
		Dictionary<String, Object> properties = new Hashtable<>();
		Integer connectorsPollTime = Integer.parseInt(configService.getConfigurationProperty(KEY_POLL_TIME, "20"));
		properties.put(KEY_POLL_TIME, connectorsPollTime);
		logService.log(LogService.LOG_DEBUG,
				"Configuring Connectors with:\n" + OSGiUtil.configDictionaryToString(properties));

		OSGiUtil.configureService(configurationAdmin, PID, properties);
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

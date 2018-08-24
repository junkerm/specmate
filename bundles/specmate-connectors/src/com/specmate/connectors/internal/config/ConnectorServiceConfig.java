package com.specmate.connectors.internal.config;

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
public class ConnectorServiceConfig {

	public static final String DISABLED_STRING = "disabled";
	public static final String PID = "com.specmate.connectors.ConnectorService";
	public static final String KEY_POLL_SCHEDULE = "connectorPollSchedule";

	private ConfigurationAdmin configurationAdmin;
	private IConfigService configService;
	private LogService logService;

	/** Configures the connector service. */
	@Activate
	public void configureConnectorService() throws SpecmateException {
		Dictionary<String, Object> properties = new Hashtable<>();
		String connectorScheduleStr = configService.getConfigurationProperty(KEY_POLL_SCHEDULE, DISABLED_STRING);

		if (connectorScheduleStr.equalsIgnoreCase(DISABLED_STRING)) {
			logService.log(LogService.LOG_INFO, "Connectors service disabled.");
			return;
		}

		properties.put(KEY_POLL_SCHEDULE, connectorScheduleStr);
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

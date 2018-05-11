package com.specmate.connectors.hpconnector.internal.config;

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
public class HPServerProxyConfig {
	public static final String CONNECTOR_PID = "com.specmate.HPServerProxyConnector";
	public static final String EXPORTER_PID = "com.specmate.HPServerProxyExporter";
	public static final String KEY_HOST = "hpproxy.hpConnectorHost";
	public static final String KEY_PORT = "hpproxy.hpConnectorPort";
	public static final String KEY_TIMEOUT = "hpproxy.hpConnectorTimeout";
	public static final String KEY_CONNECTOR_ID = "connectorId";
	public static final String KEY_EXPORTER_ID = "exporterID";

	private ConfigurationAdmin configurationAdmin;
	private IConfigService configService;
	private LogService logService;

	/**
	 * Configures the CDO persistency service.
	 * 
	 * @throws SpecmateException
	 */
	@Activate
	private void configureHPProxy() throws SpecmateException {
		Dictionary<String, Object> properties = new Hashtable<>();
		String host = configService.getConfigurationProperty(KEY_HOST);
		String port = configService.getConfigurationProperty(KEY_PORT);
		String timeout = configService.getConfigurationProperty(KEY_TIMEOUT);

		if (host != null && port != null && timeout != null) {
			properties.put(KEY_HOST, host);
			properties.put(KEY_TIMEOUT, Integer.parseInt(timeout));
			properties.put(KEY_PORT, port);
			logService.log(LogService.LOG_DEBUG,
					"Configuring CDO with:\n" + OSGiUtil.configDictionaryToString(properties));

			OSGiUtil.configureService(configurationAdmin, CONNECTOR_PID, properties);
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

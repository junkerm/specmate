package com.specmate.config.internal;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.specmate.common.OSGiUtil;
import com.specmate.connectors.hpconnector.HPServerProxyConfig;
import com.specmate.persistency.cdo.CDOPersistenceConfig;

/** Service for providing configurations for other services. */
@Component(immediate = true)
public class ConfigService {

	/** The configuration admin. */
	private ConfigurationAdmin configurationAdmin;

	@Activate
	public void activate() throws IOException {
		configureCDO();
		configureHPConnector();
	}

	/** Configures the CDO persistency service. */
	private void configureCDO() throws IOException {
		Dictionary<String, Object> properties = new Hashtable<>();
		properties.put(CDOPersistenceConfig.KEY_REPOSITORY_NAME, "specmate_repository");
		properties.put(CDOPersistenceConfig.KEY_RESOURCE_NAME, "specmate_resource");
		OSGiUtil.configureService(configurationAdmin, CDOPersistenceConfig.PID, properties);
	}

	/** Configures the HP proxy connection. */
	private void configureHPConnector() throws IOException {
		Dictionary<String, Object> properties = new Hashtable<>();
		properties.put(HPServerProxyConfig.KEY_HOST, "localhost");
		properties.put(HPServerProxyConfig.KEY_PORT, "8081");
		properties.put(HPServerProxyConfig.KEY_TIMEOUT, 20);
		OSGiUtil.configureService(configurationAdmin, HPServerProxyConfig.PID, properties);
	}

	/** Service reference */
	@Reference
	public void setConfigurationAdmin(ConfigurationAdmin configurationAdmin) {
		this.configurationAdmin = configurationAdmin;
	}
}

package com.specmate.config.internal;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.specmate.connectors.hpconnector.HPServerProxyConfig;
import com.specmate.persistency.cdo.CDOPersistenceConfig;

@Component(immediate = true)
public class ConfigService {

	private static final String ALL_LOCATIONS = "?";
	private ConfigurationAdmin configurationAdmin;

	@Activate
	public void activate() throws IOException {
		configureCDO();
		configureHPConnector();
	}

	private void configureCDO() throws IOException {
		Configuration config = configurationAdmin.getConfiguration(CDOPersistenceConfig.PID);
		config.setBundleLocation(ALL_LOCATIONS);
		Dictionary<String, Object> properties = new Hashtable<>();
		properties.put(CDOPersistenceConfig.KEY_REPOSITORY_NAME, "specmate_repository");
		properties.put(CDOPersistenceConfig.KEY_RESOURCE_NAME, "specmate_resource");
		config.update(properties);
	}

	private void configureHPConnector() throws IOException {
		Configuration config = configurationAdmin.getConfiguration(HPServerProxyConfig.PID);
		config.setBundleLocation(ALL_LOCATIONS);
		Dictionary<String, Object> properties = new Hashtable<>();
		properties.put(HPServerProxyConfig.KEY_HOST, "localhost");
		properties.put(HPServerProxyConfig.KEY_PORT, "8081");
		properties.put(HPServerProxyConfig.KEY_TIMEOUT, 20);
		config.update(properties);
	}

	@Reference
	public void setConfigurationAdmin(ConfigurationAdmin configurationAdmin) {
		this.configurationAdmin = configurationAdmin;
	}
}

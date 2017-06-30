package com.specmate.persistency.cdo.internal.config;

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
public class CDOPersistenceConfig {

	public static final String PID = "com.specmate.persistency.cdo.internal.CDOPersistencyService";
	public static final String KEY_REPOSITORY_NAME = "cdoRepositoryName";
	public static final String KEY_RESOURCE_NAME = "cdoResourceName";
	private ConfigurationAdmin configurationAdmin;
	private IConfigService configService;
	private LogService logService;

	/** Configures the CDO persistency service. */
	@Activate
	private void configureCDO() throws IOException {
		Dictionary<String, Object> properties = new Hashtable<>();
		String specmateRepository = configService.getConfigurationProperty(KEY_REPOSITORY_NAME, "specmate_repository");
		String specmateResource = configService.getConfigurationProperty(KEY_RESOURCE_NAME, "specmate_resource");
		properties.put(KEY_REPOSITORY_NAME, specmateRepository);
		properties.put(KEY_RESOURCE_NAME, specmateResource);
		logService.log(LogService.LOG_DEBUG, "Configuring CDO with:\n" + OSGiUtil.configDictionaryToString(properties));

		OSGiUtil.configureService(configurationAdmin, CDOPersistenceConfig.PID, properties);
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

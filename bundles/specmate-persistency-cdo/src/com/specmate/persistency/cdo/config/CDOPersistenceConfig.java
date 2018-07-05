package com.specmate.persistency.cdo.config;

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
public class CDOPersistenceConfig {

	public static final String PID = "com.specmate.persistency.cdo.internal.CDOPersistencyService";
	public static final String KEY_REPOSITORY_NAME = "cdoRepositoryName";
	public static final String KEY_RESOURCE_NAME = "cdoResourceName";
	public static final String KEY_USER_RESOURCE_NAME = "cdoUserResourceName";
	public static final String KEY_HOST = "cdoHost";
	private ConfigurationAdmin configurationAdmin;
	private IConfigService configService;
	private LogService logService;

	/**
	 * Configures the CDO persistency service.
	 *
	 * @throws SpecmateException
	 */
	@Activate
	private void configureCDO() throws SpecmateException {
		Dictionary<String, Object> properties = new Hashtable<>();
		String specmateRepository = configService.getConfigurationProperty(KEY_REPOSITORY_NAME);
		String specmateResource = configService.getConfigurationProperty(KEY_RESOURCE_NAME);
		String specmateUserResource = configService.getConfigurationProperty(KEY_USER_RESOURCE_NAME);
		String host = configService.getConfigurationProperty(KEY_HOST);
		if (specmateRepository != null && specmateResource != null && specmateUserResource != null && host != null) {
			properties.put(KEY_REPOSITORY_NAME, specmateRepository);
			properties.put(KEY_RESOURCE_NAME, specmateResource);
			properties.put(KEY_USER_RESOURCE_NAME, specmateUserResource);
			properties.put(KEY_HOST, host);
			logService.log(LogService.LOG_DEBUG,
					"Configuring CDO with:\n" + OSGiUtil.configDictionaryToString(properties));
			OSGiUtil.configureService(configurationAdmin, CDOPersistenceConfig.PID, properties);
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

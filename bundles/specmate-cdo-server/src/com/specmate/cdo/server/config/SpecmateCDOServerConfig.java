package com.specmate.cdo.server.config;

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
public class SpecmateCDOServerConfig {
	public final static String PID = "com.specmate.cdoserver";
	public static final String KEY_REPOSITORY = "cdoRepositoryName";
	public static final String KEY_JDBC_CONNECTION = "cdoJDBCConnection";
	private ConfigurationAdmin configurationAdmin;
	private IConfigService configService;
	private LogService logService;

	@Activate
	public void activate() throws SpecmateException {
		Dictionary<String, Object> properties = new Hashtable<>();
		String specmateRepository = configService.getConfigurationProperty(KEY_REPOSITORY);
		String jdbcConnection = configService.getConfigurationProperty(KEY_JDBC_CONNECTION);
		if (specmateRepository != null && jdbcConnection != null) {
			properties.put(KEY_REPOSITORY, specmateRepository);
			properties.put(KEY_JDBC_CONNECTION, jdbcConnection);
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

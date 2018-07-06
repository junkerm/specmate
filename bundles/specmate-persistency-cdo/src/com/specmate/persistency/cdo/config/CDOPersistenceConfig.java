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
	public static final String DB_H2 = "h2";
	public static final String DB_ORACLE = "oracle";
	public static final String KEY_DB_TYPE = "cdoDBType";
	public static final String KEY_JDBC_CONNECTION = "cdoJDBCConnection";
	public static final String KEY_REPOSITORY_NAME = "cdoRepositoryName";
	public static final String KEY_RESOURCE_NAME = "cdoResourceName";
	public static final String KEY_USER_RESOURCE_NAME = "cdoUserResourceName";
	public static final String KEY_ORACLE_USERNAME = "username";
	public static final String KEY_ORACLE_PASSWORD = "password";
	private String configuredDB;
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

		String specmateJDBCConnection = getJDBCConnnection();
		String specmateRepository = configService.getConfigurationProperty(getDBPrefix() + KEY_REPOSITORY_NAME);
		String specmateResource = configService.getConfigurationProperty(getDBPrefix() + KEY_RESOURCE_NAME);
		String specmateUserResource = configService.getConfigurationProperty(getDBPrefix() + KEY_USER_RESOURCE_NAME);
		if (specmateJDBCConnection != null && specmateRepository != null && specmateResource != null
				&& specmateUserResource != null) {
			properties.put(KEY_JDBC_CONNECTION, specmateJDBCConnection);
			properties.put(KEY_REPOSITORY_NAME, specmateRepository);
			properties.put(KEY_RESOURCE_NAME, specmateResource);
			properties.put(KEY_USER_RESOURCE_NAME, specmateUserResource);
			properties.put(KEY_DB_TYPE, configuredDB);
			logService.log(LogService.LOG_DEBUG,
					"Configuring CDO with:\n" + OSGiUtil.configDictionaryToString(properties));

			if (configuredDB.equals(DB_ORACLE)) {
				String oracleUsername = configService.getConfigurationProperty(getDBPrefix() + KEY_ORACLE_USERNAME);
				String oraclePassword = configService.getConfigurationProperty(getDBPrefix() + KEY_ORACLE_PASSWORD);

				if (oracleUsername != null && oraclePassword != null) {
					properties.put(KEY_ORACLE_USERNAME, oracleUsername);
					properties.put(KEY_ORACLE_PASSWORD, oraclePassword);
				}
			}

			OSGiUtil.configureService(configurationAdmin, CDOPersistenceConfig.PID, properties);
		}
	}

	private String getJDBCConnnection() {
		configuredDB = DB_H2;
		String specmateJDBCConnection = configService.getConfigurationProperty(getDBPrefix() + KEY_JDBC_CONNECTION);
		if (specmateJDBCConnection == null) {
			configuredDB = DB_ORACLE;
			specmateJDBCConnection = configService.getConfigurationProperty(getDBPrefix() + KEY_JDBC_CONNECTION);
		}

		return specmateJDBCConnection;
	}

	private String getDBPrefix() {
		return configuredDB + ".";
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

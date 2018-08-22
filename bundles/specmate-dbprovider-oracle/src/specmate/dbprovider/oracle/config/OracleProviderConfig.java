package specmate.dbprovider.oracle.config;

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

@Component
public class OracleProviderConfig {
	public static final String PID = "com.specmate.dbprovider.oracle.OracleProviderConfig";
	public static final String KEY_JDBC_CONNECTION = "jdbcConnection";
	public static final String KEY_USERNAME = "username";
	public static final String KEY_PASSWORD = "password";
	public static final int MAX_ID_LENGTH = 30;
	private static final String DB_PREFIX = "oracle.";
	private ConfigurationAdmin configurationAdmin;
	private IConfigService configService;
	private LogService logService;

	@Activate
	private void configure() throws SpecmateException {
		Dictionary<String, Object> properties = new Hashtable<>();

		String specmateJDBCConnection = configService.getConfigurationProperty(DB_PREFIX + KEY_JDBC_CONNECTION);
		String specmateUsername = configService.getConfigurationProperty(DB_PREFIX + KEY_USERNAME);
		String specmatePassword = configService.getConfigurationProperty(DB_PREFIX + KEY_PASSWORD);

		if (specmateJDBCConnection != null) {
			properties.put(KEY_JDBC_CONNECTION, specmateJDBCConnection);
		}

		logService.log(LogService.LOG_DEBUG, "Configuring CDO with:\n" + OSGiUtil.configDictionaryToString(properties));

		// Don't log username/password
		if (specmateUsername != null) {
			properties.put(KEY_USERNAME, specmateUsername);
		}

		if (specmatePassword != null) {
			properties.put(KEY_PASSWORD, specmatePassword);
		}

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

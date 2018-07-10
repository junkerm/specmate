package specmate.dbprovider.h2.config;

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
public class H2ProviderConfig {

	public static final String PID = "com.specmate.dbprovider.h2.H2ProviderConfig";
	public static final String KEY_JDBC_CONNECTION = "cdoJDBCConnection";
	public static final String KEY_REPOSITORY_NAME = "cdoRepositoryName";
	public static final String KEY_RESOURCE_NAME = "cdoResourceName";
	private static final String DB_PREFIX = "h2.";
	private ConfigurationAdmin configurationAdmin;
	private IConfigService configService;
	private LogService logService;

	@Activate
	private void configure() throws SpecmateException {
		Dictionary<String, Object> properties = new Hashtable<>();

		String specmateJDBCConnection = configService.getConfigurationProperty(DB_PREFIX + KEY_JDBC_CONNECTION);
		String specmateRepository = configService.getConfigurationProperty(DB_PREFIX + KEY_REPOSITORY_NAME);
		String specmateResource = configService.getConfigurationProperty(DB_PREFIX + KEY_RESOURCE_NAME);

		if (specmateJDBCConnection != null) {
			properties.put(KEY_JDBC_CONNECTION, specmateJDBCConnection);
		}

		if (specmateRepository != null) {
			properties.put(KEY_REPOSITORY_NAME, specmateRepository);
		}

		if (specmateResource != null) {
			properties.put(KEY_RESOURCE_NAME, specmateResource);
		}

		logService.log(LogService.LOG_DEBUG, "Configuring CDO with:\n" + OSGiUtil.configDictionaryToString(properties));

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

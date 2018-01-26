package com.specmate.search.internal.config;

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
public class LuceneBasedSearchServiceConfig {

	public static final String PID = "com.specmate.search.LuceneBasedModelSearchService";
	public static final String KEY_LUCENE_DB_LOCATION = "search.lucene.location";
	private ConfigurationAdmin configurationAdmin;
	private IConfigService configService;
	private LogService logService;

	@Activate
	public void configureLuceneBasedSearchService() throws SpecmateException {
		Dictionary<String, Object> properties = new Hashtable<>();
		String luceneLocation = configService.getConfigurationProperty(KEY_LUCENE_DB_LOCATION, "20");
		properties.put(KEY_LUCENE_DB_LOCATION, luceneLocation);
		logService.log(LogService.LOG_DEBUG,
				"Configuring LuceneBasedModelSearchService with:\n" + OSGiUtil.configDictionaryToString(properties));
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

package com.specmate.search.config;

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

/**
 * Configuration service for the lucene based model search service. Reads a
 * configuration from the config service and configures the lucene based saarch
 * service accordingly.
 */
@Component(immediate = true)
public class LuceneBasedSearchServiceConfig {

	/** The PID of the luce based search service. */
	public static final String PID = "com.specmate.search.LuceneBasedModelSearchService";

	/** Config key for the location of the lucene database. */
	public static final String KEY_LUCENE_DB_LOCATION = "search.lucene.location";

	/** Config key for the maximum number of results of a single search. */
	public static final String KEY_MAX_SEARCH_RESULTS = "search.maxResults";

	/** Config key for the fields that are allowed to be searchable. */
	public static final String KEY_ALLOWED_FIELDS = "search.allowedFields";

	/** The configuration admin intance */
	private ConfigurationAdmin configurationAdmin;

	/** The config service instance */
	private IConfigService configService;

	/** The loging service instance */
	private LogService logService;

	/**
	 * Retrieves config properties from the config service and instantiates the
	 * lucene based search service
	 */
	@Activate
	public void configureLuceneBasedSearchService() throws SpecmateException {
		Dictionary<String, Object> properties = new Hashtable<>();
		String luceneLocation = configService.getConfigurationProperty(KEY_LUCENE_DB_LOCATION);
		String[] allowedFields = configService.getConfigurationPropertyArray(KEY_ALLOWED_FIELDS);
		Integer maxSearchResults = configService.getConfigurationPropertyInt(KEY_MAX_SEARCH_RESULTS);
		if (luceneLocation == null || allowedFields == null || maxSearchResults == null) {
			return;
		}

		properties.put(KEY_LUCENE_DB_LOCATION, luceneLocation);

		properties.put(KEY_MAX_SEARCH_RESULTS, maxSearchResults);

		properties.put(KEY_ALLOWED_FIELDS, allowedFields);
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

	/** Service reference of the log service */
	@Reference
	public void setLogService(LogService logService) {
		this.logService = logService;
	}
}

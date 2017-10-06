package com.specmate.emfrest.internal.config;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;

import org.apache.commons.lang3.StringUtils;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import com.specmate.common.OSGiUtil;
import com.specmate.config.api.IConfigService;

@Component(immediate = true)
public class SearchServiceConfig {
	public static final String PID = "com.specmate.SearchService";
	public static final String KEY_QUERY_TEMPLATE = "queryTemplate";

	private ConfigurationAdmin configurationAdmin;
	private IConfigService configService;
	private LogService logService;

	/** Configures the search service. */
	@Activate
	private void configureSearchService() throws IOException {
		Dictionary<String, Object> properties = new Hashtable<>();
		String queryTemplate = configService.getConfigurationProperty(KEY_QUERY_TEMPLATE);
		if (!StringUtils.isEmpty(queryTemplate)) {
			properties.put(KEY_QUERY_TEMPLATE, queryTemplate);
			logService.log(LogService.LOG_DEBUG,
					"Configuring Search Service with:\n" + OSGiUtil.configDictionaryToString(properties));

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

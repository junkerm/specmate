package com.specmate.connectors.trello.config;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import com.specmate.common.OSGiUtil;
import com.specmate.common.SpecmateException;
import com.specmate.config.api.IConfigService;

public class TrelloConnectorConfig {

	public static final String PID = "com.specmate.connectors.trello.TrelloConnector";
	public static final String KEY_BOARD_ID = "trello.boardId";
	public static final String KEY_TRELLO_KEY = "trello.key";
	public static final String KEY_TRELLO_TOKEN = "trello.token";
	private ConfigurationAdmin configurationAdmin;
	private LogService logService;
	private IConfigService configService;

	/**
	 * Configures the CDO persistency service.
	 * 
	 * @throws SpecmateException
	 */
	@Activate
	private void configureTelloConnector() throws SpecmateException {
		Dictionary<String, Object> properties = new Hashtable<>();
		String boardId = configService.getConfigurationProperty(KEY_BOARD_ID);
		String key = configService.getConfigurationProperty(KEY_TRELLO_KEY);
		String token = configService.getConfigurationProperty(KEY_TRELLO_TOKEN);

		if (boardId != null && key != null && token != null) {
			properties.put(KEY_BOARD_ID, boardId);
			properties.put(KEY_TRELLO_KEY, Integer.parseInt(token));
			properties.put(KEY_TRELLO_TOKEN, key);
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

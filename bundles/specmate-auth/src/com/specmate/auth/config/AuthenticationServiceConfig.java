package com.specmate.auth.config;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.specmate.common.OSGiUtil;
import com.specmate.common.SpecmateException;
import com.specmate.config.api.IConfigService;

@Component(immediate = true)
public class AuthenticationServiceConfig {
	/** The PID of the authentication service */
	public static final String PID = "com.specmate.auth.AuthenticationServiceImpl";
	
	/** Config key for deciding whether the sessions should be persisted or not */
	public static final String SESSION_PERSISTENT = "session.persistent"; 
	
	/** The configuration admin instance */
	private ConfigurationAdmin configurationAdmin;

	/** The config service instance */
	private IConfigService configService;
	
	@Activate
	public void activate() throws SpecmateException {
		Dictionary<String, Object> properties = new Hashtable<>();
		boolean isPersistentSession = Boolean.parseBoolean(configService.getConfigurationProperty(SESSION_PERSISTENT));
		
		if (isPersistentSession) {
			properties.put("SessionService.target", "(impl=persistent)");
		} else {
			properties.put("SessionService.target", "(impl=volatile)");
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
}

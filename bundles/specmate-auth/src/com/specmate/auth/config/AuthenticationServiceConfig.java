package com.specmate.auth.config;

import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.specmate.common.SpecmateException;
import com.specmate.common.config.SessionServiceImplementationConfig;
import com.specmate.config.api.IConfigService;

@Component(immediate = true)
public class AuthenticationServiceConfig extends SessionServiceImplementationConfig {
	/** The PID of the authentication service */
	public static final String PID = "com.specmate.auth.AuthenticationServiceImpl";

	@Override
	@Activate
	public void activate() throws SpecmateException {
		configureSessionImplementation();
	}

	@Override
	public String getPID() {
		return PID;
	}

	@Reference
	@Override
	public void setConfigurationAdmin(ConfigurationAdmin configurationAdmin) {
		this.configurationAdmin = configurationAdmin;
	}

	@Reference
	@Override
	public void setConfigurationService(IConfigService configService) {
		this.configService = configService;
	}
}

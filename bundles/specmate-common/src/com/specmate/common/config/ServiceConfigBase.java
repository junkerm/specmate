package com.specmate.common.config;

import org.osgi.service.cm.ConfigurationAdmin;

import com.specmate.common.SpecmateException;
import com.specmate.config.api.IConfigService;

public abstract class ServiceConfigBase {
	/** The configuration admin instance */
	protected ConfigurationAdmin configurationAdmin;

	/** The config service instance */
	protected IConfigService configService;

	public abstract void activate() throws SpecmateException;

	public abstract String getPID();

	/** Service reference for config admin */
	public abstract void setConfigurationAdmin(ConfigurationAdmin configurationAdmin);

	/** Service reference for config service */
	public abstract void setConfigurationService(IConfigService configService);
}

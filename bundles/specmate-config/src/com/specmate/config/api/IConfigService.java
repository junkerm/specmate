package com.specmate.config.api;

public interface IConfigService {
	public String getConfigurationProperty(String key);

	public String getConfigurationProperty(String key, String defaultValue);
}

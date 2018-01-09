package com.specmate.config.api;

import java.util.Map.Entry;
import java.util.Set;

public interface IConfigService {
	public String getConfigurationProperty(String key);

	public String getConfigurationProperty(String key, String defaultValue);

	Set<Entry<Object, Object>> getConfigurationProperties(String prefix);
}

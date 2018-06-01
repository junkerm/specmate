package com.specmate.common;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

public class OSGiUtil {

	private static final String ALL_LOCATIONS = "?";

	public static void configureService(ConfigurationAdmin configurationAdmin, String pid,
			Dictionary<String, Object> properties) throws SpecmateException {
		Configuration config;
		try {
			config = configurationAdmin.getConfiguration(pid);
			config.setBundleLocation(ALL_LOCATIONS);
			config.update(properties);
		} catch (IOException e) {
			throw new SpecmateException("Could not configure service:" + pid, e);
		}

	}

	public static String configDictionaryToString(Dictionary<String, Object> dict) {
		List<String> buffer = new LinkedList<>();
		Enumeration<String> keys = dict.keys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			Object value = dict.get(key);
			buffer.add(key + "=" + value.toString());
		}
		return StringUtils.join(buffer, "\n");
	}

	public static void configureFactory(ConfigurationAdmin configurationAdmin, String factoryPid,
			Dictionary<String, Object> properties) throws SpecmateException {
		Configuration config;
		try {
			config = configurationAdmin.createFactoryConfiguration(factoryPid, ALL_LOCATIONS);
			config.setBundleLocation(ALL_LOCATIONS);
			config.update(properties);
		} catch (IOException e) {
			throw new SpecmateException("Could not configure factory service:" + factoryPid, e);
		}

	}
}

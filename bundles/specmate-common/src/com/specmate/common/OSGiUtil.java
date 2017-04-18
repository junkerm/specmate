package com.specmate.common;

import java.io.IOException;
import java.util.Dictionary;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

public class OSGiUtil {

	private static final String ALL_LOCATIONS = "?";

	public static void configureService(ConfigurationAdmin configurationAdmin, String pid,
			Dictionary<String, Object> properties) throws IOException {
		Configuration config = configurationAdmin.getConfiguration(pid);
		config.setBundleLocation(ALL_LOCATIONS);
		config.update(properties);
	}
}

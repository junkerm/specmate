package com.specmate.common.config;

import java.util.Dictionary;
import java.util.Hashtable;

import com.specmate.common.OSGiUtil;
import com.specmate.common.SpecmateException;

public abstract class SessionServiceImplementationConfig extends ServiceConfigBase {
	/** Config key for deciding whether the sessions should be persisted or not */
	public static final String SESSION_PERSISTENT = "session.persistent";

	protected void configureSessionImplementation() throws SpecmateException {
		Dictionary<String, Object> properties = new Hashtable<>();
		boolean isPersistentSession = Boolean.parseBoolean(configService.getConfigurationProperty(SESSION_PERSISTENT));

		if (isPersistentSession) {
			properties.put("SessionService.target", "(impl=persistent)");
		} else {
			properties.put("SessionService.target", "(impl=volatile)");
		}

		OSGiUtil.configureService(configurationAdmin, getPID(), properties);

	}
}

package com.specmate.auth.internal;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Activate;

import com.specmate.auth.api.ISessionService;
import com.specmate.auth.config.SessionServiceConfig;
import com.specmate.common.SpecmateValidationException;

public abstract class BaseSessionService implements ISessionService {
	private static final Set<Character> ALLOWED_PROJECTNAME_CHARS = new HashSet<>(Arrays.asList(' ', '_', '-'));
	protected String pathPattern = ".+services/rest/%s/.*";
	protected long maxIdleMilliSeconds;
	protected RandomString randomString = new RandomString();
	
	@Activate
	public void activate(Map<String, Object> properties) throws SpecmateValidationException {
		readConfig(properties);
	}
	
	protected String sanitize(String projectName) {
		StringBuilder sb = new StringBuilder();
		if (projectName != null) {
			for (int i = 0; i < projectName.length(); i++) {
				Character c = projectName.charAt(i);
				if (Character.isLetterOrDigit(c) || ALLOWED_PROJECTNAME_CHARS.contains(c)) {
					sb.append(c);
				}
			}
		}
		
		return sb.toString();
	}
	
	private void readConfig(Map<String, Object> properties) throws SpecmateValidationException {
		String errMsg = "Missing config for %s";
		if (!properties.containsKey(SessionServiceConfig.SESSION_MAX_IDLE_MINUTES)) {
			throw new SpecmateValidationException(String.format(errMsg, SessionServiceConfig.SESSION_MAX_IDLE_MINUTES));
		} else {
			int maxIdleMinutes = (int) properties.get(SessionServiceConfig.SESSION_MAX_IDLE_MINUTES);
			maxIdleMilliSeconds = maxIdleMinutes * 60 * 1000L;
		}
	}
}
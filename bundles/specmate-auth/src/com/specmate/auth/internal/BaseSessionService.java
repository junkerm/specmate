package com.specmate.auth.internal;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.osgi.service.component.annotations.Activate;

import com.specmate.auth.api.ISessionService;
import com.specmate.auth.config.SessionServiceConfig;
import com.specmate.common.SpecmateException;
import com.specmate.common.SpecmateValidationException;
import com.specmate.usermodel.AccessRights;
import com.specmate.usermodel.UserSession;
import com.specmate.usermodel.UsermodelFactory;

public abstract class BaseSessionService implements ISessionService {
	private static final Set<Character> ALLOWED_PROJECTNAME_CHARS = new HashSet<>(Arrays.asList(' ', '_', '-'));
	protected String pathPattern = ".+services/rest/%s/.*";
	protected long maxIdleMilliSeconds;
	protected RandomString randomString = new RandomString();

	@Activate
	public void activate(Map<String, Object> properties) throws SpecmateException, SpecmateValidationException {
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

	protected UserSession createSession(AccessRights source, AccessRights target, String projectName) {
		UserSession session = UsermodelFactory.eINSTANCE.createUserSession();
		session.setSourceSystem(source);
		session.setTargetSystem(target);
		session.setAllowedPathPattern(String.format(pathPattern, projectName));
		session.setLastActive(new Date().getTime());
		String token = randomString.nextString();
		session.setId(token);
		return session;
	}

	protected boolean checkExpiration(long lastActive) {
		return (new Date().getTime() - lastActive > maxIdleMilliSeconds);
	}

	protected boolean checkAuthorization(String pattern, String path) {
		return Pattern.matches(pattern, path);
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
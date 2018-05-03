package com.specmate.auth.internal;

import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;

import com.specmate.auth.api.IAuthentificationService;
import com.specmate.auth.config.AuthentificationServiceConfig;
import com.specmate.common.SpecmateException;
import com.specmate.common.SpecmateValidationException;

/**
 * Authentification design based on this implementation: https://stackoverflow.com/a/26778123
 */
@Component(configurationPid = AuthentificationServiceConfig.PID, configurationPolicy = ConfigurationPolicy.REQUIRE,
		service = IAuthentificationService.class)
public class AuthentificationServiceImpl implements IAuthentificationService {
	private RandomString randomString = new RandomString();
	private Map<String, UserSession> userSessions = new HashMap<>();
	private int maxIdleMinutes;

	@Activate
	public void activate(Map<String, Object> properties) throws SpecmateValidationException {
		readConfig(properties);
	}
	
	@Override
	public String authenticate(String username, String password, String projectname) throws SpecmateException {
		/*
		 * TODO retrieve the access rights for this user and set in the user session.
		 */
		
		String token = randomString.nextString();
		userSessions.put(token, new UserSession(AccessRights.AUTHENTICATE_ALL, maxIdleMinutes, projectname));
		return token;
	}
	
	@Override
	public void deauthenticate(String token) throws SpecmateException {
		checkSessionExists(token);
		userSessions.remove(token);
	}
	
	@Override
	public void validateToken(String token, String path) throws SpecmateException {
		checkSessionExists(token);
		
		UserSession session = userSessions.get(token);
		if (session.isExpired()) {
			userSessions.remove(token);
			throw new SpecmateException("Session " + token + " is expired.");
		} else if (!session.isAuthorized(path)) {
			throw new SpecmateException("Session " + token + " not authorized for " + path + ".");
		} else {
			session.refresh();
		}
	}
	
	private void checkSessionExists(String token) throws SpecmateException {
		if (!userSessions.containsKey(token)) {
			throw new SpecmateException("Session " + token + " does not exist.");
		}
	}
	
	private void readConfig(Map<String, Object> properties) throws SpecmateValidationException {
		String errMsg = "Missing config for %s";
		if (!properties.containsKey(AuthentificationServiceConfig.SESSION_MAX_IDLE_MINUTES)) {
			throw new SpecmateValidationException(String.format(errMsg, AuthentificationServiceConfig.SESSION_MAX_IDLE_MINUTES));
		} else {
			this.maxIdleMinutes = (int) properties.get(AuthentificationServiceConfig.SESSION_MAX_IDLE_MINUTES);
		}
	}
}

package com.specmate.auth.internal;

import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

import com.specmate.auth.api.IAuthentificationService;
import com.specmate.common.SpecmateException;

/**
 * Authentification design based on this implementation: https://stackoverflow.com/a/26778123
 */
@Component
public class AuthentificationServiceImpl implements IAuthentificationService {
	private RandomString randomString = new RandomString();
	private Map<String, UserSession> userSessions = new HashMap<>();

	@Override
	public String authenticate(String username, String password, String projectname) throws SpecmateException {
		/*
		 * TODO retrieve the access rights for this user and set in the user session.
		 */
		
		String token = randomString.nextString();
		userSessions.put(token, new UserSession(AccessRights.AUTHENTICATE_ALL, projectname));
		return token;
	}
	
	@Override
	public void deauthenticate(String token) throws SpecmateException {
		checkSessionExists(token);
		userSessions.remove(token);
	}
	
	@Override
	public void validateToken(String token) throws SpecmateException {
		checkSessionExists(token);
		
		if (userSessions.get(token).isExpired()) {
			userSessions.remove(token);
			throw new SpecmateException("Session " + token + " is expired.");
		}
	}
	
	private void checkSessionExists(String token) throws SpecmateException {
		if (!userSessions.containsKey(token)) {
			throw new SpecmateException("Session " + token + " does not exist.");
		}
	}
}

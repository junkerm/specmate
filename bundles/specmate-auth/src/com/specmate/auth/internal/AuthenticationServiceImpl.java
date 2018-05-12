package com.specmate.auth.internal;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.specmate.auth.api.IAuthenticationService;
import com.specmate.auth.api.ISessionService;
import com.specmate.common.SpecmateException;
import com.specmate.usermodel.AccessRights;

/**
 * Authentication design based on this implementation: https://stackoverflow.com/a/26778123
 */
@Component(immediate = true, service = IAuthenticationService.class)
public class AuthenticationServiceImpl implements IAuthenticationService {
	private ISessionService sessionService;
	
	@Override
	public String authenticate(String username, String password, String projectname) throws SpecmateException {
		/*
		 * TODO retrieve the access rights for this user and set in the user session.
		 */
		
		return sessionService.create(AccessRights.ALL, projectname);
	}
	
	/**
	 * Use this method only in tests to create a session that authorizes requests to all resources.
	 */
	@Override
	public String authenticate(String username, String password) throws SpecmateException {
		return sessionService.create();
	}
	
	@Override
	public void deauthenticate(String token) throws SpecmateException {
		sessionService.delete(token);
	}
	
	@Override
	public void validateToken(String token, String path) throws SpecmateException {
		if (sessionService.isExpired(token)) {
			sessionService.delete(token);
			throw new SpecmateException("Session " + token + " is expired.");
		}
		
		if (!sessionService.isAuthorized(token, path)) {
 			throw new SpecmateException("Session " + token + " not authorized for " + path + ".");
 		}
		
		sessionService.refresh(token);
	}
	
	@Reference
	public void setSessionService(ISessionService sessionService) {
		this.sessionService = sessionService;
	}
}

package com.specmate.auth.internal;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;

import com.specmate.auth.api.ISessionService;
import com.specmate.auth.config.SessionServiceConfig;
import com.specmate.common.SpecmateException;
import com.specmate.usermodel.AccessRights;
import com.specmate.usermodel.UserSession;
import com.specmate.usermodel.UsermodelFactory;

@Component(immediate = true, service = ISessionService.class, configurationPid = SessionServiceConfig.PID, 
	configurationPolicy = ConfigurationPolicy.REQUIRE, property="impl=volatile")
public class InMemorySessionService extends BaseSessionService {
	private Map<String, UserSession> sessions = new HashMap<>();

	@Override
	public String create(AccessRights source, AccessRights target, String projectName) {
		UserSession session = createSession(source, target, projectName);
		String token = session.getId();
		sessions.put(token, session);
		return token;
	}

	@Override
	public String create() {
		UserSession session = UsermodelFactory.eINSTANCE.createUserSession();
		session.setSourceSystem(AccessRights.NONE);
		session.setTargetSystem(AccessRights.NONE);
		session.setAllowedPathPattern(".*");
		session.setLastActive(new Date().getTime());
		String token = randomString.nextString();
		session.setId(token);
		sessions.put(token, session);
		return token;
	}
	
	@Override
	public boolean isAuthorized(String token, String path) throws SpecmateException {
		checkSessionExists(token);
		return checkAuthorization(sessions.get(token).getAllowedPathPattern(), path);
	}
	
	@Override
	public AccessRights getSourceAccessRights(String token) throws SpecmateException {
		checkSessionExists(token);
		return sessions.get(token).getSourceSystem();
	}
	
	@Override
	public AccessRights getTargetAccessRights(String token) throws SpecmateException {
		checkSessionExists(token);
		return sessions.get(token).getTargetSystem();
	}

	@Override
	public boolean isExpired(String token) throws SpecmateException {
		checkSessionExists(token);
		return checkExpiration(sessions.get(token).getLastActive());
	}

	@Override
	public void refresh(String token) throws SpecmateException {
		checkSessionExists(token);
		sessions.get(token).setLastActive(new Date().getTime());
	}
	
	@Override
	public void delete(String token) throws SpecmateException {
		checkSessionExists(token);
		sessions.remove(token);
	}
	
	private void checkSessionExists(String token) throws SpecmateException {
		if (!sessions.containsKey(token)) {
			throw new SpecmateException("Session " + token + " does not exist.");
		}
	}
}
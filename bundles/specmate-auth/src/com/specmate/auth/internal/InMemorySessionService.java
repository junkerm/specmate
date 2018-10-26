package com.specmate.auth.internal;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

import com.specmate.auth.api.ISessionService;
import com.specmate.auth.config.SessionServiceConfig;
import com.specmate.common.SpecmateException;
import com.specmate.config.api.IConfigService;
import com.specmate.usermodel.AccessRights;
import com.specmate.usermodel.UserSession;
import com.specmate.usermodel.UsermodelFactory;

@Component(immediate = true, service = ISessionService.class, configurationPid = SessionServiceConfig.PID, configurationPolicy = ConfigurationPolicy.REQUIRE, property = "impl=volatile")
public class InMemorySessionService extends BaseSessionService {
	private Map<String, UserSession> sessions = new HashMap<>();

	@Override
	public UserSession create(AccessRights source, AccessRights target, String userName, String projectName) {
		UserSession session = createSession(source, target, userName, sanitize(projectName));
		String token = session.getId();
		sessions.put(token, session);
		return session;
	}

	@Override
	public UserSession create() {
		UserSession session = UsermodelFactory.eINSTANCE.createUserSession();
		session.setSourceSystem(AccessRights.NONE);
		session.setTargetSystem(AccessRights.NONE);
		session.setAllowedPathPattern(".*");
		session.setUserName("unknown");
		session.setLastActive(new Date().getTime());
		String token = randomString.nextString();
		session.setId(token);
		sessions.put(token, session);
		return session;
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

	@Override
	public String getUserName(String token) throws SpecmateException {
		return sessions.get(token).getUserName();
	}

	private void checkSessionExists(String token) throws SpecmateException {
		if (!sessions.containsKey(token)) {
			throw new SpecmateException("Session " + token + " does not exist.");
		}
	}

	@Reference
	public void setConfigService(IConfigService configService) {
		this.configService = configService;
	}
}
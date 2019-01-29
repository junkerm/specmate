package com.specmate.auth.internal;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

import com.specmate.auth.api.ISessionService;
import com.specmate.auth.config.SessionServiceConfig;
import com.specmate.common.exception.SpecmateException;
import com.specmate.common.exception.SpecmateInternalException;
import com.specmate.config.api.IConfigService;
import com.specmate.model.administration.ErrorCode;
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
	public void refresh(String token) throws SpecmateException {
		UserSession session = getSession(token);
		if (session == null) {
			throw new SpecmateInternalException(ErrorCode.USER_SESSION,
					"Invalid session when trying to refresh session.");
		}
		sessions.get(token).setLastActive(new Date().getTime());
	}

	@Override
	public void delete(String token) throws SpecmateException {
		UserSession session = getSession(token);
		if (session == null) {
			throw new SpecmateInternalException(ErrorCode.USER_SESSION,
					"Invalid session when trying to delete session.");
		}
		sessions.remove(token);
	}

	@Override
	protected UserSession getSession(String token) throws SpecmateException {
		return sessions.get(token);
	}

	@Reference
	public void setConfigService(IConfigService configService) {
		this.configService = configService;
	}
}
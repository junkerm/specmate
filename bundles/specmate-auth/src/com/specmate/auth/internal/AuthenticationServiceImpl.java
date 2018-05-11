package com.specmate.auth.internal;

import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

import com.specmate.auth.api.IAuthenticationService;
import com.specmate.auth.config.AuthenticationServiceConfig;
import com.specmate.common.SpecmateException;
import com.specmate.common.SpecmateValidationException;
import com.specmate.connectors.api.IProjectService;
import com.specmate.connectors.api.Project;

/**
 * Authentication design based on this implementation:
 * https://stackoverflow.com/a/26778123
 */
@Component(configurationPid = AuthenticationServiceConfig.PID, configurationPolicy = ConfigurationPolicy.REQUIRE, service = IAuthenticationService.class)
public class AuthenticationServiceImpl implements IAuthenticationService {
	private RandomString randomString = new RandomString();
	private Map<String, UserSession> userSessions = new HashMap<>();
	private int maxIdleMinutes;
	private IProjectService projectService;

	@Activate
	public void activate(Map<String, Object> properties) throws SpecmateValidationException {
		readConfig(properties);
	}

	@Override
	public String authenticate(String username, String password, String projectName) throws SpecmateException {

		Project project = projectService.getProject(projectName);
		boolean authenticated = project.getConnector().getRequirementsSourceService().authenticate(username, password);
		if (!authenticated) {
			throw new SpecmateException("User not authenticated");
		}

		String token = randomString.nextString();
		userSessions.put(token, new UserSession(AccessRights.AUTHENTICATE_ALL, maxIdleMinutes, projectName));
		return token;
	}

	/**
	 * Use this method only in tests to create a session that authenticates
	 * requests to all resources.
	 */
	@Override
	public String authenticate(String username, String password) throws SpecmateException {
		String token = randomString.nextString();
		userSessions.put(token, new UserSession(AccessRights.NONE, maxIdleMinutes));
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
		}

		if (!session.isAuthorized(path)) {
			throw new SpecmateException("Session " + token + " not authorized for " + path + ".");
		}

		session.refresh();
	}

	private void checkSessionExists(String token) throws SpecmateException {
		if (!userSessions.containsKey(token)) {
			throw new SpecmateException("Session " + token + " does not exist.");
		}
	}

	private void readConfig(Map<String, Object> properties) throws SpecmateValidationException {
		String errMsg = "Missing config for %s";
		if (!properties.containsKey(AuthenticationServiceConfig.SESSION_MAX_IDLE_MINUTES)) {
			throw new SpecmateValidationException(
					String.format(errMsg, AuthenticationServiceConfig.SESSION_MAX_IDLE_MINUTES));
		} else {
			this.maxIdleMinutes = (int) properties.get(AuthenticationServiceConfig.SESSION_MAX_IDLE_MINUTES);
		}
	}

	@Reference
	public void setProjectService(IProjectService projectService) {
		this.projectService = projectService;
	}
}

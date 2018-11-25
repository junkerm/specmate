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
import com.specmate.common.exception.SpecmateAuthorizationException;
import com.specmate.common.exception.SpecmateException;
import com.specmate.common.exception.SpecmateInternalException;
import com.specmate.config.api.IConfigService;
import com.specmate.connectors.api.IProjectConfigService;
import com.specmate.model.administration.ErrorCode;
import com.specmate.usermodel.AccessRights;
import com.specmate.usermodel.UserSession;
import com.specmate.usermodel.UsermodelFactory;

public abstract class BaseSessionService implements ISessionService {
	private static final Set<Character> ALLOWED_PROJECTNAME_CHARS = new HashSet<>(Arrays.asList(' ', '_', '-'));
	protected String pathPattern = ".+services/rest/%s/.*";
	protected long maxIdleMilliSeconds;
	protected RandomString randomString = new RandomString();

	/** Get access to the project configuration */
	protected IConfigService configService;

	@Activate
	public void activate(Map<String, Object> properties) throws SpecmateException {
		readConfig(properties);
	}

	@Override
	public boolean isAuthorized(String token, String path) throws SpecmateException {
		UserSession session = getSession(token);
		return session != null && checkAuthorization(session.getAllowedPathPattern(), path);
	}

	@Override
	public AccessRights getSourceAccessRights(String token) throws SpecmateException {
		UserSession session = getSession(token);
		if (session == null) {
			throw new SpecmateAuthorizationException("Invalid session when trying to retrieve source access rights.");
		}
		return session.getSourceSystem();
	}

	@Override
	public AccessRights getTargetAccessRights(String token) throws SpecmateException {
		UserSession session = getSession(token);
		if (session == null) {
			throw new SpecmateAuthorizationException("Invalid session when trying to retrieve target access rights.");
		}
		return session.getTargetSystem();
	}

	@Override
	public boolean isExpired(String token) throws SpecmateException {
		UserSession session = getSession(token);
		if (session == null) {
			throw new SpecmateAuthorizationException("Invalid session when trying to determine session expiration.");
		}
		return checkExpiration(session.getLastActive());
	}

	@Override
	public String getUserName(String token) throws SpecmateException {
		UserSession session = getSession(token);
		if (session == null) {
			throw new SpecmateAuthorizationException("Invalid session when trying to retrieve user name.");
		}
		return session.getUserName();
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

	protected UserSession createSession(AccessRights source, AccessRights target, String userName, String projectName) {
		UserSession session = UsermodelFactory.eINSTANCE.createUserSession();
		session.setSourceSystem(source);
		session.setTargetSystem(target);
		session.setAllowedPathPattern(String.format(pathPattern, projectName));
		session.setUserName(userName);
		session.setLastActive(new Date().getTime());
		String token = randomString.nextString();
		session.setId(token);

		String projectLibraryKey = IProjectConfigService.PROJECT_PREFIX + projectName
				+ IProjectConfigService.KEY_PROJECT_LIBRARY;
		String[] libraryFolderIds = configService.getConfigurationPropertyArray(projectLibraryKey);
		if (libraryFolderIds != null) {
			session.getLibraryFolders().addAll(Arrays.asList(libraryFolderIds));
		}

		return session;
	}

	protected boolean checkExpiration(long lastActive) {
		return (new Date().getTime() - lastActive > maxIdleMilliSeconds);
	}

	protected boolean checkAuthorization(String pattern, String path) {
		return Pattern.matches(pattern, path);
	}

	protected abstract UserSession getSession(String token) throws SpecmateException;

	private void readConfig(Map<String, Object> properties) throws SpecmateInternalException {
		String errMsg = "Missing config for %s";
		if (!properties.containsKey(SessionServiceConfig.SESSION_MAX_IDLE_MINUTES)) {
			throw new SpecmateInternalException(ErrorCode.CONFIGURATION,
					String.format(errMsg, SessionServiceConfig.SESSION_MAX_IDLE_MINUTES));
		} else {
			int maxIdleMinutes = (int) properties.get(SessionServiceConfig.SESSION_MAX_IDLE_MINUTES);
			maxIdleMilliSeconds = maxIdleMinutes * 60 * 1000L;
		}
	}
}
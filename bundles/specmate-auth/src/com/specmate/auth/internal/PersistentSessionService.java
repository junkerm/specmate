package com.specmate.auth.internal;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.cdo.common.id.CDOID;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

import com.specmate.auth.api.ISessionService;
import com.specmate.auth.config.SessionServiceConfig;
import com.specmate.common.SpecmateException;
import com.specmate.common.SpecmateValidationException;
import com.specmate.config.api.IConfigService;
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.persistency.IPersistencyService;
import com.specmate.persistency.ITransaction;
import com.specmate.persistency.IView;
import com.specmate.usermodel.AccessRights;
import com.specmate.usermodel.UserSession;
import com.specmate.usermodel.UsermodelFactory;

@Component(immediate = true, service = ISessionService.class, configurationPid = SessionServiceConfig.PID, configurationPolicy = ConfigurationPolicy.REQUIRE, property = "impl=persistent")
public class PersistentSessionService extends BaseSessionService {
	private static final long SESSION_REFRESH_LIMIT = 1000L * 60; // 60 seconds
	private IPersistencyService persistencyService;
	private ITransaction sessionTransaction;
	private IView sessionView;

	@Override
	@Activate
	public void activate(Map<String, Object> properties) throws SpecmateException, SpecmateValidationException {
		super.activate(properties);
		sessionTransaction = persistencyService.openTransaction();
		sessionView = persistencyService.openView();
	}

	@Deactivate
	public void deactivate() throws SpecmateException {
		if (sessionTransaction != null) {
			sessionTransaction.close();
		}

		if (sessionView != null) {
			sessionView.close();
		}
	}

	@Override
	public UserSession create(AccessRights source, AccessRights target, String userName, String projectName)
			throws SpecmateException {
		UserSession session = createSession(source, target, userName, sanitize(projectName));
		sessionTransaction.getResource().getContents().add(session);
		sessionTransaction.commit();
		return session;
	}

	@Override
	public UserSession create() {
		// Don't create unauthenticated sessions in persistent storage.
		UserSession session = UsermodelFactory.eINSTANCE.createUserSession();
		session.setAllowedPathPattern(null);
		session.setLastActive(0);
		session.setSourceSystem(AccessRights.NONE);
		session.setTargetSystem(AccessRights.NONE);
		session.setId("INVALID-SESSION");
		return session;
	}

	@Override
	public boolean isExpired(String token) throws SpecmateException {
		UserSession session = getSession(token);
		return checkExpiration(session.getLastActive());
	}

	@Override
	public boolean isAuthorized(String token, String path) throws SpecmateException {
		UserSession session = getSession(token);
		return checkAuthorization(session.getAllowedPathPattern(), path);
	}

	@Override
	public void refresh(String token) throws SpecmateException {
		UserSession session = (UserSession) sessionTransaction.getObjectById(getSessionID(token));
		long now = new Date().getTime();
		// If we let each request refresh the session, we get errors from CDO regarding
		// out-of-date revision changes.
		// Here we rate limit session refreshes. The better option would be to not store
		// revisions of UserSession objects, but this is a setting than can be only
		// applied on the
		// whole repository, which we don't want.
		// A third option would be to update sessions with an SQL query, circumventing
		// CDO and revisions altogether.
		if (session.getLastActive() - now > SESSION_REFRESH_LIMIT) {
			session.setLastActive(now);
			sessionTransaction.commit();
		}
	}

	@Override
	public AccessRights getSourceAccessRights(String token) throws SpecmateException {
		return getSession(token).getSourceSystem();
	}

	@Override
	public AccessRights getTargetAccessRights(String token) throws SpecmateException {
		return getSession(token).getTargetSystem();
	}

	@Override
	public void delete(String token) throws SpecmateException {
		UserSession session = (UserSession) sessionTransaction.getObjectById(getSessionID(token));
		SpecmateEcoreUtil.detach(session);
		sessionTransaction.commit();
	}

	@Override
	public String getUserName(String token) throws SpecmateException {
		return getSession(token).getUserName();
	}

	private UserSession getSession(String token) throws SpecmateException {
		String query = "UserSession.allInstances()->select(u | u.id='" + token + "')";

		List<Object> results = sessionView.query(query,
				UsermodelFactory.eINSTANCE.getUsermodelPackage().getUserSession());
		if (results.size() == 0) {
			throw new SpecmateException("Session " + token + " not found.");
		}
		if (results.size() > 1) {
			throw new SpecmateException("More than one session " + token + " found.");
		}

		return (UserSession) results.get(0);
	}

	private CDOID getSessionID(String token) throws SpecmateException {
		return getSession(token).cdoID();
	}

	@Reference
	public void setPersistencyService(IPersistencyService persistencyService) {
		this.persistencyService = persistencyService;
	}

	@Reference
	public void setConfigService(IConfigService configService) {
		this.configService = configService;
	}
}

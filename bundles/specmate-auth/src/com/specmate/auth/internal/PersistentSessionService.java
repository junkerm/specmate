package com.specmate.auth.internal;

import java.util.Date;
import java.util.List;

import org.eclipse.emf.cdo.common.id.CDOID;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

import com.specmate.auth.api.ISessionService;
import com.specmate.auth.config.SessionServiceConfig;
import com.specmate.common.SpecmateException;
import com.specmate.persistency.IPersistencyService;
import com.specmate.persistency.ITransaction;
import com.specmate.persistency.IView;
import com.specmate.usermodel.AccessRights;
import com.specmate.usermodel.UserSession;
import com.specmate.usermodel.UsermodelFactory;

@Component(immediate = true, service = ISessionService.class, configurationPid = SessionServiceConfig.PID, 
	configurationPolicy = ConfigurationPolicy.REQUIRE, property="impl=persistent")
public class PersistentSessionService extends BaseSessionService {
	private static final long SESSION_REFRESH_LIMIT = 1000L * 60; // 60 seconds
	private IPersistencyService persistencyService;
	
	@Override
	public String create(AccessRights accessRights, String projectName) throws SpecmateException {
		ITransaction transaction = persistencyService.openTransaction();
		UserSession session = createSession(accessRights, projectName);
		String token = session.getId(); 
		transaction.getResource().getContents().add(session);
		transaction.commit();
		transaction.close();
		return token;
	}

	@Override
	public String create() {
		// Don't create unauthenticated sessions in persistent storage.
		return "INVALID-SESSION";
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
		ITransaction transaction = persistencyService.openTransaction();
		UserSession session = (UserSession) transaction.getObjectById(getSessionID(token));
		long now = new Date().getTime();
		// If we let each request refresh the session, we get errors from CDO regarding out-of-date revision changes.
		// Here we rate limit session refreshes. The better option would be to not store revisions of UserSession 
		// objects, but this is a setting than can be only applied on the whole repository, which we don't want.
		// A third option would be to update sessions with an SQL query, circumventing CDO and revisions altogether.
		if (session.getLastActive() - now > SESSION_REFRESH_LIMIT) {
			session.setLastActive(now);
			transaction.commit();
		}
		transaction.close();
	}

	@Override
	public AccessRights getAccessRights(String token) throws SpecmateException {
		return getSession(token).getAccessRights();
	}

	@Override
	public void delete(String token) throws SpecmateException {
		ITransaction transaction = persistencyService.openTransaction();
		String query = "UserSession.allInstances()->delete(u | u.token='" + token + "')";
		transaction.query(query, UsermodelFactory.eINSTANCE.getUsermodelPackage().getUserSession());
		transaction.commit();
		transaction.close();
	}
	
	@Reference
	public void setPersistencyService(IPersistencyService persistencyService) {
		this.persistencyService = persistencyService;
	}
	
	private UserSession getSession(String token) throws SpecmateException {
		IView view = persistencyService.openView();
		String query = "UserSession.allInstances()->select(u | u.id='" + token + "')";
		
		List<Object> results = view.query(query, UsermodelFactory.eINSTANCE.getUsermodelPackage().getUserSession());
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

}

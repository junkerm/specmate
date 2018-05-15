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

@Component(service = ISessionService.class, configurationPid = SessionServiceConfig.PID, 
	configurationPolicy = ConfigurationPolicy.REQUIRE, property="impl=persistent")
public class PersistentSessionService extends BaseSessionService {
	private IPersistencyService persistencyService;

	@Override
	public String create(AccessRights accessRights, String projectName) throws SpecmateException {
		ITransaction transaction = persistencyService.openTransaction();
		UserSession session = createSession(accessRights, projectName);
		String token = session.getToken(); 
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
		session.setLastActive(new Date().getTime());
		transaction.commit();
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
		String query = "UserSession.allInstances()->select(u | u.token='" + token + "')";
		
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

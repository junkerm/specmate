package com.specmate.emfrest.authentication;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import com.specmate.auth.api.IAuthentificationService;
import com.specmate.common.SpecmateException;
import com.specmate.emfrest.api.IRestService;
import com.specmate.emfrest.api.RestServiceBase;

@Component(service = IRestService.class)
public class Login extends RestServiceBase {
	public static final String SERVICE_NAME = "login";
	private static final String USERNAME_PARAM = "username";
	private static final String PASSWORD_PARAM = "password";
	private static final String PROJECT_PARAM = "projectname";

	private IAuthentificationService authService;
	private LogService logService;
	
	@Override
	public String getServiceName() {
		return SERVICE_NAME;
	}
	
	@Override
	public boolean canGet(Object target) {
		return true;
	}
	
	@Override
	public Object get(Object object, MultivaluedMap<String, String> queryParams) {
		String username = queryParams.getFirst(USERNAME_PARAM);
		String password = queryParams.getFirst(PASSWORD_PARAM);
		String projectname = queryParams.getFirst(PROJECT_PARAM);
		
		try {
			String token = authService.authenticate(username, password, projectname);
			logService.log(LogService.LOG_INFO, "Session " + token + " for user " + username + " created.");
			return Response.ok(token).build();
		} catch (SpecmateException e) {
			logService.log(LogService.LOG_INFO, e.getMessage());
			return Response.status(Response.Status.FORBIDDEN).build();
		}
	}

	@Reference
	public void setAuthService(IAuthentificationService authService) {
		this.authService = authService;
	}
	
	@Reference
	public void setLogService(LogService logService) {
		this.logService = logService;
	}
}

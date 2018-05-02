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
public class Logout extends RestServiceBase {
	public static final String SERVICE_NAME = "logout";
	private static final String TOKEN_PARAM = "token";
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
		String token = queryParams.getFirst(TOKEN_PARAM);
		try {
			authService.deauthenticate(token);
		} catch (SpecmateException e) {
			logService.log(LogService.LOG_WARNING, e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		
		return token;
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

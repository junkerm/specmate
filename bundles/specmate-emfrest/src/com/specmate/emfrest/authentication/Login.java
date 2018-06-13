package com.specmate.emfrest.authentication;

import javax.ws.rs.core.Response;

import org.eclipse.emf.ecore.EObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import com.specmate.auth.api.IAuthenticationService;
import com.specmate.common.SpecmateException;
import com.specmate.emfrest.api.IRestService;
import com.specmate.emfrest.api.RestServiceBase;
import com.specmate.usermodel.User;

@Component(service = IRestService.class)
public class Login extends RestServiceBase {
	public static final String SERVICE_NAME = "login";

	private IAuthenticationService authService;
	private LogService logService;

	@Override
	public String getServiceName() {
		return SERVICE_NAME;
	}

	@Override
	public boolean canPost(Object object2, EObject object) {
		return true;
	}

	@Override
	public Object post(Object object, EObject object2, String token) throws SpecmateException {
		if (object2 instanceof User) {
			User user = (User) object2;
			try {
				// TODO: Remove the if stmt below (it's just for testing)
				if (user.getUserName().contains("invalid")) {
					throw new SpecmateException("Inavlid User");
				}
				token = authService.authenticate(user.getUserName(), user.getPassWord(), user.getProjectName());
				logService.log(LogService.LOG_INFO,
						"Session " + token + " for user " + user.getUserName() + " created.");
				return Response.ok(token).build();
			} catch (SpecmateException e) {
				logService.log(LogService.LOG_INFO, e.getMessage());
				return Response.status(Response.Status.FORBIDDEN).build();
			}
		} else {
			throw new SpecmateException("Invalid login data.");
		}
	}

	@Reference
	public void setAuthService(IAuthenticationService authService) {
		this.authService = authService;
	}

	@Reference
	public void setLogService(LogService logService) {
		this.logService = logService;
	}
}

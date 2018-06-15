package com.specmate.emfrest.authentication;

import java.util.List;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.specmate.auth.api.IAuthenticationService;
import com.specmate.common.SpecmateException;
import com.specmate.emfrest.api.IRestService;
import com.specmate.emfrest.api.RestServiceBase;

@Component(service = IRestService.class)
public class AccessRights extends RestServiceBase {
	public static final String SERVICE_NAME = "accessrights";
	private static final String SERVICE_PARAM = "service";
	private IAuthenticationService authService;

	@Override
	public String getServiceName() {
		return SERVICE_NAME;
	}

	@Override
	public boolean canGet(Object obj) {
		return true;
	}

	@Override
	public Object get(Object object, MultivaluedMap<String, String> queryParams, String token) {
		List<String> serviceParams = queryParams.get(SERVICE_PARAM);
		if (serviceParams.isEmpty()) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		String service = serviceParams.get(0);

		try {
			if (service.equals("import")) {
				return Response.ok(authService.getSourceAccessRights(token)).build();
			} else if (service.equals("export")) {
				return Response.ok(authService.getTargetAccessRights(token)).build();
			} else {
				return Response.status(Response.Status.BAD_REQUEST).build();
			}
		} catch (SpecmateException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@Reference
	public void setAuthService(IAuthenticationService authService) {
		this.authService = authService;
	}
}

package com.specmate.emfrest.authentication;

import java.util.List;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.specmate.auth.api.IAuthenticationService;
import com.specmate.common.RestResult;
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
	public RestResult<?> get(Object object, MultivaluedMap<String, String> queryParams, String token) {
		List<String> serviceParams = queryParams.get(SERVICE_PARAM);
		if (serviceParams.isEmpty()) {
			return new RestResult<>(Response.Status.BAD_REQUEST);
		}
		String service = serviceParams.get(0);

		try {
			if (service.equals("import")) {
				return new RestResult<>(Response.Status.OK, authService.getSourceAccessRights(token));
			} else if (service.equals("export")) {
				return new RestResult<>(Response.Status.OK, authService.getTargetAccessRights(token));
			} else {
				return new RestResult<>(Response.Status.BAD_REQUEST);
			}
		} catch (SpecmateException e) {
			return new RestResult<>(Response.Status.INTERNAL_SERVER_ERROR);
		}
	}

	@Reference
	public void setAuthService(IAuthenticationService authService) {
		this.authService = authService;
	}
}

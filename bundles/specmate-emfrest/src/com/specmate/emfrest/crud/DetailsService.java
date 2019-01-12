package com.specmate.emfrest.crud;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.specmate.auth.api.IAuthenticationService;
import com.specmate.common.exception.SpecmateException;
import com.specmate.emfrest.api.IRestService;
import com.specmate.emfrest.api.RestServiceBase;
import com.specmate.rest.RestResult;

@Component(immediate = true, service = IRestService.class)
public class DetailsService extends RestServiceBase {
	private IAuthenticationService authService;

	@Override
	public String getServiceName() {
		return "details";
	}

	@Override
	public boolean canGet(Object target) {
		return (target instanceof EObject) && !(target instanceof Resource);
	}

	@Override
	public RestResult<?> get(Object target, MultivaluedMap<String, String> queryParams, String token)
			throws SpecmateException {
		return new RestResult<>(Response.Status.OK, target);
	}

	@Override
	public boolean canPut(Object target, Object update) {
		return (target instanceof EObject) && (update instanceof EObject);
	}

	@Override
	public RestResult<?> put(Object target, Object update, String token) throws SpecmateException {
		return CrudUtil.update(target, (EObject) update, authService.getUserName(token));
	}

	@Reference
	public void setAuthService(IAuthenticationService authService) {
		this.authService = authService;
	}
}

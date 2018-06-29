package com.specmate.emfrest.crud;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.specmate.auth.api.IAuthenticationService;
import com.specmate.common.RestResult;
import com.specmate.common.SpecmateException;
import com.specmate.emfrest.api.IRestService;
import com.specmate.emfrest.api.RestServiceBase;
import com.specmate.model.support.util.SpecmateEcoreUtil;

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
	public boolean canPut(Object target, EObject object) {
		return (target instanceof EObject);
	}

	@Override
	public RestResult<?> put(Object target, EObject object, String token) throws SpecmateException {
		EObject theTarget = (EObject) target;
		SpecmateEcoreUtil.copyAttributeValues(object, theTarget);
		SpecmateEcoreUtil.copyReferences(object, theTarget);
		SpecmateEcoreUtil.unsetAllReferences(object);
		return new RestResult<>(Response.Status.OK, target, authService.getUserName(token));
	}

	@Reference
	public void setAuthService(IAuthenticationService authService) {
		this.authService = authService;
	}
}

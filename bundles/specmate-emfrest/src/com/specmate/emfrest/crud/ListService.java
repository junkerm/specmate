package com.specmate.emfrest.crud;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.specmate.auth.api.IAuthenticationService;
import com.specmate.common.SpecmateException;
import com.specmate.emfrest.api.IRestService;
import com.specmate.emfrest.api.RestServiceBase;
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.rest.RestResult;

@Component(immediate = true, service = IRestService.class)
public class ListService extends RestServiceBase {

	private IAuthenticationService authService;

	@Override
	public String getServiceName() {
		return "list";
	}

	@Override
	public boolean canGet(Object target) {
		return (target instanceof EObject || target instanceof Resource);
	}

	@Override
	public RestResult<?> get(Object target, MultivaluedMap<String, String> queryParams, String token)
			throws SpecmateException {
		return new RestResult<>(Response.Status.OK, SpecmateEcoreUtil.getChildren(target));
	}

	@Override
	public boolean canPost(Object parent, Object toAdd) {
		return (parent instanceof EObject || parent instanceof Resource) && (toAdd instanceof EObject);
	}

	@Override
	public RestResult<?> post(Object parent, Object toAdd, String token) throws SpecmateException {

		return CrudUtil.create(parent, (EObject) toAdd, authService.getUserName(token));
	}

	@Reference
	public void setAuthService(IAuthenticationService authService) {
		this.authService = authService;
	}
}

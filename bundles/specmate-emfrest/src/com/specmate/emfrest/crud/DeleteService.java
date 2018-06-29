package com.specmate.emfrest.crud;

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
public class DeleteService extends RestServiceBase {
	private IAuthenticationService authService;

	@Override
	public String getServiceName() {
		return "delete";
	}

	@Override
	public boolean canDelete(Object target) {
		return (target instanceof EObject) && !(target instanceof Resource);
	}

	@Override
	public RestResult<?> delete(Object target, String token) throws SpecmateException {
		if (target instanceof EObject && !(target instanceof Resource)) {
			SpecmateEcoreUtil.detach((EObject) target);
			return new RestResult<>(Response.Status.OK, target, authService.getUserName(token));
		} else {
			throw new SpecmateException("Attempt to delete non EObject");
		}
	}

	@Reference
	public void setAuthService(IAuthenticationService authService) {
		this.authService = authService;
	}

}

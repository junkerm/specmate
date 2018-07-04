package com.specmate.emfrest.crud;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.specmate.auth.api.IAuthenticationService;
import com.specmate.common.RestResult;
import com.specmate.common.SpecmateException;
import com.specmate.emfrest.api.IRestService;
import com.specmate.emfrest.api.RestServiceBase;

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
		return CrudUtil.delete(target, authService.getUserName(token));
	}

	@Reference
	public void setAuthService(IAuthenticationService authService) {
		this.authService = authService;
	}

}

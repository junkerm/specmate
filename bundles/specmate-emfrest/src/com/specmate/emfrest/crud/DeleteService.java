package com.specmate.emfrest.crud;

import javax.ws.rs.core.Response;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.osgi.service.component.annotations.Component;

import com.specmate.common.RestResult;
import com.specmate.common.SpecmateException;
import com.specmate.emfrest.api.IRestService;
import com.specmate.emfrest.api.RestServiceBase;
import com.specmate.model.support.util.SpecmateEcoreUtil;

@Component(immediate = true, service = IRestService.class)
public class DeleteService extends RestServiceBase {

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
			return new RestResult<>(Response.Status.OK, target);
		} else {
			throw new SpecmateException("Attempt to delete non EObject");
		}
	}
}

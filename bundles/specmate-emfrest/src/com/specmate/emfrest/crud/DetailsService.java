package com.specmate.emfrest.crud;

import javax.ws.rs.core.MultivaluedMap;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.osgi.service.component.annotations.Component;

import com.specmate.common.SpecmateException;
import com.specmate.emfrest.api.IRestService;
import com.specmate.emfrest.api.RestServiceBase;
import com.specmate.model.support.util.SpecmateEcoreUtil;

@Component(immediate = true, service = IRestService.class)
public class DetailsService extends RestServiceBase {

	@Override
	public String getServiceName() {
		return "details";
	}

	@Override
	public boolean canGet(Object target) {
		return (target instanceof EObject) && !(target instanceof Resource);
	}

	@Override
	public Object get(Object target, MultivaluedMap<String, String> queryParams, String token)
			throws SpecmateException {
		return target;
	}

	@Override
	public boolean canPut(Object target, EObject object) {
		return (target instanceof EObject);
	}

	@Override
	public Object put(Object target, EObject object, String token) {
		EObject theTarget = (EObject) target;
		SpecmateEcoreUtil.copyAttributeValues(object, theTarget);
		SpecmateEcoreUtil.copyReferences(object, theTarget);
		SpecmateEcoreUtil.unsetAllReferences(object);
		return target;
	}

}

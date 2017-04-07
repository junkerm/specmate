package com.specmate.emfrest.internal.crud;

import org.eclipse.emf.ecore.EObject;
import org.osgi.service.component.annotations.Component;

import com.specmate.common.SpecmateException;
import com.specmate.emfrest.api.IRestService;
import com.specmate.model.support.util.SpecmateEcoreUtil;

@Component(immediate = true)
public class DeleteService implements IRestService {

	@Override
	public String getServiceName() {
		return "delete";
	}

	@Override
	public boolean canGet() {
		return false;
	}

	@Override
	public Object get(Object target) {
		return null;
	}

	@Override
	public boolean canPost() {
		return false;
	}

	@Override
	public Object post(Object target, EObject object) {
		return null;
	}

	@Override
	public boolean canPut() {
		return false;
	}

	@Override
	public Object put(Object target, EObject object) {
		return null;
	}

	@Override
	public boolean canDelete() {
		return true;
	}

	@Override
	public Object delete(Object target) throws SpecmateException {
		if (target instanceof EObject) {
			SpecmateEcoreUtil.detach((EObject) target);
			return target;
		} else {
			throw new SpecmateException("Attempt to delete non EObject");
		}
	}
}

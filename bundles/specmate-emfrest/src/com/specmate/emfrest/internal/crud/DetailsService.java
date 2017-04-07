package com.specmate.emfrest.internal.crud;

import org.eclipse.emf.ecore.EObject;
import org.osgi.service.component.annotations.Component;

import com.specmate.common.AssertUtil;
import com.specmate.emfrest.api.IRestService;
import com.specmate.model.support.util.SpecmateEcoreUtil;

@Component(immediate = true)
public class DetailsService implements IRestService {

	@Override
	public String getServiceName() {
		return "details";
	}

	@Override
	public boolean canGet() {
		return true;
	}

	@Override
	public Object get(Object target) {
		return target;
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
		return true;
	}

	@Override
	public Object put(Object target, EObject object) {
		AssertUtil.assertInstanceOf(target, EObject.class);
		EObject theTarget = (EObject) target;
		SpecmateEcoreUtil.copyAttributeValues(object, theTarget);
		SpecmateEcoreUtil.copyReferences(object, theTarget);
		return target;
	}

	@Override
	public boolean canDelete() {
		return false;
	}

	@Override
	public Object delete(Object target) {
		return null;
	}
}

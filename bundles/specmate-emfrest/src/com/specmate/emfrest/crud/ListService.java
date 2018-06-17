package com.specmate.emfrest.crud;

import java.util.List;
import java.util.regex.Pattern;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.osgi.service.component.annotations.Component;

import com.specmate.common.RestResult;
import com.specmate.common.SpecmateException;
import com.specmate.common.SpecmateValidationException;
import com.specmate.emfrest.api.IRestService;
import com.specmate.emfrest.api.RestServiceBase;
import com.specmate.model.support.util.SpecmateEcoreUtil;

@Component(immediate = true, service = IRestService.class)
public class ListService extends RestServiceBase {

	private static final String CONTENTS = "contents";

	/** Pattern that describes valid object ids */
	private Pattern idPattern = Pattern.compile("[a-zA-Z_0-9\\-]*");

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
		return new RestResult<>(Response.Status.OK, getChildren(target));
	}

	private List<EObject> getChildren(Object target) throws SpecmateException {
		if (target instanceof Resource) {
			return ((Resource) target).getContents();
		} else if (target instanceof EObject) {
			return ((EObject) target).eContents();
		} else {
			throw new SpecmateException("Object is no resource and no EObject");
		}
	}

	@Override
	public boolean canPost(Object parent, EObject toAdd) {
		return (parent instanceof EObject || parent instanceof Resource);
	}

	@Override
	public RestResult<?> post(Object parent, EObject toAdd, String token) throws SpecmateValidationException {
		ValidationResult validationResult = validate(parent, toAdd);
		if (!validationResult.isValid()) {
			throw new SpecmateValidationException(validationResult.getErrorMessage());
		}
		if (parent instanceof Resource) {
			((Resource) parent).getContents().add(toAdd);
		} else if (parent instanceof EObject) {
			EObject eObjectParent = (EObject) parent;
			EStructuralFeature containmentFeature = eObjectParent.eClass().getEStructuralFeature(CONTENTS);
			if (containmentFeature.isMany()) {
				((EList<Object>) eObjectParent.eGet(containmentFeature)).add(toAdd);
			} else {
				eObjectParent.eSet(containmentFeature, toAdd);
			}
		}
		return new RestResult<>(Response.Status.OK, toAdd);
	}

	private ValidationResult validate(Object parent, EObject object) {
		String id = SpecmateEcoreUtil.getID(object);
		if (id == null) {
			return new ValidationResult(false, "Object does not have a valid Id");
		}
		if (!idPattern.matcher(id).matches()) {
			return new ValidationResult(false, "Object id may only contain letters, digits, '_' and '_'");
		}
		EObject existing;
		try {
			existing = SpecmateEcoreUtil.getEObjectWithId(id, getChildren(parent));
		} catch (SpecmateException e) {
			return new ValidationResult(false, e.getMessage());
		}
		if (existing != null) {
			return new ValidationResult(false, "Duplicate id:" + id);
		}
		return new ValidationResult(true, null);
	}

	private class ValidationResult {
		public ValidationResult(boolean isValid, String errorMessage) {
			super();
			this.isValid = isValid;
			this.errorMessage = errorMessage;
		}

		private boolean isValid;
		private String errorMessage;

		public boolean isValid() {
			return isValid;
		}

		public String getErrorMessage() {
			return errorMessage;
		}
	}
}

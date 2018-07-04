package com.specmate.emfrest.crud;

import java.util.List;
import java.util.regex.Pattern;

import javax.ws.rs.core.Response;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;

import com.specmate.common.RestResult;
import com.specmate.common.SpecmateException;
import com.specmate.common.SpecmateValidationException;
import com.specmate.model.support.util.SpecmateEcoreUtil;

public class CrudUtil {

	/** Pattern that describes valid object ids */
	private static Pattern idPattern = Pattern.compile("[a-zA-Z_0-9\\-]*");

	private static final String CONTENTS = "contents";

	public static RestResult<?> create(Object parent, EObject toAdd, String userName)
			throws SpecmateValidationException {
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
		return new RestResult<>(Response.Status.OK, toAdd, userName);
	}

	public static RestResult<?> update(Object target, EObject object, String userName) {
		EObject theTarget = (EObject) target;
		SpecmateEcoreUtil.copyAttributeValues(object, theTarget);
		SpecmateEcoreUtil.copyReferences(object, theTarget);
		SpecmateEcoreUtil.unsetAllReferences(object);
		return new RestResult<>(Response.Status.OK, target, userName);
	}

	public static RestResult<?> delete(Object target, String userName) throws SpecmateException {
		if (target instanceof EObject && !(target instanceof Resource)) {
			SpecmateEcoreUtil.detach((EObject) target);
			return new RestResult<>(Response.Status.OK, target, userName);
		} else {
			throw new SpecmateException("Attempt to delete non EObject");
		}
	}

	private static ValidationResult validate(Object parent, EObject object) {
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

	private static class ValidationResult {
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

	public static List<EObject> getChildren(Object target) throws SpecmateException {
		if (target instanceof Resource) {
			return ((Resource) target).getContents();
		} else if (target instanceof EObject) {
			return ((EObject) target).eContents();
		} else {
			throw new SpecmateException("Object is no resource and no EObject");
		}
	}

}

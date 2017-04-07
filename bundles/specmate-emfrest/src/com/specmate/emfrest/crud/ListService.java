package com.specmate.emfrest.crud;

import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.osgi.service.component.annotations.Component;

import com.specmate.common.SpecmateException;
import com.specmate.emfrest.api.IRestService;
import com.specmate.model.support.util.SpecmateEcoreUtil;

@Component(immediate = true, property = { "service.ranking=1" })
public class ListService implements IRestService {

	private static final String CONTENTS = "contents";

	/** Pattern that describes valid object ids */
	private Pattern idPattern = Pattern.compile("[a-zA-Z_0-9\\-]*");

	@Override
	public String getServiceName() {
		return "list";
	}

	@Override
	public boolean canGet() {
		return true;
	}

	@Override
	public Object get(Object target) throws SpecmateException {
		return getChildren(target);
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
	public boolean canPost() {
		return true;
	}

	@Override
	public Object post(Object parent, EObject toAdd) throws SpecmateException {
		ValidationResult validationResult = validate(parent, toAdd);
		if (!validationResult.isValid()) {
			throw new SpecmateException(validationResult.getErrorMessage());
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
		return toAdd;
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
		return false;
	}

	@Override
	public Object delete(Object target) {
		return null;
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

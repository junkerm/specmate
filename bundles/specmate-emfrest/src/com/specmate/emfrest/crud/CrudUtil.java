package com.specmate.emfrest.crud;

import static com.specmate.model.support.util.SpecmateEcoreUtil.getProjectId;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.ws.rs.core.Response;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.specmate.common.exception.SpecmateAuthorizationException;
import com.specmate.common.exception.SpecmateException;
import com.specmate.common.exception.SpecmateInternalException;
import com.specmate.common.exception.SpecmateValidationException;
import com.specmate.model.administration.ErrorCode;
import com.specmate.model.base.IContainer;
import com.specmate.model.base.IContentElement;
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.rest.RestResult;

public class CrudUtil {

	/** Pattern that describes valid object ids */
	private static Pattern idPattern = Pattern.compile("[a-zA-Z_0-9\\-]*");

	private static final String CONTENTS = "contents";

	public static RestResult<?> create(Object parent, EObject toAddObj, String userName) throws SpecmateException {
		if (toAddObj != null && !isProjectModificationRequestAuthorized(parent, toAddObj, true)) {
			throw new SpecmateAuthorizationException("User " + userName + " is not authorized to create elements.");
		}

		EObject toAdd = toAddObj;
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

	public static RestResult<?> update(Object target, EObject update, String userName) throws SpecmateException {
		if (update != null && !isProjectModificationRequestAuthorized(target, update, true)) {
			throw new SpecmateAuthorizationException("User " + userName + " is not authorized to update elements.");
		}
		EObject theTarget = (EObject) target;
		EObject theObj = update;
		SpecmateEcoreUtil.copyAttributeValues(theObj, theTarget);
		SpecmateEcoreUtil.copyReferences(theObj, theTarget);
		SpecmateEcoreUtil.unsetAllReferences(theObj);
		return new RestResult<>(Response.Status.OK, target, userName);
	}

	public static RestResult<?> duplicate(Object target) throws SpecmateException {
		EObject original = (EObject) target;
		IContentElement copy = (IContentElement) EcoreUtil.copy(original);
		IContainer parent = (IContainer) original.eContainer();
		EList<IContentElement> contents = parent.getContents();

		// Change ID
		String newID = SpecmateEcoreUtil.getIdForChild(parent, copy.eClass());
		copy.setId(newID);

		String name = copy.getName().replaceFirst("^Copy [0-9]+ of ", "");

		String prefix = "Copy ";
		String suffix = " of " + name;
		int copyNumber = 1;

		Set<String> names = contents.stream().map(e -> e.getName())
				.filter(e -> e.startsWith(prefix) && e.endsWith(suffix)).collect(Collectors.toSet());
		String newName = "";
		do {
			newName = prefix + copyNumber + suffix;
			copyNumber++;
		} while (names.contains(newName));

		copy.setName(newName);
		contents.add(copy);

		return new RestResult<>(Response.Status.OK, target);
	}

	public static RestResult<?> delete(Object target, String userName) throws SpecmateException {
		if (target instanceof EObject && !(target instanceof Resource)) {
			SpecmateEcoreUtil.detach((EObject) target);
			return new RestResult<>(Response.Status.OK, target, userName);
		} else {
			throw new SpecmateInternalException(ErrorCode.REST_SERVICE, "Attempt to delete non EObject.");
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
			throw new SpecmateInternalException(ErrorCode.REST_SERVICE, "Object is no resource and no EObject.");
		}
	}

	/**
	 * Checks whether the update is either detached from any project or is part of
	 * the same project than the object represented by this resource.
	 *
	 * @param update
	 *            The update object for which to check the project
	 * @param recurse
	 *            If true, also checks the projects for objects referenced by the
	 *            update
	 * @return
	 */
	private static boolean isProjectModificationRequestAuthorized(Object resourceObject, EObject update,
			boolean recurse) {

		if (!(resourceObject instanceof Resource) && resourceObject instanceof EObject) {
			EObject resourceEObject = (EObject) resourceObject;
			String currentProject = getProjectId(resourceEObject);
			String otherProject = getProjectId(update);
			if (!(otherProject == null || currentProject.equals(otherProject))) {
				return false;
			}
			if (recurse) {
				for (EReference reference : update.eClass().getEAllReferences()) {
					if (reference.isMany()) {
						for (EObject refObject : (List<EObject>) update.eGet(reference)) {
							if (!isProjectModificationRequestAuthorized(resourceObject, refObject, false)) {
								return false;
							}
						}
					} else {
						if (!isProjectModificationRequestAuthorized(resourceObject, (EObject) update.eGet(reference),
								false)) {
							return false;
						}
					}
				}
			}
		}

		return true;

	}

}

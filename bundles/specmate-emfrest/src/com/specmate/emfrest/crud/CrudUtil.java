package com.specmate.emfrest.crud;

import static com.specmate.model.support.util.SpecmateEcoreUtil.getProjectId;

import java.util.List;
import java.util.Set;
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
import com.specmate.model.administration.ErrorCode;
import com.specmate.model.base.IContainer;
import com.specmate.model.base.IContentElement;
import com.specmate.model.base.ISpecmateModelObject;
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.rest.RestResult;

public class CrudUtil {
	private static final String CONTENTS = "contents";

	public static RestResult<?> create(Object parent, EObject toAddObj, String userName) throws SpecmateException {
		if (toAddObj != null && !isProjectModificationRequestAuthorized(parent, toAddObj, true)) {
			throw new SpecmateAuthorizationException("User " + userName + " is not authorized to create elements.");
		}

		EObject toAdd = toAddObj;
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

	/**
	 * Copies an object recursively with all children and adds the copy to the
	 * parent of the object. The duplicate gets a name that is guaranteed to be
	 * unique within the parent.
	 *
	 * @param target
	 *            The target object that shall be duplicated
	 * @param childrenCopyBlackList
	 *            A list of element types. Child-Elements of target are only copied
	 *            if the are of a type that is not on the blacklist
	 * @return
	 * @throws SpecmateException
	 */
	public static RestResult<?> duplicate(Object target, List<Class<? extends IContainer>> childrenCopyBlackList)
			throws SpecmateException {

		EObject original = (EObject) target;
		IContainer copy = filteredCopy(childrenCopyBlackList, original);
		IContainer parent = (IContainer) original.eContainer();
		setUniqueCopyId(copy, parent);
		parent.getContents().add(copy);

		return new RestResult<>(Response.Status.OK, target);
	}

	private static IContainer filteredCopy(List<Class<? extends IContainer>> avoidRecurse, EObject original) {
		IContainer copy = (IContainer) EcoreUtil.copy(original);
		List<IContentElement> retain = copy.getContents().stream()
				.filter(el -> !avoidRecurse.stream().anyMatch(avoid -> avoid.isAssignableFrom(el.getClass())))
				.collect(Collectors.toList());
		copy.getContents().clear();
		copy.getContents().addAll(retain);
		return copy;
	}

	private static void setUniqueCopyId(IContainer copy, IContainer parent) {
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
	}

	public static RestResult<?> delete(Object target, String userName) throws SpecmateException {
		if (target instanceof EObject && !(target instanceof Resource)) {
			SpecmateEcoreUtil.detach((EObject) target);
			return new RestResult<>(Response.Status.OK, target, userName);
		} else {
			throw new SpecmateInternalException(ErrorCode.REST_SERVICE, "Attempt to delete non EObject.");
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

package com.specmate.model.support.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.specmate.common.AssertUtil;
import com.specmate.model.base.IContainer;
import com.specmate.model.base.IContentElement;

public class SpecmateEcoreUtil {
	public static void copyAttributeValues(EObject source, EObject target) {
		AssertUtil.preTrue(target.getClass().isAssignableFrom(source.getClass()));
		for (EAttribute attribute : target.eClass().getEAllAttributes()) {
			target.eSet(attribute, source.eGet(attribute));
		}
	}

	public static void copyReferences(EObject source, EObject target) {
		AssertUtil.preTrue(target.getClass().isAssignableFrom(source.getClass()));
		for (EReference reference : target.eClass().getEAllReferences()) {
			if (!reference.isContainment()) {
				target.eSet(reference, source.eGet(reference));
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static <T extends EObject> T shallowCopy(T source) {
		ShallowCopier copier = new ShallowCopier();
		return (T) copier.copy(source);
	}

	public static EObject getEObjectWithId(String id, List<? extends EObject> objects) {
		for (EObject object : objects) {
			String currentId = SpecmateEcoreUtil.getID(object);
			if (currentId != null && currentId.equals(id)) {
				return object;
			}
		}
		return null;
	}

	public static EObject getEObjectWithName(String name, List<EObject> objects) {
		for (EObject object : objects) {
			String currentNanme = SpecmateEcoreUtil.getName(object);
			if (currentNanme != null && currentNanme.equals(name)) {
				return object;
			}
		}
		return null;
	}

	public EObject getFirstRoot(Resource resource) {
		return resource.getContents().get(0);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getObjectByUriFragment(Resource resource, String id, Class<T> clazz) {
		EObject object = resource.getEObject(id);
		if (object != null & clazz.isAssignableFrom(object.getClass())) {
			return (T) object;
		} else
			return null;
	}

	public static <T> List<T> getRootObjectsByType(Resource resource, Class<T> clazz) {
		return pickInstancesOf(resource.getContents(), clazz);
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> pickInstancesOf(EList<? extends EObject> contents, Class<T> clazz) {
		ArrayList<T> list = new ArrayList<>();
		for (EObject content : contents) {
			if (clazz.isAssignableFrom(content.getClass())) {
				list.add((T) content);
			}
		}
		return list;
	}

	public static <T> T getAttributeValue(EObject object, String featureName, Class<T> clazz) {
		EStructuralFeature feature = object.eClass().getEStructuralFeature(featureName);
		if (feature != null && feature instanceof EAttribute) {
			Object result = object.eGet(feature);
			if (result == null) {
				return null;
			}
			if (clazz.isAssignableFrom(result.getClass())) {
				return clazz.cast(result);
			}
		}
		return null;
	}

	public static String getName(EObject object) {
		return getAttributeValue(object, "name", String.class);
	}

	public static String getDescription(EObject object) {
		return getAttributeValue(object, "description", String.class);
	}

	/**
	 * @return The id ob <code>object</code> or <code>null</code> if object does
	 *         not have any id
	 */
	public static String getID(EObject object) {
		return getAttributeValue(object, "id", String.class);
	}

	public static void unsetAllReferences(EObject object, Collection<EStructuralFeature> keepFeatures) {
		for (EStructuralFeature feature : object.eClass().getEAllStructuralFeatures()) {
			if (!keepFeatures.contains(feature)) {
				if (feature instanceof EReference) {
					EReference reference = (EReference) feature;
					if (!reference.isContainment() && !reference.isContainer()) {
						object.eUnset(feature);
					}
				}
			}
		}
	}

	public static void unsetAllReferences(EObject object) {
		unsetAllReferences(object, Collections.emptyList());
	}

	public static String getIdForChild(IContainer parent, EClass type) {
		int i = 1;
		String format = "%s-%d";
		EList<IContentElement> contents = parent.getContents();
		String candidate;
		do {
			candidate = String.format(format, type.getName(), i);
			i++;
		} while (getEObjectWithId(candidate, contents) != null);

		return candidate;
	}

	public static void detach(EObject object, Collection<EStructuralFeature> keepFeatures) {
		Iterator<EObject> childIterator = EcoreUtil.getAllContents(object, false);
		while (childIterator.hasNext()) {
			EObject next = childIterator.next();
			unsetAllReferences(next, keepFeatures);
		}
		unsetAllReferences(object, keepFeatures);
		EcoreUtil.remove(object);
	}

	public static void detach(EObject object) {
		detach(object, Collections.emptyList());
	}

}

@SuppressWarnings("serial")
class ShallowCopier extends EcoreUtil.Copier {
	@Override
	protected void copyContainment(EReference eReference, EObject eObject, EObject copyEObject) {
	}
}

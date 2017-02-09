package com.specmate.model.support.util;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.eclipse.emf.ecore.EClass;

import com.specmate.model.basemodel.IAnnotation;
import com.specmate.model.basemodel.IAnnotationContainer;

public class SpecmateModelUtils {
	public static <T extends IAnnotation> boolean hasAnnotation(IAnnotationContainer container, Class<T> clazz) {
		for (IAnnotation annotation : container.getAnnotations()) {
			if (clazz.isAssignableFrom(annotation.getClass())) {
				return true;
			}
		}
		return false;
	}

	public static <T extends IAnnotation> boolean hasAnnotation(IAnnotationContainer container, EClass clazz) {
		for (IAnnotation annotation : container.getAnnotations()) {
			if (clazz.isSuperTypeOf(annotation.eClass())) {
				return true;
			}
		}
		return false;
	}

	public static <T extends IAnnotation> List<T> getAnnotationsOfType(IAnnotationContainer container,
			Class<T> annotationType) {
		return SpecmateEcoreUtil.pickInstancesOf(container.getAnnotations(), annotationType);
	}

	public static <T extends IAnnotation> void clearAnnotationsOfType(IAnnotationContainer container,
			Class<T> annotationType) {
		for (T annotation : SpecmateEcoreUtil.pickInstancesOf(container.getAnnotations(), annotationType)) {
			container.getAnnotations().remove(annotation);
		}
		;
	}

	public static <T extends IAnnotation> boolean hasMatchingAnnotation(IAnnotationContainer container,
			Class<T> annotationType, Predicate<T> predicate) {
		List<T> typeInstances = SpecmateEcoreUtil.pickInstancesOf(container.getAnnotations(), annotationType);
		return typeInstances.stream().anyMatch(predicate);
	}

	public static <T extends IAnnotation> T getMatchingAnnotation(IAnnotationContainer container,
			Class<T> annotationType, Predicate<T> predicate) {
		List<T> typeInstances = SpecmateEcoreUtil.pickInstancesOf(container.getAnnotations(), annotationType);
		Optional<T> res = typeInstances.stream().filter(predicate).findFirst();
		if (res.isPresent()) {
			return res.get();
		} else
			return null;
	}
}

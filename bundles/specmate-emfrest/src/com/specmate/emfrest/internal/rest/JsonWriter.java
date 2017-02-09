package com.specmate.emfrest.internal.rest;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.specmate.emfjson.EMFJsonSerializer;
import com.specmate.emfjson.ISerializerStopPredicate;
import com.specmate.emfjson.SerializerStopPredicateBase;
import com.specmate.emfrest.internal.util.EmfRestUtil;
import com.specmate.model.support.urihandler.IURIFactory;

/** Serializes EMF object to JSON */
public class JsonWriter {
	
	/** URI factory to obtain URIs from EObjects */
	private IURIFactory factory;

	/** constructor */
	public JsonWriter(IURIFactory factory){
		this.factory=factory;
	}

	/** {@inheritDoc} */
	public long getSize(Object obj, Class<?> clazz, Type type,
			Annotation[] annotation, MediaType mediaType) {
		return -1;
	}

	/** {@inheritDoc} */
	public boolean isWriteable(Class<?> clazz, Type type,
			Annotation[] annotation, MediaType mediaType) {
		return mediaType.equals(MediaType.APPLICATION_JSON_TYPE)
				&& (EObject.class.isAssignableFrom(clazz) || List.class
						.isAssignableFrom(clazz));
	}
	
	/** {@inheritDoc} */
	public void writeTo(Object obj, Class<?> clazz, Type type,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> headers, OutputStream stream) {
		ISerializerStopPredicate predicate = getStopPredicate(annotations);
		EMFJsonSerializer serializer = new EMFJsonSerializer(factory, predicate);
		String result = null;
		if (obj instanceof EObject) {
			try {
				result = serializer.serialize((EObject) obj).toString();
			} catch (Exception e) {
				throw new WebApplicationException(e);
			}
		} else if (obj instanceof List) {
			try {
				result = serializer.serialize((List<?>) obj).toString();
			} catch (Exception e) {
				throw new WebApplicationException(e);
			}
		} else {
			EmfRestUtil.throwBadRequest("Cannot serialize " + clazz);
		}
		PrintWriter writer = new PrintWriter(stream);
		writer.write(result);
		writer.flush();
	}

	private ISerializerStopPredicate getStopPredicate(Annotation[] annotations) {
		for (Annotation annotation : annotations) {
			if (annotation.annotationType().equals(
					EMFJsonSerializerStopAt.class)) {
				final EMFJsonSerializerStopAt stopAnnotation = (EMFJsonSerializerStopAt) annotation;
				return new AnnotationStopPredicate(stopAnnotation);
			}
		}
		return new SerializerStopPredicateBase();
	}

	private class AnnotationStopPredicate implements ISerializerStopPredicate {
		private List<String> stopClasses;
		private List<String> stopFeatures;
		private int stopDepth;

		public AnnotationStopPredicate(EMFJsonSerializerStopAt stopAnnotation) {
			if (stopAnnotation.stopClass() != null) {
				stopClasses = Arrays.asList(stopAnnotation.stopClass());
			} else {
				stopClasses = Collections.emptyList();
			}

			if (stopAnnotation.stopFeature() != null) {
				stopFeatures = Arrays.asList(stopAnnotation.stopFeature());
			} else {
				stopFeatures = Collections.emptyList();
			}
			stopDepth = stopAnnotation.stopDepth();
		}

		@Override
		public boolean stopAtFeature(EStructuralFeature feature) {
			return stopFeatures.contains(feature.getName());
		}

		@Override
		public boolean stopAtDepth(int depth) {
			return stopDepth <= depth;
		}

		@Override
		public boolean stopAtClass(EClassifier clazz) {
			return stopClasses.contains(clazz.getName());
		}
	}

}

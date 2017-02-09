package com.specmate.emfrest.internal.rest;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.eclipse.emf.ecore.EObject;

import com.specmate.model.support.urihandler.IURIFactory;

/** MessageBodyWriter for EObjects */
@Provider
public class JsonEObjectWriter implements MessageBodyWriter<EObject> {
	
	/** The wrapped JsonWriter */
	private JsonWriter writer;

	/** constructor */
	public  JsonEObjectWriter(@Context IURIFactory factory) {
		this.writer = new JsonWriter(factory);
	}

	/** {@inheritDoc} */
	public long getSize(EObject obj, Class<?> clazz, Type type,
			Annotation[] annotation, MediaType mediaType) {
		return writer.getSize(obj, clazz, type, annotation, mediaType);
	}

	/** {@inheritDoc} */
	public boolean isWriteable(Class<?> clazz, Type type,
			Annotation[] annotation, MediaType mediaType) {
		return writer.isWriteable(clazz, type, annotation, mediaType);
	}

	/** {@inheritDoc} */
	public void writeTo(EObject obj, Class<?> clazz, Type type,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> headers, OutputStream stream)
			throws IOException, WebApplicationException {
		writer.writeTo(obj, clazz, type, annotations, mediaType, headers,
				stream);
	}
}

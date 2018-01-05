package com.specmate.emfrest.internal.rest;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.eclipse.emf.ecore.EObject;

import com.specmate.common.ISerializationConfiguration;
import com.specmate.urihandler.IURIFactory;

/** MessageBodyWriter for Lists */
@Provider
public class JsonListWriter implements MessageBodyWriter<List<EObject>> {

	/** The wrapped JsonWriter */
	private JsonWriter writer;

	/** constructor */
	public JsonListWriter(@Context IURIFactory factory, @Context ISerializationConfiguration serializationConfig) {
		this.writer = new JsonWriter(factory, serializationConfig);
	}

	/** {@inheritDoc} */
	@Override
	public long getSize(List<EObject> obj, Class<?> clazz, Type type, Annotation[] annotation, MediaType mediaType) {
		return writer.getSize(obj, clazz, type, annotation, mediaType);
	}

	/** {@inheritDoc} */
	@Override
	public boolean isWriteable(Class<?> clazz, Type type, Annotation[] annotation, MediaType mediaType) {
		return writer.isWriteable(clazz, type, annotation, mediaType);
	}

	/** {@inheritDoc} */
	@Override
	public void writeTo(List<EObject> obj, Class<?> clazz, Type type, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> headers, OutputStream stream) throws IOException, WebApplicationException {
		writer.writeTo(obj, clazz, type, annotations, mediaType, headers, stream);
	}
}

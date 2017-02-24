package com.specmate.emfrest.internal.rest;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

import org.eclipse.emf.ecore.EObject;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.osgi.service.log.LogService;

import com.specmate.emfjson.EMFJsonDeserializer;
import com.specmate.emfrest.internal.util.EmfRestUtil;
import com.specmate.model.support.urihandler.IObjectResolver;
import com.specmate.persistency.ITransaction;

/** MessageBodyReader that serializes JSON to EMF objects */
@Provider
public class JsonReader implements MessageBodyReader<EObject> {

	/** The transaction from where to retrieve object from the repository */
	@Inject
	ITransaction transaction;

	/** The OSGi logging service */
	@Inject
	LogService logService;

	/** The object resolver to obtain objects from URIs */
	@Inject
	IObjectResolver resolver;

	/** {@inheritDoc} */
	@Override
	public boolean isReadable(Class<?> clazz, Type type, Annotation[] annotations, MediaType mediaType) {
		return mediaType.isCompatible(MediaType.APPLICATION_JSON_TYPE)
				&& (EObject.class.isAssignableFrom(clazz) || List.class.isAssignableFrom(clazz));
	}

	/** {@inheritDoc} */
	@Override
	public EObject readFrom(Class<EObject> clazz, Type type, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, String> headers, InputStream inputStream) throws WebApplicationException {
		EObject deserializedEObject = null;
		try {
			JSONObject jsonObject = new JSONObject(new JSONTokener(new InputStreamReader(inputStream, "UTF-8")));
			EMFJsonDeserializer emfJsonDeserializer = new EMFJsonDeserializer(resolver, transaction.getResource());
			deserializedEObject = emfJsonDeserializer.deserializeEObject(jsonObject);
		} catch (Exception e) {
			logService.log(LogService.LOG_ERROR, "Could not parse the json input", e);
			EmfRestUtil.throwBadRequest("Could not parse the json input");
		}
		return deserializedEObject;
	}
}

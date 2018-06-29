package com.specmate.emfrest.api;

import javax.ws.rs.core.MultivaluedMap;

import org.eclipse.emf.ecore.EObject;

import com.specmate.common.RestResult;
import com.specmate.common.SpecmateException;
import com.specmate.common.SpecmateValidationException;

public interface IRestService extends Comparable<IRestService> {

	String getServiceName();

	boolean canGet(Object object);

	RestResult<?> get(Object object, MultivaluedMap<String, String> queryParams, String token) throws SpecmateException;

	boolean canPost(Object object2, EObject object);

	RestResult<?> post(Object object2, EObject object, String token)
			throws SpecmateException, SpecmateValidationException;

	boolean canPut(Object object2, EObject object);

	RestResult<?> put(Object object2, EObject object, String token)
			throws SpecmateException, SpecmateValidationException;

	boolean canDelete(Object object);

	RestResult<?> delete(Object object, String token) throws SpecmateException, SpecmateValidationException;

	int getPriority();

}
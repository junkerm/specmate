package com.specmate.emfrest.api;

import javax.ws.rs.core.MultivaluedMap;

import org.eclipse.emf.ecore.EObject;

import com.specmate.common.SpecmateException;
import com.specmate.common.SpecmateValidationException;

public interface IRestService extends Comparable<IRestService> {

	String getServiceName();

	boolean canGet(Object object);

	Object get(Object object, MultivaluedMap<String, String> queryParams) throws SpecmateException;

	boolean canPost(Object object2, EObject object);

	Object post(Object object2, EObject object) throws SpecmateException, SpecmateValidationException;

	boolean canPut(Object object2, EObject object);

	Object put(Object object2, EObject object) throws SpecmateException, SpecmateValidationException;

	boolean canDelete(Object object);

	Object delete(Object object) throws SpecmateException, SpecmateValidationException;

	int getPriority();

	boolean isStatusService();

}
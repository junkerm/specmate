package com.specmate.emfrest.api;

import org.eclipse.emf.ecore.EObject;

import com.specmate.common.SpecmateException;

public interface IRestService extends Comparable<IRestService> {

	String getServiceName();

	boolean canGet();

	Object get(Object object) throws SpecmateException;

	boolean canPost();

	Object post(Object object2, EObject object) throws SpecmateException;

	boolean canPut();

	Object put(Object object2, EObject object) throws SpecmateException;

	boolean canDelete();

	Object delete(Object object) throws SpecmateException;

	int getPriority();

}
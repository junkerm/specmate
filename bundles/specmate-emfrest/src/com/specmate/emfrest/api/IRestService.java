package com.specmate.emfrest.api;

import org.eclipse.emf.ecore.EObject;

import com.specmate.common.SpecmateException;

public interface IRestService {

	public String getServiceName();

	public boolean canGet();

	public Object get(Object object) throws SpecmateException;

	public boolean canPost();

	public Object post(Object object2, EObject object) throws SpecmateException;

	public boolean canPut();

	public Object put(Object object2, EObject object) throws SpecmateException;

	public boolean canDelete();

	public Object delete(Object object) throws SpecmateException;

}

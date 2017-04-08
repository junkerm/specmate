package com.specmate.emfrest.api;

import org.eclipse.emf.ecore.EObject;

import com.specmate.common.SpecmateException;

public abstract class RestServiceBase implements IRestService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.specmate.emfrest.api.IRestService2#getServiceName()
	 */
	@Override
	public abstract String getServiceName();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.specmate.emfrest.api.IRestService2#canGet()
	 */
	@Override
	public boolean canGet() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.specmate.emfrest.api.IRestService2#get(java.lang.Object)
	 */
	@Override
	public Object get(Object object) throws SpecmateException {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.specmate.emfrest.api.IRestService2#canPost()
	 */
	@Override
	public boolean canPost() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.specmate.emfrest.api.IRestService2#post(java.lang.Object,
	 * org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public Object post(Object object2, EObject object) throws SpecmateException {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.specmate.emfrest.api.IRestService2#canPut()
	 */
	@Override
	public boolean canPut() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.specmate.emfrest.api.IRestService2#put(java.lang.Object,
	 * org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public Object put(Object object2, EObject object) throws SpecmateException {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.specmate.emfrest.api.IRestService2#canDelete()
	 */
	@Override
	public boolean canDelete() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.specmate.emfrest.api.IRestService2#delete(java.lang.Object)
	 */
	@Override
	public Object delete(Object object) throws SpecmateException {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.specmate.emfrest.api.IRestService2#getPriority()
	 */
	@Override
	public int getPriority() {
		return 0;
	}

	@Override
	public int compareTo(IRestService otherService) {
		return Integer.compare(otherService.getPriority(), this.getPriority());
	}

}

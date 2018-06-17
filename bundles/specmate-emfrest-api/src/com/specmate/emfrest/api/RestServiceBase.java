package com.specmate.emfrest.api;

import javax.ws.rs.core.MultivaluedMap;

import org.eclipse.emf.ecore.EObject;

import com.specmate.common.RestResult;
import com.specmate.common.SpecmateException;
import com.specmate.common.SpecmateValidationException;

public abstract class RestServiceBase implements IRestService {

	/*
	 * (non-Javadoc)
	 *
	 * @see com.specmate.emfrest.api.IRestService#getServiceName()
	 */
	@Override
	public abstract String getServiceName();

	/*
	 * (non-Javadoc)
	 *
	 * @see com.specmate.emfrest.api.IRestService#canGet()
	 */
	@Override
	public boolean canGet(Object object) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.specmate.emfrest.api.IRestService#get(java.lang.Object)
	 */
	@Override
	public RestResult<?> get(Object object, MultivaluedMap<String, String> queryParams, String token)
			throws SpecmateException {
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.specmate.emfrest.api.IRestService#canPost()
	 */
	@Override
	public boolean canPost(Object object2, EObject object) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.specmate.emfrest.api.IRestService#post(java.lang.Object,
	 * org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public RestResult<?> post(Object parent, EObject child, String token)
			throws SpecmateException, SpecmateValidationException {
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.specmate.emfrest.api.IRestService#canPut()
	 */
	@Override
	public boolean canPut(Object object2, EObject object) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.specmate.emfrest.api.IRestService#put(java.lang.Object,
	 * org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public RestResult<?> put(Object object2, EObject object, String token)
			throws SpecmateException, SpecmateValidationException {
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.specmate.emfrest.api.IRestService#canDelete()
	 */
	@Override
	public boolean canDelete(Object object) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.specmate.emfrest.api.IRestService#delete(java.lang.Object)
	 */
	@Override
	public RestResult<?> delete(Object object, String token) throws SpecmateException, SpecmateValidationException {
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.specmate.emfrest.api.IRestService#getPriority()
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

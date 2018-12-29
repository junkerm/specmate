package com.specmate.emfrest.api;

import javax.ws.rs.core.MultivaluedMap;

import com.specmate.common.exception.SpecmateException;
import com.specmate.rest.RestResult;

public interface IRestService extends Comparable<IRestService> {

	String getServiceName();

	boolean canGet(Object object);

	RestResult<?> get(Object object, MultivaluedMap<String, String> queryParams, String token) throws SpecmateException;

	boolean canPost(Object object2, Object object);

	RestResult<?> post(Object object2, Object object, String token) throws SpecmateException;

	boolean canPut(Object object2, Object object);

	RestResult<?> put(Object object2, Object object, String token) throws SpecmateException;

	boolean canDelete(Object object);

	RestResult<?> delete(Object object, String token) throws SpecmateException;

	int getPriority();

}
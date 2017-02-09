package com.specmate.model.support.urihandler;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * Interface for URI resolvers. URI resolvers can return an EObject from an URI string. 
 * @see IURIFactory
 * @author junkerm
 *
 */
public interface IObjectResolver {
	
	/**
	 * Returns an EObject contained in <code>resource</code> using the URI <code>uri</code>. 
	 * @param uri The uri used for locating the EObject
	 * @param resource The resource that contains the object
	 */
	public EObject getObject(String uri, Resource resource);
}

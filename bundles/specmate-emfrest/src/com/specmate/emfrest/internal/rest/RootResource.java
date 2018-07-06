package com.specmate.emfrest.internal.rest;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.Path;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;

import org.eclipse.emf.ecore.EObject;
import org.osgi.framework.BundleContext;
import org.osgi.service.log.LogService;

import com.specmate.persistency.ITransaction;
import com.specmate.urihandler.IURIFactory;

/**
 * Resource representing the root of the EMF object tree
 *
 * @author junkerm
 */
@Path("/rest")
public class RootResource extends SpecmateResource {

	/** The current transaction to retrieve and store objects */
	@Inject
	ITransaction transaction;

	/** The OSGi logging service */
	@Inject
	LogService logService;

	/** The OSGI bundle context of the containing bundle */
	@Inject
	BundleContext bundleContext;

	/** Factory to retrieve URIs from objects */
	@Inject
	IURIFactory uriFactory;

	/** Context of the servlet serving the current request */
	@Context
	ServletContext servletContext;

	/** Context of this resource */
	@Context
	ResourceContext resourceContext;

	/** Returns all direct children of the transaction's (EMF) resource */
	@Override
	protected List<EObject> doGetChildren() {
		return transaction.getResource().getContents();
	}

	@Override
	Object getResourceObject() {
		return transaction.getResource();
	}

}

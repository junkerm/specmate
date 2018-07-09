package com.specmate.emfrest.internal.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;

import org.eclipse.emf.ecore.EObject;
import org.osgi.framework.BundleContext;
import org.osgi.service.log.LogService;

import com.specmate.urihandler.IURIFactory;

public class InstanceResource extends SpecmateResource {

	/** The context of this resource */
	@Context
	ResourceContext resourceContext;

	/** The OSGi logging service */
	@Inject
	LogService logService;

	/** The OSGi bundle context */
	@Inject
	BundleContext context;

	/** Factory used to generate URIs for model objects */
	@Inject
	IURIFactory uriFactory;

	private EObject instance;

	/** Returns the model instance that this resource refers to. */
	public EObject getModelInstance() {
		return instance;
	}

	/** Sets the model instance that this resource refers to. */
	public void setModelInstance(EObject instance) {
		this.instance = instance;
	}

	@Override
	protected List<EObject> doGetChildren() {
		return this.getModelInstance().eContents();
	}

	@Override
	Object getResourceObject() {
		return this.instance;
	}

}

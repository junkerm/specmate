package com.specmate.emfrest.internal.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.emf.ecore.EObject;
import org.osgi.service.log.LogService;

import com.specmate.common.SpecmateException;
import com.specmate.emfrest.internal.util.EmfRestUtil;
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.persistency.ITransaction;

/** Base class for all list-type resources */
public abstract class ListResource {
	
	/** context */
	@Context ResourceContext resourceContext;
	
	/** Transaction for retrieving and manipulating the object repository */
	@Context ITransaction transaction;
	
	/** OSGi logging service */
	@Inject LogService logService;

	@Path("{id:[^_][^/]*}")
	public InstanceResource getObjectById(@PathParam("id") String id){
		List<EObject> objects = getList();
		EObject object = SpecmateEcoreUtil.getEObjectWithId(id, objects);
		if (object == null) {
			throw EmfRestUtil.throwNotFound("Resource not found");
		} else {
			InstanceResource resource = resourceContext.getResource(InstanceResource.class);
			resource.setModelInstance(object);
			return resource;
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<EObject> getAll(){
		return getList();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addObject( EObject object){
		addToList(object);
		try {
			transaction.commit();
			return Response.ok().build();
		} catch (SpecmateException e) {
			logService.log(LogService.LOG_ERROR,"Error while committing", e);
			throw EmfRestUtil.throwInternalServerError("Commit error");
		}
	}

	/**
	 * Retrieves the list of objects for this resource
	 * @return
	 */
	abstract protected List<EObject> getList();

	/** 
	 * Addas an object to the resources list
	 * @param object
	 */
	abstract protected void addToList(EObject object);

}

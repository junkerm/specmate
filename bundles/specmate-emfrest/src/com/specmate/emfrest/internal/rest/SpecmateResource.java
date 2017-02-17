package com.specmate.emfrest.internal.rest;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
import com.specmate.model.support.commands.ICommand;
import com.specmate.model.support.commands.ICommandService;
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.persistency.ITransaction;

/** Base class for all list-type resources */
public abstract class SpecmateResource {

	/** context */
	@Context
	ResourceContext resourceContext;

	/** Transaction for retrieving and manipulating the object repository */
	@Context
	ITransaction transaction;

	/** OSGi logging service */
	@Inject
	LogService logService;

	/**
	 * Service to get commands for adding, updating and removing model elements
	 */
	@Inject
	ICommandService commandService;

	/** The model object that this resource relates to */
	private EObject instance;
	

	/** Returns the model instance that this resource refers to. */
	public EObject getModelInstance() {
		return instance;
	}

	/** Sets the model instance that this resource refers to. */
	public void setModelInstance(EObject instance) {
		this.instance = instance;
	}

	@Path("/details")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public final EObject getContent() {
		return instance;
	}

	@Path("/details")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public final void updateContent(EObject update) {
		doUpdateContent(update);
	}

	@Path("/list")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public final List<EObject> getChildren() {
		return doGetChildren();
	}

	@Path("/list")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public final Response addObject(EObject object) {
		doAddObject(object);
		try {
			transaction.commit();
			return Response.ok().build();
		} catch (SpecmateException e) {
			logService.log(LogService.LOG_ERROR, "Error while committing", e);
			throw EmfRestUtil.throwInternalServerError("Commit error");
		}
	}

	@Path("/list")
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	public final Response removeObject(EObject object) {
		List<EObject> objects = getChildren();
		String name = SpecmateEcoreUtil.getName(object);
		EObject toRemove = SpecmateEcoreUtil.getEObjectWithName(name, objects);

		if (toRemove == null) {
			throw EmfRestUtil.throwNotFound("Object to delete not found");
		} else {
			Optional<ICommand> deleteCommand = commandService.getDeleteCommand(instance);
			if (deleteCommand.isPresent()) {
				try {
					deleteCommand.get().execute();
				} catch (SpecmateException s) {
					logService.log(LogService.LOG_ERROR, "Delete command failed", s);
					transaction.rollback();
					EmfRestUtil.throwInternalServerError("Delete command failed");
					return null;
				}
				try {
					transaction.commit();
					return Response.ok().build();
				} catch (SpecmateException e) {
					logService.log(LogService.LOG_ERROR, "Error while committing", e);
					EmfRestUtil.throwInternalServerError("Commit error");
					return null;
				}
			} else {
				EmfRestUtil.throwMethodNotAllowed();
				return null;
			}
		}

	}

	@Path("/{name:[^_][^/]*(?=/)}")
	public InstanceResource getObjectById(@PathParam("name") String name) {
		List<EObject> objects = getChildren();
		EObject object = SpecmateEcoreUtil.getEObjectWithName(name, objects);
		if (object == null) {
			throw EmfRestUtil.throwNotFound("Resource not found");
		} else {
			InstanceResource resource = resourceContext.getResource(InstanceResource.class);
			resource.setModelInstance(object);
			return resource;
		}
	}

	/**
	 * Retrieves the list of objects for this resource
	 * 
	 * @return
	 */
	abstract protected List<EObject> doGetChildren();

	/**
	 * Adds an object as child
	 * 
	 * @param object
	 */
	abstract protected void doAddObject(EObject object);

	/**
	 * Updates the object with the given object data
	 * 
	 * @param object
	 */
	abstract protected void doUpdateContent(EObject object);

}

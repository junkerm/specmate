package com.specmate.emfrest.internal.rest;

import java.util.List;

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
import javax.ws.rs.core.Response.Status;

import org.eclipse.emf.ecore.EObject;
import org.osgi.service.log.LogService;

import com.specmate.common.SpecmateException;
import com.specmate.emfrest.api.IRestService;
import com.specmate.emfrest.internal.RestServiceProvider;
import com.specmate.emfrest.internal.util.EmfRestUtil;
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.persistency.ITransaction;

/** Base class for all list-type resources */
public abstract class SpecmateResource {

	private static final String SERVICE_KEY = "service";

	private static final String SERVICE_PATTERN = "/{" + SERVICE_KEY + ":[^_][^/]*}";

	/** context */
	@Context
	ResourceContext resourceContext;

	/** Transaction for retrieving and manipulating the object repository */
	@Context
	ITransaction transaction;

	@Inject
	RestServiceProvider serviceProvider;

	/** OSGi logging service */
	@Inject
	LogService logService;

	@Path(SERVICE_PATTERN)
	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public final Object get(@PathParam(SERVICE_KEY) String serviceName) {
		IRestService service = serviceProvider.getRestService(serviceName);
		if (service == null) {
			return Response.status(Status.NOT_FOUND).build();
		} else if (!service.canGet()) {
			return Response.status(Status.METHOD_NOT_ALLOWED).build();
		} else {
			try {
				return service.get(getResourceObject());
			} catch (SpecmateException e) {
				return Response.status(Status.BAD_REQUEST).build();
			}
		}
	}

	@Path(SERVICE_PATTERN)
	@PUT
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Consumes(MediaType.APPLICATION_JSON)
	public final Object put(@PathParam(SERVICE_KEY) String serviceName, EObject update) {
		IRestService service = serviceProvider.getRestService(serviceName);
		if (service == null) {
			return Response.status(Status.NOT_FOUND).build();
		} else if (!service.canPut()) {
			return Response.status(Status.METHOD_NOT_ALLOWED).build();
		} else {
			Object putResult;
			try {
				putResult = service.put(getResourceObject(), update);
			} catch (SpecmateException e) {
				transaction.rollback();
				return Response.status(Status.BAD_REQUEST).build();
			}
			try {
				transaction.commit();
				return putResult;
			} catch (SpecmateException e) {
				transaction.rollback();
				return Response.status(Status.BAD_REQUEST).build();
			}

		}
	}

	@Path(SERVICE_PATTERN)
	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Consumes(MediaType.APPLICATION_JSON)
	public final Object post(@PathParam(SERVICE_KEY) String serviceName, EObject posted) {
		IRestService service = serviceProvider.getRestService(serviceName);
		if (service == null) {
			return Response.status(Status.NOT_FOUND).build();
		} else if (!service.canPost()) {
			return Response.status(Status.METHOD_NOT_ALLOWED).build();
		} else {
			Object postResult;
			try {
				postResult = service.post(getResourceObject(), posted);
			} catch (SpecmateException e) {
				transaction.rollback();
				return Response.status(Status.BAD_REQUEST).build();
			}
			try {
				transaction.commit();
				return postResult;
			} catch (SpecmateException e) {
				transaction.rollback();
				return Response.status(Status.INTERNAL_SERVER_ERROR);
			}
		}

	}

	@Path(SERVICE_PATTERN)
	@DELETE
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Consumes(MediaType.APPLICATION_JSON)
	public final Object delete(@PathParam(SERVICE_KEY) String serviceName) {
		IRestService service = serviceProvider.getRestService(serviceName);
		if (service == null) {
			return Response.status(Status.NOT_FOUND).build();
		} else if (!service.canDelete()) {
			return Response.status(Status.METHOD_NOT_ALLOWED).build();
		} else {
			Object deleteResult;
			try {
				deleteResult = service.delete(getResourceObject());
			} catch (SpecmateException e) {
				transaction.rollback();
				return Response.status(Status.BAD_REQUEST).build();
			}
			try {
				transaction.commit();
				return deleteResult;
			} catch (SpecmateException e) {
				transaction.rollback();
				return Response.status(Status.INTERNAL_SERVER_ERROR);
			}
		}
	}

	@Path("/{id:[^_][^/]*(?=/)}")
	public InstanceResource getObjectById(@PathParam("id") String name) {
		List<EObject> objects = doGetChildren();
		EObject object = SpecmateEcoreUtil.getEObjectWithId(name, objects);
		if (object == null) {
			throw EmfRestUtil.throwNotFound("Resource not found: " + name);
		} else {
			InstanceResource resource = resourceContext.getResource(InstanceResource.class);
			resource.setModelInstance(object);
			return resource;
		}
	}

	abstract Object getResourceObject();

	/**
	 * Retrieves the list of objects for this resource
	 * 
	 * @return
	 */
	abstract protected List<EObject> doGetChildren();

}

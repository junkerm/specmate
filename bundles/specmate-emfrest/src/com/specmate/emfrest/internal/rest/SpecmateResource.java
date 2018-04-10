package com.specmate.emfrest.internal.rest;

import java.util.List;
import java.util.SortedSet;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
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
import javax.ws.rs.core.UriInfo;

import org.eclipse.emf.ecore.EObject;
import org.osgi.service.log.LogService;

import com.specmate.administration.api.IStatusService;
import com.specmate.common.SpecmateException;
import com.specmate.common.SpecmateValidationException;
import com.specmate.emfrest.api.IRestService;
import com.specmate.emfrest.internal.RestServiceProvider;
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

	@Inject
	IStatusService statusService;

	/** OSGi logging service */
	@Inject
	LogService logService;

	@Path(SERVICE_PATTERN)
	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public final Object get(@PathParam(SERVICE_KEY) String serviceName, @Context UriInfo uriInfo) {
		return handleRequest(serviceName, s -> s.canGet(getResourceObject()),
				s -> s.get(getResourceObject(), uriInfo.getQueryParameters()), false);
	}

	@Path(SERVICE_PATTERN)
	@PUT
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Consumes(MediaType.APPLICATION_JSON)
	public final Object put(@PathParam(SERVICE_KEY) String serviceName, EObject update) {
		return handleRequest(serviceName, s -> s.canPut(getResourceObject(), update),
				s -> s.put(getResourceObject(), update), true);
	}

	@Path(SERVICE_PATTERN)
	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Consumes(MediaType.APPLICATION_JSON)
	public final Object post(@PathParam(SERVICE_KEY) String serviceName, EObject posted) {
		return handleRequest(serviceName, s -> s.canPost(getResourceObject(), posted),
				s -> s.post(getResourceObject(), posted), true);
	}

	@Path(SERVICE_PATTERN)
	@DELETE
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Consumes(MediaType.APPLICATION_JSON)
	public final Object delete(@PathParam(SERVICE_KEY) String serviceName) {
		return handleRequest(serviceName, s -> s.canDelete(getResourceObject()), s -> s.delete(getResourceObject()),
				true);
	}

	private Object handleRequest(String serviceName, RestServiceChecker checkRestService,
			RestServiceExcecutor executeRestService, boolean commitTransaction) {
		SortedSet<IRestService> services = serviceProvider.getAllRestServices(serviceName);
		if (services.isEmpty()) {
			logService.log(LogService.LOG_ERROR, "No suitable service found.");
			return Response.status(Status.NOT_FOUND).build();
		}
		for (IRestService service : services) {
			if (checkRestService.checkIfApplicable(service)) {
				if (commitTransaction && statusService.getCurrentStatus().isReadOnly() && !service.isStatusService()) {
					logService.log(LogService.LOG_ERROR, "Attempt to access writing resource when in read-only mode");
					return Response.status(Status.FORBIDDEN).build();
				}
				Object result;
				if (!commitTransaction) {
				try {
					result = executeRestService.executeRestService(service);
						return result;
				} catch (SpecmateException e) {
					transaction.rollback();
					logService.log(LogService.LOG_ERROR, e.getLocalizedMessage());
					return Response.status(Status.INTERNAL_SERVER_ERROR).build();
				} catch (SpecmateValidationException e) {
					transaction.rollback();
					logService.log(LogService.LOG_ERROR, e.getLocalizedMessage());
					return Response.status(Status.BAD_REQUEST).build();
				}
				} else {
	
			}
		}
		}
		return Response.status(Status.NOT_FOUND).build();
	}

	@Path("/{id:[^_][^/]*(?=/)}")
	public Object getObjectById(@PathParam("id") String name, @Context HttpServletRequest httpRequest) {
		List<EObject> objects = doGetChildren();
		EObject object = SpecmateEcoreUtil.getEObjectWithId(name, objects);
		if (object == null) {
			logService.log(LogService.LOG_ERROR, "Resource not found:" + httpRequest.getRequestURL());
			return Response.status(Status.NOT_FOUND).build();
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

	@FunctionalInterface
	private interface RestServiceChecker {
		boolean checkIfApplicable(IRestService service);
	}

	@FunctionalInterface
	private interface RestServiceExcecutor {
		Object executeRestService(IRestService service) throws SpecmateException, SpecmateValidationException;
	}
}

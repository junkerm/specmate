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
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.eclipse.emf.ecore.EObject;
import org.osgi.service.log.LogService;

import com.specmate.administration.api.IStatusService;
import com.specmate.common.exception.SpecmateAuthorizationException;
import com.specmate.common.exception.SpecmateException;
import com.specmate.common.exception.SpecmateValidationException;
import com.specmate.emfrest.api.IRestService;
import com.specmate.emfrest.internal.RestServiceProvider;
import com.specmate.emfrest.internal.auth.AuthorizationHeader;
import com.specmate.emfrest.internal.auth.Secured;
import com.specmate.metrics.IHistogram;
import com.specmate.metrics.IMetricsService;
import com.specmate.metrics.ITimer;
import com.specmate.model.administration.AdministrationFactory;
import com.specmate.model.administration.ErrorCode;
import com.specmate.model.administration.ProblemDetail;
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.persistency.ITransaction;
import com.specmate.rest.RestResult;

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

	@Inject
	IMetricsService metricsService;

	/** OSGi logging service */
	@Inject
	LogService logService;

	@Secured
	@Path(SERVICE_PATTERN)
	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public final Object get(@PathParam(SERVICE_KEY) String serviceName, @Context UriInfo uriInfo,
			@Context HttpHeaders headers) {

		return handleRequest(serviceName, s -> s.canGet(getResourceObject()),
				s -> s.get(getResourceObject(), uriInfo.getQueryParameters(), getAuthenticationToken(headers)), false);

	}

	@Secured
	@Path(SERVICE_PATTERN)
	@PUT
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Consumes(MediaType.APPLICATION_JSON)
	public final Object put(@PathParam(SERVICE_KEY) String serviceName, EObject update, @Context HttpHeaders headers) {
		return handleRequest(serviceName, s -> s.canPut(getResourceObject(), update),
				s -> s.put(getResourceObject(), update, getAuthenticationToken(headers)), true);

	}

	@Secured
	@Path(SERVICE_PATTERN)
	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Consumes(MediaType.APPLICATION_JSON)
	public final Object post(@PathParam(SERVICE_KEY) String serviceName, EObject posted, @Context HttpHeaders headers) {
		return handleRequest(serviceName, s -> s.canPost(getResourceObject(), posted),
				s -> s.post(getResourceObject(), posted, getAuthenticationToken(headers)), true);

	}

	@Secured
	@Path(SERVICE_PATTERN)
	@DELETE
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Consumes(MediaType.APPLICATION_JSON)
	public final Object delete(@PathParam(SERVICE_KEY) String serviceName, @Context HttpHeaders headers) {
		return handleRequest(serviceName, s -> s.canDelete(getResourceObject()),
				s -> s.delete(getResourceObject(), getAuthenticationToken(headers)), true);

	}

	@Secured
	@Path("/batch")
	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Consumes(MediaType.APPLICATION_JSON)
	public final Object batch(String postedJson, @Context HttpHeaders headers) {
		return handleRequest("batch", s -> s.canPost(getResourceObject(), postedJson),
				s -> s.post(getResourceObject(), postedJson, getAuthenticationToken(headers)), true);

	}

	private String getAuthenticationToken(HttpHeaders headers) {
		String authorizationHeader = AuthorizationHeader.getFrom(headers);
		if (!AuthorizationHeader.isTokenBasedAuthentication(authorizationHeader)) {
			return null;
		}

		return AuthorizationHeader.extractTokenFrom(authorizationHeader);
	}

	private Object handleRequest(String serviceName, RestServiceChecker checkRestService,
			RestServiceExcecutor<?> executeRestService, boolean commitTransaction) {

		SortedSet<IRestService> services = serviceProvider.getAllRestServices(serviceName);

		for (IRestService service : services) {
			if (!checkRestService.checkIfApplicable(service)) {
				logService.log(LogService.LOG_ERROR,
						"Service " + serviceName + " cannot perform the requested operation.");

				Status status = Status.METHOD_NOT_ALLOWED;
				ProblemDetail pd = AdministrationFactory.eINSTANCE.createProblemDetail();
				pd.setStatus(status.getStatusCode());
				pd.setEcode(ErrorCode.METHOD_NOT_ALLOWED);
				pd.setDetail(serviceName);

				return Response.status(status).entity(pd).build();
			}

			if (commitTransaction && statusService.getCurrentStatus().isReadOnly()
					&& !(service instanceof IStatusService)) {
				logService.log(LogService.LOG_ERROR, "Attempt to access writing resource when in read-only mode");

				Status status = Status.SERVICE_UNAVAILABLE;
				ProblemDetail pd = AdministrationFactory.eINSTANCE.createProblemDetail();
				pd.setStatus(status.getStatusCode());
				pd.setEcode(ErrorCode.IN_MAINTENANCE_MODE);

				return Response.status(status).entity(pd).build();
			}

			IHistogram histogram;
			ITimer timer = null;
			try {
				histogram = metricsService.createHistogram(service.getServiceName(),
						"Service time for service " + service.getServiceName());
				timer = histogram.startTimer();
			} catch (SpecmateException e) {
				logService.log(LogService.LOG_ERROR, "Could not obtain metric.", e);
			}

			try {

				RestResult<?> result;

				try {
					if (commitTransaction) {
						result = transaction.doAndCommit(() -> executeRestService.executeRestService(service));
						return result.getResponse();
					} else {
						result = executeRestService.executeRestService(service);
						return result.getResponse();
					}
				} catch (SpecmateValidationException e) {
					transaction.rollback();

					logService.log(LogService.LOG_ERROR, e.getMessage());

					Status status = Status.BAD_REQUEST;
					ProblemDetail pd = AdministrationFactory.eINSTANCE.createProblemDetail();
					pd.setStatus(status.getStatusCode());
					pd.setEcode(e.getErrorcode());
					pd.setDetail(e.getValidatorName());
					pd.setInstance(e.getValidatedObjectName());

					return Response.status(status).entity(pd).build();
				} catch (SpecmateAuthorizationException e) {
					transaction.rollback();
					logService.log(LogService.LOG_ERROR, e.getMessage());

					Status status = Status.UNAUTHORIZED;
					ProblemDetail pd = AdministrationFactory.eINSTANCE.createProblemDetail();
					pd.setStatus(status.getStatusCode());
					pd.setEcode(e.getErrorcode());
					pd.setDetail(e.getMessage());

					return Response.status(status).entity(pd).build();

				} catch (SpecmateException e) {
					transaction.rollback();
					logService.log(LogService.LOG_ERROR, e.getMessage());

					Status status = Status.INTERNAL_SERVER_ERROR;
					ProblemDetail pd = AdministrationFactory.eINSTANCE.createProblemDetail();
					pd.setStatus(status.getStatusCode());
					pd.setEcode(e.getErrorcode());
					pd.setDetail(e.getMessage());

					return Response.status(status).entity(pd).build();
				}

			} finally {
				if (timer != null) {
					timer.observeDuration();
				}
			}
		}

		logService.log(LogService.LOG_ERROR, "No suitable service found.");

		Status status = Status.NOT_FOUND;
		ProblemDetail pd = AdministrationFactory.eINSTANCE.createProblemDetail();
		pd.setStatus(status.getStatusCode());
		pd.setEcode(ErrorCode.NO_SUCH_SERVICE);
		pd.setDetail(serviceName);

		return Response.status(status).entity(pd).build();
	}

	@Path("/{id:[^_][^/]*(?=/)}")
	public Object getObjectById(@PathParam("id") String name, @Context HttpServletRequest httpRequest) {
		List<EObject> objects = doGetChildren();
		EObject object = SpecmateEcoreUtil.getEObjectWithId(name, objects);
		if (object == null) {
			logService.log(LogService.LOG_ERROR, "Resource not found:" + httpRequest.getRequestURL());

			Status status = Status.NOT_FOUND;
			ProblemDetail pd = AdministrationFactory.eINSTANCE.createProblemDetail();
			pd.setStatus(status.getStatusCode());
			// pd.setType(EErrorCode.NS.toString());
			pd.setDetail(httpRequest.getRequestURI());

			return Response.status(status).entity(pd).build();
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
	private interface RestServiceExcecutor<T> {
		RestResult<?> executeRestService(IRestService service) throws SpecmateException;
	}
}

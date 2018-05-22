package com.specmate.emfrest.internal.rest;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.emf.ecore.EObject;
import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.SseFeature;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.event.EventHandler;
import org.osgi.service.log.LogService;

import com.specmate.common.SpecmateException;
import com.specmate.emfrest.internal.Secured;
import com.specmate.persistency.ITransaction;
import com.specmate.urihandler.IURIFactory;

public class InstanceResource extends SpecmateResource {

	/** The context of this resource */
	@Context
	ResourceContext resourceContext;

	/** The OSGi logging service */
	@Inject
	LogService logService;

	/** The current model repository transaction */
	@Inject
	ITransaction transaction;

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

	@Secured
	@Path("/_events")
	@GET
	@Produces(SseFeature.SERVER_SENT_EVENTS)
	public Object getServerSentEvents() {
		String uri;
		try {
			uri = uriFactory.getURI(getModelInstance());
		} catch (SpecmateException e) {
			logService.log(LogService.LOG_ERROR, "Could not retrieve uri for object" + getModelInstance());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return registerEventHandler(uri.replace(".", "_"));
	}

	/**
	 * Registers a new event handler for change events of the model instance and
	 * its children.
	 */
	private EventOutput registerEventHandler(String uri) {
		EventOutput eventOutput = new EventOutput();

		ModelEventHandler handler = new ModelEventHandler(eventOutput, uriFactory, logService);
		Dictionary<String, Object> properties = new Hashtable<>();
		uri = uri.replace(".", "_");
		String[] topics = new String[] { "com/specmate/model/notification/" + uri + "/*",
				"com/specmate/model/notification/" + uri };
		properties.put("event.topics", topics);
		ServiceRegistration<EventHandler> registration = context.registerService(EventHandler.class, handler,
				properties);
		handler.setRegistration(registration);
		return eventOutput;
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

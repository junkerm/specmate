package com.specmate.emfrest.internal.rest;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.SseFeature;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.event.EventHandler;
import org.osgi.service.log.LogService;

import com.specmate.common.SpecmateException;
import com.specmate.emfrest.internal.util.EmfRestUtil;
import com.specmate.model.support.commands.ICommand;
import com.specmate.model.support.commands.ICommandService;
import com.specmate.model.support.urihandler.IURIFactory;
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.persistency.ITransaction;

public class InstanceResource {

	/** The model object that this resource relates to */
	private EObject instance;

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

	/**
	 * Service to get commands for adding, updating and removing model elements
	 */
	@Inject
	ICommandService commandService;

	/** Returns the model instance that this resource refers to. */
	public EObject getModelInstance() {
		return instance;
	}

	/** Sets the model instance that this resource refers to. */
	public void setModelInstance(EObject storedObject) {
		this.instance = storedObject;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public EObject getContent() {
		return instance;
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateContent(EObject update) {
		Optional<ICommand> updateCommand = commandService.getUpdateCommand(instance, update);
		if (updateCommand.isPresent()) {
			try {
				updateCommand.get().execute();

				// unset all features to avoid dangling references on this
				// temporary object
				SpecmateEcoreUtil.unsetAllReferences(update);

			} catch (SpecmateException s) {
				logService.log(LogService.LOG_ERROR, "Update command failed", s);
				transaction.rollback();
				EmfRestUtil.throwInternalServerError("Update command failed");
			}
			try {
				transaction.commit();
			} catch (SpecmateException e) {
				logService.log(LogService.LOG_ERROR, "Commit failed", e);
				EmfRestUtil.throwInternalServerError("Commit failed");
			}
		} else {
			EmfRestUtil.throwMethodNotAllowed();
		}

	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteContent() {
		Optional<ICommand> deleteCommand = commandService.getDeleteCommand(instance);
		if (deleteCommand.isPresent()) {
			try {
				deleteCommand.get().execute();
			} catch (SpecmateException s) {
				logService.log(LogService.LOG_ERROR, "Delete command failed", s);
				transaction.rollback();
				EmfRestUtil.throwInternalServerError("Delete command failed");
			}
			try {
				transaction.commit();
			} catch (SpecmateException e) {
				logService.log(LogService.LOG_ERROR, "Commit failed", e);
				EmfRestUtil.throwInternalServerError("Commit failed");
			}
		} else {
			EmfRestUtil.throwMethodNotAllowed();
		}
	}

	@Path("/{feature:[^_][^/]*}")
	public Object getFeature(@PathParam("feature") String featureName,
			@Context BundleContext context) {
		for (EReference reference : instance.eClass()
				.getEAllReferences()) {
			if (reference.getName().equals(featureName)) {
				if (reference.isMany()) {
					ManyFeatureResource resource = resourceContext.getResource(ManyFeatureResource.class);
					resource.setReference(reference);
					resource.setOwner(instance);
					return resource;
				} else {
					SingleFeatureResource resource = resourceContext.getResource(SingleFeatureResource.class);
					resource.setFeature(reference);
					resource.setInstance(instance);
					return resource;
				}
			}
		}
		throw EmfRestUtil.throwNotFound("Resource not found");
	}

	@Path("/_events")
	@GET
	@Produces(SseFeature.SERVER_SENT_EVENTS)
	public EventOutput getServerSentEvents() {
		String uri;
		try {
			uri = uriFactory.getURI(instance);
		} catch (SpecmateException e) {
			logService.log(LogService.LOG_ERROR, "Could not retrieve uri for object" + instance);
			throw EmfRestUtil.throwInternalServerError("Could not retrieve internal uri");
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
		Dictionary<String, Object> properties = new Hashtable<String, Object>();
		uri = uri.replace(".", "_");
		String[] topics = new String[] { "com/specmate/model/notification/" + uri + "/*",
				"com/specmate/model/notification/" + uri };
		properties.put("event.topics", topics);
		ServiceRegistration<EventHandler> registration = context.registerService(EventHandler.class, handler,
				properties);
		handler.setRegistration(registration);
		return eventOutput;
	}

}

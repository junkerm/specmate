package com.specmate.emfrest.internal.rest;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
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

	/**
	 * Service to get commands for adding, updating and removing model elements
	 */
	@Inject
	ICommandService commandService;

	@Override
	public void doUpdateContent(EObject update) {
		Optional<ICommand> updateCommand = commandService.getUpdateCommand(getModelInstance(), update);
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
				transaction.rollback();
				EmfRestUtil.throwInternalServerError("Commit failed");
			}
		} else {
			EmfRestUtil.throwMethodNotAllowed();
		}

	}

	@Path("/_events")
	@GET
	@Produces(SseFeature.SERVER_SENT_EVENTS)
	public EventOutput getServerSentEvents() {
		String uri;
		try {
			uri = uriFactory.getURI(getModelInstance());
		} catch (SpecmateException e) {
			logService.log(LogService.LOG_ERROR, "Could not retrieve uri for object" + getModelInstance());
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
	protected void doAddObject(EObject object) {
		EStructuralFeature feature = getModelInstance().eClass().getEStructuralFeature("contents");
		if (feature != null) {
			Optional<ICommand> command;
			command = commandService.getCreateCommand(getModelInstance(), object, feature.getName());

			if (command.isPresent()) {
				try {
					command.get().execute();
				} catch (SpecmateException e1) {
					EmfRestUtil.throwBadRequest(e1.getMessage());
				}
				try {
					transaction.commit();
				} catch (SpecmateException e) {
					logService.log(LogService.LOG_ERROR, "Commit failed", e);
				}
			} else {
				EmfRestUtil.throwMethodNotAllowed();
			}

		} else {
			EmfRestUtil.throwNotFound("Resource does not exist");
		}
	}

}

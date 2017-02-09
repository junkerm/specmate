package com.specmate.emfrest.internal.rest;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;

import org.eclipse.emf.ecore.EObject;
import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.SseFeature;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.osgi.service.log.LogService;

import com.specmate.common.SpecmateException;
import com.specmate.emfrest.internal.util.EmfRestUtil;
import com.specmate.model.support.commands.ICommand;
import com.specmate.model.support.commands.ICommandService;
import com.specmate.model.support.urihandler.IURIFactory;
import com.specmate.persistency.ITransaction;

/** 
 * Resource representing the root of the EMF object tree 
 * @author junkerm
 * */
@Path("/rest")
public class RootResource extends ListResource {

	/** The current transaction to retrieve and store objects */
	@Inject
	ITransaction transaction;
	
	/** The command services to retrieve commands for manipulating the object graph */
	@Inject
	ICommandService commandService;
	
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

	/** Context of this resource*/
	@Context
	ResourceContext resourceContext;

	/** Returns all direct children of the transaction's (EMF) resource */
	@Override
	protected List<EObject> getList() {
		return transaction.getResource().getContents();
	}

	/** Adds an object as child to the transaction's (EMF) resource */
	@Override
	protected void addToList(EObject toAdd) {
		Optional<ICommand> command = commandService.getCreateCommand(transaction.getResource(),
				toAdd, null);
		transaction.rollback();
		if (command.isPresent()) {
			try {
				command.get().execute();
			} catch (SpecmateException e1) {
				EmfRestUtil.throwBadRequest(e1.getMessage());
			}
			try {
				transaction.commit();
			} catch (SpecmateException e) {
				logService.log(LogService.LOG_ERROR, "Commit failed");
			}
		} else {
			EmfRestUtil.throwMethodNotAllowed();
		}
	}
	
	/** 
	 * Registers a {@link ModelEventHandler} listening for direct changes of the EMF resource
	 * @return
	 */
	@Path("/_events")
	@GET
	@Produces(SseFeature.SERVER_SENT_EVENTS)
	public EventOutput getServerSentEvents() {
		EventOutput eventOutput = new EventOutput();
		
		ModelEventHandler handler = new ModelEventHandler(eventOutput,uriFactory,logService);
		Dictionary<String, Object> properties = new Hashtable<String, Object>();
		String[] topics = {"com/specmate/model/notification","com/specmate/model/notification/*"};
		properties.put(EventConstants.EVENT_TOPIC, topics);
		ServiceRegistration<EventHandler> registration = bundleContext.registerService(EventHandler.class, handler, properties);
		handler.setRegistration(registration);
		return eventOutput;
	}

}

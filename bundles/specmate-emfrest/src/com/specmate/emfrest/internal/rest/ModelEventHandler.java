package com.specmate.emfrest.internal.rest;

import java.io.IOException;

import org.eclipse.emf.ecore.EObject;
import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.OutboundEvent;
import org.json.JSONObject;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.osgi.service.log.LogService;

import com.specmate.common.SpecmateException;
import com.specmate.emfjson.EMFJsonSerializer;
import com.specmate.persistency.event.EChangeKind;
import com.specmate.persistency.event.ModelEvent;
import com.specmate.urihandler.IURIFactory;

/**
 * Handles model events with a certain path and propagates the changes via
 * Server Side Events
 */
class ModelEventHandler implements EventHandler {

	private static final String VALUE_KEY = "value";
	private static final String FEATURE_NAME_KEY = "featureName";
	private static final String URI_KEY = "uri";
	private static final String TYPE_KEY = "type";
	private static final String EVENT_MESSAGE_NAME = "specmate_model_event";
	private static final String INDEX_KEY = "index";

	/** The event output to propagate the model changes */
	private EventOutput eventOutput;

	/** The registration under which we were registered with the OSGi runtime */
	private ServiceRegistration<EventHandler> registration;

	/** The factory for retrieving URIs from EObjects */
	private IURIFactory uriFactory;

	/** The OSGi logging service */
	private LogService logService;

	/** construcgtor */
	public ModelEventHandler(EventOutput eventOutput, IURIFactory uriFactory, LogService logService) {
		this.eventOutput = eventOutput;
		this.uriFactory = uriFactory;
		this.logService = logService;
	}

	/** Sets the OSGi service registration */
	public void setRegistration(ServiceRegistration<EventHandler> registration) {
		this.registration = registration;
	}

	/** {@inheritDoc} */
	@Override
	public void handleEvent(Event event) {
		if (!(event instanceof ModelEvent)) {
			return;
		}

		ModelEvent modelEvent = (ModelEvent) event;
		JSONObject jsonEvent = null;
		try {
			jsonEvent = createJSONEvent(modelEvent);
		} catch (SpecmateException e) {
			logService.log(LogService.LOG_ERROR, "Could not serialize event", e);
		}
		if (jsonEvent != null) {
			sendEvent(jsonEvent);
		}
	}

	/**
	 * Sends a JSON encoded event out via the SSE mechanism
	 * 
	 * @param jsonEvent
	 */
	private void sendEvent(JSONObject jsonEvent) {
		final OutboundEvent.Builder eventBuilder = new OutboundEvent.Builder();
		eventBuilder.name(EVENT_MESSAGE_NAME);
		eventBuilder.data(String.class, jsonEvent.toString());
		OutboundEvent outEvent = eventBuilder.build();

		// TODO: When client disconnects the eventOutput is not closed
		// Unregistration happens in catch block.
		// Is this correct? --> Look at SSEBroadcaster implementation if there
		// is a better solution
		try {
			if (!eventOutput.isClosed()) {
				eventOutput.write(outEvent);
			} else {
				registration.unregister();
			}
		} catch (IOException e) {
			registration.unregister();
			logService.log(LogService.LOG_ERROR, "Could not write REST event");
		}
	}

	/**
	 * Produces a {@link JSONObject} from a {@link ModelEvent}
	 * 
	 * @param modelEvent
	 *            The model event to encode.
	 * @return The produced {@link JSONObject}
	 * @throws SpecmateException
	 *             When the serialization fails
	 */
	private JSONObject createJSONEvent(ModelEvent modelEvent) throws SpecmateException {
		JSONObject jsonEvent = new JSONObject();
		jsonEvent.put(TYPE_KEY, modelEvent.getType().toString());
		jsonEvent.put(URI_KEY, modelEvent.getUrl());
		if (modelEvent.getFeatureName() != null) {
			jsonEvent.put(FEATURE_NAME_KEY, modelEvent.getFeatureName());
		}
		if(modelEvent.getType()==EChangeKind.REMOVE){
			jsonEvent.put(INDEX_KEY, modelEvent.getIndex());
		}
		Object newValue = modelEvent.getNewValue();
		if (newValue != null) {
			if (newValue instanceof EObject) {
				EMFJsonSerializer emfJsonSerializer = new EMFJsonSerializer(this.uriFactory);
				if(modelEvent.getType()==EChangeKind.NEW || modelEvent.isContainment()){
					jsonEvent.put(VALUE_KEY, emfJsonSerializer.serialize((EObject) newValue));
				} else {
					jsonEvent.put(VALUE_KEY, emfJsonSerializer.getProxy((EObject)newValue));
				}
			} else {
				jsonEvent.put(VALUE_KEY, newValue.toString());
			}
		}
		return jsonEvent;
	}

}
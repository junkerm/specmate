package com.specmate.persistency.cdo.internal;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.ecore.EObject;

import com.specmate.persistency.event.EventType;
import com.specmate.persistency.event.IPersistencyEvent;

public class UpdateEvent implements IPersistencyEvent {

		@Override
	public Collection<EObject> getObjects() {
		return Collections.emptyList();
	}

	@Override
	public EventType getType() {
		return EventType.UPDATE;
	}

}

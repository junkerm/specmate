package com.specmate.persistency.event;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;

public interface IPersistencyEvent {
	public EventType getType();
	public Collection<EObject> getObjects();
}

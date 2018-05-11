package com.specmate.connectors.api;

import java.util.Collection;

import com.specmate.common.SpecmateException;
import com.specmate.model.base.IContainer;
import com.specmate.model.requirements.Requirement;

public interface IRequirementsSource {

	Collection<Requirement> getRequirements() throws SpecmateException;

	String getId();

	IContainer getContainerForRequirement(Requirement requirement) throws SpecmateException;

	boolean authenticate(String username, String password) throws SpecmateException;

}

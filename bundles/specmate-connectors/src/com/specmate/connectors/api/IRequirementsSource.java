package com.specmate.connectors.api;

import com.specmate.common.SpecmateException;
import com.specmate.model.base.IContainer;
import com.specmate.model.requirements.Requirement;

public interface IRequirementsSource {

	IContainer getRequirements() throws SpecmateException;

	String getId();

	IContainer getContainerForRequirement(Requirement requirement) throws SpecmateException;

}

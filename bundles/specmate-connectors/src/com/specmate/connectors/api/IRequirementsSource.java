package com.specmate.connectors.api;

import com.specmate.common.SpecmateException;
import com.specmate.model.base.IContainer;

public interface IRequirementsSource {

	IContainer getRequirements() throws SpecmateException;

	String getId();

	IContainer getContainerForRequirement(String key);

}

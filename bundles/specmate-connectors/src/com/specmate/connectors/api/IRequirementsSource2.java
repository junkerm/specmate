package com.specmate.connectors.api;

import java.util.List;

import com.specmate.common.SpecmateException;
import com.specmate.model.base.IContainer;
import com.specmate.model.base.IContentElement;

public interface IRequirementsSource2 extends IRequirementsSource {
	public List<? extends IContentElement> getRoots() throws SpecmateException;

	List<? extends IContentElement> getChildren(IContainer container) throws SpecmateException;
}

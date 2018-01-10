package com.specmate.connectors.hpconnector.internal.services;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.osgi.service.component.annotations.Component;

import com.specmate.common.SpecmateException;
import com.specmate.connectors.api.IRequirementsSource;
import com.specmate.model.base.BaseFactory;
import com.specmate.model.base.IContainer;
import com.specmate.model.requirements.Requirement;
import com.specmate.model.requirements.RequirementsFactory;

@Component(immediate = true)
public class TestRequirementsSource implements IRequirementsSource {

	private IContainer folder;

	@Override
	public Collection<Requirement> getRequirements() throws SpecmateException {
		List<Requirement> requirements = new LinkedList<>();
		for (int i = 1; i < 500000; i++) {
			Requirement r = RequirementsFactory.eINSTANCE.createRequirement();
			r.setName("Requirement" + i);
			r.setDescription("Requirement" + i);
			r.setExtId(Integer.toString(i));
			r.setId("req" + Integer.toString(i));
			requirements.add(r);
		}
		return requirements;
	}

	@Override
	public String getId() {
		return "test-source";
	}

	@Override
	public IContainer getContainerForRequirement(Requirement requirement) throws SpecmateException {
		if (this.folder == null) {
			this.folder = BaseFactory.eINSTANCE.createFolder();
			this.folder.setId("test-sorce-folder");
		}
		return this.folder;
	}

}

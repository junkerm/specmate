package com.specmate.connectors.hpconnector.internal;

import java.util.Collection;

import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import com.specmate.common.RestResult;
import com.specmate.common.SpecmateException;
import com.specmate.connectors.api.ConnectorUtil;
import com.specmate.connectors.api.IRequirementsSource;
import com.specmate.model.base.BaseFactory;
import com.specmate.model.base.Folder;
import com.specmate.model.base.IContainer;
import com.specmate.model.requirements.Requirement;

@Component(service = IRequirementsSource.class, immediate = true)
public class HPConnector implements IRequirementsSource {

	public static final String CONNECTOR_URL = "http://localhost:8081";
	private LogService logService;
	private HPServerProxy serverProxy;

	@Override
	public Collection<Requirement> getRequirements() throws SpecmateException {
		return serverProxy.getRequirements();
	}

	@Override
	public IContainer getContainerForRequirement(Requirement localRequirement) throws SpecmateException {
		Folder folder = BaseFactory.eINSTANCE.createFolder();
		RestResult<JSONObject> result = null;
		String extId = localRequirement.getExtId();
		logService.log(LogService.LOG_DEBUG, "Retrieving requirements details for " + extId);

		Requirement retrievedRequirement = serverProxy.retrieveRequirementsDetails(localRequirement.getExtId());

		if (retrievedRequirement.getPlannedRelease() != null && !retrievedRequirement.getPlannedRelease().isEmpty()) {
			folder.setId(ConnectorUtil.toId(retrievedRequirement.getPlannedRelease()));
			folder.setName(retrievedRequirement.getPlannedRelease());
		} else {
			folder.setName("default");
			folder.setId("default");
		}
		return folder;
	}

	@Override
	public String getId() {
		return "HP-Import";
	}

	@Reference
	public void setLogService(LogService logService) {
		this.logService = logService;
	}

	@Reference
	public void setHPServerProxy(HPServerProxy serverProxy) {
		this.serverProxy = serverProxy;
	}

}

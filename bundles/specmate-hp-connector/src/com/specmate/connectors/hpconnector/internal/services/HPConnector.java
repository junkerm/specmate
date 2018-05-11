package com.specmate.connectors.hpconnector.internal.services;

import java.util.Collection;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import com.specmate.common.SpecmateException;
import com.specmate.common.SpecmateValidationException;
import com.specmate.connectors.api.ConnectorUtil;
import com.specmate.connectors.api.IRequirementsSource;
import com.specmate.connectors.config.ProjectConfig;
import com.specmate.connectors.hpconnector.internal.config.HPServerProxyConfig;
import com.specmate.connectors.hpconnector.internal.util.HPProxyConnection;
import com.specmate.model.base.BaseFactory;
import com.specmate.model.base.Folder;
import com.specmate.model.base.IContainer;
import com.specmate.model.requirements.Requirement;

/** Connector to the HP Proxy server. */
@Component(service = IRequirementsSource.class, configurationPid = HPServerProxyConfig.CONNECTOR_PID, configurationPolicy = ConfigurationPolicy.REQUIRE)
public class HPConnector implements IRequirementsSource {

	/** Logging service */
	private LogService logService;

	/** The connection to the hp proxy */
	private HPProxyConnection hpConnection;

	private String id;

	private String projectName;

	/**
	 * Service Activation
	 * 
	 * @throws SpecmateValidationException
	 */
	@Activate
	public void activate(Map<String, Object> properties) throws SpecmateValidationException {
		// TODO validateion
		String host = (String) properties.get(HPServerProxyConfig.KEY_HOST);
		String port = (String) properties.get(HPServerProxyConfig.KEY_PORT);
		int timeout = Integer.parseInt((String) properties.get(HPServerProxyConfig.KEY_TIMEOUT));
		this.projectName = (String) properties.get(ProjectConfig.KEY_PROJECT_NAME);
		this.id = (String) properties.get(ProjectConfig.KEY_CONNECTOR_ID);
		this.hpConnection = new HPProxyConnection(host, port, timeout);
	}

	/** Returns the list of requirements. */
	@Override
	public Collection<Requirement> getRequirements() throws SpecmateException {
		return hpConnection.getRequirements();
	}

	/** Returns a folder with the name of the release of the requirement. */
	@Override
	public IContainer getContainerForRequirement(Requirement localRequirement) throws SpecmateException {
		Folder folder = BaseFactory.eINSTANCE.createFolder();
		String extId = localRequirement.getExtId();
		logService.log(LogService.LOG_DEBUG, "Retrieving requirements details for " + extId);

		Requirement retrievedRequirement = hpConnection.getRequirementsDetails(localRequirement.getExtId());

		if (retrievedRequirement.getPlannedRelease() != null && !retrievedRequirement.getPlannedRelease().isEmpty()) {
			folder.setId(ConnectorUtil.toId(retrievedRequirement.getPlannedRelease()));
			folder.setName(retrievedRequirement.getPlannedRelease());
		} else {
			folder.setName("default");
			folder.setId("default");
		}
		return folder;
	}

	/** The id for this connector. */
	@Override
	public String getId() {
		return this.id;
	}

	/** Service reference */
	@Reference
	public void setLogService(LogService logService) {
		this.logService = logService;
	}

	@Override
	public boolean authenticate(String username, String password) throws SpecmateException {
		return hpConnection.authenticateRead(username, password, projectName);
	}

}

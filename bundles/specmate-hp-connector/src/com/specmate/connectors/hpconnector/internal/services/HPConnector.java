package com.specmate.connectors.hpconnector.internal.services;

import java.util.Collection;
import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import com.specmate.common.exception.SpecmateException;
import com.specmate.connectors.api.ConnectorUtil;
import com.specmate.connectors.api.IRequirementsSource;
import com.specmate.connectors.config.ProjectConfigService;
import com.specmate.connectors.hpconnector.internal.config.HPServerProxyConfig;
import com.specmate.connectors.hpconnector.internal.util.HPProxyConnection;
import com.specmate.emfrest.api.IRestService;
import com.specmate.emfrest.crud.DetailsService;
import com.specmate.model.base.BaseFactory;
import com.specmate.model.base.Folder;
import com.specmate.model.base.IContainer;
import com.specmate.model.requirements.Requirement;
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.rest.RestResult;

/** Connector to the HP Proxy server. */
@Component(service = { IRequirementsSource.class,
		IRestService.class }, configurationPid = HPServerProxyConfig.CONNECTOR_PID, configurationPolicy = ConfigurationPolicy.REQUIRE)
public class HPConnector extends DetailsService implements IRequirementsSource, IRestService {

	/** Logging service */
	private LogService logService;

	/** The connection to the hp proxy */
	private HPProxyConnection hpConnection;

	private String id;

	private String projectName;

	/**
	 * Service Activation
	 *
	 * @throws SpecmateException
	 */
	@Activate
	public void activate(Map<String, Object> properties) throws SpecmateException {
		// TODO validateion
		String host = (String) properties.get(HPServerProxyConfig.KEY_HOST);
		String port = (String) properties.get(HPServerProxyConfig.KEY_PORT);
		int timeout = Integer.parseInt((String) properties.get(HPServerProxyConfig.KEY_TIMEOUT));
		this.projectName = (String) properties.get(ProjectConfigService.KEY_PROJECT_NAME);
		this.id = (String) properties.get(ProjectConfigService.KEY_CONNECTOR_ID);
		this.hpConnection = new HPProxyConnection(host, port, timeout);
	}

	/** Returns the list of requirements. */
	@Override
	public Collection<Requirement> getRequirements() throws SpecmateException {
		return hpConnection.getRequirements(this.projectName);
	}

	/** Returns a folder with the name of the release of the requirement. */
	@Override
	public IContainer getContainerForRequirement(Requirement localRequirement) throws SpecmateException {
		Folder folder = BaseFactory.eINSTANCE.createFolder();
		String extId = localRequirement.getExtId();
		logService.log(LogService.LOG_DEBUG, "Retrieving requirements details for " + extId + ".");

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

	@Override
	public int getPriority() {
		return super.getPriority() + 1;
	}

	@Override
	public boolean canGet(Object target) {
		if (target instanceof Requirement) {
			Requirement req = (Requirement) target;
			return req.getSource() != null && req.getSource().equals(HPProxyConnection.HPPROXY_SOURCE_ID)
					&& (SpecmateEcoreUtil.getProjectId(req).equals(this.id));
		}
		return false;
	}

	@Override
	public boolean canPut(Object target, Object object) {
		return false;
	}

	@Override
	public boolean canPost(Object object2, Object object) {
		return false;
	}

	@Override
	public boolean canDelete(Object object) {
		return false;
	}

	/**
	 * Behavior for GET requests. For requirements the current data is fetched from
	 * the HP server.
	 */
	@Override
	public RestResult<?> get(Object target, MultivaluedMap<String, String> queryParams, String token)
			throws SpecmateException {
		if (!(target instanceof Requirement)) {
			return super.get(target, queryParams, token);
		}
		Requirement localRequirement = (Requirement) target;

		// TODO: We should check the source of the requirment, there might be
		// more sources in future
		if (localRequirement.getExtId() == null) {
			return new RestResult<>(Response.Status.OK, localRequirement);
		}

		Requirement retrievedRequirement = this.hpConnection.getRequirementsDetails(localRequirement.getExtId());
		SpecmateEcoreUtil.copyAttributeValues(retrievedRequirement, localRequirement);

		return new RestResult<>(Response.Status.OK, localRequirement);
	}

	/** Service reference */
	@Reference
	public void setLogService(LogService logService) {
		this.logService = logService;
	}

	@Override
	public boolean authenticate(String username, String password) {
		return hpConnection.authenticateRead(username, password, projectName);
	}

}

package com.specmate.connectors.hpconnector.internal.services;

import java.util.Collection;
import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import com.specmate.common.exception.SpecmateException;
import com.specmate.common.exception.SpecmateInternalException;
import com.specmate.connectors.api.ConnectorUtil;
import com.specmate.connectors.api.IProjectConfigService;
import com.specmate.connectors.api.IRequirementsSource;
import com.specmate.connectors.hpconnector.internal.config.HPServerProxyConfig;
import com.specmate.connectors.hpconnector.internal.util.HPProxyConnection;
import com.specmate.emfrest.api.IRestService;
import com.specmate.emfrest.crud.DetailsService;
import com.specmate.model.administration.ErrorCode;
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

	/** The id of the connector */
	private String id;

	/** The id of the HP project */
	private String hpProjectName;

	/**
	 * Service Activation
	 *
	 * @throws SpecmateException
	 */
	@Activate
	public void activate(Map<String, Object> properties) throws SpecmateException {
		String host = (String) properties.get(HPServerProxyConfig.KEY_HOST);
		if (StringUtils.isEmpty(host)) {
			throw new SpecmateInternalException(ErrorCode.CONFIGURATION, "HP Connector: host is empty");
		}

		String port = (String) properties.get(HPServerProxyConfig.KEY_PORT);
		if (StringUtils.isEmpty(port)) {
			throw new SpecmateInternalException(ErrorCode.CONFIGURATION, "HP Connector: port is empty");
		}

		String timeoutString = (String) properties.get(HPServerProxyConfig.KEY_TIMEOUT);
		if (StringUtils.isEmpty(timeoutString)) {
			throw new SpecmateInternalException(ErrorCode.CONFIGURATION, "HP Connector: timeout is empty");
		}
		int timeout = Integer.parseInt(timeoutString);

		this.hpProjectName = (String) properties.get(HPServerProxyConfig.KEY_HP_PROJECT_NAME);
		if (StringUtils.isEmpty(timeoutString)) {
			throw new SpecmateInternalException(ErrorCode.CONFIGURATION, "HP Connector: project name is empty");
		}
		this.id = (String) properties.get(IProjectConfigService.KEY_CONNECTOR_ID);
		if (StringUtils.isEmpty(timeoutString)) {
			throw new SpecmateInternalException(ErrorCode.CONFIGURATION, "HP Connector: connector id is empty");
		}

		this.hpConnection = new HPProxyConnection(host, port, timeout, this.logService);
	}

	/** Returns the list of requirements. */
	@Override
	public Collection<Requirement> getRequirements() throws SpecmateException {
		return this.hpConnection.getRequirements(this.hpProjectName);
	}

	/** Returns a folder with the name of the release of the requirement. */
	@Override
	public IContainer getContainerForRequirement(Requirement localRequirement) throws SpecmateException {
		Folder folder = BaseFactory.eINSTANCE.createFolder();
		String extId = localRequirement.getExtId();
		this.logService.log(LogService.LOG_DEBUG, "Retrieving requirements details for " + extId + ".");

		Requirement retrievedRequirement = this.hpConnection.getRequirementsDetails(localRequirement.getExtId());

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
	 * Behavior for GET requests. For requirements the current data is fetched
	 * from the HP server.
	 */
	@Override
	public RestResult<?> get(Object target, MultivaluedMap<String, String> queryParams, String token)
			throws SpecmateException {
		if (!(target instanceof Requirement)) {
			return super.get(target, queryParams, token);
		}
		Requirement localRequirement = (Requirement) target;

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
	public boolean authenticate(String username, String password) throws SpecmateException {
		return this.hpConnection.authenticateRead(username, password, this.hpProjectName);
	}

}

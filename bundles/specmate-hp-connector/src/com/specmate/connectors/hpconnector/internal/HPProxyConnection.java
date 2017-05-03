package com.specmate.connectors.hpconnector.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import com.specmate.common.RestClient;
import com.specmate.common.RestResult;
import com.specmate.common.SpecmateException;
import com.specmate.common.SpecmateValidationException;
import com.specmate.connectors.hpconnector.HPServerProxyConfig;
import com.specmate.model.requirements.Requirement;
import com.specmate.model.requirements.RequirementsFactory;

/**
 * Service that provides a connection to the HP proxy. The services is activated
 * when a configuration is provided.
 */
@Component(immediate = true, service = HPProxyConnection.class, configurationPid = HPServerProxyConfig.PID, configurationPolicy = ConfigurationPolicy.REQUIRE)
public class HPProxyConnection {

	/** Error message */
	private static final String ERROR_MSG = "Error while retrieving from HP Interface";

	/**
	 * The REST client used to fetch requirements details. The RestClient is
	 * thread-safe, hence we can use one instance even if the service is called
	 * concurrently.
	 */
	private RestClient restClient;

	/** The logging service. */
	private LogService logService;

	/**
	 * Service activation
	 * 
	 * @throws SpecmateValidationException
	 */
	@Activate
	public void activate(Map<String, Object> properties) throws SpecmateValidationException {
		validateConfig(properties);
		String host = (String) properties.get(HPServerProxyConfig.KEY_HOST);
		String port = (String) properties.get(HPServerProxyConfig.KEY_PORT);
		int timeout = (Integer) properties.get(HPServerProxyConfig.KEY_TIMEOUT);
		this.restClient = new RestClient(host + ":" + port, timeout * 1000, this.logService);
		this.logService.log(LogService.LOG_INFO, "Initialized HP Server Proxy with " + properties.toString());
	}

	/** Validates if all configuration parameters are available. */
	private void validateConfig(Map<String, Object> properties) throws SpecmateValidationException {
		String errMsg = "Missing config for %s";
		if (!properties.containsKey(HPServerProxyConfig.KEY_HOST)) {
			throw new SpecmateValidationException(String.format(errMsg, HPServerProxyConfig.KEY_HOST));
		}
		if (!properties.containsKey(HPServerProxyConfig.KEY_PORT)) {
			throw new SpecmateValidationException(String.format(errMsg, HPServerProxyConfig.KEY_PORT));
		}
		if (!properties.containsKey(HPServerProxyConfig.KEY_TIMEOUT)) {
			throw new SpecmateValidationException(String.format(errMsg, HPServerProxyConfig.KEY_TIMEOUT));
		}
	}

	/** Service deactivation */
	public void deactivate() {
		this.restClient.close();
		this.logService.log(LogService.LOG_INFO, "Shut down HP Server Proxy");
	}

	/** Retrieves requirements details from the HP server. */
	public Requirement getRequirementsDetails(String extId) throws SpecmateException {
		RestResult<JSONObject> result;
		try {
			result = restClient.get("/getRequirementDetails", "extId", extId);
		} catch (Exception e) {
			throw new SpecmateException(ERROR_MSG);
		}
		Response response = result.getResponse();
		if (response.getStatus() != Response.Status.OK.getStatusCode()) {
			throw new SpecmateException(ERROR_MSG);
		}
		JSONObject jsonRequirement = result.getPayload();
		Requirement requirement = RequirementsFactory.eINSTANCE.createRequirement();
		HPUtil.updateRequirement(jsonRequirement, requirement);
		return requirement;
	}

	/** Retrieves a list of requirements from the HP proxy. */
	public Collection<Requirement> getRequirements() throws SpecmateException {
		RestResult<JSONArray> result;
		try {
			result = restClient.getList("/getRequirements");
		} catch (Exception e) {
			throw new SpecmateException(ERROR_MSG);
		}
		Response response = result.getResponse();
		JSONArray jsonRequirements = result.getPayload();
		if (response.getStatus() != Response.Status.OK.getStatusCode()) {
			throw new SpecmateException(ERROR_MSG);
		}

		List<Requirement> requirements = new ArrayList<>();
		for (int i = 0; i < jsonRequirements.length(); i++) {
			JSONObject jsonRequirement = jsonRequirements.getJSONObject(i);
			Requirement requirement = RequirementsFactory.eINSTANCE.createRequirement();
			HPUtil.updateRequirement(jsonRequirement, requirement);
			requirements.add(requirement);
		}
		return requirements;
	}

	/** Service reference */
	@Reference
	public void setLogService(LogService logService) {
		this.logService = logService;
	}
}

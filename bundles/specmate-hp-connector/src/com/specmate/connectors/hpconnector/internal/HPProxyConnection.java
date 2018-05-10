package com.specmate.connectors.hpconnector.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import com.specmate.common.RestClient;
import com.specmate.common.RestResult;
import com.specmate.common.SpecmateException;
import com.specmate.common.SpecmateValidationException;
import com.specmate.connectors.hpconnector.internal.config.HPServerProxyConfig;
import com.specmate.model.requirements.Requirement;
import com.specmate.model.requirements.RequirementsFactory;
import com.specmate.model.testspecification.TestProcedure;

/**
 * Service that provides a connection to the HP proxy. The services is activated
 * when a configuration is provided.
 */
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
	public HPProxyConnection(String host, String port, int timeout) throws SpecmateValidationException {
		validateConfig(host, port, timeout);
		this.restClient = new RestClient("http://" + host + ":" + port, timeout * 1000, this.logService);
	}

	/** Validates if all configuration parameters are available. */
	private void validateConfig(String host, String port, int timeout) throws SpecmateValidationException {
		String errMsg = "Missing config for %s";
		if (host == null || StringUtils.isEmpty(host)) {
			throw new SpecmateValidationException(String.format(errMsg, HPServerProxyConfig.KEY_HOST));
		}
		if (port == null || StringUtils.isEmpty(port)) {
			throw new SpecmateValidationException(String.format(errMsg, HPServerProxyConfig.KEY_PORT));
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
			throw new SpecmateException(e);
		}
		Response response = result.getResponse();
		if (response.getStatus() != Response.Status.OK.getStatusCode()) {
			throw new SpecmateException(ERROR_MSG + ": Status code is " + response.getStatus());
		}
		JSONObject jsonRequirement = result.getPayload();
		Requirement requirement = RequirementsFactory.eINSTANCE.createRequirement();
		requirement.setLive(true);
		HPUtil.updateRequirement(jsonRequirement, requirement);
		return requirement;
	}

	/** Retrieves a list of requirements from the HP proxy. */
	public Collection<Requirement> getRequirements() throws SpecmateException {
		RestResult<JSONArray> result;
		try {
			result = restClient.getList("/getRequirements");
		} catch (Exception e) {
			throw new SpecmateException(e);
		}
		Response response = result.getResponse();
		JSONArray jsonRequirements = result.getPayload();
		if (response.getStatus() != Response.Status.OK.getStatusCode()) {
			throw new SpecmateException(ERROR_MSG + ": Status code is " + response.getStatus());
		}

		List<Requirement> requirements = new ArrayList<>();
		for (int i = 0; i < jsonRequirements.length(); i++) {
			JSONObject jsonRequirement = jsonRequirements.getJSONObject(i);
			Requirement requirement = RequirementsFactory.eINSTANCE.createRequirement();
			requirement.setLive(true);
			HPUtil.updateRequirement(jsonRequirement, requirement);
			requirements.add(requirement);
		}
		return requirements;
	}

	public void exportTestProcedure(TestProcedure procedure) throws SpecmateException {
		JSONObject procedureAsJSON = HPUtil.getJSONForTestProcedure(procedure);
		if (StringUtils.isEmpty(procedure.getExtId())) {
			try {
				RestResult<JSONObject> result = restClient.post("/createTestProcedure", procedureAsJSON);
				if (result.getResponse().getStatus() != Status.OK.getStatusCode()) {
					throw new SpecmateException("Could not sync test procedure to ALM");
				}
			} catch (Exception e) {
				throw new SpecmateException(e);
			}
		}
	}

	/** Service reference */
	@Reference
	public void setLogService(LogService logService) {
		this.logService = logService;
	}
}

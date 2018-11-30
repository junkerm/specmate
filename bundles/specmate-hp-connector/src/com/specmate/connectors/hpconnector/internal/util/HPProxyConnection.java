package com.specmate.connectors.hpconnector.internal.util;

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

import com.specmate.common.SpecmateException;
import com.specmate.common.SpecmateValidationException;
import com.specmate.connectors.hpconnector.internal.config.HPServerProxyConfig;
import com.specmate.model.requirements.Requirement;
import com.specmate.model.requirements.RequirementsFactory;
import com.specmate.model.testspecification.TestProcedure;
import com.specmate.rest.RestClient;
import com.specmate.rest.RestResult;

/**
 * Service that provides a connection to the HP proxy. The services is activated
 * when a configuration is provided.
 */
public class HPProxyConnection {

	private static final String QUERY_PARAM_PROJECT = "project";

	private static final String QUERY_PARAM_PASSWORD = "password";

	private static final String QUERY_PARAM_USER = "username";
	
	/** The source id */
	public static final String HPPROXY_SOURCE_ID = "hpproxy";
	

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
	public Collection<Requirement> getRequirements(String project) throws SpecmateException {
		List<Requirement> requirements = new ArrayList<>();

		JSONArray jsonRequirements;
		int page = 1;
		do {
			RestResult<JSONArray> result;
			try {
				result = restClient.getList("/getRequirements", QUERY_PARAM_PROJECT, project, "page",
						Integer.toString(page));
			} catch (Exception e) {
				throw new SpecmateException(e);
			}
			Response response = result.getResponse();
			jsonRequirements = result.getPayload();
			if (response.getStatus() != Response.Status.OK.getStatusCode()) {
				throw new SpecmateException(ERROR_MSG + ": Status code is " + response.getStatus());
			}

			for (int i = 0; i < jsonRequirements.length(); i++) {
				JSONObject jsonRequirement = jsonRequirements.getJSONObject(i);
				Requirement requirement = RequirementsFactory.eINSTANCE.createRequirement();
				requirement.setLive(true);
				requirement.setSource(HPPROXY_SOURCE_ID);
				HPUtil.updateRequirement(jsonRequirement, requirement);
				requirements.add(requirement);
			}
			page++;
		} while (jsonRequirements != null && jsonRequirements.length() > 0);
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

	public boolean authenticateRead(String username, String password, String projectName) throws SpecmateException {
		return checkAuthenticated("authenticateRead", username, password, projectName);
	}

	public boolean authenticateExport(String username, String password) throws SpecmateException {
		return checkAuthenticated("authenticateExport", username, password, null);
	}

	private boolean checkAuthenticated(String endpoint, String username, String password, String projectName)
			throws SpecmateException {
		try {

			RestResult<JSONObject> result;
			if (projectName != null) {
				result = restClient.get("/" + endpoint, QUERY_PARAM_USER, username, QUERY_PARAM_PASSWORD, password,
						QUERY_PARAM_PROJECT, projectName);
			} else {
				result = restClient.get("/" + endpoint, QUERY_PARAM_USER, username, QUERY_PARAM_PASSWORD, password);
			}
			if (result.getResponse().getStatus() == Status.OK.getStatusCode()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			throw new SpecmateException(e);
		}
	}

}

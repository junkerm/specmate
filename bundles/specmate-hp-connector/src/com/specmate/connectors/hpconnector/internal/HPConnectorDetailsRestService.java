package com.specmate.connectors.hpconnector.internal;

import javax.ws.rs.core.Response;

import org.json.JSONObject;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import com.specmate.common.RestClient;
import com.specmate.common.RestResult;
import com.specmate.common.SpecmateException;
import com.specmate.emfrest.api.IRestService;
import com.specmate.emfrest.crud.DetailsService;
import com.specmate.model.requirements.Requirement;

/**
 * This service replace the default details service. It fetches requirements
 * details directly from the HP server.
 * 
 * @author junkerm
 *
 */
@Component(immediate = true, service = IRestService.class)
public class HPConnectorDetailsRestService extends DetailsService {

	private LogService logService;

	/**
	 * The REST client used to fetch requirements details. The RestClient is
	 * thread-safe, hence we can use one instance even if the service is called
	 * concurrently.
	 */
	private RestClient restClient;

	/** Service activation */
	@Activate
	public void activate() {
		this.restClient = new RestClient(HPConnector.CONNECTOR_URL, 20 * 1000);
	}

	/** Service deactivation */
	public void deactivate() {
		this.restClient.close();
	}

	/**
	 * Priority of this service, such that it is favoured over the default
	 * details service.
	 */
	@Override
	public int getPriority() {
		return super.getPriority() + 1;
	}

	/**
	 * Behavior for GET requests. For requirements the current data is fetched
	 * from the HP server.
	 */
	@Override
	public Object get(Object target) throws SpecmateException {
		if (!(target instanceof Requirement)) {
			return super.get(target);
		}
		Requirement requirement = (Requirement) target;

		// TODO: We should check the source of the requirment, there might be
		// more sources in future
		if (requirement.getExtId() == null) {
			return requirement;
		}
		try {
			JSONObject jsonRequirement = retrieveRequirementsDetails(requirement);
			HPUtil.updateRequirement(jsonRequirement, requirement);
		} catch (SpecmateException e) {
			logService.log(LogService.LOG_ERROR, e.getMessage());
		}

		return requirement;

	}

	/** Retrieves requirements details from the HP server. */
	private JSONObject retrieveRequirementsDetails(Requirement requirement) throws SpecmateException {
		RestResult<JSONObject> result;
		try {
			result = restClient.get("/getRequirementDetails", "extId", requirement.getExtId());
		} catch (Exception e) {
			throw new SpecmateException("Error while retrieving requirements details from HP Interface");
		}
		Response response = result.getResponse();
		if (response.getStatus() != Response.Status.OK.getStatusCode()) {
			throw new SpecmateException("Error while retrieving requirements details from HP Interface");
		}
		JSONObject jsonRequirement = result.getPayload();
		return jsonRequirement;
	}

	@Reference
	public void setLogService(LogService logService) {
		this.logService = logService;
	}

}

package com.specmate.connectors.hpconnector;

import javax.ws.rs.core.Response;

import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import com.specmate.common.RestClient;
import com.specmate.common.RestResult;
import com.specmate.common.SpecmateException;
import com.specmate.emfrest.api.IRestService;
import com.specmate.emfrest.crud.DetailsService;
import com.specmate.model.requirements.Requirement;

@Component(immediate = true, service = IRestService.class)
public class HPConnectorDetailsRestService extends DetailsService {

	private LogService logService;

	@Override
	public int getPriority() {
		return super.getPriority() + 1;
	}

	@Override
	public Object get(Object target) throws SpecmateException {
		if (!(target instanceof Requirement)) {
			return super.get(target);
		}
		Requirement requirement = (Requirement) target;

		if (requirement.getExtId() == null) {
			return requirement;
		}
		RestClient restClient = new RestClient(HPConnector.CONNECTOR_URL, 20 * 1000);

		RestResult<JSONObject> result;
		try {
			result = restClient.get("/getRequirementDetails", "extId", requirement.getExtId());
		} catch (Exception e) {
			this.logService.log(LogService.LOG_ERROR, "Could not retrieve requirements details from HP interface.");
			return requirement;
		}
		Response response = result.getResponse();
		if (response.getStatus() != Response.Status.OK.getStatusCode()) {
			throw new SpecmateException("Error while retrieving requirements details from HP Interface");
		}
		JSONObject jsonRequirement = result.getPayload();
		HPUtil.updateRequirement(jsonRequirement, requirement);
		return requirement;
	}

	@Reference
	public void setLogService(LogService logService) {
		this.logService = logService;
	}

	@Override
	public String getServiceName() {
		return "details";
	}
}

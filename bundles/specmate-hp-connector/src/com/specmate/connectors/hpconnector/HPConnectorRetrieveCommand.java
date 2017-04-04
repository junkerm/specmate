package com.specmate.connectors.hpconnector;

import javax.ws.rs.core.Response;

import org.eclipse.emf.ecore.EObject;
import org.json.JSONObject;
import org.osgi.service.log.LogService;

import com.specmate.common.RestClient;
import com.specmate.common.RestResult;
import com.specmate.common.SpecmateException;
import com.specmate.model.requirements.Requirement;
import com.specmate.model.support.commands.CommandBase;

public class HPConnectorRetrieveCommand extends CommandBase {

	private Requirement requirement;

	public HPConnectorRetrieveCommand(Requirement requirement) {
		this.requirement = requirement;
	}

	@Override
	public int getPriority() {
		// higher priority (=lower number) than default (=100)
		return 99;
	}

	@Override
	public EObject execute() throws SpecmateException {
		if (requirement.getExtId() == null) {
			return requirement;
		}
		RestClient restClient = new RestClient(HPConnector.CONNECTOR_URL, 20 * 1000);

		RestResult<JSONObject> result;
		try {
			result = restClient.get("/getRequirementDetails", "extId", requirement.getExtId());
		} catch (Exception e) {
			getLogService().log(LogService.LOG_ERROR, "Could not retrieve requirements details from HP interface.");
			return requirement;
		}
		Response response = result.getResponse();
		if (response.getStatus() != Response.Status.OK.getStatusCode()) {
			throw new SpecmateException("Error while retrieving requirements details from HP Interface");
		}
		JSONObject jsonRequirement = result.getPayload();
		HPUtil.updateRequirement(jsonRequirement, this.requirement);
		return requirement;
	}

}

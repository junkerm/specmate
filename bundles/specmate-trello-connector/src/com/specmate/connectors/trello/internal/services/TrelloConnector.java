package com.specmate.connectors.trello.internal.services;

import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response.Status;

import org.json.JSONArray;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import com.specmate.common.SpecmateException;
import com.specmate.common.SpecmateValidationException;
import com.specmate.connectors.api.IRequirementsSource;
import com.specmate.connectors.config.ProjectConfigService;
import com.specmate.connectors.trello.config.TrelloConnectorConfig;
import com.specmate.model.base.BaseFactory;
import com.specmate.model.base.Folder;
import com.specmate.model.base.IContainer;
import com.specmate.model.requirements.Requirement;
import com.specmate.model.requirements.RequirementsFactory;
import com.specmate.rest.RestClient;
import com.specmate.rest.RestResult;

@Component(immediate = true, service = IRequirementsSource.class, configurationPid = TrelloConnectorConfig.PID, configurationPolicy = ConfigurationPolicy.REQUIRE)
public class TrelloConnector implements IRequirementsSource {

	private static final String TRELLO_API_BASE_URL = "https://api.trello.com";
	private static final int TIMEOUT = 5000;
	private LogService logService;
	private RestClient restClient;
	private String boardId;
	private String key;
	private String token;
	private String id;

	@Activate
	public void activate(Map<String, Object> properties) throws SpecmateValidationException {
		validateConfig(properties);
		this.boardId = (String) properties.get(TrelloConnectorConfig.KEY_BOARD_ID);
		this.key = (String) properties.get(TrelloConnectorConfig.KEY_TRELLO_KEY);
		this.token = (String) properties.get(TrelloConnectorConfig.KEY_TRELLO_TOKEN);
		this.id = (String) properties.get(ProjectConfigService.KEY_CONNECTOR_ID);
		this.restClient = new RestClient(TRELLO_API_BASE_URL, TIMEOUT, this.logService);
		this.logService.log(LogService.LOG_INFO, "Initialized HP Server Proxy with " + properties.toString());
	}

	private void validateConfig(Map<String, Object> properties) throws SpecmateValidationException {
		String aBoardId = (String) properties.get(TrelloConnectorConfig.KEY_BOARD_ID);
		String aKey = (String) properties.get(TrelloConnectorConfig.KEY_TRELLO_KEY);
		String aToken = (String) properties.get(TrelloConnectorConfig.KEY_TRELLO_TOKEN);

		if (isEmpty(aBoardId) || isEmpty(aKey) || isEmpty(aToken)) {
			throw new SpecmateValidationException("Trello Connector is not well configured.");
		}
	}

	@Reference
	public void setLogService(LogService logService) {
		this.logService = logService;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public Collection<Requirement> getRequirements() throws SpecmateException {
		RestResult<JSONArray> restResult = restClient.getList("/1/boards/" + boardId + "/cards", "key", this.key,
				"token", this.token);
		if (restResult.getResponse().getStatus() == Status.OK.getStatusCode()) {
			restResult.getResponse().close();
			List<Requirement> requirements = new ArrayList<>();
			JSONArray cardsArray = restResult.getPayload();
			for (int i = 0; i < cardsArray.length(); i++) {
				JSONObject cardObject = cardsArray.getJSONObject(i);
				requirements.add(makeRequirementFromCard(cardObject));
			}
			return requirements;
		} else {
			restResult.getResponse().close();
			throw new SpecmateException("Could not retrieve list of trello cards.");
		}
	}

	@Override
	public IContainer getContainerForRequirement(Requirement requirement) throws SpecmateException {
		RestResult<JSONObject> restResult = restClient.get("/1/cards/" + requirement.getExtId2() + "/list", "key",
				this.key, "token", this.token);
		if (restResult.getResponse().getStatus() == Status.OK.getStatusCode()) {
			JSONObject listObject = restResult.getPayload();
			return makeFolderFromList(listObject);
		} else {
			throw new SpecmateException("Could not retrieve list for trello card: " + requirement.getExtId2());
		}
	}

	public List<Folder> getLists() throws SpecmateException {
		RestResult<JSONArray> restResult = restClient.getList("/1/boards/" + boardId + "/lists", "cards", "open", "key",
				this.key, "token", this.token);
		if (restResult.getResponse().getStatus() == Status.OK.getStatusCode()) {
			restResult.getResponse().close();
			List<Folder> folders = new ArrayList<>();
			JSONArray listsArray = restResult.getPayload();
			for (int i = 0; i < listsArray.length(); i++) {
				JSONObject listObject = listsArray.getJSONObject(i);
				Folder folder = makeFolderFromList(listObject);
				folders.add(folder);
			}
			return folders;
		}
		restResult.getResponse().close();
		throw new SpecmateException("Could not load Trello Lists.");
	}

	private Folder makeFolderFromList(JSONObject listObject) {
		Folder folder = BaseFactory.eINSTANCE.createFolder();
		folder.setId(listObject.getString("id"));
		folder.setName(listObject.getString("name"));
		return folder;
	}

	private Requirement makeRequirementFromCard(JSONObject cardObject) {
		Requirement requirement = RequirementsFactory.eINSTANCE.createRequirement();
		String idShort = Integer.toString(cardObject.getInt("idShort"));
		String id = cardObject.getString("id");
		requirement.setId(idShort);
		requirement.setExtId(idShort);
		requirement.setExtId2(id);
		requirement.setName(cardObject.getString("name"));
		requirement.setDescription(cardObject.getString("desc"));
		return requirement;
	}

	@Override
	public boolean authenticate(String username, String password) throws SpecmateException {
		return true;
	}

}

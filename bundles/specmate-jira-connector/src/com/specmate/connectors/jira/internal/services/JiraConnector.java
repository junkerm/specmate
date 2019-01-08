package com.specmate.connectors.jira.internal.services;

import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.RestClientException;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.specmate.common.exception.SpecmateAuthorizationException;
import com.specmate.common.exception.SpecmateException;
import com.specmate.common.exception.SpecmateInternalException;
import com.specmate.connectors.api.IRequirementsSource;
import com.specmate.connectors.config.ProjectConfigService;
import com.specmate.connectors.jira.config.JiraConnectorConfig;
import com.specmate.model.administration.ErrorCode;
import com.specmate.model.base.BaseFactory;
import com.specmate.model.base.Folder;
import com.specmate.model.base.IContainer;
import com.specmate.model.requirements.Requirement;
import com.specmate.model.requirements.RequirementsFactory;

@Component(immediate = true, service = IRequirementsSource.class, configurationPid = JiraConnectorConfig.PID, configurationPolicy = ConfigurationPolicy.REQUIRE)
public class JiraConnector implements IRequirementsSource {

	private LogService logService;
	private String id;
	private JiraRestClient jiraClient;
	private String projectName;
	private String url;

	private Map<Issue, Folder> epicFolders = new HashMap<>();
	private Map<Requirement, Issue> requirmentEpics = new HashMap<>();
	private Folder defaultFolder;

	@Activate
	public void activate(Map<String, Object> properties) throws SpecmateException {
		validateConfig(properties);

		id = (String) properties.get(ProjectConfigService.KEY_CONNECTOR_ID);
		url = (String) properties.get(JiraConnectorConfig.KEY_JIRA_URL);
		projectName = (String) properties.get(JiraConnectorConfig.KEY_JIRA_PROJECT);
		String username = (String) properties.get(JiraConnectorConfig.KEY_JIRA_USERNAME);
		String password = (String) properties.get(JiraConnectorConfig.KEY_JIRA_PASSWORD);

		try {
			jiraClient = new AsynchronousJiraRestClientFactory().createWithBasicHttpAuthentication(new URI(url),
					username, password);
		} catch (URISyntaxException e) {
			throw new SpecmateInternalException(ErrorCode.JIRA, e);
		}

		defaultFolder = createFolder(projectName + "-Default", projectName + "-Default");

		this.logService.log(LogService.LOG_INFO, "Initialized Jira Connector with " + properties.toString() + ".");
	}

	private void validateConfig(Map<String, Object> properties) throws SpecmateException {
		String aProject = (String) properties.get(JiraConnectorConfig.KEY_JIRA_PROJECT);
		String aUsername = (String) properties.get(JiraConnectorConfig.KEY_JIRA_USERNAME);
		String aPassword = (String) properties.get(JiraConnectorConfig.KEY_JIRA_PASSWORD);

		if (isEmpty(aProject) || isEmpty(aUsername) || isEmpty(aPassword)) {
			throw new SpecmateInternalException(ErrorCode.CONFIGURATION, "Jira Connector is not well configured.");
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

		List<Requirement> requirements = new ArrayList<>();

		List<Issue> storiesWithoutEpic = this.getStoriesWithoutEpic();
		for (Issue story : storiesWithoutEpic) {
			requirements.add(createRequirement(story, null));
		}

		List<Issue> epics = this.getEpics();

		for (Issue epic : epics) {
			createFolderIfNotExists(epic);
			List<Issue> stories = getStoriesForEpic(epic);
			for (Issue story : stories) {
				Requirement requirement = createRequirement(story, epic);
				requirmentEpics.put(requirement, epic);
				requirements.add(requirement);
			}
		}

		return requirements;
	}

	private List<Issue> getStoriesForEpic(Issue epic) {
		return this.getIssues("project=" + projectName + " AND issueType=story AND \"Epic Link\"=\"" + epic.getKey()
				+ "\" ORDER BY assignee, resolutiondate");
	}

	private List<Issue> getStoriesWithoutEpic() {
		return this.getIssues("project=" + projectName
				+ " AND issueType=story AND \"Epic Link\" IS EMPTY ORDER BY assignee, resolutiondate");
	}

	private List<Issue> getEpics() {
		return this.getIssues("project=" + projectName + " AND issueType=epic ORDER BY id");
	}

	private List<Issue> getIssues(String jql) {
		List<Issue> issues = new ArrayList<>();

		int maxResults = Integer.MAX_VALUE;
		while (issues.size() < maxResults) {
			try {
				SearchResult searchResult = jiraClient.getSearchClient().searchJql(jql, -1, issues.size(), null)
						.claim();
				maxResults = searchResult.getTotal();
				searchResult.getIssues().forEach(issue -> issues.add(issue));
				logService.log(LogService.LOG_DEBUG, "Loaded ~" + searchResult.getMaxResults() + " issues from Jira "
						+ url + " project: " + projectName);
			} catch (RestClientException e) {
				if (e.getStatusCode().get() == 400) {
					// We expect this in the case, where no results are found.
					logService.log(LogService.LOG_INFO, "No results for query [" + jql + "]: " + e.getMessage());
					e.printStackTrace();
				} else {
					logService.log(LogService.LOG_ERROR, e.getMessage());
					e.printStackTrace();
				}
			}

		}

		logService.log(LogService.LOG_INFO,
				"Finished loading of " + issues.size() + " issues from Jira " + url + " project: " + projectName);

		return issues;
	}

	@Override
	public IContainer getContainerForRequirement(Requirement requirement) throws SpecmateException {
		Issue epic = requirmentEpics.get(requirement);
		if (epic == null) {
			return defaultFolder;
		}
		return epicFolders.get(epic);
	}

	@Override
	public boolean authenticate(String username, String password) throws SpecmateException {
		try {
			JiraRestClient client = new AsynchronousJiraRestClientFactory()
					.createWithBasicHttpAuthentication(new URI(url), username, password);
			client.getProjectClient().getProject(projectName).claim();
		} catch (URISyntaxException e) {
			e.printStackTrace();
			throw new SpecmateAuthorizationException("Jira authentication failed.", e);
		} catch (RestClientException e) {
			Integer status = e.getStatusCode().get();
			if (status == 401) {
				logService.log(LogService.LOG_INFO,
						"Invalid credentials provided for jira project " + this.projectName + '.');
				return false;
			}
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private static Requirement createRequirement(Issue story, Issue epic) throws SpecmateException {
		Requirement requirement = RequirementsFactory.eINSTANCE.createRequirement();
		String id = story.getKey();
		String idShort = Long.toString(story.getId());
		requirement.setId(id + "-" + idShort);
		requirement.setExtId(id);
		requirement.setExtId2(id);
		requirement.setName(story.getSummary());
		requirement.setDescription(story.getDescription());
		requirement.setStatus(story.getStatus().getName());
		try {
			JSONObject teamObject = (JSONObject) story.getFieldByName("Team").getValue();
			if (teamObject != null) {
				String team = (String) teamObject.get("value");
				requirement.setImplementingITTeam(team);
			}
		} catch (JSONException e) {
			throw new SpecmateInternalException(ErrorCode.JIRA, e);
		}
		return requirement;
	}

	private void createFolderIfNotExists(Issue epic) {
		if (!epicFolders.containsKey(epic)) {
			String folderId = epic.getKey();
			String folderName = folderId + ": " + epic.getSummary();
			Folder folder = createFolder(folderId, folderName);
			epicFolders.put(epic, folder);
		}
	}

	private Folder createFolder(String folderId, String folderName) {
		Folder folder = BaseFactory.eINSTANCE.createFolder();
		folder.setId(folderId);
		folder.setName(folderName);
		return folder;
	}

}

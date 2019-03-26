package com.specmate.connectors.jira.internal.services;

import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.RestClientException;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.specmate.common.cache.ICache;
import com.specmate.common.cache.ICacheLoader;
import com.specmate.common.cache.ICacheService;
import com.specmate.common.exception.SpecmateAuthorizationException;
import com.specmate.common.exception.SpecmateException;
import com.specmate.common.exception.SpecmateInternalException;
import com.specmate.connectors.api.IProjectConfigService;
import com.specmate.connectors.api.IRequirementsSource;
import com.specmate.connectors.jira.config.JiraConnectorConfig;
import com.specmate.emfrest.api.IRestService;
import com.specmate.emfrest.crud.DetailsService;
import com.specmate.model.administration.ErrorCode;
import com.specmate.model.base.BaseFactory;
import com.specmate.model.base.Folder;
import com.specmate.model.base.IContainer;
import com.specmate.model.requirements.Requirement;
import com.specmate.model.requirements.RequirementsFactory;
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.rest.RestResult;

@Component(immediate = true, service = { IRestService.class,
		IRequirementsSource.class }, configurationPid = JiraConnectorConfig.PID, configurationPolicy = ConfigurationPolicy.REQUIRE)
public class JiraConnector extends DetailsService implements IRequirementsSource, IRestService {

	private static final String JIRA_STORY_CACHE_NAME = "jiraStoryCache";

	private static final String JIRA_SOURCE_ID = "jira";

	private LogService logService;
	private String id;
	private JiraRestClient jiraClient;
	private String projectName;
	private String url;

	private Map<Issue, Folder> epicFolders = new HashMap<>();
	private Map<Requirement, Issue> requirmentEpics = new HashMap<>();
	private Folder defaultFolder;

	private ICacheService cacheService;

	private ICache<String, Issue> cache;

	@Activate
	public void activate(Map<String, Object> properties) throws SpecmateException {
		validateConfig(properties);

		id = (String) properties.get(IProjectConfigService.KEY_CONNECTOR_ID);
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

		cache = cacheService.createCache(JIRA_STORY_CACHE_NAME, 500, 3600, new ICacheLoader<String, Issue>() {

			@Override
			public Issue load(String key) throws SpecmateException {
				Issue issue = getStory(key);
				return issue;
			}
		});

		logService.log(LogService.LOG_DEBUG, "Initialized Jira Connector with " + properties.toString() + ".");
	}

	@Deactivate
	public void deactivate() throws SpecmateInternalException {
		try {
			jiraClient.close();
			cacheService.removeCache(JIRA_STORY_CACHE_NAME);
		} catch (IOException e) {
			throw new SpecmateInternalException(ErrorCode.INTERNAL_PROBLEM, "Could not close JIRA client.", e);
		}
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
		return id;
	}

	@Override
	public Collection<Requirement> getRequirements() throws SpecmateException {

		List<Requirement> requirements = new ArrayList<>();

		List<Issue> storiesWithoutEpic = getStoriesWithoutEpic();
		for (Issue story : storiesWithoutEpic) {
			requirements.add(createRequirement(story));
		}

		List<Issue> epics = getEpics();

		for (Issue epic : epics) {
			createFolderIfNotExists(epic);
			List<Issue> stories = getStoriesForEpic(epic);
			for (Issue story : stories) {
				Requirement requirement = createRequirement(story);
				requirmentEpics.put(requirement, epic);
				requirements.add(requirement);
			}
		}

		return requirements;
	}

	private List<Issue> getStoriesForEpic(Issue epic) throws SpecmateException {
		return getIssues("project=" + projectName + " AND issueType=systemanforderung AND key in linkedIssues(\""
				+ epic.getKey() + "\",\"is described by\")" + " ORDER BY assignee, resolutiondate");
	}

	private List<Issue> getStoriesWithoutEpic() throws SpecmateException {
		return Collections.emptyList();
		// return getIssues("project=" + projectName
		// + " AND issueType=story AND \"Epic Link\" IS EMPTY ORDER BY assignee,
		// resolutiondate");
	}

	private List<Issue> getEpics() throws SpecmateException {
		return getIssues("project=" + projectName + " AND issueType=funktion ORDER BY id");
	}

	private Issue getStory(String id) throws SpecmateException {
		List<Issue> issues = getIssues("project=" + projectName + " AND id=" + id);
		if (issues == null || issues.size() == 0) {
			throw new SpecmateInternalException(ErrorCode.INTERNAL_PROBLEM, "JIRA Issue not found: " + id);
		}
		return issues.get(0);
	}

	private List<Issue> getIssues(String jql) throws SpecmateException {
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
					return issues;
				} else {
					throw new SpecmateInternalException(ErrorCode.INTERNAL_PROBLEM, "Could not load issues from jira",
							e);
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
						"Invalid credentials provided for jira project " + projectName + '.');
				return false;
			}
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private static Requirement createRequirement(Issue story) throws SpecmateException {
		Requirement requirement = RequirementsFactory.eINSTANCE.createRequirement();
		String id = story.getKey();
		String idShort = Long.toString(story.getId());
		requirement.setId(id + "-" + idShort);
		requirement.setExtId(id);
		requirement.setExtId2(id);
		requirement.setSource(JIRA_SOURCE_ID);
		requirement.setName(story.getSummary());
		requirement.setDescription(story.getDescription());
		requirement.setStatus(story.getStatus().getName());
		requirement.setLive(true);
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

	@Override
	public int getPriority() {
		return super.getPriority() + 1;
	}

	@Override
	public boolean canGet(Object target) {
		if (target instanceof Requirement) {
			Requirement req = (Requirement) target;
			return req.getSource() != null && req.getSource().equals(JIRA_SOURCE_ID)
					&& (SpecmateEcoreUtil.getProjectId(req).equals(id));
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

		if (localRequirement.getExtId() == null) {
			return super.get(target, queryParams, token);
		}

		Issue issue;
		try {
			issue = cache.get(localRequirement.getExtId());
		} catch (Exception e) {
			// Loading has failed
			return new RestResult<>(Status.NOT_FOUND);
		}

		Requirement retrievedRequirement = createRequirement(issue);
		SpecmateEcoreUtil.copyAttributeValues(retrievedRequirement, localRequirement);

		return new RestResult<>(Response.Status.OK, localRequirement);
	}

	@Reference
	public void setCacheService(ICacheService cacheService) {
		this.cacheService = cacheService;
	}

}

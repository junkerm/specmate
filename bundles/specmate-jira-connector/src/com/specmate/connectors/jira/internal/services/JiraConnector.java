package com.specmate.connectors.jira.internal.services;

import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.Project;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.atlassian.util.concurrent.Promise;
import com.specmate.common.RestClient;
import com.specmate.common.SpecmateException;
import com.specmate.common.SpecmateValidationException;
import com.specmate.connectors.api.IRequirementsSource;
import com.specmate.connectors.config.ProjectConfigService;
import com.specmate.connectors.jira.config.JiraConnectorConfig;
import com.specmate.model.base.Folder;
import com.specmate.model.base.IContainer;
import com.specmate.model.requirements.Requirement;

@Component(immediate = true, service = IRequirementsSource.class, configurationPid = JiraConnectorConfig.PID, configurationPolicy = ConfigurationPolicy.REQUIRE)
public class JiraConnector implements IRequirementsSource {

	private static final String TRELLO_API_BASE_URL = "https://api.trello.com";
	private static final int TIMEOUT = 5000;
	private LogService logService;
	private String id;
	private JiraRestClient jiraClient;
	private Project jiraProject;

	@Activate
	public void activate(Map<String, Object> properties) throws SpecmateValidationException {
		validateConfig(properties);
		String url = (String) properties.get(JiraConnectorConfig.KEY_JIRA_URL);
		String projectName = (String) properties.get(JiraConnectorConfig.KEY_JIRA_PROJECT);
		String username = (String) properties.get(JiraConnectorConfig.KEY_JIRA_USERNAME);
		String password = (String) properties.get(JiraConnectorConfig.KEY_JIRA_PASSWORD);
		this.id = (String) properties.get(ProjectConfigService.KEY_CONNECTOR_ID);
		
		try {
			jiraClient = new AsynchronousJiraRestClientFactory().createWithBasicHttpAuthentication(new URI(url), username, password);
			jiraProject = jiraClient.getProjectClient().getProject(projectName).claim();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.logService.log(LogService.LOG_INFO, "Initialized Jira Connector with " + properties.toString());
	}

	private void validateConfig(Map<String, Object> properties) throws SpecmateValidationException {
		String aProject = (String) properties.get(JiraConnectorConfig.KEY_JIRA_PROJECT);
		String aUsername = (String) properties.get(JiraConnectorConfig.KEY_JIRA_USERNAME);
		String aPassword = (String) properties.get(JiraConnectorConfig.KEY_JIRA_PASSWORD);

		if (isEmpty(aProject) || isEmpty(aUsername) || isEmpty(aPassword)) {
			throw new SpecmateValidationException("Jira Connector is not well configured.");
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
		
		SearchResult searchResult = jiraClient.getSearchClient().searchJql("project = ***REMOVED*** AND issueType = story ORDER BY assignee, resolutiondate", -1, null, null).claim();

        for (Issue issue : searchResult.getIssues()) {
            System.out.println("------------" + issue.getSummary() + "------------");
            System.out.println(issue.getDescription());
        }
        return new ArrayList<Requirement>();
	}

	@Override
	public IContainer getContainerForRequirement(Requirement requirement) throws SpecmateException {
		throw new SpecmateException("Not implemented");
	}

	public List<Folder> getLists() throws SpecmateException {
		throw new SpecmateException("Not implemented");
	}

	@Override
	public boolean authenticate(String username, String password) throws SpecmateException {
		return true;
	}

}

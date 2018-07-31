package com.specmate.connectors.jira.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.osgi.service.log.LogService;

import com.specmate.common.SpecmateException;
import com.specmate.common.SpecmateValidationException;
import com.specmate.connectors.jira.config.JiraConnectorConfig;
import com.specmate.connectors.jira.internal.services.JiraConnector;

import static org.mockito.Mockito.mock;

public class JiraConnectorTest {
	
	@Test
	public void initialize() {
		Map<String, Object> properties = new HashMap<>();
		properties.put(JiraConnectorConfig.KEY_JIRA_URL, "***REMOVED***");
		properties.put(JiraConnectorConfig.KEY_JIRA_PROJECT, "***REMOVED***");
		properties.put(JiraConnectorConfig.KEY_JIRA_USERNAME, "***REMOVED***");
		properties.put(JiraConnectorConfig.KEY_JIRA_PASSWORD, "***REMOVED***");
		
		JiraConnector connector = new JiraConnector();
		connector.setLogService(mock(LogService.class));
		try {
			connector.activate(properties);
			connector.getRequirements();
		} catch (SpecmateValidationException | SpecmateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

package com.specmate.test.integration;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;
import org.junit.Test;

import com.specmate.common.RestClient;
import com.specmate.common.RestResult;
import com.specmate.common.SpecmateException;

public class AuthenticationTest extends EmfRestTest {
	private JSONObject projectA, projectB;
	private String projectAName, projectBName;
	private JSONObject requirementA, requirementB;

	public AuthenticationTest() throws Exception {
		super();
		
		projectA = postFolderToRoot();
		projectAName = getId(projectA);
		requirementA = postRequirement(projectAName);
		
		
		projectB = postFolderToRoot();
		projectBName = getId(projectB);
		requirementB = postRequirement(projectBName);
	}
	
	@Test
	public void testUnauthorizedPost() throws SpecmateException {
		String authenticationToken = authenticationService.authenticate("resttest", "resttest", projectAName);
		RestClient clientProjectA = new RestClient(REST_ENDPOINT, authenticationToken, logService);
		
		RestResult<JSONObject> result = clientProjectA.post(listUrl(projectAName), requirementB);
		assertEquals(Status.OK.getStatusCode(), result.getResponse().getStatus());
		
		result = clientProjectA.post(listUrl(projectBName), requirementA);
		assertEquals(Status.UNAUTHORIZED.getStatusCode(), result.getResponse().getStatus());
	}
	
	@Test
	public void testUnauthorizedGet() throws SpecmateException {
		String authenticationToken = authenticationService.authenticate("resttest", "resttest", projectAName);
		RestClient clientProjectA = new RestClient(REST_ENDPOINT, authenticationToken, logService);
		
		RestResult<JSONObject> result = clientProjectA.get(detailUrl(projectAName));
		assertEquals(Status.OK.getStatusCode(), result.getResponse().getStatus());
		
		result = clientProjectA.get(projectBName);
		assertEquals(Status.UNAUTHORIZED.getStatusCode(), result.getResponse().getStatus());
	}
}

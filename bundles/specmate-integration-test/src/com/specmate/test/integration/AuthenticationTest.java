package com.specmate.test.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;
import org.junit.Test;

import com.specmate.common.exception.SpecmateException;
import com.specmate.model.administration.ErrorCode;
import com.specmate.rest.RestClient;
import com.specmate.rest.RestResult;
import com.specmate.test.integration.support.DummyProject;
import com.specmate.test.integration.support.DummyProjectService;
import com.specmate.usermodel.UserSession;

public class AuthenticationTest extends EmfRestTest {
	private String projectAName, projectBName;
	private JSONObject requirementA, requirementB;

	public AuthenticationTest() throws Exception {
		super();

		projectAName = getSelectedProjectName();
		requirementA = postRequirement();
		nextProject();
		projectBName = getSelectedProjectName();
		requirementB = postRequirement();

		if (projectService instanceof DummyProjectService) {
			DummyProjectService dummyProjectService = (DummyProjectService) projectService;
			dummyProjectService.addProject(new DummyProject(projectAName));
			dummyProjectService.addProject(new DummyProject(projectBName));
		}

		resetSelectedProject();
	}

	@Test
	public void testUnauthorizedPost() throws SpecmateException {
		UserSession session = authenticationService.authenticate("resttest", "resttest", projectAName);
		RestClient clientProjectA = new RestClient(REST_ENDPOINT, session.getId(), logService);

		RestResult<JSONObject> result = clientProjectA.post(listUrl(), requirementB);
		assertEquals(Status.OK.getStatusCode(), result.getResponse().getStatus());
		result.getResponse().close();

		nextProject();

		result = clientProjectA.post(listUrl(), requirementA);
		assertEquals(Status.UNAUTHORIZED.getStatusCode(), result.getResponse().getStatus());
		JSONObject obj = result.getPayload();
		assertNotNull(obj);
		assertEquals(ErrorCode.NO_AUTHORIZATION.getLiteral(), obj.get("ecode"));
		assertTrue(((String) obj.get("detail")).contains(projectBName));
		result.getResponse().close();

		resetSelectedProject();
	}
	
	@Test
	public void testWrongProjectAtLogin() throws SpecmateException {
		UserSession session = authenticationService.authenticate("resttest", "resttest", projectAName);
		RestClient clientProjectA = new RestClient(REST_ENDPOINT, session.getId(), logService);
			
		String wrongProjectName = "wrongProjectName";
		String LOGIN_JSON = "{\"___nsuri\":\"http://specmate.com/20190125/model/user\",\"className\":\"User\",\"userName\":\"resttest\",\"passWord\":\"resttest\",\"projectName\":\"" + wrongProjectName + "\"}";
		RestResult<JSONObject> result = clientProjectA.post("login", new JSONObject(LOGIN_JSON));
		int loginStatus = result.getResponse().getStatus();

		assertEquals(Status.UNAUTHORIZED.getStatusCode(), loginStatus);
		result.getResponse().close();

		resetSelectedProject();
	}

	@Test
	public void testUnauthorizedGet() throws SpecmateException {
		UserSession session = authenticationService.authenticate("resttest", "resttest", projectAName);
		RestClient clientProjectA = new RestClient(REST_ENDPOINT, session.getId(), logService);

		RestResult<JSONObject> result = clientProjectA.get(detailUrl());
		assertEquals(Status.OK.getStatusCode(), result.getResponse().getStatus());
		result.getResponse().close();

		nextProject();

		result = clientProjectA.get(projectBName);
		assertEquals(Status.UNAUTHORIZED.getStatusCode(), result.getResponse().getStatus());
		JSONObject obj = result.getPayload();
		assertNotNull(obj);
		assertEquals(ErrorCode.NO_AUTHORIZATION.getLiteral(), obj.get("ecode"));
		assertTrue(((String) obj.get("detail")).contains(projectBName));
		result.getResponse().close();

		resetSelectedProject();
	}
}

package com.specmate.test.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;
import org.junit.Test;

import com.specmate.model.administration.ErrorCode;
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.rest.RestClient;
import com.specmate.rest.RestResult;
import com.specmate.usermodel.UserSession;

public class ErrorResponsesTest extends EmfRestTest {

	public ErrorResponsesTest() throws Exception {
		super();
	}

	@Test
	public void testUnknownRESTservice() {
		String unknownService = "thisEndpointDoesNotExist";
		RestResult<JSONObject> result = restClient.get(unknownService, "some parameter");
		assertEquals(Status.NOT_FOUND.getStatusCode(), result.getResponse().getStatus());
		JSONObject obj = result.getPayload();
		assertNotNull(obj);
		assertEquals(ErrorCode.NO_SUCH_SERVICE.getLiteral(), obj.get("ecode"));
		assertEquals(unknownService, obj.get("detail"));
		result.getResponse().close();
	}

	@Test
	public void testMethodNotAllowed() {
		String serviceName = "history";
		RestResult<JSONObject> result = restClient.post(serviceName, createTestFolder());
		assertEquals(Status.NOT_FOUND.getStatusCode(), result.getResponse().getStatus());
		JSONObject obj = result.getPayload();
		assertNotNull(obj);
		assertEquals(ErrorCode.NO_SUCH_SERVICE.getLiteral(), obj.get("ecode"));
		assertEquals(serviceName, obj.get("detail"));
		result.getResponse().close();
	}

	@Test
	public void testValidatorError() {
		JSONObject folder = createTestFolder("", "emptyid");
		RestResult<JSONObject> result = restClient.post("list", folder);
		assertEquals(Status.BAD_REQUEST.getStatusCode(), result.getResponse().getStatus());
		JSONObject obj = result.getPayload();
		assertNotNull(obj);
		assertEquals(ErrorCode.VALIDATOR.getLiteral(), obj.get("ecode"));
		result.getResponse().close();
	}

	@Test
	public void testTamperedUserSessionError() {
		UserSession tampered = SpecmateEcoreUtil.shallowCopy(session);
		tampered.setId("tampered");
		RestClient tamperedClient = new RestClient(REST_ENDPOINT, tampered.getId(), logService);
		JSONObject folder = createTestFolder();
		RestResult<JSONObject> result = tamperedClient.post("list", folder);
		assertEquals(Status.UNAUTHORIZED.getStatusCode(), result.getResponse().getStatus());
		JSONObject obj = result.getPayload();
		assertNotNull(obj);
		assertEquals(ErrorCode.NO_AUTHORIZATION.getLiteral(), obj.get("ecode"));
		result.getResponse().close();
	}
}

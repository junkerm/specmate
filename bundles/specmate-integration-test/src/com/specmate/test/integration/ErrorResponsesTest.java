package com.specmate.test.integration;

import static org.junit.Assert.assertNotNull;

import org.json.JSONObject;
import org.junit.Test;

import com.specmate.rest.RestResult;

public class ErrorResponsesTest extends EmfRestTest {

	public ErrorResponsesTest() throws Exception {
		super();
	}

	@Test
	public void testUnknownRESTservice() {
		RestResult<JSONObject> result = restClient.get("thisEndpointDoesNotExist", "some parameter");
		// assertEquals(Status.NOT_FOUND.getStatusCode(),
		// result.getResponse().getStatus());
		JSONObject obj = result.getPayload();
		assertNotNull(obj);
		System.out.println(obj.get("ecode"));

		result.getResponse().close();
	}

}

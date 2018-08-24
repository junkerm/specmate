package com.specmate.test.integration;

import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;
import org.junit.Assert;

import com.specmate.administration.api.ESpecmateStatus;
import com.specmate.model.administration.AdministrationPackage;
import com.specmate.rest.RestResult;

public class AdministrationTest extends EmfRestTest {

	public AdministrationTest() throws Exception {
		super();
	}

	private JSONObject getStatusObject(String mode) {
		JSONObject maintenandeStatus = new JSONObject();
		maintenandeStatus.put(NSURI_KEY, AdministrationPackage.eNS_URI);
		maintenandeStatus.put(ECLASS, AdministrationPackage.Literals.STATUS.getName());
		maintenandeStatus.put(AdministrationPackage.Literals.STATUS__VALUE.getName(), mode);
		return maintenandeStatus;
	}

	private void enterMode(String mode) {
		JSONObject status = getStatusObject(mode);
		String statusUrl = buildUrl("status");
		RestResult<JSONObject> result = restClient.post(statusUrl, status);
		Assert.assertEquals(Status.OK.getStatusCode(), result.getResponse().getStatus());
	}

	private void enterMaintenanceMode() {
		enterMode(ESpecmateStatus.MAINTENANCE_NAME);
	}

	private void enterNormalMode() {
		enterMode(ESpecmateStatus.NORMAL_NAME);
	}

	private void checkIsInMode(String mode) {
		String url = buildUrl("status");
		JSONObject status = getObjectByUrl(Status.OK.getStatusCode(), url);
		Assert.assertEquals(status.get(AdministrationPackage.Literals.STATUS__VALUE.getName()), mode);
	}

	private void checkIsInMaintenanceMode() {
		checkIsInMode(ESpecmateStatus.MAINTENANCE_NAME);
	}

	private void checkIsInNormalMode() {
		checkIsInMode(ESpecmateStatus.NORMAL_NAME);
	}

	//@Test
//	public void testMaintenanceMode() {
//		JSONObject folder = postFolderToRoot();
//		String folderId = getId(folder);
//		enterMaintenanceMode();
//		checkIsInMaintenanceMode();
//
//		// check if read is still possible
//		JSONObject retrievedFolder1 = getObject(folderId);
//
//		// check if write access (post) leads to an exception
//		JSONObject folder2 = createTestFolder();
//		postObject(Status.FORBIDDEN.getStatusCode(), folder2);
//
//		// check if write access (put) leads to an an exception
//		updateObject(Status.FORBIDDEN.getStatusCode(), retrievedFolder1, folderId);
//
//		enterNormalMode();
//		checkIsInNormalMode();
//
//		// check if read is still possible
//		retrievedFolder1 = getObject(folderId);
//
//		// check if write access (post) is possible again
//		postObject(Status.OK.getStatusCode(), folder2);
//
//		// check if write access (put) is possible again
//		updateObject(Status.OK.getStatusCode(), retrievedFolder1, folderId);
//
//		postFolderToRoot();
//
//	}

}

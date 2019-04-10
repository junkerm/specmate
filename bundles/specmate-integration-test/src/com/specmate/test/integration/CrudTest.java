package com.specmate.test.integration;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.osgi.service.log.LogService;

import com.specmate.model.base.BasePackage;
import com.specmate.model.batch.BatchPackage;
import com.specmate.rest.RestResult;

public class CrudTest extends EmfRestTest {

	public CrudTest() throws Exception {
		super();
	}

	private void performDuplicate(String... segments) {
		String duplicateUrl = buildUrl("duplicate", segments);
		RestResult<JSONObject> result = restClient.post(duplicateUrl, null);
		Assert.assertEquals(Status.OK.getStatusCode(), result.getResponse().getStatus());
	}

	/**
	 * Tests posting a folder to the root. Checks, if the return code of the post
	 * request is OK and if retrieving the object again returns the original object.
	 */
	@Test
	public void testPostFolderToRootAndRetrieve() {
		String postUrl = listUrl();
		JSONObject folder = createTestFolder();
		logService.log(LogService.LOG_DEBUG, "Posting the object " + folder.toString() + " to url " + postUrl);
		RestResult<JSONObject> result = restClient.post(postUrl, folder);
		Assert.assertEquals(result.getResponse().getStatus(), Status.OK.getStatusCode());
		result.getResponse().close();

		String retrieveUrl = detailUrl(getId(folder));
		RestResult<JSONObject> getResult = restClient.get(retrieveUrl);
		JSONObject retrievedFolder = getResult.getPayload();
		logService.log(LogService.LOG_DEBUG,
				"Retrieved the object " + retrievedFolder.toString() + " from url " + retrieveUrl);
		Assert.assertTrue(EmfRestTestUtil.compare(folder, retrievedFolder, true));
		getResult.getResponse().close();
	}

	/**
	 * Tests posting a folder that contains special characters in its name. Checks,
	 * if the return code of the post request is OK and if retrieving the object
	 * again returns the original object.
	 */
	@Test
	public void testPostFolderWithSpecialChars() {
		String postUrl = listUrl();
		JSONObject folder = createTestFolder("TestFolder", "äöüß§$% &=?!/\\^_:.,#'+~*(){}[]");
		logService.log(LogService.LOG_DEBUG, "Posting the object " + folder.toString() + " to url " + postUrl);
		RestResult<JSONObject> result = restClient.post(postUrl, folder);
		Assert.assertEquals(result.getResponse().getStatus(), Status.OK.getStatusCode());
		result.getResponse().close();

		String retrieveUrl = detailUrl(getId(folder));
		RestResult<JSONObject> getResult = restClient.get(retrieveUrl);
		JSONObject retrievedFolder = getResult.getPayload();
		logService.log(LogService.LOG_DEBUG,
				"Retrieved the object " + retrievedFolder.toString() + " from url " + retrieveUrl);
		Assert.assertTrue(EmfRestTestUtil.compare(folder, retrievedFolder, true));
		getResult.getResponse().close();
	}

	/**
	 * Tests posting a folder to another folder. Checks, if the return code of the
	 * post request is OK and if retrieving the object again returns the original
	 * object.
	 */
	@Test
	public void testPostFolderToFolderAndRetrieve() {
		JSONObject folder = postFolderToTopFolder();
		String folderName = getId(folder);

		String postUrl2 = listUrl(folderName);
		JSONObject folder2 = createTestFolder();
		String folderName2 = getId(folder2);
		logService.log(LogService.LOG_DEBUG, "Posting the object " + folder2.toString() + " to url " + postUrl2);
		RestResult<JSONObject> result2 = restClient.post(postUrl2, folder2);
		Assert.assertEquals(result2.getResponse().getStatus(), Status.OK.getStatusCode());
		result2.getResponse().close();

		String retrieveUrl = detailUrl(folderName, folderName2);
		RestResult<JSONObject> getResult = restClient.get(retrieveUrl);
		JSONObject retrievedFolder = getResult.getPayload();
		logService.log(LogService.LOG_DEBUG,
				"Retrieved the object " + retrievedFolder.toString() + " from url " + retrieveUrl);
		Assert.assertTrue(EmfRestTestUtil.compare(retrievedFolder, folder2, true));
		getResult.getResponse().close();
	}

	/** Tests if retrieving a non-existing object returns 404-Not found */
	@Test
	public void testMissingFolder() {
		// Create new folder just to get a fresh name
		JSONObject folder = createTestFolder();
		String folderName = getId(folder);

		// Not posting to backend instead try to retrieve
		String retrieveUrl = detailUrl(folderName);
		RestResult<JSONObject> getResult = restClient.get(retrieveUrl);

		Assert.assertEquals(Status.NOT_FOUND.getStatusCode(), getResult.getResponse().getStatus());
		getResult.getResponse().close();
	}

	/** Tests retrieving a list of child folders from a folder */
	@Test
	public void testRetrieveChildrenList() {
		int numberOfChildren = 2;

		JSONObject folder = postFolderToTopFolder();
		String folderName = getId(folder);

		String postUrl2 = listUrl(folderName);
		JSONObject[] folders = new JSONObject[2];
		for (int i = 0; i < numberOfChildren; i++) {
			folders[i] = createTestFolder();
			logService.log(LogService.LOG_DEBUG, "Posting the object " + folders[i].toString() + " to url " + postUrl2);
			RestResult<JSONObject> result2 = restClient.post(postUrl2, folders[i]);
			Assert.assertEquals(result2.getResponse().getStatus(), Status.OK.getStatusCode());
			result2.getResponse().close();
		}

		RestResult<JSONArray> listResult = restClient.getList(postUrl2);
		JSONArray childrenList = listResult.getPayload();
		logService.log(LogService.LOG_DEBUG, "Retrieved the list " + childrenList.toString() + " from url " + postUrl2);
		Assert.assertEquals(2, childrenList.length());
		for (int i = 0; i < numberOfChildren; i++) {
			Assert.assertTrue(EmfRestTestUtil.compare(folders[i], childrenList.getJSONObject(i), true));
		}
		listResult.getResponse().close();
	}

	/** Tests if an empty folder can be deleted */
	@Test
	public void testDeleteEmptyFolder() {
		JSONObject folder = postFolderToTopFolder();
		String folderName = getId(folder);
		getObject(folderName);

		deleteObject(folderName);

		// Check if folder still exists
		getObject(Status.NOT_FOUND.getStatusCode(), folderName);
	}

	/** Tests if a non-empty folder can be deleted */
	@Test
	public void testDeleteNonEmptyFolder() {
		// Post folder to root
		JSONObject outerFolder = postFolderToTopFolder();
		String folderName = getId(outerFolder);

		// Post folder in new folder
		postFolder(folderName);

		// Check if top level folder exists
		getObject(folderName);

		// Delete top level folder
		deleteObject(folderName);

		// Check if top level folder still exists
		getObject(Status.NOT_FOUND.getStatusCode(), folderName);
	}

	/**
	 * Tests posting a requirement to a folder. Checks, if the return code of the
	 * post request is OK and if retrieving the requirement again returns the
	 * original object.
	 */
	@Test
	public void testPostRequirementToFolderAndRetrieve() {
		JSONObject folder = postFolderToTopFolder();
		String folderName = getId(folder);

		JSONObject requirement = postRequirement(folderName);
		String requirementName = getId(requirement);

		getObject(folderName, requirementName);
	}

	@Test
	public void testUpdateFolder() {
		JSONObject folder = postFolderToTopFolder();
		String folderName = getId(folder);

		folder.put(BasePackage.Literals.INAMED__NAME.getName(), "New Name");
		updateObject(folder, folderName);

		JSONObject retrievedFolder = getObject(folderName);
		Assert.assertTrue(EmfRestTestUtil.compare(folder, retrievedFolder, true));
	}

	@Test
	public void testPostCEGToRequirement() {
		JSONObject requirement = postRequirementToRoot();
		String requirementId = getId(requirement);

		JSONObject cegModel = postCEG(requirementId);
		String cegId = getId(cegModel);

		JSONObject retrievedCEG = getObject(requirementId, cegId);
		Assert.assertTrue(EmfRestTestUtil.compare(retrievedCEG, cegModel, true));
	}

	@Test
	public void testPostProcessToRequirement() {
		JSONObject requirement = postRequirementToRoot();
		String requirementId = getId(requirement);

		JSONObject processModel = postProcess(requirementId);
		String processId = getId(processModel);

		JSONObject retrievedProcess = getObject(requirementId, processId);
		Assert.assertTrue(EmfRestTestUtil.compare(retrievedProcess, processModel, true));
	}

	@Test
	public void testPostCEGNodesAndConnectionToCEG() {
		JSONObject requirement = postRequirementToRoot();
		String requirementId = getId(requirement);

		// post ceg
		JSONObject cegModel = postCEG(requirementId);
		String cegId = getId(cegModel);

		// post node 1
		JSONObject cegNode1 = postCEGNode(requirementId, cegId);
		String node1Id = getId(cegNode1);

		JSONObject retrievedCegNode1 = getObject(requirementId, cegId, node1Id);
		Assert.assertTrue(EmfRestTestUtil.compare(cegNode1, retrievedCegNode1, true));

		// post node 2
		JSONObject cegNode2 = postCEGNode(requirementId, cegId);
		String node2Id = getId(cegNode2);

		JSONObject retrievedCegNode2 = getObject(requirementId, cegId, node2Id);
		Assert.assertTrue(EmfRestTestUtil.compare(cegNode2, retrievedCegNode2, true));

		// post connection
		JSONObject connection = postCEGConnection(retrievedCegNode1, retrievedCegNode2, false, requirementId, cegId);
		String connectionId = getId(connection);

		JSONObject retrievedConnection = getObject(requirementId, cegId, connectionId);
		Assert.assertTrue(EmfRestTestUtil.compare(retrievedConnection, connection, true));
	}

	@Test
	public void testPostProcessNodesAndConnectionToProcess() {
		JSONObject requirement = postRequirementToRoot();
		String requirementId = getId(requirement);

		// post process
		JSONObject processModel = postProcess(requirementId);
		String processId = getId(processModel);

		// post start node
		JSONObject startNode = postStartNode(requirementId, processId);
		String startNodeId = getId(startNode);

		JSONObject retrievedStartNode = getObject(requirementId, processId, startNodeId);
		Assert.assertTrue(EmfRestTestUtil.compare(startNode, retrievedStartNode, true));

		// post step 1
		JSONObject stepNode1 = postStepNode(requirementId, processId);
		String stepNode1Id = getId(stepNode1);

		JSONObject retrievedStepNode1 = getObject(requirementId, processId, stepNode1Id);
		Assert.assertTrue(EmfRestTestUtil.compare(stepNode1, retrievedStepNode1, true));

		// post decision
		JSONObject decisionNode = postDecisionNode(requirementId, processId);
		String decisionNodeId = getId(decisionNode);

		JSONObject retrievedDecisionNode = getObject(requirementId, processId, decisionNodeId);
		Assert.assertTrue(EmfRestTestUtil.compare(decisionNode, retrievedDecisionNode, true));

		// post step 2
		JSONObject stepNode2 = postStepNode(requirementId, processId);
		String stepNode2Id = getId(stepNode2);

		JSONObject retrievedStepNode2 = getObject(requirementId, processId, stepNode2Id);
		Assert.assertTrue(EmfRestTestUtil.compare(stepNode2, retrievedStepNode2, true));

		// post step 3
		JSONObject stepNode3 = postStepNode(requirementId, processId);
		String stepNode3Id = getId(stepNode3);

		JSONObject retrievedStepNode3 = getObject(requirementId, processId, stepNode3Id);
		Assert.assertTrue(EmfRestTestUtil.compare(stepNode3, retrievedStepNode3, true));

		// post step 4
		JSONObject stepNode4 = postStepNode(requirementId, processId);
		String stepNode4Id = getId(stepNode4);

		JSONObject retrievedStepNode4 = getObject(requirementId, processId, stepNode4Id);
		Assert.assertTrue(EmfRestTestUtil.compare(stepNode4, retrievedStepNode4, true));

		// post end node
		JSONObject endNode = postEndNode(requirementId, processId);
		String endNodeId = getId(endNode);

		JSONObject retrievedEndNode = getObject(requirementId, processId, endNodeId);
		Assert.assertTrue(EmfRestTestUtil.compare(endNode, retrievedEndNode, true));

		// post connection 1
		JSONObject connection = postStepConnection(retrievedStartNode, retrievedStepNode1, requirementId, processId);
		String connectionId = getId(connection);

		JSONObject retrievedConnection = getObject(requirementId, processId, connectionId);
		Assert.assertTrue(EmfRestTestUtil.compare(retrievedConnection, connection, true));

		// post connection 2
		JSONObject connection2 = postStepConnection(retrievedStepNode1, retrievedDecisionNode, requirementId,
				processId);
		String connection2Id = getId(connection2);

		JSONObject retrievedConnection2 = getObject(requirementId, processId, connection2Id);
		Assert.assertTrue(EmfRestTestUtil.compare(retrievedConnection2, connection2, true));

		// post connection 3
		JSONObject connection3 = postDecisionConnection(retrievedDecisionNode, retrievedStepNode2, requirementId,
				processId);
		String connection3Id = getId(connection3);

		JSONObject retrievedConnection3 = getObject(requirementId, processId, connection3Id);
		Assert.assertTrue(EmfRestTestUtil.compare(retrievedConnection3, connection3, true));

		// post connection 4
		JSONObject connection4 = postDecisionConnection(retrievedDecisionNode, retrievedStepNode3, requirementId,
				processId);
		String connection4Id = getId(connection4);

		JSONObject retrievedConnection4 = getObject(requirementId, processId, connection4Id);
		Assert.assertTrue(EmfRestTestUtil.compare(retrievedConnection4, connection4, true));

		// post connection 5
		JSONObject connection5 = postStepConnection(retrievedStepNode2, retrievedStepNode4, requirementId, processId);
		String connection5Id = getId(connection5);

		JSONObject retrievedConnection5 = getObject(requirementId, processId, connection5Id);
		Assert.assertTrue(EmfRestTestUtil.compare(retrievedConnection5, connection5, true));

		// post connection 6
		JSONObject connection6 = postStepConnection(retrievedStepNode3, retrievedStepNode4, requirementId, processId);
		String connection6Id = getId(connection6);

		JSONObject retrievedConnection6 = getObject(requirementId, processId, connection6Id);
		Assert.assertTrue(EmfRestTestUtil.compare(retrievedConnection6, connection6, true));

		// post connection 6
		JSONObject connection7 = postStepConnection(retrievedStepNode4, retrievedEndNode, requirementId, processId);
		String connection7Id = getId(connection7);

		JSONObject retrievedConnection7 = getObject(requirementId, processId, connection7Id);
		Assert.assertTrue(EmfRestTestUtil.compare(retrievedConnection7, connection7, true));
	}

	@Test
	public void testPostFolderWithNoId() {
		JSONObject folder = createTestFolder();
		folder.remove(ID_KEY);
		postObject(Status.BAD_REQUEST.getStatusCode(), folder);
	}

	@Test
	public void testPostFolderWithDuplicateId() {
		JSONObject folder = postFolderToTopFolder();
		postObject(Status.BAD_REQUEST.getStatusCode(), folder);
	}

	@Test
	public void testPostFolderWithIllegalId() {
		JSONObject folder = createTestFolder();
		folder.put(ID_KEY, "id with spaces");
		postObject(Status.BAD_REQUEST.getStatusCode(), folder);
	}

	@Test
	public void testPostTestSpecificationToCEG() {
		JSONObject requirement = postRequirementToRoot();
		String requirementId = getId(requirement);

		// Post ceg model
		JSONObject cegModel = postCEG(requirementId);
		String cegId = getId(cegModel);

		// Post test specification
		JSONObject testSpecification = postTestSpecification(requirementId, cegId);
		String testSpecId = getId(testSpecification);

		JSONObject retrievedTestSpecification = getObject(requirementId, cegId, testSpecId);
		Assert.assertTrue(EmfRestTestUtil.compare(retrievedTestSpecification, testSpecification, true));
	}
	
	@Test
	public void testPostTestSpecificationToProcess() {
		JSONObject requirement = postRequirementToRoot();
		String requirementId = getId(requirement);

		// Post process model
		JSONObject processModel = postProcess(requirementId);
		String processId = getId(processModel);

		// Post test specification
		JSONObject testSpecification = postTestSpecification(requirementId, processId);
		String testSpecId = getId(testSpecification);

		JSONObject retrievedTestSpecification = getObject(requirementId, processId, testSpecId);
		Assert.assertTrue(EmfRestTestUtil.compare(retrievedTestSpecification, testSpecification, true));
	}

	@Test
	public void testGenerateTestsFromCEG() {
		JSONObject requirement = postRequirementToRoot();
		String requirementId = getId(requirement);

		// Post ceg model
		JSONObject cegModel = postCEG(requirementId);
		String cegId = getId(cegModel);

		// post node 1
		JSONObject cegNode1 = postCEGNode(requirementId, cegId);
		String cegNode1Id = getId(cegNode1);
		JSONObject retrievedCegNode1 = getObject(requirementId, cegId, cegNode1Id);

		// post node 2
		JSONObject cegNode2 = postCEGNode(requirementId, cegId);
		String cegNode2Id = getId(cegNode2);
		JSONObject retrievedCegNode2 = getObject(requirementId, cegId, cegNode2Id);

		// post connection
		postCEGConnection(retrievedCegNode1, retrievedCegNode2, false, requirementId, cegId);

		// Post test specification
		JSONObject testSpec = postTestSpecification(requirementId, cegId);
		String testSpecId = getId(testSpec);

		// Generate test cases
		String generateUrl = buildUrl("generateTests", requirementId, cegId, testSpecId);
		logService.log(LogService.LOG_DEBUG, "Request test genreation at  url " + generateUrl);
		RestResult<JSONObject> result = restClient.post(generateUrl, null);
		Assert.assertEquals(Status.NO_CONTENT.getStatusCode(), result.getResponse().getStatus());
		result.getResponse().close();

		String retrieveUrl = listUrl(requirementId, cegId, testSpecId);
		RestResult<JSONArray> getResult = restClient.getList(retrieveUrl);
		JSONArray retrievedTestChilds = getResult.getPayload();
		logService.log(LogService.LOG_DEBUG,
				"Retrieved the object " + retrievedTestChilds.toString() + " from url " + retrieveUrl);

		// Expect 4 children: two test cases and two test parameters
		Assert.assertEquals(4, retrievedTestChilds.length());
		getResult.getResponse().close();
	}

	@Test
	public void testGenerateTestsFromProcess() {
		JSONObject requirement = postRequirementToRoot();
		String requirementId = getId(requirement);

		// Post process model
		JSONObject processModel = postProcess(requirementId);
		String processId = getId(processModel);

		// post start node
		JSONObject startNode = postStartNode(requirementId, processId);
		String startNodeId = getId(startNode);
		JSONObject retrievedStartNode = getObject(requirementId, processId, startNodeId);

		// post decision node
		JSONObject decisionNode = postDecisionNode(requirementId, processId);
		String decisionNodeId = getId(decisionNode);
		JSONObject retrievedDecisionNode = getObject(requirementId, processId, decisionNodeId);

		// post step connection
		postStepConnection(retrievedStartNode, retrievedDecisionNode, requirementId, processId);

		// post step node
		JSONObject stepNode = postStepNode(requirementId, processId);
		String stepNodeId = getId(stepNode);
		JSONObject retrievedStepNode = getObject(requirementId, processId, stepNodeId);

		// post end node
		JSONObject endNode = postEndNode(requirementId, processId);
		String endNodeId = getId(endNode);
		JSONObject retrievedEndNode = getObject(requirementId, processId, endNodeId);

		// post decision connection
		postDecisionConnection(retrievedDecisionNode, retrievedStepNode, requirementId, processId);

		// post decision connection
		postDecisionConnection(retrievedDecisionNode, retrievedEndNode, requirementId, processId);

		// post connection
		postStepConnection(retrievedStepNode, retrievedEndNode, requirementId, processId);

		// Post test specification
		JSONObject testSpec = postTestSpecification(requirementId, processId);
		String testSpecId = getId(testSpec);

		// Generate test cases
		String generateUrl = buildUrl("generateTests", requirementId, processId, testSpecId);
		logService.log(LogService.LOG_DEBUG, "Request test genreation at  url " + generateUrl);
		RestResult<JSONObject> result = restClient.post(generateUrl, null);
		Assert.assertEquals(Status.NO_CONTENT.getStatusCode(), result.getResponse().getStatus());
		result.getResponse().close();

		String retrieveUrl = listUrl(requirementId, processId, testSpecId);
		RestResult<JSONArray> getResult = restClient.getList(retrieveUrl);
		JSONArray retrievedTestChilds = getResult.getPayload();
		logService.log(LogService.LOG_DEBUG,
				"Retrieved the object " + retrievedTestChilds.toString() + " from url " + retrieveUrl);

		List<JSONObject> testCases = getTestCases(retrievedTestChilds);

		// Expect 2 tests should be generated
		Assert.assertEquals(2, testCases.size());
		
		// Expect 4 children: two test cases and two test parameters
		Assert.assertEquals(4, retrievedTestChilds.length());
		getResult.getResponse().close();
	}

	@Test
	public void testGenerateTestsWithMutExConstraint() {
		JSONObject requirement = postRequirementToRoot();
		String requirementId = getId(requirement);

		// Post ceg model
		JSONObject cegModel = postCEG(requirementId);
		String cegId = getId(cegModel);

		// post node 1
		JSONObject cegNode1 = createTestCegNode("myvar", "=A", "OR");
		cegNode1 = postObject(cegNode1, requirementId, cegId);
		String cegNode1Id = getId(cegNode1);
		JSONObject retrievedCegNode1 = getObject(requirementId, cegId, cegNode1Id);

		// post node 2
		JSONObject cegNode2 = createTestCegNode("myvar", "=B", "OR");
		cegNode2 = postObject(cegNode2, requirementId, cegId);
		String cegNode2Id = getId(cegNode2);
		JSONObject retrievedCegNode2 = getObject(requirementId, cegId, cegNode2Id);

		// post node 3
		JSONObject cegNode3 = createTestCegNode("result", "true", "AND");
		cegNode3 = postObject(cegNode3, requirementId, cegId);
		String cegNode3Id = getId(cegNode3);
		JSONObject retrievedCegNode3 = getObject(requirementId, cegId, cegNode3Id);

		// post connection
		postCEGConnection(retrievedCegNode1, retrievedCegNode3, false, requirementId, cegId);
		postCEGConnection(retrievedCegNode2, retrievedCegNode3, false, requirementId, cegId);

		// Post test specification
		JSONObject testSpec = postTestSpecification(requirementId, cegId);
		String testSpecId = getId(testSpec);

		// Generate test cases
		String generateUrl = buildUrl("generateTests", requirementId, cegId, testSpecId);
		logService.log(LogService.LOG_DEBUG, "Request test genreation at  url " + generateUrl);
		RestResult<JSONObject> result = restClient.post(generateUrl, null);
		Assert.assertEquals(Status.NO_CONTENT.getStatusCode(), result.getResponse().getStatus());

		String retrieveUrl = listUrl(requirementId, cegId, testSpecId);
		RestResult<JSONArray> getResult = restClient.getList(retrieveUrl);
		JSONArray retrievedTestChilds = getResult.getPayload();
		logService.log(LogService.LOG_DEBUG,
				"Retrieved the object " + retrievedTestChilds.toString() + " from url " + retrieveUrl);

		List<JSONObject> testCases = getTestCases(retrievedTestChilds);

		// Expect 3 tests should be generated
		Assert.assertEquals(3, testCases.size());

		int numberOfInconsistentTests = 0;
		for (JSONObject testCase : testCases) {
			if (!testCase.getBoolean("consistent")) {
				numberOfInconsistentTests++;
			}
		}
		Assert.assertEquals(1, numberOfInconsistentTests);
	}

	/**
	 * Generates a model with contradictory constraints and trys to generate test
	 * cases.
	 *
	 */
	@Test
	public void testContradictoryModelTestGeneration() {
		JSONObject requirement = postRequirementToRoot();
		String requirementId = getId(requirement);

		// Post ceg model
		JSONObject cegModel = postCEG(requirementId);
		String cegId = getId(cegModel);

		// post node 1
		JSONObject cegNode1 = postCEGNode(requirementId, cegId);
		String cegNode1Id = getId(cegNode1);
		JSONObject retrievedCegNode1 = getObject(requirementId, cegId, cegNode1Id);

		// post node 2
		JSONObject cegNode2 = postCEGNode(requirementId, cegId);
		String cegNode2Id = getId(cegNode2);
		JSONObject retrievedCegNode2 = getObject(requirementId, cegId, cegNode2Id);

		// post node 3
		JSONObject cegNode3 = postCEGNode(requirementId, cegId);
		String cegNode3Id = getId(cegNode3);
		JSONObject retrievedCegNode3 = getObject(requirementId, cegId, cegNode3Id);

		// post node 4
		JSONObject cegNode4 = postCEGNode(requirementId, cegId);
		String cegNode4Id = getId(cegNode4);
		JSONObject retrievedCegNode4 = getObject(requirementId, cegId, cegNode4Id);

		// post connections
		postCEGConnection(retrievedCegNode1, retrievedCegNode2, false, requirementId, cegId);
		postCEGConnection(retrievedCegNode1, retrievedCegNode3, false, requirementId, cegId);
		postCEGConnection(retrievedCegNode2, retrievedCegNode4, true, requirementId, cegId);
		postCEGConnection(retrievedCegNode3, retrievedCegNode4, false, requirementId, cegId);

		// Post test specification
		JSONObject testSpec = postTestSpecification(requirementId, cegId);
		String testSpecId = getId(testSpec);

		// Generate test cases
		String generateUrl = buildUrl("generateTests", requirementId, cegId, testSpecId);
		logService.log(LogService.LOG_DEBUG, "Request test genreation at  url " + generateUrl);
		RestResult<JSONObject> result = restClient.post(generateUrl, null);

		// Generation should succeed
		Assert.assertEquals(Status.NO_CONTENT.getStatusCode(), result.getResponse().getStatus());
		result.getResponse().close();

		String retrieveUrl = listUrl(requirementId, cegId, testSpecId);
		RestResult<JSONArray> getResult = restClient.getList(retrieveUrl);
		JSONArray retrievedTestChilds = getResult.getPayload();
		logService.log(LogService.LOG_DEBUG,
				"Retrieved the object " + retrievedTestChilds.toString() + " from url " + retrieveUrl);

		List<JSONObject> testCases = getTestCases(retrievedTestChilds);

		// Expect 4 tests should be generated
		Assert.assertEquals(4, testCases.size());

		int numberOfInconsistentTests = 0;
		for (JSONObject testCase : testCases) {
			if (!testCase.getBoolean("consistent")) {
				numberOfInconsistentTests++;
			}
		}
		Assert.assertEquals(2, numberOfInconsistentTests);
		getResult.getResponse().close();
	}

	/**
	 * Generates a model where the generation rules potentially lead to a conflict.
	 *
	 */
	@Test
	public void testConflictingRuleApplicationModelTestGeneration() {
		JSONObject requirement = postRequirementToRoot();
		String requirementId = getId(requirement);

		// Post ceg model
		JSONObject cegModel = postCEG(requirementId);
		String cegId = getId(cegModel);

		// post node 1
		JSONObject cegNode1 = postCEGNode(requirementId, cegId);
		String cegNode1Id = getId(cegNode1);
		JSONObject retrievedCegNode1 = getObject(requirementId, cegId, cegNode1Id);

		// post node 2
		JSONObject cegNode2 = postCEGNode(requirementId, cegId);
		String cegNode2Id = getId(cegNode2);
		JSONObject retrievedCegNode2 = getObject(requirementId, cegId, cegNode2Id);

		// post node 3
		JSONObject cegNode3 = postCEGNode(requirementId, cegId);
		String cegNode3Id = getId(cegNode3);
		JSONObject retrievedCegNode3 = getObject(requirementId, cegId, cegNode3Id);

		// post connections
		postCEGConnection(retrievedCegNode1, retrievedCegNode2, false, requirementId, cegId);
		postCEGConnection(retrievedCegNode1, retrievedCegNode3, false, requirementId, cegId);
		postCEGConnection(retrievedCegNode2, retrievedCegNode3, true, requirementId, cegId);

		// Post test specification
		JSONObject testSpec = postTestSpecification(requirementId, cegId);
		String testSpecId = getId(testSpec);

		// Generate test cases
		String generateUrl = buildUrl("generateTests", requirementId, cegId, testSpecId);
		logService.log(LogService.LOG_DEBUG, "Request test genreation at  url " + generateUrl);
		RestResult<JSONObject> result = restClient.post(generateUrl, null);

		// Generation should succeed
		Assert.assertEquals(Status.NO_CONTENT.getStatusCode(), result.getResponse().getStatus());
		result.getResponse().close();

		String retrieveUrl = listUrl(requirementId, cegId, testSpecId);
		RestResult<JSONArray> getResult = restClient.getList(retrieveUrl);
		JSONArray retrievedTestChilds = getResult.getPayload();
		logService.log(LogService.LOG_DEBUG,
				"Retrieved the object " + retrievedTestChilds.toString() + " from url " + retrieveUrl);

		List<JSONObject> testCases = getTestCases(retrievedTestChilds);

		// Expect 3 tests should be generated
		Assert.assertEquals(3, testCases.size());

		int numberOfInconsistentTests = 0;
		for (JSONObject testCase : testCases) {
			if (!testCase.getBoolean("consistent")) {
				numberOfInconsistentTests++;
			}
		}
		Assert.assertEquals(1, numberOfInconsistentTests);
		getResult.getResponse().close();
	}

	private List<JSONObject> getTestCases(JSONArray array) {
		ArrayList<JSONObject> testCases = new ArrayList<>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject object = array.getJSONObject(i);
			if (object.getString("className").equals("TestCase")) {
				testCases.add(object);
			}
		}
		return testCases;
	}

	/**
	 * Posts two test specifications to a CEG model and checks if they are retrieved
	 * by the list recursive service.
	 */
	@Test
	public void testGetListRecursive() {
		JSONObject requirement = postRequirementToRoot();
		String requirementId = getId(requirement);

		// Post ceg model
		JSONObject cegModel = postCEG(requirementId);
		String cegId = getId(cegModel);

		// Post test specification
		JSONObject testSpecification = postTestSpecification(requirementId, cegId);

		// Post second test specification
		JSONObject testSpecification2 = postTestSpecification(requirementId, cegId);

		// Perform recursive list call
		String listUrl = buildUrl("listRecursive", requirementId);
		RestResult<JSONArray> listResult = restClient.getList(listUrl, "class", "TestSpecification");
		Assert.assertEquals(Status.OK.getStatusCode(), listResult.getResponse().getStatus());
		JSONArray retrievedTestSpecifications = listResult.getPayload();
		logService.log(LogService.LOG_DEBUG,
				"Retrieved the object " + retrievedTestSpecifications.toString() + " from url " + listUrl);
		Assert.assertEquals(2, retrievedTestSpecifications.length());
		Assert.assertTrue(
				EmfRestTestUtil.compare(retrievedTestSpecifications.getJSONObject(0), testSpecification, true));
		Assert.assertTrue(
				EmfRestTestUtil.compare(retrievedTestSpecifications.getJSONObject(1), testSpecification2, true));
		listResult.getResponse().close();
	}
	
	/**
	 * Posts two test specifications to a Process model and checks if they are retrieved
	 * by the list recursive service.
	 */
	@Test
	public void testGetListRecursiveFromProcess() {
		JSONObject requirement = postRequirementToRoot();
		String requirementId = getId(requirement);

		// Post process model
		JSONObject processModel = postProcess(requirementId);
		String processId = getId(processModel);

		// Post test specification
		JSONObject testSpecification = postTestSpecification(requirementId, processId);

		// Post second test specification
		JSONObject testSpecification2 = postTestSpecification(requirementId, processId);

		// Perform recursive list call
		String listUrl = buildUrl("listRecursive", requirementId);
		RestResult<JSONArray> listResult = restClient.getList(listUrl, "class", "TestSpecification");
		Assert.assertEquals(Status.OK.getStatusCode(), listResult.getResponse().getStatus());
		JSONArray retrievedTestSpecifications = listResult.getPayload();
		logService.log(LogService.LOG_DEBUG,
				"Retrieved the object " + retrievedTestSpecifications.toString() + " from url " + listUrl);
		Assert.assertEquals(2, retrievedTestSpecifications.length());
		Assert.assertTrue(
				EmfRestTestUtil.compare(retrievedTestSpecifications.getJSONObject(0), testSpecification, true));
		Assert.assertTrue(
				EmfRestTestUtil.compare(retrievedTestSpecifications.getJSONObject(1), testSpecification2, true));
		listResult.getResponse().close();
	}

	protected JSONObject createTestBatchOp(JSONObject target, String type, JSONObject value) {
		JSONObject batchOp = new JSONObject();
		batchOp.put(NSURI_KEY, BatchPackage.eNS_URI);
		batchOp.put(ECLASS, BatchPackage.Literals.OPERATION.getName());
		batchOp.put(BatchPackage.Literals.OPERATION__TARGET.getName(), EmfRestTestUtil.proxy(target));
		batchOp.put(BatchPackage.Literals.OPERATION__VALUE.getName(), value);
		batchOp.put(BatchPackage.Literals.OPERATION__TYPE.getName(), type);
		return batchOp;
	}

	protected JSONObject createTestBatch(JSONObject... ops) {
		JSONObject batch = new JSONObject();
		batch.put(NSURI_KEY, BatchPackage.eNS_URI);
		batch.put(ECLASS, BatchPackage.Literals.BATCH_OPERATION.getName());
		JSONArray oparray = new JSONArray();
		for (JSONObject op : ops) {
			oparray.put(op);
		}
		batch.put(BatchPackage.Literals.BATCH_OPERATION__OPERATIONS.getName(), oparray);
		return batch;
	}

	@Test
	public void testBatch() {
		JSONObject topFolder = getObject();
		JSONObject folder = createTestFolder();
		String folderId = getId(folder);
		JSONObject toDelete = postFolder();
		String toDeleteId = getId(toDelete);
		JSONObject retrievedToDelete = getObject(toDeleteId);

		JSONObject batchOp = createTestBatchOp(topFolder, "CREATE", folder);
		// Set the correct url
		folder.put(EmfRestTestUtil.URL_KEY, topFolder.get(EmfRestTestUtil.URL_KEY) + "/" + folderId);

		JSONObject updateFolder = createTestFolder();
		updateFolder.put(BasePackage.Literals.IID__ID.getName(), folderId);
		JSONObject batchOp2 = createTestBatchOp(folder, "UPDATE", updateFolder);
		JSONObject batchOp3 = createTestBatchOp(retrievedToDelete, "DELETE", null);
		JSONObject batch = createTestBatch(batchOp, batchOp2, batchOp3);

		String postUrl = buildProjectUrl("batch");
		RestResult<JSONObject> result = restClient.post(postUrl, batch);
		result.getResponse().close();

		JSONObject retrievedFolder = getObject(folderId);
		Assert.assertTrue(EmfRestTestUtil.compare(updateFolder, retrievedFolder, true));

		getObject(Status.NOT_FOUND.getStatusCode(), toDeleteId);
	}

	@Test
	public void testInconsistentProject() {
		JSONObject topfolder0 = getObject();
		JSONObject ceg0 = postCEG();
		updateUrlFromParent(topfolder0, ceg0);

		// Post node in CEG of project 0
		JSONObject node00 = postCEGNode(getId(ceg0));
		updateUrlFromParent(ceg0, node00);

		nextProject();

		JSONObject topfolder1 = getObject();
		JSONObject ceg1 = postCEG();
		updateUrlFromParent(topfolder1, ceg1);

		// Post node in CEG of project 1
		JSONObject node10 = postCEGNode(getId(ceg1));
		updateUrlFromParent(ceg1, node10);

		resetSelectedProject();

		// Create a connection between nodes in different projects
		JSONObject cegConnection = createTestCEGConnection(node00, node10, false);

		// Try to post in project 0
		postObject(Status.UNAUTHORIZED.getStatusCode(), cegConnection, getId(ceg0));

		nextProject();

		// Try to post in project 1
		postObject(Status.UNAUTHORIZED.getStatusCode(), cegConnection, getId(ceg1));
	}

	@Test
	public void testDuplicate() {
		JSONObject requirement = postRequirementToRoot();
		String requirementId = getId(requirement);

		// Post ceg model
		JSONObject cegModel = postCEG(requirementId);
		String cegId = getId(cegModel);

		// Post process model
		JSONObject processModel = postProcess(requirementId);
		String processId = getId(processModel);

		// Post testspec model
		JSONObject testSpec = postTestSpecification(requirementId);
		String testSpecId = getId(testSpec);

		RestResult<JSONArray> result = restClient.getList(listUrl(requirementId));
		JSONArray payload = result.getPayload();
		Assert.assertEquals(3, payload.length());

		performDuplicate(requirementId, cegId);
		performDuplicate(requirementId, processId);
		performDuplicate(requirementId, testSpecId);

		result = restClient.getList(listUrl(requirementId));
		payload = result.getPayload();
		Assert.assertEquals(6, payload.length());

		// They are not equal, as id and name are different
		// Assert.assertTrue(EmfRestTestUtil.compare(payload.getJSONObject(0),
		// payload.getJSONObject(1), true));

	}
}

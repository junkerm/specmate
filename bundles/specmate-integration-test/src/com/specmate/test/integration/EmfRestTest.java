package com.specmate.test.integration;

import javax.ws.rs.core.Response.Status;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.log.LogService;
import org.osgi.util.tracker.ServiceTracker;

import com.specmate.common.RestClient;
import com.specmate.common.RestResult;
import com.specmate.common.SpecmateException;
import com.specmate.emfjson.EMFJsonSerializer;
import com.specmate.model.base.BasePackage;
import com.specmate.model.requirements.NodeType;
import com.specmate.model.requirements.RequirementsPackage;
import com.specmate.model.testspecification.TestspecificationPackage;
import com.specmate.persistency.IPersistencyService;
import com.specmate.persistency.ITransaction;
import com.specmate.persistency.IView;

public class EmfRestTest {

	private static final String ID_KEY = "id";
	private static final String NSURI_KEY = EMFJsonSerializer.KEY_NSURI;
	private static final String ECLASS = EMFJsonSerializer.KEY_ECLASS;
	private static BundleContext context;
	private static IPersistencyService persistency;

	protected static IView view;

	protected static int counter = 0;
	protected static LogService logService;
	private static RestClient restClient;

	@BeforeClass
	public static void init() throws Exception {
		context = FrameworkUtil.getBundle(EmfRestTest.class).getBundleContext();
		persistency = getPersistencyService();
		view = persistency.openView();
		logService = getLogger();
		restClient = new RestClient("http://localhost:8088/services/rest", logService);
		clearPersistency();
		Thread.sleep(2000);
	}

	private static LogService getLogger() throws InterruptedException {
		ServiceTracker<LogService, LogService> logTracker = new ServiceTracker<>(context, LogService.class.getName(),
				null);
		logTracker.open();
		LogService logService = logTracker.waitForService(10000);
		Assert.assertNotNull(logService);
		return logService;
	}

	private static void clearPersistency() throws SpecmateException {
		ITransaction transaction = persistency.openTransaction();
		transaction.getResource().getContents().clear();
		transaction.commit();
	}

	private static IPersistencyService getPersistencyService() throws InterruptedException, SpecmateException {
		ServiceTracker<ConfigurationAdmin, ConfigurationAdmin> configTracker = new ServiceTracker<>(context,
				ConfigurationAdmin.class.getName(), null);
		configTracker.open();
		// ConfigurationAdmin configAdmin = configTracker.getService();
		//
		// Dictionary<String, Object> properties = new Hashtable<>();
		// properties.put(CDOPersistenceConfig.KEY_REPOSITORY_NAME, "testRepo");
		// properties.put(CDOPersistenceConfig.KEY_RESOURCE_NAME,
		// "restResource");
		// OSGiUtil.configureService(configAdmin, CDOPersistenceConfig.PID,
		// properties);

		ServiceTracker<IPersistencyService, IPersistencyService> persistencyTracker = new ServiceTracker<>(context,
				IPersistencyService.class.getName(), null);
		persistencyTracker.open();
		IPersistencyService persistency = persistencyTracker.waitForService(100000);
		Assert.assertNotNull(persistency);
		return persistency;
	}

	private JSONObject createTestFolder(String folderId, String folderName) {
		JSONObject folder = new JSONObject();
		folder.put(NSURI_KEY, BasePackage.eNS_URI);
		folder.put(ECLASS, BasePackage.Literals.FOLDER.getName());
		folder.put(BasePackage.Literals.IID__ID.getName(), folderId);
		folder.put(BasePackage.Literals.INAMED__NAME.getName(), folderId);
		return folder;
	}

	private JSONObject createTestFolder() {
		String folderName = "TestFolder" + counter++;
		return createTestFolder(folderName, folderName);
	}

	private JSONObject createTestRequirement() {
		String requirementsName = "TestRequirement" + counter++;
		JSONObject requirement = new JSONObject();
		requirement.put(NSURI_KEY, RequirementsPackage.eNS_URI);
		requirement.put(ECLASS, RequirementsPackage.Literals.REQUIREMENT.getName());
		requirement.put(BasePackage.Literals.INAMED__NAME.getName(), requirementsName);
		requirement.put(BasePackage.Literals.IID__ID.getName(), requirementsName);
		requirement.put(BasePackage.Literals.IDESCRIBED__DESCRIPTION.getName(), "description");
		requirement.put(BasePackage.Literals.IEXTERNAL__EXT_ID.getName(), "extid123");
		requirement.put(BasePackage.Literals.IEXTERNAL__EXT_ID2.getName(), "extid456");
		requirement.put(BasePackage.Literals.IEXTERNAL__LIVE.getName(), false);
		requirement.put(RequirementsPackage.Literals.REQUIREMENT__NUMBER_OF_TESTS.getName(), "0");
		requirement.put(RequirementsPackage.Literals.REQUIREMENT__TAC.getName(), "tac");
		requirement.put(RequirementsPackage.Literals.REQUIREMENT__IMPLEMENTING_UNIT.getName(), "unit1");
		requirement.put(RequirementsPackage.Literals.REQUIREMENT__IMPLEMENTING_BO_TEAM.getName(), "bo2");
		requirement.put(RequirementsPackage.Literals.REQUIREMENT__IMPLEMENTING_IT_TEAM.getName(), "it1");
		requirement.put(RequirementsPackage.Literals.REQUIREMENT__PLANNED_RELEASE.getName(), "release1");
		requirement.put(RequirementsPackage.Literals.REQUIREMENT__STATUS.getName(), "status");
		requirement.put(RequirementsPackage.Literals.REQUIREMENT__IS_REGRESSION_REQUIREMENT.getName(), true);
		return requirement;
	}

	private JSONObject createTestCegModel() {
		String cegName = "TestCeg" + counter++;
		JSONObject ceg = new JSONObject();
		ceg.put(NSURI_KEY, RequirementsPackage.eNS_URI);
		ceg.put(ECLASS, RequirementsPackage.Literals.CEG_MODEL.getName());
		ceg.put(BasePackage.Literals.IID__ID.getName(), cegName);
		ceg.put(BasePackage.Literals.INAMED__NAME.getName(), cegName);
		return ceg;
	}

	private JSONObject createTestCegNode() {
		String cegName = "TestCegNode" + counter++;
		JSONObject cegNode = new JSONObject();
		cegNode.put(NSURI_KEY, RequirementsPackage.eNS_URI);
		cegNode.put(ECLASS, RequirementsPackage.Literals.CEG_NODE.getName());
		cegNode.put(BasePackage.Literals.IID__ID.getName(), cegName);
		cegNode.put(BasePackage.Literals.INAMED__NAME.getName(), cegName);
		cegNode.put(RequirementsPackage.Literals.CEG_NODE__VARIABLE.getName(), cegName);
		cegNode.put(RequirementsPackage.Literals.CEG_NODE__CONDITION.getName(), "5");
		cegNode.put(RequirementsPackage.Literals.CEG_NODE__TYPE.getName(), NodeType.OR.getLiteral());
		return cegNode;
	}

	private JSONObject createTestConnection(JSONObject node1, JSONObject node2) {
		String connectionName = "TestConnection" + counter++;
		JSONObject connection = new JSONObject();
		connection.put(NSURI_KEY, RequirementsPackage.eNS_URI);
		connection.put(ECLASS, RequirementsPackage.Literals.CEG_CONNECTION.getName());
		connection.put(BasePackage.Literals.IID__ID.getName(), connectionName);
		connection.put(BasePackage.Literals.IMODEL_CONNECTION__SOURCE.getName(), EmfRestTestUtil.proxy(node1));
		connection.put(BasePackage.Literals.IMODEL_CONNECTION__TARGET.getName(), EmfRestTestUtil.proxy(node2));
		connection.put(RequirementsPackage.Literals.CEG_CONNECTION__NEGATE.getName(), true);
		return connection;
	}

	private JSONObject createTestTestSpecification() {
		String testSpecName = "TestSpecification" + counter++;
		JSONObject testSpecification = new JSONObject();
		testSpecification.put(NSURI_KEY, TestspecificationPackage.eNS_URI);
		testSpecification.put(ECLASS, TestspecificationPackage.Literals.TEST_SPECIFICATION.getName());
		testSpecification.put(BasePackage.Literals.IID__ID.getName(), testSpecName);
		testSpecification.put(BasePackage.Literals.INAMED__NAME.getName(), testSpecName);
		return testSpecification;
	}

	private String buildUrl(String service, String... segments) {
		StringBuilder builder = new StringBuilder();
		for (String segment : segments) {
			builder.append("/").append(segment);
		}
		return builder.toString() + "/" + service;
	}

	private String detailUrl(String... segments) {
		return buildUrl("details", segments);
	}

	private String listUrl(String... segments) {
		return buildUrl("list", segments);
	}

	private String deleteUrl(String... segments) {
		return buildUrl("delete", segments);
	}

	private String getId(JSONObject requirement) {
		return requirement.getString(ID_KEY);
	}

	private JSONObject postFolderToRoot() {
		JSONObject folder = createTestFolder();
		return postObject(folder);
	}

	private JSONObject postFolder(String... segments) {
		JSONObject folder = createTestFolder();
		return postObject(folder, segments);
	}

	private JSONObject postRequirementToRoot() {
		return postRequirement();
	}

	private JSONObject postRequirement(String... segments) {
		JSONObject requirement = createTestRequirement();
		return postObject(requirement, segments);
	}

	private JSONObject postCEG(String... segments) {
		JSONObject cegModel = createTestCegModel();
		return postObject(cegModel, segments);
	}

	private JSONObject postCEGNode(String... segments) {
		JSONObject cegNode = createTestCegNode();
		return postObject(cegNode, segments);
	}

	private JSONObject postCEGConnection(JSONObject node1, JSONObject node2, String... segments) {
		JSONObject cegConnection = createTestConnection(node1, node2);
		return postObject(cegConnection, segments);
	}

	private JSONObject postTestSpecification(String... segments) {
		JSONObject testSpecification = createTestTestSpecification();
		return postObject(testSpecification, segments);
	}

	private JSONObject postObject(JSONObject object, String... segments) {
		return postObject(Status.OK.getStatusCode(), object, segments);
	}

	private JSONObject postObject(int statusCode, JSONObject object, String... segments) {
		String postUrl = listUrl(segments);
		logService.log(LogService.LOG_DEBUG, "Posting the object " + object.toString() + " to url " + postUrl);
		RestResult<JSONObject> result = restClient.post(postUrl, object);
		Assert.assertEquals(result.getResponse().getStatus(), statusCode);
		return object;
	}

	private void updateObject(JSONObject object, String segments) {
		String updateUrl = detailUrl(segments);
		logService.log(LogService.LOG_DEBUG, "Updateing the object " + object.toString() + " at url " + updateUrl);
		RestResult<JSONObject> putResult = restClient.put(updateUrl, object);
		Assert.assertEquals(Status.OK.getStatusCode(), putResult.getResponse().getStatus());
	}

	private JSONObject getObject(String... segments) {
		return getObject(Status.OK.getStatusCode(), segments);
	}

	private JSONObject getObject(int statusCode, String... segments) {
		String retrieveUrl = detailUrl(segments);
		RestResult<JSONObject> getResult = restClient.get(retrieveUrl);
		JSONObject retrievedObject = getResult.getPayload();
		if (retrievedObject != null) {
			logService.log(LogService.LOG_DEBUG,
					"Retrieved the object " + retrievedObject.toString() + " from url " + retrieveUrl);
		} else {
			logService.log(LogService.LOG_DEBUG, "Empty result from url " + retrieveUrl);
		}
		Assert.assertEquals(statusCode, getResult.getResponse().getStatus());
		return retrievedObject;
	}

	private void deleteObject(String... segments) {
		// Delete folder
		String deleteUrl = deleteUrl(segments);
		logService.log(LogService.LOG_DEBUG, "Deleting object with URL " + deleteUrl);
		RestResult<Object> deleteResult = restClient.delete(deleteUrl);
		Assert.assertEquals(Status.OK.getStatusCode(), deleteResult.getResponse().getStatus());
	}

	private JSONArray performSearch(String query) {
		String searchUrl = buildUrl("search");
		RestResult<JSONArray> result = restClient.getList(searchUrl, "query", query);
		Assert.assertEquals(Status.OK.getStatusCode(), result.getResponse().getStatus());
		JSONArray foundObjects = result.getPayload();
		return foundObjects;
	}

	/**
	 * Tests posting a folder to the root. Checks, if the return code of the
	 * post request is OK and if retrieving the object again returns the
	 * original object.
	 */
	@Test
	public void testPostFolderToRootAndRetrieve() {
		String postUrl = listUrl();
		JSONObject folder = createTestFolder();
		logService.log(LogService.LOG_DEBUG, "Posting the object " + folder.toString() + " to url " + postUrl);
		RestResult<JSONObject> result = restClient.post(postUrl, folder);
		Assert.assertEquals(result.getResponse().getStatus(), Status.OK.getStatusCode());

		String retrieveUrl = detailUrl(getId(folder));
		RestResult<JSONObject> getResult = restClient.get(retrieveUrl);
		JSONObject retrievedFolder = getResult.getPayload();
		logService.log(LogService.LOG_DEBUG,
				"Retrieved the object " + retrievedFolder.toString() + " from url " + retrieveUrl);
		Assert.assertTrue(EmfRestTestUtil.compare(folder, retrievedFolder, true));
	}

	/**
	 * Tests posting a folder that contains special characters in its name.
	 * Checks, if the return code of the post request is OK and if retrieving
	 * the object again returns the original object.
	 */
	@Test
	public void testPostFolderWithSpecialChars() {
		String postUrl = listUrl();
		JSONObject folder = createTestFolder("TestFolder", "äöüß§$% &=?!/\\^_:.,#'+~*(){}[]");
		logService.log(LogService.LOG_DEBUG, "Posting the object " + folder.toString() + " to url " + postUrl);
		RestResult<JSONObject> result = restClient.post(postUrl, folder);
		Assert.assertEquals(result.getResponse().getStatus(), Status.OK.getStatusCode());

		String retrieveUrl = detailUrl(getId(folder));
		RestResult<JSONObject> getResult = restClient.get(retrieveUrl);
		JSONObject retrievedFolder = getResult.getPayload();
		logService.log(LogService.LOG_DEBUG,
				"Retrieved the object " + retrievedFolder.toString() + " from url " + retrieveUrl);
		Assert.assertTrue(EmfRestTestUtil.compare(folder, retrievedFolder, true));
	}

	/**
	 * Tests posting a folder to another folder. Checks, if the return code of
	 * the post request is OK and if retrieving the object again returns the
	 * original object.
	 */
	@Test
	public void testPostFolderToFolderAndRetrieve() {
		JSONObject folder = postFolderToRoot();
		String folderName = getId(folder);

		String postUrl2 = listUrl(folderName);
		JSONObject folder2 = createTestFolder();
		String folderName2 = getId(folder2);
		logService.log(LogService.LOG_DEBUG, "Posting the object " + folder2.toString() + " to url " + postUrl2);
		RestResult<JSONObject> result2 = restClient.post(postUrl2, folder2);
		Assert.assertEquals(result2.getResponse().getStatus(), Status.OK.getStatusCode());

		String retrieveUrl = detailUrl(folderName, folderName2);
		RestResult<JSONObject> getResult = restClient.get(retrieveUrl);
		JSONObject retrievedFolder = getResult.getPayload();
		logService.log(LogService.LOG_DEBUG,
				"Retrieved the object " + retrievedFolder.toString() + " from url " + retrieveUrl);
		Assert.assertTrue(EmfRestTestUtil.compare(retrievedFolder, folder2, true));
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

	}

	/** Tests retrieving a list of child folders from a folder */
	@Test
	public void testRetrieveChildrenList() {
		int numberOfChildren = 2;

		JSONObject folder = postFolderToRoot();
		String folderName = getId(folder);

		String postUrl2 = listUrl(folderName);
		JSONObject[] folders = new JSONObject[2];
		for (int i = 0; i < numberOfChildren; i++) {
			folders[i] = createTestFolder();
			logService.log(LogService.LOG_DEBUG, "Posting the object " + folders[i].toString() + " to url " + postUrl2);
			RestResult<JSONObject> result2 = restClient.post(postUrl2, folders[i]);
			Assert.assertEquals(result2.getResponse().getStatus(), Status.OK.getStatusCode());
		}

		RestResult<JSONArray> listResult = restClient.getList(postUrl2);
		JSONArray childrenList = listResult.getPayload();
		logService.log(LogService.LOG_DEBUG, "Retrieved the list " + childrenList.toString() + " from url " + postUrl2);
		Assert.assertEquals(2, childrenList.length());
		for (int i = 0; i < numberOfChildren; i++) {
			Assert.assertTrue(EmfRestTestUtil.compare(folders[i], childrenList.getJSONObject(i), true));
		}

	}

	/** Tests if an empty folder can be deleted */
	@Test
	public void testDeleteEmptyFolder() {
		JSONObject folder = postFolderToRoot();
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
		JSONObject outerFolder = postFolderToRoot();
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
	 * Tests posting a requirement to a folder. Checks, if the return code of
	 * the post request is OK and if retrieving the requirement again returns
	 * the original object.
	 */
	@Test
	public void testPostRequirementToFolderAndRetrieve() {
		JSONObject folder = postFolderToRoot();
		String folderName = getId(folder);

		JSONObject requirement = postRequirement(folderName);
		String requirementName = getId(requirement);

		getObject(folderName, requirementName);
	}

	@Test
	public void testUpdateFolder() {
		JSONObject folder = postFolderToRoot();
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
		JSONObject connection = postCEGConnection(retrievedCegNode1, retrievedCegNode2, requirementId, cegId);
		String connectionId = getId(connection);

		JSONObject retrievedConnection = getObject(requirementId, cegId, connectionId);
		Assert.assertTrue(EmfRestTestUtil.compare(retrievedConnection, connection, true));
	}

	@Test
	public void testPostFolderWithNoId() {
		JSONObject folder = createTestFolder();
		folder.remove(ID_KEY);

		postObject(Status.BAD_REQUEST.getStatusCode(), folder);
	}

	@Test
	public void testPostFolderWithDuplicateId() {
		JSONObject folder = postFolderToRoot();
		postObject(Status.BAD_REQUEST.getStatusCode(), folder);
	}

	@Test
	public void testPostFolderWithIllegalId() {
		JSONObject folder = createTestFolder();
		folder.put(ID_KEY, "id with spaces");
		postObject(Status.BAD_REQUEST.getStatusCode(), folder);
	}

	@Test
	public void testPostTestSpecification() {
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
	public void testGenerateTests() {
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
		postCEGConnection(retrievedCegNode1, retrievedCegNode2, requirementId, cegId);

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

		// Expect 4 chidren: two test cases and two test parameters
		Assert.assertEquals(4, retrievedTestChilds.length());
	}

	/**
	 * Posts two test specifications to a CEG model and checks if they are
	 * retrieved by the list recursive service.
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

		// Peform recursive list call
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
	}

	/**
	 * Posts two test specifications to a CEG model and checks if they are
	 * retrieved by the list recursive service.
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void testSearch() throws InterruptedException {
		JSONObject folder = createTestFolder();
		folder.put(BasePackage.Literals.INAMED__NAME.getName(), "Test");
		folder.put(BasePackage.Literals.IDESCRIBED__DESCRIPTION.getName(), "TEST");
		postObject(folder);
		String folderName = getId(folder);

		JSONObject requirement = createTestRequirement();
		requirement.put(BasePackage.Literals.INAMED__NAME.getName(), "Test BLA BLI");
		requirement.put(BasePackage.Literals.IDESCRIBED__DESCRIPTION.getName(), "TEST BLUP");
		postObject(requirement, folderName);
		String requirementId = getId(requirement);

		JSONObject requirement2 = createTestRequirement();
		requirement2.put(BasePackage.Literals.INAMED__NAME.getName(), "Test");
		requirement2.put(BasePackage.Literals.IDESCRIBED__DESCRIPTION.getName(), "TEST BLI");
		requirement2.put(BasePackage.Literals.IEXTERNAL__EXT_ID.getName(), "4711");
		postObject(requirement2, folderName);

		JSONObject requirement3 = createTestRequirement();
		requirement3.put(BasePackage.Literals.INAMED__NAME.getName(), "Tree");
		requirement3.put(BasePackage.Literals.IDESCRIBED__DESCRIPTION.getName(), "Tree");
		postObject(requirement3, folderName);

		JSONObject cegModel = createTestCegModel();
		cegModel.put(BasePackage.Literals.INAMED__NAME.getName(), "Test CEG");
		cegModel.put(BasePackage.Literals.IDESCRIBED__DESCRIPTION.getName(), "CEG");
		postObject(cegModel, folderName, requirementId);

		// Allow time to commit to search index
		Thread.sleep(8000);

		// Check if search on name field works
		JSONArray foundObjects = performSearch("blup");
		Assert.assertEquals(1, foundObjects.length());

		foundObjects = performSearch("BLUP");
		Assert.assertEquals(1, foundObjects.length());

		foundObjects = performSearch("Blup");
		Assert.assertEquals(1, foundObjects.length());

		// check if search on description field works
		foundObjects = performSearch("bla");
		Assert.assertEquals(1, foundObjects.length());

		// check if search on extid field works
		foundObjects = performSearch("4711");
		Assert.assertEquals(1, foundObjects.length());

		// check if search on multiple fields across objects works
		foundObjects = performSearch("bli");
		Assert.assertEquals(2, foundObjects.length());

		// check if wildcard search workds
		foundObjects = performSearch("bl*");
		Assert.assertEquals(2, foundObjects.length());

		// check if explicit name search works
		foundObjects = performSearch("name:bli");
		Assert.assertEquals(1, foundObjects.length());

		// check if explicit description search works
		foundObjects = performSearch("description:bli");
		Assert.assertEquals(1, foundObjects.length());

		// check if negative search works
		foundObjects = performSearch("bli -(name:bla)");
		Assert.assertEquals(1, foundObjects.length());

		// check if type search workds
		foundObjects = performSearch("type:CEGModel");
		Assert.assertEquals(1, foundObjects.length());

		// check if type search workds
		foundObjects = performSearch("type:Requirement");
		Assert.assertEquals(3, foundObjects.length());

		// check if search is robust agains syntax errors (no closed bracket)
		foundObjects = performSearch("(type:Requirement");
		Assert.assertEquals(0, foundObjects.length());

		// check if search is robust agains syntax errors (no opened bracket)
		foundObjects = performSearch("type:Requirement)");
		Assert.assertEquals(0, foundObjects.length());

		// check if search is robust agains syntax errors (no opened bracket)
		foundObjects = performSearch("type:Requirement)");
		Assert.assertEquals(0, foundObjects.length());

	}

}
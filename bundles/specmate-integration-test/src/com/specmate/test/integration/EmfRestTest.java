package com.specmate.test.integration;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;

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

import com.specmate.common.OSGiUtil;
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
import com.specmate.persistency.cdo.CDOPersistenceConfig;

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
		restClient = new RestClient("http://localhost:8088/services/rest");
		clearPersistency();
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

	private static IPersistencyService getPersistencyService() throws InterruptedException, IOException {
		ServiceTracker<ConfigurationAdmin, ConfigurationAdmin> configTracker = new ServiceTracker<>(context,
				ConfigurationAdmin.class.getName(), null);
		configTracker.open();
		ConfigurationAdmin configAdmin = configTracker.getService();

		Dictionary<String, Object> properties = new Hashtable<>();
		properties.put(CDOPersistenceConfig.KEY_REPOSITORY_NAME, "testRepo");
		properties.put(CDOPersistenceConfig.KEY_RESOURCE_NAME, "restResource");
		OSGiUtil.configureService(configAdmin, CDOPersistenceConfig.PID, properties);

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
		requirement.put(RequirementsPackage.Literals.REQUIREMENT__EXT_ID.getName(), "extid123");
		requirement.put(RequirementsPackage.Literals.REQUIREMENT__EXT_ID2.getName(), "extid456");
		requirement.put(RequirementsPackage.Literals.REQUIREMENT__NUMBER_OF_TESTS.getName(), "0");
		requirement.put(RequirementsPackage.Literals.REQUIREMENT__TAC.getName(), "tac");
		requirement.put(RequirementsPackage.Literals.REQUIREMENT__IMPLEMENTING_UNIT.getName(), "unit1");
		requirement.put(RequirementsPackage.Literals.REQUIREMENT__IMPLEMENTING_BO_TEAM.getName(), "bo2");
		requirement.put(RequirementsPackage.Literals.REQUIREMENT__IMPLEMENTING_IT_TEAM.getName(), "it1");
		requirement.put(RequirementsPackage.Literals.REQUIREMENT__PLANNED_RELEASE.getName(), "release1");
		requirement.put(RequirementsPackage.Literals.REQUIREMENT__STATUS.getName(), "status");
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
		cegNode.put(RequirementsPackage.Literals.CEG_NODE__OPERATOR.getName(), "=");
		cegNode.put(RequirementsPackage.Literals.CEG_NODE__VALUE.getName(), "5");
		cegNode.put(RequirementsPackage.Literals.CEG_NODE__TYPE.getName(), NodeType.OR.getLiteral());
		return cegNode;
	}

	private JSONObject createTestConnection(JSONObject node1, JSONObject node2) {
		String connectionName = "TestConnection" + counter++;
		JSONObject connection = new JSONObject();
		connection.put(NSURI_KEY, RequirementsPackage.eNS_URI);
		connection.put(ECLASS, RequirementsPackage.Literals.CEG_CONNECTION.getName());
		connection.put(BasePackage.Literals.IID__ID.getName(), connectionName);
		connection.put(RequirementsPackage.Literals.CEG_CONNECTION__SOURCE.getName(), EmfRestTestUtil.proxy(node1));
		connection.put(RequirementsPackage.Literals.CEG_CONNECTION__TARGET.getName(), EmfRestTestUtil.proxy(node2));
		connection.put(RequirementsPackage.Literals.CEG_CONNECTION__NEGATE.getName(), "true");
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

		String retrieveUrl = detailUrl(folder.getString(ID_KEY));
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

		String retrieveUrl = detailUrl(folder.getString(ID_KEY));
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
		String postUrl = listUrl();
		JSONObject folder = createTestFolder();
		String folderName = folder.getString(ID_KEY);

		logService.log(LogService.LOG_DEBUG, "Posting the object " + folder.toString() + " to url " + postUrl);
		RestResult<JSONObject> result = restClient.post(postUrl, folder);
		Assert.assertEquals(result.getResponse().getStatus(), Status.OK.getStatusCode());

		String postUrl2 = listUrl(folderName);
		JSONObject folder2 = createTestFolder();
		String folderName2 = folder2.getString(ID_KEY);
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
		String folderName = folder.getString(ID_KEY);

		// Not posting to backend instead try to retrieve
		String retrieveUrl = detailUrl(folderName);
		RestResult<JSONObject> getResult = restClient.get(retrieveUrl);

		Assert.assertEquals(Status.NOT_FOUND.getStatusCode(), getResult.getResponse().getStatus());

	}

	/** Tests retrieving a list of child folders from a folder */
	@Test
	public void testRetrieveChildrenList() {
		int numberOfChildren = 2;

		String postUrl = listUrl();
		JSONObject folder = createTestFolder();
		String folderName = folder.getString(ID_KEY);
		logService.log(LogService.LOG_DEBUG, "Posting the object " + folder.toString() + " to url " + postUrl);
		RestResult<JSONObject> result = restClient.post(postUrl, folder);
		Assert.assertEquals(result.getResponse().getStatus(), Status.OK.getStatusCode());

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
		// Post folder to root
		String postUrl = listUrl();
		JSONObject folder = createTestFolder();
		logService.log(LogService.LOG_DEBUG, "Posting the object " + folder.toString() + " to url " + postUrl);
		RestResult<JSONObject> result = restClient.post(postUrl, folder);
		Assert.assertEquals(Status.OK.getStatusCode(), result.getResponse().getStatus());

		// Check if folder exists
		String retrieveUrl = detailUrl(folder.getString(ID_KEY));
		RestResult<JSONObject> getResult = restClient.get(retrieveUrl);
		JSONObject retrievedFolder = getResult.getPayload();
		logService.log(LogService.LOG_DEBUG,
				"Retrieved the object " + retrievedFolder.toString() + " from url " + retrieveUrl);
		Assert.assertEquals(Status.OK.getStatusCode(), getResult.getResponse().getStatus());

		// Delete folder
		String deleteUrl = deleteUrl(folder.getString(ID_KEY));
		logService.log(LogService.LOG_DEBUG, "Deleting object with URL " + deleteUrl);
		RestResult<Object> deleteResult = restClient.delete(deleteUrl);
		Assert.assertEquals(Status.OK.getStatusCode(), deleteResult.getResponse().getStatus());

		// Check if folder still exists
		getResult = restClient.get(retrieveUrl);
		Assert.assertEquals(Status.NOT_FOUND.getStatusCode(), getResult.getResponse().getStatus());
	}

	/** Tests if a non-empty folder can be deleted */
	@Test
	public void testDeleteNonEmptyFolder() {
		// Post folder to root
		String postUrl = listUrl();
		JSONObject folder = createTestFolder();
		String folderName = folder.getString(ID_KEY);
		logService.log(LogService.LOG_DEBUG, "Posting the object " + folder.toString() + " to url " + postUrl);
		RestResult<JSONObject> result = restClient.post(postUrl, folder);
		Assert.assertEquals(Status.OK.getStatusCode(), result.getResponse().getStatus());

		// Post folder in new folder
		String postUrl2 = listUrl(folderName);
		JSONObject folder2 = createTestFolder();
		logService.log(LogService.LOG_DEBUG, "Posting the object " + folder2.toString() + " to url " + postUrl2);
		RestResult<JSONObject> result2 = restClient.post(postUrl2, folder2);
		Assert.assertEquals(result2.getResponse().getStatus(), Status.OK.getStatusCode());

		// Check if top level folder exists
		String retrieveUrl = detailUrl(folder.getString(ID_KEY));
		RestResult<JSONObject> getResult = restClient.get(retrieveUrl);
		JSONObject retrievedFolder = getResult.getPayload();
		logService.log(LogService.LOG_DEBUG,
				"Retrieved the object " + retrievedFolder.toString() + " from url " + retrieveUrl);
		Assert.assertEquals(Status.OK.getStatusCode(), getResult.getResponse().getStatus());

		// Delete top level folder
		String deleteUrl = deleteUrl(folder.getString(ID_KEY));
		logService.log(LogService.LOG_DEBUG, "Deleting object with URL " + deleteUrl);
		RestResult<Object> deleteResult = restClient.delete(deleteUrl);
		Assert.assertEquals(Status.OK.getStatusCode(), deleteResult.getResponse().getStatus());

		// Check if top level folder still exists
		getResult = restClient.get(retrieveUrl);
		Assert.assertEquals(Status.NOT_FOUND.getStatusCode(), getResult.getResponse().getStatus());
	}

	/**
	 * Tests posting a requirement to a folder. Checks, if the return code of
	 * the post request is OK and if retrieving the requirement again returns
	 * the original object.
	 */
	@Test
	public void testPostRequirementToFolderAndRetrieve() {
		String postUrl = listUrl();
		JSONObject folder = createTestFolder();
		String folderName = folder.getString(ID_KEY);

		logService.log(LogService.LOG_DEBUG, "Posting the object " + folder.toString() + " to url " + postUrl);
		RestResult<JSONObject> result = restClient.post(postUrl, folder);
		Assert.assertEquals(result.getResponse().getStatus(), Status.OK.getStatusCode());

		String postUrl2 = listUrl(folderName);
		JSONObject requirement = createTestRequirement();
		String requirementName = requirement.getString(ID_KEY);
		logService.log(LogService.LOG_DEBUG, "Posting the object " + requirement.toString() + " to url " + postUrl2);
		RestResult<JSONObject> result2 = restClient.post(postUrl2, requirement);
		Assert.assertEquals(Status.OK.getStatusCode(), result2.getResponse().getStatus());

		String retrieveUrl = detailUrl(folderName, requirementName);
		RestResult<JSONObject> getResult = restClient.get(retrieveUrl);
		JSONObject retrievedFolder = getResult.getPayload();
		logService.log(LogService.LOG_DEBUG,
				"Retrieved the object " + retrievedFolder.toString() + " from url " + retrieveUrl);
		Assert.assertTrue(EmfRestTestUtil.compare(retrievedFolder, requirement, true));
	}

	@Test
	public void testUpdateFolder() {
		String postUrl = listUrl();
		JSONObject folder = createTestFolder();
		logService.log(LogService.LOG_DEBUG, "Posting the object " + folder.toString() + " to url " + postUrl);
		RestResult<JSONObject> result = restClient.post(postUrl, folder);
		Assert.assertEquals(result.getResponse().getStatus(), Status.OK.getStatusCode());

		String updateUrl = detailUrl(folder.getString(ID_KEY));
		folder.put(BasePackage.Literals.INAMED__NAME.getName(), "New Name");
		logService.log(LogService.LOG_DEBUG, "Updateing the object " + folder.toString() + " at url " + updateUrl);
		RestResult<JSONObject> putResult = restClient.put(updateUrl, folder);
		Assert.assertEquals(Status.OK.getStatusCode(), putResult.getResponse().getStatus());

		String getUrl = detailUrl(folder.getString(ID_KEY));
		RestResult<JSONObject> getResult = restClient.get(getUrl);
		Assert.assertEquals(Status.OK.getStatusCode(), getResult.getResponse().getStatus());
		JSONObject retrievedFolder = getResult.getPayload();
		logService.log(LogService.LOG_DEBUG,
				"Retrieved the object " + retrievedFolder.toString() + " from url " + getUrl);
		Assert.assertTrue(EmfRestTestUtil.compare(folder, retrievedFolder, true));
	}

	@Test
	public void testPostCEGToRequirement() {
		String postUrl = listUrl();
		JSONObject requirement = createTestRequirement();
		String requirementId = requirement.getString(ID_KEY);
		logService.log(LogService.LOG_DEBUG, "Posting the object " + requirement.toString() + " to url " + postUrl);
		RestResult<JSONObject> result = restClient.post(postUrl, requirement);
		Assert.assertEquals(Status.OK.getStatusCode(), result.getResponse().getStatus());

		String postUrl2 = listUrl(requirementId);
		JSONObject cegModel = createTestCegModel();
		String cegId = cegModel.getString(ID_KEY);
		logService.log(LogService.LOG_DEBUG, "Posting the object " + cegModel.toString() + " to url " + postUrl2);
		RestResult<JSONObject> result2 = restClient.post(postUrl2, cegModel);
		Assert.assertEquals(Status.OK.getStatusCode(), result2.getResponse().getStatus());

		String retrieveUrl = detailUrl(requirementId, cegId);
		RestResult<JSONObject> getResult = restClient.get(retrieveUrl);
		JSONObject retrievedCeg = getResult.getPayload();
		logService.log(LogService.LOG_DEBUG,
				"Retrieved the object " + retrievedCeg.toString() + " from url " + retrieveUrl);
		Assert.assertTrue(EmfRestTestUtil.compare(retrievedCeg, cegModel, true));
	}

	@Test
	public void testPostCEGNodesAndConnectionToCEG() {
		// post requirement
		String requirementPostUrl = listUrl();
		JSONObject requirement = createTestRequirement();
		String requirementId = requirement.getString(ID_KEY);
		logService.log(LogService.LOG_DEBUG,
				"Posting the object " + requirement.toString() + " to url " + requirementPostUrl);
		RestResult<JSONObject> result = restClient.post(requirementPostUrl, requirement);
		Assert.assertEquals(Status.OK.getStatusCode(), result.getResponse().getStatus());

		// post ceg
		String cegPostUrl = listUrl(requirementId);
		JSONObject cegModel = createTestCegModel();
		String cegId = cegModel.getString(ID_KEY);
		logService.log(LogService.LOG_DEBUG, "Posting the object " + cegModel.toString() + " to url " + cegPostUrl);
		result = restClient.post(cegPostUrl, cegModel);
		Assert.assertEquals(Status.OK.getStatusCode(), result.getResponse().getStatus());

		String nodePostUrl = listUrl(requirementId, cegId);

		// post node 1
		JSONObject cegNode1 = createTestCegNode();
		String cegNodeId1 = cegNode1.getString(ID_KEY);
		logService.log(LogService.LOG_DEBUG, "Posting the object " + cegNode1.toString() + " to url " + nodePostUrl);
		result = restClient.post(nodePostUrl, cegNode1);
		Assert.assertEquals(Status.OK.getStatusCode(), result.getResponse().getStatus());

		String cegNode1RetrieveUrl = detailUrl(requirementId, cegId, cegNodeId1);
		result = restClient.get(cegNode1RetrieveUrl);
		JSONObject retrievedCegNode1 = result.getPayload();
		logService.log(LogService.LOG_DEBUG,
				"Retrieved the object " + retrievedCegNode1.toString() + " from url " + cegNode1RetrieveUrl);
		Assert.assertTrue(EmfRestTestUtil.compare(cegNode1, retrievedCegNode1, true));

		// post node 2
		JSONObject cegNode2 = createTestCegNode();
		String cegNodeId2 = cegNode2.getString(ID_KEY);
		logService.log(LogService.LOG_DEBUG, "Posting the object " + cegNode2.toString() + " to url " + nodePostUrl);
		result = restClient.post(nodePostUrl, cegNode2);
		Assert.assertEquals(Status.OK.getStatusCode(), result.getResponse().getStatus());

		String cegNode2retrieveUrl = detailUrl(requirementId, cegId, cegNodeId2);
		result = restClient.get(cegNode2retrieveUrl);
		JSONObject retrievedCegNode2 = result.getPayload();
		logService.log(LogService.LOG_DEBUG,
				"Retrieved the object " + retrievedCegNode2.toString() + " from url " + cegNode2retrieveUrl);
		Assert.assertTrue(EmfRestTestUtil.compare(cegNode2, retrievedCegNode2, true));

		// post connection
		JSONObject connection = createTestConnection(retrievedCegNode1, retrievedCegNode2);
		String connectionId = connection.getString(ID_KEY);
		logService.log(LogService.LOG_DEBUG, "Posting the object " + cegNode2.toString() + " to url " + nodePostUrl);
		result = restClient.post(nodePostUrl, connection);
		Assert.assertEquals(Status.OK.getStatusCode(), result.getResponse().getStatus());

		String connectionRetrieveUrl = detailUrl(requirementId, cegId, connectionId);
		result = restClient.get(connectionRetrieveUrl);
		JSONObject retrievedConnection = result.getPayload();
		logService.log(LogService.LOG_DEBUG,
				"Retrieved the object " + retrievedConnection.toString() + " from url " + cegNode2retrieveUrl);
		Assert.assertTrue(EmfRestTestUtil.compare(retrievedConnection, connection, true));
	}

	@Test
	public void testPostFolderWithNoId() {
		String postUrl = "/list";
		JSONObject folder = createTestFolder();
		folder.remove(ID_KEY);
		logService.log(LogService.LOG_DEBUG, "Posting the object " + folder.toString() + " to url " + postUrl);
		RestResult<JSONObject> result = restClient.post(postUrl, folder);
		Assert.assertEquals(Status.BAD_REQUEST.getStatusCode(), result.getResponse().getStatus());
	}

	@Test
	public void testPostFolderWithDuplicateId() {
		String postUrl = "/list";
		JSONObject folder = createTestFolder();

		logService.log(LogService.LOG_DEBUG, "Posting the object " + folder.toString() + " to url " + postUrl);
		RestResult<JSONObject> result = restClient.post(postUrl, folder);
		Assert.assertEquals(Status.OK.getStatusCode(), result.getResponse().getStatus());

		logService.log(LogService.LOG_DEBUG, "Posting the object " + folder.toString() + " to url " + postUrl);
		result = restClient.post(postUrl, folder);
		Assert.assertEquals(Status.BAD_REQUEST.getStatusCode(), result.getResponse().getStatus());
	}

	@Test
	public void testPostFolderWithIllegalId() {
		String postUrl = "/list";
		JSONObject folder = createTestFolder();
		folder.put(ID_KEY, "id with spaces");
		logService.log(LogService.LOG_DEBUG, "Posting the object " + folder.toString() + " to url " + postUrl);
		RestResult<JSONObject> result = restClient.post(postUrl, folder);
		Assert.assertEquals(Status.BAD_REQUEST.getStatusCode(), result.getResponse().getStatus());
	}

	@Test
	public void testPostTestSpecification() {
		// Post requirement
		String postUrl = listUrl();
		JSONObject requirement = createTestRequirement();
		String requirementId = requirement.getString(ID_KEY);
		logService.log(LogService.LOG_DEBUG, "Posting the object " + requirement.toString() + " to url " + postUrl);
		RestResult<JSONObject> result = restClient.post(postUrl, requirement);
		Assert.assertEquals(Status.OK.getStatusCode(), result.getResponse().getStatus());

		// Post ceg model
		String postUrl2 = listUrl(requirementId);
		JSONObject cegModel = createTestCegModel();
		String cegId = cegModel.getString(ID_KEY);
		logService.log(LogService.LOG_DEBUG, "Posting the object " + cegModel.toString() + " to url " + postUrl2);
		RestResult<JSONObject> result2 = restClient.post(postUrl2, cegModel);
		Assert.assertEquals(Status.OK.getStatusCode(), result2.getResponse().getStatus());

		// Post test specification
		String postUrl3 = listUrl(requirementId, cegId);
		JSONObject testSpecification = createTestTestSpecification();
		String testSpecificationId = testSpecification.getString(ID_KEY);
		logService.log(LogService.LOG_DEBUG,
				"Posting the object " + testSpecification.toString() + " to url " + postUrl3);
		RestResult<JSONObject> result3 = restClient.post(postUrl3, testSpecification);
		Assert.assertEquals(Status.OK.getStatusCode(), result3.getResponse().getStatus());

		String retrieveUrl = detailUrl(requirementId, cegId, testSpecificationId);
		RestResult<JSONObject> getResult = restClient.get(retrieveUrl);
		JSONObject retrievedTestSpecification = getResult.getPayload();
		logService.log(LogService.LOG_DEBUG,
				"Retrieved the object " + retrievedTestSpecification.toString() + " from url " + retrieveUrl);
		Assert.assertTrue(EmfRestTestUtil.compare(retrievedTestSpecification, testSpecification, true));
	}

	@Test
	public void testGenerateTests() {
		// Post requirement
		String postUrl = listUrl();
		JSONObject requirement = createTestRequirement();
		String requirementId = requirement.getString(ID_KEY);
		logService.log(LogService.LOG_DEBUG, "Posting the object " + requirement.toString() + " to url " + postUrl);
		RestResult<JSONObject> result = restClient.post(postUrl, requirement);
		Assert.assertEquals(Status.OK.getStatusCode(), result.getResponse().getStatus());

		// Post ceg model
		String postUrl2 = listUrl(requirementId);
		JSONObject cegModel = createTestCegModel();
		String cegId = cegModel.getString(ID_KEY);
		logService.log(LogService.LOG_DEBUG, "Posting the object " + cegModel.toString() + " to url " + postUrl2);
		RestResult<JSONObject> result2 = restClient.post(postUrl2, cegModel);
		Assert.assertEquals(Status.OK.getStatusCode(), result2.getResponse().getStatus());

		String nodePostUrl = listUrl(requirementId, cegId);

		// post node 1
		JSONObject cegNode1 = createTestCegNode();
		String cegNodeId1 = cegNode1.getString(ID_KEY);
		logService.log(LogService.LOG_DEBUG, "Posting the object " + cegNode1.toString() + " to url " + nodePostUrl);
		result = restClient.post(nodePostUrl, cegNode1);
		Assert.assertEquals(Status.OK.getStatusCode(), result.getResponse().getStatus());

		String cegNode1RetrieveUrl = detailUrl(requirementId, cegId, cegNodeId1);
		result = restClient.get(cegNode1RetrieveUrl);
		JSONObject retrievedCegNode1 = result.getPayload();
		logService.log(LogService.LOG_DEBUG,
				"Retrieved the object " + retrievedCegNode1.toString() + " from url " + cegNode1RetrieveUrl);
		Assert.assertTrue(EmfRestTestUtil.compare(cegNode1, retrievedCegNode1, true));

		// post node 2
		JSONObject cegNode2 = createTestCegNode();
		String cegNodeId2 = cegNode2.getString(ID_KEY);
		logService.log(LogService.LOG_DEBUG, "Posting the object " + cegNode2.toString() + " to url " + nodePostUrl);
		result = restClient.post(nodePostUrl, cegNode2);
		Assert.assertEquals(Status.OK.getStatusCode(), result.getResponse().getStatus());

		String cegNode2retrieveUrl = detailUrl(requirementId, cegId, cegNodeId2);
		result = restClient.get(cegNode2retrieveUrl);
		JSONObject retrievedCegNode2 = result.getPayload();
		logService.log(LogService.LOG_DEBUG,
				"Retrieved the object " + retrievedCegNode2.toString() + " from url " + cegNode2retrieveUrl);
		Assert.assertTrue(EmfRestTestUtil.compare(cegNode2, retrievedCegNode2, true));

		// post connection
		JSONObject connection = createTestConnection(retrievedCegNode1, retrievedCegNode2);
		String connectionId = connection.getString(ID_KEY);
		logService.log(LogService.LOG_DEBUG, "Posting the object " + cegNode2.toString() + " to url " + nodePostUrl);
		result = restClient.post(nodePostUrl, connection);
		Assert.assertEquals(Status.OK.getStatusCode(), result.getResponse().getStatus());

		// Post test specification
		String postUrl3 = listUrl(requirementId, cegId);
		JSONObject testSpecification = createTestTestSpecification();
		String testSpecificationId = testSpecification.getString(ID_KEY);
		logService.log(LogService.LOG_DEBUG,
				"Posting the object " + testSpecification.toString() + " to url " + postUrl3);
		RestResult<JSONObject> result3 = restClient.post(postUrl3, testSpecification);
		Assert.assertEquals(Status.OK.getStatusCode(), result3.getResponse().getStatus());

		// Generate test cases
		String generateUrl = buildUrl("generateTests", requirementId, cegId, testSpecificationId);
		logService.log(LogService.LOG_DEBUG, "Request test genreation at  url " + generateUrl);
		RestResult<JSONObject> result4 = restClient.post(generateUrl, null);
		Assert.assertEquals(Status.NO_CONTENT.getStatusCode(), result4.getResponse().getStatus());

		String retrieveUrl = listUrl(requirementId, cegId, testSpecificationId);
		RestResult<JSONArray> getResult = restClient.getList(retrieveUrl);
		JSONArray retrievedTestChilds = getResult.getPayload();
		logService.log(LogService.LOG_DEBUG,
				"Retrieved the object " + retrievedTestChilds.toString() + " from url " + retrieveUrl);
		Assert.assertEquals(3, retrievedTestChilds.length());

	}
}
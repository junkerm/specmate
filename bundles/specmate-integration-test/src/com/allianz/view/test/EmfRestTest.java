package com.allianz.view.test;

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
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.log.LogService;
import org.osgi.util.tracker.ServiceTracker;

import com.specmate.common.SpecmateException;
import com.specmate.model.base.BasePackage;
import com.specmate.model.requirements.RequirementsPackage;
import com.specmate.persistency.IPersistencyService;
import com.specmate.persistency.ITransaction;
import com.specmate.persistency.IView;

public class EmfRestTest {

	private static final String NAME_KEY = "name";
	private static final String NSURI_KEY = "___nsuri";
	private static final String ECLASS = "___eclass";
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
		Configuration config = configAdmin
				.getConfiguration("com.specmate.persistency.cdo.internal.CDOPersistencyService");
		Dictionary<String, Object> properties = new Hashtable<>();
		properties.put("repositoryName", "testRepo");
		properties.put("resourceName", "restResource");
		config.update(properties);
		ServiceTracker<IPersistencyService, IPersistencyService> persistencyTracker = new ServiceTracker<>(context,
				IPersistencyService.class.getName(), null);
		persistencyTracker.open();
		IPersistencyService persistency = persistencyTracker.waitForService(100000);
		Assert.assertNotNull(persistency);
		return persistency;
	}

	private JSONObject createTestFolder() {
		String folderName = "Test Folder" + counter++;
		JSONObject folder = new JSONObject();
		folder.put(NSURI_KEY, BasePackage.eNS_URI);
		folder.put(ECLASS, BasePackage.Literals.FOLDER.getName());
		folder.put(BasePackage.Literals.INAMED__NAME.getName(), folderName);
		return folder;
	}

	private JSONObject createTestRequirement() {
		String folderName = "Test Requirement" + counter++;
		JSONObject requirement = new JSONObject();
		requirement.put(NSURI_KEY, RequirementsPackage.eNS_URI);
		requirement.put(ECLASS, RequirementsPackage.Literals.REQUIREMENT.getName());
		requirement.put(BasePackage.Literals.INAMED__NAME.getName(), folderName);
		requirement.put(BasePackage.Literals.IID__ID.getName(), "123");
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

	/**
	 * Tests posting a folder to the root. Checks, if the return code of the
	 * post request is OK and if retrieving the object again returns the
	 * original object.
	 */
	@Test
	public void testPostFolderToRootAndRetrieve() {
		String postUrl = "/list";
		JSONObject folder = createTestFolder();
		logService.log(LogService.LOG_DEBUG, "Posting the object " + folder.toString() + " to url " + postUrl);
		RestResult<JSONObject> result = restClient.post(postUrl, folder);
		Assert.assertEquals(result.getResponse().getStatus(), Status.OK.getStatusCode());

		String retrieveUrl = "/" + folder.getString(NAME_KEY) + "/details";
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
		String postUrl = "/list";
		JSONObject folder = createTestFolder();
		String folderName = folder.getString(NAME_KEY);

		logService.log(LogService.LOG_DEBUG, "Posting the object " + folder.toString() + " to url " + postUrl);
		RestResult<JSONObject> result = restClient.post(postUrl, folder);
		Assert.assertEquals(result.getResponse().getStatus(), Status.OK.getStatusCode());

		String postUrl2 = "/" + folderName + "/list";
		JSONObject folder2 = createTestFolder();
		String folderName2 = folder2.getString(NAME_KEY);
		logService.log(LogService.LOG_DEBUG, "Posting the object " + folder2.toString() + " to url " + postUrl2);
		RestResult<JSONObject> result2 = restClient.post(postUrl2, folder2);
		Assert.assertEquals(result2.getResponse().getStatus(), Status.OK.getStatusCode());

		String retrieveUrl = "/" + folderName + "/" + folderName2 + "/details";
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
		String folderName = folder.getString(NAME_KEY);

		// Not posting to backend instead try to retrieve
		String retrieveUrl = "/" + folderName + "/details";
		RestResult<JSONObject> getResult = restClient.get(retrieveUrl);

		Assert.assertEquals(Status.NOT_FOUND.getStatusCode(), getResult.getResponse().getStatus());

	}

	/** Tests retrieving a list of child folders from a folder */
	@Test
	public void testRetrieveChildrenList() {
		int numberOfChildren = 2;

		String postUrl = "/list";
		JSONObject folder = createTestFolder();
		String folderName = folder.getString(NAME_KEY);
		logService.log(LogService.LOG_DEBUG, "Posting the object " + folder.toString() + " to url " + postUrl);
		RestResult<JSONObject> result = restClient.post(postUrl, folder);
		Assert.assertEquals(result.getResponse().getStatus(), Status.OK.getStatusCode());

		String postUrl2 = "/" + folderName + "/list";
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
		String postUrl = "/list";
		JSONObject folder = createTestFolder();
		logService.log(LogService.LOG_DEBUG, "Posting the object " + folder.toString() + " to url " + postUrl);
		RestResult<JSONObject> result = restClient.post(postUrl, folder);
		Assert.assertEquals(Status.OK.getStatusCode(), result.getResponse().getStatus());

		// Check if folder exists
		String retrieveUrl = "/" + folder.getString(NAME_KEY) + "/details";
		RestResult<JSONObject> getResult = restClient.get(retrieveUrl);
		JSONObject retrievedFolder = getResult.getPayload();
		logService.log(LogService.LOG_DEBUG,
				"Retrieved the object " + retrievedFolder.toString() + " from url " + retrieveUrl);
		Assert.assertEquals(Status.OK.getStatusCode(), getResult.getResponse().getStatus());

		// Delete folder
		String deleteUrl = "/" + folder.getString(NAME_KEY) + "/delete";
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
		String postUrl = "/list";
		JSONObject folder = createTestFolder();
		String folderName = folder.getString(NAME_KEY);
		logService.log(LogService.LOG_DEBUG, "Posting the object " + folder.toString() + " to url " + postUrl);
		RestResult<JSONObject> result = restClient.post(postUrl, folder);
		Assert.assertEquals(Status.OK.getStatusCode(), result.getResponse().getStatus());

		// Post folder in new folder
		String postUrl2 = "/" + folderName + "/list";
		JSONObject folder2 = createTestFolder();
		logService.log(LogService.LOG_DEBUG, "Posting the object " + folder2.toString() + " to url " + postUrl2);
		RestResult<JSONObject> result2 = restClient.post(postUrl2, folder2);
		Assert.assertEquals(result2.getResponse().getStatus(), Status.OK.getStatusCode());

		// Check if top level folder exists
		String retrieveUrl = "/" + folder.getString(NAME_KEY) + "/details";
		RestResult<JSONObject> getResult = restClient.get(retrieveUrl);
		JSONObject retrievedFolder = getResult.getPayload();
		logService.log(LogService.LOG_DEBUG,
				"Retrieved the object " + retrievedFolder.toString() + " from url " + retrieveUrl);
		Assert.assertEquals(Status.OK.getStatusCode(), getResult.getResponse().getStatus());

		// Delete top level folder
		String deleteUrl = "/" + folder.getString(NAME_KEY) + "/delete";
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
		String postUrl = "/list";
		JSONObject folder = createTestFolder();
		String folderName = folder.getString(NAME_KEY);

		logService.log(LogService.LOG_DEBUG, "Posting the object " + folder.toString() + " to url " + postUrl);
		RestResult<JSONObject> result = restClient.post(postUrl, folder);
		Assert.assertEquals(result.getResponse().getStatus(), Status.OK.getStatusCode());

		String postUrl2 = "/" + folderName + "/list";
		JSONObject requirement = createTestRequirement();
		String requirementName = requirement.getString(NAME_KEY);
		logService.log(LogService.LOG_DEBUG, "Posting the object " + requirement.toString() + " to url " + postUrl2);
		RestResult<JSONObject> result2 = restClient.post(postUrl2, requirement);
		Assert.assertEquals(Status.OK.getStatusCode(), result2.getResponse().getStatus());

		String retrieveUrl = "/" + folderName + "/" + requirementName + "/details";
		RestResult<JSONObject> getResult = restClient.get(retrieveUrl);
		JSONObject retrievedFolder = getResult.getPayload();
		logService.log(LogService.LOG_DEBUG,
				"Retrieved the object " + retrievedFolder.toString() + " from url " + retrieveUrl);
		Assert.assertTrue(EmfRestTestUtil.compare(retrievedFolder, requirement, true));
	}

}
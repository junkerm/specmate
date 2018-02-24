package com.specmate.test.integration;

import javax.ws.rs.core.Response.Status;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.osgi.util.tracker.ServiceTracker;

import com.specmate.common.RestResult;
import com.specmate.common.SpecmateException;
import com.specmate.model.base.BasePackage;
import com.specmate.search.api.IModelSearchService;

public class SearchTest extends EmfRestTest {
	private static IModelSearchService searchService;
	
	public SearchTest() throws Exception {}
	
	@BeforeClass
	public static void init() throws Exception {
		searchService = getSearchService();
	}
	
	private static IModelSearchService getSearchService() throws InterruptedException {
		ServiceTracker<IModelSearchService, IModelSearchService> searchServiceTracker = new ServiceTracker<>(context,
				IModelSearchService.class.getName(), null);
		searchServiceTracker.open();
		IModelSearchService searchService = searchServiceTracker.waitForService(100000);
		Assert.assertNotNull(searchService);
		return searchService;
	}
	
	private JSONArray performSearch(String query) {
		String searchUrl = buildUrl("search");
		RestResult<JSONArray> result = restClient.getList(searchUrl, "query", query);
		Assert.assertEquals(Status.OK.getStatusCode(), result.getResponse().getStatus());
		JSONArray foundObjects = result.getPayload();
		return foundObjects;
	}
	
	@Before
	public void clear() throws SpecmateException {
		searchService.clear();
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
		Thread.sleep(35000);

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

		// spurios "minus"
		foundObjects = performSearch("bla -");
		Assert.assertEquals(0, foundObjects.length());

	}
}

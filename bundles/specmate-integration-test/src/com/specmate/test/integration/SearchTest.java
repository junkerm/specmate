package com.specmate.test.integration;

import static com.specmate.test.integration.EmfRestTestUtil.matches;

import java.util.Dictionary;
import java.util.Hashtable;

import javax.ws.rs.core.Response.Status;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.osgi.util.tracker.ServiceTracker;

import com.specmate.common.OSGiUtil;
import com.specmate.common.exception.SpecmateException;
import com.specmate.model.base.BasePackage;
import com.specmate.rest.RestResult;
import com.specmate.search.api.IModelSearchService;
import com.specmate.search.config.LuceneBasedSearchServiceConfig;

public class SearchTest extends EmfRestTest {
	private static IModelSearchService searchService;

	public SearchTest() throws Exception {
		super();
		if (searchService == null) {
			configureSearch();
		}
		searchService = getSearchService();
	}

	private void configureSearch() throws SpecmateException, InterruptedException {
		OSGiUtil.configureService(configAdmin, LuceneBasedSearchServiceConfig.PID, getSearchServiceProperties());

		// allow for the search service to start
		Thread.sleep(2000);
	}

	protected Dictionary<String, Object> getSearchServiceProperties() {
		Dictionary<String, Object> properties = new Hashtable<>();
		properties.put(LuceneBasedSearchServiceConfig.KEY_ALLOWED_FIELDS, "extId, type, name, description");
		properties.put(LuceneBasedSearchServiceConfig.KEY_LUCENE_DB_LOCATION, "./database/lucene");
		properties.put(LuceneBasedSearchServiceConfig.KEY_MAX_SEARCH_RESULTS, 500);
		return properties;
	}

	private IModelSearchService getSearchService() throws InterruptedException {
		ServiceTracker<IModelSearchService, IModelSearchService> searchServiceTracker = new ServiceTracker<>(context,
				IModelSearchService.class.getName(), null);
		searchServiceTracker.open();
		IModelSearchService searchService = searchServiceTracker.waitForService(10000);
		Assert.assertNotNull(searchService);
		return searchService;
	}

	private JSONArray performSearch(String query) {
		String searchUrl = buildProjectUrl("search");
		RestResult<JSONArray> result = restClient.getList(searchUrl, "query", query);
		Assert.assertEquals(Status.OK.getStatusCode(), result.getResponse().getStatus());
		result.getResponse().close();
		JSONArray foundObjects = result.getPayload();
		return foundObjects;
	}

	private void performReindex() {
		String reindexUrl = buildRootUrl("reindex");
		RestResult<JSONObject> result = restClient.get(reindexUrl);
		Assert.assertEquals(Status.NO_CONTENT.getStatusCode(), result.getResponse().getStatus());
		result.getResponse().close();
	}

	private JSONArray queryRelatedRequirements(String... segments) {
		String relatedUrl = buildUrl("related", segments);
		RestResult<JSONArray> result = restClient.getList(relatedUrl);
		Assert.assertEquals(Status.OK.getStatusCode(), result.getResponse().getStatus());
		JSONArray foundObjects = result.getPayload();
		result.getResponse().close();
		return foundObjects;
	}

	@Before
	public void clear() throws SpecmateException {
		searchService.clear();
	}

	/**
	 * Posts two test specifications to a CEG model and checks if they are retrieved
	 * by the list recursive service.
	 *
	 * @throws InterruptedException
	 */
	@Test
	public void testSearch() throws InterruptedException {
		JSONObject folder = createTestFolder();
		folder.put(BasePackage.Literals.INAMED__NAME.getName(), "Test");
		folder.put(BasePackage.Literals.IDESCRIBED__DESCRIPTION.getName(), "TEST");
		postObject(folder);

		JSONObject requirement = createTestRequirement();
		requirement.put(BasePackage.Literals.INAMED__NAME.getName(), "Test BLA BLI");
		requirement.put(BasePackage.Literals.IDESCRIBED__DESCRIPTION.getName(), "TEST BLUP");
		postObject(requirement);
		String requirementId = getId(requirement);

		JSONObject requirement2 = createTestRequirement();
		requirement2.put(BasePackage.Literals.INAMED__NAME.getName(), "Test");
		requirement2.put(BasePackage.Literals.IDESCRIBED__DESCRIPTION.getName(), "TEST BLI");
		requirement2.put(BasePackage.Literals.IEXTERNAL__EXT_ID.getName(), "4711");
		postObject(requirement2);

		JSONObject requirement3 = createTestRequirement();
		requirement3.put(BasePackage.Literals.INAMED__NAME.getName(), "Tree");
		requirement3.put(BasePackage.Literals.IDESCRIBED__DESCRIPTION.getName(), "Tree");
		postObject(requirement3);

		JSONObject cegModel = createTestCegModel();
		cegModel.put(BasePackage.Literals.INAMED__NAME.getName(), "Test CEG");
		cegModel.put(BasePackage.Literals.IDESCRIBED__DESCRIPTION.getName(), "CEG");
		postObject(cegModel, requirementId);

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

		// spurios "minus"
		foundObjects = performSearch("bla -");
		Assert.assertEquals(0, foundObjects.length());

		deleteObject(requirementId);
		foundObjects = performSearch("BLUP");
		Assert.assertEquals(0, foundObjects.length());

	}

	@Test
	public void testSearchScopedOnProject() throws InterruptedException {
		JSONObject requirement1 = createTestRequirement();
		requirement1.put(BasePackage.Literals.INAMED__NAME.getName(), "blup");
		requirement1.put(BasePackage.Literals.IDESCRIBED__DESCRIPTION.getName(), "blup");
		postObject(requirement1);
		String requirementId1 = getId(requirement1);

		nextProject();

		JSONObject requirement2 = createTestRequirement();
		requirement2.put(BasePackage.Literals.INAMED__NAME.getName(), "blup");
		requirement2.put(BasePackage.Literals.IDESCRIBED__DESCRIPTION.getName(), "blup");
		postObject(requirement2);
		String requirementId2 = getId(requirement2);

		// Allow time to commit to search index
		Thread.sleep(35000);

		resetSelectedProject();

		JSONArray foundObjects = performSearch("blup");
		Assert.assertEquals(1, foundObjects.length());
		Assert.assertEquals(requirementId1, getId(foundObjects.getJSONObject(0)));

		nextProject();

		JSONArray foundObjects2 = performSearch("blup");
		Assert.assertEquals(1, foundObjects2.length());
		Assert.assertEquals(requirementId2, getId(foundObjects2.getJSONObject(0)));
	}

	@Test
	public void testReIndexing() throws InterruptedException {
		this.getSearchService().disableIndexing();

		JSONObject folder = createTestFolder();
		folder.put(BasePackage.Literals.INAMED__NAME.getName(), "Test");
		folder.put(BasePackage.Literals.IDESCRIBED__DESCRIPTION.getName(), "TEST");
		postObject(folder);

		JSONObject requirement = createTestRequirement();
		requirement.put(BasePackage.Literals.INAMED__NAME.getName(), "Test BLA BLI");
		requirement.put(BasePackage.Literals.IDESCRIBED__DESCRIPTION.getName(), "TEST BLUP");
		postObject(requirement);

		// Check if search finds nothing as indexing was disabled
		JSONArray foundObjects = performSearch("blup");
		Assert.assertEquals(0, foundObjects.length());

		getSearchService().enableIndexing();
		performReindex();
		Thread.sleep(35000);

		// Check if search finds something, hence reindexing worked
		foundObjects = performSearch("blup");
		Assert.assertEquals(1, foundObjects.length());

	}

	@Test
	public void testRelatedRequirements() {
		JSONObject requirement = postRequirement();
		String requirementId = getId(requirement);

		JSONObject requirement2 = postRequirement();
		String requirement2Id = getId(requirement2);
		JSONObject retrievedRequirement2 = getObject(requirement2Id);

		JSONObject requirement3 = postRequirement();
		String requirement3Id = getId(requirement3);
		JSONObject retrievedRequirement3 = getObject(requirement3Id);

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

		// add traces
		setStepTrace(retrievedStepNode1, retrievedRequirement2, retrievedRequirement3);
		updateObject(retrievedStepNode1, requirementId, processId, stepNode1Id);

		// post end node
		JSONObject endNode = postEndNode(requirementId, processId);
		String endNodeId = getId(endNode);

		JSONObject retrievedEndNode = getObject(requirementId, processId, endNodeId);
		Assert.assertTrue(EmfRestTestUtil.compare(endNode, retrievedEndNode, true));

		// post connection 1
		postStepConnection(retrievedStartNode, retrievedStepNode1, requirementId, processId);

		// post connection 1
		postStepConnection(retrievedStepNode1, retrievedEndNode, requirementId, processId);

		// check related requirements
		JSONArray related1 = queryRelatedRequirements(requirement2Id);
		Assert.assertEquals(2, related1.length());
		Assert.assertTrue(matches(related1,
				jsonObject -> jsonObject.get(BasePackage.Literals.IID__ID.getName()).equals(requirementId)));
		Assert.assertTrue(matches(related1,
				jsonObject -> jsonObject.get(BasePackage.Literals.IID__ID.getName()).equals(requirement3Id)));

		JSONArray related2 = queryRelatedRequirements(requirementId);
		Assert.assertEquals(2, related2.length());
		Assert.assertTrue(matches(related2,
				jsonObject -> jsonObject.get(BasePackage.Literals.IID__ID.getName()).equals(requirement2Id)));
		Assert.assertTrue(matches(related2,
				jsonObject -> jsonObject.get(BasePackage.Literals.IID__ID.getName()).equals(requirement3Id)));

	}
}

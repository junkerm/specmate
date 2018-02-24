package com.specmate.test.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.specmate.common.RestResult;
import com.specmate.common.SpecmateException;
import com.specmate.model.base.BasePackage;
import com.specmate.model.history.HistoryPackage;

public class HistoryTest extends EmfRestTest {

	public HistoryTest() throws Exception {}
	
	private String historyUrl(String... segments) {
		return buildUrl("history", segments);
	}
	
	private String historyRecursiveUrl(String... segments) {
		return buildUrl("historyRecursive", segments);
	}
	
	private JSONArray getEntries(boolean recursive, String... segments) {
		String historyUrl = recursive ? historyRecursiveUrl(segments) : historyUrl(segments);
		RestResult<JSONObject> result = restClient.get(historyUrl);
		JSONObject history = result.getPayload();
		return history.getJSONArray(HistoryPackage.Literals.HISTORY__ENTRIES.getName());
	}
	
	@Before
	public void clear() throws SpecmateException {
		clearPersistency();
	}
	
	/**
	 * Tests that the history entries are returned is reversed creation order 
	 * and expected state (creation, modification).
	 */
	@Test
	public void testHistoryIsInSequence() {
		JSONObject requirement = postRequirementToRoot();
		String requirementId = getId(requirement);
		String newName = "changedName";
		int numChangeNames = 11;
		for(int i = 0; i < numChangeNames; i++) {
			requirement.put(BasePackage.Literals.INAMED__NAME.getName(), newName + i);
			updateObject(requirement, requirementId);
		}
		
		checkSequence(getEntries(false, requirementId), newName, numChangeNames);
		checkSequence(getEntries(true, requirementId), newName, numChangeNames);
	}
	
	private void checkSequence(JSONArray entries, String newName, int numChangeNames) {
		assertEquals(numChangeNames + 1, entries.length());
		for(int i = 0, j = numChangeNames - 1; i < numChangeNames; i++, j--) {
			JSONObject entry = entries.getJSONObject(i);
			assertTrue(entry.getString(HistoryPackage.Literals.HISTORY_ENTRY__USER.getName()).length() > 0);
			JSONArray changes = entry.getJSONArray(HistoryPackage.Literals.HISTORY_ENTRY__CHANGES.getName());
			assertEquals(1, changes.length());
			JSONObject change = changes.getJSONObject(0);
			assertEquals(newName + j, change.getString(HistoryPackage.Literals.CHANGE__NEW_VALUE.getName()));
			assertEquals(BasePackage.Literals.INAMED__NAME.getName(), change.getString(HistoryPackage.Literals.CHANGE__FEATURE.getName()));
			assertFalse(change.getBoolean(HistoryPackage.Literals.CHANGE__IS_CREATE.getName()));
			assertFalse(change.getBoolean(HistoryPackage.Literals.CHANGE__IS_DELETE.getName()));
		}
		
		JSONObject creation = entries.getJSONObject(numChangeNames);
		assertTrue(creation.getString(HistoryPackage.Literals.HISTORY_ENTRY__USER.getName()).length() > 0); 
		JSONArray changes = creation.getJSONArray(HistoryPackage.Literals.HISTORY_ENTRY__CHANGES.getName());
		for(int i = 0; i < changes.length(); i++) {
			JSONObject change = changes.getJSONObject(i);
			assertTrue(change.getBoolean(HistoryPackage.Literals.CHANGE__IS_CREATE.getName()));
			assertFalse(change.getBoolean(HistoryPackage.Literals.CHANGE__IS_DELETE.getName()));
		}
	}
	
	/**
	 * Tests that the object hierarchy is traversed to get history elements by
	 * asserting the number of history entries for created and deleted objects. 
	 */
	@Test
	public void testRecursiveHistory() {
		// Change 1
		JSONObject requirement = postRequirementToRoot();
		String requirementId = getId(requirement);

		// Change 2
		JSONObject cegModel = postCEG(requirementId);
		String cegId = getId(cegModel);

		// Change 3
		JSONObject cegNode1 = postCEGNode(requirementId, cegId);
		String node1Id = getId(cegNode1);

		JSONObject retrievedCegNode1 = getObject(requirementId, cegId, node1Id);
		Assert.assertTrue(EmfRestTestUtil.compare(cegNode1, retrievedCegNode1, true));

		// Change 4
		JSONObject cegNode2 = postCEGNode(requirementId, cegId);
		String node2Id = getId(cegNode2);

		JSONObject retrievedCegNode2 = getObject(requirementId, cegId, node2Id);
		Assert.assertTrue(EmfRestTestUtil.compare(cegNode2, retrievedCegNode2, true));

		// Change 5, 6, 7
		JSONObject connection = postCEGConnection(retrievedCegNode1, retrievedCegNode2, requirementId, cegId);
		String connectionId = getId(connection);
		
		JSONArray entries = getEntries(true, requirementId);
		assertEquals(7, entries.length());
		
		// Change 8, 9, 10
		deleteObject(requirementId, cegId, connectionId);
		
		/**
		 * And this assertion fails because deleting a connection between two nodes removes the
		 * connection (feature: id) from the history completely, both creation and deletion.
		 * Strangely, the elements with feature "incomingConnections" and "outgoingConnections" are maintained
		 * in the history (both creation and deletion).
		 * Not sure what is going on here...
		 */
		entries = getEntries(true, requirementId);
		assertEquals(10, entries.length());
		JSONObject entry = entries.getJSONObject(0);
		JSONArray changes = entry.getJSONArray(HistoryPackage.Literals.HISTORY_ENTRY__CHANGES.getName());
		assertEquals(1, changes.length());
		JSONObject deletion = changes.getJSONObject(0);
		assertTrue(deletion.getBoolean(HistoryPackage.Literals.CHANGE__IS_DELETE.getName()));
	}
	
	/**
	 * Tests that the history of subtrees returns the correct number of elements 
	 * for created and modified objects. 
	 */
	@Test
	public void testHistorySubset() {
		// Change 1
		JSONObject requirement = postRequirementToRoot();
		String requirementId = getId(requirement);

		// Change 2
		JSONObject cegModel = postCEG(requirementId);
		String cegId = getId(cegModel);

		// Change 3
		JSONObject cegNode1 = postCEGNode(requirementId, cegId);
		String node1Id = getId(cegNode1);

		JSONObject retrievedCegNode1 = getObject(requirementId, cegId, node1Id);
		Assert.assertTrue(EmfRestTestUtil.compare(cegNode1, retrievedCegNode1, true));

		// Change 4
		JSONObject cegNode2 = postCEGNode(requirementId, cegId);
		String node2Id = getId(cegNode2);

		JSONObject retrievedCegNode2 = getObject(requirementId, cegId, node2Id);
		Assert.assertTrue(EmfRestTestUtil.compare(cegNode2, retrievedCegNode2, true));

		// Change 5, 6, 7
		postCEGConnection(retrievedCegNode1, retrievedCegNode2, requirementId, cegId);
		
		JSONArray entries = getEntries(true, requirementId);
		assertEquals(7, entries.length());
		entries = getEntries(false, requirementId);
		assertEquals(1, entries.length());
		entries = getEntries(true, requirementId, cegId);
		assertEquals(6, entries.length());
		entries = getEntries(false, requirementId, cegId);
		assertEquals(1, entries.length());
		
		cegModel.put(BasePackage.Literals.INAMED__NAME.getName(), "newName");
		updateObject(cegModel, requirementId, cegId);
		entries = getEntries(false, requirementId, cegId);
		assertEquals(2, entries.length());
	}
	
}

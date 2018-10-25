package com.specmate.test.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import com.specmate.emfrest.history.HistoryRestService;
import com.specmate.model.base.BasePackage;
import com.specmate.model.history.HistoryPackage;
import com.specmate.rest.RestResult;

public class HistoryTest extends EmfRestTest {

	public HistoryTest() throws Exception {
	}

	private JSONArray getEntries(String type, String... segments) {
		String historyUrl = buildUrl("history", segments);
		RestResult<JSONObject> result = restClient.get(historyUrl, "type", type);
		JSONObject history = result.getPayload();
		result.getResponse().close();
		return history.getJSONArray(HistoryPackage.Literals.HISTORY__ENTRIES.getName());
	}

	/**
	 * Tests that the history entries are returned is reversed creation order and
	 * expected state (creation, modification).
	 */
	@Test
	public void testHistoryIsInSequence() {
		JSONObject requirement = postRequirementToRoot();
		String requirementId = getId(requirement);
		String newName = "changedName";
		int numChangeNames = 11;
		for (int i = 0; i < numChangeNames; i++) {
			requirement.put(BasePackage.Literals.INAMED__NAME.getName(), newName + i);
			updateObject(requirement, requirementId);
		}

		checkSequence(getEntries(HistoryRestService.HSINGLE, requirementId), newName, numChangeNames);
		checkSequence(getEntries(HistoryRestService.HRECURSIVE, requirementId), newName, numChangeNames);
	}

	private void checkSequence(JSONArray entries, String newName, int numChangeNames) {
		assertEquals(numChangeNames + 1, entries.length());
		for (int i = 0, j = numChangeNames - 1; i < numChangeNames; i++, j--) {
			JSONObject entry = entries.getJSONObject(i);
			assertTrue(entry.getString(HistoryPackage.Literals.HISTORY_ENTRY__USER.getName()).length() > 0);
			JSONArray changes = entry.getJSONArray(HistoryPackage.Literals.HISTORY_ENTRY__CHANGES.getName());
			assertEquals(1, changes.length());
			JSONObject change = changes.getJSONObject(0);
			assertEquals(newName + j, change.getString(HistoryPackage.Literals.CHANGE__NEW_VALUE.getName()));
			assertEquals(BasePackage.Literals.INAMED__NAME.getName(),
					change.getString(HistoryPackage.Literals.CHANGE__FEATURE.getName()));
			assertFalse(change.getBoolean(HistoryPackage.Literals.CHANGE__IS_CREATE.getName()));
			assertFalse(change.getBoolean(HistoryPackage.Literals.CHANGE__IS_DELETE.getName()));
		}

		JSONObject creation = entries.getJSONObject(numChangeNames);
		assertTrue(creation.getString(HistoryPackage.Literals.HISTORY_ENTRY__USER.getName()).length() > 0);
		JSONArray changes = creation.getJSONArray(HistoryPackage.Literals.HISTORY_ENTRY__CHANGES.getName());
		for (int i = 0; i < changes.length(); i++) {
			JSONObject change = changes.getJSONObject(i);
			assertTrue(change.getBoolean(HistoryPackage.Literals.CHANGE__IS_CREATE.getName()));
			assertFalse(change.getBoolean(HistoryPackage.Literals.CHANGE__IS_DELETE.getName()));
		}
	}

	/**
	 * Tests that the object hierarchy is traversed to get history elements by
	 * asserting the number of history entries for created and deleted objects.
	 *
	 * IGNORING test for now as design of history content has changed and may change
	 * in the near future.
	 */
	/*
	 * @Test public void testRecursiveHistory() { // Changes 1 JSONObject
	 * requirement = postRequirementToRoot(); String requirementId =
	 * getId(requirement);
	 * 
	 * // Changes 3 JSONObject cegModel = postCEG(requirementId); String cegId =
	 * getId(cegModel);
	 * 
	 * // Changes 5 JSONObject cegNode1 = postCEGNode(requirementId, cegId); String
	 * node1Id = getId(cegNode1);
	 * 
	 * JSONObject retrievedCegNode1 = getObject(requirementId, cegId, node1Id);
	 * Assert.assertTrue(EmfRestTestUtil.compare(cegNode1, retrievedCegNode1,
	 * true));
	 * 
	 * // Changes 7 JSONObject cegNode2 = postCEGNode(requirementId, cegId); String
	 * node2Id = getId(cegNode2);
	 * 
	 * JSONObject retrievedCegNode2 = getObject(requirementId, cegId, node2Id);
	 * Assert.assertTrue(EmfRestTestUtil.compare(cegNode2, retrievedCegNode2,
	 * true));
	 * 
	 * // Change 9 JSONObject connection = postCEGConnection(retrievedCegNode1,
	 * retrievedCegNode2, false, requirementId, cegId); String connectionId =
	 * getId(connection);
	 * 
	 * JSONArray entries = getEntries(HistoryRestService.HRECURSIVE, requirementId);
	 * assertEquals(9, entries.length());
	 * 
	 * // Change 9: since we deleted an object, is does not appear in history
	 * anymore // (-1), but we catch the deletion with // the change event on the
	 * containment (+1). As a result, the number of history // entries does not
	 * change when deleting // an object. deleteObject(requirementId, cegId,
	 * connectionId);
	 * 
	 * entries = getEntries(HistoryRestService.HRECURSIVE, requirementId);
	 * assertEquals(9, entries.length()); JSONObject entry =
	 * entries.getJSONObject(0); JSONArray changes =
	 * entry.getJSONArray(HistoryPackage.Literals.HISTORY_ENTRY__CHANGES.getName());
	 * assertEquals(1, changes.length()); JSONObject deletion =
	 * changes.getJSONObject(0);
	 * assertTrue(deletion.getBoolean(HistoryPackage.Literals.CHANGE__IS_DELETE.
	 * getName())); }
	 */
}

package com.specmate.test.integration;

import javax.ws.rs.core.Response.Status;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import com.specmate.model.base.BasePackage;
import com.specmate.model.requirements.CEGConnection;
import com.specmate.model.requirements.CEGNode;
import com.specmate.model.requirements.RequirementsPackage;
import com.specmate.rest.RestResult;

public class ModelGenerationTest extends EmfRestTest {

	public ModelGenerationTest() throws Exception {
		super();
	}

	@Test
	public void testModelGeneration() {
		String text = "If Specmate detects an error or the user has no login, Specmate shows a warning window and makes a sound.";
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated);
	}

	private void checkResultingModel(JSONArray generated) {
		Assert.assertTrue(EmfRestTestUtil.matches(generated, obj -> {
			return obj.getString(ECLASS).equals(CEGNode.class.getSimpleName())
					&& obj.getString(RequirementsPackage.Literals.CEG_NODE__VARIABLE.getName()).equals("Specmate")
					&& obj.getString(RequirementsPackage.Literals.CEG_NODE__CONDITION.getName())
							.equals("detects an error")
					&& obj.getString(URL).equals(
							"iproject0/itopfolder0/req_generateCEGWithModelRequirementsText/ceg_generateCEGWithModelRequirementsText/CEGNode-2");
		}));

		Assert.assertTrue(EmfRestTestUtil.matches(generated, obj -> {
			return obj.getString(ECLASS).equals(CEGNode.class.getSimpleName())
					&& obj.getString(RequirementsPackage.Literals.CEG_NODE__VARIABLE.getName()).equals("the user")
					&& obj.getString(RequirementsPackage.Literals.CEG_NODE__CONDITION.getName()).equals("has  login")
					&& obj.getString(URL).equals(
							"iproject0/itopfolder0/req_generateCEGWithModelRequirementsText/ceg_generateCEGWithModelRequirementsText/CEGNode-3");
		}));

		Assert.assertTrue(EmfRestTestUtil.matches(generated, obj -> {
			return obj.getString(ECLASS).equals(CEGNode.class.getSimpleName())
					&& obj.getString(RequirementsPackage.Literals.CEG_NODE__VARIABLE.getName()).equals("Specmate")
					&& obj.getString(RequirementsPackage.Literals.CEG_NODE__CONDITION.getName())
							.equals("shows a warning window")
					&& obj.getString(RequirementsPackage.Literals.CEG_NODE__TYPE.getName()).equals("OR")
					&& obj.getString(URL).equals(
							"iproject0/itopfolder0/req_generateCEGWithModelRequirementsText/ceg_generateCEGWithModelRequirementsText/CEGNode-1");
		}));

		Assert.assertTrue(EmfRestTestUtil.matches(generated, obj -> {
			return obj.getString(ECLASS).equals(CEGNode.class.getSimpleName())
					&& obj.getString(RequirementsPackage.Literals.CEG_NODE__VARIABLE.getName()).equals("Specmate")
					&& obj.getString(RequirementsPackage.Literals.CEG_NODE__CONDITION.getName()).equals("makes a sound")
					&& obj.getString(RequirementsPackage.Literals.CEG_NODE__TYPE.getName()).equals("OR")
					&& obj.getString(URL).equals(
							"iproject0/itopfolder0/req_generateCEGWithModelRequirementsText/ceg_generateCEGWithModelRequirementsText/CEGNode-4");
		}));

		Assert.assertTrue(EmfRestTestUtil.matches(generated, obj -> {
			return obj.getString(ECLASS).equals(CEGConnection.class.getSimpleName())
					&& !obj.getBoolean(RequirementsPackage.Literals.CEG_CONNECTION__NEGATE.getName())
					&& obj.getJSONObject(BasePackage.Literals.IMODEL_CONNECTION__SOURCE.getName()).getString(URL)
							.equals("iproject0/itopfolder0/req_generateCEGWithModelRequirementsText/ceg_generateCEGWithModelRequirementsText/CEGNode-2")
					&& obj.getJSONObject(BasePackage.Literals.IMODEL_CONNECTION__TARGET.getName()).getString(URL)
							.equals("iproject0/itopfolder0/req_generateCEGWithModelRequirementsText/ceg_generateCEGWithModelRequirementsText/CEGNode-1");
		}));

		Assert.assertTrue(EmfRestTestUtil.matches(generated, obj -> {
			return obj.getString(ECLASS).equals(CEGConnection.class.getSimpleName())
					&& !obj.getBoolean(RequirementsPackage.Literals.CEG_CONNECTION__NEGATE.getName())
					&& obj.getJSONObject(BasePackage.Literals.IMODEL_CONNECTION__SOURCE.getName()).getString(URL)
							.equals("iproject0/itopfolder0/req_generateCEGWithModelRequirementsText/ceg_generateCEGWithModelRequirementsText/CEGNode-2")
					&& obj.getJSONObject(BasePackage.Literals.IMODEL_CONNECTION__TARGET.getName()).getString(URL)
							.equals("iproject0/itopfolder0/req_generateCEGWithModelRequirementsText/ceg_generateCEGWithModelRequirementsText/CEGNode-4");
		}));

		Assert.assertTrue(EmfRestTestUtil.matches(generated, obj -> {
			return obj.getString(ECLASS).equals(CEGConnection.class.getSimpleName())
					&& obj.getBoolean(RequirementsPackage.Literals.CEG_CONNECTION__NEGATE.getName())
					&& obj.getJSONObject(BasePackage.Literals.IMODEL_CONNECTION__SOURCE.getName()).getString(URL)
							.equals("iproject0/itopfolder0/req_generateCEGWithModelRequirementsText/ceg_generateCEGWithModelRequirementsText/CEGNode-3")
					&& obj.getJSONObject(BasePackage.Literals.IMODEL_CONNECTION__TARGET.getName()).getString(URL)
							.equals("iproject0/itopfolder0/req_generateCEGWithModelRequirementsText/ceg_generateCEGWithModelRequirementsText/CEGNode-1");
		}));

		Assert.assertTrue(EmfRestTestUtil.matches(generated, obj -> {
			return obj.getString(ECLASS).equals(CEGConnection.class.getSimpleName())
					&& obj.getBoolean(RequirementsPackage.Literals.CEG_CONNECTION__NEGATE.getName())
					&& obj.getJSONObject(BasePackage.Literals.IMODEL_CONNECTION__SOURCE.getName()).getString(URL)
							.equals("iproject0/itopfolder0/req_generateCEGWithModelRequirementsText/ceg_generateCEGWithModelRequirementsText/CEGNode-3")
					&& obj.getJSONObject(BasePackage.Literals.IMODEL_CONNECTION__TARGET.getName()).getString(URL)
							.equals("iproject0/itopfolder0/req_generateCEGWithModelRequirementsText/ceg_generateCEGWithModelRequirementsText/CEGNode-4");
		}));
	}

	private JSONArray generateCEGWithModelRequirementsText(String text) {
		JSONObject requirement = createTestRequirement("req_generateCEGWithModelRequirementsText");
		postObject(requirement);
		String requirementId = getId(requirement);

		JSONObject cegModel = createTestCegModel("ceg_generateCEGWithModelRequirementsText");
		postObject(cegModel, requirementId);
		String cegId = getId(cegModel);
		JSONObject retrievedCEG = getObject(requirementId, cegId);
		Assert.assertTrue(EmfRestTestUtil.compare(retrievedCEG, cegModel, true));

		retrievedCEG.put(RequirementsPackage.Literals.CEG_MODEL__MODEL_REQUIREMENTS.getName(), text);
		updateObject(retrievedCEG, requirementId, cegId);

		String generateUrl = buildUrl("generateModel", requirementId, cegId);
		RestResult<JSONObject> result = restClient.post(generateUrl, null);
		Assert.assertEquals(Status.OK.getStatusCode(), result.getResponse().getStatus());

		return getContent(requirementId, cegId);
	}
}

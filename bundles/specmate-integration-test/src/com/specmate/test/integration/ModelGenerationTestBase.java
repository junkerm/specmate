package com.specmate.test.integration;

import static com.specmate.test.integration.EmfRestTestUtil.count;

import java.util.List;
import java.util.function.Predicate;

import javax.ws.rs.core.Response.Status;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import com.specmate.model.base.BasePackage;
import com.specmate.model.requirements.CEGConnection;
import com.specmate.model.requirements.CEGModel;
import com.specmate.model.requirements.CEGNode;
import com.specmate.model.requirements.NodeType;
import com.specmate.model.requirements.RequirementsFactory;
import com.specmate.model.requirements.RequirementsPackage;
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.rest.RestResult;

public class ModelGenerationTestBase extends EmfRestTest {

	private static int id = 0;

	private Predicate<JSONObject> IS_CEG_NODE = obj -> obj.getString(ECLASS).equals(CEGNode.class.getSimpleName());

	private Predicate<JSONObject> MATCHES_VAR_COND(String var, String cond, String type) {
		return (obj -> obj.getString(ECLASS).equals(CEGNode.class.getSimpleName())
				&& obj.getString(RequirementsPackage.Literals.CEG_NODE__TYPE.getName()).equals(type)
				&& checkStringEquality(obj.getString(RequirementsPackage.Literals.CEG_NODE__VARIABLE.getName()), var)
				&& checkStringEquality(obj.getString(RequirementsPackage.Literals.CEG_NODE__CONDITION.getName()), cond));
	}

	private Predicate<JSONObject> MATCHES_ID_VAR_COND(String id, String var, String cond) {
		return (obj -> obj.getString(ECLASS).equals(CEGNode.class.getSimpleName())
				&& obj.getString(BasePackage.Literals.IID__ID.getName()).equals(id)
				&& checkStringEquality(obj.getString(RequirementsPackage.Literals.CEG_NODE__VARIABLE.getName()), var)
				&& checkStringEquality(obj.getString(RequirementsPackage.Literals.CEG_NODE__CONDITION.getName()), cond));
	}
	
	
	/**
	 * Check if two strings contain the same words. Order and case are not important 
	 * @param s1
	 * @param s2
	 * @return
	 */
	private boolean checkStringEquality(String s1, String s2) {
		//System.out.println("Strings: " + s1 + " ---- " + s2);
		if(s1.equalsIgnoreCase(s2)) {
			System.out.println("Erfolgreich1: " + s1 + " ---- " + s2);
			return true;
		}
		String s1Lower = s1.toLowerCase();
		String[] stringArray = s2.toLowerCase().split(" ");
		for (String string : stringArray) {
			if(!s1Lower.contains(string)) {
				System.out.println("UNErfolgreich1: " + s1 + " ---- " + s2);
				return false;
			}
			s1Lower= s1Lower.replace(string, "");
		}
		s1Lower= s1Lower.replace(" ", "");
		System.out.println("s1Lower '" + s1Lower + "'");
		if(s1Lower.equals("")) {
			System.out.println("Erfolgreich2: " + s1 + " ---- " + s2);
			return true;
		}		
		System.out.println("UNErfolgreich2: " + s1 + " ---- " + s2);
		return false;
	}

	public ModelGenerationTestBase() throws Exception {
		super();

	}

	protected void checkResultingModel(JSONArray generated, CEGModel model) {
		List<CEGNode> nodesExp = SpecmateEcoreUtil.pickInstancesOf(model.getContents(), CEGNode.class);
		List<CEGConnection> connExp = SpecmateEcoreUtil.pickInstancesOf(model.getContents(), CEGConnection.class);

		// List all generated nodes as String
		String nodes = "";
		for (int i = 0; i < generated.length(); i++) {
			JSONObject item = generated.getJSONObject(i);
			if (item.keySet().contains("variable")) {
				nodes += "[" + item.get("variable") + " ; ";
			}
			if (item.keySet().contains("condition")) {
				nodes += item.get("condition") + "],";
			}
		}
		System.out.println(generated);

		// Verify number of nodes
		int numCEGNodesGen = count(generated, IS_CEG_NODE);
		int numCEGNodesExp = nodesExp.size();
		Assert.assertEquals("Number of Nodes not correct. Nodes Found:" + nodes, numCEGNodesExp, numCEGNodesGen);

		// Verify node content
		for (CEGNode node : nodesExp) {
			boolean matched = false;
			matched = (EmfRestTestUtil.matches(generated,
					MATCHES_VAR_COND(node.getVariable(), node.getCondition(), node.getType().getLiteral())));

			Assert.assertTrue("Node with variable \"" + node.getVariable() + "\" and condition \"" + node.getCondition()
					+ "\" not found. Nodes found: " + nodes, matched);
		}

		// Verify connections
		for (CEGConnection conn : connExp) {
			CEGNode expSource = (CEGNode) conn.getSource();
			CEGNode expTarget = (CEGNode) conn.getTarget();
			boolean matched = EmfRestTestUtil.matches(generated, obj -> {
				if (!obj.getString(ECLASS).equals(CEGConnection.class.getSimpleName())) {
					return false;
				}
				if (obj.getBoolean(RequirementsPackage.Literals.CEG_CONNECTION__NEGATE.getName()) != conn.isNegate()) {
					return false;
				}
				JSONObject srcRef = obj.getJSONObject(BasePackage.Literals.IMODEL_CONNECTION__SOURCE.getName());
				String srcId = srcRef.getString(URL);
				srcId = srcId.substring(srcId.lastIndexOf("/") + 1);
				JSONObject targetRef = obj.getJSONObject(BasePackage.Literals.IMODEL_CONNECTION__TARGET.getName());
				String targetId = targetRef.getString(URL);
				targetId = targetId.substring(targetId.lastIndexOf("/") + 1);

				return EmfRestTestUtil.matches(generated,
						MATCHES_ID_VAR_COND(srcId, expSource.getVariable(), expSource.getCondition()))
						&& EmfRestTestUtil.matches(generated,
								MATCHES_ID_VAR_COND(targetId, expTarget.getVariable(), expTarget.getCondition()));
			});

			Assert.assertTrue("Connection from node (\"" + expSource.getVariable() + "\",\"" + expSource.getCondition()
					+ "\")" + " to node (\"" + expTarget.getVariable() + "\",\"" + expTarget.getCondition()
					+ "\")  not found. Negated: " + conn.isNegate(), matched);
		}

	}

	protected JSONArray generateCEGWithModelRequirementsText(String text) {
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

	protected CEGNode createNode(CEGModel model, String var, String cond, NodeType type) {
		CEGNode node = RequirementsFactory.eINSTANCE.createCEGNode();
		node.setVariable(var);
		node.setCondition(cond);
		node.setId("id" + id++);
		node.setType(type);
		model.getContents().add(node);
		return node;
	}

	protected CEGConnection createConnection(CEGModel model, CEGNode src, CEGNode target, boolean negated) {
		CEGConnection conn = RequirementsFactory.eINSTANCE.createCEGConnection();
		conn.setNegate(negated);
		conn.setSource(src);
		conn.setTarget(target);
		model.getContents().add(conn);
		return conn;
	}
}

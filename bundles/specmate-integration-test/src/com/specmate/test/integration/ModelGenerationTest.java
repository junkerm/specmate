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

public class ModelGenerationTest extends EmfRestTest {

	private static int id = 0;

	private Predicate<JSONObject> IS_CEG_NODE = obj -> obj.getString(ECLASS).equals(CEGNode.class.getSimpleName());

	private Predicate<JSONObject> MATCHES_VAR_COND(String var, String cond, String type) {
		return (obj -> obj.getString(ECLASS).equals(CEGNode.class.getSimpleName())
				&& obj.getString(RequirementsPackage.Literals.CEG_NODE__TYPE.getName()).equals(type)
				&& obj.getString(RequirementsPackage.Literals.CEG_NODE__VARIABLE.getName()).equals(var)
				&& obj.getString(RequirementsPackage.Literals.CEG_NODE__CONDITION.getName()).equals(cond));
	}

	private Predicate<JSONObject> MATCHES_ID_VAR_COND(String id, String var, String cond) {
		return (obj -> obj.getString(ECLASS).equals(CEGNode.class.getSimpleName())
				&& obj.getString(BasePackage.Literals.IID__ID.getName()).equals(id)
				&& obj.getString(RequirementsPackage.Literals.CEG_NODE__VARIABLE.getName()).equals(var)
				&& obj.getString(RequirementsPackage.Literals.CEG_NODE__CONDITION.getName()).equals(cond));
	}

	public ModelGenerationTest() throws Exception {
		super();

	}

	@Test
	public void testModelGenerationEn1_Or() {
		String text = "If Specmate detects an error or the user has no login, Specmate shows a warning window and makes a sound.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "Specmate", "detects an error", NodeType.AND);
		CEGNode node2 = createNode(model, "the user", "has a login", NodeType.AND);
		CEGNode node3 = createNode(model, "Specmate", "shows a warning window", NodeType.OR);
		CEGNode node4 = createNode(model, "Specmate", "makes a sound", NodeType.OR);

		createConnection(model, node1, node3, false);
		createConnection(model, node2, node3, true);
		createConnection(model, node1, node4, false);
		createConnection(model, node2, node4, true);

		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationEn2_Passive() {
		String text = "If an error is detected or the user has no login, a warning window is shown and a sound is emitted.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "an error", "is detected", NodeType.AND);
		CEGNode node2 = createNode(model, "the user", "has a login", NodeType.AND);
		CEGNode node3 = createNode(model, "a warning window", "is shown", NodeType.OR);
		CEGNode node4 = createNode(model, "a sound", "is emitted", NodeType.OR);

		createConnection(model, node1, node3, false);
		createConnection(model, node2, node3, true);
		createConnection(model, node1, node4, false);
		createConnection(model, node2, node4, true);

		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationEn3_AndOr() {
		String text = "If the user has no login and login is needed or an error is detected, a warning window is shown and a signal is emitted.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "an error", "is detected", NodeType.AND);
		CEGNode node2 = createNode(model, "the user", "has a login", NodeType.AND);
		CEGNode node3 = createNode(model, "login", "is needed", NodeType.AND);
		CEGNode node4 = createNode(model, "the user", "has no login and login is needed", NodeType.AND);
		CEGNode node5 = createNode(model, "a warning window", "is shown", NodeType.OR);
		CEGNode node6 = createNode(model, "a signal", "is emitted", NodeType.OR);

		createConnection(model, node2, node4, true);
		createConnection(model, node3, node4, false);
		createConnection(model, node4, node5, false);
		createConnection(model, node4, node6, false);
		createConnection(model, node1, node5, false);
		createConnection(model, node1, node6, false);

		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationEn4_SpecmateExamples() {
		String text = "When the user selects the option to create a process model in the Process Models section of the Requirements Overview, an empty process model is displayed in the Process Model Editor.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "the user",
				"selects the option to create a process model in the Process Models section of the Requirements Overview",
				NodeType.AND);
		CEGNode node2 = createNode(model, "an empty process model", "is displayed in the Process Model Editor",
				NodeType.AND);

		createConnection(model, node1, node2, false);

		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationDe1_Or() {
		String text = "Wenn das Werkzeug einen Fehler oder ein Problem erkennt oder der Benutzer keine Anmeldung hat, zeigt das Werkzeug ein Warnfenster an und gibt einen Signalton aus.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "das Werkzeug", "einen Fehler erkennt", NodeType.AND);
		CEGNode node1b = createNode(model, "das Werkzeug", "ein Problem erkennt", NodeType.AND);
		CEGNode node2 = createNode(model, "der Benutzer", "eine Anmeldung hat", NodeType.AND);
		CEGNode node3 = createNode(model, "das Werkzeug", "zeigt ein Warnfenster an", NodeType.OR);
		CEGNode node4 = createNode(model, "das Werkzeug", "gibt einen Signalton aus", NodeType.OR);

		createConnection(model, node1, node3, false);
		createConnection(model, node1b, node3, false);
		createConnection(model, node2, node3, true);
		createConnection(model, node1, node4, false);
		createConnection(model, node1b, node4, false);
		createConnection(model, node2, node4, true);

		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationDe2_Passive() {
		String text = "Wenn ein Fehler erkannt wird oder der Benutzer keine Anmeldung hat, wird ein Warnfenster angezeigt und hervorgehoben.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "ein Fehler", "erkannt wird", NodeType.AND);
		CEGNode node2 = createNode(model, "der Benutzer", "eine Anmeldung hat", NodeType.AND);
		CEGNode node3 = createNode(model, "ein Warnfenster", "wird angezeigt", NodeType.OR);
		CEGNode node4 = createNode(model, "ein Warnfenster", "hervorgehoben", NodeType.OR);

		createConnection(model, node1, node3, false);
		createConnection(model, node2, node3, true);
		createConnection(model, node1, node4, false);
		createConnection(model, node2, node4, true);

		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationDe4_SpecmateExamples() {
		// TODO: Fix variable/condition split
		String text = "Wenn der Benutzer die Option zum Anlegen eines Prozessmodells im Abschnitt Prozessmodelle der Anforderungsübersicht wählt, wird im Prozessmodell-Editor ein leeres Prozessmodell angezeigt.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "der Benutzer",
				"die Option zum Anlegen eines Prozessmodells im Abschnitt Prozessmodelle der Anforderungsübersicht wählt",
				NodeType.AND);
		CEGNode node2 = createNode(model, "Prozessmodell-Editor", "wird im ein leeres Prozessmodell angezeigt",
				NodeType.AND);

		createConnection(model, node1, node2, false);

		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	private void checkResultingModel(JSONArray generated, CEGModel model) {
		List<CEGNode> nodesExp = SpecmateEcoreUtil.pickInstancesOf(model.getContents(), CEGNode.class);
		List<CEGConnection> connExp = SpecmateEcoreUtil.pickInstancesOf(model.getContents(), CEGConnection.class);

		// Verify number of nodes
		int numCEGNodesGen = count(generated, IS_CEG_NODE);
		int numCEGNodesExp = nodesExp.size();
		Assert.assertEquals(numCEGNodesExp, numCEGNodesGen);

		// Verify node content
		for (CEGNode node : nodesExp) {
			boolean matched = (EmfRestTestUtil.matches(generated,
					MATCHES_VAR_COND(node.getVariable(), node.getCondition(), node.getType().getLiteral())));
			Assert.assertTrue("Node with variable \"" + node.getVariable() + "\" and condition \"" + node.getCondition()
					+ "\" not found. With variables generated:" + generated + " and machtes-var-cond: " + MATCHES_VAR_COND(node.getVariable(), node.getCondition(), node.getType().getLiteral()) , matched);
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
			}

			);

			Assert.assertTrue("Condition from node (\"" + expSource.getVariable() + "\",\"" + expSource.getCondition()
					+ "\")" + " to node (\"" + expTarget.getVariable() + "\",\"" + expTarget.getCondition()
					+ "\")  not found", matched);
		}
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

	private CEGNode createNode(CEGModel model, String var, String cond, NodeType type) {
		CEGNode node = RequirementsFactory.eINSTANCE.createCEGNode();
		node.setVariable(var);
		node.setCondition(cond);
		node.setId("id" + id++);
		node.setType(type);
		model.getContents().add(node);
		return node;
	}

	private CEGConnection createConnection(CEGModel model, CEGNode src, CEGNode target, boolean negated) {
		CEGConnection conn = RequirementsFactory.eINSTANCE.createCEGConnection();
		conn.setNegate(negated);
		conn.setSource(src);
		conn.setTarget(target);
		model.getContents().add(conn);
		return conn;
	}
}

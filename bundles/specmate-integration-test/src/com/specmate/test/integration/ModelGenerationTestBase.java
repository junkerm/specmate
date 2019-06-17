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
				&& obj.getString(RequirementsPackage.Literals.CEG_NODE__VARIABLE.getName()).equals(var)
				&& obj.getString(RequirementsPackage.Literals.CEG_NODE__CONDITION.getName()).equals(cond));
	}

	private Predicate<JSONObject> MATCHES_ID_VAR_COND(String id, String var, String cond) {
		return (obj -> obj.getString(ECLASS).equals(CEGNode.class.getSimpleName())
				&& obj.getString(BasePackage.Literals.IID__ID.getName()).equals(id)
				&& obj.getString(RequirementsPackage.Literals.CEG_NODE__VARIABLE.getName()).equals(var)
				&& obj.getString(RequirementsPackage.Literals.CEG_NODE__CONDITION.getName()).equals(cond));
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

		// Verify number of nodes
		int numCEGNodesGen = count(generated, IS_CEG_NODE);
		int numCEGNodesExp = nodesExp.size();
		Assert.assertEquals("Number of Nodes not correct. Nodes Found:" + nodes, numCEGNodesExp, numCEGNodesGen);

		// Verify node content
		for (CEGNode node : nodesExp) {
			boolean matched = false;
			if (node.getCondition().equals("")) {
				matched = (EmfRestTestUtil.matches(generated,
						MATCHES_VAR_COND(node.getVariable(), node.getCondition(), node.getType().getLiteral())))
						|| (EmfRestTestUtil.matches(generated,
								MATCHES_VAR_COND(firstLetterUpper(node.getVariable()), node.getCondition(),
										node.getType().getLiteral())))
						|| (EmfRestTestUtil.matches(generated, MATCHES_VAR_COND(firstLetterLower(node.getVariable()),
								node.getCondition(), node.getType().getLiteral())));
			} else {
				matched = (EmfRestTestUtil.matches(generated,
						MATCHES_VAR_COND(node.getVariable(), node.getCondition(), node.getType().getLiteral())))
						|| (EmfRestTestUtil.matches(generated,
								MATCHES_VAR_COND(firstLetterUpper(node.getVariable()),
										firstLetterUpper(node.getCondition()), node.getType().getLiteral())))
						|| (EmfRestTestUtil.matches(generated,
								MATCHES_VAR_COND(firstLetterLower(node.getVariable()),
										firstLetterLower(node.getCondition()), node.getType().getLiteral())))
						|| (EmfRestTestUtil.matches(generated,
								MATCHES_VAR_COND(node.getVariable(), firstLetterUpper(node.getCondition()),
										node.getType().getLiteral())))
						|| (EmfRestTestUtil.matches(generated,
								MATCHES_VAR_COND(node.getVariable(), firstLetterLower(node.getCondition()),
										node.getType().getLiteral())))
						|| (EmfRestTestUtil.matches(generated, MATCHES_VAR_COND(node.getVariable(),
								firstLetterLower(node.getCondition()), node.getType().getLiteral())));
			}

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
				
				return verifyConnections(expSource, srcId, expTarget, targetId, generated);
			}

			);

			Assert.assertTrue("Connection from node (\"" + expSource.getVariable() + "\",\"" + expSource.getCondition()
					+ "\")" + " to node (\"" + expTarget.getVariable() + "\",\"" + expTarget.getCondition()
					+ "\")  not found", matched);
		}

	}

	private String firstLetterUpper(String string) {
		return string.substring(0, 1).toUpperCase() + string.substring(1);
	}

	private String firstLetterLower(String string) {
		return string.substring(0, 1).toLowerCase() + string.substring(1);
	}
	
	//Check all combination of Nodes(lower/upper case, with/without condition...)
	private boolean verifyConnections(CEGNode expSource, String srcId,  CEGNode expTarget, String targetId, JSONArray generated) {
		if (expSource.getCondition().equals("")) {
			return (EmfRestTestUtil.matches(generated,
					MATCHES_ID_VAR_COND(srcId, expSource.getVariable(), expSource.getCondition()))
					|| EmfRestTestUtil.matches(generated,
							MATCHES_ID_VAR_COND(srcId,
									expSource.getVariable().substring(0, 1).toUpperCase()
											+ expSource.getVariable().substring(1),
									expSource.getCondition()))
					|| EmfRestTestUtil.matches(generated,
							MATCHES_ID_VAR_COND(srcId,
									expSource.getVariable().substring(0, 1).toLowerCase()
											+ expSource.getVariable().substring(1),
									expSource.getCondition()))
					|| EmfRestTestUtil.matches(generated,
							MATCHES_ID_VAR_COND(srcId,
									expSource.getVariable().substring(0, 1).toUpperCase()
											+ expSource.getVariable().substring(1),
									expSource.getCondition()))
					|| EmfRestTestUtil.matches(generated,
							MATCHES_ID_VAR_COND(srcId,
									expSource.getVariable().substring(0, 1).toLowerCase()
											+ expSource.getVariable().substring(1),
									expSource.getCondition()))
					|| EmfRestTestUtil.matches(generated,
							MATCHES_ID_VAR_COND(srcId, expSource.getVariable(), expSource.getCondition()))
					|| EmfRestTestUtil.matches(generated,
							MATCHES_ID_VAR_COND(srcId, expSource.getVariable(), expSource.getCondition())))

					&& (EmfRestTestUtil.matches(generated,
							MATCHES_ID_VAR_COND(targetId, expTarget.getVariable(), expTarget.getCondition()))
							|| EmfRestTestUtil.matches(generated,
									MATCHES_ID_VAR_COND(targetId,
											expTarget.getVariable().substring(0, 1).toUpperCase()
													+ expTarget.getVariable().substring(1),
											expTarget.getCondition().substring(0, 1).toUpperCase()
													+ expTarget.getCondition().substring(1)))
							|| EmfRestTestUtil.matches(generated,
									MATCHES_ID_VAR_COND(targetId,
											expTarget.getVariable().substring(0, 1).toLowerCase()
													+ expTarget.getVariable().substring(1),
											expTarget.getCondition().substring(0, 1).toLowerCase()
													+ expTarget.getCondition().substring(1)))
							|| EmfRestTestUtil.matches(generated, MATCHES_ID_VAR_COND(targetId,
									expTarget.getVariable().substring(0, 1).toUpperCase()
											+ expTarget.getVariable().substring(1),
									expTarget.getCondition()))
							|| EmfRestTestUtil.matches(generated, MATCHES_ID_VAR_COND(targetId,
									expTarget.getVariable().substring(0, 1).toLowerCase()
											+ expTarget.getVariable().substring(1),
									expTarget.getCondition()))
							|| EmfRestTestUtil.matches(generated,
									MATCHES_ID_VAR_COND(targetId, expTarget.getVariable(),
											expTarget.getCondition().substring(0, 1).toUpperCase()
													+ expTarget.getCondition().substring(1)))
							|| EmfRestTestUtil.matches(generated,
									MATCHES_ID_VAR_COND(targetId, expTarget.getVariable(),
											expTarget.getCondition().substring(0, 1).toLowerCase()
													+ expTarget.getCondition().substring(1))));
		} else if (expTarget.getCondition().equals("")) {
			return (EmfRestTestUtil.matches(generated,
					MATCHES_ID_VAR_COND(srcId, expSource.getVariable(), expSource.getCondition()))
					|| EmfRestTestUtil.matches(generated,
							MATCHES_ID_VAR_COND(srcId,
									expSource.getVariable().substring(0, 1).toUpperCase()
											+ expSource.getVariable().substring(1),
									expSource.getCondition().substring(0, 1).toUpperCase()
											+ expSource.getCondition().substring(1)))
					|| EmfRestTestUtil.matches(generated,
							MATCHES_ID_VAR_COND(srcId,
									expSource.getVariable().substring(0, 1).toLowerCase()
											+ expSource.getVariable().substring(1),
									expSource.getCondition().substring(0, 1).toLowerCase()
											+ expSource.getCondition().substring(1)))
					|| EmfRestTestUtil.matches(generated,
							MATCHES_ID_VAR_COND(srcId,
									expSource.getVariable().substring(0, 1).toUpperCase()
											+ expSource.getVariable().substring(1),
									expSource.getCondition()))
					|| EmfRestTestUtil.matches(generated,
							MATCHES_ID_VAR_COND(srcId,
									expSource.getVariable().substring(0, 1).toLowerCase()
											+ expSource.getVariable().substring(1),
									expSource.getCondition()))
					|| EmfRestTestUtil.matches(generated,
							MATCHES_ID_VAR_COND(srcId, expSource.getVariable(),
									expSource.getCondition().substring(0, 1).toUpperCase()
											+ expSource.getCondition().substring(1)))
					|| EmfRestTestUtil.matches(generated,
							MATCHES_ID_VAR_COND(srcId, expSource.getVariable(),
									expSource.getCondition().substring(0, 1).toLowerCase()
											+ expSource.getCondition().substring(1))))

					&& (EmfRestTestUtil.matches(generated,
							MATCHES_ID_VAR_COND(targetId, expTarget.getVariable(), expTarget.getCondition()))
							|| EmfRestTestUtil.matches(generated, MATCHES_ID_VAR_COND(targetId,
									expTarget.getVariable().substring(0, 1).toUpperCase()
											+ expTarget.getVariable().substring(1),
									expTarget.getCondition()))
							|| EmfRestTestUtil.matches(generated, MATCHES_ID_VAR_COND(targetId,
									expTarget.getVariable().substring(0, 1).toLowerCase()
											+ expTarget.getVariable().substring(1),
									expTarget.getCondition()))
							|| EmfRestTestUtil.matches(generated, MATCHES_ID_VAR_COND(targetId,
									expTarget.getVariable().substring(0, 1).toUpperCase()
											+ expTarget.getVariable().substring(1),
									expTarget.getCondition()))
							|| EmfRestTestUtil.matches(generated, MATCHES_ID_VAR_COND(targetId,
									expTarget.getVariable().substring(0, 1).toLowerCase()
											+ expTarget.getVariable().substring(1),
									expTarget.getCondition()))
							|| EmfRestTestUtil.matches(generated,
									MATCHES_ID_VAR_COND(targetId, expTarget.getVariable(),
											expTarget.getCondition()))
							|| EmfRestTestUtil.matches(generated, MATCHES_ID_VAR_COND(targetId,
									expTarget.getVariable(), expTarget.getCondition())));
		} else {
			return (EmfRestTestUtil.matches(generated,
					MATCHES_ID_VAR_COND(srcId, expSource.getVariable(), expSource.getCondition()))
					|| EmfRestTestUtil.matches(generated,
							MATCHES_ID_VAR_COND(srcId,
									expSource.getVariable().substring(0, 1).toUpperCase()
											+ expSource.getVariable().substring(1),
									expSource.getCondition().substring(0, 1).toUpperCase()
											+ expSource.getCondition().substring(1)))
					|| EmfRestTestUtil.matches(generated,
							MATCHES_ID_VAR_COND(srcId,
									expSource.getVariable().substring(0, 1).toLowerCase()
											+ expSource.getVariable().substring(1),
									expSource.getCondition().substring(0, 1).toLowerCase()
											+ expSource.getCondition().substring(1)))
					|| EmfRestTestUtil.matches(generated,
							MATCHES_ID_VAR_COND(srcId,
									expSource.getVariable().substring(0, 1).toUpperCase()
											+ expSource.getVariable().substring(1),
									expSource.getCondition()))
					|| EmfRestTestUtil.matches(generated,
							MATCHES_ID_VAR_COND(srcId,
									expSource.getVariable().substring(0, 1).toLowerCase()
											+ expSource.getVariable().substring(1),
									expSource.getCondition()))
					|| EmfRestTestUtil.matches(generated,
							MATCHES_ID_VAR_COND(srcId, expSource.getVariable(),
									expSource.getCondition().substring(0, 1).toUpperCase()
											+ expSource.getCondition().substring(1)))
					|| EmfRestTestUtil.matches(generated,
							MATCHES_ID_VAR_COND(srcId, expSource.getVariable(),
									expSource.getCondition().substring(0, 1).toLowerCase()
											+ expSource.getCondition().substring(1))))

					&& (EmfRestTestUtil.matches(generated,
							MATCHES_ID_VAR_COND(targetId, expTarget.getVariable(), expTarget.getCondition()))
							|| EmfRestTestUtil.matches(generated,
									MATCHES_ID_VAR_COND(targetId,
											expTarget.getVariable().substring(0, 1).toUpperCase()
													+ expTarget.getVariable().substring(1),
											expTarget.getCondition().substring(0, 1).toUpperCase()
													+ expTarget.getCondition().substring(1)))
							|| EmfRestTestUtil.matches(generated,
									MATCHES_ID_VAR_COND(targetId,
											expTarget.getVariable().substring(0, 1).toLowerCase()
													+ expTarget.getVariable().substring(1),
											expTarget.getCondition().substring(0, 1).toLowerCase()
													+ expTarget.getCondition().substring(1)))
							|| EmfRestTestUtil.matches(generated, MATCHES_ID_VAR_COND(targetId,
									expTarget.getVariable().substring(0, 1).toUpperCase()
											+ expTarget.getVariable().substring(1),
									expTarget.getCondition()))
							|| EmfRestTestUtil.matches(generated, MATCHES_ID_VAR_COND(targetId,
									expTarget.getVariable().substring(0, 1).toLowerCase()
											+ expTarget.getVariable().substring(1),
									expTarget.getCondition()))
							|| EmfRestTestUtil.matches(generated,
									MATCHES_ID_VAR_COND(targetId, expTarget.getVariable(),
											expTarget.getCondition().substring(0, 1).toUpperCase()
													+ expTarget.getCondition().substring(1)))
							|| EmfRestTestUtil.matches(generated,
									MATCHES_ID_VAR_COND(targetId, expTarget.getVariable(),
											expTarget.getCondition().substring(0, 1).toLowerCase()
													+ expTarget.getCondition().substring(1))));
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

package com.specmate.test.integration;

import org.junit.Assert;
import org.junit.Test;

import java.net.URISyntaxException;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.json.JSONArray;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import com.specmate.model.requirements.CEGModel;
import com.specmate.model.requirements.CEGNode;
import com.specmate.model.requirements.NodeType;
import com.specmate.model.requirements.RequirementsFactory;
import com.specmate.objectif.resolve.BusinessRuleUtil;
import com.specmate.objectif.resolve.rule.AndNode;
import com.specmate.objectif.resolve.rule.BusinessRuleNode;
import com.specmate.objectif.resolve.rule.LiteralNode;
import com.specmate.objectif.resolve.rule.ObjectifNode;
import com.specmate.objectif.resolve.rule.OrNode;
import com.specmate.xtext.XTextException;

// Two types of tests:
// 1) tests for checking the functionality of the xtext parsing
// 2) tests for checking the functionality of the pseudo code translator
public class ObjectifTest extends ModelGenerationTestBase {

	public ObjectifTest() throws Exception {
		super();
	}

	// Unit tests for the Xtext Parsing
	@Test
	public void testLoadRules() throws URISyntaxException, XTextException {
		List<BusinessRuleNode> rules = loadRules("/resources/test_rules.objectif");
		Assert.assertTrue(rules.size() == 5);
	}

	@Test
	public void testAND() throws URISyntaxException, XTextException {
		List<BusinessRuleNode> rules = loadRules("/resources/test_rules.objectif");
		BusinessRuleNode rule = rules.get(0);
		ObjectifNode cause = rule.getCause();
		Assert.assertTrue(cause instanceof AndNode);

		ObjectifNode causeA = ((AndNode) cause).getLeft();
		ObjectifNode causeB = ((AndNode) cause).getRight();
		Assert.assertTrue(causeA instanceof LiteralNode);
		Assert.assertTrue(((LiteralNode) causeA).getContent().equals("A=5"));
		Assert.assertTrue(causeB instanceof LiteralNode);
		Assert.assertTrue(((LiteralNode) causeB).getContent().equals("X=9"));

		ObjectifNode effect = rule.getEffect();
		Assert.assertTrue(effect instanceof LiteralNode);
		Assert.assertTrue(((LiteralNode) effect).getContent().equals("C=7"));
	}

	@Test
	public void testOR() throws URISyntaxException, XTextException {
		List<BusinessRuleNode> rules = loadRules("/resources/test_rules.objectif");
		BusinessRuleNode rule = rules.get(1);
		ObjectifNode cause = rule.getCause();
		Assert.assertTrue(cause instanceof OrNode);

		ObjectifNode causeA = ((OrNode) cause).getLeft();
		ObjectifNode causeB = ((OrNode) cause).getRight();
		Assert.assertTrue(causeA instanceof LiteralNode);
		Assert.assertTrue(((LiteralNode) causeA).getContent().equals("X = Z"));
		Assert.assertTrue(causeB instanceof LiteralNode);
		Assert.assertTrue(((LiteralNode) causeB).getContent().equals("Y>4"));

		ObjectifNode effect = rule.getEffect();
		Assert.assertTrue(effect instanceof LiteralNode);
		Assert.assertTrue(((LiteralNode) effect).getContent().equals("F=\"Test\""));
	}

	@Test
	public void testNesting() throws URISyntaxException, XTextException {
		List<BusinessRuleNode> rules = loadRules("/resources/test_rules.objectif");
		BusinessRuleNode rule = rules.get(2);
		ObjectifNode cause = rule.getCause();
		Assert.assertTrue(cause instanceof LiteralNode);
		Assert.assertTrue(((LiteralNode) cause).getContent().equals("A=1"));

		ObjectifNode effect = rule.getEffect();
		Assert.assertTrue(effect instanceof BusinessRuleNode);

		BusinessRuleNode ruleB = (BusinessRuleNode) effect;

		ObjectifNode causeB = ruleB.getCause();
		Assert.assertTrue(causeB instanceof LiteralNode);
		Assert.assertTrue(((LiteralNode) causeB).getContent().equals("B=1"));

		ObjectifNode effectB = ruleB.getEffect();
		Assert.assertTrue(effectB instanceof LiteralNode);
		Assert.assertTrue(((LiteralNode) effectB).getContent().equals("C=1"));
	}

	@Test
	public void testAlternative() throws URISyntaxException, XTextException {
		List<BusinessRuleNode> rules = loadRules("/resources/test_rules.objectif");
		BusinessRuleNode rule = rules.get(3);
		ObjectifNode cause = rule.getCause();
		Assert.assertTrue(cause instanceof LiteralNode);
		Assert.assertTrue(((LiteralNode) cause).getContent().equals("A=1"));
		ObjectifNode effect = rule.getEffect();
		Assert.assertTrue(effect instanceof LiteralNode);
		Assert.assertTrue(((LiteralNode) effect).getContent().equals("B=1"));

		Assert.assertTrue(rule.hasAlternative());
		ObjectifNode alternative = rule.getAlternative();
		Assert.assertTrue(alternative instanceof LiteralNode);
		Assert.assertTrue(((LiteralNode) alternative).getContent().equals("C=2"));
	}

	@Test
	public void testAlternativeCombinedWithNesting() throws URISyntaxException, XTextException {
		List<BusinessRuleNode> rules = loadRules("/resources/test_rules.objectif");
		BusinessRuleNode rule = rules.get(4);
		ObjectifNode cause = rule.getCause();
		Assert.assertTrue(cause instanceof LiteralNode);
		Assert.assertTrue(((LiteralNode) cause).getContent().equals("A=1"));
		ObjectifNode effect = rule.getEffect();
		Assert.assertTrue(effect instanceof BusinessRuleNode);
		BusinessRuleNode ruleB = (BusinessRuleNode) effect;

		ObjectifNode causeB = ruleB.getCause();
		Assert.assertTrue(causeB instanceof LiteralNode);
		Assert.assertTrue(((LiteralNode) causeB).getContent().equals("B=1"));

		ObjectifNode effectB = ruleB.getEffect();
		Assert.assertTrue(effectB instanceof LiteralNode);
		Assert.assertTrue(((LiteralNode) effectB).getContent().equals("C=1"));

		Assert.assertTrue(ruleB.hasAlternative());
		ObjectifNode alternative = ruleB.getAlternative();
		Assert.assertTrue(alternative instanceof LiteralNode);
		Assert.assertTrue(((LiteralNode) alternative).getContent().equals("C=2"));

	}

	// Unit tests for the pseudo code translation
	@Test
	public void testModelGenerationSimplePseudoCode() {
		String text = "WENN x DANN y ENDE-WENN";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "BR1", "is present", NodeType.OR);
		CEGNode node2 = createNode(model, "x", "is present", null);
		CEGNode node3 = createNode(model, "y", "is present", null);
		createConnection(model, node1, node3, false);
		createConnection(model, node2, node1, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationPseudoCodeWithInterconnectedCauses() {
		String text = "WENN x UND y UND z ODER w DANN effekt ENDE-WENN";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "BR1", "is present", NodeType.OR);
		CEGNode node2 = createNode(model, "x", "is present", null);
		CEGNode node3 = createNode(model, "y", "is present", null);
		CEGNode node4 = createNode(model, "z", "is present", null);
		CEGNode node5 = createNode(model, "w", "is present", null);
		CEGNode node6 = createNode(model, "effekt", "is present", null);
		CEGNode node7 = createNode(model, "Intermediate Node1", "is present", NodeType.AND);
		createConnection(model, node2, node7, false);
		createConnection(model, node3, node7, false);
		createConnection(model, node4, node7, false);
		createConnection(model, node5, node1, false);
		createConnection(model, node7, node1, false);
		createConnection(model, node1, node6, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationPseudoCodeWithInterconnectedCausesAndSingleNesting() {
		String text = "WENN x UND y UND z ODER w DANN WENN xB ODER yB DANN effekt ENDE-WENN ENDE-WENN";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "BR1", "is present", NodeType.OR);
		CEGNode node2 = createNode(model, "x", "is present", null);
		CEGNode node3 = createNode(model, "y", "is present", null);
		CEGNode node4 = createNode(model, "z", "is present", null);
		CEGNode node5 = createNode(model, "w", "is present", null);
		CEGNode node7 = createNode(model, "Intermediate Node1", "is present", NodeType.AND);

		CEGNode node8 = createNode(model, "BR2", "is present", NodeType.AND);
		CEGNode node9 = createNode(model, "xB", "is present", null);
		CEGNode node10 = createNode(model, "yB", "is present", null);
		CEGNode node6 = createNode(model, "effekt", "is present", null);
		CEGNode node11 = createNode(model, "Intermediate Node2", "is present", NodeType.OR);

		createConnection(model, node2, node7, false);
		createConnection(model, node3, node7, false);
		createConnection(model, node4, node7, false);
		createConnection(model, node5, node1, false);
		createConnection(model, node7, node1, false);
		createConnection(model, node1, node8, false);
		createConnection(model, node9, node11, false);
		createConnection(model, node10, node11, false);
		createConnection(model, node11, node8, false);
		createConnection(model, node8, node6, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationPseudoCodeWithMultipleNesting() {
		String text = "WENN x UND y DANN WENN xB ODER yB DANN WENN xC UND yC DANN effekt ENDE-WENN ENDE-WENN ENDE-WENN";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "BR1", "is present", NodeType.AND);
		CEGNode node2 = createNode(model, "x", "is present", null);
		CEGNode node3 = createNode(model, "y", "is present", null);

		CEGNode node4 = createNode(model, "BR2", "is present", NodeType.AND);
		CEGNode node5 = createNode(model, "xB", "is present", null);
		CEGNode node6 = createNode(model, "yB", "is present", null);
		CEGNode node7 = createNode(model, "Intermediate Node1", "is present", NodeType.OR);
		
		CEGNode node8 = createNode(model, "BR3", "is present", NodeType.AND);
		CEGNode node9 = createNode(model, "xC", "is present", null);
		CEGNode node10 = createNode(model, "yC", "is present", null);
		CEGNode node11 = createNode(model, "effekt", "is present", null);

		createConnection(model, node2, node1, false);
		createConnection(model, node3, node1, false);
		createConnection(model, node1, node4, false);
		createConnection(model, node5, node7, false);
		createConnection(model, node6, node7, false);
		createConnection(model, node7, node4, false);
		createConnection(model, node4, node8, false);
		createConnection(model, node9, node8, false);
		createConnection(model, node10, node8, false);
		createConnection(model, node8, node11, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}
	
	@Test
	public void testModelGenerationPseudoCodeWithMultipleNestingAndAlternativeEffect() {
		String text = "WENN x UND y DANN WENN xB ODER yB DANN WENN xC UND yC DANN effekt SONST aEffekt ENDE-WENN ENDE-WENN ENDE-WENN";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "BR1", "is present", NodeType.AND);
		CEGNode node2 = createNode(model, "x", "is present", null);
		CEGNode node3 = createNode(model, "y", "is present", null);

		CEGNode node4 = createNode(model, "BR2", "is present", NodeType.AND);
		CEGNode node5 = createNode(model, "xB", "is present", null);
		CEGNode node6 = createNode(model, "yB", "is present", null);
		CEGNode node7 = createNode(model, "Intermediate Node1", "is present", NodeType.OR);
		
		CEGNode node8 = createNode(model, "BR3", "is present", NodeType.AND);
		CEGNode node9 = createNode(model, "xC", "is present", null);
		CEGNode node10 = createNode(model, "yC", "is present", null);
		CEGNode node11 = createNode(model, "effekt", "is present", null);
		CEGNode node12 = createNode(model, "aEffekt", "is present", null);

		createConnection(model, node2, node1, false);
		createConnection(model, node3, node1, false);
		createConnection(model, node1, node4, false);
		createConnection(model, node5, node7, false);
		createConnection(model, node6, node7, false);
		createConnection(model, node7, node4, false);
		createConnection(model, node4, node8, false);
		createConnection(model, node9, node8, false);
		createConnection(model, node10, node8, false);
		createConnection(model, node8, node11, false);
		createConnection(model, node8, node12, true);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	private List<BusinessRuleNode> loadRules(String mainFile) throws URISyntaxException, XTextException {
		URI main = getLocalFile(mainFile);
		return new BusinessRuleUtil().loadXTextResources(main);
	}

	private URI getLocalFile(String fileName) throws URISyntaxException {
		Bundle bundle = FrameworkUtil.getBundle(NLPServiceTest.class);
		return URI.createURI(bundle.getResource(fileName).toURI().toString());
	}
}

package com.specmate.test.integration;

import org.junit.Assert;
import org.junit.Test;

import java.net.URISyntaxException;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import com.specmate.objectif.resolve.BusinessRuleUtil;
import com.specmate.objectif.resolve.rule.AndNode;
import com.specmate.objectif.resolve.rule.BusinessRuleNode;
import com.specmate.objectif.resolve.rule.LiteralNode;
import com.specmate.objectif.resolve.rule.ObjectifNode;
import com.specmate.objectif.resolve.rule.OrNode;
import com.specmate.xtext.XTextException;

public class ObjectifTest {
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
		
		ObjectifNode causeA = ((AndNode)cause).getLeft();
		ObjectifNode causeB = ((AndNode)cause).getRight();
		Assert.assertTrue(causeA instanceof LiteralNode);
		Assert.assertTrue(((LiteralNode)causeA).getContent().equals("A=5"));
		Assert.assertTrue(causeB instanceof LiteralNode);
		Assert.assertTrue(((LiteralNode)causeB).getContent().equals("X=9"));
		
		ObjectifNode effect = rule.getEffect();
		Assert.assertTrue(effect instanceof LiteralNode);
		Assert.assertTrue(((LiteralNode)effect).getContent().equals("C=7"));
	}
	
	@Test
	public void testOR() throws URISyntaxException, XTextException {
		List<BusinessRuleNode> rules = loadRules("/resources/test_rules.objectif");
		BusinessRuleNode rule = rules.get(1);
		ObjectifNode cause = rule.getCause();
		Assert.assertTrue(cause instanceof OrNode);
		
		ObjectifNode causeA = ((OrNode)cause).getLeft();
		ObjectifNode causeB = ((OrNode)cause).getRight();
		Assert.assertTrue(causeA instanceof LiteralNode);
		Assert.assertTrue(((LiteralNode)causeA).getContent().equals("X = Z"));
		Assert.assertTrue(causeB instanceof LiteralNode);
		Assert.assertTrue(((LiteralNode)causeB).getContent().equals("Y>4"));
		
		ObjectifNode effect = rule.getEffect();
		Assert.assertTrue(effect instanceof LiteralNode);
		Assert.assertTrue(((LiteralNode)effect).getContent().equals("F=\"Test\""));
	}
	
	@Test
	public void testNesting() throws URISyntaxException, XTextException {
		List<BusinessRuleNode> rules = loadRules("/resources/test_rules.objectif");
		BusinessRuleNode rule = rules.get(2);
		ObjectifNode cause = rule.getCause();
		Assert.assertTrue(cause instanceof LiteralNode);
		Assert.assertTrue(((LiteralNode)cause).getContent().equals("A=1"));
		
		ObjectifNode effect = rule.getEffect();
		Assert.assertTrue(effect instanceof BusinessRuleNode);
		
		BusinessRuleNode ruleB = (BusinessRuleNode) effect; 
		
		ObjectifNode causeB = ruleB.getCause();
		Assert.assertTrue(causeB instanceof LiteralNode);
		Assert.assertTrue(((LiteralNode)causeB).getContent().equals("B=1"));
		
		ObjectifNode effectB = ruleB.getEffect();
		Assert.assertTrue(effectB instanceof LiteralNode);
		Assert.assertTrue(((LiteralNode)effectB).getContent().equals("C=1"));
	}
	
	@Test
	public void testAlternative() throws URISyntaxException, XTextException {
		List<BusinessRuleNode> rules = loadRules("/resources/test_rules.objectif");
		BusinessRuleNode rule = rules.get(3);
		ObjectifNode cause = rule.getCause();
		Assert.assertTrue(cause instanceof LiteralNode);
		Assert.assertTrue(((LiteralNode)cause).getContent().equals("A=1"));
		ObjectifNode effect = rule.getEffect();
		Assert.assertTrue(effect instanceof LiteralNode);
		Assert.assertTrue(((LiteralNode)effect).getContent().equals("B=1"));
		
		Assert.assertTrue(rule.hasAlternative());
		ObjectifNode alternative = rule.getAlternative();
		Assert.assertTrue(alternative instanceof LiteralNode);
		Assert.assertTrue(((LiteralNode)alternative).getContent().equals("C=2"));
	}
	
	@Test
	public void testAlternativeCombinedWithNesting() throws URISyntaxException, XTextException {
		List<BusinessRuleNode> rules = loadRules("/resources/test_rules.objectif");
		BusinessRuleNode rule = rules.get(4);
		ObjectifNode cause = rule.getCause();
		Assert.assertTrue(cause instanceof LiteralNode);
		Assert.assertTrue(((LiteralNode)cause).getContent().equals("A=1"));
		ObjectifNode effect = rule.getEffect();
		Assert.assertTrue(effect instanceof BusinessRuleNode);
		BusinessRuleNode ruleB = (BusinessRuleNode) effect; 
		
		ObjectifNode causeB = ruleB.getCause();
		Assert.assertTrue(causeB instanceof LiteralNode);
		Assert.assertTrue(((LiteralNode)causeB).getContent().equals("B=1"));
		
		ObjectifNode effectB = ruleB.getEffect();
		Assert.assertTrue(effectB instanceof LiteralNode);
		Assert.assertTrue(((LiteralNode)effectB).getContent().equals("C=1"));
		
		Assert.assertTrue(ruleB.hasAlternative());
		ObjectifNode alternative = ruleB.getAlternative();
		Assert.assertTrue(alternative instanceof LiteralNode);
		Assert.assertTrue(((LiteralNode)alternative).getContent().equals("C=2"));
		
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

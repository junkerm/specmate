package com.specmate.test.integration;

import static org.junit.Assert.assertEquals;

import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.uima.jcas.JCas;
import org.eclipse.emf.common.util.URI;
import org.junit.Assert;
import org.junit.Test;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import com.specmate.cause_effect_patterns.parse.DependencyParsetree;
import com.specmate.cause_effect_patterns.parse.matcher.MatchResult;
import com.specmate.cause_effect_patterns.parse.matcher.MatchRule;
import com.specmate.cause_effect_patterns.parse.matcher.MatchUtil;
import com.specmate.cause_effect_patterns.parse.matcher.MatcherBase;
import com.specmate.cause_effect_patterns.parse.matcher.MatcherException;
import com.specmate.cause_effect_patterns.resolve.XTextUtil;
import com.specmate.cause_effect_patterns.resolve.XTextException;
import com.specmate.common.exception.SpecmateException;
import com.specmate.nlp.api.ELanguage;
import com.specmate.nlp.api.INLPService;

public class CauseEffectPatternTest {
	
	@Test
	public void testLoadRules() throws SpecmateException, URISyntaxException, XTextException {
		List<MatchRule> res = loadRules("/resources/rule_EN.spec");
		assertEquals(35, res.size());
		
		List<MatchRule> res2 = loadRules("/resources/test_ruleset.spec");
		assertEquals(2, res2.size());
	}
	
	
	@Test
	public void testDependencyParse() throws SpecmateException, MatcherException, URISyntaxException, XTextException {
		INLPService nlpService = NLPServiceTest.getNLPService();
		JCas result = nlpService.processText("If the tool encounters an error, it beeps.", ELanguage.EN);
		
		DependencyParsetree data = DependencyParsetree.generateFromJCas(result);
		Assert.assertEquals(data.getHeads().size(), 1);
		
		List<MatcherBase> rules = convertMatchRule(loadRules("/resources/test_ruleset.spec"));
		
		// Run the rules
		List<MatchResult> results = MatchUtil.evaluateRuleset(rules, data);
		Assert.assertEquals(data.getHeads().size(), results.size());
		
		MatchResult res = results.get(0);
		Assert.assertTrue(res.isSuccessfulMatch());
		
		// Get the result:
		//Cause
		Assert.assertTrue(res.hasSubmatch("Cause"));
		MatchResult cause = res.getSubmatch("Cause");
		Assert.assertTrue(cause.isSuccessfulMatch());		
		Assert.assertTrue(cause.hasSubmatch("Subject"));
		MatchResult causeSubj = cause.getSubmatch("Subject");
		Assert.assertTrue(cause.hasSubmatch("Predicate"));
		MatchResult causePred = cause.getSubmatch("Predicate");
		
		Assert.assertEquals("the tool encounters an error", cause.getMatchTree().getTextInterval(0).text);
		Assert.assertEquals("the tool", causeSubj.getMatchTree().getTextInterval(0).text);
		Assert.assertEquals("encounters an error", causePred.getMatchTree().getTextInterval(0).text);
		
		//Effect
		Assert.assertTrue(res.hasSubmatch("Effect"));
		MatchResult effect = res.getSubmatch("Effect");
		Assert.assertTrue(effect.isSuccessfulMatch());
		Assert.assertTrue(effect.hasSubmatch("Subject"));
		MatchResult effectSubj = effect.getSubmatch("Subject");
		Assert.assertTrue(effect.hasSubmatch("Predicate"));
		MatchResult effectPred = effect.getSubmatch("Predicate");
		
		Assert.assertEquals(", it beeps.", effect.getMatchTree().getTextInterval(0).text);
		Assert.assertEquals("it", effectSubj.getMatchTree().getTextInterval(0).text);
		Assert.assertEquals("beeps.", effectPred.getMatchTree().getTextInterval(1).text);
		
		List<MatcherBase> rules2 = rules.stream().limit(1).collect(Collectors.toList());
		
		JCas result2 = nlpService.processText("When the tool encounters an error then it beeps.", ELanguage.EN);
		DependencyParsetree data2 = DependencyParsetree.generateFromJCas(result2);
		
		List<MatchResult> results2 = MatchUtil.evaluateRuleset(rules2, data2);
		Assert.assertEquals(data2.getHeads().size(), results2.size());
		
		MatchResult res2 = results2.get(0);
		Assert.assertTrue(!res2.isSuccessfulMatch());
	}
	
	private URI getLocalFile(String fileName) throws URISyntaxException {
		Bundle bundle = FrameworkUtil.getBundle(NLPServiceTest.class);
		return URI.createURI(bundle.getResource(fileName).toURI().toString());
	}
	
	private List<MatchRule> loadRules(String mainFile) throws URISyntaxException, XTextException {
		URI main = getLocalFile(mainFile);
		URI pos = getLocalFile("/resources/pos_EN.spec");
		URI dep = getLocalFile("/resources/dep_EN.spec");
		return XTextUtil.generateMatchers(main, pos, dep); 
	}
	
	private List<MatcherBase> convertMatchRule(List<MatchRule> rules) {
		return rules.stream().map(m -> m.matcher).collect(Collectors.toList());
	}
}

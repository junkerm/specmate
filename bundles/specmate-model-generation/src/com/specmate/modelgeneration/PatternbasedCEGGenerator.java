package com.specmate.modelgeneration;

import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.apache.uima.jcas.JCas;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.xml.type.internal.RegEx.Match;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import com.specmate.cause_effect_patterns.parse.DependencyParsetree;
import com.specmate.cause_effect_patterns.parse.matcher.MatchResult;
import com.specmate.cause_effect_patterns.parse.matcher.MatchRule;
import com.specmate.cause_effect_patterns.parse.matcher.MatchUtil;
import com.specmate.cause_effect_patterns.parse.matcher.MatcherBase;
import com.specmate.cause_effect_patterns.resolve.XTextException;
import com.specmate.cause_effect_patterns.resolve.XTextUtil;
import com.specmate.common.exception.SpecmateException;
import com.specmate.model.requirements.CEGModel;
import com.specmate.model.requirements.CEGNode;
import com.specmate.model.requirements.NodeType;
import com.specmate.nlp.api.ELanguage;
import com.specmate.nlp.api.INLPService;

public class PatternbasedCEGGenerator {
	private INLPService tagger;
	private List<MatchRule> rules;
	private CEGCreation creation;
	private ELanguage lang;
	
	public PatternbasedCEGGenerator(String folder, ELanguage lang, INLPService tagger) throws URISyntaxException, XTextException {
		URI dep = getLocalFile(folder + "/Dep " + lang.getLanguage().toUpperCase() + ".spec");
		URI pos = getLocalFile(folder + "/Pos " + lang.getLanguage().toUpperCase() + ".spec");
		URI rule= getLocalFile(folder + "/Rule "+ lang.getLanguage().toUpperCase() + ".spec");
		
		this.rules = XTextUtil.generateMatchers(rule, dep, pos); 
		this.tagger = tagger;
		this.creation = new CEGCreation();
		this.lang = lang;
	}

	private URI getLocalFile(String fileName) throws URISyntaxException {
		Bundle bundle = FrameworkUtil.getBundle(PatternbasedCEGGenerator.class);
		return URI.createURI(bundle.getResource(fileName).toURI().toString());
	}
	
	public void createModel(CEGModel model, String text) throws SpecmateException {
		JCas tagResult = this.tagger.processText(text, this.lang);
		DependencyParsetree data = DependencyParsetree.generateFromJCas(tagResult);
		List<MatchResult> results = MatchUtil.evaluateRuleset(this.rules, data);
		
		LinkedList<CEGNode> nodes = new LinkedList<CEGNode>();
		
		for(MatchResult result: results) {
			if(result.isSuccessfulMatch() && result.hasSubmatch("Cause") && result.hasSubmatch("Effect")) {
				// Resolve Cause & Effect
				MatchResult cause = result.getSubmatch("Cause");
				MatchResult effect = result.getSubmatch("Effect");
				resolveCauseEffect(model, nodes,cause, effect);
			}
		}
	}
	
	private void resolveCauseEffect(CEGModel model, LinkedList<CEGNode> nodes, MatchResult cause, MatchResult effect) {
		DirectCause directCause = resolveCause(model, nodes, cause);
		resolveEffect(model, nodes, directCause, effect);
	}
	
	
	private DirectCause resolveCause(CEGModel model, LinkedList<CEGNode> nodes, MatchResult cause) {
		return null;
	}
	
	private void resolveEffect(CEGModel model, LinkedList<CEGNode> nodes, DirectCause directCause, MatchResult effect) {
		
	}
	
	private class DirectCause {
		public List<CEGNode> positiveCauses;
		public List<CEGNode> negativeCauses;
		public NodeType effectType;
		
		public DirectCause() {
			
		}
	}
}

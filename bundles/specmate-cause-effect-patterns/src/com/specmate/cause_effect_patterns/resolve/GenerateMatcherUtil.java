package com.specmate.cause_effect_patterns.resolve;

import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.ISetup;

import com.specmate.cause_effect_patterns.internal.SpecDSLStandaloneSetup;
import com.specmate.cause_effect_patterns.internal.specDSL.Model;
import com.specmate.cause_effect_patterns.internal.specDSL.Rule;
import com.specmate.cause_effect_patterns.parse.matcher.MatchRule;
import com.specmate.cause_effect_patterns.parse.matcher.MatcherBase;
import com.specmate.cause_effect_patterns.parse.matcher.MatcherException;
import com.specmate.cause_effect_patterns.resolve.util.RuleUtility;
import com.specmate.xtext.XTextException;
import com.specmate.xtext.XTextResourceProcessor;

public class GenerateMatcherUtil extends XTextResourceProcessor<MatchRule> {
	private static MatchRule resolveRule(Rule r) throws MatcherException {
		MatcherBase match = RuleUtility.transform(r);
		return new MatchRule(match, r.getName());	
	}

	@Override
	public ISetup getGrammar() {
		return new SpecDSLStandaloneSetup();
	}

	@Override
	public List<MatchRule> process(EList<EObject> data) throws XTextException {
		List<Rule> rules = data.stream()
		.filter(e -> e instanceof Model)
		.flatMap(m -> ((Model)m).getElements().stream())
		.filter(e -> e instanceof Rule)
		.map(r -> (Rule) r)
		.collect(Collectors.toList());
		List<MatchRule> result = new Vector<MatchRule>();
		for (Rule r:rules) {
			try {
				result.add(resolveRule(r));
			} catch (MatcherException e) {
				throw new XTextException(e.getMessage());
			}
		}
		
		return result;
	}
}

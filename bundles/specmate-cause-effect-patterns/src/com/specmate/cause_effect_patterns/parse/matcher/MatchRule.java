package com.specmate.cause_effect_patterns.parse.matcher;

import com.specmate.cause_effect_patterns.parse.DependencyParsetree;
import com.specmate.cause_effect_patterns.parse.matcher.MatcherBase;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;

public class MatchRule {
	public MatcherBase matcher;
	public String ruleName;
	
	public MatchRule(MatcherBase matcher, String name) {
		this.matcher = matcher;
		this.ruleName = name;
	}
	
	public MatchResult match(DependencyParsetree data, Token head) {
		MatchResult result = this.matcher.match(data, head);
		result.setRuleName(this.ruleName);
		return result;
	}
	
	@Override
	public String toString() {
		return "Match Rule: " + this.ruleName+"\n"+this.matcher;
	}
}

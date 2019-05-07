package com.specmate.cause_effect_patterns.parse.matcher;

import com.specmate.cause_effect_patterns.parse.matcher.MatcherBase;

public class MatchRule {
	public MatcherBase matcher;
	public String ruleName;
	
	public MatchRule(MatcherBase matcher, String name) {
		this.matcher = matcher;
		this.ruleName = name;
	}
	
	@Override
	public String toString() {
		return "Match Rule: " + this.ruleName+"\n"+this.matcher;
	}
}

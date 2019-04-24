package com.specmate.cause_effect_patterns.parse.matcher;

import com.specmate.cause_effect_patterns.parse.matcher.Matcher;

public class MatchRule {
	public Matcher matcher;
	public String ruleName;
	
	public MatchRule(Matcher matcher, String name) {
		this.matcher = matcher;
		this.ruleName = name;
	}
	
	@Override
	public String toString() {
		return "Match Rule: " + this.ruleName+"\n"+this.matcher;
	}
}

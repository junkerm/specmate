package org.xtext.specmate.resolve.matcher;

public class MatchRule {
	private Matcher matcher;
	private String ruleName;
	
	public MatchRule(Matcher matcher, String name) {
		this.matcher = matcher;
		this.ruleName = name;
	}
	
	@Override
	public String toString() {
		return "Match Rule: " + this.ruleName+"\n"+this.matcher;
	}
}

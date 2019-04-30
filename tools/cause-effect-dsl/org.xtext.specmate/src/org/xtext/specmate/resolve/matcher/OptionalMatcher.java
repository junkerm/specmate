package org.xtext.specmate.resolve.matcher;

public class OptionalMatcher extends Matcher {
	private Matcher subMatcher;
	public OptionalMatcher(Matcher subMatcher) {
		this.subMatcher = subMatcher;
	}
	
	@Override
	public String getRepresentation() {
		return "Optional ("+this.subMatcher+")";
	}

}

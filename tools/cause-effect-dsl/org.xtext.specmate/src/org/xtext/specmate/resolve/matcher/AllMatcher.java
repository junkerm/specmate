package org.xtext.specmate.resolve.matcher;

import java.util.Arrays;

public class AllMatcher extends Matcher{
	
	private Matcher[] matchers;
	public AllMatcher(Matcher...matchers) {
		this.matchers = matchers;
	}
	
	@Override
	public String getRepresentation() {
		return "All "+Arrays.toString(matchers);
	}
	
}

package org.xtext.specmate.resolve.matcher;

public class OrMatcher extends Matcher {
	public Matcher[] matchers;
	public OrMatcher(Matcher...matchers) {
		this.matchers = matchers;
	}
	
	@Override
	public String getRepresentation() {
		String out = "(";
		for (Matcher matcher : matchers) {
			if(out.length() != 1) {
				out+=" | ";
			}
			out+= matcher.getRepresentation();
		}
		return out+")";
	}
}

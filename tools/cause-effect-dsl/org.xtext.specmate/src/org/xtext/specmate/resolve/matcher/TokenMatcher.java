package org.xtext.specmate.resolve.matcher;

public class TokenMatcher extends Matcher {
	public TokenMatcher(String expr, String tag) {
		this.expression = expr;
		this.posTag = tag;
	}
	
	public TokenMatcher(String expr) {
		this(expr, null);
	}
	
	public String expression;
	public String posTag;

	@Override
	public String getRepresentation() {
		String expr = expression;
		if (expression.equals("*")) {
			expr = ".*";
		}
		expr = "\""+expr+"\"";
		
		if(posTag == null) {
			return "{"+expr+"}";	
		}
		return "{"+expr+", \""+posTag+"\"}";
	}
}

package com.specmate.nlp.matcher;

import org.apache.uima.jcas.tcas.Annotation;

public class CoveredTextMatcher implements IConstituentTreeMatcher {

	private String text;

	public  CoveredTextMatcher(String text) {
		this.text = text;
	}
	
	@Override
	public MatchResult match(Annotation constituent) {
		boolean result = constituent.getCoveredText().contentEquals(this.text);
		return new MatchResult(result);
	}

}

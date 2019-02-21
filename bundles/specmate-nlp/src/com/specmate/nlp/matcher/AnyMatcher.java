package com.specmate.nlp.matcher;

import org.apache.uima.jcas.tcas.Annotation;

public class AnyMatcher implements IConstituentTreeMatcher {

	@Override
	public MatchResult match(Annotation constituent) {
		return new MatchResult(true);
	}

}

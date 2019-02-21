package com.specmate.nlp.matcher;

import org.apache.uima.jcas.tcas.Annotation;

public interface IConstituentTreeMatcher {
	public MatchResult match(Annotation constituent);
}

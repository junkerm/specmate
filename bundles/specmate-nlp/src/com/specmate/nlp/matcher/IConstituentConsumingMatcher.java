package com.specmate.nlp.matcher;

import org.apache.uima.jcas.tcas.Annotation;

public interface IConstituentConsumingMatcher {

	boolean consume(Annotation currentAnnoation);

	MatchResult getMatchResult();

	void consumeEmpty();

}

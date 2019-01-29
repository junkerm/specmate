package com.specmate.nlp.matcher;

import java.util.Iterator;
import java.util.List;

import org.apache.uima.jcas.tcas.Annotation;

public class SequenceMatcher {

	private List<IConstituentConsumingMatcher> matchers;

	public SequenceMatcher(List<IConstituentConsumingMatcher> matchers) {
		this.matchers = matchers;
	}

	public MatchResult match(List<Annotation> sequence) {
		Iterator<IConstituentConsumingMatcher> mIt = matchers.iterator();
		Iterator<Annotation> aIt = sequence.iterator();

		IConstituentConsumingMatcher currentMatcher = mIt.next();
		Annotation currentAnnoation = aIt.next();
		
		MatchResult result = new MatchResult(true);

		while (true) {
			boolean matching = false;
			if(currentAnnoation!=null) {
				matching = currentMatcher.consume(currentAnnoation);
				if(matching) {
					if (aIt.hasNext()) {
						currentAnnoation = aIt.next();
					} else {
						currentAnnoation = null;
					}
				} else {
					if (currentMatcher.getMatchResult().isMatch()) {
						result.addMatchGroupsFrom(currentMatcher.getMatchResult());
						if (mIt.hasNext()) {
							currentMatcher = mIt.next();
						} else {
							return new MatchResult(false);
						}
					} else {
						return new MatchResult(false);
					}
				}
				
			} else {
				if (currentMatcher.getMatchResult().isMatch()) {
					result.addMatchGroupsFrom(currentMatcher.getMatchResult());
					if (mIt.hasNext()) {
						currentMatcher = mIt.next();
					} else {
						return result;
					}
				} else {
					return new MatchResult(false);
				}
			}
		}
	}
}

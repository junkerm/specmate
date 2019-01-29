package com.specmate.nlp.matcher;

import org.apache.uima.jcas.tcas.Annotation;

public class ExactlyOneConsumer implements IConstituentConsumingMatcher {

	private IConstituentTreeMatcher matcher;
	private boolean hasMatched = false;
	private boolean consumed = false;

	public ExactlyOneConsumer(IConstituentTreeMatcher matcher) {
		this.matcher = matcher;
	}
	
	@Override
	public boolean consume(Annotation annotation) {
		if(consumed){
			return false;
		}
		consumed=true;
		MatchResult result = matcher.match(annotation);
		hasMatched = result.isMatch();
		return hasMatched;
	}

	@Override
	public MatchResult getMatchResult() {
		return new MatchResult(hasMatched);
	}

	@Override
	public void consumeEmpty() {
		hasMatched=false;		
	}

}

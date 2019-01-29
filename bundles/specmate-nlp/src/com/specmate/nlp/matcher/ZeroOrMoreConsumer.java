package com.specmate.nlp.matcher;

import java.util.ArrayList;
import java.util.List;

import org.apache.uima.jcas.tcas.Annotation;

public class ZeroOrMoreConsumer implements IConstituentConsumingMatcher {

	private IConstituentTreeMatcher matcher;
	private List<Annotation> consumed = new ArrayList<>();
	private String name;

	public ZeroOrMoreConsumer(IConstituentTreeMatcher matcher, String name) {
		this.matcher = matcher;
		this.name=name;
	}
	
	public ZeroOrMoreConsumer(IConstituentTreeMatcher matcher) {
		this(matcher,null);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	@Override
	public boolean consume(Annotation annotation) {
		MatchResult result = matcher.match(annotation);
		boolean matched = result.isMatch();
		if(matched){
			consumed.add(annotation);
		}
		return matched;
	}

	@Override
	public MatchResult getMatchResult() {
		MatchResult result = new MatchResult(true);
		if(name!=null){
			result.addMatchGroup(name, consumed);
		}
		return result;
	}

	@Override
	public void consumeEmpty() {
		
	}

}

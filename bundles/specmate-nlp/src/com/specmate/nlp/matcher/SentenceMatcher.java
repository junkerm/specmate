package com.specmate.nlp.matcher;

import org.apache.uima.jcas.tcas.Annotation;

import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.Constituent;

public class SentenceMatcher implements IConstituentTreeMatcher {

	private IConstituentTreeMatcher subMatcher;

	public SentenceMatcher(IConstituentTreeMatcher subMatcher){
		this.subMatcher = subMatcher;
	}
	
	@Override
	public MatchResult match(Annotation annotation) {
		if(!(annotation instanceof Constituent)){
			return new MatchResult(false);
		}
		Constituent constituent = (Constituent)annotation;
		boolean isSentence = constituent.getConstituentType().contentEquals("S");
		if(!isSentence){
			return new MatchResult(false);
		} else {
			return subMatcher.match(annotation);
		}
	}

}

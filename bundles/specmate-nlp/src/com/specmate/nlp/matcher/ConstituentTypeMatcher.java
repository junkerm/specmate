package com.specmate.nlp.matcher;

import org.apache.uima.jcas.tcas.Annotation;

import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.Constituent;

public class ConstituentTypeMatcher implements IConstituentTreeMatcher {

	private String type;

	public ConstituentTypeMatcher(String type) {
		this.type=type;
	}
	
	@Override
	public MatchResult match(Annotation annotation) {
		if(!(annotation instanceof Constituent)){
			return new MatchResult(false);
		}
		Constituent constituent = (Constituent)annotation;
		boolean typeMatches = constituent.getConstituentType().equals(type);
		return new MatchResult(typeMatches);
	}

}

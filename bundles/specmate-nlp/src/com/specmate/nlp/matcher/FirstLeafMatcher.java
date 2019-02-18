package com.specmate.nlp.matcher;

import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.jcas.cas.FSArray;
import org.apache.uima.jcas.tcas.Annotation;

import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.Constituent;

public class FirstLeafMatcher implements IConstituentTreeMatcher{

	private IConstituentTreeMatcher subMatcher;
	public FirstLeafMatcher(IConstituentTreeMatcher subMatcher) {
		this.subMatcher = subMatcher;
	}
	
	@Override
	public MatchResult match(Annotation annotation) {
		if(!(annotation instanceof Constituent)){
			return new MatchResult(false);
		}
		Constituent constituent = (Constituent)annotation;
		while(constituent != null){
			FSArray children = constituent.getChildren();
			Annotation firstAnno = firstAnnotation(children);
			if(firstAnno instanceof Constituent){
				constituent = (Constituent)firstAnno;	
			}
			return subMatcher.match(firstAnno);
		}
		return new MatchResult(false);
	}
	
	private Annotation firstAnnotation(FSArray children){
		for(FeatureStructure fs : children){
			if(fs instanceof Annotation){
				return (Annotation)fs;
			}
		}
		return null;
	}

}

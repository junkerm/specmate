package com.specmate.nlp.matcher;

import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.jcas.tcas.Annotation;

import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.Constituent;

public class FirstChildMatcher implements IConstituentTreeMatcher{

	private IConstituentTreeMatcher subMatcher;
	public FirstChildMatcher(IConstituentTreeMatcher subMatcher) {
		this.subMatcher = subMatcher;
	}
	
	@Override
	public MatchResult match(Annotation annotation) {
		if(!(annotation instanceof Constituent)){
			return new MatchResult(false);
		}
		Constituent constituent = (Constituent)annotation;
		for(FeatureStructure fs: constituent.getChildren()){
			if(fs instanceof Annotation){
				return subMatcher.match((Annotation)fs);
			}
		}
		return new MatchResult(false);
	}

}

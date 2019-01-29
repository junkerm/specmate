package com.specmate.nlp.matcher;

import java.util.function.Function;

import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.jcas.cas.FSArray;
import org.apache.uima.jcas.tcas.Annotation;

import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.Constituent;

public class AncestorMatcher implements IConstituentTreeMatcher{

	private IConstituentTreeMatcher subMatcher;
	public AncestorMatcher(IConstituentTreeMatcher subMatcher) {
		this.subMatcher = subMatcher;
	}
	
	@Override
	public MatchResult match(Annotation annotation) {
		if(!(annotation instanceof Constituent)){
			return new MatchResult(false);
		}
		Constituent constituent = (Constituent)annotation;
		return iterateDepthFirst(constituent, a -> {
			return  subMatcher.match(a);
		});	
	}
	
	public MatchResult iterateDepthFirst(Constituent constituent, Function<Annotation,MatchResult> matchFunction){
		FSArray children = constituent.getChildren();
		for (FeatureStructure fs : children){
			if(fs instanceof Annotation){
				Annotation annotation = (Annotation)fs;
				MatchResult result = matchFunction.apply(annotation);				
				if(fs instanceof Constituent){
					iterateDepthFirst((Constituent)annotation, matchFunction);
					if(result.isMatch()){
						return result;
					}
				}
			}
		}
		return new MatchResult(false);
	}

}

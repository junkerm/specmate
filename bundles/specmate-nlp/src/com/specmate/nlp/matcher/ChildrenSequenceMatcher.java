package com.specmate.nlp.matcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.uima.jcas.tcas.Annotation;

import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.Constituent;

public class ChildrenSequenceMatcher implements IConstituentTreeMatcher {

	private SequenceMatcher matcher;

	public  ChildrenSequenceMatcher(SequenceMatcher matcher) {
		this.matcher = matcher;
	}
	
	@Override
	public MatchResult match(Annotation annotation) {
		if(!(annotation instanceof Constituent)){
			return matcher.match(Collections.emptyList());
		} else {
			Constituent constituent = (Constituent) annotation;
			List<Annotation> childAnnotations = new ArrayList<>();
			constituent.getChildren().forEach(c -> {
				if(c instanceof Annotation){
					childAnnotations.add((Annotation)c);
				}
			});
			return matcher.match(childAnnotations);
		}
	}

}

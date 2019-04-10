package com.specmate.nlp.dependency.matcher;

import com.specmate.nlp.dependency.DependencyData;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;

public class OptionMatcher extends Matcher{
	private Matcher[] options;
	
	public OptionMatcher(Matcher...options) {
		this.options = options;
	}
	
	@Override
	public MatchResult match(DependencyData data, Token head) {
		for(Matcher option: options) {
			MatchResult res = option.match(data, head);
			if(res.isSuccessfulMatch()) {
				res.addSubtree(super.match(data, head));
				return res;
			}
		}
		return MatchResult.unsuccessful();
	}
}

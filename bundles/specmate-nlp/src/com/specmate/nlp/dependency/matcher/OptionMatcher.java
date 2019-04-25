package com.specmate.nlp.dependency.matcher;

import com.specmate.nlp.dependency.DependencyParsetree;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;

/**
 * Matcher that matches to the first of its options.
 * Does not do any backtracking.
 * 
 * @author Dominik
 *
 */
public class OptionMatcher extends Matcher{
	private Matcher[] options;
	
	public OptionMatcher(Matcher...options) {
		this.options = options;
	}
	
	@Override
	public MatchResult match(DependencyParsetree data, Token head) {
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

package com.specmate.cause_effect_patterns.dependency.matcher;

import com.specmate.cause_effect_patterns.dependency.DependencyParsetree;
import com.specmate.cause_effect_patterns.dependency.matcher.MatchResult;
import com.specmate.cause_effect_patterns.dependency.matcher.Matcher;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;

/**
 * Matcher that matches to the first of its options.
 * Does not do any backtracking.
 * 
 * @author Dominik
 *
 */
public class OrMatcher extends Matcher{
	private Matcher[] options;
	
	public OrMatcher(Matcher...options) {
		this.options = options;
	}
	
	@Override
	public String getRepresentation() {
		String result = "(";
		for (Matcher matcher : this.getArcChildren()) {
			if(result.length() > 1) {
				result+=" | ";
			}
			result+= matcher.toString();
		}
		return result+")";
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

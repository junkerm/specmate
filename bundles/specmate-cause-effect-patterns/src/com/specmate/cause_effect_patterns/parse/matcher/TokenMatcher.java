package com.specmate.cause_effect_patterns.parse.matcher;

import java.util.Optional;

import com.specmate.cause_effect_patterns.parse.DependencyParsetree;
import com.specmate.cause_effect_patterns.parse.matcher.MatchResult;
import com.specmate.cause_effect_patterns.parse.matcher.Matcher;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;

/**
 * Matches to a single token that fits a given regular expression and has a given POS tag.
 * @author Dominik
 *
 */
public class TokenMatcher extends Matcher{
	String pattern;
	Optional<String> posTag;
	
	public TokenMatcher(String pattern, String posTag) {
		this(pattern);
		this.posTag = Optional.of(posTag);
	}
	
	public TokenMatcher(String pattern) {
		this.pattern = pattern;
		this.posTag = Optional.empty();
	}
	
	@Override
	public String getRepresentation() {
		String expr = pattern;
		if (pattern.equals("*")) {
			expr = ".*";
		}
		expr = "\""+expr+"\"";
		
		if(!posTag.isPresent()) {
			return "{"+expr+"}";	
		}
		return "{"+expr+", \""+posTag.get()+"\"}";
	}
	

	@Override
	public MatchResult match(DependencyParsetree data, Token head) {
		String tokenText = head.getCoveredText().trim();
		if(!tokenText.matches(this.pattern)) {
			return MatchResult.unsuccessful();
		}
		
		if(this.posTag.isPresent()) {
			if(!head.getPosValue().equals(this.posTag.get())) {
				return MatchResult.unsuccessful();
			}
		}
		return super.match(data, head);
	}
}

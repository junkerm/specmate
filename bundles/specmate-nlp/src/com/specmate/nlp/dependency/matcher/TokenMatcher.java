package com.specmate.nlp.dependency.matcher;

import java.util.Optional;

import com.specmate.nlp.dependency.DependencyParsetree;

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

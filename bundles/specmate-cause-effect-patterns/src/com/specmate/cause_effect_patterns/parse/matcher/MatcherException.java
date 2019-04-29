package com.specmate.cause_effect_patterns.parse.matcher;

import java.util.Set;

import com.specmate.cause_effect_patterns.parse.matcher.MatcherBase;
import com.specmate.cause_effect_patterns.parse.matcher.MatcherException;
import com.specmate.cause_effect_patterns.parse.matcher.SubtreeMatcher;

public class MatcherException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private MatcherException(String message) {
		super(message);
	}

	public static MatcherException illegalTwoParentNode(MatcherBase match, MatcherBase parentA, MatcherBase parentB) {
		return new MatcherException("Node "+match+" has multiple parents: "+parentA+", "+parentB);
	}

	public static MatcherException incompatibleMatchers(SubtreeMatcher treeMatcher, String from, String to) {
		return new MatcherException("Two Tree Matchers \""+treeMatcher+"\" have incompatible headdata "+"\""+from+"\" and \""+to+"\"");
	}

	public static MatcherException multipleMatchheads(Set<MatcherBase> headSet) {
		String message = "Rule has multiple match heads: ";
		for(MatcherBase m: headSet) {
			message += m+" "; 
		}
		return new MatcherException(message);
	}

	public static MatcherException circularRule(MatcherBase matcherA, MatcherBase matcherB) {
		String message = "Rule defines cicular dependency between "+matcherA+" and "+ matcherB;
		return new MatcherException(message);
	}

	public static MatcherException noMatchhead() {
		return new MatcherException("Rule has no match head");
	}
}

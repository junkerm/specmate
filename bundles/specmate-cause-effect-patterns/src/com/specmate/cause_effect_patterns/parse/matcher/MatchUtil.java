package com.specmate.cause_effect_patterns.parse.matcher;

import java.util.List;
import java.util.Vector;

import com.specmate.cause_effect_patterns.parse.DependencyParsetree;
import com.specmate.cause_effect_patterns.parse.matcher.MatchResult;
import com.specmate.cause_effect_patterns.parse.matcher.MatchUtil;
import com.specmate.cause_effect_patterns.parse.matcher.MatcherBase;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;

public class MatchUtil {
	/**
	 * Applies a list of matchers on the given dependency data object.
	 * 
	 * @param rules
	 * @param data
	 * @return A List of Results for each head of the dependency data object.
	 */
	public static List<MatchResult> evaluateRuleset(List<MatcherBase> rules, DependencyParsetree data) {
		Vector<MatchResult> result = new Vector<MatchResult>();
		for(Token head: data.getHeads()) {
			result.add(MatchUtil.evaluateRuleset(rules, data, head));
		}
		return result; 
	}
	
	/**
	 * Recursively applies the list of matchers on the given dependency data object starting at the head token.
	 * 
	 * @param rules
	 * @param data
	 * @param head
	 * @return
	 */
	public static MatchResult evaluateRuleset(List<MatcherBase> rules, DependencyParsetree data, Token head) {
		MatchResult result = MatchResult.unsuccessful();
		for(MatcherBase rule: rules) {
			result = rule.match(data, head);
			if(result.isSuccessfulMatch()) {
				for(String submatchName: result.getSubmatchNames()) {
					MatchResult sub = result.getSubmatch(submatchName);
					DependencyParsetree subData = sub.getMatchTree(); 
					Token subHead = subData.getHeads().stream().findFirst().get();
					MatchResult recursiveCall = evaluateRuleset(rules, subData, subHead);
					if(recursiveCall.isSuccessfulMatch()) {
						for( String subKey: recursiveCall.getSubmatchNames()) {
							sub.addSubmatch(subKey, recursiveCall.getSubmatch(subKey));	
						}
					}
				}
				break;
			}
		}
		
		return result;
	}
}

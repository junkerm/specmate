package com.specmate.cause_effect_patterns.parse.matcher;

import java.util.List;
import java.util.Vector;

import com.specmate.cause_effect_patterns.parse.DependencyParsetree;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;

public class MatchUtil {
	/**
	 * Applies a list of matchers on the given dependency data object.
	 * 
	 * @param rules
	 * @param data
	 * @return A List of Results for each head of the dependency data object.
	 */
	public static List<MatchResult> evaluateRuleset(List<MatchRule> rules, DependencyParsetree data) {
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
	public static MatchResult evaluateRuleset(List<MatchRule> rules, DependencyParsetree data, Token head) {
		MatchResult result = MatchResult.unsuccessful();
		for(MatchRule rule: rules) {
			result = rule.match(data, head);
			if(result.isSuccessfulMatch()) {
				for(String submatchName: result.getSubmatchNames()) {
					MatchResult sub = result.getSubmatch(submatchName);
					DependencyParsetree subData = sub.getMatchTree(); 
					Token subHead = subData.getHeads().stream().findFirst().get();
					MatchResult recursiveCall = evaluateRuleset(rules, subData, subHead);
					if(recursiveCall.isSuccessfulMatch()) {
						sub.setRuleName(recursiveCall.getRuleName());
						for( String subKey: recursiveCall.getSubmatchNames()) {
							MatchResult subRes =  recursiveCall.getSubmatch(subKey);
							subRes.setRuleName(recursiveCall.getRuleName());
							sub.addSubmatch(subKey, subRes);
						}
					}
				}
				break;
			}
		}
		
		return result;
	}
}

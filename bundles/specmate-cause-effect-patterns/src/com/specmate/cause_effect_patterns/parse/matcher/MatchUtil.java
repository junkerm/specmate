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
		return evaluateRuleset(rules, data, head, 0);
	}
		
	private static MatchResult evaluateRuleset(List<MatchRule> rules, DependencyParsetree data, Token head, int ruleOffset) {
		MatchResult result = MatchResult.unsuccessful();
		
		List<MatchRule> offsetedRules = rules.subList(ruleOffset, rules.size());
		for(MatchRule rule: offsetedRules) {
			result = rule.match(data, head);
			if(result.isSuccessfulMatch()) {
				for(String submatchName: result.getSubmatchNames()) {
					MatchResult sub = result.getSubmatch(submatchName);
					DependencyParsetree subData = sub.getMatchTree();
					Token subHead = subData.getHeads().stream().findFirst().get();
					
					MatchResult recursiveCall;
					if(subData.getTreeFragmentText().equals(data.getTreeFragmentText()) && subHead.equals(head)) {
						int newOffset = rules.indexOf(rule) + 1;
						recursiveCall = evaluateRuleset(rules, subData, subHead, newOffset);
					} else {
						recursiveCall = evaluateRuleset(rules, subData, subHead);
					}
					
					if(recursiveCall.isSuccessfulMatch()) {
						sub.setRuleName(recursiveCall.getRuleName());
						for( String subKey: recursiveCall.getSubmatchNames()) {
							MatchResult subRes =  recursiveCall.getSubmatch(subKey);
							if(!subRes.hasRuleName() ) {
								subRes.setRuleName(recursiveCall.getRuleName());
							}
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

package com.specmate.nlp.dependency.matcher;

import java.util.List;
import java.util.Vector;

import com.specmate.nlp.dependency.DependencyData;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;

public class MatchUtil {
	public static List<MatchResult> evaluateRuleset(List<Matcher> rules, DependencyData data) {
		Vector<MatchResult> out = new Vector<MatchResult>();
		for(Token head: data.getHeads()) {
			out.add(MatchUtil.evaluateRuleset(rules, data, head));
		}
		return out; 
	}
	
	public static MatchResult evaluateRuleset(List<Matcher> rules, DependencyData data, Token head) {
		MatchResult out = MatchResult.unsuccessful();
		for(Matcher rule: rules) {
			out = rule.match(data, head);
			if(out.isSuccessfulMatch()) {
				for(String submatchName: out.getSubmatchNames()) {
					MatchResult sub = out.getSubmatch(submatchName);
					DependencyData subData = sub.getMatchTree(); 
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
		
		return out;
	}
}

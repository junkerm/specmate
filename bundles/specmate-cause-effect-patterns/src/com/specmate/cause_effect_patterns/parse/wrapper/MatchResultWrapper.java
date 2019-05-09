package com.specmate.cause_effect_patterns.parse.wrapper;

import com.specmate.cause_effect_patterns.parse.matcher.MatchResult;

public class MatchResultWrapper {
	public static class SubtreeNames {
		public static final String CAUSE = "Cause";
		public static final String EFFECT ="Effect";
		public static final String PART_A = "PartA";
		public static final String PART_B = "PartB";
		public static final String TMP = "TMP";
		public static final String HEAD = "Head";
		public static final String CONDITION ="Condition";
		public static final String VARIABLE = "Variable";
		public static final String VERB = "Verb";
		public static final String OBJECT = "Object";
	}
	
	public static class RuleNames {
		public static final String CONDITION = "Condition";
		public static final String CONJUNCTION = "Conjunction";
		public static final String NEGATION = "Negation";
		public static final String CONDITION_VARIABLE = "CondVar";
		public static final String VERB_OBJECT = "VerbObject";
		public static final String XOR = "_XOR";
		public static final String NOR = "_NOR";
		public static final String OR = "_OR";
		public static final String AND = "_AND";
			
	}
	
	public MatchResult result;
	
	public MatchResultWrapper(MatchResult result) {
		this.result = result;
	}
	
	private MatchResultWrapper getFromSubtree(String subtree) {
		return new MatchResultWrapper(this.result.getSubmatch(subtree));
	}
	
	public boolean isSucessfull() {
		return this.result.isSuccessfulMatch();
	}
	
	public boolean isCondition() {
		boolean name = this.result.hasRuleName() && this.result.getRuleName().contains(RuleNames.CONDITION);
		boolean subMatches = this.result.hasSubmatch(SubtreeNames.CAUSE) && this.result.hasSubmatch(SubtreeNames.EFFECT);
		return name && subMatches && isSucessfull();
	}
	
	public boolean isConjunction() {
		boolean name = this.result.hasRuleName() && this.result.getRuleName().contains(RuleNames.CONJUNCTION);
		boolean subMatches = this.result.hasSubmatch(SubtreeNames.PART_A) && this.result.hasSubmatch(SubtreeNames.PART_B);
		return name && subMatches && isSucessfull();
	}
	
	public boolean isAndConjunction() {
		boolean name = this.result.hasRuleName() && this.result.getRuleName().contains(RuleNames.AND);
		return isConjunction() && name;
	}
	
	public boolean isOrConjunction() {
		boolean name = this.result.hasRuleName() && this.result.getRuleName().contains(RuleNames.OR);
		return isConjunction() && name;
	}
	
	public boolean isXorConjunction() {
		boolean name = this.result.hasRuleName() && this.result.getRuleName().contains(RuleNames.XOR);
		return isConjunction() && name;
	}
	
	public boolean isNorConjunction() {
		boolean name = this.result.hasRuleName() && this.result.getRuleName().contains(RuleNames.NOR);
		return isConjunction() && name;
	}
	
	public boolean isNegation() {
		boolean name = this.result.hasRuleName() && this.result.getRuleName().contains(RuleNames.NEGATION);
		boolean subMatches = this.result.hasSubmatch(SubtreeNames.HEAD);
		return name && subMatches && isSucessfull();
	}
	
	public boolean isConditionVarible() {
		boolean name = this.result.hasRuleName() && this.result.getRuleName().contains(RuleNames.CONDITION_VARIABLE);
		boolean subMatches = this.result.hasSubmatch(SubtreeNames.VARIABLE) && this.result.hasSubmatch(SubtreeNames.CONDITION);
		return name && subMatches && isSucessfull();
	}
	
	public boolean isVerbObject() {
		boolean name = this.result.hasRuleName() && this.result.getRuleName().contains(RuleNames.VERB_OBJECT);
		boolean subMatches = this.result.hasSubmatch(SubtreeNames.VERB) && this.result.hasSubmatch(SubtreeNames.OBJECT);
		return name && subMatches && isSucessfull();
	}
	
	public MatchResultWrapper getFirstArgument() {
		if(isCondition()) {
			return this.getFromSubtree(SubtreeNames.CAUSE);
		} else if(isConjunction()) {
			return this.getFromSubtree(SubtreeNames.PART_A);
		} else if(isNegation()) {
			return this.getFromSubtree(SubtreeNames.HEAD);
		} else if(isConditionVarible()) {
			return this.getFromSubtree(SubtreeNames.VARIABLE);
		} else if(isVerbObject()) {
			return this.getFromSubtree(SubtreeNames.VERB);
		}
		
		return null;
	}
	
	public MatchResultWrapper getSecondArgument() {
		if(isCondition()) {
			return this.getFromSubtree(SubtreeNames.EFFECT);
		} else if(isConjunction()) {
			return this.getFromSubtree(SubtreeNames.PART_B);
		} else if(isConditionVarible()) {
			return this.getFromSubtree(SubtreeNames.CONDITION);
		}  else if(isVerbObject()) {
			return this.getFromSubtree(SubtreeNames.OBJECT);
		}
		return null;
	}
}

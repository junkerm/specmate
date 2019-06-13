package com.specmate.cause_effect_patterns.parse.wrapper;

import com.specmate.cause_effect_patterns.parse.matcher.MatchResult;

public class MatchResultWrapper {
	public static class SubtreeNames {
		public static final String CONDITIONAL = "Conditional";
		public static final String LIMIT = "Limit";
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
		public static final String PREPOSITION = "Preposition";
	}
	
	public static class RuleNames {
		public static final String LIMITED_CONDITION = "LimitedCondition";
		public static final String CONDITION = "Condition";
		public static final String CONJUNCTION = "Conjunction";
		public static final String NEGATION = "Negation";
		public static final String CONDITION_VARIABLE = "CondVar";
		public static final String VERB_OBJECT = "VerbObject";
		public static final String VERB_PREPOSITION = "VerbPreposition";
		public static final String XOR = "_XOR";
		public static final String NOR = "_NOR";
		public static final String OR = "_OR";
		public static final String AND = "_AND";
	}
	
	public static enum RuleType {
		LIMITED_CONDITION,
		CONDITION,
		CONJUNCTION_AND,
		CONJUNCTION_OR,
		CONJUNCTION_NOR,
		CONJUNCTION_XOR,
		NEGATION,
		COND_VAR,
		VERB_OBJECT,
		VERB_PREPOSITION;
		
		
		public int getPriority() {
			switch(this) {
			case LIMITED_CONDITION:
				return 0;
			case CONDITION:
				return 1;
			case CONJUNCTION_XOR:
				return 2;
			case CONJUNCTION_NOR:
				return 3;
			case CONJUNCTION_OR:
				return 4;
			case CONJUNCTION_AND:
				return 5;
			case COND_VAR:
				return 6;
			case VERB_OBJECT:
				return 7;
			case VERB_PREPOSITION:
				return 8;
			default: // Negation should not be moved
				return -1;
			}
			
		}
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
	
	public boolean isLimitedCondition() {
		boolean name = this.result.hasRuleName() && this.result.getRuleName().contains(RuleNames.LIMITED_CONDITION);
		boolean subMatches = this.result.hasSubmatch(SubtreeNames.LIMIT) && this.result.hasSubmatch(SubtreeNames.CONDITIONAL);
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
	
	public boolean isVerbPreposition() {
		boolean name = this.result.hasRuleName() && this.result.getRuleName().contains(RuleNames.VERB_PREPOSITION);
		boolean subMatches = this.result.hasSubmatch(SubtreeNames.VERB) && this.result.hasSubmatch(SubtreeNames.PREPOSITION);
		return name && subMatches && isSucessfull();
	}
	
	public int getArgumentCount() {
		if(isCondition() || isLimitedCondition() || isConjunction()) {
			return 2;
		} if(isNegation()) {
			return 1;
		}
		return 0;
	}
	
	private String getFirstArgumentName() {
		if(isLimitedCondition()) {
			return SubtreeNames.LIMIT;
		} else if(isCondition()) {
			return SubtreeNames.CAUSE;
		} else if(isConjunction()) {
			return SubtreeNames.PART_A;
		} else if(isNegation()) {
			return SubtreeNames.HEAD;
		} else if(isConditionVarible()) {
			return SubtreeNames.VARIABLE;
		} else if(isVerbObject()) {
			return SubtreeNames.VERB;
		} else if(isVerbPreposition()) {
			return SubtreeNames.VERB;
		}
		
		return null;
	}
	
	public MatchResultWrapper getFirstArgument() {
		String name = getFirstArgumentName();
		if(name != null) {
			return this.getFromSubtree(name);
		}
		return null;
	}
	
	private String getSecondArgumentName() {
		if(isLimitedCondition()) {
			return SubtreeNames.CONDITIONAL;
		} else if(isCondition()) {
			return SubtreeNames.EFFECT;
		} else if(isConjunction()) {
			return SubtreeNames.PART_B;
		} else if(isConditionVarible()) {
			return SubtreeNames.CONDITION;
		}  else if(isVerbObject()) {
			return SubtreeNames.OBJECT;
		} else if(isVerbPreposition()) {
			return SubtreeNames.PREPOSITION;
		}
		return null;
	}
	
	public MatchResultWrapper getSecondArgument() {
		String name = getSecondArgumentName();
		if(name != null) {
			return this.getFromSubtree(name);
		}
		return null;
	}
	
	public void leftSwap() {
		if(getArgumentCount() != 2) {
			return;
		}
		
		String leftName = getFirstArgumentName();
		String rightName = getSecondArgumentName();
		MatchResultWrapper left = getFirstArgument();
		MatchResultWrapper right = getSecondArgument();
		
		String childLeftName = left.getFirstArgumentName();
		String childRightName = left.getSecondArgumentName();
		MatchResultWrapper childLeft = left.getFirstArgument();
		MatchResultWrapper childRight = left.getSecondArgument();
		// Swap
		this.result.clearSubmatches();
		// left' (childLeftName) 	= childLeft
		this.result.addSubmatch(childLeftName, childLeft.result);
		// right'(childRightName) 	= left
		this.result.addSubmatch(childRightName, left.result);

		left.result.clearSubmatches();
		// childLeft' (leftName) 	= childRight
		left.result.addSubmatch(leftName, childRight.result);
		// childRight'(rightName)	= right
		left.result.addSubmatch(rightName, right.result);
		
		// Update type
		String name		= this.result.getRuleName();
		String childName= left.result.getRuleName();
		this.result.setRuleName(childName);
		left.result.setRuleName(name);
	}

	public void rightSwap() {
		if(getArgumentCount() != 2) {
			return;
		}
		
		String leftName = getFirstArgumentName();
		String rightName = getSecondArgumentName();
		MatchResultWrapper left = getFirstArgument();
		MatchResultWrapper right = getSecondArgument();
		
		String childLeftName = right.getFirstArgumentName();
		String childRightName = right.getSecondArgumentName();
		MatchResultWrapper childLeft = right.getFirstArgument();
		MatchResultWrapper childRight = right.getSecondArgument();
		// Swap
		this.result.clearSubmatches();
		// left' (childLeftName) = right
		this.result.addSubmatch(childLeftName, right.result);
		// right'(childRightName)= childRight
		this.result.addSubmatch(childRightName, childRight.result);
		
		right.result.clearSubmatches();
		// childLeft'(left)		 = left
		right.result.addSubmatch(leftName, left.result);
		// childRight'(right)	 = childLeft
		right.result.addSubmatch(rightName, childLeft.result);
		
		// Update type
		String name		= this.result.getRuleName();
		String childName= right.result.getRuleName();
		this.result.setRuleName(childName);
		right.result.setRuleName(name);
	}
	
	public RuleType getType() {
		if(isLimitedCondition()) {
			return RuleType.LIMITED_CONDITION;
		} else if(isCondition()) {
			return RuleType.CONDITION;
		} else if(isAndConjunction()) {
			return RuleType.CONJUNCTION_AND;
		} else if(isOrConjunction()) {
			return RuleType.CONJUNCTION_OR;
		} else if(isXorConjunction()) {
			return RuleType.CONJUNCTION_XOR;
		} else if(isNorConjunction()) {
			return RuleType.CONJUNCTION_NOR;
		} else if(isNegation()) {
			return RuleType.NEGATION;
		} else if(isVerbObject()) {
			return RuleType.VERB_OBJECT;
		} else if(isConditionVarible()) {
			return RuleType.COND_VAR;
		}
		
		return null;
	}
}

package com.specmate.objectif.resolve.rule;

import java.util.Optional;

public class BusinessRuleNode extends ObjectifNode {
	private ObjectifNode cause;
	private ObjectifNode effect;
	private Optional<ObjectifNode> alternative;
	
	public BusinessRuleNode(ObjectifNode cause, ObjectifNode effect, ObjectifNode alternative) {
		this(cause, effect);
		this.alternative = Optional.of(alternative);
	}
	
	public BusinessRuleNode(ObjectifNode cause, ObjectifNode effect) {
		this.cause = cause;
		this.effect = effect;
		this.alternative = Optional.empty();
	}

	@Override
	void generateCEG() {
		this.cause.generateCEG();
		this.effect.generateCEG();
		if(this.alternative.isPresent()) {
			this.alternative.get().generateCEG();
		}
	}
	
	@Override
	public String toString() {
		String result = "if ";
		if(this.cause instanceof LiteralNode) {
			result += "("+this.cause+")";
		} else {
			result += this.cause;
		}
		result += " { "+this.effect+" }"; 
		if(this.alternative.isPresent()) {
			result += " else { "+this.alternative.get()+" }";
		}
		return result;
	}

	public ObjectifNode getCause() {
		return this.cause;
	}
	
	public ObjectifNode getEffect() {
		return this.effect;
	}
	
	public boolean hasAlternative() {
		return this.alternative.isPresent();
	}
	
	public ObjectifNode getAlternative() {
		return this.alternative.get();
	}
}

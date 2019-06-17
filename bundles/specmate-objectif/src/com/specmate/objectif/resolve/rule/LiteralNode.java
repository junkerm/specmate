package com.specmate.objectif.resolve.rule;

public class LiteralNode extends ObjectifNode {
	String[] literal;
	
	public LiteralNode(String[] content) {
		this.literal = content;
	}
	
	
	@Override
	void generateCEG() {
		// TODO Auto-generated method stub

	}

	@Override
	public String toString() {
		return getContent();
	}
	
	public String getContent() {
		return String.join(" ", this.literal);
	}
}

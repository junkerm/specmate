package com.specmate.nlp.dependency.matcher;

import java.util.HashMap;
import java.util.Map;

public class MatchResult {
	private boolean isSuccessfulMatch;
	private Map<String, MatchSubtree> subtrees;
	private MatchSubtree unmatched;
	
	
	private MatchResult(boolean success, MatchSubtree unmatched) {
		this.setUnmatched(unmatched);
		this.isSuccessfulMatch = success;
		this.subtrees = new HashMap<String, MatchSubtree>();
		
	}
	
	private MatchResult(boolean success) {
		this(success, new MatchSubtree());
	}
	
	private MatchResult() {
		this(false);
	}
	
	public boolean isSuccessfulMatch() {
		return isSuccessfulMatch;
	}
	
	public static MatchResult unsuccessful() {
		return new MatchResult();
	}

	public static MatchResult success(MatchSubtree unmatched) {
		MatchResult out = MatchResult.success();
		out.getUnmatched().addSubtree(unmatched);
		return out;
	}
	
	public static MatchResult success() {
		MatchResult out = new MatchResult(true);
		return out;
	}
	
	public void addSubresult(MatchResult subresult) {
		for(String subtreeID: subresult.subtrees.keySet()) {
			this.subtrees.put(subtreeID, subresult.subtrees.get(subtreeID));
		}
		this.isSuccessfulMatch = this.isSuccessfulMatch() && subresult.isSuccessfulMatch();
		this.getUnmatched().addSubtree(subresult.getUnmatched());
	}

	public MatchSubtree getUnmatched() {
		return unmatched;
	}

	public void setUnmatched(MatchSubtree unmatched) {
		this.unmatched = unmatched;
	}

	public void setSubtree(String subtreeName, MatchSubtree subtree) {
		this.subtrees.put(subtreeName, subtree);
	}
}

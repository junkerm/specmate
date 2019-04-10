package com.specmate.nlp.dependency.matcher;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.specmate.nlp.dependency.DependencyData;

public class MatchResult {
	private boolean isSuccessfulMatch;
	private Map<String, MatchResult> submatch;
	private DependencyData matchTree;
	
	
	private MatchResult(boolean success, DependencyData matchTree) {
		this.matchTree = matchTree;
		this.isSuccessfulMatch = success;
		this.submatch = new HashMap<String, MatchResult>();
	}
	
	private MatchResult(boolean success) {
		this(success, new DependencyData());
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

	public static MatchResult success(DependencyData matchTree) {
		MatchResult out = new MatchResult(true, matchTree);
		return out;
	}
	
	public static MatchResult success() {
		MatchResult out = new MatchResult(true);
		return out;
	}
	
	public void addSubtree(MatchResult subtree) {
		for(String subtreeID: subtree.submatch.keySet()) {
			this.submatch.put(subtreeID, subtree.submatch.get(subtreeID));
		}
		this.isSuccessfulMatch = this.isSuccessfulMatch() && subtree.isSuccessfulMatch();
		this.getMatchTree().addSubtree(subtree.getMatchTree());
	}

	public DependencyData getMatchTree() {
		return matchTree;
	}

	public void clearMatchTree() {
		this.matchTree = new DependencyData();
	}

	public void addSubmatch(String subtreeName, MatchResult submatch) {
		this.isSuccessfulMatch = this.isSuccessfulMatch && submatch.isSuccessfulMatch;
		this.submatch.put(subtreeName, submatch);
	}

	public boolean hasSubmatch(String subtreeName) {
		return this.submatch.containsKey(subtreeName);
	}
	
	public MatchResult getSubmatch(String subtreeName) {
		return this.submatch.get(subtreeName);
	}
	
	public Set<String> getSubmatchNames() {
		return this.submatch.keySet();
	}
}

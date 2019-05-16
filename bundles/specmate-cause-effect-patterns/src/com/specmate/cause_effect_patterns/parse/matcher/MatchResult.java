package com.specmate.cause_effect_patterns.parse.matcher;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Vector;

import com.specmate.cause_effect_patterns.parse.DependencyParsetree;

/**
 * Class storing the result of a dependency matching operation.
 * Has a field indicating if the match was successfull.
 * If it was the field matchTree will contain the subtree that was 
 * matched and submatch will contain the results of any {@link SubtreeMatcher} 
 * that was part of the matching process. The key of this map is the name
 * specified in the Subtreematcher class.
 * 
 * @author Dominik
 *
 */
public class MatchResult {
	private boolean isSuccessfulMatch;
	private Map<String, MatchResult> submatch;
	private DependencyParsetree matchTree;
	private Optional<String> ruleName;
	
	private MatchResult(boolean success, DependencyParsetree matchTree) {
		this.matchTree = matchTree;
		this.isSuccessfulMatch = success;
		this.submatch = new HashMap<String, MatchResult>();
		this.ruleName = Optional.empty();
	}
	
	private MatchResult(boolean success) {
		this(success, new DependencyParsetree());
	}
	
	private MatchResult() {
		this(false);
	}
	
	public void setRuleName(String ruleName) {
		this.ruleName = Optional.of(ruleName);
		for(String sub: this.submatch.keySet()) {
			MatchResult subRes = this.submatch.get(sub);
			if(!subRes.hasRuleName()) {
				subRes.setRuleName(ruleName);
			}
		}
	}
	
	public boolean hasRuleName() {
		return this.ruleName.isPresent();
	}
	
	public String getRuleName() {
		return this.ruleName.get();
	}
	
	public boolean isSuccessfulMatch() {
		return isSuccessfulMatch;
	}
	
	public static MatchResult unsuccessful() {
		return new MatchResult();
	}

	public static MatchResult success(DependencyParsetree matchTree) {
		MatchResult result = new MatchResult(true, matchTree);
		return result;
	}
	
	public static MatchResult success() {
		return new MatchResult(true);
	}
	
	public void addSubtree(MatchResult subtree) {
		for(String subtreeID: subtree.submatch.keySet()) {
			this.submatch.put(subtreeID, subtree.submatch.get(subtreeID));
		}
		if(subtree.submatch.size() > 0) {
			this.mergePrefixSubmatches();
		}
		
		this.isSuccessfulMatch = this.isSuccessfulMatch() && subtree.isSuccessfulMatch();
		this.getMatchTree().addSubtree(subtree.getMatchTree());
	}

	
	private void mergePrefixSubmatches() {
		Vector<String> keys = new Vector<String>(this.submatch.keySet());
		Collections.sort(keys, (s1,s2) -> s1.length() - s2.length());
		for (int i = 0; i < keys.size(); i++) {
			String keyA = keys.get(i);
			for (int j = i+1; j < keys.size(); j++) {
				String keyB = keys.get(j);
				if(keyB.startsWith(keyA+"_")) {
					//merge keys
					MatchResult resA = this.submatch.get(keyA);
					MatchResult resB = this.submatch.get(keyB);
					resA.addSubtree(resB);
					keys.remove(j);
					this.submatch.remove(keyB);
					j--;
				}
			}
		}
	}
	
	public DependencyParsetree getMatchTree() {
		return matchTree;
	}

	public void clearMatchTree() {
		this.matchTree = new DependencyParsetree();
	}

	public void addSubmatch(String subtreeName, MatchResult submatch) {
		this.isSuccessfulMatch = this.isSuccessfulMatch && submatch.isSuccessfulMatch;
		this.submatch.put(subtreeName, submatch);
		if(this.submatch.size() > 1) {
			this.mergePrefixSubmatches();
		}
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

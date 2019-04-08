package com.specmate.nlp.dependency.matcher;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Vector;

import org.apache.uima.internal.util.IntHashSet;

import com.specmate.nlp.dependency.DependencyData;
import com.specmate.nlp.dependency.DependencyNode;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.dependency.Dependency;

public abstract class Matcher {
	
	private HashMap<String, List<Matcher>> arcs;
	public Matcher() {
		this.arcs = new HashMap<String, List<Matcher>>();
	}
	
	public void arcTo(Matcher to, String DependencyTag) {
		if (!this.arcs.containsKey(DependencyTag)) {
			this.arcs.put(DependencyTag, new Vector<Matcher>());
		}
		this.arcs.get(DependencyTag).add(to);
	}
	
	public MatchResult match(DependencyData data, Token head) {
		DependencyNode dependencies = data.getDependencies(head);
		MatchResult out = MatchResult.success();
		
		for(Entry<String, List<Matcher>> entry: this.arcs.entrySet()) {
			String depTag = entry.getKey();
			List<Matcher> matchers = entry.getValue();
			List<Dependency> candidates = dependencies.getDependenciesFromTag(depTag);
			MatchResult match = this.matchChildren(data, matchers, candidates);
			
			if(!match.isSuccessfulMatch()) {
				return MatchResult.unsuccessful();
			}
			out.addSubresult(match);
		}
		return out;
	}
	
	private MatchResult matchChildren(DependencyData data, List<Matcher> matchers, List<Dependency> candidates) {
		int matchSize = matchers.size();
		int candidateSize = candidates.size();
		if (matchSize > candidateSize) {
			return MatchResult.unsuccessful();
		}
		
		MatchResult[][] matchResults = new MatchResult[matchers.size()][candidates.size()];
		IntHashSet availableCandidates = new IntHashSet(candidates.size());
		int[] matching = new int[matchers.size()];
		
		for(int i=0; i<candidates.size(); i++) {
			// We add 1 to all elements because IntHashSet does not allow any element to be 0.
			availableCandidates.add(i+1);
			if(i < matchSize) {
				matching[i] = 0;
			}
		}
		
		int currentMatcherIndex = 0;
		
		while(currentMatcherIndex < matchSize) {
			Matcher currentMatcher = matchers.get(currentMatcherIndex);
			boolean unmatched = true;
			
			for(int i = matching[currentMatcherIndex]; i < candidateSize; i++) {
				if (availableCandidates.contains(i+1)) {
					if(matchResults[currentMatcherIndex][i] == null) {
						Token subHead = candidates.get(i).getGovernor();
						matchResults[currentMatcherIndex][i] = currentMatcher.match(data, subHead);
					}
					
					if(matchResults[currentMatcherIndex][i].isSuccessfulMatch()) {
						unmatched = false;
						currentMatcherIndex++;
						matching[currentMatcherIndex] = 0;
						availableCandidates.remove(i+1);
						break;
					} else {
						matching[currentMatcherIndex]++;
					}
				}
			}
			
			if (unmatched) {
				if (currentMatcherIndex > 0) {
					currentMatcherIndex--;
					availableCandidates.add(1+matching[currentMatcherIndex]);
					matching[currentMatcherIndex]++;
				} else {
					return MatchResult.unsuccessful();
				}
			}
		}
		
		MatchSubtree unmatched = new MatchSubtree();
		for(int remainElement: availableCandidates.toIntArray()) {
			// Invert the adding 1 from before
			int remain = remainElement - 1;
			unmatched.addSubtree(MatchSubtree.getSubtree(data, candidates.get(remain).getGovernor()));
		}
		
		MatchResult out = MatchResult.success(unmatched);
		for (int i = 0; i < matching.length; i++) {
			out.addSubresult(matchResults[i][matching[i]]);
			// Optional add to discarded Tree 
		}
		return out;
	}
}

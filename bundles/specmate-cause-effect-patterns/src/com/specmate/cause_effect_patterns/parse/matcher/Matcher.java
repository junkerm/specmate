package com.specmate.cause_effect_patterns.parse.matcher;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;

import org.apache.uima.internal.util.IntHashSet;

import com.google.common.base.Optional;
import com.google.common.collect.ArrayListMultimap;
import com.specmate.cause_effect_patterns.parse.DependencyNode;
import com.specmate.cause_effect_patterns.parse.DependencyParsetree;
import com.specmate.cause_effect_patterns.parse.matcher.MatchResult;
import com.specmate.cause_effect_patterns.parse.matcher.Matcher;
import com.specmate.cause_effect_patterns.parse.matcher.MatcherException;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.dependency.Dependency;

/**
 * Abstract Matcher Parentclass for extention.
 * Provides methods for adding additional matchers via dependency arcs and
 * a generic match method that calls the match method on the arcs.
 * Use super.match to get the desired behavior.
 * 
 * @author Dominik
 *
 */
public abstract class Matcher {
	private Optional<Matcher> parent;
	private Set<Matcher> children;
	private ArrayListMultimap<String, Matcher> arcs;
	
	public Matcher() {
		this.children = new HashSet<Matcher>();
		this.parent = Optional.absent();
		this.arcs = ArrayListMultimap.create();
	}
	
	public void arcTo(Matcher to, String dependencyTag) throws MatcherException {
		if(to.parent.isPresent() && this.parent.isPresent()) {
			if(!to.parent.get().equals(this.parent.get())) {
				throw MatcherException.illegalTwoParentNode(to, this, to.parent.get());
			}
		}
		
		if (to.children.contains(this)) {
			throw MatcherException.circularRule(this, to);
		}
		this.children.add(to);
		this.children.addAll(to.children);
		
		this.arcs.put(dependencyTag, to);
	}
	
	public Collection<Matcher> getArcChildren() {
		return this.arcs.values();
	}
	
	@Override
	public String toString() {
		String result = "";
		for (Entry<String,Matcher> arc : arcs.entries()) {
			result+= this.getRepresentation() +" -- " + arc.getKey()+" --> "+arc.getValue().getRepresentation()+"\n"+arc.getValue();
		}
		return result.trim();
	}
	
	public String getRepresentation() {
		return "Matcher";
	}

	public List<MatchResult> match(DependencyParsetree data) {
		Vector<MatchResult> result = new Vector<MatchResult>();
		for (Token head: data.getHeads()) {
			result.add(this.match(data, head));
		}
		return result;
	}
	
	public MatchResult match(DependencyParsetree data, Token head) {
		DependencyNode dependencies = data.getDependencyNode(head);
		MatchResult result = MatchResult.success();
		
		if(dependencies == null && !this.arcs.isEmpty()) {
			return MatchResult.unsuccessful();
		}
		
		
		for(String depTag: this.arcs.keySet()) {
			List<Matcher> matchers = this.arcs.get(depTag);
			List<Dependency> candidates = dependencies.getDependenciesFromTag(depTag);
			MatchResult match = this.matchChildren(data, matchers, candidates);
			
			if(!match.isSuccessfulMatch()) {
				return MatchResult.unsuccessful();
			}
			result.addSubtree(match);
		}
		
		// Add remaining unmatched Subtrees
		if(dependencies != null) {
			Set<String> keys = dependencies.getKeySet();
			keys.removeAll(this.arcs.keySet());
			for(String key: keys) {
				List<Dependency> deps = dependencies.getDependenciesFromTag(key);
				for(Dependency dep: deps) {
					DependencyParsetree rem = DependencyParsetree.getSubtree(data, dep.getDependent());
					result.getMatchTree().addSubtree(rem, dep);
				}
			}
		}
		return result;
	}
	
	private MatchResult matchChildren(DependencyParsetree data, List<Matcher> matchers, List<Dependency> candidates) {
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
						Token subHead = candidates.get(i).getDependent();
						matchResults[currentMatcherIndex][i] = currentMatcher.match(data, subHead);
					}
					
					if(matchResults[currentMatcherIndex][i].isSuccessfulMatch()) {
						unmatched = false;
						matching[currentMatcherIndex] = 0;
						currentMatcherIndex++;
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
		
		DependencyParsetree unmatched = new DependencyParsetree(data.getTokenOrder());
		for(int remainElement: availableCandidates.toIntArray()) {
			// Invert the adding 1 from before
			int remain = remainElement - 1;
			Dependency dep = candidates.get(remain);
			unmatched.addSubtree(DependencyParsetree.getSubtree(data, dep.getGovernor()),dep);
		}
		
		MatchResult result = MatchResult.success(unmatched);
		for (int i = 0; i < matching.length; i++) {
			result.addSubtree(matchResults[i][matching[i]]);
			// Optional add to discarded Tree 
		}
		return result;
	}
}

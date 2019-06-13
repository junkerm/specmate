package com.specmate.cause_effect_patterns.parse.matcher;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.Vector;

import org.apache.uima.internal.util.IntHashSet;


import com.google.common.collect.ArrayListMultimap;
import com.specmate.cause_effect_patterns.parse.DependencyNode;
import com.specmate.cause_effect_patterns.parse.DependencyParsetree;
import com.specmate.cause_effect_patterns.parse.matcher.MatchResult;
import com.specmate.cause_effect_patterns.parse.matcher.MatcherBase;
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
public abstract class MatcherBase {
	private Optional<MatcherBase> parent;
	private Set<MatcherBase> children;
	protected ArrayListMultimap<String, MatcherBase> arcs;
	
	public MatcherBase() {
		this.children = new HashSet<MatcherBase>();
		this.parent = Optional.empty();
		this.arcs = ArrayListMultimap.create();
	}
	
	public void arcTo(MatcherBase to, String dependencyTag) throws MatcherException {
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
	
	public Collection<MatcherBase> getArcChildren() {
		return this.arcs.values();
	}
	
	@Override
	public String toString() {
		String result = "";
		for (Entry<String,MatcherBase> arc : arcs.entries()) {
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
	
	
	private int positionOfInterest;
	public MatchResult match(DependencyParsetree data, Token head) {
		DependencyNode dependencies = data.getDependencyNode(head);
		MatchResult result = MatchResult.success();
		
		if(dependencies == null && !this.arcs.isEmpty()) {
			return MatchResult.unsuccessful();
		}
		
		
		
		// Prefer candidates close after the position of interest (i.e. the position of our last match)
		this.positionOfInterest = -1;
		
		for(String depTag: this.arcs.keySet()) {
			List<MatcherBase> matchers = this.arcs.get(depTag);
			List<Dependency> candidates = dependencies.getDependenciesFromTag(depTag);
			
			final int poI = this.positionOfInterest;
			Collections.sort(candidates, (a,b) -> {
				int pA = a.getDependent().getBegin();
				int pB = b.getDependent().getBegin();
				
				if(pA <= poI && poI <= pB) {
					return 1;
				}
				
				if(pB <= poI && poI <= pA) {
					return -1;
				}
				
				return pA - pB;
			});
			
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
	
	private MatchResult matchChildren(DependencyParsetree data, List<MatcherBase> matchers, List<Dependency> candidates) {
		if (matchers.size() > candidates.size()) {
			return MatchResult.unsuccessful();
		}
		
		Matching matching = computeMatching(data, matchers, candidates);
		
		if (matching == null) {
			return MatchResult.unsuccessful();
		}
		
		DependencyParsetree unmatched = new DependencyParsetree(data.getTokenOrder());
		for(int remainElement: matching.getUnmatchedCandidates()) {
			Dependency dep = candidates.get(remainElement);
			unmatched.addSubtree(DependencyParsetree.getSubtree(data, dep.getDependent()),dep);
		}
		
		MatchResult result = MatchResult.success(unmatched);
		for (int i = 0; i < matching.matching.length; i++) {
			if(this instanceof SubtreeMatcher && matchers.get(i) instanceof SubtreeMatcher) {
				// We group together subtrees with the same prefix
				// Like Cause and Cause_B get grouped together in a single Cause
				// This allowes for more expressive rules
				String treeA = ((SubtreeMatcher) this).getTreeName();
				String treeB = ((SubtreeMatcher) matchers.get(i)).getTreeName();
				String prefA = treeA.split("_")[0];
				String prefB = treeB.split("_")[0];
				
				if(treeA.startsWith(treeB+"_") || treeB.startsWith(treeA+"_") || prefA.equals(prefB)) {
					DependencyParsetree subMatch = matching.getResult(i).getSubmatch(treeB).getMatchTree();
					Dependency subDep = candidates.get(matching.matching[i]);
					subMatch.addDependency(subDep);
				}
			}
			result.addSubtree(matching.getResult(i));
			// Optional add to discarded Tree 
		}
		return result;
	}

	private Matching computeMatching(DependencyParsetree data, List<MatcherBase> matchers, List<Dependency> candidates) {
		int matchSize = matchers.size();
		int candidateSize = candidates.size();
		
		Matching result = new Matching(matchSize, candidateSize); 
		
		int currentMatcherIndex = 0;
		while(currentMatcherIndex < matchSize) {
			MatcherBase currentMatcher = matchers.get(currentMatcherIndex);
			boolean unmatched = true;
			for(int i = result.matching[currentMatcherIndex]; i < candidateSize; i++) {
				if (result.isAvailable(i)) {
					if(result.matchResults[currentMatcherIndex][i] == null) {
						Token subHead = candidates.get(i).getDependent();
						result.matchResults[currentMatcherIndex][i] = currentMatcher.match(data, subHead);
					}
										
					if(result.matchResults[currentMatcherIndex][i].isSuccessfulMatch()) {
						unmatched = false;
						result.setMatch(currentMatcherIndex, i);
						currentMatcherIndex++;
						int candidatePosition = candidates.get(i).getBegin();
						if(this.positionOfInterest == -1 || this.positionOfInterest > candidatePosition) {
							this.positionOfInterest = candidatePosition;	
						}
						
						break;
					} else {
						result.matching[currentMatcherIndex]++;
					}
				}
			}
			
			if (unmatched) {
				if (currentMatcherIndex > 0) {
					result.unmatch(currentMatcherIndex);
					currentMatcherIndex--;
				} else {
					return null;
				}
			}
		}
		return result;
	}
	
	private class Matching {
		public MatchResult[][] matchResults;
		private IntHashSet availableCandidates;
		public int[] matching;
		
		public Matching(int matchSize, int candidateSize) {
			this.matchResults = new MatchResult[matchSize][candidateSize];
			this.availableCandidates = new IntHashSet(candidateSize);
			this.matching = new int[matchSize];
			
			for(int i=0; i<candidateSize; i++) {
				// We add 1 to all elements because IntHashSet does not allow any element to be 0.
				this.availableCandidates.add(i+1);
				if(i < matchSize) {
					this.matching[i] = 0;
				}
			}
		}
		
		public void unmatch(int index) {
			this.matching[index] = 0;
			availableCandidates.add(1+this.matching[index - 1]);
			this.matching[index - 1]++;
		}

		public void setMatch(int currentMatcherIndex, int i) {
			matching[currentMatcherIndex] = i;
			availableCandidates.remove(i+1);
		}

		public boolean isAvailable(int candidate) {
			return availableCandidates.contains(candidate+1);
		}
		
		public int[] getUnmatchedCandidates() {
			int[] remainingCandidates = this.availableCandidates.toIntArray();
			for(int i=0; i<remainingCandidates.length; i++) {
				// Invert the adding 1 from before
				remainingCandidates[i]--;
			}
			return remainingCandidates;
		}
		
		public MatchResult getResult(int i) {
			return this.matchResults[i][this.matching[i]];
		}
	}
}

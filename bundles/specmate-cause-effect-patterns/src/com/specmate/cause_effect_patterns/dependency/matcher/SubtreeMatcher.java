package com.specmate.cause_effect_patterns.dependency.matcher;

import com.google.common.base.Optional;
import com.specmate.cause_effect_patterns.dependency.DependencyParsetree;
import com.specmate.cause_effect_patterns.dependency.matcher.MatchResult;
import com.specmate.cause_effect_patterns.dependency.matcher.Matcher;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;

/**
 * Matches the entire subtree.
 * Can optionally specified with a regex pattern and a POS tag
 * for the root node of the subtree.
 * If no pattern or tag is provided the match is always successful.
 * 
 * @author Dominik
 *
 */
public class SubtreeMatcher extends Matcher {
	
	private Optional<String> posTag;
	private Optional<String> pattern;
	private String subtreeName;

	public SubtreeMatcher(String subtreeName, String pattern, String posTag) {
		this(subtreeName, pattern);
		if (posTag != null) {
			this.posTag = Optional.of(posTag);
		}
	}
	
	public SubtreeMatcher(String subtreeName, String pattern) {
		this(subtreeName);
		if(pattern != null) {
			this.pattern = Optional.of(pattern);	
		}
	}
	
	public SubtreeMatcher(String subtreeName) {
		this.subtreeName = subtreeName;
		this.pattern = Optional.absent();
		this.posTag = Optional.absent();
	}
	
	@Override
	public String getRepresentation() {
		return "["+this.subtreeName+"]";
	}
	
	public void updateRootData(String expr, String tag) throws MatcherException {
		if (expr != null) {
			if (!this.pattern.isPresent()) {
				this.pattern = Optional.of(expr);
			} else if(!this.pattern.get().equals(expr)) {
					throw MatcherException.incompatibleMatchers(this,this.pattern.get(), expr);
			}
		}
		
		if (tag != null) {
			if (!this.posTag.isPresent()) {
				this.posTag = Optional.of(tag);
			} else if(!this.posTag.get().equals(tag)) {
					throw MatcherException.incompatibleMatchers(this,this.posTag.get(), tag);
			}	
		}
	}
	
	@Override
	public MatchResult match(DependencyParsetree data, Token head) {
		if(this.pattern.isPresent()) {
			String tokenText = head.getCoveredText().trim();
			if(!tokenText.matches(this.pattern.get())) {
				return MatchResult.unsuccessful();
			}
		}
		
		if(this.posTag.isPresent()) {
			if(!head.getPosValue().equals(this.posTag.get())) {
				return MatchResult.unsuccessful();
			}
		}
		
		MatchResult res = super.match(data, head);

		DependencyParsetree thisNode = new DependencyParsetree(data.getTokenOrder() , head);
		thisNode.addSubtree(res.getMatchTree());
		MatchResult subtree = MatchResult.success(thisNode);
		
		res.addSubmatch(this.subtreeName, subtree);
		res.clearMatchTree();
		return res;
	}
}

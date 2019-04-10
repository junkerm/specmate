package com.specmate.nlp.dependency.matcher;

import com.google.common.base.Optional;
import com.specmate.nlp.dependency.DependencyData;

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
		this.posTag = Optional.of(posTag);
	}
	
	public SubtreeMatcher(String subtreeName, String pattern) {
		this(subtreeName);
		this.pattern = Optional.of(pattern);
	}
	
	public SubtreeMatcher(String subtreeName) {
		this.subtreeName = subtreeName;
		this.pattern = Optional.absent();
		this.posTag = Optional.absent();
	}
	
	@Override
	public MatchResult match(DependencyData data, Token head) {
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

		DependencyData thisNode = new DependencyData(head);
		thisNode.addSubtree(res.getMatchTree());
		MatchResult subtree = MatchResult.success(thisNode);
		
		res.addSubmatch(this.subtreeName, subtree);
		res.clearMatchTree();
		return res;
	}
}

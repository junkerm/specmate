package com.specmate.nlp.dependency.matcher;

import com.google.common.base.Optional;
import com.specmate.nlp.dependency.DependencyData;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;

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
		MatchSubtree thisNode = new MatchSubtree(head);
		thisNode.addSubtree(res.getUnmatched());
		res.setSubtree(this.subtreeName, thisNode);
		res.setUnmatched(null);
		return res;
	}
}

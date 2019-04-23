package org.xtext.specmate.resolve.matcher;

import java.util.HashSet;
import java.util.Set;

public class SubtreeMatcher extends Matcher {
	public String treeTag;
	public String expression;
	public String posTag;
	
	public SubtreeMatcher(String tree, String expr, String tag) {
		this.treeTag = tree;
		this.expression = expr;
		this.posTag = tag;
	}

	public void updateRootData(String expr, String tag) throws MatcherException {
		if (this.expression == null) {
			this.expression = expr;
		} else if(expr != null && !this.expression.equals(expr)) {
				throw MatcherException.incompatibleMatchers(this,this.expression, expr);
		}
		
		if (this.posTag == null) {
			this.posTag = tag;
		} else if(tag != null && !this.posTag.equals(tag)) {
				throw MatcherException.incompatibleMatchers(this,this.posTag, tag);
		}
	}

	@Override
	public Set<String> getSubtree() {
		HashSet<String> tree = new HashSet<String>();
		tree.add(treeTag);
		return tree;
	}
	
	@Override
	public String getRepresentation() {
		return "["+this.treeTag+"]";
	}
}

package com.specmate.cause_effect_patterns.resolve.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;

import com.specmate.cause_effect_patterns.internal.specDSL.DependencyRule;
import com.specmate.cause_effect_patterns.internal.specDSL.ExplicitNode;
import com.specmate.cause_effect_patterns.internal.specDSL.Node;
import com.specmate.cause_effect_patterns.internal.specDSL.OptionNode;
import com.specmate.cause_effect_patterns.internal.specDSL.Rule;
import com.specmate.cause_effect_patterns.internal.specDSL.Tag;
import com.specmate.cause_effect_patterns.internal.specDSL.TreeNode;
import com.specmate.cause_effect_patterns.parse.matcher.MatcherBase;
import com.specmate.cause_effect_patterns.parse.matcher.MatcherException;
import com.specmate.cause_effect_patterns.parse.matcher.OrMatcher;
import com.specmate.cause_effect_patterns.parse.matcher.SubtreeMatcher;
import com.specmate.cause_effect_patterns.parse.matcher.TokenMatcher;

public class RuleUtility {
	private static Map<String, SubtreeMatcher> treeMatcher;
	
	public static synchronized MatcherBase transform(Rule rule) throws MatcherException {
		HeadFindStructure<MatcherBase> headFinder = new HeadFindStructure<MatcherBase>();
		treeMatcher = new HashMap<String, SubtreeMatcher>();
		
		for (DependencyRule dRule: rule.getDependencies()) {
			MatcherBase lNode = resolveMatcher(dRule.getLeftNode());
			
			MatcherBase ruleHead = lNode;
			if(!headFinder.contains(ruleHead)) {
				headFinder.add(ruleHead);	
			}
			
			while(! (dRule.getRightNode() instanceof Node)) {
				String dTag = resolveTag(dRule.getDTag());
				dRule = (DependencyRule) dRule.getRightNode();
				MatcherBase rNode = resolveMatcher(dRule.getLeftNode());
				if (headFinder.contains(rNode)) {
					headFinder.setHead(rNode, ruleHead);
				} else {
					headFinder.add(rNode, ruleHead);	
				}
				
				lNode.arcTo(rNode, dTag);
				lNode = rNode;
			}
			MatcherBase rNode = resolveMatcher((Node)dRule.getRightNode());
			if (headFinder.contains(rNode)) {
				headFinder.setHead(rNode, ruleHead);
			} else {
				headFinder.add(rNode, ruleHead);	
			}
			
			String dTag = resolveTag(dRule.getDTag());
			lNode.arcTo(rNode, dTag);
		}
		
		Set<MatcherBase> headSet = headFinder.getHeadSet();
		if (headSet.size() > 1) {
			throw MatcherException.multipleMatchheads(headSet);
		}
		
		if (headSet.size() == 0) {
			throw MatcherException.noMatchhead();
		}
		return headSet.iterator().next();
	}
	
	private static MatcherBase resolveMatcher(Node node) throws MatcherException {
		if (node instanceof ExplicitNode) {
			// Explicit Node
			ExplicitNode eNode = (ExplicitNode) node;
			
			String expr = eNode.getExpr();
			if(!eNode.isCaseSensitive()) {
				expr = "(?i)"+expr;
			}
			
			if(eNode.isAnyMatch()) {
				expr = ".*";
			}
			if(eNode.getPTag() != null) {
				return new TokenMatcher(expr, resolveTag(eNode.getPTag()));
			}			
			return new TokenMatcher(expr);
		} else if (node instanceof OptionNode) {
			// Option Node
			OptionNode oNode = (OptionNode) node;
			
			MatcherBase[] matchers = new MatcherBase[oNode.getRightNodes().size()+1];
			EList<ExplicitNode> nodes = oNode.getRightNodes();
			for (int i = 0; i < nodes.size(); i++) {
				matchers[i] = resolveMatcher(nodes.get(i)); 
			}
			matchers[matchers.length-1] = resolveMatcher(oNode.getLeftNode());
			return new OrMatcher(matchers);
		} else {
			// Tree Node
			TreeNode tNode = (TreeNode) node;
			String tree = tNode.getTree().getName().getName();
			
			String expr = tNode.getExpr();
			String tag = null;
			if (tNode.getPTag() != null) {
				tag = resolveTag(tNode.getPTag());
			}
			
			SubtreeMatcher tMatch = null;
			if(treeMatcher.containsKey(tree)) {
				tMatch = treeMatcher.get(tree);
				tMatch.updateRootData(expr, tag);
			} else {
				tMatch = new SubtreeMatcher(tree, expr, tag);
				treeMatcher.put(tree, tMatch);
			}
			return tMatch;
		}
	}
	
	private static String resolveTag(Tag tag) {
		if (tag.getTagname() != null) {
			return tag.getTagname();
		}
		return tag.getName();
	}
}

package org.xtext.specmate.resolve.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.xtext.specmate.resolve.matcher.Matcher;
import org.xtext.specmate.resolve.matcher.MatcherException;
import org.xtext.specmate.resolve.matcher.OrMatcher;
import org.xtext.specmate.resolve.matcher.SubtreeMatcher;
import org.xtext.specmate.resolve.matcher.TokenMatcher;
import org.xtext.specmate.specDSL.DependencyRule;
import org.xtext.specmate.specDSL.ExplicitNode;
import org.xtext.specmate.specDSL.Node;
import org.xtext.specmate.specDSL.OptionNode;
import org.xtext.specmate.specDSL.Rule;
import org.xtext.specmate.specDSL.Tag;
import org.xtext.specmate.specDSL.TreeNode;

public class RuleUtility {
	private static Map<String, SubtreeMatcher> treeMatcher;
	
	public static Matcher transform(Rule rule) throws MatcherException {
		HeadFindStructure<Matcher> headFinder = new HeadFindStructure<Matcher>();
		treeMatcher = new HashMap<String, SubtreeMatcher>();
		
		for (DependencyRule dRule: rule.getDependencies()) {
			Matcher lNode = resolveMatcher(dRule.getLeftNode());
			
			Matcher ruleHead = lNode;
			if(!headFinder.contains(ruleHead)) {
				headFinder.add(ruleHead);	
			}
			
			while(! (dRule.getRightNode() instanceof Node)) {
				String dTag = resolveTag(dRule.getDTag());
				dRule = (DependencyRule) dRule.getRightNode();
				Matcher rNode = resolveMatcher(dRule.getLeftNode());
				if (headFinder.contains(rNode)) {
					headFinder.setHead(rNode, ruleHead);
				} else {
					headFinder.add(rNode, ruleHead);	
				}
				
				lNode.addArc(rNode, dTag);
				lNode = rNode;
			}
			Matcher rNode = resolveMatcher((Node)dRule.getRightNode());
			if (headFinder.contains(rNode)) {
				headFinder.setHead(rNode, ruleHead);
			} else {
				headFinder.add(rNode, ruleHead);	
			}
			
			String dTag = resolveTag(dRule.getDTag());
			lNode.addArc(rNode, dTag);
		}
		
		Set<Matcher> headSet = headFinder.getHeadSet();
		if (headSet.size() > 1) {
			throw MatcherException.multipleMatchheads(headSet);
		}
		
		if (headSet.size() == 0) {
			throw MatcherException.noMatchhead();
		}
		return headSet.iterator().next();
	}
	
	private static Matcher resolveMatcher(Node node) throws MatcherException {
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
			
			Matcher[] matchers = new Matcher[oNode.getRightNodes().size()+1];
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

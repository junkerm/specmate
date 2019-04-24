package com.specmate.cause_effect_patterns.parse;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.internal.util.SortedIntSet;
import org.apache.uima.jcas.JCas;

import com.specmate.cause_effect_patterns.parse.DependencyNode;
import com.specmate.cause_effect_patterns.parse.DependencyParsetree;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.dependency.Dependency;

/**
 * Representation for a dependency parse.
 * 
 * @author Dominik
 *
 */
public class DependencyParsetree {
	private static String ROOT = "ROOT";
	
	private Map<Token, DependencyNode> dependencies;
	private Set<Token> heads;
	private List<TextInterval> treeFragments;
	private SortedIntSet tokenOrder;
	
	public SortedIntSet getTokenOrder() {
		return this.tokenOrder;
	}
	
	public static DependencyParsetree generateFromJCas(JCas jcas) {
		DependencyParsetree result = new DependencyParsetree();
		
		Collection<Dependency> dependencyList = JCasUtil.select(jcas, Dependency.class);
		
		for(Dependency d: dependencyList) {
			Token governor = d.getGovernor();
			
			if(!result.dependencies.containsKey(governor)) {
				result.dependencies.put(governor, new DependencyNode());
				result.addFragment(governor);
			} 
			
			if(d.getDependencyType().equals(ROOT)) {
				result.heads.add(governor);
			} else {
				result.dependencies.get(governor).addDepenency(d);
				result.addFragment(d.getDependent());
			}
		}
		result.minimizeTreeFragments();
		return result;
	}

	public static DependencyParsetree getSubtree(DependencyParsetree data, Token token) {
		DependencyParsetree result = new DependencyParsetree(data.tokenOrder, token);
		
		if(data.hasDependencies(token)) {
			for (Dependency d: data.getDependencyNode(token)) {
				Token child = d.getDependent();
				if(child == token) {
					continue;
				}
				result.addSubtree(DependencyParsetree.getSubtree(data, child), d);
			}
		}
		return result;
	}
	
	public DependencyParsetree(SortedIntSet order) {
		this();
		this.tokenOrder = order;
	}
	
	public DependencyParsetree(SortedIntSet sortedIntSet, Token head) {
		this(sortedIntSet);
		this.heads.add(head);
		addFragment(head);
	}
	
	public DependencyParsetree() {
		this.dependencies = new HashMap<Token, DependencyNode>();
		this.heads = new HashSet<Token>();
		this.treeFragments = new Vector<DependencyParsetree.TextInterval>();
		this.tokenOrder = new SortedIntSet();
	}
	
	public void addSubtree(DependencyParsetree subtree) {
		this.dependencies.putAll(subtree.dependencies);
		this.treeFragments.addAll(subtree.treeFragments);
		this.tokenOrder.union(subtree.tokenOrder);
		this.minimizeTreeFragments();
	}
	
	public void addSubtree(DependencyParsetree subtree, Dependency dependency) {
		Token governor = dependency.getGovernor();
		if(!this.dependencies.containsKey(governor)) {
			this.dependencies.put(governor, new DependencyNode());
			addFragment(governor);
		}
		this.dependencies.get(governor).addDepenency(dependency);
		this.addSubtree(subtree);
	}
	
	public DependencyNode getDependencyNode(Token dependent) {
		return this.dependencies.get(dependent);
	}

	public Collection<Token> getHeads() {
		return this.heads;
	}

	public boolean hasDependencies(Token token) {
		return this.dependencies.containsKey(token);
	}

	private void addFragment(Token token) {
		this.treeFragments.add(new TextInterval(token));
		this.tokenOrder.add(token.getBegin());
	}
	
	/**
	 * Merges tree fragments if they can be merged to a bigger fragment.
	 */
	private void minimizeTreeFragments() {
		for(int i=0; i< this.treeFragments.size(); i++) {
			TextInterval iInt = this.treeFragments.get(i);
			
			for(int j = i+1; j<this.treeFragments.size(); j++) {
				TextInterval comb = iInt.combine(this.treeFragments.get(j), this.tokenOrder);
				if(comb != null) {
					this.treeFragments.remove(j);
					this.treeFragments.set(i, comb);
					i = -1;
					break;
				}
			}
		}
		
		Collections.sort(this.treeFragments);
	}
	
	public String getTreeFragmentText() {
		String result = "Fragments:\n";
		for(TextInterval i: this.treeFragments) {
			result += "\t"+i+"\n";
		}
		return result;
	}
	
	@Override
	public String toString() {
		String result = "Roots:\n";
		for(Token root: this.heads) {
			result+= "\t"+root.getCoveredText()+"\n";
		}
		
		result+="Dependencies:\n";
		for(Token t: this.dependencies.keySet()) {
			result+= "\t"+t.getCoveredText()+"\n";
			DependencyNode node = this.getDependencyNode(t);
			for(String key: node.getKeySet()) {
				List<Dependency>dependencies = node.getDependenciesFromTag(key);
				for(Dependency d: dependencies) {
					result += "\t\t--"+d.getDependencyType()+"-->"+d.getDependent().getCoveredText()+"\n";
				}
			}
		}
			
		result+= this.getTreeFragmentText();
		return result;
	}
	
	private class TextInterval implements Comparable<TextInterval>{
		public int from, to;
		public String text;
		
 	  	public TextInterval(int from, int to, String text) {
			this.from = from;
			this.to = to;
			this.text = text;
		}
		
		public TextInterval(Token token) {
			this(token.getBegin(), token.getEnd(), token.getCoveredText());
		}
		
		public TextInterval combine(TextInterval other, SortedIntSet order) {
			TextInterval begin = this;
			TextInterval end = other;
			
			if(other.from < this.from || (other.from == this.from && other.to >= this.to)) {
				begin = other;
				end = this;
			}
			
			if(begin.to == end.from) {
				return new TextInterval(begin.from, end.to, begin.text+end.text);
			}
			
			if(begin.getLastIndex(order) == end.getFirstIndex(order)-1) {
				return new TextInterval(begin.from, end.to, begin.text+" "+end.text);
			}
			
			if(begin.to >= end.to) {
				return begin;
			}
			
			return null;
		}
		
		private int getFirstIndex(SortedIntSet order) {
			return order.find(this.from);
		}
		
		private int getLastIndex(SortedIntSet order) {
			int i = getFirstIndex(order);
			while(i < order.size()) {
				if(this.to < order.get(i)) {
					break;
				}
				i++;
			}
			return i-1;
		}
		
		@Override
		public String toString() {
			return this.text + " " + this.from+ " - "+this.to;
		}

		@Override
		public int compareTo(TextInterval o) {
			if(this.to <= o.from) {
				return -1;
			}
			if(o.to <= this.from) {
				return 1;
			}
			return 0;
		}
	}
}

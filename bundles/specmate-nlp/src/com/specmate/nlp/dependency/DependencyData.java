package com.specmate.nlp.dependency;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.dependency.Dependency;

/**
 * Representation for a dependency parse.
 * 
 * @author Dominik
 *
 */
public class DependencyData {
	private static String ROOT = "ROOT";
	
	private Map<Token, DependencyNode> dependencies;
	private Set<Token> heads;
	private List<TextInterval> treeFragments;
	
	public static DependencyData generateFromJCas(JCas jcas) {
		DependencyData out = new DependencyData();
		
		Collection<Dependency> dependencyList = JCasUtil.select(jcas, Dependency.class);
		
		for(Dependency d: dependencyList) {
			Token governor = d.getGovernor();
			
			if(!out.dependencies.containsKey(governor)) {
				out.dependencies.put(governor, new DependencyNode());
				out.treeFragments.add(out.new TextInterval(governor));
			} 
			
			if(d.getDependencyType().equals(ROOT)) {
				out.heads.add(governor);
			} else {
				out.dependencies.get(governor).addDepenency(d);
				out.treeFragments.add(out.new TextInterval(d.getDependent()));
			}
		}
		out.minimizeTreeFragments();
		return out;
	}

	public static DependencyData getSubtree(DependencyData data, Token token) {
		DependencyData out = new DependencyData(token);
		
		if(data.hasDependencies(token)) {
			for (Dependency d: data.getDependencyNode(token)) {
				Token child = d.getDependent();
				if(child == token) {
					continue;
				}
				out.addSubtree(DependencyData.getSubtree(data, child), d);
			}
		}
		return out;
	}
	
	public DependencyData(Token head) {
		this();
		this.heads.add(head);
		this.treeFragments.add(new TextInterval(head));
	}
	
	public DependencyData() {
		this.dependencies = new HashMap<Token, DependencyNode>();
		this.heads = new HashSet<Token>();
		this.treeFragments = new Vector<DependencyData.TextInterval>();
	}
	
	public void addSubtree(DependencyData subtree) {
		this.dependencies.putAll(subtree.dependencies);
		this.treeFragments.addAll(subtree.treeFragments);
		this.minimizeTreeFragments();
	}
	
	public void addSubtree(DependencyData subtree, Dependency connection) {
		Token governor = connection.getGovernor();
		if(!this.dependencies.containsKey(governor)) {
			this.dependencies.put(governor, new DependencyNode());
			this.treeFragments.add(this.new TextInterval(governor));
		}
		this.dependencies.get(governor).addDepenency(connection);
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

	/**
	 * Merges tree fragments if they can be merged to a bigger fragment.
	 */
	private void minimizeTreeFragments() {
		for(int i=0; i< this.treeFragments.size(); i++) {
			TextInterval iInt = this.treeFragments.get(i);
			
			for(int j = i+1; j<this.treeFragments.size(); j++) {
				TextInterval comb = iInt.combine(this.treeFragments.get(j));
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
		String out = "Fragments:\n";
		for(TextInterval i: this.treeFragments) {
			out += "\t"+i+"\n";
		}
		return out;
	}
	
	@Override
	public String toString() {
		String out = "Roots:\n";
		for(Token root: this.heads) {
			out+= "\t"+root.getCoveredText()+"\n";
		}
		
		out+="Dependencies:\n";
		for(Token t: this.dependencies.keySet()) {
			out+= "\t"+t.getCoveredText()+"\n";
			DependencyNode node = this.getDependencyNode(t);
			for(String key: node.getKeySet()) {
				List<Dependency>dependencies = node.getDependenciesFromTag(key);
				for(Dependency d: dependencies) {
					out += "\t\t--"+d.getDependencyType()+"-->"+d.getDependent().getCoveredText()+"\n";
				}
			}
		}
			
		out+= this.getTreeFragmentText();
		return out;
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
		
		public TextInterval combine(TextInterval other) {
			TextInterval begin = this;
			TextInterval end = other;
			
			if(other.from < this.from || (other.from == this.from && other.to >= this.to)) {
				begin = other;
				end = this;
			}
			
			if(begin.to == end.from) {
				return new TextInterval(begin.from, end.to, begin.text+end.text);
			}
			
			if(begin.to == end.from-1) {
				return new TextInterval(begin.from, end.to, begin.text+" "+end.text);
			}
			
			if(begin.to >= end.to) {
				return begin;
			}
			
			return null;
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

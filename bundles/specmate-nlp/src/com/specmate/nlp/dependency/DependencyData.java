package com.specmate.nlp.dependency;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.dependency.Dependency;

public class DependencyData {
	private Map<Token, DependencyNode> dependencies;
	private Set<Token> heads;
	
	public DependencyData(JCas jcas) {
		this.dependencies = new HashMap<Token, DependencyNode>();
		this.heads = new HashSet<Token>();
		
		HashSet<Token> nonHeads = new HashSet<Token>(); 
		
		Collection<Dependency> dependencyList = JCasUtil.select(jcas, Dependency.class);
		
		
		for(Dependency d: dependencyList) {
			Token dependent = d.getDependent();
			if(!this.dependencies.containsKey(dependent)) {
				this.dependencies.put(dependent, new DependencyNode());
			} 
			this.dependencies.get(dependent).addDepenency(d);
			
			nonHeads.add(d.getGovernor());
			this.heads.add(dependent);
		}
		
		this.heads.removeAll(nonHeads);
	}

	public DependencyNode getDependencies(Token dependent) {
		return this.dependencies.get(dependent);
	}
}

package com.specmate.nlp.dependency;

import java.util.Iterator;
import java.util.List;

import com.google.common.collect.ArrayListMultimap;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.dependency.Dependency;

public class DependencyNode implements Iterable<Token> {
	private ArrayListMultimap<String, Dependency> dependencies;

	public DependencyNode() {
		this.dependencies = ArrayListMultimap.create();
	}
	
	public void addDepenency(Dependency d) {
		this.dependencies.put(d.getDependencyType(), d);
	}
	
	public List<Dependency> getDependenciesFromTag(String dependencyTag) {
		return this.dependencies.get(dependencyTag);
	}

	@Override
	public Iterator<Token> iterator() {
		return dependencies.entries().stream().map(e -> e.getValue().getGovernor()).iterator();
	}
	
}

package com.specmate.cause_effect_patterns.parse;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.collect.ArrayListMultimap;

import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.dependency.Dependency;

/**
 * Wrapper for a (DependencyTag --> Dependency) Multimap
 * Acts as iterator for all dependencies.
 * 
 * @author Dominik
 *
 */
public class DependencyNode  implements Iterable<Dependency>{
	private ArrayListMultimap<String, Dependency> dependencies;

	public DependencyNode() {
		this.dependencies = ArrayListMultimap.create();
	}
	
	public void addDepenency(Dependency d) {
		this.dependencies.put(d.getDependencyType(), d);
	}
	
	public void addDependencies(DependencyNode other) {
		for(Entry<String, Dependency> entry: other.dependencies.entries()) {
			String key = entry.getKey();
			Dependency value = entry.getValue();
			if(!this.dependencies.containsEntry(key, value)) {
				this.dependencies.put(key, value);
			}
		}
	}
	
	public List<Dependency> getDependenciesFromTag(String dependencyTag) {
		return this.dependencies.get(dependencyTag);
	}

	public Set<String> getKeySet() {
		return this.dependencies.keys().elementSet();
	}
	
	@Override
	public Iterator<Dependency> iterator() {
		return dependencies.values().iterator();
	}
}

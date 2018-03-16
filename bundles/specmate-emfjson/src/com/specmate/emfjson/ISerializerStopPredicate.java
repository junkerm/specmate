package com.specmate.emfjson;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * Predicate that describes where to stop the EMF to JSON serialization
 * @author junkerm
 *
 */
public interface ISerializerStopPredicate {

	/**
	 * @param feature 
	 * @return <code>true</code> if the serialization should stop at feature <code>feature</code>
	 */
	public abstract boolean stopAtFeature(EStructuralFeature feature);

	
	/**
	 * @param clazz 
	 * @return <code>true</code> if the serialization should stop at classifier <code>clazz</code>
	 */
	public abstract boolean stopAtClass(EClassifier clazz);

	/**
	 * 
	 * @param depth
	 * @return <code>true</code> if the serialization should stop at depth <code>depth</code>
	 */
	public abstract boolean stopAtDepth(int depth);

}
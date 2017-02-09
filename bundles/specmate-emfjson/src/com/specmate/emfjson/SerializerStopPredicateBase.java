package com.specmate.emfjson;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * Dummy implementation of {@link ISerializerStopPredicate} that never stops serializing.
 * @author junkerm
 */
public class SerializerStopPredicateBase implements ISerializerStopPredicate {
	
	/** {@inheritDoc} */
	@Override
	public boolean stopAtFeature(EStructuralFeature feature){
		return false;
	}
	
	/** {@inheritDoc} */
	@Override
	public boolean stopAtClass(EClassifier clazz){
		return false;
	}
	
	/** {@inheritDoc} */
	@Override
	public boolean stopAtDepth(int depth){
		return false;
	}
}

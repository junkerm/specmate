package com.specmate.modelgeneration;

import org.apache.uima.jcas.JCas;

import com.specmate.common.exception.SpecmateInternalException;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;

/**
 * Matches the patterns, which are used to detect causality, to the sentences
 *
 * @author Andreas Wehrle
 *
 */
public abstract class PatternMatcher {

	public abstract String[] detectCauseAndEffect(Sentence sentence, JCas jCas) throws SpecmateInternalException;

}

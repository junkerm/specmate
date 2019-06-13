package com.specmate.modelgeneration.legacy;

import org.apache.uima.jcas.JCas;

import com.specmate.common.exception.SpecmateInternalException;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;

public interface ICauseEffectPatternMatcher {
	public String[] detectCauseAndEffect(Sentence sentence, JCas jCas) throws SpecmateInternalException;

}

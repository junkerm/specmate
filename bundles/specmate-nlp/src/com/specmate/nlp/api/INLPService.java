package com.specmate.nlp.api;

import org.apache.uima.jcas.JCas;

import com.specmate.common.exception.SpecmateException;

/**
 * Interface for a service that performs NLP analysis based on the DKPro
 * abstractions
 *
 * @author w2kf59j
 *
 */
public interface INLPService {
	public JCas processText(String text) throws SpecmateException;
}

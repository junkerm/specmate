package com.specmate.testspecification.internal.services;

import org.apache.uima.jcas.JCas;

import com.specmate.common.SpecmateException;

/**
 * Service-Interface to tag a text with the DKPro NLP-Framework
 * 
 * @author Andreas Wehrle
 *
 */
public interface NLPTagger {

	/**
	 * Tags a text with NLP-Tags
	 * 
	 * @param text
	 * @return NLP tagged text
	 * @throws SpecmateException
	 */
	public JCas tagText(String text) throws SpecmateException;
}

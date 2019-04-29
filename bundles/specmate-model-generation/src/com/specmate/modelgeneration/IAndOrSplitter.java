package com.specmate.modelgeneration;

import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;

public interface IAndOrSplitter {

	String[] textSplitterAnd(String effect, Sentence sentence, JCas jCas);

	String[] textSplitterOr(String string, Sentence sentence, JCas jCas);

	boolean containsAnd(String cause);

	boolean containsOr(String cause);

	String[] splitAndOrSimple(String text);

}

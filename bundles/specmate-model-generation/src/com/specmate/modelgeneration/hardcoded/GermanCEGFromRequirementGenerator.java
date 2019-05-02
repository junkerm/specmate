package com.specmate.modelgeneration.hardcoded;

import java.util.Collection;
import java.util.StringJoiner;

import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.osgi.service.log.LogService;

import com.specmate.common.exception.SpecmateException;
import com.specmate.nlp.api.ELanguage;
import com.specmate.nlp.api.INLPService;
import com.specmate.nlp.util.GermanSentenceUnfolder;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;

public class GermanCEGFromRequirementGenerator extends CEGFromRequirementGenerator {

	public GermanCEGFromRequirementGenerator(LogService logService, INLPService tagger) {
		super(logService, tagger);
	}

	@Override
	protected ICauseEffectPatternMatcher getPatternMatcher() {
		return new GermanPatternMatcher();
	}

	@Override
	protected IAndOrSplitter getAndOrSplitter() {
		return new GermanAndOrSplitter();
	}

	@Override
	protected String replaceNegation(String text) {
		if (text.contains("kein ")) {
			return text.replace("kein ", "ein ");
		}
		if (text.contains("keine ")) {
			return text.replace("keine ", "eine ");
		}
		if (text.contains("keinen ")) {
			return text.replace("keinen ", "einen ");
		}
		if (text.contains("nicht ")) {
			return text.replace("nicht ", "");
		}
		return null;
	}

	@Override
	protected String preprocess(String text) throws SpecmateException {
		JCas result = tagger.processText(text, ELanguage.DE);
		Collection<Sentence> sentences = JCasUtil.select(result, Sentence.class);
		StringJoiner joiner = new StringJoiner(" ");
		for (Sentence sen : sentences) {
			joiner.add(new GermanSentenceUnfolder().unfold(tagger, sen.getCoveredText(), ELanguage.DE));
		}
		return joiner.toString();

	}

	@Override
	protected ELanguage getLanguage() {
		return ELanguage.DE;
	}

}

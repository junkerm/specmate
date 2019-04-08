package com.specmate.modelgeneration;

import org.osgi.service.log.LogService;

import com.specmate.nlp.api.ELanguage;
import com.specmate.nlp.api.INLPService;

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
			return text.replace("kein ", " ein");
		}
		if (text.contains("nicht ")) {
			return text.replace("nicht ", "");
		}
		return null;
	}

	@Override
	protected ELanguage getLanguage() {
		return ELanguage.DE;
	}

}

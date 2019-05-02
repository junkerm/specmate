package com.specmate.modelgeneration.hardcoded;

import org.osgi.service.log.LogService;

import com.specmate.nlp.api.ELanguage;
import com.specmate.nlp.api.INLPService;

public class EnglishCEGFromRequirementGenerator extends CEGFromRequirementGenerator {

	public EnglishCEGFromRequirementGenerator(LogService logService, INLPService tagger) {
		super(logService, tagger);
	}

	@Override
	protected ICauseEffectPatternMatcher getPatternMatcher() {
		return new EnglishPatternMatcher();
	}

	@Override
	protected IAndOrSplitter getAndOrSplitter() {
		return new EnglishAndOrSplitter();
	}

	@Override
	protected String replaceNegation(String text) {
		if (text.contains("no ")) {
			return text.replace("no ", "a ");
		}
		if (text.contains("cannot ")) {
			return text.replace("cannot ", "can ");
		}
		if (text.contains("not ")) {
			return text.replace("not ", "");
		}
		if (text.contains("n't ")) {
			return text.replace("n't ", " ");
		}
		return null;

	}

	@Override
	protected ELanguage getLanguage() {
		return ELanguage.EN;
	}

}

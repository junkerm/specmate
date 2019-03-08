package com.specmate.modelgeneration;

import org.osgi.service.log.LogService;

import com.specmate.nlp.api.INLPService;

public class EnglishCEGFromRequirementGenerator extends CEGFromRequirementGenerator {

	private PatternMatcher patternMatcher;

	public EnglishCEGFromRequirementGenerator(LogService logService, INLPService tagger) {
		super(logService, tagger);
		this.patternMatcher = new EnglishPatternMatcher();
	}

	@Override
	protected PatternMatcher getPatternMatcher() {
		return this.patternMatcher;
	}

}

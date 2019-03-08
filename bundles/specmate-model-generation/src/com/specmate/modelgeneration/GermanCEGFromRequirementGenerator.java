package com.specmate.modelgeneration;

import org.osgi.service.log.LogService;

import com.specmate.nlp.api.INLPService;

public class GermanCEGFromRequirementGenerator extends CEGFromRequirementGenerator {

	private GermanPatternMatcher patternMatcher;

	public GermanCEGFromRequirementGenerator(LogService logService, INLPService tagger) {
		super(logService, tagger);
		this.patternMatcher = new GermanPatternMatcher();
	}

	@Override
	protected PatternMatcher getPatternMatcher() {
		// TODO Auto-generated method stub
		return null;
	}

}

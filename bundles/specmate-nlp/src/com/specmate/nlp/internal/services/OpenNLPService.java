package com.specmate.nlp.internal.services;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.jcas.JCas;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import com.specmate.common.exception.SpecmateException;
import com.specmate.common.exception.SpecmateInternalException;
import com.specmate.model.administration.ErrorCode;
import com.specmate.nlp.api.INLPService;

import de.tudarmstadt.ukp.dkpro.core.maltparser.MaltParser;
import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpParser;
import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpPosTagger;
import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpSegmenter;

/**
 * Service to tag a text with the DKPro NLP-Framework
 *
 * @author Andreas Wehrle
 *
 */
@Component(immediate = true)
public class OpenNLPService implements INLPService {
	AnalysisEngine engine;
	private LogService logService;

	/**
	 * Start the NLP Engine
	 *
	 * @throws SpecmateException
	 */
	@Activate
	public void activate() throws SpecmateException {
		// logService.log(org.osgi.service.log.LogService.LOG_DEBUG, "OpenNLP
		// NLP service is starting");
		AnalysisEngineDescription segmenter = null;
		AnalysisEngineDescription posTagger = null;
		AnalysisEngineDescription parser = null;
		AnalysisEngineDescription dependencyParser = null;

		try {
			logService.log(LogService.LOG_INFO, "Starting up Open NLP Service.");
			segmenter = createEngineDescription(OpenNlpSegmenter.class, OpenNlpSegmenter.PARAM_LANGUAGE, "en");
			posTagger = createEngineDescription(OpenNlpPosTagger.class, OpenNlpPosTagger.PARAM_LANGUAGE, "en",
					OpenNlpPosTagger.PARAM_VARIANT, "maxent");
			dependencyParser = createEngineDescription(MaltParser.class, MaltParser.PARAM_LANGUAGE, "en",
					MaltParser.PARAM_IGNORE_MISSING_FEATURES, true);
			parser = createEngineDescription(OpenNlpParser.class, OpenNlpParser.PARAM_PRINT_TAGSET, true,
					OpenNlpParser.PARAM_LANGUAGE, "en", OpenNlpParser.PARAM_WRITE_PENN_TREE, true,
					OpenNlpParser.PARAM_WRITE_POS, true);
			engine = createEngine(createEngineDescription(segmenter, posTagger, dependencyParser, parser));
			logService.log(org.osgi.service.log.LogService.LOG_INFO, "OpenNLP NLP service started");
		} catch (Throwable e) {
			logService.log(LogService.LOG_ERROR,
					"OpenNLP NLP service failed  when starting. Reason: " + e.getMessage());
			throw new SpecmateInternalException(ErrorCode.INTERNAL_PROBLEM,
					"OpenNLP NLP service failed when starting. Reason: " + e.getMessage());
		}

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.specmate.testspecification.internal.services.NLPTagger#tagText(java.
	 * lang. String)
	 */
	@Override
	public JCas processText(String text) throws SpecmateException {
		JCas jcas = null;
		try {
			jcas = JCasFactory.createJCas();
			jcas.setDocumentText(text);
			jcas.setDocumentLanguage("en");
			SimplePipeline.runPipeline(jcas, engine);
		} catch (Throwable e) {
			// Catch any kind of runtime or checked exception
			throw new SpecmateInternalException(ErrorCode.INTERNAL_PROBLEM,
					"NLP: Tagging failed. Reason: " + e.getMessage());
		}
		return jcas;

	}

	@Reference
	public void setLogService(LogService logService) {
		this.logService = logService;
	}

}

package com.specmate.nlp.internal.services;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;

import java.util.HashMap;
import java.util.Map;

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
import com.specmate.nlp.api.ELanguage;
import com.specmate.nlp.api.INLPService;
import com.specmate.nlp.util.NLPUtil;

import de.tudarmstadt.ukp.dkpro.core.maltparser.MaltParser;
import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpChunker;
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
public class NLPServiceImpl implements INLPService {

	private Map<String, AnalysisEngine> engines = new HashMap<String, AnalysisEngine>();
	private LogService logService;

	/**
	 * Start the NLP Engine
	 *
	 * @throws SpecmateException
	 */
	@Activate
	public void activate() throws SpecmateException {
		// logService.log(org.osgi.service.log.LogService.LOG_DEBUG, "OpenNLP
		createGermanPipeline();
		createEnglishPipeline();

	}

	private void createEnglishPipeline() throws SpecmateInternalException {
		logService.log(LogService.LOG_DEBUG, "Initializing english NLP pipeline");

		AnalysisEngineDescription segmenter = null;
		AnalysisEngineDescription posTagger = null;
		AnalysisEngineDescription parser = null;
		AnalysisEngineDescription chunker = null;
		AnalysisEngineDescription dependencyParser = null;

		String lang = ELanguage.EN.getLanguage();

		try {
			segmenter = createEngineDescription(OpenNlpSegmenter.class, OpenNlpSegmenter.PARAM_LANGUAGE, lang);
			posTagger = createEngineDescription(OpenNlpPosTagger.class, OpenNlpPosTagger.PARAM_LANGUAGE, lang,
					OpenNlpPosTagger.PARAM_VARIANT, "maxent");
			chunker = createEngineDescription(OpenNlpChunker.class, OpenNlpChunker.PARAM_LANGUAGE, lang);
			dependencyParser = createEngineDescription(MaltParser.class, MaltParser.PARAM_LANGUAGE, lang,
					MaltParser.PARAM_IGNORE_MISSING_FEATURES, true);
			parser = createEngineDescription(OpenNlpParser.class, OpenNlpParser.PARAM_PRINT_TAGSET, true,
					OpenNlpParser.PARAM_LANGUAGE, lang, OpenNlpParser.PARAM_WRITE_PENN_TREE, true,
					OpenNlpParser.PARAM_WRITE_POS, true);

			AnalysisEngine engine = createEngine(
					createEngineDescription(segmenter, posTagger, chunker, dependencyParser, parser));

			engines.put(lang, engine);
		} catch (Throwable e) {
			throw new SpecmateInternalException(ErrorCode.NLP,
					"OpenNLP NLP service failed when starting. Reason: " + e.getMessage());
		}
	}

	private void createGermanPipeline() throws SpecmateInternalException {
		logService.log(LogService.LOG_DEBUG, "Initializing german NLP pipeline");
		AnalysisEngineDescription segmenter = null;
		AnalysisEngineDescription posTagger = null;
		AnalysisEngineDescription chunker = null;
		AnalysisEngineDescription parser = null;
		AnalysisEngineDescription dependencyParser = null;

		String lang = ELanguage.DE.getLanguage();

		try {
			segmenter = createEngineDescription(OpenNlpSegmenter.class, OpenNlpSegmenter.PARAM_LANGUAGE, lang);
			posTagger = createEngineDescription(OpenNlpPosTagger.class, OpenNlpPosTagger.PARAM_LANGUAGE, lang,
					OpenNlpPosTagger.PARAM_VARIANT, "maxent");
			chunker = createEngineDescription(OpenNlpChunker.class, OpenNlpParser.PARAM_PRINT_TAGSET, true,
					OpenNlpChunker.PARAM_LANGUAGE, lang, OpenNlpChunker.PARAM_MODEL_LOCATION,
					"classpath:/models/de-chunker.bin");
			dependencyParser = createEngineDescription(MaltParser.class, MaltParser.PARAM_LANGUAGE, lang,
					MaltParser.PARAM_IGNORE_MISSING_FEATURES, true, MaltParser.PARAM_MODEL_LOCATION,
					"classpath:/models/de-dependencies.mco");
			parser = createEngineDescription(OpenNlpParser.class, OpenNlpParser.PARAM_PRINT_TAGSET, true,
					OpenNlpParser.PARAM_LANGUAGE, lang, OpenNlpParser.PARAM_WRITE_PENN_TREE, false,
					OpenNlpParser.PARAM_WRITE_POS, false, OpenNlpParser.PARAM_MODEL_LOCATION,
					"classpath:/models/de-parser-chunking.bin");

			AnalysisEngine engine = createEngine(
					createEngineDescription(segmenter, posTagger, chunker, parser, dependencyParser));

			engines.put(lang, engine);
		} catch (Throwable e) {
			// logService.log(LogService.LOG_ERROR, "OpenNLP NLP service failed
			// when starting. Reason: " + e.getMessage());
			throw new SpecmateInternalException(ErrorCode.NLP,
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
	public JCas processText(String text, ELanguage language) throws SpecmateException {
		AnalysisEngine engine = engines.get(language.getLanguage());
		if (engine == null) {
			throw new SpecmateInternalException(ErrorCode.NLP,
					"No analysis engine for language " + language.getLanguage() + " available.");
		}
		JCas jcas = null;
		try {
			jcas = JCasFactory.createJCas();
			jcas.setDocumentText(text);
			jcas.setDocumentLanguage(language.getLanguage());
			SimplePipeline.runPipeline(jcas, engine);
		} catch (Throwable e) {
			// Catch any kind of runtime or checked exception
			throw new SpecmateInternalException(ErrorCode.NLP, "NLP: Tagging failed. Reason: " + e.getMessage());
		}
		NLPUtil.refineNpChunks(jcas);
		return jcas;
	}

	@Override
	public ELanguage detectLanguage(String text) {
		// FIXME: detection is only placeholder for real language detection
		String lower = text.toLowerCase();
		if (lower.contains("wenn") || lower.contains("der") || lower.contains("die") || lower.contains("das")
				|| lower.contains("ein")) {
			return ELanguage.DE;
		} else {
			return ELanguage.EN;
		}
	}

	@Reference
	public void setLogService(LogService logService) {
		this.logService = logService;
	}

}

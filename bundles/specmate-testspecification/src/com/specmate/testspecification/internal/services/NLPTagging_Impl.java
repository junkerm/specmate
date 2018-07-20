package com.specmate.testspecification.internal.services;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReaderDescription;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.fit.pipeline.JCasIterable;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import com.specmate.common.SpecmateException;

import de.tudarmstadt.ukp.dkpro.core.io.text.StringReader;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordCoreferenceResolver;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordDependencyConverter;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordLemmatizer;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordNamedEntityRecognizer;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordParser;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordPosTagger;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordSegmenter;

/**
 * Service to tag a text with the DKPro NLP-Framework
 * 
 * @author Andreas Wehrle
 *
 */
@Component(immediate = true)
public class NLPTagging_Impl implements NLPTagger {
	AnalysisEngine engine;
	private LogService logService;

	/**
	 * Start the NLP Engine
	 * 
	 * @throws SpecmateException
	 */
	@Activate
	public void activate() throws SpecmateException {
		logService.log(org.osgi.service.log.LogService.LOG_INFO, "NLP: DKPro is starting");
		try {
			engine = createEngine(createEngineDescription(createEngineDescription(StanfordSegmenter.class),
					createEngineDescription(StanfordPosTagger.class),
					createEngineDescription(StanfordNamedEntityRecognizer.class),
					createEngineDescription(StanfordLemmatizer.class), createEngineDescription(StanfordParser.class),
					createEngineDescription(StanfordDependencyConverter.class),
					createEngineDescription(StanfordCoreferenceResolver.class)));
			tagText("Init");
			logService.log(org.osgi.service.log.LogService.LOG_INFO, "NLP: DKPro started");

		} catch (ResourceInitializationException e) {
			e.printStackTrace();
			throw new SpecmateException("NLP: Initialisation failed", e);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.specmate.testspecification.internal.services.NLPTagger#tagText(java.lang.
	 * String)
	 */
	public JCas tagText(String text) throws SpecmateException {
		long start = System.currentTimeMillis();
		JCas jcas = null;
		try {
			jcas = JCasFactory.createJCas();
			jcas.setDocumentText(text);
			jcas.setDocumentLanguage("en");
			SimplePipeline.runPipeline(jcas, engine);
		} catch (UIMAException e) {
			e.printStackTrace();
			throw new SpecmateException("NLP: Tagging failed", e);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logService.log(org.osgi.service.log.LogService.LOG_INFO,
				"Time: " + (System.currentTimeMillis() - start) + "ms");
		return jcas;

	}

	@Reference
	public void setLogService(LogService logService) {
		this.logService = logService;
	}

}

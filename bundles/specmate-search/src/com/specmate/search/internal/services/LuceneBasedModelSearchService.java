package com.specmate.search.internal.services;

import static com.specmate.search.internal.config.LuceneBasedSearchServiceConfig.KEY_LUCENE_DB_LOCATION;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.SearcherManager;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.osgi.service.log.LogService;

import com.specmate.common.SpecmateException;
import com.specmate.common.SpecmateValidationException;
import com.specmate.persistency.IPersistencyService;
import com.specmate.persistency.IView;
import com.specmate.persistency.event.ModelEvent;
import com.specmate.search.api.IModelSearchService;

/**
 * Service that provides a search facility via Apache Lucene. It registers with
 * the OSGI event admin not listen for model changes.
 * 
 * @author junkerm
 *
 */
@Component(immediate = true, service = { IModelSearchService.class, EventHandler.class }, property = {
		"event.topics=com/specmate/model/notification", "event.topics=com/specmate/model/notification/*" })
public class LuceneBasedModelSearchService implements EventHandler, IModelSearchService {

	/** The persistency service to access the model data */
	private IPersistencyService persistencyService;

	/** The view through which the model data is accessed. */
	private IView view;

	/** The lucene directory. */
	private Directory directory;

	/** Index writer to modify the lucene database */
	private IndexWriter indexWriter;

	/** Search manager to perform searches on the lucene database. */
	private SearcherManager searcherManager;

	/** Service for message logging. */
	private LogService logService;

	/** Periodic scheduler for the periodic commit */
	private ScheduledExecutorService scheduledExecutor;

	/**
	 * Service activation
	 * 
	 * @throws SpecmateValidationException
	 */
	@Activate
	public void activate(Map<String, Object> properties) throws SpecmateException, SpecmateValidationException {
		validateConfig(properties);
		this.view = persistencyService.openView();

		try {
			String luceneLocation = (String) properties.get(KEY_LUCENE_DB_LOCATION);
			initializeLucene(luceneLocation);
			startPeriodicCommitThread();
		} catch (IOException e) {
			logService.log(LogService.LOG_ERROR, "Could not open index for full-text search.");
		}
	}

	/** Service Deactivation */
	@Deactivate
	public void deactivate() {
		if (this.indexWriter != null) {
			try {
				this.indexWriter.close();
			} catch (IOException e) {
				logService.log(LogService.LOG_ERROR, "Could not close full-text index.");
			}
		}
		if (this.scheduledExecutor != null) {
			this.scheduledExecutor.shutdown();
		}
	}

	private void startPeriodicCommitThread() {
		this.scheduledExecutor = Executors.newScheduledThreadPool(3);
		scheduledExecutor.scheduleWithFixedDelay(() -> {
			try {
				indexWriter.commit();
				searcherManager.maybeRefresh();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}, 30, 30, TimeUnit.SECONDS);
	}

	private void initializeLucene(String luceneLocation) throws IOException {
		Analyzer analyzer = new StandardAnalyzer();
		directory = FSDirectory.open(Paths.get(luceneLocation));
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		indexWriter = new IndexWriter(directory, config);
		this.searcherManager = new SearcherManager(indexWriter, null);
	}

	private void validateConfig(Map<String, Object> properties) throws SpecmateValidationException {
		String errMsg = "Missing config for %s";
		if (!properties.containsKey(KEY_LUCENE_DB_LOCATION)) {
			throw new SpecmateValidationException(String.format(errMsg, KEY_LUCENE_DB_LOCATION));
		}
	}

	@Override
	public List<EObject> search(String queryString) throws SpecmateException {
		Query query;
		try {
			query = constructQuery(queryString);
		} catch (ParseException e) {
			throw new SpecmateException("Could not parse lucne query", e);
		}

		IndexSearcher isearcher;
		try {
			isearcher = searcherManager.acquire();
		} catch (IOException e) {
			throw new SpecmateException("Could not aquire index searcher.", e);
		}

		try {

			ScoreDoc[] hits;
			hits = isearcher.search(query, 1000).scoreDocs;
			List<EObject> result = new ArrayList<>();
			// Iterate through the results:
			for (int i = 0; i < hits.length; i++) {
				Document hitDoc = isearcher.doc(hits[i].doc);
				String id = hitDoc.get(FieldConstants.FIELD_ID);
				EObject object = view.getObjectById(id);
				if (object != null) {
					result.add(object);
				}
			}
			return result;
		} catch (IOException e) {
			throw new SpecmateException("IO error while searching lucene database.", e);
		} finally {
			try {
				searcherManager.release(isearcher);
			} catch (IOException e) {

			}
		}

	}

	private Query constructQuery(String queryString) throws ParseException {
		// Parse a simple query that searches for "text":
		Analyzer analyzer = new StandardAnalyzer();
		QueryParser parser = new QueryParser(FieldConstants.FIELD_NAME, analyzer);
		String luceneQueryString = new StringBuilder("name:\"").append(queryString).append("\" description:\"")
				.append(queryString).append("\" id:\"").append(queryString).append("\"").toString();
		Query query;
		query = parser.parse(luceneQueryString);
		return query;
	}

	@Override
	public void handleEvent(Event event) {
		if (!(event instanceof ModelEvent)) {
			return;
		}
		ModelEvent modelEvent = (ModelEvent) event;
		switch (modelEvent.getType()) {
		case NEW:
			Document document = getDocumentForModelObject(modelEvent.getId(), modelEvent.getClassName(),
					modelEvent.getFeatureMap());
			if (document == null) {
				break;
			}
			try {
				indexWriter.addDocument(document);
			} catch (IOException e) {
				this.logService.log(LogService.LOG_ERROR, "Could not add document to index: " + event.getTopic(), e);
			}
			break;
		case DELETE:
			try {
				indexWriter.deleteDocuments(new Term(FieldConstants.FIELD_ID, modelEvent.getId()));
			} catch (IOException e) {
				this.logService.log(LogService.LOG_ERROR, "Could not delete document from index: " + event.getTopic(),
						e);
			}
			break;
		default:
			updateIndex(modelEvent.getId(), modelEvent.getFeatureMap());
		}
	}

	private void updateIndex(String id, Map<EStructuralFeature, Object> featureMap) {
		List<String> updateFields = Arrays.asList(FieldConstants.FIELD_NAME, FieldConstants.FIELD_DESCRIPTION);
		try {
			Field[] fields = featureMap.entrySet().stream()
					.filter(entry -> updateFields.contains(entry.getKey().getName()))
					.map(entry -> new Field(entry.getKey().getName(), (String) entry.getValue(), TextField.TYPE_STORED))
					.collect(Collectors.toList()).toArray(new Field[0]);
			indexWriter.updateDocValues(new Term(FieldConstants.FIELD_ID, id), fields);
		} catch (IOException e) {
			this.logService.log(LogService.LOG_ERROR, "Could not update index: " + id, e);
		}

	}

	private Document getDocumentForModelObject(String id, String className,
			Map<EStructuralFeature, Object> featureMap) {
		return DocumentFactory.create(className, id, featureMap);
	}

	@Reference
	public void setPersistency(IPersistencyService persistencyService) {
		this.persistencyService = persistencyService;
	}

	@Reference
	public void setLogService(LogService logService) {
		this.logService = logService;
	}

}

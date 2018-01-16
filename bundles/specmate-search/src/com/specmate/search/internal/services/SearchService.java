package com.specmate.search.internal.services;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.eclipse.emf.ecore.EObject;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.osgi.service.log.LogService;

import com.specmate.common.SpecmateException;
import com.specmate.model.base.ISpecmateModelObject;
import com.specmate.persistency.IPersistencyService;
import com.specmate.persistency.IView;
import com.specmate.persistency.event.ModelEvent;
import com.specmate.search.api.ISearchService;

@Component(immediate = true, service = { EventHandler.class, ISearchService.class }, property = {
		"event.topics=com/specmate/model/notification", "event.topics=com/specmate/model/notification/*" })
public class SearchService implements EventHandler, ISearchService {

	private IPersistencyService persistencyService;
	private IView view;
	private IndexWriter indexWriter;
	private LogService logService;
	private Directory directory;
	private DirectoryReader indexReader;

	@Activate
	public void activate() throws SpecmateException {
		this.view = persistencyService.openView();
		Analyzer analyzer = new StandardAnalyzer();
		try {
			directory = FSDirectory.open(Paths.get("./database/lucene"));
			IndexWriterConfig config = new IndexWriterConfig(analyzer);
			indexWriter = new IndexWriter(directory, config);
		} catch (IOException e) {
			logService.log(LogService.LOG_ERROR, "Could not open index for full-text search.");
		}
	}

	@Deactivate
	public void deactivate() {
		if (this.indexWriter != null) {
			try {
				this.indexWriter.close();
			} catch (IOException e) {
				logService.log(LogService.LOG_ERROR, "Could not close full-text index.");
			}
		}
	}

	@Override
	public List<EObject> search(String queryString) throws SpecmateException {
		IndexSearcher isearcher = new IndexSearcher(getIndexReader());
		// Parse a simple query that searches for "text":
		Analyzer analyzer = new StandardAnalyzer();
		QueryParser parser = new QueryParser("name", analyzer);
		String luceneQueryString = "name:\"" + queryString + "\" description:\"" + queryString + "\" id:\""
				+ queryString + "\"";
		Query query;
		try {
			query = parser.parse(luceneQueryString);
		} catch (ParseException e) {
			throw new SpecmateException("Could not parse lucne query", e);
		}
		ScoreDoc[] hits;
		try {
			hits = isearcher.search(query, 1000).scoreDocs;
			List<EObject> result = new ArrayList<>();
			// Iterate through the results:
			for (int i = 0; i < hits.length; i++) {
				Document hitDoc = isearcher.doc(hits[i].doc);
				String id = hitDoc.get("id");
				EObject object = view.getObjectById(id);
				if (object != null) {
					result.add(object);
				}
			}
			return result;
		} catch (IOException e) {
			throw new SpecmateException("IO error while searching lucene database.", e);
		}

	}

	private synchronized IndexReader getIndexReader() throws SpecmateException {
		if (indexReader == null) {
			try {
				indexReader = DirectoryReader.open(this.indexWriter);
			} catch (IOException e) {
				logService.log(LogService.LOG_ERROR, "Could not create reader for searching full-text index");
				throw new SpecmateException(e);
			}
		} else {
			DirectoryReader oldReader = indexReader;
			try {
				indexReader = DirectoryReader.openIfChanged(oldReader, indexWriter);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return indexReader;

	}

	@Override
	public void handleEvent(Event event) {
		if (!(event instanceof ModelEvent)) {
			return;
		}
		ModelEvent modelEvent = (ModelEvent) event;
		EObject modelObject;
		switch (modelEvent.getType()) {
		case NEW:
			modelObject = view.getObjectById(modelEvent.getId());
			Document document = getDocumentForModelObject(modelEvent.getId(), modelObject);
			if (document == null) {
				break;
			}
			try {
				indexWriter.addDocument(document);
			} catch (IOException e) {
				this.logService.log(LogService.LOG_ERROR, "Could not add document to index: " + event.getTopic());
			}
			break;
		case DELETE:
			try {
				indexWriter.deleteDocuments(new Term("id", modelEvent.getId()));
			} catch (IOException e) {
				this.logService.log(LogService.LOG_ERROR, "Could not delete document from index: " + event.getTopic());
			}
			break;
		default:
			modelObject = view.getObjectById(modelEvent.getId());
			updateIndex(modelEvent.getId(), modelObject);
		}
		try {
			indexWriter.commit();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void updateIndex(Object id, EObject modelObject) {
		// TODO Auto-generated method stub

	}

	private Document getDocumentForModelObject(String id, EObject modelObject) {
		if (!(modelObject instanceof ISpecmateModelObject)) {
			return null;
		}
		ISpecmateModelObject specmateModel = (ISpecmateModelObject) modelObject;
		Document doc = new Document();
		doc.add(new Field("id", id.toString(), TextField.TYPE_STORED));
		doc.add(new Field("type", specmateModel.eClass().getName(), TextField.TYPE_STORED));
		if (specmateModel.getName() != null) {
			doc.add(new Field("name", specmateModel.getName(), TextField.TYPE_STORED));
		}
		if (specmateModel.getDescription() != null) {
			doc.add(new Field("description", specmateModel.getDescription(), TextField.TYPE_STORED));
		}
		return doc;
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

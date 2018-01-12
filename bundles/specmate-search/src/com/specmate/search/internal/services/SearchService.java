package com.specmate.search.internal.services;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.eclipse.emf.ecore.EObject;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.osgi.service.log.LogService;

import com.specmate.common.SpecmateException;
import com.specmate.model.base.ISpecmateModelObject;
import com.specmate.persistency.IPersistencyService;
import com.specmate.persistency.IView;
import com.specmate.persistency.event.ModelEvent;

@Component(immediate = true, property = { "event.topics=com/specmate/model/notification",
		"event.topics=com/specmate/model/notification/*" })
public class SearchService implements EventHandler {

	private IPersistencyService persistencyService;
	private IView view;
	private IndexWriter indexWriter;
	private LogService logService;

	@Activate
	public void activate() throws SpecmateException {
		this.view = persistencyService.openView();
		Analyzer analyzer = new StandardAnalyzer();

		// Store the index in memory:
		// Directory directory = new RAMDirectory();
		// To store an index on disk, use this instead:
		Directory directory = null;
		DirectoryReader ireader = null;
		try {
			directory = FSDirectory.open(Paths.get("./database/lucene"));
			IndexWriterConfig config = new IndexWriterConfig(analyzer);
			indexWriter = new IndexWriter(directory, config);
			//
			// // Now search the index:
			// ireader = DirectoryReader.open(directory);
			// IndexSearcher isearcher = new IndexSearcher(ireader);
			// // Parse a simple query that searches for "text":
			// QueryParser parser = new QueryParser("fieldname", analyzer);
			// Query query = parser.parse("text");
			// ScoreDoc[] hits = isearcher.search(query, 1000).scoreDocs;
			// // Iterate through the results:
			// for (int i = 0; i < hits.length; i++) {
			// Document hitDoc = isearcher.doc(hits[i].doc);
			// }

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

		}

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
				indexWriter.deleteDocuments(new Term("id", modelEvent.getId().toString()));
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

	private Document getDocumentForModelObject(Object id, EObject modelObject) {
		if (!(modelObject instanceof ISpecmateModelObject)) {
			return null;
		}
		ISpecmateModelObject specmateModel = (ISpecmateModelObject) modelObject;
		Document doc = new Document();
		doc.add(new Field("id", id.toString(), TextField.TYPE_STORED));
		doc.add(new Field("name", specmateModel.getName(), TextField.TYPE_STORED));
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

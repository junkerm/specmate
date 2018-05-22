package com.specmate.search.internal.services;

import java.util.Map;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.specmate.model.base.BasePackage;

public class DocumentFactory {

	public static Document create(String className, String id, String project,
			Map<EStructuralFeature, Object> featureMap) {
		switch (className) {
		case "TestProcedure":
			return createTestProcedureDocument(className, id, project, featureMap);
		default:
			return createGenericDocument(className, id, project, featureMap);
		}
	}

	private static Document createDocument(String className, String id, String extId, String project, String name,
			String description) {
		Document doc = new Document();
		doc.add(new Field(FieldConstants.FIELD_ID, id, TextField.TYPE_STORED));
		doc.add(new Field(FieldConstants.FIELD_PROJECT, project, TextField.TYPE_STORED));
		doc.add(new Field("type", className.toLowerCase(), TextField.TYPE_STORED));
		if (extId != null) {
			doc.add(new Field(FieldConstants.FIELD_EXTID, extId, TextField.TYPE_STORED));
		}
		if (name != null) {
			doc.add(new Field(FieldConstants.FIELD_NAME, name.toLowerCase(), TextField.TYPE_STORED));
		}
		if (description != null) {
			doc.add(new Field(FieldConstants.FIELD_DESCRIPTION, description.toLowerCase(), TextField.TYPE_STORED));
		}
		return doc;
	}

	private static Document createGenericDocument(String className, String id, String project,
			Map<EStructuralFeature, Object> featureMap) {
		String name = (String) featureMap.get(BasePackage.Literals.INAMED__NAME);
		String description = (String) featureMap.get(BasePackage.Literals.IDESCRIBED__DESCRIPTION);
		String extId = (String) featureMap.get(BasePackage.Literals.IEXTERNAL__EXT_ID);
		return createDocument(className, id, project, extId, name, description);
	}

	private static Document createTestProcedureDocument(String className, String id, String project,
			Map<EStructuralFeature, Object> featureMap) {
		// TODO include test steps in document
		String name = (String) featureMap.get(BasePackage.Literals.INAMED__NAME);
		String description = (String) featureMap.get(BasePackage.Literals.IDESCRIBED__DESCRIPTION);
		return createDocument(className, id, project, null, name, description);
	}
}

package com.specmate.search.internal.services;

import java.util.Map;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.specmate.model.base.BasePackage;

public class DocumentFactory {

	public static Document create(String className, String id, Map<EStructuralFeature, Object> featureMap) {
		switch (className) {
		case "TestProcedure":
			return createTestProcedureDocument(className, id, featureMap);
		default:
			return createGenericDocument(className, id, featureMap);
		}
	}

	private static Document createDocument(String className, String id, String name, String description) {
		Document doc = new Document();
		doc.add(new Field(FieldConstants.FIELD_ID, id, TextField.TYPE_STORED));
		doc.add(new Field("type", className, TextField.TYPE_STORED));
		if (name != null) {
			doc.add(new Field(FieldConstants.FIELD_NAME, name, TextField.TYPE_STORED));
		}
		if (description != null) {
			doc.add(new Field(FieldConstants.FIELD_DESCRIPTION, description, TextField.TYPE_STORED));
		}
		return doc;
	}

	private static Document createGenericDocument(String className, String id,
			Map<EStructuralFeature, Object> featureMap) {
		String name = (String) featureMap.get(BasePackage.Literals.INAMED__NAME);
		String description = (String) featureMap.get(BasePackage.Literals.IDESCRIBED__DESCRIPTION);
		return createDocument(className, id, name, description);
	}

	private static Document createTestProcedureDocument(String className, String id,
			Map<EStructuralFeature, Object> featureMap) {
		// TODO include test steps in document
		String name = (String) featureMap.get(BasePackage.Literals.INAMED__NAME);
		String description = (String) featureMap.get(BasePackage.Literals.IDESCRIBED__DESCRIPTION);
		return createDocument(className, id, name, description);
	}
}

package com.specmate.migration.h2;

import java.sql.Connection;
import java.util.Date;

import org.osgi.service.log.LogService;

import com.specmate.common.SpecmateException;

public abstract class SQLMapper {
	protected static final String SPECMATE_URL = "http://specmate.com/";
	protected Connection connection;
	protected String packageName;
	protected String sourceVersion;
	protected String targetVersion;
	protected LogService logService;

	public SQLMapper(Connection connection, LogService logService, String packageName, String sourceVersion,
			String targetVersion) {
		this.connection = connection;
		this.logService = logService;
		this.packageName = packageName;
		this.sourceVersion = sourceVersion;
		this.targetVersion = targetVersion;
	}

	protected String insertExternalObjectReference(String objectName) throws SpecmateException {
		return getInsertExternalReferenceQuery(getBaseURI(objectName), getLatestId() - 1);
	}

	protected String insertExternalAttributeReference(String objectName, String attributeName)
			throws SpecmateException {

		String attributeUri = getBaseURI(objectName) + "/" + attributeName;
		return getInsertExternalReferenceQuery(attributeUri, getLatestId() - 1);
	}

	protected String renameExternalReference(String objectName, String oldAttributeName, String newAttributeName)
			throws SpecmateException {
		String baseUri = SPECMATE_URL + targetVersion + "/" + packageName + "#//" + objectName + "/";
		String oldUri = baseUri + oldAttributeName;
		String newUri = baseUri + newAttributeName;
		return "UPDATE CDO_EXTERNAL_REFS SET uri = '" + newUri + "' WHERE uri = '" + oldUri + "'";
	}

	private String getInsertExternalReferenceQuery(String uri, int id) {
		Date now = new Date();
		return "INSERT INTO CDO_EXTERNAL_REFS (ID, URI, COMMITTIME) " + "VALUES (" + id + ", '" + uri + "', "
				+ now.getTime() + ")";
	}

	private int getLatestId() throws SpecmateException {
		return SQLUtil.getIntResult("SELECT id FROM CDO_EXTERNAL_REFS ORDER BY id ASC", 1, connection);
	}

	private String getBaseURI(String objectName) {
		return SPECMATE_URL + targetVersion + "/" + packageName + "#//" + objectName;
	}
}

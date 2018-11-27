package com.specmate.dbprovider.api.migration;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.specmate.common.SpecmateException;

public abstract class SQLMapper {
	protected static final String SPECMATE_URL = "http://specmate.com/";
	protected Connection connection;
	protected String packageName;
	protected String sourceVersion;
	protected String targetVersion;

	public SQLMapper(Connection connection, String packageName, String sourceVersion, String targetVersion) {
		this.connection = connection;
		this.packageName = packageName;
		this.sourceVersion = sourceVersion;
		this.targetVersion = targetVersion;
	}

	protected boolean hasDefault(Object defaultValue) {
		return defaultValue != null ? true : false;
	}

	protected void executeChange(String alterString, String objectName, String attributeName, boolean setDefault)
			throws SpecmateException {
		String failmsg = "Migration: Could not add column " + attributeName + " to table " + objectName + ".";
		List<String> queries = new ArrayList<>();
		queries.add(alterString);

		if (setDefault) {
			queries.add("UPDATE " + objectName + " SET " + attributeName + " = DEFAULT");
		}

		SQLUtil.executeStatements(queries, connection, failmsg);
	}
}

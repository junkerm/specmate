package com.specmate.migration.h2;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.specmate.common.SpecmateException;

public class ObjectToSQLMapper {
	protected Connection connection;

	public ObjectToSQLMapper(Connection connection) {
		this.connection = connection;
	}
	
	public void newObject(String tableName, List<String> attributeNames, String packageName, String version) throws SpecmateException {
		String failmsg = "Migration: Could not add table " + tableName + ".";
		List<String> queries = new ArrayList<>();
		
		queries.add("CREATE TABLE " + tableName + "(" +
				"CDO_ID BIGINT NOT NULL, " +
				"CDO_VERSION INTEGER NOT NULL, " +
				"CDO_CREATED BIGINT NOT NULL, " +
				"CDO_REVISED BIGINT NOT NULL, " +
				"CDO_RESOURCE BIGINT NOT NULL, " +
				"CDO_CONTAINER BIGINT NOT NULL, " +
				"CDO_FEATURE INTEGER NOT NULL)");
		
		queries.add("CREATE UNIQUE INDEX " + 
				SQLUtil.createRandomIdentifier("PRIMARY_KEY_" + tableName) + 
				" ON " + tableName + " (CDO_ID ASC, CDO_VERSION ASC)");
		
		queries.add("CREATE INDEX " + 
				SQLUtil.createRandomIdentifier("INDEX_" + tableName) 
				+ " ON " + tableName + " (CDO_REVISED ASC)");
		
		queries.add("ALTER TABLE " + tableName + " ADD CONSTRAINT " + 
				SQLUtil.createRandomIdentifier("CONSTRAINT_" + tableName) + 
				" PRIMARY KEY (CDO_ID, CDO_VERSION)");
		
		queries.addAll(getExtRefQueries(tableName, attributeNames, packageName, version));
		SQLUtil.executeStatements(queries, connection, failmsg);
	}
	
	private List<String> getExtRefQueries(String tableName, List<String> attributeNames, String packageName, String version) throws SpecmateException {
		int id = SQLUtil.getIntResult("SELECT id FROM CDO_EXTERNAL_REFS ORDER BY ID ASC LIMIT 1", 1, connection);
		
		List<String> queries = new ArrayList<>();
		String baseUri = "http://specmate.com/" + version + "/" + packageName + "#//" + tableName;
		id = id - 1;
		queries.add(getExtRefQuery(baseUri, id));
		for (String name : attributeNames) {
			String attributeUri = baseUri + "/" + name;
			id = id - 1;
			queries.add(getExtRefQuery(attributeUri, id));
		}
		
		return queries;
	}
	
	private String getExtRefQuery(String uri, int id) {
		Date now = new Date();
		return "INSERT INTO CDO_EXTERNAL_REFS (ID, URI, COMMITTIME) " +
				"VALUES (" + id + ", '" + uri + "', " + now.getTime() + ")";
	}
}

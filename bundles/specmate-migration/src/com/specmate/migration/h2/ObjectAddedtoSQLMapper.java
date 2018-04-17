package com.specmate.migration.h2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.specmate.common.SpecmateException;

public class ObjectAddedtoSQLMapper {
	protected Connection connection;

	public ObjectAddedtoSQLMapper(Connection connection) {
		this.connection = connection;
	}
	
	/*
	 * L64
	*/
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
		int id = 0;
		try {
			PreparedStatement st = SQLUtil.executeStatement("SELECT id FROM CDO_EXTERNAL_REFS ORDER BY ID ASC LIMIT 1;", connection);
			ResultSet result = st.getResultSet();
			if (result.next()) {
				id = result.getInt(1);
			}
		} catch (SQLException e) {
			throw new SpecmateException("Migration: Could not get retrieve id. " + e.getMessage());
		}
		
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

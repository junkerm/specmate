package com.specmate.migration.h2;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.specmate.common.SpecmateException;

public class AttributeAddedtoSQLMapper {
	protected Connection connection;
	
	public AttributeAddedtoSQLMapper(Connection connection) {
		this.connection = connection;
	}
	
	public void migrateNewStringAttribute(String tableName, String attributeName, String defaultValue) throws SpecmateException {
		String alterString = "ALTER TABLE " + tableName + 
				" ADD COLUMN " + attributeName + 
				" VARCHAR(32672)";
		
		if (defaultValue != null) {
			alterString += " DEFAULT '" + defaultValue + "'";
		}
		
		alterDB(alterString, tableName, attributeName, defaultValue);
	}
	
	public void migrateNewBooleanAttribute(String tableName, String attributeName, Boolean defaultValue) throws SpecmateException {
		String alterString = "ALTER TABLE " + tableName + 
				" ADD COLUMN " + attributeName + 
				" BOOLEAN";

		if (defaultValue != null) {
			alterString += " DEFAULT " + defaultValue;
		}
		
		alterDB(alterString, tableName, attributeName, defaultValue);
	}
	
	public void migrateNewIntegerAttribute(String tableName, String attributeName, Integer defaultValue) throws SpecmateException {
		String alterString = "ALTER TABLE " + tableName + 
				" ADD COLUMN " + attributeName + 
				" INTEGER";
		
		if (defaultValue != null) {
			alterString += " DEFAULT " + defaultValue.intValue();
		}
		
		alterDB(alterString, tableName, attributeName, defaultValue);
	}
	
	public void migrateNewDoubleAttribute(String tableName, String attributeName, Double defaultValue) throws SpecmateException {
		String alterString = "ALTER TABLE " + tableName + 
				" ADD COLUMN " + attributeName + 
				" DOUBLE";

		if (defaultValue != null) {
			alterString += " DEFAULT " + defaultValue;
		}
		
		alterDB(alterString, tableName, attributeName, defaultValue);
	}
	
	public void migrateNewLongAttribute(String tableName, String attributeName, Long defaultValue) throws SpecmateException {
		String alterString = "ALTER TABLE " + tableName + 
				" ADD COLUMN " + attributeName + 
				" BIGINT";

		if (defaultValue != null) {
			alterString += " DEFAULT " + defaultValue;
		}
		
		alterDB(alterString, tableName, attributeName, defaultValue);
	}
	
	public void migrateNewReference(String tableName, String attributeName) throws SpecmateException {
		String failmsg = "Migration: Could not add column " + attributeName + " to table " + tableName + ".";
		String tableNameList = tableName + "_" + attributeName + "_LIST";
		List<String> queries = new ArrayList<>();
		queries.add("ALTER TABLE " + tableName + 
				" ADD COLUMN " + attributeName +
				" INTEGER");
		
		queries.add("CREATE TABLE " + tableNameList + " (" +
				"CDO_SOURCE BIGINT NOT NULL, " +
				"CDO_VERSION INTEGER NOT NULL, " +
				"CDO_IDX INTEGER NOT NULL, " +
				"CDO_VALUE BIGINT)");
		
		queries.add("CREATE UNIQUE INDEX " + 
				SQLUtil.createRandomIdentifier("PRIMARY_KEY_" + tableNameList) + 
				" ON " + tableNameList + " (CDO_SOURCE ASC, CDO_VERSION ASC, CDO_IDX ASC)");
		
		queries.add("ALTER TABLE " + tableNameList + " ADD CONSTRAINT " + 
				SQLUtil.createRandomIdentifier("CONSTRAINT_" + tableNameList) + 
				" PRIMARY KEY (CDO_SOURCE, CDO_VERSION, CDO_IDX)");
		
		SQLUtil.executeStatements(queries, connection, failmsg);
	}
	
	private void alterDB(String alterString, String tableName, String attributeName, Object defaultValue) throws SpecmateException {
		String failmsg = "Migration: Could not add column " + attributeName + " to table " + tableName + ".";
		List<String> queries = new ArrayList<>();
		queries.add(alterString);
		
		if (defaultValue != null) {
			queries.add("UPDATE " + tableName +
						" SET " + attributeName +
						" = DEFAULT");
		}
		
		SQLUtil.executeStatements(queries, connection, failmsg);
	}
}
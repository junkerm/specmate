package com.specmate.migration.h2;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.specmate.common.SpecmateException;

public class AttributeToSQLMapper extends SQLMapper {
	
	public AttributeToSQLMapper(Connection connection, String packageName, String sourceVersion, String targetVersion) {
		super(connection, packageName, sourceVersion, targetVersion);
	}
	
	public void migrateNewStringAttribute(String objectName, String attributeName, String defaultValue) throws SpecmateException {
		String alterString = "ALTER TABLE " + objectName + 
				" ADD COLUMN " + attributeName + 
				" VARCHAR(32672)";
		
		if (defaultValue != null) {
			alterString += " DEFAULT '" + defaultValue + "'";
		}
		
		alterDB(alterString, objectName, attributeName, defaultValue);
	}
	
	public void migrateNewBooleanAttribute(String objectName, String attributeName, Boolean defaultValue) throws SpecmateException {
		String alterString = "ALTER TABLE " + objectName + 
				" ADD COLUMN " + attributeName + 
				" BOOLEAN";

		if (defaultValue != null) {
			alterString += " DEFAULT " + defaultValue;
		}
		
		alterDB(alterString, objectName, attributeName, defaultValue);
	}
	
	public void migrateNewIntegerAttribute(String objectName, String attributeName, Integer defaultValue) throws SpecmateException {
		String alterString = "ALTER TABLE " + objectName + 
				" ADD COLUMN " + attributeName + 
				" INTEGER";
		
		if (defaultValue != null) {
			alterString += " DEFAULT " + defaultValue.intValue();
		}
		
		alterDB(alterString, objectName, attributeName, defaultValue);
	}
	
	public void migrateNewDoubleAttribute(String objectName, String attributeName, Double defaultValue) throws SpecmateException {
		String alterString = "ALTER TABLE " + objectName + 
				" ADD COLUMN " + attributeName + 
				" DOUBLE";

		if (defaultValue != null) {
			alterString += " DEFAULT " + defaultValue;
		}
		
		alterDB(alterString, objectName, attributeName, defaultValue);
	}
	
	public void migrateNewLongAttribute(String objectName, String attributeName, Long defaultValue) throws SpecmateException {
		String alterString = "ALTER TABLE " + objectName + 
				" ADD COLUMN " + attributeName + 
				" BIGINT";

		if (defaultValue != null) {
			alterString += " DEFAULT " + defaultValue;
		}
		
		alterDB(alterString, objectName, attributeName, defaultValue);
	}
	
	public void migrateNewReference(String objectName, String attributeName) throws SpecmateException {
		String failmsg = "Migration: Could not add column " + attributeName + " to table " + objectName + ".";
		String tableNameList = objectName + "_" + attributeName + "_LIST";
		List<String> queries = new ArrayList<>();
		queries.add("ALTER TABLE " + objectName + 
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
	
	public void migrateRenameAttribute(String objectName, String oldAttributeName, String newAttributeName) throws SpecmateException {
		String failmsg = "Migration: Could not rename column " + oldAttributeName + " in table " + objectName + ".";
		List<String> queries = new ArrayList<>();
		queries.add("ALTER TABLE " + objectName + " ALTER COLUMN " + oldAttributeName + " RENAME TO " + newAttributeName);
		queries.add(renameExternalReference(objectName, oldAttributeName, newAttributeName));
		SQLUtil.executeStatements(queries, connection, failmsg);
	}
	
	private void alterDB(String alterString, String objectName, String attributeName, Object defaultValue) throws SpecmateException {
		String failmsg = "Migration: Could not add column " + attributeName + " to table " + objectName + ".";
		List<String> queries = new ArrayList<>();
		queries.add(alterString);
		
		if (defaultValue != null) {
			queries.add("UPDATE " + objectName +
						" SET " + attributeName +
						" = DEFAULT");
		}
		
		List<String> attributeNames = new ArrayList<>();
		attributeNames.add(attributeName);
		
		queries.addAll(insertExternalReferences(objectName, attributeNames));
		
		SQLUtil.executeStatements(queries, connection, failmsg);
	}
}
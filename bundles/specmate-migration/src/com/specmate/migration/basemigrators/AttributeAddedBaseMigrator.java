package com.specmate.migration.basemigrators;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.specmate.common.SpecmateException;

public abstract class AttributeAddedBaseMigrator {
	protected Connection connection;
	
	public int migrateString(String table, String attributeName, String defaultValue) throws SpecmateException {
		String alterString = "ALTER TABLE " + table + 
				" ADD COLUMN " + attributeName + 
				" VARCHAR(32672)";
		
		if(defaultValue != null) {
			alterString += " DEFAULT '" + defaultValue + "'";
		}
		
		return alterDB(alterString, table, attributeName, defaultValue);
	}
	
	public int migrateBoolean(String table, String attributeName, Boolean defaultValue) throws SpecmateException {
		String alterString = "ALTER TABLE " + table + 
				" ADD COLUMN " + attributeName + 
				" BOOLEAN";

		if(defaultValue != null) {
			alterString += " DEFAULT " + defaultValue;
		}
		
		return alterDB(alterString, table, attributeName, defaultValue);
	}
	
	public int migrateInteger(String table, String attributeName, Integer defaultValue) throws SpecmateException {
		String alterString = "ALTER TABLE " + table + 
				" ADD COLUMN " + attributeName + 
				" INTEGER";
		
		if(defaultValue != null) {
			alterString += " DEFAULT " + defaultValue.intValue();
		}
		
		return alterDB(alterString, table, attributeName, defaultValue);
	}
	
	public int migrateDouble(String table, String attributeName, Double defaultValue) throws SpecmateException {
		String alterString = "ALTER TABLE " + table + 
				" ADD COLUMN " + attributeName + 
				" DOUBLE";

		if(defaultValue != null) {
			alterString += " DEFAULT " + defaultValue;
		}
		
		return alterDB(alterString, table, attributeName, defaultValue);
	}
	
	private int alterDB(String alterString, String table, String attributeName, Object defaultValue) throws SpecmateException {
		String failmsg = "Migration: Could not add column " + attributeName + " to table " + table + ".";
		PreparedStatement alterStmt = null;
		PreparedStatement updateStmt = null;
		int affectedRows = 0;
		
		String updateString = null;
		if(defaultValue != null) {
			updateString = "UPDATE " + table +
						" SET " + attributeName +
						" = DEFAULT";
		}
		
		try {
			connection.setAutoCommit(false);
			
			alterStmt = connection.prepareStatement(alterString);
			alterStmt.execute();
			if(updateString != null) {
				updateStmt = connection.prepareStatement(updateString);
				affectedRows = updateStmt.executeUpdate();
			}
			
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException f) {
				throw new SpecmateException(failmsg + e.getMessage() + f.getMessage());
			}
			
			throw new SpecmateException(failmsg + e.getMessage());
		} finally {
			try {
				if(alterStmt != null) {
					alterStmt.close();
				}
				if(updateStmt != null) {
					updateStmt.close();
				}
			
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				throw new SpecmateException(failmsg);
			}
		}
		
		return affectedRows;
	}
}

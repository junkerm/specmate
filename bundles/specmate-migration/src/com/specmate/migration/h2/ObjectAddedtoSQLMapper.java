package com.specmate.migration.h2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import com.specmate.common.SpecmateException;

public class ObjectAddedtoSQLMapper {
	protected Connection connection;
	private Date now;

	public ObjectAddedtoSQLMapper(Connection connection) {
		this.connection = connection;
		now = new Date();
	}
	
	public void newObject(String name) throws SpecmateException {
		String createTable = "CREATE TABLE " + name + "(" +
				"CDO_ID BIGINT NOT NULL, " +
				"CDO_VERSION INTEGER NOT NULL, " +
				"CDO_CREATED BIGINT NOT NULL, " +
				"CDO_REVISED BIGINT NOT NULL, " +
				"CDO_RESOURCE BIGINT NOT NULL, " +
				"CDO_CONTAINER BIGINT NOT NULL, " +
				"CDO_FEATURE INTEGER NOT NULL)";
		
		String createPkIndex = "CREATE UNIQUE INDEX " + 
				createRandomIdentifier("PRIMARY_KEY_" + name) + 
				" ON " + name + " (CDO_ID ASC, CDO_VERSION ASC)";
		
		String createIndex = "CREATE INDEX " + 
				createRandomIdentifier("INDEX_" + name) 
				+ " ON " + name + " (CDO_REVISED ASC)";
		
		String createConstraint = "ALTER TABLE " + name + " ADD CONSTRAINT " + 
				createRandomIdentifier("CONSTRAINT_" + name) + 
				" PRIMARY KEY (CDO_ID, CDO_VERSION)";
		
		String failmsg = "Migration: Could not add table " + name + ".";
		
		PreparedStatement createTableStmt = null;
		PreparedStatement createPkIndexStmt = null;
		PreparedStatement createIndexStmt = null;
		PreparedStatement createConstraintStmt = null;
		
		try {
			connection.setAutoCommit(false);
			
			createTableStmt = connection.prepareStatement(createTable);
			createTableStmt.execute();
		
			createPkIndexStmt = connection.prepareStatement(createPkIndex);
			createPkIndexStmt.execute();
			
			createIndexStmt = connection.prepareStatement(createIndex);
			createIndexStmt.execute();
			
			createConstraintStmt = connection.prepareStatement(createConstraint);
			createConstraintStmt.execute();
			
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
				closePreparedStatement(createTableStmt);
				closePreparedStatement(createPkIndexStmt);
				closePreparedStatement(createIndexStmt);
				closePreparedStatement(createConstraintStmt);
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				throw new SpecmateException(failmsg);
			}
		}
	}
	
	private String createRandomIdentifier(String prefix) {
		return prefix + "_" + now.getTime() + "_" + ThreadLocalRandom.current().nextInt(0, Integer.MAX_VALUE);
	}
	
	private void closePreparedStatement(PreparedStatement stmt) throws SQLException {
		if (stmt != null) {
			stmt.close();
		}
	}
}

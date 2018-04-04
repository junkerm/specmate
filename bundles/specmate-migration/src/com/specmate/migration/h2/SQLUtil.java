package com.specmate.migration.h2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.specmate.common.SpecmateException;

public class SQLUtil {
	public static PreparedStatement executeStatement(String sql, Connection connection) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.execute();
		return stmt;
	}
	
	public static void executeStatements(List<String> queries, Connection connection, String failmsg) throws SpecmateException {
		List<PreparedStatement> statements = new ArrayList<>();
		
		try {
			connection.setAutoCommit(false);
			for (String query : queries) {
				statements.add(executeStatement(query, connection));
			}
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException f) {
				throw new SpecmateException(failmsg + " " + e.getMessage() + " " + f.getMessage());
			}
			
			throw new SpecmateException(failmsg + " " + e.getMessage());
		} finally {
			try {
				closePreparedStatements(statements);
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				throw new SpecmateException(failmsg + " " + e.getMessage());
			}
		}
	}
	
	public static String createRandomIdentifier(String prefix) {
		Date now = new Date();
		return prefix + "_" + now.getTime() + "_" + ThreadLocalRandom.current().nextInt(0, Integer.MAX_VALUE);
	}
	
	public static void closePreparedStatement(PreparedStatement stmt) throws SQLException {
		if (stmt != null) {
			stmt.close();
		}
	}
	
	public static void closePreparedStatements(List<PreparedStatement> statements) throws SQLException {
		for(PreparedStatement stmt : statements) {
			closePreparedStatement(stmt);
		}
	}
}

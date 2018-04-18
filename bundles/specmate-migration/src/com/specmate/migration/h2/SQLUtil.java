package com.specmate.migration.h2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
	
	public static int getIntResult(String query, int resultIndex, Connection connection) throws SpecmateException {
		String failmsg = "Could not retrieve integer value from column " + resultIndex + ".";
		int res = 0;
		ResultSet result = null;
		try {
			PreparedStatement st = SQLUtil.executeStatement(query, connection);
			result = st.getResultSet(); 
			if (result != null && result.next()) {
				res = result.getInt(resultIndex);
			} else {
				throw new SpecmateException(failmsg);
			}
		} catch (SQLException e) {
			throw new SpecmateException(failmsg + " " + e.getMessage());
		} finally {
			if (result != null) {
				try {
					result.close();
				} catch (SQLException e) {
					throw new SpecmateException("Could not close result set. " + e.getMessage());
				}
			}
		}
		
		return res;
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

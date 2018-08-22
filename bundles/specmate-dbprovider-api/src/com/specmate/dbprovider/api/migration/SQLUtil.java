package com.specmate.dbprovider.api.migration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.specmate.common.SpecmateException;

public class SQLUtil {
	private static final int MAX_ID_LENGTH = 30; // Oracle limit for identifiers
	private static int seqId = 0;

	public static void executeStatement(String query, Connection connection, String failmsg) throws SpecmateException {
		List<String> queries = new ArrayList<>();
		queries.add(query);
		executeStatements(queries, connection, failmsg);
	}

	public static void executeStatements(List<String> queries, Connection connection, String failmsg)
			throws SpecmateException {
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
				e.setNextException(f);
				throw new SpecmateException(failmsg, e);
			}

			throw new SpecmateException(failmsg, e);
		} finally {
			try {
				closePreparedStatements(statements);
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				throw new SpecmateException(failmsg, e);
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
			throw new SpecmateException(failmsg, e);
		} finally {
			if (result != null) {
				try {
					result.close();
				} catch (SQLException e) {
					throw new SpecmateException("Could not close result set.", e);
				}
			}
		}

		return res;
	}

	public static ResultSet getResult(String query, Connection connection) throws SpecmateException {
		String failmsg = "Could not retrieve result from query: " + query + ".";
		ResultSet result = null;
		try {
			PreparedStatement st = SQLUtil.executeStatement(query, connection);
			result = st.getResultSet();
			if (result == null) {
				throw new SpecmateException(failmsg);
			}
		} catch (SQLException e) {
			throw new SpecmateException(failmsg, e);
		}

		return result;
	}

	public static void closeResult(ResultSet result) throws SpecmateException {
		if (result != null) {
			try {
				result.close();
			} catch (SQLException e) {
				throw new SpecmateException("Could not close result set.", e);
			}
		}
	}

	public static String createTimebasedIdentifier(String prefix) {
		Date now = new Date();
		String id = prefix + "_" + now.getTime() + "_" + seqId;
		seqId++;
		assert (id.length() <= MAX_ID_LENGTH);
		return id;
	}

	public static void closePreparedStatement(PreparedStatement stmt) throws SQLException {
		if (stmt != null) {
			stmt.close();
		}
	}

	public static void closePreparedStatements(List<PreparedStatement> statements) throws SQLException {
		for (PreparedStatement stmt : statements) {
			closePreparedStatement(stmt);
		}
	}

	private static PreparedStatement executeStatement(String sql, Connection connection) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.execute();
		return stmt;
	}
}

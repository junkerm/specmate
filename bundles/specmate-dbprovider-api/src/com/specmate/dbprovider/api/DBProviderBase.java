package com.specmate.dbprovider.api;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.specmate.common.exception.SpecmateException;
import com.specmate.common.exception.SpecmateInternalException;
import com.specmate.model.administration.ErrorCode;

public abstract class DBProviderBase implements IDBProvider {
	protected Connection connection;
	protected String jdbcConnection;
	protected List<DBConfigChangedCallback> cbRegister = new ArrayList<>();

	@Override
	public void registerDBConfigChangedCallback(DBConfigChangedCallback cb) {
		cbRegister.add(cb);
	}

	@Override
	public void unregisterDBConfigChangedCallback(DBConfigChangedCallback cb) {
		cbRegister.remove(cb);
	}

	protected void closeConnection() throws SpecmateException {
		if (this.connection != null) {
			try {
				connection.close();
				connection = null;
			} catch (SQLException e) {
				throw new SpecmateInternalException(ErrorCode.PERSISTENCY, "Could not close connection.", e);
			}
		}
	}
}

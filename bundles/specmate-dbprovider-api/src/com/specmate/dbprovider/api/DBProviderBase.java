package com.specmate.dbprovider.api;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.specmate.common.SpecmateException;

public abstract class DBProviderBase implements IDBProvider {
	protected Connection connection;
	protected String jdbcConnection;
	protected String repository;
	protected String resource;
	protected List<DBConfigChangedCallback> cbRegister = new ArrayList<>();

	@Override
	public String getRepository() {
		return this.repository;
	}

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
				throw new SpecmateException("Could not close connection.", e);
			}
		}
	}
}

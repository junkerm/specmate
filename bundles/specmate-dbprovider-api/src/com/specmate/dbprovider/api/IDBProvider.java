package com.specmate.dbprovider.api;

import java.sql.Connection;

import org.eclipse.emf.cdo.server.IStore;

import com.specmate.common.exception.SpecmateException;
import com.specmate.dbprovider.api.migration.IAttributeToSQLMapper;
import com.specmate.dbprovider.api.migration.IObjectToSQLMapper;

/**
 * Allows to interact with database back-ends without knowing the particular
 * implementation.
 */
public interface IDBProvider {

	/**
	 * Opens an SQL connection. Clients must not close the connection.
	 * Implementations must make sure that only one connection is created and
	 * eventually closed.
	 */
	public Connection getConnection() throws SpecmateException;

	/**
	 * Register a client callback that can be called when the database
	 * configuration changes.
	 */
	public void registerDBConfigChangedCallback(DBConfigChangedCallback cb);

	/**
	 * Cancels the registration
	 */
	public void unregisterDBConfigChangedCallback(DBConfigChangedCallback cb);

	/**
	 * Creates a new, database specific, store object. CDO is responsible for
	 * releasing the resource.
	 */
	public IStore createStore() throws SpecmateException;

	/**
	 * @return true if the database is empty or does not exist, false otherwise
	 */
	public boolean isVirginDB() throws SpecmateException;

	/**
	 * Creates a new, database specific, attribute to SQL mapper that provides
	 * convenience functions for database migrations.
	 */
	public IAttributeToSQLMapper getAttributeToSQLMapper(String packageName, String sourceVersion, String targetVersion)
			throws SpecmateException;

	/**
	 * Creates a new, database specific, object to SQL mapper that provides
	 * convenience functions for database migrations.
	 */
	public IObjectToSQLMapper getObjectToSQLMapper(String packageName, String sourceVersion, String targetVersion)
			throws SpecmateException;

	/**
	 * Creates the db specific literal for TRUE
	 */
	public String getTrueLiteral();
}

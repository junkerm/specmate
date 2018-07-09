package com.specmate.dbprovider.api;

import java.sql.Connection;

import org.eclipse.emf.cdo.server.IStore;

import com.specmate.common.SpecmateException;

public interface IDBProvider {

	public Connection getConnection() throws SpecmateException;

	public void closeConnection() throws SpecmateException;

	public void readConfig() throws SpecmateException;

	public String getResource();

	public String getRepository();

	public IStore getStore() throws SpecmateException;

	public boolean isVirginDB() throws SpecmateException;
}

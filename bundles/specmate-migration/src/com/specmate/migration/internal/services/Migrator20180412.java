package com.specmate.migration.internal.services;

import java.sql.Connection;

import org.osgi.service.component.annotations.Component;

import com.specmate.common.exception.SpecmateException;
import com.specmate.dbprovider.api.IDBProvider;
import com.specmate.dbprovider.api.migration.IAttributeToSQLMapper;
import com.specmate.dbprovider.api.migration.IObjectToSQLMapper;
import com.specmate.migration.api.IMigrator;

@Component(property = "sourceVersion=20180412", service = IMigrator.class)
public class Migrator20180412 implements IMigrator {

	private IDBProvider dbProvider;

	@Override
	public String getSourceVersion() {
		return "20180412";
	}

	@Override
	public String getTargetVersion() {
		return "20180510";
	}

	@Override
	public void migrate(Connection connection) throws SpecmateException {
		String packageName = "model/user";
		// rename attributes in user Object (since "User" is a protected term in CDO,
		// CDO adds a "0" to the name)
		IAttributeToSQLMapper aMapper = dbProvider.getAttributeToSQLMapper(packageName, getSourceVersion(),
				getTargetVersion());

		aMapper.migrateRenameAttribute("User0", "name", "userName");
		aMapper.migrateRenameAttribute("User0", "passwordHash", "passWord");
		aMapper.migrateRenameAttribute("User0", "salt", "projectName");

		// Add new Object UserSession
		IObjectToSQLMapper oMapper = dbProvider.getObjectToSQLMapper(packageName, getSourceVersion(),
				getTargetVersion());
		oMapper.newObject("UserSession");

		// Add attributes to UserSession
		aMapper.migrateNewStringAttribute("UserSession", "id", null);
		aMapper.migrateNewStringAttribute("UserSession", "allowedPathPattern", "");
		aMapper.migrateNewLongAttribute("UserSession", "lastActive", null);
		aMapper.migrateNewIntegerAttribute("UserSession", "accessRights", 0);
	}
}

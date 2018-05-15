package com.specmate.migration.internal.services;

import java.sql.Connection;

import org.osgi.service.component.annotations.Component;

import com.specmate.common.SpecmateException;
import com.specmate.migration.api.IMigrator;
import com.specmate.migration.h2.AttributeToSQLMapper;
import com.specmate.migration.h2.ObjectToSQLMapper;

@Component(property = "sourceVersion=20180412", service = IMigrator.class)
public class Migrator20180412 extends BaseMigrator {
	
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
		// rename attributes in user Object (since "User" is a protected term in CDO, CDO adds a "0" to the name)
		AttributeToSQLMapper aMapper = new AttributeToSQLMapper(connection, logService, packageName, 
				getSourceVersion(), getTargetVersion());
		aMapper.migrateRenameAttribute("User0", "name", "userName");
		aMapper.migrateRenameAttribute("User0", "passwordHash", "passWord");
		aMapper.migrateRenameAttribute("User0", "salt", "projectName");
		
		// Add new Object UserSession
		ObjectToSQLMapper oMapper = new ObjectToSQLMapper(connection, logService, packageName, 
				getSourceVersion(), getTargetVersion());
		oMapper.newObject("UserSession");
		
		// Add attributes to UserSession
		aMapper.migrateNewStringAttribute("UserSession", "id", null);
		aMapper.migrateNewStringAttribute("UserSession", "allowedPathPattern", "");
		aMapper.migrateNewLongAttribute("UserSession", "lastActive", null);
		aMapper.migrateNewIntegerAttribute("UserSession", "accessRights", 0);
	}
}

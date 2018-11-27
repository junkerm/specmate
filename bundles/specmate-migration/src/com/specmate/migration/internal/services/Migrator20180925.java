package com.specmate.migration.internal.services;

import java.sql.Connection;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.specmate.common.SpecmateException;
import com.specmate.dbprovider.api.IDBProvider;
import com.specmate.dbprovider.api.migration.IAttributeToSQLMapper;
import com.specmate.dbprovider.api.migration.IObjectToSQLMapper;
import com.specmate.migration.api.IMigrator;

@Component(property = "sourceVersion=20180925", service = IMigrator.class)
public class Migrator20180925 implements IMigrator {

	private IDBProvider dbProvider;

	@Override
	public String getSourceVersion() {
		return "20180925";
	}

	@Override
	public String getTargetVersion() {
		return "20181108";
	}

	@Override
	public void migrate(Connection connection) throws SpecmateException {
		String objectName = "TestSpecificationSkeleton";
		String packageName = "model/testspecification";
		IObjectToSQLMapper oMapper = dbProvider.getObjectToSQLMapper(packageName, getSourceVersion(),
				getTargetVersion());

		oMapper.newObject(objectName);

		IAttributeToSQLMapper aMapper = dbProvider.getAttributeToSQLMapper(packageName, getSourceVersion(),
				getTargetVersion());
		aMapper.migrateNewStringAttribute(objectName, "name", "");
		// "language" seems to be a reserved term, hence CDO uses "language0"
		aMapper.migrateNewStringAttribute(objectName, "language0", "");
		aMapper.migrateNewStringAttribute(objectName, "code", "");
	}

	@Reference
	public void setDBProvider(IDBProvider dbProvider) {
		this.dbProvider = dbProvider;
	}
}

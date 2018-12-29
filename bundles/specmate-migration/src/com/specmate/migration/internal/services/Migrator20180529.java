package com.specmate.migration.internal.services;

import java.sql.Connection;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.specmate.common.exception.SpecmateException;
import com.specmate.dbprovider.api.IDBProvider;
import com.specmate.dbprovider.api.migration.IAttributeToSQLMapper;
import com.specmate.migration.api.IMigrator;

@Component(property = "sourceVersion=20180529", service = IMigrator.class)
public class Migrator20180529 implements IMigrator {

	private IDBProvider dbProvider;

	@Override
	public String getSourceVersion() {
		return "20180529";
	}

	@Override
	public String getTargetVersion() {
		return "20180616";
	}

	@Override
	public void migrate(Connection connection) throws SpecmateException {
		IAttributeToSQLMapper aMapper = dbProvider.getAttributeToSQLMapper("model/user", getSourceVersion(),
				getTargetVersion());

		aMapper.migrateNewStringAttribute("UserSession", "userName", "");
	}

	@Reference
	public void setDBProvider(IDBProvider dbProvider) {
		this.dbProvider = dbProvider;
	}
}

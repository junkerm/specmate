package com.specmate.migration.internal.services;

import java.sql.Connection;

import org.osgi.service.component.annotations.Component;

import com.specmate.common.SpecmateException;
import com.specmate.migration.api.IMigrator;
import com.specmate.migration.h2.AttributeToSQLMapper;

@Component(property = "sourceVersion=20180616", service = IMigrator.class)
public class Migrator20180616 extends BaseMigrator {

	@Override
	public String getSourceVersion() {
		return "20180616";
	}

	@Override
	public String getTargetVersion() {
		return "20180622";
	}

	@Override
	public void migrate(Connection connection) throws SpecmateException {
		AttributeToSQLMapper aMapper = new AttributeToSQLMapper(connection, logService, "model/testspecification",
				getSourceVersion(), getTargetVersion());

		aMapper.migrateNewBooleanAttribute("TestCase", "consistent", true);
	}

}

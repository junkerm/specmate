package com.specmate.migration.internal.services;

import java.sql.Connection;

import org.osgi.service.component.annotations.Component;

import com.specmate.common.SpecmateException;
import com.specmate.migration.api.IMigrator;
import com.specmate.migration.basemigrators.AttributeAddedBaseMigrator;

@Component(property = "sourceVersion=20180126")
public class Migrator20180126 extends AttributeAddedBaseMigrator implements IMigrator {

	@Override
	public String getSourceVersion() {
		return "20180126";
	}

	@Override
	public String getTargetVersion() {
		return "20180412";
	}

	@Override
	public void migrate(Connection connection) throws SpecmateException {
		this.connection = connection;
		migrateNewStringAttribute("processstep", "expectedoutcome", null);
	}

}

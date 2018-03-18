package com.specmate.migration.test.support;

import java.sql.Connection;

import org.osgi.service.component.annotations.Component;

import com.specmate.common.SpecmateException;
import com.specmate.migration.api.IMigrator;
import com.specmate.migration.basemigrators.AttributeAddedBaseMigrator;

@Component(property = "sourceVersion=0")
public class AttributeAddedMigrator extends AttributeAddedBaseMigrator implements IMigrator {

	@Override
	public String getSourceVersion() {
		return "0";
	}

	@Override
	public String getTargetVersion() {
		return "1";
	}

	@Override
	public void migrate(Connection connection) throws SpecmateException {
		this.connection = connection;
		migrateNewStringAttribute("folder", "name", "");
		migrateNewStringAttribute("diagram", "name", null);
	}

}

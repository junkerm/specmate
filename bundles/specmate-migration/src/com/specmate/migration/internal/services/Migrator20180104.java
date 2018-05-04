package com.specmate.migration.internal.services;

import java.sql.Connection;

import org.osgi.service.component.annotations.Component;

import com.specmate.common.SpecmateException;
import com.specmate.migration.api.IMigrator;

@Component(property = "sourceVersion=20180104")
public class Migrator20180104 extends BaseMigrator implements IMigrator {

	@Override
	public String getSourceVersion() {
		return "20180104";
	}

	@Override
	public String getTargetVersion() {
		return "20180126";
	}

	@Override
	public void migrate(Connection connection) throws SpecmateException {
		// nothing to do
	}

}

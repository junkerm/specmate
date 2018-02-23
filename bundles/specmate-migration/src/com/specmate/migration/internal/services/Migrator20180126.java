package com.specmate.migration.internal.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.osgi.service.component.annotations.Component;

import com.specmate.common.SpecmateException;
import com.specmate.migration.api.IMigrator;

@Component(property = "sourceVersion=20180126")
public class Migrator20180126 implements IMigrator {

	@Override
	public String getSourceVersion() {
		return "20180126";
	}

	@Override
	public String getTargetVersion() {
		return "20180223";
	}

	@Override
	public void migrate(Connection connection) throws SpecmateException {
		try {
			PreparedStatement stmt = connection
					.prepareStatement("alter table testparameter add column position0 integer(10)");
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new SpecmateException("Migration: Could not add column position to table testparameter.");
		}

	}

}

package com.specmate.migration.test.support;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.osgi.service.component.annotations.Component;

import com.specmate.common.SpecmateException;
import com.specmate.migration.api.IMigrator;

@Component(property = "sourceVersion=0")
public class AttributeAddedMigrator implements IMigrator {

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
		String[] tables = {"folder", "diagram"};
		
		for(int i = 0; i < tables.length; i++) {
			try {
				PreparedStatement stmt = connection
						.prepareStatement("alter table " + tables[i] + " add column id varchar(32672)");
				stmt.execute();
				stmt.close();
			} catch (SQLException e) {
				throw new SpecmateException("Migration: Could not add column id to table " + tables[i] + ".");
			}
		}
	}

}

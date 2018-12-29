package com.specmate.migration.internal.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.osgi.service.component.annotations.Component;

import com.specmate.common.exception.SpecmateException;
import com.specmate.common.exception.SpecmateInternalException;
import com.specmate.migration.api.IMigrator;
import com.specmate.model.administration.ErrorCode;

@Component(property = "sourceVersion=20171228")
public class Migrator20171228 implements IMigrator {

	@Override
	public String getSourceVersion() {
		return "20171228";
	}

	@Override
	public String getTargetVersion() {
		return "20171231";
	}

	@Override
	public void migrate(Connection connection) throws SpecmateException {
		try {
			PreparedStatement stmt = connection
					.prepareStatement("alter table requirement add column platform varchar(32672)");
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new SpecmateInternalException(ErrorCode.MIGRATION,
					"Could not add column platform to table requirement.", e);
		}

	}

}

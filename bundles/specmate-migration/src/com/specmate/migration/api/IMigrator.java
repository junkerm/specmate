package com.specmate.migration.api;

import java.sql.Connection;

import com.specmate.common.SpecmateException;

public interface IMigrator {
	String getSourceVersion();

	String getTargetVersion();

	void migrate(Connection connection) throws SpecmateException;
}

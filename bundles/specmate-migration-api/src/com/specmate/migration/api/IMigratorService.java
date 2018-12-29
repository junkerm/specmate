package com.specmate.migration.api;

import com.specmate.common.exception.SpecmateException;

public interface IMigratorService {

	boolean needsMigration() throws SpecmateException;

	void doMigration() throws SpecmateException;
}

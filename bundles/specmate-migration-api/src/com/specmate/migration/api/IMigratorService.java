package com.specmate.migration.api;

import com.specmate.common.SpecmateException;

public interface IMigratorService {

	boolean needsMigration() throws SpecmateException;

	void doMigration() throws SpecmateException;
}

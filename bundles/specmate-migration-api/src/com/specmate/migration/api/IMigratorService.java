package com.specmate.migration.api;

import com.specmate.common.SpecmateException;
import com.specmate.persistency.IPackageProvider;

public interface IMigratorService {

	boolean needsMigration() throws SpecmateException;

	boolean doMigration() throws SpecmateException;
	
	void setModelProviderService(IPackageProvider packageProvider);
}

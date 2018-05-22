package com.specmate.migration.internal.services;

import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import com.specmate.migration.api.IMigrator;

public abstract class BaseMigrator implements IMigrator {
	protected LogService logService;
	
	@Reference
	public void setLogService(LogService logService) {
		this.logService = logService;
	}
}

package com.specmate.migration.internal.services;

import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

public abstract class BaseMigrator {
	protected LogService logService;
	
	@Reference
	public void setLogService(LogService logService) {
		this.logService = logService;
	}
}

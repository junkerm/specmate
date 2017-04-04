package com.specmate.model.support.commands;

import org.eclipse.emf.ecore.EObject;
import org.osgi.service.log.LogService;

import com.specmate.common.SpecmateException;

public abstract class CommandBase {

	private LogService logService;

	public abstract EObject execute() throws SpecmateException;

	public int getPriority() {
		return 100;
	}

	public void setLogService(LogService logService) {
		this.logService = logService;
	}

	public LogService getLogService() {
		return this.logService;
	}
}

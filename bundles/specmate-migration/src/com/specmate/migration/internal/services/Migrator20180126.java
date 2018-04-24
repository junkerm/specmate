package com.specmate.migration.internal.services;

import java.sql.Connection;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import com.specmate.common.SpecmateException;
import com.specmate.migration.api.IMigrator;
import com.specmate.migration.h2.AttributeToSQLMapper;
import com.specmate.migration.h2.ObjectToSQLMapper;

@Component(property = "sourceVersion=20180126")
public class Migrator20180126 implements IMigrator {

	private LogService logService;

	@Override
	public String getSourceVersion() {
		return "20180126";
	}

	@Override
	public String getTargetVersion() {
		return "20180412";
	}

	@Override
	public void migrate(Connection connection) throws SpecmateException {
		// new attribute expected outcome
		AttributeToSQLMapper expOutcomeAdded = new AttributeToSQLMapper(connection, logService, "model/processes",
				getSourceVersion(), getTargetVersion());
		expOutcomeAdded.migrateNewStringAttribute("ProcessStep", "expectedOutcome", "");

		// new object status
		String objectName = "Status";
		ObjectToSQLMapper oAdded = new ObjectToSQLMapper(connection, logService, "model/administration",
				getSourceVersion(), getTargetVersion());
		oAdded.newObject(objectName);

		// new attribute value@Status
		AttributeToSQLMapper valueAdded = new AttributeToSQLMapper(connection, logService, "model/administration",
				getSourceVersion(), getTargetVersion());
		valueAdded.migrateNewStringAttribute(objectName, "value", "");
	}

	@Reference
	public void setLogService(LogService logService) {
		this.logService = logService;
	}
}

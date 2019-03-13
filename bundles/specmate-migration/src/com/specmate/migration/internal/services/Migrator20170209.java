package com.specmate.migration.internal.services;

import java.sql.Connection;

import org.osgi.service.component.annotations.Component;

import com.specmate.common.exception.SpecmateException;
import com.specmate.migration.api.IMigrator;

@Component(property = "sourceVersion=20170209")
public class Migrator20170209 implements IMigrator {

	@Override
	public String getSourceVersion() {
		return "20170209";
	}

	@Override
	public String getTargetVersion() {
		return "20171228";
	}

	@Override
	public void migrate(Connection connection) throws SpecmateException {
		// nothing to do
	}

}

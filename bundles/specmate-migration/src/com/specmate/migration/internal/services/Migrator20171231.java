package com.specmate.migration.internal.services;

import java.sql.Connection;

import org.osgi.service.component.annotations.Component;

import com.specmate.common.SpecmateException;
import com.specmate.migration.api.IMigrator;

@Component(property = "sourceVersion=20171228")
public class Migrator20171231 implements IMigrator {

	@Override
	public String getSourceVersion() {
		return "20171231";
	}

	@Override
	public String getTargetVersion() {
		return "20180104";
	}

	@Override
	public void migrate(Connection connection) throws SpecmateException {
		// nothing to do
	}

}

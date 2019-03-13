package com.specmate.migration.internal.services;

import java.sql.Connection;

import org.osgi.service.component.annotations.Component;

import com.specmate.common.exception.SpecmateException;
import com.specmate.migration.api.IMigrator;

@Component(property = "sourceVersion=20180622", service = IMigrator.class)
public class Migrator20180622 implements IMigrator {

	@Override
	public String getSourceVersion() {
		return "20180622";
	}

	@Override
	public String getTargetVersion() {
		return "20180720";
	}

	@Override
	public void migrate(Connection connection) throws SpecmateException {
		// nothing to do
	}

}

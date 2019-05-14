package com.specmate.migration.internal.services;

import java.sql.Connection;

import org.osgi.service.component.annotations.Component;

import com.specmate.common.exception.SpecmateException;
import com.specmate.migration.api.IMigrator;

@Component(property = "sourceVersion=20190125", service = IMigrator.class)
public class Migrator20190125 implements IMigrator {

	@Override
	public String getSourceVersion() {
		return "20190125";
	}

	@Override
	public String getTargetVersion() {
		return "20190514";
	}

	@Override
	public void migrate(Connection connection) throws SpecmateException {
		// TODO
	}

}
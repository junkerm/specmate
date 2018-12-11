package com.specmate.migration.internal.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.specmate.common.SpecmateException;
import com.specmate.config.api.IConfigService;
import com.specmate.connectors.api.IProjectConfigService;
import com.specmate.dbprovider.api.IDBProvider;
import com.specmate.dbprovider.api.migration.IAttributeToSQLMapper;
import com.specmate.migration.api.IMigrator;

@Component(property = "sourceVersion=20181108", service = IMigrator.class)
public class Migrator20181108 implements IMigrator {

	private IDBProvider dbProvider;
	private IConfigService configService;

	@Override
	public String getSourceVersion() {
		return "20181108";
	}

	@Override
	public String getTargetVersion() {
		return "20181210";
	}

	@Override
	public void migrate(Connection connection) throws SpecmateException {
		IAttributeToSQLMapper aMapper = dbProvider.getAttributeToSQLMapper("model/base", getSourceVersion(),
				getTargetVersion());
		aMapper.migrateNewBooleanAttribute("Folder", "isLibrary", false);

		String[] projectsNames = configService.getConfigurationPropertyArray(IProjectConfigService.KEY_PROJECT_NAMES);

		try {
			if (projectsNames != null) {
				for (int i = 0; i < projectsNames.length; i++) {
					String projectName = projectsNames[i];
					String projectLibraryKey = IProjectConfigService.PROJECT_PREFIX + projectName
							+ IProjectConfigService.KEY_PROJECT_LIBRARY;
					String[] libraryFolders = configService.getConfigurationPropertyArray(projectLibraryKey);
					if (libraryFolders != null) {
						for (int j = 0; j < libraryFolders.length; j++) {
							String libraryFolder = libraryFolders[j];
							String sql = "UPDATE FOLDER set isLibrary = true where id = '" + libraryFolder + "'";
							PreparedStatement stmt = connection.prepareStatement(sql);
							stmt.execute();
							stmt.close();
						}
					}
				}
			}
		} catch (SQLException e) {
			throw new SpecmateException("Could not add isLibrary attribute to Folder table.", e);
		}

	}

	@Reference
	public void setDBProvider(IDBProvider dbProvider) {
		this.dbProvider = dbProvider;
	}

	@Reference
	public void setConfigService(IConfigService configService) {
		this.configService = configService;
	}
}

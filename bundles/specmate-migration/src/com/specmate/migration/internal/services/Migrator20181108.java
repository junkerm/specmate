package com.specmate.migration.internal.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import com.specmate.common.exception.SpecmateException;
import com.specmate.common.exception.SpecmateInternalException;
import com.specmate.config.api.IConfigService;
import com.specmate.connectors.api.IProjectConfigService;
import com.specmate.dbprovider.api.IDBProvider;
import com.specmate.dbprovider.api.migration.IAttributeToSQLMapper;
import com.specmate.dbprovider.api.migration.SQLUtil;
import com.specmate.migration.api.IMigrator;
import com.specmate.model.administration.ErrorCode;

@Component(property = "sourceVersion=20181108", service = IMigrator.class)
public class Migrator20181108 implements IMigrator {

	private IDBProvider dbProvider;
	private IConfigService configService;
	private LogService logService;

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
		IAttributeToSQLMapper aMapper = this.dbProvider.getAttributeToSQLMapper("model/base", getSourceVersion(),
				getTargetVersion());
		aMapper.migrateNewBooleanAttribute("Folder", "library", false);

		String[] projectsIDs = this.configService.getConfigurationPropertyArray(IProjectConfigService.KEY_PROJECT_IDS);

		try {
			if (projectsIDs != null) {
				for (int i = 0; i < projectsIDs.length; i++) {
					String projectID = projectsIDs[i];
					String projectLibraryKey = IProjectConfigService.PROJECT_PREFIX + projectID
							+ IProjectConfigService.KEY_PROJECT_LIBRARY;
					String[] libraryFolders = this.configService.getConfigurationPropertyArray(projectLibraryKey);
					if (libraryFolders != null) {
						List<Integer> foldersToUpdate = Collections.emptyList();
						try {
							foldersToUpdate = filterLibraryFolders(projectID, libraryFolders, connection);
						} catch (SpecmateException e) {
							this.logService.log(LogService.LOG_WARNING,
									"Failed to retrieve library folder for project " + projectID);
						}
						for (Integer cdo_id : foldersToUpdate) {
							String trueLiteral = this.dbProvider.getTrueLiteral();
							String sql = "UPDATE FOLDER set library = " + trueLiteral + " WHERE CDO_ID = '" + cdo_id
									+ "'";
							PreparedStatement stmt = connection.prepareStatement(sql);
							stmt.execute();
							stmt.close();
						}
					}
				}
			}
		} catch (SQLException e) {
			throw new SpecmateInternalException(ErrorCode.MIGRATION,
					"Could not add isLibrary attribute to Folder table.", e);
		}

	}

	private List<Integer> filterLibraryFolders(String projectID, String[] libraryFolders, Connection connection)
			throws SpecmateException {
		List<Integer> foldersToUpdate = new ArrayList<>();

		String sql = "SELECT CDO_CONTAINER FROM folder WHERE id = '" + projectID + "'";
		int root_cdo_id = SQLUtil.getFirstIntResult(sql, 1, connection);
		if (root_cdo_id != 0) {
			throw new SpecmateInternalException(ErrorCode.MIGRATION,
					"Folder with id " + projectID + " is not a project.");
		}

		sql = "SELECT CDO_ID FROM folder WHERE id = '" + projectID + "'";
		int project_cdo_id = SQLUtil.getFirstIntResult(sql, 1, connection);

		for (int i = 0; i < libraryFolders.length; i++) {
			String libraryFolderId = libraryFolders[i];
			sql = "SELECT CDO_ID, CDO_CONTAINER FROM folder WHERE id = '" + libraryFolderId + "'";
			List<Integer[]> folders = SQLUtil.getIntArrayListResult(sql, connection);

			for (Integer[] folder : folders) {
				Integer cdo_id = folder[0];
				Integer cdo_container = folder[1];
				if (cdo_container == project_cdo_id) {
					foldersToUpdate.add(cdo_id);
					break;
				}
			}
		}

		return foldersToUpdate;
	}

	@Reference
	public void setDBProvider(IDBProvider dbProvider) {
		this.dbProvider = dbProvider;
	}

	@Reference
	public void setConfigService(IConfigService configService) {
		this.configService = configService;
	}

	@Reference
	public void setLogService(LogService logService) {
		this.logService = logService;
	}
}

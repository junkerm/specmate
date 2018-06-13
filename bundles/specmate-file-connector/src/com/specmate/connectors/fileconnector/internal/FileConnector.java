package com.specmate.connectors.fileconnector.internal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import com.google.common.io.PatternFilenameFilter;
import com.specmate.common.SpecmateException;
import com.specmate.common.SpecmateValidationException;
import com.specmate.connectors.api.ConnectorUtil;
import com.specmate.connectors.api.IRequirementsSource;
import com.specmate.connectors.config.ProjectConfigService;
import com.specmate.connectors.fileconnector.internal.config.FileConnectorConfig;
import com.specmate.model.base.BaseFactory;
import com.specmate.model.base.Folder;
import com.specmate.model.base.IContainer;
import com.specmate.model.requirements.Requirement;
import com.specmate.model.requirements.RequirementsFactory;

/** Connector to the HP Proxy server. */
@Component(service = IRequirementsSource.class, immediate = true, configurationPid = FileConnectorConfig.PID, configurationPolicy = ConfigurationPolicy.REQUIRE)
public class FileConnector implements IRequirementsSource {

	/** The log service */
	private LogService logService;

	/** The folder where to look for requirements */
	private String folder;

	/** Default sub folder where all requirements go to */
	private Folder defaultFolder;

	/** User name for the connector */
	private String user;

	/** Password for the connector */
	private String password;

	/** id of the project folder */
	private String id;

	@Activate
	public void activate(Map<String, Object> properties) throws SpecmateValidationException {
		validateConfig(properties);
		this.folder = (String) properties.get(FileConnectorConfig.KEY_FOLDER);
		this.user = (String) properties.get(FileConnectorConfig.KEY_USER);
		this.password = (String) properties.get(FileConnectorConfig.KEY_PASSWORD);
		this.id = (String) properties.get(ProjectConfigService.KEY_CONNECTOR_ID);
		this.defaultFolder = BaseFactory.eINSTANCE.createFolder();
		this.defaultFolder.setId("default");
		this.defaultFolder.setName("default");
		this.logService.log(LogService.LOG_INFO, "Initialized file connector with " + properties.toString());
	}

	private void validateConfig(Map<String, Object> properties) throws SpecmateValidationException {
		String folderName = (String) properties.get(FileConnectorConfig.KEY_FOLDER);
		if (StringUtils.isEmpty(folderName)) {
			throw new SpecmateValidationException("Empty folder path");
		}
		File file = new File(folderName);
		if (!file.exists() || !file.isDirectory()) {
			throw new SpecmateValidationException("Folder with path " + folderName + " does not exist");
		}
	}

	@Override
	public Collection<Requirement> getRequirements() throws SpecmateException {
		List<Requirement> requirements = new ArrayList<>();
		File file = new File(folder);
		if (file.isDirectory()) {
			FilenameFilter filter = new PatternFilenameFilter(".*\\.txt");
			File[] files = file.listFiles(filter);
			for (int i = 0; i < files.length; i++) {
				requirements.addAll(scanRequirementsFile(files[i]));
			}
		}
		return requirements;
	}

	private Collection<? extends Requirement> scanRequirementsFile(File file) {
		List<Requirement> requirements = new ArrayList<>();
		BufferedReader buffReader = null;
		try {
			FileReader reader = new FileReader(file);
			buffReader = new BufferedReader(reader);

		} catch (FileNotFoundException e) {
			logService.log(LogService.LOG_ERROR, "File not found " + file.getAbsolutePath());
		}

		String line;
		Requirement currentRequirement = RequirementsFactory.eINSTANCE.createRequirement();
		EScanState state = EScanState.TITLE;
		StringBuilder descriptionBuffer = new StringBuilder();
		try {
			line = buffReader.readLine();
			while (line != null) {
				switch (state) {
				case TITLE:
					if (!StringUtils.isEmpty(line)) {
						currentRequirement.setName(line);
						currentRequirement.setId(ConnectorUtil.toId(line));
						currentRequirement.setExtId(ConnectorUtil.toId(line));
						state = EScanState.DESCRIPTION;
					}
					break;
				case DESCRIPTION:
					if (StringUtils.isEmpty(line)) {
						currentRequirement.setDescription(descriptionBuffer.toString());
						requirements.add(currentRequirement);
						currentRequirement = RequirementsFactory.eINSTANCE.createRequirement();
						descriptionBuffer.setLength(0);
						descriptionBuffer.trimToSize();
						state = EScanState.TITLE;
					} else {
						descriptionBuffer.append(line);
					}
				}
				line = buffReader.readLine();
			}
			if (state == EScanState.DESCRIPTION) {
				currentRequirement.setDescription(descriptionBuffer.toString());
				requirements.add(currentRequirement);
			}
			return requirements;
		} catch (IOException e) {
			logService.log(LogService.LOG_ERROR, "Could not read from file " + file.getAbsolutePath());
			return Collections.emptyList();
		} finally {
			if (buffReader != null) {
				try {
					buffReader.close();
				} catch (IOException e) {
					logService.log(LogService.LOG_ERROR, "Could not close file stream to " + file.getAbsolutePath());
				}
			}
		}

	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public IContainer getContainerForRequirement(Requirement requirement) throws SpecmateException {
		return defaultFolder;
	}

	/** Service reference */
	@Reference
	public void setLogService(LogService logService) {
		this.logService = logService;
	}

	private enum EScanState {
		TITLE, DESCRIPTION
	}

	@Override
	public boolean authenticate(String username, String password) {
		if(user==null) {
			return false;
		} else {
			return username.equals(this.user) && password.equals(this.password);
		}
		
	}
}

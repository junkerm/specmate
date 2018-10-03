package com.specmate.connectors.api;

import com.specmate.common.SpecmateException;
import com.specmate.common.SpecmateValidationException;

public interface IProjectConfigService {
	/** The prefix for project configuration keys */
	public static final String PROJECT_PREFIX = "project.";

	/** The PID of a single project service */
	public static final String PROJECT_PID = "com.specmate.connectors.project";

	/** The infix for the list of top-level library folder ids. */
	public static final String INFIX_PROJECT_LIBRARY = ".library";

	/** The suffix for the library name */
	public static final String SUFFIX_PROJECT_LIBRARY_NAME = ".name";

	/** The suffix for the library description */
	public static final String SUFFIX_PROJECT_LIBRARY_DESCRIPTION = ".description";

	/** The configuration key for the list of projects. */
	public static final String KEY_PROJECT_NAMES = PROJECT_PREFIX + "projects";

	/** The configuration key for the id of a connector */
	public static final String KEY_CONNECTOR_ID = "connectorID";

	/** The configuration key for the id of an exporter */
	public static final String KEY_EXPORTER_ID = "exporterID";

	/** the configuration key for the name of a project */
	public static final String KEY_PROJECT_NAME = "projectName";

	/** the configuration key for the library folders of a project */
	public static final String KEY_PROJECT_LIBRARY_FOLDERS = "libraryFolders";

	public void configureProjects(String[] projectNames) throws SpecmateException, SpecmateValidationException;
}

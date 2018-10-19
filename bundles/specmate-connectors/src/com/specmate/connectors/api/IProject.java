package com.specmate.connectors.api;

import java.util.List;

public interface IProject {

	/**
	 * @return the name of the project
	 */
	String getName();

	/**
	 * @return the defined requirements source for the project, or
	 *         <code>null</code>.
	 */
	IRequirementsSource getConnector();

	/**
	 * @return the defined sink to which test information is exported, or
	 *         <code>null</code>.
	 */
	IExportService getExporter();

	/**
	 * @return the list of defined library folders for the project, or
	 *         <code>null</code>.
	 */
	List<String> getLibraryFolders();

}

package com.specmate.connectors.api;

import java.util.List;

public interface IProject {

	String getName();

	IRequirementsSource getConnector();

	IExportService getExporter();

	List<String> getLibraryFolders();

}

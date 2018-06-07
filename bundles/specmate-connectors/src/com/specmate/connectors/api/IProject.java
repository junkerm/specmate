package com.specmate.connectors.api;

public interface IProject {

	String getName();

	IRequirementsSource getConnector();

	IExportService getExporter();
	

}

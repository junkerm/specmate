package com.specmate.connectors.api;

public class Exporter extends ConfigurableBase {

	private IExportService exporterService;

	public void setExporterService(IExportService exporterService) {
		this.exporterService = exporterService;
	}

	public IExportService getExporterService() {
		return exporterService;
	}

}

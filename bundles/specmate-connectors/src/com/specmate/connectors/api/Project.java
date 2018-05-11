package com.specmate.connectors.api;

public class Project {
	private String name;
	private ConfigurableBase connector = null;
	private Exporter exporter;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setConnector(ConfigurableBase connector) {
		this.connector = connector;
	}

	public ConfigurableBase getConnector() {
		return connector;
	}

	public Exporter getExporter() {
		return this.exporter;
	}

	public void setExporter(Exporter exporter) {
		this.exporter = exporter;

	}

}

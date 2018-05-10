package com.specmate.connectors.api;

import java.util.ArrayList;
import java.util.List;

public class Project {
	private String name;
	private List<Connector> connectors = new ArrayList<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addConnector(Connector connector) {
		this.connectors.add(connector);
	}

	public List<Connector> getConnectors() {
		return connectors;
	}

}

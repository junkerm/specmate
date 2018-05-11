package com.specmate.connectors.api;

import java.util.Dictionary;
import java.util.Hashtable;

public abstract class ConfigurableBase {

	private String pid;
	private Dictionary<String, Object> config;
	private IRequirementsSource requirementsSourceService;

	public Dictionary<String, Object> getConfig() {
		return config;
	}

	public void setConfig(Hashtable<String, Object> configTable) {
		this.config = configTable;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public void setRequirementsSourceService(IRequirementsSource requirementsSourceService) {
		this.requirementsSourceService = requirementsSourceService;

	}

}

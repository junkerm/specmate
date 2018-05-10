package com.specmate.connectors.api;

import java.util.Dictionary;
import java.util.Hashtable;

public class Connector {
	private String pid;

	private Dictionary<String, Object> config;

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

}

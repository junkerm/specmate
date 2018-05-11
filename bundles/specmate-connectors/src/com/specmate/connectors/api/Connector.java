package com.specmate.connectors.api;

public class Connector extends ConfigurableBase {

	private IRequirementsSource requirementsSourceService;

	public void setRequirementsSourceService(IRequirementsSource requirementsSourceService) {
		this.requirementsSourceService = requirementsSourceService;

	}

	public IRequirementsSource getRequirementsSourceService() {
		return this.requirementsSourceService;
	}
}

package com.specmate.administration.api;

public enum ESpecmateStatus {

	NORMAL(ESpecmateStatus.NORMAL_NAME, false), MAINTENANCE("maintenance", true);

	private boolean readonly;
	private String statusName;

	public static final String NORMAL_NAME = "normal";
	public static final String MAINTENANCE_NAME = "maintenance";

	private ESpecmateStatus(String statusName, boolean readonly) {
		this.statusName = statusName;
		this.readonly = readonly;
	}

	public boolean isReadOnly() {
		return this.readonly;
	}

	public String getName() {
		return this.statusName;
	}
}

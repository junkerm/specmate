package com.specmate.common;

public enum EErrorCode {
	NS("noSuchService"), MM("maintenanceMode"), IV("invalidData"), NA("noAuthorization"), IP("internalProblem");

	private String type;

	EErrorCode(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return type;
	}
}

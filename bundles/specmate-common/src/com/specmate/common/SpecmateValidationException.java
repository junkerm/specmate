package com.specmate.common;

public class SpecmateValidationException extends Exception {
	private String validatorName;
	private String validatedObjectName;

	/** constructor */
	public SpecmateValidationException(String msg) {
		super(msg);
	}

	/** constructor */
	public SpecmateValidationException(Exception e) {
		super(e);
	}

	/** constructor */
	public SpecmateValidationException(String msg, Exception e) {
		super(msg, e);
	}

	public SpecmateValidationException(String msg, String validatorName, String validatedObjectName) {
		super(msg);
		this.validatorName = validatorName;
		this.validatedObjectName = validatedObjectName;
	}

	public String getValidatorName() {
		return validatorName;
	}

	public String getValidatedObjectName() {
		return validatedObjectName;
	}
}

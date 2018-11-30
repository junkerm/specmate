package com.specmate.common.exception;

import com.specmate.model.administration.ErrorCode;

/**
 * Exception to handle errors that are caused by invalid data originating from
 * clients.
 *
 */
public class SpecmateValidationException extends SpecmateException {
	private String validatorName;
	private String validatedObjectName;

	/** constructor */
	public SpecmateValidationException(String msg) {
		super(ErrorCode.INVALID_DATA, msg);
	}

	/** constructor */
	public SpecmateValidationException(Exception e) {
		super(ErrorCode.INVALID_DATA, e);
	}

	/** constructor */
	public SpecmateValidationException(String msg, Exception e) {
		super(ErrorCode.INVALID_DATA, msg, e);
	}

	/** constructor */
	public SpecmateValidationException(String msg, String validatorName, String validatedObjectName) {
		super(ErrorCode.INVALID_DATA, msg);
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

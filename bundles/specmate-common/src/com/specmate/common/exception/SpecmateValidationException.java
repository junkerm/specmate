package com.specmate.common.exception;

import com.specmate.model.administration.ErrorCode;

/**
 * Generic exception to handle errors that are caused by invalid data
 * originating from clients.
 *
 */
public class SpecmateValidationException extends SpecmateException {

	public SpecmateValidationException(String msg) {
		super(ErrorCode.INVALID_DATA, msg);
	}

	public SpecmateValidationException(String msg, Exception cause) {
		super(ErrorCode.INVALID_DATA, msg, cause);
	}

}

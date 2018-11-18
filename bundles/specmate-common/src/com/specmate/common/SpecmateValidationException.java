package com.specmate.common;

/**
 * Generic exception to handle errors that are caused by invalid data
 * originating from clients.
 *
 */
public class SpecmateValidationException extends Exception {

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
}

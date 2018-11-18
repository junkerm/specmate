package com.specmate.common;

/**
 * Generic exception to handle internal errors that should be logged, but not
 * exposed to clients.
 * 
 * @author junkerm
 *
 */
public class SpecmateException extends Exception {

	/** constructor */
	public SpecmateException(String msg) {
		super(msg);
	}

	/** constructor */
	public SpecmateException(Exception e) {
		super(e);
	}

	/** constructor */
	public SpecmateException(String msg, Exception e) {
		super(msg, e);
	}
}

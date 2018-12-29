package com.specmate.common.exception;

import com.specmate.model.administration.ErrorCode;

/**
 *
 * @author junkerm
 *
 */
public abstract class SpecmateException extends Exception {
	private ErrorCode ecode;

	/** constructor */
	public SpecmateException(ErrorCode ecode, String msg) {
		super(msg);
		this.ecode = ecode;
	}

	/** constructor */
	public SpecmateException(ErrorCode ecode, Exception cause) {
		super(cause);
		this.ecode = ecode;
	}

	/** constructor */
	public SpecmateException(ErrorCode ecode, String msg, Exception cause) {
		super(msg, cause);
		this.ecode = ecode;
	}

	@Override
	public String getMessage() {
		return getErrorcodeS() + ": " + super.getMessage();
	}

	public String getErrorcodeS() {
		return "E" + ecode.ordinal();
	}

	public ErrorCode getErrorcode() {
		return this.ecode;
	}
}

package com.specmate.common;

public class SpecmateInvalidQueryException extends SpecmateException {

	public SpecmateInvalidQueryException(Exception e) {
		super(e);
	}

	public SpecmateInvalidQueryException(String msg, Exception e) {
		super(msg, e);
	}

	public SpecmateInvalidQueryException(String msg) {
		super(msg);
	}

}

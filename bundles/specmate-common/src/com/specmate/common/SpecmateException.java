package com.specmate.common;

/**
 * Generic exception in specmate
 * @author junkerm
 *
 */
public class SpecmateException extends Exception {

	/** constructor */
	public SpecmateException(String msg){
		super(msg);
	}
	
	/** constructor */
	public SpecmateException(Exception e){
		super(e);
	}
	
	/** constructor */
	public SpecmateException(String msg, Exception e){
		super(msg,e);
	}
}

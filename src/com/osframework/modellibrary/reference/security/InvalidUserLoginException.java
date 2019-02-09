package com.osframework.modellibrary.reference.security;

public class InvalidUserLoginException extends Exception {
	
	static final long serialVersionUID = 1L;

	public InvalidUserLoginException() {
		
	}
	
	public InvalidUserLoginException (String msg) {
		super(msg);
	}
}

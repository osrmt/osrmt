package com.osframework.modellibrary.reference.security;

public class InvalidUserPasswordException extends Exception {
	
	static final long serialVersionUID = 1L;

	public InvalidUserPasswordException() {
		
	}
	
	public InvalidUserPasswordException (String msg) {
		super(msg);
	}
}

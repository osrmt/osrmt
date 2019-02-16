package com.osframework.modellibrary.reference.security;

public class AuthorizeException extends Exception {
	
	static final long serialVersionUID = 1L;

	public AuthorizeException() {
		
	}
	
	public AuthorizeException (String msg) {
		super(msg);
	}
}

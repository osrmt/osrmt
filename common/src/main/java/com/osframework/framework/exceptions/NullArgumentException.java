package com.osframework.framework.exceptions;

public class NullArgumentException extends Exception {
	
	static final long serialVersionUID = 1L;

	public NullArgumentException() {
		
	}
	
	public NullArgumentException (String msg) {
		super(msg);
	}
}

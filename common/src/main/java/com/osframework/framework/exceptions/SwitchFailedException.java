package com.osframework.framework.exceptions;

public class SwitchFailedException extends Exception {

	public SwitchFailedException() {
		super();
	}

	public SwitchFailedException(String message) {
		super(message);
	}

	public SwitchFailedException(String message, Throwable cause) {
		super(message, cause);
	}

	public SwitchFailedException(Throwable cause) {
		super(cause);
	}

}

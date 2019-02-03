package com.osrmt.modellibrary.reqmanager;

public class BaselineExistsException extends Exception {

	private static final long serialVersionUID = 1L;

	public BaselineExistsException() {
		super();
	}

	public BaselineExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public BaselineExistsException(String message) {
		super(message);
	}

	public BaselineExistsException(Throwable cause) {
		super(cause);
	}

}

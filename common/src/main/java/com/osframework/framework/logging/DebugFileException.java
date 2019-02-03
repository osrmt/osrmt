package com.osframework.framework.logging;

public class DebugFileException extends Exception {

	static final long serialVersionUID = 1L;
	
	DebugFileException(String msg, Exception e) {
		super(msg,e);
	}
}

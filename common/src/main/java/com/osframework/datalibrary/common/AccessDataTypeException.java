package com.osframework.datalibrary.common;

/**
 * Access class deals with a subset of data types - if any are used
 * that are not handled then this exception is called.
 */
public class AccessDataTypeException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public AccessDataTypeException(String exception){
		super(exception);
	}
}

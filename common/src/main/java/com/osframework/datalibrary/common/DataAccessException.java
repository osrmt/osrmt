package com.osframework.datalibrary.common;

/**
 * DataAccessException is thrown by DataAdapters and used by the beans
 * to distinguish the type of Exception
 *
 */
public class DataAccessException extends Exception {
	static final long serialVersionUID = 1L;
	
	public DataAccessException(Exception exception){
		super(exception);
	}

	public DataAccessException(String msg){
		super(msg);
	}

}

package com.osframework.datalibrary.common;

/**
 * DataAccessException is thrown by DataAdapters and used by the beans
 * to distinguish the type of Exception
 *
 */
public class DataUpdateException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public DataUpdateException(Exception exception){
		super(exception);
	}

}

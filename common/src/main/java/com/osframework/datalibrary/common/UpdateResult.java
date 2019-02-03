package com.osframework.datalibrary.common;

import java.io.Serializable;

public class UpdateResult implements Serializable {

	private int rowsUpdated = 0;
	private int primaryKeyId = 0;
	
	public UpdateResult(){
			
	}
	
	public UpdateResult(int rowsUpdated, int primaryKeyId) {
		this.setRowsUpdated(rowsUpdated);
		this.setPrimaryKeyId(primaryKeyId);
	}
	
	
	public int getPrimaryKeyId() {
		return primaryKeyId;
	}
	public void setPrimaryKeyId(int primaryKeyId) {
		this.primaryKeyId = primaryKeyId;
	}
	public int getRowsUpdated() {
		return rowsUpdated;
	}
	public void setRowsUpdated(int rowsUpdated) {
		this.rowsUpdated = rowsUpdated;
	}
	
	
}
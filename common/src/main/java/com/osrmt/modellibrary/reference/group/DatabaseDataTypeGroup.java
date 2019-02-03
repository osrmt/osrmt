//************ AUTO GENERATED DO NOT EDIT *********//
package com.osrmt.modellibrary.reference.group;

public class DatabaseDataTypeGroup implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int DATE = 121;
	public static final int DOUBLE = 119;
	public static final int INTEGER = 118;
	public static final int STRING = 120;

	private int DatabaseDataTypeRefId = 0;
	public DatabaseDataTypeGroup(int DatabaseDataTypeRefId) {
		this.DatabaseDataTypeRefId = DatabaseDataTypeRefId;		
	}

	public int getDatabaseDataTypeRefId() {
		return DatabaseDataTypeRefId;
	}

	public static DatabaseDataTypeGroup get(int DatabaseDataTypeRefId) {
		return new DatabaseDataTypeGroup(DatabaseDataTypeRefId);
	}

}

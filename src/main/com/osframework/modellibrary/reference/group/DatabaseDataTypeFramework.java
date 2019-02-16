//************ AUTO GENERATED DO NOT EDIT *********//
package com.osframework.modellibrary.reference.group;

public class DatabaseDataTypeFramework implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int DATE = 121;
	public static final int DOUBLE = 119;
	public static final int INTEGER = 118;
	public static final int STRING = 120;

	private int DatabaseDataTypeRefId = 0;
	public DatabaseDataTypeFramework(int DatabaseDataTypeRefId) {
		this.DatabaseDataTypeRefId = DatabaseDataTypeRefId;		
	}

	public int getDatabaseDataTypeRefId() {
		return DatabaseDataTypeRefId;
	}

	public static DatabaseDataTypeFramework get(int DatabaseDataTypeRefId) {
		return new DatabaseDataTypeFramework(DatabaseDataTypeRefId);
	}

}

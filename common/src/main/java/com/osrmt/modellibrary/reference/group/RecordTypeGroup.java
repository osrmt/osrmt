//************ AUTO GENERATED DO NOT EDIT *********//
package com.osrmt.modellibrary.reference.group;

public class RecordTypeGroup implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int SYSTEMREFERENCE = 320;
	public static final int UNCATEGORIZED = 321;
	public static final int USERRECORD = 323;
	public static final int USERREFERENCE = 322;

	private int RecordTypeRefId = 0;
	public RecordTypeGroup(int RecordTypeRefId) {
		this.RecordTypeRefId = RecordTypeRefId;		
	}

	public int getRecordTypeRefId() {
		return RecordTypeRefId;
	}

	public static RecordTypeGroup get(int RecordTypeRefId) {
		return new RecordTypeGroup(RecordTypeRefId);
	}

}

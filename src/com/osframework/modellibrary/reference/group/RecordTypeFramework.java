//************ AUTO GENERATED DO NOT EDIT *********//
package com.osframework.modellibrary.reference.group;

public class RecordTypeFramework implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int SYSTEMREFERENCE = 320;
	public static final int UNCATEGORIZED = 321;
	public static final int USERRECORD = 323;
	public static final int USERREFERENCE = 322;

	private int RecordTypeRefId = 0;
	public RecordTypeFramework(int RecordTypeRefId) {
		this.RecordTypeRefId = RecordTypeRefId;		
	}

	public int getRecordTypeRefId() {
		return RecordTypeRefId;
	}

	public static RecordTypeFramework get(int RecordTypeRefId) {
		return new RecordTypeFramework(RecordTypeRefId);
	}

}

//************ AUTO GENERATED DO NOT EDIT *********//
package com.osframework.modellibrary.reference.group;

public class TraceTypeFramework implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int MATRIX = 272;

	private int TraceTypeRefId = 0;
	public TraceTypeFramework(int TraceTypeRefId) {
		this.TraceTypeRefId = TraceTypeRefId;		
	}

	public int getTraceTypeRefId() {
		return TraceTypeRefId;
	}

	public static TraceTypeFramework get(int TraceTypeRefId) {
		return new TraceTypeFramework(TraceTypeRefId);
	}

}

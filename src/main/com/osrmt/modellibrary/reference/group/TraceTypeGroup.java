//************ AUTO GENERATED DO NOT EDIT *********//
package com.osrmt.modellibrary.reference.group;

public class TraceTypeGroup implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int MATRIX = 272;
	public static final int NOTTRACED = 273;
	public static final int TRACED = 271;

	private int TraceTypeRefId = 0;
	public TraceTypeGroup(int TraceTypeRefId) {
		this.TraceTypeRefId = TraceTypeRefId;		
	}

	public int getTraceTypeRefId() {
		return TraceTypeRefId;
	}

	public static TraceTypeGroup get(int TraceTypeRefId) {
		return new TraceTypeGroup(TraceTypeRefId);
	}

}

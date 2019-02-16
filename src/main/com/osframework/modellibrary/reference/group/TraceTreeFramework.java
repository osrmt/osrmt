//************ AUTO GENERATED DO NOT EDIT *********//
package com.osframework.modellibrary.reference.group;

public class TraceTreeFramework implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int CIRCULARDEPENDENCIES = 1515;

	private int TraceTreeRefId = 0;
	public TraceTreeFramework(int TraceTreeRefId) {
		this.TraceTreeRefId = TraceTreeRefId;		
	}

	public int getTraceTreeRefId() {
		return TraceTreeRefId;
	}

	public static TraceTreeFramework get(int TraceTreeRefId) {
		return new TraceTreeFramework(TraceTreeRefId);
	}

}

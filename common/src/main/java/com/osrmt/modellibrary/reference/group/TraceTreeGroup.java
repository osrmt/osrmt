//************ AUTO GENERATED DO NOT EDIT *********//
package com.osrmt.modellibrary.reference.group;

public class TraceTreeGroup implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int CIRCULARDEPENDENCIES = 1515;
	public static final int MAXTREEDEPTH = 1516;
	public static final int STARTALL = 1510;
	public static final int STARTCHILDREN = 1514;
	public static final int STARTNOCHILDREN = 1512;
	public static final int STARTNOPARENTS = 1511;
	public static final int STARTPARENTS = 1513;
	public static final int TRACEDOWNTO = 1519;
	public static final int TRACEUPTO = 1520;

	private int TraceTreeRefId = 0;
	public TraceTreeGroup(int TraceTreeRefId) {
		this.TraceTreeRefId = TraceTreeRefId;		
	}

	public int getTraceTreeRefId() {
		return TraceTreeRefId;
	}

	public static TraceTreeGroup get(int TraceTreeRefId) {
		return new TraceTreeGroup(TraceTreeRefId);
	}

}

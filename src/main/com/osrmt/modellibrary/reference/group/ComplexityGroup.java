//************ AUTO GENERATED DO NOT EDIT *********//
package com.osrmt.modellibrary.reference.group;

public class ComplexityGroup implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int HIGH = 25;
	public static final int LOW = 27;
	public static final int MEDIUM = 26;

	private int ComplexityRefId = 0;
	public ComplexityGroup(int ComplexityRefId) {
		this.ComplexityRefId = ComplexityRefId;		
	}

	public int getComplexityRefId() {
		return ComplexityRefId;
	}

	public static ComplexityGroup get(int ComplexityRefId) {
		return new ComplexityGroup(ComplexityRefId);
	}

}

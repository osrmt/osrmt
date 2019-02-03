//************ AUTO GENERATED DO NOT EDIT *********//
package com.osframework.modellibrary.reference.group;

public class ComplexityFramework implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int HIGH = 25;

	private int ComplexityRefId = 0;
	public ComplexityFramework(int ComplexityRefId) {
		this.ComplexityRefId = ComplexityRefId;		
	}

	public int getComplexityRefId() {
		return ComplexityRefId;
	}

	public static ComplexityFramework get(int ComplexityRefId) {
		return new ComplexityFramework(ComplexityRefId);
	}

}

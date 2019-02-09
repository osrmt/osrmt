//************ AUTO GENERATED DO NOT EDIT *********//
package com.osframework.modellibrary.reference.group;

public class PriorityFramework implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int DESIGNHIGH = 208;

	private int PriorityRefId = 0;
	public PriorityFramework(int PriorityRefId) {
		this.PriorityRefId = PriorityRefId;		
	}

	public int getPriorityRefId() {
		return PriorityRefId;
	}

	public static PriorityFramework get(int PriorityRefId) {
		return new PriorityFramework(PriorityRefId);
	}

}

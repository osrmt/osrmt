//************ AUTO GENERATED DO NOT EDIT *********//
package com.osframework.modellibrary.reference.group;

public class StatusFramework implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int DESIGNASSIGNED = 82;

	private int StatusRefId = 0;
	public StatusFramework(int StatusRefId) {
		this.StatusRefId = StatusRefId;		
	}

	public int getStatusRefId() {
		return StatusRefId;
	}

	public static StatusFramework get(int StatusRefId) {
		return new StatusFramework(StatusRefId);
	}

}

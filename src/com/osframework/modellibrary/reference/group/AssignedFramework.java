//************ AUTO GENERATED DO NOT EDIT *********//
package com.osframework.modellibrary.reference.group;

public class AssignedFramework implements java.io.Serializable {

	private static final long serialVersionUID = 1L;


	private int AssignedRefId = 0;
	public AssignedFramework(int AssignedRefId) {
		this.AssignedRefId = AssignedRefId;		
	}

	public int getAssignedRefId() {
		return AssignedRefId;
	}

	public static AssignedFramework get(int AssignedRefId) {
		return new AssignedFramework(AssignedRefId);
	}

}

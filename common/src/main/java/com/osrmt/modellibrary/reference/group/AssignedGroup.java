//************ AUTO GENERATED DO NOT EDIT *********//
package com.osrmt.modellibrary.reference.group;

public class AssignedGroup implements java.io.Serializable {

	private static final long serialVersionUID = 1L;


	private int AssignedRefId = 0;
	public AssignedGroup(int AssignedRefId) {
		this.AssignedRefId = AssignedRefId;		
	}

	public int getAssignedRefId() {
		return AssignedRefId;
	}

	public static AssignedGroup get(int AssignedRefId) {
		return new AssignedGroup(AssignedRefId);
	}

}

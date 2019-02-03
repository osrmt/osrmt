//************ AUTO GENERATED DO NOT EDIT *********//
package com.osrmt.modellibrary.reference.group;

public class RoleGroup implements java.io.Serializable {

	private static final long serialVersionUID = 1L;


	private int RoleRefId = 0;
	public RoleGroup(int RoleRefId) {
		this.RoleRefId = RoleRefId;		
	}

	public int getRoleRefId() {
		return RoleRefId;
	}

	public static RoleGroup get(int RoleRefId) {
		return new RoleGroup(RoleRefId);
	}

}

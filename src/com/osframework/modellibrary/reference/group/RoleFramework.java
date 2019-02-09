//************ AUTO GENERATED DO NOT EDIT *********//
package com.osframework.modellibrary.reference.group;

public class RoleFramework implements java.io.Serializable {

	private static final long serialVersionUID = 1L;


	private int RoleRefId = 0;
	public RoleFramework(int RoleRefId) {
		this.RoleRefId = RoleRefId;		
	}

	public int getRoleRefId() {
		return RoleRefId;
	}

	public static RoleFramework get(int RoleRefId) {
		return new RoleFramework(RoleRefId);
	}

}

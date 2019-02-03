//************ AUTO GENERATED DO NOT EDIT *********//
package com.osframework.modellibrary.reference.group;

public class UserGroupFramework implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int ADMINISTRATOR = 1383;

	private int UserGroupRefId = 0;
	public UserGroupFramework(int UserGroupRefId) {
		this.UserGroupRefId = UserGroupRefId;		
	}

	public int getUserGroupRefId() {
		return UserGroupRefId;
	}

	public static UserGroupFramework get(int UserGroupRefId) {
		return new UserGroupFramework(UserGroupRefId);
	}

}

//************ AUTO GENERATED DO NOT EDIT *********//
package com.osrmt.modellibrary.reference.group;

public class UserGroupGroup implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int ADMINISTRATOR = 1383;
	public static final int BUSINESSANALYST = 1378;
	public static final int CCB = 1379;
	public static final int DEVELOPER = 1381;
	public static final int DOCUMENTORS = 1384;
	public static final int PRODUCTMANAGER = 1380;
	public static final int QUALITYREGULATOR = 1385;
	public static final int QUALITYTESTER = 1382;

	private int UserGroupRefId = 0;
	public UserGroupGroup(int UserGroupRefId) {
		this.UserGroupRefId = UserGroupRefId;		
	}

	public int getUserGroupRefId() {
		return UserGroupRefId;
	}

	public static UserGroupGroup get(int UserGroupRefId) {
		return new UserGroupGroup(UserGroupRefId);
	}

}

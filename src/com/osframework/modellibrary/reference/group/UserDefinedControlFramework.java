//************ AUTO GENERATED DO NOT EDIT *********//
package com.osframework.modellibrary.reference.group;

public class UserDefinedControlFramework implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int UICOMBOBOX = 151;
	public static final int UIDOUBLEFIELD = 148;
	public static final int UIEDITORPANE = 149;
	public static final int UIINTEGERFIELD = 147;
	public static final int UILISTBOX = 150;
	public static final int UITEXTFIELD = 146;

	private int UserDefinedControlRefId = 0;
	public UserDefinedControlFramework(int UserDefinedControlRefId) {
		this.UserDefinedControlRefId = UserDefinedControlRefId;		
	}

	public int getUserDefinedControlRefId() {
		return UserDefinedControlRefId;
	}

	public static UserDefinedControlFramework get(int UserDefinedControlRefId) {
		return new UserDefinedControlFramework(UserDefinedControlRefId);
	}

}

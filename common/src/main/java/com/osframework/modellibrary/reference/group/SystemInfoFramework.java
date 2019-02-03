//************ AUTO GENERATED DO NOT EDIT *********//
package com.osframework.modellibrary.reference.group;

public class SystemInfoFramework implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int VERSION = 1100;
	public static final int VERSIONDATE = 1101;

	private int SystemInfoRefId = 0;
	public SystemInfoFramework(int SystemInfoRefId) {
		this.SystemInfoRefId = SystemInfoRefId;		
	}

	public int getSystemInfoRefId() {
		return SystemInfoRefId;
	}

	public static SystemInfoFramework get(int SystemInfoRefId) {
		return new SystemInfoFramework(SystemInfoRefId);
	}

}

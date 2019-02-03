//************ AUTO GENERATED DO NOT EDIT *********//
package com.osframework.modellibrary.reference.group;

public class OperatingSystemFramework implements java.io.Serializable {

	private static final long serialVersionUID = 1L;


	private int OperatingSystemRefId = 0;
	public OperatingSystemFramework(int OperatingSystemRefId) {
		this.OperatingSystemRefId = OperatingSystemRefId;		
	}

	public int getOperatingSystemRefId() {
		return OperatingSystemRefId;
	}

	public static OperatingSystemFramework get(int OperatingSystemRefId) {
		return new OperatingSystemFramework(OperatingSystemRefId);
	}

}

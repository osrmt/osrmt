//************ AUTO GENERATED DO NOT EDIT *********//
package com.osrmt.modellibrary.reference.group;

public class OperatingSystemGroup implements java.io.Serializable {

	private static final long serialVersionUID = 1L;


	private int OperatingSystemRefId = 0;
	public OperatingSystemGroup(int OperatingSystemRefId) {
		this.OperatingSystemRefId = OperatingSystemRefId;		
	}

	public int getOperatingSystemRefId() {
		return OperatingSystemRefId;
	}

	public static OperatingSystemGroup get(int OperatingSystemRefId) {
		return new OperatingSystemGroup(OperatingSystemRefId);
	}

}

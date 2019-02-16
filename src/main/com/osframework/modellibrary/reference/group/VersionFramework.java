//************ AUTO GENERATED DO NOT EDIT *********//
package com.osframework.modellibrary.reference.group;

public class VersionFramework implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int DESIGN10 = 474;

	private int VersionRefId = 0;
	public VersionFramework(int VersionRefId) {
		this.VersionRefId = VersionRefId;		
	}

	public int getVersionRefId() {
		return VersionRefId;
	}

	public static VersionFramework get(int VersionRefId) {
		return new VersionFramework(VersionRefId);
	}

}

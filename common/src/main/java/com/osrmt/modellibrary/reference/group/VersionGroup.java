//************ AUTO GENERATED DO NOT EDIT *********//
package com.osrmt.modellibrary.reference.group;

public class VersionGroup implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int DESIGN10 = 474;
	public static final int DESIGN11 = 938;
	public static final int FEATURE10 = 472;
	public static final int FEATURE11 = 907;
	public static final int FEATURE20 = 1167;
	public static final int IMPLEMENTATION10 = 475;
	public static final int IMPLEMENTATION11 = 936;
	public static final int REQUIREMENT10 = 473;
	public static final int REQUIREMENT11 = 935;
	public static final int TESTCASE10 = 476;
	public static final int TESTCASE11 = 937;

	private int VersionRefId = 0;
	public VersionGroup(int VersionRefId) {
		this.VersionRefId = VersionRefId;		
	}

	public int getVersionRefId() {
		return VersionRefId;
	}

	public static VersionGroup get(int VersionRefId) {
		return new VersionGroup(VersionRefId);
	}

}

//************ AUTO GENERATED DO NOT EDIT *********//
package com.osrmt.modellibrary.reference.group;

public class ArtifactGroup implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int DESIGN = 130;
	public static final int FEATURE = 129;
	public static final int IMPLEMENTATION = 132;
	public static final int REQUIREMENT = 131;
	public static final int TESTCASE = 133;

	private int ArtifactRefId = 0;
	public ArtifactGroup(int ArtifactRefId) {
		this.ArtifactRefId = ArtifactRefId;		
	}

	public int getArtifactRefId() {
		return ArtifactRefId;
	}

	public static ArtifactGroup get(int ArtifactRefId) {
		return new ArtifactGroup(ArtifactRefId);
	}

}

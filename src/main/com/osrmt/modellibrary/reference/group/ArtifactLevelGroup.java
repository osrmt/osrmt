//************ AUTO GENERATED DO NOT EDIT *********//
package com.osrmt.modellibrary.reference.group;

public class ArtifactLevelGroup implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int BRANCH = 122;
	public static final int COMPONENT = 124;
	public static final int LEAF = 123;

	private int ArtifactLevelRefId = 0;
	public ArtifactLevelGroup(int ArtifactLevelRefId) {
		this.ArtifactLevelRefId = ArtifactLevelRefId;		
	}

	public int getArtifactLevelRefId() {
		return ArtifactLevelRefId;
	}

	public static ArtifactLevelGroup get(int ArtifactLevelRefId) {
		return new ArtifactLevelGroup(ArtifactLevelRefId);
	}

}

//************ AUTO GENERATED DO NOT EDIT *********//
package com.osframework.modellibrary.reference.group;

public class ArtifactLevelFramework implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int BRANCH = 122;

	private int ArtifactLevelRefId = 0;
	public ArtifactLevelFramework(int ArtifactLevelRefId) {
		this.ArtifactLevelRefId = ArtifactLevelRefId;		
	}

	public int getArtifactLevelRefId() {
		return ArtifactLevelRefId;
	}

	public static ArtifactLevelFramework get(int ArtifactLevelRefId) {
		return new ArtifactLevelFramework(ArtifactLevelRefId);
	}

}

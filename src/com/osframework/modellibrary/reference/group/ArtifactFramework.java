//************ AUTO GENERATED DO NOT EDIT *********//
package com.osframework.modellibrary.reference.group;

public class ArtifactFramework implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int DESIGN = 130;

	private int ArtifactRefId = 0;
	public ArtifactFramework(int ArtifactRefId) {
		this.ArtifactRefId = ArtifactRefId;		
	}

	public int getArtifactRefId() {
		return ArtifactRefId;
	}

	public static ArtifactFramework get(int ArtifactRefId) {
		return new ArtifactFramework(ArtifactRefId);
	}

}

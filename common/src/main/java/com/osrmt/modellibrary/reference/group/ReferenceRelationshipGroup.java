//************ AUTO GENERATED DO NOT EDIT *********//
package com.osrmt.modellibrary.reference.group;

public class ReferenceRelationshipGroup implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int REFERENCEGROUP = 1008;

	private int ReferenceRelationshipRefId = 0;
	public ReferenceRelationshipGroup(int ReferenceRelationshipRefId) {
		this.ReferenceRelationshipRefId = ReferenceRelationshipRefId;		
	}

	public int getReferenceRelationshipRefId() {
		return ReferenceRelationshipRefId;
	}

	public static ReferenceRelationshipGroup get(int ReferenceRelationshipRefId) {
		return new ReferenceRelationshipGroup(ReferenceRelationshipRefId);
	}

}

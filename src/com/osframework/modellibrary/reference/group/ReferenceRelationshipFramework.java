//************ AUTO GENERATED DO NOT EDIT *********//
package com.osframework.modellibrary.reference.group;

public class ReferenceRelationshipFramework implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int REFERENCEGROUP = 1008;

	private int ReferenceRelationshipRefId = 0;
	public ReferenceRelationshipFramework(int ReferenceRelationshipRefId) {
		this.ReferenceRelationshipRefId = ReferenceRelationshipRefId;		
	}

	public int getReferenceRelationshipRefId() {
		return ReferenceRelationshipRefId;
	}

	public static ReferenceRelationshipFramework get(int ReferenceRelationshipRefId) {
		return new ReferenceRelationshipFramework(ReferenceRelationshipRefId);
	}

}

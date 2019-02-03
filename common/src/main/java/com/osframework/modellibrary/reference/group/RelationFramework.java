//************ AUTO GENERATED DO NOT EDIT *********//
package com.osframework.modellibrary.reference.group;

public class RelationFramework implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int DEPENDENT = 1178;

	private int RelationRefId = 0;
	public RelationFramework(int RelationRefId) {
		this.RelationRefId = RelationRefId;		
	}

	public int getRelationRefId() {
		return RelationRefId;
	}

	public static RelationFramework get(int RelationRefId) {
		return new RelationFramework(RelationRefId);
	}

}

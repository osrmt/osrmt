//************ AUTO GENERATED DO NOT EDIT *********//
package com.osrmt.modellibrary.reference.group;

public class RelationGroup implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int DEPENDENT = 1178;
	public static final int RELATED = 1179;

	private int RelationRefId = 0;
	public RelationGroup(int RelationRefId) {
		this.RelationRefId = RelationRefId;		
	}

	public int getRelationRefId() {
		return RelationRefId;
	}

	public static RelationGroup get(int RelationRefId) {
		return new RelationGroup(RelationRefId);
	}

}

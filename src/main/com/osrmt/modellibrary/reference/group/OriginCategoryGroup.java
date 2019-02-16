//************ AUTO GENERATED DO NOT EDIT *********//
package com.osrmt.modellibrary.reference.group;

public class OriginCategoryGroup implements java.io.Serializable {

	private static final long serialVersionUID = 1L;


	private int OriginCategoryRefId = 0;
	public OriginCategoryGroup(int OriginCategoryRefId) {
		this.OriginCategoryRefId = OriginCategoryRefId;		
	}

	public int getOriginCategoryRefId() {
		return OriginCategoryRefId;
	}

	public static OriginCategoryGroup get(int OriginCategoryRefId) {
		return new OriginCategoryGroup(OriginCategoryRefId);
	}

}

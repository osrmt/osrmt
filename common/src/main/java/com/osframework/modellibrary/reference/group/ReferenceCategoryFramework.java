//************ AUTO GENERATED DO NOT EDIT *********//
package com.osframework.modellibrary.reference.group;

public class ReferenceCategoryFramework implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int ARTIFACT = 347;

	private int ReferenceCategoryRefId = 0;
	public ReferenceCategoryFramework(int ReferenceCategoryRefId) {
		this.ReferenceCategoryRefId = ReferenceCategoryRefId;		
	}

	public int getReferenceCategoryRefId() {
		return ReferenceCategoryRefId;
	}

	public static ReferenceCategoryFramework get(int ReferenceCategoryRefId) {
		return new ReferenceCategoryFramework(ReferenceCategoryRefId);
	}

}

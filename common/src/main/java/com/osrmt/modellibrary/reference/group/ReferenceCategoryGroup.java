//************ AUTO GENERATED DO NOT EDIT *********//
package com.osrmt.modellibrary.reference.group;

public class ReferenceCategoryGroup implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int ARTIFACT = 347;
	public static final int FRAMEWORK = 343;
	public static final int INPUTOUTPUT = 344;
	public static final int REQMANAGEMENT = 345;
	public static final int SECURITY = 342;
	public static final int SYSTEM = 346;

	private int ReferenceCategoryRefId = 0;
	public ReferenceCategoryGroup(int ReferenceCategoryRefId) {
		this.ReferenceCategoryRefId = ReferenceCategoryRefId;		
	}

	public int getReferenceCategoryRefId() {
		return ReferenceCategoryRefId;
	}

	public static ReferenceCategoryGroup get(int ReferenceCategoryRefId) {
		return new ReferenceCategoryGroup(ReferenceCategoryRefId);
	}

}

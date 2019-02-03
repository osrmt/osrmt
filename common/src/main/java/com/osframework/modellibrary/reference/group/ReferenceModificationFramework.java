//************ AUTO GENERATED DO NOT EDIT *********//
package com.osframework.modellibrary.reference.group;

public class ReferenceModificationFramework implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int ADD = 327;
	public static final int DISPLAY = 325;
	public static final int FULL = 326;
	public static final int NOACCESS = 324;

	private int ReferenceModificationRefId = 0;
	public ReferenceModificationFramework(int ReferenceModificationRefId) {
		this.ReferenceModificationRefId = ReferenceModificationRefId;		
	}

	public int getReferenceModificationRefId() {
		return ReferenceModificationRefId;
	}

	public static ReferenceModificationFramework get(int ReferenceModificationRefId) {
		return new ReferenceModificationFramework(ReferenceModificationRefId);
	}

}

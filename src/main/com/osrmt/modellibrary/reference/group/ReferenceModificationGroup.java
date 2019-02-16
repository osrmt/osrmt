//************ AUTO GENERATED DO NOT EDIT *********//
package com.osrmt.modellibrary.reference.group;

public class ReferenceModificationGroup implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int ADD = 327;
	public static final int DISPLAY = 325;
	public static final int FULL = 326;
	public static final int NOACCESS = 324;

	private int ReferenceModificationRefId = 0;
	public ReferenceModificationGroup(int ReferenceModificationRefId) {
		this.ReferenceModificationRefId = ReferenceModificationRefId;		
	}

	public int getReferenceModificationRefId() {
		return ReferenceModificationRefId;
	}

	public static ReferenceModificationGroup get(int ReferenceModificationRefId) {
		return new ReferenceModificationGroup(ReferenceModificationRefId);
	}

}

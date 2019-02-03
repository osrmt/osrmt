//************ AUTO GENERATED DO NOT EDIT *********//
package com.osrmt.modellibrary.reference.group;

public class PositionGroup implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int ADMIN = 2;
	public static final int DEVELOPER = 6957209;
	public static final int MARKETING = 224;
	public static final int ROOT = 3;

	private int PositionRefId = 0;
	public PositionGroup(int PositionRefId) {
		this.PositionRefId = PositionRefId;		
	}

	public int getPositionRefId() {
		return PositionRefId;
	}

	public static PositionGroup get(int PositionRefId) {
		return new PositionGroup(PositionRefId);
	}

}

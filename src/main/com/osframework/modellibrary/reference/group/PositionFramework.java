//************ AUTO GENERATED DO NOT EDIT *********//
package com.osframework.modellibrary.reference.group;

public class PositionFramework implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int ADMIN = 2;

	private int PositionRefId = 0;
	public PositionFramework(int PositionRefId) {
		this.PositionRefId = PositionRefId;		
	}

	public int getPositionRefId() {
		return PositionRefId;
	}

	public static PositionFramework get(int PositionRefId) {
		return new PositionFramework(PositionRefId);
	}

}

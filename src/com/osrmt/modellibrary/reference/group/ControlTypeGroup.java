//************ AUTO GENERATED DO NOT EDIT *********//
package com.osrmt.modellibrary.reference.group;

public class ControlTypeGroup implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int UIDATEFIELD = 40;
	public static final int UILISTBOX = 43;
	public static final int UIREFERENCESEARCH = 44;
	public static final int UISEPARATOR = 45;
	public static final int UITAB = 46;
	public static final int UITEXTFIELD = 47;

	private int ControlTypeRefId = 0;
	public ControlTypeGroup(int ControlTypeRefId) {
		this.ControlTypeRefId = ControlTypeRefId;		
	}

	public int getControlTypeRefId() {
		return ControlTypeRefId;
	}

	public static ControlTypeGroup get(int ControlTypeRefId) {
		return new ControlTypeGroup(ControlTypeRefId);
	}

}

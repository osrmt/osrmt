//************ AUTO GENERATED DO NOT EDIT *********//
package com.osrmt.modellibrary.reference.group;

public class IssuePriorityGroup implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int HIGH = 1396;
	public static final int IMMEDIATE = 1388;
	public static final int LOW = 1397;

	private int IssuePriorityRefId = 0;
	public IssuePriorityGroup(int IssuePriorityRefId) {
		this.IssuePriorityRefId = IssuePriorityRefId;		
	}

	public int getIssuePriorityRefId() {
		return IssuePriorityRefId;
	}

	public static IssuePriorityGroup get(int IssuePriorityRefId) {
		return new IssuePriorityGroup(IssuePriorityRefId);
	}

}

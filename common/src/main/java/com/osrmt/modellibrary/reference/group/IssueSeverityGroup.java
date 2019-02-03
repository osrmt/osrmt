//************ AUTO GENERATED DO NOT EDIT *********//
package com.osrmt.modellibrary.reference.group;

public class IssueSeverityGroup implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int CRITICAL = 1391;
	public static final int HIGH = 1400;
	public static final int MARGINAL = 1401;
	public static final int NEGLIGIBLE = 1402;

	private int IssueSeverityRefId = 0;
	public IssueSeverityGroup(int IssueSeverityRefId) {
		this.IssueSeverityRefId = IssueSeverityRefId;		
	}

	public int getIssueSeverityRefId() {
		return IssueSeverityRefId;
	}

	public static IssueSeverityGroup get(int IssueSeverityRefId) {
		return new IssueSeverityGroup(IssueSeverityRefId);
	}

}

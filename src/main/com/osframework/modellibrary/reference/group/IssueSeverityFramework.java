//************ AUTO GENERATED DO NOT EDIT *********//
package com.osframework.modellibrary.reference.group;

public class IssueSeverityFramework implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int CRITICAL = 1391;

	private int IssueSeverityRefId = 0;
	public IssueSeverityFramework(int IssueSeverityRefId) {
		this.IssueSeverityRefId = IssueSeverityRefId;		
	}

	public int getIssueSeverityRefId() {
		return IssueSeverityRefId;
	}

	public static IssueSeverityFramework get(int IssueSeverityRefId) {
		return new IssueSeverityFramework(IssueSeverityRefId);
	}

}

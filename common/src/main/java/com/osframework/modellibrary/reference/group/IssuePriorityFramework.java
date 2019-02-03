//************ AUTO GENERATED DO NOT EDIT *********//
package com.osframework.modellibrary.reference.group;

public class IssuePriorityFramework implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int HIGH = 1396;

	private int IssuePriorityRefId = 0;
	public IssuePriorityFramework(int IssuePriorityRefId) {
		this.IssuePriorityRefId = IssuePriorityRefId;		
	}

	public int getIssuePriorityRefId() {
		return IssuePriorityRefId;
	}

	public static IssuePriorityFramework get(int IssuePriorityRefId) {
		return new IssuePriorityFramework(IssuePriorityRefId);
	}

}

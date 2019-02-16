//************ AUTO GENERATED DO NOT EDIT *********//
package com.osframework.modellibrary.reference.group;

public class IssueStatusFramework implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int ACCEPTED = 1367;

	private int IssueStatusRefId = 0;
	public IssueStatusFramework(int IssueStatusRefId) {
		this.IssueStatusRefId = IssueStatusRefId;		
	}

	public int getIssueStatusRefId() {
		return IssueStatusRefId;
	}

	public static IssueStatusFramework get(int IssueStatusRefId) {
		return new IssueStatusFramework(IssueStatusRefId);
	}

}

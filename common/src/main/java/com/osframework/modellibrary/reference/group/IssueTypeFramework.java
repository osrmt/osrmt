//************ AUTO GENERATED DO NOT EDIT *********//
package com.osframework.modellibrary.reference.group;

public class IssueTypeFramework implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int CHANGECONTROL = 1364;

	private int IssueTypeRefId = 0;
	public IssueTypeFramework(int IssueTypeRefId) {
		this.IssueTypeRefId = IssueTypeRefId;		
	}

	public int getIssueTypeRefId() {
		return IssueTypeRefId;
	}

	public static IssueTypeFramework get(int IssueTypeRefId) {
		return new IssueTypeFramework(IssueTypeRefId);
	}

}

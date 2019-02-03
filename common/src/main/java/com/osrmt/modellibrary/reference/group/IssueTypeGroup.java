//************ AUTO GENERATED DO NOT EDIT *********//
package com.osrmt.modellibrary.reference.group;

public class IssueTypeGroup implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int CHANGECONTROL = 1364;
	public static final int DEFECT = 1348;
	public static final int FEEDBACK = 1347;

	private int IssueTypeRefId = 0;
	public IssueTypeGroup(int IssueTypeRefId) {
		this.IssueTypeRefId = IssueTypeRefId;		
	}

	public int getIssueTypeRefId() {
		return IssueTypeRefId;
	}

	public static IssueTypeGroup get(int IssueTypeRefId) {
		return new IssueTypeGroup(IssueTypeRefId);
	}

}

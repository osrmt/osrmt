//************ AUTO GENERATED DO NOT EDIT *********//
package com.osrmt.modellibrary.reference.group;

public class IssueResolvedCategoryGroup implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int CODECHANGE = 1390;
	public static final int CONFIGURATION = 1403;

	private int IssueResolvedCategoryRefId = 0;
	public IssueResolvedCategoryGroup(int IssueResolvedCategoryRefId) {
		this.IssueResolvedCategoryRefId = IssueResolvedCategoryRefId;		
	}

	public int getIssueResolvedCategoryRefId() {
		return IssueResolvedCategoryRefId;
	}

	public static IssueResolvedCategoryGroup get(int IssueResolvedCategoryRefId) {
		return new IssueResolvedCategoryGroup(IssueResolvedCategoryRefId);
	}

}

//************ AUTO GENERATED DO NOT EDIT *********//
package com.osrmt.modellibrary.reference.group;

public class IssueClosedCategoryGroup implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int NOTREPRODUCIBLE = 1392;
	public static final int RESOLVED = 1386;

	private int IssueClosedCategoryRefId = 0;
	public IssueClosedCategoryGroup(int IssueClosedCategoryRefId) {
		this.IssueClosedCategoryRefId = IssueClosedCategoryRefId;		
	}

	public int getIssueClosedCategoryRefId() {
		return IssueClosedCategoryRefId;
	}

	public static IssueClosedCategoryGroup get(int IssueClosedCategoryRefId) {
		return new IssueClosedCategoryGroup(IssueClosedCategoryRefId);
	}

}

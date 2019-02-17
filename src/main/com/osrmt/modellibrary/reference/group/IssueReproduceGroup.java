//************ AUTO GENERATED DO NOT EDIT *********//
package com.osrmt.modellibrary.reference.group;

public class IssueReproduceGroup implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int INFREQUENTLY = 1398;
	public static final int NOTREPRODUCIBLE = 1399;
	public static final int REPRODUCIBLE = 1389;

	private int IssueReproduceRefId = 0;
	public IssueReproduceGroup(int IssueReproduceRefId) {
		this.IssueReproduceRefId = IssueReproduceRefId;		
	}

	public int getIssueReproduceRefId() {
		return IssueReproduceRefId;
	}

	public static IssueReproduceGroup get(int IssueReproduceRefId) {
		return new IssueReproduceGroup(IssueReproduceRefId);
	}

}

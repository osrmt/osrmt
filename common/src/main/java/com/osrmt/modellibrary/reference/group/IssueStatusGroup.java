//************ AUTO GENERATED DO NOT EDIT *********//
package com.osrmt.modellibrary.reference.group;

public class IssueStatusGroup implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int ACCEPTED = 1367;
	public static final int APPROVED = 1368;
	public static final int CODED = 1369;
	public static final int COMPLETED = 1377;
	public static final int DEPLOYED = 1372;
	public static final int DESIGNED = 1553;
	public static final int DOCUMENTED = 1376;
	public static final int INSTALLED = 1374;
	public static final int NEW = 1365;
	public static final int REJECTED = 1552;
	public static final int REVIEWED = 1370;
	public static final int SUBMITTED = 1366;
	public static final int TESTED = 1371;
	public static final int VALIDATED = 1375;
	public static final int VERIFIED = 1373;

	private int IssueStatusRefId = 0;
	public IssueStatusGroup(int IssueStatusRefId) {
		this.IssueStatusRefId = IssueStatusRefId;		
	}

	public int getIssueStatusRefId() {
		return IssueStatusRefId;
	}

	public static IssueStatusGroup get(int IssueStatusRefId) {
		return new IssueStatusGroup(IssueStatusRefId);
	}

}

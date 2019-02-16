//************ AUTO GENERATED DO NOT EDIT *********//
package com.osrmt.modellibrary.reference.group;

public class IssueFrequencyGroup implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int FREQUENT = 1387;
	public static final int IMPROBABLE = 1395;
	public static final int OCCASIONAL = 1393;
	public static final int REMOTE = 1394;

	private int IssueFrequencyRefId = 0;
	public IssueFrequencyGroup(int IssueFrequencyRefId) {
		this.IssueFrequencyRefId = IssueFrequencyRefId;		
	}

	public int getIssueFrequencyRefId() {
		return IssueFrequencyRefId;
	}

	public static IssueFrequencyGroup get(int IssueFrequencyRefId) {
		return new IssueFrequencyGroup(IssueFrequencyRefId);
	}

}

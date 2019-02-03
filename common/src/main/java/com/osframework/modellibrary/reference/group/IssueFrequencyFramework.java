//************ AUTO GENERATED DO NOT EDIT *********//
package com.osframework.modellibrary.reference.group;

public class IssueFrequencyFramework implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int FREQUENT = 1387;

	private int IssueFrequencyRefId = 0;
	public IssueFrequencyFramework(int IssueFrequencyRefId) {
		this.IssueFrequencyRefId = IssueFrequencyRefId;		
	}

	public int getIssueFrequencyRefId() {
		return IssueFrequencyRefId;
	}

	public static IssueFrequencyFramework get(int IssueFrequencyRefId) {
		return new IssueFrequencyFramework(IssueFrequencyRefId);
	}

}

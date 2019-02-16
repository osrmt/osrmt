//************ AUTO GENERATED DO NOT EDIT *********//
package com.osrmt.modellibrary.reference.group;

public class IssueVersionGroup implements java.io.Serializable {

	private static final long serialVersionUID = 1L;


	private int IssueVersionRefId = 0;
	public IssueVersionGroup(int IssueVersionRefId) {
		this.IssueVersionRefId = IssueVersionRefId;		
	}

	public int getIssueVersionRefId() {
		return IssueVersionRefId;
	}

	public static IssueVersionGroup get(int IssueVersionRefId) {
		return new IssueVersionGroup(IssueVersionRefId);
	}

}

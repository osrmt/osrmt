//************ AUTO GENERATED DO NOT EDIT *********//
package com.osframework.modellibrary.reference.group;

public class IssueVersionFramework implements java.io.Serializable {

	private static final long serialVersionUID = 1L;


	private int IssueVersionRefId = 0;
	public IssueVersionFramework(int IssueVersionRefId) {
		this.IssueVersionRefId = IssueVersionRefId;		
	}

	public int getIssueVersionRefId() {
		return IssueVersionRefId;
	}

	public static IssueVersionFramework get(int IssueVersionRefId) {
		return new IssueVersionFramework(IssueVersionRefId);
	}

}

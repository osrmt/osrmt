package com.osrmt.modellibrary.issue;
import com.osframework.modellibrary.common.CriteriaModel;
import com.osframework.modellibrary.common.DbCalendar;

public class IssueCriteria extends CriteriaModel {

	/** Foreign key to reference: IssueType  */
	private int issueTypeRefId = 0;

	/** Foreign key to reference: Product  */
	private int productRefId = 0;

	/** Date/time issue occured  */
	private DbCalendar beginIssueOccurDt;

	/** Date/time issue occured  */
	private DbCalendar endIssueOccurDt;

	/** Foreign key to reference: IssueVersion  */
	private int versionRefId = 0;

	/** Foreign key to artifact table  */
	private int artifactId = 0;

	/** Foreign key to test log  */
	private int testLogId = 0;

	/** Foreign key to reference: IssueStatus  */
	private int statusRefId = 0;

	/** Foreign key to reference: UserGroup  */
	private int assignedUserGroupRefId = 0;

	/** Foreign key to user table  */
	private int assignedUserId = 0;

	/** Priority foreign key to reference: IssuePriority  */
	private int priorityRefId = 0;

	/** Foreign key to reference: IssueSeverity  */
	private int severityRefId = 0;

	/** Date desired a fix by  */
	private DbCalendar beginIssueOpenDt;

	/** Date desired a fix by  */
	private DbCalendar endIssueOpenDt;

	/** Author of issue  */
	private int submittedUserId = 0;

	/** Foreign key to organization table  */
	private int organizationId = 0;

	/** Duplicate or rolled up issue  */
	private int parentIssueId = 0;

	/** Date/time issue was closed  */
	private DbCalendar beginClosedDt;

	/** Date/time issue was closed  */
	private DbCalendar endClosedDt;

	/** External system identifier  */
	private String externalAlias1;

	/** External system identifier  */
	private String externalAlias2;

	/** External system identifier  */
	private String externalAlias3;

	public int getArtifactId() {
		return artifactId;
	}

	public void setArtifactId(int artifactId) {
		this.artifactId = artifactId;
		super.addParameter("artifact_id",operatorEquals,new Integer(artifactId));
	}

	public int getAssignedUserGroupRefId() {
		return assignedUserGroupRefId;
	}

	public void setAssignedUserGroupRefId(int assignedUserGroupRefId) {
		this.assignedUserGroupRefId = assignedUserGroupRefId;
		super.addParameter("assigned_user_group_ref_id",operatorEquals,new Integer(assignedUserGroupRefId));
	}

	public int getAssignedUserId() {
		return assignedUserId;
	}

	public void setAssignedUserId(int assignedUserId) {
		this.assignedUserId = assignedUserId;
		super.addParameter("assigned_user_id",operatorEquals,new Integer(assignedUserId));
	}

	public DbCalendar getBeginClosedDt() {
		return beginClosedDt;
	}

	public void setBeginClosedDt(DbCalendar beginClosedDt) {
		this.beginClosedDt = beginClosedDt;
		super.addParameter("closed_dt",operatorGreaterThanOrEquals,beginClosedDt);
	}

	public DbCalendar getEndClosedDt() {
		return endClosedDt;
	}

	public void setEndClosedDt(DbCalendar endClosedDt) {
		this.endClosedDt = endClosedDt;
		super.addParameter("closed_dt",operatorLessThanOrEquals,endClosedDt);
	}

	public String getExternalAlias1() {
		return externalAlias1;
	}

	public void setExternalAlias1(String externalAlias1) {
		this.externalAlias1 = externalAlias1;
		super.addParameter("external_alias1",operatorEquals,externalAlias1);
	}

	public String getExternalAlias2() {
		return externalAlias2;
	}

	public void setExternalAlias2(String externalAlias2) {
		this.externalAlias2 = externalAlias2;
		super.addParameter("external_alias2",operatorEquals,externalAlias2);
	}

	public String getExternalAlias3() {
		return externalAlias3;
	}

	public void setExternalAlias3(String externalAlias3) {
		this.externalAlias3 = externalAlias3;
		super.addParameter("external_alias3",operatorEquals,externalAlias3);
	}

	public DbCalendar getBeginIssueOpenDt() {
		return beginIssueOpenDt;
	}

	public void setBeginIssueOpenDt(DbCalendar beginIssueOpenDt) {
		this.beginIssueOpenDt = beginIssueOpenDt;
		super.addParameter("issue_open_dt",operatorGreaterThanOrEquals,beginIssueOpenDt);
	}

	public DbCalendar getEndIssueOpenDt() {
		return endIssueOpenDt;
	}

	public void setEndIssueOpenDt(DbCalendar endIssueOpenDt) {
		this.endIssueOpenDt = endIssueOpenDt;
		super.addParameter("issue_open_dt",operatorLessThanOrEquals,endIssueOpenDt);
	}

	public DbCalendar getBeginIssueOccurDt() {
		return beginIssueOccurDt;
	}

	public void setBeginIssueOccurDt(DbCalendar beginIssueOccurDt) {
		this.beginIssueOccurDt = beginIssueOccurDt;
		super.addParameter("issue_occur_dt",operatorGreaterThanOrEquals,beginIssueOccurDt);
	}

	public DbCalendar getEndIssueOccurDt() {
		return endIssueOccurDt;
	}

	public void setEndIssueOccurDt(DbCalendar endIssueOccurDt) {
		this.endIssueOccurDt = endIssueOccurDt;
		super.addParameter("issue_occur_dt",operatorLessThanOrEquals,endIssueOccurDt);
	}


	public int getIssueTypeRefId() {
		return issueTypeRefId;
	}

	public void setIssueTypeRefId(int issueTypeRefId) {
		this.issueTypeRefId = issueTypeRefId;
		super.addParameter("issue_type_ref_id",operatorEquals,new Integer(issueTypeRefId));
	}

	public int getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
		super.addParameter("organization_id",operatorEquals,new Integer(organizationId));
	}

	public int getParentIssueId() {
		return parentIssueId;
	}

	public void setParentIssueId(int parentIssueId) {
		this.parentIssueId = parentIssueId;
		super.addParameter("parent_issue_id",operatorEquals,new Integer(parentIssueId));
	}

	public int getPriorityRefId() {
		return priorityRefId;
	}

	public void setPriorityRefId(int priorityRefId) {
		this.priorityRefId = priorityRefId;
		super.addParameter("priority_ref_id",operatorEquals,new Integer(priorityRefId));
	}

	public int getProductRefId() {
		return productRefId;
	}

	public void setProductRefId(int productRefId) {
		this.productRefId = productRefId;
		super.addParameter("product_ref_id",operatorEquals,new Integer(productRefId));
	}

	public int getSeverityRefId() {
		return severityRefId;
	}

	public void setSeverityRefId(int severityRefId) {
		this.severityRefId = severityRefId;
		super.addParameter("severity_ref_id",operatorEquals,new Integer(severityRefId));
	}

	public int getStatusRefId() {
		return statusRefId;
	}

	public void setStatusRefId(int statusRefId) {
		this.statusRefId = statusRefId;
		super.addParameter("status_ref_id",operatorEquals,new Integer(statusRefId));
	}

	public int getSubmittedUserId() {
		return submittedUserId;
	}

	public void setSubmittedUserId(int submittedUserId) {
		this.submittedUserId = submittedUserId;
		super.addParameter("submitted_user_id",operatorEquals,new Integer(submittedUserId));
	}

	public int getTestLogId() {
		return testLogId;
	}

	public void setTestLogId(int testLogId) {
		this.testLogId = testLogId;
		super.addParameter("test_log_id",operatorEquals,new Integer(testLogId));
	}

	public int getVersionRefId() {
		return versionRefId;
	}

	public void setVersionRefId(int versionRefId) {
		this.versionRefId = versionRefId;
		super.addParameter("version_ref_id",operatorEquals,new Integer(versionRefId));
	}

	
}


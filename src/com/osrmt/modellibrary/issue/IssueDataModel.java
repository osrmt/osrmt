/*
    //usage

    Copyright (C) 2006  Aron Lancout Smith

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA

*/
package com.osrmt.modellibrary.issue;
import java.util.*;
import com.osframework.framework.locale.*;
import com.osframework.framework.logging.*;
import com.osframework.framework.utility.*;
import com.osframework.framework.exceptions.*;
import com.osframework.ejb.reference.common.*;
import com.osframework.ejb.reference.security.*;
import com.osframework.modellibrary.common.*;
import com.osframework.modellibrary.reference.group.*;
import com.osframework.appclient.services.*;
import com.osframework.modellibrary.reference.security.*;

/**
null
*/
public class IssueDataModel implements IControlModel, java.io.Serializable {

	static final long serialVersionUID = 1L;
	private int hashsize = 47;
	private Hashtable modifiedFields = new Hashtable(hashsize);
	private boolean referenceSet = false;


	/** Unique identifier to issue table  */
	private int issueId = 0;

	/** Foreign key to reference: IssueType  */
	private int issueTypeRefId = 0;

	private String issueTypeRefDisplay="";

	/** Unique number per issue type identifying issue  */
	private int issueNbr = 0;

	/** Foreign key to reference: Product  */
	private int productRefId = 0;

	private String productRefDisplay="";

	/** Issue display name  */
	private String issueName;

	/** Date/time issue occured  */
	private DbCalendar issueOccurDt = DbCalendar.getDbCalendar();

	/** Date/time issue was opened or reopened  */
	private DbCalendar issueOpenDt = DbCalendar.getDbCalendar();

	/** Foreign key to reference: IssueVersion  */
	private int versionRefId = 0;

	private String versionRefDisplay="";

	/** Foreign key to artifact table  */
	private int artifactId = 0;

	/** Foreign key to test log  */
	private int testLogId = 0;

	/** Foreign key to reference: IssueStatus  */
	private int statusRefId = 0;

	private String statusRefDisplay="";

	/** Null when issue closed Foreign key to reference: UserGroup  */
	private int assignedUserGroupRefId = 0;

	private String assignedUserGroupRefDisplay="";

	/** User group assigned to this artifact  */
	private int assignedUserGroupId = 0;

	/** Null when issue closed Foreign key to user table  */
	private int assignedUserId = 0;

	private String assignedUserName="";

	/** Description of the issue  */
	private String description;

	/** Priority foreign key to reference: IssuePriority  */
	private int priorityRefId = 0;

	private String priorityRefDisplay="";

	/** Foreign key to reference: IssueSeverity  */
	private int severityRefId = 0;

	private String severityRefDisplay="";

	/** Date desired a fix by  */
	private DbCalendar fixByDt;

	/** Frequency of issue occurence  */
	private int frequencyRefId = 0;

	private String frequencyRefDisplay="";

	/** Author of issue  */
	private int submittedUserId = 0;

	private String submittedUserName="";

	/** Foreign key to organization table  */
	private int organizationId = 0;

	/** Foreign key to organization environment table  */
	private int organizationEnvironmentId = 0;

	/** Foreign key to reference: Reproduce  */
	private int reproduceRefId = 0;

	private String reproduceRefDisplay="";

	/** Duplicate or rolled up issue  */
	private int parentIssueId = 0;

	/** Effort to implement (man hours)  */
	private double effort = 0;

	/** Instructions on how to reproduce this issue  */
	private String reproduceDirections;

	/** Last update of the issue  */
	private String lastUpdate;

	/** History of the issue  */
	private String history;

	/** Date/time issue was closed  */
	private DbCalendar closedDt;

	/** Foreign key to reference: ClosedCategory  */
	private int closedCategoryRefId = 0;

	private String closedCategoryRefDisplay="";

	/** User who closed the issue  */
	private int closedUserId = 0;

	private String closedUserName="";

	/** Date/time issue was resolved  */
	private DbCalendar resolvedDt;

	/** User who resolved the issue  */
	private int resolvedUserId = 0;

	private String resolvedUserName="";

	/** Foreign key to reference: ResolvedCategory  */
	private int resolvedCategoryRefId = 0;

	private String resolvedCategoryRefDisplay="";

	/** External system identifier  */
	private String externalAlias1;

	/** External system identifier  */
	private String externalAlias2;

	/** External system identifier  */
	private String externalAlias3;

	/** Foreign key to reference: OperatingSystem  */
	private int clientOsRefId = 0;

	private String clientOsRefDisplay="";

	/** Foreign key to reference: OperatingSystem  */
	private int serverOsRefId = 0;

	private String serverOsRefDisplay="";

	/** Date the record was created  */
	private DbCalendar createDt = DbCalendar.getDbCalendar();

	/** User that created this record  */
	private int createUserId = 0;

	private String createUserName="";

	/** Date the record was updated  */
	private DbCalendar updateDt = DbCalendar.getDbCalendar();

	/** Date the record was last updated  */
	private int updateUserId = 0;

	private String updateUserName="";

	/** Number of times this record has been updated  */
	private int updateCount = 0;

	/** Active indicator 1=Active 0=Inactive  */
	private int activeInd = 1;

	/** 0: Customer defined >0:   */
	private int systemAssignedVersionNbr = 5;

	/** Identifies database row as Reference or Record and System or User entered  */
	private int recordTypeRefId = 321;

	private String recordTypeRefDisplay="";


	/** 
	 * Unique identifier to issue table
	 * 
	 */ 
	public int getIssueId() {
		return issueId;
	}

	/** 
	 * Unique identifier to issue table
	 * 
	 */ 
	public void setIssueId(int issueId) {
		if (!Comparison.areEqual(this.issueId, issueId)) {
			this.issueId = issueId;
			setModified("issueId");
		};
	}

	/** 
	 * Foreign key to reference: IssueType
	 * 
	 */ 
	public int getIssueTypeRefId() {
		return issueTypeRefId;
	}

	/** 
	 * Foreign key to reference: IssueType
	 * 
	 */ 
	public void setIssueTypeRefId(int issueTypeRefId) {
		if (!Comparison.areEqual(this.issueTypeRefId, issueTypeRefId)) {
			this.issueTypeRefId = issueTypeRefId;
			setModified("issueTypeRefId");
		};
	}

	/** 
	 * Foreign key to reference: IssueType
	 * 
	 */ 
	public String getIssueTypeRefDisplay() {
		return issueTypeRefDisplay;
	}

	/** 
	 * Foreign key to reference: IssueType
	 * 
	 */ 
	public void setIssueTypeRefDisplay(String display) {
		this.issueTypeRefDisplay = display;
	}

	/** 
	 * Unique number per issue type identifying issue
	 * 
	 */ 
	public int getIssueNbr() {
		return issueNbr;
	}

	/** 
	 * Unique number per issue type identifying issue
	 * 
	 */ 
	public void setIssueNbr(int issueNbr) {
		if (!Comparison.areEqual(this.issueNbr, issueNbr)) {
			this.issueNbr = issueNbr;
			setModified("issueNbr");
		};
	}

	/** 
	 * Foreign key to reference: Product
	 * 
	 */ 
	public int getProductRefId() {
		return productRefId;
	}

	/** 
	 * Foreign key to reference: Product
	 * 
	 * Required database field.
	 */ 
	public void setProductRefId(int productRefId) {
		if (!Comparison.areEqual(this.productRefId, productRefId)) {
			this.productRefId = productRefId;
			setModified("productRefId");
		};
	}

	/** 
	 * Foreign key to reference: Product
	 * 
	 */ 
	public String getProductRefDisplay() {
		return productRefDisplay;
	}

	/** 
	 * Foreign key to reference: Product
	 * 
	 */ 
	public void setProductRefDisplay(String display) {
		this.productRefDisplay = display;
	}

	/** 
	 * Issue display name
	 * 
	 */ 
	public String getIssueName() {
		return issueName;
	}

	/** 
	 * Issue display name
	 * 
	 */ 
	public void setIssueName(String issueName) {
		if (!Comparison.areEqual(this.issueName, issueName)) {
			this.issueName = issueName;
			setModified("issueName");
		};
	}

	/** 
	 * Date/time issue occured
	 * 
	 */ 
	public DbCalendar getIssueOccurDt() {
		return issueOccurDt;
	}

	/** 
	 * Date/time issue occured
	 * 
	 */ 
	public void setIssueOccurDt(GregorianCalendar issueOccurDt) {
		if (!Comparison.areEqual(this.issueOccurDt, issueOccurDt)) {
			this.issueOccurDt = new DbCalendar();
			if (issueOccurDt != null) {
				this.issueOccurDt.setTimeInMillis(issueOccurDt.getTimeInMillis());
			}
			setModified("issueOccurDt");
		};
	}

	/** 
	 * Date/time issue occured
	 * 
	 */ 
	public void setIssueOccurDt(long milliseconds) {
		if (this.issueOccurDt==null) {
			this.issueOccurDt = new DbCalendar();
		}
		if (!Comparison.areEqual(this.issueOccurDt, issueOccurDt)) {
			this.issueOccurDt.setTimeInMillis(milliseconds);
			setModified("issueOccurDt");
		}

	}

	/** 
	 * Date/time issue was opened or reopened
	 * 
	 */ 
	public DbCalendar getIssueOpenDt() {
		return issueOpenDt;
	}

	/** 
	 * Date/time issue was opened or reopened
	 * 
	 */ 
	public void setIssueOpenDt(GregorianCalendar issueOpenDt) {
		if (!Comparison.areEqual(this.issueOpenDt, issueOpenDt)) {
			this.issueOpenDt = new DbCalendar();
			if (issueOpenDt != null) {
				this.issueOpenDt.setTimeInMillis(issueOpenDt.getTimeInMillis());
			}
			setModified("issueOpenDt");
		};
	}

	/** 
	 * Date/time issue was opened or reopened
	 * 
	 */ 
	public void setIssueOpenDt(long milliseconds) {
		if (this.issueOpenDt==null) {
			this.issueOpenDt = new DbCalendar();
		}
		if (!Comparison.areEqual(this.issueOpenDt, issueOpenDt)) {
			this.issueOpenDt.setTimeInMillis(milliseconds);
			setModified("issueOpenDt");
		}

	}

	/** 
	 * Foreign key to reference: IssueVersion
	 * 
	 */ 
	public int getVersionRefId() {
		return versionRefId;
	}

	/** 
	 * Foreign key to reference: IssueVersion
	 * 
	 * Required database field.
	 */ 
	public void setVersionRefId(int versionRefId) {
		if (!Comparison.areEqual(this.versionRefId, versionRefId)) {
			this.versionRefId = versionRefId;
			setModified("versionRefId");
		};
	}

	/** 
	 * Foreign key to reference: IssueVersion
	 * 
	 */ 
	public String getVersionRefDisplay() {
		return versionRefDisplay;
	}

	/** 
	 * Foreign key to reference: IssueVersion
	 * 
	 */ 
	public void setVersionRefDisplay(String display) {
		this.versionRefDisplay = display;
	}

	/** 
	 * Foreign key to artifact table
	 * 
	 */ 
	public int getArtifactId() {
		return artifactId;
	}

	/** 
	 * Foreign key to artifact table
	 * 
	 * Required database field.
	 */ 
	public void setArtifactId(int artifactId) {
		if (!Comparison.areEqual(this.artifactId, artifactId)) {
			this.artifactId = artifactId;
			setModified("artifactId");
		};
	}

	/** 
	 * Foreign key to test log
	 * 
	 */ 
	public int getTestLogId() {
		return testLogId;
	}

	/** 
	 * Foreign key to test log
	 * 
	 * Required database field.
	 */ 
	public void setTestLogId(int testLogId) {
		if (!Comparison.areEqual(this.testLogId, testLogId)) {
			this.testLogId = testLogId;
			setModified("testLogId");
		};
	}

	/** 
	 * Foreign key to reference: IssueStatus
	 * 
	 */ 
	public int getStatusRefId() {
		return statusRefId;
	}

	/** 
	 * Foreign key to reference: IssueStatus
	 * 
	 * Required database field.
	 */ 
	public void setStatusRefId(int statusRefId) {
		if (!Comparison.areEqual(this.statusRefId, statusRefId)) {
			this.statusRefId = statusRefId;
			setModified("statusRefId");
		};
	}

	/** 
	 * Foreign key to reference: IssueStatus
	 * 
	 */ 
	public String getStatusRefDisplay() {
		return statusRefDisplay;
	}

	/** 
	 * Foreign key to reference: IssueStatus
	 * 
	 */ 
	public void setStatusRefDisplay(String display) {
		this.statusRefDisplay = display;
	}

	/** 
	 * Null when issue closed Foreign key to reference: UserGroup
	 * 
	 */ 
	public int getAssignedUserGroupRefId() {
		return assignedUserGroupRefId;
	}

	/** 
	 * Null when issue closed Foreign key to reference: UserGroup
	 * 
	 * Required database field.
	 */ 
	public void setAssignedUserGroupRefId(int assignedUserGroupRefId) {
		if (!Comparison.areEqual(this.assignedUserGroupRefId, assignedUserGroupRefId)) {
			this.assignedUserGroupRefId = assignedUserGroupRefId;
			setModified("assignedUserGroupRefId");
		};
	}

	/** 
	 * Null when issue closed Foreign key to reference: UserGroup
	 * 
	 */ 
	public String getAssignedUserGroupRefDisplay() {
		return assignedUserGroupRefDisplay;
	}

	/** 
	 * Null when issue closed Foreign key to reference: UserGroup
	 * 
	 */ 
	public void setAssignedUserGroupRefDisplay(String display) {
		this.assignedUserGroupRefDisplay = display;
	}

	/** 
	 * User group assigned to this artifact
	 * 
	 */ 
	public int getAssignedUserGroupId() {
		return assignedUserGroupId;
	}

	/** 
	 * User group assigned to this artifact
	 * 
	 */ 
	public void setAssignedUserGroupId(int assignedUserGroupId) {
		if (!Comparison.areEqual(this.assignedUserGroupId, assignedUserGroupId)) {
			this.assignedUserGroupId = assignedUserGroupId;
			setModified("assignedUserGroupId");
		};
	}

	/** 
	 * Null when issue closed Foreign key to user table
	 * 
	 */ 
	public int getAssignedUserId() {
		return assignedUserId;
	}

	/** 
	 * Null when issue closed Foreign key to user table
	 * 
	 * Required database field.
	 */ 
	public void setAssignedUserId(int assignedUserId) {
		if (!Comparison.areEqual(this.assignedUserId, assignedUserId)) {
			this.assignedUserId = assignedUserId;
			setModified("assignedUserId");
		};
	}

	/** 
	 * Null when issue closed Foreign key to user table
	 * 
	 */ 
	public String getAssignedUserName() {
		return assignedUserName;
	}

	/** 
	 * Null when issue closed Foreign key to user table
	 * 
	 */ 
	public void setAssignedUserName(String userName) {
		this.assignedUserName = userName;
	}

	/** 
	 * Description of the issue
	 * 
	 */ 
	public String getDescription() {
		return description;
	}

	/** 
	 * Description of the issue
	 * 
	 */ 
	public void setDescription(String description) {
		if (!Comparison.areEqual(this.description, description)) {
			this.description = description;
			setModified("description");
		};
	}

	/** 
	 * Priority foreign key to reference: IssuePriority
	 * 
	 */ 
	public int getPriorityRefId() {
		return priorityRefId;
	}

	/** 
	 * Priority foreign key to reference: IssuePriority
	 * 
	 * Required database field.
	 */ 
	public void setPriorityRefId(int priorityRefId) {
		if (!Comparison.areEqual(this.priorityRefId, priorityRefId)) {
			this.priorityRefId = priorityRefId;
			setModified("priorityRefId");
		};
	}

	/** 
	 * Priority foreign key to reference: IssuePriority
	 * 
	 */ 
	public String getPriorityRefDisplay() {
		return priorityRefDisplay;
	}

	/** 
	 * Priority foreign key to reference: IssuePriority
	 * 
	 */ 
	public void setPriorityRefDisplay(String display) {
		this.priorityRefDisplay = display;
	}

	/** 
	 * Foreign key to reference: IssueSeverity
	 * 
	 */ 
	public int getSeverityRefId() {
		return severityRefId;
	}

	/** 
	 * Foreign key to reference: IssueSeverity
	 * 
	 * Required database field.
	 */ 
	public void setSeverityRefId(int severityRefId) {
		if (!Comparison.areEqual(this.severityRefId, severityRefId)) {
			this.severityRefId = severityRefId;
			setModified("severityRefId");
		};
	}

	/** 
	 * Foreign key to reference: IssueSeverity
	 * 
	 */ 
	public String getSeverityRefDisplay() {
		return severityRefDisplay;
	}

	/** 
	 * Foreign key to reference: IssueSeverity
	 * 
	 */ 
	public void setSeverityRefDisplay(String display) {
		this.severityRefDisplay = display;
	}

	/** 
	 * Date desired a fix by
	 * 
	 */ 
	public DbCalendar getFixByDt() {
		return fixByDt;
	}

	/** 
	 * Date desired a fix by
	 * 
	 */ 
	public void setFixByDt(GregorianCalendar fixByDt) {
		if (!Comparison.areEqual(this.fixByDt, fixByDt)) {
			this.fixByDt = new DbCalendar();
			if (fixByDt != null) {
				this.fixByDt.setTimeInMillis(fixByDt.getTimeInMillis());
			}
			setModified("fixByDt");
		};
	}

	/** 
	 * Date desired a fix by
	 * 
	 */ 
	public void setFixByDt(long milliseconds) {
		if (this.fixByDt==null) {
			this.fixByDt = new DbCalendar();
		}
		if (!Comparison.areEqual(this.fixByDt, fixByDt)) {
			this.fixByDt.setTimeInMillis(milliseconds);
			setModified("fixByDt");
		}

	}

	/** 
	 * Frequency of issue occurence
	 * 
	 */ 
	public int getFrequencyRefId() {
		return frequencyRefId;
	}

	/** 
	 * Frequency of issue occurence
	 * 
	 */ 
	public void setFrequencyRefId(int frequencyRefId) {
		if (!Comparison.areEqual(this.frequencyRefId, frequencyRefId)) {
			this.frequencyRefId = frequencyRefId;
			setModified("frequencyRefId");
		};
	}

	/** 
	 * Frequency of issue occurence
	 * 
	 */ 
	public String getFrequencyRefDisplay() {
		return frequencyRefDisplay;
	}

	/** 
	 * Frequency of issue occurence
	 * 
	 */ 
	public void setFrequencyRefDisplay(String display) {
		this.frequencyRefDisplay = display;
	}

	/** 
	 * Author of issue
	 * 
	 */ 
	public int getSubmittedUserId() {
		return submittedUserId;
	}

	/** 
	 * Author of issue
	 * 
	 * Required database field.
	 */ 
	public void setSubmittedUserId(int submittedUserId) {
		if (!Comparison.areEqual(this.submittedUserId, submittedUserId)) {
			this.submittedUserId = submittedUserId;
			setModified("submittedUserId");
		};
	}

	/** 
	 * Author of issue
	 * 
	 */ 
	public String getSubmittedUserName() {
		return submittedUserName;
	}

	/** 
	 * Author of issue
	 * 
	 */ 
	public void setSubmittedUserName(String userName) {
		this.submittedUserName = userName;
	}

	/** 
	 * Foreign key to organization table
	 * 
	 */ 
	public int getOrganizationId() {
		return organizationId;
	}

	/** 
	 * Foreign key to organization table
	 * 
	 */ 
	public void setOrganizationId(int organizationId) {
		if (!Comparison.areEqual(this.organizationId, organizationId)) {
			this.organizationId = organizationId;
			setModified("organizationId");
		};
	}

	/** 
	 * Foreign key to organization environment table
	 * 
	 */ 
	public int getOrganizationEnvironmentId() {
		return organizationEnvironmentId;
	}

	/** 
	 * Foreign key to organization environment table
	 * 
	 */ 
	public void setOrganizationEnvironmentId(int organizationEnvironmentId) {
		if (!Comparison.areEqual(this.organizationEnvironmentId, organizationEnvironmentId)) {
			this.organizationEnvironmentId = organizationEnvironmentId;
			setModified("organizationEnvironmentId");
		};
	}

	/** 
	 * Foreign key to reference: Reproduce
	 * 
	 */ 
	public int getReproduceRefId() {
		return reproduceRefId;
	}

	/** 
	 * Foreign key to reference: Reproduce
	 * 
	 * Required database field.
	 */ 
	public void setReproduceRefId(int reproduceRefId) {
		if (!Comparison.areEqual(this.reproduceRefId, reproduceRefId)) {
			this.reproduceRefId = reproduceRefId;
			setModified("reproduceRefId");
		};
	}

	/** 
	 * Foreign key to reference: Reproduce
	 * 
	 */ 
	public String getReproduceRefDisplay() {
		return reproduceRefDisplay;
	}

	/** 
	 * Foreign key to reference: Reproduce
	 * 
	 */ 
	public void setReproduceRefDisplay(String display) {
		this.reproduceRefDisplay = display;
	}

	/** 
	 * Duplicate or rolled up issue
	 * 
	 */ 
	public int getParentIssueId() {
		return parentIssueId;
	}

	/** 
	 * Duplicate or rolled up issue
	 * 
	 */ 
	public void setParentIssueId(int parentIssueId) {
		if (!Comparison.areEqual(this.parentIssueId, parentIssueId)) {
			this.parentIssueId = parentIssueId;
			setModified("parentIssueId");
		};
	}

	/** 
	 * Effort to implement (man hours)
	 * 
	 */ 
	public double getEffort() {
		return effort;
	}

	/** 
	 * Effort to implement (man hours)
	 * 
	 * Required database field.
	 */ 
	public void setEffort(double effort) {
		if (!Comparison.areEqual(this.effort, effort)) {
			this.effort = effort;
			setModified("effort");
		};
	}

	/** 
	 * Instructions on how to reproduce this issue
	 * 
	 */ 
	public String getReproduceDirections() {
		return reproduceDirections;
	}

	/** 
	 * Instructions on how to reproduce this issue
	 * 
	 */ 
	public void setReproduceDirections(String reproduceDirections) {
		if (!Comparison.areEqual(this.reproduceDirections, reproduceDirections)) {
			this.reproduceDirections = reproduceDirections;
			setModified("reproduceDirections");
		};
	}

	/** 
	 * Last update of the issue
	 * 
	 */ 
	public String getLastUpdate() {
		return lastUpdate;
	}

	/** 
	 * Last update of the issue
	 * 
	 */ 
	public void setLastUpdate(String lastUpdate) {
		if (!Comparison.areEqual(this.lastUpdate, lastUpdate)) {
			this.lastUpdate = lastUpdate;
			setModified("lastUpdate");
		};
	}

	/** 
	 * History of the issue
	 * 
	 */ 
	public String getHistory() {
		return history;
	}

	/** 
	 * History of the issue
	 * 
	 */ 
	public void setHistory(String history) {
		if (!Comparison.areEqual(this.history, history)) {
			this.history = history;
			setModified("history");
		};
	}

	/** 
	 * Date/time issue was closed
	 * 
	 */ 
	public DbCalendar getClosedDt() {
		return closedDt;
	}

	/** 
	 * Date/time issue was closed
	 * 
	 */ 
	public void setClosedDt(GregorianCalendar closedDt) {
		if (!Comparison.areEqual(this.closedDt, closedDt)) {
			this.closedDt = new DbCalendar();
			if (closedDt != null) {
				this.closedDt.setTimeInMillis(closedDt.getTimeInMillis());
			}
			setModified("closedDt");
		};
	}

	/** 
	 * Date/time issue was closed
	 * 
	 */ 
	public void setClosedDt(long milliseconds) {
		if (this.closedDt==null) {
			this.closedDt = new DbCalendar();
		}
		if (!Comparison.areEqual(this.closedDt, closedDt)) {
			this.closedDt.setTimeInMillis(milliseconds);
			setModified("closedDt");
		}

	}

	/** 
	 * Foreign key to reference: ClosedCategory
	 * 
	 */ 
	public int getClosedCategoryRefId() {
		return closedCategoryRefId;
	}

	/** 
	 * Foreign key to reference: ClosedCategory
	 * 
	 */ 
	public void setClosedCategoryRefId(int closedCategoryRefId) {
		if (!Comparison.areEqual(this.closedCategoryRefId, closedCategoryRefId)) {
			this.closedCategoryRefId = closedCategoryRefId;
			setModified("closedCategoryRefId");
		};
	}

	/** 
	 * Foreign key to reference: ClosedCategory
	 * 
	 */ 
	public String getClosedCategoryRefDisplay() {
		return closedCategoryRefDisplay;
	}

	/** 
	 * Foreign key to reference: ClosedCategory
	 * 
	 */ 
	public void setClosedCategoryRefDisplay(String display) {
		this.closedCategoryRefDisplay = display;
	}

	/** 
	 * User who closed the issue
	 * 
	 */ 
	public int getClosedUserId() {
		return closedUserId;
	}

	/** 
	 * User who closed the issue
	 * 
	 */ 
	public void setClosedUserId(int closedUserId) {
		if (!Comparison.areEqual(this.closedUserId, closedUserId)) {
			this.closedUserId = closedUserId;
			setModified("closedUserId");
		};
	}

	/** 
	 * User who closed the issue
	 * 
	 */ 
	public String getClosedUserName() {
		return closedUserName;
	}

	/** 
	 * User who closed the issue
	 * 
	 */ 
	public void setClosedUserName(String userName) {
		this.closedUserName = userName;
	}

	/** 
	 * Date/time issue was resolved
	 * 
	 */ 
	public DbCalendar getResolvedDt() {
		return resolvedDt;
	}

	/** 
	 * Date/time issue was resolved
	 * 
	 */ 
	public void setResolvedDt(GregorianCalendar resolvedDt) {
		if (!Comparison.areEqual(this.resolvedDt, resolvedDt)) {
			this.resolvedDt = new DbCalendar();
			if (resolvedDt != null) {
				this.resolvedDt.setTimeInMillis(resolvedDt.getTimeInMillis());
			}
			setModified("resolvedDt");
		};
	}

	/** 
	 * Date/time issue was resolved
	 * 
	 */ 
	public void setResolvedDt(long milliseconds) {
		if (this.resolvedDt==null) {
			this.resolvedDt = new DbCalendar();
		}
		if (!Comparison.areEqual(this.resolvedDt, resolvedDt)) {
			this.resolvedDt.setTimeInMillis(milliseconds);
			setModified("resolvedDt");
		}

	}

	/** 
	 * User who resolved the issue
	 * 
	 */ 
	public int getResolvedUserId() {
		return resolvedUserId;
	}

	/** 
	 * User who resolved the issue
	 * 
	 */ 
	public void setResolvedUserId(int resolvedUserId) {
		if (!Comparison.areEqual(this.resolvedUserId, resolvedUserId)) {
			this.resolvedUserId = resolvedUserId;
			setModified("resolvedUserId");
		};
	}

	/** 
	 * User who resolved the issue
	 * 
	 */ 
	public String getResolvedUserName() {
		return resolvedUserName;
	}

	/** 
	 * User who resolved the issue
	 * 
	 */ 
	public void setResolvedUserName(String userName) {
		this.resolvedUserName = userName;
	}

	/** 
	 * Foreign key to reference: ResolvedCategory
	 * 
	 */ 
	public int getResolvedCategoryRefId() {
		return resolvedCategoryRefId;
	}

	/** 
	 * Foreign key to reference: ResolvedCategory
	 * 
	 */ 
	public void setResolvedCategoryRefId(int resolvedCategoryRefId) {
		if (!Comparison.areEqual(this.resolvedCategoryRefId, resolvedCategoryRefId)) {
			this.resolvedCategoryRefId = resolvedCategoryRefId;
			setModified("resolvedCategoryRefId");
		};
	}

	/** 
	 * Foreign key to reference: ResolvedCategory
	 * 
	 */ 
	public String getResolvedCategoryRefDisplay() {
		return resolvedCategoryRefDisplay;
	}

	/** 
	 * Foreign key to reference: ResolvedCategory
	 * 
	 */ 
	public void setResolvedCategoryRefDisplay(String display) {
		this.resolvedCategoryRefDisplay = display;
	}

	/** 
	 * External system identifier
	 * 
	 */ 
	public String getExternalAlias1() {
		return externalAlias1;
	}

	/** 
	 * External system identifier
	 * 
	 */ 
	public void setExternalAlias1(String externalAlias1) {
		if (!Comparison.areEqual(this.externalAlias1, externalAlias1)) {
			this.externalAlias1 = externalAlias1;
			setModified("externalAlias1");
		};
	}

	/** 
	 * External system identifier
	 * 
	 */ 
	public String getExternalAlias2() {
		return externalAlias2;
	}

	/** 
	 * External system identifier
	 * 
	 */ 
	public void setExternalAlias2(String externalAlias2) {
		if (!Comparison.areEqual(this.externalAlias2, externalAlias2)) {
			this.externalAlias2 = externalAlias2;
			setModified("externalAlias2");
		};
	}

	/** 
	 * External system identifier
	 * 
	 */ 
	public String getExternalAlias3() {
		return externalAlias3;
	}

	/** 
	 * External system identifier
	 * 
	 */ 
	public void setExternalAlias3(String externalAlias3) {
		if (!Comparison.areEqual(this.externalAlias3, externalAlias3)) {
			this.externalAlias3 = externalAlias3;
			setModified("externalAlias3");
		};
	}

	/** 
	 * Foreign key to reference: OperatingSystem
	 * 
	 */ 
	public int getClientOsRefId() {
		return clientOsRefId;
	}

	/** 
	 * Foreign key to reference: OperatingSystem
	 * 
	 */ 
	public void setClientOsRefId(int clientOsRefId) {
		if (!Comparison.areEqual(this.clientOsRefId, clientOsRefId)) {
			this.clientOsRefId = clientOsRefId;
			setModified("clientOsRefId");
		};
	}

	/** 
	 * Foreign key to reference: OperatingSystem
	 * 
	 */ 
	public String getClientOsRefDisplay() {
		return clientOsRefDisplay;
	}

	/** 
	 * Foreign key to reference: OperatingSystem
	 * 
	 */ 
	public void setClientOsRefDisplay(String display) {
		this.clientOsRefDisplay = display;
	}

	/** 
	 * Foreign key to reference: OperatingSystem
	 * 
	 */ 
	public int getServerOsRefId() {
		return serverOsRefId;
	}

	/** 
	 * Foreign key to reference: OperatingSystem
	 * 
	 */ 
	public void setServerOsRefId(int serverOsRefId) {
		if (!Comparison.areEqual(this.serverOsRefId, serverOsRefId)) {
			this.serverOsRefId = serverOsRefId;
			setModified("serverOsRefId");
		};
	}

	/** 
	 * Foreign key to reference: OperatingSystem
	 * 
	 */ 
	public String getServerOsRefDisplay() {
		return serverOsRefDisplay;
	}

	/** 
	 * Foreign key to reference: OperatingSystem
	 * 
	 */ 
	public void setServerOsRefDisplay(String display) {
		this.serverOsRefDisplay = display;
	}

	/** 
	 * Date the record was created
	 * 
	 */ 
	public DbCalendar getCreateDt() {
		return createDt;
	}

	/** 
	 * Date the record was created
	 * 
	 */ 
	public void setCreateDt(GregorianCalendar createDt) {
		if (!Comparison.areEqual(this.createDt, createDt)) {
			this.createDt = new DbCalendar();
			if (createDt != null) {
				this.createDt.setTimeInMillis(createDt.getTimeInMillis());
			}
			setModified("createDt");
		};
	}

	/** 
	 * Date the record was created
	 * 
	 */ 
	public void setCreateDt(long milliseconds) {
		if (this.createDt==null) {
			this.createDt = new DbCalendar();
		}
		if (!Comparison.areEqual(this.createDt, createDt)) {
			this.createDt.setTimeInMillis(milliseconds);
			setModified("createDt");
		}

	}

	/** 
	 * User that created this record
	 * 
	 */ 
	public int getCreateUserId() {
		return createUserId;
	}

	/** 
	 * User that created this record
	 * 
	 * Required database field.
	 */ 
	public void setCreateUserId(int createUserId) {
		if (!Comparison.areEqual(this.createUserId, createUserId)) {
			this.createUserId = createUserId;
			setModified("createUserId");
		};
	}

	/** 
	 * User that created this record
	 * 
	 */ 
	public String getCreateUserName() {
		return createUserName;
	}

	/** 
	 * User that created this record
	 * 
	 */ 
	public void setCreateUserName(String userName) {
		this.createUserName = userName;
	}

	/** 
	 * Date the record was updated
	 * 
	 */ 
	public DbCalendar getUpdateDt() {
		return updateDt;
	}

	/** 
	 * Date the record was updated
	 * 
	 */ 
	public void setUpdateDt(GregorianCalendar updateDt) {
		if (!Comparison.areEqual(this.updateDt, updateDt)) {
			this.updateDt = new DbCalendar();
			if (updateDt != null) {
				this.updateDt.setTimeInMillis(updateDt.getTimeInMillis());
			}
			setModified("updateDt");
		};
	}

	/** 
	 * Date the record was updated
	 * 
	 */ 
	public void setUpdateDt(long milliseconds) {
		if (this.updateDt==null) {
			this.updateDt = new DbCalendar();
		}
		if (!Comparison.areEqual(this.updateDt, updateDt)) {
			this.updateDt.setTimeInMillis(milliseconds);
			setModified("updateDt");
		}

	}

	/** 
	 * Date the record was last updated
	 * 
	 */ 
	public int getUpdateUserId() {
		return updateUserId;
	}

	/** 
	 * Date the record was last updated
	 * 
	 * Required database field.
	 */ 
	public void setUpdateUserId(int updateUserId) {
		if (!Comparison.areEqual(this.updateUserId, updateUserId)) {
			this.updateUserId = updateUserId;
			setModified("updateUserId");
		};
	}

	/** 
	 * Date the record was last updated
	 * 
	 */ 
	public String getUpdateUserName() {
		return updateUserName;
	}

	/** 
	 * Date the record was last updated
	 * 
	 */ 
	public void setUpdateUserName(String userName) {
		this.updateUserName = userName;
	}

	/** 
	 * Number of times this record has been updated
	 * 
	 */ 
	public int getUpdateCount() {
		return updateCount;
	}

	/** 
	 * Number of times this record has been updated
	 * 
	 * Required database field.
	 */ 
	public void setUpdateCount(int updateCount) {
		if (!Comparison.areEqual(this.updateCount, updateCount)) {
			this.updateCount = updateCount;
			setModified("updateCount");
		};
	}

	/** 
	 * Active indicator 1=Active 0=Inactive
	 * 
	 */ 
	public int getActiveInd() {
		return activeInd;
	}

	/** 
	 * Active indicator 1=Active 0=Inactive
	 * 
	 * Required database field.
	 */ 
	public void setActiveInd(int activeInd) {
		if (!Comparison.areEqual(this.activeInd, activeInd)) {
			this.activeInd = activeInd;
			setModified("activeInd");
		};
	}

	/** 
	 * Active indicator 1=Active 0=Inactive
	 * 
	 */ 
	public boolean isActive() {
		return activeInd == 1;
	}

	/** 
	 * Active indicator 1=Active 0=Inactive
	 * 
	 */ 
	public boolean isNotActive() {
		return activeInd == 0;
	}

	/** 
	 * Active indicator 1=Active 0=Inactive
	 * 
	 */ 
	public void setActive() {
		this.setActiveInd(1);
	}

	public void setNotActive() {
		this.setActiveInd(0);
	}

	/** 
	 * 0: Customer defined >0: 
	 * 
	 */ 
	public int getSystemAssignedVersionNbr() {
		return systemAssignedVersionNbr;
	}

	/** 
	 * 0: Customer defined >0: 
	 * 
	 * Required database field.
	 */ 
	public void setSystemAssignedVersionNbr(int systemAssignedVersionNbr) {
		if (!Comparison.areEqual(this.systemAssignedVersionNbr, systemAssignedVersionNbr)) {
			this.systemAssignedVersionNbr = systemAssignedVersionNbr;
			setModified("systemAssignedVersionNbr");
		};
	}

	/** 
	 * Identifies database row as Reference or Record and System or User entered
	 * 
	 */ 
	public int getRecordTypeRefId() {
		return recordTypeRefId;
	}

	/** 
	 * Identifies database row as Reference or Record and System or User entered
	 * 
	 * Required database field.
	 */ 
	public void setRecordTypeRefId(int recordTypeRefId) {
		if (!Comparison.areEqual(this.recordTypeRefId, recordTypeRefId)) {
			this.recordTypeRefId = recordTypeRefId;
			setModified("recordTypeRefId");
		};
	}

	/** 
	 * Identifies database row as Reference or Record and System or User entered
	 * 
	 */ 
	public String getRecordTypeRefDisplay() {
		return recordTypeRefDisplay;
	}

	/** 
	 * Identifies database row as Reference or Record and System or User entered
	 * 
	 */ 
	public void setRecordTypeRefDisplay(String display) {
		this.recordTypeRefDisplay = display;
	}


public static ResultColumnList getResultColumnList() {
	ResultColumnList columns = new ResultColumnList();
		columns.addColumn("issueId", Integer.class);
		columns.addColumn("issueTypeRefId", Integer.class);
		columns.addColumn("issueTypeRefDisplay", String.class);
		columns.addColumn("issueNbr", Integer.class);
		columns.addColumn("productRefId", Integer.class);
		columns.addColumn("productRefDisplay", String.class);
		columns.addColumn("issueName", String.class);
		columns.addColumn("issueOccurDt", GregorianCalendar.class);
		columns.addColumn("issueOpenDt", GregorianCalendar.class);
		columns.addColumn("versionRefId", Integer.class);
		columns.addColumn("versionRefDisplay", String.class);
		columns.addColumn("artifactId", Integer.class);
		columns.addColumn("testLogId", Integer.class);
		columns.addColumn("statusRefId", Integer.class);
		columns.addColumn("statusRefDisplay", String.class);
		columns.addColumn("assignedUserGroupRefId", Integer.class);
		columns.addColumn("assignedUserGroupRefDisplay", String.class);
		columns.addColumn("assignedUserGroupId", Integer.class);
		columns.addColumn("assignedUserId", Integer.class);
		columns.addColumn("assignedUserName", String.class);
		columns.addColumn("description", String.class);
		columns.addColumn("priorityRefId", Integer.class);
		columns.addColumn("priorityRefDisplay", String.class);
		columns.addColumn("severityRefId", Integer.class);
		columns.addColumn("severityRefDisplay", String.class);
		columns.addColumn("fixByDt", GregorianCalendar.class);
		columns.addColumn("frequencyRefId", Integer.class);
		columns.addColumn("frequencyRefDisplay", String.class);
		columns.addColumn("submittedUserId", Integer.class);
		columns.addColumn("submittedUserName", String.class);
		columns.addColumn("organizationId", Integer.class);
		columns.addColumn("organizationEnvironmentId", Integer.class);
		columns.addColumn("reproduceRefId", Integer.class);
		columns.addColumn("reproduceRefDisplay", String.class);
		columns.addColumn("parentIssueId", Integer.class);
		columns.addColumn("effort", Double.class);
		columns.addColumn("reproduceDirections", String.class);
		columns.addColumn("lastUpdate", String.class);
		columns.addColumn("history", String.class);
		columns.addColumn("closedDt", GregorianCalendar.class);
		columns.addColumn("closedCategoryRefId", Integer.class);
		columns.addColumn("closedCategoryRefDisplay", String.class);
		columns.addColumn("closedUserId", Integer.class);
		columns.addColumn("closedUserName", String.class);
		columns.addColumn("resolvedDt", GregorianCalendar.class);
		columns.addColumn("resolvedUserId", Integer.class);
		columns.addColumn("resolvedUserName", String.class);
		columns.addColumn("resolvedCategoryRefId", Integer.class);
		columns.addColumn("resolvedCategoryRefDisplay", String.class);
		columns.addColumn("externalAlias1", String.class);
		columns.addColumn("externalAlias2", String.class);
		columns.addColumn("externalAlias3", String.class);
		columns.addColumn("clientOsRefId", Integer.class);
		columns.addColumn("clientOsRefDisplay", String.class);
		columns.addColumn("serverOsRefId", Integer.class);
		columns.addColumn("serverOsRefDisplay", String.class);
		columns.addColumn("createDt", GregorianCalendar.class);
		columns.addColumn("createUserId", Integer.class);
		columns.addColumn("createUserName", String.class);
		columns.addColumn("updateDt", GregorianCalendar.class);
		columns.addColumn("updateUserId", Integer.class);
		columns.addColumn("updateUserName", String.class);
		columns.addColumn("updateCount", Integer.class);
		columns.addColumn("activeInd", Integer.class);
		columns.addColumn("systemAssignedVersionNbr", Integer.class);
		columns.addColumn("recordTypeRefId", Integer.class);
		columns.addColumn("recordTypeRefDisplay", String.class);
		return columns;
	}
		
	/** 
	 * Flags this field as being modified
	 */ 
	public void setReferenceDisplay(IReferenceMap reference, ISecurity security) {
		try {
			if (referenceSet) {
				return;
			}
		referenceSet = true;
			if (reference != null && this.issueTypeRefId > 0) setIssueTypeRefDisplay(reference.getDisplay(this.issueTypeRefId));
			if (reference != null && this.productRefId > 0) setProductRefDisplay(reference.getDisplay(this.productRefId));
			if (reference != null && this.versionRefId > 0) setVersionRefDisplay(reference.getDisplay(this.versionRefId));
			if (reference != null && this.statusRefId > 0) setStatusRefDisplay(reference.getDisplay(this.statusRefId));
			if (reference != null && this.assignedUserGroupRefId > 0) setAssignedUserGroupRefDisplay(reference.getDisplay(this.assignedUserGroupRefId));
			if (security != null && this.assignedUserId > 0) setAssignedUserName(security.getUser(this.assignedUserId).getDisplayName());
			if (reference != null && this.priorityRefId > 0) setPriorityRefDisplay(reference.getDisplay(this.priorityRefId));
			if (reference != null && this.severityRefId > 0) setSeverityRefDisplay(reference.getDisplay(this.severityRefId));
			if (reference != null && this.frequencyRefId > 0) setFrequencyRefDisplay(reference.getDisplay(this.frequencyRefId));
			if (security != null && this.submittedUserId > 0) setSubmittedUserName(security.getUser(this.submittedUserId).getDisplayName());
			if (reference != null && this.reproduceRefId > 0) setReproduceRefDisplay(reference.getDisplay(this.reproduceRefId));
			if (reference != null && this.closedCategoryRefId > 0) setClosedCategoryRefDisplay(reference.getDisplay(this.closedCategoryRefId));
			if (security != null && this.closedUserId > 0) setClosedUserName(security.getUser(this.closedUserId).getDisplayName());
			if (security != null && this.resolvedUserId > 0) setResolvedUserName(security.getUser(this.resolvedUserId).getDisplayName());
			if (reference != null && this.resolvedCategoryRefId > 0) setResolvedCategoryRefDisplay(reference.getDisplay(this.resolvedCategoryRefId));
			if (reference != null && this.clientOsRefId > 0) setClientOsRefDisplay(reference.getDisplay(this.clientOsRefId));
			if (reference != null && this.serverOsRefId > 0) setServerOsRefDisplay(reference.getDisplay(this.serverOsRefId));
			if (security != null && this.createUserId > 0) setCreateUserName(security.getUser(this.createUserId).getDisplayName());
			if (security != null && this.updateUserId > 0) setUpdateUserName(security.getUser(this.updateUserId).getDisplayName());
			if (reference != null && this.recordTypeRefId > 0) setRecordTypeRefDisplay(reference.getDisplay(this.recordTypeRefId));
		} catch (Exception e) {
			Debug.LogException(this, e);
		}
	}		
	/** 
	 * Flags this field as being modified
	 */ 
	public void setModified(String fieldName) {
		if (!modifiedFields.containsKey(fieldName)) {
			modifiedFields.put(fieldName, "1");
		}
	}

	/** 
	 * Resets flagged fields so none are marked as modified
	 */ 
	public void resetModified() {
		modifiedFields = new Hashtable(hashsize);
	}

	public boolean hasModified() {
		return modifiedFields.size() > 0;
	}

	
		
	/** 
	 * Copys the values of all modified fields to object m
	 */ 
	public void copyModifiedTo(IssueDataModel m) {
		Enumeration e1 = modifiedFields.keys();
		while (e1.hasMoreElements()) {
			String fieldName = (String) e1.nextElement();

			if (fieldName.compareTo("issueId")==0)
				m.setIssueId(this.getIssueId());
			else if (fieldName.compareTo("issueTypeRefId")==0)
				m.setIssueTypeRefId(this.getIssueTypeRefId());
			else if (fieldName.compareTo("issueNbr")==0)
				m.setIssueNbr(this.getIssueNbr());
			else if (fieldName.compareTo("productRefId")==0)
				m.setProductRefId(this.getProductRefId());
			else if (fieldName.compareTo("issueName")==0)
				m.setIssueName(this.getIssueName());
			else if (fieldName.compareTo("issueOccurDt")==0)
				m.setIssueOccurDt(this.getIssueOccurDt());
			else if (fieldName.compareTo("issueOpenDt")==0)
				m.setIssueOpenDt(this.getIssueOpenDt());
			else if (fieldName.compareTo("versionRefId")==0)
				m.setVersionRefId(this.getVersionRefId());
			else if (fieldName.compareTo("artifactId")==0)
				m.setArtifactId(this.getArtifactId());
			else if (fieldName.compareTo("testLogId")==0)
				m.setTestLogId(this.getTestLogId());
			else if (fieldName.compareTo("statusRefId")==0)
				m.setStatusRefId(this.getStatusRefId());
			else if (fieldName.compareTo("assignedUserGroupRefId")==0)
				m.setAssignedUserGroupRefId(this.getAssignedUserGroupRefId());
			else if (fieldName.compareTo("assignedUserGroupId")==0)
				m.setAssignedUserGroupId(this.getAssignedUserGroupId());
			else if (fieldName.compareTo("assignedUserId")==0)
				m.setAssignedUserId(this.getAssignedUserId());
			else if (fieldName.compareTo("description")==0)
				m.setDescription(this.getDescription());
			else if (fieldName.compareTo("priorityRefId")==0)
				m.setPriorityRefId(this.getPriorityRefId());
			else if (fieldName.compareTo("severityRefId")==0)
				m.setSeverityRefId(this.getSeverityRefId());
			else if (fieldName.compareTo("fixByDt")==0)
				m.setFixByDt(this.getFixByDt());
			else if (fieldName.compareTo("frequencyRefId")==0)
				m.setFrequencyRefId(this.getFrequencyRefId());
			else if (fieldName.compareTo("submittedUserId")==0)
				m.setSubmittedUserId(this.getSubmittedUserId());
			else if (fieldName.compareTo("organizationId")==0)
				m.setOrganizationId(this.getOrganizationId());
			else if (fieldName.compareTo("organizationEnvironmentId")==0)
				m.setOrganizationEnvironmentId(this.getOrganizationEnvironmentId());
			else if (fieldName.compareTo("reproduceRefId")==0)
				m.setReproduceRefId(this.getReproduceRefId());
			else if (fieldName.compareTo("parentIssueId")==0)
				m.setParentIssueId(this.getParentIssueId());
			else if (fieldName.compareTo("effort")==0)
				m.setEffort(this.getEffort());
			else if (fieldName.compareTo("reproduceDirections")==0)
				m.setReproduceDirections(this.getReproduceDirections());
			else if (fieldName.compareTo("lastUpdate")==0)
				m.setLastUpdate(this.getLastUpdate());
			else if (fieldName.compareTo("history")==0)
				m.setHistory(this.getHistory());
			else if (fieldName.compareTo("closedDt")==0)
				m.setClosedDt(this.getClosedDt());
			else if (fieldName.compareTo("closedCategoryRefId")==0)
				m.setClosedCategoryRefId(this.getClosedCategoryRefId());
			else if (fieldName.compareTo("closedUserId")==0)
				m.setClosedUserId(this.getClosedUserId());
			else if (fieldName.compareTo("resolvedDt")==0)
				m.setResolvedDt(this.getResolvedDt());
			else if (fieldName.compareTo("resolvedUserId")==0)
				m.setResolvedUserId(this.getResolvedUserId());
			else if (fieldName.compareTo("resolvedCategoryRefId")==0)
				m.setResolvedCategoryRefId(this.getResolvedCategoryRefId());
			else if (fieldName.compareTo("externalAlias1")==0)
				m.setExternalAlias1(this.getExternalAlias1());
			else if (fieldName.compareTo("externalAlias2")==0)
				m.setExternalAlias2(this.getExternalAlias2());
			else if (fieldName.compareTo("externalAlias3")==0)
				m.setExternalAlias3(this.getExternalAlias3());
			else if (fieldName.compareTo("clientOsRefId")==0)
				m.setClientOsRefId(this.getClientOsRefId());
			else if (fieldName.compareTo("serverOsRefId")==0)
				m.setServerOsRefId(this.getServerOsRefId());
			else if (fieldName.compareTo("createDt")==0)
				m.setCreateDt(this.getCreateDt());
			else if (fieldName.compareTo("createUserId")==0)
				m.setCreateUserId(this.getCreateUserId());
			else if (fieldName.compareTo("updateDt")==0)
				m.setUpdateDt(this.getUpdateDt());
			else if (fieldName.compareTo("updateUserId")==0)
				m.setUpdateUserId(this.getUpdateUserId());
			else if (fieldName.compareTo("updateCount")==0)
				m.setUpdateCount(this.getUpdateCount());
			else if (fieldName.compareTo("activeInd")==0)
				m.setActiveInd(this.getActiveInd());
			else if (fieldName.compareTo("systemAssignedVersionNbr")==0)
				m.setSystemAssignedVersionNbr(this.getSystemAssignedVersionNbr());
			else if (fieldName.compareTo("recordTypeRefId")==0)
				m.setRecordTypeRefId(this.getRecordTypeRefId());
			else 		
				Debug.LogError(this, ExceptionInfo.invalidCopyModifiedField + " " + fieldName);
	}
}

	/**
	 * Update this object with the data from m
	 */
	public void updateWith (IssueModel m) {

		this.setIssueId(m.getIssueId());
		this.setIssueTypeRefId(m.getIssueTypeRefId());
		this.setIssueTypeRefDisplay(m.getIssueTypeRefDisplay());
		this.setIssueNbr(m.getIssueNbr());
		this.setProductRefId(m.getProductRefId());
		this.setProductRefDisplay(m.getProductRefDisplay());
		this.setIssueName(m.getIssueName());
		this.setIssueOccurDt(m.getIssueOccurDt());
		this.setIssueOpenDt(m.getIssueOpenDt());
		this.setVersionRefId(m.getVersionRefId());
		this.setVersionRefDisplay(m.getVersionRefDisplay());
		this.setArtifactId(m.getArtifactId());
		this.setTestLogId(m.getTestLogId());
		this.setStatusRefId(m.getStatusRefId());
		this.setStatusRefDisplay(m.getStatusRefDisplay());
		this.setAssignedUserGroupRefId(m.getAssignedUserGroupRefId());
		this.setAssignedUserGroupRefDisplay(m.getAssignedUserGroupRefDisplay());
		this.setAssignedUserGroupId(m.getAssignedUserGroupId());
		this.setAssignedUserId(m.getAssignedUserId());
		this.setAssignedUserName(m.getAssignedUserName());
		this.setDescription(m.getDescription());
		this.setPriorityRefId(m.getPriorityRefId());
		this.setPriorityRefDisplay(m.getPriorityRefDisplay());
		this.setSeverityRefId(m.getSeverityRefId());
		this.setSeverityRefDisplay(m.getSeverityRefDisplay());
		this.setFixByDt(m.getFixByDt());
		this.setFrequencyRefId(m.getFrequencyRefId());
		this.setFrequencyRefDisplay(m.getFrequencyRefDisplay());
		this.setSubmittedUserId(m.getSubmittedUserId());
		this.setSubmittedUserName(m.getSubmittedUserName());
		this.setOrganizationId(m.getOrganizationId());
		this.setOrganizationEnvironmentId(m.getOrganizationEnvironmentId());
		this.setReproduceRefId(m.getReproduceRefId());
		this.setReproduceRefDisplay(m.getReproduceRefDisplay());
		this.setParentIssueId(m.getParentIssueId());
		this.setEffort(m.getEffort());
		this.setReproduceDirections(m.getReproduceDirections());
		this.setLastUpdate(m.getLastUpdate());
		this.setHistory(m.getHistory());
		this.setClosedDt(m.getClosedDt());
		this.setClosedCategoryRefId(m.getClosedCategoryRefId());
		this.setClosedCategoryRefDisplay(m.getClosedCategoryRefDisplay());
		this.setClosedUserId(m.getClosedUserId());
		this.setClosedUserName(m.getClosedUserName());
		this.setResolvedDt(m.getResolvedDt());
		this.setResolvedUserId(m.getResolvedUserId());
		this.setResolvedUserName(m.getResolvedUserName());
		this.setResolvedCategoryRefId(m.getResolvedCategoryRefId());
		this.setResolvedCategoryRefDisplay(m.getResolvedCategoryRefDisplay());
		this.setExternalAlias1(m.getExternalAlias1());
		this.setExternalAlias2(m.getExternalAlias2());
		this.setExternalAlias3(m.getExternalAlias3());
		this.setClientOsRefId(m.getClientOsRefId());
		this.setClientOsRefDisplay(m.getClientOsRefDisplay());
		this.setServerOsRefId(m.getServerOsRefId());
		this.setServerOsRefDisplay(m.getServerOsRefDisplay());
		this.setCreateDt(m.getCreateDt());
		this.setCreateUserId(m.getCreateUserId());
		this.setCreateUserName(m.getCreateUserName());
		this.setUpdateDt(m.getUpdateDt());
		this.setUpdateUserId(m.getUpdateUserId());
		this.setUpdateUserName(m.getUpdateUserName());
		this.setUpdateCount(m.getUpdateCount());
		this.setActiveInd(m.getActiveInd());
		this.setSystemAssignedVersionNbr(m.getSystemAssignedVersionNbr());
		this.setRecordTypeRefId(m.getRecordTypeRefId());
		this.setRecordTypeRefDisplay(m.getRecordTypeRefDisplay());
	}

	/**
	 * Compare the two objects
	 */
	public boolean isEqualTo (IssueModel m) {

		if (!Comparison.areEqual(this.getIssueId(),  m.getIssueId())) return false;
		if (!Comparison.areEqual(this.getIssueTypeRefId(),  m.getIssueTypeRefId())) return false;
		if (!Comparison.areEqual(this.getIssueNbr(),  m.getIssueNbr())) return false;
		if (!Comparison.areEqual(this.getProductRefId(),  m.getProductRefId())) return false;
		if (!Comparison.areEqual(this.getIssueName(),  m.getIssueName())) return false;
		if (!Comparison.areEqual(this.getIssueOccurDt(),  m.getIssueOccurDt())) return false;
		if (!Comparison.areEqual(this.getIssueOpenDt(),  m.getIssueOpenDt())) return false;
		if (!Comparison.areEqual(this.getVersionRefId(),  m.getVersionRefId())) return false;
		if (!Comparison.areEqual(this.getArtifactId(),  m.getArtifactId())) return false;
		if (!Comparison.areEqual(this.getTestLogId(),  m.getTestLogId())) return false;
		if (!Comparison.areEqual(this.getStatusRefId(),  m.getStatusRefId())) return false;
		if (!Comparison.areEqual(this.getAssignedUserGroupRefId(),  m.getAssignedUserGroupRefId())) return false;
		if (!Comparison.areEqual(this.getAssignedUserGroupId(),  m.getAssignedUserGroupId())) return false;
		if (!Comparison.areEqual(this.getAssignedUserId(),  m.getAssignedUserId())) return false;
		if (!Comparison.areEqual(this.getDescription(),  m.getDescription())) return false;
		if (!Comparison.areEqual(this.getPriorityRefId(),  m.getPriorityRefId())) return false;
		if (!Comparison.areEqual(this.getSeverityRefId(),  m.getSeverityRefId())) return false;
		if (!Comparison.areEqual(this.getFixByDt(),  m.getFixByDt())) return false;
		if (!Comparison.areEqual(this.getFrequencyRefId(),  m.getFrequencyRefId())) return false;
		if (!Comparison.areEqual(this.getSubmittedUserId(),  m.getSubmittedUserId())) return false;
		if (!Comparison.areEqual(this.getOrganizationId(),  m.getOrganizationId())) return false;
		if (!Comparison.areEqual(this.getOrganizationEnvironmentId(),  m.getOrganizationEnvironmentId())) return false;
		if (!Comparison.areEqual(this.getReproduceRefId(),  m.getReproduceRefId())) return false;
		if (!Comparison.areEqual(this.getParentIssueId(),  m.getParentIssueId())) return false;
		if (!Comparison.areEqual(this.getEffort(),  m.getEffort())) return false;
		if (!Comparison.areEqual(this.getReproduceDirections(),  m.getReproduceDirections())) return false;
		if (!Comparison.areEqual(this.getLastUpdate(),  m.getLastUpdate())) return false;
		if (!Comparison.areEqual(this.getHistory(),  m.getHistory())) return false;
		if (!Comparison.areEqual(this.getClosedDt(),  m.getClosedDt())) return false;
		if (!Comparison.areEqual(this.getClosedCategoryRefId(),  m.getClosedCategoryRefId())) return false;
		if (!Comparison.areEqual(this.getClosedUserId(),  m.getClosedUserId())) return false;
		if (!Comparison.areEqual(this.getResolvedDt(),  m.getResolvedDt())) return false;
		if (!Comparison.areEqual(this.getResolvedUserId(),  m.getResolvedUserId())) return false;
		if (!Comparison.areEqual(this.getResolvedCategoryRefId(),  m.getResolvedCategoryRefId())) return false;
		if (!Comparison.areEqual(this.getExternalAlias1(),  m.getExternalAlias1())) return false;
		if (!Comparison.areEqual(this.getExternalAlias2(),  m.getExternalAlias2())) return false;
		if (!Comparison.areEqual(this.getExternalAlias3(),  m.getExternalAlias3())) return false;
		if (!Comparison.areEqual(this.getClientOsRefId(),  m.getClientOsRefId())) return false;
		if (!Comparison.areEqual(this.getServerOsRefId(),  m.getServerOsRefId())) return false;
		if (!Comparison.areEqual(this.getCreateDt(),  m.getCreateDt())) return false;
		if (!Comparison.areEqual(this.getCreateUserId(),  m.getCreateUserId())) return false;
		if (!Comparison.areEqual(this.getUpdateCount(),  m.getUpdateCount())) return false;
		if (!Comparison.areEqual(this.getActiveInd(),  m.getActiveInd())) return false;
		if (!Comparison.areEqual(this.getSystemAssignedVersionNbr(),  m.getSystemAssignedVersionNbr())) return false;
		if (!Comparison.areEqual(this.getRecordTypeRefId(),  m.getRecordTypeRefId())) return false;
		return true;
	}
	/**
	 * Returns a list of all field names which are required and are null
	 */
	public Vector getMissingRequired () {

		Vector v = new Vector();
		return v;
	}
	public String toString() {
		try {
			StringBuffer sb = new StringBuffer(1024);
			sb.append("IssueModel:\r\n");
			sb.append("issue_id:" + getIssueId());
			sb.append("\r\n");
			sb.append("issue_type_ref_id:" + getIssueTypeRefId());
			sb.append("\r\n");
			sb.append("issue_type_ref_display:" + getIssueTypeRefDisplay());
			sb.append("\r\n");
			sb.append("issue_nbr:" + getIssueNbr());
			sb.append("\r\n");
			sb.append("product_ref_id:" + getProductRefId());
			sb.append("\r\n");
			sb.append("product_ref_display:" + getProductRefDisplay());
			sb.append("\r\n");
			sb.append("issue_name:" + getIssueName());
			sb.append("\r\n");
			sb.append("issue_occur_dt:" + CalendarUtility.Format(getIssueOccurDt(),AppFormats.getLongDateTimeFormat()));
			sb.append("\r\n");
			sb.append("issue_open_dt:" + CalendarUtility.Format(getIssueOpenDt(),AppFormats.getLongDateTimeFormat()));
			sb.append("\r\n");
			sb.append("version_ref_id:" + getVersionRefId());
			sb.append("\r\n");
			sb.append("version_ref_display:" + getVersionRefDisplay());
			sb.append("\r\n");
			sb.append("artifact_id:" + getArtifactId());
			sb.append("\r\n");
			sb.append("test_log_id:" + getTestLogId());
			sb.append("\r\n");
			sb.append("status_ref_id:" + getStatusRefId());
			sb.append("\r\n");
			sb.append("status_ref_display:" + getStatusRefDisplay());
			sb.append("\r\n");
			sb.append("assigned_user_group_ref_id:" + getAssignedUserGroupRefId());
			sb.append("\r\n");
			sb.append("assigned_user_group_ref_display:" + getAssignedUserGroupRefDisplay());
			sb.append("\r\n");
			sb.append("assigned_user_group_id:" + getAssignedUserGroupId());
			sb.append("\r\n");
			sb.append("assigned_user_id:" + getAssignedUserId());
			sb.append("\r\n");
			sb.append("assigned_user_name:" + getAssignedUserName());
			sb.append("\r\n");
			sb.append("description:" + getDescription());
			sb.append("\r\n");
			sb.append("priority_ref_id:" + getPriorityRefId());
			sb.append("\r\n");
			sb.append("priority_ref_display:" + getPriorityRefDisplay());
			sb.append("\r\n");
			sb.append("severity_ref_id:" + getSeverityRefId());
			sb.append("\r\n");
			sb.append("severity_ref_display:" + getSeverityRefDisplay());
			sb.append("\r\n");
			sb.append("fix_by_dt:" + CalendarUtility.Format(getFixByDt(),AppFormats.getLongDateTimeFormat()));
			sb.append("\r\n");
			sb.append("frequency_ref_id:" + getFrequencyRefId());
			sb.append("\r\n");
			sb.append("frequency_ref_display:" + getFrequencyRefDisplay());
			sb.append("\r\n");
			sb.append("submitted_user_id:" + getSubmittedUserId());
			sb.append("\r\n");
			sb.append("submitted_user_name:" + getSubmittedUserName());
			sb.append("\r\n");
			sb.append("organization_id:" + getOrganizationId());
			sb.append("\r\n");
			sb.append("organization_environment_id:" + getOrganizationEnvironmentId());
			sb.append("\r\n");
			sb.append("reproduce_ref_id:" + getReproduceRefId());
			sb.append("\r\n");
			sb.append("reproduce_ref_display:" + getReproduceRefDisplay());
			sb.append("\r\n");
			sb.append("parent_issue_id:" + getParentIssueId());
			sb.append("\r\n");
			sb.append("effort:" + getEffort());
			sb.append("\r\n");
			sb.append("reproduce_directions:" + getReproduceDirections());
			sb.append("\r\n");
			sb.append("last_update:" + getLastUpdate());
			sb.append("\r\n");
			sb.append("history:" + getHistory());
			sb.append("\r\n");
			sb.append("closed_dt:" + CalendarUtility.Format(getClosedDt(),AppFormats.getLongDateTimeFormat()));
			sb.append("\r\n");
			sb.append("closed_category_ref_id:" + getClosedCategoryRefId());
			sb.append("\r\n");
			sb.append("closed_category_ref_display:" + getClosedCategoryRefDisplay());
			sb.append("\r\n");
			sb.append("closed_user_id:" + getClosedUserId());
			sb.append("\r\n");
			sb.append("closed_user_name:" + getClosedUserName());
			sb.append("\r\n");
			sb.append("resolved_dt:" + CalendarUtility.Format(getResolvedDt(),AppFormats.getLongDateTimeFormat()));
			sb.append("\r\n");
			sb.append("resolved_user_id:" + getResolvedUserId());
			sb.append("\r\n");
			sb.append("resolved_user_name:" + getResolvedUserName());
			sb.append("\r\n");
			sb.append("resolved_category_ref_id:" + getResolvedCategoryRefId());
			sb.append("\r\n");
			sb.append("resolved_category_ref_display:" + getResolvedCategoryRefDisplay());
			sb.append("\r\n");
			sb.append("external_alias1:" + getExternalAlias1());
			sb.append("\r\n");
			sb.append("external_alias2:" + getExternalAlias2());
			sb.append("\r\n");
			sb.append("external_alias3:" + getExternalAlias3());
			sb.append("\r\n");
			sb.append("client_os_ref_id:" + getClientOsRefId());
			sb.append("\r\n");
			sb.append("client_os_ref_display:" + getClientOsRefDisplay());
			sb.append("\r\n");
			sb.append("server_os_ref_id:" + getServerOsRefId());
			sb.append("\r\n");
			sb.append("server_os_ref_display:" + getServerOsRefDisplay());
			sb.append("\r\n");
			sb.append("create_dt:" + CalendarUtility.Format(getCreateDt(),AppFormats.getLongDateTimeFormat()));
			sb.append("\r\n");
			sb.append("create_user_id:" + getCreateUserId());
			sb.append("\r\n");
			sb.append("create_user_name:" + getCreateUserName());
			sb.append("\r\n");
			sb.append("update_dt:" + CalendarUtility.Format(getUpdateDt(),AppFormats.getLongDateTimeFormat()));
			sb.append("\r\n");
			sb.append("update_user_id:" + getUpdateUserId());
			sb.append("\r\n");
			sb.append("update_user_name:" + getUpdateUserName());
			sb.append("\r\n");
			sb.append("update_count:" + getUpdateCount());
			sb.append("\r\n");
			sb.append("active_ind:" + getActiveInd());
			sb.append("\r\n");
			sb.append("system_assigned_version_nbr:" + getSystemAssignedVersionNbr());
			sb.append("\r\n");
			sb.append("record_type_ref_id:" + getRecordTypeRefId());
			sb.append("\r\n");
			sb.append("record_type_ref_display:" + getRecordTypeRefDisplay());
			sb.append("\r\n");
			return sb.toString();
		} catch (Exception ex) {
			return "IssueModel:"; 
		}
	}
	public void setModelColDataAt(Object o, int modelCol) {
			switch (modelCol) {
			case 1257: setIssueId(((Integer) o).intValue()); break;
			case 1339: setIssueTypeRefId(((Integer) o).intValue()); break;
			case 1492: setIssueNbr(((Integer) o).intValue()); break;
			case 1258: setProductRefId(((Integer) o).intValue()); break;
			case 1259: setIssueName((String) o); break;
			case 1260: setIssueOccurDt((GregorianCalendar) o); break;
			case 1261: setIssueOpenDt((GregorianCalendar) o); break;
			case 1262: setVersionRefId(((Integer) o).intValue()); break;
			case 1263: setArtifactId(((Integer) o).intValue()); break;
			case 1264: setTestLogId(((Integer) o).intValue()); break;
			case 1340: setStatusRefId(((Integer) o).intValue()); break;
			case 1341: setAssignedUserGroupRefId(((Integer) o).intValue()); break;
			case 1687: setAssignedUserGroupId(((Integer) o).intValue()); break;
			case 1267: setAssignedUserId(((Integer) o).intValue()); break;
			case 1318: setAssignedUserName((String) o); break;
			case 1268: setDescription((String) o); break;
			case 1342: setPriorityRefId(((Integer) o).intValue()); break;
			case 1270: setSeverityRefId(((Integer) o).intValue()); break;
			case 1271: setFixByDt((GregorianCalendar) o); break;
			case 1272: setFrequencyRefId(((Integer) o).intValue()); break;
			case 1273: setSubmittedUserId(((Integer) o).intValue()); break;
			case 1322: setSubmittedUserName((String) o); break;
			case 1274: setOrganizationId(((Integer) o).intValue()); break;
			case 1275: setOrganizationEnvironmentId(((Integer) o).intValue()); break;
			case 1276: setReproduceRefId(((Integer) o).intValue()); break;
			case 1277: setParentIssueId(((Integer) o).intValue()); break;
			case 1278: setEffort(((Double) o).doubleValue()); break;
			case 1279: setReproduceDirections((String) o); break;
			case 1505: setLastUpdate((String) o); break;
			case 1280: setHistory((String) o); break;
			case 1281: setClosedDt((GregorianCalendar) o); break;
			case 1282: setClosedCategoryRefId(((Integer) o).intValue()); break;
			case 1283: setClosedUserId(((Integer) o).intValue()); break;
			case 1325: setClosedUserName((String) o); break;
			case 1284: setResolvedDt((GregorianCalendar) o); break;
			case 1285: setResolvedUserId(((Integer) o).intValue()); break;
			case 1326: setResolvedUserName((String) o); break;
			case 1286: setResolvedCategoryRefId(((Integer) o).intValue()); break;
			case 1287: setExternalAlias1((String) o); break;
			case 1288: setExternalAlias2((String) o); break;
			case 1289: setExternalAlias3((String) o); break;
			case 1290: setClientOsRefId(((Integer) o).intValue()); break;
			case 1291: setServerOsRefId(((Integer) o).intValue()); break;
			case 1292: setCreateDt((GregorianCalendar) o); break;
			case 1293: setCreateUserId(((Integer) o).intValue()); break;
			case 1330: setCreateUserName((String) o); break;
			case 1294: setUpdateDt((GregorianCalendar) o); break;
			case 1295: setUpdateUserId(((Integer) o).intValue()); break;
			case 1331: setUpdateUserName((String) o); break;
			case 1296: setUpdateCount(((Integer) o).intValue()); break;
			case 1297: setActiveInd(((Integer) o).intValue()); break;
			case 1298: setSystemAssignedVersionNbr(((Integer) o).intValue()); break;
			case 1299: setRecordTypeRefId(((Integer) o).intValue()); break;
			default: Debug.LogWarning(this, ReferenceServices.getMsg(SystemMessageFramework.SWITCHSTATEMENTFAILED) + " modelColRefId: " + modelCol);
	} 
}

	public Object getModelColDataAt(int modelCol) {
			if (modelCol == 1257) return new Integer(issueId);
			if (modelCol == 1339) return new Integer(issueTypeRefId);
			if (modelCol == 1339) return issueTypeRefDisplay;
			if (modelCol == 1492) return new Integer(issueNbr);
			if (modelCol == 1258) return new Integer(productRefId);
			if (modelCol == 1258) return productRefDisplay;
			if (modelCol == 1259) return issueName;
			if (modelCol == 1260) return issueOccurDt;
			if (modelCol == 1261) return issueOpenDt;
			if (modelCol == 1262) return new Integer(versionRefId);
			if (modelCol == 1262) return versionRefDisplay;
			if (modelCol == 1263) return new Integer(artifactId);
			if (modelCol == 1264) return new Integer(testLogId);
			if (modelCol == 1340) return new Integer(statusRefId);
			if (modelCol == 1340) return statusRefDisplay;
			if (modelCol == 1341) return new Integer(assignedUserGroupRefId);
			if (modelCol == 1341) return assignedUserGroupRefDisplay;
			if (modelCol == 1687) return new Integer(assignedUserGroupId);
			if (modelCol == 1267) return new Integer(assignedUserId);
			if (modelCol == 1267) return assignedUserName;
			if (modelCol == 1268) return description;
			if (modelCol == 1342) return new Integer(priorityRefId);
			if (modelCol == 1342) return priorityRefDisplay;
			if (modelCol == 1270) return new Integer(severityRefId);
			if (modelCol == 1270) return severityRefDisplay;
			if (modelCol == 1271) return fixByDt;
			if (modelCol == 1272) return new Integer(frequencyRefId);
			if (modelCol == 1272) return frequencyRefDisplay;
			if (modelCol == 1273) return new Integer(submittedUserId);
			if (modelCol == 1273) return submittedUserName;
			if (modelCol == 1274) return new Integer(organizationId);
			if (modelCol == 1275) return new Integer(organizationEnvironmentId);
			if (modelCol == 1276) return new Integer(reproduceRefId);
			if (modelCol == 1276) return reproduceRefDisplay;
			if (modelCol == 1277) return new Integer(parentIssueId);
			if (modelCol == 1278) return new Double(effort);
			if (modelCol == 1279) return reproduceDirections;
			if (modelCol == 1505) return lastUpdate;
			if (modelCol == 1280) return history;
			if (modelCol == 1281) return closedDt;
			if (modelCol == 1282) return new Integer(closedCategoryRefId);
			if (modelCol == 1282) return closedCategoryRefDisplay;
			if (modelCol == 1283) return new Integer(closedUserId);
			if (modelCol == 1283) return closedUserName;
			if (modelCol == 1284) return resolvedDt;
			if (modelCol == 1285) return new Integer(resolvedUserId);
			if (modelCol == 1285) return resolvedUserName;
			if (modelCol == 1286) return new Integer(resolvedCategoryRefId);
			if (modelCol == 1286) return resolvedCategoryRefDisplay;
			if (modelCol == 1287) return externalAlias1;
			if (modelCol == 1288) return externalAlias2;
			if (modelCol == 1289) return externalAlias3;
			if (modelCol == 1290) return new Integer(clientOsRefId);
			if (modelCol == 1290) return clientOsRefDisplay;
			if (modelCol == 1291) return new Integer(serverOsRefId);
			if (modelCol == 1291) return serverOsRefDisplay;
			if (modelCol == 1292) return createDt;
			if (modelCol == 1293) return new Integer(createUserId);
			if (modelCol == 1293) return createUserName;
			if (modelCol == 1294) return updateDt;
			if (modelCol == 1295) return new Integer(updateUserId);
			if (modelCol == 1295) return updateUserName;
			if (modelCol == 1296) return new Integer(updateCount);
			if (modelCol == 1297) return new Integer(activeInd);
			if (modelCol == 1298) return new Integer(systemAssignedVersionNbr);
			if (modelCol == 1299) return new Integer(recordTypeRefId);
			if (modelCol == 1299) return recordTypeRefDisplay;
		return null;
	}

	public int getModelColDatabaseDataType(int modelCol) {
			if (modelCol == 1257) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1339) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1339) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1492) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1258) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1258) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1259) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1260) return DatabaseDataTypeFramework.DATE;
			if (modelCol == 1261) return DatabaseDataTypeFramework.DATE;
			if (modelCol == 1262) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1262) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1263) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1264) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1340) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1340) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1341) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1341) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1687) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1267) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1267) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1268) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1342) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1342) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1270) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1270) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1271) return DatabaseDataTypeFramework.DATE;
			if (modelCol == 1272) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1272) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1273) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1273) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1274) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1275) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1276) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1276) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1277) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1278) return DatabaseDataTypeFramework.DOUBLE;
			if (modelCol == 1279) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1505) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1280) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1281) return DatabaseDataTypeFramework.DATE;
			if (modelCol == 1282) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1282) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1283) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1283) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1284) return DatabaseDataTypeFramework.DATE;
			if (modelCol == 1285) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1285) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1286) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1286) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1287) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1288) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1289) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1290) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1290) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1291) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1291) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1292) return DatabaseDataTypeFramework.DATE;
			if (modelCol == 1293) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1293) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1294) return DatabaseDataTypeFramework.DATE;
			if (modelCol == 1295) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1295) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1296) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1297) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1298) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1299) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1299) return DatabaseDataTypeFramework.STRING;
			return DatabaseDataTypeFramework.STRING;
	}

	public boolean isNew() {
		return getIssueId() == 0;
	}

	public Object getPrimaryValue() {
		return new Integer(getIssueId());
	}

	public Object getDataAt(int i) {
			if (i == 0) return new Integer(issueId);
			if (i == 1) return new Integer(issueTypeRefId);
			if (i == 2) return issueTypeRefDisplay;
			if (i == 3) return new Integer(issueNbr);
			if (i == 4) return new Integer(productRefId);
			if (i == 5) return productRefDisplay;
			if (i == 6) return issueName;
			if (i == 7) return issueOccurDt;
			if (i == 8) return issueOpenDt;
			if (i == 9) return new Integer(versionRefId);
			if (i == 10) return versionRefDisplay;
			if (i == 11) return new Integer(artifactId);
			if (i == 12) return new Integer(testLogId);
			if (i == 13) return new Integer(statusRefId);
			if (i == 14) return statusRefDisplay;
			if (i == 15) return new Integer(assignedUserGroupRefId);
			if (i == 16) return assignedUserGroupRefDisplay;
			if (i == 17) return new Integer(assignedUserGroupId);
			if (i == 18) return new Integer(assignedUserId);
			if (i == 19) return assignedUserName;
			if (i == 20) return description;
			if (i == 21) return new Integer(priorityRefId);
			if (i == 22) return priorityRefDisplay;
			if (i == 23) return new Integer(severityRefId);
			if (i == 24) return severityRefDisplay;
			if (i == 25) return fixByDt;
			if (i == 26) return new Integer(frequencyRefId);
			if (i == 27) return frequencyRefDisplay;
			if (i == 28) return new Integer(submittedUserId);
			if (i == 29) return submittedUserName;
			if (i == 30) return new Integer(organizationId);
			if (i == 31) return new Integer(organizationEnvironmentId);
			if (i == 32) return new Integer(reproduceRefId);
			if (i == 33) return reproduceRefDisplay;
			if (i == 34) return new Integer(parentIssueId);
			if (i == 35) return new Double(effort);
			if (i == 36) return reproduceDirections;
			if (i == 37) return lastUpdate;
			if (i == 38) return history;
			if (i == 39) return closedDt;
			if (i == 40) return new Integer(closedCategoryRefId);
			if (i == 41) return closedCategoryRefDisplay;
			if (i == 42) return new Integer(closedUserId);
			if (i == 43) return closedUserName;
			if (i == 44) return resolvedDt;
			if (i == 45) return new Integer(resolvedUserId);
			if (i == 46) return resolvedUserName;
			if (i == 47) return new Integer(resolvedCategoryRefId);
			if (i == 48) return resolvedCategoryRefDisplay;
			if (i == 49) return externalAlias1;
			if (i == 50) return externalAlias2;
			if (i == 51) return externalAlias3;
			if (i == 52) return new Integer(clientOsRefId);
			if (i == 53) return clientOsRefDisplay;
			if (i == 54) return new Integer(serverOsRefId);
			if (i == 55) return serverOsRefDisplay;
			if (i == 56) return createDt;
			if (i == 57) return new Integer(createUserId);
			if (i == 58) return createUserName;
			if (i == 59) return updateDt;
			if (i == 60) return new Integer(updateUserId);
			if (i == 61) return updateUserName;
			if (i == 62) return new Integer(updateCount);
			if (i == 63) return new Integer(activeInd);
			if (i == 64) return new Integer(systemAssignedVersionNbr);
			if (i == 65) return new Integer(recordTypeRefId);
			if (i == 66) return recordTypeRefDisplay;
		return null;
	}

	public int getDatabaseDataType(int i) {
			if (i == 0) return DatabaseDataTypeFramework.INTEGER;
			if (i == 1) return DatabaseDataTypeFramework.INTEGER;
			if (i == 2) return DatabaseDataTypeFramework.STRING;
			if (i == 3) return DatabaseDataTypeFramework.INTEGER;
			if (i == 4) return DatabaseDataTypeFramework.INTEGER;
			if (i == 5) return DatabaseDataTypeFramework.STRING;
			if (i == 6) return DatabaseDataTypeFramework.STRING;
			if (i == 7) return DatabaseDataTypeFramework.DATE;
			if (i == 8) return DatabaseDataTypeFramework.DATE;
			if (i == 9) return DatabaseDataTypeFramework.INTEGER;
			if (i == 10) return DatabaseDataTypeFramework.STRING;
			if (i == 11) return DatabaseDataTypeFramework.INTEGER;
			if (i == 12) return DatabaseDataTypeFramework.INTEGER;
			if (i == 13) return DatabaseDataTypeFramework.INTEGER;
			if (i == 14) return DatabaseDataTypeFramework.STRING;
			if (i == 15) return DatabaseDataTypeFramework.INTEGER;
			if (i == 16) return DatabaseDataTypeFramework.STRING;
			if (i == 17) return DatabaseDataTypeFramework.INTEGER;
			if (i == 18) return DatabaseDataTypeFramework.INTEGER;
			if (i == 19) return DatabaseDataTypeFramework.STRING;
			if (i == 20) return DatabaseDataTypeFramework.STRING;
			if (i == 21) return DatabaseDataTypeFramework.INTEGER;
			if (i == 22) return DatabaseDataTypeFramework.STRING;
			if (i == 23) return DatabaseDataTypeFramework.INTEGER;
			if (i == 24) return DatabaseDataTypeFramework.STRING;
			if (i == 25) return DatabaseDataTypeFramework.DATE;
			if (i == 26) return DatabaseDataTypeFramework.INTEGER;
			if (i == 27) return DatabaseDataTypeFramework.STRING;
			if (i == 28) return DatabaseDataTypeFramework.INTEGER;
			if (i == 29) return DatabaseDataTypeFramework.STRING;
			if (i == 30) return DatabaseDataTypeFramework.INTEGER;
			if (i == 31) return DatabaseDataTypeFramework.INTEGER;
			if (i == 32) return DatabaseDataTypeFramework.INTEGER;
			if (i == 33) return DatabaseDataTypeFramework.STRING;
			if (i == 34) return DatabaseDataTypeFramework.INTEGER;
			if (i == 35) return DatabaseDataTypeFramework.DOUBLE;
			if (i == 36) return DatabaseDataTypeFramework.STRING;
			if (i == 37) return DatabaseDataTypeFramework.STRING;
			if (i == 38) return DatabaseDataTypeFramework.STRING;
			if (i == 39) return DatabaseDataTypeFramework.DATE;
			if (i == 40) return DatabaseDataTypeFramework.INTEGER;
			if (i == 41) return DatabaseDataTypeFramework.STRING;
			if (i == 42) return DatabaseDataTypeFramework.INTEGER;
			if (i == 43) return DatabaseDataTypeFramework.STRING;
			if (i == 44) return DatabaseDataTypeFramework.DATE;
			if (i == 45) return DatabaseDataTypeFramework.INTEGER;
			if (i == 46) return DatabaseDataTypeFramework.STRING;
			if (i == 47) return DatabaseDataTypeFramework.INTEGER;
			if (i == 48) return DatabaseDataTypeFramework.STRING;
			if (i == 49) return DatabaseDataTypeFramework.STRING;
			if (i == 50) return DatabaseDataTypeFramework.STRING;
			if (i == 51) return DatabaseDataTypeFramework.STRING;
			if (i == 52) return DatabaseDataTypeFramework.INTEGER;
			if (i == 53) return DatabaseDataTypeFramework.STRING;
			if (i == 54) return DatabaseDataTypeFramework.INTEGER;
			if (i == 55) return DatabaseDataTypeFramework.STRING;
			if (i == 56) return DatabaseDataTypeFramework.DATE;
			if (i == 57) return DatabaseDataTypeFramework.INTEGER;
			if (i == 58) return DatabaseDataTypeFramework.STRING;
			if (i == 59) return DatabaseDataTypeFramework.DATE;
			if (i == 60) return DatabaseDataTypeFramework.INTEGER;
			if (i == 61) return DatabaseDataTypeFramework.STRING;
			if (i == 62) return DatabaseDataTypeFramework.INTEGER;
			if (i == 63) return DatabaseDataTypeFramework.INTEGER;
			if (i == 64) return DatabaseDataTypeFramework.INTEGER;
			if (i == 65) return DatabaseDataTypeFramework.INTEGER;
			if (i == 66) return DatabaseDataTypeFramework.STRING;
			return DatabaseDataTypeFramework.STRING;
	}

	public void setDataAt(Object o, int i) {
		switch(i) {
			case 0: setIssueId(((Integer) o).intValue()); break;
			case 1: setIssueTypeRefId(((Integer) o).intValue()); break;
			case 2: setIssueTypeRefDisplay((String) o); break;
			case 3: setIssueNbr(((Integer) o).intValue()); break;
			case 4: setProductRefId(((Integer) o).intValue()); break;
			case 5: setProductRefDisplay((String) o); break;
			case 6: setIssueName((String) o); break;
			case 7: setIssueOccurDt((GregorianCalendar) o); break;
			case 8: setIssueOpenDt((GregorianCalendar) o); break;
			case 9: setVersionRefId(((Integer) o).intValue()); break;
			case 10: setVersionRefDisplay((String) o); break;
			case 11: setArtifactId(((Integer) o).intValue()); break;
			case 12: setTestLogId(((Integer) o).intValue()); break;
			case 13: setStatusRefId(((Integer) o).intValue()); break;
			case 14: setStatusRefDisplay((String) o); break;
			case 15: setAssignedUserGroupRefId(((Integer) o).intValue()); break;
			case 16: setAssignedUserGroupRefDisplay((String) o); break;
			case 17: setAssignedUserGroupId(((Integer) o).intValue()); break;
			case 18: setAssignedUserId(((Integer) o).intValue()); break;
			case 19: setAssignedUserName((String) o); break;
			case 20: setDescription((String) o); break;
			case 21: setPriorityRefId(((Integer) o).intValue()); break;
			case 22: setPriorityRefDisplay((String) o); break;
			case 23: setSeverityRefId(((Integer) o).intValue()); break;
			case 24: setSeverityRefDisplay((String) o); break;
			case 25: setFixByDt((GregorianCalendar) o); break;
			case 26: setFrequencyRefId(((Integer) o).intValue()); break;
			case 27: setFrequencyRefDisplay((String) o); break;
			case 28: setSubmittedUserId(((Integer) o).intValue()); break;
			case 29: setSubmittedUserName((String) o); break;
			case 30: setOrganizationId(((Integer) o).intValue()); break;
			case 31: setOrganizationEnvironmentId(((Integer) o).intValue()); break;
			case 32: setReproduceRefId(((Integer) o).intValue()); break;
			case 33: setReproduceRefDisplay((String) o); break;
			case 34: setParentIssueId(((Integer) o).intValue()); break;
			case 35: setEffort(((Double) o).doubleValue()); break;
			case 36: setReproduceDirections((String) o); break;
			case 37: setLastUpdate((String) o); break;
			case 38: setHistory((String) o); break;
			case 39: setClosedDt((GregorianCalendar) o); break;
			case 40: setClosedCategoryRefId(((Integer) o).intValue()); break;
			case 41: setClosedCategoryRefDisplay((String) o); break;
			case 42: setClosedUserId(((Integer) o).intValue()); break;
			case 43: setClosedUserName((String) o); break;
			case 44: setResolvedDt((GregorianCalendar) o); break;
			case 45: setResolvedUserId(((Integer) o).intValue()); break;
			case 46: setResolvedUserName((String) o); break;
			case 47: setResolvedCategoryRefId(((Integer) o).intValue()); break;
			case 48: setResolvedCategoryRefDisplay((String) o); break;
			case 49: setExternalAlias1((String) o); break;
			case 50: setExternalAlias2((String) o); break;
			case 51: setExternalAlias3((String) o); break;
			case 52: setClientOsRefId(((Integer) o).intValue()); break;
			case 53: setClientOsRefDisplay((String) o); break;
			case 54: setServerOsRefId(((Integer) o).intValue()); break;
			case 55: setServerOsRefDisplay((String) o); break;
			case 56: setCreateDt((GregorianCalendar) o); break;
			case 57: setCreateUserId(((Integer) o).intValue()); break;
			case 58: setCreateUserName((String) o); break;
			case 59: setUpdateDt((GregorianCalendar) o); break;
			case 60: setUpdateUserId(((Integer) o).intValue()); break;
			case 61: setUpdateUserName((String) o); break;
			case 62: setUpdateCount(((Integer) o).intValue()); break;
			case 63: setActiveInd(((Integer) o).intValue()); break;
			case 64: setSystemAssignedVersionNbr(((Integer) o).intValue()); break;
			case 65: setRecordTypeRefId(((Integer) o).intValue()); break;
			case 66: setRecordTypeRefDisplay((String) o); break;
		}
	}

}
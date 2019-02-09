/*
    Artifact stores the common attributes of all SDLC artifacts - features, requirements, design, implementation, test cases etc.

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
package com.osrmt.modellibrary.reqmanager;
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
Artifact stores the common attributes of all SDLC artifacts - features, requirements, design, implementation, test cases etc.
*/
public class ArtifactDataModel implements IControlModel, java.io.Serializable {

	static final long serialVersionUID = 1L;
	private int hashsize = 60;
	private Hashtable modifiedFields = new Hashtable(hashsize);
	private boolean referenceSet = false;


	/** Unique identifier to artifact table  */
	private int artifactId = 0;

	/** Product foreign key to reference  */
	private int productRefId = 0;

	private String productRefDisplay="";

	/** Foreign key to reference: Version  */
	private int versionRefId = 0;

	private String versionRefDisplay="";

	/** Foreign key to reference: Artifact  */
	private int artifactRefId = 0;

	private String artifactRefDisplay="";

	/** Sequentially assigned to type artifact number  */
	private int artifactNbr = 0;

	/** Artifact display sequence within a level  */
	private int artifactSeq = 0;

	/** Foreign key to reference: ArtifactLevel  */
	private int artifactLevelRefId = 0;

	private String artifactLevelRefDisplay="";

	/** Foreign key to reference: ArtifactLevel  */
	private int componentTypeRefId = 0;

	private String componentTypeRefDisplay="";

	/** Foreign key to Baseline  */
	private int lastUpdatedBaselineId = 0;

	/** Artifact display name  */
	private String artifactName;

	/** Description of the feature  */
	private String description;

	/** Status foreign key to status  */
	private int statusRefId = 0;

	private String statusRefDisplay="";

	/** Priority foreign key to reference  */
	private int priorityRefId = 0;

	private String priorityRefDisplay="";

	/** Difficulty to implement artifact  */
	private int complexityRefId = 0;

	private String complexityRefDisplay="";

	/** Author of requirement  */
	private int authorRefId = 0;

	private String authorRefDisplay="";

	/** User assigned to implement this module  */
	private int assignedRefId = 0;

	private String assignedRefDisplay="";

	/** Foreign key to reference: Category  */
	private int categoryRefId = 0;

	private String categoryRefDisplay="";

	/** User from User table assigned to this artifact  */
	private int assignedUserId = 0;

	private String assignedUserName="";

	/** User group assigned to this artifact  */
	private int assignedUserGroupId = 0;

	/** Effort to implement (man hours)  */
	private double effort = 0;

	/** Rationale for the artifact  */
	private String rationale;

	/** Origin of requirement  */
	private String origin;

	/** Goal of use case  */
	private String goal;

	/** Context specific data  */
	private String context;

	/** Precondition to usage  */
	private String precondition;

	/** Post condition after usage  */
	private String postcondition;

	/** Artifact summary  */
	private String summary;

	/** External identifiers  */
	private String externalReferences;

	/** Foreign key to reference: Version  */
	private int plannedVersionRefId = 0;

	private String plannedVersionRefDisplay="";

	/** Foreign key to reference: Version  */
	private int lastModifiedVersionRefId = 0;

	private String lastModifiedVersionRefDisplay="";

	/** Artifact has been removed from the product but needs to be tracked  */
	private int removedInd = 0;

	/** Foreign key to reference: Module  */
	private int moduleRefId = 0;

	private String moduleRefDisplay="";

	/** Foreign key to reference: OriginCategory  */
	private int originCategoryRefId = 0;

	private String originCategoryRefDisplay="";

	/** Reason to change artifact.  Only populated in the history table.  */
	private String changeReason;

	/** Change made to the artifact  */
	private String changeMade;

	/** Sequence of artifacts duplicating the order in the tree  */
	private int reportSequence = 0;

	/** Custom outline assigned to artifacts based upon a script.  Manually generated.  */
	private String reportOutline;

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

	/** Custom text field 1  */
	private String customText1;

	/** Custom text field 2  */
	private String customText2;

	/** Custom text field 3  */
	private String customText3;

	/** Custom text field 4  */
	private String customText4;

	/** Custom ref field 1  */
	private int custom1RefId = 0;

	private String custom1RefDisplay="";

	/** Custom ref field 2  */
	private int custom2RefId = 0;

	private String custom2RefDisplay="";

	/** Custom ref field 3  */
	private int custom3RefId = 0;

	private String custom3RefDisplay="";

	/** Custom ref field 4  */
	private int custom4RefId = 0;

	private String custom4RefDisplay="";

	/** Custom date field 1  */
	private DbCalendar customDate1;

	/** Custom date field 2  */
	private DbCalendar customDate2;

	/** Custom memo field 1  */
	private String customMemo1;

	/** Custom memo field 2  */
	private String customMemo2;

	/** Custom integer field 1  */
	private int customInt1 = 0;

	/** Custom integer field 2  */
	private int customInt2 = 0;

	/** Custom double field 1  */
	private double customDouble1 = 0;


	/** 
	 * Unique identifier to artifact table
	 * 
	 */ 
	public int getArtifactId() {
		return artifactId;
	}

	/** 
	 * Unique identifier to artifact table
	 * 
	 */ 
	public void setArtifactId(int artifactId) {
		if (!Comparison.areEqual(this.artifactId, artifactId)) {
			this.artifactId = artifactId;
			setModified("artifactId");
		};
	}

	/** 
	 * Product foreign key to reference
	 * 
	 */ 
	public int getProductRefId() {
		return productRefId;
	}

	/** 
	 * Product foreign key to reference
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
	 * Product foreign key to reference
	 * 
	 */ 
	public String getProductRefDisplay() {
		return productRefDisplay;
	}

	/** 
	 * Product foreign key to reference
	 * 
	 */ 
	public void setProductRefDisplay(String display) {
		this.productRefDisplay = display;
	}

	/** 
	 * Foreign key to reference: Version
	 * 
	 */ 
	public int getVersionRefId() {
		return versionRefId;
	}

	/** 
	 * Foreign key to reference: Version
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
	 * Foreign key to reference: Version
	 * 
	 */ 
	public String getVersionRefDisplay() {
		return versionRefDisplay;
	}

	/** 
	 * Foreign key to reference: Version
	 * 
	 */ 
	public void setVersionRefDisplay(String display) {
		this.versionRefDisplay = display;
	}

	/** 
	 * Foreign key to reference: Artifact
	 * 
	 */ 
	public int getArtifactRefId() {
		return artifactRefId;
	}

	/** 
	 * Foreign key to reference: Artifact
	 * 
	 * Required database field.
	 */ 
	public void setArtifactRefId(int artifactRefId) {
		if (!Comparison.areEqual(this.artifactRefId, artifactRefId)) {
			this.artifactRefId = artifactRefId;
			setModified("artifactRefId");
		};
	}

	/** 
	 * Foreign key to reference: Artifact
	 * 
	 */ 
	public String getArtifactRefDisplay() {
		return artifactRefDisplay;
	}

	/** 
	 * Foreign key to reference: Artifact
	 * 
	 */ 
	public void setArtifactRefDisplay(String display) {
		this.artifactRefDisplay = display;
	}

	/** 
	 * Sequentially assigned to type artifact number
	 * 
	 */ 
	public int getArtifactNbr() {
		return artifactNbr;
	}

	/** 
	 * Sequentially assigned to type artifact number
	 * 
	 * Required database field.
	 */ 
	public void setArtifactNbr(int artifactNbr) {
		if (!Comparison.areEqual(this.artifactNbr, artifactNbr)) {
			this.artifactNbr = artifactNbr;
			setModified("artifactNbr");
		};
	}

	/** 
	 * Artifact display sequence within a level
	 * 
	 */ 
	public int getArtifactSeq() {
		return artifactSeq;
	}

	/** 
	 * Artifact display sequence within a level
	 * 
	 * Required database field.
	 */ 
	public void setArtifactSeq(int artifactSeq) {
		if (!Comparison.areEqual(this.artifactSeq, artifactSeq)) {
			this.artifactSeq = artifactSeq;
			setModified("artifactSeq");
		};
	}

	/** 
	 * Foreign key to reference: ArtifactLevel
	 * 
	 */ 
	public int getArtifactLevelRefId() {
		return artifactLevelRefId;
	}

	/** 
	 * Foreign key to reference: ArtifactLevel
	 * 
	 * Required database field.
	 */ 
	public void setArtifactLevelRefId(int artifactLevelRefId) {
		if (!Comparison.areEqual(this.artifactLevelRefId, artifactLevelRefId)) {
			this.artifactLevelRefId = artifactLevelRefId;
			setModified("artifactLevelRefId");
		};
	}

	/** 
	 * Foreign key to reference: ArtifactLevel
	 * 
	 */ 
	public String getArtifactLevelRefDisplay() {
		return artifactLevelRefDisplay;
	}

	/** 
	 * Foreign key to reference: ArtifactLevel
	 * 
	 */ 
	public void setArtifactLevelRefDisplay(String display) {
		this.artifactLevelRefDisplay = display;
	}

	/** 
	 * Foreign key to reference: ArtifactLevel
	 * 
	 */ 
	public int getComponentTypeRefId() {
		return componentTypeRefId;
	}

	/** 
	 * Foreign key to reference: ArtifactLevel
	 * 
	 * Required database field.
	 */ 
	public void setComponentTypeRefId(int componentTypeRefId) {
		if (!Comparison.areEqual(this.componentTypeRefId, componentTypeRefId)) {
			this.componentTypeRefId = componentTypeRefId;
			setModified("componentTypeRefId");
		};
	}

	/** 
	 * Foreign key to reference: ArtifactLevel
	 * 
	 */ 
	public String getComponentTypeRefDisplay() {
		return componentTypeRefDisplay;
	}

	/** 
	 * Foreign key to reference: ArtifactLevel
	 * 
	 */ 
	public void setComponentTypeRefDisplay(String display) {
		this.componentTypeRefDisplay = display;
	}

	/** 
	 * Foreign key to Baseline
	 * 
	 */ 
	public int getLastUpdatedBaselineId() {
		return lastUpdatedBaselineId;
	}

	/** 
	 * Foreign key to Baseline
	 * 
	 * Required database field.
	 */ 
	public void setLastUpdatedBaselineId(int lastUpdatedBaselineId) {
		if (!Comparison.areEqual(this.lastUpdatedBaselineId, lastUpdatedBaselineId)) {
			this.lastUpdatedBaselineId = lastUpdatedBaselineId;
			setModified("lastUpdatedBaselineId");
		};
	}

	/** 
	 * Artifact display name
	 * 
	 */ 
	public String getArtifactName() {
		return artifactName;
	}

	/** 
	 * Artifact display name
	 * 
	 */ 
	public void setArtifactName(String artifactName) {
		if (!Comparison.areEqual(this.artifactName, artifactName)) {
			this.artifactName = artifactName;
			setModified("artifactName");
		};
	}

	/** 
	 * Description of the feature
	 * 
	 */ 
	public String getDescription() {
		return description;
	}

	/** 
	 * Description of the feature
	 * 
	 */ 
	public void setDescription(String description) {
		if (!Comparison.areEqual(this.description, description)) {
			this.description = description;
			setModified("description");
		};
	}

	/** 
	 * Status foreign key to status
	 * 
	 */ 
	public int getStatusRefId() {
		return statusRefId;
	}

	/** 
	 * Status foreign key to status
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
	 * Status foreign key to status
	 * 
	 */ 
	public String getStatusRefDisplay() {
		return statusRefDisplay;
	}

	/** 
	 * Status foreign key to status
	 * 
	 */ 
	public void setStatusRefDisplay(String display) {
		this.statusRefDisplay = display;
	}

	/** 
	 * Priority foreign key to reference
	 * 
	 */ 
	public int getPriorityRefId() {
		return priorityRefId;
	}

	/** 
	 * Priority foreign key to reference
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
	 * Priority foreign key to reference
	 * 
	 */ 
	public String getPriorityRefDisplay() {
		return priorityRefDisplay;
	}

	/** 
	 * Priority foreign key to reference
	 * 
	 */ 
	public void setPriorityRefDisplay(String display) {
		this.priorityRefDisplay = display;
	}

	/** 
	 * Difficulty to implement artifact
	 * 
	 */ 
	public int getComplexityRefId() {
		return complexityRefId;
	}

	/** 
	 * Difficulty to implement artifact
	 * 
	 * Required database field.
	 */ 
	public void setComplexityRefId(int complexityRefId) {
		if (!Comparison.areEqual(this.complexityRefId, complexityRefId)) {
			this.complexityRefId = complexityRefId;
			setModified("complexityRefId");
		};
	}

	/** 
	 * Difficulty to implement artifact
	 * 
	 */ 
	public String getComplexityRefDisplay() {
		return complexityRefDisplay;
	}

	/** 
	 * Difficulty to implement artifact
	 * 
	 */ 
	public void setComplexityRefDisplay(String display) {
		this.complexityRefDisplay = display;
	}

	/** 
	 * Author of requirement
	 * 
	 */ 
	public int getAuthorRefId() {
		return authorRefId;
	}

	/** 
	 * Author of requirement
	 * 
	 * Required database field.
	 */ 
	public void setAuthorRefId(int authorRefId) {
		if (!Comparison.areEqual(this.authorRefId, authorRefId)) {
			this.authorRefId = authorRefId;
			setModified("authorRefId");
		};
	}

	/** 
	 * Author of requirement
	 * 
	 */ 
	public String getAuthorRefDisplay() {
		return authorRefDisplay;
	}

	/** 
	 * Author of requirement
	 * 
	 */ 
	public void setAuthorRefDisplay(String display) {
		this.authorRefDisplay = display;
	}

	/** 
	 * User assigned to implement this module
	 * 
	 */ 
	public int getAssignedRefId() {
		return assignedRefId;
	}

	/** 
	 * User assigned to implement this module
	 * 
	 * Required database field.
	 */ 
	public void setAssignedRefId(int assignedRefId) {
		if (!Comparison.areEqual(this.assignedRefId, assignedRefId)) {
			this.assignedRefId = assignedRefId;
			setModified("assignedRefId");
		};
	}

	/** 
	 * User assigned to implement this module
	 * 
	 */ 
	public String getAssignedRefDisplay() {
		return assignedRefDisplay;
	}

	/** 
	 * User assigned to implement this module
	 * 
	 */ 
	public void setAssignedRefDisplay(String display) {
		this.assignedRefDisplay = display;
	}

	/** 
	 * Foreign key to reference: Category
	 * 
	 */ 
	public int getCategoryRefId() {
		return categoryRefId;
	}

	/** 
	 * Foreign key to reference: Category
	 * 
	 * Required database field.
	 */ 
	public void setCategoryRefId(int categoryRefId) {
		if (!Comparison.areEqual(this.categoryRefId, categoryRefId)) {
			this.categoryRefId = categoryRefId;
			setModified("categoryRefId");
		};
	}

	/** 
	 * Foreign key to reference: Category
	 * 
	 */ 
	public String getCategoryRefDisplay() {
		return categoryRefDisplay;
	}

	/** 
	 * Foreign key to reference: Category
	 * 
	 */ 
	public void setCategoryRefDisplay(String display) {
		this.categoryRefDisplay = display;
	}

	/** 
	 * User from User table assigned to this artifact
	 * 
	 */ 
	public int getAssignedUserId() {
		return assignedUserId;
	}

	/** 
	 * User from User table assigned to this artifact
	 * 
	 */ 
	public void setAssignedUserId(int assignedUserId) {
		if (!Comparison.areEqual(this.assignedUserId, assignedUserId)) {
			this.assignedUserId = assignedUserId;
			setModified("assignedUserId");
		};
	}

	/** 
	 * User from User table assigned to this artifact
	 * 
	 */ 
	public String getAssignedUserName() {
		return assignedUserName;
	}

	/** 
	 * User from User table assigned to this artifact
	 * 
	 */ 
	public void setAssignedUserName(String userName) {
		this.assignedUserName = userName;
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
	 * Rationale for the artifact
	 * 
	 */ 
	public String getRationale() {
		return rationale;
	}

	/** 
	 * Rationale for the artifact
	 * 
	 */ 
	public void setRationale(String rationale) {
		if (!Comparison.areEqual(this.rationale, rationale)) {
			this.rationale = rationale;
			setModified("rationale");
		};
	}

	/** 
	 * Origin of requirement
	 * 
	 */ 
	public String getOrigin() {
		return origin;
	}

	/** 
	 * Origin of requirement
	 * 
	 */ 
	public void setOrigin(String origin) {
		if (!Comparison.areEqual(this.origin, origin)) {
			this.origin = origin;
			setModified("origin");
		};
	}

	/** 
	 * Goal of use case
	 * 
	 */ 
	public String getGoal() {
		return goal;
	}

	/** 
	 * Goal of use case
	 * 
	 */ 
	public void setGoal(String goal) {
		if (!Comparison.areEqual(this.goal, goal)) {
			this.goal = goal;
			setModified("goal");
		};
	}

	/** 
	 * Context specific data
	 * 
	 */ 
	public String getContext() {
		return context;
	}

	/** 
	 * Context specific data
	 * 
	 */ 
	public void setContext(String context) {
		if (!Comparison.areEqual(this.context, context)) {
			this.context = context;
			setModified("context");
		};
	}

	/** 
	 * Precondition to usage
	 * 
	 */ 
	public String getPrecondition() {
		return precondition;
	}

	/** 
	 * Precondition to usage
	 * 
	 */ 
	public void setPrecondition(String precondition) {
		if (!Comparison.areEqual(this.precondition, precondition)) {
			this.precondition = precondition;
			setModified("precondition");
		};
	}

	/** 
	 * Post condition after usage
	 * 
	 */ 
	public String getPostcondition() {
		return postcondition;
	}

	/** 
	 * Post condition after usage
	 * 
	 */ 
	public void setPostcondition(String postcondition) {
		if (!Comparison.areEqual(this.postcondition, postcondition)) {
			this.postcondition = postcondition;
			setModified("postcondition");
		};
	}

	/** 
	 * Artifact summary
	 * 
	 */ 
	public String getSummary() {
		return summary;
	}

	/** 
	 * Artifact summary
	 * 
	 */ 
	public void setSummary(String summary) {
		if (!Comparison.areEqual(this.summary, summary)) {
			this.summary = summary;
			setModified("summary");
		};
	}

	/** 
	 * External identifiers
	 * 
	 */ 
	public String getExternalReferences() {
		return externalReferences;
	}

	/** 
	 * External identifiers
	 * 
	 */ 
	public void setExternalReferences(String externalReferences) {
		if (!Comparison.areEqual(this.externalReferences, externalReferences)) {
			this.externalReferences = externalReferences;
			setModified("externalReferences");
		};
	}

	/** 
	 * Foreign key to reference: Version
	 * 
	 */ 
	public int getPlannedVersionRefId() {
		return plannedVersionRefId;
	}

	/** 
	 * Foreign key to reference: Version
	 * 
	 * Required database field.
	 */ 
	public void setPlannedVersionRefId(int plannedVersionRefId) {
		if (!Comparison.areEqual(this.plannedVersionRefId, plannedVersionRefId)) {
			this.plannedVersionRefId = plannedVersionRefId;
			setModified("plannedVersionRefId");
		};
	}

	/** 
	 * Foreign key to reference: Version
	 * 
	 */ 
	public String getPlannedVersionRefDisplay() {
		return plannedVersionRefDisplay;
	}

	/** 
	 * Foreign key to reference: Version
	 * 
	 */ 
	public void setPlannedVersionRefDisplay(String display) {
		this.plannedVersionRefDisplay = display;
	}

	/** 
	 * Foreign key to reference: Version
	 * 
	 */ 
	public int getLastModifiedVersionRefId() {
		return lastModifiedVersionRefId;
	}

	/** 
	 * Foreign key to reference: Version
	 * 
	 * Required database field.
	 */ 
	public void setLastModifiedVersionRefId(int lastModifiedVersionRefId) {
		if (!Comparison.areEqual(this.lastModifiedVersionRefId, lastModifiedVersionRefId)) {
			this.lastModifiedVersionRefId = lastModifiedVersionRefId;
			setModified("lastModifiedVersionRefId");
		};
	}

	/** 
	 * Foreign key to reference: Version
	 * 
	 */ 
	public String getLastModifiedVersionRefDisplay() {
		return lastModifiedVersionRefDisplay;
	}

	/** 
	 * Foreign key to reference: Version
	 * 
	 */ 
	public void setLastModifiedVersionRefDisplay(String display) {
		this.lastModifiedVersionRefDisplay = display;
	}

	/** 
	 * Artifact has been removed from the product but needs to be tracked
	 * 
	 */ 
	public int getRemovedInd() {
		return removedInd;
	}

	/** 
	 * Artifact has been removed from the product but needs to be tracked
	 * 
	 * Required database field.
	 */ 
	public void setRemovedInd(int removedInd) {
		if (!Comparison.areEqual(this.removedInd, removedInd)) {
			this.removedInd = removedInd;
			setModified("removedInd");
		};
	}

	/** 
	 * Artifact has been removed from the product but needs to be tracked
	 * 
	 */ 
	public boolean isRemoved() {
		return removedInd == 1;
	}

	/** 
	 * Artifact has been removed from the product but needs to be tracked
	 * 
	 */ 
	public boolean isNotRemoved() {
		return removedInd == 0;
	}

	/** 
	 * Artifact has been removed from the product but needs to be tracked
	 * 
	 */ 
	public void setRemoved() {
		this.setRemovedInd(1);
	}

	public void setNotRemoved() {
		this.setRemovedInd(0);
	}

	/** 
	 * Foreign key to reference: Module
	 * 
	 */ 
	public int getModuleRefId() {
		return moduleRefId;
	}

	/** 
	 * Foreign key to reference: Module
	 * 
	 * Required database field.
	 */ 
	public void setModuleRefId(int moduleRefId) {
		if (!Comparison.areEqual(this.moduleRefId, moduleRefId)) {
			this.moduleRefId = moduleRefId;
			setModified("moduleRefId");
		};
	}

	/** 
	 * Foreign key to reference: Module
	 * 
	 */ 
	public String getModuleRefDisplay() {
		return moduleRefDisplay;
	}

	/** 
	 * Foreign key to reference: Module
	 * 
	 */ 
	public void setModuleRefDisplay(String display) {
		this.moduleRefDisplay = display;
	}

	/** 
	 * Foreign key to reference: OriginCategory
	 * 
	 */ 
	public int getOriginCategoryRefId() {
		return originCategoryRefId;
	}

	/** 
	 * Foreign key to reference: OriginCategory
	 * 
	 * Required database field.
	 */ 
	public void setOriginCategoryRefId(int originCategoryRefId) {
		if (!Comparison.areEqual(this.originCategoryRefId, originCategoryRefId)) {
			this.originCategoryRefId = originCategoryRefId;
			setModified("originCategoryRefId");
		};
	}

	/** 
	 * Foreign key to reference: OriginCategory
	 * 
	 */ 
	public String getOriginCategoryRefDisplay() {
		return originCategoryRefDisplay;
	}

	/** 
	 * Foreign key to reference: OriginCategory
	 * 
	 */ 
	public void setOriginCategoryRefDisplay(String display) {
		this.originCategoryRefDisplay = display;
	}

	/** 
	 * Reason to change artifact.  Only populated in the history table.
	 * 
	 */ 
	public String getChangeReason() {
		return changeReason;
	}

	/** 
	 * Reason to change artifact.  Only populated in the history table.
	 * 
	 */ 
	public void setChangeReason(String changeReason) {
		if (!Comparison.areEqual(this.changeReason, changeReason)) {
			this.changeReason = changeReason;
			setModified("changeReason");
		};
	}

	/** 
	 * Change made to the artifact
	 * 
	 */ 
	public String getChangeMade() {
		return changeMade;
	}

	/** 
	 * Change made to the artifact
	 * 
	 */ 
	public void setChangeMade(String changeMade) {
		if (!Comparison.areEqual(this.changeMade, changeMade)) {
			this.changeMade = changeMade;
			setModified("changeMade");
		};
	}

	/** 
	 * Sequence of artifacts duplicating the order in the tree
	 * 
	 */ 
	public int getReportSequence() {
		return reportSequence;
	}

	/** 
	 * Sequence of artifacts duplicating the order in the tree
	 * 
	 */ 
	public void setReportSequence(int reportSequence) {
		if (!Comparison.areEqual(this.reportSequence, reportSequence)) {
			this.reportSequence = reportSequence;
			setModified("reportSequence");
		};
	}

	/** 
	 * Custom outline assigned to artifacts based upon a script.  Manually generated.
	 * 
	 */ 
	public String getReportOutline() {
		return reportOutline;
	}

	/** 
	 * Custom outline assigned to artifacts based upon a script.  Manually generated.
	 * 
	 */ 
	public void setReportOutline(String reportOutline) {
		if (!Comparison.areEqual(this.reportOutline, reportOutline)) {
			this.reportOutline = reportOutline;
			setModified("reportOutline");
		};
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

	/** 
	 * Custom text field 1
	 * 
	 */ 
	public String getCustomText1() {
		return customText1;
	}

	/** 
	 * Custom text field 1
	 * 
	 */ 
	public void setCustomText1(String customText1) {
		if (!Comparison.areEqual(this.customText1, customText1)) {
			this.customText1 = customText1;
			setModified("customText1");
		};
	}

	/** 
	 * Custom text field 2
	 * 
	 */ 
	public String getCustomText2() {
		return customText2;
	}

	/** 
	 * Custom text field 2
	 * 
	 */ 
	public void setCustomText2(String customText2) {
		if (!Comparison.areEqual(this.customText2, customText2)) {
			this.customText2 = customText2;
			setModified("customText2");
		};
	}

	/** 
	 * Custom text field 3
	 * 
	 */ 
	public String getCustomText3() {
		return customText3;
	}

	/** 
	 * Custom text field 3
	 * 
	 */ 
	public void setCustomText3(String customText3) {
		if (!Comparison.areEqual(this.customText3, customText3)) {
			this.customText3 = customText3;
			setModified("customText3");
		};
	}

	/** 
	 * Custom text field 4
	 * 
	 */ 
	public String getCustomText4() {
		return customText4;
	}

	/** 
	 * Custom text field 4
	 * 
	 */ 
	public void setCustomText4(String customText4) {
		if (!Comparison.areEqual(this.customText4, customText4)) {
			this.customText4 = customText4;
			setModified("customText4");
		};
	}

	/** 
	 * Custom ref field 1
	 * 
	 */ 
	public int getCustom1RefId() {
		return custom1RefId;
	}

	/** 
	 * Custom ref field 1
	 * 
	 */ 
	public void setCustom1RefId(int custom1RefId) {
		if (!Comparison.areEqual(this.custom1RefId, custom1RefId)) {
			this.custom1RefId = custom1RefId;
			setModified("custom1RefId");
		};
	}

	/** 
	 * Custom ref field 1
	 * 
	 */ 
	public String getCustom1RefDisplay() {
		return custom1RefDisplay;
	}

	/** 
	 * Custom ref field 1
	 * 
	 */ 
	public void setCustom1RefDisplay(String display) {
		this.custom1RefDisplay = display;
	}

	/** 
	 * Custom ref field 2
	 * 
	 */ 
	public int getCustom2RefId() {
		return custom2RefId;
	}

	/** 
	 * Custom ref field 2
	 * 
	 */ 
	public void setCustom2RefId(int custom2RefId) {
		if (!Comparison.areEqual(this.custom2RefId, custom2RefId)) {
			this.custom2RefId = custom2RefId;
			setModified("custom2RefId");
		};
	}

	/** 
	 * Custom ref field 2
	 * 
	 */ 
	public String getCustom2RefDisplay() {
		return custom2RefDisplay;
	}

	/** 
	 * Custom ref field 2
	 * 
	 */ 
	public void setCustom2RefDisplay(String display) {
		this.custom2RefDisplay = display;
	}

	/** 
	 * Custom ref field 3
	 * 
	 */ 
	public int getCustom3RefId() {
		return custom3RefId;
	}

	/** 
	 * Custom ref field 3
	 * 
	 */ 
	public void setCustom3RefId(int custom3RefId) {
		if (!Comparison.areEqual(this.custom3RefId, custom3RefId)) {
			this.custom3RefId = custom3RefId;
			setModified("custom3RefId");
		};
	}

	/** 
	 * Custom ref field 3
	 * 
	 */ 
	public String getCustom3RefDisplay() {
		return custom3RefDisplay;
	}

	/** 
	 * Custom ref field 3
	 * 
	 */ 
	public void setCustom3RefDisplay(String display) {
		this.custom3RefDisplay = display;
	}

	/** 
	 * Custom ref field 4
	 * 
	 */ 
	public int getCustom4RefId() {
		return custom4RefId;
	}

	/** 
	 * Custom ref field 4
	 * 
	 */ 
	public void setCustom4RefId(int custom4RefId) {
		if (!Comparison.areEqual(this.custom4RefId, custom4RefId)) {
			this.custom4RefId = custom4RefId;
			setModified("custom4RefId");
		};
	}

	/** 
	 * Custom ref field 4
	 * 
	 */ 
	public String getCustom4RefDisplay() {
		return custom4RefDisplay;
	}

	/** 
	 * Custom ref field 4
	 * 
	 */ 
	public void setCustom4RefDisplay(String display) {
		this.custom4RefDisplay = display;
	}

	/** 
	 * Custom date field 1
	 * 
	 */ 
	public DbCalendar getCustomDate1() {
		return customDate1;
	}

	/** 
	 * Custom date field 1
	 * 
	 */ 
	public void setCustomDate1(GregorianCalendar customDate1) {
		if (!Comparison.areEqual(this.customDate1, customDate1)) {
			this.customDate1 = new DbCalendar();
			if (customDate1 != null) {
				this.customDate1.setTimeInMillis(customDate1.getTimeInMillis());
			}
			setModified("customDate1");
		};
	}

	/** 
	 * Custom date field 1
	 * 
	 */ 
	public void setCustomDate1(long milliseconds) {
		if (this.customDate1==null) {
			this.customDate1 = new DbCalendar();
		}
		if (!Comparison.areEqual(this.customDate1, customDate1)) {
			this.customDate1.setTimeInMillis(milliseconds);
			setModified("customDate1");
		}

	}

	/** 
	 * Custom date field 2
	 * 
	 */ 
	public DbCalendar getCustomDate2() {
		return customDate2;
	}

	/** 
	 * Custom date field 2
	 * 
	 */ 
	public void setCustomDate2(GregorianCalendar customDate2) {
		if (!Comparison.areEqual(this.customDate2, customDate2)) {
			this.customDate2 = new DbCalendar();
			if (customDate2 != null) {
				this.customDate2.setTimeInMillis(customDate2.getTimeInMillis());
			}
			setModified("customDate2");
		};
	}

	/** 
	 * Custom date field 2
	 * 
	 */ 
	public void setCustomDate2(long milliseconds) {
		if (this.customDate2==null) {
			this.customDate2 = new DbCalendar();
		}
		if (!Comparison.areEqual(this.customDate2, customDate2)) {
			this.customDate2.setTimeInMillis(milliseconds);
			setModified("customDate2");
		}

	}

	/** 
	 * Custom memo field 1
	 * 
	 */ 
	public String getCustomMemo1() {
		return customMemo1;
	}

	/** 
	 * Custom memo field 1
	 * 
	 */ 
	public void setCustomMemo1(String customMemo1) {
		if (!Comparison.areEqual(this.customMemo1, customMemo1)) {
			this.customMemo1 = customMemo1;
			setModified("customMemo1");
		};
	}

	/** 
	 * Custom memo field 2
	 * 
	 */ 
	public String getCustomMemo2() {
		return customMemo2;
	}

	/** 
	 * Custom memo field 2
	 * 
	 */ 
	public void setCustomMemo2(String customMemo2) {
		if (!Comparison.areEqual(this.customMemo2, customMemo2)) {
			this.customMemo2 = customMemo2;
			setModified("customMemo2");
		};
	}

	/** 
	 * Custom integer field 1
	 * 
	 */ 
	public int getCustomInt1() {
		return customInt1;
	}

	/** 
	 * Custom integer field 1
	 * 
	 */ 
	public void setCustomInt1(int customInt1) {
		if (!Comparison.areEqual(this.customInt1, customInt1)) {
			this.customInt1 = customInt1;
			setModified("customInt1");
		};
	}

	/** 
	 * Custom integer field 2
	 * 
	 */ 
	public int getCustomInt2() {
		return customInt2;
	}

	/** 
	 * Custom integer field 2
	 * 
	 */ 
	public void setCustomInt2(int customInt2) {
		if (!Comparison.areEqual(this.customInt2, customInt2)) {
			this.customInt2 = customInt2;
			setModified("customInt2");
		};
	}

	/** 
	 * Custom double field 1
	 * 
	 */ 
	public double getCustomDouble1() {
		return customDouble1;
	}

	/** 
	 * Custom double field 1
	 * 
	 */ 
	public void setCustomDouble1(double customDouble1) {
		if (!Comparison.areEqual(this.customDouble1, customDouble1)) {
			this.customDouble1 = customDouble1;
			setModified("customDouble1");
		};
	}


public static ResultColumnList getResultColumnList() {
	ResultColumnList columns = new ResultColumnList();
		columns.addColumn("artifactId", Integer.class);
		columns.addColumn("productRefId", Integer.class);
		columns.addColumn("productRefDisplay", String.class);
		columns.addColumn("versionRefId", Integer.class);
		columns.addColumn("versionRefDisplay", String.class);
		columns.addColumn("artifactRefId", Integer.class);
		columns.addColumn("artifactRefDisplay", String.class);
		columns.addColumn("artifactNbr", Integer.class);
		columns.addColumn("artifactSeq", Integer.class);
		columns.addColumn("artifactLevelRefId", Integer.class);
		columns.addColumn("artifactLevelRefDisplay", String.class);
		columns.addColumn("componentTypeRefId", Integer.class);
		columns.addColumn("componentTypeRefDisplay", String.class);
		columns.addColumn("lastUpdatedBaselineId", Integer.class);
		columns.addColumn("artifactName", String.class);
		columns.addColumn("description", String.class);
		columns.addColumn("statusRefId", Integer.class);
		columns.addColumn("statusRefDisplay", String.class);
		columns.addColumn("priorityRefId", Integer.class);
		columns.addColumn("priorityRefDisplay", String.class);
		columns.addColumn("complexityRefId", Integer.class);
		columns.addColumn("complexityRefDisplay", String.class);
		columns.addColumn("authorRefId", Integer.class);
		columns.addColumn("authorRefDisplay", String.class);
		columns.addColumn("assignedRefId", Integer.class);
		columns.addColumn("assignedRefDisplay", String.class);
		columns.addColumn("categoryRefId", Integer.class);
		columns.addColumn("categoryRefDisplay", String.class);
		columns.addColumn("assignedUserId", Integer.class);
		columns.addColumn("assignedUserName", String.class);
		columns.addColumn("assignedUserGroupId", Integer.class);
		columns.addColumn("effort", Double.class);
		columns.addColumn("rationale", String.class);
		columns.addColumn("origin", String.class);
		columns.addColumn("goal", String.class);
		columns.addColumn("context", String.class);
		columns.addColumn("precondition", String.class);
		columns.addColumn("postcondition", String.class);
		columns.addColumn("summary", String.class);
		columns.addColumn("externalReferences", String.class);
		columns.addColumn("plannedVersionRefId", Integer.class);
		columns.addColumn("plannedVersionRefDisplay", String.class);
		columns.addColumn("lastModifiedVersionRefId", Integer.class);
		columns.addColumn("lastModifiedVersionRefDisplay", String.class);
		columns.addColumn("removedInd", Integer.class);
		columns.addColumn("moduleRefId", Integer.class);
		columns.addColumn("moduleRefDisplay", String.class);
		columns.addColumn("originCategoryRefId", Integer.class);
		columns.addColumn("originCategoryRefDisplay", String.class);
		columns.addColumn("changeReason", String.class);
		columns.addColumn("changeMade", String.class);
		columns.addColumn("reportSequence", Integer.class);
		columns.addColumn("reportOutline", String.class);
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
		columns.addColumn("customText1", String.class);
		columns.addColumn("customText2", String.class);
		columns.addColumn("customText3", String.class);
		columns.addColumn("customText4", String.class);
		columns.addColumn("custom1RefId", Integer.class);
		columns.addColumn("custom1RefDisplay", String.class);
		columns.addColumn("custom2RefId", Integer.class);
		columns.addColumn("custom2RefDisplay", String.class);
		columns.addColumn("custom3RefId", Integer.class);
		columns.addColumn("custom3RefDisplay", String.class);
		columns.addColumn("custom4RefId", Integer.class);
		columns.addColumn("custom4RefDisplay", String.class);
		columns.addColumn("customDate1", GregorianCalendar.class);
		columns.addColumn("customDate2", GregorianCalendar.class);
		columns.addColumn("customMemo1", String.class);
		columns.addColumn("customMemo2", String.class);
		columns.addColumn("customInt1", Integer.class);
		columns.addColumn("customInt2", Integer.class);
		columns.addColumn("customDouble1", Double.class);
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
			if (reference != null && this.productRefId > 0) setProductRefDisplay(reference.getDisplay(this.productRefId));
			if (reference != null && this.versionRefId > 0) setVersionRefDisplay(reference.getDisplay(this.versionRefId));
			if (reference != null && this.artifactRefId > 0) setArtifactRefDisplay(reference.getDisplay(this.artifactRefId));
			if (reference != null && this.artifactLevelRefId > 0) setArtifactLevelRefDisplay(reference.getDisplay(this.artifactLevelRefId));
			if (reference != null && this.componentTypeRefId > 0) setComponentTypeRefDisplay(reference.getDisplay(this.componentTypeRefId));
			if (reference != null && this.statusRefId > 0) setStatusRefDisplay(reference.getDisplay(this.statusRefId));
			if (reference != null && this.priorityRefId > 0) setPriorityRefDisplay(reference.getDisplay(this.priorityRefId));
			if (reference != null && this.complexityRefId > 0) setComplexityRefDisplay(reference.getDisplay(this.complexityRefId));
			if (reference != null && this.authorRefId > 0) setAuthorRefDisplay(reference.getDisplay(this.authorRefId));
			if (reference != null && this.assignedRefId > 0) setAssignedRefDisplay(reference.getDisplay(this.assignedRefId));
			if (reference != null && this.categoryRefId > 0) setCategoryRefDisplay(reference.getDisplay(this.categoryRefId));
			if (security != null && this.assignedUserId > 0) setAssignedUserName(security.getUser(this.assignedUserId).getDisplayName());
			if (reference != null && this.plannedVersionRefId > 0) setPlannedVersionRefDisplay(reference.getDisplay(this.plannedVersionRefId));
			if (reference != null && this.lastModifiedVersionRefId > 0) setLastModifiedVersionRefDisplay(reference.getDisplay(this.lastModifiedVersionRefId));
			if (reference != null && this.moduleRefId > 0) setModuleRefDisplay(reference.getDisplay(this.moduleRefId));
			if (reference != null && this.originCategoryRefId > 0) setOriginCategoryRefDisplay(reference.getDisplay(this.originCategoryRefId));
			if (security != null && this.createUserId > 0) setCreateUserName(security.getUser(this.createUserId).getDisplayName());
			if (security != null && this.updateUserId > 0) setUpdateUserName(security.getUser(this.updateUserId).getDisplayName());
			if (reference != null && this.recordTypeRefId > 0) setRecordTypeRefDisplay(reference.getDisplay(this.recordTypeRefId));
			if (reference != null && this.custom1RefId > 0) setCustom1RefDisplay(reference.getDisplay(this.custom1RefId));
			if (reference != null && this.custom2RefId > 0) setCustom2RefDisplay(reference.getDisplay(this.custom2RefId));
			if (reference != null && this.custom3RefId > 0) setCustom3RefDisplay(reference.getDisplay(this.custom3RefId));
			if (reference != null && this.custom4RefId > 0) setCustom4RefDisplay(reference.getDisplay(this.custom4RefId));
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
	public void copyModifiedTo(ArtifactDataModel m) {
		Enumeration e1 = modifiedFields.keys();
		while (e1.hasMoreElements()) {
			String fieldName = (String) e1.nextElement();

			if (fieldName.compareTo("artifactId")==0)
				m.setArtifactId(this.getArtifactId());
			else if (fieldName.compareTo("productRefId")==0)
				m.setProductRefId(this.getProductRefId());
			else if (fieldName.compareTo("versionRefId")==0)
				m.setVersionRefId(this.getVersionRefId());
			else if (fieldName.compareTo("artifactRefId")==0)
				m.setArtifactRefId(this.getArtifactRefId());
			else if (fieldName.compareTo("artifactNbr")==0)
				m.setArtifactNbr(this.getArtifactNbr());
			else if (fieldName.compareTo("artifactSeq")==0)
				m.setArtifactSeq(this.getArtifactSeq());
			else if (fieldName.compareTo("artifactLevelRefId")==0)
				m.setArtifactLevelRefId(this.getArtifactLevelRefId());
			else if (fieldName.compareTo("componentTypeRefId")==0)
				m.setComponentTypeRefId(this.getComponentTypeRefId());
			else if (fieldName.compareTo("lastUpdatedBaselineId")==0)
				m.setLastUpdatedBaselineId(this.getLastUpdatedBaselineId());
			else if (fieldName.compareTo("artifactName")==0)
				m.setArtifactName(this.getArtifactName());
			else if (fieldName.compareTo("description")==0)
				m.setDescription(this.getDescription());
			else if (fieldName.compareTo("statusRefId")==0)
				m.setStatusRefId(this.getStatusRefId());
			else if (fieldName.compareTo("priorityRefId")==0)
				m.setPriorityRefId(this.getPriorityRefId());
			else if (fieldName.compareTo("complexityRefId")==0)
				m.setComplexityRefId(this.getComplexityRefId());
			else if (fieldName.compareTo("authorRefId")==0)
				m.setAuthorRefId(this.getAuthorRefId());
			else if (fieldName.compareTo("assignedRefId")==0)
				m.setAssignedRefId(this.getAssignedRefId());
			else if (fieldName.compareTo("categoryRefId")==0)
				m.setCategoryRefId(this.getCategoryRefId());
			else if (fieldName.compareTo("assignedUserId")==0)
				m.setAssignedUserId(this.getAssignedUserId());
			else if (fieldName.compareTo("assignedUserGroupId")==0)
				m.setAssignedUserGroupId(this.getAssignedUserGroupId());
			else if (fieldName.compareTo("effort")==0)
				m.setEffort(this.getEffort());
			else if (fieldName.compareTo("rationale")==0)
				m.setRationale(this.getRationale());
			else if (fieldName.compareTo("origin")==0)
				m.setOrigin(this.getOrigin());
			else if (fieldName.compareTo("goal")==0)
				m.setGoal(this.getGoal());
			else if (fieldName.compareTo("context")==0)
				m.setContext(this.getContext());
			else if (fieldName.compareTo("precondition")==0)
				m.setPrecondition(this.getPrecondition());
			else if (fieldName.compareTo("postcondition")==0)
				m.setPostcondition(this.getPostcondition());
			else if (fieldName.compareTo("summary")==0)
				m.setSummary(this.getSummary());
			else if (fieldName.compareTo("externalReferences")==0)
				m.setExternalReferences(this.getExternalReferences());
			else if (fieldName.compareTo("plannedVersionRefId")==0)
				m.setPlannedVersionRefId(this.getPlannedVersionRefId());
			else if (fieldName.compareTo("lastModifiedVersionRefId")==0)
				m.setLastModifiedVersionRefId(this.getLastModifiedVersionRefId());
			else if (fieldName.compareTo("removedInd")==0)
				m.setRemovedInd(this.getRemovedInd());
			else if (fieldName.compareTo("moduleRefId")==0)
				m.setModuleRefId(this.getModuleRefId());
			else if (fieldName.compareTo("originCategoryRefId")==0)
				m.setOriginCategoryRefId(this.getOriginCategoryRefId());
			else if (fieldName.compareTo("changeReason")==0)
				m.setChangeReason(this.getChangeReason());
			else if (fieldName.compareTo("changeMade")==0)
				m.setChangeMade(this.getChangeMade());
			else if (fieldName.compareTo("reportSequence")==0)
				m.setReportSequence(this.getReportSequence());
			else if (fieldName.compareTo("reportOutline")==0)
				m.setReportOutline(this.getReportOutline());
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
			else if (fieldName.compareTo("customText1")==0)
				m.setCustomText1(this.getCustomText1());
			else if (fieldName.compareTo("customText2")==0)
				m.setCustomText2(this.getCustomText2());
			else if (fieldName.compareTo("customText3")==0)
				m.setCustomText3(this.getCustomText3());
			else if (fieldName.compareTo("customText4")==0)
				m.setCustomText4(this.getCustomText4());
			else if (fieldName.compareTo("custom1RefId")==0)
				m.setCustom1RefId(this.getCustom1RefId());
			else if (fieldName.compareTo("custom2RefId")==0)
				m.setCustom2RefId(this.getCustom2RefId());
			else if (fieldName.compareTo("custom3RefId")==0)
				m.setCustom3RefId(this.getCustom3RefId());
			else if (fieldName.compareTo("custom4RefId")==0)
				m.setCustom4RefId(this.getCustom4RefId());
			else if (fieldName.compareTo("customDate1")==0)
				m.setCustomDate1(this.getCustomDate1());
			else if (fieldName.compareTo("customDate2")==0)
				m.setCustomDate2(this.getCustomDate2());
			else if (fieldName.compareTo("customMemo1")==0)
				m.setCustomMemo1(this.getCustomMemo1());
			else if (fieldName.compareTo("customMemo2")==0)
				m.setCustomMemo2(this.getCustomMemo2());
			else if (fieldName.compareTo("customInt1")==0)
				m.setCustomInt1(this.getCustomInt1());
			else if (fieldName.compareTo("customInt2")==0)
				m.setCustomInt2(this.getCustomInt2());
			else if (fieldName.compareTo("customDouble1")==0)
				m.setCustomDouble1(this.getCustomDouble1());
			else 		
				Debug.LogError(this, ExceptionInfo.invalidCopyModifiedField + " " + fieldName);
	}
}

	/**
	 * Update this object with the data from m
	 */
	public void updateWith (ArtifactModel m) {

		this.setArtifactId(m.getArtifactId());
		this.setProductRefId(m.getProductRefId());
		this.setProductRefDisplay(m.getProductRefDisplay());
		this.setVersionRefId(m.getVersionRefId());
		this.setVersionRefDisplay(m.getVersionRefDisplay());
		this.setArtifactRefId(m.getArtifactRefId());
		this.setArtifactRefDisplay(m.getArtifactRefDisplay());
		this.setArtifactNbr(m.getArtifactNbr());
		this.setArtifactSeq(m.getArtifactSeq());
		this.setArtifactLevelRefId(m.getArtifactLevelRefId());
		this.setArtifactLevelRefDisplay(m.getArtifactLevelRefDisplay());
		this.setComponentTypeRefId(m.getComponentTypeRefId());
		this.setComponentTypeRefDisplay(m.getComponentTypeRefDisplay());
		this.setLastUpdatedBaselineId(m.getLastUpdatedBaselineId());
		this.setArtifactName(m.getArtifactName());
		this.setDescription(m.getDescription());
		this.setStatusRefId(m.getStatusRefId());
		this.setStatusRefDisplay(m.getStatusRefDisplay());
		this.setPriorityRefId(m.getPriorityRefId());
		this.setPriorityRefDisplay(m.getPriorityRefDisplay());
		this.setComplexityRefId(m.getComplexityRefId());
		this.setComplexityRefDisplay(m.getComplexityRefDisplay());
		this.setAuthorRefId(m.getAuthorRefId());
		this.setAuthorRefDisplay(m.getAuthorRefDisplay());
		this.setAssignedRefId(m.getAssignedRefId());
		this.setAssignedRefDisplay(m.getAssignedRefDisplay());
		this.setCategoryRefId(m.getCategoryRefId());
		this.setCategoryRefDisplay(m.getCategoryRefDisplay());
		this.setAssignedUserId(m.getAssignedUserId());
		this.setAssignedUserName(m.getAssignedUserName());
		this.setAssignedUserGroupId(m.getAssignedUserGroupId());
		this.setEffort(m.getEffort());
		this.setRationale(m.getRationale());
		this.setOrigin(m.getOrigin());
		this.setGoal(m.getGoal());
		this.setContext(m.getContext());
		this.setPrecondition(m.getPrecondition());
		this.setPostcondition(m.getPostcondition());
		this.setSummary(m.getSummary());
		this.setExternalReferences(m.getExternalReferences());
		this.setPlannedVersionRefId(m.getPlannedVersionRefId());
		this.setPlannedVersionRefDisplay(m.getPlannedVersionRefDisplay());
		this.setLastModifiedVersionRefId(m.getLastModifiedVersionRefId());
		this.setLastModifiedVersionRefDisplay(m.getLastModifiedVersionRefDisplay());
		this.setRemovedInd(m.getRemovedInd());
		this.setModuleRefId(m.getModuleRefId());
		this.setModuleRefDisplay(m.getModuleRefDisplay());
		this.setOriginCategoryRefId(m.getOriginCategoryRefId());
		this.setOriginCategoryRefDisplay(m.getOriginCategoryRefDisplay());
		this.setChangeReason(m.getChangeReason());
		this.setChangeMade(m.getChangeMade());
		this.setReportSequence(m.getReportSequence());
		this.setReportOutline(m.getReportOutline());
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
		this.setCustomText1(m.getCustomText1());
		this.setCustomText2(m.getCustomText2());
		this.setCustomText3(m.getCustomText3());
		this.setCustomText4(m.getCustomText4());
		this.setCustom1RefId(m.getCustom1RefId());
		this.setCustom1RefDisplay(m.getCustom1RefDisplay());
		this.setCustom2RefId(m.getCustom2RefId());
		this.setCustom2RefDisplay(m.getCustom2RefDisplay());
		this.setCustom3RefId(m.getCustom3RefId());
		this.setCustom3RefDisplay(m.getCustom3RefDisplay());
		this.setCustom4RefId(m.getCustom4RefId());
		this.setCustom4RefDisplay(m.getCustom4RefDisplay());
		this.setCustomDate1(m.getCustomDate1());
		this.setCustomDate2(m.getCustomDate2());
		this.setCustomMemo1(m.getCustomMemo1());
		this.setCustomMemo2(m.getCustomMemo2());
		this.setCustomInt1(m.getCustomInt1());
		this.setCustomInt2(m.getCustomInt2());
		this.setCustomDouble1(m.getCustomDouble1());
	}

	/**
	 * Compare the two objects
	 */
	public boolean isEqualTo (ArtifactModel m) {

		if (!Comparison.areEqual(this.getArtifactId(),  m.getArtifactId())) return false;
		if (!Comparison.areEqual(this.getProductRefId(),  m.getProductRefId())) return false;
		if (!Comparison.areEqual(this.getVersionRefId(),  m.getVersionRefId())) return false;
		if (!Comparison.areEqual(this.getArtifactRefId(),  m.getArtifactRefId())) return false;
		if (!Comparison.areEqual(this.getArtifactNbr(),  m.getArtifactNbr())) return false;
		if (!Comparison.areEqual(this.getArtifactSeq(),  m.getArtifactSeq())) return false;
		if (!Comparison.areEqual(this.getArtifactLevelRefId(),  m.getArtifactLevelRefId())) return false;
		if (!Comparison.areEqual(this.getComponentTypeRefId(),  m.getComponentTypeRefId())) return false;
		if (!Comparison.areEqual(this.getLastUpdatedBaselineId(),  m.getLastUpdatedBaselineId())) return false;
		if (!Comparison.areEqual(this.getArtifactName(),  m.getArtifactName())) return false;
		if (!Comparison.areEqual(this.getDescription(),  m.getDescription())) return false;
		if (!Comparison.areEqual(this.getStatusRefId(),  m.getStatusRefId())) return false;
		if (!Comparison.areEqual(this.getPriorityRefId(),  m.getPriorityRefId())) return false;
		if (!Comparison.areEqual(this.getComplexityRefId(),  m.getComplexityRefId())) return false;
		if (!Comparison.areEqual(this.getAuthorRefId(),  m.getAuthorRefId())) return false;
		if (!Comparison.areEqual(this.getAssignedRefId(),  m.getAssignedRefId())) return false;
		if (!Comparison.areEqual(this.getCategoryRefId(),  m.getCategoryRefId())) return false;
		if (!Comparison.areEqual(this.getAssignedUserId(),  m.getAssignedUserId())) return false;
		if (!Comparison.areEqual(this.getAssignedUserGroupId(),  m.getAssignedUserGroupId())) return false;
		if (!Comparison.areEqual(this.getEffort(),  m.getEffort())) return false;
		if (!Comparison.areEqual(this.getRationale(),  m.getRationale())) return false;
		if (!Comparison.areEqual(this.getOrigin(),  m.getOrigin())) return false;
		if (!Comparison.areEqual(this.getGoal(),  m.getGoal())) return false;
		if (!Comparison.areEqual(this.getContext(),  m.getContext())) return false;
		if (!Comparison.areEqual(this.getPrecondition(),  m.getPrecondition())) return false;
		if (!Comparison.areEqual(this.getPostcondition(),  m.getPostcondition())) return false;
		if (!Comparison.areEqual(this.getSummary(),  m.getSummary())) return false;
		if (!Comparison.areEqual(this.getExternalReferences(),  m.getExternalReferences())) return false;
		if (!Comparison.areEqual(this.getPlannedVersionRefId(),  m.getPlannedVersionRefId())) return false;
		if (!Comparison.areEqual(this.getLastModifiedVersionRefId(),  m.getLastModifiedVersionRefId())) return false;
		if (!Comparison.areEqual(this.getRemovedInd(),  m.getRemovedInd())) return false;
		if (!Comparison.areEqual(this.getModuleRefId(),  m.getModuleRefId())) return false;
		if (!Comparison.areEqual(this.getOriginCategoryRefId(),  m.getOriginCategoryRefId())) return false;
		if (!Comparison.areEqual(this.getChangeReason(),  m.getChangeReason())) return false;
		if (!Comparison.areEqual(this.getChangeMade(),  m.getChangeMade())) return false;
		if (!Comparison.areEqual(this.getReportSequence(),  m.getReportSequence())) return false;
		if (!Comparison.areEqual(this.getReportOutline(),  m.getReportOutline())) return false;
		if (!Comparison.areEqual(this.getCreateDt(),  m.getCreateDt())) return false;
		if (!Comparison.areEqual(this.getCreateUserId(),  m.getCreateUserId())) return false;
		if (!Comparison.areEqual(this.getUpdateCount(),  m.getUpdateCount())) return false;
		if (!Comparison.areEqual(this.getActiveInd(),  m.getActiveInd())) return false;
		if (!Comparison.areEqual(this.getSystemAssignedVersionNbr(),  m.getSystemAssignedVersionNbr())) return false;
		if (!Comparison.areEqual(this.getRecordTypeRefId(),  m.getRecordTypeRefId())) return false;
		if (!Comparison.areEqual(this.getCustomText1(),  m.getCustomText1())) return false;
		if (!Comparison.areEqual(this.getCustomText2(),  m.getCustomText2())) return false;
		if (!Comparison.areEqual(this.getCustomText3(),  m.getCustomText3())) return false;
		if (!Comparison.areEqual(this.getCustomText4(),  m.getCustomText4())) return false;
		if (!Comparison.areEqual(this.getCustom1RefId(),  m.getCustom1RefId())) return false;
		if (!Comparison.areEqual(this.getCustom2RefId(),  m.getCustom2RefId())) return false;
		if (!Comparison.areEqual(this.getCustom3RefId(),  m.getCustom3RefId())) return false;
		if (!Comparison.areEqual(this.getCustom4RefId(),  m.getCustom4RefId())) return false;
		if (!Comparison.areEqual(this.getCustomDate1(),  m.getCustomDate1())) return false;
		if (!Comparison.areEqual(this.getCustomDate2(),  m.getCustomDate2())) return false;
		if (!Comparison.areEqual(this.getCustomMemo1(),  m.getCustomMemo1())) return false;
		if (!Comparison.areEqual(this.getCustomMemo2(),  m.getCustomMemo2())) return false;
		if (!Comparison.areEqual(this.getCustomInt1(),  m.getCustomInt1())) return false;
		if (!Comparison.areEqual(this.getCustomInt2(),  m.getCustomInt2())) return false;
		if (!Comparison.areEqual(this.getCustomDouble1(),  m.getCustomDouble1())) return false;
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
			sb.append("ArtifactModel:\r\n");
			sb.append("artifact_id:" + getArtifactId());
			sb.append("\r\n");
			sb.append("product_ref_id:" + getProductRefId());
			sb.append("\r\n");
			sb.append("product_ref_display:" + getProductRefDisplay());
			sb.append("\r\n");
			sb.append("version_ref_id:" + getVersionRefId());
			sb.append("\r\n");
			sb.append("version_ref_display:" + getVersionRefDisplay());
			sb.append("\r\n");
			sb.append("artifact_ref_id:" + getArtifactRefId());
			sb.append("\r\n");
			sb.append("artifact_ref_display:" + getArtifactRefDisplay());
			sb.append("\r\n");
			sb.append("artifact_nbr:" + getArtifactNbr());
			sb.append("\r\n");
			sb.append("artifact_seq:" + getArtifactSeq());
			sb.append("\r\n");
			sb.append("artifact_level_ref_id:" + getArtifactLevelRefId());
			sb.append("\r\n");
			sb.append("artifact_level_ref_display:" + getArtifactLevelRefDisplay());
			sb.append("\r\n");
			sb.append("component_type_ref_id:" + getComponentTypeRefId());
			sb.append("\r\n");
			sb.append("component_type_ref_display:" + getComponentTypeRefDisplay());
			sb.append("\r\n");
			sb.append("last_updated_baseline_id:" + getLastUpdatedBaselineId());
			sb.append("\r\n");
			sb.append("artifact_name:" + getArtifactName());
			sb.append("\r\n");
			sb.append("description:" + getDescription());
			sb.append("\r\n");
			sb.append("status_ref_id:" + getStatusRefId());
			sb.append("\r\n");
			sb.append("status_ref_display:" + getStatusRefDisplay());
			sb.append("\r\n");
			sb.append("priority_ref_id:" + getPriorityRefId());
			sb.append("\r\n");
			sb.append("priority_ref_display:" + getPriorityRefDisplay());
			sb.append("\r\n");
			sb.append("complexity_ref_id:" + getComplexityRefId());
			sb.append("\r\n");
			sb.append("complexity_ref_display:" + getComplexityRefDisplay());
			sb.append("\r\n");
			sb.append("author_ref_id:" + getAuthorRefId());
			sb.append("\r\n");
			sb.append("author_ref_display:" + getAuthorRefDisplay());
			sb.append("\r\n");
			sb.append("assigned_ref_id:" + getAssignedRefId());
			sb.append("\r\n");
			sb.append("assigned_ref_display:" + getAssignedRefDisplay());
			sb.append("\r\n");
			sb.append("category_ref_id:" + getCategoryRefId());
			sb.append("\r\n");
			sb.append("category_ref_display:" + getCategoryRefDisplay());
			sb.append("\r\n");
			sb.append("assigned_user_id:" + getAssignedUserId());
			sb.append("\r\n");
			sb.append("assigned_user_name:" + getAssignedUserName());
			sb.append("\r\n");
			sb.append("assigned_user_group_id:" + getAssignedUserGroupId());
			sb.append("\r\n");
			sb.append("effort:" + getEffort());
			sb.append("\r\n");
			sb.append("rationale:" + getRationale());
			sb.append("\r\n");
			sb.append("origin:" + getOrigin());
			sb.append("\r\n");
			sb.append("goal:" + getGoal());
			sb.append("\r\n");
			sb.append("context:" + getContext());
			sb.append("\r\n");
			sb.append("precondition:" + getPrecondition());
			sb.append("\r\n");
			sb.append("postcondition:" + getPostcondition());
			sb.append("\r\n");
			sb.append("summary:" + getSummary());
			sb.append("\r\n");
			sb.append("external_references:" + getExternalReferences());
			sb.append("\r\n");
			sb.append("planned_version_ref_id:" + getPlannedVersionRefId());
			sb.append("\r\n");
			sb.append("planned_version_ref_display:" + getPlannedVersionRefDisplay());
			sb.append("\r\n");
			sb.append("last_modified_version_ref_id:" + getLastModifiedVersionRefId());
			sb.append("\r\n");
			sb.append("last_modified_version_ref_display:" + getLastModifiedVersionRefDisplay());
			sb.append("\r\n");
			sb.append("removed_ind:" + getRemovedInd());
			sb.append("\r\n");
			sb.append("module_ref_id:" + getModuleRefId());
			sb.append("\r\n");
			sb.append("module_ref_display:" + getModuleRefDisplay());
			sb.append("\r\n");
			sb.append("origin_category_ref_id:" + getOriginCategoryRefId());
			sb.append("\r\n");
			sb.append("origin_category_ref_display:" + getOriginCategoryRefDisplay());
			sb.append("\r\n");
			sb.append("change_reason:" + getChangeReason());
			sb.append("\r\n");
			sb.append("change_made:" + getChangeMade());
			sb.append("\r\n");
			sb.append("report_sequence:" + getReportSequence());
			sb.append("\r\n");
			sb.append("report_outline:" + getReportOutline());
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
			sb.append("custom_text1:" + getCustomText1());
			sb.append("\r\n");
			sb.append("custom_text2:" + getCustomText2());
			sb.append("\r\n");
			sb.append("custom_text3:" + getCustomText3());
			sb.append("\r\n");
			sb.append("custom_text4:" + getCustomText4());
			sb.append("\r\n");
			sb.append("custom1_ref_id:" + getCustom1RefId());
			sb.append("\r\n");
			sb.append("custom1_ref_display:" + getCustom1RefDisplay());
			sb.append("\r\n");
			sb.append("custom2_ref_id:" + getCustom2RefId());
			sb.append("\r\n");
			sb.append("custom2_ref_display:" + getCustom2RefDisplay());
			sb.append("\r\n");
			sb.append("custom3_ref_id:" + getCustom3RefId());
			sb.append("\r\n");
			sb.append("custom3_ref_display:" + getCustom3RefDisplay());
			sb.append("\r\n");
			sb.append("custom4_ref_id:" + getCustom4RefId());
			sb.append("\r\n");
			sb.append("custom4_ref_display:" + getCustom4RefDisplay());
			sb.append("\r\n");
			sb.append("custom_date1:" + CalendarUtility.Format(getCustomDate1(),AppFormats.getLongDateTimeFormat()));
			sb.append("\r\n");
			sb.append("custom_date2:" + CalendarUtility.Format(getCustomDate2(),AppFormats.getLongDateTimeFormat()));
			sb.append("\r\n");
			sb.append("custom_memo1:" + getCustomMemo1());
			sb.append("\r\n");
			sb.append("custom_memo2:" + getCustomMemo2());
			sb.append("\r\n");
			sb.append("custom_int1:" + getCustomInt1());
			sb.append("\r\n");
			sb.append("custom_int2:" + getCustomInt2());
			sb.append("\r\n");
			sb.append("custom_double1:" + getCustomDouble1());
			sb.append("\r\n");
			return sb.toString();
		} catch (Exception ex) {
			return "ArtifactModel:"; 
		}
	}
	public void setModelColDataAt(Object o, int modelCol) {
			switch (modelCol) {
			case 614: setArtifactId(((Integer) o).intValue()); break;
			case 615: setProductRefId(((Integer) o).intValue()); break;
			case 616: setVersionRefId(((Integer) o).intValue()); break;
			case 617: setArtifactRefId(((Integer) o).intValue()); break;
			case 618: setArtifactNbr(((Integer) o).intValue()); break;
			case 974: setArtifactSeq(((Integer) o).intValue()); break;
			case 945: setArtifactLevelRefId(((Integer) o).intValue()); break;
			case 982: setComponentTypeRefId(((Integer) o).intValue()); break;
			case 1154: setLastUpdatedBaselineId(((Integer) o).intValue()); break;
			case 619: setArtifactName((String) o); break;
			case 620: setDescription((String) o); break;
			case 621: setStatusRefId(((Integer) o).intValue()); break;
			case 622: setPriorityRefId(((Integer) o).intValue()); break;
			case 623: setComplexityRefId(((Integer) o).intValue()); break;
			case 624: setAuthorRefId(((Integer) o).intValue()); break;
			case 625: setAssignedRefId(((Integer) o).intValue()); break;
			case 946: setCategoryRefId(((Integer) o).intValue()); break;
			case 1643: setAssignedUserId(((Integer) o).intValue()); break;
			case 1677: setAssignedUserName((String) o); break;
			case 1644: setAssignedUserGroupId(((Integer) o).intValue()); break;
			case 626: setEffort(((Double) o).doubleValue()); break;
			case 627: setRationale((String) o); break;
			case 628: setOrigin((String) o); break;
			case 947: setGoal((String) o); break;
			case 948: setContext((String) o); break;
			case 949: setPrecondition((String) o); break;
			case 950: setPostcondition((String) o); break;
			case 951: setSummary((String) o); break;
			case 1176: setExternalReferences((String) o); break;
			case 1207: setPlannedVersionRefId(((Integer) o).intValue()); break;
			case 1208: setLastModifiedVersionRefId(((Integer) o).intValue()); break;
			case 1209: setRemovedInd(((Integer) o).intValue()); break;
			case 1210: setModuleRefId(((Integer) o).intValue()); break;
			case 1211: setOriginCategoryRefId(((Integer) o).intValue()); break;
			case 5354: setChangeReason((String) o); break;
			case 5469: setChangeMade((String) o); break;
			case 5355: setReportSequence(((Integer) o).intValue()); break;
			case 5356: setReportOutline((String) o); break;
			case 629: setCreateDt((GregorianCalendar) o); break;
			case 630: setCreateUserId(((Integer) o).intValue()); break;
			case 841: setCreateUserName((String) o); break;
			case 631: setUpdateDt((GregorianCalendar) o); break;
			case 632: setUpdateUserId(((Integer) o).intValue()); break;
			case 842: setUpdateUserName((String) o); break;
			case 633: setUpdateCount(((Integer) o).intValue()); break;
			case 634: setActiveInd(((Integer) o).intValue()); break;
			case 635: setSystemAssignedVersionNbr(((Integer) o).intValue()); break;
			case 636: setRecordTypeRefId(((Integer) o).intValue()); break;
			case 1645: setCustomText1((String) o); break;
			case 1646: setCustomText2((String) o); break;
			case 1647: setCustomText3((String) o); break;
			case 1648: setCustomText4((String) o); break;
			case 1649: setCustom1RefId(((Integer) o).intValue()); break;
			case 1650: setCustom2RefId(((Integer) o).intValue()); break;
			case 1651: setCustom3RefId(((Integer) o).intValue()); break;
			case 1652: setCustom4RefId(((Integer) o).intValue()); break;
			case 1653: setCustomDate1((GregorianCalendar) o); break;
			case 1654: setCustomDate2((GregorianCalendar) o); break;
			case 1655: setCustomMemo1((String) o); break;
			case 1656: setCustomMemo2((String) o); break;
			case 1657: setCustomInt1(((Integer) o).intValue()); break;
			case 1658: setCustomInt2(((Integer) o).intValue()); break;
			case 1659: setCustomDouble1(((Double) o).doubleValue()); break;
			default: Debug.LogWarning(this, ReferenceServices.getMsg(SystemMessageFramework.SWITCHSTATEMENTFAILED) + " modelColRefId: " + modelCol);
	} 
}

	public Object getModelColDataAt(int modelCol) {
			if (modelCol == 614) return new Integer(artifactId);
			if (modelCol == 615) return new Integer(productRefId);
			if (modelCol == 615) return productRefDisplay;
			if (modelCol == 616) return new Integer(versionRefId);
			if (modelCol == 616) return versionRefDisplay;
			if (modelCol == 617) return new Integer(artifactRefId);
			if (modelCol == 617) return artifactRefDisplay;
			if (modelCol == 618) return new Integer(artifactNbr);
			if (modelCol == 974) return new Integer(artifactSeq);
			if (modelCol == 945) return new Integer(artifactLevelRefId);
			if (modelCol == 945) return artifactLevelRefDisplay;
			if (modelCol == 982) return new Integer(componentTypeRefId);
			if (modelCol == 982) return componentTypeRefDisplay;
			if (modelCol == 1154) return new Integer(lastUpdatedBaselineId);
			if (modelCol == 619) return artifactName;
			if (modelCol == 620) return description;
			if (modelCol == 621) return new Integer(statusRefId);
			if (modelCol == 621) return statusRefDisplay;
			if (modelCol == 622) return new Integer(priorityRefId);
			if (modelCol == 622) return priorityRefDisplay;
			if (modelCol == 623) return new Integer(complexityRefId);
			if (modelCol == 623) return complexityRefDisplay;
			if (modelCol == 624) return new Integer(authorRefId);
			if (modelCol == 624) return authorRefDisplay;
			if (modelCol == 625) return new Integer(assignedRefId);
			if (modelCol == 625) return assignedRefDisplay;
			if (modelCol == 946) return new Integer(categoryRefId);
			if (modelCol == 946) return categoryRefDisplay;
			if (modelCol == 1643) return new Integer(assignedUserId);
			if (modelCol == 1643) return assignedUserName;
			if (modelCol == 1644) return new Integer(assignedUserGroupId);
			if (modelCol == 626) return new Double(effort);
			if (modelCol == 627) return rationale;
			if (modelCol == 628) return origin;
			if (modelCol == 947) return goal;
			if (modelCol == 948) return context;
			if (modelCol == 949) return precondition;
			if (modelCol == 950) return postcondition;
			if (modelCol == 951) return summary;
			if (modelCol == 1176) return externalReferences;
			if (modelCol == 1207) return new Integer(plannedVersionRefId);
			if (modelCol == 1207) return plannedVersionRefDisplay;
			if (modelCol == 1208) return new Integer(lastModifiedVersionRefId);
			if (modelCol == 1208) return lastModifiedVersionRefDisplay;
			if (modelCol == 1209) return new Integer(removedInd);
			if (modelCol == 1210) return new Integer(moduleRefId);
			if (modelCol == 1210) return moduleRefDisplay;
			if (modelCol == 1211) return new Integer(originCategoryRefId);
			if (modelCol == 1211) return originCategoryRefDisplay;
			if (modelCol == 5354) return changeReason;
			if (modelCol == 5469) return changeMade;
			if (modelCol == 5355) return new Integer(reportSequence);
			if (modelCol == 5356) return reportOutline;
			if (modelCol == 629) return createDt;
			if (modelCol == 630) return new Integer(createUserId);
			if (modelCol == 630) return createUserName;
			if (modelCol == 631) return updateDt;
			if (modelCol == 632) return new Integer(updateUserId);
			if (modelCol == 632) return updateUserName;
			if (modelCol == 633) return new Integer(updateCount);
			if (modelCol == 634) return new Integer(activeInd);
			if (modelCol == 635) return new Integer(systemAssignedVersionNbr);
			if (modelCol == 636) return new Integer(recordTypeRefId);
			if (modelCol == 636) return recordTypeRefDisplay;
			if (modelCol == 1645) return customText1;
			if (modelCol == 1646) return customText2;
			if (modelCol == 1647) return customText3;
			if (modelCol == 1648) return customText4;
			if (modelCol == 1649) return new Integer(custom1RefId);
			if (modelCol == 1649) return custom1RefDisplay;
			if (modelCol == 1650) return new Integer(custom2RefId);
			if (modelCol == 1650) return custom2RefDisplay;
			if (modelCol == 1651) return new Integer(custom3RefId);
			if (modelCol == 1651) return custom3RefDisplay;
			if (modelCol == 1652) return new Integer(custom4RefId);
			if (modelCol == 1652) return custom4RefDisplay;
			if (modelCol == 1653) return customDate1;
			if (modelCol == 1654) return customDate2;
			if (modelCol == 1655) return customMemo1;
			if (modelCol == 1656) return customMemo2;
			if (modelCol == 1657) return new Integer(customInt1);
			if (modelCol == 1658) return new Integer(customInt2);
			if (modelCol == 1659) return new Double(customDouble1);
		return null;
	}

	public int getModelColDatabaseDataType(int modelCol) {
			if (modelCol == 614) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 615) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 615) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 616) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 616) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 617) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 617) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 618) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 974) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 945) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 945) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 982) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 982) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1154) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 619) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 620) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 621) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 621) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 622) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 622) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 623) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 623) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 624) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 624) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 625) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 625) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 946) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 946) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1643) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1643) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1644) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 626) return DatabaseDataTypeFramework.DOUBLE;
			if (modelCol == 627) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 628) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 947) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 948) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 949) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 950) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 951) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1176) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1207) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1207) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1208) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1208) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1209) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1210) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1210) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1211) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1211) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 5354) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 5469) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 5355) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 5356) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 629) return DatabaseDataTypeFramework.DATE;
			if (modelCol == 630) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 630) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 631) return DatabaseDataTypeFramework.DATE;
			if (modelCol == 632) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 632) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 633) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 634) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 635) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 636) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 636) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1645) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1646) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1647) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1648) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1649) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1649) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1650) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1650) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1651) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1651) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1652) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1652) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1653) return DatabaseDataTypeFramework.DATE;
			if (modelCol == 1654) return DatabaseDataTypeFramework.DATE;
			if (modelCol == 1655) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1656) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1657) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1658) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1659) return DatabaseDataTypeFramework.DOUBLE;
			return DatabaseDataTypeFramework.STRING;
	}

	public boolean isNew() {
		return getArtifactId() == 0;
	}

	public Object getPrimaryValue() {
		return new Integer(getArtifactId());
	}

	public Object getDataAt(int i) {
			if (i == 0) return new Integer(artifactId);
			if (i == 1) return new Integer(productRefId);
			if (i == 2) return productRefDisplay;
			if (i == 3) return new Integer(versionRefId);
			if (i == 4) return versionRefDisplay;
			if (i == 5) return new Integer(artifactRefId);
			if (i == 6) return artifactRefDisplay;
			if (i == 7) return new Integer(artifactNbr);
			if (i == 8) return new Integer(artifactSeq);
			if (i == 9) return new Integer(artifactLevelRefId);
			if (i == 10) return artifactLevelRefDisplay;
			if (i == 11) return new Integer(componentTypeRefId);
			if (i == 12) return componentTypeRefDisplay;
			if (i == 13) return new Integer(lastUpdatedBaselineId);
			if (i == 14) return artifactName;
			if (i == 15) return description;
			if (i == 16) return new Integer(statusRefId);
			if (i == 17) return statusRefDisplay;
			if (i == 18) return new Integer(priorityRefId);
			if (i == 19) return priorityRefDisplay;
			if (i == 20) return new Integer(complexityRefId);
			if (i == 21) return complexityRefDisplay;
			if (i == 22) return new Integer(authorRefId);
			if (i == 23) return authorRefDisplay;
			if (i == 24) return new Integer(assignedRefId);
			if (i == 25) return assignedRefDisplay;
			if (i == 26) return new Integer(categoryRefId);
			if (i == 27) return categoryRefDisplay;
			if (i == 28) return new Integer(assignedUserId);
			if (i == 29) return assignedUserName;
			if (i == 30) return new Integer(assignedUserGroupId);
			if (i == 31) return new Double(effort);
			if (i == 32) return rationale;
			if (i == 33) return origin;
			if (i == 34) return goal;
			if (i == 35) return context;
			if (i == 36) return precondition;
			if (i == 37) return postcondition;
			if (i == 38) return summary;
			if (i == 39) return externalReferences;
			if (i == 40) return new Integer(plannedVersionRefId);
			if (i == 41) return plannedVersionRefDisplay;
			if (i == 42) return new Integer(lastModifiedVersionRefId);
			if (i == 43) return lastModifiedVersionRefDisplay;
			if (i == 44) return new Integer(removedInd);
			if (i == 45) return new Integer(moduleRefId);
			if (i == 46) return moduleRefDisplay;
			if (i == 47) return new Integer(originCategoryRefId);
			if (i == 48) return originCategoryRefDisplay;
			if (i == 49) return changeReason;
			if (i == 50) return changeMade;
			if (i == 51) return new Integer(reportSequence);
			if (i == 52) return reportOutline;
			if (i == 53) return createDt;
			if (i == 54) return new Integer(createUserId);
			if (i == 55) return createUserName;
			if (i == 56) return updateDt;
			if (i == 57) return new Integer(updateUserId);
			if (i == 58) return updateUserName;
			if (i == 59) return new Integer(updateCount);
			if (i == 60) return new Integer(activeInd);
			if (i == 61) return new Integer(systemAssignedVersionNbr);
			if (i == 62) return new Integer(recordTypeRefId);
			if (i == 63) return recordTypeRefDisplay;
			if (i == 64) return customText1;
			if (i == 65) return customText2;
			if (i == 66) return customText3;
			if (i == 67) return customText4;
			if (i == 68) return new Integer(custom1RefId);
			if (i == 69) return custom1RefDisplay;
			if (i == 70) return new Integer(custom2RefId);
			if (i == 71) return custom2RefDisplay;
			if (i == 72) return new Integer(custom3RefId);
			if (i == 73) return custom3RefDisplay;
			if (i == 74) return new Integer(custom4RefId);
			if (i == 75) return custom4RefDisplay;
			if (i == 76) return customDate1;
			if (i == 77) return customDate2;
			if (i == 78) return customMemo1;
			if (i == 79) return customMemo2;
			if (i == 80) return new Integer(customInt1);
			if (i == 81) return new Integer(customInt2);
			if (i == 82) return new Double(customDouble1);
		return null;
	}

	public int getDatabaseDataType(int i) {
			if (i == 0) return DatabaseDataTypeFramework.INTEGER;
			if (i == 1) return DatabaseDataTypeFramework.INTEGER;
			if (i == 2) return DatabaseDataTypeFramework.STRING;
			if (i == 3) return DatabaseDataTypeFramework.INTEGER;
			if (i == 4) return DatabaseDataTypeFramework.STRING;
			if (i == 5) return DatabaseDataTypeFramework.INTEGER;
			if (i == 6) return DatabaseDataTypeFramework.STRING;
			if (i == 7) return DatabaseDataTypeFramework.INTEGER;
			if (i == 8) return DatabaseDataTypeFramework.INTEGER;
			if (i == 9) return DatabaseDataTypeFramework.INTEGER;
			if (i == 10) return DatabaseDataTypeFramework.STRING;
			if (i == 11) return DatabaseDataTypeFramework.INTEGER;
			if (i == 12) return DatabaseDataTypeFramework.STRING;
			if (i == 13) return DatabaseDataTypeFramework.INTEGER;
			if (i == 14) return DatabaseDataTypeFramework.STRING;
			if (i == 15) return DatabaseDataTypeFramework.STRING;
			if (i == 16) return DatabaseDataTypeFramework.INTEGER;
			if (i == 17) return DatabaseDataTypeFramework.STRING;
			if (i == 18) return DatabaseDataTypeFramework.INTEGER;
			if (i == 19) return DatabaseDataTypeFramework.STRING;
			if (i == 20) return DatabaseDataTypeFramework.INTEGER;
			if (i == 21) return DatabaseDataTypeFramework.STRING;
			if (i == 22) return DatabaseDataTypeFramework.INTEGER;
			if (i == 23) return DatabaseDataTypeFramework.STRING;
			if (i == 24) return DatabaseDataTypeFramework.INTEGER;
			if (i == 25) return DatabaseDataTypeFramework.STRING;
			if (i == 26) return DatabaseDataTypeFramework.INTEGER;
			if (i == 27) return DatabaseDataTypeFramework.STRING;
			if (i == 28) return DatabaseDataTypeFramework.INTEGER;
			if (i == 29) return DatabaseDataTypeFramework.STRING;
			if (i == 30) return DatabaseDataTypeFramework.INTEGER;
			if (i == 31) return DatabaseDataTypeFramework.DOUBLE;
			if (i == 32) return DatabaseDataTypeFramework.STRING;
			if (i == 33) return DatabaseDataTypeFramework.STRING;
			if (i == 34) return DatabaseDataTypeFramework.STRING;
			if (i == 35) return DatabaseDataTypeFramework.STRING;
			if (i == 36) return DatabaseDataTypeFramework.STRING;
			if (i == 37) return DatabaseDataTypeFramework.STRING;
			if (i == 38) return DatabaseDataTypeFramework.STRING;
			if (i == 39) return DatabaseDataTypeFramework.STRING;
			if (i == 40) return DatabaseDataTypeFramework.INTEGER;
			if (i == 41) return DatabaseDataTypeFramework.STRING;
			if (i == 42) return DatabaseDataTypeFramework.INTEGER;
			if (i == 43) return DatabaseDataTypeFramework.STRING;
			if (i == 44) return DatabaseDataTypeFramework.INTEGER;
			if (i == 45) return DatabaseDataTypeFramework.INTEGER;
			if (i == 46) return DatabaseDataTypeFramework.STRING;
			if (i == 47) return DatabaseDataTypeFramework.INTEGER;
			if (i == 48) return DatabaseDataTypeFramework.STRING;
			if (i == 49) return DatabaseDataTypeFramework.STRING;
			if (i == 50) return DatabaseDataTypeFramework.STRING;
			if (i == 51) return DatabaseDataTypeFramework.INTEGER;
			if (i == 52) return DatabaseDataTypeFramework.STRING;
			if (i == 53) return DatabaseDataTypeFramework.DATE;
			if (i == 54) return DatabaseDataTypeFramework.INTEGER;
			if (i == 55) return DatabaseDataTypeFramework.STRING;
			if (i == 56) return DatabaseDataTypeFramework.DATE;
			if (i == 57) return DatabaseDataTypeFramework.INTEGER;
			if (i == 58) return DatabaseDataTypeFramework.STRING;
			if (i == 59) return DatabaseDataTypeFramework.INTEGER;
			if (i == 60) return DatabaseDataTypeFramework.INTEGER;
			if (i == 61) return DatabaseDataTypeFramework.INTEGER;
			if (i == 62) return DatabaseDataTypeFramework.INTEGER;
			if (i == 63) return DatabaseDataTypeFramework.STRING;
			if (i == 64) return DatabaseDataTypeFramework.STRING;
			if (i == 65) return DatabaseDataTypeFramework.STRING;
			if (i == 66) return DatabaseDataTypeFramework.STRING;
			if (i == 67) return DatabaseDataTypeFramework.STRING;
			if (i == 68) return DatabaseDataTypeFramework.INTEGER;
			if (i == 69) return DatabaseDataTypeFramework.STRING;
			if (i == 70) return DatabaseDataTypeFramework.INTEGER;
			if (i == 71) return DatabaseDataTypeFramework.STRING;
			if (i == 72) return DatabaseDataTypeFramework.INTEGER;
			if (i == 73) return DatabaseDataTypeFramework.STRING;
			if (i == 74) return DatabaseDataTypeFramework.INTEGER;
			if (i == 75) return DatabaseDataTypeFramework.STRING;
			if (i == 76) return DatabaseDataTypeFramework.DATE;
			if (i == 77) return DatabaseDataTypeFramework.DATE;
			if (i == 78) return DatabaseDataTypeFramework.STRING;
			if (i == 79) return DatabaseDataTypeFramework.STRING;
			if (i == 80) return DatabaseDataTypeFramework.INTEGER;
			if (i == 81) return DatabaseDataTypeFramework.INTEGER;
			if (i == 82) return DatabaseDataTypeFramework.DOUBLE;
			return DatabaseDataTypeFramework.STRING;
	}

	public void setDataAt(Object o, int i) {
		switch(i) {
			case 0: setArtifactId(((Integer) o).intValue()); break;
			case 1: setProductRefId(((Integer) o).intValue()); break;
			case 2: setProductRefDisplay((String) o); break;
			case 3: setVersionRefId(((Integer) o).intValue()); break;
			case 4: setVersionRefDisplay((String) o); break;
			case 5: setArtifactRefId(((Integer) o).intValue()); break;
			case 6: setArtifactRefDisplay((String) o); break;
			case 7: setArtifactNbr(((Integer) o).intValue()); break;
			case 8: setArtifactSeq(((Integer) o).intValue()); break;
			case 9: setArtifactLevelRefId(((Integer) o).intValue()); break;
			case 10: setArtifactLevelRefDisplay((String) o); break;
			case 11: setComponentTypeRefId(((Integer) o).intValue()); break;
			case 12: setComponentTypeRefDisplay((String) o); break;
			case 13: setLastUpdatedBaselineId(((Integer) o).intValue()); break;
			case 14: setArtifactName((String) o); break;
			case 15: setDescription((String) o); break;
			case 16: setStatusRefId(((Integer) o).intValue()); break;
			case 17: setStatusRefDisplay((String) o); break;
			case 18: setPriorityRefId(((Integer) o).intValue()); break;
			case 19: setPriorityRefDisplay((String) o); break;
			case 20: setComplexityRefId(((Integer) o).intValue()); break;
			case 21: setComplexityRefDisplay((String) o); break;
			case 22: setAuthorRefId(((Integer) o).intValue()); break;
			case 23: setAuthorRefDisplay((String) o); break;
			case 24: setAssignedRefId(((Integer) o).intValue()); break;
			case 25: setAssignedRefDisplay((String) o); break;
			case 26: setCategoryRefId(((Integer) o).intValue()); break;
			case 27: setCategoryRefDisplay((String) o); break;
			case 28: setAssignedUserId(((Integer) o).intValue()); break;
			case 29: setAssignedUserName((String) o); break;
			case 30: setAssignedUserGroupId(((Integer) o).intValue()); break;
			case 31: setEffort(((Double) o).doubleValue()); break;
			case 32: setRationale((String) o); break;
			case 33: setOrigin((String) o); break;
			case 34: setGoal((String) o); break;
			case 35: setContext((String) o); break;
			case 36: setPrecondition((String) o); break;
			case 37: setPostcondition((String) o); break;
			case 38: setSummary((String) o); break;
			case 39: setExternalReferences((String) o); break;
			case 40: setPlannedVersionRefId(((Integer) o).intValue()); break;
			case 41: setPlannedVersionRefDisplay((String) o); break;
			case 42: setLastModifiedVersionRefId(((Integer) o).intValue()); break;
			case 43: setLastModifiedVersionRefDisplay((String) o); break;
			case 44: setRemovedInd(((Integer) o).intValue()); break;
			case 45: setModuleRefId(((Integer) o).intValue()); break;
			case 46: setModuleRefDisplay((String) o); break;
			case 47: setOriginCategoryRefId(((Integer) o).intValue()); break;
			case 48: setOriginCategoryRefDisplay((String) o); break;
			case 49: setChangeReason((String) o); break;
			case 50: setChangeMade((String) o); break;
			case 51: setReportSequence(((Integer) o).intValue()); break;
			case 52: setReportOutline((String) o); break;
			case 53: setCreateDt((GregorianCalendar) o); break;
			case 54: setCreateUserId(((Integer) o).intValue()); break;
			case 55: setCreateUserName((String) o); break;
			case 56: setUpdateDt((GregorianCalendar) o); break;
			case 57: setUpdateUserId(((Integer) o).intValue()); break;
			case 58: setUpdateUserName((String) o); break;
			case 59: setUpdateCount(((Integer) o).intValue()); break;
			case 60: setActiveInd(((Integer) o).intValue()); break;
			case 61: setSystemAssignedVersionNbr(((Integer) o).intValue()); break;
			case 62: setRecordTypeRefId(((Integer) o).intValue()); break;
			case 63: setRecordTypeRefDisplay((String) o); break;
			case 64: setCustomText1((String) o); break;
			case 65: setCustomText2((String) o); break;
			case 66: setCustomText3((String) o); break;
			case 67: setCustomText4((String) o); break;
			case 68: setCustom1RefId(((Integer) o).intValue()); break;
			case 69: setCustom1RefDisplay((String) o); break;
			case 70: setCustom2RefId(((Integer) o).intValue()); break;
			case 71: setCustom2RefDisplay((String) o); break;
			case 72: setCustom3RefId(((Integer) o).intValue()); break;
			case 73: setCustom3RefDisplay((String) o); break;
			case 74: setCustom4RefId(((Integer) o).intValue()); break;
			case 75: setCustom4RefDisplay((String) o); break;
			case 76: setCustomDate1((GregorianCalendar) o); break;
			case 77: setCustomDate2((GregorianCalendar) o); break;
			case 78: setCustomMemo1((String) o); break;
			case 79: setCustomMemo2((String) o); break;
			case 80: setCustomInt1(((Integer) o).intValue()); break;
			case 81: setCustomInt2(((Integer) o).intValue()); break;
			case 82: setCustomDouble1(((Double) o).doubleValue()); break;
		}
	}

}
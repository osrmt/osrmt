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
package com.osrmt.modellibrary.reqmanager;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Vector;

import com.osframework.appclient.services.ReferenceServices;
import com.osframework.ejb.reference.common.IReferenceMap;
import com.osframework.ejb.reference.security.ISecurity;
import com.osframework.framework.exceptions.ExceptionInfo;
import com.osframework.framework.locale.AppFormats;
import com.osframework.framework.logging.Debug;
import com.osframework.framework.utility.CalendarUtility;
import com.osframework.modellibrary.common.Comparison;
import com.osframework.modellibrary.common.DbCalendar;
import com.osframework.modellibrary.common.IControlModel;
import com.osframework.modellibrary.common.ResultColumnList;
import com.osframework.modellibrary.reference.group.DatabaseDataTypeFramework;
import com.osframework.modellibrary.reference.group.SystemMessageFramework;

/**
null
*/
public class RequirementTreeHistoryDataModel implements IControlModel, java.io.Serializable {

	static final long serialVersionUID = 1L;
	private int hashsize = 18;
	private Hashtable modifiedFields = new Hashtable(hashsize);
	private boolean referenceSet = false;


	/** Unique key to reference_tree history  */
	private int requirementTreeHistoryId = 0;

	/** Foreign key to Requirement tree table  */
	private int requirementTreeId = 0;

	/** Date the history record was created  */
	private DbCalendar historyDt = DbCalendar.getDbCalendar();

	/** User who made the history record  */
	private int historyUserId = 0;

	private String historyUserName="";

	/** Foreign key to baseline  */
	private int baselineId = 0;

	/** Foreign key to child table  */
	private int childId = 0;

	/** Foreign key to reference : Artifact Child artifact ref id must be > 0 */
	private int childArtifactRefId = 0;

	private String childArtifactRefDisplay="";

	/** Foreign key to reference: Relation  */
	private int relationRefId = 0;

	private String relationRefDisplay="";

	/** Foreign key to parent table  */
	private int parentId = 0;

	/** Foreign key to reference: Artifact Parent artifact ref id must be > 0 */
	private int parentArtifactRefId = 0;

	private String parentArtifactRefDisplay="";

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
	 * Unique key to reference_tree history
	 * 
	 */ 
	public int getRequirementTreeHistoryId() {
		return requirementTreeHistoryId;
	}

	/** 
	 * Unique key to reference_tree history
	 * 
	 */ 
	public void setRequirementTreeHistoryId(int requirementTreeHistoryId) {
		if (!Comparison.areEqual(this.requirementTreeHistoryId, requirementTreeHistoryId)) {
			this.requirementTreeHistoryId = requirementTreeHistoryId;
			setModified("requirementTreeHistoryId");
		};
	}

	/** 
	 * Foreign key to Requirement tree table
	 * 
	 */ 
	public int getRequirementTreeId() {
		return requirementTreeId;
	}

	/** 
	 * Foreign key to Requirement tree table
	 * 
	 * Required database field.
	 */ 
	public void setRequirementTreeId(int requirementTreeId) {
		if (!Comparison.areEqual(this.requirementTreeId, requirementTreeId)) {
			this.requirementTreeId = requirementTreeId;
			setModified("requirementTreeId");
		};
	}

	/** 
	 * Date the history record was created
	 * 
	 */ 
	public DbCalendar getHistoryDt() {
		return historyDt;
	}

	/** 
	 * Date the history record was created
	 * 
	 */ 
	public void setHistoryDt(GregorianCalendar historyDt) {
		if (!Comparison.areEqual(this.historyDt, historyDt)) {
			this.historyDt = new DbCalendar();
			if (historyDt != null) {
				this.historyDt.setTimeInMillis(historyDt.getTimeInMillis());
			}
			setModified("historyDt");
		};
	}

	/** 
	 * Date the history record was created
	 * 
	 */ 
	public void setHistoryDt(long milliseconds) {
		if (this.historyDt==null) {
			this.historyDt = new DbCalendar();
		}
		if (!Comparison.areEqual(this.historyDt, historyDt)) {
			this.historyDt.setTimeInMillis(milliseconds);
			setModified("historyDt");
		}

	}

	/** 
	 * User who made the history record
	 * 
	 */ 
	public int getHistoryUserId() {
		return historyUserId;
	}

	/** 
	 * User who made the history record
	 * 
	 * Required database field.
	 */ 
	public void setHistoryUserId(int historyUserId) {
		if (!Comparison.areEqual(this.historyUserId, historyUserId)) {
			this.historyUserId = historyUserId;
			setModified("historyUserId");
		};
	}

	/** 
	 * User who made the history record
	 * 
	 */ 
	public String getHistoryUserName() {
		return historyUserName;
	}

	/** 
	 * User who made the history record
	 * 
	 */ 
	public void setHistoryUserName(String userName) {
		this.historyUserName = userName;
	}

	/** 
	 * Foreign key to baseline
	 * 
	 */ 
	public int getBaselineId() {
		return baselineId;
	}

	/** 
	 * Foreign key to baseline
	 * 
	 * Required database field.
	 */ 
	public void setBaselineId(int baselineId) {
		if (!Comparison.areEqual(this.baselineId, baselineId)) {
			this.baselineId = baselineId;
			setModified("baselineId");
		};
	}

	/** 
	 * Foreign key to child table
	 * 
	 */ 
	public int getChildId() {
		return childId;
	}

	/** 
	 * Foreign key to child table
	 * 
	 * Required database field.
	 */ 
	public void setChildId(int childId) {
		if (!Comparison.areEqual(this.childId, childId)) {
			this.childId = childId;
			setModified("childId");
		};
	}

	/** 
	 * Foreign key to reference : Artifact
	 * Child artifact ref id must be > 0
	 */ 
	public int getChildArtifactRefId() {
		return childArtifactRefId;
	}

	/** 
	 * Foreign key to reference : Artifact
	 * Child artifact ref id must be > 0
	 * Required database field.
	 */ 
	public void setChildArtifactRefId(int childArtifactRefId) {
		if (!Comparison.areEqual(this.childArtifactRefId, childArtifactRefId)) {
			this.childArtifactRefId = childArtifactRefId;
			setModified("childArtifactRefId");
		};
	}

	/** 
	 * Foreign key to reference : Artifact
	 * Child artifact ref id must be > 0
	 */ 
	public String getChildArtifactRefDisplay() {
		return childArtifactRefDisplay;
	}

	/** 
	 * Foreign key to reference : Artifact
	 * Child artifact ref id must be > 0
	 */ 
	public void setChildArtifactRefDisplay(String display) {
		this.childArtifactRefDisplay = display;
	}

	/** 
	 * Foreign key to reference: Relation
	 * 
	 */ 
	public int getRelationRefId() {
		return relationRefId;
	}

	/** 
	 * Foreign key to reference: Relation
	 * 
	 * Required database field.
	 */ 
	public void setRelationRefId(int relationRefId) {
		if (!Comparison.areEqual(this.relationRefId, relationRefId)) {
			this.relationRefId = relationRefId;
			setModified("relationRefId");
		};
	}

	/** 
	 * Foreign key to reference: Relation
	 * 
	 */ 
	public String getRelationRefDisplay() {
		return relationRefDisplay;
	}

	/** 
	 * Foreign key to reference: Relation
	 * 
	 */ 
	public void setRelationRefDisplay(String display) {
		this.relationRefDisplay = display;
	}

	/** 
	 * Foreign key to parent table
	 * 
	 */ 
	public int getParentId() {
		return parentId;
	}

	/** 
	 * Foreign key to parent table
	 * 
	 * Required database field.
	 */ 
	public void setParentId(int parentId) {
		if (!Comparison.areEqual(this.parentId, parentId)) {
			this.parentId = parentId;
			setModified("parentId");
		};
	}

	/** 
	 * Foreign key to reference: Artifact
	 * Parent artifact ref id must be > 0
	 */ 
	public int getParentArtifactRefId() {
		return parentArtifactRefId;
	}

	/** 
	 * Foreign key to reference: Artifact
	 * Parent artifact ref id must be > 0
	 * Required database field.
	 */ 
	public void setParentArtifactRefId(int parentArtifactRefId) {
		if (!Comparison.areEqual(this.parentArtifactRefId, parentArtifactRefId)) {
			this.parentArtifactRefId = parentArtifactRefId;
			setModified("parentArtifactRefId");
		};
	}

	/** 
	 * Foreign key to reference: Artifact
	 * Parent artifact ref id must be > 0
	 */ 
	public String getParentArtifactRefDisplay() {
		return parentArtifactRefDisplay;
	}

	/** 
	 * Foreign key to reference: Artifact
	 * Parent artifact ref id must be > 0
	 */ 
	public void setParentArtifactRefDisplay(String display) {
		this.parentArtifactRefDisplay = display;
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
		columns.addColumn("requirementTreeHistoryId", Integer.class);
		columns.addColumn("requirementTreeId", Integer.class);
		columns.addColumn("historyDt", GregorianCalendar.class);
		columns.addColumn("historyUserId", Integer.class);
		columns.addColumn("historyUserName", String.class);
		columns.addColumn("baselineId", Integer.class);
		columns.addColumn("childId", Integer.class);
		columns.addColumn("childArtifactRefId", Integer.class);
		columns.addColumn("childArtifactRefDisplay", String.class);
		columns.addColumn("relationRefId", Integer.class);
		columns.addColumn("relationRefDisplay", String.class);
		columns.addColumn("parentId", Integer.class);
		columns.addColumn("parentArtifactRefId", Integer.class);
		columns.addColumn("parentArtifactRefDisplay", String.class);
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
			if (security != null && this.historyUserId > 0) setHistoryUserName(security.getUser(this.historyUserId).getDisplayName());
			if (reference != null && this.childArtifactRefId > 0) setChildArtifactRefDisplay(reference.getDisplay(this.childArtifactRefId));
			if (reference != null && this.relationRefId > 0) setRelationRefDisplay(reference.getDisplay(this.relationRefId));
			if (reference != null && this.parentArtifactRefId > 0) setParentArtifactRefDisplay(reference.getDisplay(this.parentArtifactRefId));
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
	public void copyModifiedTo(RequirementTreeHistoryDataModel m) {
		Enumeration e1 = modifiedFields.keys();
		while (e1.hasMoreElements()) {
			String fieldName = (String) e1.nextElement();

			if (fieldName.compareTo("requirementTreeHistoryId")==0)
				m.setRequirementTreeHistoryId(this.getRequirementTreeHistoryId());
			else if (fieldName.compareTo("requirementTreeId")==0)
				m.setRequirementTreeId(this.getRequirementTreeId());
			else if (fieldName.compareTo("historyDt")==0)
				m.setHistoryDt(this.getHistoryDt());
			else if (fieldName.compareTo("historyUserId")==0)
				m.setHistoryUserId(this.getHistoryUserId());
			else if (fieldName.compareTo("baselineId")==0)
				m.setBaselineId(this.getBaselineId());
			else if (fieldName.compareTo("childId")==0)
				m.setChildId(this.getChildId());
			else if (fieldName.compareTo("childArtifactRefId")==0)
				m.setChildArtifactRefId(this.getChildArtifactRefId());
			else if (fieldName.compareTo("relationRefId")==0)
				m.setRelationRefId(this.getRelationRefId());
			else if (fieldName.compareTo("parentId")==0)
				m.setParentId(this.getParentId());
			else if (fieldName.compareTo("parentArtifactRefId")==0)
				m.setParentArtifactRefId(this.getParentArtifactRefId());
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
	public void updateWith (RequirementTreeHistoryModel m) {

		this.setRequirementTreeHistoryId(m.getRequirementTreeHistoryId());
		this.setRequirementTreeId(m.getRequirementTreeId());
		this.setHistoryDt(m.getHistoryDt());
		this.setHistoryUserId(m.getHistoryUserId());
		this.setHistoryUserName(m.getHistoryUserName());
		this.setBaselineId(m.getBaselineId());
		this.setChildId(m.getChildId());
		this.setChildArtifactRefId(m.getChildArtifactRefId());
		this.setChildArtifactRefDisplay(m.getChildArtifactRefDisplay());
		this.setRelationRefId(m.getRelationRefId());
		this.setRelationRefDisplay(m.getRelationRefDisplay());
		this.setParentId(m.getParentId());
		this.setParentArtifactRefId(m.getParentArtifactRefId());
		this.setParentArtifactRefDisplay(m.getParentArtifactRefDisplay());
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

	public void updateWith (RequirementTreeModel m) {

		this.setRequirementTreeId(m.getRequirementTreeId());
		this.setChildId(m.getChildId());
		this.setChildArtifactRefId(m.getChildArtifactRefId());
		this.setRelationRefId(m.getRelationRefId());
		this.setParentId(m.getParentId());
		this.setParentArtifactRefId(m.getParentArtifactRefId());
		this.setCreateDt(m.getCreateDt());
		this.setCreateUserId(m.getCreateUserId());
		this.setUpdateDt(m.getUpdateDt());
		this.setUpdateUserId(m.getUpdateUserId());
		this.setUpdateCount(m.getUpdateCount());
		this.setActiveInd(m.getActiveInd());
		this.setSystemAssignedVersionNbr(m.getSystemAssignedVersionNbr());
		this.setRecordTypeRefId(m.getRecordTypeRefId());
	}

	/**
	 * Compare the two objects
	 */
	public boolean isEqualTo (RequirementTreeHistoryModel m) {

		if (!Comparison.areEqual(this.getRequirementTreeHistoryId(),  m.getRequirementTreeHistoryId())) return false;
		if (!Comparison.areEqual(this.getRequirementTreeId(),  m.getRequirementTreeId())) return false;
		if (!Comparison.areEqual(this.getHistoryDt(),  m.getHistoryDt())) return false;
		if (!Comparison.areEqual(this.getHistoryUserId(),  m.getHistoryUserId())) return false;
		if (!Comparison.areEqual(this.getBaselineId(),  m.getBaselineId())) return false;
		if (!Comparison.areEqual(this.getChildId(),  m.getChildId())) return false;
		if (!Comparison.areEqual(this.getChildArtifactRefId(),  m.getChildArtifactRefId())) return false;
		if (!Comparison.areEqual(this.getRelationRefId(),  m.getRelationRefId())) return false;
		if (!Comparison.areEqual(this.getParentId(),  m.getParentId())) return false;
		if (!Comparison.areEqual(this.getParentArtifactRefId(),  m.getParentArtifactRefId())) return false;
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
			sb.append("RequirementTreeHistoryModel:\r\n");
			sb.append("requirement_tree_history_id:" + getRequirementTreeHistoryId());
			sb.append("\r\n");
			sb.append("requirement_tree_id:" + getRequirementTreeId());
			sb.append("\r\n");
			sb.append("history_dt:" + CalendarUtility.Format(getHistoryDt(),AppFormats.getLongDateTimeFormat()));
			sb.append("\r\n");
			sb.append("history_user_id:" + getHistoryUserId());
			sb.append("\r\n");
			sb.append("history_user_name:" + getHistoryUserName());
			sb.append("\r\n");
			sb.append("baseline_id:" + getBaselineId());
			sb.append("\r\n");
			sb.append("child_id:" + getChildId());
			sb.append("\r\n");
			sb.append("child_artifact_ref_id:" + getChildArtifactRefId());
			sb.append("\r\n");
			sb.append("child_artifact_ref_display:" + getChildArtifactRefDisplay());
			sb.append("\r\n");
			sb.append("relation_ref_id:" + getRelationRefId());
			sb.append("\r\n");
			sb.append("relation_ref_display:" + getRelationRefDisplay());
			sb.append("\r\n");
			sb.append("parent_id:" + getParentId());
			sb.append("\r\n");
			sb.append("parent_artifact_ref_id:" + getParentArtifactRefId());
			sb.append("\r\n");
			sb.append("parent_artifact_ref_display:" + getParentArtifactRefDisplay());
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
			return "RequirementTreeHistoryModel:"; 
		}
	}
	public void setModelColDataAt(Object o, int modelCol) {
			switch (modelCol) {
			case 5490: setRequirementTreeHistoryId(((Integer) o).intValue()); break;
			case 5491: setRequirementTreeId(((Integer) o).intValue()); break;
			case 5492: setHistoryDt((GregorianCalendar) o); break;
			case 5493: setHistoryUserId(((Integer) o).intValue()); break;
			case 5536: setHistoryUserName((String) o); break;
			case 5494: setBaselineId(((Integer) o).intValue()); break;
			case 5495: setChildId(((Integer) o).intValue()); break;
			case 5496: setChildArtifactRefId(((Integer) o).intValue()); break;
			case 5497: setRelationRefId(((Integer) o).intValue()); break;
			case 5498: setParentId(((Integer) o).intValue()); break;
			case 5499: setParentArtifactRefId(((Integer) o).intValue()); break;
			case 5500: setCreateDt((GregorianCalendar) o); break;
			case 5501: setCreateUserId(((Integer) o).intValue()); break;
			case 5540: setCreateUserName((String) o); break;
			case 5502: setUpdateDt((GregorianCalendar) o); break;
			case 5503: setUpdateUserId(((Integer) o).intValue()); break;
			case 5541: setUpdateUserName((String) o); break;
			case 5504: setUpdateCount(((Integer) o).intValue()); break;
			case 5505: setActiveInd(((Integer) o).intValue()); break;
			case 5506: setSystemAssignedVersionNbr(((Integer) o).intValue()); break;
			case 5507: setRecordTypeRefId(((Integer) o).intValue()); break;
			default: Debug.LogWarning(this, ReferenceServices.getMsg(SystemMessageFramework.SWITCHSTATEMENTFAILED) + " modelColRefId: " + modelCol);
	} 
}

	public Object getModelColDataAt(int modelCol) {
			if (modelCol == 5490) return new Integer(requirementTreeHistoryId);
			if (modelCol == 5491) return new Integer(requirementTreeId);
			if (modelCol == 5492) return historyDt;
			if (modelCol == 5493) return new Integer(historyUserId);
			if (modelCol == 5493) return historyUserName;
			if (modelCol == 5494) return new Integer(baselineId);
			if (modelCol == 5495) return new Integer(childId);
			if (modelCol == 5496) return new Integer(childArtifactRefId);
			if (modelCol == 5496) return childArtifactRefDisplay;
			if (modelCol == 5497) return new Integer(relationRefId);
			if (modelCol == 5497) return relationRefDisplay;
			if (modelCol == 5498) return new Integer(parentId);
			if (modelCol == 5499) return new Integer(parentArtifactRefId);
			if (modelCol == 5499) return parentArtifactRefDisplay;
			if (modelCol == 5500) return createDt;
			if (modelCol == 5501) return new Integer(createUserId);
			if (modelCol == 5501) return createUserName;
			if (modelCol == 5502) return updateDt;
			if (modelCol == 5503) return new Integer(updateUserId);
			if (modelCol == 5503) return updateUserName;
			if (modelCol == 5504) return new Integer(updateCount);
			if (modelCol == 5505) return new Integer(activeInd);
			if (modelCol == 5506) return new Integer(systemAssignedVersionNbr);
			if (modelCol == 5507) return new Integer(recordTypeRefId);
			if (modelCol == 5507) return recordTypeRefDisplay;
		return null;
	}

	public int getModelColDatabaseDataType(int modelCol) {
			if (modelCol == 5490) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 5491) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 5492) return DatabaseDataTypeFramework.DATE;
			if (modelCol == 5493) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 5493) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 5494) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 5495) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 5496) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 5496) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 5497) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 5497) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 5498) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 5499) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 5499) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 5500) return DatabaseDataTypeFramework.DATE;
			if (modelCol == 5501) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 5501) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 5502) return DatabaseDataTypeFramework.DATE;
			if (modelCol == 5503) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 5503) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 5504) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 5505) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 5506) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 5507) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 5507) return DatabaseDataTypeFramework.STRING;
			return DatabaseDataTypeFramework.STRING;
	}

	public boolean isNew() {
		return getRequirementTreeHistoryId() == 0;
	}

	public Object getPrimaryValue() {
		return new Integer(getRequirementTreeHistoryId());
	}

	public Object getDataAt(int i) {
			if (i == 0) return new Integer(requirementTreeHistoryId);
			if (i == 1) return new Integer(requirementTreeId);
			if (i == 2) return historyDt;
			if (i == 3) return new Integer(historyUserId);
			if (i == 4) return historyUserName;
			if (i == 5) return new Integer(baselineId);
			if (i == 6) return new Integer(childId);
			if (i == 7) return new Integer(childArtifactRefId);
			if (i == 8) return childArtifactRefDisplay;
			if (i == 9) return new Integer(relationRefId);
			if (i == 10) return relationRefDisplay;
			if (i == 11) return new Integer(parentId);
			if (i == 12) return new Integer(parentArtifactRefId);
			if (i == 13) return parentArtifactRefDisplay;
			if (i == 14) return createDt;
			if (i == 15) return new Integer(createUserId);
			if (i == 16) return createUserName;
			if (i == 17) return updateDt;
			if (i == 18) return new Integer(updateUserId);
			if (i == 19) return updateUserName;
			if (i == 20) return new Integer(updateCount);
			if (i == 21) return new Integer(activeInd);
			if (i == 22) return new Integer(systemAssignedVersionNbr);
			if (i == 23) return new Integer(recordTypeRefId);
			if (i == 24) return recordTypeRefDisplay;
		return null;
	}

	public int getDatabaseDataType(int i) {
			if (i == 0) return DatabaseDataTypeFramework.INTEGER;
			if (i == 1) return DatabaseDataTypeFramework.INTEGER;
			if (i == 2) return DatabaseDataTypeFramework.DATE;
			if (i == 3) return DatabaseDataTypeFramework.INTEGER;
			if (i == 4) return DatabaseDataTypeFramework.STRING;
			if (i == 5) return DatabaseDataTypeFramework.INTEGER;
			if (i == 6) return DatabaseDataTypeFramework.INTEGER;
			if (i == 7) return DatabaseDataTypeFramework.INTEGER;
			if (i == 8) return DatabaseDataTypeFramework.STRING;
			if (i == 9) return DatabaseDataTypeFramework.INTEGER;
			if (i == 10) return DatabaseDataTypeFramework.STRING;
			if (i == 11) return DatabaseDataTypeFramework.INTEGER;
			if (i == 12) return DatabaseDataTypeFramework.INTEGER;
			if (i == 13) return DatabaseDataTypeFramework.STRING;
			if (i == 14) return DatabaseDataTypeFramework.DATE;
			if (i == 15) return DatabaseDataTypeFramework.INTEGER;
			if (i == 16) return DatabaseDataTypeFramework.STRING;
			if (i == 17) return DatabaseDataTypeFramework.DATE;
			if (i == 18) return DatabaseDataTypeFramework.INTEGER;
			if (i == 19) return DatabaseDataTypeFramework.STRING;
			if (i == 20) return DatabaseDataTypeFramework.INTEGER;
			if (i == 21) return DatabaseDataTypeFramework.INTEGER;
			if (i == 22) return DatabaseDataTypeFramework.INTEGER;
			if (i == 23) return DatabaseDataTypeFramework.INTEGER;
			if (i == 24) return DatabaseDataTypeFramework.STRING;
			return DatabaseDataTypeFramework.STRING;
	}

	public void setDataAt(Object o, int i) {
		switch(i) {
			case 0: setRequirementTreeHistoryId(((Integer) o).intValue()); break;
			case 1: setRequirementTreeId(((Integer) o).intValue()); break;
			case 2: setHistoryDt((GregorianCalendar) o); break;
			case 3: setHistoryUserId(((Integer) o).intValue()); break;
			case 4: setHistoryUserName((String) o); break;
			case 5: setBaselineId(((Integer) o).intValue()); break;
			case 6: setChildId(((Integer) o).intValue()); break;
			case 7: setChildArtifactRefId(((Integer) o).intValue()); break;
			case 8: setChildArtifactRefDisplay((String) o); break;
			case 9: setRelationRefId(((Integer) o).intValue()); break;
			case 10: setRelationRefDisplay((String) o); break;
			case 11: setParentId(((Integer) o).intValue()); break;
			case 12: setParentArtifactRefId(((Integer) o).intValue()); break;
			case 13: setParentArtifactRefDisplay((String) o); break;
			case 14: setCreateDt((GregorianCalendar) o); break;
			case 15: setCreateUserId(((Integer) o).intValue()); break;
			case 16: setCreateUserName((String) o); break;
			case 17: setUpdateDt((GregorianCalendar) o); break;
			case 18: setUpdateUserId(((Integer) o).intValue()); break;
			case 19: setUpdateUserName((String) o); break;
			case 20: setUpdateCount(((Integer) o).intValue()); break;
			case 21: setActiveInd(((Integer) o).intValue()); break;
			case 22: setSystemAssignedVersionNbr(((Integer) o).intValue()); break;
			case 23: setRecordTypeRefId(((Integer) o).intValue()); break;
			case 24: setRecordTypeRefDisplay((String) o); break;
		}
	}

}
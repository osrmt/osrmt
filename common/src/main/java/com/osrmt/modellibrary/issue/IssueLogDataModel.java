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
public class IssueLogDataModel implements IControlModel, java.io.Serializable {

	static final long serialVersionUID = 1L;
	private int hashsize = 14;
	private Hashtable modifiedFields = new Hashtable(hashsize);
	private boolean referenceSet = false;


	/** Unique identifier to issue log table  */
	private int issueLogId = 0;

	/** Foreign key to issue table  */
	private int issueId = 0;

	/** Date/time this log took place  */
	private DbCalendar actionDt;

	/** Text description of action e.g. assigned to [user]  */
	private String actionText;

	/** Description of the issue update  */
	private String description;

	/** User who executed this action  */
	private int actionUserId = 0;

	private String actionUserName="";

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
	 * Unique identifier to issue log table
	 * 
	 */ 
	public int getIssueLogId() {
		return issueLogId;
	}

	/** 
	 * Unique identifier to issue log table
	 * 
	 */ 
	public void setIssueLogId(int issueLogId) {
		if (!Comparison.areEqual(this.issueLogId, issueLogId)) {
			this.issueLogId = issueLogId;
			setModified("issueLogId");
		};
	}

	/** 
	 * Foreign key to issue table
	 * 
	 */ 
	public int getIssueId() {
		return issueId;
	}

	/** 
	 * Foreign key to issue table
	 * 
	 * Required database field.
	 */ 
	public void setIssueId(int issueId) {
		if (!Comparison.areEqual(this.issueId, issueId)) {
			this.issueId = issueId;
			setModified("issueId");
		};
	}

	/** 
	 * Date/time this log took place
	 * 
	 */ 
	public DbCalendar getActionDt() {
		return actionDt;
	}

	/** 
	 * Date/time this log took place
	 * 
	 */ 
	public void setActionDt(GregorianCalendar actionDt) {
		if (!Comparison.areEqual(this.actionDt, actionDt)) {
			this.actionDt = new DbCalendar();
			if (actionDt != null) {
				this.actionDt.setTimeInMillis(actionDt.getTimeInMillis());
			}
			setModified("actionDt");
		};
	}

	/** 
	 * Date/time this log took place
	 * 
	 */ 
	public void setActionDt(long milliseconds) {
		if (this.actionDt==null) {
			this.actionDt = new DbCalendar();
		}
		if (!Comparison.areEqual(this.actionDt, actionDt)) {
			this.actionDt.setTimeInMillis(milliseconds);
			setModified("actionDt");
		}

	}

	/** 
	 * Text description of action e.g. assigned to [user]
	 * 
	 */ 
	public String getActionText() {
		return actionText;
	}

	/** 
	 * Text description of action e.g. assigned to [user]
	 * 
	 * Required database field.
	 */ 
	public void setActionText(String actionText) {
		if (!Comparison.areEqual(this.actionText, actionText)) {
			this.actionText = actionText;
			setModified("actionText");
		};
	}

	/** 
	 * Description of the issue update
	 * 
	 */ 
	public String getDescription() {
		return description;
	}

	/** 
	 * Description of the issue update
	 * 
	 */ 
	public void setDescription(String description) {
		if (!Comparison.areEqual(this.description, description)) {
			this.description = description;
			setModified("description");
		};
	}

	/** 
	 * User who executed this action
	 * 
	 */ 
	public int getActionUserId() {
		return actionUserId;
	}

	/** 
	 * User who executed this action
	 * 
	 * Required database field.
	 */ 
	public void setActionUserId(int actionUserId) {
		if (!Comparison.areEqual(this.actionUserId, actionUserId)) {
			this.actionUserId = actionUserId;
			setModified("actionUserId");
		};
	}

	/** 
	 * User who executed this action
	 * 
	 */ 
	public String getActionUserName() {
		return actionUserName;
	}

	/** 
	 * User who executed this action
	 * 
	 */ 
	public void setActionUserName(String userName) {
		this.actionUserName = userName;
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
		columns.addColumn("issueLogId", Integer.class);
		columns.addColumn("issueId", Integer.class);
		columns.addColumn("actionDt", GregorianCalendar.class);
		columns.addColumn("actionText", String.class);
		columns.addColumn("description", String.class);
		columns.addColumn("actionUserId", Integer.class);
		columns.addColumn("actionUserName", String.class);
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
			if (security != null && this.actionUserId > 0) setActionUserName(security.getUser(this.actionUserId).getDisplayName());
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
	public void copyModifiedTo(IssueLogDataModel m) {
		Enumeration e1 = modifiedFields.keys();
		while (e1.hasMoreElements()) {
			String fieldName = (String) e1.nextElement();

			if (fieldName.compareTo("issueLogId")==0)
				m.setIssueLogId(this.getIssueLogId());
			else if (fieldName.compareTo("issueId")==0)
				m.setIssueId(this.getIssueId());
			else if (fieldName.compareTo("actionDt")==0)
				m.setActionDt(this.getActionDt());
			else if (fieldName.compareTo("actionText")==0)
				m.setActionText(this.getActionText());
			else if (fieldName.compareTo("description")==0)
				m.setDescription(this.getDescription());
			else if (fieldName.compareTo("actionUserId")==0)
				m.setActionUserId(this.getActionUserId());
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
	public void updateWith (IssueLogModel m) {

		this.setIssueLogId(m.getIssueLogId());
		this.setIssueId(m.getIssueId());
		this.setActionDt(m.getActionDt());
		this.setActionText(m.getActionText());
		this.setDescription(m.getDescription());
		this.setActionUserId(m.getActionUserId());
		this.setActionUserName(m.getActionUserName());
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
	public boolean isEqualTo (IssueLogModel m) {

		if (!Comparison.areEqual(this.getIssueLogId(),  m.getIssueLogId())) return false;
		if (!Comparison.areEqual(this.getIssueId(),  m.getIssueId())) return false;
		if (!Comparison.areEqual(this.getActionDt(),  m.getActionDt())) return false;
		if (!Comparison.areEqual(this.getActionText(),  m.getActionText())) return false;
		if (!Comparison.areEqual(this.getDescription(),  m.getDescription())) return false;
		if (!Comparison.areEqual(this.getActionUserId(),  m.getActionUserId())) return false;
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
		if (getActionText()== null) v.add("actionText");
		return v;
	}
	public String toString() {
		try {
			StringBuffer sb = new StringBuffer(1024);
			sb.append("IssueLogModel:\r\n");
			sb.append("issue_log_id:" + getIssueLogId());
			sb.append("\r\n");
			sb.append("issue_id:" + getIssueId());
			sb.append("\r\n");
			sb.append("action_dt:" + CalendarUtility.Format(getActionDt(),AppFormats.getLongDateTimeFormat()));
			sb.append("\r\n");
			sb.append("action_text:" + getActionText());
			sb.append("\r\n");
			sb.append("description:" + getDescription());
			sb.append("\r\n");
			sb.append("action_user_id:" + getActionUserId());
			sb.append("\r\n");
			sb.append("action_user_name:" + getActionUserName());
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
			return "IssueLogModel:"; 
		}
	}
	public void setModelColDataAt(Object o, int modelCol) {
			switch (modelCol) {
			case 1300: setIssueLogId(((Integer) o).intValue()); break;
			case 1301: setIssueId(((Integer) o).intValue()); break;
			case 1302: setActionDt((GregorianCalendar) o); break;
			case 1303: setActionText((String) o); break;
			case 1304: setDescription((String) o); break;
			case 1305: setActionUserId(((Integer) o).intValue()); break;
			case 1333: setActionUserName((String) o); break;
			case 1306: setCreateDt((GregorianCalendar) o); break;
			case 1307: setCreateUserId(((Integer) o).intValue()); break;
			case 1334: setCreateUserName((String) o); break;
			case 1308: setUpdateDt((GregorianCalendar) o); break;
			case 1309: setUpdateUserId(((Integer) o).intValue()); break;
			case 1335: setUpdateUserName((String) o); break;
			case 1310: setUpdateCount(((Integer) o).intValue()); break;
			case 1311: setActiveInd(((Integer) o).intValue()); break;
			case 1312: setSystemAssignedVersionNbr(((Integer) o).intValue()); break;
			case 1313: setRecordTypeRefId(((Integer) o).intValue()); break;
			default: Debug.LogWarning(this, ReferenceServices.getMsg(SystemMessageFramework.SWITCHSTATEMENTFAILED) + " modelColRefId: " + modelCol);
	} 
}

	public Object getModelColDataAt(int modelCol) {
			if (modelCol == 1300) return new Integer(issueLogId);
			if (modelCol == 1301) return new Integer(issueId);
			if (modelCol == 1302) return actionDt;
			if (modelCol == 1303) return actionText;
			if (modelCol == 1304) return description;
			if (modelCol == 1305) return new Integer(actionUserId);
			if (modelCol == 1305) return actionUserName;
			if (modelCol == 1306) return createDt;
			if (modelCol == 1307) return new Integer(createUserId);
			if (modelCol == 1307) return createUserName;
			if (modelCol == 1308) return updateDt;
			if (modelCol == 1309) return new Integer(updateUserId);
			if (modelCol == 1309) return updateUserName;
			if (modelCol == 1310) return new Integer(updateCount);
			if (modelCol == 1311) return new Integer(activeInd);
			if (modelCol == 1312) return new Integer(systemAssignedVersionNbr);
			if (modelCol == 1313) return new Integer(recordTypeRefId);
			if (modelCol == 1313) return recordTypeRefDisplay;
		return null;
	}

	public int getModelColDatabaseDataType(int modelCol) {
			if (modelCol == 1300) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1301) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1302) return DatabaseDataTypeFramework.DATE;
			if (modelCol == 1303) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1304) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1305) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1305) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1306) return DatabaseDataTypeFramework.DATE;
			if (modelCol == 1307) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1307) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1308) return DatabaseDataTypeFramework.DATE;
			if (modelCol == 1309) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1309) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1310) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1311) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1312) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1313) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1313) return DatabaseDataTypeFramework.STRING;
			return DatabaseDataTypeFramework.STRING;
	}

	public boolean isNew() {
		return getIssueLogId() == 0;
	}

	public Object getPrimaryValue() {
		return new Integer(getIssueLogId());
	}

	public Object getDataAt(int i) {
			if (i == 0) return new Integer(issueLogId);
			if (i == 1) return new Integer(issueId);
			if (i == 2) return actionDt;
			if (i == 3) return actionText;
			if (i == 4) return description;
			if (i == 5) return new Integer(actionUserId);
			if (i == 6) return actionUserName;
			if (i == 7) return createDt;
			if (i == 8) return new Integer(createUserId);
			if (i == 9) return createUserName;
			if (i == 10) return updateDt;
			if (i == 11) return new Integer(updateUserId);
			if (i == 12) return updateUserName;
			if (i == 13) return new Integer(updateCount);
			if (i == 14) return new Integer(activeInd);
			if (i == 15) return new Integer(systemAssignedVersionNbr);
			if (i == 16) return new Integer(recordTypeRefId);
			if (i == 17) return recordTypeRefDisplay;
		return null;
	}

	public int getDatabaseDataType(int i) {
			if (i == 0) return DatabaseDataTypeFramework.INTEGER;
			if (i == 1) return DatabaseDataTypeFramework.INTEGER;
			if (i == 2) return DatabaseDataTypeFramework.DATE;
			if (i == 3) return DatabaseDataTypeFramework.STRING;
			if (i == 4) return DatabaseDataTypeFramework.STRING;
			if (i == 5) return DatabaseDataTypeFramework.INTEGER;
			if (i == 6) return DatabaseDataTypeFramework.STRING;
			if (i == 7) return DatabaseDataTypeFramework.DATE;
			if (i == 8) return DatabaseDataTypeFramework.INTEGER;
			if (i == 9) return DatabaseDataTypeFramework.STRING;
			if (i == 10) return DatabaseDataTypeFramework.DATE;
			if (i == 11) return DatabaseDataTypeFramework.INTEGER;
			if (i == 12) return DatabaseDataTypeFramework.STRING;
			if (i == 13) return DatabaseDataTypeFramework.INTEGER;
			if (i == 14) return DatabaseDataTypeFramework.INTEGER;
			if (i == 15) return DatabaseDataTypeFramework.INTEGER;
			if (i == 16) return DatabaseDataTypeFramework.INTEGER;
			if (i == 17) return DatabaseDataTypeFramework.STRING;
			return DatabaseDataTypeFramework.STRING;
	}

	public void setDataAt(Object o, int i) {
		switch(i) {
			case 0: setIssueLogId(((Integer) o).intValue()); break;
			case 1: setIssueId(((Integer) o).intValue()); break;
			case 2: setActionDt((GregorianCalendar) o); break;
			case 3: setActionText((String) o); break;
			case 4: setDescription((String) o); break;
			case 5: setActionUserId(((Integer) o).intValue()); break;
			case 6: setActionUserName((String) o); break;
			case 7: setCreateDt((GregorianCalendar) o); break;
			case 8: setCreateUserId(((Integer) o).intValue()); break;
			case 9: setCreateUserName((String) o); break;
			case 10: setUpdateDt((GregorianCalendar) o); break;
			case 11: setUpdateUserId(((Integer) o).intValue()); break;
			case 12: setUpdateUserName((String) o); break;
			case 13: setUpdateCount(((Integer) o).intValue()); break;
			case 14: setActiveInd(((Integer) o).intValue()); break;
			case 15: setSystemAssignedVersionNbr(((Integer) o).intValue()); break;
			case 16: setRecordTypeRefId(((Integer) o).intValue()); break;
			case 17: setRecordTypeRefDisplay((String) o); break;
		}
	}

}
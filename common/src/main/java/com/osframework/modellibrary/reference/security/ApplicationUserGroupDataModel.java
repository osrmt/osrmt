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
package com.osframework.modellibrary.reference.security;
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
public class ApplicationUserGroupDataModel implements IControlModel, java.io.Serializable {

	static final long serialVersionUID = 1L;
	private int hashsize = 12;
	private Hashtable modifiedFields = new Hashtable(hashsize);
	private boolean referenceSet = false;


	/** Unique identifier for the user group table  */
	private int applicationUserGroupId = 0;

	/** User foreign key to application user table  */
	private int userId = 0;

	/** User group foreign key to reference table  */
	private int userGroupRefId = 0;

	private String userGroupRefDisplay="";

	/** Distribution list  */
	private String email;

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
	 * Unique identifier for the user group table
	 * 
	 */ 
	public int getApplicationUserGroupId() {
		return applicationUserGroupId;
	}

	/** 
	 * Unique identifier for the user group table
	 * 
	 */ 
	public void setApplicationUserGroupId(int applicationUserGroupId) {
		if (!Comparison.areEqual(this.applicationUserGroupId, applicationUserGroupId)) {
			this.applicationUserGroupId = applicationUserGroupId;
			setModified("applicationUserGroupId");
		};
	}

	/** 
	 * User foreign key to application user table
	 * 
	 */ 
	public int getUserId() {
		return userId;
	}

	/** 
	 * User foreign key to application user table
	 * 
	 * Required database field.
	 */ 
	public void setUserId(int userId) {
		if (!Comparison.areEqual(this.userId, userId)) {
			this.userId = userId;
			setModified("userId");
		};
	}

	/** 
	 * User group foreign key to reference table
	 * 
	 */ 
	public int getUserGroupRefId() {
		return userGroupRefId;
	}

	/** 
	 * User group foreign key to reference table
	 * 
	 * Required database field.
	 */ 
	public void setUserGroupRefId(int userGroupRefId) {
		if (!Comparison.areEqual(this.userGroupRefId, userGroupRefId)) {
			this.userGroupRefId = userGroupRefId;
			setModified("userGroupRefId");
		};
	}

	/** 
	 * User group foreign key to reference table
	 * 
	 */ 
	public String getUserGroupRefDisplay() {
		return userGroupRefDisplay;
	}

	/** 
	 * User group foreign key to reference table
	 * 
	 */ 
	public void setUserGroupRefDisplay(String display) {
		this.userGroupRefDisplay = display;
	}

	/** 
	 * Distribution list
	 * 
	 */ 
	public String getEmail() {
		return email;
	}

	/** 
	 * Distribution list
	 * 
	 */ 
	public void setEmail(String email) {
		if (!Comparison.areEqual(this.email, email)) {
			this.email = email;
			setModified("email");
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


public static ResultColumnList getResultColumnList() {
	ResultColumnList columns = new ResultColumnList();
		columns.addColumn("applicationUserGroupId", Integer.class);
		columns.addColumn("userId", Integer.class);
		columns.addColumn("userGroupRefId", Integer.class);
		columns.addColumn("userGroupRefDisplay", String.class);
		columns.addColumn("email", String.class);
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
			if (reference != null && this.userGroupRefId > 0) setUserGroupRefDisplay(reference.getDisplay(this.userGroupRefId));
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
	public void copyModifiedTo(ApplicationUserGroupDataModel m) {
		Enumeration e1 = modifiedFields.keys();
		while (e1.hasMoreElements()) {
			String fieldName = (String) e1.nextElement();

			if (fieldName.compareTo("applicationUserGroupId")==0)
				m.setApplicationUserGroupId(this.getApplicationUserGroupId());
			else if (fieldName.compareTo("userId")==0)
				m.setUserId(this.getUserId());
			else if (fieldName.compareTo("userGroupRefId")==0)
				m.setUserGroupRefId(this.getUserGroupRefId());
			else if (fieldName.compareTo("email")==0)
				m.setEmail(this.getEmail());
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
	public void updateWith (ApplicationUserGroupModel m) {

		this.setApplicationUserGroupId(m.getApplicationUserGroupId());
		this.setUserId(m.getUserId());
		this.setUserGroupRefId(m.getUserGroupRefId());
		this.setUserGroupRefDisplay(m.getUserGroupRefDisplay());
		this.setEmail(m.getEmail());
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
	public boolean isEqualTo (ApplicationUserGroupModel m) {

		if (!Comparison.areEqual(this.getApplicationUserGroupId(),  m.getApplicationUserGroupId())) return false;
		if (!Comparison.areEqual(this.getUserId(),  m.getUserId())) return false;
		if (!Comparison.areEqual(this.getUserGroupRefId(),  m.getUserGroupRefId())) return false;
		if (!Comparison.areEqual(this.getEmail(),  m.getEmail())) return false;
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
			sb.append("ApplicationUserGroupModel:\r\n");
			sb.append("application_user_group_id:" + getApplicationUserGroupId());
			sb.append("\r\n");
			sb.append("user_id:" + getUserId());
			sb.append("\r\n");
			sb.append("user_group_ref_id:" + getUserGroupRefId());
			sb.append("\r\n");
			sb.append("user_group_ref_display:" + getUserGroupRefDisplay());
			sb.append("\r\n");
			sb.append("email:" + getEmail());
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
			return "ApplicationUserGroupModel:"; 
		}
	}
	public void setModelColDataAt(Object o, int modelCol) {
			switch (modelCol) {
			case 1409: setApplicationUserGroupId(((Integer) o).intValue()); break;
			case 1410: setUserId(((Integer) o).intValue()); break;
			case 1411: setUserGroupRefId(((Integer) o).intValue()); break;
			case 1412: setEmail((String) o); break;
			case 1413: setCreateDt((GregorianCalendar) o); break;
			case 1414: setCreateUserId(((Integer) o).intValue()); break;
			case 1422: setCreateUserName((String) o); break;
			case 1415: setUpdateDt((GregorianCalendar) o); break;
			case 1416: setUpdateUserId(((Integer) o).intValue()); break;
			case 1423: setUpdateUserName((String) o); break;
			case 1417: setUpdateCount(((Integer) o).intValue()); break;
			case 1418: setActiveInd(((Integer) o).intValue()); break;
			case 1419: setSystemAssignedVersionNbr(((Integer) o).intValue()); break;
			case 1420: setRecordTypeRefId(((Integer) o).intValue()); break;
			default: Debug.LogWarning(this, ReferenceServices.getMsg(SystemMessageFramework.SWITCHSTATEMENTFAILED) + " modelColRefId: " + modelCol);
	} 
}

	public Object getModelColDataAt(int modelCol) {
			if (modelCol == 1409) return new Integer(applicationUserGroupId);
			if (modelCol == 1410) return new Integer(userId);
			if (modelCol == 1411) return new Integer(userGroupRefId);
			if (modelCol == 1411) return userGroupRefDisplay;
			if (modelCol == 1412) return email;
			if (modelCol == 1413) return createDt;
			if (modelCol == 1414) return new Integer(createUserId);
			if (modelCol == 1414) return createUserName;
			if (modelCol == 1415) return updateDt;
			if (modelCol == 1416) return new Integer(updateUserId);
			if (modelCol == 1416) return updateUserName;
			if (modelCol == 1417) return new Integer(updateCount);
			if (modelCol == 1418) return new Integer(activeInd);
			if (modelCol == 1419) return new Integer(systemAssignedVersionNbr);
			if (modelCol == 1420) return new Integer(recordTypeRefId);
			if (modelCol == 1420) return recordTypeRefDisplay;
		return null;
	}

	public int getModelColDatabaseDataType(int modelCol) {
			if (modelCol == 1409) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1410) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1411) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1411) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1412) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1413) return DatabaseDataTypeFramework.DATE;
			if (modelCol == 1414) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1414) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1415) return DatabaseDataTypeFramework.DATE;
			if (modelCol == 1416) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1416) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1417) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1418) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1419) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1420) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1420) return DatabaseDataTypeFramework.STRING;
			return DatabaseDataTypeFramework.STRING;
	}

	public boolean isNew() {
		return getApplicationUserGroupId() == 0;
	}

	public Object getPrimaryValue() {
		return new Integer(getApplicationUserGroupId());
	}

	public Object getDataAt(int i) {
			if (i == 0) return new Integer(applicationUserGroupId);
			if (i == 1) return new Integer(userId);
			if (i == 2) return new Integer(userGroupRefId);
			if (i == 3) return userGroupRefDisplay;
			if (i == 4) return email;
			if (i == 5) return createDt;
			if (i == 6) return new Integer(createUserId);
			if (i == 7) return createUserName;
			if (i == 8) return updateDt;
			if (i == 9) return new Integer(updateUserId);
			if (i == 10) return updateUserName;
			if (i == 11) return new Integer(updateCount);
			if (i == 12) return new Integer(activeInd);
			if (i == 13) return new Integer(systemAssignedVersionNbr);
			if (i == 14) return new Integer(recordTypeRefId);
			if (i == 15) return recordTypeRefDisplay;
		return null;
	}

	public int getDatabaseDataType(int i) {
			if (i == 0) return DatabaseDataTypeFramework.INTEGER;
			if (i == 1) return DatabaseDataTypeFramework.INTEGER;
			if (i == 2) return DatabaseDataTypeFramework.INTEGER;
			if (i == 3) return DatabaseDataTypeFramework.STRING;
			if (i == 4) return DatabaseDataTypeFramework.STRING;
			if (i == 5) return DatabaseDataTypeFramework.DATE;
			if (i == 6) return DatabaseDataTypeFramework.INTEGER;
			if (i == 7) return DatabaseDataTypeFramework.STRING;
			if (i == 8) return DatabaseDataTypeFramework.DATE;
			if (i == 9) return DatabaseDataTypeFramework.INTEGER;
			if (i == 10) return DatabaseDataTypeFramework.STRING;
			if (i == 11) return DatabaseDataTypeFramework.INTEGER;
			if (i == 12) return DatabaseDataTypeFramework.INTEGER;
			if (i == 13) return DatabaseDataTypeFramework.INTEGER;
			if (i == 14) return DatabaseDataTypeFramework.INTEGER;
			if (i == 15) return DatabaseDataTypeFramework.STRING;
			return DatabaseDataTypeFramework.STRING;
	}

	public void setDataAt(Object o, int i) {
		switch(i) {
			case 0: setApplicationUserGroupId(((Integer) o).intValue()); break;
			case 1: setUserId(((Integer) o).intValue()); break;
			case 2: setUserGroupRefId(((Integer) o).intValue()); break;
			case 3: setUserGroupRefDisplay((String) o); break;
			case 4: setEmail((String) o); break;
			case 5: setCreateDt((GregorianCalendar) o); break;
			case 6: setCreateUserId(((Integer) o).intValue()); break;
			case 7: setCreateUserName((String) o); break;
			case 8: setUpdateDt((GregorianCalendar) o); break;
			case 9: setUpdateUserId(((Integer) o).intValue()); break;
			case 10: setUpdateUserName((String) o); break;
			case 11: setUpdateCount(((Integer) o).intValue()); break;
			case 12: setActiveInd(((Integer) o).intValue()); break;
			case 13: setSystemAssignedVersionNbr(((Integer) o).intValue()); break;
			case 14: setRecordTypeRefId(((Integer) o).intValue()); break;
			case 15: setRecordTypeRefDisplay((String) o); break;
		}
	}

}
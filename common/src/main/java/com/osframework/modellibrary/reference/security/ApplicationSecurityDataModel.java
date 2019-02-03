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
public class ApplicationSecurityDataModel implements IControlModel, java.io.Serializable {

	static final long serialVersionUID = 1L;
	private int hashsize = 13;
	private Hashtable modifiedFields = new Hashtable(hashsize);
	private boolean referenceSet = false;


	/** Unique application setting identifier  */
	private int applicationSecurityId = 0;

	/** Foreign key of related table  */
	private int tableKeyId = 0;

	/** Foreign key of reference  */
	private int tableRefId = 0;

	private String tableRefDisplay="";

	/** Unique application setting identifier  */
	private int applicationViewId = 0;

	/** Read only access to application view  */
	private int readOnlyInd = 0;

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
	 * Unique application setting identifier
	 * 
	 */ 
	public int getApplicationSecurityId() {
		return applicationSecurityId;
	}

	/** 
	 * Unique application setting identifier
	 * 
	 * Required database field.
	 */ 
	public void setApplicationSecurityId(int applicationSecurityId) {
		if (!Comparison.areEqual(this.applicationSecurityId, applicationSecurityId)) {
			this.applicationSecurityId = applicationSecurityId;
			setModified("applicationSecurityId");
		};
	}

	/** 
	 * Foreign key of related table
	 * 
	 */ 
	public int getTableKeyId() {
		return tableKeyId;
	}

	/** 
	 * Foreign key of related table
	 * 
	 * Required database field.
	 */ 
	public void setTableKeyId(int tableKeyId) {
		if (!Comparison.areEqual(this.tableKeyId, tableKeyId)) {
			this.tableKeyId = tableKeyId;
			setModified("tableKeyId");
		};
	}

	/** 
	 * Foreign key of reference
	 * 
	 */ 
	public int getTableRefId() {
		return tableRefId;
	}

	/** 
	 * Foreign key of reference
	 * 
	 * Required database field.
	 */ 
	public void setTableRefId(int tableRefId) {
		if (!Comparison.areEqual(this.tableRefId, tableRefId)) {
			this.tableRefId = tableRefId;
			setModified("tableRefId");
		};
	}

	/** 
	 * Foreign key of reference
	 * 
	 */ 
	public String getTableRefDisplay() {
		return tableRefDisplay;
	}

	/** 
	 * Foreign key of reference
	 * 
	 */ 
	public void setTableRefDisplay(String display) {
		this.tableRefDisplay = display;
	}

	/** 
	 * Unique application setting identifier
	 * 
	 */ 
	public int getApplicationViewId() {
		return applicationViewId;
	}

	/** 
	 * Unique application setting identifier
	 * 
	 * Required database field.
	 */ 
	public void setApplicationViewId(int applicationViewId) {
		if (!Comparison.areEqual(this.applicationViewId, applicationViewId)) {
			this.applicationViewId = applicationViewId;
			setModified("applicationViewId");
		};
	}

	/** 
	 * Read only access to application view
	 * 
	 */ 
	public int getReadOnlyInd() {
		return readOnlyInd;
	}

	/** 
	 * Read only access to application view
	 * 
	 * Required database field.
	 */ 
	public void setReadOnlyInd(int readOnlyInd) {
		if (!Comparison.areEqual(this.readOnlyInd, readOnlyInd)) {
			this.readOnlyInd = readOnlyInd;
			setModified("readOnlyInd");
		};
	}

	/** 
	 * Read only access to application view
	 * 
	 */ 
	public boolean isReadOnly() {
		return readOnlyInd == 1;
	}

	/** 
	 * Read only access to application view
	 * 
	 */ 
	public boolean isNotReadOnly() {
		return readOnlyInd == 0;
	}

	/** 
	 * Read only access to application view
	 * 
	 */ 
	public void setReadOnly() {
		this.setReadOnlyInd(1);
	}

	public void setNotReadOnly() {
		this.setReadOnlyInd(0);
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
		columns.addColumn("applicationSecurityId", Integer.class);
		columns.addColumn("tableKeyId", Integer.class);
		columns.addColumn("tableRefId", Integer.class);
		columns.addColumn("tableRefDisplay", String.class);
		columns.addColumn("applicationViewId", Integer.class);
		columns.addColumn("readOnlyInd", Integer.class);
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
			if (reference != null && this.tableRefId > 0) setTableRefDisplay(reference.getDisplay(this.tableRefId));
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
	public void copyModifiedTo(ApplicationSecurityDataModel m) {
		Enumeration e1 = modifiedFields.keys();
		while (e1.hasMoreElements()) {
			String fieldName = (String) e1.nextElement();

			if (fieldName.compareTo("applicationSecurityId")==0)
				m.setApplicationSecurityId(this.getApplicationSecurityId());
			else if (fieldName.compareTo("tableKeyId")==0)
				m.setTableKeyId(this.getTableKeyId());
			else if (fieldName.compareTo("tableRefId")==0)
				m.setTableRefId(this.getTableRefId());
			else if (fieldName.compareTo("applicationViewId")==0)
				m.setApplicationViewId(this.getApplicationViewId());
			else if (fieldName.compareTo("readOnlyInd")==0)
				m.setReadOnlyInd(this.getReadOnlyInd());
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
	public void updateWith (ApplicationSecurityModel m) {

		this.setApplicationSecurityId(m.getApplicationSecurityId());
		this.setTableKeyId(m.getTableKeyId());
		this.setTableRefId(m.getTableRefId());
		this.setTableRefDisplay(m.getTableRefDisplay());
		this.setApplicationViewId(m.getApplicationViewId());
		this.setReadOnlyInd(m.getReadOnlyInd());
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
	public boolean isEqualTo (ApplicationSecurityModel m) {

		if (!Comparison.areEqual(this.getApplicationSecurityId(),  m.getApplicationSecurityId())) return false;
		if (!Comparison.areEqual(this.getTableKeyId(),  m.getTableKeyId())) return false;
		if (!Comparison.areEqual(this.getTableRefId(),  m.getTableRefId())) return false;
		if (!Comparison.areEqual(this.getApplicationViewId(),  m.getApplicationViewId())) return false;
		if (!Comparison.areEqual(this.getReadOnlyInd(),  m.getReadOnlyInd())) return false;
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
			sb.append("ApplicationSecurityModel:\r\n");
			sb.append("application_security_id:" + getApplicationSecurityId());
			sb.append("\r\n");
			sb.append("table_key_id:" + getTableKeyId());
			sb.append("\r\n");
			sb.append("table_ref_id:" + getTableRefId());
			sb.append("\r\n");
			sb.append("table_ref_display:" + getTableRefDisplay());
			sb.append("\r\n");
			sb.append("application_view_id:" + getApplicationViewId());
			sb.append("\r\n");
			sb.append("read_only_ind:" + getReadOnlyInd());
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
			return "ApplicationSecurityModel:"; 
		}
	}
	public void setModelColDataAt(Object o, int modelCol) {
			switch (modelCol) {
			case 599: setApplicationSecurityId(((Integer) o).intValue()); break;
			case 600: setTableKeyId(((Integer) o).intValue()); break;
			case 601: setTableRefId(((Integer) o).intValue()); break;
			case 1113: setApplicationViewId(((Integer) o).intValue()); break;
			case 605: setReadOnlyInd(((Integer) o).intValue()); break;
			case 606: setCreateDt((GregorianCalendar) o); break;
			case 607: setCreateUserId(((Integer) o).intValue()); break;
			case 830: setCreateUserName((String) o); break;
			case 608: setUpdateDt((GregorianCalendar) o); break;
			case 609: setUpdateUserId(((Integer) o).intValue()); break;
			case 831: setUpdateUserName((String) o); break;
			case 610: setUpdateCount(((Integer) o).intValue()); break;
			case 611: setActiveInd(((Integer) o).intValue()); break;
			case 612: setSystemAssignedVersionNbr(((Integer) o).intValue()); break;
			case 613: setRecordTypeRefId(((Integer) o).intValue()); break;
			default: Debug.LogWarning(this, ReferenceServices.getMsg(SystemMessageFramework.SWITCHSTATEMENTFAILED) + " modelColRefId: " + modelCol);
	} 
}

	public Object getModelColDataAt(int modelCol) {
			if (modelCol == 599) return new Integer(applicationSecurityId);
			if (modelCol == 600) return new Integer(tableKeyId);
			if (modelCol == 601) return new Integer(tableRefId);
			if (modelCol == 601) return tableRefDisplay;
			if (modelCol == 1113) return new Integer(applicationViewId);
			if (modelCol == 605) return new Integer(readOnlyInd);
			if (modelCol == 606) return createDt;
			if (modelCol == 607) return new Integer(createUserId);
			if (modelCol == 607) return createUserName;
			if (modelCol == 608) return updateDt;
			if (modelCol == 609) return new Integer(updateUserId);
			if (modelCol == 609) return updateUserName;
			if (modelCol == 610) return new Integer(updateCount);
			if (modelCol == 611) return new Integer(activeInd);
			if (modelCol == 612) return new Integer(systemAssignedVersionNbr);
			if (modelCol == 613) return new Integer(recordTypeRefId);
			if (modelCol == 613) return recordTypeRefDisplay;
		return null;
	}

	public int getModelColDatabaseDataType(int modelCol) {
			if (modelCol == 599) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 600) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 601) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 601) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1113) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 605) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 606) return DatabaseDataTypeFramework.DATE;
			if (modelCol == 607) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 607) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 608) return DatabaseDataTypeFramework.DATE;
			if (modelCol == 609) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 609) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 610) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 611) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 612) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 613) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 613) return DatabaseDataTypeFramework.STRING;
			return DatabaseDataTypeFramework.STRING;
	}

	public boolean isNew() {
		return getApplicationSecurityId() == 0;
	}

	public Object getPrimaryValue() {
		return new Integer(getApplicationSecurityId());
	}

	public Object getDataAt(int i) {
			if (i == 0) return new Integer(applicationSecurityId);
			if (i == 1) return new Integer(tableKeyId);
			if (i == 2) return new Integer(tableRefId);
			if (i == 3) return tableRefDisplay;
			if (i == 4) return new Integer(applicationViewId);
			if (i == 5) return new Integer(readOnlyInd);
			if (i == 6) return createDt;
			if (i == 7) return new Integer(createUserId);
			if (i == 8) return createUserName;
			if (i == 9) return updateDt;
			if (i == 10) return new Integer(updateUserId);
			if (i == 11) return updateUserName;
			if (i == 12) return new Integer(updateCount);
			if (i == 13) return new Integer(activeInd);
			if (i == 14) return new Integer(systemAssignedVersionNbr);
			if (i == 15) return new Integer(recordTypeRefId);
			if (i == 16) return recordTypeRefDisplay;
		return null;
	}

	public int getDatabaseDataType(int i) {
			if (i == 0) return DatabaseDataTypeFramework.INTEGER;
			if (i == 1) return DatabaseDataTypeFramework.INTEGER;
			if (i == 2) return DatabaseDataTypeFramework.INTEGER;
			if (i == 3) return DatabaseDataTypeFramework.STRING;
			if (i == 4) return DatabaseDataTypeFramework.INTEGER;
			if (i == 5) return DatabaseDataTypeFramework.INTEGER;
			if (i == 6) return DatabaseDataTypeFramework.DATE;
			if (i == 7) return DatabaseDataTypeFramework.INTEGER;
			if (i == 8) return DatabaseDataTypeFramework.STRING;
			if (i == 9) return DatabaseDataTypeFramework.DATE;
			if (i == 10) return DatabaseDataTypeFramework.INTEGER;
			if (i == 11) return DatabaseDataTypeFramework.STRING;
			if (i == 12) return DatabaseDataTypeFramework.INTEGER;
			if (i == 13) return DatabaseDataTypeFramework.INTEGER;
			if (i == 14) return DatabaseDataTypeFramework.INTEGER;
			if (i == 15) return DatabaseDataTypeFramework.INTEGER;
			if (i == 16) return DatabaseDataTypeFramework.STRING;
			return DatabaseDataTypeFramework.STRING;
	}

	public void setDataAt(Object o, int i) {
		switch(i) {
			case 0: setApplicationSecurityId(((Integer) o).intValue()); break;
			case 1: setTableKeyId(((Integer) o).intValue()); break;
			case 2: setTableRefId(((Integer) o).intValue()); break;
			case 3: setTableRefDisplay((String) o); break;
			case 4: setApplicationViewId(((Integer) o).intValue()); break;
			case 5: setReadOnlyInd(((Integer) o).intValue()); break;
			case 6: setCreateDt((GregorianCalendar) o); break;
			case 7: setCreateUserId(((Integer) o).intValue()); break;
			case 8: setCreateUserName((String) o); break;
			case 9: setUpdateDt((GregorianCalendar) o); break;
			case 10: setUpdateUserId(((Integer) o).intValue()); break;
			case 11: setUpdateUserName((String) o); break;
			case 12: setUpdateCount(((Integer) o).intValue()); break;
			case 13: setActiveInd(((Integer) o).intValue()); break;
			case 14: setSystemAssignedVersionNbr(((Integer) o).intValue()); break;
			case 15: setRecordTypeRefId(((Integer) o).intValue()); break;
			case 16: setRecordTypeRefDisplay((String) o); break;
		}
	}

}
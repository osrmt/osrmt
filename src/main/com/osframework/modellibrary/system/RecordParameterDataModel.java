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
package com.osframework.modellibrary.system;
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
public class RecordParameterDataModel implements IControlModel, java.io.Serializable {

	static final long serialVersionUID = 1L;
	private int hashsize = 16;
	private Hashtable modifiedFields = new Hashtable(hashsize);
	private boolean referenceSet = false;

	private RecordParameterValueList recordParameterValueList = new RecordParameterValueList();

	/** Unique identifier to record parameter table  */
	private int recordParameterId = 0;

	/** Foreign key to table extended  */
	private int tableId = 0;

	/** Foreign key to reference: Table  */
	private int tableRefId = 0;

	private String tableRefDisplay="";

	/** Parameter name  */
	private String parameterName;

	/** Reference identifier for the model column to bind to  */
	private int modelColumnRefId = 0;

	private String modelColumnRefDisplay="";

	/** Foreign key to reference: DatabaseDataType  */
	private int dataTypeRefId = 0;

	private String dataTypeRefDisplay="";

	/** Parameter sequence  */
	private int parameterSeq = 0;

	/** Foreign key to Application Custom Control  */
	private int applicationControlId = 0;

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
	 * Unique identifier to record parameter table
	 * 
	 */ 
	public int getRecordParameterId() {
		return recordParameterId;
	}

	/** 
	 * Unique identifier to record parameter table
	 * 
	 */ 
	public void setRecordParameterId(int recordParameterId) {
		if (!Comparison.areEqual(this.recordParameterId, recordParameterId)) {
			this.recordParameterId = recordParameterId;
			setModified("recordParameterId");
		};
	}

	/** 
	 * Foreign key to table extended
	 * 
	 */ 
	public int getTableId() {
		return tableId;
	}

	/** 
	 * Foreign key to table extended
	 * 
	 * Required database field.
	 */ 
	public void setTableId(int tableId) {
		if (!Comparison.areEqual(this.tableId, tableId)) {
			this.tableId = tableId;
			setModified("tableId");
		};
	}

	/** 
	 * Foreign key to reference: Table
	 * 
	 */ 
	public int getTableRefId() {
		return tableRefId;
	}

	/** 
	 * Foreign key to reference: Table
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
	 * Foreign key to reference: Table
	 * 
	 */ 
	public String getTableRefDisplay() {
		return tableRefDisplay;
	}

	/** 
	 * Foreign key to reference: Table
	 * 
	 */ 
	public void setTableRefDisplay(String display) {
		this.tableRefDisplay = display;
	}

	/** 
	 * Parameter name
	 * 
	 */ 
	public String getParameterName() {
		return parameterName;
	}

	/** 
	 * Parameter name
	 * 
	 */ 
	public void setParameterName(String parameterName) {
		if (!Comparison.areEqual(this.parameterName, parameterName)) {
			this.parameterName = parameterName;
			setModified("parameterName");
		};
	}

	/** 
	 * Reference identifier for the model column to bind to
	 * 
	 */ 
	public int getModelColumnRefId() {
		return modelColumnRefId;
	}

	/** 
	 * Reference identifier for the model column to bind to
	 * 
	 * Required database field.
	 */ 
	public void setModelColumnRefId(int modelColumnRefId) {
		if (!Comparison.areEqual(this.modelColumnRefId, modelColumnRefId)) {
			this.modelColumnRefId = modelColumnRefId;
			setModified("modelColumnRefId");
		};
	}

	/** 
	 * Reference identifier for the model column to bind to
	 * 
	 */ 
	public String getModelColumnRefDisplay() {
		return modelColumnRefDisplay;
	}

	/** 
	 * Reference identifier for the model column to bind to
	 * 
	 */ 
	public void setModelColumnRefDisplay(String display) {
		this.modelColumnRefDisplay = display;
	}

	/** 
	 * Foreign key to reference: DatabaseDataType
	 * 
	 */ 
	public int getDataTypeRefId() {
		return dataTypeRefId;
	}

	/** 
	 * Foreign key to reference: DatabaseDataType
	 * 
	 * Required database field.
	 */ 
	public void setDataTypeRefId(int dataTypeRefId) {
		if (!Comparison.areEqual(this.dataTypeRefId, dataTypeRefId)) {
			this.dataTypeRefId = dataTypeRefId;
			setModified("dataTypeRefId");
		};
	}

	/** 
	 * Foreign key to reference: DatabaseDataType
	 * 
	 */ 
	public String getDataTypeRefDisplay() {
		return dataTypeRefDisplay;
	}

	/** 
	 * Foreign key to reference: DatabaseDataType
	 * 
	 */ 
	public void setDataTypeRefDisplay(String display) {
		this.dataTypeRefDisplay = display;
	}

	/** 
	 * Parameter sequence
	 * 
	 */ 
	public int getParameterSeq() {
		return parameterSeq;
	}

	/** 
	 * Parameter sequence
	 * 
	 * Required database field.
	 */ 
	public void setParameterSeq(int parameterSeq) {
		if (!Comparison.areEqual(this.parameterSeq, parameterSeq)) {
			this.parameterSeq = parameterSeq;
			setModified("parameterSeq");
		};
	}

	/** 
	 * Foreign key to Application Custom Control
	 * 
	 */ 
	public int getApplicationControlId() {
		return applicationControlId;
	}

	/** 
	 * Foreign key to Application Custom Control
	 * 
	 * Required database field.
	 */ 
	public void setApplicationControlId(int applicationControlId) {
		if (!Comparison.areEqual(this.applicationControlId, applicationControlId)) {
			this.applicationControlId = applicationControlId;
			setModified("applicationControlId");
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

	public RecordParameterValueList getRecordParameterValueList() {
		return recordParameterValueList;
	}

	public void setRecordParameterValueList(RecordParameterValueList m) {
		recordParameterValueList = m;
	}


public static ResultColumnList getResultColumnList() {
	ResultColumnList columns = new ResultColumnList();
		columns.addColumn("recordParameterId", Integer.class);
		columns.addColumn("tableId", Integer.class);
		columns.addColumn("tableRefId", Integer.class);
		columns.addColumn("tableRefDisplay", String.class);
		columns.addColumn("parameterName", String.class);
		columns.addColumn("modelColumnRefId", Integer.class);
		columns.addColumn("modelColumnRefDisplay", String.class);
		columns.addColumn("dataTypeRefId", Integer.class);
		columns.addColumn("dataTypeRefDisplay", String.class);
		columns.addColumn("parameterSeq", Integer.class);
		columns.addColumn("applicationControlId", Integer.class);
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
			if (reference != null && this.modelColumnRefId > 0) setModelColumnRefDisplay(reference.getDisplay(this.modelColumnRefId));
			if (reference != null && this.dataTypeRefId > 0) setDataTypeRefDisplay(reference.getDisplay(this.dataTypeRefId));
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
	public void copyModifiedTo(RecordParameterDataModel m) {
		Enumeration e1 = modifiedFields.keys();
		while (e1.hasMoreElements()) {
			String fieldName = (String) e1.nextElement();

			if (fieldName.compareTo("recordParameterId")==0)
				m.setRecordParameterId(this.getRecordParameterId());
			else if (fieldName.compareTo("tableId")==0)
				m.setTableId(this.getTableId());
			else if (fieldName.compareTo("tableRefId")==0)
				m.setTableRefId(this.getTableRefId());
			else if (fieldName.compareTo("parameterName")==0)
				m.setParameterName(this.getParameterName());
			else if (fieldName.compareTo("modelColumnRefId")==0)
				m.setModelColumnRefId(this.getModelColumnRefId());
			else if (fieldName.compareTo("dataTypeRefId")==0)
				m.setDataTypeRefId(this.getDataTypeRefId());
			else if (fieldName.compareTo("parameterSeq")==0)
				m.setParameterSeq(this.getParameterSeq());
			else if (fieldName.compareTo("applicationControlId")==0)
				m.setApplicationControlId(this.getApplicationControlId());
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
	public void updateWith (RecordParameterModel m) {

		this.setRecordParameterId(m.getRecordParameterId());
		this.setTableId(m.getTableId());
		this.setTableRefId(m.getTableRefId());
		this.setTableRefDisplay(m.getTableRefDisplay());
		this.setParameterName(m.getParameterName());
		this.setModelColumnRefId(m.getModelColumnRefId());
		this.setModelColumnRefDisplay(m.getModelColumnRefDisplay());
		this.setDataTypeRefId(m.getDataTypeRefId());
		this.setDataTypeRefDisplay(m.getDataTypeRefDisplay());
		this.setParameterSeq(m.getParameterSeq());
		this.setApplicationControlId(m.getApplicationControlId());
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
	public boolean isEqualTo (RecordParameterModel m) {

		if (!Comparison.areEqual(this.getRecordParameterId(),  m.getRecordParameterId())) return false;
		if (!Comparison.areEqual(this.getTableId(),  m.getTableId())) return false;
		if (!Comparison.areEqual(this.getTableRefId(),  m.getTableRefId())) return false;
		if (!Comparison.areEqual(this.getParameterName(),  m.getParameterName())) return false;
		if (!Comparison.areEqual(this.getModelColumnRefId(),  m.getModelColumnRefId())) return false;
		if (!Comparison.areEqual(this.getDataTypeRefId(),  m.getDataTypeRefId())) return false;
		if (!Comparison.areEqual(this.getParameterSeq(),  m.getParameterSeq())) return false;
		if (!Comparison.areEqual(this.getApplicationControlId(),  m.getApplicationControlId())) return false;
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
			sb.append("RecordParameterModel:\r\n");
			sb.append("record_parameter_id:" + getRecordParameterId());
			sb.append("\r\n");
			sb.append("table_id:" + getTableId());
			sb.append("\r\n");
			sb.append("table_ref_id:" + getTableRefId());
			sb.append("\r\n");
			sb.append("table_ref_display:" + getTableRefDisplay());
			sb.append("\r\n");
			sb.append("parameter_name:" + getParameterName());
			sb.append("\r\n");
			sb.append("model_column_ref_id:" + getModelColumnRefId());
			sb.append("\r\n");
			sb.append("model_column_ref_display:" + getModelColumnRefDisplay());
			sb.append("\r\n");
			sb.append("data_type_ref_id:" + getDataTypeRefId());
			sb.append("\r\n");
			sb.append("data_type_ref_display:" + getDataTypeRefDisplay());
			sb.append("\r\n");
			sb.append("parameter_seq:" + getParameterSeq());
			sb.append("\r\n");
			sb.append("application_control_id:" + getApplicationControlId());
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
			sb.append(recordParameterValueList.toString());
			return sb.toString();
		} catch (Exception ex) {
			return "RecordParameterModel:"; 
		}
	}
	public void setModelColDataAt(Object o, int modelCol) {
			switch (modelCol) {
			case 711: setRecordParameterId(((Integer) o).intValue()); break;
			case 712: setTableId(((Integer) o).intValue()); break;
			case 713: setTableRefId(((Integer) o).intValue()); break;
			case 714: setParameterName((String) o); break;
			case 901: setModelColumnRefId(((Integer) o).intValue()); break;
			case 715: setDataTypeRefId(((Integer) o).intValue()); break;
			case 1164: setParameterSeq(((Integer) o).intValue()); break;
			case 717: setApplicationControlId(((Integer) o).intValue()); break;
			case 718: setCreateDt((GregorianCalendar) o); break;
			case 719: setCreateUserId(((Integer) o).intValue()); break;
			case 874: setCreateUserName((String) o); break;
			case 720: setUpdateDt((GregorianCalendar) o); break;
			case 721: setUpdateUserId(((Integer) o).intValue()); break;
			case 875: setUpdateUserName((String) o); break;
			case 722: setUpdateCount(((Integer) o).intValue()); break;
			case 723: setActiveInd(((Integer) o).intValue()); break;
			case 724: setSystemAssignedVersionNbr(((Integer) o).intValue()); break;
			case 725: setRecordTypeRefId(((Integer) o).intValue()); break;
			default: Debug.LogWarning(this, ReferenceServices.getMsg(SystemMessageFramework.SWITCHSTATEMENTFAILED) + " modelColRefId: " + modelCol);
	} 
}

	public Object getModelColDataAt(int modelCol) {
			if (modelCol == 711) return new Integer(recordParameterId);
			if (modelCol == 712) return new Integer(tableId);
			if (modelCol == 713) return new Integer(tableRefId);
			if (modelCol == 713) return tableRefDisplay;
			if (modelCol == 714) return parameterName;
			if (modelCol == 901) return new Integer(modelColumnRefId);
			if (modelCol == 901) return modelColumnRefDisplay;
			if (modelCol == 715) return new Integer(dataTypeRefId);
			if (modelCol == 715) return dataTypeRefDisplay;
			if (modelCol == 1164) return new Integer(parameterSeq);
			if (modelCol == 717) return new Integer(applicationControlId);
			if (modelCol == 718) return createDt;
			if (modelCol == 719) return new Integer(createUserId);
			if (modelCol == 719) return createUserName;
			if (modelCol == 720) return updateDt;
			if (modelCol == 721) return new Integer(updateUserId);
			if (modelCol == 721) return updateUserName;
			if (modelCol == 722) return new Integer(updateCount);
			if (modelCol == 723) return new Integer(activeInd);
			if (modelCol == 724) return new Integer(systemAssignedVersionNbr);
			if (modelCol == 725) return new Integer(recordTypeRefId);
			if (modelCol == 725) return recordTypeRefDisplay;
		return null;
	}

	public int getModelColDatabaseDataType(int modelCol) {
			if (modelCol == 711) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 712) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 713) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 713) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 714) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 901) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 901) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 715) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 715) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1164) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 717) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 718) return DatabaseDataTypeFramework.DATE;
			if (modelCol == 719) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 719) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 720) return DatabaseDataTypeFramework.DATE;
			if (modelCol == 721) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 721) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 722) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 723) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 724) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 725) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 725) return DatabaseDataTypeFramework.STRING;
			return DatabaseDataTypeFramework.STRING;
	}

	public boolean isNew() {
		return getRecordParameterId() == 0;
	}

	public Object getPrimaryValue() {
		return new Integer(getRecordParameterId());
	}

	public Object getDataAt(int i) {
			if (i == 0) return new Integer(recordParameterId);
			if (i == 1) return new Integer(tableId);
			if (i == 2) return new Integer(tableRefId);
			if (i == 3) return tableRefDisplay;
			if (i == 4) return parameterName;
			if (i == 5) return new Integer(modelColumnRefId);
			if (i == 6) return modelColumnRefDisplay;
			if (i == 7) return new Integer(dataTypeRefId);
			if (i == 8) return dataTypeRefDisplay;
			if (i == 9) return new Integer(parameterSeq);
			if (i == 10) return new Integer(applicationControlId);
			if (i == 11) return createDt;
			if (i == 12) return new Integer(createUserId);
			if (i == 13) return createUserName;
			if (i == 14) return updateDt;
			if (i == 15) return new Integer(updateUserId);
			if (i == 16) return updateUserName;
			if (i == 17) return new Integer(updateCount);
			if (i == 18) return new Integer(activeInd);
			if (i == 19) return new Integer(systemAssignedVersionNbr);
			if (i == 20) return new Integer(recordTypeRefId);
			if (i == 21) return recordTypeRefDisplay;
		return null;
	}

	public int getDatabaseDataType(int i) {
			if (i == 0) return DatabaseDataTypeFramework.INTEGER;
			if (i == 1) return DatabaseDataTypeFramework.INTEGER;
			if (i == 2) return DatabaseDataTypeFramework.INTEGER;
			if (i == 3) return DatabaseDataTypeFramework.STRING;
			if (i == 4) return DatabaseDataTypeFramework.STRING;
			if (i == 5) return DatabaseDataTypeFramework.INTEGER;
			if (i == 6) return DatabaseDataTypeFramework.STRING;
			if (i == 7) return DatabaseDataTypeFramework.INTEGER;
			if (i == 8) return DatabaseDataTypeFramework.STRING;
			if (i == 9) return DatabaseDataTypeFramework.INTEGER;
			if (i == 10) return DatabaseDataTypeFramework.INTEGER;
			if (i == 11) return DatabaseDataTypeFramework.DATE;
			if (i == 12) return DatabaseDataTypeFramework.INTEGER;
			if (i == 13) return DatabaseDataTypeFramework.STRING;
			if (i == 14) return DatabaseDataTypeFramework.DATE;
			if (i == 15) return DatabaseDataTypeFramework.INTEGER;
			if (i == 16) return DatabaseDataTypeFramework.STRING;
			if (i == 17) return DatabaseDataTypeFramework.INTEGER;
			if (i == 18) return DatabaseDataTypeFramework.INTEGER;
			if (i == 19) return DatabaseDataTypeFramework.INTEGER;
			if (i == 20) return DatabaseDataTypeFramework.INTEGER;
			if (i == 21) return DatabaseDataTypeFramework.STRING;
			return DatabaseDataTypeFramework.STRING;
	}

	public void setDataAt(Object o, int i) {
		switch(i) {
			case 0: setRecordParameterId(((Integer) o).intValue()); break;
			case 1: setTableId(((Integer) o).intValue()); break;
			case 2: setTableRefId(((Integer) o).intValue()); break;
			case 3: setTableRefDisplay((String) o); break;
			case 4: setParameterName((String) o); break;
			case 5: setModelColumnRefId(((Integer) o).intValue()); break;
			case 6: setModelColumnRefDisplay((String) o); break;
			case 7: setDataTypeRefId(((Integer) o).intValue()); break;
			case 8: setDataTypeRefDisplay((String) o); break;
			case 9: setParameterSeq(((Integer) o).intValue()); break;
			case 10: setApplicationControlId(((Integer) o).intValue()); break;
			case 11: setCreateDt((GregorianCalendar) o); break;
			case 12: setCreateUserId(((Integer) o).intValue()); break;
			case 13: setCreateUserName((String) o); break;
			case 14: setUpdateDt((GregorianCalendar) o); break;
			case 15: setUpdateUserId(((Integer) o).intValue()); break;
			case 16: setUpdateUserName((String) o); break;
			case 17: setUpdateCount(((Integer) o).intValue()); break;
			case 18: setActiveInd(((Integer) o).intValue()); break;
			case 19: setSystemAssignedVersionNbr(((Integer) o).intValue()); break;
			case 20: setRecordTypeRefId(((Integer) o).intValue()); break;
			case 21: setRecordTypeRefDisplay((String) o); break;
		}
	}

}
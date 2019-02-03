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
public class RecordParameterValueDataModel implements IControlModel, java.io.Serializable {

	static final long serialVersionUID = 1L;
	private int hashsize = 16;
	private Hashtable modifiedFields = new Hashtable(hashsize);
	private boolean referenceSet = false;


	/** Unique identifier to record parameter value table  */
	private int recordParameterValueId = 0;

	/** Foreign key to record parameter  */
	private int recordParameterId = 0;

	/** Parameter sequence  */
	private int parameterSeq = 0;

	/** Foreign key to reference: DatabaseDataType  */
	private int dataTypeRefId = 0;

	private String dataTypeRefDisplay="";

	/** Integer data value  */
	private int valueInt;

	/** Double data value  */
	private double valueDouble;

	/** String value  */
	private String valueString;

	/** Date value  */
	private DbCalendar valueDate;

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
	 * Unique identifier to record parameter value table
	 * 
	 */ 
	public int getRecordParameterValueId() {
		return recordParameterValueId;
	}

	/** 
	 * Unique identifier to record parameter value table
	 * 
	 */ 
	public void setRecordParameterValueId(int recordParameterValueId) {
		if (!Comparison.areEqual(this.recordParameterValueId, recordParameterValueId)) {
			this.recordParameterValueId = recordParameterValueId;
			setModified("recordParameterValueId");
		};
	}

	/** 
	 * Foreign key to record parameter
	 * 
	 */ 
	public int getRecordParameterId() {
		return recordParameterId;
	}

	/** 
	 * Foreign key to record parameter
	 * 
	 */ 
	public void setRecordParameterId(int recordParameterId) {
		if (!Comparison.areEqual(this.recordParameterId, recordParameterId)) {
			this.recordParameterId = recordParameterId;
			setModified("recordParameterId");
		};
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
	 * Integer data value
	 * 
	 */ 
	public int getValueInt() {
		return valueInt;
	}

	/** 
	 * Integer data value
	 * 
	 */ 
	public void setValueInt(int valueInt) {
		if (!Comparison.areEqual(this.valueInt, valueInt)) {
			this.valueInt = valueInt;
			setModified("valueInt");
		};
	}

	/** 
	 * Double data value
	 * 
	 */ 
	public double getValueDouble() {
		return valueDouble;
	}

	/** 
	 * Double data value
	 * 
	 */ 
	public void setValueDouble(double valueDouble) {
		if (!Comparison.areEqual(this.valueDouble, valueDouble)) {
			this.valueDouble = valueDouble;
			setModified("valueDouble");
		};
	}

	/** 
	 * String value
	 * 
	 */ 
	public String getValueString() {
		return valueString;
	}

	/** 
	 * String value
	 * 
	 */ 
	public void setValueString(String valueString) {
		if (!Comparison.areEqual(this.valueString, valueString)) {
			this.valueString = valueString;
			setModified("valueString");
		};
	}

	/** 
	 * Date value
	 * 
	 */ 
	public DbCalendar getValueDate() {
		return valueDate;
	}

	/** 
	 * Date value
	 * 
	 */ 
	public void setValueDate(GregorianCalendar valueDate) {
		if (!Comparison.areEqual(this.valueDate, valueDate)) {
			this.valueDate = new DbCalendar();
			if (valueDate != null) {
				this.valueDate.setTimeInMillis(valueDate.getTimeInMillis());
			}
			setModified("valueDate");
		};
	}

	/** 
	 * Date value
	 * 
	 */ 
	public void setValueDate(long milliseconds) {
		if (this.valueDate==null) {
			this.valueDate = new DbCalendar();
		}
		if (!Comparison.areEqual(this.valueDate, valueDate)) {
			this.valueDate.setTimeInMillis(milliseconds);
			setModified("valueDate");
		}

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
		columns.addColumn("recordParameterValueId", Integer.class);
		columns.addColumn("recordParameterId", Integer.class);
		columns.addColumn("parameterSeq", Integer.class);
		columns.addColumn("dataTypeRefId", Integer.class);
		columns.addColumn("dataTypeRefDisplay", String.class);
		columns.addColumn("valueInt", Integer.class);
		columns.addColumn("valueDouble", Double.class);
		columns.addColumn("valueString", String.class);
		columns.addColumn("valueDate", GregorianCalendar.class);
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
	public void copyModifiedTo(RecordParameterValueDataModel m) {
		Enumeration e1 = modifiedFields.keys();
		while (e1.hasMoreElements()) {
			String fieldName = (String) e1.nextElement();

			if (fieldName.compareTo("recordParameterValueId")==0)
				m.setRecordParameterValueId(this.getRecordParameterValueId());
			else if (fieldName.compareTo("recordParameterId")==0)
				m.setRecordParameterId(this.getRecordParameterId());
			else if (fieldName.compareTo("parameterSeq")==0)
				m.setParameterSeq(this.getParameterSeq());
			else if (fieldName.compareTo("dataTypeRefId")==0)
				m.setDataTypeRefId(this.getDataTypeRefId());
			else if (fieldName.compareTo("valueInt")==0)
				m.setValueInt(this.getValueInt());
			else if (fieldName.compareTo("valueDouble")==0)
				m.setValueDouble(this.getValueDouble());
			else if (fieldName.compareTo("valueString")==0)
				m.setValueString(this.getValueString());
			else if (fieldName.compareTo("valueDate")==0)
				m.setValueDate(this.getValueDate());
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
	public void updateWith (RecordParameterValueModel m) {

		this.setRecordParameterValueId(m.getRecordParameterValueId());
		this.setRecordParameterId(m.getRecordParameterId());
		this.setParameterSeq(m.getParameterSeq());
		this.setDataTypeRefId(m.getDataTypeRefId());
		this.setDataTypeRefDisplay(m.getDataTypeRefDisplay());
		this.setValueInt(m.getValueInt());
		this.setValueDouble(m.getValueDouble());
		this.setValueString(m.getValueString());
		this.setValueDate(m.getValueDate());
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
	public boolean isEqualTo (RecordParameterValueModel m) {

		if (!Comparison.areEqual(this.getRecordParameterValueId(),  m.getRecordParameterValueId())) return false;
		if (!Comparison.areEqual(this.getRecordParameterId(),  m.getRecordParameterId())) return false;
		if (!Comparison.areEqual(this.getParameterSeq(),  m.getParameterSeq())) return false;
		if (!Comparison.areEqual(this.getDataTypeRefId(),  m.getDataTypeRefId())) return false;
		if (!Comparison.areEqual(this.getValueInt(),  m.getValueInt())) return false;
		if (!Comparison.areEqual(this.getValueDouble(),  m.getValueDouble())) return false;
		if (!Comparison.areEqual(this.getValueString(),  m.getValueString())) return false;
		if (!Comparison.areEqual(this.getValueDate(),  m.getValueDate())) return false;
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
			sb.append("RecordParameterValueModel:\r\n");
			sb.append("record_parameter_value_id:" + getRecordParameterValueId());
			sb.append("\r\n");
			sb.append("record_parameter_id:" + getRecordParameterId());
			sb.append("\r\n");
			sb.append("parameter_seq:" + getParameterSeq());
			sb.append("\r\n");
			sb.append("data_type_ref_id:" + getDataTypeRefId());
			sb.append("\r\n");
			sb.append("data_type_ref_display:" + getDataTypeRefDisplay());
			sb.append("\r\n");
			sb.append("value_int:" + getValueInt());
			sb.append("\r\n");
			sb.append("value_double:" + getValueDouble());
			sb.append("\r\n");
			sb.append("value_string:" + getValueString());
			sb.append("\r\n");
			sb.append("value_date:" + CalendarUtility.Format(getValueDate(),AppFormats.getLongDateTimeFormat()));
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
			return "RecordParameterValueModel:"; 
		}
	}
	public void setModelColDataAt(Object o, int modelCol) {
			switch (modelCol) {
			case 776: setRecordParameterValueId(((Integer) o).intValue()); break;
			case 777: setRecordParameterId(((Integer) o).intValue()); break;
			case 1166: setParameterSeq(((Integer) o).intValue()); break;
			case 779: setDataTypeRefId(((Integer) o).intValue()); break;
			case 780: setValueInt(((Integer) o).intValue()); break;
			case 781: setValueDouble(((Double) o).doubleValue()); break;
			case 782: setValueString((String) o); break;
			case 783: setValueDate((GregorianCalendar) o); break;
			case 784: setCreateDt((GregorianCalendar) o); break;
			case 785: setCreateUserId(((Integer) o).intValue()); break;
			case 896: setCreateUserName((String) o); break;
			case 786: setUpdateDt((GregorianCalendar) o); break;
			case 787: setUpdateUserId(((Integer) o).intValue()); break;
			case 897: setUpdateUserName((String) o); break;
			case 788: setUpdateCount(((Integer) o).intValue()); break;
			case 789: setActiveInd(((Integer) o).intValue()); break;
			case 790: setSystemAssignedVersionNbr(((Integer) o).intValue()); break;
			case 791: setRecordTypeRefId(((Integer) o).intValue()); break;
			default: Debug.LogWarning(this, ReferenceServices.getMsg(SystemMessageFramework.SWITCHSTATEMENTFAILED) + " modelColRefId: " + modelCol);
	} 
}

	public Object getModelColDataAt(int modelCol) {
			if (modelCol == 776) return new Integer(recordParameterValueId);
			if (modelCol == 777) return new Integer(recordParameterId);
			if (modelCol == 1166) return new Integer(parameterSeq);
			if (modelCol == 779) return new Integer(dataTypeRefId);
			if (modelCol == 779) return dataTypeRefDisplay;
			if (modelCol == 780) return new Integer(valueInt);
			if (modelCol == 781) return new Double(valueDouble);
			if (modelCol == 782) return valueString;
			if (modelCol == 783) return valueDate;
			if (modelCol == 784) return createDt;
			if (modelCol == 785) return new Integer(createUserId);
			if (modelCol == 785) return createUserName;
			if (modelCol == 786) return updateDt;
			if (modelCol == 787) return new Integer(updateUserId);
			if (modelCol == 787) return updateUserName;
			if (modelCol == 788) return new Integer(updateCount);
			if (modelCol == 789) return new Integer(activeInd);
			if (modelCol == 790) return new Integer(systemAssignedVersionNbr);
			if (modelCol == 791) return new Integer(recordTypeRefId);
			if (modelCol == 791) return recordTypeRefDisplay;
		return null;
	}

	public int getModelColDatabaseDataType(int modelCol) {
			if (modelCol == 776) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 777) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1166) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 779) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 779) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 780) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 781) return DatabaseDataTypeFramework.DOUBLE;
			if (modelCol == 782) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 783) return DatabaseDataTypeFramework.DATE;
			if (modelCol == 784) return DatabaseDataTypeFramework.DATE;
			if (modelCol == 785) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 785) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 786) return DatabaseDataTypeFramework.DATE;
			if (modelCol == 787) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 787) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 788) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 789) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 790) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 791) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 791) return DatabaseDataTypeFramework.STRING;
			return DatabaseDataTypeFramework.STRING;
	}

	public boolean isNew() {
		return getRecordParameterValueId() == 0;
	}

	public Object getPrimaryValue() {
		return new Integer(getRecordParameterValueId());
	}

	public Object getDataAt(int i) {
			if (i == 0) return new Integer(recordParameterValueId);
			if (i == 1) return new Integer(recordParameterId);
			if (i == 2) return new Integer(parameterSeq);
			if (i == 3) return new Integer(dataTypeRefId);
			if (i == 4) return dataTypeRefDisplay;
			if (i == 5) return new Integer(valueInt);
			if (i == 6) return new Double(valueDouble);
			if (i == 7) return valueString;
			if (i == 8) return valueDate;
			if (i == 9) return createDt;
			if (i == 10) return new Integer(createUserId);
			if (i == 11) return createUserName;
			if (i == 12) return updateDt;
			if (i == 13) return new Integer(updateUserId);
			if (i == 14) return updateUserName;
			if (i == 15) return new Integer(updateCount);
			if (i == 16) return new Integer(activeInd);
			if (i == 17) return new Integer(systemAssignedVersionNbr);
			if (i == 18) return new Integer(recordTypeRefId);
			if (i == 19) return recordTypeRefDisplay;
		return null;
	}

	public int getDatabaseDataType(int i) {
			if (i == 0) return DatabaseDataTypeFramework.INTEGER;
			if (i == 1) return DatabaseDataTypeFramework.INTEGER;
			if (i == 2) return DatabaseDataTypeFramework.INTEGER;
			if (i == 3) return DatabaseDataTypeFramework.INTEGER;
			if (i == 4) return DatabaseDataTypeFramework.STRING;
			if (i == 5) return DatabaseDataTypeFramework.INTEGER;
			if (i == 6) return DatabaseDataTypeFramework.DOUBLE;
			if (i == 7) return DatabaseDataTypeFramework.STRING;
			if (i == 8) return DatabaseDataTypeFramework.DATE;
			if (i == 9) return DatabaseDataTypeFramework.DATE;
			if (i == 10) return DatabaseDataTypeFramework.INTEGER;
			if (i == 11) return DatabaseDataTypeFramework.STRING;
			if (i == 12) return DatabaseDataTypeFramework.DATE;
			if (i == 13) return DatabaseDataTypeFramework.INTEGER;
			if (i == 14) return DatabaseDataTypeFramework.STRING;
			if (i == 15) return DatabaseDataTypeFramework.INTEGER;
			if (i == 16) return DatabaseDataTypeFramework.INTEGER;
			if (i == 17) return DatabaseDataTypeFramework.INTEGER;
			if (i == 18) return DatabaseDataTypeFramework.INTEGER;
			if (i == 19) return DatabaseDataTypeFramework.STRING;
			return DatabaseDataTypeFramework.STRING;
	}

	public void setDataAt(Object o, int i) {
		switch(i) {
			case 0: setRecordParameterValueId(((Integer) o).intValue()); break;
			case 1: setRecordParameterId(((Integer) o).intValue()); break;
			case 2: setParameterSeq(((Integer) o).intValue()); break;
			case 3: setDataTypeRefId(((Integer) o).intValue()); break;
			case 4: setDataTypeRefDisplay((String) o); break;
			case 5: setValueInt(((Integer) o).intValue()); break;
			case 6: setValueDouble(((Double) o).doubleValue()); break;
			case 7: setValueString((String) o); break;
			case 8: setValueDate((GregorianCalendar) o); break;
			case 9: setCreateDt((GregorianCalendar) o); break;
			case 10: setCreateUserId(((Integer) o).intValue()); break;
			case 11: setCreateUserName((String) o); break;
			case 12: setUpdateDt((GregorianCalendar) o); break;
			case 13: setUpdateUserId(((Integer) o).intValue()); break;
			case 14: setUpdateUserName((String) o); break;
			case 15: setUpdateCount(((Integer) o).intValue()); break;
			case 16: setActiveInd(((Integer) o).intValue()); break;
			case 17: setSystemAssignedVersionNbr(((Integer) o).intValue()); break;
			case 18: setRecordTypeRefId(((Integer) o).intValue()); break;
			case 19: setRecordTypeRefDisplay((String) o); break;
		}
	}

}
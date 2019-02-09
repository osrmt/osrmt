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
public class BaselineDataModel implements IControlModel, java.io.Serializable {

	static final long serialVersionUID = 1L;
	private int hashsize = 13;
	private Hashtable modifiedFields = new Hashtable(hashsize);
	private boolean referenceSet = false;


	/** Unique identifier to baseline table  */
	private int baselineId = 0;

	/** Product foreign key to reference  */
	private int productRefId = 0;

	private String productRefDisplay="";

	/** Baseline name  */
	private String baselineName;

	/** Official baseline date time  */
	private DbCalendar baselineDt = DbCalendar.getDbCalendar();

	/** Description of the baseline  */
	private String description;

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
	 * Unique identifier to baseline table
	 * 
	 */ 
	public int getBaselineId() {
		return baselineId;
	}

	/** 
	 * Unique identifier to baseline table
	 * 
	 */ 
	public void setBaselineId(int baselineId) {
		if (!Comparison.areEqual(this.baselineId, baselineId)) {
			this.baselineId = baselineId;
			setModified("baselineId");
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
	 * Baseline name
	 * 
	 */ 
	public String getBaselineName() {
		return baselineName;
	}

	/** 
	 * Baseline name
	 * 
	 * Required database field.
	 */ 
	public void setBaselineName(String baselineName) {
		if (!Comparison.areEqual(this.baselineName, baselineName)) {
			this.baselineName = baselineName;
			setModified("baselineName");
		};
	}

	/** 
	 * Official baseline date time
	 * 
	 */ 
	public DbCalendar getBaselineDt() {
		return baselineDt;
	}

	/** 
	 * Official baseline date time
	 * 
	 */ 
	public void setBaselineDt(GregorianCalendar baselineDt) {
		if (!Comparison.areEqual(this.baselineDt, baselineDt)) {
			this.baselineDt = new DbCalendar();
			if (baselineDt != null) {
				this.baselineDt.setTimeInMillis(baselineDt.getTimeInMillis());
			}
			setModified("baselineDt");
		};
	}

	/** 
	 * Official baseline date time
	 * 
	 */ 
	public void setBaselineDt(long milliseconds) {
		if (this.baselineDt==null) {
			this.baselineDt = new DbCalendar();
		}
		if (!Comparison.areEqual(this.baselineDt, baselineDt)) {
			this.baselineDt.setTimeInMillis(milliseconds);
			setModified("baselineDt");
		}

	}

	/** 
	 * Description of the baseline
	 * 
	 */ 
	public String getDescription() {
		return description;
	}

	/** 
	 * Description of the baseline
	 * 
	 */ 
	public void setDescription(String description) {
		if (!Comparison.areEqual(this.description, description)) {
			this.description = description;
			setModified("description");
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
		columns.addColumn("baselineId", Integer.class);
		columns.addColumn("productRefId", Integer.class);
		columns.addColumn("productRefDisplay", String.class);
		columns.addColumn("baselineName", String.class);
		columns.addColumn("baselineDt", GregorianCalendar.class);
		columns.addColumn("description", String.class);
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
			if (reference != null && this.productRefId > 0) setProductRefDisplay(reference.getDisplay(this.productRefId));
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
	public void copyModifiedTo(BaselineDataModel m) {
		Enumeration e1 = modifiedFields.keys();
		while (e1.hasMoreElements()) {
			String fieldName = (String) e1.nextElement();

			if (fieldName.compareTo("baselineId")==0)
				m.setBaselineId(this.getBaselineId());
			else if (fieldName.compareTo("productRefId")==0)
				m.setProductRefId(this.getProductRefId());
			else if (fieldName.compareTo("baselineName")==0)
				m.setBaselineName(this.getBaselineName());
			else if (fieldName.compareTo("baselineDt")==0)
				m.setBaselineDt(this.getBaselineDt());
			else if (fieldName.compareTo("description")==0)
				m.setDescription(this.getDescription());
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
	public void updateWith (BaselineModel m) {

		this.setBaselineId(m.getBaselineId());
		this.setProductRefId(m.getProductRefId());
		this.setProductRefDisplay(m.getProductRefDisplay());
		this.setBaselineName(m.getBaselineName());
		this.setBaselineDt(m.getBaselineDt());
		this.setDescription(m.getDescription());
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
	public boolean isEqualTo (BaselineModel m) {

		if (!Comparison.areEqual(this.getBaselineId(),  m.getBaselineId())) return false;
		if (!Comparison.areEqual(this.getProductRefId(),  m.getProductRefId())) return false;
		if (!Comparison.areEqual(this.getBaselineName(),  m.getBaselineName())) return false;
		if (!Comparison.areEqual(this.getBaselineDt(),  m.getBaselineDt())) return false;
		if (!Comparison.areEqual(this.getDescription(),  m.getDescription())) return false;
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
		if (getBaselineName()== null) v.add("baselineName");
		return v;
	}
	public String toString() {
		try {
			StringBuffer sb = new StringBuffer(1024);
			sb.append("BaselineModel:\r\n");
			sb.append("baseline_id:" + getBaselineId());
			sb.append("\r\n");
			sb.append("product_ref_id:" + getProductRefId());
			sb.append("\r\n");
			sb.append("product_ref_display:" + getProductRefDisplay());
			sb.append("\r\n");
			sb.append("baseline_name:" + getBaselineName());
			sb.append("\r\n");
			sb.append("baseline_dt:" + CalendarUtility.Format(getBaselineDt(),AppFormats.getLongDateTimeFormat()));
			sb.append("\r\n");
			sb.append("description:" + getDescription());
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
			return "BaselineModel:"; 
		}
	}
	public void setModelColDataAt(Object o, int modelCol) {
			switch (modelCol) {
			case 1141: setBaselineId(((Integer) o).intValue()); break;
			case 1155: setProductRefId(((Integer) o).intValue()); break;
			case 1142: setBaselineName((String) o); break;
			case 1163: setBaselineDt((GregorianCalendar) o); break;
			case 1159: setDescription((String) o); break;
			case 1143: setCreateDt((GregorianCalendar) o); break;
			case 1144: setCreateUserId(((Integer) o).intValue()); break;
			case 1151: setCreateUserName((String) o); break;
			case 1145: setUpdateDt((GregorianCalendar) o); break;
			case 1146: setUpdateUserId(((Integer) o).intValue()); break;
			case 1152: setUpdateUserName((String) o); break;
			case 1147: setUpdateCount(((Integer) o).intValue()); break;
			case 1148: setActiveInd(((Integer) o).intValue()); break;
			case 1149: setSystemAssignedVersionNbr(((Integer) o).intValue()); break;
			case 1150: setRecordTypeRefId(((Integer) o).intValue()); break;
			default: Debug.LogWarning(this, ReferenceServices.getMsg(SystemMessageFramework.SWITCHSTATEMENTFAILED) + " modelColRefId: " + modelCol);
	} 
}

	public Object getModelColDataAt(int modelCol) {
			if (modelCol == 1141) return new Integer(baselineId);
			if (modelCol == 1155) return new Integer(productRefId);
			if (modelCol == 1155) return productRefDisplay;
			if (modelCol == 1142) return baselineName;
			if (modelCol == 1163) return baselineDt;
			if (modelCol == 1159) return description;
			if (modelCol == 1143) return createDt;
			if (modelCol == 1144) return new Integer(createUserId);
			if (modelCol == 1144) return createUserName;
			if (modelCol == 1145) return updateDt;
			if (modelCol == 1146) return new Integer(updateUserId);
			if (modelCol == 1146) return updateUserName;
			if (modelCol == 1147) return new Integer(updateCount);
			if (modelCol == 1148) return new Integer(activeInd);
			if (modelCol == 1149) return new Integer(systemAssignedVersionNbr);
			if (modelCol == 1150) return new Integer(recordTypeRefId);
			if (modelCol == 1150) return recordTypeRefDisplay;
		return null;
	}

	public int getModelColDatabaseDataType(int modelCol) {
			if (modelCol == 1141) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1155) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1155) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1142) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1163) return DatabaseDataTypeFramework.DATE;
			if (modelCol == 1159) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1143) return DatabaseDataTypeFramework.DATE;
			if (modelCol == 1144) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1144) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1145) return DatabaseDataTypeFramework.DATE;
			if (modelCol == 1146) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1146) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1147) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1148) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1149) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1150) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1150) return DatabaseDataTypeFramework.STRING;
			return DatabaseDataTypeFramework.STRING;
	}

	public boolean isNew() {
		return getBaselineId() == 0;
	}

	public Object getPrimaryValue() {
		return new Integer(getBaselineId());
	}

	public Object getDataAt(int i) {
			if (i == 0) return new Integer(baselineId);
			if (i == 1) return new Integer(productRefId);
			if (i == 2) return productRefDisplay;
			if (i == 3) return baselineName;
			if (i == 4) return baselineDt;
			if (i == 5) return description;
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
			if (i == 2) return DatabaseDataTypeFramework.STRING;
			if (i == 3) return DatabaseDataTypeFramework.STRING;
			if (i == 4) return DatabaseDataTypeFramework.DATE;
			if (i == 5) return DatabaseDataTypeFramework.STRING;
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
			case 0: setBaselineId(((Integer) o).intValue()); break;
			case 1: setProductRefId(((Integer) o).intValue()); break;
			case 2: setProductRefDisplay((String) o); break;
			case 3: setBaselineName((String) o); break;
			case 4: setBaselineDt((GregorianCalendar) o); break;
			case 5: setDescription((String) o); break;
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
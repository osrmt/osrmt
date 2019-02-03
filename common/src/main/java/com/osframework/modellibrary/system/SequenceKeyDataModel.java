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
import java.util.Hashtable;
import java.util.Vector;

import com.osframework.appclient.services.ReferenceServices;
import com.osframework.ejb.reference.common.IReferenceMap;
import com.osframework.ejb.reference.security.ISecurity;
import com.osframework.framework.exceptions.ExceptionInfo;
import com.osframework.framework.logging.Debug;
import com.osframework.modellibrary.common.Comparison;
import com.osframework.modellibrary.common.IControlModel;
import com.osframework.modellibrary.common.ResultColumnList;
import com.osframework.modellibrary.reference.group.DatabaseDataTypeFramework;
import com.osframework.modellibrary.reference.group.SystemMessageFramework;

/**
null
*/
public class SequenceKeyDataModel implements IControlModel, java.io.Serializable {

	static final long serialVersionUID = 1L;
	private int hashsize = 3;
	private Hashtable modifiedFields = new Hashtable(hashsize);
	private boolean referenceSet = false;


	/** Database table name  */
	private int tableRefId = 0;

	private String tableRefDisplay="";

	/** Next number to be used  */
	private int nextId = 1000001;

	/** Identifies database row as Reference or Record and System or User entered  */
	private int recordTypeRefId = 321;

	private String recordTypeRefDisplay="";


	/** 
	 * Database table name
	 * 
	 */ 
	public int getTableRefId() {
		return tableRefId;
	}

	/** 
	 * Database table name
	 * 
	 * Required database field.
	 */ 
	public void setTableRefId(int tableRefId) {
		this.tableRefId = tableRefId;
		setModified("tableRefId");
	}

	/** 
	 * Database table name
	 * 
	 */ 
	public String getTableRefDisplay() {
		return tableRefDisplay;
	}

	/** 
	 * Database table name
	 * 
	 */ 
	public void setTableRefDisplay(String display) {
		this.tableRefDisplay = display;
	}

	/** 
	 * Next number to be used
	 * 
	 */ 
	public int getNextId() {
		return nextId;
	}

	/** 
	 * Next number to be used
	 * 
	 * Required database field.
	 */ 
	public void setNextId(int nextId) {
		this.nextId = nextId;
		setModified("nextId");
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
		this.recordTypeRefId = recordTypeRefId;
		setModified("recordTypeRefId");
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
		columns.addColumn("tableRefId", Integer.class);
		columns.addColumn("tableRefDisplay", String.class);
		columns.addColumn("nextId", Integer.class);
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
	public void copyModifiedTo(SequenceKeyDataModel m) {
		Enumeration e1 = modifiedFields.keys();
		while (e1.hasMoreElements()) {
			String fieldName = (String) e1.nextElement();

			if (fieldName.compareTo("tableRefId")==0)
				m.setTableRefId(this.getTableRefId());
			else if (fieldName.compareTo("nextId")==0)
				m.setNextId(this.getNextId());
			else if (fieldName.compareTo("recordTypeRefId")==0)
				m.setRecordTypeRefId(this.getRecordTypeRefId());
			else 		
				Debug.LogError(this, ExceptionInfo.invalidCopyModifiedField + " " + fieldName);
	}
}

	/**
	 * Update this object with the data from m
	 */
	public void updateWith (SequenceKeyModel m) {

		this.setTableRefId(m.getTableRefId());
		this.setTableRefDisplay(m.getTableRefDisplay());
		this.setNextId(m.getNextId());
		this.setRecordTypeRefId(m.getRecordTypeRefId());
		this.setRecordTypeRefDisplay(m.getRecordTypeRefDisplay());
	}

	/**
	 * Compare the two objects
	 */
	public boolean isEqualTo (SequenceKeyModel m) {

		if (!Comparison.areEqual(this.getTableRefId(),  m.getTableRefId())) return false;
		if (!Comparison.areEqual(this.getNextId(),  m.getNextId())) return false;
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
			sb.append("SequenceKeyModel:\r\n");
			sb.append("table_ref_id:" + getTableRefId());
			sb.append("\r\n");
			sb.append("table_ref_display:" + getTableRefDisplay());
			sb.append("\r\n");
			sb.append("next_id:" + getNextId());
			sb.append("\r\n");
			sb.append("record_type_ref_id:" + getRecordTypeRefId());
			sb.append("\r\n");
			sb.append("record_type_ref_display:" + getRecordTypeRefDisplay());
			sb.append("\r\n");
			return sb.toString();
		} catch (Exception ex) {
			return "SequenceKeyModel:"; 
		}
	}
	public void setModelColDataAt(Object o, int modelCol) {
			switch (modelCol) {
			case 749: setTableRefId(((Integer) o).intValue()); break;
			case 750: setNextId(((Integer) o).intValue()); break;
			case 751: setRecordTypeRefId(((Integer) o).intValue()); break;
			default: Debug.LogWarning(this, ReferenceServices.getMsg(SystemMessageFramework.SWITCHSTATEMENTFAILED) + " modelColRefId: " + modelCol);
	} 
}

	public Object getModelColDataAt(int modelCol) {
			if (modelCol == 749) return new Integer(tableRefId);
			if (modelCol == 749) return tableRefDisplay;
			if (modelCol == 750) return new Integer(nextId);
			if (modelCol == 751) return new Integer(recordTypeRefId);
			if (modelCol == 751) return recordTypeRefDisplay;
		return null;
	}

	public int getModelColDatabaseDataType(int modelCol) {
			if (modelCol == 749) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 749) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 750) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 751) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 751) return DatabaseDataTypeFramework.STRING;
			return DatabaseDataTypeFramework.STRING;
	}

	public boolean isNew() {
		return getTableRefId() == 0;
	}

	public Object getPrimaryValue() {
		return new Integer(getTableRefId());
	}

	public Object getDataAt(int i) {
			if (i == 0) return new Integer(tableRefId);
			if (i == 1) return tableRefDisplay;
			if (i == 2) return new Integer(nextId);
			if (i == 3) return new Integer(recordTypeRefId);
			if (i == 4) return recordTypeRefDisplay;
		return null;
	}

	public int getDatabaseDataType(int i) {
			if (i == 0) return DatabaseDataTypeFramework.INTEGER;
			if (i == 1) return DatabaseDataTypeFramework.STRING;
			if (i == 2) return DatabaseDataTypeFramework.INTEGER;
			if (i == 3) return DatabaseDataTypeFramework.INTEGER;
			if (i == 4) return DatabaseDataTypeFramework.STRING;
			return DatabaseDataTypeFramework.STRING;
	}

	public void setDataAt(Object o, int i) {
		switch(i) {
			case 0: setTableRefId(((Integer) o).intValue()); break;
			case 1: setTableRefDisplay((String) o); break;
			case 2: setNextId(((Integer) o).intValue()); break;
			case 3: setRecordTypeRefId(((Integer) o).intValue()); break;
			case 4: setRecordTypeRefDisplay((String) o); break;
		}
	}

}
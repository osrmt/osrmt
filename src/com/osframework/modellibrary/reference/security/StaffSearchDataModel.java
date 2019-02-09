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
import java.util.*;
import com.osframework.framework.locale.*;
import com.osframework.framework.logging.*;
import com.osframework.framework.utility.*;
import com.osframework.framework.exceptions.*;
import com.osframework.ejb.reference.common.*;
import com.osframework.ejb.reference.security.*;
import com.osframework.modellibrary.common.*;
import com.osframework.modellibrary.reference.security.*;

/**
null
*/
public class StaffSearchDataModel extends BaseModel implements java.io.Serializable  {

	static final long serialVersionUID = 1L;
	private int hashsize = 20;
	private Hashtable modifiedFields = new Hashtable(hashsize);


	/** Unique identifier for the staff table  */
	private int staffId = 0;

	/** Staff first name  */
	private String firstName;

	/** Staff last name  */
	private String lastName;

	/** Staff formatted name  */
	private String displayName;


	/** 
	 * Unique identifier for the staff table
	 * 
	 */ 
	public int getStaffId() {
		return staffId;
	}

	/** 
	 * Unique identifier for the staff table
	 * 
	 */ 
	public void setStaffId(int staffId) {
		this.staffId = staffId;
		setModified("staffId");
	}

	/** 
	 * Staff first name
	 * 
	 */ 
	public String getFirstName() {
		return firstName;
	}

	/** 
	 * Staff first name
	 * 
	 */ 
	public void setFirstName(String firstName) {
		this.firstName = firstName;
		setModified("firstName");
	}

	/** 
	 * Staff last name
	 * 
	 */ 
	public String getLastName() {
		return lastName;
	}

	/** 
	 * Staff last name
	 * 
	 */ 
	public void setLastName(String lastName) {
		this.lastName = lastName;
		setModified("lastName");
	}

	/** 
	 * Staff formatted name
	 * 
	 */ 
	public String getDisplayName() {
		return displayName;
	}

	/** 
	 * Staff formatted name
	 * 
	 */ 
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
		setModified("displayName");
	}


public static ResultColumnList getResultColumnList() {
	ResultColumnList columns = new ResultColumnList();
		columns.addColumn("staffId", Integer.class);
		columns.addColumn("firstName", String.class);
		columns.addColumn("lastName", String.class);
		columns.addColumn("displayName", String.class);
		return columns;
	}
		
	/** 
	 * Flags this field as being modified
	 */ 
	public void setReferenceDisplay(IReferenceMap reference, ISecurity security) {
		try {
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
	public void copyModifiedTo(StaffSearchDataModel m) {
		Enumeration e1 = modifiedFields.keys();
		while (e1.hasMoreElements()) {
			String fieldName = (String) e1.nextElement();

			if (fieldName.compareTo("staffId")==0)
				m.setStaffId(this.getStaffId());
			else if (fieldName.compareTo("firstName")==0)
				m.setFirstName(this.getFirstName());
			else if (fieldName.compareTo("lastName")==0)
				m.setLastName(this.getLastName());
			else if (fieldName.compareTo("displayName")==0)
				m.setDisplayName(this.getDisplayName());
			else 		
				Debug.LogError(this, ExceptionInfo.invalidCopyModifiedField + " " + fieldName);
	}
}

	/**
	 * Update this object with the data from m
	 */
	public void updateWith (StaffSearchModel m) {

		this.setStaffId(m.getStaffId());
		this.setFirstName(m.getFirstName());
		this.setLastName(m.getLastName());
		this.setDisplayName(m.getDisplayName());
	}

	/**
	 * Compare the two objects
	 */
	public boolean isEqualTo (StaffSearchModel m) {

		if (!Comparison.areEqual(this.getStaffId(),  m.getStaffId())) return false;
		if (!Comparison.areEqual(this.getFirstName(),  m.getFirstName())) return false;
		if (!Comparison.areEqual(this.getLastName(),  m.getLastName())) return false;
		if (!Comparison.areEqual(this.getDisplayName(),  m.getDisplayName())) return false;
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
			sb.append("StaffSearchModel:\r\n");
			sb.append("staff_id:" + getStaffId());
			sb.append("\r\n");
			sb.append("first_name:" + getFirstName());
			sb.append("\r\n");
			sb.append("last_name:" + getLastName());
			sb.append("\r\n");
			sb.append("display_name:" + getDisplayName());
			sb.append("\r\n");
			return sb.toString();
		} catch (Exception ex) {
			return "StaffSearchModel:"; 
		}
	}
	public Object getDataAt(int i) {
			if (i == 0) return new Integer(staffId);
			if (i == 1) return firstName;
			if (i == 2) return lastName;
			if (i == 3) return displayName;
		return null;
	}

}
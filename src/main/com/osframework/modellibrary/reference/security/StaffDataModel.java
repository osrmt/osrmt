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
public class StaffDataModel extends BaseModel implements java.io.Serializable  {

	static final long serialVersionUID = 1L;
	private int hashsize = 20;
	private Hashtable modifiedFields = new Hashtable(hashsize);


	/** Unique identifier for the staff table  */
	private int staffId = 0;

	/** Staff last name  */
	private String lastName;

	/** Staff first name  */
	private String firstName;

	/** Staff formatted name  */
	private String displayName;

	/** Staff group  */
	private int staffGroupRefId = 0;

	private String staffGroupRefDisplay="";

	/** Last name uppercase  */
	private String lastNameIndexed;

	/** First name uppercase  */
	private String firstNameIndexed;

	/** Date the record was created  */
	private GregorianCalendar createDt = new GregorianCalendar();;

	/** User that created this record  */
	private int createUserId = 0;

	private String createUserName="";

	/** Date the record was updated  */
	private GregorianCalendar updateDt = new GregorianCalendar();;

	/** Date the record was last updated  */
	private int updateUserId = 0;

	private String updateUserName="";

	/** Number of times this record has been updated  */
	private int updateCount = 0;

	/** Active indicator 1=Active 0=Inactive  */
	private int activeInd = 1;

	/** 0: Customer defined >0:   */
	private int systemAssignedVersionNbr = 0;


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
		if (lastName != null) {
			setLastNameIndexed(lastName.toUpperCase());
		}
		setModified("lastName");
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
		if (firstName != null) {
			setFirstNameIndexed(firstName.toUpperCase());
		}
		setModified("firstName");
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

	/** 
	 * Staff group
	 * 
	 */ 
	public int getStaffGroupRefId() {
		return staffGroupRefId;
	}

	/** 
	 * Staff group
	 * 
	 */ 
	public void setStaffGroupRefId(int staffGroupRefId) {
		this.staffGroupRefId = staffGroupRefId;
		setModified("staffGroupRefId");
	}

	/** 
	 * Staff group
	 * 
	 */ 
	public String getStaffGroupRefDisplay() {
		return staffGroupRefDisplay;
	}

	/** 
	 * Staff group
	 * 
	 */ 
	public void setStaffGroupRefDisplay(String display) {
		this.staffGroupRefDisplay = display;
	}

	/** 
	 * Last name uppercase
	 * 
	 */ 
	public String getLastNameIndexed() {
		return lastNameIndexed;
	}

	/** 
	 * Last name uppercase
	 * 
	 */ 
	public void setLastNameIndexed(String lastNameIndexed) {
		this.lastNameIndexed = lastNameIndexed;
		setModified("lastNameIndexed");
	}

	/** 
	 * First name uppercase
	 * 
	 */ 
	public String getFirstNameIndexed() {
		return firstNameIndexed;
	}

	/** 
	 * First name uppercase
	 * 
	 */ 
	public void setFirstNameIndexed(String firstNameIndexed) {
		this.firstNameIndexed = firstNameIndexed;
		setModified("firstNameIndexed");
	}

	/** 
	 * Date the record was created
	 * 
	 */ 
	public GregorianCalendar getCreateDt() {
		return createDt;
	}

	/** 
	 * Date the record was created
	 * 
	 */ 
	public void setCreateDt(GregorianCalendar createDt) {
		if (createDt != null) {;
			if (this.createDt == null) {
				this.createDt = new GregorianCalendar();
			}
			this.createDt.setTimeInMillis(createDt.getTimeInMillis());
		}
		setModified("createDt");
	}

	/** 
	 * Date the record was created
	 * 
	 */ 
	public void setCreateDt(long milliseconds) {
		if (this.createDt==null) {
			this.createDt= new GregorianCalendar();
		}
		this.createDt.setTimeInMillis(milliseconds);
		setModified("createDt");
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
	 */ 
	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
		setModified("createUserId");
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
	public GregorianCalendar getUpdateDt() {
		return updateDt;
	}

	/** 
	 * Date the record was updated
	 * 
	 */ 
	public void setUpdateDt(GregorianCalendar updateDt) {
		if (updateDt != null) {;
			if (this.updateDt == null) {
				this.updateDt = new GregorianCalendar();
			}
			this.updateDt.setTimeInMillis(updateDt.getTimeInMillis());
		}
		setModified("updateDt");
	}

	/** 
	 * Date the record was updated
	 * 
	 */ 
	public void setUpdateDt(long milliseconds) {
		if (this.updateDt==null) {
			this.updateDt= new GregorianCalendar();
		}
		this.updateDt.setTimeInMillis(milliseconds);
		setModified("updateDt");
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
	 */ 
	public void setUpdateUserId(int updateUserId) {
		this.updateUserId = updateUserId;
		setModified("updateUserId");
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
	 */ 
	public void setUpdateCount(int updateCount) {
		this.updateCount = updateCount;
		setModified("updateCount");
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
	 */ 
	public void setActiveInd(int activeInd) {
		this.activeInd = activeInd;
		setModified("activeInd");
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
		this.activeInd = 1;
	}

	public void setNotActive() {
		this.activeInd = 0;
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
		this.systemAssignedVersionNbr = systemAssignedVersionNbr;
		setModified("systemAssignedVersionNbr");
	}


public static ResultColumnList getResultColumnList() {
	ResultColumnList columns = new ResultColumnList();
		columns.addColumn("staffId", Integer.class);
		columns.addColumn("lastName", String.class);
		columns.addColumn("firstName", String.class);
		columns.addColumn("displayName", String.class);
		columns.addColumn("staffGroupRefId", Integer.class);
		columns.addColumn("staffGroupRefDisplay", String.class);
		columns.addColumn("lastNameIndexed", String.class);
		columns.addColumn("firstNameIndexed", String.class);
		columns.addColumn("createDt", GregorianCalendar.class);
		columns.addColumn("createUserId", Integer.class);
		columns.addColumn("createUserName", String.class);
		columns.addColumn("updateDt", GregorianCalendar.class);
		columns.addColumn("updateUserId", Integer.class);
		columns.addColumn("updateUserName", String.class);
		columns.addColumn("updateCount", Integer.class);
		columns.addColumn("activeInd", Integer.class);
		columns.addColumn("systemAssignedVersionNbr", Integer.class);
		return columns;
	}
		
	/** 
	 * Flags this field as being modified
	 */ 
	public void setReferenceDisplay(IReferenceMap reference, ISecurity security) {
		try {
			setStaffGroupRefDisplay(reference.getDisplay(this.staffGroupRefId));
			setCreateUserName(security.getUser(this.createUserId).getDisplayName());
			setUpdateUserName(security.getUser(this.updateUserId).getDisplayName());
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
	public void copyModifiedTo(StaffDataModel m) {
		Enumeration e1 = modifiedFields.keys();
		while (e1.hasMoreElements()) {
			String fieldName = (String) e1.nextElement();

			if (fieldName.compareTo("staffId")==0)
				m.setStaffId(this.getStaffId());
			else if (fieldName.compareTo("lastName")==0)
				m.setLastName(this.getLastName());
			else if (fieldName.compareTo("firstName")==0)
				m.setFirstName(this.getFirstName());
			else if (fieldName.compareTo("displayName")==0)
				m.setDisplayName(this.getDisplayName());
			else if (fieldName.compareTo("staffGroupRefId")==0)
				m.setStaffGroupRefId(this.getStaffGroupRefId());
			else if (fieldName.compareTo("lastNameIndexed")==0)
				m.setLastNameIndexed(this.getLastNameIndexed());
			else if (fieldName.compareTo("firstNameIndexed")==0)
				m.setFirstNameIndexed(this.getFirstNameIndexed());
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
			else 		
				Debug.LogError(this, ExceptionInfo.invalidCopyModifiedField + " " + fieldName);
	}
}

	/**
	 * Update this object with the data from m
	 */
	public void updateWith (StaffModel m) {

		this.setStaffId(m.getStaffId());
		this.setLastName(m.getLastName());
		this.setFirstName(m.getFirstName());
		this.setDisplayName(m.getDisplayName());
		this.setStaffGroupRefId(m.getStaffGroupRefId());
		this.setStaffGroupRefDisplay(m.getStaffGroupRefDisplay());
		this.setLastNameIndexed(m.getLastNameIndexed());
		this.setFirstNameIndexed(m.getFirstNameIndexed());
		this.setCreateDt(m.getCreateDt());
		this.setCreateUserId(m.getCreateUserId());
		this.setCreateUserName(m.getCreateUserName());
		this.setUpdateDt(m.getUpdateDt());
		this.setUpdateUserId(m.getUpdateUserId());
		this.setUpdateUserName(m.getUpdateUserName());
		this.setUpdateCount(m.getUpdateCount());
		this.setActiveInd(m.getActiveInd());
		this.setSystemAssignedVersionNbr(m.getSystemAssignedVersionNbr());
	}

	/**
	 * Compare the two objects
	 */
	public boolean isEqualTo (StaffModel m) {

		if (!Comparison.areEqual(this.getStaffId(),  m.getStaffId())) return false;
		if (!Comparison.areEqual(this.getLastName(),  m.getLastName())) return false;
		if (!Comparison.areEqual(this.getFirstName(),  m.getFirstName())) return false;
		if (!Comparison.areEqual(this.getDisplayName(),  m.getDisplayName())) return false;
		if (!Comparison.areEqual(this.getStaffGroupRefId(),  m.getStaffGroupRefId())) return false;
		if (!Comparison.areEqual(this.getLastNameIndexed(),  m.getLastNameIndexed())) return false;
		if (!Comparison.areEqual(this.getFirstNameIndexed(),  m.getFirstNameIndexed())) return false;
		if (!Comparison.areEqual(this.getCreateDt(),  m.getCreateDt())) return false;
		if (!Comparison.areEqual(this.getCreateUserId(),  m.getCreateUserId())) return false;
		if (!Comparison.areEqual(this.getUpdateDt(),  m.getUpdateDt())) return false;
		if (!Comparison.areEqual(this.getUpdateUserId(),  m.getUpdateUserId())) return false;
		if (!Comparison.areEqual(this.getUpdateCount(),  m.getUpdateCount())) return false;
		if (!Comparison.areEqual(this.getActiveInd(),  m.getActiveInd())) return false;
		if (!Comparison.areEqual(this.getSystemAssignedVersionNbr(),  m.getSystemAssignedVersionNbr())) return false;
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
			sb.append("StaffModel:\r\n");
			sb.append("staff_id:" + getStaffId());
			sb.append("\r\n");
			sb.append("last_name:" + getLastName());
			sb.append("\r\n");
			sb.append("first_name:" + getFirstName());
			sb.append("\r\n");
			sb.append("display_name:" + getDisplayName());
			sb.append("\r\n");
			sb.append("staff_group_ref_id:" + getStaffGroupRefId());
			sb.append("\r\n");
			sb.append("staff_group_ref_display:" + getStaffGroupRefDisplay());
			sb.append("\r\n");
			sb.append("last_name_indexed:" + getLastNameIndexed());
			sb.append("\r\n");
			sb.append("first_name_indexed:" + getFirstNameIndexed());
			sb.append("\r\n");
			sb.append("create_dt:" + getCreateDt());
			sb.append("\r\n");
			sb.append("create_user_id:" + getCreateUserId());
			sb.append("\r\n");
			sb.append("create_user_name:" + getCreateUserName());
			sb.append("\r\n");
			sb.append("update_dt:" + getUpdateDt());
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
			return sb.toString();
		} catch (Exception ex) {
			return "StaffModel:"; 
		}
	}
}
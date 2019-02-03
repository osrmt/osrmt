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
public class ApplicationViewDataModel implements IControlModel, java.io.Serializable {

	static final long serialVersionUID = 1L;
	private int hashsize = 21;
	private Hashtable modifiedFields = new Hashtable(hashsize);
	private boolean referenceSet = false;


	/** Unique application setting identifier  */
	private int applicationViewId = 0;

	/** Reference application id  */
	private int applicationRefId = 0;

	private String applicationRefDisplay="";

	/** Foreign key of reference to application specific subtype eg. FeatureForm of ArtifactForm  */
	private int appTypeRefId = 0;

	private String appTypeRefDisplay="";

	/** Security id  */
	private int viewRefId = 0;

	private String viewRefDisplay="";

	/** Form title  */
	private String formTitle;

	/** Execute on form initialization after all controls have been initialized "components = Vector FormControls  */
	private String initScript;

	/** Execute prior to saving the form  */
	private String validationScript;

	/** Form pixel width  */
	private int formWidth = 0;

	/** Form pixel height  */
	private int formHeight = 0;

	/** Center form in the middle of the screen  */
	private int formCenterScreenInd = 0;

	/** Form pixel position on x axis  */
	private int formXPos = 0;

	/** Form pixel position on y axis  */
	private int formYPos = 0;

	/** Number of columns controls are laid out on  */
	private int formControlColumns = 0;

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
	 * Reference application id
	 * 
	 */ 
	public int getApplicationRefId() {
		return applicationRefId;
	}

	/** 
	 * Reference application id
	 * 
	 * Required database field.
	 */ 
	public void setApplicationRefId(int applicationRefId) {
		if (!Comparison.areEqual(this.applicationRefId, applicationRefId)) {
			this.applicationRefId = applicationRefId;
			setModified("applicationRefId");
		};
	}

	/** 
	 * Reference application id
	 * 
	 */ 
	public String getApplicationRefDisplay() {
		return applicationRefDisplay;
	}

	/** 
	 * Reference application id
	 * 
	 */ 
	public void setApplicationRefDisplay(String display) {
		this.applicationRefDisplay = display;
	}

	/** 
	 * Foreign key of reference to application specific subtype eg. FeatureForm of ArtifactForm
	 * 
	 */ 
	public int getAppTypeRefId() {
		return appTypeRefId;
	}

	/** 
	 * Foreign key of reference to application specific subtype eg. FeatureForm of ArtifactForm
	 * 
	 * Required database field.
	 */ 
	public void setAppTypeRefId(int appTypeRefId) {
		if (!Comparison.areEqual(this.appTypeRefId, appTypeRefId)) {
			this.appTypeRefId = appTypeRefId;
			setModified("appTypeRefId");
		};
	}

	/** 
	 * Foreign key of reference to application specific subtype eg. FeatureForm of ArtifactForm
	 * 
	 */ 
	public String getAppTypeRefDisplay() {
		return appTypeRefDisplay;
	}

	/** 
	 * Foreign key of reference to application specific subtype eg. FeatureForm of ArtifactForm
	 * 
	 */ 
	public void setAppTypeRefDisplay(String display) {
		this.appTypeRefDisplay = display;
	}

	/** 
	 * Security id
	 * 
	 */ 
	public int getViewRefId() {
		return viewRefId;
	}

	/** 
	 * Security id
	 * 
	 * Required database field.
	 */ 
	public void setViewRefId(int viewRefId) {
		if (!Comparison.areEqual(this.viewRefId, viewRefId)) {
			this.viewRefId = viewRefId;
			setModified("viewRefId");
		};
	}

	/** 
	 * Security id
	 * 
	 */ 
	public String getViewRefDisplay() {
		return viewRefDisplay;
	}

	/** 
	 * Security id
	 * 
	 */ 
	public void setViewRefDisplay(String display) {
		this.viewRefDisplay = display;
	}

	/** 
	 * Form title
	 * 
	 */ 
	public String getFormTitle() {
		return formTitle;
	}

	/** 
	 * Form title
	 * 
	 */ 
	public void setFormTitle(String formTitle) {
		if (!Comparison.areEqual(this.formTitle, formTitle)) {
			this.formTitle = formTitle;
			setModified("formTitle");
		};
	}

	/** 
	 * Execute on form initialization after all controls have been initialized "components = Vector FormControls
	 * 
	 */ 
	public String getInitScript() {
		return initScript;
	}

	/** 
	 * Execute on form initialization after all controls have been initialized "components = Vector FormControls
	 * 
	 */ 
	public void setInitScript(String initScript) {
		if (!Comparison.areEqual(this.initScript, initScript)) {
			this.initScript = initScript;
			setModified("initScript");
		};
	}

	/** 
	 * Execute prior to saving the form
	 * 
	 */ 
	public String getValidationScript() {
		return validationScript;
	}

	/** 
	 * Execute prior to saving the form
	 * 
	 */ 
	public void setValidationScript(String validationScript) {
		if (!Comparison.areEqual(this.validationScript, validationScript)) {
			this.validationScript = validationScript;
			setModified("validationScript");
		};
	}

	/** 
	 * Form pixel width
	 * 
	 */ 
	public int getFormWidth() {
		return formWidth;
	}

	/** 
	 * Form pixel width
	 * 
	 */ 
	public void setFormWidth(int formWidth) {
		if (!Comparison.areEqual(this.formWidth, formWidth)) {
			this.formWidth = formWidth;
			setModified("formWidth");
		};
	}

	/** 
	 * Form pixel height
	 * 
	 */ 
	public int getFormHeight() {
		return formHeight;
	}

	/** 
	 * Form pixel height
	 * 
	 */ 
	public void setFormHeight(int formHeight) {
		if (!Comparison.areEqual(this.formHeight, formHeight)) {
			this.formHeight = formHeight;
			setModified("formHeight");
		};
	}

	/** 
	 * Center form in the middle of the screen
	 * 
	 */ 
	public int getFormCenterScreenInd() {
		return formCenterScreenInd;
	}

	/** 
	 * Center form in the middle of the screen
	 * 
	 */ 
	public void setFormCenterScreenInd(int formCenterScreenInd) {
		if (!Comparison.areEqual(this.formCenterScreenInd, formCenterScreenInd)) {
			this.formCenterScreenInd = formCenterScreenInd;
			setModified("formCenterScreenInd");
		};
	}

	/** 
	 * Center form in the middle of the screen
	 * 
	 */ 
	public boolean isFormCenterScreen() {
		return formCenterScreenInd == 1;
	}

	/** 
	 * Center form in the middle of the screen
	 * 
	 */ 
	public boolean isNotFormCenterScreen() {
		return formCenterScreenInd == 0;
	}

	/** 
	 * Center form in the middle of the screen
	 * 
	 */ 
	public void setFormCenterScreen() {
		this.setFormCenterScreenInd(1);
	}

	public void setNotFormCenterScreen() {
		this.setFormCenterScreenInd(0);
	}

	/** 
	 * Form pixel position on x axis
	 * 
	 */ 
	public int getFormXPos() {
		return formXPos;
	}

	/** 
	 * Form pixel position on x axis
	 * 
	 */ 
	public void setFormXPos(int formXPos) {
		if (!Comparison.areEqual(this.formXPos, formXPos)) {
			this.formXPos = formXPos;
			setModified("formXPos");
		};
	}

	/** 
	 * Form pixel position on y axis
	 * 
	 */ 
	public int getFormYPos() {
		return formYPos;
	}

	/** 
	 * Form pixel position on y axis
	 * 
	 */ 
	public void setFormYPos(int formYPos) {
		if (!Comparison.areEqual(this.formYPos, formYPos)) {
			this.formYPos = formYPos;
			setModified("formYPos");
		};
	}

	/** 
	 * Number of columns controls are laid out on
	 * 
	 */ 
	public int getFormControlColumns() {
		return formControlColumns;
	}

	/** 
	 * Number of columns controls are laid out on
	 * 
	 */ 
	public void setFormControlColumns(int formControlColumns) {
		if (!Comparison.areEqual(this.formControlColumns, formControlColumns)) {
			this.formControlColumns = formControlColumns;
			setModified("formControlColumns");
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
		columns.addColumn("applicationViewId", Integer.class);
		columns.addColumn("applicationRefId", Integer.class);
		columns.addColumn("applicationRefDisplay", String.class);
		columns.addColumn("appTypeRefId", Integer.class);
		columns.addColumn("appTypeRefDisplay", String.class);
		columns.addColumn("viewRefId", Integer.class);
		columns.addColumn("viewRefDisplay", String.class);
		columns.addColumn("formTitle", String.class);
		columns.addColumn("initScript", String.class);
		columns.addColumn("validationScript", String.class);
		columns.addColumn("formWidth", Integer.class);
		columns.addColumn("formHeight", Integer.class);
		columns.addColumn("formCenterScreenInd", Integer.class);
		columns.addColumn("formXPos", Integer.class);
		columns.addColumn("formYPos", Integer.class);
		columns.addColumn("formControlColumns", Integer.class);
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
			if (reference != null && this.applicationRefId > 0) setApplicationRefDisplay(reference.getDisplay(this.applicationRefId));
			if (reference != null && this.appTypeRefId > 0) setAppTypeRefDisplay(reference.getDisplay(this.appTypeRefId));
			if (reference != null && this.viewRefId > 0) setViewRefDisplay(reference.getDisplay(this.viewRefId));
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
	public void copyModifiedTo(ApplicationViewDataModel m) {
		Enumeration e1 = modifiedFields.keys();
		while (e1.hasMoreElements()) {
			String fieldName = (String) e1.nextElement();

			if (fieldName.compareTo("applicationViewId")==0)
				m.setApplicationViewId(this.getApplicationViewId());
			else if (fieldName.compareTo("applicationRefId")==0)
				m.setApplicationRefId(this.getApplicationRefId());
			else if (fieldName.compareTo("appTypeRefId")==0)
				m.setAppTypeRefId(this.getAppTypeRefId());
			else if (fieldName.compareTo("viewRefId")==0)
				m.setViewRefId(this.getViewRefId());
			else if (fieldName.compareTo("formTitle")==0)
				m.setFormTitle(this.getFormTitle());
			else if (fieldName.compareTo("initScript")==0)
				m.setInitScript(this.getInitScript());
			else if (fieldName.compareTo("validationScript")==0)
				m.setValidationScript(this.getValidationScript());
			else if (fieldName.compareTo("formWidth")==0)
				m.setFormWidth(this.getFormWidth());
			else if (fieldName.compareTo("formHeight")==0)
				m.setFormHeight(this.getFormHeight());
			else if (fieldName.compareTo("formCenterScreenInd")==0)
				m.setFormCenterScreenInd(this.getFormCenterScreenInd());
			else if (fieldName.compareTo("formXPos")==0)
				m.setFormXPos(this.getFormXPos());
			else if (fieldName.compareTo("formYPos")==0)
				m.setFormYPos(this.getFormYPos());
			else if (fieldName.compareTo("formControlColumns")==0)
				m.setFormControlColumns(this.getFormControlColumns());
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
	public void updateWith (ApplicationViewModel m) {

		this.setApplicationViewId(m.getApplicationViewId());
		this.setApplicationRefId(m.getApplicationRefId());
		this.setApplicationRefDisplay(m.getApplicationRefDisplay());
		this.setAppTypeRefId(m.getAppTypeRefId());
		this.setAppTypeRefDisplay(m.getAppTypeRefDisplay());
		this.setViewRefId(m.getViewRefId());
		this.setViewRefDisplay(m.getViewRefDisplay());
		this.setFormTitle(m.getFormTitle());
		this.setInitScript(m.getInitScript());
		this.setValidationScript(m.getValidationScript());
		this.setFormWidth(m.getFormWidth());
		this.setFormHeight(m.getFormHeight());
		this.setFormCenterScreenInd(m.getFormCenterScreenInd());
		this.setFormXPos(m.getFormXPos());
		this.setFormYPos(m.getFormYPos());
		this.setFormControlColumns(m.getFormControlColumns());
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
	public boolean isEqualTo (ApplicationViewModel m) {

		if (!Comparison.areEqual(this.getApplicationViewId(),  m.getApplicationViewId())) return false;
		if (!Comparison.areEqual(this.getApplicationRefId(),  m.getApplicationRefId())) return false;
		if (!Comparison.areEqual(this.getAppTypeRefId(),  m.getAppTypeRefId())) return false;
		if (!Comparison.areEqual(this.getViewRefId(),  m.getViewRefId())) return false;
		if (!Comparison.areEqual(this.getFormTitle(),  m.getFormTitle())) return false;
		if (!Comparison.areEqual(this.getInitScript(),  m.getInitScript())) return false;
		if (!Comparison.areEqual(this.getValidationScript(),  m.getValidationScript())) return false;
		if (!Comparison.areEqual(this.getFormWidth(),  m.getFormWidth())) return false;
		if (!Comparison.areEqual(this.getFormHeight(),  m.getFormHeight())) return false;
		if (!Comparison.areEqual(this.getFormCenterScreenInd(),  m.getFormCenterScreenInd())) return false;
		if (!Comparison.areEqual(this.getFormXPos(),  m.getFormXPos())) return false;
		if (!Comparison.areEqual(this.getFormYPos(),  m.getFormYPos())) return false;
		if (!Comparison.areEqual(this.getFormControlColumns(),  m.getFormControlColumns())) return false;
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
			sb.append("ApplicationViewModel:\r\n");
			sb.append("application_view_id:" + getApplicationViewId());
			sb.append("\r\n");
			sb.append("application_ref_id:" + getApplicationRefId());
			sb.append("\r\n");
			sb.append("application_ref_display:" + getApplicationRefDisplay());
			sb.append("\r\n");
			sb.append("app_type_ref_id:" + getAppTypeRefId());
			sb.append("\r\n");
			sb.append("app_type_ref_display:" + getAppTypeRefDisplay());
			sb.append("\r\n");
			sb.append("view_ref_id:" + getViewRefId());
			sb.append("\r\n");
			sb.append("view_ref_display:" + getViewRefDisplay());
			sb.append("\r\n");
			sb.append("form_title:" + getFormTitle());
			sb.append("\r\n");
			sb.append("init_script:" + getInitScript());
			sb.append("\r\n");
			sb.append("validation_script:" + getValidationScript());
			sb.append("\r\n");
			sb.append("form_width:" + getFormWidth());
			sb.append("\r\n");
			sb.append("form_height:" + getFormHeight());
			sb.append("\r\n");
			sb.append("form_center_screen_ind:" + getFormCenterScreenInd());
			sb.append("\r\n");
			sb.append("form_x_pos:" + getFormXPos());
			sb.append("\r\n");
			sb.append("form_y_pos:" + getFormYPos());
			sb.append("\r\n");
			sb.append("form_control_columns:" + getFormControlColumns());
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
			return "ApplicationViewModel:"; 
		}
	}
	public void setModelColDataAt(Object o, int modelCol) {
			switch (modelCol) {
			case 1114: setApplicationViewId(((Integer) o).intValue()); break;
			case 1115: setApplicationRefId(((Integer) o).intValue()); break;
			case 1116: setAppTypeRefId(((Integer) o).intValue()); break;
			case 1117: setViewRefId(((Integer) o).intValue()); break;
			case 1248: setFormTitle((String) o); break;
			case 1249: setInitScript((String) o); break;
			case 1250: setValidationScript((String) o); break;
			case 1251: setFormWidth(((Integer) o).intValue()); break;
			case 1252: setFormHeight(((Integer) o).intValue()); break;
			case 1253: setFormCenterScreenInd(((Integer) o).intValue()); break;
			case 1254: setFormXPos(((Integer) o).intValue()); break;
			case 1255: setFormYPos(((Integer) o).intValue()); break;
			case 1256: setFormControlColumns(((Integer) o).intValue()); break;
			case 1118: setCreateDt((GregorianCalendar) o); break;
			case 1119: setCreateUserId(((Integer) o).intValue()); break;
			case 1130: setCreateUserName((String) o); break;
			case 1120: setUpdateDt((GregorianCalendar) o); break;
			case 1121: setUpdateUserId(((Integer) o).intValue()); break;
			case 1131: setUpdateUserName((String) o); break;
			case 1122: setUpdateCount(((Integer) o).intValue()); break;
			case 1123: setActiveInd(((Integer) o).intValue()); break;
			case 1124: setSystemAssignedVersionNbr(((Integer) o).intValue()); break;
			case 1125: setRecordTypeRefId(((Integer) o).intValue()); break;
			default: Debug.LogWarning(this, ReferenceServices.getMsg(SystemMessageFramework.SWITCHSTATEMENTFAILED) + " modelColRefId: " + modelCol);
	} 
}

	public Object getModelColDataAt(int modelCol) {
			if (modelCol == 1114) return new Integer(applicationViewId);
			if (modelCol == 1115) return new Integer(applicationRefId);
			if (modelCol == 1115) return applicationRefDisplay;
			if (modelCol == 1116) return new Integer(appTypeRefId);
			if (modelCol == 1116) return appTypeRefDisplay;
			if (modelCol == 1117) return new Integer(viewRefId);
			if (modelCol == 1117) return viewRefDisplay;
			if (modelCol == 1248) return formTitle;
			if (modelCol == 1249) return initScript;
			if (modelCol == 1250) return validationScript;
			if (modelCol == 1251) return new Integer(formWidth);
			if (modelCol == 1252) return new Integer(formHeight);
			if (modelCol == 1253) return new Integer(formCenterScreenInd);
			if (modelCol == 1254) return new Integer(formXPos);
			if (modelCol == 1255) return new Integer(formYPos);
			if (modelCol == 1256) return new Integer(formControlColumns);
			if (modelCol == 1118) return createDt;
			if (modelCol == 1119) return new Integer(createUserId);
			if (modelCol == 1119) return createUserName;
			if (modelCol == 1120) return updateDt;
			if (modelCol == 1121) return new Integer(updateUserId);
			if (modelCol == 1121) return updateUserName;
			if (modelCol == 1122) return new Integer(updateCount);
			if (modelCol == 1123) return new Integer(activeInd);
			if (modelCol == 1124) return new Integer(systemAssignedVersionNbr);
			if (modelCol == 1125) return new Integer(recordTypeRefId);
			if (modelCol == 1125) return recordTypeRefDisplay;
		return null;
	}

	public int getModelColDatabaseDataType(int modelCol) {
			if (modelCol == 1114) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1115) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1115) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1116) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1116) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1117) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1117) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1248) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1249) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1250) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1251) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1252) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1253) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1254) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1255) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1256) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1118) return DatabaseDataTypeFramework.DATE;
			if (modelCol == 1119) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1119) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1120) return DatabaseDataTypeFramework.DATE;
			if (modelCol == 1121) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1121) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1122) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1123) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1124) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1125) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1125) return DatabaseDataTypeFramework.STRING;
			return DatabaseDataTypeFramework.STRING;
	}

	public boolean isNew() {
		return getApplicationViewId() == 0;
	}

	public Object getPrimaryValue() {
		return new Integer(getApplicationViewId());
	}

	public Object getDataAt(int i) {
			if (i == 0) return new Integer(applicationViewId);
			if (i == 1) return new Integer(applicationRefId);
			if (i == 2) return applicationRefDisplay;
			if (i == 3) return new Integer(appTypeRefId);
			if (i == 4) return appTypeRefDisplay;
			if (i == 5) return new Integer(viewRefId);
			if (i == 6) return viewRefDisplay;
			if (i == 7) return formTitle;
			if (i == 8) return initScript;
			if (i == 9) return validationScript;
			if (i == 10) return new Integer(formWidth);
			if (i == 11) return new Integer(formHeight);
			if (i == 12) return new Integer(formCenterScreenInd);
			if (i == 13) return new Integer(formXPos);
			if (i == 14) return new Integer(formYPos);
			if (i == 15) return new Integer(formControlColumns);
			if (i == 16) return createDt;
			if (i == 17) return new Integer(createUserId);
			if (i == 18) return createUserName;
			if (i == 19) return updateDt;
			if (i == 20) return new Integer(updateUserId);
			if (i == 21) return updateUserName;
			if (i == 22) return new Integer(updateCount);
			if (i == 23) return new Integer(activeInd);
			if (i == 24) return new Integer(systemAssignedVersionNbr);
			if (i == 25) return new Integer(recordTypeRefId);
			if (i == 26) return recordTypeRefDisplay;
		return null;
	}

	public int getDatabaseDataType(int i) {
			if (i == 0) return DatabaseDataTypeFramework.INTEGER;
			if (i == 1) return DatabaseDataTypeFramework.INTEGER;
			if (i == 2) return DatabaseDataTypeFramework.STRING;
			if (i == 3) return DatabaseDataTypeFramework.INTEGER;
			if (i == 4) return DatabaseDataTypeFramework.STRING;
			if (i == 5) return DatabaseDataTypeFramework.INTEGER;
			if (i == 6) return DatabaseDataTypeFramework.STRING;
			if (i == 7) return DatabaseDataTypeFramework.STRING;
			if (i == 8) return DatabaseDataTypeFramework.STRING;
			if (i == 9) return DatabaseDataTypeFramework.STRING;
			if (i == 10) return DatabaseDataTypeFramework.INTEGER;
			if (i == 11) return DatabaseDataTypeFramework.INTEGER;
			if (i == 12) return DatabaseDataTypeFramework.INTEGER;
			if (i == 13) return DatabaseDataTypeFramework.INTEGER;
			if (i == 14) return DatabaseDataTypeFramework.INTEGER;
			if (i == 15) return DatabaseDataTypeFramework.INTEGER;
			if (i == 16) return DatabaseDataTypeFramework.DATE;
			if (i == 17) return DatabaseDataTypeFramework.INTEGER;
			if (i == 18) return DatabaseDataTypeFramework.STRING;
			if (i == 19) return DatabaseDataTypeFramework.DATE;
			if (i == 20) return DatabaseDataTypeFramework.INTEGER;
			if (i == 21) return DatabaseDataTypeFramework.STRING;
			if (i == 22) return DatabaseDataTypeFramework.INTEGER;
			if (i == 23) return DatabaseDataTypeFramework.INTEGER;
			if (i == 24) return DatabaseDataTypeFramework.INTEGER;
			if (i == 25) return DatabaseDataTypeFramework.INTEGER;
			if (i == 26) return DatabaseDataTypeFramework.STRING;
			return DatabaseDataTypeFramework.STRING;
	}

	public void setDataAt(Object o, int i) {
		switch(i) {
			case 0: setApplicationViewId(((Integer) o).intValue()); break;
			case 1: setApplicationRefId(((Integer) o).intValue()); break;
			case 2: setApplicationRefDisplay((String) o); break;
			case 3: setAppTypeRefId(((Integer) o).intValue()); break;
			case 4: setAppTypeRefDisplay((String) o); break;
			case 5: setViewRefId(((Integer) o).intValue()); break;
			case 6: setViewRefDisplay((String) o); break;
			case 7: setFormTitle((String) o); break;
			case 8: setInitScript((String) o); break;
			case 9: setValidationScript((String) o); break;
			case 10: setFormWidth(((Integer) o).intValue()); break;
			case 11: setFormHeight(((Integer) o).intValue()); break;
			case 12: setFormCenterScreenInd(((Integer) o).intValue()); break;
			case 13: setFormXPos(((Integer) o).intValue()); break;
			case 14: setFormYPos(((Integer) o).intValue()); break;
			case 15: setFormControlColumns(((Integer) o).intValue()); break;
			case 16: setCreateDt((GregorianCalendar) o); break;
			case 17: setCreateUserId(((Integer) o).intValue()); break;
			case 18: setCreateUserName((String) o); break;
			case 19: setUpdateDt((GregorianCalendar) o); break;
			case 20: setUpdateUserId(((Integer) o).intValue()); break;
			case 21: setUpdateUserName((String) o); break;
			case 22: setUpdateCount(((Integer) o).intValue()); break;
			case 23: setActiveInd(((Integer) o).intValue()); break;
			case 24: setSystemAssignedVersionNbr(((Integer) o).intValue()); break;
			case 25: setRecordTypeRefId(((Integer) o).intValue()); break;
			case 26: setRecordTypeRefDisplay((String) o); break;
		}
	}

}
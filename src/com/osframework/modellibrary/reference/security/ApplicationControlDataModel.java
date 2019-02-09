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
import com.osframework.modellibrary.reference.group.*;
import com.osframework.appclient.services.*;
import com.osframework.modellibrary.reference.security.*;

/**
null
*/
public class ApplicationControlDataModel implements IControlModel, java.io.Serializable {

	static final long serialVersionUID = 1L;
	private int hashsize = 34;
	private Hashtable modifiedFields = new Hashtable(hashsize);
	private boolean referenceSet = false;


	/** Unique identifier to the application control table  */
	private int applicationControlId = 0;

	/** Unique application setting identifier  */
	private int applicationViewId = 0;

	/** Sequential display of control within app  */
	private int displaySequence = 0;

	/** Foreign key to Reference - Control  */
	private int controlRefId = 0;

	private String controlRefDisplay="";

	/** Foreign key to Reference - ControlType  */
	private int controlTypeRefId = 0;

	private String controlTypeRefDisplay="";

	/** Label text  */
	private String controlText;

	/** Control description for tool tip  */
	private String controlDescription;

	/** Reference identifier for the model column to bind to  */
	private int modelColumnRefId = 0;

	private String modelColumnRefDisplay="";

	/** Foreign key to application_custom_control  */
	private int applicationCustomControlId = 0;

	/** Foreign key to app_control_user_defined  */
	private int appControlUserDefinedId = 0;

	/** Format mask for control  */
	private String controlFormat;

	/** Reference qualifying the source of this control  */
	private int sourceRefId = 0;

	private String sourceRefDisplay="";

	/** Default value for control  */
	private String defaultValue;

	/** Control locked from editing  */
	private int lockedInd = 0;

	/** Control disabled  */
	private int disabledInd = 0;

	/** Required data entry  */
	private int requiredInd = 0;

	/** Control is visible  */
	private int visibleInd = 1;

	/** Javascript executed when the control is first initialized  */
	private String initScript;

	/** Javascript executed when the control loses focus ref: control, model, components  */
	private String focusLostScript;

	/** Javascript executed when the control gains focus ref: control, model, components  */
	private String focusGainedScript;

	/** File path from /resources/images/  */
	private String imagePath;

	/** Wrap control in scrollpane 1=true 0=false  */
	private int scrollpaneInd = 0;

	/** 0=no growth >0 weighted value to grow  */
	private double growHeight = 0;

	/** 0=no growth >0 weighted value to grow  */
	private int growWidth = 1;

	/** Number of columns to span control with  */
	private int unitWidth = 1;

	/** Multiple of standard row height  */
	private int unitHeight = 1;

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
	 * Unique identifier to the application control table
	 * 
	 */ 
	public int getApplicationControlId() {
		return applicationControlId;
	}

	/** 
	 * Unique identifier to the application control table
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
	 * Sequential display of control within app
	 * 
	 */ 
	public int getDisplaySequence() {
		return displaySequence;
	}

	/** 
	 * Sequential display of control within app
	 * 
	 * Required database field.
	 */ 
	public void setDisplaySequence(int displaySequence) {
		if (!Comparison.areEqual(this.displaySequence, displaySequence)) {
			this.displaySequence = displaySequence;
			setModified("displaySequence");
		};
	}

	/** 
	 * Foreign key to Reference - Control
	 * 
	 */ 
	public int getControlRefId() {
		return controlRefId;
	}

	/** 
	 * Foreign key to Reference - Control
	 * 
	 * Required database field.
	 */ 
	public void setControlRefId(int controlRefId) {
		if (!Comparison.areEqual(this.controlRefId, controlRefId)) {
			this.controlRefId = controlRefId;
			setModified("controlRefId");
		};
	}

	/** 
	 * Foreign key to Reference - Control
	 * 
	 */ 
	public String getControlRefDisplay() {
		return controlRefDisplay;
	}

	/** 
	 * Foreign key to Reference - Control
	 * 
	 */ 
	public void setControlRefDisplay(String display) {
		this.controlRefDisplay = display;
	}

	/** 
	 * Foreign key to Reference - ControlType
	 * 
	 */ 
	public int getControlTypeRefId() {
		return controlTypeRefId;
	}

	/** 
	 * Foreign key to Reference - ControlType
	 * 
	 * Required database field.
	 */ 
	public void setControlTypeRefId(int controlTypeRefId) {
		if (!Comparison.areEqual(this.controlTypeRefId, controlTypeRefId)) {
			this.controlTypeRefId = controlTypeRefId;
			setModified("controlTypeRefId");
		};
	}

	/** 
	 * Foreign key to Reference - ControlType
	 * 
	 */ 
	public String getControlTypeRefDisplay() {
		return controlTypeRefDisplay;
	}

	/** 
	 * Foreign key to Reference - ControlType
	 * 
	 */ 
	public void setControlTypeRefDisplay(String display) {
		this.controlTypeRefDisplay = display;
	}

	/** 
	 * Label text
	 * 
	 */ 
	public String getControlText() {
		return controlText;
	}

	/** 
	 * Label text
	 * 
	 */ 
	public void setControlText(String controlText) {
		if (!Comparison.areEqual(this.controlText, controlText)) {
			this.controlText = controlText;
			setModified("controlText");
		};
	}

	/** 
	 * Control description for tool tip
	 * 
	 */ 
	public String getControlDescription() {
		return controlDescription;
	}

	/** 
	 * Control description for tool tip
	 * 
	 */ 
	public void setControlDescription(String controlDescription) {
		if (!Comparison.areEqual(this.controlDescription, controlDescription)) {
			this.controlDescription = controlDescription;
			setModified("controlDescription");
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
	 * Foreign key to application_custom_control
	 * 
	 */ 
	public int getApplicationCustomControlId() {
		return applicationCustomControlId;
	}

	/** 
	 * Foreign key to application_custom_control
	 * 
	 * Required database field.
	 */ 
	public void setApplicationCustomControlId(int applicationCustomControlId) {
		if (!Comparison.areEqual(this.applicationCustomControlId, applicationCustomControlId)) {
			this.applicationCustomControlId = applicationCustomControlId;
			setModified("applicationCustomControlId");
		};
	}

	/** 
	 * Foreign key to app_control_user_defined
	 * 
	 */ 
	public int getAppControlUserDefinedId() {
		return appControlUserDefinedId;
	}

	/** 
	 * Foreign key to app_control_user_defined
	 * 
	 * Required database field.
	 */ 
	public void setAppControlUserDefinedId(int appControlUserDefinedId) {
		if (!Comparison.areEqual(this.appControlUserDefinedId, appControlUserDefinedId)) {
			this.appControlUserDefinedId = appControlUserDefinedId;
			setModified("appControlUserDefinedId");
		};
	}

	/** 
	 * Format mask for control
	 * 
	 */ 
	public String getControlFormat() {
		return controlFormat;
	}

	/** 
	 * Format mask for control
	 * 
	 */ 
	public void setControlFormat(String controlFormat) {
		if (!Comparison.areEqual(this.controlFormat, controlFormat)) {
			this.controlFormat = controlFormat;
			setModified("controlFormat");
		};
	}

	/** 
	 * Reference qualifying the source of this control
	 * 
	 */ 
	public int getSourceRefId() {
		return sourceRefId;
	}

	/** 
	 * Reference qualifying the source of this control
	 * 
	 * Required database field.
	 */ 
	public void setSourceRefId(int sourceRefId) {
		if (!Comparison.areEqual(this.sourceRefId, sourceRefId)) {
			this.sourceRefId = sourceRefId;
			setModified("sourceRefId");
		};
	}

	/** 
	 * Reference qualifying the source of this control
	 * 
	 */ 
	public String getSourceRefDisplay() {
		return sourceRefDisplay;
	}

	/** 
	 * Reference qualifying the source of this control
	 * 
	 */ 
	public void setSourceRefDisplay(String display) {
		this.sourceRefDisplay = display;
	}

	/** 
	 * Default value for control
	 * 
	 */ 
	public String getDefaultValue() {
		return defaultValue;
	}

	/** 
	 * Default value for control
	 * 
	 */ 
	public void setDefaultValue(String defaultValue) {
		if (!Comparison.areEqual(this.defaultValue, defaultValue)) {
			this.defaultValue = defaultValue;
			setModified("defaultValue");
		};
	}

	/** 
	 * Control locked from editing
	 * 
	 */ 
	public int getLockedInd() {
		return lockedInd;
	}

	/** 
	 * Control locked from editing
	 * 
	 * Required database field.
	 */ 
	public void setLockedInd(int lockedInd) {
		if (!Comparison.areEqual(this.lockedInd, lockedInd)) {
			this.lockedInd = lockedInd;
			setModified("lockedInd");
		};
	}

	/** 
	 * Control locked from editing
	 * 
	 */ 
	public boolean isLocked() {
		return lockedInd == 1;
	}

	/** 
	 * Control locked from editing
	 * 
	 */ 
	public boolean isNotLocked() {
		return lockedInd == 0;
	}

	/** 
	 * Control locked from editing
	 * 
	 */ 
	public void setLocked() {
		this.setLockedInd(1);
	}

	public void setNotLocked() {
		this.setLockedInd(0);
	}

	/** 
	 * Control disabled
	 * 
	 */ 
	public int getDisabledInd() {
		return disabledInd;
	}

	/** 
	 * Control disabled
	 * 
	 * Required database field.
	 */ 
	public void setDisabledInd(int disabledInd) {
		if (!Comparison.areEqual(this.disabledInd, disabledInd)) {
			this.disabledInd = disabledInd;
			setModified("disabledInd");
		};
	}

	/** 
	 * Control disabled
	 * 
	 */ 
	public boolean isDisabled() {
		return disabledInd == 1;
	}

	/** 
	 * Control disabled
	 * 
	 */ 
	public boolean isNotDisabled() {
		return disabledInd == 0;
	}

	/** 
	 * Control disabled
	 * 
	 */ 
	public void setDisabled() {
		this.setDisabledInd(1);
	}

	public void setNotDisabled() {
		this.setDisabledInd(0);
	}

	/** 
	 * Required data entry
	 * 
	 */ 
	public int getRequiredInd() {
		return requiredInd;
	}

	/** 
	 * Required data entry
	 * 
	 * Required database field.
	 */ 
	public void setRequiredInd(int requiredInd) {
		if (!Comparison.areEqual(this.requiredInd, requiredInd)) {
			this.requiredInd = requiredInd;
			setModified("requiredInd");
		};
	}

	/** 
	 * Required data entry
	 * 
	 */ 
	public boolean isRequired() {
		return requiredInd == 1;
	}

	/** 
	 * Required data entry
	 * 
	 */ 
	public boolean isNotRequired() {
		return requiredInd == 0;
	}

	/** 
	 * Required data entry
	 * 
	 */ 
	public void setRequired() {
		this.setRequiredInd(1);
	}

	public void setNotRequired() {
		this.setRequiredInd(0);
	}

	/** 
	 * Control is visible
	 * 
	 */ 
	public int getVisibleInd() {
		return visibleInd;
	}

	/** 
	 * Control is visible
	 * 
	 * Required database field.
	 */ 
	public void setVisibleInd(int visibleInd) {
		if (!Comparison.areEqual(this.visibleInd, visibleInd)) {
			this.visibleInd = visibleInd;
			setModified("visibleInd");
		};
	}

	/** 
	 * Control is visible
	 * 
	 */ 
	public boolean isVisible() {
		return visibleInd == 1;
	}

	/** 
	 * Control is visible
	 * 
	 */ 
	public boolean isNotVisible() {
		return visibleInd == 0;
	}

	/** 
	 * Control is visible
	 * 
	 */ 
	public void setVisible() {
		this.setVisibleInd(1);
	}

	public void setNotVisible() {
		this.setVisibleInd(0);
	}

	/** 
	 * Javascript executed when the control is first initialized
	 * 
	 */ 
	public String getInitScript() {
		return initScript;
	}

	/** 
	 * Javascript executed when the control is first initialized
	 * 
	 */ 
	public void setInitScript(String initScript) {
		if (!Comparison.areEqual(this.initScript, initScript)) {
			this.initScript = initScript;
			setModified("initScript");
		};
	}

	/** 
	 * Javascript executed when the control loses focus ref: control, model, components
	 * 
	 */ 
	public String getFocusLostScript() {
		return focusLostScript;
	}

	/** 
	 * Javascript executed when the control loses focus ref: control, model, components
	 * 
	 */ 
	public void setFocusLostScript(String focusLostScript) {
		if (!Comparison.areEqual(this.focusLostScript, focusLostScript)) {
			this.focusLostScript = focusLostScript;
			setModified("focusLostScript");
		};
	}

	/** 
	 * Javascript executed when the control gains focus ref: control, model, components
	 * 
	 */ 
	public String getFocusGainedScript() {
		return focusGainedScript;
	}

	/** 
	 * Javascript executed when the control gains focus ref: control, model, components
	 * 
	 */ 
	public void setFocusGainedScript(String focusGainedScript) {
		if (!Comparison.areEqual(this.focusGainedScript, focusGainedScript)) {
			this.focusGainedScript = focusGainedScript;
			setModified("focusGainedScript");
		};
	}

	/** 
	 * File path from /resources/images/
	 * 
	 */ 
	public String getImagePath() {
		return imagePath;
	}

	/** 
	 * File path from /resources/images/
	 * 
	 */ 
	public void setImagePath(String imagePath) {
		if (!Comparison.areEqual(this.imagePath, imagePath)) {
			this.imagePath = imagePath;
			setModified("imagePath");
		};
	}

	/** 
	 * Wrap control in scrollpane 1=true 0=false
	 * 
	 */ 
	public int getScrollpaneInd() {
		return scrollpaneInd;
	}

	/** 
	 * Wrap control in scrollpane 1=true 0=false
	 * 
	 * Required database field.
	 */ 
	public void setScrollpaneInd(int scrollpaneInd) {
		if (!Comparison.areEqual(this.scrollpaneInd, scrollpaneInd)) {
			this.scrollpaneInd = scrollpaneInd;
			setModified("scrollpaneInd");
		};
	}

	/** 
	 * Wrap control in scrollpane 1=true 0=false
	 * 
	 */ 
	public boolean isScrollpane() {
		return scrollpaneInd == 1;
	}

	/** 
	 * Wrap control in scrollpane 1=true 0=false
	 * 
	 */ 
	public boolean isNotScrollpane() {
		return scrollpaneInd == 0;
	}

	/** 
	 * Wrap control in scrollpane 1=true 0=false
	 * 
	 */ 
	public void setScrollpane() {
		this.setScrollpaneInd(1);
	}

	public void setNotScrollpane() {
		this.setScrollpaneInd(0);
	}

	/** 
	 * 0=no growth >0 weighted value to grow
	 * 
	 */ 
	public double getGrowHeight() {
		return growHeight;
	}

	/** 
	 * 0=no growth >0 weighted value to grow
	 * 
	 * Required database field.
	 */ 
	public void setGrowHeight(double growHeight) {
		if (!Comparison.areEqual(this.growHeight, growHeight)) {
			this.growHeight = growHeight;
			setModified("growHeight");
		};
	}

	/** 
	 * 0=no growth >0 weighted value to grow
	 * 
	 */ 
	public int getGrowWidth() {
		return growWidth;
	}

	/** 
	 * 0=no growth >0 weighted value to grow
	 * 
	 * Required database field.
	 */ 
	public void setGrowWidth(int growWidth) {
		if (!Comparison.areEqual(this.growWidth, growWidth)) {
			this.growWidth = growWidth;
			setModified("growWidth");
		};
	}

	/** 
	 * Number of columns to span control with
	 * 
	 */ 
	public int getUnitWidth() {
		return unitWidth;
	}

	/** 
	 * Number of columns to span control with
	 * 
	 * Required database field.
	 */ 
	public void setUnitWidth(int unitWidth) {
		if (!Comparison.areEqual(this.unitWidth, unitWidth)) {
			this.unitWidth = unitWidth;
			setModified("unitWidth");
		};
	}

	/** 
	 * Multiple of standard row height
	 * 
	 */ 
	public int getUnitHeight() {
		return unitHeight;
	}

	/** 
	 * Multiple of standard row height
	 * 
	 * Required database field.
	 */ 
	public void setUnitHeight(int unitHeight) {
		if (!Comparison.areEqual(this.unitHeight, unitHeight)) {
			this.unitHeight = unitHeight;
			setModified("unitHeight");
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
		columns.addColumn("applicationControlId", Integer.class);
		columns.addColumn("applicationViewId", Integer.class);
		columns.addColumn("displaySequence", Integer.class);
		columns.addColumn("controlRefId", Integer.class);
		columns.addColumn("controlRefDisplay", String.class);
		columns.addColumn("controlTypeRefId", Integer.class);
		columns.addColumn("controlTypeRefDisplay", String.class);
		columns.addColumn("controlText", String.class);
		columns.addColumn("controlDescription", String.class);
		columns.addColumn("modelColumnRefId", Integer.class);
		columns.addColumn("modelColumnRefDisplay", String.class);
		columns.addColumn("applicationCustomControlId", Integer.class);
		columns.addColumn("appControlUserDefinedId", Integer.class);
		columns.addColumn("controlFormat", String.class);
		columns.addColumn("sourceRefId", Integer.class);
		columns.addColumn("sourceRefDisplay", String.class);
		columns.addColumn("defaultValue", String.class);
		columns.addColumn("lockedInd", Integer.class);
		columns.addColumn("disabledInd", Integer.class);
		columns.addColumn("requiredInd", Integer.class);
		columns.addColumn("visibleInd", Integer.class);
		columns.addColumn("initScript", String.class);
		columns.addColumn("focusLostScript", String.class);
		columns.addColumn("focusGainedScript", String.class);
		columns.addColumn("imagePath", String.class);
		columns.addColumn("scrollpaneInd", Integer.class);
		columns.addColumn("growHeight", Double.class);
		columns.addColumn("growWidth", Integer.class);
		columns.addColumn("unitWidth", Integer.class);
		columns.addColumn("unitHeight", Integer.class);
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
			if (reference != null && this.controlRefId > 0) setControlRefDisplay(reference.getDisplay(this.controlRefId));
			if (reference != null && this.controlTypeRefId > 0) setControlTypeRefDisplay(reference.getDisplay(this.controlTypeRefId));
			if (reference != null && this.modelColumnRefId > 0) setModelColumnRefDisplay(reference.getDisplay(this.modelColumnRefId));
			if (reference != null && this.sourceRefId > 0) setSourceRefDisplay(reference.getDisplay(this.sourceRefId));
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
	public void copyModifiedTo(ApplicationControlDataModel m) {
		Enumeration e1 = modifiedFields.keys();
		while (e1.hasMoreElements()) {
			String fieldName = (String) e1.nextElement();

			if (fieldName.compareTo("applicationControlId")==0)
				m.setApplicationControlId(this.getApplicationControlId());
			else if (fieldName.compareTo("applicationViewId")==0)
				m.setApplicationViewId(this.getApplicationViewId());
			else if (fieldName.compareTo("displaySequence")==0)
				m.setDisplaySequence(this.getDisplaySequence());
			else if (fieldName.compareTo("controlRefId")==0)
				m.setControlRefId(this.getControlRefId());
			else if (fieldName.compareTo("controlTypeRefId")==0)
				m.setControlTypeRefId(this.getControlTypeRefId());
			else if (fieldName.compareTo("controlText")==0)
				m.setControlText(this.getControlText());
			else if (fieldName.compareTo("controlDescription")==0)
				m.setControlDescription(this.getControlDescription());
			else if (fieldName.compareTo("modelColumnRefId")==0)
				m.setModelColumnRefId(this.getModelColumnRefId());
			else if (fieldName.compareTo("applicationCustomControlId")==0)
				m.setApplicationCustomControlId(this.getApplicationCustomControlId());
			else if (fieldName.compareTo("appControlUserDefinedId")==0)
				m.setAppControlUserDefinedId(this.getAppControlUserDefinedId());
			else if (fieldName.compareTo("controlFormat")==0)
				m.setControlFormat(this.getControlFormat());
			else if (fieldName.compareTo("sourceRefId")==0)
				m.setSourceRefId(this.getSourceRefId());
			else if (fieldName.compareTo("defaultValue")==0)
				m.setDefaultValue(this.getDefaultValue());
			else if (fieldName.compareTo("lockedInd")==0)
				m.setLockedInd(this.getLockedInd());
			else if (fieldName.compareTo("disabledInd")==0)
				m.setDisabledInd(this.getDisabledInd());
			else if (fieldName.compareTo("requiredInd")==0)
				m.setRequiredInd(this.getRequiredInd());
			else if (fieldName.compareTo("visibleInd")==0)
				m.setVisibleInd(this.getVisibleInd());
			else if (fieldName.compareTo("initScript")==0)
				m.setInitScript(this.getInitScript());
			else if (fieldName.compareTo("focusLostScript")==0)
				m.setFocusLostScript(this.getFocusLostScript());
			else if (fieldName.compareTo("focusGainedScript")==0)
				m.setFocusGainedScript(this.getFocusGainedScript());
			else if (fieldName.compareTo("imagePath")==0)
				m.setImagePath(this.getImagePath());
			else if (fieldName.compareTo("scrollpaneInd")==0)
				m.setScrollpaneInd(this.getScrollpaneInd());
			else if (fieldName.compareTo("growHeight")==0)
				m.setGrowHeight(this.getGrowHeight());
			else if (fieldName.compareTo("growWidth")==0)
				m.setGrowWidth(this.getGrowWidth());
			else if (fieldName.compareTo("unitWidth")==0)
				m.setUnitWidth(this.getUnitWidth());
			else if (fieldName.compareTo("unitHeight")==0)
				m.setUnitHeight(this.getUnitHeight());
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
	public void updateWith (ApplicationControlModel m) {

		this.setApplicationControlId(m.getApplicationControlId());
		this.setApplicationViewId(m.getApplicationViewId());
		this.setDisplaySequence(m.getDisplaySequence());
		this.setControlRefId(m.getControlRefId());
		this.setControlRefDisplay(m.getControlRefDisplay());
		this.setControlTypeRefId(m.getControlTypeRefId());
		this.setControlTypeRefDisplay(m.getControlTypeRefDisplay());
		this.setControlText(m.getControlText());
		this.setControlDescription(m.getControlDescription());
		this.setModelColumnRefId(m.getModelColumnRefId());
		this.setModelColumnRefDisplay(m.getModelColumnRefDisplay());
		this.setApplicationCustomControlId(m.getApplicationCustomControlId());
		this.setAppControlUserDefinedId(m.getAppControlUserDefinedId());
		this.setControlFormat(m.getControlFormat());
		this.setSourceRefId(m.getSourceRefId());
		this.setSourceRefDisplay(m.getSourceRefDisplay());
		this.setDefaultValue(m.getDefaultValue());
		this.setLockedInd(m.getLockedInd());
		this.setDisabledInd(m.getDisabledInd());
		this.setRequiredInd(m.getRequiredInd());
		this.setVisibleInd(m.getVisibleInd());
		this.setInitScript(m.getInitScript());
		this.setFocusLostScript(m.getFocusLostScript());
		this.setFocusGainedScript(m.getFocusGainedScript());
		this.setImagePath(m.getImagePath());
		this.setScrollpaneInd(m.getScrollpaneInd());
		this.setGrowHeight(m.getGrowHeight());
		this.setGrowWidth(m.getGrowWidth());
		this.setUnitWidth(m.getUnitWidth());
		this.setUnitHeight(m.getUnitHeight());
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
	public boolean isEqualTo (ApplicationControlModel m) {

		if (!Comparison.areEqual(this.getApplicationControlId(),  m.getApplicationControlId())) return false;
		if (!Comparison.areEqual(this.getApplicationViewId(),  m.getApplicationViewId())) return false;
		if (!Comparison.areEqual(this.getDisplaySequence(),  m.getDisplaySequence())) return false;
		if (!Comparison.areEqual(this.getControlRefId(),  m.getControlRefId())) return false;
		if (!Comparison.areEqual(this.getControlTypeRefId(),  m.getControlTypeRefId())) return false;
		if (!Comparison.areEqual(this.getControlText(),  m.getControlText())) return false;
		if (!Comparison.areEqual(this.getControlDescription(),  m.getControlDescription())) return false;
		if (!Comparison.areEqual(this.getModelColumnRefId(),  m.getModelColumnRefId())) return false;
		if (!Comparison.areEqual(this.getApplicationCustomControlId(),  m.getApplicationCustomControlId())) return false;
		if (!Comparison.areEqual(this.getAppControlUserDefinedId(),  m.getAppControlUserDefinedId())) return false;
		if (!Comparison.areEqual(this.getControlFormat(),  m.getControlFormat())) return false;
		if (!Comparison.areEqual(this.getSourceRefId(),  m.getSourceRefId())) return false;
		if (!Comparison.areEqual(this.getDefaultValue(),  m.getDefaultValue())) return false;
		if (!Comparison.areEqual(this.getLockedInd(),  m.getLockedInd())) return false;
		if (!Comparison.areEqual(this.getDisabledInd(),  m.getDisabledInd())) return false;
		if (!Comparison.areEqual(this.getRequiredInd(),  m.getRequiredInd())) return false;
		if (!Comparison.areEqual(this.getVisibleInd(),  m.getVisibleInd())) return false;
		if (!Comparison.areEqual(this.getInitScript(),  m.getInitScript())) return false;
		if (!Comparison.areEqual(this.getFocusLostScript(),  m.getFocusLostScript())) return false;
		if (!Comparison.areEqual(this.getFocusGainedScript(),  m.getFocusGainedScript())) return false;
		if (!Comparison.areEqual(this.getImagePath(),  m.getImagePath())) return false;
		if (!Comparison.areEqual(this.getScrollpaneInd(),  m.getScrollpaneInd())) return false;
		if (!Comparison.areEqual(this.getGrowHeight(),  m.getGrowHeight())) return false;
		if (!Comparison.areEqual(this.getGrowWidth(),  m.getGrowWidth())) return false;
		if (!Comparison.areEqual(this.getUnitWidth(),  m.getUnitWidth())) return false;
		if (!Comparison.areEqual(this.getUnitHeight(),  m.getUnitHeight())) return false;
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
			sb.append("ApplicationControlModel:\r\n");
			sb.append("application_control_id:" + getApplicationControlId());
			sb.append("\r\n");
			sb.append("application_view_id:" + getApplicationViewId());
			sb.append("\r\n");
			sb.append("display_sequence:" + getDisplaySequence());
			sb.append("\r\n");
			sb.append("control_ref_id:" + getControlRefId());
			sb.append("\r\n");
			sb.append("control_ref_display:" + getControlRefDisplay());
			sb.append("\r\n");
			sb.append("control_type_ref_id:" + getControlTypeRefId());
			sb.append("\r\n");
			sb.append("control_type_ref_display:" + getControlTypeRefDisplay());
			sb.append("\r\n");
			sb.append("control_text:" + getControlText());
			sb.append("\r\n");
			sb.append("control_description:" + getControlDescription());
			sb.append("\r\n");
			sb.append("model_column_ref_id:" + getModelColumnRefId());
			sb.append("\r\n");
			sb.append("model_column_ref_display:" + getModelColumnRefDisplay());
			sb.append("\r\n");
			sb.append("application_custom_control_id:" + getApplicationCustomControlId());
			sb.append("\r\n");
			sb.append("app_control_user_defined_id:" + getAppControlUserDefinedId());
			sb.append("\r\n");
			sb.append("control_format:" + getControlFormat());
			sb.append("\r\n");
			sb.append("source_ref_id:" + getSourceRefId());
			sb.append("\r\n");
			sb.append("source_ref_display:" + getSourceRefDisplay());
			sb.append("\r\n");
			sb.append("default_value:" + getDefaultValue());
			sb.append("\r\n");
			sb.append("locked_ind:" + getLockedInd());
			sb.append("\r\n");
			sb.append("disabled_ind:" + getDisabledInd());
			sb.append("\r\n");
			sb.append("required_ind:" + getRequiredInd());
			sb.append("\r\n");
			sb.append("visible_ind:" + getVisibleInd());
			sb.append("\r\n");
			sb.append("init_script:" + getInitScript());
			sb.append("\r\n");
			sb.append("focus_lost_script:" + getFocusLostScript());
			sb.append("\r\n");
			sb.append("focus_gained_script:" + getFocusGainedScript());
			sb.append("\r\n");
			sb.append("image_path:" + getImagePath());
			sb.append("\r\n");
			sb.append("scrollpane_ind:" + getScrollpaneInd());
			sb.append("\r\n");
			sb.append("grow_height:" + getGrowHeight());
			sb.append("\r\n");
			sb.append("grow_width:" + getGrowWidth());
			sb.append("\r\n");
			sb.append("unit_width:" + getUnitWidth());
			sb.append("\r\n");
			sb.append("unit_height:" + getUnitHeight());
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
			return "ApplicationControlModel:"; 
		}
	}
	public void setModelColDataAt(Object o, int modelCol) {
			switch (modelCol) {
			case 565: setApplicationControlId(((Integer) o).intValue()); break;
			case 1112: setApplicationViewId(((Integer) o).intValue()); break;
			case 569: setDisplaySequence(((Integer) o).intValue()); break;
			case 570: setControlRefId(((Integer) o).intValue()); break;
			case 571: setControlTypeRefId(((Integer) o).intValue()); break;
			case 572: setControlText((String) o); break;
			case 573: setControlDescription((String) o); break;
			case 792: setModelColumnRefId(((Integer) o).intValue()); break;
			case 575: setApplicationCustomControlId(((Integer) o).intValue()); break;
			case 576: setAppControlUserDefinedId(((Integer) o).intValue()); break;
			case 577: setControlFormat((String) o); break;
			case 578: setSourceRefId(((Integer) o).intValue()); break;
			case 579: setDefaultValue((String) o); break;
			case 580: setLockedInd(((Integer) o).intValue()); break;
			case 581: setDisabledInd(((Integer) o).intValue()); break;
			case 582: setRequiredInd(((Integer) o).intValue()); break;
			case 583: setVisibleInd(((Integer) o).intValue()); break;
			case 584: setInitScript((String) o); break;
			case 585: setFocusLostScript((String) o); break;
			case 586: setFocusGainedScript((String) o); break;
			case 587: setImagePath((String) o); break;
			case 589: setScrollpaneInd(((Integer) o).intValue()); break;
			case 1170: setGrowHeight(((Double) o).doubleValue()); break;
			case 1171: setGrowWidth(((Integer) o).intValue()); break;
			case 1172: setUnitWidth(((Integer) o).intValue()); break;
			case 1173: setUnitHeight(((Integer) o).intValue()); break;
			case 591: setCreateDt((GregorianCalendar) o); break;
			case 592: setCreateUserId(((Integer) o).intValue()); break;
			case 823: setCreateUserName((String) o); break;
			case 593: setUpdateDt((GregorianCalendar) o); break;
			case 594: setUpdateUserId(((Integer) o).intValue()); break;
			case 824: setUpdateUserName((String) o); break;
			case 595: setUpdateCount(((Integer) o).intValue()); break;
			case 596: setActiveInd(((Integer) o).intValue()); break;
			case 597: setSystemAssignedVersionNbr(((Integer) o).intValue()); break;
			case 598: setRecordTypeRefId(((Integer) o).intValue()); break;
			default: Debug.LogWarning(this, ReferenceServices.getMsg(SystemMessageFramework.SWITCHSTATEMENTFAILED) + " modelColRefId: " + modelCol);
	} 
}

	public Object getModelColDataAt(int modelCol) {
			if (modelCol == 565) return new Integer(applicationControlId);
			if (modelCol == 1112) return new Integer(applicationViewId);
			if (modelCol == 569) return new Integer(displaySequence);
			if (modelCol == 570) return new Integer(controlRefId);
			if (modelCol == 570) return controlRefDisplay;
			if (modelCol == 571) return new Integer(controlTypeRefId);
			if (modelCol == 571) return controlTypeRefDisplay;
			if (modelCol == 572) return controlText;
			if (modelCol == 573) return controlDescription;
			if (modelCol == 792) return new Integer(modelColumnRefId);
			if (modelCol == 792) return modelColumnRefDisplay;
			if (modelCol == 575) return new Integer(applicationCustomControlId);
			if (modelCol == 576) return new Integer(appControlUserDefinedId);
			if (modelCol == 577) return controlFormat;
			if (modelCol == 578) return new Integer(sourceRefId);
			if (modelCol == 578) return sourceRefDisplay;
			if (modelCol == 579) return defaultValue;
			if (modelCol == 580) return new Integer(lockedInd);
			if (modelCol == 581) return new Integer(disabledInd);
			if (modelCol == 582) return new Integer(requiredInd);
			if (modelCol == 583) return new Integer(visibleInd);
			if (modelCol == 584) return initScript;
			if (modelCol == 585) return focusLostScript;
			if (modelCol == 586) return focusGainedScript;
			if (modelCol == 587) return imagePath;
			if (modelCol == 589) return new Integer(scrollpaneInd);
			if (modelCol == 1170) return new Double(growHeight);
			if (modelCol == 1171) return new Integer(growWidth);
			if (modelCol == 1172) return new Integer(unitWidth);
			if (modelCol == 1173) return new Integer(unitHeight);
			if (modelCol == 591) return createDt;
			if (modelCol == 592) return new Integer(createUserId);
			if (modelCol == 592) return createUserName;
			if (modelCol == 593) return updateDt;
			if (modelCol == 594) return new Integer(updateUserId);
			if (modelCol == 594) return updateUserName;
			if (modelCol == 595) return new Integer(updateCount);
			if (modelCol == 596) return new Integer(activeInd);
			if (modelCol == 597) return new Integer(systemAssignedVersionNbr);
			if (modelCol == 598) return new Integer(recordTypeRefId);
			if (modelCol == 598) return recordTypeRefDisplay;
		return null;
	}

	public int getModelColDatabaseDataType(int modelCol) {
			if (modelCol == 565) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1112) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 569) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 570) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 570) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 571) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 571) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 572) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 573) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 792) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 792) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 575) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 576) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 577) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 578) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 578) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 579) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 580) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 581) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 582) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 583) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 584) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 585) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 586) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 587) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 589) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1170) return DatabaseDataTypeFramework.DOUBLE;
			if (modelCol == 1171) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1172) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1173) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 591) return DatabaseDataTypeFramework.DATE;
			if (modelCol == 592) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 592) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 593) return DatabaseDataTypeFramework.DATE;
			if (modelCol == 594) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 594) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 595) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 596) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 597) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 598) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 598) return DatabaseDataTypeFramework.STRING;
			return DatabaseDataTypeFramework.STRING;
	}

	public boolean isNew() {
		return getApplicationControlId() == 0;
	}

	public Object getPrimaryValue() {
		return new Integer(getApplicationControlId());
	}

	public Object getDataAt(int i) {
			if (i == 0) return new Integer(applicationControlId);
			if (i == 1) return new Integer(applicationViewId);
			if (i == 2) return new Integer(displaySequence);
			if (i == 3) return new Integer(controlRefId);
			if (i == 4) return controlRefDisplay;
			if (i == 5) return new Integer(controlTypeRefId);
			if (i == 6) return controlTypeRefDisplay;
			if (i == 7) return controlText;
			if (i == 8) return controlDescription;
			if (i == 9) return new Integer(modelColumnRefId);
			if (i == 10) return modelColumnRefDisplay;
			if (i == 11) return new Integer(applicationCustomControlId);
			if (i == 12) return new Integer(appControlUserDefinedId);
			if (i == 13) return controlFormat;
			if (i == 14) return new Integer(sourceRefId);
			if (i == 15) return sourceRefDisplay;
			if (i == 16) return defaultValue;
			if (i == 17) return new Integer(lockedInd);
			if (i == 18) return new Integer(disabledInd);
			if (i == 19) return new Integer(requiredInd);
			if (i == 20) return new Integer(visibleInd);
			if (i == 21) return initScript;
			if (i == 22) return focusLostScript;
			if (i == 23) return focusGainedScript;
			if (i == 24) return imagePath;
			if (i == 25) return new Integer(scrollpaneInd);
			if (i == 26) return new Double(growHeight);
			if (i == 27) return new Integer(growWidth);
			if (i == 28) return new Integer(unitWidth);
			if (i == 29) return new Integer(unitHeight);
			if (i == 30) return createDt;
			if (i == 31) return new Integer(createUserId);
			if (i == 32) return createUserName;
			if (i == 33) return updateDt;
			if (i == 34) return new Integer(updateUserId);
			if (i == 35) return updateUserName;
			if (i == 36) return new Integer(updateCount);
			if (i == 37) return new Integer(activeInd);
			if (i == 38) return new Integer(systemAssignedVersionNbr);
			if (i == 39) return new Integer(recordTypeRefId);
			if (i == 40) return recordTypeRefDisplay;
		return null;
	}

	public int getDatabaseDataType(int i) {
			if (i == 0) return DatabaseDataTypeFramework.INTEGER;
			if (i == 1) return DatabaseDataTypeFramework.INTEGER;
			if (i == 2) return DatabaseDataTypeFramework.INTEGER;
			if (i == 3) return DatabaseDataTypeFramework.INTEGER;
			if (i == 4) return DatabaseDataTypeFramework.STRING;
			if (i == 5) return DatabaseDataTypeFramework.INTEGER;
			if (i == 6) return DatabaseDataTypeFramework.STRING;
			if (i == 7) return DatabaseDataTypeFramework.STRING;
			if (i == 8) return DatabaseDataTypeFramework.STRING;
			if (i == 9) return DatabaseDataTypeFramework.INTEGER;
			if (i == 10) return DatabaseDataTypeFramework.STRING;
			if (i == 11) return DatabaseDataTypeFramework.INTEGER;
			if (i == 12) return DatabaseDataTypeFramework.INTEGER;
			if (i == 13) return DatabaseDataTypeFramework.STRING;
			if (i == 14) return DatabaseDataTypeFramework.INTEGER;
			if (i == 15) return DatabaseDataTypeFramework.STRING;
			if (i == 16) return DatabaseDataTypeFramework.STRING;
			if (i == 17) return DatabaseDataTypeFramework.INTEGER;
			if (i == 18) return DatabaseDataTypeFramework.INTEGER;
			if (i == 19) return DatabaseDataTypeFramework.INTEGER;
			if (i == 20) return DatabaseDataTypeFramework.INTEGER;
			if (i == 21) return DatabaseDataTypeFramework.STRING;
			if (i == 22) return DatabaseDataTypeFramework.STRING;
			if (i == 23) return DatabaseDataTypeFramework.STRING;
			if (i == 24) return DatabaseDataTypeFramework.STRING;
			if (i == 25) return DatabaseDataTypeFramework.INTEGER;
			if (i == 26) return DatabaseDataTypeFramework.DOUBLE;
			if (i == 27) return DatabaseDataTypeFramework.INTEGER;
			if (i == 28) return DatabaseDataTypeFramework.INTEGER;
			if (i == 29) return DatabaseDataTypeFramework.INTEGER;
			if (i == 30) return DatabaseDataTypeFramework.DATE;
			if (i == 31) return DatabaseDataTypeFramework.INTEGER;
			if (i == 32) return DatabaseDataTypeFramework.STRING;
			if (i == 33) return DatabaseDataTypeFramework.DATE;
			if (i == 34) return DatabaseDataTypeFramework.INTEGER;
			if (i == 35) return DatabaseDataTypeFramework.STRING;
			if (i == 36) return DatabaseDataTypeFramework.INTEGER;
			if (i == 37) return DatabaseDataTypeFramework.INTEGER;
			if (i == 38) return DatabaseDataTypeFramework.INTEGER;
			if (i == 39) return DatabaseDataTypeFramework.INTEGER;
			if (i == 40) return DatabaseDataTypeFramework.STRING;
			return DatabaseDataTypeFramework.STRING;
	}

	public void setDataAt(Object o, int i) {
		switch(i) {
			case 0: setApplicationControlId(((Integer) o).intValue()); break;
			case 1: setApplicationViewId(((Integer) o).intValue()); break;
			case 2: setDisplaySequence(((Integer) o).intValue()); break;
			case 3: setControlRefId(((Integer) o).intValue()); break;
			case 4: setControlRefDisplay((String) o); break;
			case 5: setControlTypeRefId(((Integer) o).intValue()); break;
			case 6: setControlTypeRefDisplay((String) o); break;
			case 7: setControlText((String) o); break;
			case 8: setControlDescription((String) o); break;
			case 9: setModelColumnRefId(((Integer) o).intValue()); break;
			case 10: setModelColumnRefDisplay((String) o); break;
			case 11: setApplicationCustomControlId(((Integer) o).intValue()); break;
			case 12: setAppControlUserDefinedId(((Integer) o).intValue()); break;
			case 13: setControlFormat((String) o); break;
			case 14: setSourceRefId(((Integer) o).intValue()); break;
			case 15: setSourceRefDisplay((String) o); break;
			case 16: setDefaultValue((String) o); break;
			case 17: setLockedInd(((Integer) o).intValue()); break;
			case 18: setDisabledInd(((Integer) o).intValue()); break;
			case 19: setRequiredInd(((Integer) o).intValue()); break;
			case 20: setVisibleInd(((Integer) o).intValue()); break;
			case 21: setInitScript((String) o); break;
			case 22: setFocusLostScript((String) o); break;
			case 23: setFocusGainedScript((String) o); break;
			case 24: setImagePath((String) o); break;
			case 25: setScrollpaneInd(((Integer) o).intValue()); break;
			case 26: setGrowHeight(((Double) o).doubleValue()); break;
			case 27: setGrowWidth(((Integer) o).intValue()); break;
			case 28: setUnitWidth(((Integer) o).intValue()); break;
			case 29: setUnitHeight(((Integer) o).intValue()); break;
			case 30: setCreateDt((GregorianCalendar) o); break;
			case 31: setCreateUserId(((Integer) o).intValue()); break;
			case 32: setCreateUserName((String) o); break;
			case 33: setUpdateDt((GregorianCalendar) o); break;
			case 34: setUpdateUserId(((Integer) o).intValue()); break;
			case 35: setUpdateUserName((String) o); break;
			case 36: setUpdateCount(((Integer) o).intValue()); break;
			case 37: setActiveInd(((Integer) o).intValue()); break;
			case 38: setSystemAssignedVersionNbr(((Integer) o).intValue()); break;
			case 39: setRecordTypeRefId(((Integer) o).intValue()); break;
			case 40: setRecordTypeRefDisplay((String) o); break;
		}
	}

}
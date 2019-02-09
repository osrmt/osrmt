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
public class RecordFileHistoryDataModel implements IControlModel, java.io.Serializable {

	static final long serialVersionUID = 1L;
	private int hashsize = 23;
	private Hashtable modifiedFields = new Hashtable(hashsize);
	private boolean referenceSet = false;


	/** Unique identifier to record_file history table  */
	private int recordFileHistoryId = 0;

	/** Foreign key to Record File  table  */
	private int recordFileId = 0;

	/** Date the history record was created  */
	private DbCalendar historyDt = DbCalendar.getDbCalendar();

	/** User who made the history record  */
	private int historyUserId = 0;

	private String historyUserName="";

	/** Foreign key to baseline  */
	private int baselineId = 0;

	/** Foreign key to table extended  */
	private int tableId = 0;

	/** Foreign key to reference: Table  */
	private int tableRefId = 0;

	private String tableRefDisplay="";

	/** Foreign key to reference: FileType  */
	private int fileTypeRefId = 0;

	private String fileTypeRefDisplay="";

	/** Version number  */
	private double version = 0;

	/** Original source file  */
	private String sourceFile;

	/** Stored File name  */
	private String storageFileName;

	/** Storage directory  */
	private String storageDirectory;

	/** Saved file name  */
	private String fileName;

	/** Latest version 1=Yes 0:replaced  */
	private int activeVersionInd = 1;

	/** Description  */
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
	 * Unique identifier to record_file history table
	 * 
	 */ 
	public int getRecordFileHistoryId() {
		return recordFileHistoryId;
	}

	/** 
	 * Unique identifier to record_file history table
	 * 
	 */ 
	public void setRecordFileHistoryId(int recordFileHistoryId) {
		if (!Comparison.areEqual(this.recordFileHistoryId, recordFileHistoryId)) {
			this.recordFileHistoryId = recordFileHistoryId;
			setModified("recordFileHistoryId");
		};
	}

	/** 
	 * Foreign key to Record File  table
	 * 
	 */ 
	public int getRecordFileId() {
		return recordFileId;
	}

	/** 
	 * Foreign key to Record File  table
	 * 
	 * Required database field.
	 */ 
	public void setRecordFileId(int recordFileId) {
		if (!Comparison.areEqual(this.recordFileId, recordFileId)) {
			this.recordFileId = recordFileId;
			setModified("recordFileId");
		};
	}

	/** 
	 * Date the history record was created
	 * 
	 */ 
	public DbCalendar getHistoryDt() {
		return historyDt;
	}

	/** 
	 * Date the history record was created
	 * 
	 */ 
	public void setHistoryDt(GregorianCalendar historyDt) {
		if (!Comparison.areEqual(this.historyDt, historyDt)) {
			this.historyDt = new DbCalendar();
			if (historyDt != null) {
				this.historyDt.setTimeInMillis(historyDt.getTimeInMillis());
			}
			setModified("historyDt");
		};
	}

	/** 
	 * Date the history record was created
	 * 
	 */ 
	public void setHistoryDt(long milliseconds) {
		if (this.historyDt==null) {
			this.historyDt = new DbCalendar();
		}
		if (!Comparison.areEqual(this.historyDt, historyDt)) {
			this.historyDt.setTimeInMillis(milliseconds);
			setModified("historyDt");
		}

	}

	/** 
	 * User who made the history record
	 * 
	 */ 
	public int getHistoryUserId() {
		return historyUserId;
	}

	/** 
	 * User who made the history record
	 * 
	 * Required database field.
	 */ 
	public void setHistoryUserId(int historyUserId) {
		if (!Comparison.areEqual(this.historyUserId, historyUserId)) {
			this.historyUserId = historyUserId;
			setModified("historyUserId");
		};
	}

	/** 
	 * User who made the history record
	 * 
	 */ 
	public String getHistoryUserName() {
		return historyUserName;
	}

	/** 
	 * User who made the history record
	 * 
	 */ 
	public void setHistoryUserName(String userName) {
		this.historyUserName = userName;
	}

	/** 
	 * Foreign key to baseline
	 * 
	 */ 
	public int getBaselineId() {
		return baselineId;
	}

	/** 
	 * Foreign key to baseline
	 * 
	 * Required database field.
	 */ 
	public void setBaselineId(int baselineId) {
		if (!Comparison.areEqual(this.baselineId, baselineId)) {
			this.baselineId = baselineId;
			setModified("baselineId");
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
	 * Foreign key to reference: FileType
	 * 
	 */ 
	public int getFileTypeRefId() {
		return fileTypeRefId;
	}

	/** 
	 * Foreign key to reference: FileType
	 * 
	 * Required database field.
	 */ 
	public void setFileTypeRefId(int fileTypeRefId) {
		if (!Comparison.areEqual(this.fileTypeRefId, fileTypeRefId)) {
			this.fileTypeRefId = fileTypeRefId;
			setModified("fileTypeRefId");
		};
	}

	/** 
	 * Foreign key to reference: FileType
	 * 
	 */ 
	public String getFileTypeRefDisplay() {
		return fileTypeRefDisplay;
	}

	/** 
	 * Foreign key to reference: FileType
	 * 
	 */ 
	public void setFileTypeRefDisplay(String display) {
		this.fileTypeRefDisplay = display;
	}

	/** 
	 * Version number
	 * 
	 */ 
	public double getVersion() {
		return version;
	}

	/** 
	 * Version number
	 * 
	 * Required database field.
	 */ 
	public void setVersion(double version) {
		if (!Comparison.areEqual(this.version, version)) {
			this.version = version;
			setModified("version");
		};
	}

	/** 
	 * Original source file
	 * 
	 */ 
	public String getSourceFile() {
		return sourceFile;
	}

	/** 
	 * Original source file
	 * 
	 */ 
	public void setSourceFile(String sourceFile) {
		if (!Comparison.areEqual(this.sourceFile, sourceFile)) {
			this.sourceFile = sourceFile;
			setModified("sourceFile");
		};
	}

	/** 
	 * Stored File name
	 * 
	 */ 
	public String getStorageFileName() {
		return storageFileName;
	}

	/** 
	 * Stored File name
	 * 
	 */ 
	public void setStorageFileName(String storageFileName) {
		if (!Comparison.areEqual(this.storageFileName, storageFileName)) {
			this.storageFileName = storageFileName;
			setModified("storageFileName");
		};
	}

	/** 
	 * Storage directory
	 * 
	 */ 
	public String getStorageDirectory() {
		return storageDirectory;
	}

	/** 
	 * Storage directory
	 * 
	 */ 
	public void setStorageDirectory(String storageDirectory) {
		if (!Comparison.areEqual(this.storageDirectory, storageDirectory)) {
			this.storageDirectory = storageDirectory;
			setModified("storageDirectory");
		};
	}

	/** 
	 * Saved file name
	 * 
	 */ 
	public String getFileName() {
		return fileName;
	}

	/** 
	 * Saved file name
	 * 
	 */ 
	public void setFileName(String fileName) {
		if (!Comparison.areEqual(this.fileName, fileName)) {
			this.fileName = fileName;
			setModified("fileName");
		};
	}

	/** 
	 * Latest version 1=Yes 0:replaced
	 * 
	 */ 
	public int getActiveVersionInd() {
		return activeVersionInd;
	}

	/** 
	 * Latest version 1=Yes 0:replaced
	 * 
	 * Required database field.
	 */ 
	public void setActiveVersionInd(int activeVersionInd) {
		if (!Comparison.areEqual(this.activeVersionInd, activeVersionInd)) {
			this.activeVersionInd = activeVersionInd;
			setModified("activeVersionInd");
		};
	}

	/** 
	 * Latest version 1=Yes 0:replaced
	 * 
	 */ 
	public boolean isActiveVersion() {
		return activeVersionInd == 1;
	}

	/** 
	 * Latest version 1=Yes 0:replaced
	 * 
	 */ 
	public boolean isNotActiveVersion() {
		return activeVersionInd == 0;
	}

	/** 
	 * Latest version 1=Yes 0:replaced
	 * 
	 */ 
	public void setActiveVersion() {
		this.setActiveVersionInd(1);
	}

	public void setNotActiveVersion() {
		this.setActiveVersionInd(0);
	}

	/** 
	 * Description
	 * 
	 */ 
	public String getDescription() {
		return description;
	}

	/** 
	 * Description
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
		columns.addColumn("recordFileHistoryId", Integer.class);
		columns.addColumn("recordFileId", Integer.class);
		columns.addColumn("historyDt", GregorianCalendar.class);
		columns.addColumn("historyUserId", Integer.class);
		columns.addColumn("historyUserName", String.class);
		columns.addColumn("baselineId", Integer.class);
		columns.addColumn("tableId", Integer.class);
		columns.addColumn("tableRefId", Integer.class);
		columns.addColumn("tableRefDisplay", String.class);
		columns.addColumn("fileTypeRefId", Integer.class);
		columns.addColumn("fileTypeRefDisplay", String.class);
		columns.addColumn("version", Double.class);
		columns.addColumn("sourceFile", String.class);
		columns.addColumn("storageFileName", String.class);
		columns.addColumn("storageDirectory", String.class);
		columns.addColumn("fileName", String.class);
		columns.addColumn("activeVersionInd", Integer.class);
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
			if (security != null && this.historyUserId > 0) setHistoryUserName(security.getUser(this.historyUserId).getDisplayName());
			if (reference != null && this.tableRefId > 0) setTableRefDisplay(reference.getDisplay(this.tableRefId));
			if (reference != null && this.fileTypeRefId > 0) setFileTypeRefDisplay(reference.getDisplay(this.fileTypeRefId));
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
	public void copyModifiedTo(RecordFileHistoryDataModel m) {
		Enumeration e1 = modifiedFields.keys();
		while (e1.hasMoreElements()) {
			String fieldName = (String) e1.nextElement();

			if (fieldName.compareTo("recordFileHistoryId")==0)
				m.setRecordFileHistoryId(this.getRecordFileHistoryId());
			else if (fieldName.compareTo("recordFileId")==0)
				m.setRecordFileId(this.getRecordFileId());
			else if (fieldName.compareTo("historyDt")==0)
				m.setHistoryDt(this.getHistoryDt());
			else if (fieldName.compareTo("historyUserId")==0)
				m.setHistoryUserId(this.getHistoryUserId());
			else if (fieldName.compareTo("baselineId")==0)
				m.setBaselineId(this.getBaselineId());
			else if (fieldName.compareTo("tableId")==0)
				m.setTableId(this.getTableId());
			else if (fieldName.compareTo("tableRefId")==0)
				m.setTableRefId(this.getTableRefId());
			else if (fieldName.compareTo("fileTypeRefId")==0)
				m.setFileTypeRefId(this.getFileTypeRefId());
			else if (fieldName.compareTo("version")==0)
				m.setVersion(this.getVersion());
			else if (fieldName.compareTo("sourceFile")==0)
				m.setSourceFile(this.getSourceFile());
			else if (fieldName.compareTo("storageFileName")==0)
				m.setStorageFileName(this.getStorageFileName());
			else if (fieldName.compareTo("storageDirectory")==0)
				m.setStorageDirectory(this.getStorageDirectory());
			else if (fieldName.compareTo("fileName")==0)
				m.setFileName(this.getFileName());
			else if (fieldName.compareTo("activeVersionInd")==0)
				m.setActiveVersionInd(this.getActiveVersionInd());
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
	public void updateWith (RecordFileHistoryModel m) {

		this.setRecordFileHistoryId(m.getRecordFileHistoryId());
		this.setRecordFileId(m.getRecordFileId());
		this.setHistoryDt(m.getHistoryDt());
		this.setHistoryUserId(m.getHistoryUserId());
		this.setHistoryUserName(m.getHistoryUserName());
		this.setBaselineId(m.getBaselineId());
		this.setTableId(m.getTableId());
		this.setTableRefId(m.getTableRefId());
		this.setTableRefDisplay(m.getTableRefDisplay());
		this.setFileTypeRefId(m.getFileTypeRefId());
		this.setFileTypeRefDisplay(m.getFileTypeRefDisplay());
		this.setVersion(m.getVersion());
		this.setSourceFile(m.getSourceFile());
		this.setStorageFileName(m.getStorageFileName());
		this.setStorageDirectory(m.getStorageDirectory());
		this.setFileName(m.getFileName());
		this.setActiveVersionInd(m.getActiveVersionInd());
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

	public void updateWith (RecordFileModel m) {

		this.setRecordFileId(m.getRecordFileId());
		this.setTableId(m.getTableId());
		this.setTableRefId(m.getTableRefId());
		this.setFileTypeRefId(m.getFileTypeRefId());
		this.setVersion(m.getVersion());
		this.setSourceFile(m.getSourceFile());
		this.setStorageFileName(m.getStorageFileName());
		this.setStorageDirectory(m.getStorageDirectory());
		this.setFileName(m.getFileName());
		this.setActiveVersionInd(m.getActiveVersionInd());
		this.setDescription(m.getDescription());
		this.setCreateDt(m.getCreateDt());
		this.setCreateUserId(m.getCreateUserId());
		this.setUpdateDt(m.getUpdateDt());
		this.setUpdateUserId(m.getUpdateUserId());
		this.setUpdateCount(m.getUpdateCount());
		this.setActiveInd(m.getActiveInd());
		this.setSystemAssignedVersionNbr(m.getSystemAssignedVersionNbr());
		this.setRecordTypeRefId(m.getRecordTypeRefId());
	}

	/**
	 * Compare the two objects
	 */
	public boolean isEqualTo (RecordFileHistoryModel m) {

		if (!Comparison.areEqual(this.getRecordFileHistoryId(),  m.getRecordFileHistoryId())) return false;
		if (!Comparison.areEqual(this.getRecordFileId(),  m.getRecordFileId())) return false;
		if (!Comparison.areEqual(this.getHistoryDt(),  m.getHistoryDt())) return false;
		if (!Comparison.areEqual(this.getHistoryUserId(),  m.getHistoryUserId())) return false;
		if (!Comparison.areEqual(this.getBaselineId(),  m.getBaselineId())) return false;
		if (!Comparison.areEqual(this.getTableId(),  m.getTableId())) return false;
		if (!Comparison.areEqual(this.getTableRefId(),  m.getTableRefId())) return false;
		if (!Comparison.areEqual(this.getFileTypeRefId(),  m.getFileTypeRefId())) return false;
		if (!Comparison.areEqual(this.getVersion(),  m.getVersion())) return false;
		if (!Comparison.areEqual(this.getSourceFile(),  m.getSourceFile())) return false;
		if (!Comparison.areEqual(this.getStorageFileName(),  m.getStorageFileName())) return false;
		if (!Comparison.areEqual(this.getStorageDirectory(),  m.getStorageDirectory())) return false;
		if (!Comparison.areEqual(this.getFileName(),  m.getFileName())) return false;
		if (!Comparison.areEqual(this.getActiveVersionInd(),  m.getActiveVersionInd())) return false;
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
		return v;
	}
	public String toString() {
		try {
			StringBuffer sb = new StringBuffer(1024);
			sb.append("RecordFileHistoryModel:\r\n");
			sb.append("record_file_history_id:" + getRecordFileHistoryId());
			sb.append("\r\n");
			sb.append("record_file_id:" + getRecordFileId());
			sb.append("\r\n");
			sb.append("history_dt:" + CalendarUtility.Format(getHistoryDt(),AppFormats.getLongDateTimeFormat()));
			sb.append("\r\n");
			sb.append("history_user_id:" + getHistoryUserId());
			sb.append("\r\n");
			sb.append("history_user_name:" + getHistoryUserName());
			sb.append("\r\n");
			sb.append("baseline_id:" + getBaselineId());
			sb.append("\r\n");
			sb.append("table_id:" + getTableId());
			sb.append("\r\n");
			sb.append("table_ref_id:" + getTableRefId());
			sb.append("\r\n");
			sb.append("table_ref_display:" + getTableRefDisplay());
			sb.append("\r\n");
			sb.append("file_type_ref_id:" + getFileTypeRefId());
			sb.append("\r\n");
			sb.append("file_type_ref_display:" + getFileTypeRefDisplay());
			sb.append("\r\n");
			sb.append("version:" + getVersion());
			sb.append("\r\n");
			sb.append("source_file:" + getSourceFile());
			sb.append("\r\n");
			sb.append("storage_file_name:" + getStorageFileName());
			sb.append("\r\n");
			sb.append("storage_directory:" + getStorageDirectory());
			sb.append("\r\n");
			sb.append("file_name:" + getFileName());
			sb.append("\r\n");
			sb.append("active_version_ind:" + getActiveVersionInd());
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
			return "RecordFileHistoryModel:"; 
		}
	}
	public void setModelColDataAt(Object o, int modelCol) {
			switch (modelCol) {
			case 5508: setRecordFileHistoryId(((Integer) o).intValue()); break;
			case 5509: setRecordFileId(((Integer) o).intValue()); break;
			case 5510: setHistoryDt((GregorianCalendar) o); break;
			case 5511: setHistoryUserId(((Integer) o).intValue()); break;
			case 5543: setHistoryUserName((String) o); break;
			case 5512: setBaselineId(((Integer) o).intValue()); break;
			case 5513: setTableId(((Integer) o).intValue()); break;
			case 5514: setTableRefId(((Integer) o).intValue()); break;
			case 5515: setFileTypeRefId(((Integer) o).intValue()); break;
			case 5516: setVersion(((Double) o).doubleValue()); break;
			case 5517: setSourceFile((String) o); break;
			case 5518: setStorageFileName((String) o); break;
			case 5519: setStorageDirectory((String) o); break;
			case 5520: setFileName((String) o); break;
			case 5521: setActiveVersionInd(((Integer) o).intValue()); break;
			case 5522: setDescription((String) o); break;
			case 5523: setCreateDt((GregorianCalendar) o); break;
			case 5524: setCreateUserId(((Integer) o).intValue()); break;
			case 5546: setCreateUserName((String) o); break;
			case 5525: setUpdateDt((GregorianCalendar) o); break;
			case 5526: setUpdateUserId(((Integer) o).intValue()); break;
			case 5547: setUpdateUserName((String) o); break;
			case 5527: setUpdateCount(((Integer) o).intValue()); break;
			case 5528: setActiveInd(((Integer) o).intValue()); break;
			case 5529: setSystemAssignedVersionNbr(((Integer) o).intValue()); break;
			case 5530: setRecordTypeRefId(((Integer) o).intValue()); break;
			default: Debug.LogWarning(this, ReferenceServices.getMsg(SystemMessageFramework.SWITCHSTATEMENTFAILED) + " modelColRefId: " + modelCol);
	} 
}

	public Object getModelColDataAt(int modelCol) {
			if (modelCol == 5508) return new Integer(recordFileHistoryId);
			if (modelCol == 5509) return new Integer(recordFileId);
			if (modelCol == 5510) return historyDt;
			if (modelCol == 5511) return new Integer(historyUserId);
			if (modelCol == 5511) return historyUserName;
			if (modelCol == 5512) return new Integer(baselineId);
			if (modelCol == 5513) return new Integer(tableId);
			if (modelCol == 5514) return new Integer(tableRefId);
			if (modelCol == 5514) return tableRefDisplay;
			if (modelCol == 5515) return new Integer(fileTypeRefId);
			if (modelCol == 5515) return fileTypeRefDisplay;
			if (modelCol == 5516) return new Double(version);
			if (modelCol == 5517) return sourceFile;
			if (modelCol == 5518) return storageFileName;
			if (modelCol == 5519) return storageDirectory;
			if (modelCol == 5520) return fileName;
			if (modelCol == 5521) return new Integer(activeVersionInd);
			if (modelCol == 5522) return description;
			if (modelCol == 5523) return createDt;
			if (modelCol == 5524) return new Integer(createUserId);
			if (modelCol == 5524) return createUserName;
			if (modelCol == 5525) return updateDt;
			if (modelCol == 5526) return new Integer(updateUserId);
			if (modelCol == 5526) return updateUserName;
			if (modelCol == 5527) return new Integer(updateCount);
			if (modelCol == 5528) return new Integer(activeInd);
			if (modelCol == 5529) return new Integer(systemAssignedVersionNbr);
			if (modelCol == 5530) return new Integer(recordTypeRefId);
			if (modelCol == 5530) return recordTypeRefDisplay;
		return null;
	}

	public int getModelColDatabaseDataType(int modelCol) {
			if (modelCol == 5508) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 5509) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 5510) return DatabaseDataTypeFramework.DATE;
			if (modelCol == 5511) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 5511) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 5512) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 5513) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 5514) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 5514) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 5515) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 5515) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 5516) return DatabaseDataTypeFramework.DOUBLE;
			if (modelCol == 5517) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 5518) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 5519) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 5520) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 5521) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 5522) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 5523) return DatabaseDataTypeFramework.DATE;
			if (modelCol == 5524) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 5524) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 5525) return DatabaseDataTypeFramework.DATE;
			if (modelCol == 5526) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 5526) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 5527) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 5528) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 5529) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 5530) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 5530) return DatabaseDataTypeFramework.STRING;
			return DatabaseDataTypeFramework.STRING;
	}

	public boolean isNew() {
		return getRecordFileHistoryId() == 0;
	}

	public Object getPrimaryValue() {
		return new Integer(getRecordFileHistoryId());
	}

	public Object getDataAt(int i) {
			if (i == 0) return new Integer(recordFileHistoryId);
			if (i == 1) return new Integer(recordFileId);
			if (i == 2) return historyDt;
			if (i == 3) return new Integer(historyUserId);
			if (i == 4) return historyUserName;
			if (i == 5) return new Integer(baselineId);
			if (i == 6) return new Integer(tableId);
			if (i == 7) return new Integer(tableRefId);
			if (i == 8) return tableRefDisplay;
			if (i == 9) return new Integer(fileTypeRefId);
			if (i == 10) return fileTypeRefDisplay;
			if (i == 11) return new Double(version);
			if (i == 12) return sourceFile;
			if (i == 13) return storageFileName;
			if (i == 14) return storageDirectory;
			if (i == 15) return fileName;
			if (i == 16) return new Integer(activeVersionInd);
			if (i == 17) return description;
			if (i == 18) return createDt;
			if (i == 19) return new Integer(createUserId);
			if (i == 20) return createUserName;
			if (i == 21) return updateDt;
			if (i == 22) return new Integer(updateUserId);
			if (i == 23) return updateUserName;
			if (i == 24) return new Integer(updateCount);
			if (i == 25) return new Integer(activeInd);
			if (i == 26) return new Integer(systemAssignedVersionNbr);
			if (i == 27) return new Integer(recordTypeRefId);
			if (i == 28) return recordTypeRefDisplay;
		return null;
	}

	public int getDatabaseDataType(int i) {
			if (i == 0) return DatabaseDataTypeFramework.INTEGER;
			if (i == 1) return DatabaseDataTypeFramework.INTEGER;
			if (i == 2) return DatabaseDataTypeFramework.DATE;
			if (i == 3) return DatabaseDataTypeFramework.INTEGER;
			if (i == 4) return DatabaseDataTypeFramework.STRING;
			if (i == 5) return DatabaseDataTypeFramework.INTEGER;
			if (i == 6) return DatabaseDataTypeFramework.INTEGER;
			if (i == 7) return DatabaseDataTypeFramework.INTEGER;
			if (i == 8) return DatabaseDataTypeFramework.STRING;
			if (i == 9) return DatabaseDataTypeFramework.INTEGER;
			if (i == 10) return DatabaseDataTypeFramework.STRING;
			if (i == 11) return DatabaseDataTypeFramework.DOUBLE;
			if (i == 12) return DatabaseDataTypeFramework.STRING;
			if (i == 13) return DatabaseDataTypeFramework.STRING;
			if (i == 14) return DatabaseDataTypeFramework.STRING;
			if (i == 15) return DatabaseDataTypeFramework.STRING;
			if (i == 16) return DatabaseDataTypeFramework.INTEGER;
			if (i == 17) return DatabaseDataTypeFramework.STRING;
			if (i == 18) return DatabaseDataTypeFramework.DATE;
			if (i == 19) return DatabaseDataTypeFramework.INTEGER;
			if (i == 20) return DatabaseDataTypeFramework.STRING;
			if (i == 21) return DatabaseDataTypeFramework.DATE;
			if (i == 22) return DatabaseDataTypeFramework.INTEGER;
			if (i == 23) return DatabaseDataTypeFramework.STRING;
			if (i == 24) return DatabaseDataTypeFramework.INTEGER;
			if (i == 25) return DatabaseDataTypeFramework.INTEGER;
			if (i == 26) return DatabaseDataTypeFramework.INTEGER;
			if (i == 27) return DatabaseDataTypeFramework.INTEGER;
			if (i == 28) return DatabaseDataTypeFramework.STRING;
			return DatabaseDataTypeFramework.STRING;
	}

	public void setDataAt(Object o, int i) {
		switch(i) {
			case 0: setRecordFileHistoryId(((Integer) o).intValue()); break;
			case 1: setRecordFileId(((Integer) o).intValue()); break;
			case 2: setHistoryDt((GregorianCalendar) o); break;
			case 3: setHistoryUserId(((Integer) o).intValue()); break;
			case 4: setHistoryUserName((String) o); break;
			case 5: setBaselineId(((Integer) o).intValue()); break;
			case 6: setTableId(((Integer) o).intValue()); break;
			case 7: setTableRefId(((Integer) o).intValue()); break;
			case 8: setTableRefDisplay((String) o); break;
			case 9: setFileTypeRefId(((Integer) o).intValue()); break;
			case 10: setFileTypeRefDisplay((String) o); break;
			case 11: setVersion(((Double) o).doubleValue()); break;
			case 12: setSourceFile((String) o); break;
			case 13: setStorageFileName((String) o); break;
			case 14: setStorageDirectory((String) o); break;
			case 15: setFileName((String) o); break;
			case 16: setActiveVersionInd(((Integer) o).intValue()); break;
			case 17: setDescription((String) o); break;
			case 18: setCreateDt((GregorianCalendar) o); break;
			case 19: setCreateUserId(((Integer) o).intValue()); break;
			case 20: setCreateUserName((String) o); break;
			case 21: setUpdateDt((GregorianCalendar) o); break;
			case 22: setUpdateUserId(((Integer) o).intValue()); break;
			case 23: setUpdateUserName((String) o); break;
			case 24: setUpdateCount(((Integer) o).intValue()); break;
			case 25: setActiveInd(((Integer) o).intValue()); break;
			case 26: setSystemAssignedVersionNbr(((Integer) o).intValue()); break;
			case 27: setRecordTypeRefId(((Integer) o).intValue()); break;
			case 28: setRecordTypeRefDisplay((String) o); break;
		}
	}

}
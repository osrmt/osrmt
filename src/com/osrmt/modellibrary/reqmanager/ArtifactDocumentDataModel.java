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
public class ArtifactDocumentDataModel implements IControlModel, java.io.Serializable {

	static final long serialVersionUID = 1L;
	private int hashsize = 14;
	private Hashtable modifiedFields = new Hashtable(hashsize);
	private boolean referenceSet = false;


	/** Unique identifier to artifact document table  */
	private int artifactDocumentId = 0;

	/** Foreign key to artifact  */
	private int artifactId = 0;

	/** Sequence this line is for the document  */
	private int lineSeq = 0;

	/** Text for this line  */
	private String lineText;

	/** Image for this line.  Foreign key to record file  */
	private int imageRecordFileId = 0;

	/** Style used for this line.  Foreign key to reference on Style  */
	private int styleRefId = 0;

	private String styleRefDisplay="";

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
	 * Unique identifier to artifact document table
	 * 
	 */ 
	public int getArtifactDocumentId() {
		return artifactDocumentId;
	}

	/** 
	 * Unique identifier to artifact document table
	 * 
	 */ 
	public void setArtifactDocumentId(int artifactDocumentId) {
		if (!Comparison.areEqual(this.artifactDocumentId, artifactDocumentId)) {
			this.artifactDocumentId = artifactDocumentId;
			setModified("artifactDocumentId");
		};
	}

	/** 
	 * Foreign key to artifact
	 * 
	 */ 
	public int getArtifactId() {
		return artifactId;
	}

	/** 
	 * Foreign key to artifact
	 * 
	 * Required database field.
	 */ 
	public void setArtifactId(int artifactId) {
		if (!Comparison.areEqual(this.artifactId, artifactId)) {
			this.artifactId = artifactId;
			setModified("artifactId");
		};
	}

	/** 
	 * Sequence this line is for the document
	 * 
	 */ 
	public int getLineSeq() {
		return lineSeq;
	}

	/** 
	 * Sequence this line is for the document
	 * 
	 */ 
	public void setLineSeq(int lineSeq) {
		if (!Comparison.areEqual(this.lineSeq, lineSeq)) {
			this.lineSeq = lineSeq;
			setModified("lineSeq");
		};
	}

	/** 
	 * Text for this line
	 * 
	 */ 
	public String getLineText() {
		return lineText;
	}

	/** 
	 * Text for this line
	 * 
	 */ 
	public void setLineText(String lineText) {
		if (!Comparison.areEqual(this.lineText, lineText)) {
			this.lineText = lineText;
			setModified("lineText");
		};
	}

	/** 
	 * Image for this line.  Foreign key to record file
	 * 
	 */ 
	public int getImageRecordFileId() {
		return imageRecordFileId;
	}

	/** 
	 * Image for this line.  Foreign key to record file
	 * 
	 */ 
	public void setImageRecordFileId(int imageRecordFileId) {
		if (!Comparison.areEqual(this.imageRecordFileId, imageRecordFileId)) {
			this.imageRecordFileId = imageRecordFileId;
			setModified("imageRecordFileId");
		};
	}

	/** 
	 * Style used for this line.  Foreign key to reference on Style
	 * 
	 */ 
	public int getStyleRefId() {
		return styleRefId;
	}

	/** 
	 * Style used for this line.  Foreign key to reference on Style
	 * 
	 */ 
	public void setStyleRefId(int styleRefId) {
		if (!Comparison.areEqual(this.styleRefId, styleRefId)) {
			this.styleRefId = styleRefId;
			setModified("styleRefId");
		};
	}

	/** 
	 * Style used for this line.  Foreign key to reference on Style
	 * 
	 */ 
	public String getStyleRefDisplay() {
		return styleRefDisplay;
	}

	/** 
	 * Style used for this line.  Foreign key to reference on Style
	 * 
	 */ 
	public void setStyleRefDisplay(String display) {
		this.styleRefDisplay = display;
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
		columns.addColumn("artifactDocumentId", Integer.class);
		columns.addColumn("artifactId", Integer.class);
		columns.addColumn("lineSeq", Integer.class);
		columns.addColumn("lineText", String.class);
		columns.addColumn("imageRecordFileId", Integer.class);
		columns.addColumn("styleRefId", Integer.class);
		columns.addColumn("styleRefDisplay", String.class);
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
			if (reference != null && this.styleRefId > 0) setStyleRefDisplay(reference.getDisplay(this.styleRefId));
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
	public void copyModifiedTo(ArtifactDocumentDataModel m) {
		Enumeration e1 = modifiedFields.keys();
		while (e1.hasMoreElements()) {
			String fieldName = (String) e1.nextElement();

			if (fieldName.compareTo("artifactDocumentId")==0)
				m.setArtifactDocumentId(this.getArtifactDocumentId());
			else if (fieldName.compareTo("artifactId")==0)
				m.setArtifactId(this.getArtifactId());
			else if (fieldName.compareTo("lineSeq")==0)
				m.setLineSeq(this.getLineSeq());
			else if (fieldName.compareTo("lineText")==0)
				m.setLineText(this.getLineText());
			else if (fieldName.compareTo("imageRecordFileId")==0)
				m.setImageRecordFileId(this.getImageRecordFileId());
			else if (fieldName.compareTo("styleRefId")==0)
				m.setStyleRefId(this.getStyleRefId());
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
	public void updateWith (ArtifactDocumentModel m) {

		this.setArtifactDocumentId(m.getArtifactDocumentId());
		this.setArtifactId(m.getArtifactId());
		this.setLineSeq(m.getLineSeq());
		this.setLineText(m.getLineText());
		this.setImageRecordFileId(m.getImageRecordFileId());
		this.setStyleRefId(m.getStyleRefId());
		this.setStyleRefDisplay(m.getStyleRefDisplay());
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
	public boolean isEqualTo (ArtifactDocumentModel m) {

		if (!Comparison.areEqual(this.getArtifactDocumentId(),  m.getArtifactDocumentId())) return false;
		if (!Comparison.areEqual(this.getArtifactId(),  m.getArtifactId())) return false;
		if (!Comparison.areEqual(this.getLineSeq(),  m.getLineSeq())) return false;
		if (!Comparison.areEqual(this.getLineText(),  m.getLineText())) return false;
		if (!Comparison.areEqual(this.getImageRecordFileId(),  m.getImageRecordFileId())) return false;
		if (!Comparison.areEqual(this.getStyleRefId(),  m.getStyleRefId())) return false;
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
			sb.append("ArtifactDocumentModel:\r\n");
			sb.append("artifact_document_id:" + getArtifactDocumentId());
			sb.append("\r\n");
			sb.append("artifact_id:" + getArtifactId());
			sb.append("\r\n");
			sb.append("line_seq:" + getLineSeq());
			sb.append("\r\n");
			sb.append("line_text:" + getLineText());
			sb.append("\r\n");
			sb.append("image_record_file_id:" + getImageRecordFileId());
			sb.append("\r\n");
			sb.append("style_ref_id:" + getStyleRefId());
			sb.append("\r\n");
			sb.append("style_ref_display:" + getStyleRefDisplay());
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
			return "ArtifactDocumentModel:"; 
		}
	}
	public void setModelColDataAt(Object o, int modelCol) {
			switch (modelCol) {
			case 1612: setArtifactDocumentId(((Integer) o).intValue()); break;
			case 1613: setArtifactId(((Integer) o).intValue()); break;
			case 1614: setLineSeq(((Integer) o).intValue()); break;
			case 1615: setLineText((String) o); break;
			case 1616: setImageRecordFileId(((Integer) o).intValue()); break;
			case 1617: setStyleRefId(((Integer) o).intValue()); break;
			case 1618: setCreateDt((GregorianCalendar) o); break;
			case 1619: setCreateUserId(((Integer) o).intValue()); break;
			case 1627: setCreateUserName((String) o); break;
			case 1620: setUpdateDt((GregorianCalendar) o); break;
			case 1621: setUpdateUserId(((Integer) o).intValue()); break;
			case 1628: setUpdateUserName((String) o); break;
			case 1622: setUpdateCount(((Integer) o).intValue()); break;
			case 1623: setActiveInd(((Integer) o).intValue()); break;
			case 1624: setSystemAssignedVersionNbr(((Integer) o).intValue()); break;
			case 1625: setRecordTypeRefId(((Integer) o).intValue()); break;
			default: Debug.LogWarning(this, ReferenceServices.getMsg(SystemMessageFramework.SWITCHSTATEMENTFAILED) + " modelColRefId: " + modelCol);
	} 
}

	public Object getModelColDataAt(int modelCol) {
			if (modelCol == 1612) return new Integer(artifactDocumentId);
			if (modelCol == 1613) return new Integer(artifactId);
			if (modelCol == 1614) return new Integer(lineSeq);
			if (modelCol == 1615) return lineText;
			if (modelCol == 1616) return new Integer(imageRecordFileId);
			if (modelCol == 1617) return new Integer(styleRefId);
			if (modelCol == 1617) return styleRefDisplay;
			if (modelCol == 1618) return createDt;
			if (modelCol == 1619) return new Integer(createUserId);
			if (modelCol == 1619) return createUserName;
			if (modelCol == 1620) return updateDt;
			if (modelCol == 1621) return new Integer(updateUserId);
			if (modelCol == 1621) return updateUserName;
			if (modelCol == 1622) return new Integer(updateCount);
			if (modelCol == 1623) return new Integer(activeInd);
			if (modelCol == 1624) return new Integer(systemAssignedVersionNbr);
			if (modelCol == 1625) return new Integer(recordTypeRefId);
			if (modelCol == 1625) return recordTypeRefDisplay;
		return null;
	}

	public int getModelColDatabaseDataType(int modelCol) {
			if (modelCol == 1612) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1613) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1614) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1615) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1616) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1617) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1617) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1618) return DatabaseDataTypeFramework.DATE;
			if (modelCol == 1619) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1619) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1620) return DatabaseDataTypeFramework.DATE;
			if (modelCol == 1621) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1621) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1622) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1623) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1624) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1625) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1625) return DatabaseDataTypeFramework.STRING;
			return DatabaseDataTypeFramework.STRING;
	}

	public boolean isNew() {
		return getArtifactDocumentId() == 0;
	}

	public Object getPrimaryValue() {
		return new Integer(getArtifactDocumentId());
	}

	public Object getDataAt(int i) {
			if (i == 0) return new Integer(artifactDocumentId);
			if (i == 1) return new Integer(artifactId);
			if (i == 2) return new Integer(lineSeq);
			if (i == 3) return lineText;
			if (i == 4) return new Integer(imageRecordFileId);
			if (i == 5) return new Integer(styleRefId);
			if (i == 6) return styleRefDisplay;
			if (i == 7) return createDt;
			if (i == 8) return new Integer(createUserId);
			if (i == 9) return createUserName;
			if (i == 10) return updateDt;
			if (i == 11) return new Integer(updateUserId);
			if (i == 12) return updateUserName;
			if (i == 13) return new Integer(updateCount);
			if (i == 14) return new Integer(activeInd);
			if (i == 15) return new Integer(systemAssignedVersionNbr);
			if (i == 16) return new Integer(recordTypeRefId);
			if (i == 17) return recordTypeRefDisplay;
		return null;
	}

	public int getDatabaseDataType(int i) {
			if (i == 0) return DatabaseDataTypeFramework.INTEGER;
			if (i == 1) return DatabaseDataTypeFramework.INTEGER;
			if (i == 2) return DatabaseDataTypeFramework.INTEGER;
			if (i == 3) return DatabaseDataTypeFramework.STRING;
			if (i == 4) return DatabaseDataTypeFramework.INTEGER;
			if (i == 5) return DatabaseDataTypeFramework.INTEGER;
			if (i == 6) return DatabaseDataTypeFramework.STRING;
			if (i == 7) return DatabaseDataTypeFramework.DATE;
			if (i == 8) return DatabaseDataTypeFramework.INTEGER;
			if (i == 9) return DatabaseDataTypeFramework.STRING;
			if (i == 10) return DatabaseDataTypeFramework.DATE;
			if (i == 11) return DatabaseDataTypeFramework.INTEGER;
			if (i == 12) return DatabaseDataTypeFramework.STRING;
			if (i == 13) return DatabaseDataTypeFramework.INTEGER;
			if (i == 14) return DatabaseDataTypeFramework.INTEGER;
			if (i == 15) return DatabaseDataTypeFramework.INTEGER;
			if (i == 16) return DatabaseDataTypeFramework.INTEGER;
			if (i == 17) return DatabaseDataTypeFramework.STRING;
			return DatabaseDataTypeFramework.STRING;
	}

	public void setDataAt(Object o, int i) {
		switch(i) {
			case 0: setArtifactDocumentId(((Integer) o).intValue()); break;
			case 1: setArtifactId(((Integer) o).intValue()); break;
			case 2: setLineSeq(((Integer) o).intValue()); break;
			case 3: setLineText((String) o); break;
			case 4: setImageRecordFileId(((Integer) o).intValue()); break;
			case 5: setStyleRefId(((Integer) o).intValue()); break;
			case 6: setStyleRefDisplay((String) o); break;
			case 7: setCreateDt((GregorianCalendar) o); break;
			case 8: setCreateUserId(((Integer) o).intValue()); break;
			case 9: setCreateUserName((String) o); break;
			case 10: setUpdateDt((GregorianCalendar) o); break;
			case 11: setUpdateUserId(((Integer) o).intValue()); break;
			case 12: setUpdateUserName((String) o); break;
			case 13: setUpdateCount(((Integer) o).intValue()); break;
			case 14: setActiveInd(((Integer) o).intValue()); break;
			case 15: setSystemAssignedVersionNbr(((Integer) o).intValue()); break;
			case 16: setRecordTypeRefId(((Integer) o).intValue()); break;
			case 17: setRecordTypeRefDisplay((String) o); break;
		}
	}

}
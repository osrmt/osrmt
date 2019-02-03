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
package com.osframework.modellibrary.reportwriter;
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
public class ReportDataModel implements IControlModel, java.io.Serializable {

	static final long serialVersionUID = 1L;
	private int hashsize = 22;
	private Hashtable modifiedFields = new Hashtable(hashsize);
	private boolean referenceSet = false;


	/** Unique identifier to report table  */
	private int reportId = 0;

	/** Foreign key to reference: Report  */
	private int reportRefId = 0;

	private String reportRefDisplay="";

	/** Report SQL ideally select from <view> where column in (!param!)  */
	private String reportSql;

	/** View that identifies the form for parameters  */
	private int parameterViewRefId = 0;

	private String parameterViewRefDisplay="";

	/** Script to change the SQL to include the parameters  */
	private String sqlParameterScript;

	/** Indicates if the report is XML based  */
	private int xmlReportInd = 0;

	/** Script to retreive the models for an xml report  */
	private String xmlSelectScript;

	/** Script to compare two models and return a comparison integer  */
	private String xmlSortScript;

	/** Script to format the fields into an xml record  */
	private String xmlFieldScript;

	/** Regular expression to qualify image attachment for download  */
	private String xmlImageQual;

	/** XML xpath query e.g. /artifactlist/artifact  */
	private String xmlXpath;

	/** Script to determine the outline value. Parameters List<Integer>, artifact  */
	private String reportOutlineScript;

	/** SQL to qualify which artifacts will be passed to the outline script  */
	private String reportOutlineSql;

	/** Date the outline was last exceuted  */
	private DbCalendar outlineLastRunDt;

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
	 * Unique identifier to report table
	 * 
	 */ 
	public int getReportId() {
		return reportId;
	}

	/** 
	 * Unique identifier to report table
	 * 
	 */ 
	public void setReportId(int reportId) {
		if (!Comparison.areEqual(this.reportId, reportId)) {
			this.reportId = reportId;
			setModified("reportId");
		};
	}

	/** 
	 * Foreign key to reference: Report
	 * 
	 */ 
	public int getReportRefId() {
		return reportRefId;
	}

	/** 
	 * Foreign key to reference: Report
	 * 
	 * Required database field.
	 */ 
	public void setReportRefId(int reportRefId) {
		if (!Comparison.areEqual(this.reportRefId, reportRefId)) {
			this.reportRefId = reportRefId;
			setModified("reportRefId");
		};
	}

	/** 
	 * Foreign key to reference: Report
	 * 
	 */ 
	public String getReportRefDisplay() {
		return reportRefDisplay;
	}

	/** 
	 * Foreign key to reference: Report
	 * 
	 */ 
	public void setReportRefDisplay(String display) {
		this.reportRefDisplay = display;
	}

	/** 
	 * Report SQL ideally select from <view> where column in (!param!)
	 * 
	 */ 
	public String getReportSql() {
		return reportSql;
	}

	/** 
	 * Report SQL ideally select from <view> where column in (!param!)
	 * 
	 */ 
	public void setReportSql(String reportSql) {
		if (!Comparison.areEqual(this.reportSql, reportSql)) {
			this.reportSql = reportSql;
			setModified("reportSql");
		};
	}

	/** 
	 * View that identifies the form for parameters
	 * 
	 */ 
	public int getParameterViewRefId() {
		return parameterViewRefId;
	}

	/** 
	 * View that identifies the form for parameters
	 * 
	 */ 
	public void setParameterViewRefId(int parameterViewRefId) {
		if (!Comparison.areEqual(this.parameterViewRefId, parameterViewRefId)) {
			this.parameterViewRefId = parameterViewRefId;
			setModified("parameterViewRefId");
		};
	}

	/** 
	 * View that identifies the form for parameters
	 * 
	 */ 
	public String getParameterViewRefDisplay() {
		return parameterViewRefDisplay;
	}

	/** 
	 * View that identifies the form for parameters
	 * 
	 */ 
	public void setParameterViewRefDisplay(String display) {
		this.parameterViewRefDisplay = display;
	}

	/** 
	 * Script to change the SQL to include the parameters
	 * 
	 */ 
	public String getSqlParameterScript() {
		return sqlParameterScript;
	}

	/** 
	 * Script to change the SQL to include the parameters
	 * 
	 */ 
	public void setSqlParameterScript(String sqlParameterScript) {
		if (!Comparison.areEqual(this.sqlParameterScript, sqlParameterScript)) {
			this.sqlParameterScript = sqlParameterScript;
			setModified("sqlParameterScript");
		};
	}

	/** 
	 * Indicates if the report is XML based
	 * 
	 */ 
	public int getXmlReportInd() {
		return xmlReportInd;
	}

	/** 
	 * Indicates if the report is XML based
	 * 
	 */ 
	public void setXmlReportInd(int xmlReportInd) {
		if (!Comparison.areEqual(this.xmlReportInd, xmlReportInd)) {
			this.xmlReportInd = xmlReportInd;
			setModified("xmlReportInd");
		};
	}

	/** 
	 * Indicates if the report is XML based
	 * 
	 */ 
	public boolean isXmlReport() {
		return xmlReportInd == 1;
	}

	/** 
	 * Indicates if the report is XML based
	 * 
	 */ 
	public boolean isNotXmlReport() {
		return xmlReportInd == 0;
	}

	/** 
	 * Indicates if the report is XML based
	 * 
	 */ 
	public void setXmlReport() {
		this.setXmlReportInd(1);
	}

	public void setNotXmlReport() {
		this.setXmlReportInd(0);
	}

	/** 
	 * Script to retreive the models for an xml report
	 * 
	 */ 
	public String getXmlSelectScript() {
		return xmlSelectScript;
	}

	/** 
	 * Script to retreive the models for an xml report
	 * 
	 */ 
	public void setXmlSelectScript(String xmlSelectScript) {
		if (!Comparison.areEqual(this.xmlSelectScript, xmlSelectScript)) {
			this.xmlSelectScript = xmlSelectScript;
			setModified("xmlSelectScript");
		};
	}

	/** 
	 * Script to compare two models and return a comparison integer
	 * 
	 */ 
	public String getXmlSortScript() {
		return xmlSortScript;
	}

	/** 
	 * Script to compare two models and return a comparison integer
	 * 
	 */ 
	public void setXmlSortScript(String xmlSortScript) {
		if (!Comparison.areEqual(this.xmlSortScript, xmlSortScript)) {
			this.xmlSortScript = xmlSortScript;
			setModified("xmlSortScript");
		};
	}

	/** 
	 * Script to format the fields into an xml record
	 * 
	 */ 
	public String getXmlFieldScript() {
		return xmlFieldScript;
	}

	/** 
	 * Script to format the fields into an xml record
	 * 
	 */ 
	public void setXmlFieldScript(String xmlFieldScript) {
		if (!Comparison.areEqual(this.xmlFieldScript, xmlFieldScript)) {
			this.xmlFieldScript = xmlFieldScript;
			setModified("xmlFieldScript");
		};
	}

	/** 
	 * Regular expression to qualify image attachment for download
	 * 
	 */ 
	public String getXmlImageQual() {
		return xmlImageQual;
	}

	/** 
	 * Regular expression to qualify image attachment for download
	 * 
	 */ 
	public void setXmlImageQual(String xmlImageQual) {
		if (!Comparison.areEqual(this.xmlImageQual, xmlImageQual)) {
			this.xmlImageQual = xmlImageQual;
			setModified("xmlImageQual");
		};
	}

	/** 
	 * XML xpath query e.g. /artifactlist/artifact
	 * 
	 */ 
	public String getXmlXpath() {
		return xmlXpath;
	}

	/** 
	 * XML xpath query e.g. /artifactlist/artifact
	 * 
	 */ 
	public void setXmlXpath(String xmlXpath) {
		if (!Comparison.areEqual(this.xmlXpath, xmlXpath)) {
			this.xmlXpath = xmlXpath;
			setModified("xmlXpath");
		};
	}

	/** 
	 * Script to determine the outline value. Parameters List<Integer>, artifact
	 * 
	 */ 
	public String getReportOutlineScript() {
		return reportOutlineScript;
	}

	/** 
	 * Script to determine the outline value. Parameters List<Integer>, artifact
	 * 
	 */ 
	public void setReportOutlineScript(String reportOutlineScript) {
		if (!Comparison.areEqual(this.reportOutlineScript, reportOutlineScript)) {
			this.reportOutlineScript = reportOutlineScript;
			setModified("reportOutlineScript");
		};
	}

	/** 
	 * SQL to qualify which artifacts will be passed to the outline script
	 * 
	 */ 
	public String getReportOutlineSql() {
		return reportOutlineSql;
	}

	/** 
	 * SQL to qualify which artifacts will be passed to the outline script
	 * 
	 */ 
	public void setReportOutlineSql(String reportOutlineSql) {
		if (!Comparison.areEqual(this.reportOutlineSql, reportOutlineSql)) {
			this.reportOutlineSql = reportOutlineSql;
			setModified("reportOutlineSql");
		};
	}

	/** 
	 * Date the outline was last exceuted
	 * 
	 */ 
	public DbCalendar getOutlineLastRunDt() {
		return outlineLastRunDt;
	}

	/** 
	 * Date the outline was last exceuted
	 * 
	 */ 
	public void setOutlineLastRunDt(GregorianCalendar outlineLastRunDt) {
		if (!Comparison.areEqual(this.outlineLastRunDt, outlineLastRunDt)) {
			this.outlineLastRunDt = new DbCalendar();
			if (outlineLastRunDt != null) {
				this.outlineLastRunDt.setTimeInMillis(outlineLastRunDt.getTimeInMillis());
			}
			setModified("outlineLastRunDt");
		};
	}

	/** 
	 * Date the outline was last exceuted
	 * 
	 */ 
	public void setOutlineLastRunDt(long milliseconds) {
		if (this.outlineLastRunDt==null) {
			this.outlineLastRunDt = new DbCalendar();
		}
		if (!Comparison.areEqual(this.outlineLastRunDt, outlineLastRunDt)) {
			this.outlineLastRunDt.setTimeInMillis(milliseconds);
			setModified("outlineLastRunDt");
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
		columns.addColumn("reportId", Integer.class);
		columns.addColumn("reportRefId", Integer.class);
		columns.addColumn("reportRefDisplay", String.class);
		columns.addColumn("reportSql", String.class);
		columns.addColumn("parameterViewRefId", Integer.class);
		columns.addColumn("parameterViewRefDisplay", String.class);
		columns.addColumn("sqlParameterScript", String.class);
		columns.addColumn("xmlReportInd", Integer.class);
		columns.addColumn("xmlSelectScript", String.class);
		columns.addColumn("xmlSortScript", String.class);
		columns.addColumn("xmlFieldScript", String.class);
		columns.addColumn("xmlImageQual", String.class);
		columns.addColumn("xmlXpath", String.class);
		columns.addColumn("reportOutlineScript", String.class);
		columns.addColumn("reportOutlineSql", String.class);
		columns.addColumn("outlineLastRunDt", GregorianCalendar.class);
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
			if (reference != null && this.reportRefId > 0) setReportRefDisplay(reference.getDisplay(this.reportRefId));
			if (reference != null && this.parameterViewRefId > 0) setParameterViewRefDisplay(reference.getDisplay(this.parameterViewRefId));
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
	public void copyModifiedTo(ReportDataModel m) {
		Enumeration e1 = modifiedFields.keys();
		while (e1.hasMoreElements()) {
			String fieldName = (String) e1.nextElement();

			if (fieldName.compareTo("reportId")==0)
				m.setReportId(this.getReportId());
			else if (fieldName.compareTo("reportRefId")==0)
				m.setReportRefId(this.getReportRefId());
			else if (fieldName.compareTo("reportSql")==0)
				m.setReportSql(this.getReportSql());
			else if (fieldName.compareTo("parameterViewRefId")==0)
				m.setParameterViewRefId(this.getParameterViewRefId());
			else if (fieldName.compareTo("sqlParameterScript")==0)
				m.setSqlParameterScript(this.getSqlParameterScript());
			else if (fieldName.compareTo("xmlReportInd")==0)
				m.setXmlReportInd(this.getXmlReportInd());
			else if (fieldName.compareTo("xmlSelectScript")==0)
				m.setXmlSelectScript(this.getXmlSelectScript());
			else if (fieldName.compareTo("xmlSortScript")==0)
				m.setXmlSortScript(this.getXmlSortScript());
			else if (fieldName.compareTo("xmlFieldScript")==0)
				m.setXmlFieldScript(this.getXmlFieldScript());
			else if (fieldName.compareTo("xmlImageQual")==0)
				m.setXmlImageQual(this.getXmlImageQual());
			else if (fieldName.compareTo("xmlXpath")==0)
				m.setXmlXpath(this.getXmlXpath());
			else if (fieldName.compareTo("reportOutlineScript")==0)
				m.setReportOutlineScript(this.getReportOutlineScript());
			else if (fieldName.compareTo("reportOutlineSql")==0)
				m.setReportOutlineSql(this.getReportOutlineSql());
			else if (fieldName.compareTo("outlineLastRunDt")==0)
				m.setOutlineLastRunDt(this.getOutlineLastRunDt());
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
	public void updateWith (ReportModel m) {

		this.setReportId(m.getReportId());
		this.setReportRefId(m.getReportRefId());
		this.setReportRefDisplay(m.getReportRefDisplay());
		this.setReportSql(m.getReportSql());
		this.setParameterViewRefId(m.getParameterViewRefId());
		this.setParameterViewRefDisplay(m.getParameterViewRefDisplay());
		this.setSqlParameterScript(m.getSqlParameterScript());
		this.setXmlReportInd(m.getXmlReportInd());
		this.setXmlSelectScript(m.getXmlSelectScript());
		this.setXmlSortScript(m.getXmlSortScript());
		this.setXmlFieldScript(m.getXmlFieldScript());
		this.setXmlImageQual(m.getXmlImageQual());
		this.setXmlXpath(m.getXmlXpath());
		this.setReportOutlineScript(m.getReportOutlineScript());
		this.setReportOutlineSql(m.getReportOutlineSql());
		this.setOutlineLastRunDt(m.getOutlineLastRunDt());
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
	public boolean isEqualTo (ReportModel m) {

		if (!Comparison.areEqual(this.getReportId(),  m.getReportId())) return false;
		if (!Comparison.areEqual(this.getReportRefId(),  m.getReportRefId())) return false;
		if (!Comparison.areEqual(this.getReportSql(),  m.getReportSql())) return false;
		if (!Comparison.areEqual(this.getParameterViewRefId(),  m.getParameterViewRefId())) return false;
		if (!Comparison.areEqual(this.getSqlParameterScript(),  m.getSqlParameterScript())) return false;
		if (!Comparison.areEqual(this.getXmlReportInd(),  m.getXmlReportInd())) return false;
		if (!Comparison.areEqual(this.getXmlSelectScript(),  m.getXmlSelectScript())) return false;
		if (!Comparison.areEqual(this.getXmlSortScript(),  m.getXmlSortScript())) return false;
		if (!Comparison.areEqual(this.getXmlFieldScript(),  m.getXmlFieldScript())) return false;
		if (!Comparison.areEqual(this.getXmlImageQual(),  m.getXmlImageQual())) return false;
		if (!Comparison.areEqual(this.getXmlXpath(),  m.getXmlXpath())) return false;
		if (!Comparison.areEqual(this.getReportOutlineScript(),  m.getReportOutlineScript())) return false;
		if (!Comparison.areEqual(this.getReportOutlineSql(),  m.getReportOutlineSql())) return false;
		if (!Comparison.areEqual(this.getOutlineLastRunDt(),  m.getOutlineLastRunDt())) return false;
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
			sb.append("ReportModel:\r\n");
			sb.append("report_id:" + getReportId());
			sb.append("\r\n");
			sb.append("report_ref_id:" + getReportRefId());
			sb.append("\r\n");
			sb.append("report_ref_display:" + getReportRefDisplay());
			sb.append("\r\n");
			sb.append("report_sql:" + getReportSql());
			sb.append("\r\n");
			sb.append("parameter_view_ref_id:" + getParameterViewRefId());
			sb.append("\r\n");
			sb.append("parameter_view_ref_display:" + getParameterViewRefDisplay());
			sb.append("\r\n");
			sb.append("sql_parameter_script:" + getSqlParameterScript());
			sb.append("\r\n");
			sb.append("xml_report_ind:" + getXmlReportInd());
			sb.append("\r\n");
			sb.append("xml_select_script:" + getXmlSelectScript());
			sb.append("\r\n");
			sb.append("xml_sort_script:" + getXmlSortScript());
			sb.append("\r\n");
			sb.append("xml_field_script:" + getXmlFieldScript());
			sb.append("\r\n");
			sb.append("xml_image_qual:" + getXmlImageQual());
			sb.append("\r\n");
			sb.append("xml_xpath:" + getXmlXpath());
			sb.append("\r\n");
			sb.append("report_outline_script:" + getReportOutlineScript());
			sb.append("\r\n");
			sb.append("report_outline_sql:" + getReportOutlineSql());
			sb.append("\r\n");
			sb.append("outline_last_run_dt:" + CalendarUtility.Format(getOutlineLastRunDt(),AppFormats.getLongDateTimeFormat()));
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
			return "ReportModel:"; 
		}
	}
	public void setModelColDataAt(Object o, int modelCol) {
			switch (modelCol) {
			case 726: setReportId(((Integer) o).intValue()); break;
			case 727: setReportRefId(((Integer) o).intValue()); break;
			case 1165: setReportSql((String) o); break;
			case 5560: setParameterViewRefId(((Integer) o).intValue()); break;
			case 729: setSqlParameterScript((String) o); break;
			case 1602: setXmlReportInd(((Integer) o).intValue()); break;
			case 1603: setXmlSelectScript((String) o); break;
			case 1604: setXmlSortScript((String) o); break;
			case 1605: setXmlFieldScript((String) o); break;
			case 1606: setXmlImageQual((String) o); break;
			case 1607: setXmlXpath((String) o); break;
			case 5360: setReportOutlineScript((String) o); break;
			case 5361: setReportOutlineSql((String) o); break;
			case 5362: setOutlineLastRunDt((GregorianCalendar) o); break;
			case 730: setCreateDt((GregorianCalendar) o); break;
			case 731: setCreateUserId(((Integer) o).intValue()); break;
			case 878: setCreateUserName((String) o); break;
			case 732: setUpdateDt((GregorianCalendar) o); break;
			case 733: setUpdateUserId(((Integer) o).intValue()); break;
			case 879: setUpdateUserName((String) o); break;
			case 734: setUpdateCount(((Integer) o).intValue()); break;
			case 735: setActiveInd(((Integer) o).intValue()); break;
			case 736: setSystemAssignedVersionNbr(((Integer) o).intValue()); break;
			case 737: setRecordTypeRefId(((Integer) o).intValue()); break;
			default: Debug.LogWarning(this, ReferenceServices.getMsg(SystemMessageFramework.SWITCHSTATEMENTFAILED) + " modelColRefId: " + modelCol);
	} 
}

	public Object getModelColDataAt(int modelCol) {
			if (modelCol == 726) return new Integer(reportId);
			if (modelCol == 727) return new Integer(reportRefId);
			if (modelCol == 727) return reportRefDisplay;
			if (modelCol == 1165) return reportSql;
			if (modelCol == 5560) return new Integer(parameterViewRefId);
			if (modelCol == 5560) return parameterViewRefDisplay;
			if (modelCol == 729) return sqlParameterScript;
			if (modelCol == 1602) return new Integer(xmlReportInd);
			if (modelCol == 1603) return xmlSelectScript;
			if (modelCol == 1604) return xmlSortScript;
			if (modelCol == 1605) return xmlFieldScript;
			if (modelCol == 1606) return xmlImageQual;
			if (modelCol == 1607) return xmlXpath;
			if (modelCol == 5360) return reportOutlineScript;
			if (modelCol == 5361) return reportOutlineSql;
			if (modelCol == 5362) return outlineLastRunDt;
			if (modelCol == 730) return createDt;
			if (modelCol == 731) return new Integer(createUserId);
			if (modelCol == 731) return createUserName;
			if (modelCol == 732) return updateDt;
			if (modelCol == 733) return new Integer(updateUserId);
			if (modelCol == 733) return updateUserName;
			if (modelCol == 734) return new Integer(updateCount);
			if (modelCol == 735) return new Integer(activeInd);
			if (modelCol == 736) return new Integer(systemAssignedVersionNbr);
			if (modelCol == 737) return new Integer(recordTypeRefId);
			if (modelCol == 737) return recordTypeRefDisplay;
		return null;
	}

	public int getModelColDatabaseDataType(int modelCol) {
			if (modelCol == 726) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 727) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 727) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1165) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 5560) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 5560) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 729) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1602) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 1603) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1604) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1605) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1606) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 1607) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 5360) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 5361) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 5362) return DatabaseDataTypeFramework.DATE;
			if (modelCol == 730) return DatabaseDataTypeFramework.DATE;
			if (modelCol == 731) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 731) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 732) return DatabaseDataTypeFramework.DATE;
			if (modelCol == 733) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 733) return DatabaseDataTypeFramework.STRING;
			if (modelCol == 734) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 735) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 736) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 737) return DatabaseDataTypeFramework.INTEGER;
			if (modelCol == 737) return DatabaseDataTypeFramework.STRING;
			return DatabaseDataTypeFramework.STRING;
	}

	public boolean isNew() {
		return getReportId() == 0;
	}

	public Object getPrimaryValue() {
		return new Integer(getReportId());
	}

	public Object getDataAt(int i) {
			if (i == 0) return new Integer(reportId);
			if (i == 1) return new Integer(reportRefId);
			if (i == 2) return reportRefDisplay;
			if (i == 3) return reportSql;
			if (i == 4) return new Integer(parameterViewRefId);
			if (i == 5) return parameterViewRefDisplay;
			if (i == 6) return sqlParameterScript;
			if (i == 7) return new Integer(xmlReportInd);
			if (i == 8) return xmlSelectScript;
			if (i == 9) return xmlSortScript;
			if (i == 10) return xmlFieldScript;
			if (i == 11) return xmlImageQual;
			if (i == 12) return xmlXpath;
			if (i == 13) return reportOutlineScript;
			if (i == 14) return reportOutlineSql;
			if (i == 15) return outlineLastRunDt;
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
			if (i == 3) return DatabaseDataTypeFramework.STRING;
			if (i == 4) return DatabaseDataTypeFramework.INTEGER;
			if (i == 5) return DatabaseDataTypeFramework.STRING;
			if (i == 6) return DatabaseDataTypeFramework.STRING;
			if (i == 7) return DatabaseDataTypeFramework.INTEGER;
			if (i == 8) return DatabaseDataTypeFramework.STRING;
			if (i == 9) return DatabaseDataTypeFramework.STRING;
			if (i == 10) return DatabaseDataTypeFramework.STRING;
			if (i == 11) return DatabaseDataTypeFramework.STRING;
			if (i == 12) return DatabaseDataTypeFramework.STRING;
			if (i == 13) return DatabaseDataTypeFramework.STRING;
			if (i == 14) return DatabaseDataTypeFramework.STRING;
			if (i == 15) return DatabaseDataTypeFramework.DATE;
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
			case 0: setReportId(((Integer) o).intValue()); break;
			case 1: setReportRefId(((Integer) o).intValue()); break;
			case 2: setReportRefDisplay((String) o); break;
			case 3: setReportSql((String) o); break;
			case 4: setParameterViewRefId(((Integer) o).intValue()); break;
			case 5: setParameterViewRefDisplay((String) o); break;
			case 6: setSqlParameterScript((String) o); break;
			case 7: setXmlReportInd(((Integer) o).intValue()); break;
			case 8: setXmlSelectScript((String) o); break;
			case 9: setXmlSortScript((String) o); break;
			case 10: setXmlFieldScript((String) o); break;
			case 11: setXmlImageQual((String) o); break;
			case 12: setXmlXpath((String) o); break;
			case 13: setReportOutlineScript((String) o); break;
			case 14: setReportOutlineSql((String) o); break;
			case 15: setOutlineLastRunDt((GregorianCalendar) o); break;
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
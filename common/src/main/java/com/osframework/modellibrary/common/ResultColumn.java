package com.osframework.modellibrary.common;

import java.io.Serializable;


/**
 * Result column represents a single column in a ResultModel (e.g. PatientSearchModel).
 * The column may be sorted as part of the models column list.  
 *
 */
public class ResultColumn implements Serializable {

	private static final long serialVersionUID = 1L;
	private String fieldname;
	private String columnDisplay = "";
	private int originalPosition = 0;
	private Class columnClass;
	
	public ResultColumn(String fieldname, int origPos, Class colClass) {
		setFieldname(fieldname);
		setOriginalPosition(origPos);
		setColumnClass(colClass);
		setColumnDisplay(fieldname);
	}
	
	public String getFieldname() {
		return fieldname;
	}
	public void setFieldname(String fieldname) {
		this.fieldname = fieldname;
	}
	
	public int getOriginalPosition() {
		return originalPosition;
	}

	public void setOriginalPosition(int originalPosition) {
		this.originalPosition = originalPosition;
	}

	public Class getColumnClass() {
		return columnClass;
	}

	public void setColumnClass(Class columnClass) {
		this.columnClass = columnClass;
	}

	public String getColumnDisplay() {
		return columnDisplay;
	}

	public void setColumnDisplay(String columnDisplay) {
		this.columnDisplay = columnDisplay;
	}

}

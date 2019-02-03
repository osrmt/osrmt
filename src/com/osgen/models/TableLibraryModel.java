package models;

import java.util.GregorianCalendar;

public class TableLibraryModel {

	private String tableName;
	private int tableInd;
	private String columnName;
	private String javaType;
	private String dbType;
	private int columnSize;
	private String defaultValue;
	private int requiredInd;
	private String comments;
	private String version;
	private String requirementNumber;
	
	public String getColumnName() {
		return columnName;
	}
	public String getLowerColumnName() {
		return columnName.toLowerCase();
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public int getColumnSize() {
		return columnSize;
	}
	public void setColumnSize(int columnSize) {
		this.columnSize = columnSize;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getDbType() {
		return dbType;
	}
	public void setDbType(String dbType) {
		this.dbType = dbType;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public String getJavaType() {
		return javaType;
	}
	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}
	public int getRequiredInd() {
		return requiredInd;
	}
	public void setRequiredInd(int requiredInd) {
		this.requiredInd = requiredInd;
	}
	public int getTableInd() {
		return tableInd;
	}
	public void setTableInd(int tableInd) {
		this.tableInd = tableInd;
	}
	public String getTableName() {
		return tableName.toLowerCase();
	}
	public void setTableName(String tableName) {
		this.tableName = tableName.toLowerCase();
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	public String getJavaFieldGetter() {
		String field = getJavaFieldName();
		return "get" + field.substring(0,1).toUpperCase()
			+ field.substring(1);
	}
	
	public String getIndFieldGetter() {
		String field = getJavaFieldName().substring(0,getJavaFieldName().length()-3);
		return "is" + field.substring(0,1).toUpperCase()
			+ field.substring(1);
	}
	
	public String getNotIndFieldGetter() {
		String field = getJavaFieldName().substring(0,getJavaFieldName().length()-3);
		return "isNot" + field.substring(0,1).toUpperCase()
			+ field.substring(1);
	}
	
	public String getIndFieldSetter() {
		String field = getJavaFieldName().substring(0,getJavaFieldName().length()-3);
		return "set" + field.substring(0,1).toUpperCase()
			+ field.substring(1);
	}
	
	public String getNotIndFieldSetter() {
		String field = getJavaFieldName().substring(0,getJavaFieldName().length()-3);
		return "setNot" + field.substring(0,1).toUpperCase()
			+ field.substring(1);
	}
	
	public String getJavaFieldSetter() {
		String field = getJavaFieldName();
		return "set" + field.substring(0,1).toUpperCase()
			+ field.substring(1);
	}
	
	/**
	 * returns the default assignment e.g.
	 * = Calendar.getInstance()
	 * @return
	 */
	public String getDefaultAssignment() {
		if (this.getDefaultValue() == null) {
			return "";
		} else if (this.getDefaultValue().compareTo("Now()")==0) {
			return " = new GregorianCalendar();";
		} else if (this.getDefaultValue().length()>0) {
			return " = " + this.getDefaultValue();
		} else {
			return "";
		}
	}
	
	/**
	 * returns the default assignment e.g.
	 * = Calendar.getInstance()
	 * @return
	 */
	public String getDefaultAssignment(int tableModelInd) {
		if (tableModelInd == 1) {
			if (this.getDefaultValue() == null) {
				return "";
			} else if (this.getDefaultValue().compareTo("Now()")==0) {
				return " = DbCalendar.getDbCalendar()";
			} else if (this.getDefaultValue().length()>0) {
				return " = " + this.getDefaultValue();
			} else {
				return "";
			}
		} else {
			return getDefaultAssignment();
		}
	}
	
	public String getJavaName() {
		return getJavaFieldName().substring(0,1).toUpperCase()
		 + getJavaFieldName().substring(1);
	}
	
	/*
	 * Converts a typical column name 
	 * e.g. PERSON_ID, GENDER_REF_ID into a 
	 * java format e.g. personId, genderRefId
	 */
	public String getJavaFieldName() {
		StringBuffer sb = new StringBuffer(20);
		boolean underscored = false;
		String col = getColumnName();
		
		for (int i=0; i < col.length(); i++) {
			// do not retain the underscores
			if (col.substring(i,i+1).compareTo("_")!=0) {
				// capitalize if there was an underscore in
				// the last character
				if (underscored) {
					sb.append(col.substring(i,i+1).toUpperCase());
				} else {
					sb.append(col.substring(i,i+1).toLowerCase());
				}
			}
			if (col.substring(i,i+1).compareTo("_")==0) {
				underscored = true;
			} else {
				underscored = false;
			}
		}
		return sb.toString();
		
	}
	
	public String toString() {
		return "TableLibraryModel:" + getTableName()
		+ "," + getColumnName()
		+ "," + getDbType()
		+ "," + getJavaType()
		+ "," + getColumnSize()
		+ "," + (getDefaultValue() == null ? "" : getDefaultValue());
	}
	public String getRequirementNumber() {
		return requirementNumber;
	}
	public void setRequirementNumber(String requirementNumber) {
		this.requirementNumber = requirementNumber;
	}
	
	public String getClassName() {
		if (getJavaType().compareTo("int")==0) {
			return  "Integer.class";
		} else if (getJavaType().compareTo("double")==0) { 
			return  "Double.class";
		} else {
			return  getJavaType() + ".class";
		}
	}
}

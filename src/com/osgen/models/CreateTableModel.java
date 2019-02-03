package models;

import generator.MainGenerator;

import java.util.*;

public class CreateTableModel extends CreateBaseModel {
	
	protected TableLibraryList columns;
	protected IndexLibraryList indexes;
	protected TableLibraryModel table;
	protected String inset = "   ";
	
	protected static String nl = MainGenerator.nl;
	
	
	public void initialize(TableLibraryList columns, IndexLibraryList indexes) {
		this.columns = columns;
		this.indexes = indexes;
		Enumeration e1 = columns.elements();
		if (e1.hasMoreElements()) {
			table = (TableLibraryModel) e1.nextElement();
		}
	}
	
	public String getSql() {
		return getCreateTable()
		   + getCreateColumns() 
		   + endCreateTable(); 
	}
	
	public String dropSql() {
		return nl + "drop table " + this.table.getTableName().toLowerCase() + " cascade constraints;"; 
	}
	
	protected String getCreateColumns() {
		StringBuffer sb = new StringBuffer(256);
		Enumeration e1= columns.elements();
		boolean first = true;
		while (e1.hasMoreElements()) {
			TableLibraryModel col = (TableLibraryModel) e1.nextElement();
			sb.append(getTableColumn(first, col));
			first = false;
		}
		return sb.toString();
	}
	protected String getTableColumn(boolean first, TableLibraryModel col) {
		String line = "";
		if (!first) {
			line += ",";
		}
		return line + nl + inset + col.getLowerColumnName() 
				+ formatSpace(30,col.getLowerColumnName())
				+ getDbType(getType(col))
				+ getDbSize(col)
				+ getDefaultValue(col)
				+ getNotNull(col);
	}
	
	protected String getNotNull(TableLibraryModel col) {
		if (col.getRequiredInd()==1) {
			return " not null";
		} else {
			return "";
		}
	}
	protected String getDbSize(TableLibraryModel col) {
		switch (getType(col)) {
		case typeStringVarchar: return "(" + col.getColumnSize() + ")";
		case typeDoubleNumber: return "";
		case typeCalendarDate: return "";
		case typeIntNumber: return "";
		case typeStringLong: return "(2000)";
		}
		System.err.println("Column type not found " + getType(col) + " update CreateTableModel.getDbSize");
		return "";
	}
	protected String getDbType(int type) {
		switch (type) {
		case typeStringVarchar: return "varchar2";
		case typeDoubleNumber: return "number";
		case typeCalendarDate: return "date";
		case typeIntNumber: return "number";
		case typeStringLong: return "varchar2";
		}
		System.err.println("Column type not found " + type + " update CreateTableModel.getDbType");
		return "varchar2";
	}
	
	protected String getDefaultValue(TableLibraryModel col) {
		if (col.getDefaultValue() == null) {
			return "";
		} else {
			switch (getType(col)) {
			case typeStringVarchar: return " default '" + col.getDefaultValue() + "'";
			case typeDoubleNumber: return " default " + col.getDefaultValue();
			case typeCalendarDate: return " default " + getDefaultDate(col);
			case typeIntNumber: return " default " + col.getDefaultValue();
			case typeStringLong: return " default '" + col.getDefaultValue() + "'";
			}
			System.err.println("Column type not found " + getType(col) + " update CreateTableModel.getDefaultValue");
			return "varchar2";
		}
	}
	
	protected String getDefaultDate(TableLibraryModel col) {
		if (col.getDefaultValue().compareTo("Now()")==0) {
			return "sysdate";
		}
		System.err.println("Default value not found " + col.getDefaultValue() + " update CreateTableModel.getDefaultDate");
		return col.getDefaultValue();
	}
	
	protected String getCreateTable() {
		return nl + "create table " + table.getTableName().toLowerCase() + " (";
	}
	protected String endCreateTable() {
		return nl + ");";
	}
	
	public String toString() {
		return getSql();
	}

	public boolean isPrimaryKey(TableLibraryModel col) {
		Enumeration e1 = indexes.elements();
		while (e1.hasMoreElements()) {
			IndexLibraryModel mlm = (IndexLibraryModel) e1.nextElement();
			
			if (mlm.getColumnName().compareTo(col.getColumnName())==0
					&& mlm.getPrimaryInd()==1) {
				return true;
			}
		}	
		return false;
	}

	public IndexLibraryModel getPrimaryKeyIndex(TableLibraryModel col) {
		Enumeration e1 = indexes.elements();
		while (e1.hasMoreElements()) {
			IndexLibraryModel mlm = (IndexLibraryModel) e1.nextElement();
			
			if (mlm.getColumnName().compareTo(col.getColumnName())==0
					&& mlm.getPrimaryInd()==1) {
				return mlm;
			}
		}	
		return new IndexLibraryModel();
	}

}

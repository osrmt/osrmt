package models;

import java.util.Enumeration;

public class SQLServerCreateTable extends CreateTableModel {

	public String getSql() {
		return super.getSql();
	}
	
	protected String getDbType(int type) {
		switch (type) {
			case typeStringVarchar: return "varchar";
			case typeDoubleNumber: return "decimal";
			case typeCalendarDate: return "datetime";
			case typeIntNumber: return "int";
			case typeStringLong: return "text";
		}
		System.err.println("Column type not found " + type + " update CreateTableModel.getDbType");
		return "varchar2";
	}
	
	protected String getCreateColumns() {
		StringBuffer sb = new StringBuffer(256);
		Enumeration e1= columns.elements();
		boolean first = true;
		while (e1.hasMoreElements()) {
			TableLibraryModel col = (TableLibraryModel) e1.nextElement();
			sb.append(getTableColumn(first, col));
			if (isPrimaryKey(col)) {
				sb.append(nl + inset + "CONSTRAINT "
						+ getPrimaryKeyIndex(col).getIndexName().toLowerCase()
						+ " PRIMARY KEY ");
			}
			first = false;
		}
		return sb.toString();
	}
	
	protected String getDefaultDate(TableLibraryModel col) {
		if (col.getDefaultValue().compareTo("Now()")==0) {
			return "current_timestamp";
		}
		System.err.println("Default value not found " + col.getDefaultValue() + " update CreateTableModel.getDefaultDate");
		return col.getDefaultValue();
	}
	
	protected String getDbSize(TableLibraryModel col) {
		switch (getType(col)) {
		case typeStringVarchar: return "(" + col.getColumnSize() + ")";
		case typeDoubleNumber: return "";
		case typeCalendarDate: return "";
		case typeIntNumber: return "";
		case typeStringLong: return "";
		}
		System.err.println("Column type not found " + getType(col) + " update CreateTableModel.getDbSize");
		return "";
	}
	
	public String dropSql() {
		return nl + "drop table " + this.table.getTableName().toLowerCase() + " ;"; 
	}
	
}

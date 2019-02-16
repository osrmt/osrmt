package models;

public class mysqlCreateTable extends CreateTableModel {
	
	protected String getDbType(int type) {
		switch (type) {
			case typeStringVarchar: return "varchar";
			case typeDoubleNumber: return "double";
			case typeCalendarDate: return "datetime";
			case typeIntNumber: return "int";
			case typeStringLong: return "longtext";
		}
		System.err.println("Column type not found " + type + " update CreateTableModel.getDbType");
		return "varchar";
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
				+ getNotNull(col)
				+ getDefaultValue(col);
	}
	
	protected String getDefaultValue(TableLibraryModel col) {
		if (col.getDefaultValue() == null) {
			return "";
		} else {
			switch (getType(col)) {
			case typeStringVarchar: return " default '" + col.getDefaultValue() + "'";
			case typeDoubleNumber: return " default " + col.getDefaultValue();
			case typeCalendarDate: return "";// default " + getDefaultDate(col);
			case typeIntNumber: return " default " + col.getDefaultValue();
			case typeStringLong: return " default '" + col.getDefaultValue() + "'";
			}
			System.err.println("Column type not found " + getType(col) + " update CreateTableModel.getDefaultValue");
			return "varchar2";
		}
	}
	
	public String dropSql() {
		return nl + "drop table " + this.table.getTableName().toLowerCase() + " ;"; 
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

	protected String endCreateTable() {
		return nl + ") default char set utf8;";
	}
}

package models;

public class CreateBaseModel {
	
	public final static String dbTypeDoubleNumber = "double:number";
	public final static String dbTypeCalendarDate = "GregorianCalendar:date";
	public final static String dbTypeIntNumber = "int:number";
	public final static String dbTypeStringLong = "String:long";
	public final static String dbTypeStringVarchar = "String:varchar";
	public final static int typeStringVarchar = 0;
	public final static int typeDoubleNumber = 1;
	public final static int typeCalendarDate = 2;
	public final static int typeIntNumber = 3;
	public final static int typeStringLong = 4;

	/**
	 * Determines the type based upon concated java and db type
	 * @param javaDbType
	 * @return
	 */
	public int getType(TableLibraryModel m) {
		String dbtype = m.getJavaType() + ":" + m.getDbType();
		if (dbtype.compareTo(dbTypeDoubleNumber)==0) {
			return typeDoubleNumber;
		} else if (dbtype.compareTo(dbTypeCalendarDate)==0) {
			return typeCalendarDate;
		} else if (dbtype.compareTo(dbTypeIntNumber)==0) {
			return typeIntNumber;
		} else if (dbtype.compareTo(dbTypeStringLong)==0) {
			return typeStringLong;
		} else if (dbtype.compareTo(dbTypeStringVarchar)==0) {
			return typeStringVarchar;
		} else {
			System.err.println("Column not found " + dbtype + " update CreateTableModel.getColumnType");
			return typeStringVarchar;
		}
	}
	
	public static String spaces(int number) {
		if (number <= 1) {
			return " ";
		} else if (number == 2) {
			return "  ";
		} else if (number == 3) {
			return "   ";
		} else {
			String s = "";
			for (int i=0; i< number; i++) {
				s += " ";
			}
			return s;
		}
	}
	
	public static String formatSpace(int width, int used) {
		if (width > used) {
			return spaces(width-used);
		} else {
			return " ";
		}
	}

	public static String formatSpace(int width, String used) {
		if (used != null) {
			return formatSpace(width, used.length());
		} else {
			return formatSpace(width, 0);
		}
	}

}

package com.osframework.modellibrary.common;

import com.osframework.framework.logging.Debug;


public class DatabaseDataType {
	
	public final static int IntegerType = 118;
	public final static int DoubleType = 119;
	public final static int StringType = 120;
	public final static int DateType = 121;
	
	public static Class getTypeClass(int i) {
		switch (i) {
			case IntegerType: return Integer.class;
			case DoubleType: return Double.class;
			case StringType: return String.class;
			case DateType: return DbCalendar.class;
			default:
				Debug.LogError("DatabaseDataType","Type not accounted for: " + i);
		}
		return String.class;
	}
	
	
}

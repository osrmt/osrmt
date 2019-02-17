package com.osrmt.www.common;

import java.text.ParseException;
import java.util.GregorianCalendar;
import java.lang.reflect.InvocationTargetException;
import com.osframework.modellibrary.common.*;
import com.osframework.modellibrary.reference.group.DatabaseDataTypeFramework;
import com.osframework.framework.utility.*;

public class Converter {
		

	public static Object convertModelValue(int databaseDataType, Object value) throws ParseException, ConversionException {
		switch (databaseDataType) {
			case DatabaseDataTypeFramework.INTEGER: return convertInteger(value);
			case DatabaseDataTypeFramework.STRING: return convertString(value);
			case DatabaseDataTypeFramework.DATE: return convertDbCalendar(value);
			case DatabaseDataTypeFramework.DOUBLE: return convertDouble(value);		
		}
		return null;
	}
	
	
	public static Integer convertInteger(Object value) {
		if (value == null) {
			return new Integer(0); 
		} else {
			String ints = "" + value;
			if (ints.equals("")) {
				return new Integer(0);
			} else {
				return new Integer(Integer.parseInt(ints.toString()));
			}
		}
	}
	
	public static Double convertDouble(Object value) {
		if (value == null) {
			return new Double(0); 
		} else {
			String ints = "" + value;
			if (ints.equals("")) {
				return new Double(0);
			} else {
				return new Double(Double.parseDouble(ints.toString()));
			}
		}
	}
	
	public static Long convertLong(Object value) {
		if (value == null) {
			return new Long(0);
		} else {
			String ints = "" + value;
			if (ints.equals("")) {
				return new Long(0);
			} else {
				return new Long(Long.parseLong(ints.toString()));
			}
		}
	}
	
	public static String convertString(Object value) {
		if (value == null) {
			return null; 
		} else {
			String s = value.toString().trim();
			if (s.equals("")) {
				return null;
			} else {
				return s;
			}
		}
	}
	
	public static String convertDisplayString(Object value) {
		if (value == null) {
			return ""; 
		} else {
			String s = value.toString().trim();
			return s;
		}
	}
	
	/**
	 * Convert the value from the control to the type needed for the method
	 * 
	 * @param value
	 * @param setMethod
	 * @return
	 * @throws ParseException 
	 * @throws ConversionException 
	 * @throws ConversionException 
	 */
	public static DbCalendar convertDbCalendar(Object value) throws ParseException, ConversionException {
			if (value == null) {
				return null;
			} else if (value instanceof String){
				if (value.toString().trim().equals("")) {
					return null;
				}
				GregorianCalendar calendar = CalendarUtility.Parse(value.toString());
				if (calendar != null) {
					DbCalendar time = new DbCalendar();
					time.setTimeInMillis(calendar.getTimeInMillis());
					return time;
				} else {
					throw new ConversionException(); 
				}
			} else if (value instanceof GregorianCalendar) {
				GregorianCalendar calendar = (GregorianCalendar) value;
				DbCalendar time = new DbCalendar();
				time.setTimeInMillis(calendar.getTimeInMillis());
				return time;
			} else if (value instanceof java.util.Date) {
				java.util.Date date = (java.util.Date) value;
				DbCalendar time = new DbCalendar();
				time.setTimeInMillis(date.getTime());
				return time;
			} else if (value instanceof java.sql.Date) {
				java.sql.Date date = (java.sql.Date) value;
				DbCalendar time = new DbCalendar();
				time.setTimeInMillis(date.getTime());
				return time;
			} else { 
				throw new ConversionException(value.toString());
			}
	}
	
	public static boolean isDifferent(Object s, Object t) {
		return !isSame(s,t);
	}
	
	public static boolean isDateDifferent(DbCalendar s, DbCalendar t) {
		if (s == null && t == null) {
			return false;
		} else if (s == null) {
			return true;
		} else if (t == null) {
			return true;
		} else if (s.getTimeInMillis() == t.getTimeInMillis()) {
			return false;
		} else {
			return true;
		}
	}
	
	public static boolean isSame(Object s, Object t) {
		if (s == null && t == null) {
			return true;
		} else if (s == null) {
			return false;
		} else if (t == null) {
			return false;
		} else if (s.equals(t)) {
			return true;
		} else {
			return false;
		}
	}
}

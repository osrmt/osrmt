package com.osframework.framework.utility;

import java.util.*;
import java.text.*;

public class CalendarUtility {
	
	private static String shortDateFormat = "MM/dd/yy";
	private static String longDateTimeFormat = "MM/dd/yyyy HH:mm.ss";

	public static String Format(Calendar c, String format) {
		try {
			if (c == null) {
				return "";
			}
			if (format == null) {
				format = LongFormat();
			}
	        SimpleDateFormat sdf = new SimpleDateFormat(format); 
	        FieldPosition fpos = new FieldPosition(0); 
	
	        StringBuffer b = new StringBuffer(); 
	        return sdf.format(c.getTime(), b, fpos).toString(); 
		} catch (Exception ex) {
			return longDateTimeFormat;
		}
	}
	
	public static GregorianCalendar Parse(String s, String format) throws ParseException {
		if (s == null || format == null) {
			return null;
		}
        SimpleDateFormat sdf = new SimpleDateFormat(format); 
        Date d = sdf.parse(s);
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(d);
        return c; 
	}
	
	public static GregorianCalendar Parse(String s) throws ParseException {
		if (s == null) {
			return null;
		}
		SimpleDateFormat sdf;
		if (shouldParseDate(s, LongFormat())) {
	        sdf = new SimpleDateFormat(LongFormat()); 
		} else if (shouldParseDate(s, ShortDateFormat())) {
	        sdf = new SimpleDateFormat(ShortDateFormat()); 
		} else {
			sdf = new SimpleDateFormat(LongFormat()); 
		}
        Date d = sdf.parse(s);
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(d);
        return c; 
	}
	
	public static GregorianCalendar NullEndDate() {
		GregorianCalendar c = new GregorianCalendar();
		c.set(2099,11,31,0,0,0);
		return c;
	}
		
	public static String CurrentDateTime() {
		Calendar c = Calendar.getInstance();
		return Format(c, LongFormat());
	}

	public static String Format(Calendar c) {
		return Format(c, LongFormat());
	}

	public static String LongFormat() {
		return getLongDateTimeFormat();
	}

	public static String ShortDateFormat() {
		return getShortDateFormat();
	}
	
	/**
	 * Returns true if the string s is not null and is of the
	 * same length as the format.  Quicker to check then actually
	 * parsing the date.
	 * 
	 * @param s
	 * @param format
	 * @return
	 */
	public static boolean shouldParseDate(String s, String format) {
		if (s == null) {
			return false;
		} else {
			if (s.length() != format.length()) {
				return false;
			} else {
				return true;
			}
		}
	}

	public static String getLongDateTimeFormat() {
		return longDateTimeFormat;
	}

	public static void setLongDateTimeFormat(String longDateTimeFormat) {
		CalendarUtility.longDateTimeFormat = longDateTimeFormat;
	}

	public static String getShortDateFormat() {
		return shortDateFormat;
	}

	public static void setShortDateFormat(String shortDateFormat) {
		CalendarUtility.shortDateFormat = shortDateFormat;
	}
	
	
}

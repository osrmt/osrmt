package com.osframework.framework.locale;

public class AppFormats {

	private static String longDateTimeFormat = "MM/dd/yyyy HH:mm.ss";
	private static String shortDateTimeFormat = "MM/dd/yy HH:mm";
	private static String longDate = "MM/dd/yyyy";
	private static String shortDate = "MM/dd/yy";
	
	public static String getLongDate() {
		return longDate;
	}
	public static void setLongDate(String longDate) {
		AppFormats.longDate = longDate;
	}
	public static String getLongDateTimeFormat() {
		return longDateTimeFormat;
	}
	public static void setLongDateTimeFormat(String longDateTimeFormat) {
		AppFormats.longDateTimeFormat = longDateTimeFormat;
	}
	public static String getShortDate() {
		return shortDate;
	}
	public static void setShortDate(String shortDate) {
		AppFormats.shortDate = shortDate;
	}
	public static String getShortDateTimeFormat() {
		return shortDateTimeFormat;
	}
	public static void setShortDateTimeFormat(String shortDateTimeFormat) {
		AppFormats.shortDateTimeFormat = shortDateTimeFormat;
	}
}

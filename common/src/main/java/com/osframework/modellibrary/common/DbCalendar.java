package com.osframework.modellibrary.common;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import com.osframework.framework.utility.CalendarUtility;

public class DbCalendar extends GregorianCalendar  {

	private static final long serialVersionUID = 1L;
	private String dateFormat = null;
	
	public static DbCalendar getDbCalendar() {
		Calendar c = Calendar.getInstance();
		DbCalendar dbc = new DbCalendar();
		dbc.setTimeInMillis(c.getTimeInMillis());
		return dbc;
	}
	
	public static DbCalendar getDbCalendar(Calendar c) {
		DbCalendar dbc = new DbCalendar();
		if (c != null) {
			dbc.setTimeInMillis(c.getTimeInMillis());
		}		
		return dbc;
	}
	
	public void setShortDateFormat() {
		this.dateFormat = CalendarUtility.ShortDateFormat();
	}

	public void setLongFormat() {
		this.dateFormat = CalendarUtility.LongFormat();
	}

	public DbCalendar() {
		super();
	}

	public DbCalendar(TimeZone zone, Locale aLocale) {
		super(zone, aLocale);
	}

	public String toString() {
		if (dateFormat == null) {
			return CalendarUtility.Format(this);
		} else {
			return CalendarUtility.Format(this, dateFormat);
		}
	}

	public static DbCalendar getNow() {
		DbCalendar dtm = new DbCalendar();
		dtm.setTimeInMillis(new GregorianCalendar().getTimeInMillis());
		return dtm;
	}
}

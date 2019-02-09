package com.osframework.modellibrary.framework;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

import com.osframework.modellibrary.common.DbCalendar;

/*
 * SysLogModel represents a system log entry.
 */
public class SysLogModel implements Comparable {
	
	private static int nbrFields = 7;
	
	/// Unique identifier for this record
	public int Record;
	
	/// Milliseconds since the last log entry
	public int MSSinceLastEntry;
	
	/// Name of the application class which created the entry
	public String ClassName;
	
	/// Method of the application class which created the entry
	public String MethodName;
	
	/// Date/time the log entry was created
	public Calendar LogDateTime;
	
	/// Level of the log entry
	public int LogLevel;
	
	/// Message contents 
	public String Message;
	
	public static int columnCount() {
		return nbrFields;
	}
	
	public static Class[] getClasses() {
		Class[] classes = new Class[nbrFields];
		int i=0;
		classes[i++] = Integer.class;	// Record
		classes[i++] = Integer.class;	// Seconds
		classes[i++] = String.class;	// Class
		classes[i++] = String.class;	// Method
		classes[i++] = DbCalendar.class;//Date
		classes[i++] = Integer.class;	// Level
		classes[i++] = String.class;	// Msg
		return classes;
	}
	
	public static Vector getColumnNames() {
		Vector v = new Vector();
		v.add("ID");
		v.add("Milliseconds");
		v.add("Class");
		v.add("Method");
		v.add("Date");
		v.add("Level");
		v.add("Message");
		return v;
	}
	
	public Object getField(int fieldIndex) {
		switch (fieldIndex) {
			case 0: return new Integer(Record);
			case 1: return new Integer(MSSinceLastEntry);
			case 2: return ClassName;
			case 3: return MethodName;
			case 4: return DbCalendar.getDbCalendar(LogDateTime);
			case 5: return new Integer(LogLevel);
			case 6: return Message;
		}
		return null;
	}
	
	public void setField(Object o, int fieldIndex) {
		switch (fieldIndex) {
			case 0: Record = ((Integer) o).intValue(); break;
			case 1: MSSinceLastEntry = ((Integer) o).intValue(); break;
			case 2: ClassName = o.toString(); break;
			case 3: MethodName = o.toString(); break;
			case 4: LogDateTime = (Calendar) o; break;
			case 5: LogLevel = ((Integer) o).intValue(); break;
			case 6: Message = o.toString(); break;
		}
	}
	
	public int compareTo(Object arg0) {
		return 0;
	}
}

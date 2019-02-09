package com.osframework.appclient.system;

import java.util.Calendar;
import java.util.Enumeration;

import com.osframework.framework.locale.AppFormats;
import com.osframework.framework.logging.*;
import com.osframework.framework.utility.CalendarUtility;
import com.osframework.modellibrary.framework.*;

public class SystemLog {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			DebugService service = new DebugService();
			Calendar startTime = Calendar.getInstance();
			startTime.add(Calendar.MINUTE, -5);
			SysLogList list = service.GetLog(startTime, Calendar.getInstance());
			PrintList(list);
		} catch (Exception ex) {
			System.err.println(ex.toString());
		}
	}
	
	private static void PrintList(SysLogList list) throws DebugFileException {
		DebugService service = new DebugService();
		Enumeration e1 = list.elements();
		while (e1.hasMoreElements()) {
			SysLogModel m = (SysLogModel) e1.nextElement();
			String msg = service.GetDetails(m.Record, m.LogDateTime);
			if (msg != null) {
				msg = msg.replaceAll("\r\n","");
			}
			System.out.println(CalendarUtility.Format(m.LogDateTime,AppFormats.getLongDateTimeFormat())
					+ "\t" + DebugConfig.DebugLevelToString(m.LogLevel)
					+ "\t" + m.ClassName
					+ "\t" + m.MethodName 
					+ "\t" + m.MSSinceLastEntry
					+ "\t" + m.Message
					+ "\t" + msg);
		}
	}
	

}

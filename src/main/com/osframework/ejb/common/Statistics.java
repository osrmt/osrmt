package com.osframework.ejb.common;

import java.util.*;

public class Statistics {
	
	private static Hashtable methodList = new Hashtable();
	private static Hashtable stats = new Hashtable();
	
	public static void startCall() {
		String method = getMethodName();
		if (methodList.containsKey(method)) {
			methodList.remove(method);
		}
		methodList.put(method, new Long(Calendar.getInstance().getTimeInMillis()));

	}

	public static void stopCall() {
		String method = getMethodName();
		if (methodList.containsKey(method)) {
			Long longTime = (Long) methodList.get(method);
			long ms = MillisecondsSince(longTime.longValue(), Calendar.getInstance());
			storeStat(method, ms);
		}
	}

	private static String getMethodName() {
		Throwable t = new Throwable();
		if (t.getStackTrace().length > 3) {
			StackTraceElement e = t.getStackTrace()[3];
			return e.getMethodName();
		} else {
			return "Unknown";
		}
	}

	private static long MillisecondsSince(long lastWriteTime, Calendar logTime) 
	{
		long time = logTime.getTimeInMillis() - lastWriteTime;
		
		lastWriteTime = logTime.getTimeInMillis();
		
		return time;
	}
	
	public static class Timer {
		public String method;
		public long executions;
		public long avgMs;
		public String toString() {
			return method + "\tx " + executions + "\t" + avgMs + "ms";
		}
	}
	
	public static String statsToString(boolean resetStats) {
		Enumeration e1 = stats.elements();
		StringBuffer sb = new StringBuffer(1024);
		while (e1.hasMoreElements()) {
			Timer timer = (Timer) e1.nextElement();
			sb.append(timer.toString());
			sb.append("\t");
		}
		if (resetStats) {
			stats = new Hashtable();
		}
		return sb.toString();
	}
	
	private static void storeStat(String method, long ms) {
		if (stats.containsKey(method)) {
			Timer timer = (Timer) stats.get(method);
			long total = timer.executions * timer.avgMs;
			total += ms;
			timer.executions++;
			timer.avgMs = total/timer.executions;
			stats.remove(method);
			stats.put(method, timer);
		} else {
			Timer timer = new Timer();
			timer.executions = 1;
			timer.avgMs = ms;
			timer.method = method;
			stats.put(method, timer);
		}
	}
	
}

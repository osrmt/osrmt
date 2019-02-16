package com.osframework.framework.logging;

import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import com.osframework.modellibrary.framework.*;
import com.osframework.framework.utility.*;
import com.osframework.framework.locale.*;
/*
 * The debug class is the main utility framework class
 * for debugging and exception handling.
 * Typical usage:
 * try {
 * 		// your work here
 * } catch (Exception e) {
 * 		Debug.LogException(this, e);
 * }
 */ 
public class Debug {
	 
	private static String PackagePrefix = "com.osrmt.";
	private static String FrameworkPrefix = "com.osframework.";
	private static Hashtable listeners = new Hashtable();
	
	public static void addListener(Object id, ActionListener actionListener) {
		if (id != null && actionListener != null) {
			listeners.put(id, actionListener);
		}
	}

	public static void removeListener(Object id) {
		if (id != null && listeners.containsKey(id)) {
			listeners.remove(id);
		}
	}

	/// <summary>
	/// Is logging for Error-handling messages ON/OFF?
	/// </summary>
	public static boolean IsErrorLogged() 
	{
			return DebugLevel.Error <= DebugConfig.getDebugLevel();
	}

	/// <summary>
	/// Is logging for Warning messages ON/OFF?
	/// </summary>
	public static boolean IsInfoLogged()
	{
		return DebugLevel.Info <= DebugConfig.getDebugLevel();
	}

	/// <summary>
	/// Is logging for Informational messages ON/OFF?
	/// </summary>
	public static boolean IsWarnLogged()
	{
		return DebugLevel.Warning <= DebugConfig.getDebugLevel();
	}

	/// <summary>
	/// Is logging for Debug messages ON/OFF?
	/// </summary>
	public static boolean IsDebugLogged()
	{
		return DebugLevel.Debug <= DebugConfig.getDebugLevel();
	}
	/// <summary>
	/// Initialize the debug object at the debug level defined
	/// in application configuration settings.
	/// </summary>
	/// <param name="level"></param>
	static 
	{
		SetDebugLevel(DebugConfig.getDebugLevel());
	}

	/// <summary>
	/// Set the debug level for the application
	/// </summary>
	/// <param name="newLevel">The debug level requested</param>
	public static void SetDebugLevel(int newLevel) 
	{
		DebugConfig.setDebugLevel(newLevel);
	}


	/// <summary>
	/// LogException publishes the exception via the Exception Manager. It also logs
	/// the exception as an error if the currently set DebugLevel is greater than 
	/// or equal to DebugLevel.Error.
	/// </summary>
	/// <param name="application">Application logging the message. Typically called with 'this'</param>
	/// <param name="e">The exception to log</param>
	/// <param name="msg">Text message to record</param>
	public static void LogException (Object application, Exception e, String msg) 
	{
		try 
		{
			//e.printStackTrace(System.err);
			//System.err.println(msg);
			ServiceLog(application, getErrorMessage(e), DebugLevel.Error, true, msg);
		} 
		catch (Exception ex) 
		{
			ex.toString();
		}
	}

	/// <summary>
	/// LogException publishes the exception via the Exception Manager. It also logs
	/// the exception as an error if the currently set DebugLevel is greater than 
	/// or equal to DebugLevel.Error.
	/// Does not display on UI
	/// </summary>
	/// <param name="application">Application logging the message. Typically called with 'this'</param>
	/// <param name="e">The exception to log</param>
	/// <param name="msg">Text message to record</param>
	public static void LogExceptionOnly (Object application, Exception e, String msg) 
	{
		try 
		{
			//e.printStackTrace(System.err);
			//System.err.println(msg);
			ServiceLog(application, getErrorMessage(e), DebugLevel.Error, false, msg);
		} 
		catch (Exception ex) 
		{
			ex.toString();
		}
	}

	/// <summary>
	/// LogException publishes the exception via the Exception Manager. It also logs
	/// the exception as an error if the currently set DebugLevel is greater than 
	/// or equal to DebugLevel.Error.
	/// </summary>
	/// <param name="application">Application logging the message. Typically called with 'this'</param>
	/// <param name="e">The exception to log</param>
	public static void LogException (Object application, Exception e) 
	{
		try 
		{
			ServiceLog(application, getErrorMessage(e), DebugLevel.Error, true, e.getMessage());
		} 
		catch (Exception ex) 
		{
			ex.toString();
		}
	}


	/// <summary>
	/// LogException publishes the exception via the Exception Manager. It also logs
	/// the exception as an error if the currently set DebugLevel is greater than 
	/// or equal to DebugLevel.Error.
	/// </summary>
	/// <param name="application">Application logging the message. Typically called with 'this'</param>
	/// <param name="e">The exception to log</param>
	public static void LogExceptionOnly (Object application, Exception e) 
	{
		try 
		{
			ServiceLog(application, getErrorMessage(e), DebugLevel.Error, false, e.getMessage());
		} 
		catch (Exception ex) 
		{
			ex.toString();
		}
	}


	/// <summary>
	/// LogError logs the text as an error. Only logs if the currently set 
	/// DebugLevel is greater than or equal to DebugLevel.Error.
	/// </summary>
	/// <param name="application">Application logging the message. Typically called with 'this'</param>
	/// <param name="msg">Text message to record</param>
	public static void LogError(Object application, String msg) 
	{
		try 
		{
			ServiceLog(application, msg, DebugLevel.Error, true, msg);		
		} 
		catch (Exception ex) 
		{
			ex.toString();
		}
	}


	/// <summary>
	/// LogWarning logs the text as a warning. Only logs if the currently set 
	/// DebugLevel is greater than or equal to DebugLevel.Warning.
	/// </summary>
	/// <param name="application">Application logging the message</param>
	/// <param name="msg">Text message to record</param>
	public static void LogWarning(Object application, String msg) 
	{
		try 
		{
			ServiceLog(application, msg, DebugLevel.Warning, true, msg);		
		} 
		catch (Exception ex) 
		{
			ex.toString();
		}
	}

	/// <summary>
	/// LogWarning logs the text as a warning. Only logs if the currently set 
	/// DebugLevel is greater than or equal to DebugLevel.Warning.
	/// </summary>
	/// <param name="application">Application logging the message</param>
	/// <param name="msg">Text message to record</param>
	public static void LogWarningOnly(Object application, String msg) 
	{
		try 
		{
			ServiceLog(application, msg, DebugLevel.Warning, false, msg);		
		} 
		catch (Exception ex) 
		{
			ex.toString();
		}
	}

	/// <summary>
	/// LogInfo logs the text as informational. Only logs if the currently set 
	/// DebugLevel is greater than or equal to DebugLevel.Info.
	/// </summary>
	/// <param name="application">Application logging the message</param>
	/// <param name="msg">Text message to record</param>
	public static void LogInfo(Object application, String msg) 
	{
		try 
		{
			ServiceLog(application, msg, DebugLevel.Info, false, msg);		
		} 
		catch (Exception ex) 
		{
			ex.toString();
		}
	}


	/// <summary>
	/// LogDebug logs the text as debug. Only logs if the currently set 
	/// DebugLevel is greater than or equal to DebugLevel.Debug.
	/// </summary>
	/// <param name="application">Application logging the message</param>
	/// <param name="msg">Text message to record</param>
	public static void LogDebug(Object application, String msg) 
	{
		try 
		{
			ServiceLog(application, msg, DebugLevel.Debug, false, msg);		
		} 
		catch (Exception ex) 
		{
			ex.toString();
		}
	}

	/// <summary>
	/// </summary>
	/// <param name="application">The object logging the message</param>
	/// <param name="msg">Text message to record</param>
	/// <param name="level">The debug level requested</param>
	private static void ServiceLog(Object application, String msg, int level, boolean displayMessage, String guiMessage) 
	{
		/// This method uses System.Diagnostics.Trace
		/// We log when the level requested is less than or equal to the level 
		/// set (e.g. If the level is set to Warn, then an Error log will be 
		/// logged but an Info log will not. Compare to the DebugLevel 
		/// property of DebugConfig which is updated at run-time if the app.config 
		/// file is edited.
		if (level <= DebugConfig.getDebugLevel()) 
		{
			String methodName = "";
			String className = "";
			try 
			{
				Throwable t = new Throwable();
				if (t.getStackTrace().length > 2) {
					StackTraceElement e = t.getStackTrace()[2];
					methodName = e.getMethodName();
					className = e.getClassName();
					className = className.replaceAll(PackagePrefix, "");
					className = className.replaceAll(FrameworkPrefix, "");
				}
			} 
			catch (Exception ex)
			{
				ex.printStackTrace(System.err);
			}

			try
			{
				DebugFile.Write(Calendar.getInstance(), 
					DebugConfig.DebugLevelToString(level),
					className,
					methodName,
					msg);
			}
			catch(Exception e)
			{
				e.printStackTrace(System.err);
			}
			
			try
			{
				if (displayMessage && level > DebugLevel.Nothing && level < DebugLevel.Debug) {
					notifyListeners(application, className, methodName, guiMessage, level);
				}
			}
			catch(Exception e)
			{
				e.printStackTrace(System.err);
			}
		}
	}
	
	public static void displayGUIMessage(String msg) {
		try {
			notifyListeners(null, "", "", msg, DebugLevel.Info);
		} catch (Exception ex) {
			ex.toString();
		}
	}
	
	private static void notifyListeners(Object application, String className, String methodName, String guiMessage, int level) {
		DebugMessage debugMessage = new DebugMessage();
		if (application != null) {
			debugMessage.setApplicationMessage(application.toString());
		}
		debugMessage.setSourceClassName(className);
		debugMessage.setSourceMethodName(methodName);
		debugMessage.setErrorMessage(guiMessage);
		debugMessage.setDebugLevel(level);
		ActionEvent ae = new ActionEvent(debugMessage, 0, null);
		Enumeration e1 = listeners.elements();
		while (e1.hasMoreElements()) {
			ActionListener l = (ActionListener) e1.nextElement();
			l.actionPerformed(ae);
		}
	}
	
	private static String getErrorMessage(Exception e) {
		if (e == null) {
			return "";
		} else {
			String errMsg = e.toString();
			try {
				StringWriter sw = new StringWriter(); 
				e.printStackTrace(new PrintWriter(sw)); 
				errMsg = sw.toString();
			} catch (Exception ex) {				
			}
			return errMsg;
		}
	}
}

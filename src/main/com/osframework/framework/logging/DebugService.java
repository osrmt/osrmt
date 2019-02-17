package com.osframework.framework.logging;

import com.osframework.modellibrary.framework.*;
import com.osframework.framework.exceptions.*;
import java.util.*;

public class DebugService {
	
	/// Must assign msgPrefix and delimStr before mutex is obtained because 
	/// the Debug object may not have been created yet and it needs to 
	/// access the log file.
	private static boolean cIsRefreshRequired = true; // Reset after the file is read successfully
	// or the client request contains a different log level.
	private int mLastLogLevel = DebugConfig.getDebugLevel();
	private SysLogList mLogList = null;
	//	private long prev_ticks = 0;

	public DebugService()  
	{
		try 
		{
			mLogList = new SysLogList();
		} 
		catch (Exception e) 
		{
			e.printStackTrace(System.err);
		}
	}


	/// <summary>
	/// Returns the full collection of system log messages
	/// </summary>
	public SysLogList GetLog(Calendar startDate, Calendar endDate) throws DebugFileException 
	{
		return GetLog(startDate, endDate, DebugLevel.Debug);
	}

	/// <summary>
	/// Returns log messages which are less than 
	/// or equal to the maxLevel.
	/// </summary>
	/// <param name="maxLevel">Debug.ShowDebug would return all messages</param>
	/// <returns></returns>
	public SysLogList GetLog(Calendar startDate, Calendar endDate,int maxLevel) throws DebugFileException 
	{
		try 
		{
			SysLogList list = GetSystemLog(startDate, endDate, maxLevel);

			
			return list;
		} 
		catch (Exception e) 
		{
			throw new DebugFileException(ExceptionInfo.debugFileAccess, e);
		}
	}

	/// <summary>
	/// Get message content
	/// </summary>
	/// <param name="recordNbr"></param>
	/// <param name="dateTime"></param>
	/// <returns></returns>
	public String GetDetails(int recordNbr, Calendar dateTime) throws DebugFileException
	{
		String msg = "";
		try 
		{
			msg = DebugFile.GetMessageDetails(recordNbr, dateTime);
		} 
		catch (Exception e) 
		{
			throw new DebugFileException(ExceptionInfo.debugFileAccess, e);
		} 
		return msg;
	}

	/// <summary>
	/// Returns log messages which are less than or equal to the maxLevel.
	/// Debug.ShowDebug would return all messages.
	/// </summary>
	private SysLogList GetSystemLog(Calendar startDate, Calendar endDate, int maxLevel) throws DebugFileException 
	{
		Vector entries = null;
		// If the LogList is current, don't read the log file again.
		if (!cIsRefreshRequired && maxLevel == mLastLogLevel)
			return mLogList;
		
		mLastLogLevel = maxLevel;
		try
		{
			entries = DebugFile.GetEntries(startDate, endDate);
		}
		catch(Exception e)
		{
			throw new DebugFileException("Debug File Access Exception",e);
		}

		/// Get the current record discarding lines from the last partial record
		/// Then append lines to the record until the next record prefix is found.
		/// Then process the record and continue until buffer is empty.
		mLogList.clear();
		
		try
		{
			Enumeration e1 = entries.elements();
			while (e1.hasMoreElements()) 
			{
				DebugEntry entry = (DebugEntry) e1.nextElement();
				SysLogModel item = new SysLogModel();
				item.Record = entry.Record;
				item.ClassName = entry.ClassName;
				item.LogDateTime = entry.Date;
				item.LogLevel = DebugConfig.StringToDebugLevel(entry.DebugLevel);
				item.Message = entry.Message;
				item.MethodName = entry.MethodName;
				item.MSSinceLastEntry = entry.MSsince;
				if (item.LogLevel <= maxLevel) 
				{
					mLogList.add(item);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace(System.err);
			cIsRefreshRequired = true;
		}
		return mLogList;
	}
			

}

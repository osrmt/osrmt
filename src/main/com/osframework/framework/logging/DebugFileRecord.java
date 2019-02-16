package com.osframework.framework.logging;

import java.io.*;
import java.util.Arrays;
import java.util.Calendar;
import com.osframework.framework.utility.*;

public class DebugFileRecord {
	public static int RecordSize = 256;
	public static int FixedFieldTotalSize = 0;
	
	public static String DateFormat = "dd/MMM/yy HH:mm.ss";
	public static Calendar Date = Calendar.getInstance();
	public static String ClassName = "";
	public static String DebugLevel = "";
	public static String MethodName = "";
	public static int MSsince = 0;
	public static String Message = "";
	public static String MessageDetails = null;

	private static int DateSize = 18;
	private static int ClassSize = 90;
	private static int LevelSize = 7;
	private static int MethodSize = 40;
	private static int SinceSize = 4;
	private static int MsgSize = 0;

	/// <summary>
	/// Initialize variables
	/// </summary>
	public DebugFileRecord () 
	{
		DateSize = DateFormat.length();
		FixedFieldTotalSize = DateSize + ClassSize + LevelSize + 
							  MethodSize + SinceSize;
		MsgSize = RecordSize - FixedFieldTotalSize;
	}

	/// <summary>
	/// Initialize a new record
	/// </summary>
	/// <param name="date"></param>
	/// <param name="className"></param>
	/// <param name="debugLevel"></param>
	/// <param name="methodName"></param>
	/// <param name="msSince"></param>
	/// <param name="msg"></param>
	public static void Init (Calendar date, String className, String debugLevel, String methodName, int msSince, String msg) 
	{

		Date = date;
		ClassName = className;
		DebugLevel = debugLevel;
		MethodName = methodName;
		MSsince = msSince;
		Message = msg;
		if (ClassName == null)
			ClassName = "";
		if (MethodName == null)
			MethodName = "";
		if (Message == null)
			Message = "";
		if (ClassName.length() > ClassSize)
			ClassName = ClassName.substring(0,ClassSize);
		if (MethodName.length() > MethodSize)
			MethodName = MethodName.substring(0, MethodSize);
		if (DebugLevel.length() > LevelSize)
			DebugLevel = DebugLevel.substring(0, LevelSize);
		if (Message.length() > MsgSize) 
		{
			Message = Message.substring(0, MsgSize);
			MessageDetails = msg;
		} 
		else 
		{
			MessageDetails = null;
		}
	}

	/// <summary>
	/// Write self to a file
	/// </summary>
	/// <param name="fs"></param>
	public static void Write (RandomAccessFile fs) throws IOException 
	{
		// requires caller to have used seek
		long offset = fs.getFilePointer();
		int localOffset = 0;
		
		byte[] bytes = new byte[DateSize+ClassSize+LevelSize+MethodSize];
		Arrays.fill(bytes,(byte) 0); 
		String dateString = CalendarUtility.Format(Date,DateFormat);
		System.arraycopy(dateString.getBytes(),0,bytes,localOffset,dateString.length());
		localOffset += DateSize;
		
		System.arraycopy(ClassName.getBytes(),0,bytes,localOffset,ClassName.length());
		localOffset += ClassSize;
		
		System.arraycopy(DebugLevel.getBytes(),0,bytes,localOffset,DebugLevel.length());
		localOffset += LevelSize;
		
		System.arraycopy(MethodName.getBytes(),0,bytes,localOffset,MethodName.length());
		localOffset += MethodSize;
		
		fs.write(bytes);		
		offset += localOffset;
		
		fs.writeInt(MSsince);		
		offset += SinceSize;

		//TODO Message does not seem to be read or perhaps written
		//when running the TestDebugFile
		fs.write(Message.getBytes());		
	}

	/// <summary>
	/// Read self from file
	/// </summary>
	/// <param name="rec"></param>
	/// <param name="fs"></param>
	public static void Read (int rec, RandomAccessFile fs) throws IOException, java.text.ParseException
	{
		try 
		{
			fs.seek(DebugFileHeader.Length + (rec * DebugFileRecord.RecordSize));
			long offset = fs.getFilePointer();
			// Copy date to bytes
			byte[] bytes = new byte[DateSize];
			fs.read(bytes);
			String dateString = new String(bytes);
			Date = CalendarUtility.Parse(dateString, DateFormat);
			offset += DateSize;

			// Class Name
			fs.seek(offset);
			bytes = new byte[ClassSize];
			fs.read(bytes);
			ClassName = new String(bytes);
			if (ClassName.indexOf('\0')>-1) 
				ClassName = ClassName.substring(0, ClassName.indexOf('\0'));
			offset += ClassSize;

			// Debug Level
			fs.seek(offset);
			bytes = new byte[LevelSize];
			fs.read(bytes);
			DebugLevel = new String(bytes);
			if (DebugLevel.indexOf('\0')>-1) 
				DebugLevel = DebugLevel.substring(0, DebugLevel.indexOf('\0'));
			offset += LevelSize;

			
			// Method Name
			fs.seek(offset);
			bytes = new byte[MethodSize];
			fs.read(bytes);
			MethodName = new String(bytes);
			if (MethodName.indexOf('\0')>-1) 
				MethodName = MethodName.substring(0, MethodName.indexOf('\0'));
			offset += MethodSize;

			// Milliseconds since last write
			fs.seek(offset);
			MSsince= fs.readInt();
			offset += SinceSize;

			// Message
			fs.seek(offset);
			bytes = new byte[MsgSize];
			fs.read(bytes);
			Message = new String(bytes);
			if (Message.indexOf('\0')>-1) 
				Message = Message.substring(0, Message.indexOf('\0'));

			
		} 
		catch (IOException e) 
		{
			throw e;
		}
	}


}

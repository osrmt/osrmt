package com.osframework.framework.logging;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Vector;

import com.osframework.framework.utility.CalendarUtility;

public class DebugFile {
	
	/// <summary>
	/// Keep track of the last time file is written to
	/// </summary>
	private static long lastWriteTime = Calendar.getInstance().getTimeInMillis();

	/// <summary>
	/// Initialize the debug files
	/// </summary>
	static
	{
		try 
		{
			InitFiles();
		} 
		catch (Exception e) 
		{
			try 
			{	
				e.printStackTrace(System.err);
			} //ensure finally is executed
			catch (Exception ex) { ex.toString(); }
		} 
	}

	/// <summary>
	/// Create the files if they do not exist or if the configuration
	/// file sizes have changed
	/// </summary>
	/// <param name="init"></param>
	private static void InitFiles() throws IOException
	{
		// blank header
		DebugFileHeader.Init();
		// size of the debug detailed content circular file
		DebugFileHeader.DetailFileSize = DebugConfig.getDebugDetailsFileSizeBytes();
		File debugLogFile = new File(DebugConfig.getDebugLogFile());
		if (!debugLogFile.exists()) 
		{
			// Create file 
			InitializeFile(DebugConfig.getDebugLogFile(), DebugConfig.getDebugFileSizeBytes());
			WriteFileHeader();
		} 
		else 
		{
			// Check size
			if (debugLogFile.length() != DebugConfig.getDebugFileSizeBytes()) 
			{
				// Create file
				InitializeFile(DebugConfig.getDebugLogFile(), DebugConfig.getDebugFileSizeBytes());
				WriteFileHeader();
			}
		}
		File debugDetailsLogFile = new File(DebugConfig.getDebugDetailsLogFile());
		if (!debugDetailsLogFile.exists()) 
		{
			// Create file
			InitializeFile(DebugConfig.getDebugDetailsLogFile(), DebugConfig.getDebugDetailsFileSizeBytes());
		} 
		else 
		{
			// Check size
			if (debugDetailsLogFile.length() != DebugConfig.getDebugDetailsFileSizeBytes()) 
			{
				// Create file
				InitializeFile(DebugConfig.getDebugDetailsLogFile(), DebugConfig.getDebugDetailsFileSizeBytes());
			}
		}

	}

	/// <summary>
	/// Create an empty file of the requested size
	/// </summary>
	/// <param name="fileName"></param>
	/// <param name="sizeMB"></param>
	private static void InitializeFile(String fileName, int sizeBytes) throws IOException 
	{
		File file = new File(fileName);
		FileWriter fw = null;
		try 
		{
			fw = new FileWriter(file, false);
			char[] debugBytes = new char[sizeBytes];
			Arrays.fill(debugBytes,'\0');
			fw.write(debugBytes);
			fw.close();
		} 
		catch (IOException e) 
		{
			try {
				if (fw != null) 
				{
					fw.close();
				}
			} catch (Exception ex) { ex.toString(); }
			throw e;
		} 
	}

	/// <summary>
	/// Write out the file header to the debug file
	/// </summary>
	/// <param name="init"></param>
	private static void WriteFileHeader() throws IOException
	{
		RandomAccessFile fw = null;
		try 
		{
			fw = new RandomAccessFile(new File(DebugConfig.getDebugLogFile()), "rw");
			fw.seek(0);
			DebugFileHeader.write(fw);
			fw.close();
		} 
		catch (IOException e) 
		{
			if (fw != null) 
			{
				try {
				fw.close();
				} catch (Exception ex) { ex.toString(); }
			}
			throw e;
		}
	}

	/// <summary>
	/// Write a debug line of the specified parameters
	/// </summary>
	/// <param name="logDate"></param>
	/// <param name="level"></param>
	/// <param name="className"></param>
	/// <param name="methodName"></param>
	/// <param name="msg"></param>
	public static void Write(Calendar logDate, String level, String className, String methodName, String msg) throws IOException 	
	{
		try 
		{
			WriteLog(logDate, level, className, methodName, msg) ;
		} 
		catch (IOException e) 
		{
			try 
			{
				e.printStackTrace(System.err);
			} 
			catch (Exception ex) { ex.toString(); }
		}
	}

	/// <summary>
	/// Helper method to write the debug line
	/// </summary>
	/// <param name="logDate"></param>
	/// <param name="level"></param>
	/// <param name="className"></param>
	/// <param name="methodName"></param>
	/// <param name="msg"></param>
	private static void WriteLog(Calendar logDate, String level,String className, String methodName, String msg) throws IOException 
	{
		RandomAccessFile fs = null;
		try 
		{
			// Read the debug file header
			fs = new RandomAccessFile(DebugConfig.getDebugLogFile(),"rw");
			DebugFileHeader.Read(fs);
			
			// Determine the position to write the next record
			int offset = DebugFileHeader.Length + DebugFileHeader.NextRecord();
			fs.seek(offset);

			// Create the record 
			int msSince = (int) MillisecondsSince(logDate);
			DebugFileRecord.Init(logDate, className, level, methodName, msSince, msg);
			DebugFileRecord.Write(fs);

			// Create the details
			if (DebugFileRecord.MessageDetails != null) 
			{
				try 
				{
					DebugDetailFile.Write(DebugFileRecord.MessageDetails);
				} 
				catch (Exception e2) 
				{
					e2.printStackTrace(System.err);
				} 
			} 
			else 
			{
				DebugFileHeader.UpdateIndex(DebugFileHeader.RecordTail, 0, 0);
			}

			DebugFileHeader.write(fs);
			fs.close();
		} 
		catch (IOException e) 
		{
			if (fs != null) 
			{
				fs.close();
			}
			throw e;
		}
	}

	/// <summary>
	/// Retreive the debug line contents 
	/// </summary>
	/// <param name="rec"></param>
	/// <param name="dateTime"></param>
	/// <returns></returns>
	public static String GetMessageDetails(int rec, Calendar dateTime) throws IOException
	{
		RandomAccessFile fs = null;
		String msg = "";
		try 
		{
			fs = new RandomAccessFile(DebugConfig.getDebugLogFile(), "rw");
			DebugFileHeader.Read(fs);
			DebugFileRecord.Read(rec, fs);
			fs.close();
			if (dateTime.compareTo(DebugFileRecord.Date)==0) 
			{
				msg = DebugDetailFile.Read(rec);
			}
		} 
		catch (Exception e) 
		{ 
			try 
			{
				if (fs != null) 
				{
					fs.close();
				}
				e.printStackTrace(System.err);
			} 
			catch (Exception ex) { ex.toString(); }
		}
		return msg;
	}

	/// <summary>
	/// Debug method to dump the file header
	/// </summary>
	public static void DumpHeader() 
	{
		RandomAccessFile fs = null;
		RandomAccessFile fs2 = null;
		try 
		{

			fs = new RandomAccessFile(DebugConfig.getDebugLogFile(), "rw");
			DebugFileHeader.Read(fs);
			fs.close();

/*			println("  Debug Log File:\t " + DebugConfig.getDebugLogFile());
			println("Details Log File:\t " + DebugConfig.getDebugDetailsLogFile());
			println(" Max Nbr Records:\t " + DebugFileHeader.MaxNbrRecords);
			println("   Header Record:\t " + DebugFileHeader.RecordHead);
			println("     Tail Record:\t " + DebugFileHeader.RecordTail);
			println("     Record Size:\t " + DebugFileRecord.RecordSize);
			println("Next Byte Detail:\t " + DebugFileHeader.DetailNextByte);
*/			
			int newLine =0;
			for (int i=0; i< DebugFileHeader.indexEntries.length; i++) 
			{
				int start = DebugFileHeader.indexEntries[i].DetailOffset;
				int end = DebugFileHeader.indexEntries[i].DetailEnd;
				if (start!=0 || end!=0) 
				{
					System.out.print("#" + i + " " + start + " to " + end + "\t");
					if (newLine >0 && newLine%3 == 0) 
						System.out.println("");
					newLine++;
				}
			}
			System.out.println("");
			
			fs2 = new RandomAccessFile(DebugConfig.getDebugLogFile(), "rw");
			for (int i=0; i< DebugFileHeader.indexEntries.length; i++) 
			{
				try 
				{
					int start = DebugFileHeader.indexEntries[i].DetailOffset;
					int end = DebugFileHeader.indexEntries[i].DetailEnd;
					if (start!=0 || end!=0) 
					{
						DebugFileRecord.Read(i, fs2);
						String msg = DebugFile.GetMessageDetails(i, DebugFileRecord.Date);
						Calendar date = DebugFileRecord.Date;
						System.out.println("#" + i + " " + CalendarUtility.Format(date, DebugFileRecord.DateFormat) + " " 
								+ DebugFileRecord.ClassName + " " + DebugFileRecord.MethodName + " "
								+ msg.replaceAll("\r\n", "\t").replaceAll("\n", "\t"));
					}
				} 
				catch (Exception e) 
				{
					System.err.println("Error reading rec " + i + " " + e.toString());
				}
			}
			fs2.close();
		
		} 
		catch (Exception e) 
		{
			try 
			{
				e.printStackTrace(System.err);
				if (fs != null) 
				{
					fs.close();
				}
				if (fs2 != null) 
				{
					fs2.close();
				}
			} 
			catch (Exception ex) { ex.toString(); }
		}
	}

	/// <summary>
	/// Get a list of entries between the dates specified
	/// </summary>
	/// <param name="startDate"></param>
	/// <param name="endDate"></param>
	/// <returns></returns>
	public static Vector GetEntries(Calendar startDate, Calendar endDate) 
	{
		Vector list = new Vector();
		try 
		{
			list = GetRecords(startDate, endDate);
		} 
		catch (Exception e) 
		{
			try 
			{
				e.printStackTrace(System.err);
			} 
			catch (Exception ex) { ex.toString(); }
		} 
		return list;	
	}

	/// <summary>
	/// Helper method to get records between dates specified
	/// </summary>
	/// <param name="startDate"></param>
	/// <param name="endDate"></param>
	/// <returns></returns>
	private static Vector GetRecords (Calendar startDate, Calendar endDate) throws IOException, java.text.ParseException
	{
		RandomAccessFile fs = null;
		try 
		{
			Vector list = new Vector(DebugFileHeader.getNumberOfRecords());
			fs = new RandomAccessFile(DebugConfig.getDebugLogFile(),"rw");
			DebugFileHeader.Read(fs);
			int rec = DebugFileHeader.RecordHead;
			// TODO binary search would speed up this loop
			for (int i=0; i<DebugFileHeader.getNumberOfRecords(); i++) 
			{
				try {
					DebugFileRecord.Read(rec, fs);
					DebugEntry entry = new DebugEntry();
					entry.Record = rec;
					entry.ClassName = DebugFileRecord.ClassName;
					entry.Date = DebugFileRecord.Date;
					entry.DebugLevel = DebugFileRecord.DebugLevel;
					entry.Message = DebugFileRecord.Message;
					entry.MethodName = DebugFileRecord.MethodName;
					entry.MSsince = DebugFileRecord.MSsince;
					if (entry.Date.getTimeInMillis() >= startDate.getTimeInMillis() && 
						entry.Date.getTimeInMillis() <= endDate.getTimeInMillis()) 
					{
						list.add(entry);
					}
					rec++;
					if (rec >= DebugFileHeader.MaxNbrRecords) 
					{
						rec = 0;
					}
				} catch (Exception ex) {
					ex.printStackTrace(System.err);
				}
			}

			fs.close();
			return list;
		} 
		catch (IOException e) 
		{
			if (fs != null) 
			{
				fs.close();
			}
			throw e;
		}
	}

	/// <summary>
	/// Determine the milliseconds passed since the last line was written
	/// </summary>
	/// <param name="logTime"></param>
	/// <returns></returns>
	private static long MillisecondsSince(Calendar logTime) 
	{
		long time = logTime.getTimeInMillis() - lastWriteTime;
		
		lastWriteTime = logTime.getTimeInMillis();
		
		return time;
	}
}



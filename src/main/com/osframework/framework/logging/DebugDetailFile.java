package com.osframework.framework.logging;

import java.io.*;

public class DebugDetailFile {
	
	/*
	 * Read in record
	 */
	public static String Read(int record) throws IOException
	{
		RandomAccessFile raf = null;
		try 
		{
			raf = new RandomAccessFile(DebugConfig.getDebugDetailsLogFile(), "r");
			int start = DebugFileHeader.indexEntries[record].DetailOffset;
			int end = DebugFileHeader.indexEntries[record].DetailEnd;
			byte[] byteRecord = new byte[end-start];
			raf.seek(start);
			raf.read(byteRecord); 
			String s = new String(byteRecord);
			raf.close();
			return s;
		} 
		catch (IOException e) 
		{
			if (raf != null) 
			{
				try {
				raf.close();
				} catch (Exception ex) { ex.toString(); }
			}
			throw e;
		}
	}

	/*
	 * 
	 */
	public static void Write(String details) throws IOException
	{
		RandomAccessFile raf = null;
		try 
		{
			raf = new RandomAccessFile(DebugConfig.getDebugDetailsLogFile(), "rw");

			byte[] byteRecord = details.getBytes();
			int startByte = 0;
			if (byteRecord.length + DebugFileHeader.DetailNextByte < DebugFileHeader.DetailFileSize) 
			{
				startByte = DebugFileHeader.DetailNextByte;
				raf.seek(startByte);
				DebugFileHeader.DetailNextByte += byteRecord.length;
			} 
			else 
			{
				startByte = 0;
				raf.seek(startByte);
				DebugFileHeader.DetailNextByte = byteRecord.length;
			}
			
			raf.write(byteRecord);
			raf.close();
			DebugFileHeader.InvalidateIndex(startByte, DebugFileHeader.DetailNextByte);
			DebugFileHeader.UpdateIndex(DebugFileHeader.RecordTail, startByte, DebugFileHeader.DetailNextByte);
		} 
		catch (IOException e) 
		{
			if (raf != null) 
			{
				try {
				raf.close();
				} catch (Exception ex) { ex.toString(); }
			}
			throw e;
		}
	}

}

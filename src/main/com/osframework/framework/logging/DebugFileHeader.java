package com.osframework.framework.logging;

import java.io.*;

/// <summary>
/// The debug file header consists of
/// [MaxNbrRecords][RecordHead][RecordTail][DetailNextByte][Index Entries...]
/// </summary>
public class DebugFileHeader {
	
	private static int NbrHeaderIntFields = 4;
	/// <summary>
	/// Maximum number of records in the debug file
	/// </summary>
	public static int MaxNbrRecords = 0;
	/// <summary>
	/// Position of the first record in the circular debug file
	/// </summary>
	public static int RecordHead = 0;
	/// <summary>
	/// Position of the last record in the circular debug file
	/// </summary>
	public static int RecordTail = -1;
	/// <summary>
	/// Position of the next byte to write to in the detail file
	/// </summary>
	public static int DetailNextByte = 0;
	/// <summary>
	/// Index for record to detail pointers
	/// </summary>
	public static DebugIndexEntry[] indexEntries = null;

	/// <summary>
	/// Length of the file header
	/// </summary>
	public static int Length = 0;


	/// <summary>
	/// Size in bytes of details file
	/// </summary>
	public static int DetailFileSize = 0;

	/// <summary>
	/// Initialize a new blank header
	/// </summary>
	public static void Init() 
	{
		int fileLength = DebugConfig.getDebugFileSizeBytes();
		// MaxNbrRecords, RecordHead, RecordTail, DetailNextByte
		fileLength -= (IntegerLength() * NbrHeaderIntFields);
		Length = (IntegerLength() * NbrHeaderIntFields);
		// RecordSize + index entry of 4 integers
		double nbrRecs = fileLength/(double) (DebugFileRecord.RecordSize + (IntegerLength() * NbrHeaderIntFields));
		MaxNbrRecords = (int) (Math.floor(nbrRecs));
		Length += MaxNbrRecords * (IntegerLength() * NbrHeaderIntFields);
		indexEntries = new DebugIndexEntry[MaxNbrRecords];
		for (int i=0; i<indexEntries.length; i++) 
		{
			indexEntries[i] = new DebugIndexEntry();
		}
	}

	/// <summary>
	/// Return the number of records in the file
	/// </summary>
	public static int getNumberOfRecords()  
	{
		if (RecordTail >= RecordHead) 
		{
			return (RecordTail - RecordHead)+1;
		} 
		else 
		{
			return MaxNbrRecords;
		}
	}


	/// <summary>
	/// Returns the offset to write the next record
	/// </summary>
	/// <returns></returns>
	public static int NextRecord() 
	{
		if (RecordHead == 0 && RecordTail == -1) 
		{
			RecordTail = 0;
			return 0;
		}
		if (RecordTail >= RecordHead) 
		{
			RecordTail++;
		} 
		else 
		{
			RecordTail++;
			RecordHead++;
		}
		if (RecordHead >= MaxNbrRecords) 
		{
			RecordHead = 0;
		}
		if (RecordTail >= MaxNbrRecords) 
		{
			RecordTail = 0;
			RecordHead = 1;
		}
		return RecordTail * DebugFileRecord.RecordSize;
	}

	/// <summary>
	/// Write header to an open file
	/// </summary>
	/// <param name="fs"></param>
	public static void write(RandomAccessFile dos) throws IOException 
	{
		dos.seek(0);
		
		dos.writeInt(MaxNbrRecords);

		dos.writeInt(RecordHead);

		dos.writeInt(RecordTail);

		dos.writeInt(DetailNextByte);

		for (int i=0; i<indexEntries.length; i++) 
		{
			dos.writeInt(indexEntries[i].RecordNumber);

			dos.writeInt(indexEntries[i].DetailOffset);

			dos.writeInt(indexEntries[i].DetailEnd);
		}
		
	}

	/// <summary>
	/// Invalidate pointers to the detail file when the 
	/// content is overwritten
	/// </summary>
	/// <param name="start"></param>
	/// <param name="end"></param>
	public static void InvalidateIndex(int start, int end) 
	{
		for (int i=0; i<indexEntries.length; i++) 
		{
			if ((indexEntries[i].DetailOffset < end 
				&& indexEntries[i].DetailOffset > start) 
				|| (indexEntries[i].DetailEnd < end 
				&& indexEntries[i].DetailEnd > start))
			{
				indexEntries[i].DetailOffset = 0;
				indexEntries[i].DetailEnd = 0;
			}
		}
	}

	/// <summary>
	/// Update the index pointer
	/// </summary>
	/// <param name="row"></param>
	/// <param name="start"></param>
	/// <param name="end"></param>
	public static void UpdateIndex(int row, int start, int end) 
	{
		if (row < indexEntries.length) 
		{
			indexEntries[row].DetailOffset = start;
			indexEntries[row].DetailEnd = end;
		}
	}

	/// <summary>
	/// Read self from file
	/// </summary>
	/// <param name="fs"></param>
	public static void Read(RandomAccessFile dis) throws IOException 
	{
		dis.seek(0);
		
		MaxNbrRecords = dis.readInt();
		RecordHead = dis.readInt();
		RecordTail = dis.readInt();
		DetailNextByte = dis.readInt();

		if (indexEntries==null) 
		{
			indexEntries = new DebugIndexEntry[MaxNbrRecords];
			for (int i=0; i<indexEntries.length; i++) 
			{
				indexEntries[i] = new DebugIndexEntry();
			}
		}
		for (int i=0; i<indexEntries.length; i++) 
		{

			indexEntries[i].RecordNumber = dis.readInt();

			indexEntries[i].DetailOffset = dis.readInt();

			indexEntries[i].DetailEnd = dis.readInt();
		}
	}

	/// <summary>
	/// Byte length of an integer
	/// </summary>
	/// <returns></returns>
	public static int IntegerLength () 
	{
		return Integer.SIZE / 8;
	}

}

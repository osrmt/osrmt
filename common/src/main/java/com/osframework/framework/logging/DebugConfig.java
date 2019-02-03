package com.osframework.framework.logging;


/*
 * The debug config file stores all relevant properties
 * for the debug file.  Size, location etc.
 * TODO Read from a properties file
 * 
 * TODO DebugConfig and a number of similar static classes
 * could benefit from a static constructor or another way
 * to ensure the constructor is executed prior to the 
 * static property being read.
 */
public class DebugConfig {
	
	private static String debugLogFile;
	private static String debugDetailsLogFile;
	private static int debugLevel = DebugLevel.Debug;
	
	private static int debugFileSizeMB;
	private static int debugDetailsFileSizeMB;

	static
	{
		ReadSettings();
	}

	private static void ReadSettings() 
	{

		String level = "Debug";
		debugLevel = StringToDebugLevel(level);
		debugLogFile = "system.log";
		debugDetailsLogFile = "system_details.log";
		debugFileSizeMB = 1;//
		debugDetailsFileSizeMB = 1;
	}


	/// <summary>
	/// Converts the Debug Level String representation into the equivalent 
	/// DebugLevel enumeration. Invalid values generate a DebugLevel.Warn
	/// </summary>
	/// <param name="level">Valid values are "Nothing", "Error", "Info", 
	/// "Debug", "Warn". </param>
	/// <returns>DebugLevel enumeration</returns>
	public static int StringToDebugLevel(String level)
	{ 
		int debugLevel = DebugLevel.Info;
		if (level.compareToIgnoreCase("Nothing")==0) {
				debugLevel = DebugLevel.Nothing;
		} else if (level.compareToIgnoreCase("Error")==0) {
			debugLevel = DebugLevel.Error;
		} else if (level.compareToIgnoreCase("Warn")==0) {
			debugLevel = DebugLevel.Warning;
		} else if (level.compareToIgnoreCase("Info")==0) {
			debugLevel = DebugLevel.Info;
		} else if (level.compareToIgnoreCase("Debug")==0) {
			debugLevel = DebugLevel.Debug;
		}
		return debugLevel;
	}		
	
	/// <summary>
	/// Converts the DebugLevel enumeration into the equivalent String 
	/// representation. Invalid values generate a "Warn".
	/// </summary>
	/// <param name="level">Valid DebugLevel values are Nothing, Error, Info, 
	/// Debug, Warn. </param>
	/// <returns>One of the following Strings - "Nothing", "Error", "Info", 
	/// "Debug", "Warn". </returns>
	public static String DebugLevelToString(int level)
	{
		String theString = "warn";
		switch (level)
		{
			case DebugLevel.Nothing:
				theString = "Nothing";
				break;
			case DebugLevel.Error:
				theString = "Error";
				break;
			case DebugLevel.Warning:
				theString = "Warn";
				break;
			case DebugLevel.Info:
				theString = "Info";
				break;
			case DebugLevel.Debug:
				theString = "Debug";
				break;
		}
		return theString;
	}

	public static int getDebugDetailsFileSizeMB() {
		return debugDetailsFileSizeMB;
	}
	
	public static int getDebugDetailsFileSizeBytes() {
		return debugDetailsFileSizeMB * 1024 * 1024;
	}
	
	public static void setDebugDetailsFileSizeMB(int debugDetailsFileSizeMB) {
		DebugConfig.debugDetailsFileSizeMB = debugDetailsFileSizeMB;
	}

	public static String getDebugDetailsLogFile() {
		return debugDetailsLogFile;
	}

	public static void setDebugDetailsLogFile(String debugDetailsLogFile) {
		DebugConfig.debugDetailsLogFile = debugDetailsLogFile;
	}

	public static int getDebugFileSizeMB() {
		return debugFileSizeMB;
	}

	public static int getDebugFileSizeBytes() {
		return debugFileSizeMB * 1024 * 1024;
	}

	public static void setDebugFileSizeMB(int debugFileSizeMB) {
		DebugConfig.debugFileSizeMB = debugFileSizeMB;
	}

	public static int getDebugLevel() {
		return debugLevel;
	}

	public static void setDebugLevel(int debugLevel) {
		DebugConfig.debugLevel = debugLevel;
	}

	public static String getDebugLogFile() {
		return debugLogFile;
	}

	public static void setDebugLogFile(String debugLogFile) {
		DebugConfig.debugLogFile = debugLogFile;
	}


}

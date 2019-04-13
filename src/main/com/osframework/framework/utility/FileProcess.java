package com.osframework.framework.utility;

import java.io.*;
import java.util.*;

import com.osframework.framework.logging.Debug;

public class FileProcess {
	
	public static String nl() {
		if (isWindowsOS()) {
			return "\r\n";
		} else {
			return "\n";
		}		
	}
	
	public static String getFilePath(String path, String filename) {
		if (path == null) {
			return filename;
		}
		if (isWindowsOS()) {
			if (!path.endsWith("\\")) {
				path = path + "\\";
			}
		} else {
			if (!path.endsWith("/")) {
				path = path + "/";
			}
		}
		return path + filename;
	}
	
	/*
	 * Execute a file
	 */
	public static void executeFile(String path, String filename) throws IOException {
		if (!path.endsWith("\\")) {
			path = path + "\\";
		}
		executeFile(path + filename);
	}
	
	/*
	 * Execute a file
	 */
	public static void executeFile(String filepath) throws IOException {
		if (isWindowsOS()) {
			Runtime.getRuntime().exec("cmd /Q /C start \"OSRMT\" \"" + filepath + "\"");
		} else {
			Runtime.getRuntime().exec(filepath);
		}
	}
	
	
	public static boolean isWindowsOS() {
		String osname = System.getProperty("os.name");
		if (osname != null) {
			return osname.toLowerCase().contains("windows");
		} else {
			return false;
		}
	}
	
	public static String getCurrentDirectory() {
		return System.getProperty("user.dir");
	}
	
	public static String getTemporaryDirectory() {
		String dirname = "temp";
		FileSystemUtil.createDirectory(dirname);
		return dirname + "/";
	}
	
	public static String getRandomFile(String baseName) {
		Random rand = new Random(Calendar.getInstance().getTimeInMillis());
		String localFile = "~" + rand.nextInt(1000000000) + baseName;
		return localFile;
	}
	
	public static void exportData(Object o) throws Exception {
		if (o != null) {
			String className = o.getClass().getSimpleName();
			File directory = new File("schema");
			if (!directory.exists()) {
				directory.mkdir();
			}
			String fileName = getFilePath("schema",className);
			SerializeUtility.serialize(new File(fileName), o);
		}
	}
	
	public static Object importData(Object o) throws Exception {
		if (o != null) {
			String className = o.getClass().getSimpleName();
			String fileName = getFilePath("schema",className);
			return SerializeUtility.deserialize(new File(fileName));
		} else {
			return null;
		}
	}
}
